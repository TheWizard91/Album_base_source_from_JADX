package com.facebook.imagepipeline.image;

import android.net.Uri;
import javax.annotation.Nullable;

public class OriginalEncodedImageInfo {
    @Nullable
    private final Object mCallerContext;
    private final int mHeight;
    @Nullable
    private final EncodedImageOrigin mOrigin;
    private final int mSize;
    private final Uri mUri;
    private final int mWidth;

    public OriginalEncodedImageInfo(Uri sourceUri, EncodedImageOrigin origin, @Nullable Object callerContext, int width, int height, int size) {
        this.mUri = sourceUri;
        this.mOrigin = origin;
        this.mCallerContext = callerContext;
        this.mWidth = width;
        this.mHeight = height;
        this.mSize = size;
    }

    public int getWidth() {
        return this.mWidth;
    }

    public int getHeight() {
        return this.mHeight;
    }

    public int getSize() {
        return this.mSize;
    }

    public Uri getUri() {
        return this.mUri;
    }

    @Nullable
    public Object getCallerContext() {
        return this.mCallerContext;
    }

    public EncodedImageOrigin getOrigin() {
        return this.mOrigin;
    }
}
