package kotlinx.coroutines.channels;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;

@Metadata(mo33669bv = {1, 0, 3}, mo33670d1 = {"\u0000\u0012\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u0010\u0000\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003*\b\u0012\u0004\u0012\u0002H\u00030\u0004H@¢\u0006\u0004\b\u0005\u0010\u0006"}, mo33671d2 = {"<anonymous>", "", "E", "R", "Lkotlinx/coroutines/channels/ProducerScope;", "invoke", "(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;"}, mo33672k = 3, mo33673mv = {1, 1, 15})
@DebugMetadata(mo34304c = "kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$map$1", mo34305f = "Channels.common.kt", mo34306i = {0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 2, 2, 2, 2, 2, 2, 2}, mo34307l = {2201, 1400, 1400}, mo34308m = "invokeSuspend", mo34309n = {"$this$produce", "$this$consumeEach$iv", "$this$consume$iv$iv", "cause$iv$iv", "$this$consume$iv", "$this$produce", "$this$consumeEach$iv", "$this$consume$iv$iv", "cause$iv$iv", "$this$consume$iv", "e$iv", "it", "$this$produce", "$this$consumeEach$iv", "$this$consume$iv$iv", "cause$iv$iv", "$this$consume$iv", "e$iv", "it"}, mo34310s = {"L$0", "L$1", "L$3", "L$4", "L$5", "L$0", "L$1", "L$3", "L$4", "L$5", "L$7", "L$8", "L$0", "L$1", "L$3", "L$4", "L$5", "L$7", "L$8"})
/* compiled from: Channels.common.kt */
final class ChannelsKt__Channels_commonKt$map$1 extends SuspendLambda implements Function2<ProducerScope<? super R>, Continuation<? super Unit>, Object> {
    final /* synthetic */ ReceiveChannel $this_map;
    final /* synthetic */ Function2 $transform;
    Object L$0;
    Object L$1;
    Object L$2;
    Object L$3;
    Object L$4;
    Object L$5;
    Object L$6;
    Object L$7;
    Object L$8;
    Object L$9;
    int label;

