package p019io.reactivex.internal.observers;

import java.util.NoSuchElementException;
import java.util.concurrent.CancellationException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicReference;
import p019io.reactivex.Observer;
import p019io.reactivex.disposables.Disposable;
import p019io.reactivex.internal.disposables.DisposableHelper;
import p019io.reactivex.internal.util.BlockingHelper;
import p019io.reactivex.plugins.RxJavaPlugins;

/* renamed from: io.reactivex.internal.observers.FutureObserver */
public final class FutureObserver<T> extends CountDownLatch implements Observer<T>, Future<T>, Disposable {
    Throwable error;

    /* renamed from: s */
    final AtomicReference<Disposable> f217s = new AtomicReference<>();
    T value;

    public FutureObserver() {
        super(1);
    }

    public boolean cancel(boolean mayInterruptIfRunning) {
        Disposable a;
        do {
            a = this.f217s.get();
            if (a == this || a == DisposableHelper.DISPOSED) {
                return false;
            }
        } while (!this.f217s.compareAndSet(a, DisposableHelper.DISPOSED));
        if (a != null) {
            a.dispose();
        }
        countDown();
        return true;
    }

    public boolean isCancelled() {
        return DisposableHelper.isDisposed(this.f217s.get());
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
        DisposableHelper.setOnce(this.f217s, s);
    }

    public void onNext(T t) {
        if (this.value != null) {
            this.f217s.get().dispose();
            onError(new IndexOutOfBoundsException("More than one element received"));
            return;
        }
        this.value = t;
    }

    public void onError(Throwable t) {
        Disposable a;
        if (this.error == null) {
            this.error = t;
            do {
                a = this.f217s.get();
                if (a == this || a == DisposableHelper.DISPOSED) {
                    RxJavaPlugins.onError(t);
                    return;
                }
            } while (!this.f217s.compareAndSet(a, this));
            countDown();
            return;
        }
        RxJavaPlugins.onError(t);
    }

    public void onComplete() {
        Disposable a;
        if (this.value == null) {
            onError(new NoSuchElementException("The source is empty"));
            return;
        }
        do {
            a = this.f217s.get();
            if (a == this || a == DisposableHelper.DISPOSED) {
                return;
            }
        } while (!this.f217s.compareAndSet(a, this));
        countDown();
    }

    public void dispose() {
    }

    public boolean isDisposed() {
        return isDone();
    }
}
