package p019io.reactivex.internal.operators.flowable;

import org.reactivestreams.Subscriber;
import p019io.reactivex.Flowable;
import p019io.reactivex.internal.subscriptions.EmptySubscription;

/* renamed from: io.reactivex.internal.operators.flowable.FlowableNever */
public final class FlowableNever extends Flowable<Object> {
    public static final Flowable<Object> INSTANCE = new FlowableNever();

    private FlowableNever() {
    }

    public void subscribeActual(Subscriber<? super Object> s) {
        s.onSubscribe(EmptySubscription.INSTANCE);
    }
}
