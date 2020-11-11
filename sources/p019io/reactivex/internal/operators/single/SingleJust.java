package p019io.reactivex.internal.operators.single;

import p019io.reactivex.Single;
import p019io.reactivex.SingleObserver;
import p019io.reactivex.disposables.Disposables;

/* renamed from: io.reactivex.internal.operators.single.SingleJust */
public final class SingleJust<T> extends Single<T> {
    final T value;

    public SingleJust(T value2) {
        this.value = value2;
    }

    /* access modifiers changed from: protected */
    public void subscribeActual(SingleObserver<? super T> s) {
        s.onSubscribe(Disposables.disposed());
        s.onSuccess(this.value);
    }
}
