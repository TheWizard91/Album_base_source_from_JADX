package kotlinx.coroutines.flow;

import kotlin.Metadata;
import kotlin.jvm.functions.Function3;

@Metadata(mo33669bv = {1, 0, 3}, mo33670d1 = {"\u0000\u001d\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\b\u0002*\u0001\u0000\b\n\u0018\u00002\b\u0012\u0004\u0012\u00028\u00000\u0001J\u001f\u0010\u0002\u001a\u00020\u00032\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00028\u00000\u0005H@ø\u0001\u0000¢\u0006\u0002\u0010\u0006\u0002\u0004\n\u0002\b\u0019¨\u0006\u0007¸\u0006\u0000"}, mo33671d2 = {"kotlinx/coroutines/flow/internal/SafeCollectorKt$unsafeFlow$1", "Lkotlinx/coroutines/flow/Flow;", "collect", "", "collector", "Lkotlinx/coroutines/flow/FlowCollector;", "(Lkotlinx/coroutines/flow/FlowCollector;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "kotlinx-coroutines-core"}, mo33672k = 1, mo33673mv = {1, 1, 15})
/* compiled from: SafeCollector.kt */
public final class FlowKt__EmittersKt$onCompletion$$inlined$unsafeFlow$1 implements Flow<T> {
    final /* synthetic */ Function3 $action$inlined;
    final /* synthetic */ Flow $this_onCompletion$inlined;

    public FlowKt__EmittersKt$onCompletion$$inlined$unsafeFlow$1(Flow flow, Function3 function3) {
        this.$this_onCompletion$inlined = flow;
        this.$action$inlined = function3;
    }

    /* Debug info: failed to restart local var, previous not found, register: 13 */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r9v5, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r8v13, resolved type: java.lang.Throwable} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r9v6, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r7v8, resolved type: kotlinx.coroutines.flow.FlowCollector} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r9v8, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r14v3, resolved type: kotlinx.coroutines.flow.FlowCollector} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r6v12, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v7, resolved type: java.lang.Throwable} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r7v11, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v10, resolved type: java.lang.Throwable} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:22:0x00a6  */
    /* JADX WARNING: Removed duplicated region for block: B:32:0x00ed A[RETURN] */
    /* JADX WARNING: Removed duplicated region for block: B:33:0x00ee  */
    /* JADX WARNING: Removed duplicated region for block: B:35:0x00f4  */
    /* JADX WARNING: Removed duplicated region for block: B:37:0x00f9  */
    /* JADX WARNING: Removed duplicated region for block: B:45:0x0128 A[RETURN] */
    /* JADX WARNING: Removed duplicated region for block: B:46:0x0129  */
    /* JADX WARNING: Removed duplicated region for block: B:48:0x012f  */
    /* JADX WARNING: Removed duplicated region for block: B:50:0x0132  */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x0027  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.lang.Object collect(kotlinx.coroutines.flow.FlowCollector r14, kotlin.coroutines.Continuation r15) {
        /*
            r13 = this;
            boolean r0 = r15 instanceof kotlinx.coroutines.flow.FlowKt__EmittersKt$onCompletion$$inlined$unsafeFlow$1.C17881
            if (r0 == 0) goto L_0x0014
            r0 = r15
            kotlinx.coroutines.flow.FlowKt__EmittersKt$onCompletion$$inlined$unsafeFlow$1$1 r0 = (kotlinx.coroutines.flow.FlowKt__EmittersKt$onCompletion$$inlined$unsafeFlow$1.C17881) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r1 = r1 & r2
            if (r1 == 0) goto L_0x0014
            int r1 = r0.label
            int r1 = r1 - r2
            r0.label = r1
            goto L_0x0019
        L_0x0014:
            kotlinx.coroutines.flow.FlowKt__EmittersKt$onCompletion$$inlined$unsafeFlow$1$1 r0 = new kotlinx.coroutines.flow.FlowKt__EmittersKt$onCompletion$$inlined$unsafeFlow$1$1
            r0.<init>(r13, r15)
        L_0x0019:
            java.lang.Object r1 = r0.result
            java.lang.Object r2 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r3 = r0.label
            r4 = 3
            r5 = 2
            r6 = 1
            r7 = 0
            if (r3 == 0) goto L_0x00a6
            r8 = 0
            if (r3 == r6) goto L_0x007c
            if (r3 == r5) goto L_0x005b
            if (r3 != r4) goto L_0x0053
            r2 = r7
            r3 = r8
            r4 = r7
            r5 = r7
            java.lang.Object r6 = r0.L$5
            java.lang.Throwable r6 = (java.lang.Throwable) r6
            java.lang.Object r7 = r0.L$4
            r4 = r7
            java.lang.Throwable r4 = (java.lang.Throwable) r4
            java.lang.Object r7 = r0.L$3
            r5 = r7
            kotlinx.coroutines.flow.FlowCollector r5 = (kotlinx.coroutines.flow.FlowCollector) r5
            java.lang.Object r7 = r0.L$2
            r2 = r7
            kotlin.coroutines.Continuation r2 = (kotlin.coroutines.Continuation) r2
            java.lang.Object r7 = r0.L$1
            r14 = r7
            kotlinx.coroutines.flow.FlowCollector r14 = (kotlinx.coroutines.flow.FlowCollector) r14
            java.lang.Object r7 = r0.L$0
            kotlinx.coroutines.flow.FlowKt__EmittersKt$onCompletion$$inlined$unsafeFlow$1 r7 = (kotlinx.coroutines.flow.FlowKt__EmittersKt$onCompletion$$inlined$unsafeFlow$1) r7
            kotlin.ResultKt.throwOnFailure(r1)
            goto L_0x012d
        L_0x0053:
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
            java.lang.String r1 = "call to 'resume' before 'invoke' with coroutine"
            r0.<init>(r1)
            throw r0
        L_0x005b:
            r2 = r7
            r3 = r8
            r4 = r7
            r5 = r7
            java.lang.Object r6 = r0.L$4
            r4 = r6
            java.lang.Throwable r4 = (java.lang.Throwable) r4
            java.lang.Object r6 = r0.L$3
            r5 = r6
            kotlinx.coroutines.flow.FlowCollector r5 = (kotlinx.coroutines.flow.FlowCollector) r5
            java.lang.Object r6 = r0.L$2
            r2 = r6
            kotlin.coroutines.Continuation r2 = (kotlin.coroutines.Continuation) r2
            java.lang.Object r6 = r0.L$1
            r14 = r6
            kotlinx.coroutines.flow.FlowCollector r14 = (kotlinx.coroutines.flow.FlowCollector) r14
            java.lang.Object r6 = r0.L$0
            kotlinx.coroutines.flow.FlowKt__EmittersKt$onCompletion$$inlined$unsafeFlow$1 r6 = (kotlinx.coroutines.flow.FlowKt__EmittersKt$onCompletion$$inlined$unsafeFlow$1) r6
            kotlin.ResultKt.throwOnFailure(r1)
            goto L_0x00f2
        L_0x007c:
            r3 = r7
            r6 = r8
            r8 = r7
            java.lang.Object r9 = r0.L$4
            r8 = r9
            java.lang.Throwable r8 = (java.lang.Throwable) r8
            java.lang.Object r9 = r0.L$3
            r7 = r9
            kotlinx.coroutines.flow.FlowCollector r7 = (kotlinx.coroutines.flow.FlowCollector) r7
            java.lang.Object r9 = r0.L$2
            r3 = r9
            kotlin.coroutines.Continuation r3 = (kotlin.coroutines.Continuation) r3
            java.lang.Object r9 = r0.L$1
            r14 = r9
            kotlinx.coroutines.flow.FlowCollector r14 = (kotlinx.coroutines.flow.FlowCollector) r14
            java.lang.Object r9 = r0.L$0
            kotlinx.coroutines.flow.FlowKt__EmittersKt$onCompletion$$inlined$unsafeFlow$1 r9 = (kotlinx.coroutines.flow.FlowKt__EmittersKt$onCompletion$$inlined$unsafeFlow$1) r9
            kotlin.ResultKt.throwOnFailure(r1)     // Catch:{ all -> 0x009d }
            r10 = r8
            r8 = r1
            goto L_0x00cb
        L_0x009d:
            r5 = move-exception
            r12 = r6
            r6 = r5
            r5 = r7
            r7 = r8
            r8 = r9
            r9 = r12
            goto L_0x0107
        L_0x00a6:
            kotlin.ResultKt.throwOnFailure(r1)
            r3 = r0
            kotlin.coroutines.Continuation r3 = (kotlin.coroutines.Continuation) r3
            r8 = r14
            r9 = 0
            java.lang.Throwable r7 = (java.lang.Throwable) r7
            kotlinx.coroutines.flow.Flow r10 = r13.$this_onCompletion$inlined     // Catch:{ all -> 0x0103 }
            r0.L$0 = r13     // Catch:{ all -> 0x0103 }
            r0.L$1 = r14     // Catch:{ all -> 0x0103 }
            r0.L$2 = r3     // Catch:{ all -> 0x0103 }
            r0.L$3 = r8     // Catch:{ all -> 0x0103 }
            r0.L$4 = r7     // Catch:{ all -> 0x0103 }
            r0.label = r6     // Catch:{ all -> 0x0103 }
            java.lang.Object r6 = kotlinx.coroutines.flow.FlowKt.catchImpl(r10, r8, r0)     // Catch:{ all -> 0x0103 }
            if (r6 != r2) goto L_0x00c6
            return r2
        L_0x00c6:
            r10 = r7
            r7 = r8
            r8 = r6
            r6 = r9
            r9 = r13
        L_0x00cb:
            java.lang.Throwable r8 = (java.lang.Throwable) r8     // Catch:{ all -> 0x00fc }
            r4 = r8
            kotlin.coroutines.CoroutineContext r8 = r0.getContext()
            kotlinx.coroutines.flow.internal.SafeCollector r10 = new kotlinx.coroutines.flow.internal.SafeCollector
            r10.<init>(r7, r8)
            kotlinx.coroutines.flow.FlowCollector r10 = (kotlinx.coroutines.flow.FlowCollector) r10
            kotlin.jvm.functions.Function3 r8 = r9.$action$inlined
            r0.L$0 = r9
            r0.L$1 = r14
            r0.L$2 = r3
            r0.L$3 = r7
            r0.L$4 = r4
            r0.label = r5
            java.lang.Object r5 = kotlinx.coroutines.flow.FlowKt__EmittersKt.invokeSafely$FlowKt__EmittersKt(r10, r8, r4, r0)
            if (r5 != r2) goto L_0x00ee
            return r2
        L_0x00ee:
            r2 = r3
            r3 = r6
            r5 = r7
            r6 = r9
        L_0x00f2:
            if (r4 != 0) goto L_0x00f9
            kotlin.Unit r2 = kotlin.Unit.INSTANCE
            return r2
        L_0x00f9:
            r7 = r4
            r8 = 0
            throw r7
        L_0x00fc:
            r5 = move-exception
            r8 = r9
            r9 = r6
            r6 = r5
            r5 = r7
            r7 = r10
            goto L_0x0107
        L_0x0103:
            r5 = move-exception
            r6 = r5
            r5 = r8
            r8 = r13
        L_0x0107:
            kotlin.coroutines.CoroutineContext r10 = r0.getContext()
            kotlinx.coroutines.flow.internal.SafeCollector r11 = new kotlinx.coroutines.flow.internal.SafeCollector
            r11.<init>(r5, r10)
            kotlinx.coroutines.flow.FlowCollector r11 = (kotlinx.coroutines.flow.FlowCollector) r11
            kotlin.jvm.functions.Function3 r10 = r8.$action$inlined
            r0.L$0 = r8
            r0.L$1 = r14
            r0.L$2 = r3
            r0.L$3 = r5
            r0.L$4 = r7
            r0.L$5 = r6
            r0.label = r4
            java.lang.Object r4 = kotlinx.coroutines.flow.FlowKt__EmittersKt.invokeSafely$FlowKt__EmittersKt(r11, r10, r7, r0)
            if (r4 != r2) goto L_0x0129
            return r2
        L_0x0129:
            r2 = r3
            r4 = r7
            r7 = r8
            r3 = r9
        L_0x012d:
            if (r4 == 0) goto L_0x0132
            r6 = r4
            r8 = 0
            throw r6
        L_0x0132:
            throw r6
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.flow.FlowKt__EmittersKt$onCompletion$$inlined$unsafeFlow$1.collect(kotlinx.coroutines.flow.FlowCollector, kotlin.coroutines.Continuation):java.lang.Object");
    }
}
