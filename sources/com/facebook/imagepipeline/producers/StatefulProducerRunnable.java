package com.facebook.imagepipeline.producers;

import com.facebook.common.executors.StatefulRunnable;
import java.util.Map;
import javax.annotation.Nullable;

public abstract class StatefulProducerRunnable<T> extends StatefulRunnable<T> {
    private final Consumer<T> mConsumer;
    private final ProducerContext mProducerContext;
    private final ProducerListener2 mProducerListener;
    private final String mProducerName;

    /* access modifiers changed from: protected */
    public abstract void disposeResult(T t);

    public StatefulProducerRunnable(Consumer<T> consumer, ProducerListener2 producerListener, ProducerContext producerContext, String producerName) {
        this.mConsumer = consumer;
        this.mProducerListener = producerListener;
        this.mProducerName = producerName;
        this.mProducerContext = producerContext;
        producerListener.onProducerStart(producerContext, producerName);
    }

    /* access modifiers changed from: protected */
    public void onSuccess(T result) {
        ProducerListener2 producerListener2 = this.mProducerListener;
        ProducerContext producerContext = this.mProducerContext;
        String str = this.mProducerName;
        producerListener2.onProducerFinishWithSuccess(producerContext, str, producerListener2.requiresExtraMap(producerContext, str) ? getExtraMapOnSuccess(result) : null);
        this.mConsumer.onNewResult(result, 1);
    }

    /* access modifiers changed from: protected */
    public void onFailure(Exception e) {
        Map<String, String> map;
        ProducerListener2 producerListener2 = this.mProducerListener;
        ProducerContext producerContext = this.mProducerContext;
        String str = this.mProducerName;
        if (producerListener2.requiresExtraMap(producerContext, str)) {
            map = getExtraMapOnFailure(e);
        } else {
            map = null;
        }
        producerListener2.onProducerFinishWithFailure(producerContext, str, e, map);
        this.mConsumer.onFailure(e);
    }

    /* access modifiers changed from: protected */
    public void onCancellation() {
        ProducerListener2 producerListener2 = this.mProducerListener;
        ProducerContext producerContext = this.mProducerContext;
        String str = this.mProducerName;
        producerListener2.onProducerFinishWithCancellation(producerContext, str, producerListener2.requiresExtraMap(producerContext, str) ? getExtraMapOnCancellation() : null);
        this.mConsumer.onCancellation();
    }

    /* access modifiers changed from: protected */
    @Nullable
    public Map<String, String> getExtraMapOnSuccess(T t) {
        return null;
    }

    /* access modifiers changed from: protected */
    @Nullable
    public Map<String, String> getExtraMapOnFailure(Exception exception) {
        return null;
    }

    /* access modifiers changed from: protected */
    @Nullable
    public Map<String, String> getExtraMapOnCancellation() {
        return null;
    }
}
