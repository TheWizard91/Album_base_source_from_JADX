package com.facebook.imagepipeline.common;

import com.facebook.common.internal.Preconditions;
import com.facebook.common.util.HashCodeUtil;
import java.util.Locale;
import javax.annotation.Nullable;

public class ResizeOptions {
    public static final float DEFAULT_ROUNDUP_FRACTION = 0.6666667f;
    public final int height;
    public final float maxBitmapSize;
    public final float roundUpFraction;
    public final int width;

    @Nullable
    public static ResizeOptions forDimensions(int width2, int height2) {
        if (width2 <= 0 || height2 <= 0) {
            return null;
        }
        return new ResizeOptions(width2, height2);
    }

    @Nullable
    public static ResizeOptions forSquareSize(int size) {
        if (size <= 0) {
            return null;
        }
        return new ResizeOptions(size, size);
    }

    public ResizeOptions(int width2, int height2) {
        this(width2, height2, 2048.0f);
    }

    public ResizeOptions(int width2, int height2, float maxBitmapSize2) {
        this(width2, height2, maxBitmapSize2, 0.6666667f);
    }

    public ResizeOptions(int width2, int height2, float maxBitmapSize2, float roundUpFraction2) {
        boolean z = true;
        Preconditions.checkArgument(width2 > 0);
        Preconditions.checkArgument(height2 <= 0 ? false : z);
        this.width = width2;
        this.height = height2;
        this.maxBitmapSize = maxBitmapSize2;
        this.roundUpFraction = roundUpFraction2;
    }

    public int hashCode() {
        return HashCodeUtil.hashCode(this.width, this.height);
    }

    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof ResizeOptions)) {
            return false;
        }
        ResizeOptions that = (ResizeOptions) other;
        if (this.width == that.width && this.height == that.height) {
            return true;
        }
        return false;
    }

    public String toString() {
        return String.format((Locale) null, "%dx%d", new Object[]{Integer.valueOf(this.width), Integer.valueOf(this.height)});
    }
}
