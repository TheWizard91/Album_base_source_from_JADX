package p019io.reactivex.internal.operators.maybe;

import p019io.reactivex.Maybe;
import p019io.reactivex.MaybeObserver;
import p019io.reactivex.internal.disposables.EmptyDisposable;
import p019io.reactivex.internal.fuseable.ScalarCallable;

/* renamed from: io.reactivex.internal.operators.maybe.MaybeEmpty */
public final class MaybeEmpty extends Maybe<Object> implements ScalarCallable<Object> {
    public static final MaybeEmpty INSTANCE = new MaybeEmpty();

    /* access modifiers changed from: protected */
    public void subscribeActual(MaybeObserver<? super Object> observer) {
        EmptyDisposable.complete((MaybeObserver<?>) observer);
    }

    public Object call() {
        return null;
    }
}
