package p019io.reactivex.internal.operators.observable;

import p019io.reactivex.ObservableSource;
import p019io.reactivex.Observer;
import p019io.reactivex.disposables.Disposable;
import p019io.reactivex.exceptions.Exceptions;
import p019io.reactivex.functions.BiFunction;
import p019io.reactivex.internal.disposables.DisposableHelper;
import p019io.reactivex.internal.functions.ObjectHelper;
import p019io.reactivex.plugins.RxJavaPlugins;

/* renamed from: io.reactivex.internal.operators.observable.ObservableScan */
public final class ObservableScan<T> extends AbstractObservableWithUpstream<T, T> {
    final BiFunction<T, T, T> accumulator;

    public ObservableScan(ObservableSource<T> source, BiFunction<T, T, T> accumulator2) {
        super(source);
        this.accumulator = accumulator2;
    }

    public void subscribeActual(Observer<? super T> t) {
        this.source.subscribe(new ScanObserver(t, this.accumulator));
    }

    /* renamed from: io.reactivex.internal.operators.observable.ObservableScan$ScanObserver */
    static final class ScanObserver<T> implements Observer<T>, Disposable {
        final BiFunction<T, T, T> accumulator;
        final Observer<? super T> actual;
        boolean done;

        /* renamed from: s */
        Disposable f476s;
        T value;

        ScanObserver(Observer<? super T> actual2, BiFunction<T, T, T> accumulator2) {
            this.actual = actual2;
            this.accumulator = accumulator2;
        }

        public void onSubscribe(Disposable s) {
            if (DisposableHelper.validate(this.f476s, s)) {
                this.f476s = s;
                this.actual.onSubscribe(this);
            }
        }

        public void dispose() {
            this.f476s.dispose();
        }

        public boolean isDisposed() {
            return this.f476s.isDisposed();
        }

        public void onNext(T t) {
            if (!this.done) {
                Observer<? super T> a = this.actual;
                T v = this.value;
                if (v == null) {
                    this.value = t;
                    a.onNext(t);
                    return;
                }
                try {
                    T u = ObjectHelper.requireNonNull(this.accumulator.apply(v, t), "The value returned by the accumulator is null");
                    this.value = u;
                    a.onNext(u);
                } catch (Throwable e) {
                    Exceptions.throwIfFatal(e);
                    this.f476s.dispose();
                    onError(e);
                }
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
                this.actual.onComplete();
            }
        }
    }
}
