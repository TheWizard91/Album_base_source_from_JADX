package p019io.reactivex.internal.operators.flowable;

import java.util.Collection;
import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import p019io.reactivex.Flowable;
import p019io.reactivex.FlowableSubscriber;
import p019io.reactivex.disposables.Disposable;
import p019io.reactivex.exceptions.Exceptions;
import p019io.reactivex.internal.disposables.DisposableHelper;
import p019io.reactivex.internal.functions.ObjectHelper;
import p019io.reactivex.internal.queue.MpscLinkedQueue;
import p019io.reactivex.internal.subscribers.QueueDrainSubscriber;
import p019io.reactivex.internal.subscriptions.EmptySubscription;
import p019io.reactivex.internal.subscriptions.SubscriptionHelper;
import p019io.reactivex.plugins.RxJavaPlugins;
import p019io.reactivex.subscribers.DisposableSubscriber;
import p019io.reactivex.subscribers.SerializedSubscriber;

/* renamed from: io.reactivex.internal.operators.flowable.FlowableBufferBoundarySupplier */
public final class FlowableBufferBoundarySupplier<T, U extends Collection<? super T>, B> extends AbstractFlowableWithUpstream<T, U> {
    final Callable<? extends Publisher<B>> boundarySupplier;
    final Callable<U> bufferSupplier;

    public FlowableBufferBoundarySupplier(Flowable<T> source, Callable<? extends Publisher<B>> boundarySupplier2, Callable<U> bufferSupplier2) {
        super(source);
        this.boundarySupplier = boundarySupplier2;
        this.bufferSupplier = bufferSupplier2;
    }

    /* access modifiers changed from: protected */
    public void subscribeActual(Subscriber<? super U> s) {
        this.source.subscribe(new BufferBoundarySupplierSubscriber(new SerializedSubscriber(s), this.bufferSupplier, this.boundarySupplier));
    }

    /* renamed from: io.reactivex.internal.operators.flowable.FlowableBufferBoundarySupplier$BufferBoundarySupplierSubscriber */
    static final class BufferBoundarySupplierSubscriber<T, U extends Collection<? super T>, B> extends QueueDrainSubscriber<T, U, U> implements FlowableSubscriber<T>, Subscription, Disposable {
        final Callable<? extends Publisher<B>> boundarySupplier;
        U buffer;
        final Callable<U> bufferSupplier;
        final AtomicReference<Disposable> other = new AtomicReference<>();

        /* renamed from: s */
        Subscription f259s;

        BufferBoundarySupplierSubscriber(Subscriber<? super U> actual, Callable<U> bufferSupplier2, Callable<? extends Publisher<B>> boundarySupplier2) {
            super(actual, new MpscLinkedQueue());
            this.bufferSupplier = bufferSupplier2;
            this.boundarySupplier = boundarySupplier2;
        }

        public void onSubscribe(Subscription s) {
            if (SubscriptionHelper.validate(this.f259s, s)) {
                this.f259s = s;
                Subscriber<? super U> actual = this.actual;
                try {
                    this.buffer = (Collection) ObjectHelper.requireNonNull(this.bufferSupplier.call(), "The buffer supplied is null");
                    try {
                        Publisher publisher = (Publisher) ObjectHelper.requireNonNull(this.boundarySupplier.call(), "The boundary publisher supplied is null");
                        BufferBoundarySubscriber<T, U, B> bs = new BufferBoundarySubscriber<>(this);
                        this.other.set(bs);
                        actual.onSubscribe(this);
                        if (!this.cancelled) {
                            s.request(Long.MAX_VALUE);
                            publisher.subscribe(bs);
                        }
                    } catch (Throwable ex) {
                        Exceptions.throwIfFatal(ex);
                        this.cancelled = true;
                        s.cancel();
                        EmptySubscription.error(ex, actual);
                    }
                } catch (Throwable e) {
                    Exceptions.throwIfFatal(e);
                    this.cancelled = true;
                    s.cancel();
                    EmptySubscription.error(e, actual);
                }
            }
        }

        public void onNext(T t) {
            synchronized (this) {
                U b = this.buffer;
                if (b != null) {
                    b.add(t);
                }
            }
        }

        public void onError(Throwable t) {
            cancel();
            this.actual.onError(t);
        }

