package com.facebook.imagepipeline.producers;

import android.util.SparseArray;
import com.facebook.imagepipeline.common.Priority;
import com.facebook.imagepipeline.core.ImagePipelineConfig;
import com.facebook.imagepipeline.image.EncodedImageOrigin;
import com.facebook.imagepipeline.request.ImageRequest;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nullable;

public class BaseProducerContext implements ProducerContext {
    private final List<ProducerContextCallbacks> mCallbacks;
    private final Object mCallerContext;
    private EncodedImageOrigin mEncodedImageOrigin;
    private final SparseArray<String> mExtras;
    private final String mId;
    private final ImagePipelineConfig mImagePipelineConfig;
    private final ImageRequest mImageRequest;
    private boolean mIsCancelled;
    private boolean mIsIntermediateResultExpected;
    private boolean mIsPrefetch;
    private final ImageRequest.RequestLevel mLowestPermittedRequestLevel;
    private Priority mPriority;
    private final ProducerListener2 mProducerListener;
    @Nullable
    private final String mUiComponentId;

    public BaseProducerContext(ImageRequest imageRequest, String id, ProducerListener2 producerListener, Object callerContext, ImageRequest.RequestLevel lowestPermittedRequestLevel, boolean isPrefetch, boolean isIntermediateResultExpected, Priority priority, ImagePipelineConfig imagePipelineConfig) {
        this(imageRequest, id, (String) null, producerListener, callerContext, lowestPermittedRequestLevel, isPrefetch, isIntermediateResultExpected, priority, imagePipelineConfig);
    }

    public BaseProducerContext(ImageRequest imageRequest, String id, @Nullable String uiComponentId, ProducerListener2 producerListener, Object callerContext, ImageRequest.RequestLevel lowestPermittedRequestLevel, boolean isPrefetch, boolean isIntermediateResultExpected, Priority priority, ImagePipelineConfig imagePipelineConfig) {
        this.mExtras = new SparseArray<>();
        this.mEncodedImageOrigin = EncodedImageOrigin.NOT_SET;
        this.mImageRequest = imageRequest;
        this.mId = id;
        this.mUiComponentId = uiComponentId;
        this.mProducerListener = producerListener;
        this.mCallerContext = callerContext;
        this.mLowestPermittedRequestLevel = lowestPermittedRequestLevel;
        this.mIsPrefetch = isPrefetch;
        this.mPriority = priority;
        this.mIsIntermediateResultExpected = isIntermediateResultExpected;
        this.mIsCancelled = false;
        this.mCallbacks = new ArrayList();
        this.mImagePipelineConfig = imagePipelineConfig;
    }

    public ImageRequest getImageRequest() {
        return this.mImageRequest;
    }

    public String getId() {
        return this.mId;
    }

    @Nullable
    public String getUiComponentId() {
        return this.mUiComponentId;
    }

    public ProducerListener2 getProducerListener() {
        return this.mProducerListener;
    }

    public Object getCallerContext() {
        return this.mCallerContext;
    }

    public ImageRequest.RequestLevel getLowestPermittedRequestLevel() {
        return this.mLowestPermittedRequestLevel;
    }

    public synchronized boolean isPrefetch() {
        return this.mIsPrefetch;
    }

    public synchronized Priority getPriority() {
        return this.mPriority;
    }

    public synchronized boolean isIntermediateResultExpected() {
        return this.mIsIntermediateResultExpected;
    }

    public synchronized boolean isCancelled() {
        return this.mIsCancelled;
    }

    public void addCallbacks(ProducerContextCallbacks callbacks) {
        boolean cancelImmediately = false;
        synchronized (this) {
            this.mCallbacks.add(callbacks);
            if (this.mIsCancelled) {
                cancelImmediately = true;
            }
        }
        if (cancelImmediately) {
            callbacks.onCancellationRequested();
        }
    }

    public ImagePipelineConfig getImagePipelineConfig() {
        return this.mImagePipelineConfig;
    }

    public EncodedImageOrigin getEncodedImageOrigin() {
        return this.mEncodedImageOrigin;
    }

    public void setEncodedImageOrigin(EncodedImageOrigin encodedImageOrigin) {
        this.mEncodedImageOrigin = encodedImageOrigin;
    }

    public void cancel() {
        callOnCancellationRequested(cancelNoCallbacks());
    }

    @Nullable
    public synchronized List<ProducerContextCallbacks> setIsPrefetchNoCallbacks(boolean isPrefetch) {
        if (isPrefetch == this.mIsPrefetch) {
            return null;
        }
        this.mIsPrefetch = isPrefetch;
        return new ArrayList(this.mCallbacks);
    }

    @Nullable
    public synchronized List<ProducerContextCallbacks> setPriorityNoCallbacks(Priority priority) {
        if (priority == this.mPriority) {
            return null;
        }
        this.mPriority = priority;
        return new ArrayList(this.mCallbacks);
    }

    @Nullable
    public synchronized List<ProducerContextCallbacks> setIsIntermediateResultExpectedNoCallbacks(boolean isIntermediateResultExpected) {
        if (isIntermediateResultExpected == this.mIsIntermediateResultExpected) {
            return null;
        }
        this.mIsIntermediateResultExpected = isIntermediateResultExpected;
        return new ArrayList(this.mCallbacks);
    }

    @Nullable
    public synchronized List<ProducerContextCallbacks> cancelNoCallbacks() {
        if (this.mIsCancelled) {
            return null;
        }
        this.mIsCancelled = true;
        return new ArrayList(this.mCallbacks);
    }

    public static void callOnCancellationRequested(@Nullable List<ProducerContextCallbacks> callbacks) {
        if (callbacks != null) {
            for (ProducerContextCallbacks callback : callbacks) {
                callback.onCancellationRequested();
            }
        }
    }

    public static void callOnIsPrefetchChanged(@Nullable List<ProducerContextCallbacks> callbacks) {
        if (callbacks != null) {
            for (ProducerContextCallbacks callback : callbacks) {
                callback.onIsPrefetchChanged();
            }
        }
    }

    public static void callOnIsIntermediateResultExpectedChanged(@Nullable List<ProducerContextCallbacks> callbacks) {
        if (callbacks != null) {
            for (ProducerContextCallbacks callback : callbacks) {
                callback.onIsIntermediateResultExpectedChanged();
            }
        }
    }

    public static void callOnPriorityChanged(@Nullable List<ProducerContextCallbacks> callbacks) {
        if (callbacks != null) {
            for (ProducerContextCallbacks callback : callbacks) {
                callback.onPriorityChanged();
            }
        }
    }

    public void setExtra(int key, String value) {
        this.mExtras.put(key, value);
    }

    public String getExtra(int key) {
        return this.mExtras.get(key, "");
    }
}
