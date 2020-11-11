package p019io.reactivex.internal.operators.observable;

import p019io.reactivex.Maybe;
import p019io.reactivex.MaybeObserver;
import p019io.reactivex.ObservableSource;
import p019io.reactivex.Observer;
import p019io.reactivex.disposables.Disposable;
import p019io.reactivex.internal.disposables.DisposableHelper;
import p019io.reactivex.plugins.RxJavaPlugins;

/* renamed from: io.reactivex.internal.operators.observable.ObservableSingleMaybe */
public final class ObservableSingleMaybe<T> extends Maybe<T> {
    final ObservableSource<T> source;

    public ObservableSingleMaybe(ObservableSource<T> source2) {
        this.source = source2;
    }

    public void subscribeActual(MaybeObserver<? super T> t) {
        this.source.subscribe(new SingleElementObserver(t));
    }

    /* renamed from: io.reactivex.internal.operators.observable.ObservableSingleMaybe$SingleElementObserver */
    static final class SingleElementObserver<T> implements Observer<T>, Disposable {
        final MaybeObserver<? super T> actual;
        boolean done;

        /* renamed from: s */
        Disposable f482s;
        T value;

        SingleElementObserver(MaybeObserver<? super T> actual2) {
            this.actual = actual2;
        }

        public void onSubscribe(Disposable s) {
            if (DisposableHelper.validate(this.f482s, s)) {
                this.f482s = s;
                this.actual.onSubscribe(this);
            }
        }

        public void dispose() {
            this.f482s.dispose();
        }

        public boolean isDisposed() {
            return this.f482s.isDisposed();
        }

        public void onNext(T t) {
            if (!this.done) {
                if (this.value != null) {
                    this.done = true;
                    this.f482s.dispose();
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
                    this.actual.onComplete();
                } else {
                    this.actual.onSuccess(v);
                }
            }
        }
    }
}
