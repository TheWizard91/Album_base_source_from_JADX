package kotlinx.coroutines.flow;

import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.channels.ProducerScope;

@Metadata(mo33669bv = {1, 0, 3}, mo33670d1 = {"\u0000\u0014\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\u0010\u0000\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\n\u0012\u0006\u0012\u0004\u0018\u00010\u00040\u0003H@¢\u0006\u0004\b\u0005\u0010\u0006"}, mo33671d2 = {"<anonymous>", "", "T", "Lkotlinx/coroutines/channels/ProducerScope;", "", "invoke", "(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;"}, mo33672k = 3, mo33673mv = {1, 1, 15})
@DebugMetadata(mo34304c = "kotlinx.coroutines.flow.FlowKt__DelayKt$sample$2$values$1", mo34305f = "Delay.kt", mo34306i = {0, 0}, mo34307l = {137}, mo34308m = "invokeSuspend", mo34309n = {"$this$produce", "$this$collect$iv"}, mo34310s = {"L$0", "L$1"})
/* compiled from: Delay.kt */
final class FlowKt__DelayKt$sample$2$values$1 extends SuspendLambda implements Function2<ProducerScope<? super Object>, Continuation<? super Unit>, Object> {
    Object L$0;
    Object L$1;
    int label;

    /* renamed from: p$ */
    private ProducerScope f652p$;
    final /* synthetic */ FlowKt__DelayKt$sample$2 this$0;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    FlowKt__DelayKt$sample$2$values$1(FlowKt__DelayKt$sample$2 flowKt__DelayKt$sample$2, Continuation continuation) {
        super(2, continuation);
        this.this$0 = flowKt__DelayKt$sample$2;
    }

    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        Intrinsics.checkParameterIsNotNull(continuation, "completion");
        FlowKt__DelayKt$sample$2$values$1 flowKt__DelayKt$sample$2$values$1 = new FlowKt__DelayKt$sample$2$values$1(this.this$0, continuation);
        ProducerScope producerScope = (ProducerScope) obj;
        flowKt__DelayKt$sample$2$values$1.f652p$ = (ProducerScope) obj;
        return flowKt__DelayKt$sample$2$values$1;
    }

    public final Object invoke(Object obj, Object obj2) {
        return ((FlowKt__DelayKt$sample$2$values$1) create(obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    public final Object invokeSuspend(Object $result) {
        Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure($result);
            ProducerScope $this$produce = this.f652p$;
            Flow $this$collect$iv = this.this$0.$this_sample;
            this.L$0 = $this$produce;
            this.L$1 = $this$collect$iv;
            this.label = 1;
            if ($this$collect$iv.collect(new C1771xe7e632($this$produce), this) == coroutine_suspended) {
                return coroutine_suspended;
            }
        } else if (i == 1) {
            Flow $this$collect$iv2 = (Flow) this.L$1;
            ProducerScope $this$produce2 = (ProducerScope) this.L$0;
            ResultKt.throwOnFailure($result);
        } else {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        return Unit.INSTANCE;
    }
}
