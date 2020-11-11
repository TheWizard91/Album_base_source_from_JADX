package p019io.reactivex.internal.operators.maybe;

import java.util.Arrays;
import p019io.reactivex.Maybe;
import p019io.reactivex.MaybeObserver;
import p019io.reactivex.MaybeSource;
import p019io.reactivex.exceptions.Exceptions;
import p019io.reactivex.functions.Function;
import p019io.reactivex.internal.disposables.EmptyDisposable;
import p019io.reactivex.internal.functions.ObjectHelper;
import p019io.reactivex.internal.operators.maybe.MaybeMap;
import p019io.reactivex.internal.operators.maybe.MaybeZipArray;

/* renamed from: io.reactivex.internal.operators.maybe.MaybeZipIterable */
public final class MaybeZipIterable<T, R> extends Maybe<R> {
    final Iterable<? extends MaybeSource<? extends T>> sources;
    final Function<? super Object[], ? extends R> zipper;

    public MaybeZipIterable(Iterable<? extends MaybeSource<? extends T>> sources2, Function<? super Object[], ? extends R> zipper2) {
        this.sources = sources2;
        this.zipper = zipper2;
    }

    /* access modifiers changed from: protected */
    public void subscribeActual(MaybeObserver<? super R> observer) {
        MaybeSource<? extends T>[] a = new MaybeSource[8];
        int n = 0;
        try {
            for (MaybeSource<? extends T> source : this.sources) {
                if (source == null) {
                    EmptyDisposable.error((Throwable) new NullPointerException("One of the sources is null"), (MaybeObserver<?>) observer);
                    return;
                }
                if (n == a.length) {
                    a = (MaybeSource[]) Arrays.copyOf(a, (n >> 2) + n);
                }
                int n2 = n + 1;
                try {
                    a[n] = source;
                    n = n2;
                } catch (Throwable th) {
                    ex = th;
                    int i = n2;
                    Exceptions.throwIfFatal(ex);
                    EmptyDisposable.error(ex, (MaybeObserver<?>) observer);
                }
            }
            if (n == 0) {
                EmptyDisposable.complete((MaybeObserver<?>) observer);
            } else if (n == 1) {
                a[0].subscribe(new MaybeMap.MapMaybeObserver(observer, new SingletonArrayFunc()));
            } else {
                MaybeZipArray.ZipCoordinator<T, R> parent = new MaybeZipArray.ZipCoordinator<>(observer, n, this.zipper);
                observer.onSubscribe(parent);
                for (int i2 = 0; i2 < n && !parent.isDisposed(); i2++) {
                    a[i2].subscribe(parent.observers[i2]);
                }
            }
        } catch (Throwable th2) {
            ex = th2;
            Exceptions.throwIfFatal(ex);
            EmptyDisposable.error(ex, (MaybeObserver<?>) observer);
        }
    }

    /* renamed from: io.reactivex.internal.operators.maybe.MaybeZipIterable$SingletonArrayFunc */
    final class SingletonArrayFunc implements Function<T, R> {
        SingletonArrayFunc() {
        }

        public R apply(T t) throws Exception {
            return ObjectHelper.requireNonNull(MaybeZipIterable.this.zipper.apply(new Object[]{t}), "The zipper returned a null value");
        }
    }
}
