package p019io.reactivex.internal.operators.single;

import java.util.concurrent.Callable;
import p019io.reactivex.Single;
import p019io.reactivex.SingleObserver;
import p019io.reactivex.disposables.Disposable;
import p019io.reactivex.disposables.Disposables;
import p019io.reactivex.exceptions.Exceptions;
import p019io.reactivex.internal.functions.ObjectHelper;
import p019io.reactivex.plugins.RxJavaPlugins;

/* renamed from: io.reactivex.internal.operators.single.SingleFromCallable */
public final class SingleFromCallable<T> extends Single<T> {
    final Callable<? extends T> callable;

    public SingleFromCallable(Callable<? extends T> callable2) {
        this.callable = callable2;
    }

    /* access modifiers changed from: protected */
    public void subscribeActual(SingleObserver<? super T> observer) {
        Disposable d = Disposables.empty();
        observer.onSubscribe(d);
        if (!d.isDisposed()) {
            try {
                T value = ObjectHelper.requireNonNull(this.callable.call(), "The callable returned a null value");
                if (!d.isDisposed()) {
                    observer.onSuccess(value);
                }
            } catch (Throwable ex) {
                Exceptions.throwIfFatal(ex);
                if (!d.isDisposed()) {
                    observer.onError(ex);
                } else {
                    RxJavaPlugins.onError(ex);
                }
            }
        }
    }
}
