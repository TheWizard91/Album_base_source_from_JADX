package p019io.reactivex.internal.operators.observable;

import java.util.concurrent.TimeUnit;
import p019io.reactivex.ObservableSource;
import p019io.reactivex.Observer;
import p019io.reactivex.Scheduler;
import p019io.reactivex.disposables.Disposable;
import p019io.reactivex.internal.disposables.DisposableHelper;
import p019io.reactivex.observers.SerializedObserver;

/* renamed from: io.reactivex.internal.operators.observable.ObservableDelay */
public final class ObservableDelay<T> extends AbstractObservableWithUpstream<T, T> {
    final long delay;
    final boolean delayError;
    final Scheduler scheduler;
    final TimeUnit unit;

    public ObservableDelay(ObservableSource<T> source, long delay2, TimeUnit unit2, Scheduler scheduler2, boolean delayError2) {
        super(source);
        this.delay = delay2;
        this.unit = unit2;
        this.scheduler = scheduler2;
        this.delayError = delayError2;
    }

    public void subscribeActual(Observer<? super T> t) {
        Observer<? super T> observer;
        if (this.delayError) {
            observer = t;
        } else {
            observer = new SerializedObserver<>(t);
        }
        this.source.subscribe(new DelayObserver(observer, this.delay, this.unit, this.scheduler.createWorker(), this.delayError));
    }

    /* renamed from: io.reactivex.internal.operators.observable.ObservableDelay$DelayObserver */
    static final class DelayObserver<T> implements Observer<T>, Disposable {
        final Observer<? super T> actual;
        final long delay;
        final boolean delayError;

        /* renamed from: s */
        Disposable f431s;
        final TimeUnit unit;

        /* renamed from: w */
        final Scheduler.Worker f432w;

        DelayObserver(Observer<? super T> actual2, long delay2, TimeUnit unit2, Scheduler.Worker w, boolean delayError2) {
            this.actual = actual2;
            this.delay = delay2;
            this.unit = unit2;
            this.f432w = w;
            this.delayError = delayError2;
        }

        public void onSubscribe(Disposable s) {
            if (DisposableHelper.validate(this.f431s, s)) {
                this.f431s = s;
                this.actual.onSubscribe(this);
            }
        }

        public void onNext(T t) {
            this.f432w.schedule(new OnNext(t), this.delay, this.unit);
        }

        public void onError(Throwable t) {
            this.f432w.schedule(new OnError(t), this.delayError ? this.delay : 0, this.unit);
        }

        public void onComplete() {
            this.f432w.schedule(new OnComplete(), this.delay, this.unit);
        }

        public void dispose() {
            this.f431s.dispose();
            this.f432w.dispose();
        }

        public boolean isDisposed() {
            return this.f432w.isDisposed();
        }

        /* renamed from: io.reactivex.internal.operators.observable.ObservableDelay$DelayObserver$OnNext */
        final class OnNext implements Runnable {

            /* renamed from: t */
            private final T f433t;

            OnNext(T t) {
                this.f433t = t;
            }

            public void run() {
                DelayObserver.this.actual.onNext(this.f433t);
            }
        }

        /* renamed from: io.reactivex.internal.operators.observable.ObservableDelay$DelayObserver$OnError */
        final class OnError implements Runnable {
            private final Throwable throwable;

            OnError(Throwable throwable2) {
                this.throwable = throwable2;
            }

            public void run() {
                try {
                    DelayObserver.this.actual.onError(this.throwable);
                } finally {
                    DelayObserver.this.f432w.dispose();
                }
            }
        }

        /* renamed from: io.reactivex.internal.operators.observable.ObservableDelay$DelayObserver$OnComplete */
        final class OnComplete implements Runnable {
            OnComplete() {
            }

            public void run() {
                try {
                    DelayObserver.this.actual.onComplete();
                } finally {
                    DelayObserver.this.f432w.dispose();
                }
            }
        }
    }
}
