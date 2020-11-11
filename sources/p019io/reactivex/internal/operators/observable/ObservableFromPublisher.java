package p019io.reactivex.internal.operators.observable;

import org.reactivestreams.Publisher;
import org.reactivestreams.Subscription;
import p019io.reactivex.FlowableSubscriber;
import p019io.reactivex.Observable;
import p019io.reactivex.Observer;
import p019io.reactivex.disposables.Disposable;
import p019io.reactivex.internal.subscriptions.SubscriptionHelper;

/* renamed from: io.reactivex.internal.operators.observable.ObservableFromPublisher */
public final class ObservableFromPublisher<T> extends Observable<T> {
    final Publisher<? extends T> source;

    public ObservableFromPublisher(Publisher<? extends T> publisher) {
        this.source = publisher;
    }

    /* access modifiers changed from: protected */
    public void subscribeActual(Observer<? super T> o) {
        this.source.subscribe(new PublisherSubscriber(o));
    }

    /* renamed from: io.reactivex.internal.operators.observable.ObservableFromPublisher$PublisherSubscriber */
    static final class PublisherSubscriber<T> implements FlowableSubscriber<T>, Disposable {
        final Observer<? super T> actual;

        /* renamed from: s */
        Subscription f450s;

        PublisherSubscriber(Observer<? super T> o) {
            this.actual = o;
        }

        public void onComplete() {
            this.actual.onComplete();
        }

        public void onError(Throwable t) {
            this.actual.onError(t);
        }

        public void onNext(T t) {
            this.actual.onNext(t);
        }

        public void onSubscribe(Subscription s) {
            if (SubscriptionHelper.validate(this.f450s, s)) {
                this.f450s = s;
                this.actual.onSubscribe(this);
                s.request(Long.MAX_VALUE);
            }
        }

        public void dispose() {
            this.f450s.cancel();
            this.f450s = SubscriptionHelper.CANCELLED;
        }

        public boolean isDisposed() {
            return this.f450s == SubscriptionHelper.CANCELLED;
        }
    }
}
