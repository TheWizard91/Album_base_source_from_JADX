package androidx.core.transition;

import android.transition.Transition;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;

@Metadata(mo33669bv = {1, 0, 3}, mo33670d1 = {"\u0000\u000e\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0003H\n¢\u0006\u0002\b\u0004"}, mo33671d2 = {"<anonymous>", "", "it", "Landroid/transition/Transition;", "invoke"}, mo33672k = 3, mo33673mv = {1, 1, 15})
/* compiled from: Transition.kt */
public final class TransitionKt$addListener$3 extends Lambda implements Function1<Transition, Unit> {
    public static final TransitionKt$addListener$3 INSTANCE = new TransitionKt$addListener$3();

    public TransitionKt$addListener$3() {
        super(1);
    }

    public /* bridge */ /* synthetic */ Object invoke(Object obj) {
        invoke((Transition) obj);
        return Unit.INSTANCE;
    }

    public final void invoke(Transition it) {
        Intrinsics.checkParameterIsNotNull(it, "it");
    }
}
