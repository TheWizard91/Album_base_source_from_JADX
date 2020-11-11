package kotlinx.coroutines.flow;

import kotlin.Metadata;
import kotlin.coroutines.Continuation;
import kotlin.jvm.functions.Function2;

@Metadata(mo33669bv = {1, 0, 3}, mo33670d1 = {"\u0000%\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\b\u0002\n\u0002\b\u0002\n\u0002\b\u0003*\u0001\u0000\b\n\u0018\u00002\b\u0012\u0004\u0012\u00028\u00000\u0001J\u001f\u0010\u0002\u001a\u00020\u00032\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00028\u00000\u0005H@ø\u0001\u0000¢\u0006\u0002\u0010\u0006\u0002\u0004\n\u0002\b\u0019¨\u0006\u0007¸\u0006\b"}, mo33671d2 = {"kotlinx/coroutines/flow/internal/SafeCollectorKt$unsafeFlow$1", "Lkotlinx/coroutines/flow/Flow;", "collect", "", "collector", "Lkotlinx/coroutines/flow/FlowCollector;", "(Lkotlinx/coroutines/flow/FlowCollector;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "kotlinx-coroutines-core", "kotlinx/coroutines/flow/FlowKt__TransformKt$unsafeTransform$$inlined$unsafeFlow$7"}, mo33672k = 1, mo33673mv = {1, 1, 15})
/* compiled from: SafeCollector.kt */
public final class FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1 implements Flow<T> {
    final /* synthetic */ Function2 $action$inlined;
    final /* synthetic */ Flow $this_unsafeTransform$inlined;

    public FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1(Flow flow, Function2 function2) {
        this.$this_unsafeTransform$inlined = flow;
        this.$action$inlined = function2;
    }

    public Object collect(FlowCollector collector, Continuation $completion) {
        final FlowCollector $receiver = collector;
        Continuation continuation = $completion;
        return this.$this_unsafeTransform$inlined.collect(new FlowCollector<T>() {
            /* Debug info: failed to restart local var, previous not found, register: 17 */
            /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r13v3, resolved type: java.lang.Object} */
            /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r11v6, resolved type: kotlinx.coroutines.flow.FlowCollector} */
            /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r13v5, resolved type: java.lang.Object} */
            /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r5v4, resolved type: kotlinx.coroutines.flow.FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1$2$1} */
            /* JADX WARNING: Multi-variable type inference failed */
            /* JADX WARNING: Removed duplicated region for block: B:15:0x007e  */
            /* JADX WARNING: Removed duplicated region for block: B:21:0x00c7 A[RETURN] */
            /* JADX WARNING: Removed duplicated region for block: B:22:0x00c8  */
            /* JADX WARNING: Removed duplicated region for block: B:8:0x0029  */
            /* Code decompiled incorrectly, please refer to instructions dump. */
            public java.lang.Object emit(java.lang.Object r18, kotlin.coroutines.Continuation r19) {
                /*
                    r17 = this;
                    r0 = r17
                    r1 = r19
                    boolean r2 = r1 instanceof kotlinx.coroutines.flow.FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1.C18622.C18631
                    if (r2 == 0) goto L_0x0018
                    r2 = r1
                    kotlinx.coroutines.flow.FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1$2$1 r2 = (kotlinx.coroutines.flow.FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1.C18622.C18631) r2
                    int r3 = r2.label
                    r4 = -2147483648(0xffffffff80000000, float:-0.0)
                    r3 = r3 & r4
                    if (r3 == 0) goto L_0x0018
                    int r3 = r2.label
                    int r3 = r3 - r4
                    r2.label = r3
                    goto L_0x001d
                L_0x0018:
                    kotlinx.coroutines.flow.FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1$2$1 r2 = new kotlinx.coroutines.flow.FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1$2$1
                    r2.<init>(r0, r1)
                L_0x001d:
                    java.lang.Object r3 = r2.result
                    java.lang.Object r4 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
                    int r5 = r2.label
                    r6 = 2
                    r7 = 1
                    if (r5 == 0) goto L_0x007e
                    r8 = 0
                    r9 = 0
                    if (r5 == r7) goto L_0x005c
                    if (r5 != r6) goto L_0x0054
                    r4 = r9
                    r5 = r8
                    r6 = r8
                    r7 = r9
                    r8 = r9
                    r10 = r9
                    java.lang.Object r11 = r2.L$6
                    r8 = r11
                    kotlinx.coroutines.flow.FlowCollector r8 = (kotlinx.coroutines.flow.FlowCollector) r8
                    java.lang.Object r10 = r2.L$5
                    java.lang.Object r11 = r2.L$4
                    r7 = r11
                    kotlin.coroutines.Continuation r7 = (kotlin.coroutines.Continuation) r7
                    java.lang.Object r9 = r2.L$3
                    java.lang.Object r11 = r2.L$2
                    r4 = r11
                    kotlinx.coroutines.flow.FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1$2$1 r4 = (kotlinx.coroutines.flow.FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1.C18622.C18631) r4
                    java.lang.Object r11 = r2.L$1
                    java.lang.Object r12 = r2.L$0
                    kotlinx.coroutines.flow.FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1$2 r12 = (kotlinx.coroutines.flow.FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1.C18622) r12
                    kotlin.ResultKt.throwOnFailure(r3)
                    r6 = r3
                    goto L_0x00ca
                L_0x0054:
                    java.lang.IllegalStateException r2 = new java.lang.IllegalStateException
                    java.lang.String r3 = "call to 'resume' before 'invoke' with coroutine"
                    r2.<init>(r3)
                    throw r2
                L_0x005c:
                    r5 = r9
                    r7 = r8
                    r10 = r9
                    r11 = r9
                    r12 = r9
                    java.lang.Object r13 = r2.L$6
                    r11 = r13
                    kotlinx.coroutines.flow.FlowCollector r11 = (kotlinx.coroutines.flow.FlowCollector) r11
                    java.lang.Object r12 = r2.L$5
                    java.lang.Object r13 = r2.L$4
                    r10 = r13
                    kotlin.coroutines.Continuation r10 = (kotlin.coroutines.Continuation) r10
                    java.lang.Object r9 = r2.L$3
                    java.lang.Object r13 = r2.L$2
                    r5 = r13
                    kotlinx.coroutines.flow.FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1$2$1 r5 = (kotlinx.coroutines.flow.FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1.C18622.C18631) r5
                    java.lang.Object r13 = r2.L$1
                    java.lang.Object r14 = r2.L$0
                    kotlinx.coroutines.flow.FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1$2 r14 = (kotlinx.coroutines.flow.FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1.C18622) r14
                    kotlin.ResultKt.throwOnFailure(r3)
                    goto L_0x00b1
                L_0x007e:
                    kotlin.ResultKt.throwOnFailure(r3)
                    r5 = r2
                    r9 = r18
                    r8 = 0
                    kotlinx.coroutines.flow.FlowCollector r10 = r0
                    r11 = r2
                    kotlin.coroutines.Continuation r11 = (kotlin.coroutines.Continuation) r11
                    r12 = r9
                    r13 = 0
                    kotlinx.coroutines.flow.FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1 r14 = r6
                    kotlin.jvm.functions.Function2 r14 = r14.$action$inlined
                    r2.L$0 = r0
                    r15 = r18
                    r2.L$1 = r15
                    r2.L$2 = r5
                    r2.L$3 = r9
                    r2.L$4 = r11
                    r2.L$5 = r12
                    r2.L$6 = r10
                    r2.label = r7
                    java.lang.Object r7 = r14.invoke(r12, r2)
                    if (r7 != r4) goto L_0x00a9
                    return r4
                L_0x00a9:
                    r14 = r0
                    r7 = r13
                    r13 = r15
                    r16 = r11
                    r11 = r10
                    r10 = r16
                L_0x00b1:
                    r2.L$0 = r14
                    r2.L$1 = r13
                    r2.L$2 = r5
                    r2.L$3 = r9
                    r2.L$4 = r10
                    r2.L$5 = r12
                    r2.L$6 = r11
                    r2.label = r6
                    java.lang.Object r6 = r11.emit(r12, r2)
                    if (r6 != r4) goto L_0x00c8
                    return r4
                L_0x00c8:
                    r11 = r13
                    r12 = r14
                L_0x00ca:
                    return r6
                */
                throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.flow.FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1.C18622.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
            }
        }, $completion);
    }
}
