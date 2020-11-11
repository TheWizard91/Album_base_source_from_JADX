package kotlinx.coroutines.flow;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.InlineMarker;
import kotlin.jvm.internal.Intrinsics;

@Metadata(mo33669bv = {1, 0, 3}, mo33670d1 = {"\u0000\u0012\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\u0010\u0000\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003*\b\u0012\u0004\u0012\u0002H\u00030\u00042\u0006\u0010\u0005\u001a\u0002H\u0002H@¢\u0006\u0004\b\u0006\u0010\u0007"}, mo33671d2 = {"<anonymous>", "", "T", "R", "Lkotlinx/coroutines/flow/FlowCollector;", "it", "invoke", "(Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;"}, mo33672k = 3, mo33673mv = {1, 1, 15})
@DebugMetadata(mo34304c = "kotlinx.coroutines.flow.FlowKt__MergeKt$flatMapLatest$1", mo34305f = "Merge.kt", mo34306i = {0, 0, 1, 1, 1, 1}, mo34307l = {154, 180}, mo34308m = "invokeSuspend", mo34309n = {"$this$transformLatest", "it", "$this$transformLatest", "it", "$this$emitAll$iv", "flow$iv"}, mo34310s = {"L$0", "L$1", "L$0", "L$1", "L$2", "L$3"})
/* compiled from: Merge.kt */
public final class FlowKt__MergeKt$flatMapLatest$1 extends SuspendLambda implements Function3<FlowCollector<? super R>, T, Continuation<? super Unit>, Object> {
    final /* synthetic */ Function2 $transform;
    Object L$0;
    Object L$1;
    Object L$2;
    Object L$3;
    int label;

    /* renamed from: p$ */
    private FlowCollector f658p$;
    private Object p$0;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public FlowKt__MergeKt$flatMapLatest$1(Function2 function2, Continuation continuation) {
        super(3, continuation);
        this.$transform = function2;
    }

    public final Continuation<Unit> create(FlowCollector<? super R> flowCollector, T t, Continuation<? super Unit> continuation) {
        Intrinsics.checkParameterIsNotNull(flowCollector, "$this$create");
        Intrinsics.checkParameterIsNotNull(continuation, "continuation");
        FlowKt__MergeKt$flatMapLatest$1 flowKt__MergeKt$flatMapLatest$1 = new FlowKt__MergeKt$flatMapLatest$1(this.$transform, continuation);
        flowKt__MergeKt$flatMapLatest$1.f658p$ = flowCollector;
        flowKt__MergeKt$flatMapLatest$1.p$0 = t;
        return flowKt__MergeKt$flatMapLatest$1;
    }

    public final Object invoke(Object obj, Object obj2, Object obj3) {
        return ((FlowKt__MergeKt$flatMapLatest$1) create((FlowCollector) obj, obj2, (Continuation) obj3)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r5v3, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v6, resolved type: kotlinx.coroutines.flow.FlowCollector} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final java.lang.Object invokeSuspend(java.lang.Object r8) {
        /*
            r7 = this;
            java.lang.Object r0 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r1 = r7.label
            r2 = 2
            r3 = 1
            if (r1 == 0) goto L_0x0043
            r4 = 0
            if (r1 == r3) goto L_0x0030
            if (r1 != r2) goto L_0x0028
            r0 = r4
            r1 = r4
            r2 = r4
            r3 = 0
            java.lang.Object r5 = r7.L$3
            r4 = r5
            kotlinx.coroutines.flow.Flow r4 = (kotlinx.coroutines.flow.Flow) r4
            java.lang.Object r5 = r7.L$2
            r1 = r5
            kotlinx.coroutines.flow.FlowCollector r1 = (kotlinx.coroutines.flow.FlowCollector) r1
            java.lang.Object r2 = r7.L$1
            java.lang.Object r5 = r7.L$0
            r0 = r5
            kotlinx.coroutines.flow.FlowCollector r0 = (kotlinx.coroutines.flow.FlowCollector) r0
            kotlin.ResultKt.throwOnFailure(r8)
            goto L_0x0075
        L_0x0028:
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
            java.lang.String r1 = "call to 'resume' before 'invoke' with coroutine"
            r0.<init>(r1)
            throw r0
        L_0x0030:
            r1 = r4
            r3 = r4
            java.lang.Object r4 = r7.L$2
            kotlinx.coroutines.flow.FlowCollector r4 = (kotlinx.coroutines.flow.FlowCollector) r4
            java.lang.Object r3 = r7.L$1
            java.lang.Object r5 = r7.L$0
            r1 = r5
            kotlinx.coroutines.flow.FlowCollector r1 = (kotlinx.coroutines.flow.FlowCollector) r1
            kotlin.ResultKt.throwOnFailure(r8)
            r5 = r4
            r4 = r8
            goto L_0x005f
        L_0x0043:
            kotlin.ResultKt.throwOnFailure(r8)
            kotlinx.coroutines.flow.FlowCollector r4 = r7.f658p$
            java.lang.Object r1 = r7.p$0
            kotlin.jvm.functions.Function2 r5 = r7.$transform
            r7.L$0 = r4
            r7.L$1 = r1
            r7.L$2 = r4
            r7.label = r3
            java.lang.Object r3 = r5.invoke(r1, r7)
            if (r3 != r0) goto L_0x005b
            return r0
        L_0x005b:
            r5 = r4
            r4 = r3
            r3 = r1
            r1 = r5
        L_0x005f:
            kotlinx.coroutines.flow.Flow r4 = (kotlinx.coroutines.flow.Flow) r4
            r6 = 0
            r7.L$0 = r1
            r7.L$1 = r3
            r7.L$2 = r5
            r7.L$3 = r4
            r7.label = r2
            java.lang.Object r2 = r4.collect(r5, r7)
            if (r2 != r0) goto L_0x0073
            return r0
        L_0x0073:
            r0 = r1
            r2 = r3
        L_0x0075:
            kotlin.Unit r1 = kotlin.Unit.INSTANCE
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.flow.FlowKt__MergeKt$flatMapLatest$1.invokeSuspend(java.lang.Object):java.lang.Object");
    }

    public final Object invokeSuspend$$forInline(Object $result) {
        FlowCollector $this$transformLatest = this.f658p$;
        Object it = this.p$0;
        InlineMarker.mark(0);
        ((Flow) this.$transform.invoke(it, this)).collect($this$transformLatest, this);
        InlineMarker.mark(2);
        InlineMarker.mark(1);
        return Unit.INSTANCE;
    }
}
