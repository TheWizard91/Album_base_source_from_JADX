package p019io.reactivex.internal.operators.observable;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicReference;
import p019io.reactivex.Notification;
import p019io.reactivex.Observable;
import p019io.reactivex.ObservableSource;
import p019io.reactivex.internal.util.BlockingHelper;
import p019io.reactivex.internal.util.ExceptionHelper;
import p019io.reactivex.observers.DisposableObserver;
import p019io.reactivex.plugins.RxJavaPlugins;

/* renamed from: io.reactivex.internal.operators.observable.BlockingObservableLatest */
public final class BlockingObservableLatest<T> implements Iterable<T> {
    final ObservableSource<T> source;

    public BlockingObservableLatest(ObservableSource<T> source2) {
        this.source = source2;
    }

    public Iterator<T> iterator() {
        BlockingObservableLatestIterator<T> lio = new BlockingObservableLatestIterator<>();
        Observable.wrap(this.source).materialize().subscribe(lio);
        return lio;
    }

    /* renamed from: io.reactivex.internal.operators.observable.BlockingObservableLatest$BlockingObservableLatestIterator */
    static final class BlockingObservableLatestIterator<T> extends DisposableObserver<Notification<T>> implements Iterator<T> {
        Notification<T> iteratorNotification;
        final Semaphore notify = new Semaphore(0);
        final AtomicReference<Notification<T>> value = new AtomicReference<>();

        BlockingObservableLatestIterator() {
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
                if (this.iteratorNotification == null) {
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
            if (hasNext()) {
                T v = this.iteratorNotification.getValue();
                this.iteratorNotification = null;
                return v;
            }
            throw new NoSuchElementException();
        }

        public void remove() {
            throw new UnsupportedOperationException("Read-only iterator.");
        }
    }
}
