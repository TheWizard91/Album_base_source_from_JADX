package kotlinx.coroutines.channels;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;

@Metadata(mo33669bv = {1, 0, 3}, mo33670d1 = {"\u0000\u0010\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0010\u0000\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u0003H@¢\u0006\u0004\b\u0004\u0010\u0005"}, mo33671d2 = {"<anonymous>", "", "E", "Lkotlinx/coroutines/channels/ProducerScope;", "invoke", "(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;"}, mo33672k = 3, mo33673mv = {1, 1, 15})
@DebugMetadata(mo34304c = "kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$filterIndexed$1", mo34305f = "Channels.common.kt", mo34306i = {0, 0, 1, 1, 1, 2, 2, 2}, mo34307l = {774, 775, 775}, mo34308m = "invokeSuspend", mo34309n = {"$this$produce", "index", "$this$produce", "index", "e", "$this$produce", "index", "e"}, mo34310s = {"L$0", "I$0", "L$0", "I$0", "L$1", "L$0", "I$0", "L$1"})
/* compiled from: Channels.common.kt */
final class ChannelsKt__Channels_commonKt$filterIndexed$1 extends SuspendLambda implements Function2<ProducerScope<? super E>, Continuation<? super Unit>, Object> {
    final /* synthetic */ Function3 $predicate;
    final /* synthetic */ ReceiveChannel $this_filterIndexed;
    int I$0;
    Object L$0;
    Object L$1;
    Object L$2;
    int label;

