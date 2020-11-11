package p019io.reactivex.internal.operators.single;

import p019io.reactivex.Single;
import p019io.reactivex.SingleObserver;
import p019io.reactivex.SingleSource;
import p019io.reactivex.disposables.Disposable;
import p019io.reactivex.exceptions.Exceptions;
import p019io.reactivex.functions.Action;
import p019io.reactivex.internal.disposables.DisposableHelper;
import p019io.reactivex.plugins.RxJavaPlugins;

/* renamed from: io.reactivex.internal.operators.single.SingleDoAfterTerminate */
public final class SingleDoAfterTerminate<T> extends Single<T> {
    final Action onAfterTerminate;
    final SingleSource<T> source;

    public SingleDoAfterTerminate(SingleSource<T> source2, Action onAfterTerminate2) {
        this.source = source2;
        this.onAfterTerminate = onAfterTerminate2;
    }

    /* access modifiers changed from: protected */
    public void subscribeActual(SingleObserver<? super T> s) {
        this.source.subscribe(new DoAfterTerminateObserver(s, this.onAfterTerminate));
    }

    /* renamed from: io.reactivex.internal.operators.single.SingleDoAfterTerminate$DoAfterTerminateObserver */
    static final class DoAfterTerminateObserver<T> implements SingleObserver<T>, Disposable {
        final SingleObserver<? super T> actual;

        /* renamed from: d */
        Disposable f541d;
        final Action onAfterTerminate;

        DoAfterTerminateObserver(SingleObserver<? super T> actual2, Action onAfterTerminate2) {
            this.actual = actual2;
            this.onAfterTerminate = onAfterTerminate2;
        }

        public void onSubscribe(Disposable d) {
            if (DisposableHelper.validate(this.f541d, d)) {
                this.f541d = d;
                this.actual.onSubscribe(this);
            }
        }

        public void onSuccess(T t) {
            this.actual.onSuccess(t);
            onAfterTerminate();
        }

        public void onError(Throwable e) {
            this.actual.onError(e);
            onAfterTerminate();
        }

        public void dispose() {
            this.f541d.dispose();
        }

        public boolean isDisposed() {
            return this.f541d.isDisposed();
        }

        private void onAfterTerminate() {
            try {
                this.onAfterTerminate.run();
            } catch (Throwable ex) {
                Exceptions.throwIfFatal(ex);
                RxJavaPlugins.onError(ex);
            }
        }
    }
}
