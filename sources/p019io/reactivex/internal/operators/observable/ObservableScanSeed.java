package p019io.reactivex.internal.operators.observable;

import java.util.concurrent.Callable;
import p019io.reactivex.ObservableSource;
import p019io.reactivex.Observer;
import p019io.reactivex.disposables.Disposable;
import p019io.reactivex.exceptions.Exceptions;
import p019io.reactivex.functions.BiFunction;
import p019io.reactivex.internal.disposables.DisposableHelper;
import p019io.reactivex.internal.disposables.EmptyDisposable;
import p019io.reactivex.internal.functions.ObjectHelper;
import p019io.reactivex.plugins.RxJavaPlugins;

/* renamed from: io.reactivex.internal.operators.observable.ObservableScanSeed */
public final class ObservableScanSeed<T, R> extends AbstractObservableWithUpstream<T, R> {
    final BiFunction<R, ? super T, R> accumulator;
    final Callable<R> seedSupplier;

    public ObservableScanSeed(ObservableSource<T> source, Callable<R> seedSupplier2, BiFunction<R, ? super T, R> accumulator2) {
        super(source);
        this.accumulator = accumulator2;
        this.seedSupplier = seedSupplier2;
    }

    public void subscribeActual(Observer<? super R> t) {
        try {
            this.source.subscribe(new ScanSeedObserver(t, this.accumulator, ObjectHelper.requireNonNull(this.seedSupplier.call(), "The seed supplied is null")));
        } catch (Throwable e) {
            Exceptions.throwIfFatal(e);
            EmptyDisposable.error(e, (Observer<?>) t);
        }
    }

    /* renamed from: io.reactivex.internal.operators.observable.ObservableScanSeed$ScanSeedObserver */
    static final class ScanSeedObserver<T, R> implements Observer<T>, Disposable {
        final BiFunction<R, ? super T, R> accumulator;
        final Observer<? super R> actual;
        boolean done;

        /* renamed from: s */
        Disposable f477s;
        R value;

        ScanSeedObserver(Observer<? super R> actual2, BiFunction<R, ? super T, R> accumulator2, R value2) {
            this.actual = actual2;
            this.accumulator = accumulator2;
            this.value = value2;
        }

        public void onSubscribe(Disposable s) {
            if (DisposableHelper.validate(this.f477s, s)) {
                this.f477s = s;
                this.actual.onSubscribe(this);
                this.actual.onNext(this.value);
            }
        }

        public void dispose() {
            this.f477s.dispose();
        }

        public boolean isDisposed() {
            return this.f477s.isDisposed();
        }

        public void onNext(T t) {
            if (!this.done) {
                try {
                    R u = ObjectHelper.requireNonNull(this.accumulator.apply(this.value, t), "The accumulator returned a null value");
                    this.value = u;
                    this.actual.onNext(u);
                } catch (Throwable e) {
                    Exceptions.throwIfFatal(e);
                    this.f477s.dispose();
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
