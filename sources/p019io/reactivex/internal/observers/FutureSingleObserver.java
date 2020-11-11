package p019io.reactivex.internal.observers;

import java.util.concurrent.CancellationException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicReference;
import p019io.reactivex.SingleObserver;
import p019io.reactivex.disposables.Disposable;
import p019io.reactivex.internal.disposables.DisposableHelper;
import p019io.reactivex.internal.util.BlockingHelper;
import p019io.reactivex.plugins.RxJavaPlugins;

/* renamed from: io.reactivex.internal.observers.FutureSingleObserver */
public final class FutureSingleObserver<T> extends CountDownLatch implements SingleObserver<T>, Future<T>, Disposable {
    Throwable error;

    /* renamed from: s */
    final AtomicReference<Disposable> f218s = new AtomicReference<>();
    T value;

    public FutureSingleObserver() {
        super(1);
    }

    public boolean cancel(boolean mayInterruptIfRunning) {
        Disposable a;
        do {
            a = this.f218s.get();
            if (a == this || a == DisposableHelper.DISPOSED) {
                return false;
            }
        } while (!this.f218s.compareAndSet(a, DisposableHelper.DISPOSED));
        if (a != null) {
            a.dispose();
        }
        countDown();
        return true;
    }

    public boolean isCancelled() {
        return DisposableHelper.isDisposed(this.f218s.get());
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

    public void onSubscribe(Disposable s) {
        DisposableHelper.setOnce(this.f218s, s);
    }

    public void onSuccess(T t) {
        Disposable a = this.f218s.get();
        if (a != DisposableHelper.DISPOSED) {
            this.value = t;
            this.f218s.compareAndSet(a, this);
            countDown();
        }
    }

    public void onError(Throwable t) {
        Disposable a;
        do {
            a = this.f218s.get();
            if (a == DisposableHelper.DISPOSED) {
                RxJavaPlugins.onError(t);
                return;
            }
            this.error = t;
        } while (!this.f218s.compareAndSet(a, this));
        countDown();
    }

    public void dispose() {
    }

    public boolean isDisposed() {
        return isDone();
    }
}
