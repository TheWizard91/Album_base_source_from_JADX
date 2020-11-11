package p019io.reactivex.internal.operators.flowable;

import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import p019io.reactivex.Flowable;
import p019io.reactivex.exceptions.Exceptions;
import p019io.reactivex.functions.Function;
import p019io.reactivex.internal.functions.ObjectHelper;
import p019io.reactivex.internal.operators.flowable.FlowableRepeatWhen;
import p019io.reactivex.internal.subscriptions.EmptySubscription;
import p019io.reactivex.processors.FlowableProcessor;
import p019io.reactivex.processors.UnicastProcessor;
import p019io.reactivex.subscribers.SerializedSubscriber;

/* renamed from: io.reactivex.internal.operators.flowable.FlowableRetryWhen */
public final class FlowableRetryWhen<T> extends AbstractFlowableWithUpstream<T, T> {
    final Function<? super Flowable<Throwable>, ? extends Publisher<?>> handler;

    public FlowableRetryWhen(Flowable<T> source, Function<? super Flowable<Throwable>, ? extends Publisher<?>> handler2) {
        super(source);
        this.handler = handler2;
    }

    public void subscribeActual(Subscriber<? super T> s) {
        SerializedSubscriber<T> z = new SerializedSubscriber<>(s);
        FlowableProcessor<Throwable> processor = UnicastProcessor.create(8).toSerialized();
        try {
            Publisher<?> when = (Publisher) ObjectHelper.requireNonNull(this.handler.apply(processor), "handler returned a null Publisher");
            FlowableRepeatWhen.WhenReceiver<T, Throwable> receiver = new FlowableRepeatWhen.WhenReceiver<>(this.source);
            RetryWhenSubscriber<T> subscriber = new RetryWhenSubscriber<>(z, processor, receiver);
            receiver.subscriber = subscriber;
            s.onSubscribe(subscriber);
            when.subscribe(receiver);
            receiver.onNext(0);
        } catch (Throwable ex) {
            Exceptions.throwIfFatal(ex);
            EmptySubscription.error(ex, s);
        }
    }

    /* renamed from: io.reactivex.internal.operators.flowable.FlowableRetryWhen$RetryWhenSubscriber */
    static final class RetryWhenSubscriber<T> extends FlowableRepeatWhen.WhenSourceSubscriber<T, Throwable> {
        private static final long serialVersionUID = -2680129890138081029L;

        RetryWhenSubscriber(Subscriber<? super T> actual, FlowableProcessor<Throwable> processor, Subscription receiver) {
            super(actual, processor, receiver);
        }

        public void onError(Throwable t) {
            again(t);
        }

        public void onComplete() {
            this.receiver.cancel();
            this.actual.onComplete();
        }
    }
}
