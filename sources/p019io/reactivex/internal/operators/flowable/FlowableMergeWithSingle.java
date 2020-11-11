package p019io.reactivex.internal.operators.flowable;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import p019io.reactivex.Flowable;
import p019io.reactivex.FlowableSubscriber;
import p019io.reactivex.SingleObserver;
import p019io.reactivex.SingleSource;
import p019io.reactivex.disposables.Disposable;
import p019io.reactivex.internal.disposables.DisposableHelper;
import p019io.reactivex.internal.fuseable.SimplePlainQueue;
import p019io.reactivex.internal.queue.SpscArrayQueue;
import p019io.reactivex.internal.subscriptions.SubscriptionHelper;
import p019io.reactivex.internal.util.AtomicThrowable;
import p019io.reactivex.internal.util.BackpressureHelper;
import p019io.reactivex.plugins.RxJavaPlugins;

/* renamed from: io.reactivex.internal.operators.flowable.FlowableMergeWithSingle */
public final class FlowableMergeWithSingle<T> extends AbstractFlowableWithUpstream<T, T> {
    final SingleSource<? extends T> other;

    public FlowableMergeWithSingle(Flowable<T> source, SingleSource<? extends T> other2) {
        super(source);
        this.other = other2;
    }

    /* access modifiers changed from: protected */
    public void subscribeActual(Subscriber<? super T> observer) {
        MergeWithObserver<T> parent = new MergeWithObserver<>(observer);
        observer.onSubscribe(parent);
        this.source.subscribe(parent);
        this.other.subscribe(parent.otherObserver);
    }

    /* renamed from: io.reactivex.internal.operators.flowable.FlowableMergeWithSingle$MergeWithObserver */
    static final class MergeWithObserver<T> extends AtomicInteger implements FlowableSubscriber<T>, Subscription {
        static final int OTHER_STATE_CONSUMED_OR_EMPTY = 2;
        static final int OTHER_STATE_HAS_VALUE = 1;
        private static final long serialVersionUID = -4592979584110982903L;
        final Subscriber<? super T> actual;
        volatile boolean cancelled;
        int consumed;
        long emitted;
        final AtomicThrowable error = new AtomicThrowable();
        final int limit;
        volatile boolean mainDone;
        final AtomicReference<Subscription> mainSubscription = new AtomicReference<>();
        final OtherObserver<T> otherObserver = new OtherObserver<>(this);
        volatile int otherState;
        final int prefetch;
        volatile SimplePlainQueue<T> queue;
        final AtomicLong requested = new AtomicLong();
        T singleItem;

        MergeWithObserver(Subscriber<? super T> actual2) {
            this.actual = actual2;
            int bufferSize = Flowable.bufferSize();
            this.prefetch = bufferSize;
            this.limit = bufferSize - (bufferSize >> 2);
        }

        public void onSubscribe(Subscription s) {
            SubscriptionHelper.setOnce(this.mainSubscription, s, (long) this.prefetch);
        }

        public void onNext(T t) {
            if (compareAndSet(0, 1)) {
                long e = this.emitted;
                if (this.requested.get() != e) {
                    SimplePlainQueue<T> q = this.queue;
                    if (q == null || q.isEmpty()) {
                        this.emitted = 1 + e;
                        this.actual.onNext(t);
                        int c = this.consumed + 1;
                        if (c == this.limit) {
                            this.consumed = 0;
                            this.mainSubscription.get().request((long) c);
                        } else {
                            this.consumed = c;
                        }
                    } else {
                        q.offer(t);
                    }
                } else {
                    getOrCreateQueue().offer(t);
                }
                if (decrementAndGet() == 0) {
                    return;
                }
            } else {
                getOrCreateQueue().offer(t);
                if (getAndIncrement() != 0) {
                    return;
                }
            }
            drainLoop();
        }

        public void onError(Throwable ex) {
            if (this.error.addThrowable(ex)) {
                SubscriptionHelper.cancel(this.mainSubscription);
                drain();
                return;
            }
            RxJavaPlugins.onError(ex);
        }

        public void onComplete() {
            this.mainDone = true;
            drain();
        }

        public void request(long n) {
            BackpressureHelper.add(this.requested, n);
            drain();
        }

        public void cancel() {
            this.cancelled = true;
            SubscriptionHelper.cancel(this.mainSubscription);
            DisposableHelper.dispose(this.otherObserver);
            if (getAndIncrement() == 0) {
                this.queue = null;
                this.singleItem = null;
            }
        }

