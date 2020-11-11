package p019io.reactivex.internal.operators.single;

import java.util.concurrent.atomic.AtomicReference;
import p019io.reactivex.Single;
import p019io.reactivex.SingleObserver;
import p019io.reactivex.SingleSource;
import p019io.reactivex.disposables.Disposable;
import p019io.reactivex.exceptions.Exceptions;
import p019io.reactivex.functions.Function;
import p019io.reactivex.internal.disposables.DisposableHelper;
import p019io.reactivex.internal.functions.ObjectHelper;

/* renamed from: io.reactivex.internal.operators.single.SingleFlatMap */
public final class SingleFlatMap<T, R> extends Single<R> {
    final Function<? super T, ? extends SingleSource<? extends R>> mapper;
    final SingleSource<? extends T> source;

    public SingleFlatMap(SingleSource<? extends T> source2, Function<? super T, ? extends SingleSource<? extends R>> mapper2) {
        this.mapper = mapper2;
        this.source = source2;
    }

    /* access modifiers changed from: protected */
    public void subscribeActual(SingleObserver<? super R> actual) {
        this.source.subscribe(new SingleFlatMapCallback(actual, this.mapper));
    }

    /* renamed from: io.reactivex.internal.operators.single.SingleFlatMap$SingleFlatMapCallback */
    static final class SingleFlatMapCallback<T, R> extends AtomicReference<Disposable> implements SingleObserver<T>, Disposable {
        private static final long serialVersionUID = 3258103020495908596L;
        final SingleObserver<? super R> actual;
        final Function<? super T, ? extends SingleSource<? extends R>> mapper;

        SingleFlatMapCallback(SingleObserver<? super R> actual2, Function<? super T, ? extends SingleSource<? extends R>> mapper2) {
            this.actual = actual2;
            this.mapper = mapper2;
        }

        public void dispose() {
            DisposableHelper.dispose(this);
        }

        public boolean isDisposed() {
            return DisposableHelper.isDisposed((Disposable) get());
        }

        public void onSubscribe(Disposable d) {
            if (DisposableHelper.setOnce(this, d)) {
                this.actual.onSubscribe(this);
            }
        }

        public void onSuccess(T value) {
            try {
                SingleSource<? extends R> o = (SingleSource) ObjectHelper.requireNonNull(this.mapper.apply(value), "The single returned by the mapper is null");
                if (!isDisposed()) {
                    o.subscribe(new FlatMapSingleObserver(this, this.actual));
                }
            } catch (Throwable e) {
                Exceptions.throwIfFatal(e);
                this.actual.onError(e);
            }
        }

        public void onError(Throwable e) {
            this.actual.onError(e);
        }

        /* renamed from: io.reactivex.internal.operators.single.SingleFlatMap$SingleFlatMapCallback$FlatMapSingleObserver */
        static final class FlatMapSingleObserver<R> implements SingleObserver<R> {
            final SingleObserver<? super R> actual;
            final AtomicReference<Disposable> parent;

            FlatMapSingleObserver(AtomicReference<Disposable> parent2, SingleObserver<? super R> actual2) {
                this.parent = parent2;
                this.actual = actual2;
            }

            public void onSubscribe(Disposable d) {
                DisposableHelper.replace(this.parent, d);
            }

            public void onSuccess(R value) {
                this.actual.onSuccess(value);
            }

            public void onError(Throwable e) {
                this.actual.onError(e);
            }
        }
    }
}
