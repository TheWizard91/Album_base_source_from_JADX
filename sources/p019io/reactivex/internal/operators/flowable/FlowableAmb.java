package p019io.reactivex.internal.operators.flowable;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import p019io.reactivex.Flowable;
import p019io.reactivex.FlowableSubscriber;
import p019io.reactivex.exceptions.Exceptions;
import p019io.reactivex.internal.subscriptions.EmptySubscription;
import p019io.reactivex.internal.subscriptions.SubscriptionHelper;
import p019io.reactivex.plugins.RxJavaPlugins;

/* renamed from: io.reactivex.internal.operators.flowable.FlowableAmb */
public final class FlowableAmb<T> extends Flowable<T> {
    final Publisher<? extends T>[] sources;
    final Iterable<? extends Publisher<? extends T>> sourcesIterable;

    public FlowableAmb(Publisher<? extends T>[] sources2, Iterable<? extends Publisher<? extends T>> sourcesIterable2) {
        this.sources = sources2;
        this.sourcesIterable = sourcesIterable2;
    }

    public void subscribeActual(Subscriber<? super T> s) {
        Publisher<? extends T>[] sources2 = this.sources;
        int count = 0;
        if (sources2 == null) {
            sources2 = new Publisher[8];
            try {
                for (Publisher<? extends T> p : this.sourcesIterable) {
                    if (p == null) {
                        EmptySubscription.error(new NullPointerException("One of the sources is null"), s);
                        return;
                    }
                    if (count == sources2.length) {
                        Publisher<? extends T>[] b = new Publisher[((count >> 2) + count)];
                        System.arraycopy(sources2, 0, b, 0, count);
                        sources2 = b;
                    }
                    int count2 = count + 1;
                    try {
                        sources2[count] = p;
                        count = count2;
                    } catch (Throwable th) {
                        e = th;
                        int i = count2;
                        Exceptions.throwIfFatal(e);
                        EmptySubscription.error(e, s);
                        return;
                    }
                }
            } catch (Throwable th2) {
                e = th2;
                Exceptions.throwIfFatal(e);
                EmptySubscription.error(e, s);
                return;
            }
        } else {
            count = sources2.length;
        }
        if (count == 0) {
            EmptySubscription.complete(s);
        } else if (count == 1) {
            sources2[0].subscribe(s);
        } else {
            new AmbCoordinator<>(s, count).subscribe(sources2);
        }
    }

    /* renamed from: io.reactivex.internal.operators.flowable.FlowableAmb$AmbCoordinator */
    static final class AmbCoordinator<T> implements Subscription {
        final Subscriber<? super T> actual;
        final AmbInnerSubscriber<T>[] subscribers;
        final AtomicInteger winner = new AtomicInteger();

        AmbCoordinator(Subscriber<? super T> actual2, int count) {
            this.actual = actual2;
            this.subscribers = new AmbInnerSubscriber[count];
        }

        public void subscribe(Publisher<? extends T>[] sources) {
            AmbInnerSubscriber<T>[] as = this.subscribers;
            int len = as.length;
            for (int i = 0; i < len; i++) {
                as[i] = new AmbInnerSubscriber<>(this, i + 1, this.actual);
            }
            this.winner.lazySet(0);
            this.actual.onSubscribe(this);
            for (int i2 = 0; i2 < len && this.winner.get() == 0; i2++) {
                sources[i2].subscribe(as[i2]);
            }
        }

        public void request(long n) {
            if (SubscriptionHelper.validate(n)) {
                int w = this.winner.get();
                if (w > 0) {
                    this.subscribers[w - 1].request(n);
                } else if (w == 0) {
                    for (AmbInnerSubscriber<T> a : this.subscribers) {
                        a.request(n);
                    }
                }
            }
        }

        public boolean win(int index) {
            if (this.winner.get() != 0 || !this.winner.compareAndSet(0, index)) {
                return false;
            }
            AmbInnerSubscriber<T>[] a = this.subscribers;
            int n = a.length;
            for (int i = 0; i < n; i++) {
                if (i + 1 != index) {
                    a[i].cancel();
                }
            }
            return true;
        }

        public void cancel() {
            if (this.winner.get() != -1) {
                this.winner.lazySet(-1);
                for (AmbInnerSubscriber<T> a : this.subscribers) {
                    a.cancel();
                }
            }
        }
    }

    /* renamed from: io.reactivex.internal.operators.flowable.FlowableAmb$AmbInnerSubscriber */
    static final class AmbInnerSubscriber<T> extends AtomicReference<Subscription> implements FlowableSubscriber<T>, Subscription {
        private static final long serialVersionUID = -1185974347409665484L;
        final Subscriber<? super T> actual;
        final int index;
        final AtomicLong missedRequested = new AtomicLong();
        final AmbCoordinator<T> parent;
        boolean won;

        AmbInnerSubscriber(AmbCoordinator<T> parent2, int index2, Subscriber<? super T> actual2) {
            this.parent = parent2;
            this.index = index2;
            this.actual = actual2;
        }

        public void onSubscribe(Subscription s) {
            SubscriptionHelper.deferredSetOnce(this, this.missedRequested, s);
        }

        public void request(long n) {
            SubscriptionHelper.deferredRequest(this, this.missedRequested, n);
        }

        public void onNext(T t) {
            if (this.won) {
                this.actual.onNext(t);
            } else if (this.parent.win(this.index)) {
                this.won = true;
                this.actual.onNext(t);
            } else {
                ((Subscription) get()).cancel();
            }
        }

        public void onError(Throwable t) {
            if (this.won) {
                this.actual.onError(t);
            } else if (this.parent.win(this.index)) {
                this.won = true;
                this.actual.onError(t);
            } else {
                ((Subscription) get()).cancel();
                RxJavaPlugins.onError(t);
            }
        }

        public void onComplete() {
            if (this.won) {
                this.actual.onComplete();
            } else if (this.parent.win(this.index)) {
                this.won = true;
                this.actual.onComplete();
            } else {
                ((Subscription) get()).cancel();
            }
        }

        public void cancel() {
            SubscriptionHelper.cancel(this);
        }
    }
}
