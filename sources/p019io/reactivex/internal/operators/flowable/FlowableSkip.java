package p019io.reactivex.internal.operators.flowable;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import p019io.reactivex.Flowable;
import p019io.reactivex.FlowableSubscriber;
import p019io.reactivex.internal.subscriptions.SubscriptionHelper;

/* renamed from: io.reactivex.internal.operators.flowable.FlowableSkip */
public final class FlowableSkip<T> extends AbstractFlowableWithUpstream<T, T> {

    /* renamed from: n */
    final long f337n;

    public FlowableSkip(Flowable<T> source, long n) {
        super(source);
        this.f337n = n;
    }

    /* access modifiers changed from: protected */
    public void subscribeActual(Subscriber<? super T> s) {
        this.source.subscribe(new SkipSubscriber(s, this.f337n));
    }

    /* renamed from: io.reactivex.internal.operators.flowable.FlowableSkip$SkipSubscriber */
    static final class SkipSubscriber<T> implements FlowableSubscriber<T>, Subscription {
        final Subscriber<? super T> actual;
        long remaining;

        /* renamed from: s */
        Subscription f338s;

        SkipSubscriber(Subscriber<? super T> actual2, long n) {
            this.actual = actual2;
            this.remaining = n;
        }

        public void onSubscribe(Subscription s) {
            if (SubscriptionHelper.validate(this.f338s, s)) {
                long n = this.remaining;
                this.f338s = s;
                this.actual.onSubscribe(this);
                s.request(n);
            }
        }

        public void onNext(T t) {
            long j = this.remaining;
            if (j != 0) {
                this.remaining = j - 1;
            } else {
                this.actual.onNext(t);
            }
        }

        public void onError(Throwable t) {
            this.actual.onError(t);
        }

        public void onComplete() {
            this.actual.onComplete();
        }

        public void request(long n) {
            this.f338s.request(n);
        }

        public void cancel() {
            this.f338s.cancel();
        }
    }
}
