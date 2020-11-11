package com.facebook.imagepipeline.transcoder;

import com.facebook.imageformat.ImageFormat;

public class SimpleImageTranscoderFactory implements ImageTranscoderFactory {
    private final int mMaxBitmapSize;

    public SimpleImageTranscoderFactory(int maxBitmapSize) {
        this.mMaxBitmapSize = maxBitmapSize;
    }

    public ImageTranscoder createImageTranscoder(ImageFormat imageFormat, boolean isResizingEnabled) {
        return new SimpleImageTranscoder(isResizingEnabled, this.mMaxBitmapSize);
    }
}
