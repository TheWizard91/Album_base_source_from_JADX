package kotlinx.coroutines.flow.internal;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.FlowCollector;

@Metadata(mo33669bv = {1, 0, 3}, mo33670d1 = {"\u0000\u001e\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\b\u0003\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\b\u0003\u0010\u0000\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003\"\u0004\b\u0002\u0010\u0004*\u00020\u0005H@¢\u0006\u0004\b\u0006\u0010\u0007¨\u0006\b"}, mo33671d2 = {"<anonymous>", "", "T1", "T2", "R", "Lkotlinx/coroutines/CoroutineScope;", "invoke", "(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;", "kotlinx/coroutines/flow/internal/CombineKt$zipImpl$1$1"}, mo33672k = 3, mo33673mv = {1, 1, 15})
/* compiled from: Combine.kt */
final class CombineKt$zipImpl$$inlined$unsafeFlow$1$lambda$1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
    final /* synthetic */ FlowCollector $this_unsafeFlow;
    Object L$0;
    Object L$1;
    Object L$10;
    Object L$11;
    Object L$12;
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
    private CoroutineScope f698p$;
    final /* synthetic */ CombineKt$zipImpl$$inlined$unsafeFlow$1 this$0;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    CombineKt$zipImpl$$inlined$unsafeFlow$1$lambda$1(FlowCollector flowCollector, Continuation continuation, CombineKt$zipImpl$$inlined$unsafeFlow$1 combineKt$zipImpl$$inlined$unsafeFlow$1) {
        super(2, continuation);
        this.$this_unsafeFlow = flowCollector;
        this.this$0 = combineKt$zipImpl$$inlined$unsafeFlow$1;
    }

    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        Intrinsics.checkParameterIsNotNull(continuation, "completion");
        CombineKt$zipImpl$$inlined$unsafeFlow$1$lambda$1 combineKt$zipImpl$$inlined$unsafeFlow$1$lambda$1 = new CombineKt$zipImpl$$inlined$unsafeFlow$1$lambda$1(this.$this_unsafeFlow, continuation, this.this$0);
        CoroutineScope coroutineScope = (CoroutineScope) obj;
        combineKt$zipImpl$$inlined$unsafeFlow$1$lambda$1.f698p$ = (CoroutineScope) obj;
        return combineKt$zipImpl$$inlined$unsafeFlow$1$lambda$1;
    }

    public final Object invoke(Object obj, Object obj2) {
        return ((CombineKt$zipImpl$$inlined$unsafeFlow$1$lambda$1) create(obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r11v7, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r6v17, resolved type: kotlinx.coroutines.channels.ReceiveChannel} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r5v26, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r12v16, resolved type: kotlinx.coroutines.channels.ReceiveChannel} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r9v9, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r18v19, resolved type: kotlinx.coroutines.channels.ReceiveChannel} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v20, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r16v17, resolved type: kotlinx.coroutines.CoroutineScope} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r6v22, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r5v33, resolved type: java.lang.Throwable} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v68, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r10v15, resolved type: kotlinx.coroutines.channels.ReceiveChannel} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v70, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r11v13, resolved type: kotlinx.coroutines.channels.ReceiveChannel} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v71, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v24, resolved type: kotlinx.coroutines.channels.ReceiveChannel} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v72, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r9v15, resolved type: kotlinx.coroutines.CoroutineScope} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r6v27, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r5v37, resolved type: java.lang.Throwable} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v81, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r10v18, resolved type: kotlinx.coroutines.channels.ReceiveChannel} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v83, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r11v17, resolved type: kotlinx.coroutines.channels.ReceiveChannel} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v84, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v28, resolved type: kotlinx.coroutines.channels.ReceiveChannel} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v85, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r9v19, resolved type: kotlinx.coroutines.CoroutineScope} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r12v24, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r11v21, resolved type: java.lang.Throwable} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v89, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r18v27, resolved type: kotlinx.coroutines.channels.ReceiveChannel} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v90, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r9v23, resolved type: kotlinx.coroutines.channels.ReceiveChannel} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v91, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r16v25, resolved type: kotlinx.coroutines.CoroutineScope} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:127:0x040b  */
    /* JADX WARNING: Removed duplicated region for block: B:133:0x0423  */
    /* JADX WARNING: Removed duplicated region for block: B:49:0x022d A[Catch:{ all -> 0x03b9 }] */
    /* JADX WARNING: Removed duplicated region for block: B:62:0x02a8 A[Catch:{ all -> 0x036a }] */
    /* JADX WARNING: Removed duplicated region for block: B:63:0x02aa A[Catch:{ all -> 0x036a }] */
    /* JADX WARNING: Removed duplicated region for block: B:66:0x02c3 A[Catch:{ all -> 0x036a }] */
    /* JADX WARNING: Removed duplicated region for block: B:71:0x02f0  */
    /* JADX WARNING: Removed duplicated region for block: B:79:0x031e  */
    /* JADX WARNING: Removed duplicated region for block: B:97:0x039a  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final java.lang.Object invokeSuspend(java.lang.Object r31) {
        /*
            r30 = this;
            r1 = r30
            java.lang.Object r0 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r2 = r1.label
            r3 = 4
            r4 = 3
            r5 = 2
            r6 = 1
            r7 = 0
            if (r2 == 0) goto L_0x01b2
            r8 = 0
            if (r2 == r6) goto L_0x015c
            if (r2 == r5) goto L_0x00e5
            if (r2 == r4) goto L_0x0076
            if (r2 != r3) goto L_0x006e
            r2 = r7
            r9 = r7
            r10 = r8
            r11 = r7
            r12 = r7
            r13 = r8
            r14 = r8
            r15 = r7
            r16 = r7
            r17 = r7
            r18 = r7
            r19 = r7
            r20 = r7
            java.lang.Object r3 = r1.L$11
            java.lang.Object r4 = r1.L$10
            java.lang.Object r5 = r1.L$9
            kotlinx.coroutines.channels.ChannelIterator r5 = (kotlinx.coroutines.channels.ChannelIterator) r5
            java.lang.Object r6 = r1.L$8
            kotlinx.coroutines.channels.ReceiveChannel r6 = (kotlinx.coroutines.channels.ReceiveChannel) r6
            java.lang.Object r12 = r1.L$7
            r11 = r12
            java.lang.Throwable r11 = (java.lang.Throwable) r11
            java.lang.Object r12 = r1.L$6
            kotlinx.coroutines.channels.ReceiveChannel r12 = (kotlinx.coroutines.channels.ReceiveChannel) r12
            java.lang.Object r15 = r1.L$5
            kotlinx.coroutines.flow.internal.CombineKt$zipImpl$$inlined$unsafeFlow$1$lambda$1 r15 = (kotlinx.coroutines.flow.internal.CombineKt$zipImpl$$inlined$unsafeFlow$1$lambda$1) r15
            java.lang.Object r7 = r1.L$4
            kotlinx.coroutines.channels.ReceiveChannel r7 = (kotlinx.coroutines.channels.ReceiveChannel) r7
            r17 = r0
            java.lang.Object r0 = r1.L$3
            r2 = r0
            kotlinx.coroutines.channels.ChannelIterator r2 = (kotlinx.coroutines.channels.ChannelIterator) r2
            java.lang.Object r0 = r1.L$2
            r18 = r0
            kotlinx.coroutines.channels.ReceiveChannel r18 = (kotlinx.coroutines.channels.ReceiveChannel) r18
            java.lang.Object r0 = r1.L$1
            r9 = r0
            kotlinx.coroutines.channels.ReceiveChannel r9 = (kotlinx.coroutines.channels.ReceiveChannel) r9
            java.lang.Object r0 = r1.L$0
            r16 = r0
            kotlinx.coroutines.CoroutineScope r16 = (kotlinx.coroutines.CoroutineScope) r16
            kotlin.ResultKt.throwOnFailure(r31)     // Catch:{ all -> 0x01ac }
            r0 = r31
            r22 = r16
            r16 = r10
            r10 = r7
            r7 = r5
            r5 = r1
            r1 = 4
            goto L_0x032e
        L_0x006e:
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
            java.lang.String r2 = "call to 'resume' before 'invoke' with coroutine"
            r0.<init>(r2)
            throw r0
        L_0x0076:
            r17 = r0
            r0 = 0
            r2 = r0
            r3 = r0
            r4 = r8
            r5 = r0
            r6 = r0
            r13 = r8
            r14 = r8
            r7 = r0
            r9 = r0
            r10 = r0
            r11 = r0
            r12 = r0
            r15 = r0
            java.lang.Object r0 = r1.L$12
            kotlinx.coroutines.flow.FlowCollector r0 = (kotlinx.coroutines.flow.FlowCollector) r0
            java.lang.Object r15 = r1.L$11
            java.lang.Object r12 = r1.L$10
            r16 = r0
            java.lang.Object r0 = r1.L$9
            kotlinx.coroutines.channels.ChannelIterator r0 = (kotlinx.coroutines.channels.ChannelIterator) r0
            r18 = r0
            java.lang.Object r0 = r1.L$8
            kotlinx.coroutines.channels.ReceiveChannel r0 = (kotlinx.coroutines.channels.ReceiveChannel) r0
            java.lang.Object r6 = r1.L$7
            r5 = r6
            java.lang.Throwable r5 = (java.lang.Throwable) r5
            java.lang.Object r6 = r1.L$6
            kotlinx.coroutines.channels.ReceiveChannel r6 = (kotlinx.coroutines.channels.ReceiveChannel) r6
            java.lang.Object r7 = r1.L$5
            kotlinx.coroutines.flow.internal.CombineKt$zipImpl$$inlined$unsafeFlow$1$lambda$1 r7 = (kotlinx.coroutines.flow.internal.CombineKt$zipImpl$$inlined$unsafeFlow$1$lambda$1) r7
            r21 = r0
            java.lang.Object r0 = r1.L$4
            r10 = r0
            kotlinx.coroutines.channels.ReceiveChannel r10 = (kotlinx.coroutines.channels.ReceiveChannel) r10
            java.lang.Object r0 = r1.L$3
            r2 = r0
            kotlinx.coroutines.channels.ChannelIterator r2 = (kotlinx.coroutines.channels.ChannelIterator) r2
            java.lang.Object r0 = r1.L$2
            r11 = r0
            kotlinx.coroutines.channels.ReceiveChannel r11 = (kotlinx.coroutines.channels.ReceiveChannel) r11
            java.lang.Object r0 = r1.L$1
            r3 = r0
            kotlinx.coroutines.channels.ReceiveChannel r3 = (kotlinx.coroutines.channels.ReceiveChannel) r3
            java.lang.Object r0 = r1.L$0
            r9 = r0
            kotlinx.coroutines.CoroutineScope r9 = (kotlinx.coroutines.CoroutineScope) r9
            kotlin.ResultKt.throwOnFailure(r31)     // Catch:{ all -> 0x014e }
            r0 = r31
            r27 = r5
            r5 = r1
            r1 = r9
            r9 = r3
            r3 = r15
            r15 = r7
            r7 = r18
            r18 = r13
            r13 = r6
            r6 = r2
            r2 = r16
            r16 = r4
            r4 = r17
            r17 = r8
            r8 = r21
            r21 = r14
            r14 = r11
            r11 = r27
            goto L_0x02fa
        L_0x00e5:
            r17 = r0
            r0 = 0
            r2 = r0
            r3 = r0
            r4 = r8
            r5 = r0
            r6 = r0
            r13 = r8
            r14 = r8
            r7 = r0
            r9 = r0
            r10 = r0
            r11 = r0
            r12 = r0
            r15 = r0
            java.lang.Object r0 = r1.L$11
            java.lang.Object r12 = r1.L$10
            java.lang.Object r15 = r1.L$9
            kotlinx.coroutines.channels.ChannelIterator r15 = (kotlinx.coroutines.channels.ChannelIterator) r15
            r16 = r0
            java.lang.Object r0 = r1.L$8
            kotlinx.coroutines.channels.ReceiveChannel r0 = (kotlinx.coroutines.channels.ReceiveChannel) r0
            java.lang.Object r6 = r1.L$7
            r5 = r6
            java.lang.Throwable r5 = (java.lang.Throwable) r5
            java.lang.Object r6 = r1.L$6
            kotlinx.coroutines.channels.ReceiveChannel r6 = (kotlinx.coroutines.channels.ReceiveChannel) r6
            java.lang.Object r7 = r1.L$5
            kotlinx.coroutines.flow.internal.CombineKt$zipImpl$$inlined$unsafeFlow$1$lambda$1 r7 = (kotlinx.coroutines.flow.internal.CombineKt$zipImpl$$inlined$unsafeFlow$1$lambda$1) r7
            r18 = r0
            java.lang.Object r0 = r1.L$4
            r10 = r0
            kotlinx.coroutines.channels.ReceiveChannel r10 = (kotlinx.coroutines.channels.ReceiveChannel) r10
            java.lang.Object r0 = r1.L$3
            r2 = r0
            kotlinx.coroutines.channels.ChannelIterator r2 = (kotlinx.coroutines.channels.ChannelIterator) r2
            java.lang.Object r0 = r1.L$2
            r11 = r0
            kotlinx.coroutines.channels.ReceiveChannel r11 = (kotlinx.coroutines.channels.ReceiveChannel) r11
            java.lang.Object r0 = r1.L$1
            r3 = r0
            kotlinx.coroutines.channels.ReceiveChannel r3 = (kotlinx.coroutines.channels.ReceiveChannel) r3
            java.lang.Object r0 = r1.L$0
            r9 = r0
            kotlinx.coroutines.CoroutineScope r9 = (kotlinx.coroutines.CoroutineScope) r9
            kotlin.ResultKt.throwOnFailure(r31)     // Catch:{ all -> 0x014e }
            r0 = r31
            r27 = r5
            r5 = r1
            r1 = r15
            r15 = r14
            r14 = r11
            r11 = r27
            r28 = r6
            r6 = r2
            r2 = r16
            r16 = r4
            r4 = r17
            r17 = r8
            r8 = r7
            r7 = r9
            r9 = r3
            r3 = r18
            r18 = r13
            r13 = r28
            goto L_0x0278
        L_0x014e:
            r0 = move-exception
            r12 = r6
            r16 = r9
            r7 = r10
            r18 = r11
            r9 = r3
            r11 = r5
            r5 = r1
            r1 = r31
            goto L_0x03e3
        L_0x015c:
            r17 = r0
            r0 = 0
            r2 = r0
            r3 = r0
            r4 = r8
            r5 = r0
            r6 = r0
            r13 = r8
            r14 = r8
            r7 = r0
            r8 = r0
            r9 = r0
            r10 = r0
            java.lang.Object r0 = r1.L$9
            kotlinx.coroutines.channels.ChannelIterator r0 = (kotlinx.coroutines.channels.ChannelIterator) r0
            java.lang.Object r11 = r1.L$8
            r6 = r11
            kotlinx.coroutines.channels.ReceiveChannel r6 = (kotlinx.coroutines.channels.ReceiveChannel) r6
            java.lang.Object r11 = r1.L$7
            java.lang.Throwable r11 = (java.lang.Throwable) r11
            java.lang.Object r5 = r1.L$6
            r12 = r5
            kotlinx.coroutines.channels.ReceiveChannel r12 = (kotlinx.coroutines.channels.ReceiveChannel) r12
            java.lang.Object r5 = r1.L$5
            kotlinx.coroutines.flow.internal.CombineKt$zipImpl$$inlined$unsafeFlow$1$lambda$1 r5 = (kotlinx.coroutines.flow.internal.CombineKt$zipImpl$$inlined$unsafeFlow$1$lambda$1) r5
            java.lang.Object r7 = r1.L$4
            kotlinx.coroutines.channels.ReceiveChannel r7 = (kotlinx.coroutines.channels.ReceiveChannel) r7
            java.lang.Object r9 = r1.L$3
            r2 = r9
            kotlinx.coroutines.channels.ChannelIterator r2 = (kotlinx.coroutines.channels.ChannelIterator) r2
            java.lang.Object r9 = r1.L$2
            r18 = r9
            kotlinx.coroutines.channels.ReceiveChannel r18 = (kotlinx.coroutines.channels.ReceiveChannel) r18
            java.lang.Object r9 = r1.L$1
            kotlinx.coroutines.channels.ReceiveChannel r9 = (kotlinx.coroutines.channels.ReceiveChannel) r9
            java.lang.Object r3 = r1.L$0
            r16 = r3
            kotlinx.coroutines.CoroutineScope r16 = (kotlinx.coroutines.CoroutineScope) r16
            kotlin.ResultKt.throwOnFailure(r31)     // Catch:{ all -> 0x01ac }
            r15 = r0
            r8 = r5
            r0 = r6
            r10 = r16
            r3 = r17
            r5 = r2
            r6 = r4
            r2 = r31
            r4 = r1
            r1 = r18
            goto L_0x0225
        L_0x01ac:
            r0 = move-exception
            r5 = r1
            r1 = r31
            goto L_0x03e3
        L_0x01b2:
            r17 = r0
            kotlin.ResultKt.throwOnFailure(r31)
            kotlinx.coroutines.CoroutineScope r2 = r1.f698p$
            kotlinx.coroutines.flow.internal.CombineKt$zipImpl$$inlined$unsafeFlow$1 r0 = r1.this$0
            kotlinx.coroutines.flow.Flow r0 = r0.$flow$inlined
            kotlinx.coroutines.channels.ReceiveChannel r9 = kotlinx.coroutines.flow.internal.CombineKt.asChannel(r2, r0)
            kotlinx.coroutines.flow.internal.CombineKt$zipImpl$$inlined$unsafeFlow$1 r0 = r1.this$0
            kotlinx.coroutines.flow.Flow r0 = r0.$flow2$inlined
            kotlinx.coroutines.channels.ReceiveChannel r18 = kotlinx.coroutines.flow.internal.CombineKt.asChannel(r2, r0)
            if (r18 == 0) goto L_0x0431
            r0 = r18
            kotlinx.coroutines.channels.SendChannel r0 = (kotlinx.coroutines.channels.SendChannel) r0
            kotlinx.coroutines.flow.internal.CombineKt$zipImpl$$inlined$unsafeFlow$1$lambda$1$1 r3 = new kotlinx.coroutines.flow.internal.CombineKt$zipImpl$$inlined$unsafeFlow$1$lambda$1$1
            r3.<init>(r9)
            kotlin.jvm.functions.Function1 r3 = (kotlin.jvm.functions.Function1) r3
            r0.invokeOnClose(r3)
            kotlinx.coroutines.channels.ChannelIterator r3 = r18.iterator()
            r7 = r9
            r13 = 0
            r12 = r7
            r14 = 0
            r0 = 0
            r4 = r0
            java.lang.Throwable r4 = (java.lang.Throwable) r4     // Catch:{ AbortFlowException -> 0x0416, all -> 0x03fd }
            r11 = r4
            r4 = r12
            r5 = 0
            kotlinx.coroutines.channels.ChannelIterator r6 = r4.iterator()     // Catch:{ all -> 0x03db }
            r0 = r1
            r10 = r2
            r8 = r7
            r15 = r18
            r2 = r31
            r7 = r6
            r6 = r5
            r5 = r4
            r4 = r3
            r3 = r17
        L_0x01fb:
            r1.L$0 = r10     // Catch:{ all -> 0x03ce }
            r1.L$1 = r9     // Catch:{ all -> 0x03ce }
            r1.L$2 = r15     // Catch:{ all -> 0x03ce }
            r1.L$3 = r4     // Catch:{ all -> 0x03ce }
            r1.L$4 = r8     // Catch:{ all -> 0x03ce }
            r1.L$5 = r0     // Catch:{ all -> 0x03ce }
            r1.L$6 = r12     // Catch:{ all -> 0x03ce }
            r1.L$7 = r11     // Catch:{ all -> 0x03ce }
            r1.L$8 = r5     // Catch:{ all -> 0x03ce }
            r1.L$9 = r7     // Catch:{ all -> 0x03ce }
            r31 = r2
            r2 = 1
            r1.label = r2     // Catch:{ all -> 0x03c3 }
            java.lang.Object r2 = r7.hasNext(r0)     // Catch:{ all -> 0x03c3 }
            if (r2 != r3) goto L_0x021b
            return r3
        L_0x021b:
            r27 = r8
            r8 = r0
            r0 = r5
            r5 = r4
            r4 = r1
            r1 = r15
            r15 = r7
            r7 = r27
        L_0x0225:
            java.lang.Boolean r2 = (java.lang.Boolean) r2     // Catch:{ all -> 0x03b9 }
            boolean r2 = r2.booleanValue()     // Catch:{ all -> 0x03b9 }
            if (r2 == 0) goto L_0x038c
            java.lang.Object r2 = r15.next()     // Catch:{ all -> 0x03b9 }
            r16 = r2
            r17 = 0
            r4.L$0 = r10     // Catch:{ all -> 0x03b9 }
            r4.L$1 = r9     // Catch:{ all -> 0x03b9 }
            r4.L$2 = r1     // Catch:{ all -> 0x03b9 }
            r4.L$3 = r5     // Catch:{ all -> 0x03b9 }
            r4.L$4 = r7     // Catch:{ all -> 0x03b9 }
            r4.L$5 = r8     // Catch:{ all -> 0x03b9 }
            r4.L$6 = r12     // Catch:{ all -> 0x03b9 }
            r4.L$7 = r11     // Catch:{ all -> 0x03b9 }
            r4.L$8 = r0     // Catch:{ all -> 0x03b9 }
            r4.L$9 = r15     // Catch:{ all -> 0x03b9 }
            r4.L$10 = r2     // Catch:{ all -> 0x03b9 }
            r18 = r0
            r0 = r16
            r4.L$11 = r0     // Catch:{ all -> 0x03b9 }
            r16 = r0
            r0 = 2
            r4.label = r0     // Catch:{ all -> 0x03b9 }
            java.lang.Object r0 = r5.hasNext(r4)     // Catch:{ all -> 0x03b9 }
            if (r0 != r3) goto L_0x025d
            return r3
        L_0x025d:
            r27 = r14
            r14 = r1
            r1 = r15
            r15 = r27
            r28 = r12
            r12 = r2
            r2 = r16
            r16 = r6
            r6 = r5
            r5 = r4
            r4 = r3
            r3 = r18
            r18 = r13
            r13 = r28
            r29 = r10
            r10 = r7
            r7 = r29
        L_0x0278:
            java.lang.Boolean r0 = (java.lang.Boolean) r0     // Catch:{ all -> 0x037a }
            boolean r0 = r0.booleanValue()     // Catch:{ all -> 0x037a }
            if (r0 != 0) goto L_0x0294
            r2 = r31
            r0 = r8
            r8 = r10
            r10 = r7
            r7 = r1
            r1 = 4
            r27 = r6
            r6 = r3
            r3 = r4
            r4 = r27
            r28 = r15
            r15 = r14
            r14 = r28
            goto L_0x033f
        L_0x0294:
            kotlinx.coroutines.flow.FlowCollector r0 = r5.$this_unsafeFlow     // Catch:{ all -> 0x037a }
            r21 = r15
            kotlinx.coroutines.flow.internal.CombineKt$zipImpl$$inlined$unsafeFlow$1 r15 = r5.this$0     // Catch:{ all -> 0x036a }
            kotlin.jvm.functions.Function3 r15 = r15.$transform$inlined     // Catch:{ all -> 0x036a }
            kotlinx.coroutines.internal.Symbol r22 = kotlinx.coroutines.flow.internal.NullSurrogateKt.NULL     // Catch:{ all -> 0x036a }
            r23 = r22
            r22 = 0
            r24 = r4
            r4 = r23
            if (r2 != r4) goto L_0x02aa
            r4 = 0
            goto L_0x02ab
        L_0x02aa:
            r4 = r2
        L_0x02ab:
            kotlinx.coroutines.internal.Symbol r22 = kotlinx.coroutines.flow.internal.NullSurrogateKt.NULL     // Catch:{ all -> 0x036a }
            java.lang.Object r23 = r6.next()     // Catch:{ all -> 0x036a }
            r25 = r23
            r23 = r22
            r22 = 0
            r26 = r4
            r4 = r25
            r27 = r23
            r23 = r15
            r15 = r27
            if (r4 != r15) goto L_0x02c4
            r4 = 0
        L_0x02c4:
            r5.L$0 = r7     // Catch:{ all -> 0x036a }
            r5.L$1 = r9     // Catch:{ all -> 0x036a }
            r5.L$2 = r14     // Catch:{ all -> 0x036a }
            r5.L$3 = r6     // Catch:{ all -> 0x036a }
            r5.L$4 = r10     // Catch:{ all -> 0x036a }
            r5.L$5 = r8     // Catch:{ all -> 0x036a }
            r5.L$6 = r13     // Catch:{ all -> 0x036a }
            r5.L$7 = r11     // Catch:{ all -> 0x036a }
            r5.L$8 = r3     // Catch:{ all -> 0x036a }
            r5.L$9 = r1     // Catch:{ all -> 0x036a }
            r5.L$10 = r12     // Catch:{ all -> 0x036a }
            r5.L$11 = r2     // Catch:{ all -> 0x036a }
            r5.L$12 = r0     // Catch:{ all -> 0x036a }
            r15 = 3
            r5.label = r15     // Catch:{ all -> 0x036a }
            r22 = r0
            r15 = r23
            r0 = r26
            java.lang.Object r0 = r15.invoke(r0, r4, r5)     // Catch:{ all -> 0x036a }
            r4 = r24
            if (r0 != r4) goto L_0x02f0
            return r4
        L_0x02f0:
            r15 = r8
            r8 = r3
            r3 = r2
            r2 = r22
            r27 = r7
            r7 = r1
            r1 = r27
        L_0x02fa:
            r5.L$0 = r1     // Catch:{ all -> 0x0358 }
            r5.L$1 = r9     // Catch:{ all -> 0x0358 }
            r5.L$2 = r14     // Catch:{ all -> 0x0358 }
            r5.L$3 = r6     // Catch:{ all -> 0x0358 }
            r5.L$4 = r10     // Catch:{ all -> 0x0358 }
            r5.L$5 = r15     // Catch:{ all -> 0x0358 }
            r5.L$6 = r13     // Catch:{ all -> 0x0358 }
            r5.L$7 = r11     // Catch:{ all -> 0x0358 }
            r5.L$8 = r8     // Catch:{ all -> 0x0358 }
            r5.L$9 = r7     // Catch:{ all -> 0x0358 }
            r5.L$10 = r12     // Catch:{ all -> 0x0358 }
            r5.L$11 = r3     // Catch:{ all -> 0x0358 }
            r22 = r1
            r1 = 4
            r5.label = r1     // Catch:{ all -> 0x0348 }
            java.lang.Object r0 = r2.emit(r0, r5)     // Catch:{ all -> 0x0348 }
            if (r0 != r4) goto L_0x031e
            return r4
        L_0x031e:
            r0 = r31
            r2 = r6
            r6 = r8
            r8 = r17
            r17 = r4
            r4 = r12
            r12 = r13
            r13 = r18
            r18 = r14
            r14 = r21
        L_0x032e:
            r8 = r10
            r3 = r17
            r10 = r22
            r27 = r2
            r2 = r0
            r0 = r15
            r15 = r18
            r18 = r13
            r13 = r12
            r12 = r4
            r4 = r27
        L_0x033f:
            r1 = r5
            r5 = r6
            r12 = r13
            r6 = r16
            r13 = r18
            goto L_0x01fb
        L_0x0348:
            r0 = move-exception
            r1 = r31
            r2 = r6
            r7 = r10
            r12 = r13
            r13 = r18
            r16 = r22
            r18 = r14
            r14 = r21
            goto L_0x03e3
        L_0x0358:
            r0 = move-exception
            r22 = r1
            r1 = r31
            r2 = r6
            r7 = r10
            r12 = r13
            r13 = r18
            r16 = r22
            r18 = r14
            r14 = r21
            goto L_0x03e3
        L_0x036a:
            r0 = move-exception
            r1 = r31
            r2 = r6
            r16 = r7
            r7 = r10
            r12 = r13
            r13 = r18
            r18 = r14
            r14 = r21
            goto L_0x03e3
        L_0x037a:
            r0 = move-exception
            r21 = r15
            r1 = r31
            r2 = r6
            r16 = r7
            r7 = r10
            r12 = r13
            r13 = r18
            r18 = r14
            r14 = r21
            goto L_0x03e3
        L_0x038c:
            r18 = r0
            kotlin.Unit r0 = kotlin.Unit.INSTANCE     // Catch:{ all -> 0x03b9 }
            kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r12, r11)     // Catch:{ AbortFlowException -> 0x03b1, all -> 0x03a8 }
            boolean r0 = r1.isClosedForReceive()
            if (r0 != 0) goto L_0x03a4
            kotlinx.coroutines.flow.internal.AbortFlowException r0 = new kotlinx.coroutines.flow.internal.AbortFlowException
            r0.<init>()
            java.util.concurrent.CancellationException r0 = (java.util.concurrent.CancellationException) r0
            r1.cancel((java.util.concurrent.CancellationException) r0)
        L_0x03a4:
            r1 = r31
            goto L_0x042e
        L_0x03a8:
            r0 = move-exception
            r3 = r5
            r2 = r10
            r5 = r0
            r0 = r1
            r1 = r31
            goto L_0x0405
        L_0x03b1:
            r0 = move-exception
            r0 = r1
            r3 = r5
            r2 = r10
            r1 = r31
            goto L_0x041d
        L_0x03b9:
            r0 = move-exception
            r18 = r1
            r2 = r5
            r16 = r10
            r1 = r31
            r5 = r4
            goto L_0x03e3
        L_0x03c3:
            r0 = move-exception
            r5 = r1
            r2 = r4
            r7 = r8
            r16 = r10
            r18 = r15
            r1 = r31
            goto L_0x03e3
        L_0x03ce:
            r0 = move-exception
            r31 = r2
            r5 = r1
            r2 = r4
            r7 = r8
            r16 = r10
            r18 = r15
            r1 = r31
            goto L_0x03e3
        L_0x03db:
            r0 = move-exception
            r5 = r30
            r1 = r31
            r16 = r2
            r2 = r3
        L_0x03e3:
            r3 = r0
            throw r0     // Catch:{ all -> 0x03e6 }
        L_0x03e6:
            r0 = move-exception
            r4 = r0
            kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r12, r3)     // Catch:{ AbortFlowException -> 0x03f5, all -> 0x03ec }
            throw r4     // Catch:{ AbortFlowException -> 0x03f5, all -> 0x03ec }
        L_0x03ec:
            r0 = move-exception
            r3 = r2
            r4 = r5
            r2 = r16
            r5 = r0
            r0 = r18
            goto L_0x0405
        L_0x03f5:
            r0 = move-exception
            r3 = r2
            r4 = r5
            r2 = r16
            r0 = r18
            goto L_0x041d
        L_0x03fd:
            r0 = move-exception
            r4 = r30
            r1 = r31
            r5 = r0
            r0 = r18
        L_0x0405:
            boolean r6 = r0.isClosedForReceive()
            if (r6 != 0) goto L_0x0415
            kotlinx.coroutines.flow.internal.AbortFlowException r6 = new kotlinx.coroutines.flow.internal.AbortFlowException
            r6.<init>()
            java.util.concurrent.CancellationException r6 = (java.util.concurrent.CancellationException) r6
            r0.cancel((java.util.concurrent.CancellationException) r6)
        L_0x0415:
            throw r5
        L_0x0416:
            r0 = move-exception
            r4 = r30
            r1 = r31
            r0 = r18
        L_0x041d:
            boolean r5 = r0.isClosedForReceive()
            if (r5 != 0) goto L_0x042d
            kotlinx.coroutines.flow.internal.AbortFlowException r5 = new kotlinx.coroutines.flow.internal.AbortFlowException
            r5.<init>()
            java.util.concurrent.CancellationException r5 = (java.util.concurrent.CancellationException) r5
            r0.cancel((java.util.concurrent.CancellationException) r5)
        L_0x042d:
            r10 = r2
        L_0x042e:
            kotlin.Unit r0 = kotlin.Unit.INSTANCE
            return r0
        L_0x0431:
            kotlin.TypeCastException r0 = new kotlin.TypeCastException
            java.lang.String r1 = "null cannot be cast to non-null type kotlinx.coroutines.channels.SendChannel<*>"
            r0.<init>(r1)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.flow.internal.CombineKt$zipImpl$$inlined$unsafeFlow$1$lambda$1.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