        /* JADX WARNING: Code restructure failed: missing block: B:10:0x0019, code lost:
            p019io.reactivex.internal.util.QueueDrainHelper.drainMaxLoop(r4.queue, r4.actual, false, r4, r4);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:18:?, code lost:
            return;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:19:?, code lost:
            return;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:8:0x000b, code lost:
            r4.queue.offer(r0);
            r4.done = true;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:9:0x0017, code lost:
            if (enter() == false) goto L_?;
         */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public void onComplete() {
            /*
                r4 = this;
                monitor-enter(r4)
                U r0 = r4.buffer     // Catch:{ all -> 0x0022 }
                if (r0 != 0) goto L_0x0007
                monitor-exit(r4)     // Catch:{ all -> 0x0022 }
                return
            L_0x0007:
                r1 = 0
                r4.buffer = r1     // Catch:{ all -> 0x0022 }
                monitor-exit(r4)     // Catch:{ all -> 0x0022 }
                io.reactivex.internal.fuseable.SimplePlainQueue r1 = r4.queue
                r1.offer(r0)
                r1 = 1
                r4.done = r1
                boolean r1 = r4.enter()
                if (r1 == 0) goto L_0x0021
                io.reactivex.internal.fuseable.SimplePlainQueue r1 = r4.queue
                org.reactivestreams.Subscriber r2 = r4.actual
                r3 = 0
                p019io.reactivex.internal.util.QueueDrainHelper.drainMaxLoop(r1, r2, r3, r4, r4)
            L_0x0021:
                return
            L_0x0022:
                r0 = move-exception
                monitor-exit(r4)     // Catch:{ all -> 0x0022 }
                throw r0
            */
            throw new UnsupportedOperationException("Method not decompiled: p019io.reactivex.internal.operators.flowable.FlowableBufferBoundarySupplier.BufferBoundarySupplierSubscriber.onComplete():void");
        }

        public void request(long n) {
            requested(n);
        }

        public void cancel() {
            if (!this.cancelled) {
                this.cancelled = true;
                this.f259s.cancel();
                disposeOther();
                if (enter()) {
                    this.queue.clear();
                }
            }
        }

        /* access modifiers changed from: package-private */
        public void disposeOther() {
            DisposableHelper.dispose(this.other);
        }

        /* access modifiers changed from: package-private */
        public void next() {
            try {
                U next = (Collection) ObjectHelper.requireNonNull(this.bufferSupplier.call(), "The buffer supplied is null");
                try {
                    Publisher<B> boundary = (Publisher) ObjectHelper.requireNonNull(this.boundarySupplier.call(), "The boundary publisher supplied is null");
                    BufferBoundarySubscriber<T, U, B> bs = new BufferBoundarySubscriber<>(this);
                    if (DisposableHelper.replace(this.other, bs)) {
                        synchronized (this) {
                            U b = this.buffer;
                            if (b != null) {
                                this.buffer = next;
                                boundary.subscribe(bs);
                                fastPathEmitMax(b, false, this);
                            }
                        }
                    }
                } catch (Throwable ex) {
                    Exceptions.throwIfFatal(ex);
                    this.cancelled = true;
                    this.f259s.cancel();
                    this.actual.onError(ex);
                }
            } catch (Throwable e) {
                Exceptions.throwIfFatal(e);
                cancel();
                this.actual.onError(e);
            }
        }

        public void dispose() {
            this.f259s.cancel();
            disposeOther();
        }

        public boolean isDisposed() {
            return this.other.get() == DisposableHelper.DISPOSED;
        }

        public boolean accept(Subscriber<? super U> subscriber, U v) {
            this.actual.onNext(v);
            return true;
        }
    }

    /* renamed from: io.reactivex.internal.operators.flowable.FlowableBufferBoundarySupplier$BufferBoundarySubscriber */
    static final class BufferBoundarySubscriber<T, U extends Collection<? super T>, B> extends DisposableSubscriber<B> {
        boolean once;
        final BufferBoundarySupplierSubscriber<T, U, B> parent;

        BufferBoundarySubscriber(BufferBoundarySupplierSubscriber<T, U, B> parent2) {
            this.parent = parent2;
        }

        public void onNext(B b) {
            if (!this.once) {
                this.once = true;
                cancel();
                this.parent.next();
            }
        }

        public void onError(Throwable t) {
            if (this.once) {
                RxJavaPlugins.onError(t);
                return;
            }
            this.once = true;
            this.parent.onError(t);
        }

        public void onComplete() {
            if (!this.once) {
                this.once = true;
                this.parent.next();
            }
        }
    }
}