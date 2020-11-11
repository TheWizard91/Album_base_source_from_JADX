package p019io.reactivex.internal.operators.completable;

import p019io.reactivex.Completable;
import p019io.reactivex.CompletableObserver;
import p019io.reactivex.SingleObserver;
import p019io.reactivex.SingleSource;
import p019io.reactivex.disposables.Disposable;

/* renamed from: io.reactivex.internal.operators.completable.CompletableFromSingle */
public final class CompletableFromSingle<T> extends Completable {
    final SingleSource<T> single;

    public CompletableFromSingle(SingleSource<T> single2) {
        this.single = single2;
    }

    /* access modifiers changed from: protected */
    public void subscribeActual(CompletableObserver s) {
        this.single.subscribe(new CompletableFromSingleObserver(s));
    }

    /* renamed from: io.reactivex.internal.operators.completable.CompletableFromSingle$CompletableFromSingleObserver */
    static final class CompletableFromSingleObserver<T> implements SingleObserver<T> {

        /* renamed from: co */
        final CompletableObserver f242co;

        CompletableFromSingleObserver(CompletableObserver co) {
            this.f242co = co;
        }

        public void onError(Throwable e) {
            this.f242co.onError(e);
        }

        public void onSubscribe(Disposable d) {
            this.f242co.onSubscribe(d);
        }

        public void onSuccess(T t) {
            this.f242co.onComplete();
        }
    }
}
