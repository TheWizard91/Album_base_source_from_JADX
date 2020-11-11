package androidx.core.p003os;

import kotlin.Metadata;
import kotlin.jvm.functions.Function0;

@Metadata(mo33669bv = {1, 0, 3}, mo33670d1 = {"\u0000\b\n\u0000\n\u0002\u0010\u0002\n\u0000\u0010\u0000\u001a\u00020\u0001H\n¢\u0006\u0002\b\u0002"}, mo33671d2 = {"<anonymous>", "", "run"}, mo33672k = 3, mo33673mv = {1, 1, 15})
/* renamed from: androidx.core.os.HandlerKt$postDelayed$runnable$1 */
/* compiled from: Handler.kt */
public final class HandlerKt$postDelayed$runnable$1 implements Runnable {
    final /* synthetic */ Function0 $action;

    public HandlerKt$postDelayed$runnable$1(Function0 function0) {
        this.$action = function0;
    }

    public final void run() {
        this.$action.invoke();
    }
}
