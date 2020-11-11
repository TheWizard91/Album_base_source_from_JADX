package com.facebook.imagepipeline.producers;

import com.facebook.common.internal.Preconditions;
import com.facebook.imagepipeline.common.Priority;
import java.util.Comparator;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.concurrent.Executor;

public class PriorityStarvingThrottlingProducer<T> implements Producer<T> {
    public static final String PRODUCER_NAME = "PriorityStarvingThrottlingProducer";
    /* access modifiers changed from: private */
    public final Executor mExecutor;
    private final Producer<T> mInputProducer;
    private final int mMaxSimultaneousRequests;
    private int mNumCurrentRequests = 0;
    /* access modifiers changed from: private */
    public final Queue<Item<T>> mPendingRequests = new PriorityQueue(11, new PriorityComparator());

    static /* synthetic */ int access$210(PriorityStarvingThrottlingProducer x0) {
        int i = x0.mNumCurrentRequests;
        x0.mNumCurrentRequests = i - 1;
        return i;
    }

    public PriorityStarvingThrottlingProducer(int maxSimultaneousRequests, Executor executor, Producer<T> inputProducer) {
        this.mMaxSimultaneousRequests = maxSimultaneousRequests;
        this.mExecutor = (Executor) Preconditions.checkNotNull(executor);
        this.mInputProducer = (Producer) Preconditions.checkNotNull(inputProducer);
    }

    public void produceResults(Consumer<T> consumer, ProducerContext producerContext) {
        boolean delayRequest;
        long time = System.nanoTime();
        producerContext.getProducerListener().onProducerStart(producerContext, PRODUCER_NAME);
        synchronized (this) {
            int i = this.mNumCurrentRequests;
            if (i >= this.mMaxSimultaneousRequests) {
                this.mPendingRequests.add(new Item(consumer, producerContext, time));
                delayRequest = true;
            } else {
                this.mNumCurrentRequests = i + 1;
                delayRequest = false;
            }
        }
        if (!delayRequest) {
            produceResultsInternal(new Item(consumer, producerContext, time));
        }
    }

    /* access modifiers changed from: private */
    public void produceResultsInternal(Item<T> item) {
        item.producerContext.getProducerListener().onProducerFinishWithSuccess(item.producerContext, PRODUCER_NAME, (Map<String, String>) null);
        this.mInputProducer.produceResults(new ThrottlerConsumer(item.consumer), item.producerContext);
    }

    static class Item<T> {
        final Consumer<T> consumer;
        final ProducerContext producerContext;
        final long time;

        Item(Consumer<T> consumer2, ProducerContext producerContext2, long time2) {
            this.consumer = consumer2;
            this.producerContext = producerContext2;
            this.time = time2;
        }
    }

    static class PriorityComparator<T> implements Comparator<Item<T>> {
        PriorityComparator() {
        }

        public int compare(Item<T> o1, Item<T> o2) {
            Priority p1 = o1.producerContext.getPriority();
            Priority p2 = o2.producerContext.getPriority();
            if (p1 == p2) {
                return Double.compare((double) o1.time, (double) o2.time);
            }
            if (p1.ordinal() > p2.ordinal()) {
                return -1;
            }
            return 1;
        }
    }

    private class ThrottlerConsumer extends DelegatingConsumer<T, T> {
        private ThrottlerConsumer(Consumer<T> consumer) {
            super(consumer);
        }

        /* access modifiers changed from: protected */
        public void onNewResultImpl(T newResult, int status) {
            getConsumer().onNewResult(newResult, status);
            if (isLast(status)) {
                onRequestFinished();
            }
        }

        /* access modifiers changed from: protected */
        public void onFailureImpl(Throwable t) {
            getConsumer().onFailure(t);
            onRequestFinished();
        }

        /* access modifiers changed from: protected */
        public void onCancellationImpl() {
            getConsumer().onCancellation();
            onRequestFinished();
        }

        private void onRequestFinished() {
            final Item<T> nextRequest;
            synchronized (PriorityStarvingThrottlingProducer.this) {
                nextRequest = (Item) PriorityStarvingThrottlingProducer.this.mPendingRequests.poll();
                if (nextRequest == null) {
                    PriorityStarvingThrottlingProducer.access$210(PriorityStarvingThrottlingProducer.this);
                }
            }
            if (nextRequest != null) {
                PriorityStarvingThrottlingProducer.this.mExecutor.execute(new Runnable() {
                    public void run() {
                        PriorityStarvingThrottlingProducer.this.produceResultsInternal(nextRequest);
                    }
                });
            }
        }
    }
}
