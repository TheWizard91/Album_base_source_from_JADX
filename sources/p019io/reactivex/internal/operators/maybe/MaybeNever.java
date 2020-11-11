package p019io.reactivex.internal.operators.maybe;

import p019io.reactivex.Maybe;
import p019io.reactivex.MaybeObserver;
import p019io.reactivex.internal.disposables.EmptyDisposable;

/* renamed from: io.reactivex.internal.operators.maybe.MaybeNever */
public final class MaybeNever extends Maybe<Object> {
    public static final MaybeNever INSTANCE = new MaybeNever();

    /* access modifiers changed from: protected */
    public void subscribeActual(MaybeObserver<? super Object> observer) {
        observer.onSubscribe(EmptyDisposable.NEVER);
    }
}
