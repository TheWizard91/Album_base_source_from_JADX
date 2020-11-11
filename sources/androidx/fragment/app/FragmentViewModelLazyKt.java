package androidx.fragment.app;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelLazy;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStore;
import androidx.lifecycle.ViewModelStoreOwner;
import kotlin.Lazy;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KClass;

@Metadata(mo33669bv = {1, 0, 3}, mo33670d1 = {"\u00002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\u001a1\u0010\u0000\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\n\b\u0000\u0010\u0002\u0018\u0001*\u00020\u0003*\u00020\u00042\u0010\b\n\u0010\u0005\u001a\n\u0012\u0004\u0012\u00020\u0007\u0018\u00010\u0006H\b\u001aJ\u0010\b\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\b\b\u0000\u0010\u0002*\u00020\u0003*\u00020\u00042\f\u0010\t\u001a\b\u0012\u0004\u0012\u0002H\u00020\n2\f\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\f0\u00062\u0010\b\u0002\u0010\u0005\u001a\n\u0012\u0004\u0012\u00020\u0007\u0018\u00010\u0006H\u0007\u001aA\u0010\r\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\n\b\u0000\u0010\u0002\u0018\u0001*\u00020\u0003*\u00020\u00042\u000e\b\n\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u000f0\u00062\u0010\b\n\u0010\u0005\u001a\n\u0012\u0004\u0012\u00020\u0007\u0018\u00010\u0006H\b¨\u0006\u0010"}, mo33671d2 = {"activityViewModels", "Lkotlin/Lazy;", "VM", "Landroidx/lifecycle/ViewModel;", "Landroidx/fragment/app/Fragment;", "factoryProducer", "Lkotlin/Function0;", "Landroidx/lifecycle/ViewModelProvider$Factory;", "createViewModelLazy", "viewModelClass", "Lkotlin/reflect/KClass;", "storeProducer", "Landroidx/lifecycle/ViewModelStore;", "viewModels", "ownerProducer", "Landroidx/lifecycle/ViewModelStoreOwner;", "fragment-ktx_release"}, mo33672k = 2, mo33673mv = {1, 1, 15})
/* compiled from: FragmentViewModelLazy.kt */
public final class FragmentViewModelLazyKt {
    public static /* synthetic */ Lazy viewModels$default(Fragment $this$viewModels, Function0 ownerProducer, Function0 factoryProducer, int i, Object obj) {
        if ((i & 1) != 0) {
            ownerProducer = new FragmentViewModelLazyKt$viewModels$1($this$viewModels);
        }
        if ((i & 2) != 0) {
            factoryProducer = null;
        }
        Intrinsics.checkParameterIsNotNull($this$viewModels, "$this$viewModels");
        Intrinsics.checkParameterIsNotNull(ownerProducer, "ownerProducer");
        Intrinsics.reifiedOperationMarker(4, "VM");
        return createViewModelLazy($this$viewModels, Reflection.getOrCreateKotlinClass(ViewModel.class), new FragmentViewModelLazyKt$viewModels$2(ownerProducer), factoryProducer);
    }

    public static final /* synthetic */ <VM extends ViewModel> Lazy<VM> viewModels(Fragment $this$viewModels, Function0<? extends ViewModelStoreOwner> ownerProducer, Function0<? extends ViewModelProvider.Factory> factoryProducer) {
        Intrinsics.checkParameterIsNotNull($this$viewModels, "$this$viewModels");
        Intrinsics.checkParameterIsNotNull(ownerProducer, "ownerProducer");
        Intrinsics.reifiedOperationMarker(4, "VM");
        return createViewModelLazy($this$viewModels, Reflection.getOrCreateKotlinClass(ViewModel.class), new FragmentViewModelLazyKt$viewModels$2(ownerProducer), factoryProducer);
    }

    public static /* synthetic */ Lazy activityViewModels$default(Fragment $this$activityViewModels, Function0 factoryProducer, int i, Object obj) {
        if ((i & 1) != 0) {
            factoryProducer = null;
        }
        Intrinsics.checkParameterIsNotNull($this$activityViewModels, "$this$activityViewModels");
        Intrinsics.reifiedOperationMarker(4, "VM");
        return createViewModelLazy($this$activityViewModels, Reflection.getOrCreateKotlinClass(ViewModel.class), new FragmentViewModelLazyKt$activityViewModels$1($this$activityViewModels), factoryProducer != null ? factoryProducer : new FragmentViewModelLazyKt$activityViewModels$2($this$activityViewModels));
    }

    public static final /* synthetic */ <VM extends ViewModel> Lazy<VM> activityViewModels(Fragment $this$activityViewModels, Function0<? extends ViewModelProvider.Factory> factoryProducer) {
        Intrinsics.checkParameterIsNotNull($this$activityViewModels, "$this$activityViewModels");
        Intrinsics.reifiedOperationMarker(4, "VM");
        return createViewModelLazy($this$activityViewModels, Reflection.getOrCreateKotlinClass(ViewModel.class), new FragmentViewModelLazyKt$activityViewModels$1($this$activityViewModels), factoryProducer != null ? factoryProducer : new FragmentViewModelLazyKt$activityViewModels$2($this$activityViewModels));
    }

    public static /* synthetic */ Lazy createViewModelLazy$default(Fragment fragment, KClass kClass, Function0 function0, Function0 function02, int i, Object obj) {
        if ((i & 4) != 0) {
            function02 = null;
        }
        return createViewModelLazy(fragment, kClass, function0, function02);
    }

    public static final <VM extends ViewModel> Lazy<VM> createViewModelLazy(Fragment $this$createViewModelLazy, KClass<VM> viewModelClass, Function0<? extends ViewModelStore> storeProducer, Function0<? extends ViewModelProvider.Factory> factoryProducer) {
        Intrinsics.checkParameterIsNotNull($this$createViewModelLazy, "$this$createViewModelLazy");
        Intrinsics.checkParameterIsNotNull(viewModelClass, "viewModelClass");
        Intrinsics.checkParameterIsNotNull(storeProducer, "storeProducer");
        return new ViewModelLazy<>(viewModelClass, storeProducer, factoryProducer != null ? factoryProducer : new FragmentViewModelLazyKt$createViewModelLazy$factoryPromise$1($this$createViewModelLazy));
    }
}
