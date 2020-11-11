package kotlinx.coroutines.flow;

import kotlin.Metadata;
import kotlin.coroutines.Continuation;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Ref;
import kotlinx.coroutines.flow.internal.NullSurrogateKt;

@Metadata(mo33669bv = {1, 0, 3}, mo33670d1 = {"\u0000%\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\b\u0002\n\u0002\b\u0002\n\u0002\b\u0003*\u0001\u0000\b\n\u0018\u00002\b\u0012\u0004\u0012\u00028\u00000\u0001J\u001f\u0010\u0002\u001a\u00020\u00032\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00028\u00000\u0005H@ø\u0001\u0000¢\u0006\u0002\u0010\u0006\u0002\u0004\n\u0002\b\u0019¨\u0006\u0007¸\u0006\b"}, mo33671d2 = {"kotlinx/coroutines/flow/internal/SafeCollectorKt$unsafeFlow$1", "Lkotlinx/coroutines/flow/Flow;", "collect", "", "collector", "Lkotlinx/coroutines/flow/FlowCollector;", "(Lkotlinx/coroutines/flow/FlowCollector;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "kotlinx-coroutines-core", "kotlinx/coroutines/flow/FlowKt__DistinctKt$distinctUntilChangedBy$$inlined$unsafeFlow$2"}, mo33672k = 1, mo33673mv = {1, 1, 15})
/* renamed from: kotlinx.coroutines.flow.FlowKt__DistinctKt$distinctUntilChangedBy$$inlined$distinctUntilChangedBy$FlowKt__DistinctKt$1 */
/* compiled from: SafeCollector.kt */
public final class C1776xde9fe73a implements Flow<T> {
    final /* synthetic */ Function1 $keySelector$inlined;
    final /* synthetic */ Flow $this_distinctUntilChangedBy$inlined;

    public C1776xde9fe73a(Flow flow, Function1 function1) {
        this.$this_distinctUntilChangedBy$inlined = flow;
        this.$keySelector$inlined = function1;
    }

    public Object collect(FlowCollector collector, Continuation $completion) {
        final FlowCollector $receiver = collector;
        Continuation continuation = $completion;
        final Ref.ObjectRef previousKey = new Ref.ObjectRef();
        previousKey.element = NullSurrogateKt.NULL;
        return this.$this_distinctUntilChangedBy$inlined.collect(new FlowCollector<T>() {
            /* Debug info: failed to restart local var, previous not found, register: 12 */
            /* JADX WARNING: Removed duplicated region for block: B:12:0x0048  */
            /* JADX WARNING: Removed duplicated region for block: B:8:0x0024  */
            /* Code decompiled incorrectly, please refer to instructions dump. */
            public java.lang.Object emit(java.lang.Object r13, kotlin.coroutines.Continuation r14) {
                /*
                    r12 = this;
                    boolean r0 = r14 instanceof kotlinx.coroutines.flow.C1776xde9fe73a.C17772.C17781
                    if (r0 == 0) goto L_0x0014
                    r0 = r14
                    kotlinx.coroutines.flow.FlowKt__DistinctKt$distinctUntilChangedBy$$inlined$distinctUntilChangedBy$FlowKt__DistinctKt$1$2$1 r0 = (kotlinx.coroutines.flow.C1776xde9fe73a.C17772.C17781) r0
                    int r1 = r0.label
                    r2 = -2147483648(0xffffffff80000000, float:-0.0)
                    r1 = r1 & r2
                    if (r1 == 0) goto L_0x0014
                    int r1 = r0.label
                    int r1 = r1 - r2
                    r0.label = r1
                    goto L_0x0019
                L_0x0014:
                    kotlinx.coroutines.flow.FlowKt__DistinctKt$distinctUntilChangedBy$$inlined$distinctUntilChangedBy$FlowKt__DistinctKt$1$2$1 r0 = new kotlinx.coroutines.flow.FlowKt__DistinctKt$distinctUntilChangedBy$$inlined$distinctUntilChangedBy$FlowKt__DistinctKt$1$2$1
                    r0.<init>(r12, r14)
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
                    kotlinx.coroutines.flow.FlowKt__DistinctKt$distinctUntilChangedBy$$inlined$distinctUntilChangedBy$FlowKt__DistinctKt$1$2$1 r3 = (kotlinx.coroutines.flow.C1776xde9fe73a.C17772.C17781) r3
                    java.lang.Object r13 = r0.L$1
                    java.lang.Object r6 = r0.L$0
                    kotlinx.coroutines.flow.FlowKt__DistinctKt$distinctUntilChangedBy$$inlined$distinctUntilChangedBy$FlowKt__DistinctKt$1$2 r6 = (kotlinx.coroutines.flow.C1776xde9fe73a.C17772) r6
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
                    r5 = r13
                    r6 = 0
                    kotlinx.coroutines.flow.FlowKt__DistinctKt$distinctUntilChangedBy$$inlined$distinctUntilChangedBy$FlowKt__DistinctKt$1 r7 = r7
                    kotlin.jvm.functions.Function1 r7 = r7.$keySelector$inlined
                    java.lang.Object r7 = r7.invoke(r5)
                    kotlin.jvm.internal.Ref$ObjectRef r8 = r3
                    T r8 = r8.element
                    kotlinx.coroutines.internal.Symbol r9 = kotlinx.coroutines.flow.internal.NullSurrogateKt.NULL
                    if (r8 == r9) goto L_0x0076
                    kotlin.jvm.internal.Ref$ObjectRef r8 = r3
                    T r8 = r8.element
                    r9 = r7
                    r10 = 0
                    boolean r8 = kotlin.jvm.internal.Intrinsics.areEqual((java.lang.Object) r8, (java.lang.Object) r9)
                    java.lang.Boolean r8 = kotlin.coroutines.jvm.internal.Boxing.boxBoolean(r8)
                    boolean r8 = r8.booleanValue()
                    if (r8 != 0) goto L_0x0074
                    goto L_0x0076
                L_0x0074:
                    r2 = r12
                    goto L_0x0098
                L_0x0076:
                    kotlin.jvm.internal.Ref$ObjectRef r8 = r3
                    r8.element = r7
                    kotlinx.coroutines.flow.FlowCollector r8 = r0
                    r0.L$0 = r12
                    r0.L$1 = r13
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
                    r7 = r12
                L_0x0092:
                    kotlin.Unit r4 = (kotlin.Unit) r4
                    r11 = r5
                    r5 = r2
                    r2 = r7
                    r7 = r11
                L_0x0098:
                    kotlin.Unit r3 = kotlin.Unit.INSTANCE
                    return r3
                */
                throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.flow.C1776xde9fe73a.C17772.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
            }
        }, $completion);
    }
}
