package p019io.reactivex.internal.operators.maybe;

import java.util.concurrent.atomic.AtomicReference;
import p019io.reactivex.Maybe;
import p019io.reactivex.MaybeObserver;
import p019io.reactivex.MaybeSource;
import p019io.reactivex.SingleObserver;
import p019io.reactivex.SingleSource;
import p019io.reactivex.disposables.Disposable;
import p019io.reactivex.exceptions.Exceptions;
import p019io.reactivex.functions.Function;
import p019io.reactivex.internal.disposables.DisposableHelper;
import p019io.reactivex.internal.functions.ObjectHelper;

/* renamed from: io.reactivex.internal.operators.maybe.MaybeFlatMapSingleElement */
public final class MaybeFlatMapSingleElement<T, R> extends Maybe<R> {
    final Function<? super T, ? extends SingleSource<? extends R>> mapper;
    final MaybeSource<T> source;

    public MaybeFlatMapSingleElement(MaybeSource<T> source2, Function<? super T, ? extends SingleSource<? extends R>> mapper2) {
        this.source = source2;
        this.mapper = mapper2;
    }

    /* access modifiers changed from: protected */
    public void subscribeActual(MaybeObserver<? super R> actual) {
        this.source.subscribe(new FlatMapMaybeObserver(actual, this.mapper));
    }

    /* renamed from: io.reactivex.internal.operators.maybe.MaybeFlatMapSingleElement$FlatMapMaybeObserver */
    static final class FlatMapMaybeObserver<T, R> extends AtomicReference<Disposable> implements MaybeObserver<T>, Disposable {
        private static final long serialVersionUID = 4827726964688405508L;
        final MaybeObserver<? super R> actual;
        final Function<? super T, ? extends SingleSource<? extends R>> mapper;

        FlatMapMaybeObserver(MaybeObserver<? super R> actual2, Function<? super T, ? extends SingleSource<? extends R>> mapper2) {
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
                ((SingleSource) ObjectHelper.requireNonNull(this.mapper.apply(value), "The mapper returned a null SingleSource")).subscribe(new FlatMapSingleObserver(this, this.actual));
            } catch (Throwable ex) {
                Exceptions.throwIfFatal(ex);
                onError(ex);
            }
        }

        public void onError(Throwable e) {
            this.actual.onError(e);
        }

        public void onComplete() {
            this.actual.onComplete();
        }
    }

    /* renamed from: io.reactivex.internal.operators.maybe.MaybeFlatMapSingleElement$FlatMapSingleObserver */
    static final class FlatMapSingleObserver<R> implements SingleObserver<R> {
        final MaybeObserver<? super R> actual;
        final AtomicReference<Disposable> parent;

        FlatMapSingleObserver(AtomicReference<Disposable> parent2, MaybeObserver<? super R> actual2) {
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
