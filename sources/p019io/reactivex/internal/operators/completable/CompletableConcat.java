package p019io.reactivex.internal.operators.completable;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscription;
import p019io.reactivex.Completable;
import p019io.reactivex.CompletableObserver;
import p019io.reactivex.CompletableSource;
import p019io.reactivex.Flowable;
import p019io.reactivex.FlowableSubscriber;
import p019io.reactivex.disposables.Disposable;
import p019io.reactivex.exceptions.Exceptions;
import p019io.reactivex.exceptions.MissingBackpressureException;
import p019io.reactivex.internal.disposables.DisposableHelper;
import p019io.reactivex.internal.fuseable.QueueSubscription;
import p019io.reactivex.internal.fuseable.SimpleQueue;
import p019io.reactivex.internal.queue.SpscArrayQueue;
import p019io.reactivex.internal.queue.SpscLinkedArrayQueue;
import p019io.reactivex.internal.subscriptions.SubscriptionHelper;
import p019io.reactivex.plugins.RxJavaPlugins;

/* renamed from: io.reactivex.internal.operators.completable.CompletableConcat */
public final class CompletableConcat extends Completable {
    final int prefetch;
    final Publisher<? extends CompletableSource> sources;

    public CompletableConcat(Publisher<? extends CompletableSource> sources2, int prefetch2) {
        this.sources = sources2;
        this.prefetch = prefetch2;
    }

    public void subscribeActual(CompletableObserver s) {
        this.sources.subscribe(new CompletableConcatSubscriber(s, this.prefetch));
    }

    /* renamed from: io.reactivex.internal.operators.completable.CompletableConcat$CompletableConcatSubscriber */
    static final class CompletableConcatSubscriber extends AtomicInteger implements FlowableSubscriber<CompletableSource>, Disposable {
        private static final long serialVersionUID = 9032184911934499404L;
        volatile boolean active;
        final CompletableObserver actual;
        int consumed;
        volatile boolean done;
        final ConcatInnerObserver inner = new ConcatInnerObserver(this);
        final int limit;
        final AtomicBoolean once = new AtomicBoolean();
        final int prefetch;
        SimpleQueue<CompletableSource> queue;

        /* renamed from: s */
        Subscription f230s;
        int sourceFused;

        CompletableConcatSubscriber(CompletableObserver actual2, int prefetch2) {
            this.actual = actual2;
            this.prefetch = prefetch2;
            this.limit = prefetch2 - (prefetch2 >> 2);
        }

        public void onSubscribe(Subscription s) {
            if (SubscriptionHelper.validate(this.f230s, s)) {
                this.f230s = s;
                int i = this.prefetch;
                long r = i == Integer.MAX_VALUE ? Long.MAX_VALUE : (long) i;
                if (s instanceof QueueSubscription) {
                    QueueSubscription<CompletableSource> qs = (QueueSubscription) s;
                    int m = qs.requestFusion(3);
                    if (m == 1) {
                        this.sourceFused = m;
                        this.queue = qs;
                        this.done = true;
                        this.actual.onSubscribe(this);
                        drain();
                        return;
                    } else if (m == 2) {
                        this.sourceFused = m;
                        this.queue = qs;
                        this.actual.onSubscribe(this);
                        s.request(r);
                        return;
                    }
                }
                if (this.prefetch == Integer.MAX_VALUE) {
                    this.queue = new SpscLinkedArrayQueue(Flowable.bufferSize());
                } else {
                    this.queue = new SpscArrayQueue(this.prefetch);
                }
                this.actual.onSubscribe(this);
                s.request(r);
            }
        }

        public void onNext(CompletableSource t) {
            if (this.sourceFused != 0 || this.queue.offer(t)) {
                drain();
            } else {
                onError(new MissingBackpressureException());
            }
        }

        public void onError(Throwable t) {
            if (this.once.compareAndSet(false, true)) {
                DisposableHelper.dispose(this.inner);
                this.actual.onError(t);
                return;
            }
            RxJavaPlugins.onError(t);
        }

        public void onComplete() {
            this.done = true;
            drain();
        }

        public void dispose() {
            this.f230s.cancel();
            DisposableHelper.dispose(this.inner);
        }

        public boolean isDisposed() {
            return DisposableHelper.isDisposed((Disposable) this.inner.get());
        }

        /* access modifiers changed from: package-private */
        public void drain() {
            if (getAndIncrement() == 0) {
                while (!isDisposed()) {
                    if (!this.active) {
                        boolean d = this.done;
                        try {
                            CompletableSource cs = this.queue.poll();
                            boolean empty = cs == null;
                            if (!d || !empty) {
                                if (!empty) {
                                    this.active = true;
                                    cs.subscribe(this.inner);
                                    request();
                                }
                            } else if (this.once.compareAndSet(false, true)) {
                                this.actual.onComplete();
                                return;
                            } else {
                                return;
                            }
                        } catch (Throwable ex) {
                            Exceptions.throwIfFatal(ex);
                            innerError(ex);
                            return;
                        }
                    }
                    if (decrementAndGet() == 0) {
                        return;
                    }
                }
            }
        }

        /* access modifiers changed from: package-private */
        public void request() {
            if (this.sourceFused != 1) {
                int p = this.consumed + 1;
                if (p == this.limit) {
                    this.consumed = 0;
                    this.f230s.request((long) p);
                    return;
                }
                this.consumed = p;
            }
        }

        /* access modifiers changed from: package-private */
        public void innerError(Throwable e) {
            if (this.once.compareAndSet(false, true)) {
                this.f230s.cancel();
                this.actual.onError(e);
                return;
            }
            RxJavaPlugins.onError(e);
        }

        /* access modifiers changed from: package-private */
        public void innerComplete() {
            this.active = false;
            drain();
        }

        /* renamed from: io.reactivex.internal.operators.completable.CompletableConcat$CompletableConcatSubscriber$ConcatInnerObserver */
        static final class ConcatInnerObserver extends AtomicReference<Disposable> implements CompletableObserver {
            private static final long serialVersionUID = -5454794857847146511L;
            final CompletableConcatSubscriber parent;

            ConcatInnerObserver(CompletableConcatSubscriber parent2) {
                this.parent = parent2;
            }

            public void onSubscribe(Disposable d) {
                DisposableHelper.replace(this, d);
            }

            public void onError(Throwable e) {
                this.parent.innerError(e);
            }

            public void onComplete() {
                this.parent.innerComplete();
            }
        }
    }
}
