package androidx.navigation;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentViewModelLazyKt;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KProperty;

@Metadata(mo33669bv = {1, 0, 3}, mo33670d1 = {"\u0000\"\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\u001a;\u0010\u0000\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\n\b\u0000\u0010\u0002\u0018\u0001*\u00020\u0003*\u00020\u00042\b\b\u0001\u0010\u0005\u001a\u00020\u00062\u0010\b\n\u0010\u0007\u001a\n\u0012\u0004\u0012\u00020\t\u0018\u00010\bH\b¨\u0006\n"}, mo33671d2 = {"navGraphViewModels", "Lkotlin/Lazy;", "VM", "Landroidx/lifecycle/ViewModel;", "Landroidx/fragment/app/Fragment;", "navGraphId", "", "factoryProducer", "Lkotlin/Function0;", "Landroidx/lifecycle/ViewModelProvider$Factory;", "navigation-fragment-ktx_release"}, mo33672k = 2, mo33673mv = {1, 1, 16})
/* compiled from: NavGraphViewModelLazy.kt */
public final class NavGraphViewModelLazyKt {
    public static /* synthetic */ Lazy navGraphViewModels$default(Fragment $this$navGraphViewModels, int navGraphId, Function0 factoryProducer, int i, Object obj) {
        if ((i & 2) != 0) {
            factoryProducer = null;
        }
        Intrinsics.checkParameterIsNotNull($this$navGraphViewModels, "$this$navGraphViewModels");
        Lazy backStackEntry = LazyKt.lazy(new NavGraphViewModelLazyKt$navGraphViewModels$backStackEntry$2($this$navGraphViewModels, navGraphId));
        Intrinsics.reifiedOperationMarker(4, "VM");
        return FragmentViewModelLazyKt.createViewModelLazy($this$navGraphViewModels, Reflection.getOrCreateKotlinClass(ViewModel.class), new NavGraphViewModelLazyKt$navGraphViewModels$storeProducer$1(backStackEntry, (KProperty) null), new NavGraphViewModelLazyKt$navGraphViewModels$1(factoryProducer, backStackEntry, (KProperty) null));
    }

    public static final /* synthetic */ <VM extends ViewModel> Lazy<VM> navGraphViewModels(Fragment $this$navGraphViewModels, int navGraphId, Function0<? extends ViewModelProvider.Factory> factoryProducer) {
        Intrinsics.checkParameterIsNotNull($this$navGraphViewModels, "$this$navGraphViewModels");
        Lazy backStackEntry = LazyKt.lazy(new NavGraphViewModelLazyKt$navGraphViewModels$backStackEntry$2($this$navGraphViewModels, navGraphId));
        Intrinsics.reifiedOperationMarker(4, "VM");
        return FragmentViewModelLazyKt.createViewModelLazy($this$navGraphViewModels, Reflection.getOrCreateKotlinClass(ViewModel.class), new NavGraphViewModelLazyKt$navGraphViewModels$storeProducer$1(backStackEntry, (KProperty) null), new NavGraphViewModelLazyKt$navGraphViewModels$1(factoryProducer, backStackEntry, (KProperty) null));
    }
}
