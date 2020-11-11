package kotlinx.coroutines;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

@Metadata(mo33669bv = {1, 0, 3}, mo33670d1 = {"\u0000\u0018\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a\u0011\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u0007¢\u0006\u0002\b\u0003\u001a\u0011\u0010\u0000\u001a\u00020\u0004*\u00020\u0005H\u0007¢\u0006\u0002\b\u0003\u001a\n\u0010\u0006\u001a\u00020\u0002*\u00020\u0001¨\u0006\u0007"}, mo33671d2 = {"asCoroutineDispatcher", "Lkotlinx/coroutines/CoroutineDispatcher;", "Ljava/util/concurrent/Executor;", "from", "Lkotlinx/coroutines/ExecutorCoroutineDispatcher;", "Ljava/util/concurrent/ExecutorService;", "asExecutor", "kotlinx-coroutines-core"}, mo33672k = 2, mo33673mv = {1, 1, 15})
/* compiled from: Executors.kt */
public final class ExecutorsKt {
    public static final ExecutorCoroutineDispatcher from(ExecutorService $this$asCoroutineDispatcher) {
        Intrinsics.checkParameterIsNotNull($this$asCoroutineDispatcher, "$this$asCoroutineDispatcher");
        return new ExecutorCoroutineDispatcherImpl($this$asCoroutineDispatcher);
    }

    public static final CoroutineDispatcher from(Executor $this$asCoroutineDispatcher) {
        CoroutineDispatcher coroutineDispatcher;
        Intrinsics.checkParameterIsNotNull($this$asCoroutineDispatcher, "$this$asCoroutineDispatcher");
        DispatcherExecutor dispatcherExecutor = (DispatcherExecutor) (!($this$asCoroutineDispatcher instanceof DispatcherExecutor) ? null : $this$asCoroutineDispatcher);
        return (dispatcherExecutor == null || (coroutineDispatcher = dispatcherExecutor.dispatcher) == null) ? new ExecutorCoroutineDispatcherImpl($this$asCoroutineDispatcher) : coroutineDispatcher;
    }

    public static final Executor asExecutor(CoroutineDispatcher $this$asExecutor) {
        Executor executor;
        Intrinsics.checkParameterIsNotNull($this$asExecutor, "$this$asExecutor");
        ExecutorCoroutineDispatcher executorCoroutineDispatcher = (ExecutorCoroutineDispatcher) (!($this$asExecutor instanceof ExecutorCoroutineDispatcher) ? null : $this$asExecutor);
        return (executorCoroutineDispatcher == null || (executor = executorCoroutineDispatcher.getExecutor()) == null) ? new DispatcherExecutor($this$asExecutor) : executor;
    }
}
