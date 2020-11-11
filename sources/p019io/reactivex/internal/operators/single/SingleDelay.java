package p019io.reactivex.internal.operators.single;

import java.util.concurrent.TimeUnit;
import p019io.reactivex.Scheduler;
import p019io.reactivex.Single;
import p019io.reactivex.SingleObserver;
import p019io.reactivex.SingleSource;
import p019io.reactivex.disposables.Disposable;
import p019io.reactivex.internal.disposables.SequentialDisposable;

/* renamed from: io.reactivex.internal.operators.single.SingleDelay */
public final class SingleDelay<T> extends Single<T> {
    final boolean delayError;
    final Scheduler scheduler;
    final SingleSource<? extends T> source;
    final long time;
    final TimeUnit unit;

    public SingleDelay(SingleSource<? extends T> source2, long time2, TimeUnit unit2, Scheduler scheduler2, boolean delayError2) {
        this.source = source2;
        this.time = time2;
        this.unit = unit2;
        this.scheduler = scheduler2;
        this.delayError = delayError2;
    }

    /* access modifiers changed from: protected */
    public void subscribeActual(SingleObserver<? super T> s) {
        SequentialDisposable sd = new SequentialDisposable();
        s.onSubscribe(sd);
        this.source.subscribe(new Delay(sd, s));
    }

    /* renamed from: io.reactivex.internal.operators.single.SingleDelay$Delay */
    final class Delay implements SingleObserver<T> {

        /* renamed from: s */
        final SingleObserver<? super T> f535s;

        /* renamed from: sd */
        private final SequentialDisposable f536sd;

        Delay(SequentialDisposable sd, SingleObserver<? super T> s) {
            this.f536sd = sd;
            this.f535s = s;
        }

        public void onSubscribe(Disposable d) {
            this.f536sd.replace(d);
        }

        public void onSuccess(T value) {
            this.f536sd.replace(SingleDelay.this.scheduler.scheduleDirect(new OnSuccess(value), SingleDelay.this.time, SingleDelay.this.unit));
        }

        public void onError(Throwable e) {
            this.f536sd.replace(SingleDelay.this.scheduler.scheduleDirect(new OnError(e), SingleDelay.this.delayError ? SingleDelay.this.time : 0, SingleDelay.this.unit));
        }

        /* renamed from: io.reactivex.internal.operators.single.SingleDelay$Delay$OnSuccess */
        final class OnSuccess implements Runnable {
            private final T value;

            OnSuccess(T value2) {
                this.value = value2;
            }

            public void run() {
                Delay.this.f535s.onSuccess(this.value);
            }
        }

        /* renamed from: io.reactivex.internal.operators.single.SingleDelay$Delay$OnError */
        final class OnError implements Runnable {

            /* renamed from: e */
            private final Throwable f537e;

            OnError(Throwable e) {
                this.f537e = e;
            }

            public void run() {
                Delay.this.f535s.onError(this.f537e);
            }
        }
    }
}
