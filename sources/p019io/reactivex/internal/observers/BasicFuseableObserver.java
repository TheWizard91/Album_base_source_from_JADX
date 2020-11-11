package p019io.reactivex.internal.observers;

import p019io.reactivex.Observer;
import p019io.reactivex.disposables.Disposable;
import p019io.reactivex.exceptions.Exceptions;
import p019io.reactivex.internal.disposables.DisposableHelper;
import p019io.reactivex.internal.fuseable.QueueDisposable;
import p019io.reactivex.plugins.RxJavaPlugins;

/* renamed from: io.reactivex.internal.observers.BasicFuseableObserver */
public abstract class BasicFuseableObserver<T, R> implements Observer<T>, QueueDisposable<R> {
    protected final Observer<? super R> actual;
    protected boolean done;

    /* renamed from: qs */
    protected QueueDisposable<T> f211qs;

    /* renamed from: s */
    protected Disposable f212s;
    protected int sourceMode;

    public BasicFuseableObserver(Observer<? super R> actual2) {
        this.actual = actual2;
    }

    public final void onSubscribe(Disposable s) {
        if (DisposableHelper.validate(this.f212s, s)) {
            this.f212s = s;
            if (s instanceof QueueDisposable) {
                this.f211qs = (QueueDisposable) s;
            }
            if (beforeDownstream()) {
                this.actual.onSubscribe(this);
                afterDownstream();
            }
        }
    }

    /* access modifiers changed from: protected */
    public boolean beforeDownstream() {
        return true;
    }

    /* access modifiers changed from: protected */
    public void afterDownstream() {
    }

    public void onError(Throwable t) {
        if (this.done) {
            RxJavaPlugins.onError(t);
            return;
        }
        this.done = true;
        this.actual.onError(t);
    }

    /* access modifiers changed from: protected */
    public final void fail(Throwable t) {
        Exceptions.throwIfFatal(t);
        this.f212s.dispose();
        onError(t);
    }

    public void onComplete() {
        if (!this.done) {
            this.done = true;
            this.actual.onComplete();
        }
    }

    /* access modifiers changed from: protected */
    public final int transitiveBoundaryFusion(int mode) {
        QueueDisposable<T> qs = this.f211qs;
        if (qs == null || (mode & 4) != 0) {
            return 0;
        }
        int m = qs.requestFusion(mode);
        if (m != 0) {
            this.sourceMode = m;
        }
        return m;
    }

    public void dispose() {
        this.f212s.dispose();
    }

    public boolean isDisposed() {
        return this.f212s.isDisposed();
    }

    public boolean isEmpty() {
        return this.f211qs.isEmpty();
    }

    public void clear() {
        this.f211qs.clear();
    }

    public final boolean offer(R r) {
        throw new UnsupportedOperationException("Should not be called!");
    }

    public final boolean offer(R r, R r2) {
        throw new UnsupportedOperationException("Should not be called!");
    }
}
