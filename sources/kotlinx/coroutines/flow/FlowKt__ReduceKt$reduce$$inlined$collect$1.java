package kotlinx.coroutines.flow;

import kotlin.Metadata;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Ref;

@Metadata(mo33669bv = {1, 0, 3}, mo33670d1 = {"\u0000\u001b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\b\u0003\n\u0002\b\u0003*\u0001\u0000\b\n\u0018\u00002\b\u0012\u0004\u0012\u00028\u00000\u0001J\u0019\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00028\u0000H@ø\u0001\u0000¢\u0006\u0002\u0010\u0005\u0002\u0004\n\u0002\b\u0019¨\u0006\u0006¸\u0006\u0000"}, mo33671d2 = {"kotlinx/coroutines/flow/FlowKt__CollectKt$collect$3", "Lkotlinx/coroutines/flow/FlowCollector;", "emit", "", "value", "(Ljava/lang/Object;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "kotlinx-coroutines-core"}, mo33672k = 1, mo33673mv = {1, 1, 15})
/* compiled from: Collect.kt */
public final class FlowKt__ReduceKt$reduce$$inlined$collect$1 implements FlowCollector<T> {
    final /* synthetic */ Ref.ObjectRef $accumulator$inlined;
    final /* synthetic */ Function3 $operation$inlined;

    /* Debug info: failed to restart local var, previous not found, register: 12 */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r7v6, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v9, resolved type: kotlin.coroutines.Continuation} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r6v9, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v6, resolved type: kotlin.coroutines.Continuation} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:15:0x0069  */
    /* JADX WARNING: Removed duplicated region for block: B:22:0x00a4 A[RETURN] */
    /* JADX WARNING: Removed duplicated region for block: B:23:0x00a5  */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x0025  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.lang.Object emit(java.lang.Object r13, kotlin.coroutines.Continuation r14) {
        /*
            r12 = this;
            boolean r0 = r14 instanceof kotlinx.coroutines.flow.FlowKt__ReduceKt$reduce$$inlined$collect$1.C18421
            if (r0 == 0) goto L_0x0014
            r0 = r14
            kotlinx.coroutines.flow.FlowKt__ReduceKt$reduce$$inlined$collect$1$1 r0 = (kotlinx.coroutines.flow.FlowKt__ReduceKt$reduce$$inlined$collect$1.C18421) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r1 = r1 & r2
            if (r1 == 0) goto L_0x0014
            int r1 = r0.label
            int r1 = r1 - r2
            r0.label = r1
            goto L_0x0019
        L_0x0014:
            kotlinx.coroutines.flow.FlowKt__ReduceKt$reduce$$inlined$collect$1$1 r0 = new kotlinx.coroutines.flow.FlowKt__ReduceKt$reduce$$inlined$collect$1$1
            r0.<init>(r12, r14)
        L_0x0019:
            java.lang.Object r1 = r0.result
            java.lang.Object r2 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r3 = r0.label
            r4 = 2
            r5 = 1
            if (r3 == 0) goto L_0x0069
            r6 = 0
            r7 = 0
            if (r3 == r5) goto L_0x004e
            if (r3 != r4) goto L_0x0046
            r2 = r6
            r3 = r7
            r4 = r6
            java.lang.Object r5 = r0.L$4
            kotlin.jvm.internal.Ref$ObjectRef r5 = (kotlin.jvm.internal.Ref.ObjectRef) r5
            java.lang.Object r4 = r0.L$3
            java.lang.Object r6 = r0.L$2
            r2 = r6
            kotlin.coroutines.Continuation r2 = (kotlin.coroutines.Continuation) r2
            java.lang.Object r13 = r0.L$1
            java.lang.Object r6 = r0.L$0
            kotlinx.coroutines.flow.FlowKt__ReduceKt$reduce$$inlined$collect$1 r6 = (kotlinx.coroutines.flow.FlowKt__ReduceKt$reduce$$inlined$collect$1) r6
            kotlin.ResultKt.throwOnFailure(r1)
            r7 = r6
            r6 = r1
            goto L_0x00aa
        L_0x0046:
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
            java.lang.String r1 = "call to 'resume' before 'invoke' with coroutine"
            r0.<init>(r1)
            throw r0
        L_0x004e:
            r3 = r6
            r4 = r7
            r5 = r6
            java.lang.Object r6 = r0.L$4
            kotlin.jvm.internal.Ref$ObjectRef r6 = (kotlin.jvm.internal.Ref.ObjectRef) r6
            java.lang.Object r5 = r0.L$3
            java.lang.Object r7 = r0.L$2
            r3 = r7
            kotlin.coroutines.Continuation r3 = (kotlin.coroutines.Continuation) r3
            java.lang.Object r13 = r0.L$1
            java.lang.Object r7 = r0.L$0
            kotlinx.coroutines.flow.FlowKt__ReduceKt$reduce$$inlined$collect$1 r7 = (kotlinx.coroutines.flow.FlowKt__ReduceKt$reduce$$inlined$collect$1) r7
            kotlin.ResultKt.throwOnFailure(r1)
            r8 = r6
            r6 = r5
            r5 = r1
            goto L_0x00a2
        L_0x0069:
            kotlin.ResultKt.throwOnFailure(r1)
            r3 = r0
            kotlin.coroutines.Continuation r3 = (kotlin.coroutines.Continuation) r3
            r6 = r13
            r7 = 0
            kotlin.jvm.internal.Ref$ObjectRef r8 = r12.$accumulator$inlined
            T r9 = r8.element
            kotlinx.coroutines.internal.Symbol r10 = kotlinx.coroutines.flow.internal.NullSurrogateKt.NULL
            if (r9 == r10) goto L_0x00b0
            kotlin.jvm.functions.Function3 r9 = r12.$operation$inlined
            kotlin.jvm.internal.Ref$ObjectRef r10 = r12.$accumulator$inlined
            T r10 = r10.element
            r0.L$0 = r12
            r0.L$1 = r13
            r0.L$2 = r3
            r0.L$3 = r6
            r0.L$4 = r8
            r0.label = r4
            r0.L$0 = r12
            r0.L$1 = r13
            r0.L$2 = r3
            r0.L$3 = r6
            r0.L$4 = r8
            r0.label = r5
            java.lang.Object r4 = r9.invoke(r10, r6, r0)
            if (r4 != r2) goto L_0x009f
            return r2
        L_0x009f:
            r5 = r4
            r4 = r7
            r7 = r12
        L_0x00a2:
            if (r5 != r2) goto L_0x00a5
            return r2
        L_0x00a5:
            r2 = r3
            r3 = r4
            r4 = r6
            r6 = r5
            r5 = r8
        L_0x00aa:
            r8 = r5
            r11 = r3
            r3 = r2
            r2 = r7
            r7 = r11
            goto L_0x00b2
        L_0x00b0:
            r2 = r12
            r4 = r6
        L_0x00b2:
            r8.element = r6
            kotlin.Unit r3 = kotlin.Unit.INSTANCE
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.flow.FlowKt__ReduceKt$reduce$$inlined$collect$1.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
    }

    public FlowKt__ReduceKt$reduce$$inlined$collect$1(Ref.ObjectRef objectRef, Function3 function3) {
        this.$accumulator$inlined = objectRef;
        this.$operation$inlined = function3;
    }
}
