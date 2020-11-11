package kotlinx.coroutines.flow;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.jvm.internal.InlineMarker;
import kotlin.jvm.internal.Ref;
import kotlinx.coroutines.flow.internal.NullSurrogateKt;

@Metadata(mo33669bv = {1, 0, 3}, mo33670d1 = {"\u00003\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\b\u0003\n\u0002\b\u0003\n\u0002\b\u0003\n\u0002\b\u0003\n\u0002\b\u0003\n\u0002\b\u0003\n\u0002\b\u0003\n\u0002\b\u0004*\u0001\u0000\b\n\u0018\u00002\b\u0012\u0004\u0012\u00028\u00000\u0001J\u0019\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00028\u0000H@ø\u0001\u0000¢\u0006\u0002\u0010\u0005\u0002\u0004\n\u0002\b\u0019¨\u0006\u0006¸\u0006\u0007"}, mo33671d2 = {"kotlinx/coroutines/flow/FlowKt__CollectKt$collect$3", "Lkotlinx/coroutines/flow/FlowCollector;", "emit", "", "value", "(Ljava/lang/Object;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "kotlinx-coroutines-core", "kotlinx/coroutines/flow/FlowKt__DistinctKt$$special$$inlined$collect$3"}, mo33672k = 1, mo33673mv = {1, 1, 15})
/* renamed from: kotlinx.coroutines.flow.FlowKt__DistinctKt$distinctUntilChangedBy$$inlined$unsafeFlow$3$lambda$1 */
/* compiled from: Collect.kt */
public final class C1786x4264af2b implements FlowCollector<T> {
    final /* synthetic */ Ref.ObjectRef $previousKey$inlined;
    final /* synthetic */ FlowCollector $this_unsafeFlow$inlined;
    final /* synthetic */ FlowKt__DistinctKt$distinctUntilChangedBy$$inlined$unsafeFlow$3 this$0;

    /* Debug info: failed to restart local var, previous not found, register: 11 */
    /* JADX WARNING: Removed duplicated region for block: B:12:0x0048  */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x0024  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.lang.Object emit(java.lang.Object r12, kotlin.coroutines.Continuation r13) {
        /*
            r11 = this;
            boolean r0 = r13 instanceof kotlinx.coroutines.flow.C1786x4264af2b.C17871
            if (r0 == 0) goto L_0x0014
            r0 = r13
            kotlinx.coroutines.flow.FlowKt__DistinctKt$distinctUntilChangedBy$$inlined$unsafeFlow$3$lambda$1$1 r0 = (kotlinx.coroutines.flow.C1786x4264af2b.C17871) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r1 = r1 & r2
            if (r1 == 0) goto L_0x0014
            int r1 = r0.label
            int r1 = r1 - r2
            r0.label = r1
            goto L_0x0019
        L_0x0014:
            kotlinx.coroutines.flow.FlowKt__DistinctKt$distinctUntilChangedBy$$inlined$unsafeFlow$3$lambda$1$1 r0 = new kotlinx.coroutines.flow.FlowKt__DistinctKt$distinctUntilChangedBy$$inlined$unsafeFlow$3$lambda$1$1
            r0.<init>(r11, r13)
        L_0x0019:
            java.lang.Object r1 = r0.result
            java.lang.Object r2 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r3 = r0.label
            r4 = 1
            if (r3 == 0) goto L_0x0048
            if (r3 != r4) goto L_0x0040
            r2 = 0
            r3 = r2
            r4 = 0
            r5 = r2
            java.lang.Object r5 = r0.L$4
            java.lang.Object r2 = r0.L$3
            java.lang.Object r6 = r0.L$2
            r3 = r6
            kotlinx.coroutines.flow.FlowKt__DistinctKt$distinctUntilChangedBy$$inlined$unsafeFlow$3$lambda$1$1 r3 = (kotlinx.coroutines.flow.C1786x4264af2b.C17871) r3
            java.lang.Object r12 = r0.L$1
            java.lang.Object r6 = r0.L$0
            kotlinx.coroutines.flow.FlowKt__DistinctKt$distinctUntilChangedBy$$inlined$unsafeFlow$3$lambda$1 r6 = (kotlinx.coroutines.flow.C1786x4264af2b) r6
            kotlin.ResultKt.throwOnFailure(r1)
            r7 = r6
            r6 = r4
            r4 = r1
            goto L_0x0092
        L_0x0040:
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
            java.lang.String r1 = "call to 'resume' before 'invoke' with coroutine"
            r0.<init>(r1)
            throw r0
        L_0x0048:
            kotlin.ResultKt.throwOnFailure(r1)
            r3 = r0
            r5 = r12
            r6 = 0
            kotlinx.coroutines.flow.FlowKt__DistinctKt$distinctUntilChangedBy$$inlined$unsafeFlow$3 r7 = r11.this$0
            kotlin.jvm.functions.Function1 r7 = r7.$keySelector$inlined
            java.lang.Object r7 = r7.invoke(r5)
            kotlin.jvm.internal.Ref$ObjectRef r8 = r11.$previousKey$inlined
            T r8 = r8.element
            kotlinx.coroutines.internal.Symbol r9 = kotlinx.coroutines.flow.internal.NullSurrogateKt.NULL
            if (r8 == r9) goto L_0x0076
            kotlinx.coroutines.flow.FlowKt__DistinctKt$distinctUntilChangedBy$$inlined$unsafeFlow$3 r8 = r11.this$0
            kotlin.jvm.functions.Function2 r8 = r8.$areEquivalent$inlined
            kotlin.jvm.internal.Ref$ObjectRef r9 = r11.$previousKey$inlined
            T r9 = r9.element
            java.lang.Object r8 = r8.invoke(r9, r7)
            java.lang.Boolean r8 = (java.lang.Boolean) r8
            boolean r8 = r8.booleanValue()
            if (r8 != 0) goto L_0x0074
            goto L_0x0076
        L_0x0074:
            r2 = r11
            goto L_0x0098
        L_0x0076:
            kotlin.jvm.internal.Ref$ObjectRef r8 = r11.$previousKey$inlined
            r8.element = r7
            kotlinx.coroutines.flow.FlowCollector r8 = r11.$this_unsafeFlow$inlined
            r0.L$0 = r11
            r0.L$1 = r12
            r0.L$2 = r3
            r0.L$3 = r5
            r0.L$4 = r7
            r0.label = r4
            java.lang.Object r4 = r8.emit(r5, r0)
            if (r4 != r2) goto L_0x008f
            return r2
        L_0x008f:
            r2 = r5
            r5 = r7
            r7 = r11
        L_0x0092:
            kotlin.Unit r4 = (kotlin.Unit) r4
            r10 = r5
            r5 = r2
            r2 = r7
            r7 = r10
        L_0x0098:
            kotlin.Unit r3 = kotlin.Unit.INSTANCE
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.flow.C1786x4264af2b.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
    }

    public C1786x4264af2b(FlowCollector flowCollector, Ref.ObjectRef objectRef, FlowKt__DistinctKt$distinctUntilChangedBy$$inlined$unsafeFlow$3 flowKt__DistinctKt$distinctUntilChangedBy$$inlined$unsafeFlow$3) {
        this.$this_unsafeFlow$inlined = flowCollector;
        this.$previousKey$inlined = objectRef;
        this.this$0 = flowKt__DistinctKt$distinctUntilChangedBy$$inlined$unsafeFlow$3;
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
            final /* synthetic */ C1786x4264af2b this$0;

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
        Object value2 = value;
        Continuation continuation2 = continuation;
        Object key = this.this$0.$keySelector$inlined.invoke(value2);
        if (this.$previousKey$inlined.element == NullSurrogateKt.NULL || !((Boolean) this.this$0.$areEquivalent$inlined.invoke(this.$previousKey$inlined.element, key)).booleanValue()) {
            this.$previousKey$inlined.element = key;
            FlowCollector flowCollector = this.$this_unsafeFlow$inlined;
            InlineMarker.mark(0);
            Object emit = flowCollector.emit(value2, continuation);
            InlineMarker.mark(2);
            InlineMarker.mark(1);
            Unit unit = (Unit) emit;
        }
        return Unit.INSTANCE;
    }
}
