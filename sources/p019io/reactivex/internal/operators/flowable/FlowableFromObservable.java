package p019io.reactivex.internal.operators.flowable;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import p019io.reactivex.Flowable;
import p019io.reactivex.Observable;
import p019io.reactivex.Observer;
import p019io.reactivex.disposables.Disposable;

/* renamed from: io.reactivex.internal.operators.flowable.FlowableFromObservable */
public final class FlowableFromObservable<T> extends Flowable<T> {
    private final Observable<T> upstream;

    public FlowableFromObservable(Observable<T> upstream2) {
        this.upstream = upstream2;
    }

    /* access modifiers changed from: protected */
    public void subscribeActual(Subscriber<? super T> s) {
        this.upstream.subscribe(new SubscriberObserver(s));
    }

    /* renamed from: io.reactivex.internal.operators.flowable.FlowableFromObservable$SubscriberObserver */
    static class SubscriberObserver<T> implements Observer<T>, Subscription {

        /* renamed from: d */
        private Disposable f298d;

        /* renamed from: s */
        private final Subscriber<? super T> f299s;

        SubscriberObserver(Subscriber<? super T> s) {
            this.f299s = s;
        }

        public void onComplete() {
            this.f299s.onComplete();
        }

        public void onError(Throwable e) {
            this.f299s.onError(e);
        }

        public void onNext(T value) {
            this.f299s.onNext(value);
        }

        public void onSubscribe(Disposable d) {
            this.f298d = d;
            this.f299s.onSubscribe(this);
        }

        public void cancel() {
            this.f298d.dispose();
        }

        public void request(long n) {
        }
    }
}
