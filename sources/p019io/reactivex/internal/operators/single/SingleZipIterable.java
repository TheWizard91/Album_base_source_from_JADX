package p019io.reactivex.internal.operators.single;

import java.util.Arrays;
import java.util.NoSuchElementException;
import p019io.reactivex.Single;
import p019io.reactivex.SingleObserver;
import p019io.reactivex.SingleSource;
import p019io.reactivex.exceptions.Exceptions;
import p019io.reactivex.functions.Function;
import p019io.reactivex.internal.disposables.EmptyDisposable;
import p019io.reactivex.internal.functions.ObjectHelper;
import p019io.reactivex.internal.operators.single.SingleMap;
import p019io.reactivex.internal.operators.single.SingleZipArray;

/* renamed from: io.reactivex.internal.operators.single.SingleZipIterable */
public final class SingleZipIterable<T, R> extends Single<R> {
    final Iterable<? extends SingleSource<? extends T>> sources;
    final Function<? super Object[], ? extends R> zipper;

    public SingleZipIterable(Iterable<? extends SingleSource<? extends T>> sources2, Function<? super Object[], ? extends R> zipper2) {
        this.sources = sources2;
        this.zipper = zipper2;
    }

    /* access modifiers changed from: protected */
    public void subscribeActual(SingleObserver<? super R> observer) {
        SingleSource<? extends T>[] a = new SingleSource[8];
        int n = 0;
        try {
            for (SingleSource<? extends T> source : this.sources) {
                if (source == null) {
                    EmptyDisposable.error((Throwable) new NullPointerException("One of the sources is null"), (SingleObserver<?>) observer);
                    return;
                }
                if (n == a.length) {
                    a = (SingleSource[]) Arrays.copyOf(a, (n >> 2) + n);
                }
                int n2 = n + 1;
                try {
                    a[n] = source;
                    n = n2;
                } catch (Throwable th) {
                    ex = th;
                    int i = n2;
                    Exceptions.throwIfFatal(ex);
                    EmptyDisposable.error(ex, (SingleObserver<?>) observer);
                }
            }
            if (n == 0) {
                EmptyDisposable.error((Throwable) new NoSuchElementException(), (SingleObserver<?>) observer);
            } else if (n == 1) {
                a[0].subscribe(new SingleMap.MapSingleObserver(observer, new SingletonArrayFunc()));
            } else {
                SingleZipArray.ZipCoordinator<T, R> parent = new SingleZipArray.ZipCoordinator<>(observer, n, this.zipper);
                observer.onSubscribe(parent);
                for (int i2 = 0; i2 < n && !parent.isDisposed(); i2++) {
                    a[i2].subscribe(parent.observers[i2]);
                }
            }
        } catch (Throwable th2) {
            ex = th2;
            Exceptions.throwIfFatal(ex);
            EmptyDisposable.error(ex, (SingleObserver<?>) observer);
        }
    }

    /* renamed from: io.reactivex.internal.operators.single.SingleZipIterable$SingletonArrayFunc */
    final class SingletonArrayFunc implements Function<T, R> {
        SingletonArrayFunc() {
        }

        public R apply(T t) throws Exception {
            return ObjectHelper.requireNonNull(SingleZipIterable.this.zipper.apply(new Object[]{t}), "The zipper returned a null value");
        }
    }
}
