package kotlinx.coroutines.flow;

import kotlin.Metadata;

@Metadata(mo33669bv = {1, 0, 3}, mo33670d1 = {"\u0000\u001d\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\b\u0002*\u0001\u0000\b\n\u0018\u00002\b\u0012\u0004\u0012\u00028\u00000\u0001J\u001f\u0010\u0002\u001a\u00020\u00032\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00028\u00000\u0005H@ø\u0001\u0000¢\u0006\u0002\u0010\u0006\u0002\u0004\n\u0002\b\u0019¨\u0006\u0007¸\u0006\u0000"}, mo33671d2 = {"kotlinx/coroutines/flow/internal/SafeCollectorKt$unsafeFlow$1", "Lkotlinx/coroutines/flow/Flow;", "collect", "", "collector", "Lkotlinx/coroutines/flow/FlowCollector;", "(Lkotlinx/coroutines/flow/FlowCollector;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "kotlinx-coroutines-core"}, mo33672k = 1, mo33673mv = {1, 1, 15})
/* compiled from: SafeCollector.kt */
public final class FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$1 implements Flow<T> {
    final /* synthetic */ Object[] $elements$inlined;

    /* Debug info: failed to restart local var, previous not found, register: 17 */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r13v3, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r5v8, resolved type: kotlinx.coroutines.flow.FlowCollector} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:12:0x0062  */
    /* JADX WARNING: Removed duplicated region for block: B:14:0x0079  */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x0029  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.lang.Object collect(kotlinx.coroutines.flow.FlowCollector r18, kotlin.coroutines.Continuation r19) {
        /*
            r17 = this;
            r0 = r17
            r1 = r19
            boolean r2 = r1 instanceof kotlinx.coroutines.flow.FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$1.C17651
            if (r2 == 0) goto L_0x0018
            r2 = r1
            kotlinx.coroutines.flow.FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$1$1 r2 = (kotlinx.coroutines.flow.FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$1.C17651) r2
            int r3 = r2.label
            r4 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r3 & r4
            if (r3 == 0) goto L_0x0018
            int r3 = r2.label
            int r3 = r3 - r4
            r2.label = r3
            goto L_0x001d
        L_0x0018:
            kotlinx.coroutines.flow.FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$1$1 r2 = new kotlinx.coroutines.flow.FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$1$1
            r2.<init>(r0, r1)
        L_0x001d:
            java.lang.Object r3 = r2.result
            java.lang.Object r4 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r5 = r2.label
            r6 = 0
            r7 = 1
            if (r5 == 0) goto L_0x0062
            if (r5 != r7) goto L_0x005a
            r5 = 0
            r8 = r5
            r9 = r5
            java.lang.Object r9 = r2.L$5
            int r10 = r2.I$1
            int r11 = r2.I$0
            java.lang.Object r12 = r2.L$4
            java.lang.Object[] r12 = (java.lang.Object[]) r12
            java.lang.Object r13 = r2.L$3
            r5 = r13
            kotlinx.coroutines.flow.FlowCollector r5 = (kotlinx.coroutines.flow.FlowCollector) r5
            java.lang.Object r13 = r2.L$2
            r8 = r13
            kotlin.coroutines.Continuation r8 = (kotlin.coroutines.Continuation) r8
            java.lang.Object r13 = r2.L$1
            kotlinx.coroutines.flow.FlowCollector r13 = (kotlinx.coroutines.flow.FlowCollector) r13
            java.lang.Object r14 = r2.L$0
            kotlinx.coroutines.flow.FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$1 r14 = (kotlinx.coroutines.flow.FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$1) r14
            kotlin.ResultKt.throwOnFailure(r3)
            r16 = r2
            r2 = r1
            r1 = r13
            r13 = r12
            r12 = r11
            r11 = r8
            r8 = r5
            r5 = r4
            r4 = r3
            r3 = r16
            goto L_0x009d
        L_0x005a:
            java.lang.IllegalStateException r2 = new java.lang.IllegalStateException
            java.lang.String r3 = "call to 'resume' before 'invoke' with coroutine"
            r2.<init>(r3)
            throw r2
        L_0x0062:
            kotlin.ResultKt.throwOnFailure(r3)
            r5 = r2
            kotlin.coroutines.Continuation r5 = (kotlin.coroutines.Continuation) r5
            r8 = r18
            r9 = 0
            java.lang.Object[] r10 = r0.$elements$inlined
            int r11 = r10.length
            r14 = r0
            r12 = r10
            r10 = r5
            r5 = r4
            r4 = r3
            r3 = r2
            r2 = r1
            r1 = r18
        L_0x0077:
            if (r6 >= r11) goto L_0x00a9
            r13 = r12[r6]
            r3.L$0 = r14
            r3.L$1 = r1
            r3.L$2 = r10
            r3.L$3 = r8
            r3.L$4 = r12
            r3.I$0 = r11
            r3.I$1 = r6
            r3.L$5 = r13
            r3.label = r7
            java.lang.Object r15 = r8.emit(r13, r3)
            if (r15 != r5) goto L_0x0094
            return r5
        L_0x0094:
            r16 = r10
            r10 = r6
            r6 = r9
            r9 = r13
            r13 = r12
            r12 = r11
            r11 = r16
        L_0x009d:
            int r9 = r10 + 1
            r10 = r11
            r11 = r12
            r12 = r13
            r16 = r9
            r9 = r6
            r6 = r16
            goto L_0x0077
        L_0x00a9:
            kotlin.Unit r5 = kotlin.Unit.INSTANCE
            return r5
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.flow.FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$1.collect(kotlinx.coroutines.flow.FlowCollector, kotlin.coroutines.Continuation):java.lang.Object");
    }

    public FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$1(Object[] objArr) {
        this.$elements$inlined = objArr;
    }
}
