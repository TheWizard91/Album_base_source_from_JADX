package kotlinx.coroutines.flow;

import kotlin.Metadata;
import kotlin.ranges.LongRange;

@Metadata(mo33669bv = {1, 0, 3}, mo33670d1 = {"\u0000\u001d\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\b\u0002*\u0001\u0000\b\n\u0018\u00002\b\u0012\u0004\u0012\u00028\u00000\u0001J\u001f\u0010\u0002\u001a\u00020\u00032\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00028\u00000\u0005H@ø\u0001\u0000¢\u0006\u0002\u0010\u0006\u0002\u0004\n\u0002\b\u0019¨\u0006\u0007¸\u0006\u0000"}, mo33671d2 = {"kotlinx/coroutines/flow/internal/SafeCollectorKt$unsafeFlow$1", "Lkotlinx/coroutines/flow/Flow;", "collect", "", "collector", "Lkotlinx/coroutines/flow/FlowCollector;", "(Lkotlinx/coroutines/flow/FlowCollector;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "kotlinx-coroutines-core"}, mo33672k = 1, mo33673mv = {1, 1, 15})
/* compiled from: SafeCollector.kt */
public final class FlowKt__BuildersKt$asFlow$$inlined$unsafeFlow$10 implements Flow<Long> {
    final /* synthetic */ LongRange $this_asFlow$inlined;

    public FlowKt__BuildersKt$asFlow$$inlined$unsafeFlow$10(LongRange longRange) {
        this.$this_asFlow$inlined = longRange;
    }

    /* Debug info: failed to restart local var, previous not found, register: 18 */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r14v4, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r5v4, resolved type: kotlinx.coroutines.flow.FlowCollector} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:12:0x006d  */
    /* JADX WARNING: Removed duplicated region for block: B:15:0x0093  */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x0028  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.lang.Object collect(kotlinx.coroutines.flow.FlowCollector r19, kotlin.coroutines.Continuation r20) {
        /*
            r18 = this;
            r0 = r18
            r1 = r20
            boolean r2 = r1 instanceof kotlinx.coroutines.flow.FlowKt__BuildersKt$asFlow$$inlined$unsafeFlow$10.C17561
            if (r2 == 0) goto L_0x0018
            r2 = r1
            kotlinx.coroutines.flow.FlowKt__BuildersKt$asFlow$$inlined$unsafeFlow$10$1 r2 = (kotlinx.coroutines.flow.FlowKt__BuildersKt$asFlow$$inlined$unsafeFlow$10.C17561) r2
            int r3 = r2.label
            r4 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r3 & r4
            if (r3 == 0) goto L_0x0018
            int r3 = r2.label
            int r3 = r3 - r4
            r2.label = r3
            goto L_0x001d
        L_0x0018:
            kotlinx.coroutines.flow.FlowKt__BuildersKt$asFlow$$inlined$unsafeFlow$10$1 r2 = new kotlinx.coroutines.flow.FlowKt__BuildersKt$asFlow$$inlined$unsafeFlow$10$1
            r2.<init>(r0, r1)
        L_0x001d:
            java.lang.Object r3 = r2.result
            java.lang.Object r4 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r5 = r2.label
            r6 = 1
            if (r5 == 0) goto L_0x006d
            if (r5 != r6) goto L_0x0065
            r5 = 0
            r7 = r5
            r8 = 0
            r9 = r8
            r10 = r5
            r11 = 0
            r13 = r8
            r14 = r5
            long r11 = r2.J$0
            java.lang.Object r10 = r2.L$6
            java.lang.Object r15 = r2.L$5
            java.util.Iterator r15 = (java.util.Iterator) r15
            java.lang.Object r6 = r2.L$4
            java.lang.Iterable r6 = (java.lang.Iterable) r6
            java.lang.Object r14 = r2.L$3
            r5 = r14
            kotlinx.coroutines.flow.FlowCollector r5 = (kotlinx.coroutines.flow.FlowCollector) r5
            java.lang.Object r14 = r2.L$2
            r7 = r14
            kotlin.coroutines.Continuation r7 = (kotlin.coroutines.Continuation) r7
            java.lang.Object r14 = r2.L$1
            kotlinx.coroutines.flow.FlowCollector r14 = (kotlinx.coroutines.flow.FlowCollector) r14
            java.lang.Object r1 = r2.L$0
            kotlinx.coroutines.flow.FlowKt__BuildersKt$asFlow$$inlined$unsafeFlow$10 r1 = (kotlinx.coroutines.flow.FlowKt__BuildersKt$asFlow$$inlined$unsafeFlow$10) r1
            kotlin.ResultKt.throwOnFailure(r3)
            r0 = r7
            r16 = r15
            r7 = r5
            r15 = r13
            r5 = r3
            r12 = r11
            r3 = r1
            r11 = r8
            r1 = 1
            r8 = r6
            r6 = r4
            r4 = r2
            r2 = r20
            goto L_0x00cf
        L_0x0065:
            java.lang.IllegalStateException r1 = new java.lang.IllegalStateException
            java.lang.String r2 = "call to 'resume' before 'invoke' with coroutine"
            r1.<init>(r2)
            throw r1
        L_0x006d:
            kotlin.ResultKt.throwOnFailure(r3)
            r1 = r2
            kotlin.coroutines.Continuation r1 = (kotlin.coroutines.Continuation) r1
            r5 = r19
            r6 = 0
            kotlin.ranges.LongRange r7 = r0.$this_asFlow$inlined
            java.lang.Iterable r7 = (java.lang.Iterable) r7
            r8 = 0
            java.util.Iterator r9 = r7.iterator()
            r13 = r6
            r10 = r8
            r15 = r9
            r9 = r1
            r6 = r4
            r8 = r7
            r1 = r19
            r4 = r2
            r7 = r5
            r2 = r20
            r5 = r3
            r3 = r0
        L_0x008d:
            boolean r11 = r15.hasNext()
            if (r11 == 0) goto L_0x00d8
            java.lang.Object r11 = r15.next()
            r12 = r11
            java.lang.Number r12 = (java.lang.Number) r12
            r19 = r13
            long r12 = r12.longValue()
            r14 = 0
            java.lang.Long r0 = kotlin.coroutines.jvm.internal.Boxing.boxLong(r12)
            r4.L$0 = r3
            r4.L$1 = r1
            r4.L$2 = r9
            r4.L$3 = r7
            r4.L$4 = r8
            r4.L$5 = r15
            r4.L$6 = r11
            r4.J$0 = r12
            r20 = r1
            r1 = 1
            r4.label = r1
            java.lang.Object r0 = r7.emit(r0, r4)
            if (r0 != r6) goto L_0x00c2
            return r6
        L_0x00c2:
            r0 = r9
            r9 = r14
            r16 = r15
            r15 = r19
            r14 = r20
            r17 = r11
            r11 = r10
            r10 = r17
        L_0x00cf:
            r9 = r0
            r10 = r11
            r1 = r14
            r13 = r15
            r15 = r16
            r0 = r18
            goto L_0x008d
        L_0x00d8:
            kotlin.Unit r0 = kotlin.Unit.INSTANCE
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.flow.FlowKt__BuildersKt$asFlow$$inlined$unsafeFlow$10.collect(kotlinx.coroutines.flow.FlowCollector, kotlin.coroutines.Continuation):java.lang.Object");
    }
}
