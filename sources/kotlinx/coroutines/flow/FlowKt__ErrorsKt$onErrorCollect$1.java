package kotlinx.coroutines.flow;

import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;

@Metadata(mo33669bv = {1, 0, 3}, mo33670d1 = {"\u0000\u0010\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u0003\n\u0000\u0010\u0000\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u00022\u0006\u0010\u0003\u001a\u00020\u0004H\n¢\u0006\u0002\b\u0005"}, mo33671d2 = {"<anonymous>", "", "T", "it", "", "invoke"}, mo33672k = 3, mo33673mv = {1, 1, 15})
/* compiled from: Errors.kt */
final class FlowKt__ErrorsKt$onErrorCollect$1 extends Lambda implements Function1<Throwable, Boolean> {
    public static final FlowKt__ErrorsKt$onErrorCollect$1 INSTANCE = new FlowKt__ErrorsKt$onErrorCollect$1();

    FlowKt__ErrorsKt$onErrorCollect$1() {
        super(1);
    }

    public /* bridge */ /* synthetic */ Object invoke(Object obj) {
        return Boolean.valueOf(invoke((Throwable) obj));
    }

    public final boolean invoke(Throwable it) {
        Intrinsics.checkParameterIsNotNull(it, "it");
        return true;
    }
}
