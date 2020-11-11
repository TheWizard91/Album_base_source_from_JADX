package p019io.reactivex.internal.operators.observable;

import java.util.concurrent.Callable;
import p019io.reactivex.ObservableSource;
import p019io.reactivex.Observer;
import p019io.reactivex.disposables.Disposable;
import p019io.reactivex.exceptions.CompositeException;
import p019io.reactivex.exceptions.Exceptions;
import p019io.reactivex.functions.Function;
import p019io.reactivex.internal.disposables.DisposableHelper;
import p019io.reactivex.internal.functions.ObjectHelper;

/* renamed from: io.reactivex.internal.operators.observable.ObservableMapNotification */
public final class ObservableMapNotification<T, R> extends AbstractObservableWithUpstream<T, ObservableSource<? extends R>> {
    final Callable<? extends ObservableSource<? extends R>> onCompleteSupplier;
    final Function<? super Throwable, ? extends ObservableSource<? extends R>> onErrorMapper;
    final Function<? super T, ? extends ObservableSource<? extends R>> onNextMapper;

    public ObservableMapNotification(ObservableSource<T> source, Function<? super T, ? extends ObservableSource<? extends R>> onNextMapper2, Function<? super Throwable, ? extends ObservableSource<? extends R>> onErrorMapper2, Callable<? extends ObservableSource<? extends R>> onCompleteSupplier2) {
        super(source);
        this.onNextMapper = onNextMapper2;
        this.onErrorMapper = onErrorMapper2;
        this.onCompleteSupplier = onCompleteSupplier2;
    }

    public void subscribeActual(Observer<? super ObservableSource<? extends R>> t) {
        this.source.subscribe(new MapNotificationObserver(t, this.onNextMapper, this.onErrorMapper, this.onCompleteSupplier));
    }

    /* renamed from: io.reactivex.internal.operators.observable.ObservableMapNotification$MapNotificationObserver */
    static final class MapNotificationObserver<T, R> implements Observer<T>, Disposable {
        final Observer<? super ObservableSource<? extends R>> actual;
        final Callable<? extends ObservableSource<? extends R>> onCompleteSupplier;
        final Function<? super Throwable, ? extends ObservableSource<? extends R>> onErrorMapper;
        final Function<? super T, ? extends ObservableSource<? extends R>> onNextMapper;

        /* renamed from: s */
        Disposable f458s;

        MapNotificationObserver(Observer<? super ObservableSource<? extends R>> actual2, Function<? super T, ? extends ObservableSource<? extends R>> onNextMapper2, Function<? super Throwable, ? extends ObservableSource<? extends R>> onErrorMapper2, Callable<? extends ObservableSource<? extends R>> onCompleteSupplier2) {
            this.actual = actual2;
            this.onNextMapper = onNextMapper2;
            this.onErrorMapper = onErrorMapper2;
            this.onCompleteSupplier = onCompleteSupplier2;
        }

        public void onSubscribe(Disposable s) {
            if (DisposableHelper.validate(this.f458s, s)) {
                this.f458s = s;
                this.actual.onSubscribe(this);
            }
        }

        public void dispose() {
            this.f458s.dispose();
        }

        public boolean isDisposed() {
            return this.f458s.isDisposed();
        }

        public void onNext(T t) {
            try {
                this.actual.onNext((ObservableSource) ObjectHelper.requireNonNull(this.onNextMapper.apply(t), "The onNext ObservableSource returned is null"));
            } catch (Throwable e) {
                Exceptions.throwIfFatal(e);
                this.actual.onError(e);
            }
        }

        public void onError(Throwable t) {
            try {
                this.actual.onNext((ObservableSource) ObjectHelper.requireNonNull(this.onErrorMapper.apply(t), "The onError ObservableSource returned is null"));
                this.actual.onComplete();
            } catch (Throwable e) {
                Exceptions.throwIfFatal(e);
                this.actual.onError(new CompositeException(t, e));
            }
        }

        public void onComplete() {
            try {
                this.actual.onNext((ObservableSource) ObjectHelper.requireNonNull(this.onCompleteSupplier.call(), "The onComplete ObservableSource returned is null"));
                this.actual.onComplete();
            } catch (Throwable e) {
                Exceptions.throwIfFatal(e);
                this.actual.onError(e);
            }
        }
    }
}
