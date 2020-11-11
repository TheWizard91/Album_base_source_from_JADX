package p019io.reactivex.internal.operators.flowable;

import java.util.concurrent.TimeUnit;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import p019io.reactivex.Flowable;
import p019io.reactivex.FlowableSubscriber;
import p019io.reactivex.Scheduler;
import p019io.reactivex.internal.subscriptions.SubscriptionHelper;
import p019io.reactivex.schedulers.Timed;

/* renamed from: io.reactivex.internal.operators.flowable.FlowableTimeInterval */
public final class FlowableTimeInterval<T> extends AbstractFlowableWithUpstream<T, Timed<T>> {
    final Scheduler scheduler;
    final TimeUnit unit;

    public FlowableTimeInterval(Flowable<T> source, TimeUnit unit2, Scheduler scheduler2) {
        super(source);
        this.scheduler = scheduler2;
        this.unit = unit2;
    }

    /* access modifiers changed from: protected */
    public void subscribeActual(Subscriber<? super Timed<T>> s) {
        this.source.subscribe(new TimeIntervalSubscriber(s, this.unit, this.scheduler));
    }

    /* renamed from: io.reactivex.internal.operators.flowable.FlowableTimeInterval$TimeIntervalSubscriber */
    static final class TimeIntervalSubscriber<T> implements FlowableSubscriber<T>, Subscription {
        final Subscriber<? super Timed<T>> actual;
        long lastTime;

        /* renamed from: s */
        Subscription f354s;
        final Scheduler scheduler;
        final TimeUnit unit;

        TimeIntervalSubscriber(Subscriber<? super Timed<T>> actual2, TimeUnit unit2, Scheduler scheduler2) {
            this.actual = actual2;
            this.scheduler = scheduler2;
            this.unit = unit2;
        }

        public void onSubscribe(Subscription s) {
            if (SubscriptionHelper.validate(this.f354s, s)) {
                this.lastTime = this.scheduler.now(this.unit);
                this.f354s = s;
                this.actual.onSubscribe(this);
            }
        }

        public void onNext(T t) {
            long now = this.scheduler.now(this.unit);
            long last = this.lastTime;
            this.lastTime = now;
            this.actual.onNext(new Timed(t, now - last, this.unit));
        }

        public void onError(Throwable t) {
            this.actual.onError(t);
        }

        public void onComplete() {
            this.actual.onComplete();
        }

        public void request(long n) {
            this.f354s.request(n);
        }

        public void cancel() {
            this.f354s.cancel();
        }
    }
}
