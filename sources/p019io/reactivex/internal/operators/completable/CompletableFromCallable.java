package p019io.reactivex.internal.operators.completable;

import java.util.concurrent.Callable;
import p019io.reactivex.Completable;
import p019io.reactivex.CompletableObserver;
import p019io.reactivex.disposables.Disposable;
import p019io.reactivex.disposables.Disposables;
import p019io.reactivex.exceptions.Exceptions;

/* renamed from: io.reactivex.internal.operators.completable.CompletableFromCallable */
public final class CompletableFromCallable extends Completable {
    final Callable<?> callable;

    public CompletableFromCallable(Callable<?> callable2) {
        this.callable = callable2;
    }

    /* access modifiers changed from: protected */
    public void subscribeActual(CompletableObserver s) {
        Disposable d = Disposables.empty();
        s.onSubscribe(d);
        try {
            this.callable.call();
            if (!d.isDisposed()) {
                s.onComplete();
            }
        } catch (Throwable e) {
            Exceptions.throwIfFatal(e);
            if (!d.isDisposed()) {
                s.onError(e);
            }
        }
    }
}
