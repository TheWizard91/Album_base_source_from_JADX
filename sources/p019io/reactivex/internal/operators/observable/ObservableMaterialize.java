package p019io.reactivex.internal.operators.observable;

import p019io.reactivex.Notification;
import p019io.reactivex.ObservableSource;
import p019io.reactivex.Observer;
import p019io.reactivex.disposables.Disposable;
import p019io.reactivex.internal.disposables.DisposableHelper;

/* renamed from: io.reactivex.internal.operators.observable.ObservableMaterialize */
public final class ObservableMaterialize<T> extends AbstractObservableWithUpstream<T, Notification<T>> {
    public ObservableMaterialize(ObservableSource<T> source) {
        super(source);
    }

    public void subscribeActual(Observer<? super Notification<T>> t) {
        this.source.subscribe(new MaterializeObserver(t));
    }

    /* renamed from: io.reactivex.internal.operators.observable.ObservableMaterialize$MaterializeObserver */
    static final class MaterializeObserver<T> implements Observer<T>, Disposable {
        final Observer<? super Notification<T>> actual;

        /* renamed from: s */
        Disposable f459s;

        MaterializeObserver(Observer<? super Notification<T>> actual2) {
            this.actual = actual2;
        }

        public void onSubscribe(Disposable s) {
            if (DisposableHelper.validate(this.f459s, s)) {
                this.f459s = s;
                this.actual.onSubscribe(this);
            }
        }

        public void dispose() {
            this.f459s.dispose();
        }

        public boolean isDisposed() {
            return this.f459s.isDisposed();
        }

        public void onNext(T t) {
            this.actual.onNext(Notification.createOnNext(t));
        }

        public void onError(Throwable t) {
            this.actual.onNext(Notification.createOnError(t));
            this.actual.onComplete();
        }

        public void onComplete() {
            this.actual.onNext(Notification.createOnComplete());
            this.actual.onComplete();
        }
    }
}
