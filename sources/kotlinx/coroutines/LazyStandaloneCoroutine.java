package kotlinx.coroutines;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.intrinsics.CancellableKt;

@Metadata(mo33669bv = {1, 0, 3}, mo33670d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\b\u0002\u0018\u00002\u00020\u0001B9\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012'\u0010\u0004\u001a#\b\u0001\u0012\u0004\u0012\u00020\u0006\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\u0007\u0012\u0006\u0012\u0004\u0018\u00010\t0\u0005¢\u0006\u0002\b\nø\u0001\u0000¢\u0006\u0002\u0010\u000bJ\b\u0010\r\u001a\u00020\bH\u0014R6\u0010\u0004\u001a%\b\u0001\u0012\u0004\u0012\u00020\u0006\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\u0007\u0012\u0006\u0012\u0004\u0018\u00010\t\u0018\u00010\u0005¢\u0006\u0002\b\nX\u000eø\u0001\u0000¢\u0006\u0004\n\u0002\u0010\f\u0002\u0004\n\u0002\b\u0019¨\u0006\u000e"}, mo33671d2 = {"Lkotlinx/coroutines/LazyStandaloneCoroutine;", "Lkotlinx/coroutines/StandaloneCoroutine;", "parentContext", "Lkotlin/coroutines/CoroutineContext;", "block", "Lkotlin/Function2;", "Lkotlinx/coroutines/CoroutineScope;", "Lkotlin/coroutines/Continuation;", "", "", "Lkotlin/ExtensionFunctionType;", "(Lkotlin/coroutines/CoroutineContext;Lkotlin/jvm/functions/Function2;)V", "Lkotlin/jvm/functions/Function2;", "onStart", "kotlinx-coroutines-core"}, mo33672k = 1, mo33673mv = {1, 1, 15})
/* compiled from: Builders.common.kt */
final class LazyStandaloneCoroutine extends StandaloneCoroutine {
    private Function2<? super CoroutineScope, ? super Continuation<? super Unit>, ? extends Object> block;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public LazyStandaloneCoroutine(CoroutineContext parentContext, Function2<? super CoroutineScope, ? super Continuation<? super Unit>, ? extends Object> block2) {
        super(parentContext, false);
        Intrinsics.checkParameterIsNotNull(parentContext, "parentContext");
        Intrinsics.checkParameterIsNotNull(block2, "block");
        this.block = block2;
    }

    /* access modifiers changed from: protected */
    public void onStart() {
        Function2 block2 = this.block;
        if (block2 != null) {
            this.block = null;
            CancellableKt.startCoroutineCancellable(block2, this, this);
            return;
        }
        throw new IllegalStateException("Already started".toString());
    }
}
