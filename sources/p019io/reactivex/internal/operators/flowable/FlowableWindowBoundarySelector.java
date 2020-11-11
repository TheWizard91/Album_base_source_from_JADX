package p019io.reactivex.internal.operators.flowable;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import p019io.reactivex.Flowable;
import p019io.reactivex.disposables.CompositeDisposable;
import p019io.reactivex.disposables.Disposable;
import p019io.reactivex.exceptions.MissingBackpressureException;
import p019io.reactivex.functions.Function;
import p019io.reactivex.internal.disposables.DisposableHelper;
import p019io.reactivex.internal.functions.ObjectHelper;
import p019io.reactivex.internal.fuseable.SimplePlainQueue;
import p019io.reactivex.internal.queue.MpscLinkedQueue;
import p019io.reactivex.internal.subscribers.QueueDrainSubscriber;
import p019io.reactivex.internal.subscriptions.SubscriptionHelper;
import p019io.reactivex.internal.util.NotificationLite;
import p019io.reactivex.plugins.RxJavaPlugins;
import p019io.reactivex.processors.UnicastProcessor;
import p019io.reactivex.subscribers.DisposableSubscriber;
import p019io.reactivex.subscribers.SerializedSubscriber;

/* renamed from: io.reactivex.internal.operators.flowable.FlowableWindowBoundarySelector */
public final class FlowableWindowBoundarySelector<T, B, V> extends AbstractFlowableWithUpstream<T, Flowable<T>> {
    final int bufferSize;
    final Function<? super B, ? extends Publisher<V>> close;
    final Publisher<B> open;

    public FlowableWindowBoundarySelector(Flowable<T> source, Publisher<B> open2, Function<? super B, ? extends Publisher<V>> close2, int bufferSize2) {
        super(source);
        this.open = open2;
        this.close = close2;
        this.bufferSize = bufferSize2;
    }

    /* access modifiers changed from: protected */
    public void subscribeActual(Subscriber<? super Flowable<T>> s) {
        this.source.subscribe(new WindowBoundaryMainSubscriber(new SerializedSubscriber(s), this.open, this.close, this.bufferSize));
    }

    /* renamed from: io.reactivex.internal.operators.flowable.FlowableWindowBoundarySelector$WindowBoundaryMainSubscriber */
    static final class WindowBoundaryMainSubscriber<T, B, V> extends QueueDrainSubscriber<T, Object, Flowable<T>> implements Subscription {
        final AtomicReference<Disposable> boundary = new AtomicReference<>();
        final int bufferSize;
        final Function<? super B, ? extends Publisher<V>> close;
        final Publisher<B> open;
        final CompositeDisposable resources;

        /* renamed from: s */
        Subscription f363s;
        final AtomicLong windows;

        /* renamed from: ws */
        final List<UnicastProcessor<T>> f364ws;

        WindowBoundaryMainSubscriber(Subscriber<? super Flowable<T>> actual, Publisher<B> open2, Function<? super B, ? extends Publisher<V>> close2, int bufferSize2) {
            super(actual, new MpscLinkedQueue());
            AtomicLong atomicLong = new AtomicLong();
            this.windows = atomicLong;
            this.open = open2;
            this.close = close2;
            this.bufferSize = bufferSize2;
            this.resources = new CompositeDisposable();
            this.f364ws = new ArrayList();
            atomicLong.lazySet(1);
        }

        public void onSubscribe(Subscription s) {
            if (SubscriptionHelper.validate(this.f363s, s)) {
                this.f363s = s;
                this.actual.onSubscribe(this);
                if (!this.cancelled) {
                    OperatorWindowBoundaryOpenSubscriber<T, B> os = new OperatorWindowBoundaryOpenSubscriber<>(this);
                    if (this.boundary.compareAndSet((Object) null, os)) {
                        this.windows.getAndIncrement();
                        s.request(Long.MAX_VALUE);
                        this.open.subscribe(os);
                    }
                }
            }
        }

        public void onNext(T t) {
            if (!this.done) {
                if (fastEnter()) {
                    for (UnicastProcessor<T> w : this.f364ws) {
                        w.onNext(t);
                    }
                    if (leave(-1) == 0) {
                        return;
                    }
                } else {
                    this.queue.offer(NotificationLite.next(t));
                    if (!enter()) {
                        return;
                    }
                }
                drainLoop();
            }
        }

        public void onError(Throwable t) {
            if (this.done) {
                RxJavaPlugins.onError(t);
                return;
            }
            this.error = t;
            this.done = true;
            if (enter()) {
                drainLoop();
            }
            if (this.windows.decrementAndGet() == 0) {
                this.resources.dispose();
            }
            this.actual.onError(t);
        }

        public void onComplete() {
            if (!this.done) {
                this.done = true;
                if (enter()) {
                    drainLoop();
                }
                if (this.windows.decrementAndGet() == 0) {
                    this.resources.dispose();
                }
                this.actual.onComplete();
            }
        }

        /* access modifiers changed from: package-private */
        public void error(Throwable t) {
            this.f363s.cancel();
            this.resources.dispose();
            DisposableHelper.dispose(this.boundary);
            this.actual.onError(t);
        }

        public void request(long n) {
            requested(n);
        }

        public void cancel() {
            this.cancelled = true;
        }

