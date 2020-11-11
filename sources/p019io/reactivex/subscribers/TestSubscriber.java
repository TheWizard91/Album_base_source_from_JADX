package p019io.reactivex.subscribers;

import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import p019io.reactivex.FlowableSubscriber;
import p019io.reactivex.disposables.Disposable;
import p019io.reactivex.functions.Consumer;
import p019io.reactivex.internal.fuseable.QueueSubscription;
import p019io.reactivex.internal.subscriptions.SubscriptionHelper;
import p019io.reactivex.internal.util.ExceptionHelper;
import p019io.reactivex.observers.BaseTestConsumer;

/* renamed from: io.reactivex.subscribers.TestSubscriber */
public class TestSubscriber<T> extends BaseTestConsumer<T, TestSubscriber<T>> implements FlowableSubscriber<T>, Subscription, Disposable {
    private final Subscriber<? super T> actual;
    private volatile boolean cancelled;
    private final AtomicLong missedRequested;

    /* renamed from: qs */
    private QueueSubscription<T> f609qs;
    private final AtomicReference<Subscription> subscription;

    public static <T> TestSubscriber<T> create() {
        return new TestSubscriber<>();
    }

    public static <T> TestSubscriber<T> create(long initialRequested) {
        return new TestSubscriber<>(initialRequested);
    }

    public static <T> TestSubscriber<T> create(Subscriber<? super T> delegate) {
        return new TestSubscriber<>(delegate);
    }

    public TestSubscriber() {
        this(EmptySubscriber.INSTANCE, Long.MAX_VALUE);
    }

    public TestSubscriber(long initialRequest) {
        this(EmptySubscriber.INSTANCE, initialRequest);
    }

    public TestSubscriber(Subscriber<? super T> actual2) {
        this(actual2, Long.MAX_VALUE);
    }

    public TestSubscriber(Subscriber<? super T> actual2, long initialRequest) {
        if (initialRequest >= 0) {
            this.actual = actual2;
            this.subscription = new AtomicReference<>();
            this.missedRequested = new AtomicLong(initialRequest);
            return;
        }
        throw new IllegalArgumentException("Negative initial request not allowed");
    }

    public void onSubscribe(Subscription s) {
        this.lastThread = Thread.currentThread();
        if (s == null) {
            this.errors.add(new NullPointerException("onSubscribe received a null Subscription"));
        } else if (!this.subscription.compareAndSet((Object) null, s)) {
            s.cancel();
            if (this.subscription.get() != SubscriptionHelper.CANCELLED) {
                this.errors.add(new IllegalStateException("onSubscribe received multiple subscriptions: " + s));
            }
        } else {
            if (this.initialFusionMode != 0 && (s instanceof QueueSubscription)) {
                QueueSubscription<T> queueSubscription = (QueueSubscription) s;
                this.f609qs = queueSubscription;
                int m = queueSubscription.requestFusion(this.initialFusionMode);
                this.establishedFusionMode = m;
                if (m == 1) {
                    this.checkSubscriptionOnce = true;
                    this.lastThread = Thread.currentThread();
                    while (true) {
                        try {
                            T poll = this.f609qs.poll();
                            T t = poll;
                            if (poll != null) {
                                this.values.add(t);
                            } else {
                                this.completions++;
                                return;
                            }
                        } catch (Throwable ex) {
                            this.errors.add(ex);
                            return;
                        }
                    }
                }
            }
            this.actual.onSubscribe(s);
            long mr = this.missedRequested.getAndSet(0);
            if (mr != 0) {
                s.request(mr);
            }
            onStart();
        }
    }

    /* access modifiers changed from: protected */
    public void onStart() {
    }

    public void onNext(T t) {
        if (!this.checkSubscriptionOnce) {
            this.checkSubscriptionOnce = true;
            if (this.subscription.get() == null) {
                this.errors.add(new IllegalStateException("onSubscribe not called in proper order"));
            }
        }
        this.lastThread = Thread.currentThread();
        if (this.establishedFusionMode == 2) {
            while (true) {
                try {
                    T poll = this.f609qs.poll();
                    T t2 = poll;
                    if (poll != null) {
                        this.values.add(t2);
                    } else {
                        return;
                    }
                } catch (Throwable ex) {
                    this.errors.add(ex);
                    this.f609qs.cancel();
                    return;
                }
            }
        } else {
            this.values.add(t);
            if (t == null) {
                this.errors.add(new NullPointerException("onNext received a null value"));
            }
            this.actual.onNext(t);
        }
    }

