package com.facebook.imagepipeline.common;

import android.graphics.Bitmap;
import android.graphics.ColorSpace;
import com.facebook.imagepipeline.common.ImageDecodeOptionsBuilder;
import com.facebook.imagepipeline.decoder.ImageDecoder;
import com.facebook.imagepipeline.transformation.BitmapTransformation;
import javax.annotation.Nullable;

public class ImageDecodeOptionsBuilder<T extends ImageDecodeOptionsBuilder> {
    private Bitmap.Config mBitmapConfig = Bitmap.Config.ARGB_8888;
    @Nullable
    private BitmapTransformation mBitmapTransformation;
    @Nullable
    private ColorSpace mColorSpace;
    @Nullable
    private ImageDecoder mCustomImageDecoder;
    private boolean mDecodeAllFrames;
    private boolean mDecodePreviewFrame;
    private boolean mForceStaticImage;
    private int mMaxDimensionPx = Integer.MAX_VALUE;
    private int mMinDecodeIntervalMs = 100;
    private boolean mUseLastFrameForPreview;

    public ImageDecodeOptionsBuilder setFrom(ImageDecodeOptions options) {
        this.mMinDecodeIntervalMs = options.minDecodeIntervalMs;
        this.mMaxDimensionPx = options.maxDimensionPx;
        this.mDecodePreviewFrame = options.decodePreviewFrame;
        this.mUseLastFrameForPreview = options.useLastFrameForPreview;
        this.mDecodeAllFrames = options.decodeAllFrames;
        this.mForceStaticImage = options.forceStaticImage;
        this.mBitmapConfig = options.bitmapConfig;
        this.mCustomImageDecoder = options.customImageDecoder;
        this.mBitmapTransformation = options.bitmapTransformation;
        this.mColorSpace = options.colorSpace;
        return getThis();
    }

    public T setMinDecodeIntervalMs(int intervalMs) {
        this.mMinDecodeIntervalMs = intervalMs;
        return getThis();
    }

    public int getMinDecodeIntervalMs() {
        return this.mMinDecodeIntervalMs;
    }

    public T setMaxDimensionPx(int maxDimensionPx) {
        this.mMaxDimensionPx = maxDimensionPx;
        return getThis();
    }

    public int getMaxDimensionPx() {
        return this.mMaxDimensionPx;
    }

    public T setDecodePreviewFrame(boolean decodePreviewFrame) {
        this.mDecodePreviewFrame = decodePreviewFrame;
        return getThis();
    }

    public boolean getDecodePreviewFrame() {
        return this.mDecodePreviewFrame;
    }

    public boolean getUseLastFrameForPreview() {
        return this.mUseLastFrameForPreview;
    }

    public T setUseLastFrameForPreview(boolean useLastFrameForPreview) {
        this.mUseLastFrameForPreview = useLastFrameForPreview;
        return getThis();
    }

    public boolean getDecodeAllFrames() {
        return this.mDecodeAllFrames;
    }

    public T setDecodeAllFrames(boolean decodeAllFrames) {
        this.mDecodeAllFrames = decodeAllFrames;
        return getThis();
    }

    public T setForceStaticImage(boolean forceStaticImage) {
        this.mForceStaticImage = forceStaticImage;
        return getThis();
    }

    public T setCustomImageDecoder(@Nullable ImageDecoder customImageDecoder) {
        this.mCustomImageDecoder = customImageDecoder;
        return getThis();
    }

    @Nullable
    public ImageDecoder getCustomImageDecoder() {
        return this.mCustomImageDecoder;
    }

    public boolean getForceStaticImage() {
        return this.mForceStaticImage;
    }

    public Bitmap.Config getBitmapConfig() {
        return this.mBitmapConfig;
    }

    public T setBitmapConfig(Bitmap.Config bitmapConfig) {
        this.mBitmapConfig = bitmapConfig;
        return getThis();
    }

    public T setBitmapTransformation(@Nullable BitmapTransformation bitmapTransformation) {
        this.mBitmapTransformation = bitmapTransformation;
        return getThis();
    }

    @Nullable
    public BitmapTransformation getBitmapTransformation() {
        return this.mBitmapTransformation;
    }

    public T setColorSpace(ColorSpace colorSpace) {
        this.mColorSpace = colorSpace;
        return getThis();
    }

    @Nullable
    public ColorSpace getColorSpace() {
        return this.mColorSpace;
    }

    public ImageDecodeOptions build() {
        return new ImageDecodeOptions(this);
    }

    /* access modifiers changed from: protected */
    public T getThis() {
        return this;
    }
}
