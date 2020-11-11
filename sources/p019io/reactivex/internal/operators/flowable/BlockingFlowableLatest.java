package p019io.reactivex.internal.operators.flowable;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Publisher;
import p019io.reactivex.Flowable;
import p019io.reactivex.Notification;
import p019io.reactivex.internal.util.BlockingHelper;
import p019io.reactivex.internal.util.ExceptionHelper;
import p019io.reactivex.plugins.RxJavaPlugins;
import p019io.reactivex.subscribers.DisposableSubscriber;

/* renamed from: io.reactivex.internal.operators.flowable.BlockingFlowableLatest */
public final class BlockingFlowableLatest<T> implements Iterable<T> {
    final Publisher<? extends T> source;

    public BlockingFlowableLatest(Publisher<? extends T> source2) {
        this.source = source2;
    }

    public Iterator<T> iterator() {
        LatestSubscriberIterator<T> lio = new LatestSubscriberIterator<>();
        Flowable.fromPublisher(this.source).materialize().subscribe(lio);
        return lio;
    }

    /* renamed from: io.reactivex.internal.operators.flowable.BlockingFlowableLatest$LatestSubscriberIterator */
    static final class LatestSubscriberIterator<T> extends DisposableSubscriber<Notification<T>> implements Iterator<T> {
        Notification<T> iteratorNotification;
        final Semaphore notify = new Semaphore(0);
        final AtomicReference<Notification<T>> value = new AtomicReference<>();

        LatestSubscriberIterator() {
        }

        public void onNext(Notification<T> args) {
            if (this.value.getAndSet(args) == null) {
                this.notify.release();
            }
        }

        public void onError(Throwable e) {
            RxJavaPlugins.onError(e);
        }

        public void onComplete() {
        }

        public boolean hasNext() {
            Notification<T> notification = this.iteratorNotification;
            if (notification == null || !notification.isOnError()) {
                Notification<T> notification2 = this.iteratorNotification;
                if ((notification2 == null || notification2.isOnNext()) && this.iteratorNotification == null) {
                    try {
                        BlockingHelper.verifyNonBlocking();
                        this.notify.acquire();
                        Notification<T> n = this.value.getAndSet((Object) null);
                        this.iteratorNotification = n;
                        if (n.isOnError()) {
                            throw ExceptionHelper.wrapOrThrow(n.getError());
                        }
                    } catch (InterruptedException ex) {
                        dispose();
                        this.iteratorNotification = Notification.createOnError(ex);
                        throw ExceptionHelper.wrapOrThrow(ex);
                    }
                }
                return this.iteratorNotification.isOnNext();
            }
            throw ExceptionHelper.wrapOrThrow(this.iteratorNotification.getError());
        }

        public T next() {
            if (!hasNext() || !this.iteratorNotification.isOnNext()) {
                throw new NoSuchElementException();
            }
            T v = this.iteratorNotification.getValue();
            this.iteratorNotification = null;
            return v;
        }

        public void remove() {
            throw new UnsupportedOperationException("Read-only iterator.");
        }
    }
}
