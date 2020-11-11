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
@DebugMetadata(mo34304c = "kotlinx.coroutines.flow.FlowKt__DelayKt$debounce$2", mo34305f = "Delay.kt", mo34306i = {0, 0, 0, 0}, mo34307l = {137}, mo34308m = "invokeSuspend", mo34309n = {"$this$scopedFlow", "downstream", "values", "lastValue"}, mo34310s = {"L$0", "L$1", "L$2", "L$3"})
/* compiled from: Delay.kt */
final class FlowKt__DelayKt$debounce$2 extends SuspendLambda implements Function3<CoroutineScope, FlowCollector<? super T>, Continuation<? super Unit>, Object> {
    final /* synthetic */ Flow $this_debounce;
    final /* synthetic */ long $timeoutMillis;
    Object L$0;
    Object L$1;
    Object L$2;
    Object L$3;
    Object L$4;
    int label;

    /* renamed from: p$ */
    private CoroutineScope f648p$;
    private FlowCollector p$0;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    FlowKt__DelayKt$debounce$2(Flow flow, long j, Continuation continuation) {
        super(3, continuation);
        this.$this_debounce = flow;
        this.$timeoutMillis = j;
    }

    public final Continuation<Unit> create(CoroutineScope coroutineScope, FlowCollector<? super T> flowCollector, Continuation<? super Unit> continuation) {
        Intrinsics.checkParameterIsNotNull(coroutineScope, "$this$create");
        Intrinsics.checkParameterIsNotNull(flowCollector, "downstream");
        Intrinsics.checkParameterIsNotNull(continuation, "continuation");
        FlowKt__DelayKt$debounce$2 flowKt__DelayKt$debounce$2 = new FlowKt__DelayKt$debounce$2(this.$this_debounce, this.$timeoutMillis, continuation);
        flowKt__DelayKt$debounce$2.f648p$ = coroutineScope;
        flowKt__DelayKt$debounce$2.p$0 = flowCollector;
        return flowKt__DelayKt$debounce$2;
    }

