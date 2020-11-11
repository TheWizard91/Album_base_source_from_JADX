package p019io.reactivex.internal.operators.observable;

import p019io.reactivex.ObservableSource;
import p019io.reactivex.Observer;
import p019io.reactivex.disposables.Disposable;
import p019io.reactivex.internal.disposables.SequentialDisposable;

/* renamed from: io.reactivex.internal.operators.observable.ObservableSwitchIfEmpty */
public final class ObservableSwitchIfEmpty<T> extends AbstractObservableWithUpstream<T, T> {
    final ObservableSource<? extends T> other;

    public ObservableSwitchIfEmpty(ObservableSource<T> source, ObservableSource<? extends T> other2) {
        super(source);
        this.other = other2;
    }

    public void subscribeActual(Observer<? super T> t) {
        SwitchIfEmptyObserver<T> parent = new SwitchIfEmptyObserver<>(t, this.other);
        t.onSubscribe(parent.arbiter);
        this.source.subscribe(parent);
    }

    /* renamed from: io.reactivex.internal.operators.observable.ObservableSwitchIfEmpty$SwitchIfEmptyObserver */
    static final class SwitchIfEmptyObserver<T> implements Observer<T> {
        final Observer<? super T> actual;
        final SequentialDisposable arbiter = new SequentialDisposable();
        boolean empty = true;
        final ObservableSource<? extends T> other;

        SwitchIfEmptyObserver(Observer<? super T> actual2, ObservableSource<? extends T> other2) {
            this.actual = actual2;
            this.other = other2;
        }

        public void onSubscribe(Disposable s) {
            this.arbiter.update(s);
        }

        public void onNext(T t) {
            if (this.empty) {
                this.empty = false;
            }
            this.actual.onNext(t);
        }

        public void onError(Throwable t) {
            this.actual.onError(t);
        }

        public void onComplete() {
            if (this.empty) {
                this.empty = false;
                this.other.subscribe(this);
                return;
            }
            this.actual.onComplete();
        }
    }
}
