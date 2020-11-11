package p019io.reactivex.observers;

import p019io.reactivex.Observer;
import p019io.reactivex.disposables.Disposable;
import p019io.reactivex.exceptions.CompositeException;
import p019io.reactivex.exceptions.Exceptions;
import p019io.reactivex.internal.disposables.DisposableHelper;
import p019io.reactivex.internal.disposables.EmptyDisposable;
import p019io.reactivex.plugins.RxJavaPlugins;

/* renamed from: io.reactivex.observers.SafeObserver */
public final class SafeObserver<T> implements Observer<T>, Disposable {
    final Observer<? super T> actual;
    boolean done;

    /* renamed from: s */
    Disposable f601s;

    public SafeObserver(Observer<? super T> actual2) {
        this.actual = actual2;
    }

    public void onSubscribe(Disposable s) {
        if (DisposableHelper.validate(this.f601s, s)) {
            this.f601s = s;
            try {
                this.actual.onSubscribe(this);
            } catch (Throwable e1) {
                Exceptions.throwIfFatal(e1);
                RxJavaPlugins.onError(new CompositeException(e, e1));
            }
        }
    }

    public void dispose() {
        this.f601s.dispose();
    }

    public boolean isDisposed() {
        return this.f601s.isDisposed();
    }

    public void onNext(T t) {
        if (!this.done) {
            if (this.f601s == null) {
                onNextNoSubscription();
            } else if (t == null) {
                Throwable ex = new NullPointerException("onNext called with null. Null values are generally not allowed in 2.x operators and sources.");
                try {
                    this.f601s.dispose();
                    onError(ex);
                } catch (Throwable e1) {
                    Exceptions.throwIfFatal(e1);
                    onError(new CompositeException(ex, e1));
                }
            } else {
                try {
                    this.actual.onNext(t);
                } catch (Throwable e12) {
                    Exceptions.throwIfFatal(e12);
                    onError(new CompositeException(e, e12));
                }
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void onNextNoSubscription() {
        this.done = true;
        Throwable ex = new NullPointerException("Subscription not set!");
        try {
            this.actual.onSubscribe(EmptyDisposable.INSTANCE);
            try {
                this.actual.onError(ex);
            } catch (Throwable e) {
                Exceptions.throwIfFatal(e);
                RxJavaPlugins.onError(new CompositeException(ex, e));
            }
        } catch (Throwable e2) {
            Exceptions.throwIfFatal(e2);
            RxJavaPlugins.onError(new CompositeException(ex, e2));
        }
    }

    public void onError(Throwable t) {
        if (this.done) {
            RxJavaPlugins.onError(t);
            return;
        }
        this.done = true;
        if (this.f601s == null) {
            Throwable npe = new NullPointerException("Subscription not set!");
            try {
                this.actual.onSubscribe(EmptyDisposable.INSTANCE);
                try {
                    this.actual.onError(new CompositeException(t, npe));
                } catch (Throwable e) {
                    Exceptions.throwIfFatal(e);
                    RxJavaPlugins.onError(new CompositeException(t, npe, e));
                }
            } catch (Throwable e2) {
                Exceptions.throwIfFatal(e2);
                RxJavaPlugins.onError(new CompositeException(t, npe, e2));
            }
        } else {
            if (t == null) {
                t = new NullPointerException("onError called with null. Null values are generally not allowed in 2.x operators and sources.");
            }
            try {
                this.actual.onError(t);
            } catch (Throwable ex) {
                Exceptions.throwIfFatal(ex);
                RxJavaPlugins.onError(new CompositeException(t, ex));
            }
        }
    }

    public void onComplete() {
        if (!this.done) {
            this.done = true;
            if (this.f601s == null) {
                onCompleteNoSubscription();
                return;
            }
            try {
                this.actual.onComplete();
            } catch (Throwable e) {
                Exceptions.throwIfFatal(e);
                RxJavaPlugins.onError(e);
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void onCompleteNoSubscription() {
        Throwable ex = new NullPointerException("Subscription not set!");
        try {
            this.actual.onSubscribe(EmptyDisposable.INSTANCE);
            try {
                this.actual.onError(ex);
            } catch (Throwable e) {
                Exceptions.throwIfFatal(e);
                RxJavaPlugins.onError(new CompositeException(ex, e));
            }
        } catch (Throwable e2) {
            Exceptions.throwIfFatal(e2);
            RxJavaPlugins.onError(new CompositeException(ex, e2));
        }
    }
}