    /* renamed from: p$ */
    private ProducerScope f638p$;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    ChannelsKt__Channels_commonKt$map$1(ReceiveChannel receiveChannel, Function2 function2, Continuation continuation) {
        super(2, continuation);
        this.$this_map = receiveChannel;
        this.$transform = function2;
    }

    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        Intrinsics.checkParameterIsNotNull(continuation, "completion");
        ChannelsKt__Channels_commonKt$map$1 channelsKt__Channels_commonKt$map$1 = new ChannelsKt__Channels_commonKt$map$1(this.$this_map, this.$transform, continuation);
        ProducerScope producerScope = (ProducerScope) obj;
        channelsKt__Channels_commonKt$map$1.f638p$ = (ProducerScope) obj;
        return channelsKt__Channels_commonKt$map$1;
    }

    public final Object invoke(Object obj, Object obj2) {
        return ((ChannelsKt__Channels_commonKt$map$1) create(obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* Debug info: failed to restart local var, previous not found, register: 21 */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r9v6, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v11, resolved type: kotlinx.coroutines.channels.ReceiveChannel} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r9v7, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r12v11, resolved type: java.lang.Throwable} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v9, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v13, resolved type: kotlinx.coroutines.channels.ReceiveChannel} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r9v8, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r6v8, resolved type: kotlinx.coroutines.channels.ReceiveChannel} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r14v10, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r10v10, resolved type: kotlinx.coroutines.channels.ReceiveChannel} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r14v11, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r9v13, resolved type: java.lang.Throwable} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r14v12, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v14, resolved type: kotlinx.coroutines.channels.ReceiveChannel} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v19, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r6v10, resolved type: kotlinx.coroutines.channels.ReceiveChannel} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r13v10, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r12v17, resolved type: java.lang.Throwable} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r13v11, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r8v21, resolved type: kotlinx.coroutines.channels.ReceiveChannel} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:37:0x0146 A[Catch:{ all -> 0x01cf }] */
    /* JADX WARNING: Removed duplicated region for block: B:48:0x019d  */
    /* JADX WARNING: Removed duplicated region for block: B:52:0x01bc  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final java.lang.Object invokeSuspend(java.lang.Object r22) {
        /*
            r21 = this;
            r1 = r21
            java.lang.Object r0 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r2 = r1.label
            r3 = 3
            r4 = 2
            r5 = 1
            r6 = 0
            if (r2 == 0) goto L_0x0103
            r7 = 0
            if (r2 == r5) goto L_0x00bd
            if (r2 == r4) goto L_0x0065
            if (r2 != r3) goto L_0x005d
            r2 = r6
            r8 = r6
            r9 = r7
            r10 = r6
            r11 = r6
            r12 = r6
            r13 = r6
            r14 = r7
            r15 = r7
            java.lang.Object r11 = r1.L$8
            java.lang.Object r10 = r1.L$7
            java.lang.Object r3 = r1.L$6
            kotlinx.coroutines.channels.ChannelIterator r3 = (kotlinx.coroutines.channels.ChannelIterator) r3
            java.lang.Object r4 = r1.L$5
            kotlinx.coroutines.channels.ReceiveChannel r4 = (kotlinx.coroutines.channels.ReceiveChannel) r4
            java.lang.Object r13 = r1.L$4
            r12 = r13
            java.lang.Throwable r12 = (java.lang.Throwable) r12
            java.lang.Object r13 = r1.L$3
            r8 = r13
            kotlinx.coroutines.channels.ReceiveChannel r8 = (kotlinx.coroutines.channels.ReceiveChannel) r8
            java.lang.Object r13 = r1.L$2
            kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$map$1 r13 = (kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$map$1) r13
            java.lang.Object r5 = r1.L$1
            kotlinx.coroutines.channels.ReceiveChannel r5 = (kotlinx.coroutines.channels.ReceiveChannel) r5
            java.lang.Object r6 = r1.L$0
            r2 = r6
            kotlinx.coroutines.channels.ProducerScope r2 = (kotlinx.coroutines.channels.ProducerScope) r2
            kotlin.ResultKt.throwOnFailure(r22)     // Catch:{ all -> 0x0057 }
            r6 = r5
            r19 = r15
            r5 = r4
            r15 = r12
            r4 = r13
            r12 = r8
            r13 = r11
            r8 = r7
            r11 = r10
            r7 = r14
            r14 = r1
            r10 = r3
            r1 = r22
            r3 = r2
            r2 = 3
            goto L_0x01ab
        L_0x0057:
            r0 = move-exception
            r11 = r1
            r1 = r22
            goto L_0x01e5
        L_0x005d:
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
            java.lang.String r2 = "call to 'resume' before 'invoke' with coroutine"
            r0.<init>(r2)
            throw r0
        L_0x0065:
            r2 = r6
            r3 = r6
            r4 = r7
            r5 = r6
            r8 = r6
            r9 = r6
            r10 = r6
            r11 = r7
            r15 = r7
            java.lang.Object r12 = r1.L$9
            kotlinx.coroutines.channels.ProducerScope r12 = (kotlinx.coroutines.channels.ProducerScope) r12
            java.lang.Object r8 = r1.L$8
            java.lang.Object r5 = r1.L$7
            java.lang.Object r13 = r1.L$6
            kotlinx.coroutines.channels.ChannelIterator r13 = (kotlinx.coroutines.channels.ChannelIterator) r13
            java.lang.Object r14 = r1.L$5
            r10 = r14
            kotlinx.coroutines.channels.ReceiveChannel r10 = (kotlinx.coroutines.channels.ReceiveChannel) r10
            java.lang.Object r14 = r1.L$4
            r9 = r14
            java.lang.Throwable r9 = (java.lang.Throwable) r9
            java.lang.Object r14 = r1.L$3
            r3 = r14
            kotlinx.coroutines.channels.ReceiveChannel r3 = (kotlinx.coroutines.channels.ReceiveChannel) r3
            java.lang.Object r14 = r1.L$2
            kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$map$1 r14 = (kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$map$1) r14
            r18 = r0
            java.lang.Object r0 = r1.L$1
            r6 = r0
            kotlinx.coroutines.channels.ReceiveChannel r6 = (kotlinx.coroutines.channels.ReceiveChannel) r6
            java.lang.Object r0 = r1.L$0
            r2 = r0
            kotlinx.coroutines.channels.ProducerScope r2 = (kotlinx.coroutines.channels.ProducerScope) r2
            kotlin.ResultKt.throwOnFailure(r22)     // Catch:{ all -> 0x00b4 }
            r17 = r4
            r4 = r10
            r16 = r11
            r19 = r15
            r0 = r18
            r11 = r1
            r10 = r5
            r5 = r6
            r6 = r8
            r15 = r14
            r1 = r22
            r8 = r3
            r14 = r9
            r9 = r13
            r13 = r1
            r3 = r2
            r2 = 2
            goto L_0x0181
        L_0x00b4:
            r0 = move-exception
            r11 = r1
            r8 = r3
            r5 = r6
            r12 = r9
            r1 = r22
            goto L_0x01e5
        L_0x00bd:
            r18 = r0
            r0 = r6
            r2 = r6
            r3 = r6
            r4 = r6
            r5 = r7
            r15 = r7
            java.lang.Object r8 = r1.L$6
            kotlinx.coroutines.channels.ChannelIterator r8 = (kotlinx.coroutines.channels.ChannelIterator) r8
            java.lang.Object r9 = r1.L$5
            r4 = r9
            kotlinx.coroutines.channels.ReceiveChannel r4 = (kotlinx.coroutines.channels.ReceiveChannel) r4
            java.lang.Object r9 = r1.L$4
            r12 = r9
            java.lang.Throwable r12 = (java.lang.Throwable) r12
            java.lang.Object r3 = r1.L$3
            r2 = r3
            kotlinx.coroutines.channels.ReceiveChannel r2 = (kotlinx.coroutines.channels.ReceiveChannel) r2
            java.lang.Object r3 = r1.L$2
            kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$map$1 r3 = (kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$map$1) r3
            java.lang.Object r9 = r1.L$1
            r6 = r9
            kotlinx.coroutines.channels.ReceiveChannel r6 = (kotlinx.coroutines.channels.ReceiveChannel) r6
            java.lang.Object r9 = r1.L$0
            kotlinx.coroutines.channels.ProducerScope r9 = (kotlinx.coroutines.channels.ProducerScope) r9
            kotlin.ResultKt.throwOnFailure(r22)     // Catch:{ all -> 0x00fa }
            r14 = r22
            r11 = r1
            r10 = r2
            r0 = r18
            r13 = 1
            r2 = r14
            r20 = r4
            r4 = r3
            r3 = r9
            r9 = r8
            r8 = r7
            r7 = r5
            r5 = r20
            goto L_0x013e
        L_0x00fa:
            r0 = move-exception
            r11 = r1
            r8 = r2
            r5 = r6
            r2 = r9
            r1 = r22
            goto L_0x01e5
        L_0x0103:
            r18 = r0
            kotlin.ResultKt.throwOnFailure(r22)
            kotlinx.coroutines.channels.ProducerScope r2 = r1.f638p$
            kotlinx.coroutines.channels.ReceiveChannel r5 = r1.$this_map
            r7 = 0
            r8 = r5
            r15 = 0
            r12 = r6
            java.lang.Throwable r12 = (java.lang.Throwable) r12
            r0 = r8
            r3 = 0
            kotlinx.coroutines.channels.ChannelIterator r4 = r0.iterator()     // Catch:{ all -> 0x01e0 }
            r11 = r1
            r9 = r4
            r6 = r5
            r10 = r8
            r5 = r0
            r4 = r11
            r8 = r7
            r0 = r18
            r7 = r3
            r3 = r2
            r2 = r22
        L_0x0126:
            r11.L$0 = r3     // Catch:{ all -> 0x01d9 }
            r11.L$1 = r6     // Catch:{ all -> 0x01d9 }
            r11.L$2 = r4     // Catch:{ all -> 0x01d9 }
            r11.L$3 = r10     // Catch:{ all -> 0x01d9 }
            r11.L$4 = r12     // Catch:{ all -> 0x01d9 }
            r11.L$5 = r5     // Catch:{ all -> 0x01d9 }
            r11.L$6 = r9     // Catch:{ all -> 0x01d9 }
            r13 = 1
            r11.label = r13     // Catch:{ all -> 0x01d9 }
            java.lang.Object r14 = r9.hasNext(r4)     // Catch:{ all -> 0x01d9 }
            if (r14 != r0) goto L_0x013e
            return r0
        L_0x013e:
            java.lang.Boolean r14 = (java.lang.Boolean) r14     // Catch:{ all -> 0x01cf }
            boolean r14 = r14.booleanValue()     // Catch:{ all -> 0x01cf }
            if (r14 == 0) goto L_0x01bc
            java.lang.Object r14 = r9.next()     // Catch:{ all -> 0x01cf }
            r22 = r14
            r17 = 0
            kotlin.jvm.functions.Function2 r13 = r11.$transform     // Catch:{ all -> 0x01cf }
            r11.L$0 = r3     // Catch:{ all -> 0x01cf }
            r11.L$1 = r6     // Catch:{ all -> 0x01cf }
            r11.L$2 = r4     // Catch:{ all -> 0x01cf }
            r11.L$3 = r10     // Catch:{ all -> 0x01cf }
            r11.L$4 = r12     // Catch:{ all -> 0x01cf }
            r11.L$5 = r5     // Catch:{ all -> 0x01cf }
            r11.L$6 = r9     // Catch:{ all -> 0x01cf }
            r11.L$7 = r14     // Catch:{ all -> 0x01cf }
            r1 = r22
            r11.L$8 = r1     // Catch:{ all -> 0x01cf }
            r11.L$9 = r3     // Catch:{ all -> 0x01cf }
            r22 = r2
            r2 = 2
            r11.label = r2     // Catch:{ all -> 0x01c7 }
            java.lang.Object r13 = r13.invoke(r1, r11)     // Catch:{ all -> 0x01c7 }
            if (r13 != r0) goto L_0x0172
            return r0
        L_0x0172:
            r16 = r7
            r7 = r8
            r8 = r10
            r10 = r14
            r19 = r15
            r15 = r4
            r4 = r5
            r5 = r6
            r14 = r12
            r6 = r1
            r12 = r3
            r1 = r22
        L_0x0181:
            r11.L$0 = r3     // Catch:{ all -> 0x01b6 }
            r11.L$1 = r5     // Catch:{ all -> 0x01b6 }
            r11.L$2 = r15     // Catch:{ all -> 0x01b6 }
            r11.L$3 = r8     // Catch:{ all -> 0x01b6 }
            r11.L$4 = r14     // Catch:{ all -> 0x01b6 }
            r11.L$5 = r4     // Catch:{ all -> 0x01b6 }
            r11.L$6 = r9     // Catch:{ all -> 0x01b6 }
            r11.L$7 = r10     // Catch:{ all -> 0x01b6 }
            r11.L$8 = r6     // Catch:{ all -> 0x01b6 }
            r2 = 3
            r11.label = r2     // Catch:{ all -> 0x01b6 }
            java.lang.Object r12 = r12.send(r13, r11)     // Catch:{ all -> 0x01b6 }
            if (r12 != r0) goto L_0x019d
            return r0
        L_0x019d:
            r13 = r6
            r12 = r8
            r6 = r5
            r8 = r7
            r7 = r16
            r5 = r4
            r4 = r15
            r15 = r14
            r14 = r11
            r11 = r10
            r10 = r9
            r9 = r17
        L_0x01ab:
            r2 = r1
            r9 = r10
            r10 = r12
            r11 = r14
            r12 = r15
            r15 = r19
            r1 = r21
            goto L_0x0126
        L_0x01b6:
            r0 = move-exception
            r2 = r3
            r12 = r14
            r15 = r19
            goto L_0x01e5
        L_0x01bc:
            r22 = r2
            kotlin.Unit r0 = kotlin.Unit.INSTANCE     // Catch:{ all -> 0x01c7 }
            kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r10, r12)
            kotlin.Unit r0 = kotlin.Unit.INSTANCE
            return r0
        L_0x01c7:
            r0 = move-exception
            r1 = r22
            r2 = r3
            r5 = r6
            r7 = r8
            r8 = r10
            goto L_0x01e5
        L_0x01cf:
            r0 = move-exception
            r22 = r2
            r1 = r22
            r2 = r3
            r5 = r6
            r7 = r8
            r8 = r10
            goto L_0x01e5
        L_0x01d9:
            r0 = move-exception
            r1 = r2
            r2 = r3
            r5 = r6
            r7 = r8
            r8 = r10
            goto L_0x01e5
        L_0x01e0:
            r0 = move-exception
            r11 = r21
            r1 = r22
        L_0x01e5:
            r3 = r0
            throw r0     // Catch:{ all -> 0x01e8 }
        L_0x01e8:
            r0 = move-exception
            r4 = r0
            kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r8, r3)
            throw r4
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$map$1.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
