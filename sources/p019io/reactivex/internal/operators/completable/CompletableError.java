package p019io.reactivex.internal.operators.completable;

import p019io.reactivex.Completable;
import p019io.reactivex.CompletableObserver;
import p019io.reactivex.internal.disposables.EmptyDisposable;

/* renamed from: io.reactivex.internal.operators.completable.CompletableError */
public final class CompletableError extends Completable {
    final Throwable error;

    public CompletableError(Throwable error2) {
        this.error = error2;
    }

    /* access modifiers changed from: protected */
    public void subscribeActual(CompletableObserver s) {
        EmptyDisposable.error(this.error, s);
    }
}
