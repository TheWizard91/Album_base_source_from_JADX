package androidx.lifecycle;

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

@Metadata(mo33669bv = {1, 0, 3}, mo33670d1 = {"\u0000\u000e\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H@¢\u0006\u0004\b\u0003\u0010\u0004"}, mo33671d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;", "invoke", "(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;"}, mo33672k = 3, mo33673mv = {1, 1, 15})
@DebugMetadata(mo34304c = "androidx.lifecycle.LifecycleCoroutineScope$launchWhenStarted$1", mo34305f = "Lifecycle.kt", mo34306i = {0}, mo34307l = {87}, mo34308m = "invokeSuspend", mo34309n = {"$this$launch"}, mo34310s = {"L$0"})
/* compiled from: Lifecycle.kt */
final class LifecycleCoroutineScope$launchWhenStarted$1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
    final /* synthetic */ Function2 $block;
    Object L$0;
    int label;

    /* renamed from: p$ */
    private CoroutineScope f31p$;
    final /* synthetic */ LifecycleCoroutineScope this$0;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    LifecycleCoroutineScope$launchWhenStarted$1(LifecycleCoroutineScope lifecycleCoroutineScope, Function2 function2, Continuation continuation) {
        super(2, continuation);
        this.this$0 = lifecycleCoroutineScope;
        this.$block = function2;
    }

    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        Intrinsics.checkParameterIsNotNull(continuation, "completion");
        LifecycleCoroutineScope$launchWhenStarted$1 lifecycleCoroutineScope$launchWhenStarted$1 = new LifecycleCoroutineScope$launchWhenStarted$1(this.this$0, this.$block, continuation);
        CoroutineScope coroutineScope = (CoroutineScope) obj;
        lifecycleCoroutineScope$launchWhenStarted$1.f31p$ = (CoroutineScope) obj;
        return lifecycleCoroutineScope$launchWhenStarted$1;
    }

    public final Object invoke(Object obj, Object obj2) {
        return ((LifecycleCoroutineScope$launchWhenStarted$1) create(obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    public final Object invokeSuspend(Object $result) {
        Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure($result);
            CoroutineScope $this$launch = this.f31p$;
            Lifecycle lifecycle$lifecycle_runtime_ktx_release = this.this$0.getLifecycle$lifecycle_runtime_ktx_release();
            Function2 function2 = this.$block;
            this.L$0 = $this$launch;
            this.label = 1;
            if (PausingDispatcherKt.whenStarted(lifecycle$lifecycle_runtime_ktx_release, function2, this) == coroutine_suspended) {
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
