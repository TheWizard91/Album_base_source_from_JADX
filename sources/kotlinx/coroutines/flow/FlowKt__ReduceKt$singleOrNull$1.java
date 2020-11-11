package kotlinx.coroutines.flow;

import kotlin.Metadata;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.DebugMetadata;

@Metadata(mo33669bv = {1, 0, 3}, mo33670d1 = {"\u0000\u0012\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\u0010\u0000\u001a\u0004\u0018\u00010\u0001\"\b\b\u0000\u0010\u0002*\u00020\u0001*\b\u0012\u0004\u0012\u0002H\u00020\u00032\u000e\u0010\u0004\u001a\n\u0012\u0006\u0012\u0004\u0018\u0001H\u00020\u0005H@"}, mo33671d2 = {"singleOrNull", "", "T", "Lkotlinx/coroutines/flow/Flow;", "continuation", "Lkotlin/coroutines/Continuation;"}, mo33672k = 3, mo33673mv = {1, 1, 15})
@DebugMetadata(mo34304c = "kotlinx.coroutines.flow.FlowKt__ReduceKt", mo34305f = "Reduce.kt", mo34306i = {0, 0, 0}, mo34307l = {136}, mo34308m = "singleOrNull", mo34309n = {"$this$singleOrNull", "result", "$this$collect$iv"}, mo34310s = {"L$0", "L$1", "L$2"})
/* compiled from: Reduce.kt */
final class FlowKt__ReduceKt$singleOrNull$1 extends ContinuationImpl {
    Object L$0;
    Object L$1;
    Object L$2;
    int label;
    /* synthetic */ Object result;

    FlowKt__ReduceKt$singleOrNull$1(Continuation continuation) {
        super(continuation);
    }

    public final Object invokeSuspend(Object obj) {
        this.result = obj;
        this.label |= Integer.MIN_VALUE;
        return FlowKt.singleOrNull((Flow) null, this);
    }
}
