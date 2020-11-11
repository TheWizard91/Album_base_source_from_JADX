package p019io.reactivex.internal.subscribers;

import java.util.concurrent.atomic.AtomicLong;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import p019io.reactivex.FlowableSubscriber;
import p019io.reactivex.internal.subscriptions.SubscriptionHelper;
import p019io.reactivex.internal.util.BackpressureHelper;

/* renamed from: io.reactivex.internal.subscribers.SinglePostCompleteSubscriber */
public abstract class SinglePostCompleteSubscriber<T, R> extends AtomicLong implements FlowableSubscriber<T>, Subscription {
    static final long COMPLETE_MASK = Long.MIN_VALUE;
    static final long REQUEST_MASK = Long.MAX_VALUE;
    private static final long serialVersionUID = 7917814472626990048L;
    protected final Subscriber<? super R> actual;
    protected long produced;

    /* renamed from: s */
    protected Subscription f587s;
    protected R value;

    public SinglePostCompleteSubscriber(Subscriber<? super R> actual2) {
        this.actual = actual2;
    }

    public void onSubscribe(Subscription s) {
        if (SubscriptionHelper.validate(this.f587s, s)) {
            this.f587s = s;
            this.actual.onSubscribe(this);
        }
    }

    /* access modifiers changed from: protected */
    public final void complete(R n) {
        long p = this.produced;
        if (p != 0) {
            BackpressureHelper.produced(this, p);
        }
        while (true) {
            long r = get();
            if ((r & Long.MIN_VALUE) != 0) {
                onDrop(n);
                return;
            } else if ((Long.MAX_VALUE & r) != 0) {
                lazySet(-9223372036854775807L);
                this.actual.onNext(n);
                this.actual.onComplete();
                return;
            } else {
                this.value = n;
                if (!compareAndSet(0, Long.MIN_VALUE)) {
                    this.value = null;
                } else {
                    return;
                }
            }
        }
    }

    /* access modifiers changed from: protected */
    public void onDrop(R r) {
    }

    public final void request(long n) {
        long r;
        if (SubscriptionHelper.validate(n)) {
            do {
                r = get();
                if ((r & Long.MIN_VALUE) != 0) {
                    if (compareAndSet(Long.MIN_VALUE, -9223372036854775807L)) {
                        this.actual.onNext(this.value);
                        this.actual.onComplete();
                        return;
                    }
                    return;
                }
            } while (!compareAndSet(r, BackpressureHelper.addCap(r, n)));
            this.f587s.request(n);
        }
    }

    public void cancel() {
        this.f587s.cancel();
    }
}
