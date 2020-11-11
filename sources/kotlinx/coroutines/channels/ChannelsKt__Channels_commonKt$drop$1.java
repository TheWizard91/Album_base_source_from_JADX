package kotlinx.coroutines.channels;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;

@Metadata(mo33669bv = {1, 0, 3}, mo33670d1 = {"\u0000\u0010\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0010\u0000\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u0003H@¢\u0006\u0004\b\u0004\u0010\u0005"}, mo33671d2 = {"<anonymous>", "", "E", "Lkotlinx/coroutines/channels/ProducerScope;", "invoke", "(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;"}, mo33672k = 3, mo33673mv = {1, 1, 15})
@DebugMetadata(mo34304c = "kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$drop$1", mo34305f = "Channels.common.kt", mo34306i = {0, 0, 1, 1, 2, 2, 2}, mo34307l = {700, 705, 706}, mo34308m = "invokeSuspend", mo34309n = {"$this$produce", "remaining", "$this$produce", "remaining", "$this$produce", "remaining", "e"}, mo34310s = {"L$0", "I$0", "L$0", "I$0", "L$0", "I$0", "L$1"})
/* compiled from: Channels.common.kt */
final class ChannelsKt__Channels_commonKt$drop$1 extends SuspendLambda implements Function2<ProducerScope<? super E>, Continuation<? super Unit>, Object> {

    /* renamed from: $n */
    final /* synthetic */ int f632$n;
    final /* synthetic */ ReceiveChannel $this_drop;
    int I$0;
    Object L$0;
    Object L$1;
    Object L$2;
    int label;

