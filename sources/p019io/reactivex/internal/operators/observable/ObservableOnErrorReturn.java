package p019io.reactivex.internal.operators.observable;

import p019io.reactivex.ObservableSource;
import p019io.reactivex.Observer;
import p019io.reactivex.disposables.Disposable;
import p019io.reactivex.exceptions.CompositeException;
import p019io.reactivex.exceptions.Exceptions;
import p019io.reactivex.functions.Function;
import p019io.reactivex.internal.disposables.DisposableHelper;

/* renamed from: io.reactivex.internal.operators.observable.ObservableOnErrorReturn */
public final class ObservableOnErrorReturn<T> extends AbstractObservableWithUpstream<T, T> {
    final Function<? super Throwable, ? extends T> valueSupplier;

    public ObservableOnErrorReturn(ObservableSource<T> source, Function<? super Throwable, ? extends T> valueSupplier2) {
        super(source);
        this.valueSupplier = valueSupplier2;
    }

    public void subscribeActual(Observer<? super T> t) {
        this.source.subscribe(new OnErrorReturnObserver(t, this.valueSupplier));
    }

    /* renamed from: io.reactivex.internal.operators.observable.ObservableOnErrorReturn$OnErrorReturnObserver */
    static final class OnErrorReturnObserver<T> implements Observer<T>, Disposable {
        final Observer<? super T> actual;

        /* renamed from: s */
        Disposable f461s;
        final Function<? super Throwable, ? extends T> valueSupplier;

        OnErrorReturnObserver(Observer<? super T> actual2, Function<? super Throwable, ? extends T> valueSupplier2) {
            this.actual = actual2;
            this.valueSupplier = valueSupplier2;
        }

        public void onSubscribe(Disposable s) {
            if (DisposableHelper.validate(this.f461s, s)) {
                this.f461s = s;
                this.actual.onSubscribe(this);
            }
        }

        public void dispose() {
            this.f461s.dispose();
        }

        public boolean isDisposed() {
            return this.f461s.isDisposed();
        }

        public void onNext(T t) {
            this.actual.onNext(t);
        }

        public void onError(Throwable t) {
            try {
                T v = this.valueSupplier.apply(t);
                if (v == null) {
                    NullPointerException e = new NullPointerException("The supplied value is null");
                    e.initCause(t);
                    this.actual.onError(e);
                    return;
                }
                this.actual.onNext(v);
                this.actual.onComplete();
            } catch (Throwable e2) {
                Exceptions.throwIfFatal(e2);
                this.actual.onError(new CompositeException(t, e2));
            }
        }

        public void onComplete() {
            this.actual.onComplete();
        }
    }
}
