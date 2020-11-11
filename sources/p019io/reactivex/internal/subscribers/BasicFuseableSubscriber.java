package p019io.reactivex.internal.subscribers;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import p019io.reactivex.FlowableSubscriber;
import p019io.reactivex.exceptions.Exceptions;
import p019io.reactivex.internal.fuseable.QueueSubscription;
import p019io.reactivex.internal.subscriptions.SubscriptionHelper;
import p019io.reactivex.plugins.RxJavaPlugins;

/* renamed from: io.reactivex.internal.subscribers.BasicFuseableSubscriber */
public abstract class BasicFuseableSubscriber<T, R> implements FlowableSubscriber<T>, QueueSubscription<R> {
    protected final Subscriber<? super R> actual;
    protected boolean done;

    /* renamed from: qs */
    protected QueueSubscription<T> f564qs;

    /* renamed from: s */
    protected Subscription f565s;
    protected int sourceMode;

    public BasicFuseableSubscriber(Subscriber<? super R> actual2) {
        this.actual = actual2;
    }

    public final void onSubscribe(Subscription s) {
        if (SubscriptionHelper.validate(this.f565s, s)) {
            this.f565s = s;
            if (s instanceof QueueSubscription) {
                this.f564qs = (QueueSubscription) s;
            }
            if (beforeDownstream()) {
                this.actual.onSubscribe(this);
                afterDownstream();
            }
        }
    }

    /* access modifiers changed from: protected */
    public boolean beforeDownstream() {
        return true;
    }

    /* access modifiers changed from: protected */
    public void afterDownstream() {
    }

    public void onError(Throwable t) {
        if (this.done) {
            RxJavaPlugins.onError(t);
            return;
        }
        this.done = true;
        this.actual.onError(t);
    }

    /* access modifiers changed from: protected */
    public final void fail(Throwable t) {
        Exceptions.throwIfFatal(t);
        this.f565s.cancel();
        onError(t);
    }

    public void onComplete() {
        if (!this.done) {
            this.done = true;
            this.actual.onComplete();
        }
    }

    /* access modifiers changed from: protected */
    public final int transitiveBoundaryFusion(int mode) {
        QueueSubscription<T> qs = this.f564qs;
        if (qs == null || (mode & 4) != 0) {
            return 0;
        }
        int m = qs.requestFusion(mode);
        if (m != 0) {
            this.sourceMode = m;
        }
        return m;
    }

    public void request(long n) {
        this.f565s.request(n);
    }

    public void cancel() {
        this.f565s.cancel();
    }

    public boolean isEmpty() {
        return this.f564qs.isEmpty();
    }

    public void clear() {
        this.f564qs.clear();
    }

    public final boolean offer(R r) {
        throw new UnsupportedOperationException("Should not be called!");
    }

    public final boolean offer(R r, R r2) {
        throw new UnsupportedOperationException("Should not be called!");
    }
}
