package kotlinx.coroutines;

import kotlin.Metadata;
import kotlin.coroutines.Continuation;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.selects.SelectClause1;
import kotlinx.coroutines.selects.SelectInstance;

@Metadata(mo33669bv = {1, 0, 3}, mo33670d1 = {"\u0000J\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\t\n\u0002\u0010\u0003\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\b\u0002\u0018\u0000*\u0004\b\u0000\u0010\u00012\u00020\u00022\b\u0012\u0004\u0012\u0002H\u00010\u00032\b\u0012\u0004\u0012\u0002H\u00010\u0004B\u000f\u0012\b\u0010\u0005\u001a\u0004\u0018\u00010\u0006¢\u0006\u0002\u0010\u0007J\u0011\u0010\u000f\u001a\u00028\u0000H@ø\u0001\u0000¢\u0006\u0002\u0010\u0010J\u0015\u0010\u0011\u001a\u00020\f2\u0006\u0010\u0012\u001a\u00028\u0000H\u0016¢\u0006\u0002\u0010\u0013J\u0010\u0010\u0014\u001a\u00020\f2\u0006\u0010\u0015\u001a\u00020\u0016H\u0016J\r\u0010\u0017\u001a\u00028\u0000H\u0016¢\u0006\u0002\u0010\u0018JH\u0010\u0019\u001a\u00020\u001a\"\u0004\b\u0001\u0010\u001b2\f\u0010\u001c\u001a\b\u0012\u0004\u0012\u0002H\u001b0\u001d2\"\u0010\u001e\u001a\u001e\b\u0001\u0012\u0004\u0012\u00028\u0000\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u001b0 \u0012\u0006\u0012\u0004\u0018\u00010!0\u001fH\u0016ø\u0001\u0000¢\u0006\u0002\u0010\"R\u001a\u0010\b\u001a\b\u0012\u0004\u0012\u00028\u00000\u00048VX\u0004¢\u0006\u0006\u001a\u0004\b\t\u0010\nR\u0014\u0010\u000b\u001a\u00020\f8PX\u0004¢\u0006\u0006\u001a\u0004\b\r\u0010\u000e\u0002\u0004\n\u0002\b\u0019¨\u0006#"}, mo33671d2 = {"Lkotlinx/coroutines/CompletableDeferredImpl;", "T", "Lkotlinx/coroutines/JobSupport;", "Lkotlinx/coroutines/CompletableDeferred;", "Lkotlinx/coroutines/selects/SelectClause1;", "parent", "Lkotlinx/coroutines/Job;", "(Lkotlinx/coroutines/Job;)V", "onAwait", "getOnAwait", "()Lkotlinx/coroutines/selects/SelectClause1;", "onCancelComplete", "", "getOnCancelComplete$kotlinx_coroutines_core", "()Z", "await", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "complete", "value", "(Ljava/lang/Object;)Z", "completeExceptionally", "exception", "", "getCompleted", "()Ljava/lang/Object;", "registerSelectClause1", "", "R", "select", "Lkotlinx/coroutines/selects/SelectInstance;", "block", "Lkotlin/Function2;", "Lkotlin/coroutines/Continuation;", "", "(Lkotlinx/coroutines/selects/SelectInstance;Lkotlin/jvm/functions/Function2;)V", "kotlinx-coroutines-core"}, mo33672k = 1, mo33673mv = {1, 1, 15})
/* compiled from: CompletableDeferred.kt */
final class CompletableDeferredImpl<T> extends JobSupport implements CompletableDeferred<T>, SelectClause1<T> {
    public CompletableDeferredImpl(Job parent) {
        super(true);
        initParentJobInternal$kotlinx_coroutines_core(parent);
    }

    public boolean getOnCancelComplete$kotlinx_coroutines_core() {
        return true;
    }

    public T getCompleted() {
        return getCompletedInternal$kotlinx_coroutines_core();
    }

    /* Debug info: failed to restart local var, previous not found, register: 5 */
    /* JADX WARNING: Removed duplicated region for block: B:12:0x0037  */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x0024  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.lang.Object await(kotlin.coroutines.Continuation<? super T> r6) {
        /*
            r5 = this;
            boolean r0 = r6 instanceof kotlinx.coroutines.CompletableDeferredImpl$await$1
            if (r0 == 0) goto L_0x0014
            r0 = r6
            kotlinx.coroutines.CompletableDeferredImpl$await$1 r0 = (kotlinx.coroutines.CompletableDeferredImpl$await$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r1 = r1 & r2
            if (r1 == 0) goto L_0x0014
            int r1 = r0.label
            int r1 = r1 - r2
            r0.label = r1
            goto L_0x0019
        L_0x0014:
            kotlinx.coroutines.CompletableDeferredImpl$await$1 r0 = new kotlinx.coroutines.CompletableDeferredImpl$await$1
            r0.<init>(r5, r6)
        L_0x0019:
            java.lang.Object r1 = r0.result
            java.lang.Object r2 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r3 = r0.label
            r4 = 1
            if (r3 == 0) goto L_0x0037
            if (r3 != r4) goto L_0x002f
            java.lang.Object r2 = r0.L$0
            kotlinx.coroutines.CompletableDeferredImpl r2 = (kotlinx.coroutines.CompletableDeferredImpl) r2
            kotlin.ResultKt.throwOnFailure(r1)
            r3 = r1
            goto L_0x0046
        L_0x002f:
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
            java.lang.String r1 = "call to 'resume' before 'invoke' with coroutine"
            r0.<init>(r1)
            throw r0
        L_0x0037:
            kotlin.ResultKt.throwOnFailure(r1)
            r0.L$0 = r5
            r0.label = r4
            java.lang.Object r3 = r5.awaitInternal$kotlinx_coroutines_core(r0)
            if (r3 != r2) goto L_0x0045
            return r2
        L_0x0045:
            r2 = r5
        L_0x0046:
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.CompletableDeferredImpl.await(kotlin.coroutines.Continuation):java.lang.Object");
    }

    public SelectClause1<T> getOnAwait() {
        return this;
    }

    public <R> void registerSelectClause1(SelectInstance<? super R> select, Function2<? super T, ? super Continuation<? super R>, ? extends Object> block) {
        Intrinsics.checkParameterIsNotNull(select, "select");
        Intrinsics.checkParameterIsNotNull(block, "block");
        registerSelectClause1Internal$kotlinx_coroutines_core(select, block);
    }

    public boolean complete(T value) {
        return makeCompleting$kotlinx_coroutines_core(value);
    }

    public boolean completeExceptionally(Throwable exception) {
        Intrinsics.checkParameterIsNotNull(exception, "exception");
        return makeCompleting$kotlinx_coroutines_core(new CompletedExceptionally(exception, false, 2, (DefaultConstructorMarker) null));
    }
}
