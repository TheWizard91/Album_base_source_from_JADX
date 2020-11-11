package kotlinx.coroutines.flow;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.CoroutineScope;

@Metadata(mo33669bv = {1, 0, 3}, mo33670d1 = {"\u0000\u0016\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0010\u0000\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\u00020\u00032\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0005H@¢\u0006\u0004\b\u0006\u0010\u0007"}, mo33671d2 = {"<anonymous>", "", "T", "Lkotlinx/coroutines/CoroutineScope;", "downstream", "Lkotlinx/coroutines/flow/FlowCollector;", "invoke", "(Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;"}, mo33672k = 3, mo33673mv = {1, 1, 15})
@DebugMetadata(mo34304c = "kotlinx.coroutines.flow.FlowKt__DelayKt$sample$2", mo34305f = "Delay.kt", mo34306i = {0, 0, 0, 0, 0}, mo34307l = {137}, mo34308m = "invokeSuspend", mo34309n = {"$this$scopedFlow", "downstream", "values", "lastValue", "ticker"}, mo34310s = {"L$0", "L$1", "L$2", "L$3", "L$4"})
/* compiled from: Delay.kt */
final class FlowKt__DelayKt$sample$2 extends SuspendLambda implements Function3<CoroutineScope, FlowCollector<? super T>, Continuation<? super Unit>, Object> {
    final /* synthetic */ long $periodMillis;
    final /* synthetic */ Flow $this_sample;
    Object L$0;
    Object L$1;
    Object L$2;
    Object L$3;
    Object L$4;
    Object L$5;
    int label;

    /* renamed from: p$ */
    private CoroutineScope f651p$;
    private FlowCollector p$0;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    FlowKt__DelayKt$sample$2(Flow flow, long j, Continuation continuation) {
        super(3, continuation);
        this.$this_sample = flow;
        this.$periodMillis = j;
    }

    public final Continuation<Unit> create(CoroutineScope coroutineScope, FlowCollector<? super T> flowCollector, Continuation<? super Unit> continuation) {
        Intrinsics.checkParameterIsNotNull(coroutineScope, "$this$create");
        Intrinsics.checkParameterIsNotNull(flowCollector, "downstream");
        Intrinsics.checkParameterIsNotNull(continuation, "continuation");
        FlowKt__DelayKt$sample$2 flowKt__DelayKt$sample$2 = new FlowKt__DelayKt$sample$2(this.$this_sample, this.$periodMillis, continuation);
        flowKt__DelayKt$sample$2.f651p$ = coroutineScope;
        flowKt__DelayKt$sample$2.p$0 = flowCollector;
        return flowKt__DelayKt$sample$2;
    }

