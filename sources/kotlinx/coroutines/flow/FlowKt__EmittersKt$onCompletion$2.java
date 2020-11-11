package kotlinx.coroutines.flow;

import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;

@Metadata(mo33669bv = {1, 0, 3}, mo33670d1 = {"\u0000\u0016\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0003\n\u0002\b\u0002\u0010\u0000\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u00032\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005H@¢\u0006\u0004\b\u0006\u0010\u0007"}, mo33671d2 = {"<anonymous>", "", "T", "Lkotlinx/coroutines/flow/FlowCollector;", "it", "", "invoke", "(Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;"}, mo33672k = 3, mo33673mv = {1, 1, 15})
@DebugMetadata(mo34304c = "kotlinx.coroutines.flow.FlowKt__EmittersKt$onCompletion$2", mo34305f = "Emitters.kt", mo34306i = {0, 0}, mo34307l = {145}, mo34308m = "invokeSuspend", mo34309n = {"$this$onCompletion", "it"}, mo34310s = {"L$0", "L$1"})
/* compiled from: Emitters.kt */
final class FlowKt__EmittersKt$onCompletion$2 extends SuspendLambda implements Function3<FlowCollector<? super T>, Throwable, Continuation<? super Unit>, Object> {
    final /* synthetic */ Function2 $action;
    Object L$0;
    Object L$1;
    int label;

    /* renamed from: p$ */
    private FlowCollector f653p$;
    private Throwable p$0;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    FlowKt__EmittersKt$onCompletion$2(Function2 function2, Continuation continuation) {
        super(3, continuation);
        this.$action = function2;
    }

    public final Continuation<Unit> create(FlowCollector<? super T> flowCollector, Throwable th, Continuation<? super Unit> continuation) {
        Intrinsics.checkParameterIsNotNull(flowCollector, "$this$create");
        Intrinsics.checkParameterIsNotNull(continuation, "continuation");
        FlowKt__EmittersKt$onCompletion$2 flowKt__EmittersKt$onCompletion$2 = new FlowKt__EmittersKt$onCompletion$2(this.$action, continuation);
        flowKt__EmittersKt$onCompletion$2.f653p$ = flowCollector;
        flowKt__EmittersKt$onCompletion$2.p$0 = th;
        return flowKt__EmittersKt$onCompletion$2;
    }

    public final Object invoke(Object obj, Object obj2, Object obj3) {
        return ((FlowKt__EmittersKt$onCompletion$2) create((FlowCollector) obj, (Throwable) obj2, (Continuation) obj3)).invokeSuspend(Unit.INSTANCE);
    }

    public final Object invokeSuspend(Object $result) {
        Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure($result);
            FlowCollector $this$onCompletion = this.f653p$;
            Throwable it = this.p$0;
            Function2 function2 = this.$action;
            this.L$0 = $this$onCompletion;
            this.L$1 = it;
            this.label = 1;
            if (function2.invoke(it, this) == coroutine_suspended) {
                return coroutine_suspended;
            }
            Throwable th = it;
        } else if (i == 1) {
            Throwable it2 = (Throwable) this.L$1;
            FlowCollector $this$onCompletion2 = (FlowCollector) this.L$0;
            ResultKt.throwOnFailure($result);
        } else {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        return Unit.INSTANCE;
    }
}
