package kotlinx.coroutines.channels;

import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.CoroutineScope;

@Metadata(mo33669bv = {1, 0, 3}, mo33670d1 = {"\u0000\u0010\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0010\u0000\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\u00020\u0003H@¢\u0006\u0004\b\u0004\u0010\u0005"}, mo33671d2 = {"<anonymous>", "", "E", "Lkotlinx/coroutines/CoroutineScope;", "invoke", "(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;"}, mo33672k = 3, mo33673mv = {1, 1, 15})
@DebugMetadata(mo34304c = "kotlinx.coroutines.channels.ChannelsKt__ChannelsKt$sendBlocking$1", mo34305f = "Channels.kt", mo34306i = {0}, mo34307l = {27}, mo34308m = "invokeSuspend", mo34309n = {"$this$runBlocking"}, mo34310s = {"L$0"})
/* compiled from: Channels.kt */
final class ChannelsKt__ChannelsKt$sendBlocking$1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
    final /* synthetic */ Object $element;
    final /* synthetic */ SendChannel $this_sendBlocking;
    Object L$0;
    int label;

    /* renamed from: p$ */
    private CoroutineScope f630p$;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    ChannelsKt__ChannelsKt$sendBlocking$1(SendChannel sendChannel, Object obj, Continuation continuation) {
        super(2, continuation);
        this.$this_sendBlocking = sendChannel;
        this.$element = obj;
    }

    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        Intrinsics.checkParameterIsNotNull(continuation, "completion");
        ChannelsKt__ChannelsKt$sendBlocking$1 channelsKt__ChannelsKt$sendBlocking$1 = new ChannelsKt__ChannelsKt$sendBlocking$1(this.$this_sendBlocking, this.$element, continuation);
        CoroutineScope coroutineScope = (CoroutineScope) obj;
        channelsKt__ChannelsKt$sendBlocking$1.f630p$ = (CoroutineScope) obj;
        return channelsKt__ChannelsKt$sendBlocking$1;
    }

    public final Object invoke(Object obj, Object obj2) {
        return ((ChannelsKt__ChannelsKt$sendBlocking$1) create(obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    public final Object invokeSuspend(Object $result) {
        Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure($result);
            CoroutineScope $this$runBlocking = this.f630p$;
            SendChannel sendChannel = this.$this_sendBlocking;
            Object obj = this.$element;
            this.L$0 = $this$runBlocking;
            this.label = 1;
            if (sendChannel.send(obj, this) == coroutine_suspended) {
                return coroutine_suspended;
            }
            CoroutineScope coroutineScope = $this$runBlocking;
        } else if (i == 1) {
            CoroutineScope $this$runBlocking2 = (CoroutineScope) this.L$0;
            ResultKt.throwOnFailure($result);
        } else {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        return Unit.INSTANCE;
    }
}
