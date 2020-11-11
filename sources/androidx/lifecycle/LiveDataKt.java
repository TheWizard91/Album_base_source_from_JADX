package androidx.lifecycle;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;

@Metadata(mo33669bv = {1, 0, 3}, mo33670d1 = {"\u0000\u001e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0000\u001a=\u0010\u0000\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0014\b\u0004\u0010\u0006\u001a\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u00020\b0\u0007H\b¨\u0006\t"}, mo33671d2 = {"observe", "Landroidx/lifecycle/Observer;", "T", "Landroidx/lifecycle/LiveData;", "owner", "Landroidx/lifecycle/LifecycleOwner;", "onChanged", "Lkotlin/Function1;", "", "lifecycle-livedata-core-ktx_release"}, mo33672k = 2, mo33673mv = {1, 1, 15})
/* compiled from: LiveData.kt */
public final class LiveDataKt {
    public static final <T> Observer<T> observe(LiveData<T> $this$observe, LifecycleOwner owner, Function1<? super T, Unit> onChanged) {
        Intrinsics.checkParameterIsNotNull($this$observe, "$this$observe");
        Intrinsics.checkParameterIsNotNull(owner, "owner");
        Intrinsics.checkParameterIsNotNull(onChanged, "onChanged");
        Observer wrappedObserver = new LiveDataKt$observe$wrappedObserver$1(onChanged);
        $this$observe.observe(owner, wrappedObserver);
        return wrappedObserver;
    }
}
