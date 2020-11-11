package p019io.reactivex.internal.operators.completable;

import java.util.concurrent.TimeUnit;
import p019io.reactivex.Completable;
import p019io.reactivex.CompletableObserver;
import p019io.reactivex.CompletableSource;
import p019io.reactivex.Scheduler;
import p019io.reactivex.disposables.CompositeDisposable;
import p019io.reactivex.disposables.Disposable;

/* renamed from: io.reactivex.internal.operators.completable.CompletableDelay */
public final class CompletableDelay extends Completable {
    final long delay;
    final boolean delayError;
    final Scheduler scheduler;
    final CompletableSource source;
    final TimeUnit unit;

    public CompletableDelay(CompletableSource source2, long delay2, TimeUnit unit2, Scheduler scheduler2, boolean delayError2) {
        this.source = source2;
        this.delay = delay2;
        this.unit = unit2;
        this.scheduler = scheduler2;
        this.delayError = delayError2;
    }

    /* access modifiers changed from: protected */
    public void subscribeActual(CompletableObserver s) {
        this.source.subscribe(new Delay(new CompositeDisposable(), s));
    }

    /* renamed from: io.reactivex.internal.operators.completable.CompletableDelay$Delay */
    final class Delay implements CompletableObserver {

        /* renamed from: s */
        final CompletableObserver f233s;
        private final CompositeDisposable set;

        Delay(CompositeDisposable set2, CompletableObserver s) {
            this.set = set2;
            this.f233s = s;
        }

        public void onComplete() {
            this.set.add(CompletableDelay.this.scheduler.scheduleDirect(new OnComplete(), CompletableDelay.this.delay, CompletableDelay.this.unit));
        }

        public void onError(Throwable e) {
            this.set.add(CompletableDelay.this.scheduler.scheduleDirect(new OnError(e), CompletableDelay.this.delayError ? CompletableDelay.this.delay : 0, CompletableDelay.this.unit));
        }

        public void onSubscribe(Disposable d) {
            this.set.add(d);
            this.f233s.onSubscribe(this.set);
        }

        /* renamed from: io.reactivex.internal.operators.completable.CompletableDelay$Delay$OnComplete */
        final class OnComplete implements Runnable {
            OnComplete() {
            }

            public void run() {
                Delay.this.f233s.onComplete();
            }
        }

        /* renamed from: io.reactivex.internal.operators.completable.CompletableDelay$Delay$OnError */
        final class OnError implements Runnable {

            /* renamed from: e */
            private final Throwable f234e;

            OnError(Throwable e) {
                this.f234e = e;
            }

            public void run() {
                Delay.this.f233s.onError(this.f234e);
            }
        }
    }
}
