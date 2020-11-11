package p019io.reactivex.internal.subscribers;

import org.reactivestreams.Subscription;
import p019io.reactivex.exceptions.Exceptions;
import p019io.reactivex.internal.fuseable.ConditionalSubscriber;
import p019io.reactivex.internal.fuseable.QueueSubscription;
import p019io.reactivex.internal.subscriptions.SubscriptionHelper;
import p019io.reactivex.plugins.RxJavaPlugins;

/* renamed from: io.reactivex.internal.subscribers.BasicFuseableConditionalSubscriber */
public abstract class BasicFuseableConditionalSubscriber<T, R> implements ConditionalSubscriber<T>, QueueSubscription<R> {
    protected final ConditionalSubscriber<? super R> actual;
    protected boolean done;

    /* renamed from: qs */
    protected QueueSubscription<T> f562qs;

    /* renamed from: s */
    protected Subscription f563s;
    protected int sourceMode;

    public BasicFuseableConditionalSubscriber(ConditionalSubscriber<? super R> actual2) {
        this.actual = actual2;
    }

    public final void onSubscribe(Subscription s) {
        if (SubscriptionHelper.validate(this.f563s, s)) {
            this.f563s = s;
            if (s instanceof QueueSubscription) {
                this.f562qs = (QueueSubscription) s;
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
        this.f563s.cancel();
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
        QueueSubscription<T> qs = this.f562qs;
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
        this.f563s.request(n);
    }

    public void cancel() {
        this.f563s.cancel();
    }

    public boolean isEmpty() {
        return this.f562qs.isEmpty();
    }

    public void clear() {
        this.f562qs.clear();
    }

    public final boolean offer(R r) {
        throw new UnsupportedOperationException("Should not be called!");
    }

    public final boolean offer(R r, R r2) {
        throw new UnsupportedOperationException("Should not be called!");
    }
}
