package kotlinx.coroutines;

import java.util.concurrent.CancellationException;
import java.util.concurrent.Future;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.jvm.functions.Function0;

@Metadata(mo33669bv = {1, 0, 3}, mo33670d1 = {"kotlinx/coroutines/JobKt__FutureKt", "kotlinx/coroutines/JobKt__JobKt"}, mo33672k = 4, mo33673mv = {1, 1, 15})
public final class JobKt {
    public static final DisposableHandle DisposableHandle(Function0<Unit> block) {
        return JobKt__JobKt.DisposableHandle(block);
    }

    public static final CompletableJob Job(Job parent) {
        return JobKt__JobKt.Job(parent);
    }

    public static final void cancel(CoroutineContext $this$cancel, CancellationException cause) {
        JobKt__JobKt.cancel($this$cancel, cause);
    }

    public static final void cancel(Job $this$cancel, String message, Throwable cause) {
        JobKt__JobKt.cancel($this$cancel, message, cause);
    }

    public static final Object cancelAndJoin(Job $this$cancelAndJoin, Continuation<? super Unit> $completion) {
        return JobKt__JobKt.cancelAndJoin($this$cancelAndJoin, $completion);
    }

    public static final void cancelChildren(CoroutineContext $this$cancelChildren, CancellationException cause) {
        JobKt__JobKt.cancelChildren($this$cancelChildren, cause);
    }

    public static final void cancelChildren(Job $this$cancelChildren, CancellationException cause) {
        JobKt__JobKt.cancelChildren($this$cancelChildren, cause);
    }

    public static final void cancelFutureOnCancellation(CancellableContinuation<?> $this$cancelFutureOnCancellation, Future<?> future) {
        JobKt__FutureKt.cancelFutureOnCancellation($this$cancelFutureOnCancellation, future);
    }

    public static final DisposableHandle cancelFutureOnCompletion(Job $this$cancelFutureOnCompletion, Future<?> future) {
        return JobKt__FutureKt.cancelFutureOnCompletion($this$cancelFutureOnCompletion, future);
    }

    public static final DisposableHandle disposeOnCompletion(Job $this$disposeOnCompletion, DisposableHandle handle) {
        return JobKt__JobKt.disposeOnCompletion($this$disposeOnCompletion, handle);
    }

    public static final void ensureActive(CoroutineContext $this$ensureActive) {
        JobKt__JobKt.ensureActive($this$ensureActive);
    }

    public static final void ensureActive(Job $this$ensureActive) {
        JobKt__JobKt.ensureActive($this$ensureActive);
    }

    public static final boolean isActive(CoroutineContext $this$isActive) {
        return JobKt__JobKt.isActive($this$isActive);
    }
}