package kotlinx.coroutines.flow;

import kotlin.Metadata;

@Metadata(mo33669bv = {1, 0, 3}, mo33670d1 = {"\u0000\u001d\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\b\u0002*\u0001\u0000\b\n\u0018\u00002\b\u0012\u0004\u0012\u00028\u00000\u0001J\u001f\u0010\u0002\u001a\u00020\u00032\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00028\u00000\u0005H@ø\u0001\u0000¢\u0006\u0002\u0010\u0006\u0002\u0004\n\u0002\b\u0019¨\u0006\u0007¸\u0006\u0000"}, mo33671d2 = {"kotlinx/coroutines/flow/internal/SafeCollectorKt$unsafeFlow$1", "Lkotlinx/coroutines/flow/Flow;", "collect", "", "collector", "Lkotlinx/coroutines/flow/FlowCollector;", "(Lkotlinx/coroutines/flow/FlowCollector;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "kotlinx-coroutines-core"}, mo33672k = 1, mo33673mv = {1, 1, 15})
/* compiled from: SafeCollector.kt */
public final class FlowKt__BuildersKt$asFlow$$inlined$unsafeFlow$8 implements Flow<Long> {
    final /* synthetic */ long[] $this_asFlow$inlined;

    public FlowKt__BuildersKt$asFlow$$inlined$unsafeFlow$8(long[] jArr) {
        this.$this_asFlow$inlined = jArr;
    }

    /* Debug info: failed to restart local var, previous not found, register: 22 */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r14v1, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r5v6, resolved type: kotlinx.coroutines.flow.FlowCollector} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:12:0x0080  */
    /* JADX WARNING: Removed duplicated region for block: B:14:0x009d  */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x0029  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.lang.Object collect(kotlinx.coroutines.flow.FlowCollector r23, kotlin.coroutines.Continuation r24) {
        /*
            r22 = this;
            r0 = r22
            r1 = r24
            boolean r2 = r1 instanceof kotlinx.coroutines.flow.FlowKt__BuildersKt$asFlow$$inlined$unsafeFlow$8.C17631
            if (r2 == 0) goto L_0x0018
            r2 = r1
            kotlinx.coroutines.flow.FlowKt__BuildersKt$asFlow$$inlined$unsafeFlow$8$1 r2 = (kotlinx.coroutines.flow.FlowKt__BuildersKt$asFlow$$inlined$unsafeFlow$8.C17631) r2
            int r3 = r2.label
            r4 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r3 & r4
            if (r3 == 0) goto L_0x0018
            int r3 = r2.label
            int r3 = r3 - r4
            r2.label = r3
            goto L_0x001d
        L_0x0018:
            kotlinx.coroutines.flow.FlowKt__BuildersKt$asFlow$$inlined$unsafeFlow$8$1 r2 = new kotlinx.coroutines.flow.FlowKt__BuildersKt$asFlow$$inlined$unsafeFlow$8$1
            r2.<init>(r0, r1)
        L_0x001d:
            java.lang.Object r3 = r2.result
            java.lang.Object r4 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r5 = r2.label
            r6 = 1
            r7 = 0
            if (r5 == 0) goto L_0x0080
            if (r5 != r6) goto L_0x0078
            r5 = 0
            r8 = r5
            r9 = 0
            r11 = r9
            r13 = r7
            r14 = r5
            r15 = r7
            long r9 = r2.J$1
            long r11 = r2.J$0
            int r6 = r2.I$1
            int r1 = r2.I$0
            r16 = r1
            java.lang.Object r1 = r2.L$5
            long[] r1 = (long[]) r1
            r17 = r1
            java.lang.Object r1 = r2.L$4
            long[] r1 = (long[]) r1
            java.lang.Object r14 = r2.L$3
            r5 = r14
            kotlinx.coroutines.flow.FlowCollector r5 = (kotlinx.coroutines.flow.FlowCollector) r5
            java.lang.Object r14 = r2.L$2
            r8 = r14
            kotlin.coroutines.Continuation r8 = (kotlin.coroutines.Continuation) r8
            java.lang.Object r14 = r2.L$1
            kotlinx.coroutines.flow.FlowCollector r14 = (kotlinx.coroutines.flow.FlowCollector) r14
            r18 = r1
            java.lang.Object r1 = r2.L$0
            kotlinx.coroutines.flow.FlowKt__BuildersKt$asFlow$$inlined$unsafeFlow$8 r1 = (kotlinx.coroutines.flow.FlowKt__BuildersKt$asFlow$$inlined$unsafeFlow$8) r1
            kotlin.ResultKt.throwOnFailure(r3)
            r0 = r3
            r3 = r1
            r1 = r14
            r19 = r2
            r2 = r24
            r20 = r5
            r5 = r19
            r21 = r8
            r8 = r4
            r4 = r17
            r17 = r15
            r15 = r13
            r13 = r11
            r11 = r20
            r12 = r21
            goto L_0x00f1
        L_0x0078:
            java.lang.IllegalStateException r1 = new java.lang.IllegalStateException
            java.lang.String r2 = "call to 'resume' before 'invoke' with coroutine"
            r1.<init>(r2)
            throw r1
        L_0x0080:
            kotlin.ResultKt.throwOnFailure(r3)
            r1 = r2
            kotlin.coroutines.Continuation r1 = (kotlin.coroutines.Continuation) r1
            r5 = r23
            r6 = 0
            long[] r8 = r0.$this_asFlow$inlined
            r9 = 0
            int r10 = r8.length
            r12 = r1
            r13 = r6
            r11 = r7
            r15 = r9
            r1 = r23
            r6 = r3
            r7 = r4
            r9 = r5
            r4 = r8
            r3 = r0
            r5 = r2
            r2 = r24
        L_0x009b:
            if (r11 >= r10) goto L_0x0101
            r23 = r13
            r13 = r4[r11]
            java.lang.Long r16 = kotlin.coroutines.jvm.internal.Boxing.boxLong(r13)
            java.lang.Number r16 = (java.lang.Number) r16
            r24 = r6
            r17 = r7
            long r6 = r16.longValue()
            r16 = 0
            java.lang.Long r0 = kotlin.coroutines.jvm.internal.Boxing.boxLong(r6)
            r5.L$0 = r3
            r5.L$1 = r1
            r5.L$2 = r12
            r5.L$3 = r9
            r5.L$4 = r8
            r5.L$5 = r4
            r5.I$0 = r10
            r5.I$1 = r11
            r5.J$0 = r13
            r5.J$1 = r6
            r18 = r1
            r1 = 1
            r5.label = r1
            java.lang.Object r0 = r9.emit(r0, r5)
            r1 = r17
            if (r0 != r1) goto L_0x00d8
            return r1
        L_0x00d8:
            r0 = r24
            r17 = r15
            r15 = r23
            r19 = r8
            r8 = r1
            r1 = r18
            r18 = r19
            r20 = r11
            r11 = r9
            r21 = r16
            r16 = r10
            r9 = r6
            r6 = r20
            r7 = r21
        L_0x00f1:
            r7 = 1
            int r6 = r6 + r7
            r7 = r8
            r9 = r11
            r13 = r15
            r10 = r16
            r15 = r17
            r8 = r18
            r11 = r6
            r6 = r0
            r0 = r22
            goto L_0x009b
        L_0x0101:
            kotlin.Unit r0 = kotlin.Unit.INSTANCE
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.flow.FlowKt__BuildersKt$asFlow$$inlined$unsafeFlow$8.collect(kotlinx.coroutines.flow.FlowCollector, kotlin.coroutines.Continuation):java.lang.Object");
    }
}
