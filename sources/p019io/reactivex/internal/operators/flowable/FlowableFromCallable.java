package p019io.reactivex.internal.operators.flowable;

import java.util.concurrent.Callable;
import org.reactivestreams.Subscriber;
import p019io.reactivex.Flowable;
import p019io.reactivex.exceptions.Exceptions;
import p019io.reactivex.internal.functions.ObjectHelper;
import p019io.reactivex.internal.subscriptions.DeferredScalarSubscription;

/* renamed from: io.reactivex.internal.operators.flowable.FlowableFromCallable */
public final class FlowableFromCallable<T> extends Flowable<T> implements Callable<T> {
    final Callable<? extends T> callable;

    public FlowableFromCallable(Callable<? extends T> callable2) {
        this.callable = callable2;
    }

    public void subscribeActual(Subscriber<? super T> s) {
        DeferredScalarSubscription<T> deferred = new DeferredScalarSubscription<>(s);
        s.onSubscribe(deferred);
        try {
            deferred.complete(ObjectHelper.requireNonNull(this.callable.call(), "The callable returned a null value"));
        } catch (Throwable ex) {
            Exceptions.throwIfFatal(ex);
            s.onError(ex);
        }
    }

    public T call() throws Exception {
        return ObjectHelper.requireNonNull(this.callable.call(), "The callable returned a null value");
    }
}
