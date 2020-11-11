package p019io.reactivex.internal.operators.mixed;

import java.util.concurrent.Callable;
import p019io.reactivex.CompletableObserver;
import p019io.reactivex.CompletableSource;
import p019io.reactivex.MaybeSource;
import p019io.reactivex.Observer;
import p019io.reactivex.SingleSource;
import p019io.reactivex.exceptions.Exceptions;
import p019io.reactivex.functions.Function;
import p019io.reactivex.internal.disposables.EmptyDisposable;
import p019io.reactivex.internal.functions.ObjectHelper;
import p019io.reactivex.internal.operators.maybe.MaybeToObservable;
import p019io.reactivex.internal.operators.single.SingleToObservable;

/* renamed from: io.reactivex.internal.operators.mixed.ScalarXMapZHelper */
final class ScalarXMapZHelper {
    private ScalarXMapZHelper() {
        throw new IllegalStateException("No instances!");
    }

    static <T> boolean tryAsCompletable(Object source, Function<? super T, ? extends CompletableSource> mapper, CompletableObserver observer) {
        if (!(source instanceof Callable)) {
            return false;
        }
        CompletableSource cs = null;
        try {
            T item = ((Callable) source).call();
            if (item != null) {
                cs = (CompletableSource) ObjectHelper.requireNonNull(mapper.apply(item), "The mapper returned a null CompletableSource");
            }
            if (cs == null) {
                EmptyDisposable.complete(observer);
            } else {
                cs.subscribe(observer);
            }
            return true;
        } catch (Throwable ex) {
            Exceptions.throwIfFatal(ex);
            EmptyDisposable.error(ex, observer);
            return true;
        }
    }

    static <T, R> boolean tryAsMaybe(Object source, Function<? super T, ? extends MaybeSource<? extends R>> mapper, Observer<? super R> observer) {
        if (!(source instanceof Callable)) {
            return false;
        }
        MaybeSource<? extends R> cs = null;
        try {
            T item = ((Callable) source).call();
            if (item != null) {
                cs = (MaybeSource) ObjectHelper.requireNonNull(mapper.apply(item), "The mapper returned a null MaybeSource");
            }
            if (cs == null) {
                EmptyDisposable.complete((Observer<?>) observer);
            } else {
                cs.subscribe(MaybeToObservable.create(observer));
            }
            return true;
        } catch (Throwable ex) {
            Exceptions.throwIfFatal(ex);
            EmptyDisposable.error(ex, (Observer<?>) observer);
            return true;
        }
    }

    static <T, R> boolean tryAsSingle(Object source, Function<? super T, ? extends SingleSource<? extends R>> mapper, Observer<? super R> observer) {
        if (!(source instanceof Callable)) {
            return false;
        }
        SingleSource<? extends R> cs = null;
        try {
            T item = ((Callable) source).call();
            if (item != null) {
                cs = (SingleSource) ObjectHelper.requireNonNull(mapper.apply(item), "The mapper returned a null SingleSource");
            }
            if (cs == null) {
                EmptyDisposable.complete((Observer<?>) observer);
            } else {
                cs.subscribe(SingleToObservable.create(observer));
            }
            return true;
        } catch (Throwable ex) {
            Exceptions.throwIfFatal(ex);
            EmptyDisposable.error(ex, (Observer<?>) observer);
            return true;
        }
    }
}
