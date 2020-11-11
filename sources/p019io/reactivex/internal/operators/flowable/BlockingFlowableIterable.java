package p019io.reactivex.internal.operators.flowable;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import org.reactivestreams.Subscription;
import p019io.reactivex.Flowable;
import p019io.reactivex.FlowableSubscriber;
import p019io.reactivex.disposables.Disposable;
import p019io.reactivex.exceptions.MissingBackpressureException;
import p019io.reactivex.internal.queue.SpscArrayQueue;
import p019io.reactivex.internal.subscriptions.SubscriptionHelper;
import p019io.reactivex.internal.util.BlockingHelper;
import p019io.reactivex.internal.util.ExceptionHelper;

/* renamed from: io.reactivex.internal.operators.flowable.BlockingFlowableIterable */
public final class BlockingFlowableIterable<T> implements Iterable<T> {
    final int bufferSize;
    final Flowable<T> source;

    public BlockingFlowableIterable(Flowable<T> source2, int bufferSize2) {
        this.source = source2;
        this.bufferSize = bufferSize2;
    }

    public Iterator<T> iterator() {
        BlockingFlowableIterator<T> it = new BlockingFlowableIterator<>(this.bufferSize);
        this.source.subscribe(it);
        return it;
    }

    /* renamed from: io.reactivex.internal.operators.flowable.BlockingFlowableIterable$BlockingFlowableIterator */
    static final class BlockingFlowableIterator<T> extends AtomicReference<Subscription> implements FlowableSubscriber<T>, Iterator<T>, Runnable, Disposable {
        private static final long serialVersionUID = 6695226475494099826L;
        final long batchSize;
        final Condition condition;
        volatile boolean done;
        Throwable error;
        final long limit;
        final Lock lock;
        long produced;
        final SpscArrayQueue<T> queue;

        BlockingFlowableIterator(int batchSize2) {
            this.queue = new SpscArrayQueue<>(batchSize2);
            this.batchSize = (long) batchSize2;
            this.limit = (long) (batchSize2 - (batchSize2 >> 2));
            ReentrantLock reentrantLock = new ReentrantLock();
            this.lock = reentrantLock;
            this.condition = reentrantLock.newCondition();
        }

        public boolean hasNext() {
            while (true) {
                boolean d = this.done;
                boolean empty = this.queue.isEmpty();
                if (d) {
                    Throwable e = this.error;
                    if (e != null) {
                        throw ExceptionHelper.wrapOrThrow(e);
                    } else if (empty) {
                        return false;
                    }
                }
                if (!empty) {
                    return true;
                }
                BlockingHelper.verifyNonBlocking();
                this.lock.lock();
                while (!this.done && this.queue.isEmpty()) {
                    try {
                        this.condition.await();
                    } catch (InterruptedException ex) {
                        run();
                        throw ExceptionHelper.wrapOrThrow(ex);
                    } catch (Throwable th) {
                        this.lock.unlock();
                        throw th;
                    }
                }
                this.lock.unlock();
            }
        }

        public T next() {
            if (hasNext()) {
                T v = this.queue.poll();
                long p = this.produced + 1;
                if (p == this.limit) {
                    this.produced = 0;
                    ((Subscription) get()).request(p);
                } else {
                    this.produced = p;
                }
                return v;
            }
            throw new NoSuchElementException();
        }

        public void onSubscribe(Subscription s) {
            SubscriptionHelper.setOnce(this, s, this.batchSize);
        }

        public void onNext(T t) {
            if (!this.queue.offer(t)) {
                SubscriptionHelper.cancel(this);
                onError(new MissingBackpressureException("Queue full?!"));
                return;
            }
            signalConsumer();
        }

        public void onError(Throwable t) {
            this.error = t;
            this.done = true;
            signalConsumer();
        }

        public void onComplete() {
            this.done = true;
            signalConsumer();
        }

        /* access modifiers changed from: package-private */
        public void signalConsumer() {
            this.lock.lock();
            try {
                this.condition.signalAll();
            } finally {
                this.lock.unlock();
            }
        }

        public void run() {
            SubscriptionHelper.cancel(this);
            signalConsumer();
        }

        public void remove() {
            throw new UnsupportedOperationException("remove");
        }

        public void dispose() {
            SubscriptionHelper.cancel(this);
        }

        public boolean isDisposed() {
            return SubscriptionHelper.isCancelled((Subscription) get());
        }
    }
}
