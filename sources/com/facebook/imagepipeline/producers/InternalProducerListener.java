package com.facebook.imagepipeline.producers;

import java.util.Map;
import javax.annotation.Nullable;

public class InternalProducerListener implements ProducerListener2 {
    private final ProducerListener mProducerListener;
    @Nullable
    private final ProducerListener2 mProducerListener2;

    public InternalProducerListener(ProducerListener producerListener, @Nullable ProducerListener2 producerListener2) {
        this.mProducerListener = producerListener;
        this.mProducerListener2 = producerListener2;
    }

    public ProducerListener getProducerListener() {
        return this.mProducerListener;
    }

    @Nullable
    public ProducerListener2 getProducerListener2() {
        return this.mProducerListener2;
    }

    public void onProducerStart(ProducerContext context, String producerName) {
        ProducerListener producerListener = this.mProducerListener;
        if (producerListener != null) {
            producerListener.onProducerStart(context.getId(), producerName);
        }
        ProducerListener2 producerListener2 = this.mProducerListener2;
        if (producerListener2 != null) {
            producerListener2.onProducerStart(context, producerName);
        }
    }

    public void onProducerEvent(ProducerContext context, String producerName, String eventName) {
        ProducerListener producerListener = this.mProducerListener;
        if (producerListener != null) {
            producerListener.onProducerEvent(context.getId(), producerName, eventName);
        }
        ProducerListener2 producerListener2 = this.mProducerListener2;
        if (producerListener2 != null) {
            producerListener2.onProducerEvent(context, producerName, eventName);
        }
    }

    public void onProducerFinishWithSuccess(ProducerContext context, String producerName, @Nullable Map<String, String> extraMap) {
        ProducerListener producerListener = this.mProducerListener;
        if (producerListener != null) {
            producerListener.onProducerFinishWithSuccess(context.getId(), producerName, extraMap);
        }
        ProducerListener2 producerListener2 = this.mProducerListener2;
        if (producerListener2 != null) {
            producerListener2.onProducerFinishWithSuccess(context, producerName, extraMap);
        }
    }

    public void onProducerFinishWithFailure(ProducerContext context, String producerName, Throwable t, @Nullable Map<String, String> extraMap) {
        ProducerListener producerListener = this.mProducerListener;
        if (producerListener != null) {
            producerListener.onProducerFinishWithFailure(context.getId(), producerName, t, extraMap);
        }
        ProducerListener2 producerListener2 = this.mProducerListener2;
        if (producerListener2 != null) {
            producerListener2.onProducerFinishWithFailure(context, producerName, t, extraMap);
        }
    }

    public void onProducerFinishWithCancellation(ProducerContext context, String producerName, @Nullable Map<String, String> extraMap) {
        ProducerListener producerListener = this.mProducerListener;
        if (producerListener != null) {
            producerListener.onProducerFinishWithCancellation(context.getId(), producerName, extraMap);
        }
        ProducerListener2 producerListener2 = this.mProducerListener2;
        if (producerListener2 != null) {
            producerListener2.onProducerFinishWithCancellation(context, producerName, extraMap);
        }
    }

    public void onUltimateProducerReached(ProducerContext context, String producerName, boolean successful) {
        ProducerListener producerListener = this.mProducerListener;
        if (producerListener != null) {
            producerListener.onUltimateProducerReached(context.getId(), producerName, successful);
        }
        ProducerListener2 producerListener2 = this.mProducerListener2;
        if (producerListener2 != null) {
            producerListener2.onUltimateProducerReached(context, producerName, successful);
        }
    }

    public boolean requiresExtraMap(ProducerContext context, String producerName) {
        ProducerListener2 producerListener2;
        boolean required = false;
        ProducerListener producerListener = this.mProducerListener;
        if (producerListener != null) {
            required = producerListener.requiresExtraMap(context.getId());
        }
        if (required || (producerListener2 = this.mProducerListener2) == null) {
            return required;
        }
        return producerListener2.requiresExtraMap(context, producerName);
    }
}
