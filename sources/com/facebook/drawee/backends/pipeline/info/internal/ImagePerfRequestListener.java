package com.facebook.drawee.backends.pipeline.info.internal;

import com.facebook.common.time.MonotonicClock;
import com.facebook.drawee.backends.pipeline.info.ImagePerfState;
import com.facebook.imagepipeline.listener.BaseRequestListener;
import com.facebook.imagepipeline.request.ImageRequest;

public class ImagePerfRequestListener extends BaseRequestListener {
    private final MonotonicClock mClock;
    private final ImagePerfState mImagePerfState;

    public ImagePerfRequestListener(MonotonicClock monotonicClock, ImagePerfState imagePerfState) {
        this.mClock = monotonicClock;
        this.mImagePerfState = imagePerfState;
    }

    public void onRequestStart(ImageRequest request, Object callerContext, String requestId, boolean isPrefetch) {
        this.mImagePerfState.setImageRequestStartTimeMs(this.mClock.now());
        this.mImagePerfState.setImageRequest(request);
        this.mImagePerfState.setCallerContext(callerContext);
        this.mImagePerfState.setRequestId(requestId);
        this.mImagePerfState.setPrefetch(isPrefetch);
    }

    public void onRequestSuccess(ImageRequest request, String requestId, boolean isPrefetch) {
        this.mImagePerfState.setImageRequestEndTimeMs(this.mClock.now());
        this.mImagePerfState.setImageRequest(request);
        this.mImagePerfState.setRequestId(requestId);
        this.mImagePerfState.setPrefetch(isPrefetch);
    }

    public void onRequestFailure(ImageRequest request, String requestId, Throwable throwable, boolean isPrefetch) {
        this.mImagePerfState.setImageRequestEndTimeMs(this.mClock.now());
        this.mImagePerfState.setImageRequest(request);
        this.mImagePerfState.setRequestId(requestId);
        this.mImagePerfState.setPrefetch(isPrefetch);
    }

    public void onRequestCancellation(String requestId) {
        this.mImagePerfState.setImageRequestEndTimeMs(this.mClock.now());
        this.mImagePerfState.setRequestId(requestId);
    }
}
