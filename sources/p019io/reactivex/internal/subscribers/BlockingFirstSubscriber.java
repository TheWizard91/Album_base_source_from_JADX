package p019io.reactivex.internal.subscribers;

import p019io.reactivex.plugins.RxJavaPlugins;

/* renamed from: io.reactivex.internal.subscribers.BlockingFirstSubscriber */
public final class BlockingFirstSubscriber<T> extends BlockingBaseSubscriber<T> {
    public void onNext(T t) {
        if (this.value == null) {
            this.value = t;
            this.f566s.cancel();
            countDown();
        }
    }

    public void onError(Throwable t) {
        if (this.value == null) {
            this.error = t;
        } else {
            RxJavaPlugins.onError(t);
        }
        countDown();
    }
}
