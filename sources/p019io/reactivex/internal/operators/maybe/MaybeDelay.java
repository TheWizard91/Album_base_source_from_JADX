package p019io.reactivex.internal.operators.maybe;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import p019io.reactivex.MaybeObserver;
import p019io.reactivex.MaybeSource;
import p019io.reactivex.Scheduler;
import p019io.reactivex.disposables.Disposable;
import p019io.reactivex.internal.disposables.DisposableHelper;

/* renamed from: io.reactivex.internal.operators.maybe.MaybeDelay */
public final class MaybeDelay<T> extends AbstractMaybeWithUpstream<T, T> {
    final long delay;
    final Scheduler scheduler;
    final TimeUnit unit;

    public MaybeDelay(MaybeSource<T> source, long delay2, TimeUnit unit2, Scheduler scheduler2) {
        super(source);
        this.delay = delay2;
        this.unit = unit2;
        this.scheduler = scheduler2;
    }

    /* access modifiers changed from: protected */
    public void subscribeActual(MaybeObserver<? super T> observer) {
        this.source.subscribe(new DelayMaybeObserver(observer, this.delay, this.unit, this.scheduler));
    }

    /* renamed from: io.reactivex.internal.operators.maybe.MaybeDelay$DelayMaybeObserver */
    static final class DelayMaybeObserver<T> extends AtomicReference<Disposable> implements MaybeObserver<T>, Disposable, Runnable {
        private static final long serialVersionUID = 5566860102500855068L;
        final MaybeObserver<? super T> actual;
        final long delay;
        Throwable error;
        final Scheduler scheduler;
        final TimeUnit unit;
        T value;

        DelayMaybeObserver(MaybeObserver<? super T> actual2, long delay2, TimeUnit unit2, Scheduler scheduler2) {
            this.actual = actual2;
            this.delay = delay2;
            this.unit = unit2;
            this.scheduler = scheduler2;
        }

        public void run() {
            Throwable ex = this.error;
            if (ex != null) {
                this.actual.onError(ex);
                return;
            }
            T v = this.value;
            if (v != null) {
                this.actual.onSuccess(v);
            } else {
                this.actual.onComplete();
            }
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
            schedule();
        }

        public void onError(Throwable e) {
            this.error = e;
            schedule();
        }

        public void onComplete() {
            schedule();
        }

        /* access modifiers changed from: package-private */
        public void schedule() {
            DisposableHelper.replace(this, this.scheduler.scheduleDirect(this, this.delay, this.unit));
        }
    }
}
