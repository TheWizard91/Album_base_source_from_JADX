package p019io.reactivex.internal.operators.flowable;

import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import p019io.reactivex.Flowable;
import p019io.reactivex.FlowableSubscriber;
import p019io.reactivex.Scheduler;
import p019io.reactivex.internal.subscriptions.SubscriptionHelper;
import p019io.reactivex.internal.util.BackpressureHelper;

/* renamed from: io.reactivex.internal.operators.flowable.FlowableSubscribeOn */
public final class FlowableSubscribeOn<T> extends AbstractFlowableWithUpstream<T, T> {
    final boolean nonScheduledRequests;
    final Scheduler scheduler;

    public FlowableSubscribeOn(Flowable<T> source, Scheduler scheduler2, boolean nonScheduledRequests2) {
        super(source);
        this.scheduler = scheduler2;
        this.nonScheduledRequests = nonScheduledRequests2;
    }

    public void subscribeActual(Subscriber<? super T> s) {
        Scheduler.Worker w = this.scheduler.createWorker();
        SubscribeOnSubscriber<T> sos = new SubscribeOnSubscriber<>(s, w, this.source, this.nonScheduledRequests);
        s.onSubscribe(sos);
        w.schedule(sos);
    }

    /* renamed from: io.reactivex.internal.operators.flowable.FlowableSubscribeOn$SubscribeOnSubscriber */
    static final class SubscribeOnSubscriber<T> extends AtomicReference<Thread> implements FlowableSubscriber<T>, Subscription, Runnable {
        private static final long serialVersionUID = 8094547886072529208L;
        final Subscriber<? super T> actual;
        final boolean nonScheduledRequests;
        final AtomicLong requested = new AtomicLong();

        /* renamed from: s */
        final AtomicReference<Subscription> f343s = new AtomicReference<>();
        Publisher<T> source;
        final Scheduler.Worker worker;

        SubscribeOnSubscriber(Subscriber<? super T> actual2, Scheduler.Worker worker2, Publisher<T> source2, boolean requestOn) {
            this.actual = actual2;
            this.worker = worker2;
            this.source = source2;
            this.nonScheduledRequests = !requestOn;
        }

        public void run() {
            lazySet(Thread.currentThread());
            Publisher<T> src = this.source;
            this.source = null;
            src.subscribe(this);
        }

        public void onSubscribe(Subscription s) {
            if (SubscriptionHelper.setOnce(this.f343s, s)) {
                long r = this.requested.getAndSet(0);
                if (r != 0) {
                    requestUpstream(r, s);
                }
            }
        }

        public void onNext(T t) {
            this.actual.onNext(t);
        }

        public void onError(Throwable t) {
            this.actual.onError(t);
            this.worker.dispose();
        }

        public void onComplete() {
            this.actual.onComplete();
            this.worker.dispose();
        }

        public void request(long n) {
            if (SubscriptionHelper.validate(n)) {
                Subscription s = this.f343s.get();
                if (s != null) {
                    requestUpstream(n, s);
                    return;
                }
                BackpressureHelper.add(this.requested, n);
                Subscription s2 = this.f343s.get();
                if (s2 != null) {
                    long r = this.requested.getAndSet(0);
                    if (r != 0) {
                        requestUpstream(r, s2);
                    }
                }
            }
        }

        /* access modifiers changed from: package-private */
        public void requestUpstream(long n, Subscription s) {
            if (this.nonScheduledRequests || Thread.currentThread() == get()) {
                s.request(n);
            } else {
                this.worker.schedule(new Request(s, n));
            }
        }

        public void cancel() {
            SubscriptionHelper.cancel(this.f343s);
            this.worker.dispose();
        }

        /* renamed from: io.reactivex.internal.operators.flowable.FlowableSubscribeOn$SubscribeOnSubscriber$Request */
        static final class Request implements Runnable {

            /* renamed from: n */
            private final long f344n;

            /* renamed from: s */
            private final Subscription f345s;

            Request(Subscription s, long n) {
                this.f345s = s;
                this.f344n = n;
            }

            public void run() {
                this.f345s.request(this.f344n);
            }
        }
    }
}
