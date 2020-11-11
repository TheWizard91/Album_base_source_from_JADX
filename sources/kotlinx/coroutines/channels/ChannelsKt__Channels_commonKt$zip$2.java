package kotlinx.coroutines.channels;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;

@Metadata(mo33669bv = {1, 0, 3}, mo33670d1 = {"\u0000\u0012\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\u0010\u0000\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003\"\u0004\b\u0002\u0010\u0004*\b\u0012\u0004\u0012\u0002H\u00040\u0005H@¢\u0006\u0004\b\u0006\u0010\u0007"}, mo33671d2 = {"<anonymous>", "", "E", "R", "V", "Lkotlinx/coroutines/channels/ProducerScope;", "invoke", "(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;"}, mo33672k = 3, mo33673mv = {1, 1, 15})
@DebugMetadata(mo34304c = "kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$zip$2", mo34305f = "Channels.common.kt", mo34306i = {0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 2, 2, 2, 2, 2, 2, 2, 2, 2}, mo34307l = {2201, 2191, 2193}, mo34308m = "invokeSuspend", mo34309n = {"$this$produce", "otherIterator", "$this$consumeEach$iv", "$this$consume$iv$iv", "cause$iv$iv", "$this$consume$iv", "$this$produce", "otherIterator", "$this$consumeEach$iv", "$this$consume$iv$iv", "cause$iv$iv", "$this$consume$iv", "e$iv", "element1", "$this$produce", "otherIterator", "$this$consumeEach$iv", "$this$consume$iv$iv", "cause$iv$iv", "$this$consume$iv", "e$iv", "element1", "element2"}, mo34310s = {"L$0", "L$1", "L$2", "L$4", "L$5", "L$6", "L$0", "L$1", "L$2", "L$4", "L$5", "L$6", "L$8", "L$9", "L$0", "L$1", "L$2", "L$4", "L$5", "L$6", "L$8", "L$9", "L$10"})
/* compiled from: Channels.common.kt */
final class ChannelsKt__Channels_commonKt$zip$2 extends SuspendLambda implements Function2<ProducerScope<? super V>, Continuation<? super Unit>, Object> {
    final /* synthetic */ ReceiveChannel $other;
    final /* synthetic */ ReceiveChannel $this_zip;
    final /* synthetic */ Function2 $transform;
    Object L$0;
    Object L$1;
    Object L$10;
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
    private ProducerScope f644p$;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    ChannelsKt__Channels_commonKt$zip$2(ReceiveChannel receiveChannel, ReceiveChannel receiveChannel2, Function2 function2, Continuation continuation) {
        super(2, continuation);
        this.$this_zip = receiveChannel;
        this.$other = receiveChannel2;
        this.$transform = function2;
    }

    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        Intrinsics.checkParameterIsNotNull(continuation, "completion");
        ChannelsKt__Channels_commonKt$zip$2 channelsKt__Channels_commonKt$zip$2 = new ChannelsKt__Channels_commonKt$zip$2(this.$this_zip, this.$other, this.$transform, continuation);
        ProducerScope producerScope = (ProducerScope) obj;
        channelsKt__Channels_commonKt$zip$2.f644p$ = (ProducerScope) obj;
        return channelsKt__Channels_commonKt$zip$2;
    }

    public final Object invoke(Object obj, Object obj2) {
        return ((ChannelsKt__Channels_commonKt$zip$2) create(obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* Debug info: failed to restart local var, previous not found, register: 23 */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r10v8, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r5v11, resolved type: kotlinx.coroutines.channels.ReceiveChannel} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r10v9, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r14v11, resolved type: java.lang.Throwable} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v12, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r15v12, resolved type: kotlinx.coroutines.channels.ReceiveChannel} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r6v10, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v16, resolved type: kotlinx.coroutines.channels.ReceiveChannel} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r6v11, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r16v11, resolved type: kotlinx.coroutines.channels.ChannelIterator} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r14v13, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r10v12, resolved type: kotlinx.coroutines.channels.ReceiveChannel} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r9v16, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r15v15, resolved type: kotlinx.coroutines.channels.ReceiveChannel} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v23, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r16v13, resolved type: kotlinx.coroutines.channels.ChannelIterator} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r15v19, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r14v18, resolved type: java.lang.Throwable} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v26, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r9v21, resolved type: kotlinx.coroutines.channels.ReceiveChannel} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v27, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r16v16, resolved type: kotlinx.coroutines.channels.ChannelIterator} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v28, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v29, resolved type: kotlinx.coroutines.channels.ProducerScope} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:34:0x014e A[Catch:{ all -> 0x0224 }] */
    /* JADX WARNING: Removed duplicated region for block: B:48:0x01d8  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final java.lang.Object invokeSuspend(java.lang.Object r24) {
        /*
            r23 = this;
            r1 = r23
            java.lang.Object r0 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r2 = r1.label
            r3 = 3
            r4 = 2
            r5 = 1
            r6 = 0
            if (r2 == 0) goto L_0x0103
            r7 = 0
            if (r2 == r5) goto L_0x00b7
            if (r2 == r4) goto L_0x0067
            if (r2 != r3) goto L_0x005f
            r2 = r6
            r8 = r7
            r9 = r6
            r10 = r7
            r11 = r6
            r12 = r6
            r13 = r7
            r14 = r6
            r15 = r6
            r16 = r6
            r17 = r6
            java.lang.Object r6 = r1.L$10
            java.lang.Object r11 = r1.L$9
            java.lang.Object r12 = r1.L$8
            java.lang.Object r3 = r1.L$7
            kotlinx.coroutines.channels.ChannelIterator r3 = (kotlinx.coroutines.channels.ChannelIterator) r3
            java.lang.Object r4 = r1.L$6
            kotlinx.coroutines.channels.ReceiveChannel r4 = (kotlinx.coroutines.channels.ReceiveChannel) r4
            java.lang.Object r15 = r1.L$5
            r14 = r15
            java.lang.Throwable r14 = (java.lang.Throwable) r14
            java.lang.Object r15 = r1.L$4
            kotlinx.coroutines.channels.ReceiveChannel r15 = (kotlinx.coroutines.channels.ReceiveChannel) r15
            java.lang.Object r5 = r1.L$3
            kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$zip$2 r5 = (kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$zip$2) r5
            r19 = r0
            java.lang.Object r0 = r1.L$2
            r9 = r0
            kotlinx.coroutines.channels.ReceiveChannel r9 = (kotlinx.coroutines.channels.ReceiveChannel) r9
            java.lang.Object r0 = r1.L$1
            r16 = r0
            kotlinx.coroutines.channels.ChannelIterator r16 = (kotlinx.coroutines.channels.ChannelIterator) r16
            java.lang.Object r0 = r1.L$0
            r2 = r0
            kotlinx.coroutines.channels.ProducerScope r2 = (kotlinx.coroutines.channels.ProducerScope) r2
            kotlin.ResultKt.throwOnFailure(r24)     // Catch:{ all -> 0x00b0 }
            r0 = r24
            r18 = r16
            r16 = r15
            r15 = r14
            r14 = r13
            r13 = r12
            r12 = r1
            r1 = 3
            goto L_0x01eb
        L_0x005f:
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
            java.lang.String r2 = "call to 'resume' before 'invoke' with coroutine"
            r0.<init>(r2)
            throw r0
        L_0x0067:
            r19 = r0
            r0 = r6
            r8 = r7
            r2 = r6
            r3 = r7
            r4 = r6
            r5 = r6
            r13 = r7
            r9 = r6
            r10 = r6
            r11 = r6
            java.lang.Object r4 = r1.L$9
            java.lang.Object r5 = r1.L$8
            java.lang.Object r12 = r1.L$7
            kotlinx.coroutines.channels.ChannelIterator r12 = (kotlinx.coroutines.channels.ChannelIterator) r12
            java.lang.Object r14 = r1.L$6
            r10 = r14
            kotlinx.coroutines.channels.ReceiveChannel r10 = (kotlinx.coroutines.channels.ReceiveChannel) r10
            java.lang.Object r14 = r1.L$5
            java.lang.Throwable r14 = (java.lang.Throwable) r14
            java.lang.Object r9 = r1.L$4
            r15 = r9
            kotlinx.coroutines.channels.ReceiveChannel r15 = (kotlinx.coroutines.channels.ReceiveChannel) r15
            java.lang.Object r6 = r1.L$3
            kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$zip$2 r6 = (kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$zip$2) r6
            java.lang.Object r9 = r1.L$2
            kotlinx.coroutines.channels.ReceiveChannel r9 = (kotlinx.coroutines.channels.ReceiveChannel) r9
            java.lang.Object r2 = r1.L$1
            r16 = r2
            kotlinx.coroutines.channels.ChannelIterator r16 = (kotlinx.coroutines.channels.ChannelIterator) r16
            java.lang.Object r2 = r1.L$0
            kotlinx.coroutines.channels.ProducerScope r2 = (kotlinx.coroutines.channels.ProducerScope) r2
            kotlin.ResultKt.throwOnFailure(r24)     // Catch:{ all -> 0x00b0 }
            r11 = r4
            r4 = r10
            r0 = r19
            r10 = r3
            r3 = r2
            r2 = r15
            r15 = r14
            r14 = r13
            r13 = r5
            r5 = r6
            r6 = r9
            r9 = r12
            r12 = r1
            r1 = r24
            goto L_0x018e
        L_0x00b0:
            r0 = move-exception
            r11 = r1
            r3 = r2
            r2 = r24
            goto L_0x022f
        L_0x00b7:
            r19 = r0
            r0 = r6
            r8 = r7
            r2 = r6
            r13 = r7
            r3 = r6
            r4 = r7
            r5 = r6
            r7 = r6
            java.lang.Object r9 = r1.L$7
            kotlinx.coroutines.channels.ChannelIterator r9 = (kotlinx.coroutines.channels.ChannelIterator) r9
            java.lang.Object r10 = r1.L$6
            r5 = r10
            kotlinx.coroutines.channels.ReceiveChannel r5 = (kotlinx.coroutines.channels.ReceiveChannel) r5
            java.lang.Object r10 = r1.L$5
            r14 = r10
            java.lang.Throwable r14 = (java.lang.Throwable) r14
            java.lang.Object r3 = r1.L$4
            r15 = r3
            kotlinx.coroutines.channels.ReceiveChannel r15 = (kotlinx.coroutines.channels.ReceiveChannel) r15
            java.lang.Object r3 = r1.L$3
            kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$zip$2 r3 = (kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$zip$2) r3
            java.lang.Object r6 = r1.L$2
            r2 = r6
            kotlinx.coroutines.channels.ReceiveChannel r2 = (kotlinx.coroutines.channels.ReceiveChannel) r2
            java.lang.Object r6 = r1.L$1
            r16 = r6
            kotlinx.coroutines.channels.ChannelIterator r16 = (kotlinx.coroutines.channels.ChannelIterator) r16
            java.lang.Object r6 = r1.L$0
            kotlinx.coroutines.channels.ProducerScope r6 = (kotlinx.coroutines.channels.ProducerScope) r6
            kotlin.ResultKt.throwOnFailure(r24)     // Catch:{ all -> 0x00fb }
            r12 = r24
            r11 = r1
            r7 = r2
            r10 = r16
            r0 = r19
            r2 = r12
            r21 = r4
            r4 = r3
            r3 = r6
            r6 = r5
            r5 = r21
            goto L_0x0146
        L_0x00fb:
            r0 = move-exception
            r11 = r1
            r9 = r2
            r3 = r6
            r2 = r24
            goto L_0x022f
        L_0x0103:
            r19 = r0
            kotlin.ResultKt.throwOnFailure(r24)
            kotlinx.coroutines.channels.ProducerScope r2 = r1.f644p$
            kotlinx.coroutines.channels.ReceiveChannel r0 = r1.$other
            kotlinx.coroutines.channels.ChannelIterator r16 = r0.iterator()
            kotlinx.coroutines.channels.ReceiveChannel r9 = r1.$this_zip
            r8 = 0
            r15 = r9
            r13 = 0
            r14 = r6
            java.lang.Throwable r14 = (java.lang.Throwable) r14
            r0 = r15
            r3 = 0
            kotlinx.coroutines.channels.ChannelIterator r4 = r0.iterator()     // Catch:{ all -> 0x0229 }
            r6 = r0
            r11 = r1
            r5 = r3
            r7 = r9
            r10 = r16
            r0 = r19
            r3 = r2
            r9 = r4
            r2 = r24
            r4 = r11
        L_0x012c:
            r11.L$0 = r3     // Catch:{ all -> 0x0224 }
            r11.L$1 = r10     // Catch:{ all -> 0x0224 }
            r11.L$2 = r7     // Catch:{ all -> 0x0224 }
            r11.L$3 = r4     // Catch:{ all -> 0x0224 }
            r11.L$4 = r15     // Catch:{ all -> 0x0224 }
            r11.L$5 = r14     // Catch:{ all -> 0x0224 }
            r11.L$6 = r6     // Catch:{ all -> 0x0224 }
            r11.L$7 = r9     // Catch:{ all -> 0x0224 }
            r12 = 1
            r11.label = r12     // Catch:{ all -> 0x0224 }
            java.lang.Object r12 = r9.hasNext(r4)     // Catch:{ all -> 0x0224 }
            if (r12 != r0) goto L_0x0146
            return r0
        L_0x0146:
            java.lang.Boolean r12 = (java.lang.Boolean) r12     // Catch:{ all -> 0x0224 }
            boolean r12 = r12.booleanValue()     // Catch:{ all -> 0x0224 }
            if (r12 == 0) goto L_0x021b
            java.lang.Object r12 = r9.next()     // Catch:{ all -> 0x0224 }
            r24 = r12
            r16 = 0
            r11.L$0 = r3     // Catch:{ all -> 0x0224 }
            r11.L$1 = r10     // Catch:{ all -> 0x0224 }
            r11.L$2 = r7     // Catch:{ all -> 0x0224 }
            r11.L$3 = r4     // Catch:{ all -> 0x0224 }
            r11.L$4 = r15     // Catch:{ all -> 0x0224 }
            r11.L$5 = r14     // Catch:{ all -> 0x0224 }
            r11.L$6 = r6     // Catch:{ all -> 0x0224 }
            r11.L$7 = r9     // Catch:{ all -> 0x0224 }
            r11.L$8 = r12     // Catch:{ all -> 0x0224 }
            r1 = r24
            r11.L$9 = r1     // Catch:{ all -> 0x0224 }
            r19 = r1
            r1 = 2
            r11.label = r1     // Catch:{ all -> 0x0224 }
            java.lang.Object r1 = r10.hasNext(r11)     // Catch:{ all -> 0x0224 }
            if (r1 != r0) goto L_0x0178
            return r0
        L_0x0178:
            r24 = r2
            r2 = r15
            r15 = r14
            r14 = r13
            r13 = r12
            r12 = r11
            r11 = r19
            r21 = r5
            r5 = r4
            r4 = r6
            r6 = r7
            r7 = r21
            r22 = r16
            r16 = r10
            r10 = r22
        L_0x018e:
            java.lang.Boolean r1 = (java.lang.Boolean) r1     // Catch:{ all -> 0x020c }
            boolean r1 = r1.booleanValue()     // Catch:{ all -> 0x020c }
            if (r1 != 0) goto L_0x01a6
            r11 = r12
            r10 = r16
            r1 = 3
            r16 = r2
            r2 = r24
            r21 = r6
            r6 = r4
            r4 = r5
            r5 = r7
            r7 = r21
            goto L_0x01f7
        L_0x01a6:
            java.lang.Object r1 = r16.next()     // Catch:{ all -> 0x020c }
            r19 = r7
            kotlin.jvm.functions.Function2 r7 = r12.$transform     // Catch:{ all -> 0x020c }
            java.lang.Object r7 = r7.invoke(r11, r1)     // Catch:{ all -> 0x020c }
            r12.L$0 = r3     // Catch:{ all -> 0x020c }
            r20 = r8
            r8 = r16
            r12.L$1 = r8     // Catch:{ all -> 0x01ff }
            r12.L$2 = r6     // Catch:{ all -> 0x01ff }
            r12.L$3 = r5     // Catch:{ all -> 0x01ff }
            r12.L$4 = r2     // Catch:{ all -> 0x01ff }
            r12.L$5 = r15     // Catch:{ all -> 0x01ff }
            r12.L$6 = r4     // Catch:{ all -> 0x01ff }
            r12.L$7 = r9     // Catch:{ all -> 0x01ff }
            r12.L$8 = r13     // Catch:{ all -> 0x01ff }
            r12.L$9 = r11     // Catch:{ all -> 0x01ff }
            r12.L$10 = r1     // Catch:{ all -> 0x01ff }
            r16 = r1
            r1 = 3
            r12.label = r1     // Catch:{ all -> 0x01ff }
            java.lang.Object r7 = r3.send(r7, r12)     // Catch:{ all -> 0x01ff }
            if (r7 != r0) goto L_0x01d8
            return r0
        L_0x01d8:
            r18 = r8
            r7 = r19
            r8 = r20
            r19 = r0
            r0 = r24
            r21 = r16
            r16 = r2
            r2 = r3
            r3 = r9
            r9 = r6
            r6 = r21
        L_0x01eb:
            r6 = r4
            r4 = r5
            r5 = r7
            r7 = r9
            r11 = r12
            r10 = r18
            r9 = r3
            r3 = r2
            r2 = r0
            r0 = r19
        L_0x01f7:
            r1 = r23
            r13 = r14
            r14 = r15
            r15 = r16
            goto L_0x012c
        L_0x01ff:
            r0 = move-exception
            r9 = r6
            r16 = r8
            r11 = r12
            r13 = r14
            r14 = r15
            r8 = r20
            r15 = r2
            r2 = r24
            goto L_0x022f
        L_0x020c:
            r0 = move-exception
            r20 = r8
            r8 = r16
            r9 = r6
            r11 = r12
            r13 = r14
            r14 = r15
            r8 = r20
            r15 = r2
            r2 = r24
            goto L_0x022f
        L_0x021b:
            kotlin.Unit r0 = kotlin.Unit.INSTANCE     // Catch:{ all -> 0x0224 }
            kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r15, r14)
            kotlin.Unit r0 = kotlin.Unit.INSTANCE
            return r0
        L_0x0224:
            r0 = move-exception
            r9 = r7
            r16 = r10
            goto L_0x022f
        L_0x0229:
            r0 = move-exception
            r11 = r23
            r3 = r2
            r2 = r24
        L_0x022f:
            r1 = r0
            throw r0     // Catch:{ all -> 0x0232 }
        L_0x0232:
            r0 = move-exception
            r4 = r0
            kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r15, r1)
            throw r4
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$zip$2.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
