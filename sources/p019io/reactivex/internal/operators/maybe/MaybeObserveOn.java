package p019io.reactivex.internal.operators.maybe;

import java.util.concurrent.atomic.AtomicReference;
import p019io.reactivex.MaybeObserver;
import p019io.reactivex.MaybeSource;
import p019io.reactivex.Scheduler;
import p019io.reactivex.disposables.Disposable;
import p019io.reactivex.internal.disposables.DisposableHelper;

/* renamed from: io.reactivex.internal.operators.maybe.MaybeObserveOn */
public final class MaybeObserveOn<T> extends AbstractMaybeWithUpstream<T, T> {
    final Scheduler scheduler;

    public MaybeObserveOn(MaybeSource<T> source, Scheduler scheduler2) {
        super(source);
        this.scheduler = scheduler2;
    }

    /* access modifiers changed from: protected */
    public void subscribeActual(MaybeObserver<? super T> observer) {
        this.source.subscribe(new ObserveOnMaybeObserver(observer, this.scheduler));
    }

    /* renamed from: io.reactivex.internal.operators.maybe.MaybeObserveOn$ObserveOnMaybeObserver */
    static final class ObserveOnMaybeObserver<T> extends AtomicReference<Disposable> implements MaybeObserver<T>, Disposable, Runnable {
        private static final long serialVersionUID = 8571289934935992137L;
        final MaybeObserver<? super T> actual;
        Throwable error;
        final Scheduler scheduler;
        T value;

        ObserveOnMaybeObserver(MaybeObserver<? super T> actual2, Scheduler scheduler2) {
            this.actual = actual2;
            this.scheduler = scheduler2;
        }

        public void dispose() {
            DisposableHelper.dispose(this);
        }

        public boolean isDisposed() {
            return DisposableHelper.isDisposed((Disposable) get());
        }

        public void onSubscribe(Disposable d) {
            if (DisposableHelper.setOnce(this, d)) {
                this.actual.onSubscribe(this);
            }
        }

        public void onSuccess(T value2) {
            this.value = value2;
            DisposableHelper.replace(this, this.scheduler.scheduleDirect(this));
        }

        public void onError(Throwable e) {
            this.error = e;
            DisposableHelper.replace(this, this.scheduler.scheduleDirect(this));
        }

        public void onComplete() {
            DisposableHelper.replace(this, this.scheduler.scheduleDirect(this));
        }

        public void run() {
            Throwable ex = this.error;
            if (ex != null) {
                this.error = null;
                this.actual.onError(ex);
                return;
            }
            T v = this.value;
            if (v != null) {
                this.value = null;
                this.actual.onSuccess(v);
                return;
            }
            this.actual.onComplete();
        }
    }
}
