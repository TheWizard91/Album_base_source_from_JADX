package kotlinx.coroutines.flow;

import kotlin.Metadata;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Ref;

@Metadata(mo33669bv = {1, 0, 3}, mo33670d1 = {"\u0000\u001b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\b\u0003\n\u0002\b\u0003*\u0001\u0000\b\n\u0018\u00002\b\u0012\u0004\u0012\u00028\u00000\u0001J\u0019\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00028\u0000H@ø\u0001\u0000¢\u0006\u0002\u0010\u0005\u0002\u0004\n\u0002\b\u0019¨\u0006\u0006¸\u0006\u0000"}, mo33671d2 = {"kotlinx/coroutines/flow/FlowKt__CollectKt$collect$3", "Lkotlinx/coroutines/flow/FlowCollector;", "emit", "", "value", "(Ljava/lang/Object;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "kotlinx-coroutines-core"}, mo33672k = 1, mo33673mv = {1, 1, 15})
/* compiled from: Collect.kt */
public final class FlowKt__CountKt$count$$inlined$collect$2 implements FlowCollector<T> {
    final /* synthetic */ Ref.IntRef $i$inlined;
    final /* synthetic */ Function2 $predicate$inlined;

    /* Debug info: failed to restart local var, previous not found, register: 10 */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r7v6, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v6, resolved type: kotlin.coroutines.Continuation} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:15:0x005f  */
    /* JADX WARNING: Removed duplicated region for block: B:20:0x008b A[RETURN] */
    /* JADX WARNING: Removed duplicated region for block: B:21:0x008c  */
    /* JADX WARNING: Removed duplicated region for block: B:24:0x0097  */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x0025  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.lang.Object emit(java.lang.Object r11, kotlin.coroutines.Continuation r12) {
        /*
            r10 = this;
            boolean r0 = r12 instanceof kotlinx.coroutines.flow.FlowKt__CountKt$count$$inlined$collect$2.C17661
            if (r0 == 0) goto L_0x0014
            r0 = r12
            kotlinx.coroutines.flow.FlowKt__CountKt$count$$inlined$collect$2$1 r0 = (kotlinx.coroutines.flow.FlowKt__CountKt$count$$inlined$collect$2.C17661) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r1 = r1 & r2
            if (r1 == 0) goto L_0x0014
            int r1 = r0.label
            int r1 = r1 - r2
            r0.label = r1
            goto L_0x0019
        L_0x0014:
            kotlinx.coroutines.flow.FlowKt__CountKt$count$$inlined$collect$2$1 r0 = new kotlinx.coroutines.flow.FlowKt__CountKt$count$$inlined$collect$2$1
            r0.<init>(r10, r12)
        L_0x0019:
            java.lang.Object r1 = r0.result
            java.lang.Object r2 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r3 = r0.label
            r4 = 2
            r5 = 1
            if (r3 == 0) goto L_0x005f
            r6 = 0
            r7 = 0
            if (r3 == r5) goto L_0x0049
            if (r3 != r4) goto L_0x0041
            r2 = r6
            r3 = r7
            r4 = r6
            java.lang.Object r4 = r0.L$3
            java.lang.Object r6 = r0.L$2
            r2 = r6
            kotlin.coroutines.Continuation r2 = (kotlin.coroutines.Continuation) r2
            java.lang.Object r11 = r0.L$1
            java.lang.Object r6 = r0.L$0
            kotlinx.coroutines.flow.FlowKt__CountKt$count$$inlined$collect$2 r6 = (kotlinx.coroutines.flow.FlowKt__CountKt$count$$inlined$collect$2) r6
            kotlin.ResultKt.throwOnFailure(r1)
            r8 = r6
            r6 = r1
            goto L_0x008f
        L_0x0041:
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
            java.lang.String r1 = "call to 'resume' before 'invoke' with coroutine"
            r0.<init>(r1)
            throw r0
        L_0x0049:
            r3 = r6
            r4 = r7
            java.lang.Object r6 = r0.L$3
            java.lang.Object r7 = r0.L$2
            r3 = r7
            kotlin.coroutines.Continuation r3 = (kotlin.coroutines.Continuation) r3
            java.lang.Object r11 = r0.L$1
            java.lang.Object r7 = r0.L$0
            kotlinx.coroutines.flow.FlowKt__CountKt$count$$inlined$collect$2 r7 = (kotlinx.coroutines.flow.FlowKt__CountKt$count$$inlined$collect$2) r7
            kotlin.ResultKt.throwOnFailure(r1)
            r8 = r7
            r7 = r6
            r6 = r1
            goto L_0x0089
        L_0x005f:
            kotlin.ResultKt.throwOnFailure(r1)
            r3 = r0
            kotlin.coroutines.Continuation r3 = (kotlin.coroutines.Continuation) r3
            r6 = r11
            r7 = 0
            kotlin.jvm.functions.Function2 r8 = r10.$predicate$inlined
            r0.L$0 = r10
            r0.L$1 = r11
            r0.L$2 = r3
            r0.L$3 = r6
            r0.label = r4
            r0.L$0 = r10
            r0.L$1 = r11
            r0.L$2 = r3
            r0.L$3 = r6
            r0.label = r5
            java.lang.Object r4 = r8.invoke(r6, r0)
            if (r4 != r2) goto L_0x0084
            return r2
        L_0x0084:
            r8 = r10
            r9 = r6
            r6 = r4
            r4 = r7
            r7 = r9
        L_0x0089:
            if (r6 != r2) goto L_0x008c
            return r2
        L_0x008c:
            r2 = r3
            r3 = r4
            r4 = r7
        L_0x008f:
            java.lang.Boolean r6 = (java.lang.Boolean) r6
            boolean r6 = r6.booleanValue()
            if (r6 == 0) goto L_0x00a0
            kotlin.jvm.internal.Ref$IntRef r6 = r8.$i$inlined
            int r7 = r6.element
            int r7 = r7 + r5
            r6.element = r7
            int r5 = r6.element
        L_0x00a0:
            kotlin.Unit r2 = kotlin.Unit.INSTANCE
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.flow.FlowKt__CountKt$count$$inlined$collect$2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
    }

    public FlowKt__CountKt$count$$inlined$collect$2(Function2 function2, Ref.IntRef intRef) {
        this.$predicate$inlined = function2;
        this.$i$inlined = intRef;
    }
}
