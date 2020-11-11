package kotlinx.coroutines.sync;

import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import kotlin.Metadata;
import kotlin.Result;
import kotlin.TypeCastException;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.ContinuationKt;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugProbesKt;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.CancellableContinuation;
import kotlinx.coroutines.CancellableContinuationImpl;
import kotlinx.coroutines.CancellableContinuationKt;
import kotlinx.coroutines.DebugKt;
import kotlinx.coroutines.DisposableHandle;
import kotlinx.coroutines.internal.AtomicDesc;
import kotlinx.coroutines.internal.AtomicOp;
import kotlinx.coroutines.internal.LockFreeLinkedListHead;
import kotlinx.coroutines.internal.LockFreeLinkedListNode;
import kotlinx.coroutines.internal.OpDescriptor;
import kotlinx.coroutines.intrinsics.UndispatchedKt;
import kotlinx.coroutines.selects.SelectClause2;
import kotlinx.coroutines.selects.SelectInstance;
import kotlinx.coroutines.selects.SelectKt;

@Metadata(mo33669bv = {1, 0, 3}, mo33670d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0010\u000b\n\u0002\b\u0006\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0013\b\u0000\u0018\u00002\u00020\u00012\u0010\u0012\u0006\u0012\u0004\u0018\u00010\u0003\u0012\u0004\u0012\u00020\u00010\u0002:\u0006$%&'()B\u000f\u0012\u0006\u0010\u0005\u001a\u00020\u0004¢\u0006\u0004\b\u0006\u0010\u0007J\u0017\u0010\t\u001a\u00020\u00042\u0006\u0010\b\u001a\u00020\u0003H\u0016¢\u0006\u0004\b\t\u0010\nJ\u001d\u0010\f\u001a\u00020\u000b2\b\u0010\b\u001a\u0004\u0018\u00010\u0003H@ø\u0001\u0000¢\u0006\u0004\b\f\u0010\rJ\u001d\u0010\u000e\u001a\u00020\u000b2\b\u0010\b\u001a\u0004\u0018\u00010\u0003H@ø\u0001\u0000¢\u0006\u0004\b\u000e\u0010\rJT\u0010\u0015\u001a\u00020\u000b\"\u0004\b\u0000\u0010\u000f2\f\u0010\u0011\u001a\b\u0012\u0004\u0012\u00028\u00000\u00102\b\u0010\b\u001a\u0004\u0018\u00010\u00032\"\u0010\u0014\u001a\u001e\b\u0001\u0012\u0004\u0012\u00020\u0001\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00000\u0013\u0012\u0006\u0012\u0004\u0018\u00010\u00030\u0012H\u0016ø\u0001\u0000¢\u0006\u0004\b\u0015\u0010\u0016J\u000f\u0010\u0018\u001a\u00020\u0017H\u0016¢\u0006\u0004\b\u0018\u0010\u0019J\u0019\u0010\u001a\u001a\u00020\u00042\b\u0010\b\u001a\u0004\u0018\u00010\u0003H\u0016¢\u0006\u0004\b\u001a\u0010\nJ\u0019\u0010\u001b\u001a\u00020\u000b2\b\u0010\b\u001a\u0004\u0018\u00010\u0003H\u0016¢\u0006\u0004\b\u001b\u0010\u001cR\u0016\u0010\u001d\u001a\u00020\u00048V@\u0016X\u0004¢\u0006\u0006\u001a\u0004\b\u001d\u0010\u001eR\u0016\u0010 \u001a\u00020\u00048@@\u0000X\u0004¢\u0006\u0006\u001a\u0004\b\u001f\u0010\u001eR$\u0010#\u001a\u0010\u0012\u0006\u0012\u0004\u0018\u00010\u0003\u0012\u0004\u0012\u00020\u00010\u00028V@\u0016X\u0004¢\u0006\u0006\u001a\u0004\b!\u0010\"\u0002\u0004\n\u0002\b\u0019¨\u0006*"}, mo33671d2 = {"Lkotlinx/coroutines/sync/MutexImpl;", "Lkotlinx/coroutines/sync/Mutex;", "Lkotlinx/coroutines/selects/SelectClause2;", "", "", "locked", "<init>", "(Z)V", "owner", "holdsLock", "(Ljava/lang/Object;)Z", "", "lock", "(Ljava/lang/Object;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "lockSuspend", "R", "Lkotlinx/coroutines/selects/SelectInstance;", "select", "Lkotlin/Function2;", "Lkotlin/coroutines/Continuation;", "block", "registerSelectClause2", "(Lkotlinx/coroutines/selects/SelectInstance;Ljava/lang/Object;Lkotlin/jvm/functions/Function2;)V", "", "toString", "()Ljava/lang/String;", "tryLock", "unlock", "(Ljava/lang/Object;)V", "isLocked", "()Z", "isLockedEmptyQueueState$kotlinx_coroutines_core", "isLockedEmptyQueueState", "getOnLock", "()Lkotlinx/coroutines/selects/SelectClause2;", "onLock", "LockCont", "LockSelect", "LockWaiter", "LockedQueue", "TryLockDesc", "UnlockOp", "kotlinx-coroutines-core"}, mo33672k = 1, mo33673mv = {1, 1, 15})
/* compiled from: Mutex.kt */
public final class MutexImpl implements Mutex, SelectClause2<Object, Mutex> {
    static final AtomicReferenceFieldUpdater _state$FU = AtomicReferenceFieldUpdater.newUpdater(MutexImpl.class, Object.class, "_state");
    volatile Object _state;

    public MutexImpl(boolean locked) {
        this._state = locked ? MutexKt.EMPTY_LOCKED : MutexKt.EMPTY_UNLOCKED;
    }

    public boolean isLocked() {
        while (true) {
            Object state = this._state;
            if (state instanceof Empty) {
                return ((Empty) state).locked != MutexKt.UNLOCKED;
            }
            if (state instanceof LockedQueue) {
                return true;
            }
            if (state instanceof OpDescriptor) {
                ((OpDescriptor) state).perform(this);
            } else {
                throw new IllegalStateException(("Illegal state " + state).toString());
            }
        }
    }

    public final boolean isLockedEmptyQueueState$kotlinx_coroutines_core() {
        Object state = this._state;
        return (state instanceof LockedQueue) && ((LockedQueue) state).isEmpty();
    }

    public boolean tryLock(Object owner) {
        while (true) {
            Object state = this._state;
            boolean z = true;
            if (state instanceof Empty) {
                if (((Empty) state).locked != MutexKt.UNLOCKED) {
                    return false;
                }
                if (_state$FU.compareAndSet(this, state, owner == null ? MutexKt.EMPTY_LOCKED : new Empty(owner))) {
                    return true;
                }
            } else if (state instanceof LockedQueue) {
                if (((LockedQueue) state).owner == owner) {
                    z = false;
                }
                if (z) {
                    return false;
                }
                throw new IllegalStateException(("Already locked by " + owner).toString());
            } else if (state instanceof OpDescriptor) {
                ((OpDescriptor) state).perform(this);
            } else {
                throw new IllegalStateException(("Illegal state " + state).toString());
            }
        }
    }

    public Object lock(Object owner, Continuation<? super Unit> $completion) {
        if (tryLock(owner)) {
            return Unit.INSTANCE;
        }
        return lockSuspend(owner, $completion);
    }

    /* access modifiers changed from: package-private */
    public final /* synthetic */ Object lockSuspend(Object owner, Continuation<? super Unit> $completion) {
        MutexImpl $this$loop$iv;
        boolean z;
        Object obj = owner;
        boolean z2 = false;
        CancellableContinuationImpl cancellable$iv = new CancellableContinuationImpl(IntrinsicsKt.intercepted($completion), 0);
        CancellableContinuation cont = cancellable$iv;
        LockCont waiter = new LockCont(obj, cont);
        MutexImpl $this$loop$iv2 = this;
        while (true) {
            Object state = $this$loop$iv2._state;
            if (state instanceof Empty) {
                if (((Empty) state).locked != MutexKt.UNLOCKED) {
                    _state$FU.compareAndSet(this, state, new LockedQueue(((Empty) state).locked));
                    $this$loop$iv = $this$loop$iv2;
                } else {
                    if (_state$FU.compareAndSet(this, state, obj == null ? MutexKt.EMPTY_LOCKED : new Empty(obj))) {
                        Unit unit = Unit.INSTANCE;
                        Result.Companion companion = Result.Companion;
                        cont.resumeWith(Result.m1289constructorimpl(unit));
                        break;
                    }
                    $this$loop$iv = $this$loop$iv2;
                }
                $this$loop$iv2 = $this$loop$iv;
                z2 = false;
            } else {
                if (state instanceof LockedQueue) {
                    Object curOwner = ((LockedQueue) state).owner;
                    if (curOwner != obj ? true : z2) {
                        LockFreeLinkedListNode lockFreeLinkedListNode = (LockedQueue) state;
                        LockFreeLinkedListNode node$iv = waiter;
                        LockFreeLinkedListNode lockFreeLinkedListNode2 = lockFreeLinkedListNode;
                        LockFreeLinkedListNode node$iv2 = node$iv;
                        LockFreeLinkedListNode this_$iv = lockFreeLinkedListNode;
                        Object obj2 = curOwner;
                        Object obj3 = state;
                        $this$loop$iv = $this$loop$iv2;
                        LockFreeLinkedListNode.CondAddOp condAdd$iv = new C1927x426da4aa(node$iv, node$iv, state, cont, waiter, this, owner);
                        while (true) {
                            Object prev = this_$iv.getPrev();
                            if (prev != null) {
                                LockFreeLinkedListNode node$iv3 = node$iv2;
                                LockFreeLinkedListNode this_$iv2 = this_$iv;
                                int tryCondAddNext = ((LockFreeLinkedListNode) prev).tryCondAddNext(node$iv3, this_$iv2, condAdd$iv);
                                if (tryCondAddNext != 1) {
                                    if (tryCondAddNext == 2) {
                                        z = false;
                                        break;
                                    }
                                    this_$iv = this_$iv2;
                                    node$iv2 = node$iv3;
                                } else {
                                    z = true;
                                    break;
                                }
                            } else {
                                throw new TypeCastException("null cannot be cast to non-null type kotlinx.coroutines.internal.Node /* = kotlinx.coroutines.internal.LockFreeLinkedListNode */");
                            }
                        }
                        if (z) {
                            CancellableContinuationKt.removeOnCancellation(cont, waiter);
                            break;
                        }
                    } else {
                        throw new IllegalStateException(("Already locked by " + obj).toString());
                    }
                } else {
                    $this$loop$iv = $this$loop$iv2;
                    Object state2 = state;
                    if (state2 instanceof OpDescriptor) {
                        ((OpDescriptor) state2).perform(this);
                    } else {
                        throw new IllegalStateException(("Illegal state " + state2).toString());
                    }
                }
                $this$loop$iv2 = $this$loop$iv;
                z2 = false;
            }
        }
        Object result = cancellable$iv.getResult();
        if (result == IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
            DebugProbesKt.probeCoroutineSuspended($completion);
        }
        return result;
    }

    public SelectClause2<Object, Mutex> getOnLock() {
        return this;
    }

    public <R> void registerSelectClause2(SelectInstance<? super R> select, Object owner, Function2<? super Mutex, ? super Continuation<? super R>, ? extends Object> block) {
        Intrinsics.checkParameterIsNotNull(select, "select");
        Intrinsics.checkParameterIsNotNull(block, "block");
        while (!select.isSelected()) {
            Object state = this._state;
            if (state instanceof Empty) {
                if (((Empty) state).locked != MutexKt.UNLOCKED) {
                    _state$FU.compareAndSet(this, state, new LockedQueue(((Empty) state).locked));
                } else {
                    Object failure = select.performAtomicTrySelect(new TryLockDesc(this, owner));
                    if (failure == null) {
                        UndispatchedKt.startCoroutineUnintercepted(block, this, select.getCompletion());
                        return;
                    } else if (failure != SelectKt.getALREADY_SELECTED()) {
                        if (failure != MutexKt.LOCK_FAIL) {
                            throw new IllegalStateException(("performAtomicTrySelect(TryLockDesc) returned " + failure).toString());
                        }
                    } else {
                        return;
                    }
                }
            } else if (state instanceof LockedQueue) {
                boolean z = false;
                if (((LockedQueue) state).owner != owner) {
                    LockSelect node = new LockSelect(owner, this, select, block);
                    LockedQueue lockedQueue = (LockedQueue) state;
                    LockedQueue lockedQueue2 = lockedQueue;
                    LockFreeLinkedListNode.CondAddOp condAdd$iv = new MutexImpl$registerSelectClause2$$inlined$addLastIf$1(node, node, this, state);
                    while (true) {
                        Object prev = lockedQueue.getPrev();
                        if (prev != null) {
                            int tryCondAddNext = ((LockFreeLinkedListNode) prev).tryCondAddNext(node, lockedQueue, condAdd$iv);
                            if (tryCondAddNext != 1) {
                                if (tryCondAddNext == 2) {
                                    break;
                                }
                            } else {
                                z = true;
                                break;
                            }
                        } else {
                            throw new TypeCastException("null cannot be cast to non-null type kotlinx.coroutines.internal.Node /* = kotlinx.coroutines.internal.LockFreeLinkedListNode */");
                        }
                    }
                    if (z) {
                        select.disposeOnSelect(node);
                        return;
                    }
                } else {
                    throw new IllegalStateException(("Already locked by " + owner).toString());
                }
            } else if (state instanceof OpDescriptor) {
                ((OpDescriptor) state).perform(this);
            } else {
                throw new IllegalStateException(("Illegal state " + state).toString());
            }
        }
    }

    @Metadata(mo33669bv = {1, 0, 3}, mo33670d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\b\u0002\u0018\u00002\u00020\u0001:\u0001\rB\u0017\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005¢\u0006\u0002\u0010\u0006J\u001e\u0010\u0007\u001a\u00020\b2\n\u0010\t\u001a\u0006\u0012\u0002\b\u00030\n2\b\u0010\u000b\u001a\u0004\u0018\u00010\u0005H\u0016J\u0016\u0010\f\u001a\u0004\u0018\u00010\u00052\n\u0010\t\u001a\u0006\u0012\u0002\b\u00030\nH\u0016R\u0010\u0010\u0002\u001a\u00020\u00038\u0006X\u0004¢\u0006\u0002\n\u0000R\u0012\u0010\u0004\u001a\u0004\u0018\u00010\u00058\u0006X\u0004¢\u0006\u0002\n\u0000¨\u0006\u000e"}, mo33671d2 = {"Lkotlinx/coroutines/sync/MutexImpl$TryLockDesc;", "Lkotlinx/coroutines/internal/AtomicDesc;", "mutex", "Lkotlinx/coroutines/sync/MutexImpl;", "owner", "", "(Lkotlinx/coroutines/sync/MutexImpl;Ljava/lang/Object;)V", "complete", "", "op", "Lkotlinx/coroutines/internal/AtomicOp;", "failure", "prepare", "PrepareOp", "kotlinx-coroutines-core"}, mo33672k = 1, mo33673mv = {1, 1, 15})
    /* compiled from: Mutex.kt */
    private static final class TryLockDesc extends AtomicDesc {
        public final MutexImpl mutex;
        public final Object owner;

        public TryLockDesc(MutexImpl mutex2, Object owner2) {
            Intrinsics.checkParameterIsNotNull(mutex2, "mutex");
            this.mutex = mutex2;
            this.owner = owner2;
        }

        @Metadata(mo33669bv = {1, 0, 3}, mo33670d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\b\u0004\u0018\u00002\u00020\u0001B\u0011\u0012\n\u0010\u0002\u001a\u0006\u0012\u0002\b\u00030\u0003¢\u0006\u0002\u0010\u0004J\u0014\u0010\u0005\u001a\u0004\u0018\u00010\u00062\b\u0010\u0007\u001a\u0004\u0018\u00010\u0006H\u0016R\u0012\u0010\u0002\u001a\u0006\u0012\u0002\b\u00030\u0003X\u0004¢\u0006\u0002\n\u0000¨\u0006\b"}, mo33671d2 = {"Lkotlinx/coroutines/sync/MutexImpl$TryLockDesc$PrepareOp;", "Lkotlinx/coroutines/internal/OpDescriptor;", "op", "Lkotlinx/coroutines/internal/AtomicOp;", "(Lkotlinx/coroutines/sync/MutexImpl$TryLockDesc;Lkotlinx/coroutines/internal/AtomicOp;)V", "perform", "", "affected", "kotlinx-coroutines-core"}, mo33672k = 1, mo33673mv = {1, 1, 15})
        /* compiled from: Mutex.kt */
        private final class PrepareOp extends OpDescriptor {

            /* renamed from: op */
            private final AtomicOp<?> f710op;
            final /* synthetic */ TryLockDesc this$0;

            public PrepareOp(TryLockDesc $outer, AtomicOp<?> op) {
                Intrinsics.checkParameterIsNotNull(op, "op");
                this.this$0 = $outer;
                this.f710op = op;
            }

            public Object perform(Object affected) {
                Object update = this.f710op.isDecided() ? MutexKt.EMPTY_UNLOCKED : this.f710op;
                if (affected != null) {
                    MutexImpl._state$FU.compareAndSet((MutexImpl) affected, this, update);
                    return null;
                }
                throw new TypeCastException("null cannot be cast to non-null type kotlinx.coroutines.sync.MutexImpl");
            }
        }

        public Object prepare(AtomicOp<?> op) {
            Intrinsics.checkParameterIsNotNull(op, "op");
            PrepareOp prepare = new PrepareOp(this, op);
            if (!MutexImpl._state$FU.compareAndSet(this.mutex, MutexKt.EMPTY_UNLOCKED, prepare)) {
                return MutexKt.LOCK_FAIL;
            }
            return prepare.perform(this.mutex);
        }

        public void complete(AtomicOp<?> op, Object failure) {
            Empty update;
            Intrinsics.checkParameterIsNotNull(op, "op");
            if (failure != null) {
                update = MutexKt.EMPTY_UNLOCKED;
            } else {
                update = this.owner == null ? MutexKt.EMPTY_LOCKED : new Empty(this.owner);
            }
            MutexImpl._state$FU.compareAndSet(this.mutex, op, update);
        }
    }

    public boolean holdsLock(Object owner) {
        Intrinsics.checkParameterIsNotNull(owner, "owner");
        Object state = this._state;
        if (state instanceof Empty) {
            return ((Empty) state).locked == owner;
        }
        if (!(state instanceof LockedQueue)) {
            return false;
        }
        if (((LockedQueue) state).owner == owner) {
            return true;
        }
        return false;
    }

    public void unlock(Object owner) {
        while (true) {
            Object state = this._state;
            boolean z = true;
            if (state instanceof Empty) {
                if (owner == null) {
                    if (((Empty) state).locked == MutexKt.UNLOCKED) {
                        z = false;
                    }
                    if (!z) {
                        throw new IllegalStateException("Mutex is not locked".toString());
                    }
                } else {
                    if (((Empty) state).locked != owner) {
                        z = false;
                    }
                    if (!z) {
                        throw new IllegalStateException(("Mutex is locked by " + ((Empty) state).locked + " but expected " + owner).toString());
                    }
                }
                if (_state$FU.compareAndSet(this, state, MutexKt.EMPTY_UNLOCKED)) {
                    return;
                }
            } else if (state instanceof OpDescriptor) {
                ((OpDescriptor) state).perform(this);
            } else if (state instanceof LockedQueue) {
                if (owner != null) {
                    if (((LockedQueue) state).owner != owner) {
                        z = false;
                    }
                    if (!z) {
                        throw new IllegalStateException(("Mutex is locked by " + ((LockedQueue) state).owner + " but expected " + owner).toString());
                    }
                }
                LockFreeLinkedListNode waiter = ((LockedQueue) state).removeFirstOrNull();
                if (waiter == null) {
                    UnlockOp op = new UnlockOp((LockedQueue) state);
                    if (_state$FU.compareAndSet(this, state, op) && op.perform(this) == null) {
                        return;
                    }
                } else {
                    Object token = ((LockWaiter) waiter).tryResumeLockWaiter();
                    if (token != null) {
                        LockedQueue lockedQueue = (LockedQueue) state;
                        Object obj = ((LockWaiter) waiter).owner;
                        if (obj == null) {
                            obj = MutexKt.LOCKED;
                        }
                        lockedQueue.owner = obj;
                        ((LockWaiter) waiter).completeResumeLockWaiter(token);
                        return;
                    }
                }
            } else {
                throw new IllegalStateException(("Illegal state " + state).toString());
            }
        }
    }

    public String toString() {
        while (true) {
            Object state = this._state;
            if (state instanceof Empty) {
                return "Mutex[" + ((Empty) state).locked + ']';
            }
            if (state instanceof OpDescriptor) {
                ((OpDescriptor) state).perform(this);
            } else if (state instanceof LockedQueue) {
                return "Mutex[" + ((LockedQueue) state).owner + ']';
            } else {
                throw new IllegalStateException(("Illegal state " + state).toString());
            }
        }
    }

    @Metadata(mo33669bv = {1, 0, 3}, mo33670d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\b\u0002\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\b\u0010\u0005\u001a\u00020\u0006H\u0016R\u0012\u0010\u0002\u001a\u00020\u00038\u0006@\u0006X\u000e¢\u0006\u0002\n\u0000¨\u0006\u0007"}, mo33671d2 = {"Lkotlinx/coroutines/sync/MutexImpl$LockedQueue;", "Lkotlinx/coroutines/internal/LockFreeLinkedListHead;", "owner", "", "(Ljava/lang/Object;)V", "toString", "", "kotlinx-coroutines-core"}, mo33672k = 1, mo33673mv = {1, 1, 15})
    /* compiled from: Mutex.kt */
    private static final class LockedQueue extends LockFreeLinkedListHead {
        public Object owner;

        public LockedQueue(Object owner2) {
            Intrinsics.checkParameterIsNotNull(owner2, "owner");
            this.owner = owner2;
        }

        public String toString() {
            return "LockedQueue[" + this.owner + ']';
        }
    }

    @Metadata(mo33669bv = {1, 0, 3}, mo33670d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0004\b\"\u0018\u00002\u00020\u00012\u00020\u0002B\u000f\u0012\b\u0010\u0003\u001a\u0004\u0018\u00010\u0004¢\u0006\u0002\u0010\u0005J\u0010\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\u0004H&J\u0006\u0010\t\u001a\u00020\u0007J\n\u0010\n\u001a\u0004\u0018\u00010\u0004H&R\u0012\u0010\u0003\u001a\u0004\u0018\u00010\u00048\u0006X\u0004¢\u0006\u0002\n\u0000¨\u0006\u000b"}, mo33671d2 = {"Lkotlinx/coroutines/sync/MutexImpl$LockWaiter;", "Lkotlinx/coroutines/internal/LockFreeLinkedListNode;", "Lkotlinx/coroutines/DisposableHandle;", "owner", "", "(Ljava/lang/Object;)V", "completeResumeLockWaiter", "", "token", "dispose", "tryResumeLockWaiter", "kotlinx-coroutines-core"}, mo33672k = 1, mo33673mv = {1, 1, 15})
    /* compiled from: Mutex.kt */
    private static abstract class LockWaiter extends LockFreeLinkedListNode implements DisposableHandle {
        public final Object owner;

        public abstract void completeResumeLockWaiter(Object obj);

        public abstract Object tryResumeLockWaiter();

        public LockWaiter(Object owner2) {
            this.owner = owner2;
        }

        public final void dispose() {
            remove();
        }
    }

    @Metadata(mo33669bv = {1, 0, 3}, mo33670d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0002\b\u0002\b\u0002\u0018\u00002\u00020\u0001B\u001d\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005¢\u0006\u0002\u0010\u0007J\u0010\u0010\b\u001a\u00020\u00062\u0006\u0010\t\u001a\u00020\u0003H\u0016J\b\u0010\n\u001a\u00020\u000bH\u0016J\n\u0010\f\u001a\u0004\u0018\u00010\u0003H\u0016R\u0016\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u00058\u0006X\u0004¢\u0006\u0002\n\u0000¨\u0006\r"}, mo33671d2 = {"Lkotlinx/coroutines/sync/MutexImpl$LockCont;", "Lkotlinx/coroutines/sync/MutexImpl$LockWaiter;", "owner", "", "cont", "Lkotlinx/coroutines/CancellableContinuation;", "", "(Ljava/lang/Object;Lkotlinx/coroutines/CancellableContinuation;)V", "completeResumeLockWaiter", "token", "toString", "", "tryResumeLockWaiter", "kotlinx-coroutines-core"}, mo33672k = 1, mo33673mv = {1, 1, 15})
    /* compiled from: Mutex.kt */
    private static final class LockCont extends LockWaiter {
        public final CancellableContinuation<Unit> cont;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        public LockCont(Object owner, CancellableContinuation<? super Unit> cont2) {
            super(owner);
            Intrinsics.checkParameterIsNotNull(cont2, "cont");
            this.cont = cont2;
        }

        public Object tryResumeLockWaiter() {
            return CancellableContinuation.DefaultImpls.tryResume$default(this.cont, Unit.INSTANCE, (Object) null, 2, (Object) null);
        }

        public void completeResumeLockWaiter(Object token) {
            Intrinsics.checkParameterIsNotNull(token, "token");
            this.cont.completeResume(token);
        }

        public String toString() {
            return "LockCont[" + this.owner + ", " + this.cont + ']';
        }
    }

    @Metadata(mo33669bv = {1, 0, 3}, mo33670d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\b\u0002\u0018\u0000*\u0004\b\u0000\u0010\u00012\u00020\u0002BL\u0012\b\u0010\u0003\u001a\u0004\u0018\u00010\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00028\u00000\b\u0012\"\u0010\t\u001a\u001e\b\u0001\u0012\u0004\u0012\u00020\u0006\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00000\u000b\u0012\u0006\u0012\u0004\u0018\u00010\u00040\nø\u0001\u0000¢\u0006\u0002\u0010\fJ\u0010\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u0004H\u0016J\b\u0010\u0011\u001a\u00020\u0012H\u0016J\n\u0010\u0013\u001a\u0004\u0018\u00010\u0004H\u0016R1\u0010\t\u001a\u001e\b\u0001\u0012\u0004\u0012\u00020\u0006\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00000\u000b\u0012\u0006\u0012\u0004\u0018\u00010\u00040\n8\u0006X\u0004ø\u0001\u0000¢\u0006\u0004\n\u0002\u0010\rR\u0010\u0010\u0005\u001a\u00020\u00068\u0006X\u0004¢\u0006\u0002\n\u0000R\u0016\u0010\u0007\u001a\b\u0012\u0004\u0012\u00028\u00000\b8\u0006X\u0004¢\u0006\u0002\n\u0000\u0002\u0004\n\u0002\b\u0019¨\u0006\u0014"}, mo33671d2 = {"Lkotlinx/coroutines/sync/MutexImpl$LockSelect;", "R", "Lkotlinx/coroutines/sync/MutexImpl$LockWaiter;", "owner", "", "mutex", "Lkotlinx/coroutines/sync/Mutex;", "select", "Lkotlinx/coroutines/selects/SelectInstance;", "block", "Lkotlin/Function2;", "Lkotlin/coroutines/Continuation;", "(Ljava/lang/Object;Lkotlinx/coroutines/sync/Mutex;Lkotlinx/coroutines/selects/SelectInstance;Lkotlin/jvm/functions/Function2;)V", "Lkotlin/jvm/functions/Function2;", "completeResumeLockWaiter", "", "token", "toString", "", "tryResumeLockWaiter", "kotlinx-coroutines-core"}, mo33672k = 1, mo33673mv = {1, 1, 15})
    /* compiled from: Mutex.kt */
    private static final class LockSelect<R> extends LockWaiter {
        public final Function2<Mutex, Continuation<? super R>, Object> block;
        public final Mutex mutex;
        public final SelectInstance<R> select;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        public LockSelect(Object owner, Mutex mutex2, SelectInstance<? super R> select2, Function2<? super Mutex, ? super Continuation<? super R>, ? extends Object> block2) {
            super(owner);
            Intrinsics.checkParameterIsNotNull(mutex2, "mutex");
            Intrinsics.checkParameterIsNotNull(select2, "select");
            Intrinsics.checkParameterIsNotNull(block2, "block");
            this.mutex = mutex2;
            this.select = select2;
            this.block = block2;
        }

        public Object tryResumeLockWaiter() {
            if (this.select.trySelect((Object) null)) {
                return MutexKt.SELECT_SUCCESS;
            }
            return null;
        }

        public void completeResumeLockWaiter(Object token) {
            Intrinsics.checkParameterIsNotNull(token, "token");
            if (DebugKt.getASSERTIONS_ENABLED()) {
                if (!(token == MutexKt.SELECT_SUCCESS)) {
                    throw new AssertionError();
                }
            }
            ContinuationKt.startCoroutine(this.block, this.mutex, this.select.getCompletion());
        }

        public String toString() {
            return "LockSelect[" + this.owner + ", " + this.mutex + ", " + this.select + ']';
        }
    }

    @Metadata(mo33669bv = {1, 0, 3}, mo33670d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\b\u0002\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0014\u0010\u0005\u001a\u0004\u0018\u00010\u00062\b\u0010\u0007\u001a\u0004\u0018\u00010\u0006H\u0016R\u0010\u0010\u0002\u001a\u00020\u00038\u0006X\u0004¢\u0006\u0002\n\u0000\u0002\u0004\n\u0002\b\u0019¨\u0006\b"}, mo33671d2 = {"Lkotlinx/coroutines/sync/MutexImpl$UnlockOp;", "Lkotlinx/coroutines/internal/OpDescriptor;", "queue", "Lkotlinx/coroutines/sync/MutexImpl$LockedQueue;", "(Lkotlinx/coroutines/sync/MutexImpl$LockedQueue;)V", "perform", "", "affected", "kotlinx-coroutines-core"}, mo33672k = 1, mo33673mv = {1, 1, 15})
    /* compiled from: Mutex.kt */
    private static final class UnlockOp extends OpDescriptor {
        public final LockedQueue queue;

        public UnlockOp(LockedQueue queue2) {
            Intrinsics.checkParameterIsNotNull(queue2, "queue");
            this.queue = queue2;
        }

        public Object perform(Object affected) {
            Object update = this.queue.isEmpty() ? MutexKt.EMPTY_UNLOCKED : this.queue;
            if (affected != null) {
                MutexImpl._state$FU.compareAndSet((MutexImpl) affected, this, update);
                if (((MutexImpl) affected)._state == this.queue) {
                    return MutexKt.UNLOCK_FAIL;
                }
                return null;
            }
            throw new TypeCastException("null cannot be cast to non-null type kotlinx.coroutines.sync.MutexImpl");
        }
    }
}
