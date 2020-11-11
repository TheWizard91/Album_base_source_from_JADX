package com.facebook.imagepipeline.common;

import android.graphics.Bitmap;
import android.graphics.ColorSpace;
import com.facebook.common.internal.Objects;
import com.facebook.imagepipeline.decoder.ImageDecoder;
import com.facebook.imagepipeline.transformation.BitmapTransformation;
import javax.annotation.Nullable;

public class ImageDecodeOptions {
    private static final ImageDecodeOptions DEFAULTS = newBuilder().build();
    public final Bitmap.Config bitmapConfig;
    @Nullable
    public final BitmapTransformation bitmapTransformation;
    @Nullable
    public final ColorSpace colorSpace;
    @Nullable
    public final ImageDecoder customImageDecoder;
    public final boolean decodeAllFrames;
    public final boolean decodePreviewFrame;
    public final boolean forceStaticImage;
    public final int maxDimensionPx;
    public final int minDecodeIntervalMs;
    public final boolean useLastFrameForPreview;

    public ImageDecodeOptions(ImageDecodeOptionsBuilder b) {
        this.minDecodeIntervalMs = b.getMinDecodeIntervalMs();
        this.maxDimensionPx = b.getMaxDimensionPx();
        this.decodePreviewFrame = b.getDecodePreviewFrame();
        this.useLastFrameForPreview = b.getUseLastFrameForPreview();
        this.decodeAllFrames = b.getDecodeAllFrames();
        this.forceStaticImage = b.getForceStaticImage();
        this.bitmapConfig = b.getBitmapConfig();
        this.customImageDecoder = b.getCustomImageDecoder();
        this.bitmapTransformation = b.getBitmapTransformation();
        this.colorSpace = b.getColorSpace();
    }

    public static ImageDecodeOptions defaults() {
        return DEFAULTS;
    }

    public static ImageDecodeOptionsBuilder newBuilder() {
        return new ImageDecodeOptionsBuilder();
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ImageDecodeOptions that = (ImageDecodeOptions) o;
        if (this.minDecodeIntervalMs == that.minDecodeIntervalMs && this.maxDimensionPx == that.maxDimensionPx && this.decodePreviewFrame == that.decodePreviewFrame && this.useLastFrameForPreview == that.useLastFrameForPreview && this.decodeAllFrames == that.decodeAllFrames && this.forceStaticImage == that.forceStaticImage && this.bitmapConfig == that.bitmapConfig && this.customImageDecoder == that.customImageDecoder && this.bitmapTransformation == that.bitmapTransformation && this.colorSpace == that.colorSpace) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        int result = ((((((((((((this.minDecodeIntervalMs * 31) + this.maxDimensionPx) * 31) + (this.decodePreviewFrame ? 1 : 0)) * 31) + (this.useLastFrameForPreview ? 1 : 0)) * 31) + (this.decodeAllFrames ? 1 : 0)) * 31) + (this.forceStaticImage ? 1 : 0)) * 31) + this.bitmapConfig.ordinal()) * 31;
        ImageDecoder imageDecoder = this.customImageDecoder;
        int i = 0;
        int result2 = (result + (imageDecoder != null ? imageDecoder.hashCode() : 0)) * 31;
        BitmapTransformation bitmapTransformation2 = this.bitmapTransformation;
        int result3 = (result2 + (bitmapTransformation2 != null ? bitmapTransformation2.hashCode() : 0)) * 31;
        ColorSpace colorSpace2 = this.colorSpace;
        if (colorSpace2 != null) {
            i = colorSpace2.hashCode();
        }
        return result3 + i;
    }

    public String toString() {
        return "ImageDecodeOptions{" + toStringHelper().toString() + "}";
    }

    /* access modifiers changed from: protected */
    public Objects.ToStringHelper toStringHelper() {
        return Objects.toStringHelper((Object) this).add("minDecodeIntervalMs", this.minDecodeIntervalMs).add("maxDimensionPx", this.maxDimensionPx).add("decodePreviewFrame", this.decodePreviewFrame).add("useLastFrameForPreview", this.useLastFrameForPreview).add("decodeAllFrames", this.decodeAllFrames).add("forceStaticImage", this.forceStaticImage).add("bitmapConfigName", (Object) this.bitmapConfig.name()).add("customImageDecoder", (Object) this.customImageDecoder).add("bitmapTransformation", (Object) this.bitmapTransformation).add("colorSpace", (Object) this.colorSpace);
    }
}
