package p019io.reactivex.internal.operators.observable;

import p019io.reactivex.ObservableSource;
import p019io.reactivex.Observer;
import p019io.reactivex.Single;
import p019io.reactivex.SingleObserver;
import p019io.reactivex.disposables.Disposable;
import p019io.reactivex.exceptions.Exceptions;
import p019io.reactivex.functions.BiFunction;
import p019io.reactivex.internal.disposables.DisposableHelper;
import p019io.reactivex.internal.functions.ObjectHelper;
import p019io.reactivex.plugins.RxJavaPlugins;

/* renamed from: io.reactivex.internal.operators.observable.ObservableReduceSeedSingle */
public final class ObservableReduceSeedSingle<T, R> extends Single<R> {
    final BiFunction<R, ? super T, R> reducer;
    final R seed;
    final ObservableSource<T> source;

    public ObservableReduceSeedSingle(ObservableSource<T> source2, R seed2, BiFunction<R, ? super T, R> reducer2) {
        this.source = source2;
        this.seed = seed2;
        this.reducer = reducer2;
    }

    /* access modifiers changed from: protected */
    public void subscribeActual(SingleObserver<? super R> observer) {
        this.source.subscribe(new ReduceSeedObserver(observer, this.reducer, this.seed));
    }

    /* renamed from: io.reactivex.internal.operators.observable.ObservableReduceSeedSingle$ReduceSeedObserver */
    static final class ReduceSeedObserver<T, R> implements Observer<T>, Disposable {
        final SingleObserver<? super R> actual;

        /* renamed from: d */
        Disposable f465d;
        final BiFunction<R, ? super T, R> reducer;
        R value;

        ReduceSeedObserver(SingleObserver<? super R> actual2, BiFunction<R, ? super T, R> reducer2, R value2) {
            this.actual = actual2;
            this.value = value2;
            this.reducer = reducer2;
        }

        public void onSubscribe(Disposable d) {
            if (DisposableHelper.validate(this.f465d, d)) {
                this.f465d = d;
                this.actual.onSubscribe(this);
            }
        }

        public void onNext(T value2) {
            R v = this.value;
            if (v != null) {
                try {
                    this.value = ObjectHelper.requireNonNull(this.reducer.apply(v, value2), "The reducer returned a null value");
                } catch (Throwable ex) {
                    Exceptions.throwIfFatal(ex);
                    this.f465d.dispose();
                    onError(ex);
                }
            }
        }

        public void onError(Throwable e) {
            if (this.value != null) {
                this.value = null;
                this.actual.onError(e);
                return;
            }
            RxJavaPlugins.onError(e);
        }

        public void onComplete() {
            R v = this.value;
            if (v != null) {
                this.value = null;
                this.actual.onSuccess(v);
            }
        }

        public void dispose() {
            this.f465d.dispose();
        }

        public boolean isDisposed() {
            return this.f465d.isDisposed();
        }
    }
}
