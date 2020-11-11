package p019io.reactivex.internal.operators.single;

import java.util.concurrent.atomic.AtomicBoolean;
import p019io.reactivex.Single;
import p019io.reactivex.SingleObserver;
import p019io.reactivex.SingleSource;
import p019io.reactivex.disposables.CompositeDisposable;
import p019io.reactivex.disposables.Disposable;
import p019io.reactivex.exceptions.Exceptions;
import p019io.reactivex.internal.disposables.EmptyDisposable;
import p019io.reactivex.plugins.RxJavaPlugins;

/* renamed from: io.reactivex.internal.operators.single.SingleAmb */
public final class SingleAmb<T> extends Single<T> {
    private final SingleSource<? extends T>[] sources;
    private final Iterable<? extends SingleSource<? extends T>> sourcesIterable;

    public SingleAmb(SingleSource<? extends T>[] sources2, Iterable<? extends SingleSource<? extends T>> sourcesIterable2) {
        this.sources = sources2;
        this.sourcesIterable = sourcesIterable2;
    }

    /* access modifiers changed from: protected */
    public void subscribeActual(SingleObserver<? super T> s) {
        SingleSource<? extends T>[] sources2 = this.sources;
        int count = 0;
        if (sources2 == null) {
            sources2 = new SingleSource[8];
            try {
                for (SingleSource<? extends T> element : this.sourcesIterable) {
                    if (element == null) {
                        EmptyDisposable.error((Throwable) new NullPointerException("One of the sources is null"), (SingleObserver<?>) s);
                        return;
                    }
                    if (count == sources2.length) {
                        SingleSource<? extends T>[] b = new SingleSource[((count >> 2) + count)];
                        System.arraycopy(sources2, 0, b, 0, count);
                        sources2 = b;
                    }
                    int count2 = count + 1;
                    try {
                        sources2[count] = element;
                        count = count2;
                    } catch (Throwable th) {
                        e = th;
                        int i = count2;
                        Exceptions.throwIfFatal(e);
                        EmptyDisposable.error(e, (SingleObserver<?>) s);
                        return;
                    }
                }
            } catch (Throwable th2) {
                e = th2;
                Exceptions.throwIfFatal(e);
                EmptyDisposable.error(e, (SingleObserver<?>) s);
                return;
            }
        } else {
            count = sources2.length;
        }
        CompositeDisposable set = new CompositeDisposable();
        AmbSingleObserver<T> shared = new AmbSingleObserver<>(s, set);
        s.onSubscribe(set);
        int i2 = 0;
        while (i2 < count) {
            SingleSource<? extends T> s1 = sources2[i2];
            if (!shared.get()) {
                if (s1 == null) {
                    set.dispose();
                    Throwable e = new NullPointerException("One of the sources is null");
                    if (shared.compareAndSet(false, true)) {
                        s.onError(e);
                        return;
                    } else {
                        RxJavaPlugins.onError(e);
                        return;
                    }
                } else {
                    s1.subscribe(shared);
                    i2++;
                }
            } else {
                return;
            }
        }
    }

    /* renamed from: io.reactivex.internal.operators.single.SingleAmb$AmbSingleObserver */
    static final class AmbSingleObserver<T> extends AtomicBoolean implements SingleObserver<T> {
        private static final long serialVersionUID = -1944085461036028108L;

        /* renamed from: s */
        final SingleObserver<? super T> f533s;
        final CompositeDisposable set;

        AmbSingleObserver(SingleObserver<? super T> s, CompositeDisposable set2) {
            this.f533s = s;
            this.set = set2;
        }

        public void onSubscribe(Disposable d) {
            this.set.add(d);
        }

        public void onSuccess(T value) {
            if (compareAndSet(false, true)) {
                this.set.dispose();
                this.f533s.onSuccess(value);
            }
        }

        public void onError(Throwable e) {
            if (compareAndSet(false, true)) {
                this.set.dispose();
                this.f533s.onError(e);
                return;
            }
            RxJavaPlugins.onError(e);
        }
    }
}