    public final Object invoke(Object obj, Object obj2, Object obj3) {
        return ((FlowKt__DelayKt$debounce$2) create((CoroutineScope) obj, (FlowCollector) obj2, (Continuation) obj3)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r8v7, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v5, resolved type: kotlin.jvm.internal.Ref$ObjectRef} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r8v8, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r5v7, resolved type: kotlinx.coroutines.channels.ReceiveChannel} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r8v9, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r6v10, resolved type: kotlinx.coroutines.flow.FlowCollector} */
    /* JADX WARNING: Can't wrap try/catch for region: R(11:9|(2:10|11)|12|13|14|(4:16|17|18|19)(1:21)|22|28|(1:30)|(1:32)(4:33|34|7|(1:35)(0))|32) */
    /* JADX WARNING: Can't wrap try/catch for region: R(4:16|17|18|19) */
    /* JADX WARNING: Code restructure failed: missing block: B:20:0x00cb, code lost:
        r0 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:23:0x00d5, code lost:
        r0 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:24:0x00d6, code lost:
        r24 = r12;
        r25 = r13;
     */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:30:0x00f4  */
    /* JADX WARNING: Removed duplicated region for block: B:33:0x00fe  */
    /* JADX WARNING: Removed duplicated region for block: B:9:0x006d  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final java.lang.Object invokeSuspend(java.lang.Object r27) {
        /*
            r26 = this;
            r1 = r26
            java.lang.Object r0 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r2 = r1.label
            r3 = 1
            r4 = 0
            if (r2 == 0) goto L_0x003f
            if (r2 != r3) goto L_0x0037
            r2 = r4
            r5 = r4
            r6 = r4
            r7 = 0
            java.lang.Object r8 = r1.L$4
            kotlinx.coroutines.flow.FlowKt__DelayKt$debounce$2 r8 = (kotlinx.coroutines.flow.FlowKt__DelayKt$debounce$2) r8
            java.lang.Object r8 = r1.L$3
            r4 = r8
            kotlin.jvm.internal.Ref$ObjectRef r4 = (kotlin.jvm.internal.Ref.ObjectRef) r4
            java.lang.Object r8 = r1.L$2
            r5 = r8
            kotlinx.coroutines.channels.ReceiveChannel r5 = (kotlinx.coroutines.channels.ReceiveChannel) r5
            java.lang.Object r8 = r1.L$1
            r6 = r8
            kotlinx.coroutines.flow.FlowCollector r6 = (kotlinx.coroutines.flow.FlowCollector) r6
            java.lang.Object r8 = r1.L$0
            r2 = r8
            kotlinx.coroutines.CoroutineScope r2 = (kotlinx.coroutines.CoroutineScope) r2
            kotlin.ResultKt.throwOnFailure(r27)
            r12 = r1
            r14 = r4
            r15 = r5
            r13 = r6
            r4 = r0
            r5 = r2
            r2 = r27
            goto L_0x0102
        L_0x0037:
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
            java.lang.String r2 = "call to 'resume' before 'invoke' with coroutine"
            r0.<init>(r2)
            throw r0
        L_0x003f:
            kotlin.ResultKt.throwOnFailure(r27)
            kotlinx.coroutines.CoroutineScope r2 = r1.f648p$
            kotlinx.coroutines.flow.FlowCollector r11 = r1.p$0
            r6 = 0
            r7 = -1
            kotlinx.coroutines.flow.FlowKt__DelayKt$debounce$2$values$1 r5 = new kotlinx.coroutines.flow.FlowKt__DelayKt$debounce$2$values$1
            r5.<init>(r1, r4)
            r8 = r5
            kotlin.jvm.functions.Function2 r8 = (kotlin.jvm.functions.Function2) r8
            r9 = 1
            r10 = 0
            r5 = r2
            kotlinx.coroutines.channels.ReceiveChannel r5 = kotlinx.coroutines.channels.ProduceKt.produce$default(r5, r6, r7, r8, r9, r10)
            kotlin.jvm.internal.Ref$ObjectRef r6 = new kotlin.jvm.internal.Ref$ObjectRef
            r6.<init>()
            r6.element = r4
            r4 = r6
            r12 = r1
            r14 = r4
            r15 = r5
            r13 = r11
            r4 = r0
            r5 = r2
            r2 = r27
        L_0x0067:
            T r0 = r14.element
            kotlinx.coroutines.internal.Symbol r6 = kotlinx.coroutines.flow.internal.NullSurrogateKt.DONE
            if (r0 == r6) goto L_0x0106
            r16 = 0
            r12.L$0 = r5
            r12.L$1 = r13
            r12.L$2 = r15
            r12.L$3 = r14
            r12.L$4 = r12
            r12.label = r3
            r11 = r12
            kotlin.coroutines.Continuation r11 = (kotlin.coroutines.Continuation) r11
            r17 = 0
            kotlinx.coroutines.selects.SelectBuilderImpl r0 = new kotlinx.coroutines.selects.SelectBuilderImpl
            r0.<init>(r11)
            r10 = r0
            r0 = r10
            kotlinx.coroutines.selects.SelectBuilder r0 = (kotlinx.coroutines.selects.SelectBuilder) r0     // Catch:{ all -> 0x00db }
            r18 = 0
            kotlinx.coroutines.selects.SelectClause1 r9 = r15.getOnReceiveOrNull()     // Catch:{ all -> 0x00db }
            kotlinx.coroutines.flow.FlowKt__DelayKt$debounce$2$invokeSuspend$$inlined$select$lambda$1 r19 = new kotlinx.coroutines.flow.FlowKt__DelayKt$debounce$2$invokeSuspend$$inlined$select$lambda$1     // Catch:{ all -> 0x00db }
            r7 = 0
            r6 = r19
            r8 = r12
            r3 = r9
            r9 = r15
            r20 = r10
            r10 = r14
            r21 = r11
            r11 = r13
            r6.<init>(r7, r8, r9, r10, r11)     // Catch:{ all -> 0x00d5 }
            r6 = r19
            kotlin.jvm.functions.Function2 r6 = (kotlin.jvm.functions.Function2) r6     // Catch:{ all -> 0x00d5 }
            r0.invoke(r3, r6)     // Catch:{ all -> 0x00d5 }
            T r7 = r14.element     // Catch:{ all -> 0x00d5 }
            if (r7 == 0) goto L_0x00cd
            r3 = 0
            long r10 = r12.$timeoutMillis     // Catch:{ all -> 0x00d5 }
            kotlinx.coroutines.flow.FlowKt__DelayKt$debounce$2$invokeSuspend$$inlined$select$lambda$2 r19 = new kotlinx.coroutines.flow.FlowKt__DelayKt$debounce$2$invokeSuspend$$inlined$select$lambda$2     // Catch:{ all -> 0x00d5 }
            r8 = 0
            r6 = r19
            r9 = r0
            r22 = r10
            r10 = r12
            r11 = r15
            r24 = r12
            r12 = r14
            r25 = r13
            r6.<init>(r7, r8, r9, r10, r11, r12, r13)     // Catch:{ all -> 0x00cb }
            r6 = r19
            kotlin.jvm.functions.Function1 r6 = (kotlin.jvm.functions.Function1) r6     // Catch:{ all -> 0x00cb }
            r8 = r22
            r0.onTimeout(r8, r6)     // Catch:{ all -> 0x00cb }
            goto L_0x00d1
        L_0x00cb:
            r0 = move-exception
            goto L_0x00e4
        L_0x00cd:
            r24 = r12
            r25 = r13
        L_0x00d1:
            r3 = r20
            goto L_0x00e9
        L_0x00d5:
            r0 = move-exception
            r24 = r12
            r25 = r13
            goto L_0x00e4
        L_0x00db:
            r0 = move-exception
            r20 = r10
            r21 = r11
            r24 = r12
            r25 = r13
        L_0x00e4:
            r3 = r20
            r3.handleBuilderException(r0)
        L_0x00e9:
            java.lang.Object r0 = r3.getResult()
            java.lang.Object r3 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            if (r0 != r3) goto L_0x00fb
            r3 = r24
            kotlin.coroutines.Continuation r3 = (kotlin.coroutines.Continuation) r3
            kotlin.coroutines.jvm.internal.DebugProbesKt.probeCoroutineSuspended(r3)
        L_0x00fb:
            if (r0 != r4) goto L_0x00fe
            return r4
        L_0x00fe:
            r12 = r24
            r13 = r25
        L_0x0102:
            r3 = 1
            goto L_0x0067
        L_0x0106:
            kotlin.Unit r0 = kotlin.Unit.INSTANCE
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.flow.FlowKt__DelayKt$debounce$2.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
