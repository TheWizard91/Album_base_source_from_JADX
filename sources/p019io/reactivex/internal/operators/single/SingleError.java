package p019io.reactivex.internal.operators.single;

import java.util.concurrent.Callable;
import p019io.reactivex.Single;
import p019io.reactivex.SingleObserver;
import p019io.reactivex.exceptions.Exceptions;
import p019io.reactivex.internal.disposables.EmptyDisposable;
import p019io.reactivex.internal.functions.ObjectHelper;

/* renamed from: io.reactivex.internal.operators.single.SingleError */
public final class SingleError<T> extends Single<T> {
    final Callable<? extends Throwable> errorSupplier;

    public SingleError(Callable<? extends Throwable> errorSupplier2) {
        this.errorSupplier = errorSupplier2;
    }

    /* access modifiers changed from: protected */
    public void subscribeActual(SingleObserver<? super T> s) {
        try {
            e = (Throwable) ObjectHelper.requireNonNull(this.errorSupplier.call(), "Callable returned null throwable. Null values are generally not allowed in 2.x operators and sources.");
        } catch (Throwable th) {
            e = th;
            Exceptions.throwIfFatal(e);
            Throwable th2 = e;
        }
        EmptyDisposable.error(e, (SingleObserver<?>) s);
    }
}
