package kotlinx.coroutines.flow.internal;

import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.TypeCastException;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.channels.ChannelCoroutine;
import kotlinx.coroutines.channels.ProducerScope;
import kotlinx.coroutines.channels.SendChannel;
import kotlinx.coroutines.flow.Flow;

@Metadata(mo33669bv = {1, 0, 3}, mo33670d1 = {"\u0000\u0012\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\u0010\u0000\u001a\u00020\u0001*\b\u0012\u0004\u0012\u00020\u00030\u0002H@¢\u0006\u0004\b\u0004\u0010\u0005"}, mo33671d2 = {"<anonymous>", "", "Lkotlinx/coroutines/channels/ProducerScope;", "", "invoke", "(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;"}, mo33672k = 3, mo33673mv = {1, 1, 15})
@DebugMetadata(mo34304c = "kotlinx.coroutines.flow.internal.CombineKt$asFairChannel$1", mo34305f = "Combine.kt", mo34306i = {0, 0, 0}, mo34307l = {144}, mo34308m = "invokeSuspend", mo34309n = {"$this$produce", "channel", "$this$collect$iv"}, mo34310s = {"L$0", "L$1", "L$2"})
/* compiled from: Combine.kt */
final class CombineKt$asFairChannel$1 extends SuspendLambda implements Function2<ProducerScope<? super Object>, Continuation<? super Unit>, Object> {
    final /* synthetic */ Flow $flow;
    Object L$0;
    Object L$1;
    Object L$2;
    int label;

    /* renamed from: p$ */
    private ProducerScope f700p$;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    CombineKt$asFairChannel$1(Flow flow, Continuation continuation) {
        super(2, continuation);
        this.$flow = flow;
    }

    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        Intrinsics.checkParameterIsNotNull(continuation, "completion");
        CombineKt$asFairChannel$1 combineKt$asFairChannel$1 = new CombineKt$asFairChannel$1(this.$flow, continuation);
        ProducerScope producerScope = (ProducerScope) obj;
        combineKt$asFairChannel$1.f700p$ = (ProducerScope) obj;
        return combineKt$asFairChannel$1;
    }

    public final Object invoke(Object obj, Object obj2) {
        return ((CombineKt$asFairChannel$1) create(obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    public final Object invokeSuspend(Object $result) {
        Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure($result);
            ProducerScope $this$produce = this.f700p$;
            SendChannel channel = $this$produce.getChannel();
            if (channel != null) {
                ChannelCoroutine channel2 = (ChannelCoroutine) channel;
                Flow $this$collect$iv = this.$flow;
                this.L$0 = $this$produce;
                this.L$1 = channel2;
                this.L$2 = $this$collect$iv;
                this.label = 1;
                if ($this$collect$iv.collect(new CombineKt$asFairChannel$1$invokeSuspend$$inlined$collect$1(channel2), this) == coroutine_suspended) {
                    return coroutine_suspended;
                }
                ChannelCoroutine channelCoroutine = channel2;
            } else {
                throw new TypeCastException("null cannot be cast to non-null type kotlinx.coroutines.channels.ChannelCoroutine<kotlin.Any>");
            }
        } else if (i == 1) {
            Flow $this$collect$iv2 = (Flow) this.L$2;
            ChannelCoroutine channel3 = (ChannelCoroutine) this.L$1;
            ProducerScope $this$produce2 = (ProducerScope) this.L$0;
            ResultKt.throwOnFailure($result);
        } else {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        return Unit.INSTANCE;
    }
}
