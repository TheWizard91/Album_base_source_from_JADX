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
import kotlinx.coroutines.DelayKt;

@Metadata(mo33669bv = {1, 0, 3}, mo33670d1 = {"\u0000\u0010\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0010\u0000\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u0003H@¢\u0006\u0004\b\u0004\u0010\u0005"}, mo33671d2 = {"<anonymous>", "", "T", "Lkotlinx/coroutines/flow/FlowCollector;", "invoke", "(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;"}, mo33672k = 3, mo33673mv = {1, 1, 15})
@DebugMetadata(mo34304c = "kotlinx.coroutines.flow.FlowKt__MigrationKt$delayFlow$1", mo34305f = "Migration.kt", mo34306i = {0}, mo34307l = {421}, mo34308m = "invokeSuspend", mo34309n = {"$this$onStart"}, mo34310s = {"L$0"})
/* compiled from: Migration.kt */
final class FlowKt__MigrationKt$delayFlow$1 extends SuspendLambda implements Function2<FlowCollector<? super T>, Continuation<? super Unit>, Object> {
    final /* synthetic */ long $timeMillis;
    Object L$0;
    int label;

    /* renamed from: p$ */
    private FlowCollector f670p$;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    FlowKt__MigrationKt$delayFlow$1(long j, Continuation continuation) {
        super(2, continuation);
        this.$timeMillis = j;
    }

    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        Intrinsics.checkParameterIsNotNull(continuation, "completion");
        FlowKt__MigrationKt$delayFlow$1 flowKt__MigrationKt$delayFlow$1 = new FlowKt__MigrationKt$delayFlow$1(this.$timeMillis, continuation);
        FlowCollector flowCollector = (FlowCollector) obj;
        flowKt__MigrationKt$delayFlow$1.f670p$ = (FlowCollector) obj;
        return flowKt__MigrationKt$delayFlow$1;
    }

    public final Object invoke(Object obj, Object obj2) {
        return ((FlowKt__MigrationKt$delayFlow$1) create(obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    public final Object invokeSuspend(Object $result) {
        Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure($result);
            FlowCollector $this$onStart = this.f670p$;
            long j = this.$timeMillis;
            this.L$0 = $this$onStart;
            this.label = 1;
            if (DelayKt.delay(j, this) == coroutine_suspended) {
                return coroutine_suspended;
            }
            FlowCollector flowCollector = $this$onStart;
        } else if (i == 1) {
            FlowCollector $this$onStart2 = (FlowCollector) this.L$0;
            ResultKt.throwOnFailure($result);
        } else {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        return Unit.INSTANCE;
    }
}