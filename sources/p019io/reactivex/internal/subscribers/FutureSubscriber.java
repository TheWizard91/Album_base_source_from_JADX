package p019io.reactivex.internal.subscribers;

import java.util.NoSuchElementException;
import java.util.concurrent.CancellationException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Subscription;
import p019io.reactivex.FlowableSubscriber;
import p019io.reactivex.internal.subscriptions.SubscriptionHelper;
import p019io.reactivex.internal.util.BlockingHelper;
import p019io.reactivex.plugins.RxJavaPlugins;

/* renamed from: io.reactivex.internal.subscribers.FutureSubscriber */
public final class FutureSubscriber<T> extends CountDownLatch implements FlowableSubscriber<T>, Future<T>, Subscription {
    Throwable error;

    /* renamed from: s */
    final AtomicReference<Subscription> f568s = new AtomicReference<>();
    T value;

    public FutureSubscriber() {
        super(1);
    }

    public boolean cancel(boolean mayInterruptIfRunning) {
        Subscription a;
        do {
            a = this.f568s.get();
            if (a == this || a == SubscriptionHelper.CANCELLED) {
                return false;
            }
        } while (!this.f568s.compareAndSet(a, SubscriptionHelper.CANCELLED));
        if (a != null) {
            a.cancel();
        }
        countDown();
        return true;
    }

    public boolean isCancelled() {
        return SubscriptionHelper.isCancelled(this.f568s.get());
    }

    public boolean isDone() {
        return getCount() == 0;
    }

    public T get() throws InterruptedException, ExecutionException {
        if (getCount() != 0) {
            BlockingHelper.verifyNonBlocking();
            await();
        }
        if (!isCancelled()) {
            Throwable ex = this.error;
            if (ex == null) {
                return this.value;
            }
            throw new ExecutionException(ex);
        }
        throw new CancellationException();
    }

    public T get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
        if (getCount() != 0) {
            BlockingHelper.verifyNonBlocking();
            if (!await(timeout, unit)) {
                throw new TimeoutException();
            }
        }
        if (!isCancelled()) {
            Throwable ex = this.error;
            if (ex == null) {
                return this.value;
            }
            throw new ExecutionException(ex);
        }
        throw new CancellationException();
    }

    public void onSubscribe(Subscription s) {
        SubscriptionHelper.setOnce(this.f568s, s, Long.MAX_VALUE);
    }

    public void onNext(T t) {
        if (this.value != null) {
            this.f568s.get().cancel();
            onError(new IndexOutOfBoundsException("More than one element received"));
            return;
        }
        this.value = t;
    }

    public void onError(Throwable t) {
        Subscription a;
        do {
            a = this.f568s.get();
            if (a == this || a == SubscriptionHelper.CANCELLED) {
                RxJavaPlugins.onError(t);
                return;
            }
            this.error = t;
        } while (!this.f568s.compareAndSet(a, this));
        countDown();
    }

    public void onComplete() {
        Subscription a;
        if (this.value == null) {
            onError(new NoSuchElementException("The source is empty"));
            return;
        }
        do {
            a = this.f568s.get();
            if (a == this || a == SubscriptionHelper.CANCELLED) {
                return;
            }
        } while (!this.f568s.compareAndSet(a, this));
        countDown();
    }

    public void cancel() {
    }

    public void request(long n) {
    }
}
