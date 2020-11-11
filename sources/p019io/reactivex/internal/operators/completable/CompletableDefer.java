package p019io.reactivex.internal.operators.completable;

import java.util.concurrent.Callable;
import p019io.reactivex.Completable;
import p019io.reactivex.CompletableObserver;
import p019io.reactivex.CompletableSource;
import p019io.reactivex.exceptions.Exceptions;
import p019io.reactivex.internal.disposables.EmptyDisposable;
import p019io.reactivex.internal.functions.ObjectHelper;

/* renamed from: io.reactivex.internal.operators.completable.CompletableDefer */
public final class CompletableDefer extends Completable {
    final Callable<? extends CompletableSource> completableSupplier;

    public CompletableDefer(Callable<? extends CompletableSource> completableSupplier2) {
        this.completableSupplier = completableSupplier2;
    }

    /* access modifiers changed from: protected */
    public void subscribeActual(CompletableObserver s) {
        try {
            ((CompletableSource) ObjectHelper.requireNonNull(this.completableSupplier.call(), "The completableSupplier returned a null CompletableSource")).subscribe(s);
        } catch (Throwable e) {
            Exceptions.throwIfFatal(e);
            EmptyDisposable.error(e, s);
        }
    }
}
