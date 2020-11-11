package kotlinx.coroutines.flow.internal;

import kotlin.Metadata;
import kotlin.jvm.internal.Ref;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.FlowCollector;

@Metadata(mo33669bv = {1, 0, 3}, mo33670d1 = {"\u0000\u001b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\b\u0003\n\u0002\b\u0003*\u0001\u0000\b\n\u0018\u00002\b\u0012\u0004\u0012\u00028\u00000\u0001J\u0019\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00028\u0000H@ø\u0001\u0000¢\u0006\u0002\u0010\u0005\u0002\u0004\n\u0002\b\u0019¨\u0006\u0006¸\u0006\u0000"}, mo33671d2 = {"kotlinx/coroutines/flow/FlowKt__CollectKt$collect$3", "Lkotlinx/coroutines/flow/FlowCollector;", "emit", "", "value", "(Ljava/lang/Object;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "kotlinx-coroutines-core"}, mo33672k = 1, mo33673mv = {1, 1, 15})
/* renamed from: kotlinx.coroutines.flow.internal.ChannelFlowTransformLatest$flowCollect$3$invokeSuspend$$inlined$collect$1 */
/* compiled from: Collect.kt */
public final class C1916xfa64eeb5 implements FlowCollector<T> {
    final /* synthetic */ Ref.ObjectRef $previousFlow$inlined;
    final /* synthetic */ CoroutineScope $this_flowScope$inlined;
    final /* synthetic */ ChannelFlowTransformLatest$flowCollect$3 this$0;

    /* Debug info: failed to restart local var, previous not found, register: 20 */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r10v6, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v6, resolved type: kotlin.coroutines.Continuation} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:12:0x0053  */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x0029  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.lang.Object emit(java.lang.Object r21, kotlin.coroutines.Continuation r22) {
        /*
            r20 = this;
            r0 = r20
            r1 = r22
            boolean r2 = r1 instanceof kotlinx.coroutines.flow.internal.C1916xfa64eeb5.C19171
            if (r2 == 0) goto L_0x0018
            r2 = r1
            kotlinx.coroutines.flow.internal.ChannelFlowTransformLatest$flowCollect$3$invokeSuspend$$inlined$collect$1$1 r2 = (kotlinx.coroutines.flow.internal.C1916xfa64eeb5.C19171) r2
            int r3 = r2.label
            r4 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r3 & r4
            if (r3 == 0) goto L_0x0018
            int r3 = r2.label
            int r3 = r3 - r4
            r2.label = r3
            goto L_0x001d
        L_0x0018:
            kotlinx.coroutines.flow.internal.ChannelFlowTransformLatest$flowCollect$3$invokeSuspend$$inlined$collect$1$1 r2 = new kotlinx.coroutines.flow.internal.ChannelFlowTransformLatest$flowCollect$3$invokeSuspend$$inlined$collect$1$1
            r2.<init>(r0, r1)
        L_0x001d:
            java.lang.Object r3 = r2.result
            java.lang.Object r4 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r5 = r2.label
            r6 = 1
            r7 = 0
            if (r5 == 0) goto L_0x0053
            if (r5 != r6) goto L_0x004b
            r4 = r7
            r5 = r7
            r6 = 0
            r8 = r6
            r9 = r7
            java.lang.Object r10 = r2.L$5
            r5 = r10
            kotlinx.coroutines.Job r5 = (kotlinx.coroutines.Job) r5
            java.lang.Object r10 = r2.L$4
            kotlinx.coroutines.Job r10 = (kotlinx.coroutines.Job) r10
            java.lang.Object r9 = r2.L$3
            java.lang.Object r10 = r2.L$2
            r4 = r10
            kotlin.coroutines.Continuation r4 = (kotlin.coroutines.Continuation) r4
            java.lang.Object r10 = r2.L$1
            java.lang.Object r11 = r2.L$0
            kotlinx.coroutines.flow.internal.ChannelFlowTransformLatest$flowCollect$3$invokeSuspend$$inlined$collect$1 r11 = (kotlinx.coroutines.flow.internal.C1916xfa64eeb5) r11
            kotlin.ResultKt.throwOnFailure(r3)
            r13 = r10
            goto L_0x008c
        L_0x004b:
            java.lang.IllegalStateException r2 = new java.lang.IllegalStateException
            java.lang.String r3 = "call to 'resume' before 'invoke' with coroutine"
            r2.<init>(r3)
            throw r2
        L_0x0053:
            kotlin.ResultKt.throwOnFailure(r3)
            r5 = r2
            kotlin.coroutines.Continuation r5 = (kotlin.coroutines.Continuation) r5
            r9 = r21
            r8 = 0
            kotlin.jvm.internal.Ref$ObjectRef r10 = r0.$previousFlow$inlined
            T r10 = r10.element
            kotlinx.coroutines.Job r10 = (kotlinx.coroutines.Job) r10
            if (r10 == 0) goto L_0x0090
            r11 = r10
            r12 = 0
            kotlinx.coroutines.flow.internal.ChildCancelledException r13 = new kotlinx.coroutines.flow.internal.ChildCancelledException
            r13.<init>()
            java.util.concurrent.CancellationException r13 = (java.util.concurrent.CancellationException) r13
            r11.cancel((java.util.concurrent.CancellationException) r13)
            r2.L$0 = r0
            r13 = r21
            r2.L$1 = r13
            r2.L$2 = r5
            r2.L$3 = r9
            r2.L$4 = r10
            r2.L$5 = r11
            r2.label = r6
            java.lang.Object r6 = r11.join(r2)
            if (r6 != r4) goto L_0x0087
            return r4
        L_0x0087:
            r4 = r5
            r6 = r8
            r5 = r11
            r8 = r12
            r11 = r0
        L_0x008c:
            r5 = r4
            r8 = r6
            goto L_0x0093
        L_0x0090:
            r13 = r21
            r11 = r0
        L_0x0093:
            kotlin.jvm.internal.Ref$ObjectRef r4 = r11.$previousFlow$inlined
            kotlinx.coroutines.CoroutineScope r14 = r11.$this_flowScope$inlined
            r15 = 0
            kotlinx.coroutines.CoroutineStart r16 = kotlinx.coroutines.CoroutineStart.UNDISPATCHED
            kotlinx.coroutines.flow.internal.ChannelFlowTransformLatest$flowCollect$3$invokeSuspend$$inlined$collect$1$lambda$1 r6 = new kotlinx.coroutines.flow.internal.ChannelFlowTransformLatest$flowCollect$3$invokeSuspend$$inlined$collect$1$lambda$1
            r6.<init>(r9, r7, r11)
            r17 = r6
            kotlin.jvm.functions.Function2 r17 = (kotlin.jvm.functions.Function2) r17
            r18 = 1
            r19 = 0
            kotlinx.coroutines.Job r6 = kotlinx.coroutines.BuildersKt__Builders_commonKt.launch$default(r14, r15, r16, r17, r18, r19)
            r4.element = r6
            kotlin.Unit r4 = kotlin.Unit.INSTANCE
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.flow.internal.C1916xfa64eeb5.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
    }

    public C1916xfa64eeb5(ChannelFlowTransformLatest$flowCollect$3 channelFlowTransformLatest$flowCollect$3, CoroutineScope coroutineScope, Ref.ObjectRef objectRef) {
        this.this$0 = channelFlowTransformLatest$flowCollect$3;
        this.$this_flowScope$inlined = coroutineScope;
        this.$previousFlow$inlined = objectRef;
    }
}
