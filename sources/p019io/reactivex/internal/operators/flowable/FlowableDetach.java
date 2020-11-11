package p019io.reactivex.internal.operators.flowable;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import p019io.reactivex.Flowable;
import p019io.reactivex.FlowableSubscriber;
import p019io.reactivex.internal.subscriptions.SubscriptionHelper;
import p019io.reactivex.internal.util.EmptyComponent;

/* renamed from: io.reactivex.internal.operators.flowable.FlowableDetach */
public final class FlowableDetach<T> extends AbstractFlowableWithUpstream<T, T> {
    public FlowableDetach(Flowable<T> source) {
        super(source);
    }

    /* access modifiers changed from: protected */
    public void subscribeActual(Subscriber<? super T> s) {
        this.source.subscribe(new DetachSubscriber(s));
    }

    /* renamed from: io.reactivex.internal.operators.flowable.FlowableDetach$DetachSubscriber */
    static final class DetachSubscriber<T> implements FlowableSubscriber<T>, Subscription {
        Subscriber<? super T> actual;

        /* renamed from: s */
        Subscription f282s;

        DetachSubscriber(Subscriber<? super T> actual2) {
            this.actual = actual2;
        }

        public void request(long n) {
            this.f282s.request(n);
        }

        public void cancel() {
            Subscription s = this.f282s;
            this.f282s = EmptyComponent.INSTANCE;
            this.actual = EmptyComponent.asSubscriber();
            s.cancel();
        }

        public void onSubscribe(Subscription s) {
            if (SubscriptionHelper.validate(this.f282s, s)) {
                this.f282s = s;
                this.actual.onSubscribe(this);
            }
        }

        public void onNext(T t) {
            this.actual.onNext(t);
        }

        public void onError(Throwable t) {
            Subscriber<? super T> a = this.actual;
            this.f282s = EmptyComponent.INSTANCE;
            this.actual = EmptyComponent.asSubscriber();
            a.onError(t);
        }

        public void onComplete() {
            Subscriber<? super T> a = this.actual;
            this.f282s = EmptyComponent.INSTANCE;
            this.actual = EmptyComponent.asSubscriber();
            a.onComplete();
        }
    }
}
