package p019io.reactivex.internal.operators.observable;

import java.util.NoSuchElementException;
import p019io.reactivex.ObservableSource;
import p019io.reactivex.Observer;
import p019io.reactivex.Single;
import p019io.reactivex.SingleObserver;
import p019io.reactivex.disposables.Disposable;
import p019io.reactivex.internal.disposables.DisposableHelper;

/* renamed from: io.reactivex.internal.operators.observable.ObservableLastSingle */
public final class ObservableLastSingle<T> extends Single<T> {
    final T defaultItem;
    final ObservableSource<T> source;

    public ObservableLastSingle(ObservableSource<T> source2, T defaultItem2) {
        this.source = source2;
        this.defaultItem = defaultItem2;
    }

    /* access modifiers changed from: protected */
    public void subscribeActual(SingleObserver<? super T> observer) {
        this.source.subscribe(new LastObserver(observer, this.defaultItem));
    }

    /* renamed from: io.reactivex.internal.operators.observable.ObservableLastSingle$LastObserver */
    static final class LastObserver<T> implements Observer<T>, Disposable {
        final SingleObserver<? super T> actual;
        final T defaultItem;
        T item;

        /* renamed from: s */
        Disposable f457s;

        LastObserver(SingleObserver<? super T> actual2, T defaultItem2) {
            this.actual = actual2;
            this.defaultItem = defaultItem2;
        }

        public void dispose() {
            this.f457s.dispose();
            this.f457s = DisposableHelper.DISPOSED;
        }

        public boolean isDisposed() {
            return this.f457s == DisposableHelper.DISPOSED;
        }

        public void onSubscribe(Disposable s) {
            if (DisposableHelper.validate(this.f457s, s)) {
                this.f457s = s;
                this.actual.onSubscribe(this);
            }
        }

        public void onNext(T t) {
            this.item = t;
        }

        public void onError(Throwable t) {
            this.f457s = DisposableHelper.DISPOSED;
            this.item = null;
            this.actual.onError(t);
        }

        public void onComplete() {
            this.f457s = DisposableHelper.DISPOSED;
            T v = this.item;
            if (v != null) {
                this.item = null;
                this.actual.onSuccess(v);
                return;
            }
            T v2 = this.defaultItem;
            if (v2 != null) {
                this.actual.onSuccess(v2);
            } else {
                this.actual.onError(new NoSuchElementException());
            }
        }
    }
}
