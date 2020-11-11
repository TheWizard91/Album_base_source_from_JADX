package p019io.reactivex.internal.operators.flowable;

import java.util.concurrent.TimeUnit;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import p019io.reactivex.Flowable;
import p019io.reactivex.FlowableSubscriber;
import p019io.reactivex.Scheduler;
import p019io.reactivex.internal.subscriptions.SubscriptionHelper;
import p019io.reactivex.subscribers.SerializedSubscriber;

/* renamed from: io.reactivex.internal.operators.flowable.FlowableDelay */
public final class FlowableDelay<T> extends AbstractFlowableWithUpstream<T, T> {
    final long delay;
    final boolean delayError;
    final Scheduler scheduler;
    final TimeUnit unit;

    public FlowableDelay(Flowable<T> source, long delay2, TimeUnit unit2, Scheduler scheduler2, boolean delayError2) {
        super(source);
        this.delay = delay2;
        this.unit = unit2;
        this.scheduler = scheduler2;
        this.delayError = delayError2;
    }

    /* access modifiers changed from: protected */
    public void subscribeActual(Subscriber<? super T> t) {
        Subscriber<? super T> s;
        if (this.delayError) {
            s = t;
        } else {
            s = new SerializedSubscriber<>(t);
        }
        this.source.subscribe(new DelaySubscriber(s, this.delay, this.unit, this.scheduler.createWorker(), this.delayError));
    }

    /* renamed from: io.reactivex.internal.operators.flowable.FlowableDelay$DelaySubscriber */
    static final class DelaySubscriber<T> implements FlowableSubscriber<T>, Subscription {
        final Subscriber<? super T> actual;
        final long delay;
        final boolean delayError;

        /* renamed from: s */
        Subscription f276s;
        final TimeUnit unit;

        /* renamed from: w */
        final Scheduler.Worker f277w;

        DelaySubscriber(Subscriber<? super T> actual2, long delay2, TimeUnit unit2, Scheduler.Worker w, boolean delayError2) {
            this.actual = actual2;
            this.delay = delay2;
            this.unit = unit2;
            this.f277w = w;
            this.delayError = delayError2;
        }

        public void onSubscribe(Subscription s) {
            if (SubscriptionHelper.validate(this.f276s, s)) {
                this.f276s = s;
                this.actual.onSubscribe(this);
            }
        }

        public void onNext(T t) {
            this.f277w.schedule(new OnNext(t), this.delay, this.unit);
        }

        public void onError(Throwable t) {
            this.f277w.schedule(new OnError(t), this.delayError ? this.delay : 0, this.unit);
        }

        public void onComplete() {
            this.f277w.schedule(new OnComplete(), this.delay, this.unit);
        }

        public void request(long n) {
            this.f276s.request(n);
        }

        public void cancel() {
            this.f276s.cancel();
            this.f277w.dispose();
        }

        /* renamed from: io.reactivex.internal.operators.flowable.FlowableDelay$DelaySubscriber$OnNext */
        final class OnNext implements Runnable {

            /* renamed from: t */
            private final T f279t;

            OnNext(T t) {
                this.f279t = t;
            }

            public void run() {
                DelaySubscriber.this.actual.onNext(this.f279t);
            }
        }

        /* renamed from: io.reactivex.internal.operators.flowable.FlowableDelay$DelaySubscriber$OnError */
        final class OnError implements Runnable {

            /* renamed from: t */
            private final Throwable f278t;

            OnError(Throwable t) {
                this.f278t = t;
            }

            public void run() {
                try {
                    DelaySubscriber.this.actual.onError(this.f278t);
                } finally {
                    DelaySubscriber.this.f277w.dispose();
                }
            }
        }

        /* renamed from: io.reactivex.internal.operators.flowable.FlowableDelay$DelaySubscriber$OnComplete */
        final class OnComplete implements Runnable {
            OnComplete() {
            }

            public void run() {
                try {
                    DelaySubscriber.this.actual.onComplete();
                } finally {
                    DelaySubscriber.this.f277w.dispose();
                }
            }
        }
    }
}