        /* access modifiers changed from: package-private */
        public void dispose() {
            this.resources.dispose();
            DisposableHelper.dispose(this.boundary);
        }

        /* access modifiers changed from: package-private */
        public void drainLoop() {
            SimplePlainQueue<Object> q = this.queue;
            Subscriber<? super Flowable<T>> a = this.actual;
            List<UnicastProcessor<T>> ws = this.f364ws;
            int missed = 1;
            while (true) {
                boolean d = this.done;
                Object o = q.poll();
                boolean empty = o == null;
                if (d && empty) {
                    dispose();
                    Throwable e = this.error;
                    if (e != null) {
                        for (UnicastProcessor<T> w : ws) {
                            w.onError(e);
                        }
                    } else {
                        for (UnicastProcessor<T> w2 : ws) {
                            w2.onComplete();
                        }
                    }
                    ws.clear();
                    return;
                } else if (empty) {
                    missed = leave(-missed);
                    if (missed == 0) {
                        return;
                    }
                } else if (o instanceof WindowOperation) {
                    WindowOperation<T, B> wo = (WindowOperation) o;
                    if (wo.f365w != null) {
                        if (ws.remove(wo.f365w)) {
                            wo.f365w.onComplete();
                            if (this.windows.decrementAndGet() == 0) {
                                dispose();
                                return;
                            }
                        } else {
                            continue;
                        }
                    } else if (!this.cancelled) {
                        UnicastProcessor<T> w3 = UnicastProcessor.create(this.bufferSize);
                        long r = requested();
                        if (r != 0) {
                            ws.add(w3);
                            a.onNext(w3);
                            if (r != Long.MAX_VALUE) {
                                produced(1);
                            }
                            try {
                                Publisher publisher = (Publisher) ObjectHelper.requireNonNull(this.close.apply(wo.open), "The publisher supplied is null");
                                OperatorWindowBoundaryCloseSubscriber<T, V> cl = new OperatorWindowBoundaryCloseSubscriber<>(this, w3);
                                if (this.resources.add(cl)) {
                                    this.windows.getAndIncrement();
                                    publisher.subscribe(cl);
                                }
                            } catch (Throwable e2) {
                                this.cancelled = true;
                                a.onError(e2);
                            }
                        } else {
                            this.cancelled = true;
                            a.onError(new MissingBackpressureException("Could not deliver new window due to lack of requests"));
                        }
                    }
                } else {
                    for (UnicastProcessor<T> w4 : ws) {
                        w4.onNext(NotificationLite.getValue(o));
                    }
                }
            }
        }

        public boolean accept(Subscriber<? super Flowable<T>> subscriber, Object v) {
            return false;
        }

        /* access modifiers changed from: package-private */
        public void open(B b) {
            this.queue.offer(new WindowOperation((UnicastProcessor) null, b));
            if (enter()) {
                drainLoop();
            }
        }

        /* access modifiers changed from: package-private */
        public void close(OperatorWindowBoundaryCloseSubscriber<T, V> w) {
            this.resources.delete(w);
            this.queue.offer(new WindowOperation(w.f362w, null));
            if (enter()) {
                drainLoop();
            }
        }
    }

    /* renamed from: io.reactivex.internal.operators.flowable.FlowableWindowBoundarySelector$WindowOperation */
    static final class WindowOperation<T, B> {
        final B open;

        /* renamed from: w */
        final UnicastProcessor<T> f365w;

        WindowOperation(UnicastProcessor<T> w, B open2) {
            this.f365w = w;
            this.open = open2;
        }
    }

    /* renamed from: io.reactivex.internal.operators.flowable.FlowableWindowBoundarySelector$OperatorWindowBoundaryOpenSubscriber */
    static final class OperatorWindowBoundaryOpenSubscriber<T, B> extends DisposableSubscriber<B> {
        final WindowBoundaryMainSubscriber<T, B, ?> parent;

        OperatorWindowBoundaryOpenSubscriber(WindowBoundaryMainSubscriber<T, B, ?> parent2) {
            this.parent = parent2;
        }

        public void onNext(B t) {
            this.parent.open(t);
        }

        public void onError(Throwable t) {
            this.parent.error(t);
        }

        public void onComplete() {
            this.parent.onComplete();
        }
    }

    /* renamed from: io.reactivex.internal.operators.flowable.FlowableWindowBoundarySelector$OperatorWindowBoundaryCloseSubscriber */
    static final class OperatorWindowBoundaryCloseSubscriber<T, V> extends DisposableSubscriber<V> {
        boolean done;
        final WindowBoundaryMainSubscriber<T, ?, V> parent;

        /* renamed from: w */
        final UnicastProcessor<T> f362w;

        OperatorWindowBoundaryCloseSubscriber(WindowBoundaryMainSubscriber<T, ?, V> parent2, UnicastProcessor<T> w) {
            this.parent = parent2;
            this.f362w = w;
        }

        public void onNext(V v) {
            cancel();
            onComplete();
        }

        public void onError(Throwable t) {
            if (this.done) {
                RxJavaPlugins.onError(t);
                return;
            }
            this.done = true;
            this.parent.error(t);
        }

        public void onComplete() {
            if (!this.done) {
                this.done = true;
                this.parent.close(this);
            }
        }
    }
}
