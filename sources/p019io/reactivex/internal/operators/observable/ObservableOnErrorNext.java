package p019io.reactivex.internal.operators.observable;

import p019io.reactivex.ObservableSource;
import p019io.reactivex.Observer;
import p019io.reactivex.disposables.Disposable;
import p019io.reactivex.exceptions.CompositeException;
import p019io.reactivex.exceptions.Exceptions;
import p019io.reactivex.functions.Function;
import p019io.reactivex.internal.disposables.SequentialDisposable;
import p019io.reactivex.plugins.RxJavaPlugins;

/* renamed from: io.reactivex.internal.operators.observable.ObservableOnErrorNext */
public final class ObservableOnErrorNext<T> extends AbstractObservableWithUpstream<T, T> {
    final boolean allowFatal;
    final Function<? super Throwable, ? extends ObservableSource<? extends T>> nextSupplier;

    public ObservableOnErrorNext(ObservableSource<T> source, Function<? super Throwable, ? extends ObservableSource<? extends T>> nextSupplier2, boolean allowFatal2) {
        super(source);
        this.nextSupplier = nextSupplier2;
        this.allowFatal = allowFatal2;
    }

    public void subscribeActual(Observer<? super T> t) {
        OnErrorNextObserver<T> parent = new OnErrorNextObserver<>(t, this.nextSupplier, this.allowFatal);
        t.onSubscribe(parent.arbiter);
        this.source.subscribe(parent);
    }

    /* renamed from: io.reactivex.internal.operators.observable.ObservableOnErrorNext$OnErrorNextObserver */
    static final class OnErrorNextObserver<T> implements Observer<T> {
        final Observer<? super T> actual;
        final boolean allowFatal;
        final SequentialDisposable arbiter = new SequentialDisposable();
        boolean done;
        final Function<? super Throwable, ? extends ObservableSource<? extends T>> nextSupplier;
        boolean once;

        OnErrorNextObserver(Observer<? super T> actual2, Function<? super Throwable, ? extends ObservableSource<? extends T>> nextSupplier2, boolean allowFatal2) {
            this.actual = actual2;
            this.nextSupplier = nextSupplier2;
            this.allowFatal = allowFatal2;
        }

        public void onSubscribe(Disposable s) {
            this.arbiter.replace(s);
        }

        public void onNext(T t) {
            if (!this.done) {
                this.actual.onNext(t);
            }
        }

        public void onError(Throwable t) {
            if (!this.once) {
                this.once = true;
                if (!this.allowFatal || (t instanceof Exception)) {
                    try {
                        ObservableSource<? extends T> p = (ObservableSource) this.nextSupplier.apply(t);
                        if (p == null) {
                            NullPointerException npe = new NullPointerException("Observable is null");
                            npe.initCause(t);
                            this.actual.onError(npe);
                            return;
                        }
                        p.subscribe(this);
                    } catch (Throwable e) {
                        Exceptions.throwIfFatal(e);
                        this.actual.onError(new CompositeException(t, e));
                    }
                } else {
                    this.actual.onError(t);
                }
            } else if (this.done) {
                RxJavaPlugins.onError(t);
            } else {
                this.actual.onError(t);
            }
        }

        public void onComplete() {
            if (!this.done) {
                this.done = true;
                this.once = true;
                this.actual.onComplete();
            }
        }
    }
}
