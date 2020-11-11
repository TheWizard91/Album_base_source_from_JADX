package p019io.reactivex.internal.operators.observable;

import p019io.reactivex.Completable;
import p019io.reactivex.CompletableObserver;
import p019io.reactivex.Observable;
import p019io.reactivex.ObservableSource;
import p019io.reactivex.Observer;
import p019io.reactivex.disposables.Disposable;
import p019io.reactivex.internal.fuseable.FuseToObservable;
import p019io.reactivex.plugins.RxJavaPlugins;

/* renamed from: io.reactivex.internal.operators.observable.ObservableIgnoreElementsCompletable */
public final class ObservableIgnoreElementsCompletable<T> extends Completable implements FuseToObservable<T> {
    final ObservableSource<T> source;

    public ObservableIgnoreElementsCompletable(ObservableSource<T> source2) {
        this.source = source2;
    }

    public void subscribeActual(CompletableObserver t) {
        this.source.subscribe(new IgnoreObservable(t));
    }

    public Observable<T> fuseToObservable() {
        return RxJavaPlugins.onAssembly(new ObservableIgnoreElements(this.source));
    }

    /* renamed from: io.reactivex.internal.operators.observable.ObservableIgnoreElementsCompletable$IgnoreObservable */
    static final class IgnoreObservable<T> implements Observer<T>, Disposable {
        final CompletableObserver actual;

        /* renamed from: d */
        Disposable f454d;

        IgnoreObservable(CompletableObserver t) {
            this.actual = t;
        }

        public void onSubscribe(Disposable s) {
            this.f454d = s;
            this.actual.onSubscribe(this);
        }

        public void onNext(T t) {
        }

        public void onError(Throwable e) {
            this.actual.onError(e);
        }

        public void onComplete() {
            this.actual.onComplete();
        }

        public void dispose() {
            this.f454d.dispose();
        }

        public boolean isDisposed() {
            return this.f454d.isDisposed();
        }
    }
}
