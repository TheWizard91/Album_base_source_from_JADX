package p019io.reactivex.internal.util;

import java.util.concurrent.CountDownLatch;
import p019io.reactivex.disposables.Disposable;
import p019io.reactivex.internal.schedulers.NonBlockingThread;
import p019io.reactivex.plugins.RxJavaPlugins;

/* renamed from: io.reactivex.internal.util.BlockingHelper */
public final class BlockingHelper {
    private BlockingHelper() {
        throw new IllegalStateException("No instances!");
    }

    public static void awaitForComplete(CountDownLatch latch, Disposable subscription) {
        if (latch.getCount() != 0) {
            try {
                verifyNonBlocking();
                latch.await();
            } catch (InterruptedException e) {
                subscription.dispose();
                Thread.currentThread().interrupt();
                throw new IllegalStateException("Interrupted while waiting for subscription to complete.", e);
            }
        }
    }

    public static void verifyNonBlocking() {
        if (!RxJavaPlugins.isFailOnNonBlockingScheduler()) {
            return;
        }
        if ((Thread.currentThread() instanceof NonBlockingThread) || RxJavaPlugins.onBeforeBlocking()) {
            throw new IllegalStateException("Attempt to block on a Scheduler " + Thread.currentThread().getName() + " that doesn't support blocking operators as they may lead to deadlock");
        }
    }
}
