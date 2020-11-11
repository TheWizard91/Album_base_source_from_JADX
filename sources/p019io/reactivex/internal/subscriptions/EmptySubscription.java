package p019io.reactivex.internal.subscriptions;

import org.reactivestreams.Subscriber;
import p019io.reactivex.internal.fuseable.QueueSubscription;

/* renamed from: io.reactivex.internal.subscriptions.EmptySubscription */
public enum EmptySubscription implements QueueSubscription<Object> {
    INSTANCE;

    public void request(long n) {
        SubscriptionHelper.validate(n);
    }

    public void cancel() {
    }

    public String toString() {
        return "EmptySubscription";
    }

    public static void error(Throwable e, Subscriber<?> s) {
        s.onSubscribe(INSTANCE);
        s.onError(e);
    }

    public static void complete(Subscriber<?> s) {
        s.onSubscribe(INSTANCE);
        s.onComplete();
    }

    public Object poll() {
        return null;
    }

    public boolean isEmpty() {
        return true;
    }

    public void clear() {
    }

    public int requestFusion(int mode) {
        return mode & 2;
    }

    public boolean offer(Object value) {
        throw new UnsupportedOperationException("Should not be called!");
    }

    public boolean offer(Object v1, Object v2) {
        throw new UnsupportedOperationException("Should not be called!");
    }
}
