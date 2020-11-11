package kotlinx.coroutines;

import java.util.concurrent.ThreadFactory;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

@Metadata(mo33669bv = {1, 0, 3}, mo33670d1 = {"\u0000\u0010\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0010\u0000\u001a\u00020\u00012\u000e\u0010\u0002\u001a\n \u0004*\u0004\u0018\u00010\u00030\u0003H\n¢\u0006\u0002\b\u0005"}, mo33671d2 = {"<anonymous>", "Lkotlinx/coroutines/PoolThread;", "target", "Ljava/lang/Runnable;", "kotlin.jvm.PlatformType", "newThread"}, mo33672k = 3, mo33673mv = {1, 1, 15})
/* compiled from: ThreadPoolDispatcher.kt */
final class ThreadPoolDispatcher$executor$1 implements ThreadFactory {
    final /* synthetic */ ThreadPoolDispatcher this$0;

    ThreadPoolDispatcher$executor$1(ThreadPoolDispatcher threadPoolDispatcher) {
        this.this$0 = threadPoolDispatcher;
    }

    public final PoolThread newThread(Runnable target) {
        ThreadPoolDispatcher threadPoolDispatcher = this.this$0;
        Intrinsics.checkExpressionValueIsNotNull(target, "target");
        return new PoolThread(threadPoolDispatcher, target, this.this$0.nThreads == 1 ? this.this$0.name : this.this$0.name + "-" + this.this$0.threadNo.incrementAndGet());
    }
}
