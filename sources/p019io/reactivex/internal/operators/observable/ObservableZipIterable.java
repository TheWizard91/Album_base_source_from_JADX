package p019io.reactivex.internal.operators.observable;

import java.util.Iterator;
import p019io.reactivex.Observable;
import p019io.reactivex.Observer;
import p019io.reactivex.disposables.Disposable;
import p019io.reactivex.exceptions.Exceptions;
import p019io.reactivex.functions.BiFunction;
import p019io.reactivex.internal.disposables.DisposableHelper;
import p019io.reactivex.internal.disposables.EmptyDisposable;
import p019io.reactivex.internal.functions.ObjectHelper;
import p019io.reactivex.plugins.RxJavaPlugins;

/* renamed from: io.reactivex.internal.operators.observable.ObservableZipIterable */
public final class ObservableZipIterable<T, U, V> extends Observable<V> {
    final Iterable<U> other;
    final Observable<? extends T> source;
    final BiFunction<? super T, ? super U, ? extends V> zipper;

    public ObservableZipIterable(Observable<? extends T> source2, Iterable<U> other2, BiFunction<? super T, ? super U, ? extends V> zipper2) {
        this.source = source2;
        this.other = other2;
        this.zipper = zipper2;
    }

    public void subscribeActual(Observer<? super V> t) {
        try {
            Iterator<U> it = (Iterator) ObjectHelper.requireNonNull(this.other.iterator(), "The iterator returned by other is null");
            try {
                if (!it.hasNext()) {
                    EmptyDisposable.complete((Observer<?>) t);
                } else {
                    this.source.subscribe(new ZipIterableObserver(t, it, this.zipper));
                }
            } catch (Throwable e) {
                Exceptions.throwIfFatal(e);
                EmptyDisposable.error(e, (Observer<?>) t);
            }
        } catch (Throwable e2) {
            Exceptions.throwIfFatal(e2);
            EmptyDisposable.error(e2, (Observer<?>) t);
        }
    }

    /* renamed from: io.reactivex.internal.operators.observable.ObservableZipIterable$ZipIterableObserver */
    static final class ZipIterableObserver<T, U, V> implements Observer<T>, Disposable {
        final Observer<? super V> actual;
        boolean done;
        final Iterator<U> iterator;

        /* renamed from: s */
        Disposable f519s;
        final BiFunction<? super T, ? super U, ? extends V> zipper;

        ZipIterableObserver(Observer<? super V> actual2, Iterator<U> iterator2, BiFunction<? super T, ? super U, ? extends V> zipper2) {
            this.actual = actual2;
            this.iterator = iterator2;
            this.zipper = zipper2;
        }

        public void onSubscribe(Disposable s) {
            if (DisposableHelper.validate(this.f519s, s)) {
                this.f519s = s;
                this.actual.onSubscribe(this);
            }
        }

        public void dispose() {
            this.f519s.dispose();
        }

        public boolean isDisposed() {
            return this.f519s.isDisposed();
        }

        public void onNext(T t) {
            if (!this.done) {
                try {
                    try {
                        this.actual.onNext(ObjectHelper.requireNonNull(this.zipper.apply(t, ObjectHelper.requireNonNull(this.iterator.next(), "The iterator returned a null value")), "The zipper function returned a null value"));
                        try {
                            if (!this.iterator.hasNext()) {
                                this.done = true;
                                this.f519s.dispose();
                                this.actual.onComplete();
                            }
                        } catch (Throwable e) {
                            Exceptions.throwIfFatal(e);
                            error(e);
                        }
                    } catch (Throwable e2) {
                        Exceptions.throwIfFatal(e2);
                        error(e2);
                    }
                } catch (Throwable e3) {
                    Exceptions.throwIfFatal(e3);
                    error(e3);
                }
            }
        }

        /* access modifiers changed from: package-private */
        public void error(Throwable e) {
            this.done = true;
            this.f519s.dispose();
            this.actual.onError(e);
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
                this.actual.onComplete();
            }
        }
    }
}
