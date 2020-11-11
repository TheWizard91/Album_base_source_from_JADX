package p019io.reactivex.internal.operators.completable;

import p019io.reactivex.Completable;
import p019io.reactivex.CompletableObserver;
import p019io.reactivex.CompletableOperator;
import p019io.reactivex.CompletableSource;
import p019io.reactivex.exceptions.Exceptions;
import p019io.reactivex.plugins.RxJavaPlugins;

/* renamed from: io.reactivex.internal.operators.completable.CompletableLift */
public final class CompletableLift extends Completable {
    final CompletableOperator onLift;
    final CompletableSource source;

    public CompletableLift(CompletableSource source2, CompletableOperator onLift2) {
        this.source = source2;
        this.onLift = onLift2;
    }

    /* access modifiers changed from: protected */
    public void subscribeActual(CompletableObserver s) {
        try {
            this.source.subscribe(this.onLift.apply(s));
        } catch (NullPointerException ex) {
            throw ex;
        } catch (Throwable ex2) {
            Exceptions.throwIfFatal(ex2);
            RxJavaPlugins.onError(ex2);
        }
    }
}
