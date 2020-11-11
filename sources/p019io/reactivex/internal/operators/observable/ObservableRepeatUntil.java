package p019io.reactivex.internal.operators.observable;

import java.util.concurrent.atomic.AtomicInteger;
import p019io.reactivex.Observable;
import p019io.reactivex.ObservableSource;
import p019io.reactivex.Observer;
import p019io.reactivex.disposables.Disposable;
import p019io.reactivex.exceptions.Exceptions;
import p019io.reactivex.functions.BooleanSupplier;
import p019io.reactivex.internal.disposables.SequentialDisposable;

/* renamed from: io.reactivex.internal.operators.observable.ObservableRepeatUntil */
public final class ObservableRepeatUntil<T> extends AbstractObservableWithUpstream<T, T> {
    final BooleanSupplier until;

    public ObservableRepeatUntil(Observable<T> source, BooleanSupplier until2) {
        super(source);
        this.until = until2;
    }

    public void subscribeActual(Observer<? super T> s) {
        SequentialDisposable sd = new SequentialDisposable();
        s.onSubscribe(sd);
        new RepeatUntilObserver<>(s, this.until, sd, this.source).subscribeNext();
    }

    /* renamed from: io.reactivex.internal.operators.observable.ObservableRepeatUntil$RepeatUntilObserver */
    static final class RepeatUntilObserver<T> extends AtomicInteger implements Observer<T> {
        private static final long serialVersionUID = -7098360935104053232L;
        final Observer<? super T> actual;

        /* renamed from: sd */
        final SequentialDisposable f468sd;
        final ObservableSource<? extends T> source;
        final BooleanSupplier stop;

        RepeatUntilObserver(Observer<? super T> actual2, BooleanSupplier until, SequentialDisposable sd, ObservableSource<? extends T> source2) {
            this.actual = actual2;
            this.f468sd = sd;
            this.source = source2;
            this.stop = until;
        }

        public void onSubscribe(Disposable s) {
            this.f468sd.replace(s);
        }

        public void onNext(T t) {
            this.actual.onNext(t);
        }

        public void onError(Throwable t) {
            this.actual.onError(t);
        }

        public void onComplete() {
            try {
                if (this.stop.getAsBoolean()) {
                    this.actual.onComplete();
                } else {
                    subscribeNext();
                }
            } catch (Throwable e) {
                Exceptions.throwIfFatal(e);
                this.actual.onError(e);
            }
        }

        /* access modifiers changed from: package-private */
        public void subscribeNext() {
            if (getAndIncrement() == 0) {
                int missed = 1;
                do {
                    this.source.subscribe(this);
                    missed = addAndGet(-missed);
                } while (missed != 0);
            }
        }
    }
}
