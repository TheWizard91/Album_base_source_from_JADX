package kotlinx.coroutines.flow;

import kotlin.Metadata;
import kotlin.jvm.functions.Function4;

@Metadata(mo33669bv = {1, 0, 3}, mo33670d1 = {"\u0000\u001d\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\b\u0002*\u0001\u0000\b\n\u0018\u00002\b\u0012\u0004\u0012\u00028\u00000\u0001J\u001f\u0010\u0002\u001a\u00020\u00032\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00028\u00000\u0005H@ø\u0001\u0000¢\u0006\u0002\u0010\u0006\u0002\u0004\n\u0002\b\u0019¨\u0006\u0007¸\u0006\u0000"}, mo33671d2 = {"kotlinx/coroutines/flow/internal/SafeCollectorKt$unsafeFlow$1", "Lkotlinx/coroutines/flow/Flow;", "collect", "", "collector", "Lkotlinx/coroutines/flow/FlowCollector;", "(Lkotlinx/coroutines/flow/FlowCollector;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "kotlinx-coroutines-core"}, mo33672k = 1, mo33673mv = {1, 1, 15})
/* compiled from: SafeCollector.kt */
public final class FlowKt__ErrorsKt$retryWhen$$inlined$unsafeFlow$1 implements Flow<T> {
    final /* synthetic */ Function4 $predicate$inlined;
    final /* synthetic */ Flow $this_retryWhen$inlined;

    public FlowKt__ErrorsKt$retryWhen$$inlined$unsafeFlow$1(Flow flow, Function4 function4) {
        this.$this_retryWhen$inlined = flow;
        this.$predicate$inlined = function4;
    }

    /* Debug info: failed to restart local var, previous not found, register: 19 */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r13v8, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r10v8, resolved type: kotlinx.coroutines.flow.FlowCollector} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r14v13, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r7v11, resolved type: java.lang.Throwable} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r14v14, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r10v10, resolved type: kotlinx.coroutines.flow.FlowCollector} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r15v10, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r13v16, resolved type: java.lang.Throwable} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r15v11, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r10v12, resolved type: kotlinx.coroutines.flow.FlowCollector} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:17:0x00b6  */
    /* JADX WARNING: Removed duplicated region for block: B:21:0x00e6  */
    /* JADX WARNING: Removed duplicated region for block: B:27:0x011f  */
    /* JADX WARNING: Removed duplicated region for block: B:29:0x0128 A[RETURN] */
    /* JADX WARNING: Removed duplicated region for block: B:32:0x0131  */
    /* JADX WARNING: Removed duplicated region for block: B:36:0x014c  */
    /* JADX WARNING: Removed duplicated region for block: B:38:0x0150  */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x002c  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.lang.Object collect(kotlinx.coroutines.flow.FlowCollector r20, kotlin.coroutines.Continuation r21) {
        /*
            r19 = this;
            r0 = r21
            boolean r1 = r0 instanceof kotlinx.coroutines.flow.FlowKt__ErrorsKt$retryWhen$$inlined$unsafeFlow$1.C17961
            if (r1 == 0) goto L_0x0018
            r1 = r0
            kotlinx.coroutines.flow.FlowKt__ErrorsKt$retryWhen$$inlined$unsafeFlow$1$1 r1 = (kotlinx.coroutines.flow.FlowKt__ErrorsKt$retryWhen$$inlined$unsafeFlow$1.C17961) r1
            int r2 = r1.label
            r3 = -2147483648(0xffffffff80000000, float:-0.0)
            r2 = r2 & r3
            if (r2 == 0) goto L_0x0018
            int r2 = r1.label
            int r2 = r2 - r3
            r1.label = r2
            r2 = r19
            goto L_0x001f
        L_0x0018:
            kotlinx.coroutines.flow.FlowKt__ErrorsKt$retryWhen$$inlined$unsafeFlow$1$1 r1 = new kotlinx.coroutines.flow.FlowKt__ErrorsKt$retryWhen$$inlined$unsafeFlow$1$1
            r2 = r19
            r1.<init>(r2, r0)
        L_0x001f:
            java.lang.Object r3 = r1.result
            java.lang.Object r4 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r5 = r1.label
            r6 = 3
            r7 = 2
            r8 = 1
            if (r5 == 0) goto L_0x00b6
            r9 = 0
            r10 = 0
            r11 = 0
            if (r5 == r8) goto L_0x0093
            if (r5 == r7) goto L_0x0068
            if (r5 != r6) goto L_0x0060
            r5 = r10
            r13 = r10
            r14 = r9
            java.lang.Object r15 = r1.L$4
            r13 = r15
            java.lang.Throwable r13 = (java.lang.Throwable) r13
            long r11 = r1.J$0
            java.lang.Object r15 = r1.L$3
            r10 = r15
            kotlinx.coroutines.flow.FlowCollector r10 = (kotlinx.coroutines.flow.FlowCollector) r10
            java.lang.Object r15 = r1.L$2
            r5 = r15
            kotlin.coroutines.Continuation r5 = (kotlin.coroutines.Continuation) r5
            java.lang.Object r15 = r1.L$1
            kotlinx.coroutines.flow.FlowCollector r15 = (kotlinx.coroutines.flow.FlowCollector) r15
            java.lang.Object r7 = r1.L$0
            kotlinx.coroutines.flow.FlowKt__ErrorsKt$retryWhen$$inlined$unsafeFlow$1 r7 = (kotlinx.coroutines.flow.FlowKt__ErrorsKt$retryWhen$$inlined$unsafeFlow$1) r7
            kotlin.ResultKt.throwOnFailure(r3)
            r6 = r3
            r8 = r15
            r3 = r1
            r15 = r7
            r1 = r0
            r7 = r5
            r0 = 2
            r5 = r4
            r4 = r6
            goto L_0x0129
        L_0x0060:
            java.lang.IllegalStateException r1 = new java.lang.IllegalStateException
            java.lang.String r3 = "call to 'resume' before 'invoke' with coroutine"
            r1.<init>(r3)
            throw r1
        L_0x0068:
            r5 = r10
            r7 = r10
            r13 = r9
            java.lang.Object r14 = r1.L$4
            r7 = r14
            java.lang.Throwable r7 = (java.lang.Throwable) r7
            long r11 = r1.J$0
            java.lang.Object r14 = r1.L$3
            r10 = r14
            kotlinx.coroutines.flow.FlowCollector r10 = (kotlinx.coroutines.flow.FlowCollector) r10
            java.lang.Object r14 = r1.L$2
            r5 = r14
            kotlin.coroutines.Continuation r5 = (kotlin.coroutines.Continuation) r5
            java.lang.Object r14 = r1.L$1
            kotlinx.coroutines.flow.FlowCollector r14 = (kotlinx.coroutines.flow.FlowCollector) r14
            java.lang.Object r15 = r1.L$0
            kotlinx.coroutines.flow.FlowKt__ErrorsKt$retryWhen$$inlined$unsafeFlow$1 r15 = (kotlinx.coroutines.flow.FlowKt__ErrorsKt$retryWhen$$inlined$unsafeFlow$1) r15
            kotlin.ResultKt.throwOnFailure(r3)
            r6 = r3
            r8 = r14
            r3 = r1
            r14 = r13
            r1 = r0
            r13 = r7
            r0 = 2
            r7 = r5
            r5 = r4
            r4 = r6
            goto L_0x0126
        L_0x0093:
            r5 = r10
            r7 = r9
            int r9 = r1.I$0
            long r11 = r1.J$0
            java.lang.Object r13 = r1.L$3
            r10 = r13
            kotlinx.coroutines.flow.FlowCollector r10 = (kotlinx.coroutines.flow.FlowCollector) r10
            java.lang.Object r13 = r1.L$2
            r5 = r13
            kotlin.coroutines.Continuation r5 = (kotlin.coroutines.Continuation) r5
            java.lang.Object r13 = r1.L$1
            kotlinx.coroutines.flow.FlowCollector r13 = (kotlinx.coroutines.flow.FlowCollector) r13
            java.lang.Object r14 = r1.L$0
            kotlinx.coroutines.flow.FlowKt__ErrorsKt$retryWhen$$inlined$unsafeFlow$1 r14 = (kotlinx.coroutines.flow.FlowKt__ErrorsKt$retryWhen$$inlined$unsafeFlow$1) r14
            kotlin.ResultKt.throwOnFailure(r3)
            r15 = r14
            r14 = r13
            r13 = r9
            r9 = r7
            r7 = r5
            r5 = r4
            r4 = r3
            goto L_0x00ee
        L_0x00b6:
            kotlin.ResultKt.throwOnFailure(r3)
            r5 = r1
            kotlin.coroutines.Continuation r5 = (kotlin.coroutines.Continuation) r5
            r7 = r20
            r9 = 0
            r10 = 0
            r12 = 0
            r14 = r2
            r13 = r12
            r11 = r10
            r10 = r7
            r7 = r5
            r5 = r4
            r4 = r3
            r3 = r1
            r1 = r0
            r0 = r20
        L_0x00cd:
            r13 = 0
            kotlinx.coroutines.flow.Flow r15 = r14.$this_retryWhen$inlined
            r3.L$0 = r14
            r3.L$1 = r0
            r3.L$2 = r7
            r3.L$3 = r10
            r3.J$0 = r11
            r3.I$0 = r13
            r3.label = r8
            java.lang.Object r15 = kotlinx.coroutines.flow.FlowKt.catchImpl(r15, r10, r3)
            if (r15 != r5) goto L_0x00e6
            return r5
        L_0x00e6:
            r18 = r14
            r14 = r0
            r0 = r1
            r1 = r3
            r3 = r15
            r15 = r18
        L_0x00ee:
            java.lang.Throwable r3 = (java.lang.Throwable) r3
            if (r3 == 0) goto L_0x0142
            kotlin.jvm.functions.Function4 r8 = r15.$predicate$inlined
            java.lang.Long r6 = kotlin.coroutines.jvm.internal.Boxing.boxLong(r11)
            r1.L$0 = r15
            r1.L$1 = r14
            r1.L$2 = r7
            r1.L$3 = r10
            r1.J$0 = r11
            r1.L$4 = r3
            r20 = r0
            r0 = 3
            r1.label = r0
            r1.L$0 = r15
            r1.L$1 = r14
            r1.L$2 = r7
            r1.L$3 = r10
            r1.J$0 = r11
            r1.L$4 = r3
            r0 = 2
            r1.label = r0
            java.lang.Object r6 = r8.invoke(r10, r3, r6, r1)
            if (r6 != r5) goto L_0x011f
            return r5
        L_0x011f:
            r8 = r14
            r14 = r9
            r9 = r13
            r13 = r3
            r3 = r1
            r1 = r20
        L_0x0126:
            if (r6 != r5) goto L_0x0129
            return r5
        L_0x0129:
            java.lang.Boolean r6 = (java.lang.Boolean) r6
            boolean r6 = r6.booleanValue()
            if (r6 == 0) goto L_0x0141
            r6 = 1
            r16 = 1
            long r11 = r11 + r16
            r9 = r14
            r14 = r8
            r18 = r4
            r4 = r3
            r3 = r13
            r13 = r6
            r6 = r5
            r5 = r18
            goto L_0x014a
        L_0x0141:
            throw r13
        L_0x0142:
            r20 = r0
            r0 = 2
            r6 = r5
            r5 = r4
            r4 = r1
            r1 = r20
        L_0x014a:
            if (r13 != 0) goto L_0x0150
            kotlin.Unit r0 = kotlin.Unit.INSTANCE
            return r0
        L_0x0150:
            r3 = r4
            r4 = r5
            r5 = r6
            r0 = r14
            r14 = r15
            r6 = 3
            r8 = 1
            goto L_0x00cd
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.flow.FlowKt__ErrorsKt$retryWhen$$inlined$unsafeFlow$1.collect(kotlinx.coroutines.flow.FlowCollector, kotlin.coroutines.Continuation):java.lang.Object");
    }
}
