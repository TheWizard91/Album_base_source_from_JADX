package kotlinx.coroutines;

import java.util.concurrent.locks.LockSupport;
import kotlin.Metadata;
import kotlin.coroutines.CoroutineContext;
import kotlin.jvm.internal.Intrinsics;

@Metadata(mo33669bv = {1, 0, 3}, mo33670d1 = {"\u0000<\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\b\u0002\u0018\u0000*\u0004\b\u0000\u0010\u00012\b\u0012\u0004\u0012\u0002H\u00010\u0002B\u001f\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\b\u0010\u0007\u001a\u0004\u0018\u00010\b¢\u0006\u0002\u0010\tJ\u001a\u0010\r\u001a\u00020\u000e2\b\u0010\u000f\u001a\u0004\u0018\u00010\u00102\u0006\u0010\u0011\u001a\u00020\u0012H\u0014J\u000b\u0010\u0013\u001a\u00028\u0000¢\u0006\u0002\u0010\u0014R\u000e\u0010\u0005\u001a\u00020\u0006X\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u0007\u001a\u0004\u0018\u00010\bX\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\n\u001a\u00020\u000b8TX\u0004¢\u0006\u0006\u001a\u0004\b\n\u0010\f¨\u0006\u0015"}, mo33671d2 = {"Lkotlinx/coroutines/BlockingCoroutine;", "T", "Lkotlinx/coroutines/AbstractCoroutine;", "parentContext", "Lkotlin/coroutines/CoroutineContext;", "blockedThread", "Ljava/lang/Thread;", "eventLoop", "Lkotlinx/coroutines/EventLoop;", "(Lkotlin/coroutines/CoroutineContext;Ljava/lang/Thread;Lkotlinx/coroutines/EventLoop;)V", "isScopedCoroutine", "", "()Z", "afterCompletionInternal", "", "state", "", "mode", "", "joinBlocking", "()Ljava/lang/Object;", "kotlinx-coroutines-core"}, mo33672k = 1, mo33673mv = {1, 1, 15})
/* compiled from: Builders.kt */
final class BlockingCoroutine<T> extends AbstractCoroutine<T> {
    private final Thread blockedThread;
    private final EventLoop eventLoop;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public BlockingCoroutine(CoroutineContext parentContext, Thread blockedThread2, EventLoop eventLoop2) {
        super(parentContext, true);
        Intrinsics.checkParameterIsNotNull(parentContext, "parentContext");
        Intrinsics.checkParameterIsNotNull(blockedThread2, "blockedThread");
        this.blockedThread = blockedThread2;
        this.eventLoop = eventLoop2;
    }

    /* access modifiers changed from: protected */
    public boolean isScopedCoroutine() {
        return true;
    }

    /* access modifiers changed from: protected */
    public void afterCompletionInternal(Object state, int mode) {
        if (!Intrinsics.areEqual((Object) Thread.currentThread(), (Object) this.blockedThread)) {
            LockSupport.unpark(this.blockedThread);
        }
    }

    public final T joinBlocking() {
        TimeSource timeSource = TimeSourceKt.getTimeSource();
        if (timeSource != null) {
            timeSource.registerTimeLoopThread();
        }
        try {
            EventLoop eventLoop2 = this.eventLoop;
            Object obj = null;
            if (eventLoop2 != null) {
                EventLoop.incrementUseCount$default(eventLoop2, false, 1, (Object) null);
            }
            while (!Thread.interrupted()) {
                EventLoop eventLoop3 = this.eventLoop;
                long parkNanos = eventLoop3 != null ? eventLoop3.processNextEvent() : Long.MAX_VALUE;
                if (isCompleted()) {
                    EventLoop eventLoop4 = this.eventLoop;
                    if (eventLoop4 != null) {
                        EventLoop.decrementUseCount$default(eventLoop4, false, 1, (Object) null);
                    }
                    TimeSource timeSource2 = TimeSourceKt.getTimeSource();
                    if (timeSource2 != null) {
                        timeSource2.unregisterTimeLoopThread();
                    }
                    Object state = JobSupportKt.unboxState(getState$kotlinx_coroutines_core());
                    if (state instanceof CompletedExceptionally) {
                        obj = state;
                    }
                    CompletedExceptionally it = (CompletedExceptionally) obj;
                    if (it == null) {
                        return state;
                    }
                    throw it.cause;
                }
                TimeSource timeSource3 = TimeSourceKt.getTimeSource();
                if (timeSource3 != null) {
                    timeSource3.parkNanos(this, parkNanos);
                } else {
                    LockSupport.parkNanos(this, parkNanos);
                }
            }
            InterruptedException it2 = new InterruptedException();
            cancelCoroutine(it2);
            throw it2;
        } catch (Throwable th) {
            TimeSource timeSource4 = TimeSourceKt.getTimeSource();
            if (timeSource4 != null) {
                timeSource4.unregisterTimeLoopThread();
            }
            throw th;
        }
    }
}
