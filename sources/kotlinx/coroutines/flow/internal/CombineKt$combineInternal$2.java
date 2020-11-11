package kotlinx.coroutines.flow.internal;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;

@Metadata(mo33669bv = {1, 0, 3}, mo33670d1 = {"\u0000\u0012\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u0010\u0000\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003*\u00020\u0004H@¢\u0006\u0004\b\u0005\u0010\u0006"}, mo33671d2 = {"<anonymous>", "", "R", "T", "Lkotlinx/coroutines/CoroutineScope;", "invoke", "(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;"}, mo33672k = 3, mo33673mv = {1, 1, 15})
@DebugMetadata(mo34304c = "kotlinx.coroutines.flow.internal.CombineKt$combineInternal$2", mo34305f = "Combine.kt", mo34306i = {0, 0, 0, 0, 0}, mo34307l = {146}, mo34308m = "invokeSuspend", mo34309n = {"$this$coroutineScope", "size", "channels", "latestValues", "isClosed"}, mo34310s = {"L$0", "I$0", "L$1", "L$2", "L$3"})
/* compiled from: Combine.kt */
final class CombineKt$combineInternal$2 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
    final /* synthetic */ Function0 $arrayFactory;
    final /* synthetic */ Flow[] $flows;
    final /* synthetic */ FlowCollector $this_combineInternal;
    final /* synthetic */ Function3 $transform;
    int I$0;
    Object L$0;
    Object L$1;
    Object L$2;
    Object L$3;
    Object L$4;
    int label;

    /* renamed from: p$ */
    private CoroutineScope f701p$;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    CombineKt$combineInternal$2(FlowCollector flowCollector, Flow[] flowArr, Function0 function0, Function3 function3, Continuation continuation) {
        super(2, continuation);
        this.$this_combineInternal = flowCollector;
        this.$flows = flowArr;
        this.$arrayFactory = function0;
        this.$transform = function3;
    }

    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        Intrinsics.checkParameterIsNotNull(continuation, "completion");
        CombineKt$combineInternal$2 combineKt$combineInternal$2 = new CombineKt$combineInternal$2(this.$this_combineInternal, this.$flows, this.$arrayFactory, this.$transform, continuation);
        CoroutineScope coroutineScope = (CoroutineScope) obj;
        combineKt$combineInternal$2.f701p$ = (CoroutineScope) obj;
        return combineKt$combineInternal$2;
    }

    public final Object invoke(Object obj, Object obj2) {
        return ((CombineKt$combineInternal$2) create(obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r10v16, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r6v11, resolved type: java.lang.Boolean[]} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r10v18, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r7v8, resolved type: kotlinx.coroutines.channels.ReceiveChannel[]} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:15:0x0095  */
    /* JADX WARNING: Removed duplicated region for block: B:21:0x00b0  */
    /* JADX WARNING: Removed duplicated region for block: B:45:0x016f  */
    /* JADX WARNING: Removed duplicated region for block: B:48:0x0179  */
    /* JADX WARNING: Removed duplicated region for block: B:54:0x00ad A[SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final java.lang.Object invokeSuspend(java.lang.Object r32) {
        /*
            r31 = this;
            r1 = r31
            java.lang.Object r0 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r2 = r1.label
            r3 = 1
            r4 = 0
            if (r2 == 0) goto L_0x0044
            if (r2 != r3) goto L_0x003c
            r2 = 0
            r5 = r2
            r6 = r2
            r7 = r2
            r8 = r4
            r9 = r4
            java.lang.Object r10 = r1.L$4
            kotlinx.coroutines.flow.internal.CombineKt$combineInternal$2 r10 = (kotlinx.coroutines.flow.internal.CombineKt$combineInternal$2) r10
            java.lang.Object r10 = r1.L$3
            r6 = r10
            java.lang.Boolean[] r6 = (java.lang.Boolean[]) r6
            java.lang.Object r10 = r1.L$2
            r2 = r10
            java.lang.Object[] r2 = (java.lang.Object[]) r2
            java.lang.Object r10 = r1.L$1
            r7 = r10
            kotlinx.coroutines.channels.ReceiveChannel[] r7 = (kotlinx.coroutines.channels.ReceiveChannel[]) r7
            int r9 = r1.I$0
            java.lang.Object r10 = r1.L$0
            r5 = r10
            kotlinx.coroutines.CoroutineScope r5 = (kotlinx.coroutines.CoroutineScope) r5
            kotlin.ResultKt.throwOnFailure(r32)
            r10 = r1
            r15 = r2
            r14 = r5
            r13 = r6
            r12 = r7
            r11 = r9
            r2 = r32
            r5 = r0
            goto L_0x0183
        L_0x003c:
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
            java.lang.String r2 = "call to 'resume' before 'invoke' with coroutine"
            r0.<init>(r2)
            throw r0
        L_0x0044:
            kotlin.ResultKt.throwOnFailure(r32)
            kotlinx.coroutines.CoroutineScope r2 = r1.f701p$
            kotlinx.coroutines.flow.Flow[] r5 = r1.$flows
            int r5 = r5.length
            kotlinx.coroutines.channels.ReceiveChannel[] r6 = new kotlinx.coroutines.channels.ReceiveChannel[r5]
            r7 = r4
        L_0x0050:
            if (r7 >= r5) goto L_0x006a
            java.lang.Integer r8 = kotlin.coroutines.jvm.internal.Boxing.boxInt(r7)
            java.lang.Number r8 = (java.lang.Number) r8
            int r8 = r8.intValue()
            r9 = 0
            kotlinx.coroutines.flow.Flow[] r10 = r1.$flows
            r10 = r10[r8]
            kotlinx.coroutines.channels.ReceiveChannel r8 = kotlinx.coroutines.flow.internal.CombineKt.asFairChannel(r2, r10)
            r6[r7] = r8
            int r7 = r7 + 1
            goto L_0x0050
        L_0x006a:
            java.lang.Object[] r7 = new java.lang.Object[r5]
            java.lang.Boolean[] r8 = new java.lang.Boolean[r5]
            r9 = r4
        L_0x0070:
            if (r9 >= r5) goto L_0x0086
            java.lang.Integer r10 = kotlin.coroutines.jvm.internal.Boxing.boxInt(r9)
            java.lang.Number r10 = (java.lang.Number) r10
            int r10 = r10.intValue()
            r11 = 0
            java.lang.Boolean r10 = kotlin.coroutines.jvm.internal.Boxing.boxBoolean(r4)
            r8[r9] = r10
            int r9 = r9 + 1
            goto L_0x0070
        L_0x0086:
            r10 = r1
            r14 = r2
            r11 = r5
            r12 = r6
            r15 = r7
            r13 = r8
            r2 = r32
            r5 = r0
        L_0x008f:
            r0 = r13
            r6 = 0
            int r7 = r0.length
            r8 = r4
        L_0x0093:
            if (r8 >= r7) goto L_0x00ad
            r9 = r0[r8]
            boolean r16 = r9.booleanValue()
            r17 = 0
            java.lang.Boolean r16 = kotlin.coroutines.jvm.internal.Boxing.boxBoolean(r16)
            boolean r16 = r16.booleanValue()
            if (r16 != 0) goto L_0x00aa
            r0 = r4
            goto L_0x00ae
        L_0x00aa:
            int r8 = r8 + 1
            goto L_0x0093
        L_0x00ad:
            r0 = r3
        L_0x00ae:
            if (r0 != 0) goto L_0x0188
            r16 = 0
            r10.L$0 = r14
            r10.I$0 = r11
            r10.L$1 = r12
            r10.L$2 = r15
            r10.L$3 = r13
            r10.L$4 = r10
            r10.label = r3
            r9 = r10
            kotlin.coroutines.Continuation r9 = (kotlin.coroutines.Continuation) r9
            r17 = 0
            kotlinx.coroutines.selects.SelectBuilderImpl r0 = new kotlinx.coroutines.selects.SelectBuilderImpl
            r0.<init>(r9)
            r8 = r0
            r0 = r8
            kotlinx.coroutines.selects.SelectBuilder r0 = (kotlinx.coroutines.selects.SelectBuilder) r0     // Catch:{ all -> 0x0153 }
            r18 = 0
            r6 = r4
        L_0x00d2:
            if (r6 >= r11) goto L_0x0145
            r19 = r6
            r6 = r13[r19]     // Catch:{ all -> 0x0153 }
            boolean r20 = r6.booleanValue()     // Catch:{ all -> 0x0153 }
            r21 = r12[r19]     // Catch:{ all -> 0x0153 }
            kotlinx.coroutines.flow.internal.CombineKt$combineInternal$2$invokeSuspend$$inlined$select$lambda$1 r22 = new kotlinx.coroutines.flow.internal.CombineKt$combineInternal$2$invokeSuspend$$inlined$select$lambda$1     // Catch:{ all -> 0x0153 }
            r23 = 0
            r6 = r22
            r7 = r19
            r3 = r8
            r8 = r23
            r23 = r9
            r9 = r10
            r24 = r10
            r10 = r11
            r25 = r11
            r11 = r13
            r26 = r12
            r27 = r13
            r13 = r15
            r6.<init>(r7, r8, r9, r10, r11, r12, r13)     // Catch:{ all -> 0x0141 }
            kotlin.jvm.functions.Function2 r22 = (kotlin.jvm.functions.Function2) r22     // Catch:{ all -> 0x0141 }
            r7 = r22
            r13 = r0
            r22 = 0
            if (r20 == 0) goto L_0x0106
            r30 = r14
            goto L_0x012c
        L_0x0106:
            kotlinx.coroutines.selects.SelectClause1 r12 = r21.getOnReceiveOrNull()     // Catch:{ all -> 0x0141 }
            kotlinx.coroutines.flow.internal.CombineKt$combineInternal$2$invokeSuspend$$inlined$select$lambda$2 r28 = new kotlinx.coroutines.flow.internal.CombineKt$combineInternal$2$invokeSuspend$$inlined$select$lambda$2     // Catch:{ all -> 0x0141 }
            r8 = 0
            r6 = r28
            r9 = r19
            r10 = r24
            r11 = r25
            r4 = r12
            r12 = r27
            r29 = r13
            r13 = r26
            r30 = r14
            r14 = r15
            r6.<init>(r7, r8, r9, r10, r11, r12, r13, r14)     // Catch:{ all -> 0x013f }
            r6 = r28
            kotlin.jvm.functions.Function2 r6 = (kotlin.jvm.functions.Function2) r6     // Catch:{ all -> 0x013f }
            r8 = r29
            r8.invoke(r4, r6)     // Catch:{ all -> 0x013f }
        L_0x012c:
            int r6 = r19 + 1
            r8 = r3
            r9 = r23
            r10 = r24
            r11 = r25
            r12 = r26
            r13 = r27
            r14 = r30
            r3 = 1
            r4 = 0
            goto L_0x00d2
        L_0x013f:
            r0 = move-exception
            goto L_0x0161
        L_0x0141:
            r0 = move-exception
            r30 = r14
            goto L_0x0161
        L_0x0145:
            r3 = r8
            r23 = r9
            r24 = r10
            r25 = r11
            r26 = r12
            r27 = r13
            r30 = r14
            goto L_0x0164
        L_0x0153:
            r0 = move-exception
            r3 = r8
            r23 = r9
            r24 = r10
            r25 = r11
            r26 = r12
            r27 = r13
            r30 = r14
        L_0x0161:
            r3.handleBuilderException(r0)
        L_0x0164:
            java.lang.Object r0 = r3.getResult()
            java.lang.Object r3 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            if (r0 != r3) goto L_0x0176
            r3 = r24
            kotlin.coroutines.Continuation r3 = (kotlin.coroutines.Continuation) r3
            kotlin.coroutines.jvm.internal.DebugProbesKt.probeCoroutineSuspended(r3)
        L_0x0176:
            if (r0 != r5) goto L_0x0179
            return r5
        L_0x0179:
            r10 = r24
            r11 = r25
            r12 = r26
            r13 = r27
            r14 = r30
        L_0x0183:
            r3 = 1
            r4 = 0
            goto L_0x008f
        L_0x0188:
            kotlin.Unit r0 = kotlin.Unit.INSTANCE
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.flow.internal.CombineKt$combineInternal$2.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
