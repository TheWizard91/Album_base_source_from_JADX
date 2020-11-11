package p019io.reactivex.internal.operators.observable;

import p019io.reactivex.Maybe;
import p019io.reactivex.MaybeObserver;
import p019io.reactivex.ObservableSource;
import p019io.reactivex.Observer;
import p019io.reactivex.disposables.Disposable;
import p019io.reactivex.exceptions.Exceptions;
import p019io.reactivex.functions.BiFunction;
import p019io.reactivex.internal.disposables.DisposableHelper;
import p019io.reactivex.internal.functions.ObjectHelper;
import p019io.reactivex.plugins.RxJavaPlugins;

/* renamed from: io.reactivex.internal.operators.observable.ObservableReduceMaybe */
public final class ObservableReduceMaybe<T> extends Maybe<T> {
    final BiFunction<T, T, T> reducer;
    final ObservableSource<T> source;

    public ObservableReduceMaybe(ObservableSource<T> source2, BiFunction<T, T, T> reducer2) {
        this.source = source2;
        this.reducer = reducer2;
    }

    /* access modifiers changed from: protected */
    public void subscribeActual(MaybeObserver<? super T> observer) {
        this.source.subscribe(new ReduceObserver(observer, this.reducer));
    }

    /* renamed from: io.reactivex.internal.operators.observable.ObservableReduceMaybe$ReduceObserver */
    static final class ReduceObserver<T> implements Observer<T>, Disposable {
        final MaybeObserver<? super T> actual;

        /* renamed from: d */
        Disposable f464d;
        boolean done;
        final BiFunction<T, T, T> reducer;
        T value;

        ReduceObserver(MaybeObserver<? super T> observer, BiFunction<T, T, T> reducer2) {
            this.actual = observer;
            this.reducer = reducer2;
        }

        public void onSubscribe(Disposable d) {
            if (DisposableHelper.validate(this.f464d, d)) {
                this.f464d = d;
                this.actual.onSubscribe(this);
            }
        }

        public void onNext(T value2) {
            if (!this.done) {
                T v = this.value;
                if (v == null) {
                    this.value = value2;
                    return;
                }
                try {
                    this.value = ObjectHelper.requireNonNull(this.reducer.apply(v, value2), "The reducer returned a null value");
                } catch (Throwable ex) {
                    Exceptions.throwIfFatal(ex);
                    this.f464d.dispose();
                    onError(ex);
                }
            }
        }

        public void onError(Throwable e) {
            if (this.done) {
                RxJavaPlugins.onError(e);
                return;
            }
            this.done = true;
            this.value = null;
            this.actual.onError(e);
        }

        public void onComplete() {
            if (!this.done) {
                this.done = true;
                T v = this.value;
                this.value = null;
                if (v != null) {
                    this.actual.onSuccess(v);
                } else {
                    this.actual.onComplete();
                }
            }
        }

        public void dispose() {
            this.f464d.dispose();
        }

        public boolean isDisposed() {
            return this.f464d.isDisposed();
        }
    }
}
