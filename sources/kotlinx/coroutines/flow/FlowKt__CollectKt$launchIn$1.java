package kotlinx.coroutines.flow;

import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.CoroutineScope;

@Metadata(mo33669bv = {1, 0, 3}, mo33670d1 = {"\u0000\u0010\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0010\u0000\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\u00020\u0003H@¢\u0006\u0004\b\u0004\u0010\u0005"}, mo33671d2 = {"<anonymous>", "", "T", "Lkotlinx/coroutines/CoroutineScope;", "invoke", "(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;"}, mo33672k = 3, mo33673mv = {1, 1, 15})
@DebugMetadata(mo34304c = "kotlinx.coroutines.flow.FlowKt__CollectKt$launchIn$1", mo34305f = "Collect.kt", mo34306i = {0}, mo34307l = {51}, mo34308m = "invokeSuspend", mo34309n = {"$this$launch"}, mo34310s = {"L$0"})
/* compiled from: Collect.kt */
final class FlowKt__CollectKt$launchIn$1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
    final /* synthetic */ Flow $this_launchIn;
    Object L$0;
    int label;

    /* renamed from: p$ */
    private CoroutineScope f647p$;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    FlowKt__CollectKt$launchIn$1(Flow flow, Continuation continuation) {
        super(2, continuation);
        this.$this_launchIn = flow;
    }

    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        Intrinsics.checkParameterIsNotNull(continuation, "completion");
        FlowKt__CollectKt$launchIn$1 flowKt__CollectKt$launchIn$1 = new FlowKt__CollectKt$launchIn$1(this.$this_launchIn, continuation);
        CoroutineScope coroutineScope = (CoroutineScope) obj;
        flowKt__CollectKt$launchIn$1.f647p$ = (CoroutineScope) obj;
        return flowKt__CollectKt$launchIn$1;
    }

    public final Object invoke(Object obj, Object obj2) {
        return ((FlowKt__CollectKt$launchIn$1) create(obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    public final Object invokeSuspend(Object $result) {
        Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure($result);
            CoroutineScope $this$launch = this.f647p$;
            Flow flow = this.$this_launchIn;
            this.L$0 = $this$launch;
            this.label = 1;
            if (FlowKt.collect(flow, this) == coroutine_suspended) {
                return coroutine_suspended;
            }
            CoroutineScope coroutineScope = $this$launch;
        } else if (i == 1) {
            CoroutineScope $this$launch2 = (CoroutineScope) this.L$0;
            ResultKt.throwOnFailure($result);
        } else {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        return Unit.INSTANCE;
    }
}
