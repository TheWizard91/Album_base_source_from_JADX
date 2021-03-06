package p019io.reactivex.internal.operators.observable;

import p019io.reactivex.Observable;
import p019io.reactivex.Observer;
import p019io.reactivex.internal.functions.ObjectHelper;
import p019io.reactivex.internal.observers.BasicQueueDisposable;

/* renamed from: io.reactivex.internal.operators.observable.ObservableFromArray */
public final class ObservableFromArray<T> extends Observable<T> {
    final T[] array;

    public ObservableFromArray(T[] array2) {
        this.array = array2;
    }

    public void subscribeActual(Observer<? super T> s) {
        FromArrayDisposable<T> d = new FromArrayDisposable<>(s, this.array);
        s.onSubscribe(d);
        if (!d.fusionMode) {
            d.run();
        }
    }

    /* renamed from: io.reactivex.internal.operators.observable.ObservableFromArray$FromArrayDisposable */
    static final class FromArrayDisposable<T> extends BasicQueueDisposable<T> {
        final Observer<? super T> actual;
        final T[] array;
        volatile boolean disposed;
        boolean fusionMode;
        int index;

        FromArrayDisposable(Observer<? super T> actual2, T[] array2) {
            this.actual = actual2;
            this.array = array2;
        }

        public int requestFusion(int mode) {
            if ((mode & 1) == 0) {
                return 0;
            }
            this.fusionMode = true;
            return 1;
        }

        public T poll() {
            int i = this.index;
            T[] a = this.array;
            if (i == a.length) {
                return null;
            }
            this.index = i + 1;
            return ObjectHelper.requireNonNull(a[i], "The array element is null");
        }

        public boolean isEmpty() {
            return this.index == this.array.length;
        }

        public void clear() {
            this.index = this.array.length;
        }

        public void dispose() {
            this.disposed = true;
        }

        public boolean isDisposed() {
            return this.disposed;
        }

        /* access modifiers changed from: package-private */
        public void run() {
            T[] a = this.array;
            int n = a.length;
            int i = 0;
            while (i < n && !isDisposed()) {
                T value = a[i];
                if (value == null) {
                    this.actual.onError(new NullPointerException("The " + i + "th element is null"));
                    return;
                } else {
                    this.actual.onNext(value);
                    i++;
                }
            }
            if (isDisposed() == 0) {
                this.actual.onComplete();
            }
        }
    }
}
