package p019io.reactivex.internal.operators.observable;

import p019io.reactivex.ObservableSource;
import p019io.reactivex.Observer;
import p019io.reactivex.disposables.Disposable;
import p019io.reactivex.exceptions.Exceptions;
import p019io.reactivex.functions.Function;
import p019io.reactivex.internal.disposables.DisposableHelper;
import p019io.reactivex.internal.functions.ObjectHelper;
import p019io.reactivex.plugins.RxJavaPlugins;

/* renamed from: io.reactivex.internal.operators.observable.ObservableFlattenIterable */
public final class ObservableFlattenIterable<T, R> extends AbstractObservableWithUpstream<T, R> {
    final Function<? super T, ? extends Iterable<? extends R>> mapper;

    public ObservableFlattenIterable(ObservableSource<T> source, Function<? super T, ? extends Iterable<? extends R>> mapper2) {
        super(source);
        this.mapper = mapper2;
    }

    /* access modifiers changed from: protected */
    public void subscribeActual(Observer<? super R> observer) {
        this.source.subscribe(new FlattenIterableObserver(observer, this.mapper));
    }

    /* renamed from: io.reactivex.internal.operators.observable.ObservableFlattenIterable$FlattenIterableObserver */
    static final class FlattenIterableObserver<T, R> implements Observer<T>, Disposable {
        final Observer<? super R> actual;

        /* renamed from: d */
        Disposable f448d;
        final Function<? super T, ? extends Iterable<? extends R>> mapper;

        FlattenIterableObserver(Observer<? super R> actual2, Function<? super T, ? extends Iterable<? extends R>> mapper2) {
            this.actual = actual2;
            this.mapper = mapper2;
        }

        public void onSubscribe(Disposable d) {
            if (DisposableHelper.validate(this.f448d, d)) {
                this.f448d = d;
                this.actual.onSubscribe(this);
            }
        }

        public void onNext(T value) {
            if (this.f448d != DisposableHelper.DISPOSED) {
                try {
                    Observer<? super R> a = this.actual;
                    for (Object requireNonNull : (Iterable) this.mapper.apply(value)) {
                        try {
                            try {
                                a.onNext(ObjectHelper.requireNonNull(requireNonNull, "The iterator returned a null value"));
                            } catch (Throwable ex) {
                                Exceptions.throwIfFatal(ex);
                                this.f448d.dispose();
                                onError(ex);
                                return;
                            }
                        } catch (Throwable ex2) {
                            Exceptions.throwIfFatal(ex2);
                            this.f448d.dispose();
                            onError(ex2);
                            return;
                        }
                    }
                } catch (Throwable ex3) {
                    Exceptions.throwIfFatal(ex3);
                    this.f448d.dispose();
                    onError(ex3);
                }
            }
        }

        public void onError(Throwable e) {
            if (this.f448d == DisposableHelper.DISPOSED) {
                RxJavaPlugins.onError(e);
                return;
            }
            this.f448d = DisposableHelper.DISPOSED;
            this.actual.onError(e);
        }

        public void onComplete() {
            if (this.f448d != DisposableHelper.DISPOSED) {
                this.f448d = DisposableHelper.DISPOSED;
                this.actual.onComplete();
            }
        }

        public boolean isDisposed() {
            return this.f448d.isDisposed();
        }

        public void dispose() {
            this.f448d.dispose();
            this.f448d = DisposableHelper.DISPOSED;
        }
    }
}
