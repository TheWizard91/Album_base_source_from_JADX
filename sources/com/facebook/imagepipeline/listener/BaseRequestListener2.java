package com.facebook.imagepipeline.listener;

import com.facebook.imagepipeline.producers.ProducerContext;
import java.util.Map;
import javax.annotation.Nullable;

public class BaseRequestListener2 implements RequestListener2 {
    public void onRequestStart(ProducerContext producerContext) {
    }

    public void onRequestSuccess(ProducerContext producerContext) {
    }

    public void onRequestFailure(ProducerContext producerContext, Throwable throwable) {
    }

    public void onRequestCancellation(ProducerContext producerContext) {
    }

    public void onProducerStart(ProducerContext producerContext, String producerName) {
    }

    public void onProducerEvent(ProducerContext producerContext, String producerName, String eventName) {
    }

    public void onProducerFinishWithSuccess(ProducerContext producerContext, String producerName, @Nullable Map<String, String> map) {
    }

    public void onProducerFinishWithFailure(ProducerContext producerContext, String producerName, Throwable t, @Nullable Map<String, String> map) {
    }

    public void onProducerFinishWithCancellation(ProducerContext producerContext, String producerName, @Nullable Map<String, String> map) {
    }

    public void onUltimateProducerReached(ProducerContext producerContext, String producerName, boolean successful) {
    }

    public boolean requiresExtraMap(ProducerContext producerContext, String producerName) {
        return false;
    }
}
