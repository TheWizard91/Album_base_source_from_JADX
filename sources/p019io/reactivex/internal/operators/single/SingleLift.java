package p019io.reactivex.internal.operators.single;

import p019io.reactivex.Single;
import p019io.reactivex.SingleObserver;
import p019io.reactivex.SingleOperator;
import p019io.reactivex.SingleSource;
import p019io.reactivex.exceptions.Exceptions;
import p019io.reactivex.internal.disposables.EmptyDisposable;
import p019io.reactivex.internal.functions.ObjectHelper;

/* renamed from: io.reactivex.internal.operators.single.SingleLift */
public final class SingleLift<T, R> extends Single<R> {
    final SingleOperator<? extends R, ? super T> onLift;
    final SingleSource<T> source;

    public SingleLift(SingleSource<T> source2, SingleOperator<? extends R, ? super T> onLift2) {
        this.source = source2;
        this.onLift = onLift2;
    }

    /* access modifiers changed from: protected */
    public void subscribeActual(SingleObserver<? super R> s) {
        try {
            this.source.subscribe((SingleObserver) ObjectHelper.requireNonNull(this.onLift.apply(s), "The onLift returned a null SingleObserver"));
        } catch (Throwable ex) {
            Exceptions.throwIfFatal(ex);
            EmptyDisposable.error(ex, (SingleObserver<?>) s);
        }
    }
}
