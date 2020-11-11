package kotlinx.coroutines.flow;

import kotlin.Metadata;
import kotlin.sequences.Sequence;

@Metadata(mo33669bv = {1, 0, 3}, mo33670d1 = {"\u0000\u001d\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\b\u0002*\u0001\u0000\b\n\u0018\u00002\b\u0012\u0004\u0012\u00028\u00000\u0001J\u001f\u0010\u0002\u001a\u00020\u00032\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00028\u00000\u0005H@ø\u0001\u0000¢\u0006\u0002\u0010\u0006\u0002\u0004\n\u0002\b\u0019¨\u0006\u0007¸\u0006\u0000"}, mo33671d2 = {"kotlinx/coroutines/flow/internal/SafeCollectorKt$unsafeFlow$1", "Lkotlinx/coroutines/flow/Flow;", "collect", "", "collector", "Lkotlinx/coroutines/flow/FlowCollector;", "(Lkotlinx/coroutines/flow/FlowCollector;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "kotlinx-coroutines-core"}, mo33672k = 1, mo33673mv = {1, 1, 15})
/* compiled from: SafeCollector.kt */
public final class FlowKt__BuildersKt$asFlow$$inlined$unsafeFlow$5 implements Flow<T> {
    final /* synthetic */ Sequence $this_asFlow$inlined;

    /* Debug info: failed to restart local var, previous not found, register: 16 */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r15v2, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r5v7, resolved type: kotlinx.coroutines.flow.FlowCollector} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:12:0x0066  */
    /* JADX WARNING: Removed duplicated region for block: B:15:0x0087  */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x0028  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.lang.Object collect(kotlinx.coroutines.flow.FlowCollector r17, kotlin.coroutines.Continuation r18) {
        /*
            r16 = this;
            r0 = r16
            r1 = r18
            boolean r2 = r1 instanceof kotlinx.coroutines.flow.FlowKt__BuildersKt$asFlow$$inlined$unsafeFlow$5.C17601
            if (r2 == 0) goto L_0x0018
            r2 = r1
            kotlinx.coroutines.flow.FlowKt__BuildersKt$asFlow$$inlined$unsafeFlow$5$1 r2 = (kotlinx.coroutines.flow.FlowKt__BuildersKt$asFlow$$inlined$unsafeFlow$5.C17601) r2
            int r3 = r2.label
            r4 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r3 & r4
            if (r3 == 0) goto L_0x0018
            int r3 = r2.label
            int r3 = r3 - r4
            r2.label = r3
            goto L_0x001d
        L_0x0018:
            kotlinx.coroutines.flow.FlowKt__BuildersKt$asFlow$$inlined$unsafeFlow$5$1 r2 = new kotlinx.coroutines.flow.FlowKt__BuildersKt$asFlow$$inlined$unsafeFlow$5$1
            r2.<init>(r0, r1)
        L_0x001d:
            java.lang.Object r3 = r2.result
            java.lang.Object r4 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r5 = r2.label
            r6 = 1
            if (r5 == 0) goto L_0x0066
            if (r5 != r6) goto L_0x005e
            r5 = 0
            r7 = r5
            r8 = 0
            r9 = r8
            r10 = r5
            r11 = r5
            r12 = r8
            r13 = r5
            java.lang.Object r11 = r2.L$7
            java.lang.Object r10 = r2.L$6
            java.lang.Object r14 = r2.L$5
            java.util.Iterator r14 = (java.util.Iterator) r14
            java.lang.Object r15 = r2.L$4
            r13 = r15
            kotlin.sequences.Sequence r13 = (kotlin.sequences.Sequence) r13
            java.lang.Object r15 = r2.L$3
            r5 = r15
            kotlinx.coroutines.flow.FlowCollector r5 = (kotlinx.coroutines.flow.FlowCollector) r5
            java.lang.Object r15 = r2.L$2
            r7 = r15
            kotlin.coroutines.Continuation r7 = (kotlin.coroutines.Continuation) r7
            java.lang.Object r15 = r2.L$1
            kotlinx.coroutines.flow.FlowCollector r15 = (kotlinx.coroutines.flow.FlowCollector) r15
            java.lang.Object r6 = r2.L$0
            kotlinx.coroutines.flow.FlowKt__BuildersKt$asFlow$$inlined$unsafeFlow$5 r6 = (kotlinx.coroutines.flow.FlowKt__BuildersKt$asFlow$$inlined$unsafeFlow$5) r6
            kotlin.ResultKt.throwOnFailure(r3)
            r0 = r8
            r8 = r7
            r7 = r6
            r6 = r5
            r5 = r4
            r4 = r3
            r3 = r2
            r2 = r1
            r1 = r15
            goto L_0x00a9
        L_0x005e:
            java.lang.IllegalStateException r2 = new java.lang.IllegalStateException
            java.lang.String r3 = "call to 'resume' before 'invoke' with coroutine"
            r2.<init>(r3)
            throw r2
        L_0x0066:
            kotlin.ResultKt.throwOnFailure(r3)
            r5 = r2
            kotlin.coroutines.Continuation r5 = (kotlin.coroutines.Continuation) r5
            r6 = r17
            r7 = 0
            kotlin.sequences.Sequence r8 = r0.$this_asFlow$inlined
            r9 = 0
            java.util.Iterator r10 = r8.iterator()
            r12 = r7
            r13 = r8
            r14 = r10
            r7 = r0
            r8 = r5
            r5 = r4
            r4 = r3
            r3 = r2
            r2 = r1
            r1 = r17
        L_0x0081:
            boolean r10 = r14.hasNext()
            if (r10 == 0) goto L_0x00ad
            java.lang.Object r10 = r14.next()
            r11 = r10
            r15 = 0
            r3.L$0 = r7
            r3.L$1 = r1
            r3.L$2 = r8
            r3.L$3 = r6
            r3.L$4 = r13
            r3.L$5 = r14
            r3.L$6 = r10
            r3.L$7 = r11
            r0 = 1
            r3.label = r0
            java.lang.Object r0 = r6.emit(r11, r3)
            if (r0 != r5) goto L_0x00a7
            return r5
        L_0x00a7:
            r0 = r9
            r9 = r15
        L_0x00a9:
            r9 = r0
            r0 = r16
            goto L_0x0081
        L_0x00ad:
            kotlin.Unit r0 = kotlin.Unit.INSTANCE
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.flow.FlowKt__BuildersKt$asFlow$$inlined$unsafeFlow$5.collect(kotlinx.coroutines.flow.FlowCollector, kotlin.coroutines.Continuation):java.lang.Object");
    }

    public FlowKt__BuildersKt$asFlow$$inlined$unsafeFlow$5(Sequence sequence) {
        this.$this_asFlow$inlined = sequence;
    }
}
