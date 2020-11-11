package p019io.reactivex.internal.observers;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import p019io.reactivex.CompletableObserver;
import p019io.reactivex.MaybeObserver;
import p019io.reactivex.SingleObserver;
import p019io.reactivex.disposables.Disposable;
import p019io.reactivex.internal.util.BlockingHelper;
import p019io.reactivex.internal.util.ExceptionHelper;

/* renamed from: io.reactivex.internal.observers.BlockingMultiObserver */
public final class BlockingMultiObserver<T> extends CountDownLatch implements SingleObserver<T>, CompletableObserver, MaybeObserver<T> {
    volatile boolean cancelled;

    /* renamed from: d */
    Disposable f214d;
    Throwable error;
    T value;

    public BlockingMultiObserver() {
        super(1);
    }

    /* access modifiers changed from: package-private */
    public void dispose() {
        this.cancelled = true;
        Disposable d = this.f214d;
        if (d != null) {
            d.dispose();
        }
    }

    public void onSubscribe(Disposable d) {
        this.f214d = d;
        if (this.cancelled) {
            d.dispose();
        }
    }

    public void onSuccess(T value2) {
        this.value = value2;
        countDown();
    }

    public void onError(Throwable e) {
        this.error = e;
        countDown();
    }

    public void onComplete() {
        countDown();
    }

    public T blockingGet() {
        if (getCount() != 0) {
            try {
                BlockingHelper.verifyNonBlocking();
                await();
            } catch (InterruptedException ex) {
                dispose();
                throw ExceptionHelper.wrapOrThrow(ex);
            }
        }
        Throwable ex2 = this.error;
        if (ex2 == null) {
            return this.value;
        }
        throw ExceptionHelper.wrapOrThrow(ex2);
    }

    public T blockingGet(T defaultValue) {
        if (getCount() != 0) {
            try {
                BlockingHelper.verifyNonBlocking();
                await();
            } catch (InterruptedException ex) {
                dispose();
                throw ExceptionHelper.wrapOrThrow(ex);
            }
        }
        Throwable ex2 = this.error;
        if (ex2 == null) {
            T v = this.value;
            return v != null ? v : defaultValue;
        }
        throw ExceptionHelper.wrapOrThrow(ex2);
    }

    public Throwable blockingGetError() {
        if (getCount() != 0) {
            try {
                BlockingHelper.verifyNonBlocking();
                await();
            } catch (InterruptedException ex) {
                dispose();
                return ex;
            }
        }
        return this.error;
    }

    public Throwable blockingGetError(long timeout, TimeUnit unit) {
        if (getCount() != 0) {
            try {
                BlockingHelper.verifyNonBlocking();
                if (!await(timeout, unit)) {
                    dispose();
                    throw ExceptionHelper.wrapOrThrow(new TimeoutException());
                }
            } catch (InterruptedException ex) {
                dispose();
                throw ExceptionHelper.wrapOrThrow(ex);
            }
        }
        return this.error;
    }

    public boolean blockingAwait(long timeout, TimeUnit unit) {
        if (getCount() != 0) {
            try {
                BlockingHelper.verifyNonBlocking();
                if (!await(timeout, unit)) {
                    dispose();
                    return false;
                }
            } catch (InterruptedException ex) {
                dispose();
                throw ExceptionHelper.wrapOrThrow(ex);
            }
        }
        Throwable ex2 = this.error;
        if (ex2 == null) {
            return true;
        }
        throw ExceptionHelper.wrapOrThrow(ex2);
    }
}
