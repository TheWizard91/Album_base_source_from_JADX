package com.facebook.imagepipeline.producers;

import android.util.Pair;
import com.facebook.common.internal.Preconditions;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executor;

public class ThrottlingProducer<T> implements Producer<T> {
    public static final String PRODUCER_NAME = "ThrottlingProducer";
    /* access modifiers changed from: private */
    public final Executor mExecutor;
    private final Producer<T> mInputProducer;
    private final int mMaxSimultaneousRequests;
    private int mNumCurrentRequests = 0;
    /* access modifiers changed from: private */
    public final ConcurrentLinkedQueue<Pair<Consumer<T>, ProducerContext>> mPendingRequests = new ConcurrentLinkedQueue<>();

    static /* synthetic */ int access$210(ThrottlingProducer x0) {
        int i = x0.mNumCurrentRequests;
        x0.mNumCurrentRequests = i - 1;
        return i;
    }

    public ThrottlingProducer(int maxSimultaneousRequests, Executor executor, Producer<T> inputProducer) {
        this.mMaxSimultaneousRequests = maxSimultaneousRequests;
        this.mExecutor = (Executor) Preconditions.checkNotNull(executor);
        this.mInputProducer = (Producer) Preconditions.checkNotNull(inputProducer);
    }

    public void produceResults(Consumer<T> consumer, ProducerContext producerContext) {
        boolean delayRequest;
        producerContext.getProducerListener().onProducerStart(producerContext, PRODUCER_NAME);
        synchronized (this) {
            int i = this.mNumCurrentRequests;
            if (i >= this.mMaxSimultaneousRequests) {
                this.mPendingRequests.add(Pair.create(consumer, producerContext));
                delayRequest = true;
            } else {
                this.mNumCurrentRequests = i + 1;
                delayRequest = false;
            }
        }
        if (!delayRequest) {
            produceResultsInternal(consumer, producerContext);
        }
    }

    /* access modifiers changed from: package-private */
    public void produceResultsInternal(Consumer<T> consumer, ProducerContext producerContext) {
        producerContext.getProducerListener().onProducerFinishWithSuccess(producerContext, PRODUCER_NAME, (Map<String, String>) null);
        this.mInputProducer.produceResults(new ThrottlerConsumer(consumer), producerContext);
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
            final Pair<Consumer<T>, ProducerContext> nextRequestPair;
            synchronized (ThrottlingProducer.this) {
                nextRequestPair = (Pair) ThrottlingProducer.this.mPendingRequests.poll();
                if (nextRequestPair == null) {
                    ThrottlingProducer.access$210(ThrottlingProducer.this);
                }
            }
            if (nextRequestPair != null) {
                ThrottlingProducer.this.mExecutor.execute(new Runnable() {
                    public void run() {
                        ThrottlingProducer.this.produceResultsInternal((Consumer) nextRequestPair.first, (ProducerContext) nextRequestPair.second);
                    }
                });
            }
        }
    }
}