    public final Object invoke(Object obj, Object obj2, Object obj3) {
        return ((FlowKt__DelayKt$sample$2) create((CoroutineScope) obj, (FlowCollector) obj2, (Continuation) obj3)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r9v7, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r5v8, resolved type: kotlinx.coroutines.channels.ReceiveChannel} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r9v8, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v6, resolved type: kotlin.jvm.internal.Ref$ObjectRef} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r9v9, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r6v9, resolved type: kotlinx.coroutines.channels.ReceiveChannel} */
    /* JADX WARNING: Can't wrap try/catch for region: R(11:9|10|11|12|13|14|15|20|(1:22)|(1:24)(4:25|26|7|(1:27)(0))|24) */
    /* JADX WARNING: Code restructure failed: missing block: B:16:0x00d7, code lost:
        r0 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:19:0x00e0, code lost:
        r3 = r20;
        r3.handleBuilderException(r0);
     */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:22:0x00f0  */
    /* JADX WARNING: Removed duplicated region for block: B:25:0x00fa  */
    /* JADX WARNING: Removed duplicated region for block: B:9:0x007e  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final java.lang.Object invokeSuspend(java.lang.Object r24) {
        /*
            r23 = this;
            r1 = r23
            java.lang.Object r0 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r2 = r1.label
            r3 = 1
            r4 = 0
            if (r2 == 0) goto L_0x0046
            if (r2 != r3) goto L_0x003e
            r2 = r4
            r5 = r4
            r6 = r4
            r7 = 0
            r8 = r4
            java.lang.Object r9 = r1.L$5
            kotlinx.coroutines.flow.FlowKt__DelayKt$sample$2 r9 = (kotlinx.coroutines.flow.FlowKt__DelayKt$sample$2) r9
            java.lang.Object r9 = r1.L$4
            r5 = r9
            kotlinx.coroutines.channels.ReceiveChannel r5 = (kotlinx.coroutines.channels.ReceiveChannel) r5
            java.lang.Object r9 = r1.L$3
            r4 = r9
            kotlin.jvm.internal.Ref$ObjectRef r4 = (kotlin.jvm.internal.Ref.ObjectRef) r4
            java.lang.Object r9 = r1.L$2
            r6 = r9
            kotlinx.coroutines.channels.ReceiveChannel r6 = (kotlinx.coroutines.channels.ReceiveChannel) r6
            java.lang.Object r9 = r1.L$1
            r8 = r9
            kotlinx.coroutines.flow.FlowCollector r8 = (kotlinx.coroutines.flow.FlowCollector) r8
            java.lang.Object r9 = r1.L$0
            r2 = r9
            kotlinx.coroutines.CoroutineScope r2 = (kotlinx.coroutines.CoroutineScope) r2
            kotlin.ResultKt.throwOnFailure(r24)
            r11 = r1
            r13 = r4
            r12 = r5
            r14 = r6
            r15 = r8
            r4 = r0
            r5 = r2
            r2 = r24
            goto L_0x00fc
        L_0x003e:
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
            java.lang.String r2 = "call to 'resume' before 'invoke' with coroutine"
            r0.<init>(r2)
            throw r0
        L_0x0046:
            kotlin.ResultKt.throwOnFailure(r24)
            kotlinx.coroutines.CoroutineScope r2 = r1.f651p$
            kotlinx.coroutines.flow.FlowCollector r11 = r1.p$0
            r6 = 0
            r7 = -1
            kotlinx.coroutines.flow.FlowKt__DelayKt$sample$2$values$1 r5 = new kotlinx.coroutines.flow.FlowKt__DelayKt$sample$2$values$1
            r5.<init>(r1, r4)
            r8 = r5
            kotlin.jvm.functions.Function2 r8 = (kotlin.jvm.functions.Function2) r8
            r9 = 1
            r10 = 0
            r5 = r2
            kotlinx.coroutines.channels.ReceiveChannel r12 = kotlinx.coroutines.channels.ProduceKt.produce$default(r5, r6, r7, r8, r9, r10)
            kotlin.jvm.internal.Ref$ObjectRef r5 = new kotlin.jvm.internal.Ref$ObjectRef
            r5.<init>()
            r5.element = r4
            r13 = r5
            long r5 = r1.$periodMillis
            r7 = 0
            r9 = 2
            r4 = r2
            kotlinx.coroutines.channels.ReceiveChannel r4 = kotlinx.coroutines.flow.FlowKt__DelayKt.fixedPeriodTicker$default(r4, r5, r7, r9, r10)
            r5 = r2
            r15 = r11
            r14 = r12
            r2 = r24
            r11 = r1
            r12 = r4
            r4 = r0
        L_0x0078:
            T r0 = r13.element
            kotlinx.coroutines.internal.Symbol r6 = kotlinx.coroutines.flow.internal.NullSurrogateKt.DONE
            if (r0 == r6) goto L_0x0100
            r16 = 0
            r11.L$0 = r5
            r11.L$1 = r15
            r11.L$2 = r14
            r11.L$3 = r13
            r11.L$4 = r12
            r11.L$5 = r11
            r11.label = r3
            r10 = r11
            kotlin.coroutines.Continuation r10 = (kotlin.coroutines.Continuation) r10
            r17 = 0
            kotlinx.coroutines.selects.SelectBuilderImpl r0 = new kotlinx.coroutines.selects.SelectBuilderImpl
            r0.<init>(r10)
            r9 = r0
            r0 = r9
            kotlinx.coroutines.selects.SelectBuilder r0 = (kotlinx.coroutines.selects.SelectBuilder) r0     // Catch:{ all -> 0x00d9 }
            r18 = 0
            kotlinx.coroutines.selects.SelectClause1 r8 = r14.getOnReceiveOrNull()     // Catch:{ all -> 0x00d9 }
            kotlinx.coroutines.flow.FlowKt__DelayKt$sample$2$invokeSuspend$$inlined$select$lambda$1 r19 = new kotlinx.coroutines.flow.FlowKt__DelayKt$sample$2$invokeSuspend$$inlined$select$lambda$1     // Catch:{ all -> 0x00d9 }
            r7 = 0
            r6 = r19
            r3 = r8
            r8 = r14
            r20 = r9
            r9 = r12
            r21 = r10
            r10 = r13
            r22 = r11
            r11 = r15
            r6.<init>(r7, r8, r9, r10, r11)     // Catch:{ all -> 0x00d7 }
            r6 = r19
            kotlin.jvm.functions.Function2 r6 = (kotlin.jvm.functions.Function2) r6     // Catch:{ all -> 0x00d7 }
            r0.invoke(r3, r6)     // Catch:{ all -> 0x00d7 }
            kotlinx.coroutines.selects.SelectClause1 r3 = r12.getOnReceive()     // Catch:{ all -> 0x00d7 }
            kotlinx.coroutines.flow.FlowKt__DelayKt$sample$2$invokeSuspend$$inlined$select$lambda$2 r19 = new kotlinx.coroutines.flow.FlowKt__DelayKt$sample$2$invokeSuspend$$inlined$select$lambda$2     // Catch:{ all -> 0x00d7 }
            r7 = 0
            r6 = r19
            r8 = r14
            r9 = r12
            r10 = r13
            r11 = r15
            r6.<init>(r7, r8, r9, r10, r11)     // Catch:{ all -> 0x00d7 }
            r6 = r19
            kotlin.jvm.functions.Function2 r6 = (kotlin.jvm.functions.Function2) r6     // Catch:{ all -> 0x00d7 }
            r0.invoke(r3, r6)     // Catch:{ all -> 0x00d7 }
            r3 = r20
            goto L_0x00e5
        L_0x00d7:
            r0 = move-exception
            goto L_0x00e0
        L_0x00d9:
            r0 = move-exception
            r20 = r9
            r21 = r10
            r22 = r11
        L_0x00e0:
            r3 = r20
            r3.handleBuilderException(r0)
        L_0x00e5:
            java.lang.Object r0 = r3.getResult()
            java.lang.Object r3 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            if (r0 != r3) goto L_0x00f7
            r3 = r22
            kotlin.coroutines.Continuation r3 = (kotlin.coroutines.Continuation) r3
            kotlin.coroutines.jvm.internal.DebugProbesKt.probeCoroutineSuspended(r3)
        L_0x00f7:
            if (r0 != r4) goto L_0x00fa
            return r4
        L_0x00fa:
            r11 = r22
        L_0x00fc:
            r3 = 1
            goto L_0x0078
        L_0x0100:
            kotlin.Unit r0 = kotlin.Unit.INSTANCE
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.flow.FlowKt__DelayKt$sample$2.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
