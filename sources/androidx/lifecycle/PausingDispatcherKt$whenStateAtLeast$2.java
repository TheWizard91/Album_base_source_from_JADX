package androidx.lifecycle;

import androidx.lifecycle.Lifecycle;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.CoroutineScope;

@Metadata(mo33669bv = {1, 0, 3}, mo33670d1 = {"\u0000\f\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u0010\u0000\u001a\u0002H\u0001\"\u0004\b\u0000\u0010\u0001*\u00020\u0002H@¢\u0006\u0004\b\u0003\u0010\u0004"}, mo33671d2 = {"<anonymous>", "T", "Lkotlinx/coroutines/CoroutineScope;", "invoke", "(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;"}, mo33672k = 3, mo33673mv = {1, 1, 15})
@DebugMetadata(mo34304c = "androidx.lifecycle.PausingDispatcherKt$whenStateAtLeast$2", mo34305f = "PausingDispatcher.kt", mo34306i = {0, 0, 0, 0}, mo34307l = {163}, mo34308m = "invokeSuspend", mo34309n = {"$this$withContext", "job", "dispatcher", "controller"}, mo34310s = {"L$0", "L$1", "L$2", "L$3"})
/* compiled from: PausingDispatcher.kt */
final class PausingDispatcherKt$whenStateAtLeast$2 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super T>, Object> {
    final /* synthetic */ Function2 $block;
    final /* synthetic */ Lifecycle.State $minState;
    final /* synthetic */ Lifecycle $this_whenStateAtLeast;
    Object L$0;
    Object L$1;
    Object L$2;
    Object L$3;
    int label;

    /* renamed from: p$ */
    private CoroutineScope f33p$;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    PausingDispatcherKt$whenStateAtLeast$2(Lifecycle lifecycle, Lifecycle.State state, Function2 function2, Continuation continuation) {
        super(2, continuation);
        this.$this_whenStateAtLeast = lifecycle;
        this.$minState = state;
        this.$block = function2;
    }

    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        Intrinsics.checkParameterIsNotNull(continuation, "completion");
        PausingDispatcherKt$whenStateAtLeast$2 pausingDispatcherKt$whenStateAtLeast$2 = new PausingDispatcherKt$whenStateAtLeast$2(this.$this_whenStateAtLeast, this.$minState, this.$block, continuation);
        CoroutineScope coroutineScope = (CoroutineScope) obj;
        pausingDispatcherKt$whenStateAtLeast$2.f33p$ = (CoroutineScope) obj;
        return pausingDispatcherKt$whenStateAtLeast$2;
    }

    public final Object invoke(Object obj, Object obj2) {
        return ((PausingDispatcherKt$whenStateAtLeast$2) create(obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v5, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v10, resolved type: androidx.lifecycle.LifecycleController} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final java.lang.Object invokeSuspend(java.lang.Object r10) {
        /*
            r9 = this;
            java.lang.Object r0 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r1 = r9.label
            r2 = 1
            if (r1 == 0) goto L_0x0033
            if (r1 != r2) goto L_0x002b
            r0 = 0
            r1 = r0
            r2 = r0
            r3 = r0
            java.lang.Object r4 = r9.L$3
            r0 = r4
            androidx.lifecycle.LifecycleController r0 = (androidx.lifecycle.LifecycleController) r0
            java.lang.Object r4 = r9.L$2
            r2 = r4
            androidx.lifecycle.PausingDispatcher r2 = (androidx.lifecycle.PausingDispatcher) r2
            java.lang.Object r4 = r9.L$1
            r3 = r4
            kotlinx.coroutines.Job r3 = (kotlinx.coroutines.Job) r3
            java.lang.Object r4 = r9.L$0
            r1 = r4
            kotlinx.coroutines.CoroutineScope r1 = (kotlinx.coroutines.CoroutineScope) r1
            kotlin.ResultKt.throwOnFailure(r10)     // Catch:{ all -> 0x0029 }
            r4 = r2
            r2 = r10
            goto L_0x0072
        L_0x0029:
            r4 = move-exception
            goto L_0x007c
        L_0x002b:
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
            java.lang.String r1 = "call to 'resume' before 'invoke' with coroutine"
            r0.<init>(r1)
            throw r0
        L_0x0033:
            kotlin.ResultKt.throwOnFailure(r10)
            kotlinx.coroutines.CoroutineScope r1 = r9.f33p$
            kotlin.coroutines.CoroutineContext r3 = r1.getCoroutineContext()
            kotlinx.coroutines.Job$Key r4 = kotlinx.coroutines.Job.Key
            kotlin.coroutines.CoroutineContext$Key r4 = (kotlin.coroutines.CoroutineContext.Key) r4
            kotlin.coroutines.CoroutineContext$Element r3 = r3.get(r4)
            kotlinx.coroutines.Job r3 = (kotlinx.coroutines.Job) r3
            if (r3 == 0) goto L_0x0080
            androidx.lifecycle.PausingDispatcher r4 = new androidx.lifecycle.PausingDispatcher
            r4.<init>()
            androidx.lifecycle.LifecycleController r5 = new androidx.lifecycle.LifecycleController
            androidx.lifecycle.Lifecycle r6 = r9.$this_whenStateAtLeast
            androidx.lifecycle.Lifecycle$State r7 = r9.$minState
            androidx.lifecycle.DispatchQueue r8 = r4.dispatchQueue
            r5.<init>(r6, r7, r8, r3)
            r6 = r4
            kotlin.coroutines.CoroutineContext r6 = (kotlin.coroutines.CoroutineContext) r6     // Catch:{ all -> 0x0078 }
            kotlin.jvm.functions.Function2 r7 = r9.$block     // Catch:{ all -> 0x0078 }
            r9.L$0 = r1     // Catch:{ all -> 0x0078 }
            r9.L$1 = r3     // Catch:{ all -> 0x0078 }
            r9.L$2 = r4     // Catch:{ all -> 0x0078 }
            r9.L$3 = r5     // Catch:{ all -> 0x0078 }
            r9.label = r2     // Catch:{ all -> 0x0078 }
            java.lang.Object r2 = kotlinx.coroutines.BuildersKt.withContext(r6, r7, r9)     // Catch:{ all -> 0x0078 }
            if (r2 != r0) goto L_0x0071
            return r0
        L_0x0071:
            r0 = r5
        L_0x0072:
            r0.finish()
            return r2
        L_0x0078:
            r0 = move-exception
            r2 = r4
            r4 = r0
            r0 = r5
        L_0x007c:
            r0.finish()
            throw r4
        L_0x0080:
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
            java.lang.String r2 = "when[State] methods should have a parent job"
            java.lang.String r2 = r2.toString()
            r0.<init>(r2)
            java.lang.Throwable r0 = (java.lang.Throwable) r0
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.lifecycle.PausingDispatcherKt$whenStateAtLeast$2.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
