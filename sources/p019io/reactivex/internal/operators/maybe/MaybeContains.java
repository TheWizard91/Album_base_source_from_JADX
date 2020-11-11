package p019io.reactivex.internal.operators.maybe;

import p019io.reactivex.MaybeObserver;
import p019io.reactivex.MaybeSource;
import p019io.reactivex.Single;
import p019io.reactivex.SingleObserver;
import p019io.reactivex.disposables.Disposable;
import p019io.reactivex.internal.disposables.DisposableHelper;
import p019io.reactivex.internal.functions.ObjectHelper;
import p019io.reactivex.internal.fuseable.HasUpstreamMaybeSource;

/* renamed from: io.reactivex.internal.operators.maybe.MaybeContains */
public final class MaybeContains<T> extends Single<Boolean> implements HasUpstreamMaybeSource<T> {
    final MaybeSource<T> source;
    final Object value;

    public MaybeContains(MaybeSource<T> source2, Object value2) {
        this.source = source2;
        this.value = value2;
    }

    public MaybeSource<T> source() {
        return this.source;
    }

    /* access modifiers changed from: protected */
    public void subscribeActual(SingleObserver<? super Boolean> observer) {
        this.source.subscribe(new ContainsMaybeObserver(observer, this.value));
    }

    /* renamed from: io.reactivex.internal.operators.maybe.MaybeContains$ContainsMaybeObserver */
    static final class ContainsMaybeObserver implements MaybeObserver<Object>, Disposable {
        final SingleObserver<? super Boolean> actual;

        /* renamed from: d */
        Disposable f373d;
        final Object value;

        ContainsMaybeObserver(SingleObserver<? super Boolean> actual2, Object value2) {
            this.actual = actual2;
            this.value = value2;
        }

        public void dispose() {
            this.f373d.dispose();
            this.f373d = DisposableHelper.DISPOSED;
        }

        public boolean isDisposed() {
            return this.f373d.isDisposed();
        }

        public void onSubscribe(Disposable d) {
            if (DisposableHelper.validate(this.f373d, d)) {
                this.f373d = d;
                this.actual.onSubscribe(this);
            }
        }

        public void onSuccess(Object value2) {
            this.f373d = DisposableHelper.DISPOSED;
            this.actual.onSuccess(Boolean.valueOf(ObjectHelper.equals(value2, this.value)));
        }

        public void onError(Throwable e) {
            this.f373d = DisposableHelper.DISPOSED;
            this.actual.onError(e);
        }

        public void onComplete() {
            this.f373d = DisposableHelper.DISPOSED;
            this.actual.onSuccess(false);
        }
    }
}
