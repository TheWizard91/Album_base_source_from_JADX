package kotlinx.coroutines.channels;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;

@Metadata(mo33669bv = {1, 0, 3}, mo33670d1 = {"\u0000\u0010\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0010\u0000\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u0003H@¢\u0006\u0004\b\u0004\u0010\u0005"}, mo33671d2 = {"<anonymous>", "", "E", "Lkotlinx/coroutines/channels/ProducerScope;", "invoke", "(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;"}, mo33672k = 3, mo33673mv = {1, 1, 15})
@DebugMetadata(mo34304c = "kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$dropWhile$1", mo34305f = "Channels.common.kt", mo34306i = {0, 1, 1, 2, 2, 3, 4, 4}, mo34307l = {725, 726, 727, 731, 732}, mo34308m = "invokeSuspend", mo34309n = {"$this$produce", "$this$produce", "e", "$this$produce", "e", "$this$produce", "$this$produce", "e"}, mo34310s = {"L$0", "L$0", "L$1", "L$0", "L$1", "L$0", "L$0", "L$1"})
/* compiled from: Channels.common.kt */
final class ChannelsKt__Channels_commonKt$dropWhile$1 extends SuspendLambda implements Function2<ProducerScope<? super E>, Continuation<? super Unit>, Object> {
    final /* synthetic */ Function2 $predicate;
    final /* synthetic */ ReceiveChannel $this_dropWhile;
    Object L$0;
    Object L$1;
    Object L$2;
    int label;

