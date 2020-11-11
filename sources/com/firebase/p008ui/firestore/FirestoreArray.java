package com.firebase.p008ui.firestore;

import com.firebase.p008ui.common.ChangeEventType;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.MetadataChanges;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import java.util.ArrayList;
import java.util.List;

/* renamed from: com.firebase.ui.firestore.FirestoreArray */
public class FirestoreArray<T> extends ObservableSnapshotArray<T> implements EventListener<QuerySnapshot> {
    private final MetadataChanges mMetadataChanges;
    private Query mQuery;
    private ListenerRegistration mRegistration;
    private final List<DocumentSnapshot> mSnapshots;

    public FirestoreArray(Query query, SnapshotParser<T> parser) {
        this(query, MetadataChanges.EXCLUDE, parser);
    }

    public FirestoreArray(Query query, MetadataChanges changes, SnapshotParser<T> parser) {
        super(parser);
        this.mSnapshots = new ArrayList();
        this.mQuery = query;
        this.mMetadataChanges = changes;
    }

    /* access modifiers changed from: protected */
    public List<DocumentSnapshot> getSnapshots() {
        return this.mSnapshots;
    }

    /* access modifiers changed from: protected */
    public void onCreate() {
        super.onCreate();
        this.mRegistration = this.mQuery.addSnapshotListener(this.mMetadataChanges, (EventListener<QuerySnapshot>) this);
    }

    /* access modifiers changed from: protected */
    public void onDestroy() {
        super.onDestroy();
        this.mRegistration.remove();
        this.mRegistration = null;
    }

    public void onEvent(QuerySnapshot snapshots, FirebaseFirestoreException e) {
        if (e != null) {
            notifyOnError(e);
            return;
        }
        for (DocumentChange change : snapshots.getDocumentChanges(this.mMetadataChanges)) {
            int i = C11341.$SwitchMap$com$google$firebase$firestore$DocumentChange$Type[change.getType().ordinal()];
            if (i == 1) {
                onDocumentAdded(change);
            } else if (i == 2) {
                onDocumentRemoved(change);
            } else if (i == 3) {
                onDocumentModified(change);
            }
        }
        notifyOnDataChanged();
    }

    /* renamed from: com.firebase.ui.firestore.FirestoreArray$1 */
    static /* synthetic */ class C11341 {
        static final /* synthetic */ int[] $SwitchMap$com$google$firebase$firestore$DocumentChange$Type;

        static {
            int[] iArr = new int[DocumentChange.Type.values().length];
            $SwitchMap$com$google$firebase$firestore$DocumentChange$Type = iArr;
            try {
                iArr[DocumentChange.Type.ADDED.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$com$google$firebase$firestore$DocumentChange$Type[DocumentChange.Type.REMOVED.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$com$google$firebase$firestore$DocumentChange$Type[DocumentChange.Type.MODIFIED.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
        }
    }

    private void onDocumentAdded(DocumentChange change) {
        QueryDocumentSnapshot snapshot = change.getDocument();
        this.mSnapshots.add(change.getNewIndex(), snapshot);
        notifyOnChildChanged(ChangeEventType.ADDED, snapshot, change.getNewIndex(), -1);
    }

    private void onDocumentRemoved(DocumentChange change) {
        this.mSnapshots.remove(change.getOldIndex());
        notifyOnChildChanged(ChangeEventType.REMOVED, change.getDocument(), -1, change.getOldIndex());
    }

    private void onDocumentModified(DocumentChange change) {
        QueryDocumentSnapshot snapshot = change.getDocument();
        if (change.getOldIndex() == change.getNewIndex()) {
            this.mSnapshots.set(change.getNewIndex(), snapshot);
            notifyOnChildChanged(ChangeEventType.CHANGED, snapshot, change.getNewIndex(), change.getNewIndex());
            return;
        }
        this.mSnapshots.remove(change.getOldIndex());
        this.mSnapshots.add(change.getNewIndex(), snapshot);
        notifyOnChildChanged(ChangeEventType.MOVED, snapshot, change.getNewIndex(), change.getOldIndex());
        notifyOnChildChanged(ChangeEventType.CHANGED, snapshot, change.getNewIndex(), change.getNewIndex());
    }
}
