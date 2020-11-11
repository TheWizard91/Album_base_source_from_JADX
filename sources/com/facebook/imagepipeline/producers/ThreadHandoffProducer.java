package com.facebook.imagepipeline.producers;

import com.facebook.common.internal.Preconditions;
import com.facebook.imagepipeline.instrumentation.FrescoInstrumenter;
import com.facebook.imagepipeline.systrace.FrescoSystrace;
import java.util.Map;
import javax.annotation.Nullable;

public class ThreadHandoffProducer<T> implements Producer<T> {
    public static final String PRODUCER_NAME = "BackgroundThreadHandoffProducer";
    /* access modifiers changed from: private */
    public final Producer<T> mInputProducer;
    /* access modifiers changed from: private */
    public final ThreadHandoffProducerQueue mThreadHandoffProducerQueue;

    public ThreadHandoffProducer(Producer<T> inputProducer, ThreadHandoffProducerQueue inputThreadHandoffProducerQueue) {
        this.mInputProducer = (Producer) Preconditions.checkNotNull(inputProducer);
        this.mThreadHandoffProducerQueue = inputThreadHandoffProducerQueue;
    }

    public void produceResults(Consumer<T> consumer, ProducerContext context) {
        try {
            if (FrescoSystrace.isTracing()) {
                FrescoSystrace.beginSection("ThreadHandoffProducer#produceResults");
            }
            ProducerListener2 producerListener = context.getProducerListener();
            final ProducerListener2 producerListener2 = producerListener;
            final ProducerContext producerContext = context;
            final Consumer<T> consumer2 = consumer;
            final C07751 r1 = new StatefulProducerRunnable<T>(consumer, producerListener, context, PRODUCER_NAME) {
                /* access modifiers changed from: protected */
                public void onSuccess(T t) {
                    producerListener2.onProducerFinishWithSuccess(producerContext, ThreadHandoffProducer.PRODUCER_NAME, (Map<String, String>) null);
                    ThreadHandoffProducer.this.mInputProducer.produceResults(consumer2, producerContext);
                }

                /* access modifiers changed from: protected */
                public void disposeResult(T t) {
                }

                /* access modifiers changed from: protected */
                @Nullable
                public T getResult() throws Exception {
                    return null;
                }
            };
            context.addCallbacks(new BaseProducerContextCallbacks() {
                public void onCancellationRequested() {
                    r1.cancel();
                    ThreadHandoffProducer.this.mThreadHandoffProducerQueue.remove(r1);
                }
            });
            this.mThreadHandoffProducerQueue.addToQueueOrExecute(FrescoInstrumenter.decorateRunnable(r1, getInstrumentationTag(context)));
        } finally {
            if (FrescoSystrace.isTracing()) {
                FrescoSystrace.endSection();
            }
        }
    }

    @Nullable
    private static String getInstrumentationTag(ProducerContext context) {
        if (FrescoInstrumenter.isTracing()) {
            return "ThreadHandoffProducer_produceResults_" + context.getId();
        }
        return null;
    }
}
