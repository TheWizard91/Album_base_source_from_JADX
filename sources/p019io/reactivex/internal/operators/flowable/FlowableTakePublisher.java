package p019io.reactivex.internal.operators.flowable;

import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import p019io.reactivex.Flowable;
import p019io.reactivex.internal.operators.flowable.FlowableTake;

/* renamed from: io.reactivex.internal.operators.flowable.FlowableTakePublisher */
public final class FlowableTakePublisher<T> extends Flowable<T> {
    final long limit;
    final Publisher<T> source;

    public FlowableTakePublisher(Publisher<T> source2, long limit2) {
        this.source = source2;
        this.limit = limit2;
    }

    /* access modifiers changed from: protected */
    public void subscribeActual(Subscriber<? super T> s) {
        this.source.subscribe(new FlowableTake.TakeSubscriber(s, this.limit));
    }
}
