package com.facebook.drawee.debug.listener;

import android.graphics.drawable.Animatable;
import com.facebook.drawee.controller.BaseControllerListener;
import javax.annotation.Nullable;

public class ImageLoadingTimeControllerListener extends BaseControllerListener {
    private long mFinalImageSetTimeMs = -1;
    @Nullable
    private ImageLoadingTimeListener mImageLoadingTimeListener;
    private long mRequestSubmitTimeMs = -1;

    public ImageLoadingTimeControllerListener(@Nullable ImageLoadingTimeListener imageLoadingTimeListener) {
        this.mImageLoadingTimeListener = imageLoadingTimeListener;
    }

    public void onSubmit(String id, Object callerContext) {
        this.mRequestSubmitTimeMs = System.currentTimeMillis();
    }

    public void onFinalImageSet(String id, @Nullable Object imageInfo, @Nullable Animatable animatable) {
        long currentTimeMillis = System.currentTimeMillis();
        this.mFinalImageSetTimeMs = currentTimeMillis;
        ImageLoadingTimeListener imageLoadingTimeListener = this.mImageLoadingTimeListener;
        if (imageLoadingTimeListener != null) {
            imageLoadingTimeListener.onFinalImageSet(currentTimeMillis - this.mRequestSubmitTimeMs);
        }
    }
}
