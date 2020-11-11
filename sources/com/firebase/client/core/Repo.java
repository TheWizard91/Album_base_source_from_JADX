package com.firebase.client.core;

import com.fasterxml.jackson.core.util.MinimalPrettyPrinter;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseApp;
import com.firebase.client.FirebaseError;
import com.firebase.client.FirebaseException;
import com.firebase.client.MutableData;
import com.firebase.client.Transaction;
import com.firebase.client.ValueEventListener;
import com.firebase.client.authentication.AuthenticationManager;
import com.firebase.client.core.PersistentConnection;
import com.firebase.client.core.SparseSnapshotTree;
import com.firebase.client.core.SyncTree;
import com.firebase.client.core.persistence.NoopPersistenceManager;
import com.firebase.client.core.persistence.PersistenceManager;
import com.firebase.client.core.utilities.Tree;
import com.firebase.client.core.view.Event;
import com.firebase.client.core.view.EventRaiser;
import com.firebase.client.core.view.QuerySpec;
import com.firebase.client.snapshot.ChildKey;
import com.firebase.client.snapshot.EmptyNode;
import com.firebase.client.snapshot.IndexedNode;
import com.firebase.client.snapshot.Node;
import com.firebase.client.snapshot.NodeUtilities;
import com.firebase.client.utilities.DefaultClock;
import com.firebase.client.utilities.LogWrapper;
import com.firebase.client.utilities.OffsetClock;
import com.firebase.client.utilities.Utilities;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class Repo implements PersistentConnection.Delegate {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final int TRANSACTION_MAX_RETRIES = 25;
    private static final String TRANSACTION_OVERRIDE_BY_SET = "overriddenBySet";
    private static final String TRANSACTION_TOO_MANY_RETRIES = "maxretries";
    private FirebaseApp app;
    private final AuthenticationManager authenticationManager;
    /* access modifiers changed from: private */
    public final PersistentConnection connection;
    private final Context ctx;
    private final LogWrapper dataLogger;
    public long dataUpdateCount = 0;
    private final EventRaiser eventRaiser;
    private boolean hijackHash = false;
    /* access modifiers changed from: private */
    public SnapshotHolder infoData;
    /* access modifiers changed from: private */
    public SyncTree infoSyncTree;
    private boolean loggedTransactionPersistenceWarning = false;
    private long nextWriteId = 1;
    /* access modifiers changed from: private */
    public SparseSnapshotTree onDisconnect;
    private final LogWrapper operationLogger;
    private final RepoInfo repoInfo;
    /* access modifiers changed from: private */
    public final OffsetClock serverClock = new OffsetClock(new DefaultClock(), 0);
    /* access modifiers changed from: private */
    public SyncTree serverSyncTree;
    private final LogWrapper transactionLogger;
    private long transactionOrder = 0;
    /* access modifiers changed from: private */
    public Tree<List<TransactionData>> transactionQueueTree;

    private enum TransactionStatus {
        INITIALIZING,
        RUN,
        SENT,
        COMPLETED,
        SENT_NEEDS_ABORT,
        NEEDS_ABORT
    }

    private static class FirebaseAppImpl extends FirebaseApp {
        protected FirebaseAppImpl(Repo repo) {
            super(repo);
        }
    }

    Repo(RepoInfo repoInfo2, Context ctx2) {
        this.repoInfo = repoInfo2;
        this.ctx = ctx2;
        this.app = new FirebaseAppImpl(this);
        this.operationLogger = ctx2.getLogger("RepoOperation");
        this.transactionLogger = ctx2.getLogger("Transaction");
        this.dataLogger = ctx2.getLogger("DataOperation");
        this.eventRaiser = new EventRaiser(ctx2);
        PersistentConnection persistentConnection = new PersistentConnection(ctx2, repoInfo2, this);
        this.connection = persistentConnection;
        AuthenticationManager authenticationManager2 = new AuthenticationManager(ctx2, this, repoInfo2, persistentConnection);
        this.authenticationManager = authenticationManager2;
        authenticationManager2.resumeSession();
        scheduleNow(new Runnable() {
            public void run() {
                Repo.this.deferredInitialization();
            }
        });
    }

    /* access modifiers changed from: private */
    public void deferredInitialization() {
        this.connection.establishConnection();
        PersistenceManager persistenceManager = this.ctx.getPersistenceManager(this.repoInfo.host);
        this.infoData = new SnapshotHolder();
        this.onDisconnect = new SparseSnapshotTree();
        this.transactionQueueTree = new Tree<>();
        this.infoSyncTree = new SyncTree(this.ctx, new NoopPersistenceManager(), new SyncTree.ListenProvider() {
            public void startListening(final QuerySpec query, Tag tag, SyncTree.SyncTreeHash hash, final SyncTree.CompletionListener onComplete) {
                Repo.this.scheduleNow(new Runnable() {
                    public void run() {
                        Node node = Repo.this.infoData.getNode(query.getPath());
                        if (!node.isEmpty()) {
                            Repo.this.postEvents(Repo.this.infoSyncTree.applyServerOverwrite(query.getPath(), node));
                            onComplete.onListenComplete((FirebaseError) null);
                        }
                    }
                });
            }

            public void stopListening(QuerySpec query, Tag tag) {
            }
        });
        this.serverSyncTree = new SyncTree(this.ctx, persistenceManager, new SyncTree.ListenProvider() {
            public void startListening(QuerySpec query, Tag tag, SyncTree.SyncTreeHash hash, final SyncTree.CompletionListener onListenComplete) {
                Repo.this.connection.listen(query, hash, tag, new PersistentConnection.RequestResultListener() {
                    public void onRequestResult(FirebaseError error) {
                        Repo.this.postEvents(onListenComplete.onListenComplete(error));
                    }
                });
            }

            public void stopListening(QuerySpec query, Tag tag) {
                Repo.this.connection.unlisten(query);
            }
        });
        restoreWrites(persistenceManager);
        updateInfo(Constants.DOT_INFO_AUTHENTICATED, Boolean.valueOf(this.authenticationManager.getAuth() != null));
        updateInfo(Constants.DOT_INFO_CONNECTED, false);
    }

    private void restoreWrites(PersistenceManager persistenceManager) {
        List<UserWriteRecord> writes = persistenceManager.loadUserWrites();
        Map<String, Object> serverValues = ServerValues.generateServerValues(this.serverClock);
        long lastWriteId = Long.MIN_VALUE;
        for (final UserWriteRecord write : writes) {
            Firebase.CompletionListener onComplete = new Firebase.CompletionListener() {
                public void onComplete(FirebaseError error, Firebase ref) {
                    Repo.this.warnIfWriteFailed("Persisted write", write.getPath(), error);
                    Repo.this.ackWriteAndRerunTransactions(write.getWriteId(), write.getPath(), error);
                }
            };
            if (lastWriteId < write.getWriteId()) {
                lastWriteId = write.getWriteId();
                this.nextWriteId = write.getWriteId() + 1;
                if (write.isOverwrite()) {
                    if (this.operationLogger.logsDebug()) {
                        this.operationLogger.debug("Restoring overwrite with id " + write.getWriteId());
                    }
                    this.connection.put(write.getPath().toString(), write.getOverwrite().getValue(true), (String) null, onComplete);
                    this.serverSyncTree.applyUserOverwrite(write.getPath(), write.getOverwrite(), ServerValues.resolveDeferredValueSnapshot(write.getOverwrite(), serverValues), write.getWriteId(), true, false);
                } else {
                    if (this.operationLogger.logsDebug()) {
                        this.operationLogger.debug("Restoring merge with id " + write.getWriteId());
                    }
                    this.connection.merge(write.getPath().toString(), write.getMerge().getValue(true), onComplete);
                    this.serverSyncTree.applyUserMerge(write.getPath(), write.getMerge(), ServerValues.resolveDeferredValueMerge(write.getMerge(), serverValues), write.getWriteId(), false);
                }
            } else {
                throw new IllegalStateException("Write ids were not in order.");
            }
        }
    }

    public AuthenticationManager getAuthenticationManager() {
        return this.authenticationManager;
    }

    public FirebaseApp getFirebaseApp() {
        return this.app;
    }

    public String toString() {
        return this.repoInfo.toString();
    }

    public void scheduleNow(Runnable r) {
        this.ctx.requireStarted();
        this.ctx.getRunLoop().scheduleNow(r);
    }

    public void postEvent(Runnable r) {
        this.ctx.requireStarted();
        this.ctx.getEventTarget().postEvent(r);
    }

    /* access modifiers changed from: private */
    public void postEvents(List<? extends Event> events) {
        if (!events.isEmpty()) {
            this.eventRaiser.raiseEvents(events);
        }
    }

    public long getServerTime() {
        return this.serverClock.millis();
    }

    /* access modifiers changed from: package-private */
    public boolean hasListeners() {
        return !this.infoSyncTree.isEmpty() || !this.serverSyncTree.isEmpty();
    }

    public void onDataUpdate(String pathString, Object message, boolean isMerge, Tag tag) {
        List<? extends Event> events;
        if (this.operationLogger.logsDebug()) {
            this.operationLogger.debug("onDataUpdate: " + pathString);
        }
        if (this.dataLogger.logsDebug()) {
            this.operationLogger.debug("onDataUpdate: " + pathString + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR + message);
        }
        this.dataUpdateCount++;
        Path path = new Path(pathString);
        if (tag != null) {
            if (isMerge) {
                try {
                    Map<Path, Node> taggedChildren = new HashMap<>();
                    for (Map.Entry<String, Object> entry : ((Map) message).entrySet()) {
                        taggedChildren.put(new Path(entry.getKey()), NodeUtilities.NodeFromJSON(entry.getValue()));
                    }
                    events = this.serverSyncTree.applyTaggedQueryMerge(path, taggedChildren, tag);
                } catch (FirebaseException e) {
                    e = e;
                    this.operationLogger.error("FIREBASE INTERNAL ERROR", e);
                }
            } else {
                events = this.serverSyncTree.applyTaggedQueryOverwrite(path, NodeUtilities.NodeFromJSON(message), tag);
            }
        } else if (isMerge) {
            Map<Path, Node> changedChildren = new HashMap<>();
            for (Map.Entry<String, Object> entry2 : ((Map) message).entrySet()) {
                changedChildren.put(new Path(entry2.getKey()), NodeUtilities.NodeFromJSON(entry2.getValue()));
            }
            events = this.serverSyncTree.applyServerMerge(path, changedChildren);
        } else {
            events = this.serverSyncTree.applyServerOverwrite(path, NodeUtilities.NodeFromJSON(message));
        }
        try {
            if (events.size() > 0) {
                rerunTransactions(path);
            }
            postEvents(events);
        } catch (FirebaseException e2) {
            e = e2;
            this.operationLogger.error("FIREBASE INTERNAL ERROR", e);
        }
    }

    public void onRangeMergeUpdate(Path path, List<RangeMerge> merges, Tag tag) {
        List<? extends Event> events;
        if (this.operationLogger.logsDebug()) {
            this.operationLogger.debug("onRangeMergeUpdate: " + path);
        }
        if (this.dataLogger.logsDebug()) {
            this.operationLogger.debug("onRangeMergeUpdate: " + path + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR + merges);
        }
        this.dataUpdateCount++;
        if (tag != null) {
            events = this.serverSyncTree.applyTaggedRangeMerges(path, merges, tag);
        } else {
            events = this.serverSyncTree.applyServerRangeMerges(path, merges);
        }
        if (events.size() > 0) {
            rerunTransactions(path);
        }
        postEvents(events);
    }

    /* access modifiers changed from: package-private */
    public void callOnComplete(final Firebase.CompletionListener onComplete, final FirebaseError error, Path path) {
        final Firebase ref;
        if (onComplete != null) {
            ChildKey last = path.getBack();
            if (last == null || !last.isPriorityChildName()) {
                ref = new Firebase(this, path);
            } else {
                ref = new Firebase(this, path.getParent());
            }
            postEvent(new Runnable() {
                public void run() {
                    onComplete.onComplete(error, ref);
                }
            });
        }
    }

    /* access modifiers changed from: private */
    public void ackWriteAndRerunTransactions(long writeId, Path path, FirebaseError error) {
        if (error == null || error.getCode() != -25) {
            List<? extends Event> clearEvents = this.serverSyncTree.ackUserWrite(writeId, !(error == null), true, this.serverClock);
            if (clearEvents.size() > 0) {
                rerunTransactions(path);
            }
            postEvents(clearEvents);
        }
    }

    public void setValue(Path path, Node newValueUnresolved, Firebase.CompletionListener onComplete) {
        Path path2 = path;
        Node node = newValueUnresolved;
        if (this.operationLogger.logsDebug()) {
            this.operationLogger.debug("set: " + path2);
        }
        if (this.dataLogger.logsDebug()) {
            this.dataLogger.debug("set: " + path2 + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR + node);
        }
        Map<String, Object> serverValues = ServerValues.generateServerValues(this.serverClock);
        Node newValue = ServerValues.resolveDeferredValueSnapshot(node, serverValues);
        long writeId = getNextWriteId();
        postEvents(this.serverSyncTree.applyUserOverwrite(path, newValueUnresolved, newValue, writeId, true, true));
        PersistentConnection persistentConnection = this.connection;
        String path3 = path.toString();
        Object value = node.getValue(true);
        final Path path4 = path;
        Map<String, Object> map = serverValues;
        final long j = writeId;
        final Firebase.CompletionListener completionListener = onComplete;
        persistentConnection.put(path3, value, new Firebase.CompletionListener() {
            public void onComplete(FirebaseError error, Firebase ref) {
                Repo.this.warnIfWriteFailed("setValue", path4, error);
                Repo.this.ackWriteAndRerunTransactions(j, path4, error);
                Repo.this.callOnComplete(completionListener, error, path4);
            }
        });
        rerunTransactions(abortTransactions(path2, -9));
    }

    public void updateChildren(Path path, CompoundWrite updates, Firebase.CompletionListener onComplete, Map<String, Object> unParsedUpdates) {
        Path path2 = path;
        Map<String, Object> map = unParsedUpdates;
        if (this.operationLogger.logsDebug()) {
            this.operationLogger.debug("update: " + path2);
        }
        if (this.dataLogger.logsDebug()) {
            this.dataLogger.debug("update: " + path2 + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR + map);
        }
        if (updates.isEmpty()) {
            if (this.operationLogger.logsDebug()) {
                this.operationLogger.debug("update called with no changes. No-op");
            }
            callOnComplete(onComplete, (FirebaseError) null, path2);
            return;
        }
        final Firebase.CompletionListener completionListener = onComplete;
        Map<String, Object> serverValues = ServerValues.generateServerValues(this.serverClock);
        CompoundWrite resolved = ServerValues.resolveDeferredValueMerge(updates, serverValues);
        long writeId = getNextWriteId();
        postEvents(this.serverSyncTree.applyUserMerge(path, updates, resolved, writeId, true));
        final Path path3 = path;
        Map<String, Object> map2 = serverValues;
        final long j = writeId;
        this.connection.merge(path.toString(), map, new Firebase.CompletionListener() {
            public void onComplete(FirebaseError error, Firebase ref) {
                Repo.this.warnIfWriteFailed("updateChildren", path3, error);
                Repo.this.ackWriteAndRerunTransactions(j, path3, error);
                Repo.this.callOnComplete(completionListener, error, path3);
            }
        });
        rerunTransactions(abortTransactions(path2, -9));
    }

    public void purgeOutstandingWrites() {
        if (this.operationLogger.logsDebug()) {
            this.operationLogger.debug("Purging writes");
        }
        postEvents(this.serverSyncTree.removeAllWrites());
        abortTransactions(Path.getEmptyPath(), -25);
        this.connection.purgeOutstandingWrites();
    }

    public void removeEventCallback(EventRegistration eventRegistration) {
        List<Event> events;
        if (Constants.DOT_INFO.equals(eventRegistration.getQuerySpec().getPath().getFront())) {
            events = this.infoSyncTree.removeEventRegistration(eventRegistration);
        } else {
            events = this.serverSyncTree.removeEventRegistration(eventRegistration);
        }
        postEvents(events);
    }

    public void onDisconnectSetValue(final Path path, final Node newValue, final Firebase.CompletionListener onComplete) {
        this.connection.onDisconnectPut(path, newValue.getValue(true), new Firebase.CompletionListener() {
            public void onComplete(FirebaseError error, Firebase ref) {
                Repo.this.warnIfWriteFailed("onDisconnect().setValue", path, error);
                if (error == null) {
                    Repo.this.onDisconnect.remember(path, newValue);
                }
                Repo.this.callOnComplete(onComplete, error, path);
            }
        });
    }

    public void onDisconnectUpdate(final Path path, final Map<Path, Node> newChildren, final Firebase.CompletionListener listener, Map<String, Object> unParsedUpdates) {
        this.connection.onDisconnectMerge(path, unParsedUpdates, new Firebase.CompletionListener() {
            public void onComplete(FirebaseError error, Firebase ref) {
                Repo.this.warnIfWriteFailed("onDisconnect().updateChildren", path, error);
                if (error == null) {
                    for (Map.Entry<Path, Node> entry : newChildren.entrySet()) {
                        Repo.this.onDisconnect.remember(path.child(entry.getKey()), entry.getValue());
                    }
                }
                Repo.this.callOnComplete(listener, error, path);
            }
        });
    }

    public void onDisconnectCancel(final Path path, final Firebase.CompletionListener onComplete) {
        this.connection.onDisconnectCancel(path, new Firebase.CompletionListener() {
            public void onComplete(FirebaseError error, Firebase ref) {
                if (error == null) {
                    Repo.this.onDisconnect.forget(path);
                }
                Repo.this.callOnComplete(onComplete, error, path);
            }
        });
    }

    public void onConnect() {
        onServerInfoUpdate(Constants.DOT_INFO_CONNECTED, true);
    }

    public void onDisconnect() {
        onServerInfoUpdate(Constants.DOT_INFO_CONNECTED, false);
        runOnDisconnectEvents();
    }

    public void onAuthStatus(boolean authOk) {
        onServerInfoUpdate(Constants.DOT_INFO_AUTHENTICATED, Boolean.valueOf(authOk));
    }

    public void onServerInfoUpdate(ChildKey key, Object value) {
        updateInfo(key, value);
    }

    public void onServerInfoUpdate(Map<ChildKey, Object> updates) {
        for (Map.Entry<ChildKey, Object> entry : updates.entrySet()) {
            updateInfo(entry.getKey(), entry.getValue());
        }
    }

    /* access modifiers changed from: package-private */
    public void interrupt() {
        this.connection.interrupt();
    }

    /* access modifiers changed from: package-private */
    public void resume() {
        this.connection.resume();
    }

    public void addEventCallback(EventRegistration eventRegistration) {
        List<? extends Event> events;
        ChildKey front = eventRegistration.getQuerySpec().getPath().getFront();
        if (front == null || !front.equals(Constants.DOT_INFO)) {
            events = this.serverSyncTree.addEventRegistration(eventRegistration);
        } else {
            events = this.infoSyncTree.addEventRegistration(eventRegistration);
        }
        postEvents(events);
    }

    public void keepSynced(QuerySpec query, boolean keep) {
        if (query.getPath().isEmpty() || !query.getPath().getFront().equals(Constants.DOT_INFO)) {
            this.serverSyncTree.keepSynced(query, keep);
            return;
        }
        throw new AssertionError();
    }

    /* access modifiers changed from: package-private */
    public PersistentConnection getConnection() {
        return this.connection;
    }

    private void updateInfo(ChildKey childKey, Object value) {
        if (childKey.equals(Constants.DOT_INFO_SERVERTIME_OFFSET)) {
            this.serverClock.setOffset(((Long) value).longValue());
        }
        Path path = new Path(Constants.DOT_INFO, childKey);
        try {
            Node node = NodeUtilities.NodeFromJSON(value);
            this.infoData.update(path, node);
            postEvents(this.infoSyncTree.applyServerOverwrite(path, node));
        } catch (FirebaseException e) {
            this.operationLogger.error("Failed to parse info update", e);
        }
    }

    private long getNextWriteId() {
        long j = this.nextWriteId;
        this.nextWriteId = 1 + j;
        return j;
    }

    private void runOnDisconnectEvents() {
        SparseSnapshotTree resolvedTree = ServerValues.resolveDeferredValueTree(this.onDisconnect, ServerValues.generateServerValues(this.serverClock));
        final List<Event> events = new ArrayList<>();
        resolvedTree.forEachTree(Path.getEmptyPath(), new SparseSnapshotTree.SparseSnapshotTreeVisitor() {
            public void visitTree(Path prefixPath, Node node) {
                events.addAll(Repo.this.serverSyncTree.applyServerOverwrite(prefixPath, node));
                Path unused = Repo.this.rerunTransactions(Repo.this.abortTransactions(prefixPath, -9));
            }
        });
        this.onDisconnect = new SparseSnapshotTree();
        postEvents(events);
    }

    /* access modifiers changed from: private */
    public void warnIfWriteFailed(String writeType, Path path, FirebaseError error) {
        if (error != null && error.getCode() != -1 && error.getCode() != -25) {
            this.operationLogger.warn(writeType + " at " + path.toString() + " failed: " + error.toString());
        }
    }

    private static class TransactionData implements Comparable<TransactionData> {
        /* access modifiers changed from: private */
        public FirebaseError abortReason;
        /* access modifiers changed from: private */
        public boolean applyLocally;
        /* access modifiers changed from: private */
        public Node currentInputSnapshot;
        /* access modifiers changed from: private */
        public Node currentOutputSnapshotRaw;
        /* access modifiers changed from: private */
        public Node currentOutputSnapshotResolved;
        /* access modifiers changed from: private */
        public long currentWriteId;
        /* access modifiers changed from: private */
        public Transaction.Handler handler;
        private long order;
        /* access modifiers changed from: private */
        public ValueEventListener outstandingListener;
        /* access modifiers changed from: private */
        public Path path;
        /* access modifiers changed from: private */
        public int retryCount;
        /* access modifiers changed from: private */
        public TransactionStatus status;

        static /* synthetic */ int access$1808(TransactionData x0) {
            int i = x0.retryCount;
            x0.retryCount = i + 1;
            return i;
        }

        private TransactionData(Path path2, Transaction.Handler handler2, ValueEventListener outstandingListener2, TransactionStatus status2, boolean applyLocally2, long order2) {
            this.path = path2;
            this.handler = handler2;
            this.outstandingListener = outstandingListener2;
            this.status = status2;
            this.retryCount = 0;
            this.applyLocally = applyLocally2;
            this.order = order2;
            this.abortReason = null;
            this.currentInputSnapshot = null;
            this.currentOutputSnapshotRaw = null;
            this.currentOutputSnapshotResolved = null;
        }

        public int compareTo(TransactionData o) {
            long j = this.order;
            long j2 = o.order;
            if (j < j2) {
                return -1;
            }
            if (j == j2) {
                return 0;
            }
            return 1;
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:26:0x00bf  */
    /* JADX WARNING: Removed duplicated region for block: B:27:0x00de  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void startTransaction(com.firebase.client.core.Path r25, com.firebase.client.Transaction.Handler r26, boolean r27) {
        /*
            r24 = this;
            r1 = r24
            r11 = r25
            r12 = r26
            com.firebase.client.utilities.LogWrapper r0 = r1.operationLogger
            boolean r0 = r0.logsDebug()
            java.lang.String r2 = "transaction: "
            if (r0 == 0) goto L_0x0027
            com.firebase.client.utilities.LogWrapper r0 = r1.operationLogger
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            java.lang.StringBuilder r3 = r3.append(r2)
            java.lang.StringBuilder r3 = r3.append(r11)
            java.lang.String r3 = r3.toString()
            r0.debug(r3)
        L_0x0027:
            com.firebase.client.utilities.LogWrapper r0 = r1.dataLogger
            boolean r0 = r0.logsDebug()
            if (r0 == 0) goto L_0x0045
            com.firebase.client.utilities.LogWrapper r0 = r1.operationLogger
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            java.lang.StringBuilder r2 = r3.append(r2)
            java.lang.StringBuilder r2 = r2.append(r11)
            java.lang.String r2 = r2.toString()
            r0.debug(r2)
        L_0x0045:
            com.firebase.client.core.Context r0 = r1.ctx
            boolean r0 = r0.isPersistenceEnabled()
            if (r0 == 0) goto L_0x005b
            boolean r0 = r1.loggedTransactionPersistenceWarning
            if (r0 != 0) goto L_0x005b
            r0 = 1
            r1.loggedTransactionPersistenceWarning = r0
            com.firebase.client.utilities.LogWrapper r0 = r1.transactionLogger
            java.lang.String r2 = "runTransaction() usage detected while persistence is enabled. Please be aware that transactions *will not* be persisted across app restarts.  See https://www.firebase.com/docs/android/guide/offline-capabilities.html#section-handling-transactions-offline for more details."
            r0.info(r2)
        L_0x005b:
            com.firebase.client.Firebase r0 = new com.firebase.client.Firebase
            r0.<init>((com.firebase.client.core.Repo) r1, (com.firebase.client.core.Path) r11)
            r13 = r0
            com.firebase.client.core.Repo$12 r0 = new com.firebase.client.core.Repo$12
            r0.<init>()
            r14 = r0
            com.firebase.client.core.ValueEventRegistration r0 = new com.firebase.client.core.ValueEventRegistration
            com.firebase.client.core.view.QuerySpec r2 = r13.getSpec()
            r0.<init>(r1, r14, r2)
            r1.addEventCallback(r0)
            com.firebase.client.core.Repo$TransactionData r0 = new com.firebase.client.core.Repo$TransactionData
            com.firebase.client.core.Repo$TransactionStatus r6 = com.firebase.client.core.Repo.TransactionStatus.INITIALIZING
            long r8 = r24.nextTransactionOrder()
            r10 = 0
            r2 = r0
            r3 = r25
            r4 = r26
            r5 = r14
            r7 = r27
            r2.<init>(r3, r4, r5, r6, r7, r8)
            r10 = r0
            com.firebase.client.snapshot.Node r15 = r24.getLatestState(r25)
            com.firebase.client.snapshot.Node unused = r10.currentInputSnapshot = r15
            com.firebase.client.MutableData r0 = new com.firebase.client.MutableData
            r0.<init>(r15)
            r9 = r0
            r2 = 0
            r3 = 0
            com.firebase.client.Transaction$Result r0 = r12.doTransaction(r9)     // Catch:{ all -> 0x00ac }
            r4 = r0
            if (r4 == 0) goto L_0x00a2
            r0 = r2
            r16 = r4
            goto L_0x00b9
        L_0x00a2:
            java.lang.NullPointerException r0 = new java.lang.NullPointerException     // Catch:{ all -> 0x00aa }
            java.lang.String r5 = "Transaction returned null as result"
            r0.<init>(r5)     // Catch:{ all -> 0x00aa }
            throw r0     // Catch:{ all -> 0x00aa }
        L_0x00aa:
            r0 = move-exception
            goto L_0x00ae
        L_0x00ac:
            r0 = move-exception
            r4 = r3
        L_0x00ae:
            com.firebase.client.FirebaseError r2 = com.firebase.client.FirebaseError.fromException(r0)
            com.firebase.client.Transaction$Result r4 = com.firebase.client.Transaction.abort()
            r0 = r2
            r16 = r4
        L_0x00b9:
            boolean r2 = r16.isSuccess()
            if (r2 != 0) goto L_0x00de
            com.firebase.client.snapshot.Node unused = r10.currentOutputSnapshotRaw = r3
            com.firebase.client.snapshot.Node unused = r10.currentOutputSnapshotResolved = r3
            r2 = r0
            com.firebase.client.DataSnapshot r3 = new com.firebase.client.DataSnapshot
            com.firebase.client.snapshot.Node r4 = r10.currentInputSnapshot
            com.firebase.client.snapshot.IndexedNode r4 = com.firebase.client.snapshot.IndexedNode.from(r4)
            r3.<init>(r13, r4)
            com.firebase.client.core.Repo$13 r4 = new com.firebase.client.core.Repo$13
            r4.<init>(r12, r2, r3)
            r1.postEvent(r4)
            r18 = r9
            goto L_0x0144
        L_0x00de:
            com.firebase.client.core.Repo$TransactionStatus r2 = com.firebase.client.core.Repo.TransactionStatus.RUN
            com.firebase.client.core.Repo.TransactionStatus unused = r10.status = r2
            com.firebase.client.core.utilities.Tree<java.util.List<com.firebase.client.core.Repo$TransactionData>> r2 = r1.transactionQueueTree
            com.firebase.client.core.utilities.Tree r8 = r2.subTree(r11)
            java.lang.Object r2 = r8.getValue()
            java.util.List r2 = (java.util.List) r2
            if (r2 != 0) goto L_0x00f9
            java.util.ArrayList r3 = new java.util.ArrayList
            r3.<init>()
            r2 = r3
            r6 = r2
            goto L_0x00fa
        L_0x00f9:
            r6 = r2
        L_0x00fa:
            r6.add(r10)
            r8.setValue(r6)
            com.firebase.client.utilities.OffsetClock r2 = r1.serverClock
            java.util.Map r7 = com.firebase.client.core.ServerValues.generateServerValues(r2)
            com.firebase.client.snapshot.Node r5 = r16.getNode()
            com.firebase.client.snapshot.Node r4 = com.firebase.client.core.ServerValues.resolveDeferredValueSnapshot(r5, r7)
            com.firebase.client.snapshot.Node unused = r10.currentOutputSnapshotRaw = r5
            com.firebase.client.snapshot.Node unused = r10.currentOutputSnapshotResolved = r4
            long r2 = r24.getNextWriteId()
            long unused = r10.currentWriteId = r2
            com.firebase.client.core.SyncTree r2 = r1.serverSyncTree
            long r17 = r10.currentWriteId
            r19 = 0
            r3 = r25
            r20 = r4
            r4 = r5
            r21 = r5
            r5 = r20
            r22 = r6
            r23 = r7
            r6 = r17
            r17 = r8
            r8 = r27
            r18 = r9
            r9 = r19
            java.util.List r2 = r2.applyUserOverwrite(r3, r4, r5, r6, r8, r9)
            r1.postEvents(r2)
            r24.sendAllReadyTransactions()
        L_0x0144:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.firebase.client.core.Repo.startTransaction(com.firebase.client.core.Path, com.firebase.client.Transaction$Handler, boolean):void");
    }

    private Node getLatestState(Path path) {
        return getLatestState(path, new ArrayList());
    }

    private Node getLatestState(Path path, List<Long> excudeSets) {
        Node state = this.serverSyncTree.calcCompleteEventCache(path, excudeSets);
        if (state == null) {
            return EmptyNode.Empty();
        }
        return state;
    }

    public void setHijackHash(boolean hijackHash2) {
        this.hijackHash = hijackHash2;
    }

    /* access modifiers changed from: private */
    public void sendAllReadyTransactions() {
        Tree<List<TransactionData>> node = this.transactionQueueTree;
        pruneCompletedTransactions(node);
        sendReadyTransactions(node);
    }

    /* access modifiers changed from: private */
    public void sendReadyTransactions(Tree<List<TransactionData>> node) {
        if (node.getValue() != null) {
            List<TransactionData> queue = buildTransactionQueue(node);
            if (queue.size() > 0) {
                Boolean allRun = true;
                Iterator i$ = queue.iterator();
                while (true) {
                    if (i$.hasNext()) {
                        if (i$.next().status != TransactionStatus.RUN) {
                            allRun = false;
                            break;
                        }
                    } else {
                        break;
                    }
                }
                if (allRun.booleanValue()) {
                    sendTransactionQueue(queue, node.getPath());
                    return;
                }
                return;
            }
            throw new AssertionError();
        } else if (node.hasChildren()) {
            node.forEachChild(new Tree.TreeVisitor<List<TransactionData>>() {
                public void visitTree(Tree<List<TransactionData>> tree) {
                    Repo.this.sendReadyTransactions(tree);
                }
            });
        }
    }

    private void sendTransactionQueue(final List<TransactionData> queue, final Path path) {
        List<Long> setsToIgnore = new ArrayList<>();
        for (TransactionData txn : queue) {
            setsToIgnore.add(Long.valueOf(txn.currentWriteId));
        }
        Node latestState = getLatestState(path, setsToIgnore);
        Node snapToSend = latestState;
        String latestHash = "badhash";
        if (!this.hijackHash) {
            latestHash = latestState.getHash();
        }
        for (TransactionData txn2 : queue) {
            if (txn2.status == TransactionStatus.RUN) {
                TransactionStatus unused = txn2.status = TransactionStatus.SENT;
                TransactionData.access$1808(txn2);
                snapToSend = snapToSend.updateChild(Path.getRelative(path, txn2.path), txn2.currentOutputSnapshotRaw);
            } else {
                throw new AssertionError();
            }
        }
        Object dataToSend = snapToSend.getValue(true);
        long nextWriteId2 = getNextWriteId();
        this.connection.put(path.toString(), dataToSend, latestHash, new Firebase.CompletionListener() {
            public void onComplete(FirebaseError error, Firebase ref) {
                Repo.this.warnIfWriteFailed("Transaction", path, error);
                List<Event> events = new ArrayList<>();
                if (error == null) {
                    List<Runnable> callbacks = new ArrayList<>();
                    for (final TransactionData txn : queue) {
                        TransactionStatus unused = txn.status = TransactionStatus.COMPLETED;
                        events.addAll(Repo.this.serverSyncTree.ackUserWrite(txn.currentWriteId, false, false, Repo.this.serverClock));
                        final DataSnapshot snap = new DataSnapshot(new Firebase(this, txn.path), IndexedNode.from(txn.currentOutputSnapshotResolved));
                        callbacks.add(new Runnable() {
                            public void run() {
                                txn.handler.onComplete((FirebaseError) null, true, snap);
                            }
                        });
                        Repo.this.removeEventCallback(new ValueEventRegistration(Repo.this, txn.outstandingListener, QuerySpec.defaultQueryAtPath(txn.path)));
                    }
                    Repo repo = Repo.this;
                    repo.pruneCompletedTransactions(repo.transactionQueueTree.subTree(path));
                    Repo.this.sendAllReadyTransactions();
                    this.postEvents(events);
                    for (int i = 0; i < callbacks.size(); i++) {
                        Repo.this.postEvent(callbacks.get(i));
                    }
                    return;
                }
                if (error.getCode() == -1) {
                    for (TransactionData transaction : queue) {
                        if (transaction.status == TransactionStatus.SENT_NEEDS_ABORT) {
                            TransactionStatus unused2 = transaction.status = TransactionStatus.NEEDS_ABORT;
                        } else {
                            TransactionStatus unused3 = transaction.status = TransactionStatus.RUN;
                        }
                    }
                } else {
                    for (TransactionData transaction2 : queue) {
                        TransactionStatus unused4 = transaction2.status = TransactionStatus.NEEDS_ABORT;
                        FirebaseError unused5 = transaction2.abortReason = error;
                    }
                }
                Path unused6 = Repo.this.rerunTransactions(path);
            }
        });
    }

    /* access modifiers changed from: private */
    public void pruneCompletedTransactions(Tree<List<TransactionData>> node) {
        List<TransactionData> queue = node.getValue();
        if (queue != null) {
            int i = 0;
            while (i < queue.size()) {
                if (queue.get(i).status == TransactionStatus.COMPLETED) {
                    queue.remove(i);
                } else {
                    i++;
                }
            }
            if (queue.size() > 0) {
                node.setValue(queue);
            } else {
                node.setValue(null);
            }
        }
        node.forEachChild(new Tree.TreeVisitor<List<TransactionData>>() {
            public void visitTree(Tree<List<TransactionData>> tree) {
                Repo.this.pruneCompletedTransactions(tree);
            }
        });
    }

    private long nextTransactionOrder() {
        long j = this.transactionOrder;
        this.transactionOrder = 1 + j;
        return j;
    }

    /* access modifiers changed from: private */
    public Path rerunTransactions(Path changedPath) {
        Tree<List<TransactionData>> rootMostTransactionNode = getAncestorTransactionNode(changedPath);
        Path path = rootMostTransactionNode.getPath();
        rerunTransactionQueue(buildTransactionQueue(rootMostTransactionNode), path);
        return path;
    }

    private void rerunTransactionQueue(List<TransactionData> queue, Path path) {
        Iterator i$;
        Transaction.Result result;
        if (!queue.isEmpty()) {
            List<Runnable> callbacks = new ArrayList<>();
            List<Long> setsToIgnore = new ArrayList<>();
            for (TransactionData transaction : queue) {
                setsToIgnore.add(Long.valueOf(transaction.currentWriteId));
            }
            Iterator it = queue.iterator();
            while (it.hasNext()) {
                final TransactionData transaction2 = it.next();
                Path relativePath = Path.getRelative(path, transaction2.path);
                boolean abortTransaction = false;
                FirebaseError abortReason = null;
                List<Event> events = new ArrayList<>();
                if (relativePath != null) {
                    if (transaction2.status == TransactionStatus.NEEDS_ABORT) {
                        abortTransaction = true;
                        abortReason = transaction2.abortReason;
                        if (abortReason.getCode() != -25) {
                            events.addAll(this.serverSyncTree.ackUserWrite(transaction2.currentWriteId, true, false, this.serverClock));
                        }
                        i$ = it;
                        Path path2 = relativePath;
                    } else if (transaction2.status != TransactionStatus.RUN) {
                        i$ = it;
                        Path path3 = relativePath;
                    } else if (transaction2.retryCount >= 25) {
                        abortTransaction = true;
                        abortReason = FirebaseError.fromStatus(TRANSACTION_TOO_MANY_RETRIES);
                        events.addAll(this.serverSyncTree.ackUserWrite(transaction2.currentWriteId, true, false, this.serverClock));
                        i$ = it;
                        Path path4 = relativePath;
                    } else {
                        Node currentNode = getLatestState(transaction2.path, setsToIgnore);
                        Node unused = transaction2.currentInputSnapshot = currentNode;
                        FirebaseError error = null;
                        try {
                            result = transaction2.handler.doTransaction(new MutableData(currentNode));
                        } catch (Throwable e) {
                            error = FirebaseError.fromException(e);
                            result = Transaction.abort();
                        }
                        if (result.isSuccess()) {
                            Long oldWriteId = Long.valueOf(transaction2.currentWriteId);
                            Map<String, Object> serverValues = ServerValues.generateServerValues(this.serverClock);
                            i$ = it;
                            Node newDataNode = result.getNode();
                            Transaction.Result result2 = result;
                            Node newNodeResolved = ServerValues.resolveDeferredValueSnapshot(newDataNode, serverValues);
                            Node unused2 = transaction2.currentOutputSnapshotRaw = newDataNode;
                            Node unused3 = transaction2.currentOutputSnapshotResolved = newNodeResolved;
                            Path path5 = relativePath;
                            long unused4 = transaction2.currentWriteId = getNextWriteId();
                            setsToIgnore.remove(oldWriteId);
                            events.addAll(this.serverSyncTree.applyUserOverwrite(transaction2.path, newDataNode, newNodeResolved, transaction2.currentWriteId, transaction2.applyLocally, false));
                            events.addAll(this.serverSyncTree.ackUserWrite(oldWriteId.longValue(), true, false, this.serverClock));
                            Transaction.Result result3 = result2;
                        } else {
                            i$ = it;
                            Transaction.Result result4 = result;
                            Path path6 = relativePath;
                            abortTransaction = true;
                            abortReason = error;
                            events.addAll(this.serverSyncTree.ackUserWrite(transaction2.currentWriteId, true, false, this.serverClock));
                        }
                    }
                    postEvents(events);
                    if (abortTransaction) {
                        TransactionStatus unused5 = transaction2.status = TransactionStatus.COMPLETED;
                        final DataSnapshot snapshot = new DataSnapshot(new Firebase(this, transaction2.path), IndexedNode.from(transaction2.currentInputSnapshot));
                        scheduleNow(new Runnable() {
                            public void run() {
                                Repo.this.removeEventCallback(new ValueEventRegistration(Repo.this, transaction2.outstandingListener, QuerySpec.defaultQueryAtPath(transaction2.path)));
                            }
                        });
                        final FirebaseError callbackError = abortReason;
                        callbacks.add(new Runnable() {
                            public void run() {
                                transaction2.handler.onComplete(callbackError, false, snapshot);
                            }
                        });
                        FirebaseError firebaseError = callbackError;
                    }
                    it = i$;
                } else {
                    throw new AssertionError();
                }
            }
            Iterator it2 = it;
            pruneCompletedTransactions(this.transactionQueueTree);
            for (int i = 0; i < callbacks.size(); i++) {
                postEvent(callbacks.get(i));
            }
            sendAllReadyTransactions();
        }
    }

    private Tree<List<TransactionData>> getAncestorTransactionNode(Path path) {
        Tree<List<TransactionData>> transactionNode = this.transactionQueueTree;
        while (!path.isEmpty() && transactionNode.getValue() == null) {
            transactionNode = transactionNode.subTree(new Path(path.getFront()));
            path = path.popFront();
        }
        return transactionNode;
    }

    private List<TransactionData> buildTransactionQueue(Tree<List<TransactionData>> transactionNode) {
        List<TransactionData> queue = new ArrayList<>();
        aggregateTransactionQueues(queue, transactionNode);
        Collections.sort(queue);
        return queue;
    }

    /* access modifiers changed from: private */
    public void aggregateTransactionQueues(final List<TransactionData> queue, Tree<List<TransactionData>> node) {
        List<TransactionData> childQueue = node.getValue();
        if (childQueue != null) {
            queue.addAll(childQueue);
        }
        node.forEachChild(new Tree.TreeVisitor<List<TransactionData>>() {
            public void visitTree(Tree<List<TransactionData>> tree) {
                Repo.this.aggregateTransactionQueues(queue, tree);
            }
        });
    }

    /* access modifiers changed from: private */
    public Path abortTransactions(Path path, final int reason) {
        Path affectedPath = getAncestorTransactionNode(path).getPath();
        if (this.transactionLogger.logsDebug()) {
            this.operationLogger.debug("Aborting transactions for path: " + path + ". Affected: " + affectedPath);
        }
        Tree<List<TransactionData>> transactionNode = this.transactionQueueTree.subTree(path);
        transactionNode.forEachAncestor(new Tree.TreeFilter<List<TransactionData>>() {
            public boolean filterTreeNode(Tree<List<TransactionData>> tree) {
                Repo.this.abortTransactionsAtNode(tree, reason);
                return false;
            }
        });
        abortTransactionsAtNode(transactionNode, reason);
        transactionNode.forEachDescendant(new Tree.TreeVisitor<List<TransactionData>>() {
            public void visitTree(Tree<List<TransactionData>> tree) {
                Repo.this.abortTransactionsAtNode(tree, reason);
            }
        });
        return affectedPath;
    }

    /* access modifiers changed from: private */
    public void abortTransactionsAtNode(Tree<List<TransactionData>> node, int reason) {
        final FirebaseError abortError;
        Tree<List<TransactionData>> tree = node;
        int i = reason;
        List<TransactionData> queue = node.getValue();
        List<Event> events = new ArrayList<>();
        if (queue != null) {
            List<Runnable> callbacks = new ArrayList<>();
            if (i == -9) {
                abortError = FirebaseError.fromStatus(TRANSACTION_OVERRIDE_BY_SET);
            } else {
                Utilities.hardAssert(i == -25, "Unknown transaction abort reason: " + i);
                abortError = FirebaseError.fromCode(-25);
            }
            int lastSent = -1;
            for (int i2 = 0; i2 < queue.size(); i2++) {
                final TransactionData transaction = queue.get(i2);
                if (transaction.status != TransactionStatus.SENT_NEEDS_ABORT) {
                    if (transaction.status == TransactionStatus.SENT) {
                        if (lastSent == i2 - 1) {
                            TransactionStatus unused = transaction.status = TransactionStatus.SENT_NEEDS_ABORT;
                            FirebaseError unused2 = transaction.abortReason = abortError;
                            lastSent = i2;
                        } else {
                            throw new AssertionError();
                        }
                    } else if (transaction.status == TransactionStatus.RUN) {
                        removeEventCallback(new ValueEventRegistration(this, transaction.outstandingListener, QuerySpec.defaultQueryAtPath(transaction.path)));
                        if (i == -9) {
                            events.addAll(this.serverSyncTree.ackUserWrite(transaction.currentWriteId, true, false, this.serverClock));
                        } else {
                            Utilities.hardAssert(i == -25, "Unknown transaction abort reason: " + i);
                        }
                        callbacks.add(new Runnable() {
                            public void run() {
                                transaction.handler.onComplete(abortError, false, (DataSnapshot) null);
                            }
                        });
                    } else {
                        throw new AssertionError();
                    }
                }
            }
            if (lastSent == -1) {
                tree.setValue(null);
            } else {
                tree.setValue(queue.subList(0, lastSent + 1));
            }
            postEvents(events);
            for (Runnable r : callbacks) {
                postEvent(r);
            }
        }
    }

    /* access modifiers changed from: package-private */
    public SyncTree getServerSyncTree() {
        return this.serverSyncTree;
    }

    /* access modifiers changed from: package-private */
    public SyncTree getInfoSyncTree() {
        return this.infoSyncTree;
    }
}
