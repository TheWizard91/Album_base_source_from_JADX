package p019io.reactivex.internal.operators.completable;

import p019io.reactivex.Completable;
import p019io.reactivex.CompletableObserver;
import p019io.reactivex.CompletableSource;
import p019io.reactivex.disposables.Disposable;
import p019io.reactivex.internal.disposables.DisposableHelper;

/* renamed from: io.reactivex.internal.operators.completable.CompletableDetach */
public final class CompletableDetach extends Completable {
    final CompletableSource source;

    public CompletableDetach(CompletableSource source2) {
        this.source = source2;
    }

    /* access modifiers changed from: protected */
    public void subscribeActual(CompletableObserver observer) {
        this.source.subscribe(new DetachCompletableObserver(observer));
    }

    /* renamed from: io.reactivex.internal.operators.completable.CompletableDetach$DetachCompletableObserver */
    static final class DetachCompletableObserver implements CompletableObserver, Disposable {
        CompletableObserver actual;

        /* renamed from: d */
        Disposable f235d;

        DetachCompletableObserver(CompletableObserver actual2) {
            this.actual = actual2;
        }

        public void dispose() {
            this.actual = null;
            this.f235d.dispose();
            this.f235d = DisposableHelper.DISPOSED;
        }

        public boolean isDisposed() {
            return this.f235d.isDisposed();
        }

        public void onSubscribe(Disposable d) {
            if (DisposableHelper.validate(this.f235d, d)) {
                this.f235d = d;
                this.actual.onSubscribe(this);
            }
        }

        public void onError(Throwable e) {
            this.f235d = DisposableHelper.DISPOSED;
            CompletableObserver a = this.actual;
            if (a != null) {
                this.actual = null;
                a.onError(e);
            }
        }

        public void onComplete() {
            this.f235d = DisposableHelper.DISPOSED;
            CompletableObserver a = this.actual;
            if (a != null) {
                this.actual = null;
                a.onComplete();
            }
        }
    }
}
