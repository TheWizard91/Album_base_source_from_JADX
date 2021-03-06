package p019io.reactivex.internal.operators.observable;

import p019io.reactivex.ObservableSource;
import p019io.reactivex.Observer;
import p019io.reactivex.disposables.Disposable;
import p019io.reactivex.internal.disposables.DisposableHelper;
import p019io.reactivex.internal.disposables.EmptyDisposable;
import p019io.reactivex.plugins.RxJavaPlugins;

/* renamed from: io.reactivex.internal.operators.observable.ObservableTake */
public final class ObservableTake<T> extends AbstractObservableWithUpstream<T, T> {
    final long limit;

    public ObservableTake(ObservableSource<T> source, long limit2) {
        super(source);
        this.limit = limit2;
    }

    /* access modifiers changed from: protected */
    public void subscribeActual(Observer<? super T> observer) {
        this.source.subscribe(new TakeObserver(observer, this.limit));
    }

    /* renamed from: io.reactivex.internal.operators.observable.ObservableTake$TakeObserver */
    static final class TakeObserver<T> implements Observer<T>, Disposable {
        final Observer<? super T> actual;
        boolean done;
        long remaining;
        Disposable subscription;

        TakeObserver(Observer<? super T> actual2, long limit) {
            this.actual = actual2;
            this.remaining = limit;
        }

        public void onSubscribe(Disposable s) {
            if (DisposableHelper.validate(this.subscription, s)) {
                this.subscription = s;
                if (this.remaining == 0) {
                    this.done = true;
                    s.dispose();
                    EmptyDisposable.complete((Observer<?>) this.actual);
                    return;
                }
                this.actual.onSubscribe(this);
            }
        }

        public void onNext(T t) {
            if (!this.done) {
                long j = this.remaining;
                long j2 = j - 1;
                this.remaining = j2;
                if (j > 0) {
                    boolean stop = j2 == 0;
                    this.actual.onNext(t);
                    if (stop) {
                        onComplete();
                    }
                }
            }
        }

        public void onError(Throwable t) {
            if (this.done) {
                RxJavaPlugins.onError(t);
                return;
            }
            this.done = true;
            this.subscription.dispose();
            this.actual.onError(t);
        }

        public void onComplete() {
            if (!this.done) {
                this.done = true;
                this.subscription.dispose();
                this.actual.onComplete();
            }
        }

        public void dispose() {
            this.subscription.dispose();
        }

        public boolean isDisposed() {
            return this.subscription.isDisposed();
        }
    }
}
