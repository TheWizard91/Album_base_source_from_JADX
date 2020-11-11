package kotlinx.coroutines.flow;

import kotlin.Metadata;

@Metadata(mo33669bv = {1, 0, 3}, mo33670d1 = {"\u0000\u001d\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\b\u0002*\u0001\u0000\b\n\u0018\u00002\b\u0012\u0004\u0012\u00028\u00000\u0001J\u001f\u0010\u0002\u001a\u00020\u00032\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00028\u00000\u0005H@ø\u0001\u0000¢\u0006\u0002\u0010\u0006\u0002\u0004\n\u0002\b\u0019¨\u0006\u0007¸\u0006\u0000"}, mo33671d2 = {"kotlinx/coroutines/flow/internal/SafeCollectorKt$unsafeFlow$1", "Lkotlinx/coroutines/flow/Flow;", "collect", "", "collector", "Lkotlinx/coroutines/flow/FlowCollector;", "(Lkotlinx/coroutines/flow/FlowCollector;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "kotlinx-coroutines-core"}, mo33672k = 1, mo33673mv = {1, 1, 15})
/* compiled from: SafeCollector.kt */
public final class FlowKt__BuildersKt$asFlow$$inlined$unsafeFlow$7 implements Flow<Integer> {
    final /* synthetic */ int[] $this_asFlow$inlined;

    public FlowKt__BuildersKt$asFlow$$inlined$unsafeFlow$7(int[] iArr) {
        this.$this_asFlow$inlined = iArr;
    }

    /* Debug info: failed to restart local var, previous not found, register: 19 */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r12v2, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r5v4, resolved type: kotlinx.coroutines.flow.FlowCollector} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:12:0x0076  */
    /* JADX WARNING: Removed duplicated region for block: B:14:0x0094  */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x0029  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.lang.Object collect(kotlinx.coroutines.flow.FlowCollector r20, kotlin.coroutines.Continuation r21) {
        /*
            r19 = this;
            r0 = r19
            r1 = r21
            boolean r2 = r1 instanceof kotlinx.coroutines.flow.FlowKt__BuildersKt$asFlow$$inlined$unsafeFlow$7.C17621
            if (r2 == 0) goto L_0x0018
            r2 = r1
            kotlinx.coroutines.flow.FlowKt__BuildersKt$asFlow$$inlined$unsafeFlow$7$1 r2 = (kotlinx.coroutines.flow.FlowKt__BuildersKt$asFlow$$inlined$unsafeFlow$7.C17621) r2
            int r3 = r2.label
            r4 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r3 & r4
            if (r3 == 0) goto L_0x0018
            int r3 = r2.label
            int r3 = r3 - r4
            r2.label = r3
            goto L_0x001d
        L_0x0018:
            kotlinx.coroutines.flow.FlowKt__BuildersKt$asFlow$$inlined$unsafeFlow$7$1 r2 = new kotlinx.coroutines.flow.FlowKt__BuildersKt$asFlow$$inlined$unsafeFlow$7$1
            r2.<init>(r0, r1)
        L_0x001d:
            java.lang.Object r3 = r2.result
            java.lang.Object r4 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r5 = r2.label
            r6 = 1
            r7 = 0
            if (r5 == 0) goto L_0x0076
            if (r5 != r6) goto L_0x006e
            r5 = 0
            r8 = r5
            r9 = r7
            r10 = r7
            r11 = r7
            r12 = r5
            r13 = r7
            int r9 = r2.I$3
            int r10 = r2.I$2
            int r14 = r2.I$1
            int r15 = r2.I$0
            java.lang.Object r6 = r2.L$5
            int[] r6 = (int[]) r6
            java.lang.Object r1 = r2.L$4
            int[] r1 = (int[]) r1
            java.lang.Object r12 = r2.L$3
            r5 = r12
            kotlinx.coroutines.flow.FlowCollector r5 = (kotlinx.coroutines.flow.FlowCollector) r5
            java.lang.Object r12 = r2.L$2
            r8 = r12
            kotlin.coroutines.Continuation r8 = (kotlin.coroutines.Continuation) r8
            java.lang.Object r12 = r2.L$1
            kotlinx.coroutines.flow.FlowCollector r12 = (kotlinx.coroutines.flow.FlowCollector) r12
            r17 = r1
            java.lang.Object r1 = r2.L$0
            kotlinx.coroutines.flow.FlowKt__BuildersKt$asFlow$$inlined$unsafeFlow$7 r1 = (kotlinx.coroutines.flow.FlowKt__BuildersKt$asFlow$$inlined$unsafeFlow$7) r1
            kotlin.ResultKt.throwOnFailure(r3)
            r16 = r15
            r0 = 1
            r15 = r14
            r14 = r10
            r10 = r6
            r6 = r4
            r4 = r2
            r2 = r21
            r18 = r3
            r3 = r1
            r1 = r12
            r12 = r11
            r11 = r8
            r8 = r5
            r5 = r18
            goto L_0x00d7
        L_0x006e:
            java.lang.IllegalStateException r1 = new java.lang.IllegalStateException
            java.lang.String r2 = "call to 'resume' before 'invoke' with coroutine"
            r1.<init>(r2)
            throw r1
        L_0x0076:
            kotlin.ResultKt.throwOnFailure(r3)
            r1 = r2
            kotlin.coroutines.Continuation r1 = (kotlin.coroutines.Continuation) r1
            r5 = r20
            r6 = 0
            int[] r8 = r0.$this_asFlow$inlined
            r9 = 0
            int r10 = r8.length
            r11 = r1
            r12 = r6
            r13 = r9
            r15 = r10
            r1 = r20
            r6 = r4
            r10 = r7
            r9 = r8
            r4 = r2
            r7 = r5
            r2 = r21
            r5 = r3
            r3 = r0
        L_0x0092:
            if (r10 >= r15) goto L_0x00e4
            r14 = r9[r10]
            java.lang.Integer r17 = kotlin.coroutines.jvm.internal.Boxing.boxInt(r14)
            java.lang.Number r17 = (java.lang.Number) r17
            int r0 = r17.intValue()
            r17 = 0
            r20 = r2
            java.lang.Integer r2 = kotlin.coroutines.jvm.internal.Boxing.boxInt(r0)
            r4.L$0 = r3
            r4.L$1 = r1
            r4.L$2 = r11
            r4.L$3 = r7
            r4.L$4 = r8
            r4.L$5 = r9
            r4.I$0 = r15
            r4.I$1 = r10
            r4.I$2 = r14
            r4.I$3 = r0
            r21 = r0
            r0 = 1
            r4.label = r0
            java.lang.Object r2 = r7.emit(r2, r4)
            if (r2 != r6) goto L_0x00c8
            return r6
        L_0x00c8:
            r2 = r20
            r16 = r15
            r15 = r10
            r10 = r9
            r9 = r21
            r18 = r8
            r8 = r7
            r7 = r17
            r17 = r18
        L_0x00d7:
            int r7 = r15 + 1
            r0 = r19
            r9 = r10
            r15 = r16
            r10 = r7
            r7 = r8
            r8 = r17
            goto L_0x0092
        L_0x00e4:
            kotlin.Unit r0 = kotlin.Unit.INSTANCE
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.flow.FlowKt__BuildersKt$asFlow$$inlined$unsafeFlow$7.collect(kotlinx.coroutines.flow.FlowCollector, kotlin.coroutines.Continuation):java.lang.Object");
    }
}
