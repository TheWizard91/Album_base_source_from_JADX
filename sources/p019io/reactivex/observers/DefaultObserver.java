package p019io.reactivex.observers;

import p019io.reactivex.Observer;
import p019io.reactivex.disposables.Disposable;
import p019io.reactivex.internal.disposables.DisposableHelper;
import p019io.reactivex.internal.util.EndConsumerHelper;

/* renamed from: io.reactivex.observers.DefaultObserver */
public abstract class DefaultObserver<T> implements Observer<T> {

    /* renamed from: s */
    private Disposable f592s;

    public final void onSubscribe(Disposable s) {
        if (EndConsumerHelper.validate(this.f592s, s, getClass())) {
            this.f592s = s;
            onStart();
        }
    }

    /* access modifiers changed from: protected */
    public final void cancel() {
        Disposable s = this.f592s;
        this.f592s = DisposableHelper.DISPOSED;
        s.dispose();
    }

    /* access modifiers changed from: protected */
    public void onStart() {
    }
}
