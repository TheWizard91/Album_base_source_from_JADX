package p019io.reactivex.internal.operators.single;

import java.util.concurrent.atomic.AtomicReference;
import p019io.reactivex.Single;
import p019io.reactivex.SingleObserver;
import p019io.reactivex.SingleSource;
import p019io.reactivex.disposables.Disposable;
import p019io.reactivex.exceptions.Exceptions;
import p019io.reactivex.functions.Action;
import p019io.reactivex.internal.disposables.DisposableHelper;
import p019io.reactivex.plugins.RxJavaPlugins;

/* renamed from: io.reactivex.internal.operators.single.SingleDoOnDispose */
public final class SingleDoOnDispose<T> extends Single<T> {
    final Action onDispose;
    final SingleSource<T> source;

    public SingleDoOnDispose(SingleSource<T> source2, Action onDispose2) {
        this.source = source2;
        this.onDispose = onDispose2;
    }

    /* access modifiers changed from: protected */
    public void subscribeActual(SingleObserver<? super T> s) {
        this.source.subscribe(new DoOnDisposeObserver(s, this.onDispose));
    }

    /* renamed from: io.reactivex.internal.operators.single.SingleDoOnDispose$DoOnDisposeObserver */
    static final class DoOnDisposeObserver<T> extends AtomicReference<Action> implements SingleObserver<T>, Disposable {
        private static final long serialVersionUID = -8583764624474935784L;
        final SingleObserver<? super T> actual;

        /* renamed from: d */
        Disposable f543d;

        DoOnDisposeObserver(SingleObserver<? super T> actual2, Action onDispose) {
            this.actual = actual2;
            lazySet(onDispose);
        }

        public void dispose() {
            Action a = (Action) getAndSet((Object) null);
            if (a != null) {
                try {
                    a.run();
                } catch (Throwable ex) {
                    Exceptions.throwIfFatal(ex);
                    RxJavaPlugins.onError(ex);
                }
                this.f543d.dispose();
            }
        }

        public boolean isDisposed() {
            return this.f543d.isDisposed();
        }

        public void onSubscribe(Disposable d) {
            if (DisposableHelper.validate(this.f543d, d)) {
                this.f543d = d;
                this.actual.onSubscribe(this);
            }
        }

        public void onSuccess(T value) {
            this.actual.onSuccess(value);
        }

        public void onError(Throwable e) {
            this.actual.onError(e);
        }
    }
}