    /* renamed from: p$ */
    private ProducerScope f636p$;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    ChannelsKt__Channels_commonKt$filterIndexed$1(ReceiveChannel receiveChannel, Function3 function3, Continuation continuation) {
        super(2, continuation);
        this.$this_filterIndexed = receiveChannel;
        this.$predicate = function3;
    }

    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        Intrinsics.checkParameterIsNotNull(continuation, "completion");
        ChannelsKt__Channels_commonKt$filterIndexed$1 channelsKt__Channels_commonKt$filterIndexed$1 = new ChannelsKt__Channels_commonKt$filterIndexed$1(this.$this_filterIndexed, this.$predicate, continuation);
        ProducerScope producerScope = (ProducerScope) obj;
        channelsKt__Channels_commonKt$filterIndexed$1.f636p$ = (ProducerScope) obj;
        return channelsKt__Channels_commonKt$filterIndexed$1;
    }

    public final Object invoke(Object obj, Object obj2) {
        return ((ChannelsKt__Channels_commonKt$filterIndexed$1) create(obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r7v10, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v10, resolved type: kotlinx.coroutines.channels.ProducerScope} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r8v6, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v14, resolved type: kotlinx.coroutines.channels.ProducerScope} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r8v8, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v19, resolved type: kotlinx.coroutines.channels.ProducerScope} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:18:0x008c  */
    /* JADX WARNING: Removed duplicated region for block: B:27:0x00c8  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final java.lang.Object invokeSuspend(java.lang.Object r13) {
        /*
            r12 = this;
            java.lang.Object r0 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r1 = r12.label
            r2 = 3
            r3 = 2
            r4 = 1
            if (r1 == 0) goto L_0x0060
            r5 = 0
            r6 = 0
            if (r1 == r4) goto L_0x0049
            if (r1 == r3) goto L_0x002f
            if (r1 != r2) goto L_0x0027
            r1 = r5
            java.lang.Object r7 = r12.L$2
            kotlinx.coroutines.channels.ChannelIterator r7 = (kotlinx.coroutines.channels.ChannelIterator) r7
            java.lang.Object r5 = r12.L$1
            int r6 = r12.I$0
            java.lang.Object r8 = r12.L$0
            r1 = r8
            kotlinx.coroutines.channels.ProducerScope r1 = (kotlinx.coroutines.channels.ProducerScope) r1
            kotlin.ResultKt.throwOnFailure(r13)
            r9 = r12
            goto L_0x00ce
        L_0x0027:
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
            java.lang.String r1 = "call to 'resume' before 'invoke' with coroutine"
            r0.<init>(r1)
            throw r0
        L_0x002f:
            r1 = r5
            java.lang.Object r7 = r12.L$2
            kotlinx.coroutines.channels.ChannelIterator r7 = (kotlinx.coroutines.channels.ChannelIterator) r7
            java.lang.Object r5 = r12.L$1
            int r6 = r12.I$0
            java.lang.Object r8 = r12.L$0
            r1 = r8
            kotlinx.coroutines.channels.ProducerScope r1 = (kotlinx.coroutines.channels.ProducerScope) r1
            kotlin.ResultKt.throwOnFailure(r13)
            r9 = r12
            r8 = r7
            r7 = r6
            r6 = r5
            r5 = r1
            r1 = r0
            r0 = r13
            goto L_0x00af
        L_0x0049:
            r1 = r5
            r5 = r6
            java.lang.Object r6 = r12.L$1
            kotlinx.coroutines.channels.ChannelIterator r6 = (kotlinx.coroutines.channels.ChannelIterator) r6
            int r5 = r12.I$0
            java.lang.Object r7 = r12.L$0
            r1 = r7
            kotlinx.coroutines.channels.ProducerScope r1 = (kotlinx.coroutines.channels.ProducerScope) r1
            kotlin.ResultKt.throwOnFailure(r13)
            r8 = r12
            r7 = r6
            r6 = r5
            r5 = r1
            r1 = r0
            r0 = r13
            goto L_0x0084
        L_0x0060:
            kotlin.ResultKt.throwOnFailure(r13)
            kotlinx.coroutines.channels.ProducerScope r1 = r12.f636p$
            r5 = 0
            kotlinx.coroutines.channels.ReceiveChannel r6 = r12.$this_filterIndexed
            kotlinx.coroutines.channels.ChannelIterator r6 = r6.iterator()
            r7 = r12
        L_0x006d:
            r7.L$0 = r1
            r7.I$0 = r5
            r7.L$1 = r6
            r7.label = r4
            java.lang.Object r8 = r6.hasNext(r7)
            if (r8 != r0) goto L_0x007c
            return r0
        L_0x007c:
            r11 = r0
            r0 = r13
            r13 = r8
            r8 = r7
            r7 = r6
            r6 = r5
            r5 = r1
            r1 = r11
        L_0x0084:
            java.lang.Boolean r13 = (java.lang.Boolean) r13
            boolean r13 = r13.booleanValue()
            if (r13 == 0) goto L_0x00d9
            java.lang.Object r13 = r7.next()
            kotlin.jvm.functions.Function3 r9 = r8.$predicate
            java.lang.Integer r10 = kotlin.coroutines.jvm.internal.Boxing.boxInt(r6)
            int r6 = r6 + 1
            r8.L$0 = r5
            r8.I$0 = r6
            r8.L$1 = r13
            r8.L$2 = r7
            r8.label = r3
            java.lang.Object r9 = r9.invoke(r10, r13, r8)
            if (r9 != r1) goto L_0x00a9
            return r1
        L_0x00a9:
            r11 = r6
            r6 = r13
            r13 = r9
            r9 = r8
            r8 = r7
            r7 = r11
        L_0x00af:
            java.lang.Boolean r13 = (java.lang.Boolean) r13
            boolean r13 = r13.booleanValue()
            if (r13 == 0) goto L_0x00d2
            r9.L$0 = r5
            r9.I$0 = r7
            r9.L$1 = r6
            r9.L$2 = r8
            r9.label = r2
            java.lang.Object r13 = r5.send(r6, r9)
            if (r13 != r1) goto L_0x00c8
            return r1
        L_0x00c8:
            r13 = r0
            r0 = r1
            r1 = r5
            r5 = r6
            r6 = r7
            r7 = r8
        L_0x00ce:
            r5 = r6
            r6 = r7
            r7 = r9
            goto L_0x006d
        L_0x00d2:
            r13 = r0
            r0 = r1
            r1 = r5
            r5 = r7
            r6 = r8
            r7 = r9
            goto L_0x006d
        L_0x00d9:
            kotlin.Unit r13 = kotlin.Unit.INSTANCE
            return r13
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$filterIndexed$1.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
