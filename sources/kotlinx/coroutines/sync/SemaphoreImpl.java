package kotlinx.coroutines.sync;

import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import java.util.concurrent.atomic.AtomicLongFieldUpdater;
import kotlin.Metadata;
import kotlin.Result;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugProbesKt;
import kotlinx.coroutines.CancellableContinuation;
import kotlinx.coroutines.CancellableContinuationImpl;
import kotlinx.coroutines.internal.SegmentQueue;

@Metadata(mo33669bv = {1, 0, 3}, mo33670d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\u0005\n\u0002\u0010\t\n\u0002\b\b\n\u0002\u0010\u000b\n\u0002\b\u0006\b\u0002\u0018\u00002\u00020\u00012\b\u0012\u0004\u0012\u00020\u00030\u0002B\u0017\u0012\u0006\u0010\u0005\u001a\u00020\u0004\u0012\u0006\u0010\u0006\u001a\u00020\u0004¢\u0006\u0004\b\u0007\u0010\bJ\u0013\u0010\n\u001a\u00020\tH@ø\u0001\u0000¢\u0006\u0004\b\n\u0010\u000bJ\u0013\u0010\f\u001a\u00020\tH@ø\u0001\u0000¢\u0006\u0004\b\f\u0010\u000bJ\r\u0010\r\u001a\u00020\u0004¢\u0006\u0004\b\r\u0010\u000eJ!\u0010\u0012\u001a\u00020\u00032\u0006\u0010\u0010\u001a\u00020\u000f2\b\u0010\u0011\u001a\u0004\u0018\u00010\u0003H\u0016¢\u0006\u0004\b\u0012\u0010\u0013J\u000f\u0010\u0014\u001a\u00020\tH\u0016¢\u0006\u0004\b\u0014\u0010\u0015J\u000f\u0010\u0017\u001a\u00020\tH\u0000¢\u0006\u0004\b\u0016\u0010\u0015J\u000f\u0010\u0019\u001a\u00020\u0018H\u0016¢\u0006\u0004\b\u0019\u0010\u001aR\u0016\u0010\u001c\u001a\u00020\u00048V@\u0016X\u0004¢\u0006\u0006\u001a\u0004\b\u001b\u0010\u000eR\u0016\u0010\u0005\u001a\u00020\u00048\u0002@\u0002X\u0004¢\u0006\u0006\n\u0004\b\u0005\u0010\u001d\u0002\u0004\n\u0002\b\u0019¨\u0006\u001e"}, mo33671d2 = {"Lkotlinx/coroutines/sync/SemaphoreImpl;", "Lkotlinx/coroutines/sync/Semaphore;", "Lkotlinx/coroutines/internal/SegmentQueue;", "Lkotlinx/coroutines/sync/SemaphoreSegment;", "", "permits", "acquiredPermits", "<init>", "(II)V", "", "acquire", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "addToQueueAndSuspend", "incPermits", "()I", "", "id", "prev", "newSegment", "(JLkotlinx/coroutines/sync/SemaphoreSegment;)Lkotlinx/coroutines/sync/SemaphoreSegment;", "release", "()V", "resumeNextFromQueue$kotlinx_coroutines_core", "resumeNextFromQueue", "", "tryAcquire", "()Z", "getAvailablePermits", "availablePermits", "I", "kotlinx-coroutines-core"}, mo33672k = 1, mo33673mv = {1, 1, 15})
/* compiled from: Semaphore.kt */
final class SemaphoreImpl extends SegmentQueue<SemaphoreSegment> implements Semaphore {
    private static final AtomicIntegerFieldUpdater _availablePermits$FU;
    private static final AtomicLongFieldUpdater deqIdx$FU;
    static final AtomicLongFieldUpdater enqIdx$FU;
    private volatile int _availablePermits;
    private volatile long deqIdx;
    volatile long enqIdx;
    private final int permits;

    static {
        Class<SemaphoreImpl> cls = SemaphoreImpl.class;
        _availablePermits$FU = AtomicIntegerFieldUpdater.newUpdater(cls, "_availablePermits");
        enqIdx$FU = AtomicLongFieldUpdater.newUpdater(cls, "enqIdx");
        deqIdx$FU = AtomicLongFieldUpdater.newUpdater(cls, "deqIdx");
    }

    public SemaphoreImpl(int permits2, int acquiredPermits) {
        this.permits = permits2;
        boolean z = true;
        if (permits2 > 0) {
            if ((acquiredPermits < 0 || permits2 < acquiredPermits) ? false : z) {
                this._availablePermits = permits2 - acquiredPermits;
                this.enqIdx = 0;
                this.deqIdx = 0;
                return;
            }
            throw new IllegalArgumentException(("The number of acquired permits should be in 0.." + permits2).toString());
        }
        throw new IllegalArgumentException(("Semaphore should have at least 1 permit, but had " + permits2).toString());
    }

    public static final /* synthetic */ SemaphoreSegment access$getSegment(SemaphoreImpl $this, SemaphoreSegment startFrom, long id) {
        return (SemaphoreSegment) $this.getSegment(startFrom, id);
    }

    public static final /* synthetic */ SemaphoreSegment access$getTail$p(SemaphoreImpl $this) {
        return (SemaphoreSegment) $this.getTail();
    }

    public SemaphoreSegment newSegment(long id, SemaphoreSegment prev) {
        return new SemaphoreSegment(id, prev);
    }

    public int getAvailablePermits() {
        return Math.max(this._availablePermits, 0);
    }

    public boolean tryAcquire() {
        int p;
        do {
            p = this._availablePermits;
            if (p <= 0) {
                return false;
            }
        } while (!_availablePermits$FU.compareAndSet(this, p, p - 1));
        return true;
    }

    public Object acquire(Continuation<? super Unit> $completion) {
        if (_availablePermits$FU.getAndDecrement(this) > 0) {
            return Unit.INSTANCE;
        }
        return addToQueueAndSuspend($completion);
    }

    public void release() {
        if (incPermits() < 0) {
            resumeNextFromQueue$kotlinx_coroutines_core();
        }
    }

    public final int incPermits() {
        int cur$iv;
        int cur;
        do {
            cur$iv = this._availablePermits;
            cur = cur$iv;
            if (!(cur < this.permits)) {
                throw new IllegalStateException(("The number of released permits cannot be greater than " + this.permits).toString());
            }
        } while (!_availablePermits$FU.compareAndSet(this, cur$iv, cur + 1));
        return cur$iv;
    }

    /* access modifiers changed from: package-private */
    public final /* synthetic */ Object addToQueueAndSuspend(Continuation<? super Unit> $completion) {
        CancellableContinuationImpl cancellable$iv = new CancellableContinuationImpl(IntrinsicsKt.intercepted($completion), 0);
        CancellableContinuation cont = cancellable$iv;
        SemaphoreSegment last = access$getTail$p(this);
        long enqIdx2 = enqIdx$FU.getAndIncrement(this);
        SemaphoreSegment segment = access$getSegment(this, last, enqIdx2 / ((long) SemaphoreKt.SEGMENT_SIZE));
        int i = (int) (enqIdx2 % ((long) SemaphoreKt.SEGMENT_SIZE));
        if (segment == null || segment.acquirers.get(i) == SemaphoreKt.RESUMED || !segment.acquirers.compareAndSet(i, (Object) null, cont)) {
            Unit unit = Unit.INSTANCE;
            Result.Companion companion = Result.Companion;
            cont.resumeWith(Result.m1289constructorimpl(unit));
        } else {
            cont.invokeOnCancellation(new CancelSemaphoreAcquisitionHandler(this, segment, i));
        }
        Object result = cancellable$iv.getResult();
        if (result == IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
            DebugProbesKt.probeCoroutineSuspended($completion);
        }
        return result;
    }

    public final void resumeNextFromQueue$kotlinx_coroutines_core() {
        while (true) {
            long deqIdx2 = deqIdx$FU.getAndIncrement(this);
            SemaphoreSegment segment = (SemaphoreSegment) getSegmentAndMoveHead((SemaphoreSegment) getHead(), deqIdx2 / ((long) SemaphoreKt.SEGMENT_SIZE));
            if (segment != null) {
                Object value$iv = SemaphoreKt.RESUMED;
                Object value$iv2 = segment.acquirers.getAndSet((int) (deqIdx2 % ((long) SemaphoreKt.SEGMENT_SIZE)), value$iv);
                if (value$iv2 != null) {
                    if (value$iv2 != SemaphoreKt.CANCELLED) {
                        Unit unit = Unit.INSTANCE;
                        Result.Companion companion = Result.Companion;
                        ((CancellableContinuation) value$iv2).resumeWith(Result.m1289constructorimpl(unit));
                        return;
                    }
                } else {
                    return;
                }
            }
        }
    }
}