    /* renamed from: p$ */
    private ProducerScope f633p$;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    ChannelsKt__Channels_commonKt$drop$1(ReceiveChannel receiveChannel, int i, Continuation continuation) {
        super(2, continuation);
        this.$this_drop = receiveChannel;
        this.f632$n = i;
    }

    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        Intrinsics.checkParameterIsNotNull(continuation, "completion");
        ChannelsKt__Channels_commonKt$drop$1 channelsKt__Channels_commonKt$drop$1 = new ChannelsKt__Channels_commonKt$drop$1(this.$this_drop, this.f632$n, continuation);
        ProducerScope producerScope = (ProducerScope) obj;
        channelsKt__Channels_commonKt$drop$1.f633p$ = (ProducerScope) obj;
        return channelsKt__Channels_commonKt$drop$1;
    }

    public final Object invoke(Object obj, Object obj2) {
        return ((ChannelsKt__Channels_commonKt$drop$1) create(obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r6v16, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v19, resolved type: kotlinx.coroutines.channels.ProducerScope} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:24:0x0093  */
    /* JADX WARNING: Removed duplicated region for block: B:28:0x00a7  */
    /* JADX WARNING: Removed duplicated region for block: B:33:0x00c1 A[RETURN] */
    /* JADX WARNING: Removed duplicated region for block: B:34:0x00c2  */
    /* JADX WARNING: Removed duplicated region for block: B:37:0x00d1  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final java.lang.Object invokeSuspend(java.lang.Object r11) {
        /*
            r10 = this;
            java.lang.Object r0 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r1 = r10.label
            r2 = 3
            r3 = 2
            r4 = 0
            r5 = 1
            if (r1 == 0) goto L_0x005d
            r6 = 0
            if (r1 == r5) goto L_0x0047
            if (r1 == r3) goto L_0x0030
            if (r1 != r2) goto L_0x0028
            r1 = r6
            r5 = r6
            java.lang.Object r6 = r10.L$2
            kotlinx.coroutines.channels.ChannelIterator r6 = (kotlinx.coroutines.channels.ChannelIterator) r6
            java.lang.Object r5 = r10.L$1
            int r4 = r10.I$0
            java.lang.Object r7 = r10.L$0
            r1 = r7
            kotlinx.coroutines.channels.ProducerScope r1 = (kotlinx.coroutines.channels.ProducerScope) r1
            kotlin.ResultKt.throwOnFailure(r11)
            r8 = r10
            goto L_0x00ec
        L_0x0028:
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
            java.lang.String r1 = "call to 'resume' before 'invoke' with coroutine"
            r0.<init>(r1)
            throw r0
        L_0x0030:
            r1 = r6
            java.lang.Object r5 = r10.L$1
            kotlinx.coroutines.channels.ChannelIterator r5 = (kotlinx.coroutines.channels.ChannelIterator) r5
            int r4 = r10.I$0
            java.lang.Object r6 = r10.L$0
            r1 = r6
            kotlinx.coroutines.channels.ProducerScope r1 = (kotlinx.coroutines.channels.ProducerScope) r1
            kotlin.ResultKt.throwOnFailure(r11)
            r8 = r10
            r6 = r5
            r5 = r4
            r4 = r1
            r1 = r0
            r0 = r11
            goto L_0x00c9
        L_0x0047:
            r1 = r6
            java.lang.Object r6 = r10.L$1
            kotlinx.coroutines.channels.ChannelIterator r6 = (kotlinx.coroutines.channels.ChannelIterator) r6
            int r4 = r10.I$0
            java.lang.Object r7 = r10.L$0
            r1 = r7
            kotlinx.coroutines.channels.ProducerScope r1 = (kotlinx.coroutines.channels.ProducerScope) r1
            kotlin.ResultKt.throwOnFailure(r11)
            r8 = r10
            r7 = r6
            r6 = r4
            r4 = r1
            r1 = r0
            r0 = r11
            goto L_0x008b
        L_0x005d:
            kotlin.ResultKt.throwOnFailure(r11)
            kotlinx.coroutines.channels.ProducerScope r1 = r10.f633p$
            int r6 = r10.f632$n
            if (r6 < 0) goto L_0x0067
            r4 = r5
        L_0x0067:
            if (r4 == 0) goto L_0x00f1
            int r4 = r10.f632$n
            if (r4 <= 0) goto L_0x00ac
            kotlinx.coroutines.channels.ReceiveChannel r6 = r10.$this_drop
            kotlinx.coroutines.channels.ChannelIterator r6 = r6.iterator()
            r7 = r10
        L_0x0074:
            r7.L$0 = r1
            r7.I$0 = r4
            r7.L$1 = r6
            r7.label = r5
            java.lang.Object r8 = r6.hasNext(r7)
            if (r8 != r0) goto L_0x0083
            return r0
        L_0x0083:
            r9 = r0
            r0 = r11
            r11 = r8
            r8 = r7
            r7 = r6
            r6 = r4
            r4 = r1
            r1 = r9
        L_0x008b:
            java.lang.Boolean r11 = (java.lang.Boolean) r11
            boolean r11 = r11.booleanValue()
            if (r11 == 0) goto L_0x00a7
            java.lang.Object r11 = r7.next()
            int r6 = r6 + -1
            if (r6 != 0) goto L_0x00a0
            r11 = r0
            r0 = r1
            r1 = r4
            r4 = r6
            goto L_0x00ad
        L_0x00a0:
            r11 = r0
            r0 = r1
            r1 = r4
            r4 = r6
            r6 = r7
            r7 = r8
            goto L_0x0074
        L_0x00a7:
            r11 = r0
            r0 = r1
            r1 = r4
            r4 = r6
            goto L_0x00ad
        L_0x00ac:
            r8 = r10
        L_0x00ad:
            kotlinx.coroutines.channels.ReceiveChannel r5 = r8.$this_drop
            kotlinx.coroutines.channels.ChannelIterator r5 = r5.iterator()
        L_0x00b3:
            r8.L$0 = r1
            r8.I$0 = r4
            r8.L$1 = r5
            r8.label = r3
            java.lang.Object r6 = r5.hasNext(r8)
            if (r6 != r0) goto L_0x00c2
            return r0
        L_0x00c2:
            r9 = r0
            r0 = r11
            r11 = r6
            r6 = r5
            r5 = r4
            r4 = r1
            r1 = r9
        L_0x00c9:
            java.lang.Boolean r11 = (java.lang.Boolean) r11
            boolean r11 = r11.booleanValue()
            if (r11 == 0) goto L_0x00ee
            java.lang.Object r11 = r6.next()
            r8.L$0 = r4
            r8.I$0 = r5
            r8.L$1 = r11
            r8.L$2 = r6
            r8.label = r2
            java.lang.Object r7 = r4.send(r11, r8)
            if (r7 != r1) goto L_0x00e6
            return r1
        L_0x00e6:
            r9 = r5
            r5 = r11
            r11 = r0
            r0 = r1
            r1 = r4
            r4 = r9
        L_0x00ec:
            r5 = r6
            goto L_0x00b3
        L_0x00ee:
            kotlin.Unit r11 = kotlin.Unit.INSTANCE
            return r11
        L_0x00f1:
            r0 = 0
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            java.lang.String r3 = "Requested element count "
            java.lang.StringBuilder r2 = r2.append(r3)
            int r3 = r10.f632$n
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
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$drop$1.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
