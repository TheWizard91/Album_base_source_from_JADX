package p019io.reactivex.internal.operators.observable;

import java.util.NoSuchElementException;
import p019io.reactivex.Observable;
import p019io.reactivex.ObservableSource;
import p019io.reactivex.Observer;
import p019io.reactivex.Single;
import p019io.reactivex.SingleObserver;
import p019io.reactivex.disposables.Disposable;
import p019io.reactivex.internal.disposables.DisposableHelper;
import p019io.reactivex.internal.fuseable.FuseToObservable;
import p019io.reactivex.plugins.RxJavaPlugins;

/* renamed from: io.reactivex.internal.operators.observable.ObservableElementAtSingle */
public final class ObservableElementAtSingle<T> extends Single<T> implements FuseToObservable<T> {
    final T defaultValue;
    final long index;
    final ObservableSource<T> source;

    public ObservableElementAtSingle(ObservableSource<T> source2, long index2, T defaultValue2) {
        this.source = source2;
        this.index = index2;
        this.defaultValue = defaultValue2;
    }

    public void subscribeActual(SingleObserver<? super T> t) {
        this.source.subscribe(new ElementAtObserver(t, this.index, this.defaultValue));
    }

    public Observable<T> fuseToObservable() {
        return RxJavaPlugins.onAssembly(new ObservableElementAt(this.source, this.index, this.defaultValue, true));
    }

    /* renamed from: io.reactivex.internal.operators.observable.ObservableElementAtSingle$ElementAtObserver */
    static final class ElementAtObserver<T> implements Observer<T>, Disposable {
        final SingleObserver<? super T> actual;
        long count;
        final T defaultValue;
        boolean done;
        final long index;

        /* renamed from: s */
        Disposable f441s;

        ElementAtObserver(SingleObserver<? super T> actual2, long index2, T defaultValue2) {
            this.actual = actual2;
            this.index = index2;
            this.defaultValue = defaultValue2;
        }

        public void onSubscribe(Disposable s) {
            if (DisposableHelper.validate(this.f441s, s)) {
                this.f441s = s;
                this.actual.onSubscribe(this);
            }
        }

        public void dispose() {
            this.f441s.dispose();
        }

        public boolean isDisposed() {
            return this.f441s.isDisposed();
        }

        public void onNext(T t) {
            if (!this.done) {
                long c = this.count;
                if (c == this.index) {
                    this.done = true;
                    this.f441s.dispose();
                    this.actual.onSuccess(t);
                    return;
                }
                this.count = 1 + c;
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
                T v = this.defaultValue;
                if (v != null) {
                    this.actual.onSuccess(v);
                } else {
                    this.actual.onError(new NoSuchElementException());
                }
            }
        }
    }
}
