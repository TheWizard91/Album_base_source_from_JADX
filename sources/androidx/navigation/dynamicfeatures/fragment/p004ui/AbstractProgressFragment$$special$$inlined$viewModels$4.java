package androidx.navigation.dynamicfeatures.fragment.p004ui;

import androidx.lifecycle.ViewModelStore;
import androidx.lifecycle.ViewModelStoreOwner;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;

@Metadata(mo33669bv = {1, 0, 3}, mo33670d1 = {"\u0000\u0010\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0010\u0000\u001a\u00020\u0001\"\n\b\u0000\u0010\u0002\u0018\u0001*\u00020\u0003H\n¢\u0006\u0002\b\u0004¨\u0006\u0005"}, mo33671d2 = {"<anonymous>", "Landroidx/lifecycle/ViewModelStore;", "VM", "Landroidx/lifecycle/ViewModel;", "invoke", "androidx/fragment/app/FragmentViewModelLazyKt$viewModels$2"}, mo33672k = 3, mo33673mv = {1, 1, 16})
/* renamed from: androidx.navigation.dynamicfeatures.fragment.ui.AbstractProgressFragment$$special$$inlined$viewModels$4 */
/* compiled from: FragmentViewModelLazy.kt */
public final class AbstractProgressFragment$$special$$inlined$viewModels$4 extends Lambda implements Function0<ViewModelStore> {
    final /* synthetic */ Function0 $ownerProducer;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public AbstractProgressFragment$$special$$inlined$viewModels$4(Function0 function0) {
        super(0);
        this.$ownerProducer = function0;
    }

    public final ViewModelStore invoke() {
        ViewModelStore viewModelStore = ((ViewModelStoreOwner) this.$ownerProducer.invoke()).getViewModelStore();
        Intrinsics.checkExpressionValueIsNotNull(viewModelStore, "ownerProducer().viewModelStore");
        return viewModelStore;
    }
}
