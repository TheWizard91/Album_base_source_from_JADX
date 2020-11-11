package kotlinx.coroutines.channels;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;

@Metadata(mo33669bv = {1, 0, 3}, mo33670d1 = {"\u0000\u0010\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0010\u0000\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u0003H@¢\u0006\u0004\b\u0004\u0010\u0005"}, mo33671d2 = {"<anonymous>", "", "E", "Lkotlinx/coroutines/channels/ProducerScope;", "invoke", "(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;"}, mo33672k = 3, mo33673mv = {1, 1, 15})
@DebugMetadata(mo34304c = "kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$take$1", mo34305f = "Channels.common.kt", mo34306i = {0, 0, 1, 1, 1}, mo34307l = {994, 995}, mo34308m = "invokeSuspend", mo34309n = {"$this$produce", "remaining", "$this$produce", "remaining", "e"}, mo34310s = {"L$0", "I$0", "L$0", "I$0", "L$1"})
/* compiled from: Channels.common.kt */
final class ChannelsKt__Channels_commonKt$take$1 extends SuspendLambda implements Function2<ProducerScope<? super E>, Continuation<? super Unit>, Object> {

    /* renamed from: $n */
    final /* synthetic */ int f640$n;
    final /* synthetic */ ReceiveChannel $this_take;
    int I$0;
    Object L$0;
    Object L$1;
    Object L$2;
    int label;

    /* renamed from: p$ */
    private ProducerScope f641p$;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    ChannelsKt__Channels_commonKt$take$1(ReceiveChannel receiveChannel, int i, Continuation continuation) {
        super(2, continuation);
        this.$this_take = receiveChannel;
        this.f640$n = i;
    }

    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        Intrinsics.checkParameterIsNotNull(continuation, "completion");
        ChannelsKt__Channels_commonKt$take$1 channelsKt__Channels_commonKt$take$1 = new ChannelsKt__Channels_commonKt$take$1(this.$this_take, this.f640$n, continuation);
        ProducerScope producerScope = (ProducerScope) obj;
        channelsKt__Channels_commonKt$take$1.f641p$ = (ProducerScope) obj;
        return channelsKt__Channels_commonKt$take$1;
    }

    public final Object invoke(Object obj, Object obj2) {
        return ((ChannelsKt__Channels_commonKt$take$1) create(obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r6v6, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v8, resolved type: kotlinx.coroutines.channels.ProducerScope} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r7v6, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v13, resolved type: kotlinx.coroutines.channels.ProducerScope} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:23:0x007a  */
    /* JADX WARNING: Removed duplicated region for block: B:31:0x009f  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final java.lang.Object invokeSuspend(java.lang.Object r12) {
        /*
            r11 = this;
            java.lang.Object r0 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r1 = r11.label
            r2 = 2
            r3 = 0
            r4 = 1
            if (r1 == 0) goto L_0x0042
            r5 = 0
            if (r1 == r4) goto L_0x002c
            if (r1 != r2) goto L_0x0024
            r1 = r5
            java.lang.Object r6 = r11.L$2
            kotlinx.coroutines.channels.ChannelIterator r6 = (kotlinx.coroutines.channels.ChannelIterator) r6
            java.lang.Object r5 = r11.L$1
            int r3 = r11.I$0
            java.lang.Object r7 = r11.L$0
            r1 = r7
            kotlinx.coroutines.channels.ProducerScope r1 = (kotlinx.coroutines.channels.ProducerScope) r1
            kotlin.ResultKt.throwOnFailure(r12)
            r7 = r11
            goto L_0x0098
        L_0x0024:
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
            java.lang.String r1 = "call to 'resume' before 'invoke' with coroutine"
            r0.<init>(r1)
            throw r0
        L_0x002c:
            r1 = r5
            java.lang.Object r5 = r11.L$1
            kotlinx.coroutines.channels.ChannelIterator r5 = (kotlinx.coroutines.channels.ChannelIterator) r5
            int r3 = r11.I$0
            java.lang.Object r6 = r11.L$0
            r1 = r6
            kotlinx.coroutines.channels.ProducerScope r1 = (kotlinx.coroutines.channels.ProducerScope) r1
            kotlin.ResultKt.throwOnFailure(r12)
            r6 = r11
            r7 = r5
            r5 = r3
            r3 = r1
            r1 = r0
            r0 = r12
            goto L_0x0072
        L_0x0042:
            kotlin.ResultKt.throwOnFailure(r12)
            kotlinx.coroutines.channels.ProducerScope r1 = r11.f641p$
            int r5 = r11.f640$n
            if (r5 != 0) goto L_0x004e
            kotlin.Unit r0 = kotlin.Unit.INSTANCE
            return r0
        L_0x004e:
            if (r5 < 0) goto L_0x0051
            r3 = r4
        L_0x0051:
            if (r3 == 0) goto L_0x00a5
            int r3 = r11.f640$n
            kotlinx.coroutines.channels.ReceiveChannel r5 = r11.$this_take
            kotlinx.coroutines.channels.ChannelIterator r5 = r5.iterator()
            r6 = r11
        L_0x005c:
            r6.L$0 = r1
            r6.I$0 = r3
            r6.L$1 = r5
            r6.label = r4
            java.lang.Object r7 = r5.hasNext(r6)
            if (r7 != r0) goto L_0x006b
            return r0
        L_0x006b:
            r9 = r0
            r0 = r12
            r12 = r7
            r7 = r5
            r5 = r3
            r3 = r1
            r1 = r9
        L_0x0072:
            java.lang.Boolean r12 = (java.lang.Boolean) r12
            boolean r12 = r12.booleanValue()
            if (r12 == 0) goto L_0x00a2
            java.lang.Object r12 = r7.next()
            r6.L$0 = r3
            r6.I$0 = r5
            r6.L$1 = r12
            r6.L$2 = r7
            r6.label = r2
            java.lang.Object r8 = r3.send(r12, r6)
            if (r8 != r1) goto L_0x008f
            return r1
        L_0x008f:
            r9 = r5
            r5 = r12
            r12 = r0
            r0 = r1
            r1 = r3
            r3 = r9
            r10 = r7
            r7 = r6
            r6 = r10
        L_0x0098:
            int r3 = r3 + -1
            if (r3 != 0) goto L_0x009f
            kotlin.Unit r0 = kotlin.Unit.INSTANCE
            return r0
        L_0x009f:
            r5 = r6
            r6 = r7
            goto L_0x005c
        L_0x00a2:
            kotlin.Unit r12 = kotlin.Unit.INSTANCE
            return r12
        L_0x00a5:
            r0 = 0
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            java.lang.String r3 = "Requested element count "
            java.lang.StringBuilder r2 = r2.append(r3)
            int r3 = r11.f640$n
            java.lang.StringBuilder r2 = r2.append(r3)
            java.lang.String r3 = " is less than zero."
            java.lang.StringBuilder r2 = r2.append(r3)
            java.lang.String r0 = r2.toString()
            java.lang.IllegalArgumentException r2 = new java.lang.IllegalArgumentException
            java.lang.String r0 = r0.toString()
            r2.<init>(r0)
            java.lang.Throwable r2 = (java.lang.Throwable) r2
            throw r2
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$take$1.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
