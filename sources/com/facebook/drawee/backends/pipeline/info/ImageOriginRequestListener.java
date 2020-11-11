package com.facebook.drawee.backends.pipeline.info;

import com.facebook.imagepipeline.listener.BaseRequestListener;
import javax.annotation.Nullable;

public class ImageOriginRequestListener extends BaseRequestListener {
    private String mControllerId;
    @Nullable
    private final ImageOriginListener mImageOriginLister;

    public ImageOriginRequestListener(String controllerId, @Nullable ImageOriginListener imageOriginLister) {
        this.mImageOriginLister = imageOriginLister;
        init(controllerId);
    }

    public void init(String controllerId) {
        this.mControllerId = controllerId;
    }

    public void onUltimateProducerReached(String requestId, String ultimateProducerName, boolean successful) {
        ImageOriginListener imageOriginListener = this.mImageOriginLister;
        if (imageOriginListener != null) {
            imageOriginListener.onImageLoaded(this.mControllerId, ImageOriginUtils.mapProducerNameToImageOrigin(ultimateProducerName), successful, ultimateProducerName);
        }
    }
}
