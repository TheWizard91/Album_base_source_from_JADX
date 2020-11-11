package kotlinx.coroutines.flow;

import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.channels.ProduceKt;
import kotlinx.coroutines.channels.ProducerScope;

@Metadata(mo33669bv = {1, 0, 3}, mo33670d1 = {"\u0000\u0010\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0010\u0000\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u0003H@¢\u0006\u0004\b\u0004\u0010\u0005"}, mo33671d2 = {"<anonymous>", "", "T", "Lkotlinx/coroutines/channels/ProducerScope;", "invoke", "(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;"}, mo33672k = 3, mo33673mv = {1, 1, 15})
@DebugMetadata(mo34304c = "kotlinx.coroutines.flow.FlowKt__BuildersKt$flowViaChannel$1", mo34305f = "Builders.kt", mo34306i = {0}, mo34307l = {194}, mo34308m = "invokeSuspend", mo34309n = {"$this$channelFlow"}, mo34310s = {"L$0"})
/* compiled from: Builders.kt */
final class FlowKt__BuildersKt$flowViaChannel$1 extends SuspendLambda implements Function2<ProducerScope<? super T>, Continuation<? super Unit>, Object> {
    final /* synthetic */ Function2 $block;
    Object L$0;
    int label;

    /* renamed from: p$ */
    private ProducerScope f646p$;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    FlowKt__BuildersKt$flowViaChannel$1(Function2 function2, Continuation continuation) {
        super(2, continuation);
        this.$block = function2;
    }

    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        Intrinsics.checkParameterIsNotNull(continuation, "completion");
        FlowKt__BuildersKt$flowViaChannel$1 flowKt__BuildersKt$flowViaChannel$1 = new FlowKt__BuildersKt$flowViaChannel$1(this.$block, continuation);
        ProducerScope producerScope = (ProducerScope) obj;
        flowKt__BuildersKt$flowViaChannel$1.f646p$ = (ProducerScope) obj;
        return flowKt__BuildersKt$flowViaChannel$1;
    }

    public final Object invoke(Object obj, Object obj2) {
        return ((FlowKt__BuildersKt$flowViaChannel$1) create(obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    public final Object invokeSuspend(Object $result) {
        Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure($result);
            ProducerScope $this$channelFlow = this.f646p$;
            this.$block.invoke($this$channelFlow, $this$channelFlow.getChannel());
            this.L$0 = $this$channelFlow;
            this.label = 1;
            if (ProduceKt.awaitClose$default($this$channelFlow, (Function0) null, this, 1, (Object) null) == coroutine_suspended) {
                return coroutine_suspended;
            }
            ProducerScope producerScope = $this$channelFlow;
        } else if (i == 1) {
            ProducerScope $this$channelFlow2 = (ProducerScope) this.L$0;
            ResultKt.throwOnFailure($result);
        } else {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        return Unit.INSTANCE;
    }
}
