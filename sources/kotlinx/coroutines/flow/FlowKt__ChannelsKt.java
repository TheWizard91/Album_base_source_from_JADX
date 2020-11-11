package kotlinx.coroutines.flow;

import kotlin.Metadata;
import kotlin.coroutines.CoroutineContext;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineStart;
import kotlinx.coroutines.channels.BroadcastChannel;
import kotlinx.coroutines.channels.ReceiveChannel;
import kotlinx.coroutines.flow.internal.ChannelFlowKt;

@Metadata(mo33669bv = {1, 0, 3}, mo33670d1 = {"\u0000.\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\u001a\u001e\u0010\u0000\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u0003H\u0007\u001a0\u0010\u0004\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0003\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u00012\u0006\u0010\u0005\u001a\u00020\u00062\b\b\u0002\u0010\u0007\u001a\u00020\bH\u0007\u001a\u001e\u0010\t\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\nH\u0007\u001a/\u0010\u000b\u001a\u00020\f\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\r2\f\u0010\u000e\u001a\b\u0012\u0004\u0012\u0002H\u00020\nH@ø\u0001\u0000¢\u0006\u0002\u0010\u000f\u001a&\u0010\u0010\u001a\b\u0012\u0004\u0012\u0002H\u00020\n\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u00012\u0006\u0010\u0005\u001a\u00020\u0006H\u0007\u0002\u0004\n\u0002\b\u0019¨\u0006\u0011"}, mo33671d2 = {"asFlow", "Lkotlinx/coroutines/flow/Flow;", "T", "Lkotlinx/coroutines/channels/BroadcastChannel;", "broadcastIn", "scope", "Lkotlinx/coroutines/CoroutineScope;", "start", "Lkotlinx/coroutines/CoroutineStart;", "consumeAsFlow", "Lkotlinx/coroutines/channels/ReceiveChannel;", "emitAll", "", "Lkotlinx/coroutines/flow/FlowCollector;", "channel", "(Lkotlinx/coroutines/flow/FlowCollector;Lkotlinx/coroutines/channels/ReceiveChannel;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "produceIn", "kotlinx-coroutines-core"}, mo33672k = 5, mo33673mv = {1, 1, 15}, mo33676xs = "kotlinx/coroutines/flow/FlowKt")
/* compiled from: Channels.kt */
final /* synthetic */ class FlowKt__ChannelsKt {
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r8v3, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r6v9, resolved type: java.lang.Throwable} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r8v4, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r11v5, resolved type: kotlinx.coroutines.channels.ReceiveChannel<? extends T>} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r8v5, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r10v3, resolved type: kotlinx.coroutines.flow.FlowCollector<? super T>} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r7v5, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r6v12, resolved type: java.lang.Throwable} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r7v6, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r11v7, resolved type: kotlinx.coroutines.channels.ReceiveChannel<? extends T>} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r7v7, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r10v5, resolved type: kotlinx.coroutines.flow.FlowCollector<? super T>} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:20:0x006b  */
    /* JADX WARNING: Removed duplicated region for block: B:26:0x0086  */
    /* JADX WARNING: Removed duplicated region for block: B:32:0x00a0  */
    /* JADX WARNING: Removed duplicated region for block: B:34:0x00a8  */
    /* JADX WARNING: Removed duplicated region for block: B:37:0x00aa A[Catch:{ all -> 0x00c5 }] */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x0026  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static final <T> java.lang.Object emitAll(kotlinx.coroutines.flow.FlowCollector<? super T> r10, kotlinx.coroutines.channels.ReceiveChannel<? extends T> r11, kotlin.coroutines.Continuation<? super kotlin.Unit> r12) {
        /*
            boolean r0 = r12 instanceof kotlinx.coroutines.flow.FlowKt__ChannelsKt$emitAll$1
            if (r0 == 0) goto L_0x0014
            r0 = r12
            kotlinx.coroutines.flow.FlowKt__ChannelsKt$emitAll$1 r0 = (kotlinx.coroutines.flow.FlowKt__ChannelsKt$emitAll$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r1 = r1 & r2
            if (r1 == 0) goto L_0x0014
            int r1 = r0.label
            int r1 = r1 - r2
            r0.label = r1
            goto L_0x0019
        L_0x0014:
            kotlinx.coroutines.flow.FlowKt__ChannelsKt$emitAll$1 r0 = new kotlinx.coroutines.flow.FlowKt__ChannelsKt$emitAll$1
            r0.<init>(r12)
        L_0x0019:
            java.lang.Object r1 = r0.result
            java.lang.Object r2 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r3 = r0.label
            r4 = 2
            r5 = 1
            r6 = 0
            if (r3 == 0) goto L_0x006b
            if (r3 == r5) goto L_0x0049
            if (r3 != r4) goto L_0x0041
            r3 = r6
            java.lang.Object r3 = r0.L$3
            java.lang.Object r7 = r0.L$2
            r6 = r7
            java.lang.Throwable r6 = (java.lang.Throwable) r6
            java.lang.Object r7 = r0.L$1
            r11 = r7
            kotlinx.coroutines.channels.ReceiveChannel r11 = (kotlinx.coroutines.channels.ReceiveChannel) r11
            java.lang.Object r7 = r0.L$0
            r10 = r7
            kotlinx.coroutines.flow.FlowCollector r10 = (kotlinx.coroutines.flow.FlowCollector) r10
            kotlin.ResultKt.throwOnFailure(r1)     // Catch:{ all -> 0x0068 }
            goto L_0x00c4
        L_0x0041:
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
            java.lang.String r1 = "call to 'resume' before 'invoke' with coroutine"
            r0.<init>(r1)
            throw r0
        L_0x0049:
            r3 = r6
            r7 = 0
            java.lang.Object r8 = r0.L$3
            r3 = r8
            kotlinx.coroutines.flow.FlowCollector r3 = (kotlinx.coroutines.flow.FlowCollector) r3
            java.lang.Object r8 = r0.L$2
            r6 = r8
            java.lang.Throwable r6 = (java.lang.Throwable) r6
            java.lang.Object r8 = r0.L$1
            r11 = r8
            kotlinx.coroutines.channels.ReceiveChannel r11 = (kotlinx.coroutines.channels.ReceiveChannel) r11
            java.lang.Object r8 = r0.L$0
            r10 = r8
            kotlinx.coroutines.flow.FlowCollector r10 = (kotlinx.coroutines.flow.FlowCollector) r10
            kotlin.ResultKt.throwOnFailure(r1)     // Catch:{ all -> 0x0068 }
            r8 = r7
            r7 = r6
            r6 = r3
            r3 = r2
            r2 = r1
            goto L_0x008d
        L_0x0068:
            r2 = move-exception
            goto L_0x00ca
        L_0x006b:
            kotlin.ResultKt.throwOnFailure(r1)
            r3 = r6
            java.lang.Throwable r3 = (java.lang.Throwable) r3
            r6 = r3
        L_0x0072:
            r3 = r10
            r7 = 0
            r0.L$0 = r10     // Catch:{ all -> 0x0068 }
            r0.L$1 = r11     // Catch:{ all -> 0x0068 }
            r0.L$2 = r6     // Catch:{ all -> 0x0068 }
            r0.L$3 = r3     // Catch:{ all -> 0x0068 }
            r0.label = r5     // Catch:{ all -> 0x0068 }
            java.lang.Object r8 = r11.receiveOrClosed(r0)     // Catch:{ all -> 0x0068 }
            if (r8 != r2) goto L_0x0086
            return r2
        L_0x0086:
            r9 = r2
            r2 = r1
            r1 = r8
            r8 = r7
            r7 = r6
            r6 = r3
            r3 = r9
        L_0x008d:
            kotlinx.coroutines.channels.ValueOrClosed r1 = (kotlinx.coroutines.channels.ValueOrClosed) r1     // Catch:{ all -> 0x00c5 }
            java.lang.Object r1 = r1.m2307unboximpl()     // Catch:{ all -> 0x00c5 }
            boolean r6 = kotlinx.coroutines.channels.ValueOrClosed.m2305isClosedimpl(r1)     // Catch:{ all -> 0x00c5 }
            if (r6 == 0) goto L_0x00aa
            java.lang.Throwable r3 = kotlinx.coroutines.channels.ValueOrClosed.m2301getCloseCauseimpl(r1)     // Catch:{ all -> 0x00c5 }
            if (r3 != 0) goto L_0x00a8
            kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r11, r7)
            kotlin.Unit r1 = kotlin.Unit.INSTANCE
            return r1
        L_0x00a8:
            r4 = 0
            throw r3     // Catch:{ all -> 0x00c5 }
        L_0x00aa:
            java.lang.Object r6 = kotlinx.coroutines.channels.ValueOrClosed.m2302getValueimpl(r1)     // Catch:{ all -> 0x00c5 }
            r0.L$0 = r10     // Catch:{ all -> 0x00c5 }
            r0.L$1 = r11     // Catch:{ all -> 0x00c5 }
            r0.L$2 = r7     // Catch:{ all -> 0x00c5 }
            r0.L$3 = r1     // Catch:{ all -> 0x00c5 }
            r0.label = r4     // Catch:{ all -> 0x00c5 }
            java.lang.Object r6 = r10.emit(r6, r0)     // Catch:{ all -> 0x00c5 }
            if (r6 != r3) goto L_0x00bf
            return r3
        L_0x00bf:
            r6 = r7
            r9 = r3
            r3 = r1
            r1 = r2
            r2 = r9
        L_0x00c4:
            goto L_0x0072
        L_0x00c5:
            r1 = move-exception
            r6 = r7
            r9 = r2
            r2 = r1
            r1 = r9
        L_0x00ca:
            r3 = r2
            throw r2     // Catch:{ all -> 0x00cd }
        L_0x00cd:
            r2 = move-exception
            kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r11, r3)
            throw r2
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.flow.FlowKt__ChannelsKt.emitAll(kotlinx.coroutines.flow.FlowCollector, kotlinx.coroutines.channels.ReceiveChannel, kotlin.coroutines.Continuation):java.lang.Object");
    }

    public static final <T> Flow<T> consumeAsFlow(ReceiveChannel<? extends T> $this$consumeAsFlow) {
        Intrinsics.checkParameterIsNotNull($this$consumeAsFlow, "$this$consumeAsFlow");
        return new ConsumeAsFlow<>($this$consumeAsFlow, (CoroutineContext) null, 0, 6, (DefaultConstructorMarker) null);
    }

    public static final <T> Flow<T> asFlow(BroadcastChannel<T> $this$asFlow) {
        Intrinsics.checkParameterIsNotNull($this$asFlow, "$this$asFlow");
        return new FlowKt__ChannelsKt$asFlow$$inlined$unsafeFlow$1($this$asFlow);
    }

    public static /* synthetic */ BroadcastChannel broadcastIn$default(Flow flow, CoroutineScope coroutineScope, CoroutineStart coroutineStart, int i, Object obj) {
        if ((i & 2) != 0) {
            coroutineStart = CoroutineStart.LAZY;
        }
        return FlowKt.broadcastIn(flow, coroutineScope, coroutineStart);
    }

    public static final <T> BroadcastChannel<T> broadcastIn(Flow<? extends T> $this$broadcastIn, CoroutineScope scope, CoroutineStart start) {
        Intrinsics.checkParameterIsNotNull($this$broadcastIn, "$this$broadcastIn");
        Intrinsics.checkParameterIsNotNull(scope, "scope");
        Intrinsics.checkParameterIsNotNull(start, "start");
        return ChannelFlowKt.asChannelFlow($this$broadcastIn).broadcastImpl(scope, start);
    }

    public static final <T> ReceiveChannel<T> produceIn(Flow<? extends T> $this$produceIn, CoroutineScope scope) {
        Intrinsics.checkParameterIsNotNull($this$produceIn, "$this$produceIn");
        Intrinsics.checkParameterIsNotNull(scope, "scope");
        return ChannelFlowKt.asChannelFlow($this$produceIn).produceImpl(scope);
    }
}
