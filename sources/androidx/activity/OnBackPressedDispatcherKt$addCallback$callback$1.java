package androidx.activity;

import kotlin.Metadata;
import kotlin.jvm.functions.Function1;

@Metadata(mo33669bv = {1, 0, 3}, mo33670d1 = {"\u0000\u0011\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000*\u0001\u0000\b\n\u0018\u00002\u00020\u0001J\b\u0010\u0002\u001a\u00020\u0003H\u0016¨\u0006\u0004"}, mo33671d2 = {"androidx/activity/OnBackPressedDispatcherKt$addCallback$callback$1", "Landroidx/activity/OnBackPressedCallback;", "handleOnBackPressed", "", "activity-ktx_release"}, mo33672k = 1, mo33673mv = {1, 1, 15})
/* compiled from: OnBackPressedDispatcher.kt */
public final class OnBackPressedDispatcherKt$addCallback$callback$1 extends OnBackPressedCallback {
    final /* synthetic */ boolean $enabled;
    final /* synthetic */ Function1 $onBackPressed;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    OnBackPressedDispatcherKt$addCallback$callback$1(Function1 $captured_local_variable$0, boolean $captured_local_variable$1, boolean $super_call_param$2) {
        super($super_call_param$2);
        this.$onBackPressed = $captured_local_variable$0;
        this.$enabled = $captured_local_variable$1;
    }

    public void handleOnBackPressed() {
        this.$onBackPressed.invoke(this);
    }
}
