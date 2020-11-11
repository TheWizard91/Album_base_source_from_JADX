package com.facebook.imagepipeline.listener;

import com.facebook.common.logging.FLog;
import com.facebook.imagepipeline.producers.ProducerContext;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.Nullable;

public class ForwardingRequestListener2 implements RequestListener2 {
    private static final String TAG = "ForwardingRequestListener2";
    private final List<RequestListener2> mRequestListeners;

    public ForwardingRequestListener2(Set<RequestListener2> requestListeners) {
        this.mRequestListeners = new ArrayList(requestListeners.size());
        for (RequestListener2 requestListener : requestListeners) {
            if (requestListener != null) {
                this.mRequestListeners.add(requestListener);
            }
        }
    }

    public ForwardingRequestListener2(RequestListener2... requestListeners) {
        this.mRequestListeners = new ArrayList(requestListeners.length);
        for (RequestListener2 requestListener : requestListeners) {
            if (requestListener != null) {
                this.mRequestListeners.add(requestListener);
            }
        }
    }

    public void addRequestListener(RequestListener2 requestListener) {
        this.mRequestListeners.add(requestListener);
    }

    public void onRequestStart(ProducerContext producerContext) {
        int numberOfListeners = this.mRequestListeners.size();
        for (int i = 0; i < numberOfListeners; i++) {
            try {
                this.mRequestListeners.get(i).onRequestStart(producerContext);
            } catch (Exception exception) {
                onException("InternalListener exception in onRequestStart", exception);
            }
        }
    }

    public void onProducerStart(ProducerContext producerContext, String producerName) {
        int numberOfListeners = this.mRequestListeners.size();
        for (int i = 0; i < numberOfListeners; i++) {
            try {
                this.mRequestListeners.get(i).onProducerStart(producerContext, producerName);
            } catch (Exception exception) {
                onException("InternalListener exception in onProducerStart", exception);
            }
        }
    }

    public void onProducerFinishWithSuccess(ProducerContext producerContext, String producerName, @Nullable Map<String, String> extraMap) {
        int numberOfListeners = this.mRequestListeners.size();
        for (int i = 0; i < numberOfListeners; i++) {
            try {
                this.mRequestListeners.get(i).onProducerFinishWithSuccess(producerContext, producerName, extraMap);
            } catch (Exception exception) {
                onException("InternalListener exception in onProducerFinishWithSuccess", exception);
            }
        }
    }

    public void onProducerFinishWithFailure(ProducerContext producerContext, String producerName, Throwable t, @Nullable Map<String, String> extraMap) {
        int numberOfListeners = this.mRequestListeners.size();
        for (int i = 0; i < numberOfListeners; i++) {
            try {
                this.mRequestListeners.get(i).onProducerFinishWithFailure(producerContext, producerName, t, extraMap);
            } catch (Exception exception) {
                onException("InternalListener exception in onProducerFinishWithFailure", exception);
            }
        }
    }

    public void onProducerFinishWithCancellation(ProducerContext producerContext, String producerName, @Nullable Map<String, String> extraMap) {
        int numberOfListeners = this.mRequestListeners.size();
        for (int i = 0; i < numberOfListeners; i++) {
            try {
                this.mRequestListeners.get(i).onProducerFinishWithCancellation(producerContext, producerName, extraMap);
            } catch (Exception exception) {
                onException("InternalListener exception in onProducerFinishWithCancellation", exception);
            }
        }
    }

    public void onProducerEvent(ProducerContext producerContext, String producerName, String producerEventName) {
        int numberOfListeners = this.mRequestListeners.size();
        for (int i = 0; i < numberOfListeners; i++) {
            try {
                this.mRequestListeners.get(i).onProducerEvent(producerContext, producerName, producerEventName);
            } catch (Exception exception) {
                onException("InternalListener exception in onIntermediateChunkStart", exception);
            }
        }
    }

    public void onUltimateProducerReached(ProducerContext producerContext, String producerName, boolean successful) {
        int numberOfListeners = this.mRequestListeners.size();
        for (int i = 0; i < numberOfListeners; i++) {
            try {
                this.mRequestListeners.get(i).onUltimateProducerReached(producerContext, producerName, successful);
            } catch (Exception exception) {
                onException("InternalListener exception in onProducerFinishWithSuccess", exception);
            }
        }
    }

    public void onRequestSuccess(ProducerContext producerContext) {
        int numberOfListeners = this.mRequestListeners.size();
        for (int i = 0; i < numberOfListeners; i++) {
            try {
                this.mRequestListeners.get(i).onRequestSuccess(producerContext);
            } catch (Exception exception) {
                onException("InternalListener exception in onRequestSuccess", exception);
            }
        }
    }

    public void onRequestFailure(ProducerContext producerContext, Throwable throwable) {
        int numberOfListeners = this.mRequestListeners.size();
        for (int i = 0; i < numberOfListeners; i++) {
            try {
                this.mRequestListeners.get(i).onRequestFailure(producerContext, throwable);
            } catch (Exception exception) {
                onException("InternalListener exception in onRequestFailure", exception);
            }
        }
    }

    public void onRequestCancellation(ProducerContext producerContext) {
        int numberOfListeners = this.mRequestListeners.size();
        for (int i = 0; i < numberOfListeners; i++) {
            try {
                this.mRequestListeners.get(i).onRequestCancellation(producerContext);
            } catch (Exception exception) {
                onException("InternalListener exception in onRequestCancellation", exception);
            }
        }
    }

    public boolean requiresExtraMap(ProducerContext producerContext, String producerName) {
        int numberOfListeners = this.mRequestListeners.size();
        for (int i = 0; i < numberOfListeners; i++) {
            if (this.mRequestListeners.get(i).requiresExtraMap(producerContext, producerName)) {
                return true;
            }
        }
        return false;
    }

    private void onException(String message, Throwable t) {
        FLog.m61e(TAG, message, t);
    }
}
