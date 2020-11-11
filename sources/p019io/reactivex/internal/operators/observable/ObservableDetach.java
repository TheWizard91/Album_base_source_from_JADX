package p019io.reactivex.internal.operators.observable;

import p019io.reactivex.ObservableSource;
import p019io.reactivex.Observer;
import p019io.reactivex.disposables.Disposable;
import p019io.reactivex.internal.disposables.DisposableHelper;
import p019io.reactivex.internal.util.EmptyComponent;

/* renamed from: io.reactivex.internal.operators.observable.ObservableDetach */
public final class ObservableDetach<T> extends AbstractObservableWithUpstream<T, T> {
    public ObservableDetach(ObservableSource<T> source) {
        super(source);
    }

    /* access modifiers changed from: protected */
    public void subscribeActual(Observer<? super T> s) {
        this.source.subscribe(new DetachObserver(s));
    }

    /* renamed from: io.reactivex.internal.operators.observable.ObservableDetach$DetachObserver */
    static final class DetachObserver<T> implements Observer<T>, Disposable {
        Observer<? super T> actual;

        /* renamed from: s */
        Disposable f435s;

        DetachObserver(Observer<? super T> actual2) {
            this.actual = actual2;
        }

        public void dispose() {
            Disposable s = this.f435s;
            this.f435s = EmptyComponent.INSTANCE;
            this.actual = EmptyComponent.asObserver();
            s.dispose();
        }

        public boolean isDisposed() {
            return this.f435s.isDisposed();
        }

        public void onSubscribe(Disposable s) {
            if (DisposableHelper.validate(this.f435s, s)) {
                this.f435s = s;
                this.actual.onSubscribe(this);
            }
        }

        public void onNext(T t) {
            this.actual.onNext(t);
        }

        public void onError(Throwable t) {
            Observer<? super T> a = this.actual;
            this.f435s = EmptyComponent.INSTANCE;
            this.actual = EmptyComponent.asObserver();
            a.onError(t);
        }

        public void onComplete() {
            Observer<? super T> a = this.actual;
            this.f435s = EmptyComponent.INSTANCE;
            this.actual = EmptyComponent.asObserver();
            a.onComplete();
        }
    }
}
