package p019io.reactivex.internal.observers;

import p019io.reactivex.Observer;
import p019io.reactivex.disposables.Disposable;
import p019io.reactivex.internal.disposables.DisposableHelper;

/* renamed from: io.reactivex.internal.observers.DeferredScalarObserver */
public abstract class DeferredScalarObserver<T, R> extends DeferredScalarDisposable<R> implements Observer<T> {
    private static final long serialVersionUID = -266195175408988651L;

    /* renamed from: s */
    protected Disposable f215s;

    public DeferredScalarObserver(Observer<? super R> actual) {
        super(actual);
    }

    public void onSubscribe(Disposable s) {
        if (DisposableHelper.validate(this.f215s, s)) {
            this.f215s = s;
            this.actual.onSubscribe(this);
        }
    }

    public void onError(Throwable t) {
        this.value = null;
        error(t);
    }

    public void onComplete() {
        R v = this.value;
        if (v != null) {
            this.value = null;
            complete(v);
            return;
        }
        complete();
    }

    public void dispose() {
        super.dispose();
        this.f215s.dispose();
    }
}
