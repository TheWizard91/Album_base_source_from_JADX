package p019io.reactivex.internal.observers;

import java.util.concurrent.atomic.AtomicReference;
import p019io.reactivex.SingleObserver;
import p019io.reactivex.disposables.Disposable;
import p019io.reactivex.internal.disposables.DisposableHelper;

/* renamed from: io.reactivex.internal.observers.ResumeSingleObserver */
public final class ResumeSingleObserver<T> implements SingleObserver<T> {
    final SingleObserver<? super T> actual;
    final AtomicReference<Disposable> parent;

    public ResumeSingleObserver(AtomicReference<Disposable> parent2, SingleObserver<? super T> actual2) {
        this.parent = parent2;
        this.actual = actual2;
    }

    public void onSubscribe(Disposable d) {
        DisposableHelper.replace(this.parent, d);
    }

    public void onSuccess(T value) {
        this.actual.onSuccess(value);
    }

    public void onError(Throwable e) {
        this.actual.onError(e);
    }
}
