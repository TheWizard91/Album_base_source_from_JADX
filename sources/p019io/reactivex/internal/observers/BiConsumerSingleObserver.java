package p019io.reactivex.internal.observers;

import java.util.concurrent.atomic.AtomicReference;
import p019io.reactivex.SingleObserver;
import p019io.reactivex.disposables.Disposable;
import p019io.reactivex.exceptions.CompositeException;
import p019io.reactivex.exceptions.Exceptions;
import p019io.reactivex.functions.BiConsumer;
import p019io.reactivex.internal.disposables.DisposableHelper;
import p019io.reactivex.plugins.RxJavaPlugins;

/* renamed from: io.reactivex.internal.observers.BiConsumerSingleObserver */
public final class BiConsumerSingleObserver<T> extends AtomicReference<Disposable> implements SingleObserver<T>, Disposable {
    private static final long serialVersionUID = 4943102778943297569L;
    final BiConsumer<? super T, ? super Throwable> onCallback;

    public BiConsumerSingleObserver(BiConsumer<? super T, ? super Throwable> onCallback2) {
        this.onCallback = onCallback2;
    }

    public void onError(Throwable e) {
        try {
            lazySet(DisposableHelper.DISPOSED);
            this.onCallback.accept(null, e);
        } catch (Throwable ex) {
            Exceptions.throwIfFatal(ex);
            RxJavaPlugins.onError(new CompositeException(e, ex));
        }
    }

    public void onSubscribe(Disposable d) {
        DisposableHelper.setOnce(this, d);
    }

    public void onSuccess(T value) {
        try {
            lazySet(DisposableHelper.DISPOSED);
            this.onCallback.accept(value, null);
        } catch (Throwable ex) {
            Exceptions.throwIfFatal(ex);
            RxJavaPlugins.onError(ex);
        }
    }

    public void dispose() {
        DisposableHelper.dispose(this);
    }

    public boolean isDisposed() {
        return get() == DisposableHelper.DISPOSED;
    }
}
