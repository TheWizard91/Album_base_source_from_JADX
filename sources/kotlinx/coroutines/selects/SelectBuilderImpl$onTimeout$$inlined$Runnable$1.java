package kotlinx.coroutines.selects;

import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlinx.coroutines.intrinsics.CancellableKt;

@Metadata(mo33669bv = {1, 0, 3}, mo33670d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\u0010\u0000\u001a\u00020\u0001H\n¢\u0006\u0002\b\u0002¨\u0006\u0003"}, mo33671d2 = {"<anonymous>", "", "run", "kotlinx/coroutines/RunnableKt$Runnable$1"}, mo33672k = 3, mo33673mv = {1, 1, 15})
/* compiled from: Runnable.kt */
public final class SelectBuilderImpl$onTimeout$$inlined$Runnable$1 implements Runnable {
    final /* synthetic */ Function1 $block$inlined;
    final /* synthetic */ SelectBuilderImpl this$0;

    public SelectBuilderImpl$onTimeout$$inlined$Runnable$1(SelectBuilderImpl selectBuilderImpl, Function1 function1) {
        this.this$0 = selectBuilderImpl;
        this.$block$inlined = function1;
    }

    public final void run() {
        if (this.this$0.trySelect((Object) null)) {
            CancellableKt.startCoroutineCancellable(this.$block$inlined, this.this$0.getCompletion());
        }
    }
}
