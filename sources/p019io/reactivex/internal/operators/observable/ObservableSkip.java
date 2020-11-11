package p019io.reactivex.internal.operators.observable;

import p019io.reactivex.ObservableSource;
import p019io.reactivex.Observer;
import p019io.reactivex.disposables.Disposable;
import p019io.reactivex.internal.disposables.DisposableHelper;

/* renamed from: io.reactivex.internal.operators.observable.ObservableSkip */
public final class ObservableSkip<T> extends AbstractObservableWithUpstream<T, T> {

    /* renamed from: n */
    final long f484n;

    public ObservableSkip(ObservableSource<T> source, long n) {
        super(source);
        this.f484n = n;
    }

    public void subscribeActual(Observer<? super T> s) {
        this.source.subscribe(new SkipObserver(s, this.f484n));
    }

    /* renamed from: io.reactivex.internal.operators.observable.ObservableSkip$SkipObserver */
    static final class SkipObserver<T> implements Observer<T>, Disposable {
        final Observer<? super T> actual;

        /* renamed from: d */
        Disposable f485d;
        long remaining;

        SkipObserver(Observer<? super T> actual2, long n) {
            this.actual = actual2;
            this.remaining = n;
        }

        public void onSubscribe(Disposable d) {
            if (DisposableHelper.validate(this.f485d, d)) {
                this.f485d = d;
                this.actual.onSubscribe(this);
            }
        }

        public void onNext(T t) {
            long j = this.remaining;
            if (j != 0) {
                this.remaining = j - 1;
            } else {
                this.actual.onNext(t);
            }
        }

        public void onError(Throwable t) {
            this.actual.onError(t);
        }

        public void onComplete() {
            this.actual.onComplete();
        }

        public void dispose() {
            this.f485d.dispose();
        }

        public boolean isDisposed() {
            return this.f485d.isDisposed();
        }
    }
}
