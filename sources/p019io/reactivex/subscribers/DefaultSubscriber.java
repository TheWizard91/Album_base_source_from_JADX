package p019io.reactivex.subscribers;

import org.reactivestreams.Subscription;
import p019io.reactivex.FlowableSubscriber;
import p019io.reactivex.internal.subscriptions.SubscriptionHelper;
import p019io.reactivex.internal.util.EndConsumerHelper;

/* renamed from: io.reactivex.subscribers.DefaultSubscriber */
public abstract class DefaultSubscriber<T> implements FlowableSubscriber<T> {

    /* renamed from: s */
    private Subscription f605s;

    public final void onSubscribe(Subscription s) {
        if (EndConsumerHelper.validate(this.f605s, s, getClass())) {
            this.f605s = s;
            onStart();
        }
    }

    /* access modifiers changed from: protected */
    public final void request(long n) {
        Subscription s = this.f605s;
        if (s != null) {
            s.request(n);
        }
    }

    /* access modifiers changed from: protected */
    public final void cancel() {
        Subscription s = this.f605s;
        this.f605s = SubscriptionHelper.CANCELLED;
        s.cancel();
    }

    /* access modifiers changed from: protected */
    public void onStart() {
        request(Long.MAX_VALUE);
    }
}
