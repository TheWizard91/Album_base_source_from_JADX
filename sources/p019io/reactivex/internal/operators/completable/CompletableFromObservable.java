package p019io.reactivex.internal.operators.completable;

import p019io.reactivex.Completable;
import p019io.reactivex.CompletableObserver;
import p019io.reactivex.ObservableSource;
import p019io.reactivex.Observer;
import p019io.reactivex.disposables.Disposable;

/* renamed from: io.reactivex.internal.operators.completable.CompletableFromObservable */
public final class CompletableFromObservable<T> extends Completable {
    final ObservableSource<T> observable;

    public CompletableFromObservable(ObservableSource<T> observable2) {
        this.observable = observable2;
    }

    /* access modifiers changed from: protected */
    public void subscribeActual(CompletableObserver s) {
        this.observable.subscribe(new CompletableFromObservableObserver(s));
    }

    /* renamed from: io.reactivex.internal.operators.completable.CompletableFromObservable$CompletableFromObservableObserver */
    static final class CompletableFromObservableObserver<T> implements Observer<T> {

        /* renamed from: co */
        final CompletableObserver f239co;

        CompletableFromObservableObserver(CompletableObserver co) {
            this.f239co = co;
        }

        public void onSubscribe(Disposable d) {
            this.f239co.onSubscribe(d);
        }

        public void onNext(T t) {
        }

        public void onError(Throwable e) {
            this.f239co.onError(e);
        }

        public void onComplete() {
            this.f239co.onComplete();
        }
    }
}
