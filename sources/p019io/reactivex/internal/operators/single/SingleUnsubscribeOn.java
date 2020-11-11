package p019io.reactivex.internal.operators.single;

import java.util.concurrent.atomic.AtomicReference;
import p019io.reactivex.Scheduler;
import p019io.reactivex.Single;
import p019io.reactivex.SingleObserver;
import p019io.reactivex.SingleSource;
import p019io.reactivex.disposables.Disposable;
import p019io.reactivex.internal.disposables.DisposableHelper;

/* renamed from: io.reactivex.internal.operators.single.SingleUnsubscribeOn */
public final class SingleUnsubscribeOn<T> extends Single<T> {
    final Scheduler scheduler;
    final SingleSource<T> source;

    public SingleUnsubscribeOn(SingleSource<T> source2, Scheduler scheduler2) {
        this.source = source2;
        this.scheduler = scheduler2;
    }

    /* access modifiers changed from: protected */
    public void subscribeActual(SingleObserver<? super T> observer) {
        this.source.subscribe(new UnsubscribeOnSingleObserver(observer, this.scheduler));
    }

    /* renamed from: io.reactivex.internal.operators.single.SingleUnsubscribeOn$UnsubscribeOnSingleObserver */
    static final class UnsubscribeOnSingleObserver<T> extends AtomicReference<Disposable> implements SingleObserver<T>, Disposable, Runnable {
        private static final long serialVersionUID = 3256698449646456986L;
        final SingleObserver<? super T> actual;

        /* renamed from: ds */
        Disposable f557ds;
        final Scheduler scheduler;

        UnsubscribeOnSingleObserver(SingleObserver<? super T> actual2, Scheduler scheduler2) {
            this.actual = actual2;
            this.scheduler = scheduler2;
        }

        public void dispose() {
            Disposable d = (Disposable) getAndSet(DisposableHelper.DISPOSED);
            if (d != DisposableHelper.DISPOSED) {
                this.f557ds = d;
                this.scheduler.scheduleDirect(this);
            }
        }

        public void run() {
            this.f557ds.dispose();
        }

        public boolean isDisposed() {
            return DisposableHelper.isDisposed((Disposable) get());
        }

        public void onSubscribe(Disposable d) {
            if (DisposableHelper.setOnce(this, d)) {
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
