package kotlinx.coroutines.flow.internal;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function4;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;

@Metadata(mo33669bv = {1, 0, 3}, mo33670d1 = {"\u0000\u0012\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\u0010\u0000\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003\"\u0004\b\u0002\u0010\u0004*\u00020\u0005H@¢\u0006\u0004\b\u0006\u0010\u0007"}, mo33671d2 = {"<anonymous>", "", "T1", "T2", "R", "Lkotlinx/coroutines/CoroutineScope;", "invoke", "(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;"}, mo33672k = 3, mo33673mv = {1, 1, 15})
@DebugMetadata(mo34304c = "kotlinx.coroutines.flow.internal.CombineKt$combineTransformInternal$2", mo34305f = "Combine.kt", mo34306i = {0, 0, 0, 0, 0, 0, 0}, mo34307l = {144}, mo34308m = "invokeSuspend", mo34309n = {"$this$coroutineScope", "firstChannel", "secondChannel", "firstValue", "secondValue", "firstIsClosed", "secondIsClosed"}, mo34310s = {"L$0", "L$1", "L$2", "L$3", "L$4", "L$5", "L$6"})
/* compiled from: Combine.kt */
final class CombineKt$combineTransformInternal$2 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
    final /* synthetic */ Flow $first;
    final /* synthetic */ Flow $second;
    final /* synthetic */ FlowCollector $this_combineTransformInternal;
    final /* synthetic */ Function4 $transform;
    Object L$0;
    Object L$1;
    Object L$2;
    Object L$3;
    Object L$4;
    Object L$5;
    Object L$6;
    Object L$7;
    int label;

    /* renamed from: p$ */
    private CoroutineScope f702p$;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    CombineKt$combineTransformInternal$2(FlowCollector flowCollector, Flow flow, Flow flow2, Function4 function4, Continuation continuation) {
        super(2, continuation);
        this.$this_combineTransformInternal = flowCollector;
        this.$first = flow;
        this.$second = flow2;
        this.$transform = function4;
    }

    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        Intrinsics.checkParameterIsNotNull(continuation, "completion");
        CombineKt$combineTransformInternal$2 combineKt$combineTransformInternal$2 = new CombineKt$combineTransformInternal$2(this.$this_combineTransformInternal, this.$first, this.$second, this.$transform, continuation);
        CoroutineScope coroutineScope = (CoroutineScope) obj;
        combineKt$combineTransformInternal$2.f702p$ = (CoroutineScope) obj;
        return combineKt$combineTransformInternal$2;
    }

    public final Object invoke(Object obj, Object obj2) {
        return ((CombineKt$combineTransformInternal$2) create(obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r11v10, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r8v10, resolved type: kotlin.jvm.internal.Ref$BooleanRef} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r11v11, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r10v10, resolved type: kotlin.jvm.internal.Ref$BooleanRef} */
    /* JADX WARNING: Can't wrap try/catch for region: R(21:0|(1:(2:3|50)(2:4|5))(1:6)|7|(15:13|14|15|16|17|18|(1:20)(4:21|22|23|24)|25|26|27|(1:29)(4:30|31|32|33)|44|(1:46)|(1:48)(4:49|50|7|(0)(2:11|12))|48)(0)|9|13|14|15|16|17|18|(0)(0)|25|26|27|(0)(0)|44|(0)|(0)|48|(1:(5:43|44|(0)|(0)(0)|48))) */
    /* JADX WARNING: Can't wrap try/catch for region: R(4:21|22|23|24) */
    /* JADX WARNING: Can't wrap try/catch for region: R(4:30|31|32|33) */
    /* JADX WARNING: Code restructure failed: missing block: B:34:0x0178, code lost:
        r0 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:35:0x017a, code lost:
        r0 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:36:0x017b, code lost:
        r29 = r15;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:37:0x017e, code lost:
        r0 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:38:0x017f, code lost:
        r29 = r35;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:39:0x0182, code lost:
        r0 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:40:0x0183, code lost:
        r29 = r35;
        r31 = r15;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:41:0x0188, code lost:
        r0 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:42:0x0189, code lost:
        r3 = r7;
        r20 = r8;
        r22 = r9;
        r23 = r10;
        r24 = r11;
        r29 = r12;
        r25 = r13;
        r26 = r14;
        r31 = r15;
     */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:11:0x0096  */
    /* JADX WARNING: Removed duplicated region for block: B:13:0x0099  */
    /* JADX WARNING: Removed duplicated region for block: B:20:0x00f2 A[Catch:{ all -> 0x0182 }] */
    /* JADX WARNING: Removed duplicated region for block: B:21:0x00f5 A[Catch:{ all -> 0x0182 }] */
    /* JADX WARNING: Removed duplicated region for block: B:29:0x0148 A[Catch:{ all -> 0x017a }] */
    /* JADX WARNING: Removed duplicated region for block: B:30:0x014b A[Catch:{ all -> 0x017a }] */
    /* JADX WARNING: Removed duplicated region for block: B:46:0x01a8  */
    /* JADX WARNING: Removed duplicated region for block: B:48:0x01b1 A[RETURN] */
    /* JADX WARNING: Removed duplicated region for block: B:49:0x01b2  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final java.lang.Object invokeSuspend(java.lang.Object r35) {
        /*
            r34 = this;
            r1 = r34
            java.lang.Object r0 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r2 = r1.label
            r3 = 1
            r4 = 0
            r5 = 0
            if (r2 == 0) goto L_0x0053
            if (r2 != r3) goto L_0x004b
            r2 = r5
            r6 = r5
            r7 = r5
            r8 = r5
            r9 = r5
            r10 = r5
            java.lang.Object r11 = r1.L$7
            kotlinx.coroutines.flow.internal.CombineKt$combineTransformInternal$2 r11 = (kotlinx.coroutines.flow.internal.CombineKt$combineTransformInternal$2) r11
            java.lang.Object r11 = r1.L$6
            r8 = r11
            kotlin.jvm.internal.Ref$BooleanRef r8 = (kotlin.jvm.internal.Ref.BooleanRef) r8
            java.lang.Object r11 = r1.L$5
            r10 = r11
            kotlin.jvm.internal.Ref$BooleanRef r10 = (kotlin.jvm.internal.Ref.BooleanRef) r10
            java.lang.Object r11 = r1.L$4
            r6 = r11
            kotlin.jvm.internal.Ref$ObjectRef r6 = (kotlin.jvm.internal.Ref.ObjectRef) r6
            java.lang.Object r11 = r1.L$3
            r5 = r11
            kotlin.jvm.internal.Ref$ObjectRef r5 = (kotlin.jvm.internal.Ref.ObjectRef) r5
            java.lang.Object r11 = r1.L$2
            r7 = r11
            kotlinx.coroutines.channels.ReceiveChannel r7 = (kotlinx.coroutines.channels.ReceiveChannel) r7
            java.lang.Object r11 = r1.L$1
            r9 = r11
            kotlinx.coroutines.channels.ReceiveChannel r9 = (kotlinx.coroutines.channels.ReceiveChannel) r9
            java.lang.Object r11 = r1.L$0
            r2 = r11
            kotlinx.coroutines.CoroutineScope r2 = (kotlinx.coroutines.CoroutineScope) r2
            kotlin.ResultKt.throwOnFailure(r35)
            r4 = r0
            r15 = r5
            r14 = r6
            r13 = r7
            r12 = r8
            r11 = r9
            r9 = r1
            r5 = r2
            r2 = r35
            goto L_0x01c0
        L_0x004b:
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
            java.lang.String r2 = "call to 'resume' before 'invoke' with coroutine"
            r0.<init>(r2)
            throw r0
        L_0x0053:
            kotlin.ResultKt.throwOnFailure(r35)
            kotlinx.coroutines.CoroutineScope r2 = r1.f702p$
            kotlinx.coroutines.flow.Flow r6 = r1.$first
            kotlinx.coroutines.channels.ReceiveChannel r6 = kotlinx.coroutines.flow.internal.CombineKt.asFairChannel(r2, r6)
            kotlinx.coroutines.flow.Flow r7 = r1.$second
            kotlinx.coroutines.channels.ReceiveChannel r7 = kotlinx.coroutines.flow.internal.CombineKt.asFairChannel(r2, r7)
            kotlin.jvm.internal.Ref$ObjectRef r8 = new kotlin.jvm.internal.Ref$ObjectRef
            r8.<init>()
            r8.element = r5
            kotlin.jvm.internal.Ref$ObjectRef r9 = new kotlin.jvm.internal.Ref$ObjectRef
            r9.<init>()
            r9.element = r5
            r5 = r9
            kotlin.jvm.internal.Ref$BooleanRef r9 = new kotlin.jvm.internal.Ref$BooleanRef
            r9.<init>()
            r9.element = r4
            kotlin.jvm.internal.Ref$BooleanRef r10 = new kotlin.jvm.internal.Ref$BooleanRef
            r10.<init>()
            r10.element = r4
            r4 = r10
            r12 = r4
            r14 = r5
            r11 = r6
            r13 = r7
            r15 = r8
            r10 = r9
            r4 = r0
            r9 = r1
            r5 = r2
            r2 = r35
        L_0x008d:
            boolean r0 = r10.element
            if (r0 == 0) goto L_0x0099
            boolean r0 = r12.element
            if (r0 != 0) goto L_0x0096
            goto L_0x0099
        L_0x0096:
            kotlin.Unit r0 = kotlin.Unit.INSTANCE
            return r0
        L_0x0099:
            r16 = 0
            r9.L$0 = r5
            r9.L$1 = r11
            r9.L$2 = r13
            r9.L$3 = r15
            r9.L$4 = r14
            r9.L$5 = r10
            r9.L$6 = r12
            r9.L$7 = r9
            r9.label = r3
            r8 = r9
            kotlin.coroutines.Continuation r8 = (kotlin.coroutines.Continuation) r8
            r17 = 0
            kotlinx.coroutines.selects.SelectBuilderImpl r0 = new kotlinx.coroutines.selects.SelectBuilderImpl
            r0.<init>(r8)
            r7 = r0
            r0 = r7
            kotlinx.coroutines.selects.SelectBuilder r0 = (kotlinx.coroutines.selects.SelectBuilder) r0     // Catch:{ all -> 0x0188 }
            r18 = 0
            boolean r6 = r10.element     // Catch:{ all -> 0x0188 }
            kotlinx.coroutines.flow.internal.CombineKt$combineTransformInternal$2$invokeSuspend$$inlined$select$lambda$1 r19 = new kotlinx.coroutines.flow.internal.CombineKt$combineTransformInternal$2$invokeSuspend$$inlined$select$lambda$1     // Catch:{ all -> 0x0188 }
            r20 = 0
            r21 = r6
            r6 = r19
            r3 = r7
            r7 = r20
            r20 = r8
            r8 = r9
            r22 = r9
            r9 = r10
            r23 = r10
            r10 = r11
            r24 = r11
            r11 = r15
            r35 = r12
            r12 = r14
            r25 = r13
            r13 = r35
            r26 = r14
            r14 = r25
            r6.<init>(r7, r8, r9, r10, r11, r12, r13, r14)     // Catch:{ all -> 0x0182 }
            kotlin.jvm.functions.Function2 r19 = (kotlin.jvm.functions.Function2) r19     // Catch:{ all -> 0x0182 }
            r7 = r19
            r14 = r0
            r19 = r21
            r21 = r24
            r27 = 0
            if (r19 == 0) goto L_0x00f5
            r31 = r15
            goto L_0x0120
        L_0x00f5:
            kotlinx.coroutines.selects.SelectClause1 r13 = r21.getOnReceiveOrNull()     // Catch:{ all -> 0x0182 }
            kotlinx.coroutines.flow.internal.CombineKt$combineTransformInternal$2$invokeSuspend$$inlined$select$lambda$2 r28 = new kotlinx.coroutines.flow.internal.CombineKt$combineTransformInternal$2$invokeSuspend$$inlined$select$lambda$2     // Catch:{ all -> 0x0182 }
            r8 = 0
            r6 = r28
            r9 = r22
            r10 = r23
            r11 = r24
            r12 = r15
            r29 = r13
            r13 = r26
            r30 = r14
            r14 = r35
            r31 = r15
            r15 = r25
            r6.<init>(r7, r8, r9, r10, r11, r12, r13, r14, r15)     // Catch:{ all -> 0x017e }
            r6 = r28
            kotlin.jvm.functions.Function2 r6 = (kotlin.jvm.functions.Function2) r6     // Catch:{ all -> 0x017e }
            r9 = r29
            r8 = r30
            r8.invoke(r9, r6)     // Catch:{ all -> 0x017e }
        L_0x0120:
            r15 = r35
            boolean r14 = r15.element     // Catch:{ all -> 0x017a }
            kotlinx.coroutines.flow.internal.CombineKt$combineTransformInternal$2$invokeSuspend$$inlined$select$lambda$3 r19 = new kotlinx.coroutines.flow.internal.CombineKt$combineTransformInternal$2$invokeSuspend$$inlined$select$lambda$3     // Catch:{ all -> 0x017a }
            r7 = 0
            r6 = r19
            r8 = r22
            r9 = r23
            r10 = r24
            r11 = r31
            r12 = r26
            r13 = r15
            r21 = r14
            r14 = r25
            r6.<init>(r7, r8, r9, r10, r11, r12, r13, r14)     // Catch:{ all -> 0x017a }
            kotlin.jvm.functions.Function2 r19 = (kotlin.jvm.functions.Function2) r19     // Catch:{ all -> 0x017a }
            r7 = r19
            r14 = r0
            r19 = r21
            r21 = r25
            r27 = 0
            if (r19 == 0) goto L_0x014b
            r29 = r15
            goto L_0x0176
        L_0x014b:
            kotlinx.coroutines.selects.SelectClause1 r13 = r21.getOnReceiveOrNull()     // Catch:{ all -> 0x017a }
            kotlinx.coroutines.flow.internal.CombineKt$combineTransformInternal$2$invokeSuspend$$inlined$select$lambda$4 r28 = new kotlinx.coroutines.flow.internal.CombineKt$combineTransformInternal$2$invokeSuspend$$inlined$select$lambda$4     // Catch:{ all -> 0x017a }
            r8 = 0
            r6 = r28
            r9 = r22
            r10 = r23
            r11 = r24
            r12 = r31
            r32 = r13
            r13 = r26
            r33 = r14
            r14 = r15
            r29 = r15
            r15 = r25
            r6.<init>(r7, r8, r9, r10, r11, r12, r13, r14, r15)     // Catch:{ all -> 0x0178 }
            r6 = r28
            kotlin.jvm.functions.Function2 r6 = (kotlin.jvm.functions.Function2) r6     // Catch:{ all -> 0x0178 }
            r9 = r32
            r8 = r33
            r8.invoke(r9, r6)     // Catch:{ all -> 0x0178 }
        L_0x0176:
            goto L_0x019d
        L_0x0178:
            r0 = move-exception
            goto L_0x019a
        L_0x017a:
            r0 = move-exception
            r29 = r15
            goto L_0x019a
        L_0x017e:
            r0 = move-exception
            r29 = r35
            goto L_0x019a
        L_0x0182:
            r0 = move-exception
            r29 = r35
            r31 = r15
            goto L_0x019a
        L_0x0188:
            r0 = move-exception
            r3 = r7
            r20 = r8
            r22 = r9
            r23 = r10
            r24 = r11
            r29 = r12
            r25 = r13
            r26 = r14
            r31 = r15
        L_0x019a:
            r3.handleBuilderException(r0)
        L_0x019d:
            java.lang.Object r0 = r3.getResult()
            java.lang.Object r3 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            if (r0 != r3) goto L_0x01af
            r3 = r22
            kotlin.coroutines.Continuation r3 = (kotlin.coroutines.Continuation) r3
            kotlin.coroutines.jvm.internal.DebugProbesKt.probeCoroutineSuspended(r3)
        L_0x01af:
            if (r0 != r4) goto L_0x01b2
            return r4
        L_0x01b2:
            r9 = r22
            r10 = r23
            r11 = r24
            r13 = r25
            r14 = r26
            r12 = r29
            r15 = r31
        L_0x01c0:
            r3 = 1
            goto L_0x008d
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.flow.internal.CombineKt$combineTransformInternal$2.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
