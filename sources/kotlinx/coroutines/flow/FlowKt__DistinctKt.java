package kotlinx.coroutines.flow;

import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;

@Metadata(mo33669bv = {1, 0, 3}, mo33670d1 = {"\u0000&\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a\u001e\u0010\u0000\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u0001H\u0007\u001aV\u0010\u0000\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u000126\u0010\u0003\u001a2\u0012\u0013\u0012\u0011H\u0002¢\u0006\f\b\u0005\u0012\b\b\u0006\u0012\u0004\b\b(\u0007\u0012\u0013\u0012\u0011H\u0002¢\u0006\f\b\u0005\u0012\b\b\u0006\u0012\u0004\b\b(\b\u0012\u0004\u0012\u00020\t0\u0004H\u0007\u001a8\u0010\n\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u000b*\b\u0012\u0004\u0012\u0002H\u00020\u00012\u0012\u0010\f\u001a\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u000b0\rH\u0007\u001az\u0010\n\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u000b*\b\u0012\u0004\u0012\u0002H\u00020\u00012\u0014\b\u0004\u0010\f\u001a\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u000b0\r28\b\u0004\u0010\u0003\u001a2\u0012\u0013\u0012\u0011H\u000b¢\u0006\f\b\u0005\u0012\b\b\u0006\u0012\u0004\b\b(\u0007\u0012\u0013\u0012\u0011H\u000b¢\u0006\f\b\u0005\u0012\b\b\u0006\u0012\u0004\b\b(\b\u0012\u0004\u0012\u00020\t0\u0004H\b¢\u0006\u0002\b\u000e¨\u0006\u000f"}, mo33671d2 = {"distinctUntilChanged", "Lkotlinx/coroutines/flow/Flow;", "T", "areEquivalent", "Lkotlin/Function2;", "Lkotlin/ParameterName;", "name", "old", "new", "", "distinctUntilChangedBy", "K", "keySelector", "Lkotlin/Function1;", "distinctUntilChangedBy$FlowKt__DistinctKt", "kotlinx-coroutines-core"}, mo33672k = 5, mo33673mv = {1, 1, 15}, mo33676xs = "kotlinx/coroutines/flow/FlowKt")
/* compiled from: Distinct.kt */
final /* synthetic */ class FlowKt__DistinctKt {
    public static final <T> Flow<T> distinctUntilChanged(Flow<? extends T> $this$distinctUntilChanged) {
        Intrinsics.checkParameterIsNotNull($this$distinctUntilChanged, "$this$distinctUntilChanged");
        return FlowKt.distinctUntilChangedBy($this$distinctUntilChanged, FlowKt__DistinctKt$distinctUntilChanged$1.INSTANCE);
    }

    public static final <T> Flow<T> distinctUntilChanged(Flow<? extends T> $this$distinctUntilChanged, Function2<? super T, ? super T, Boolean> areEquivalent) {
        Intrinsics.checkParameterIsNotNull($this$distinctUntilChanged, "$this$distinctUntilChanged");
        Intrinsics.checkParameterIsNotNull(areEquivalent, "areEquivalent");
        return new C1773x9f1d94d1($this$distinctUntilChanged, areEquivalent);
    }

    public static final <T, K> Flow<T> distinctUntilChangedBy(Flow<? extends T> $this$distinctUntilChangedBy, Function1<? super T, ? extends K> keySelector) {
        Intrinsics.checkParameterIsNotNull($this$distinctUntilChangedBy, "$this$distinctUntilChangedBy");
        Intrinsics.checkParameterIsNotNull(keySelector, "keySelector");
        return new C1776xde9fe73a($this$distinctUntilChangedBy, keySelector);
    }

    private static final <T, K> Flow<T> distinctUntilChangedBy$FlowKt__DistinctKt(Flow<? extends T> $this$distinctUntilChangedBy, Function1<? super T, ? extends K> keySelector, Function2<? super K, ? super K, Boolean> areEquivalent) {
        return new FlowKt__DistinctKt$distinctUntilChangedBy$$inlined$unsafeFlow$3($this$distinctUntilChangedBy, keySelector, areEquivalent);
    }
}
