package kotlinx.coroutines.flow;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.InlineMarker;
import kotlin.jvm.internal.Ref;

@Metadata(mo33669bv = {1, 0, 3}, mo33670d1 = {"\u0000\u001b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\b\u0003\n\u0002\b\u0003*\u0001\u0000\b\n\u0018\u00002\b\u0012\u0004\u0012\u00028\u00000\u0001J\u0019\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00028\u0000H@ø\u0001\u0000¢\u0006\u0002\u0010\u0005\u0002\u0004\n\u0002\b\u0019¨\u0006\u0006¸\u0006\u0000"}, mo33671d2 = {"kotlinx/coroutines/flow/FlowKt__CollectKt$collect$3", "Lkotlinx/coroutines/flow/FlowCollector;", "emit", "", "value", "(Ljava/lang/Object;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "kotlinx-coroutines-core"}, mo33672k = 1, mo33673mv = {1, 1, 15})
/* compiled from: Collect.kt */
public final class FlowKt__ReduceKt$fold$$inlined$collect$2 implements FlowCollector<T> {
    final /* synthetic */ Ref.ObjectRef $accumulator$inlined;
    final /* synthetic */ Function3 $operation$inlined;

    /* Debug info: failed to restart local var, previous not found, register: 10 */
    /* JADX WARNING: Removed duplicated region for block: B:12:0x0049  */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x0024  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.lang.Object emit(java.lang.Object r11, kotlin.coroutines.Continuation r12) {
        /*
            r10 = this;
            boolean r0 = r12 instanceof kotlinx.coroutines.flow.FlowKt__ReduceKt$fold$$inlined$collect$2.C18411
            if (r0 == 0) goto L_0x0014
            r0 = r12
            kotlinx.coroutines.flow.FlowKt__ReduceKt$fold$$inlined$collect$2$1 r0 = (kotlinx.coroutines.flow.FlowKt__ReduceKt$fold$$inlined$collect$2.C18411) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r1 = r1 & r2
            if (r1 == 0) goto L_0x0014
            int r1 = r0.label
            int r1 = r1 - r2
            r0.label = r1
            goto L_0x0019
        L_0x0014:
            kotlinx.coroutines.flow.FlowKt__ReduceKt$fold$$inlined$collect$2$1 r0 = new kotlinx.coroutines.flow.FlowKt__ReduceKt$fold$$inlined$collect$2$1
            r0.<init>(r10, r12)
        L_0x0019:
            java.lang.Object r1 = r0.result
            java.lang.Object r2 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r3 = r0.label
            r4 = 1
            if (r3 == 0) goto L_0x0049
            if (r3 != r4) goto L_0x0041
            r2 = 0
            r3 = r2
            r4 = 0
            java.lang.Object r5 = r0.L$4
            kotlin.jvm.internal.Ref$ObjectRef r5 = (kotlin.jvm.internal.Ref.ObjectRef) r5
            java.lang.Object r2 = r0.L$3
            java.lang.Object r6 = r0.L$2
            r3 = r6
            kotlin.coroutines.Continuation r3 = (kotlin.coroutines.Continuation) r3
            java.lang.Object r11 = r0.L$1
            java.lang.Object r6 = r0.L$0
            kotlinx.coroutines.flow.FlowKt__ReduceKt$fold$$inlined$collect$2 r6 = (kotlinx.coroutines.flow.FlowKt__ReduceKt$fold$$inlined$collect$2) r6
            kotlin.ResultKt.throwOnFailure(r1)
            r7 = r6
            r6 = r4
            r4 = r1
            goto L_0x006d
        L_0x0041:
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
            java.lang.String r1 = "call to 'resume' before 'invoke' with coroutine"
            r0.<init>(r1)
            throw r0
        L_0x0049:
            kotlin.ResultKt.throwOnFailure(r1)
            r3 = r0
            kotlin.coroutines.Continuation r3 = (kotlin.coroutines.Continuation) r3
            r5 = r11
            r6 = 0
            kotlin.jvm.internal.Ref$ObjectRef r7 = r10.$accumulator$inlined
            kotlin.jvm.functions.Function3 r8 = r10.$operation$inlined
            T r9 = r7.element
            r0.L$0 = r10
            r0.L$1 = r11
            r0.L$2 = r3
            r0.L$3 = r5
            r0.L$4 = r7
            r0.label = r4
            java.lang.Object r4 = r8.invoke(r9, r5, r0)
            if (r4 != r2) goto L_0x006a
            return r2
        L_0x006a:
            r2 = r5
            r5 = r7
            r7 = r10
        L_0x006d:
            r5.element = r4
            kotlin.Unit r2 = kotlin.Unit.INSTANCE
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.flow.FlowKt__ReduceKt$fold$$inlined$collect$2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
    }

    public FlowKt__ReduceKt$fold$$inlined$collect$2(Ref.ObjectRef objectRef, Function3 function3) {
        this.$accumulator$inlined = objectRef;
        this.$operation$inlined = function3;
    }

    public Object emit$$forInline(Object value, Continuation continuation) {
        InlineMarker.mark(4);
        new ContinuationImpl(this, continuation) {
            Object L$0;
            Object L$1;
            Object L$2;
            Object L$3;
            Object L$4;
            int label;
            /* synthetic */ Object result;
            final /* synthetic */ FlowKt__ReduceKt$fold$$inlined$collect$2 this$0;

            {
                this.this$0 = r1;
            }

            public final Object invokeSuspend(Object obj) {
                this.result = obj;
                this.label |= Integer.MIN_VALUE;
                return this.this$0.emit((Object) null, this);
            }
        };
        InlineMarker.mark(5);
        Continuation continuation2 = continuation;
        Ref.ObjectRef objectRef = this.$accumulator$inlined;
        objectRef.element = this.$operation$inlined.invoke(objectRef.element, value, continuation);
        return Unit.INSTANCE;
    }
}