    /* renamed from: p$ */
    private ProducerScope f634p$;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    ChannelsKt__Channels_commonKt$dropWhile$1(ReceiveChannel receiveChannel, Function2 function2, Continuation continuation) {
        super(2, continuation);
        this.$this_dropWhile = receiveChannel;
        this.$predicate = function2;
    }

    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        Intrinsics.checkParameterIsNotNull(continuation, "completion");
        ChannelsKt__Channels_commonKt$dropWhile$1 channelsKt__Channels_commonKt$dropWhile$1 = new ChannelsKt__Channels_commonKt$dropWhile$1(this.$this_dropWhile, this.$predicate, continuation);
        ProducerScope producerScope = (ProducerScope) obj;
        channelsKt__Channels_commonKt$dropWhile$1.f634p$ = (ProducerScope) obj;
        return channelsKt__Channels_commonKt$dropWhile$1;
    }

    public final Object invoke(Object obj, Object obj2) {
        return ((ChannelsKt__Channels_commonKt$dropWhile$1) create(obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r8v11, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v14, resolved type: kotlinx.coroutines.channels.ProducerScope} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r9v5, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v18, resolved type: kotlinx.coroutines.channels.ProducerScope} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r5v3, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v22, resolved type: kotlinx.coroutines.channels.ProducerScope} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r5v4, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v25, resolved type: kotlinx.coroutines.channels.ProducerScope} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r6v1, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v30, resolved type: kotlinx.coroutines.channels.ProducerScope} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:22:0x00aa  */
    /* JADX WARNING: Removed duplicated region for block: B:30:0x00d7 A[RETURN] */
    /* JADX WARNING: Removed duplicated region for block: B:31:0x00d8  */
    /* JADX WARNING: Removed duplicated region for block: B:33:0x00e1  */
    /* JADX WARNING: Removed duplicated region for block: B:37:0x00f9 A[RETURN] */
    /* JADX WARNING: Removed duplicated region for block: B:38:0x00fa  */
    /* JADX WARNING: Removed duplicated region for block: B:41:0x0104  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final java.lang.Object invokeSuspend(java.lang.Object r13) {
        /*
            r12 = this;
            java.lang.Object r0 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r1 = r12.label
            r2 = 5
            r3 = 4
            r4 = 3
            r5 = 2
            r6 = 1
            if (r1 == 0) goto L_0x0083
            r7 = 0
            if (r1 == r6) goto L_0x0070
            if (r1 == r5) goto L_0x005a
            if (r1 == r4) goto L_0x004b
            if (r1 == r3) goto L_0x0037
            if (r1 != r2) goto L_0x002f
            r1 = r7
            r4 = r7
            java.lang.Object r5 = r12.L$2
            kotlinx.coroutines.channels.ChannelIterator r5 = (kotlinx.coroutines.channels.ChannelIterator) r5
            java.lang.Object r4 = r12.L$1
            java.lang.Object r6 = r12.L$0
            r1 = r6
            kotlinx.coroutines.channels.ProducerScope r1 = (kotlinx.coroutines.channels.ProducerScope) r1
            kotlin.ResultKt.throwOnFailure(r13)
            r8 = r12
            r7 = r1
            r1 = r0
            r0 = r13
            r13 = r5
            goto L_0x0118
        L_0x002f:
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
            java.lang.String r1 = "call to 'resume' before 'invoke' with coroutine"
            r0.<init>(r1)
            throw r0
        L_0x0037:
            r1 = r7
            java.lang.Object r4 = r12.L$1
            kotlinx.coroutines.channels.ChannelIterator r4 = (kotlinx.coroutines.channels.ChannelIterator) r4
            java.lang.Object r5 = r12.L$0
            r1 = r5
            kotlinx.coroutines.channels.ProducerScope r1 = (kotlinx.coroutines.channels.ProducerScope) r1
            kotlin.ResultKt.throwOnFailure(r13)
            r8 = r12
            r7 = r1
            r5 = r4
            r1 = r0
            r0 = r13
            goto L_0x00fc
        L_0x004b:
            r1 = r7
            r4 = r7
            java.lang.Object r4 = r12.L$1
            java.lang.Object r5 = r12.L$0
            r1 = r5
            kotlinx.coroutines.channels.ProducerScope r1 = (kotlinx.coroutines.channels.ProducerScope) r1
            kotlin.ResultKt.throwOnFailure(r13)
            r8 = r12
            goto L_0x00dd
        L_0x005a:
            r1 = r7
            java.lang.Object r8 = r12.L$2
            kotlinx.coroutines.channels.ChannelIterator r8 = (kotlinx.coroutines.channels.ChannelIterator) r8
            java.lang.Object r7 = r12.L$1
            java.lang.Object r9 = r12.L$0
            r1 = r9
            kotlinx.coroutines.channels.ProducerScope r1 = (kotlinx.coroutines.channels.ProducerScope) r1
            kotlin.ResultKt.throwOnFailure(r13)
            r10 = r12
            r9 = r8
            r8 = r7
            r7 = r1
            r1 = r0
            r0 = r13
            goto L_0x00c3
        L_0x0070:
            r1 = r7
            java.lang.Object r7 = r12.L$1
            kotlinx.coroutines.channels.ChannelIterator r7 = (kotlinx.coroutines.channels.ChannelIterator) r7
            java.lang.Object r8 = r12.L$0
            r1 = r8
            kotlinx.coroutines.channels.ProducerScope r1 = (kotlinx.coroutines.channels.ProducerScope) r1
            kotlin.ResultKt.throwOnFailure(r13)
            r8 = r12
            r9 = r7
            r7 = r1
            r1 = r0
            r0 = r13
            goto L_0x00a2
        L_0x0083:
            kotlin.ResultKt.throwOnFailure(r13)
            kotlinx.coroutines.channels.ProducerScope r1 = r12.f634p$
            kotlinx.coroutines.channels.ReceiveChannel r7 = r12.$this_dropWhile
            kotlinx.coroutines.channels.ChannelIterator r7 = r7.iterator()
            r8 = r12
        L_0x008f:
            r8.L$0 = r1
            r8.L$1 = r7
            r8.label = r6
            java.lang.Object r9 = r7.hasNext(r8)
            if (r9 != r0) goto L_0x009c
            return r0
        L_0x009c:
            r11 = r0
            r0 = r13
            r13 = r9
            r9 = r7
            r7 = r1
            r1 = r11
        L_0x00a2:
            java.lang.Boolean r13 = (java.lang.Boolean) r13
            boolean r13 = r13.booleanValue()
            if (r13 == 0) goto L_0x00e7
            java.lang.Object r13 = r9.next()
            kotlin.jvm.functions.Function2 r10 = r8.$predicate
            r8.L$0 = r7
            r8.L$1 = r13
            r8.L$2 = r9
            r8.label = r5
            java.lang.Object r10 = r10.invoke(r13, r8)
            if (r10 != r1) goto L_0x00bf
            return r1
        L_0x00bf:
            r11 = r8
            r8 = r13
            r13 = r10
            r10 = r11
        L_0x00c3:
            java.lang.Boolean r13 = (java.lang.Boolean) r13
            boolean r13 = r13.booleanValue()
            if (r13 != 0) goto L_0x00e1
            r10.L$0 = r7
            r10.L$1 = r8
            r10.label = r4
            java.lang.Object r13 = r7.send(r8, r10)
            if (r13 != r1) goto L_0x00d8
            return r1
        L_0x00d8:
            r13 = r0
            r0 = r1
            r1 = r7
            r4 = r8
            r8 = r10
        L_0x00dd:
            r7 = r1
            r1 = r0
            r0 = r13
            goto L_0x00e7
        L_0x00e1:
            r13 = r0
            r0 = r1
            r1 = r7
            r7 = r9
            r8 = r10
            goto L_0x008f
        L_0x00e7:
            kotlinx.coroutines.channels.ReceiveChannel r13 = r8.$this_dropWhile
            kotlinx.coroutines.channels.ChannelIterator r13 = r13.iterator()
        L_0x00ed:
            r8.L$0 = r7
            r8.L$1 = r13
            r8.label = r3
            java.lang.Object r4 = r13.hasNext(r8)
            if (r4 != r1) goto L_0x00fa
            return r1
        L_0x00fa:
            r5 = r13
            r13 = r4
        L_0x00fc:
            java.lang.Boolean r13 = (java.lang.Boolean) r13
            boolean r13 = r13.booleanValue()
            if (r13 == 0) goto L_0x0119
            java.lang.Object r4 = r5.next()
            r8.L$0 = r7
            r8.L$1 = r4
            r8.L$2 = r5
            r8.label = r2
            java.lang.Object r13 = r7.send(r4, r8)
            if (r13 != r1) goto L_0x0117
            return r1
        L_0x0117:
            r13 = r5
        L_0x0118:
            goto L_0x00ed
        L_0x0119:
            kotlin.Unit r13 = kotlin.Unit.INSTANCE
            return r13
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$dropWhile$1.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
