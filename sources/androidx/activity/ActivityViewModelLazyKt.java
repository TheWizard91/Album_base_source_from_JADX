package androidx.activity;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelLazy;
import androidx.lifecycle.ViewModelProvider;
import kotlin.Lazy;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;

@Metadata(mo33669bv = {1, 0, 3}, mo33670d1 = {"\u0000\u001c\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\u001a1\u0010\u0000\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\n\b\u0000\u0010\u0002\u0018\u0001*\u00020\u0003*\u00020\u00042\u0010\b\n\u0010\u0005\u001a\n\u0012\u0004\u0012\u00020\u0007\u0018\u00010\u0006H\b¨\u0006\b"}, mo33671d2 = {"viewModels", "Lkotlin/Lazy;", "VM", "Landroidx/lifecycle/ViewModel;", "Landroidx/activity/ComponentActivity;", "factoryProducer", "Lkotlin/Function0;", "Landroidx/lifecycle/ViewModelProvider$Factory;", "activity-ktx_release"}, mo33672k = 2, mo33673mv = {1, 1, 15})
/* compiled from: ActivityViewModelLazy.kt */
public final class ActivityViewModelLazyKt {
    public static /* synthetic */ Lazy viewModels$default(ComponentActivity $this$viewModels, Function0 factoryProducer, int i, Object obj) {
        if ((i & 1) != 0) {
            factoryProducer = null;
        }
        Intrinsics.checkParameterIsNotNull($this$viewModels, "$this$viewModels");
        Function0 factoryPromise = factoryProducer != null ? factoryProducer : new ActivityViewModelLazyKt$viewModels$factoryPromise$1($this$viewModels);
        Intrinsics.reifiedOperationMarker(4, "VM");
        return new ViewModelLazy(Reflection.getOrCreateKotlinClass(ViewModel.class), new ActivityViewModelLazyKt$viewModels$1($this$viewModels), factoryPromise);
    }

    public static final /* synthetic */ <VM extends ViewModel> Lazy<VM> viewModels(ComponentActivity $this$viewModels, Function0<? extends ViewModelProvider.Factory> factoryProducer) {
        Intrinsics.checkParameterIsNotNull($this$viewModels, "$this$viewModels");
        Function0 factoryPromise = factoryProducer != null ? factoryProducer : new ActivityViewModelLazyKt$viewModels$factoryPromise$1($this$viewModels);
        Intrinsics.reifiedOperationMarker(4, "VM");
        return new ViewModelLazy<>(Reflection.getOrCreateKotlinClass(ViewModel.class), new ActivityViewModelLazyKt$viewModels$1($this$viewModels), factoryPromise);
    }
}
