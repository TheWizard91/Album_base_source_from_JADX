package kotlinx.coroutines.flow;

import kotlin.Metadata;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.jvm.functions.Function3;

@Metadata(mo33669bv = {1, 0, 3}, mo33670d1 = {"\u0000\"\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\u0010\u0000\u001a\u0004\u0018\u00010\u0001\"\u0004\b\u0000\u0010\u0002\"\b\b\u0001\u0010\u0003*\u0002H\u0002*\b\u0012\u0004\u0012\u0002H\u00030\u00042F\u0010\u0005\u001aB\b\u0001\u0012\u0013\u0012\u0011H\u0002¢\u0006\f\b\u0007\u0012\b\b\b\u0012\u0004\b\b(\t\u0012\u0013\u0012\u0011H\u0003¢\u0006\f\b\u0007\u0012\b\b\b\u0012\u0004\b\b(\n\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00020\u000b\u0012\u0006\u0012\u0004\u0018\u00010\u00010\u00062\f\u0010\f\u001a\b\u0012\u0004\u0012\u0002H\u00020\u000bH@"}, mo33671d2 = {"reduce", "", "S", "T", "Lkotlinx/coroutines/flow/Flow;", "operation", "Lkotlin/Function3;", "Lkotlin/ParameterName;", "name", "accumulator", "value", "Lkotlin/coroutines/Continuation;", "continuation"}, mo33672k = 3, mo33673mv = {1, 1, 15})
@DebugMetadata(mo34304c = "kotlinx.coroutines.flow.FlowKt__ReduceKt", mo34305f = "Reduce.kt", mo34306i = {0, 0, 0, 0}, mo34307l = {124}, mo34308m = "reduce", mo34309n = {"$this$reduce", "operation", "accumulator", "$this$collect$iv"}, mo34310s = {"L$0", "L$1", "L$2", "L$3"})
/* compiled from: Reduce.kt */
final class FlowKt__ReduceKt$reduce$1 extends ContinuationImpl {
    Object L$0;
    Object L$1;
    Object L$2;
    Object L$3;
    int label;
    /* synthetic */ Object result;

    FlowKt__ReduceKt$reduce$1(Continuation continuation) {
        super(continuation);
    }

    public final Object invokeSuspend(Object obj) {
        this.result = obj;
        this.label |= Integer.MIN_VALUE;
        return FlowKt.reduce((Flow) null, (Function3) null, this);
    }
}