    public void onError(Throwable t) {
        if (!this.checkSubscriptionOnce) {
            this.checkSubscriptionOnce = true;
            if (this.subscription.get() == null) {
                this.errors.add(new NullPointerException("onSubscribe not called in proper order"));
            }
        }
        try {
            this.lastThread = Thread.currentThread();
            this.errors.add(t);
            if (t == null) {
                this.errors.add(new IllegalStateException("onError received a null Throwable"));
            }
            this.actual.onError(t);
        } finally {
            this.done.countDown();
        }
    }

    public void onComplete() {
        if (!this.checkSubscriptionOnce) {
            this.checkSubscriptionOnce = true;
            if (this.subscription.get() == null) {
                this.errors.add(new IllegalStateException("onSubscribe not called in proper order"));
            }
        }
        try {
            this.lastThread = Thread.currentThread();
            this.completions++;
            this.actual.onComplete();
        } finally {
            this.done.countDown();
        }
    }

    public final void request(long n) {
        SubscriptionHelper.deferredRequest(this.subscription, this.missedRequested, n);
    }

    public final void cancel() {
        if (!this.cancelled) {
            this.cancelled = true;
            SubscriptionHelper.cancel(this.subscription);
        }
    }

    public final boolean isCancelled() {
        return this.cancelled;
    }

    public final void dispose() {
        cancel();
    }

    public final boolean isDisposed() {
        return this.cancelled;
    }

    public final boolean hasSubscription() {
        return this.subscription.get() != null;
    }

    public final TestSubscriber<T> assertSubscribed() {
        if (this.subscription.get() != null) {
            return this;
        }
        throw fail("Not subscribed!");
    }

    public final TestSubscriber<T> assertNotSubscribed() {
        if (this.subscription.get() != null) {
            throw fail("Subscribed!");
        } else if (this.errors.isEmpty()) {
            return this;
        } else {
            throw fail("Not subscribed but errors found");
        }
    }

    /* access modifiers changed from: package-private */
    public final TestSubscriber<T> setInitialFusionMode(int mode) {
        this.initialFusionMode = mode;
        return this;
    }

    /* access modifiers changed from: package-private */
    public final TestSubscriber<T> assertFusionMode(int mode) {
        int m = this.establishedFusionMode;
        if (m == mode) {
            return this;
        }
        if (this.f609qs != null) {
            throw new AssertionError("Fusion mode different. Expected: " + fusionModeToString(mode) + ", actual: " + fusionModeToString(m));
        }
        throw fail("Upstream is not fuseable");
    }

    static String fusionModeToString(int mode) {
        if (mode == 0) {
            return "NONE";
        }
        if (mode == 1) {
            return "SYNC";
        }
        if (mode != 2) {
            return "Unknown(" + mode + ")";
        }
        return "ASYNC";
    }

    /* access modifiers changed from: package-private */
    public final TestSubscriber<T> assertFuseable() {
        if (this.f609qs != null) {
            return this;
        }
        throw new AssertionError("Upstream is not fuseable.");
    }

    /* access modifiers changed from: package-private */
    public final TestSubscriber<T> assertNotFuseable() {
        if (this.f609qs == null) {
            return this;
        }
        throw new AssertionError("Upstream is fuseable.");
    }

    public final TestSubscriber<T> assertOf(Consumer<? super TestSubscriber<T>> check) {
        try {
            check.accept(this);
            return this;
        } catch (Throwable ex) {
            throw ExceptionHelper.wrapOrThrow(ex);
        }
    }

    public final TestSubscriber<T> requestMore(long n) {
        request(n);
        return this;
    }

    /* renamed from: io.reactivex.subscribers.TestSubscriber$EmptySubscriber */
    enum EmptySubscriber implements FlowableSubscriber<Object> {
        INSTANCE;

        public void onSubscribe(Subscription s) {
        }

        public void onNext(Object t) {
        }

        public void onError(Throwable t) {
        }

        public void onComplete() {
        }
    }
}