package p019io.reactivex.internal.operators.single;

import p019io.reactivex.Single;
import p019io.reactivex.SingleObserver;
import p019io.reactivex.SingleSource;
import p019io.reactivex.disposables.Disposable;
import p019io.reactivex.internal.disposables.DisposableHelper;

/* renamed from: io.reactivex.internal.operators.single.SingleDetach */
public final class SingleDetach<T> extends Single<T> {
    final SingleSource<T> source;

    public SingleDetach(SingleSource<T> source2) {
        this.source = source2;
    }

    /* access modifiers changed from: protected */
    public void subscribeActual(SingleObserver<? super T> observer) {
        this.source.subscribe(new DetachSingleObserver(observer));
    }

    /* renamed from: io.reactivex.internal.operators.single.SingleDetach$DetachSingleObserver */
    static final class DetachSingleObserver<T> implements SingleObserver<T>, Disposable {
        SingleObserver<? super T> actual;

        /* renamed from: d */
        Disposable f539d;

        DetachSingleObserver(SingleObserver<? super T> actual2) {
            this.actual = actual2;
        }

        public void dispose() {
            this.actual = null;
            this.f539d.dispose();
            this.f539d = DisposableHelper.DISPOSED;
        }

        public boolean isDisposed() {
            return this.f539d.isDisposed();
        }

        public void onSubscribe(Disposable d) {
            if (DisposableHelper.validate(this.f539d, d)) {
                this.f539d = d;
                this.actual.onSubscribe(this);
            }
        }

        public void onSuccess(T value) {
            this.f539d = DisposableHelper.DISPOSED;
            SingleObserver<? super T> a = this.actual;
            if (a != null) {
                this.actual = null;
                a.onSuccess(value);
            }
        }

        public void onError(Throwable e) {
            this.f539d = DisposableHelper.DISPOSED;
            SingleObserver<? super T> a = this.actual;
            if (a != null) {
                this.actual = null;
                a.onError(e);
            }
        }
    }
}
