package p019io.reactivex.internal.operators.observable;

import java.util.NoSuchElementException;
import p019io.reactivex.ObservableSource;
import p019io.reactivex.Observer;
import p019io.reactivex.Single;
import p019io.reactivex.SingleObserver;
import p019io.reactivex.disposables.Disposable;
import p019io.reactivex.internal.disposables.DisposableHelper;
import p019io.reactivex.plugins.RxJavaPlugins;

/* renamed from: io.reactivex.internal.operators.observable.ObservableSingleSingle */
public final class ObservableSingleSingle<T> extends Single<T> {
    final T defaultValue;
    final ObservableSource<? extends T> source;

    public ObservableSingleSingle(ObservableSource<? extends T> source2, T defaultValue2) {
        this.source = source2;
        this.defaultValue = defaultValue2;
    }

    public void subscribeActual(SingleObserver<? super T> t) {
        this.source.subscribe(new SingleElementObserver(t, this.defaultValue));
    }

    /* renamed from: io.reactivex.internal.operators.observable.ObservableSingleSingle$SingleElementObserver */
    static final class SingleElementObserver<T> implements Observer<T>, Disposable {
        final SingleObserver<? super T> actual;
        final T defaultValue;
        boolean done;

        /* renamed from: s */
        Disposable f483s;
        T value;

        SingleElementObserver(SingleObserver<? super T> actual2, T defaultValue2) {
            this.actual = actual2;
            this.defaultValue = defaultValue2;
        }

        public void onSubscribe(Disposable s) {
            if (DisposableHelper.validate(this.f483s, s)) {
                this.f483s = s;
                this.actual.onSubscribe(this);
            }
        }

        public void dispose() {
            this.f483s.dispose();
        }

        public boolean isDisposed() {
            return this.f483s.isDisposed();
        }

        public void onNext(T t) {
            if (!this.done) {
                if (this.value != null) {
                    this.done = true;
                    this.f483s.dispose();
                    this.actual.onError(new IllegalArgumentException("Sequence contains more than one element!"));
                    return;
                }
                this.value = t;
            }
        }

        public void onError(Throwable t) {
            if (this.done) {
                RxJavaPlugins.onError(t);
                return;
            }
            this.done = true;
            this.actual.onError(t);
        }

        public void onComplete() {
            if (!this.done) {
                this.done = true;
                T v = this.value;
                this.value = null;
                if (v == null) {
                    v = this.defaultValue;
                }
                if (v != null) {
                    this.actual.onSuccess(v);
                } else {
                    this.actual.onError(new NoSuchElementException());
                }
            }
        }
    }
}
