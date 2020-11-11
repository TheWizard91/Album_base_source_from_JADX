package p019io.reactivex.internal.operators.flowable;

import java.util.concurrent.Callable;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import p019io.reactivex.Flowable;
import p019io.reactivex.exceptions.Exceptions;
import p019io.reactivex.internal.functions.ObjectHelper;
import p019io.reactivex.internal.subscriptions.EmptySubscription;

/* renamed from: io.reactivex.internal.operators.flowable.FlowableDefer */
public final class FlowableDefer<T> extends Flowable<T> {
    final Callable<? extends Publisher<? extends T>> supplier;

    public FlowableDefer(Callable<? extends Publisher<? extends T>> supplier2) {
        this.supplier = supplier2;
    }

    public void subscribeActual(Subscriber<? super T> s) {
        try {
            ((Publisher) ObjectHelper.requireNonNull(this.supplier.call(), "The publisher supplied is null")).subscribe(s);
        } catch (Throwable t) {
            Exceptions.throwIfFatal(t);
            EmptySubscription.error(t, s);
        }
    }
}
