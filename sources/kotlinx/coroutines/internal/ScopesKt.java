package kotlinx.coroutines.internal;

import kotlin.Metadata;
import kotlin.coroutines.Continuation;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.AbstractCoroutine;

@Metadata(mo33669bv = {1, 0, 3}, mo33670d1 = {"\u0000\u000e\n\u0000\n\u0002\u0010\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a\u0018\u0010\u0000\u001a\u00020\u0001*\u0006\u0012\u0002\b\u00030\u00022\u0006\u0010\u0003\u001a\u00020\u0001H\u0000¨\u0006\u0004"}, mo33671d2 = {"tryRecover", "", "Lkotlinx/coroutines/AbstractCoroutine;", "exception", "kotlinx-coroutines-core"}, mo33672k = 2, mo33673mv = {1, 1, 15})
/* compiled from: Scopes.kt */
public final class ScopesKt {
    public static final Throwable tryRecover(AbstractCoroutine<?> $this$tryRecover, Throwable exception) {
        Continuation cont;
        Intrinsics.checkParameterIsNotNull($this$tryRecover, "$this$tryRecover");
        Intrinsics.checkParameterIsNotNull(exception, "exception");
        ScopeCoroutine scopeCoroutine = (ScopeCoroutine) (!($this$tryRecover instanceof ScopeCoroutine) ? null : $this$tryRecover);
        if (scopeCoroutine == null || (cont = scopeCoroutine.uCont) == null) {
            return exception;
        }
        return StackTraceRecoveryKt.recoverStackTrace(exception, cont);
    }
}
