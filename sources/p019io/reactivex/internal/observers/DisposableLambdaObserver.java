package p019io.reactivex.internal.observers;

import p019io.reactivex.Observer;
import p019io.reactivex.disposables.Disposable;
import p019io.reactivex.exceptions.Exceptions;
import p019io.reactivex.functions.Action;
import p019io.reactivex.functions.Consumer;
import p019io.reactivex.internal.disposables.DisposableHelper;
import p019io.reactivex.internal.disposables.EmptyDisposable;
import p019io.reactivex.plugins.RxJavaPlugins;

/* renamed from: io.reactivex.internal.observers.DisposableLambdaObserver */
public final class DisposableLambdaObserver<T> implements Observer<T>, Disposable {
    final Observer<? super T> actual;
    final Action onDispose;
    final Consumer<? super Disposable> onSubscribe;

    /* renamed from: s */
    Disposable f216s;

    public DisposableLambdaObserver(Observer<? super T> actual2, Consumer<? super Disposable> onSubscribe2, Action onDispose2) {
        this.actual = actual2;
        this.onSubscribe = onSubscribe2;
        this.onDispose = onDispose2;
    }

    public void onSubscribe(Disposable s) {
        try {
            this.onSubscribe.accept(s);
            if (DisposableHelper.validate(this.f216s, s)) {
                this.f216s = s;
                this.actual.onSubscribe(this);
            }
        } catch (Throwable e) {
            Exceptions.throwIfFatal(e);
            s.dispose();
            this.f216s = DisposableHelper.DISPOSED;
            EmptyDisposable.error(e, (Observer<?>) this.actual);
        }
    }

    public void onNext(T t) {
        this.actual.onNext(t);
    }

    public void onError(Throwable t) {
        if (this.f216s != DisposableHelper.DISPOSED) {
            this.actual.onError(t);
        } else {
            RxJavaPlugins.onError(t);
        }
    }

    public void onComplete() {
        if (this.f216s != DisposableHelper.DISPOSED) {
            this.actual.onComplete();
        }
    }

    public void dispose() {
        try {
            this.onDispose.run();
        } catch (Throwable e) {
            Exceptions.throwIfFatal(e);
            RxJavaPlugins.onError(e);
        }
        this.f216s.dispose();
    }

    public boolean isDisposed() {
        return this.f216s.isDisposed();
    }
}
