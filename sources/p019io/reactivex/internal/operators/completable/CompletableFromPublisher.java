package p019io.reactivex.internal.operators.completable;

import org.reactivestreams.Publisher;
import org.reactivestreams.Subscription;
import p019io.reactivex.Completable;
import p019io.reactivex.CompletableObserver;
import p019io.reactivex.FlowableSubscriber;
import p019io.reactivex.disposables.Disposable;
import p019io.reactivex.internal.subscriptions.SubscriptionHelper;

/* renamed from: io.reactivex.internal.operators.completable.CompletableFromPublisher */
public final class CompletableFromPublisher<T> extends Completable {
    final Publisher<T> flowable;

    public CompletableFromPublisher(Publisher<T> flowable2) {
        this.flowable = flowable2;
    }

    /* access modifiers changed from: protected */
    public void subscribeActual(CompletableObserver cs) {
        this.flowable.subscribe(new FromPublisherSubscriber(cs));
    }

    /* renamed from: io.reactivex.internal.operators.completable.CompletableFromPublisher$FromPublisherSubscriber */
    static final class FromPublisherSubscriber<T> implements FlowableSubscriber<T>, Disposable {

        /* renamed from: cs */
        final CompletableObserver f240cs;

        /* renamed from: s */
        Subscription f241s;

        FromPublisherSubscriber(CompletableObserver actual) {
            this.f240cs = actual;
        }

        public void onSubscribe(Subscription s) {
            if (SubscriptionHelper.validate(this.f241s, s)) {
                this.f241s = s;
                this.f240cs.onSubscribe(this);
                s.request(Long.MAX_VALUE);
            }
        }

        public void onNext(T t) {
        }

        public void onError(Throwable t) {
            this.f240cs.onError(t);
        }

        public void onComplete() {
            this.f240cs.onComplete();
        }

        public void dispose() {
            this.f241s.cancel();
            this.f241s = SubscriptionHelper.CANCELLED;
        }

        public boolean isDisposed() {
            return this.f241s == SubscriptionHelper.CANCELLED;
        }
    }
}
