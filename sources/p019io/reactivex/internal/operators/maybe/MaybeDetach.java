package p019io.reactivex.internal.operators.maybe;

import p019io.reactivex.MaybeObserver;
import p019io.reactivex.MaybeSource;
import p019io.reactivex.disposables.Disposable;
import p019io.reactivex.internal.disposables.DisposableHelper;

/* renamed from: io.reactivex.internal.operators.maybe.MaybeDetach */
public final class MaybeDetach<T> extends AbstractMaybeWithUpstream<T, T> {
    public MaybeDetach(MaybeSource<T> source) {
        super(source);
    }

    /* access modifiers changed from: protected */
    public void subscribeActual(MaybeObserver<? super T> observer) {
        this.source.subscribe(new DetachMaybeObserver(observer));
    }

    /* renamed from: io.reactivex.internal.operators.maybe.MaybeDetach$DetachMaybeObserver */
    static final class DetachMaybeObserver<T> implements MaybeObserver<T>, Disposable {
        MaybeObserver<? super T> actual;

        /* renamed from: d */
        Disposable f377d;

        DetachMaybeObserver(MaybeObserver<? super T> actual2) {
            this.actual = actual2;
        }

        public void dispose() {
            this.actual = null;
            this.f377d.dispose();
            this.f377d = DisposableHelper.DISPOSED;
        }

        public boolean isDisposed() {
            return this.f377d.isDisposed();
        }

        public void onSubscribe(Disposable d) {
            if (DisposableHelper.validate(this.f377d, d)) {
                this.f377d = d;
                this.actual.onSubscribe(this);
            }
        }

        public void onSuccess(T value) {
            this.f377d = DisposableHelper.DISPOSED;
            MaybeObserver<? super T> a = this.actual;
            if (a != null) {
                this.actual = null;
                a.onSuccess(value);
            }
        }

        public void onError(Throwable e) {
            this.f377d = DisposableHelper.DISPOSED;
            MaybeObserver<? super T> a = this.actual;
            if (a != null) {
                this.actual = null;
                a.onError(e);
            }
        }

        public void onComplete() {
            this.f377d = DisposableHelper.DISPOSED;
            MaybeObserver<? super T> a = this.actual;
            if (a != null) {
                this.actual = null;
                a.onComplete();
            }
        }
    }
}