        /* access modifiers changed from: package-private */
        public void otherSuccess(T value) {
            if (compareAndSet(0, 1)) {
                long e = this.emitted;
                if (this.requested.get() != e) {
                    this.emitted = 1 + e;
                    this.actual.onNext(value);
                    this.otherState = 2;
                } else {
                    this.singleItem = value;
                    this.otherState = 1;
                    if (decrementAndGet() == 0) {
                        return;
                    }
                }
            } else {
                this.singleItem = value;
                this.otherState = 1;
                if (getAndIncrement() != 0) {
                    return;
                }
            }
            drainLoop();
        }

        /* access modifiers changed from: package-private */
        public void otherError(Throwable ex) {
            if (this.error.addThrowable(ex)) {
                SubscriptionHelper.cancel(this.mainSubscription);
                drain();
                return;
            }
            RxJavaPlugins.onError(ex);
        }

        /* access modifiers changed from: package-private */
        public SimplePlainQueue<T> getOrCreateQueue() {
            SimplePlainQueue<T> q = this.queue;
            if (q != null) {
                return q;
            }
            SimplePlainQueue<T> q2 = new SpscArrayQueue<>(Flowable.bufferSize());
            this.queue = q2;
            return q2;
        }

        /* access modifiers changed from: package-private */
        public void drain() {
            if (getAndIncrement() == 0) {
                drainLoop();
            }
        }

        /* access modifiers changed from: package-private */
        public void drainLoop() {
            long e;
            Subscriber<? super T> actual2 = this.actual;
            int missed = 1;
            long e2 = this.emitted;
            int c = this.consumed;
            int lim = this.limit;
            do {
                long r = this.requested.get();
                while (e2 != r) {
                    if (this.cancelled) {
                        this.singleItem = null;
                        this.queue = null;
                        return;
                    } else if (this.error.get() != null) {
                        this.singleItem = null;
                        this.queue = null;
                        actual2.onError(this.error.terminate());
                        return;
                    } else {
                        int os = this.otherState;
                        if (os == 1) {
                            T v = this.singleItem;
                            this.singleItem = null;
                            this.otherState = 2;
                            actual2.onNext(v);
                            e2++;
                        } else {
                            boolean d = this.mainDone;
                            SimplePlainQueue<T> q = this.queue;
                            T v2 = q != null ? q.poll() : null;
                            boolean empty = v2 == null;
                            if (d && empty && os == 2) {
                                this.queue = null;
                                actual2.onComplete();
                                return;
                            } else if (empty) {
                                break;
                            } else {
                                actual2.onNext(v2);
                                long e3 = e2 + 1;
                                c++;
                                if (c == lim) {
                                    c = 0;
                                    e = e3;
                                    this.mainSubscription.get().request((long) lim);
                                } else {
                                    e = e3;
                                }
                                e2 = e;
                            }
                        }
                    }
                }
                if (e2 == r) {
                    if (this.cancelled) {
                        this.singleItem = null;
                        this.queue = null;
                        return;
                    } else if (this.error.get() != null) {
                        this.singleItem = null;
                        this.queue = null;
                        actual2.onError(this.error.terminate());
                        return;
                    } else {
                        boolean d2 = this.mainDone;
                        SimplePlainQueue<T> q2 = this.queue;
                        boolean empty2 = q2 == null || q2.isEmpty();
                        if (d2 && empty2 && this.otherState == 2) {
                            this.queue = null;
                            actual2.onComplete();
                            return;
                        }
                    }
                }
                this.emitted = e2;
                this.consumed = c;
                missed = addAndGet(-missed);
            } while (missed != 0);
        }

        /* renamed from: io.reactivex.internal.operators.flowable.FlowableMergeWithSingle$MergeWithObserver$OtherObserver */
        static final class OtherObserver<T> extends AtomicReference<Disposable> implements SingleObserver<T> {
            private static final long serialVersionUID = -2935427570954647017L;
            final MergeWithObserver<T> parent;

            OtherObserver(MergeWithObserver<T> parent2) {
                this.parent = parent2;
            }

            public void onSubscribe(Disposable d) {
                DisposableHelper.setOnce(this, d);
            }

            public void onSuccess(T t) {
                this.parent.otherSuccess(t);
            }

            public void onError(Throwable e) {
                this.parent.otherError(e);
            }
        }
    }
}
