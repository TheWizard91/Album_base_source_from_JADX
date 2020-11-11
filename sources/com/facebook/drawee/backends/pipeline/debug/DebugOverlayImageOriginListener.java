package com.facebook.drawee.backends.pipeline.debug;

import com.facebook.drawee.backends.pipeline.info.ImageOriginListener;

public class DebugOverlayImageOriginListener implements ImageOriginListener {
    private int mImageOrigin = 1;

    public void onImageLoaded(String controllerId, int imageOrigin, boolean successful, String ultimateProducerName) {
        this.mImageOrigin = imageOrigin;
    }

    public int getImageOrigin() {
        return this.mImageOrigin;
    }
}
