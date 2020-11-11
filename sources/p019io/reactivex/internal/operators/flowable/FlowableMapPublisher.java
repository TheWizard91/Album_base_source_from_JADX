package p019io.reactivex.internal.operators.flowable;

import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import p019io.reactivex.Flowable;
import p019io.reactivex.functions.Function;
import p019io.reactivex.internal.operators.flowable.FlowableMap;

/* renamed from: io.reactivex.internal.operators.flowable.FlowableMapPublisher */
public final class FlowableMapPublisher<T, U> extends Flowable<U> {
    final Function<? super T, ? extends U> mapper;
    final Publisher<T> source;

    public FlowableMapPublisher(Publisher<T> source2, Function<? super T, ? extends U> mapper2) {
        this.source = source2;
        this.mapper = mapper2;
    }

    /* access modifiers changed from: protected */
    public void subscribeActual(Subscriber<? super U> s) {
        this.source.subscribe(new FlowableMap.MapSubscriber(s, this.mapper));
    }
}
