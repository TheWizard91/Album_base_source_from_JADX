package com.facebook.imagepipeline.cache;

import android.net.Uri;
import com.facebook.cache.common.CacheKey;
import com.facebook.common.internal.Objects;
import com.facebook.common.internal.Preconditions;
import com.facebook.common.time.RealtimeSinceBootClock;
import com.facebook.common.util.HashCodeUtil;
import com.facebook.imagepipeline.common.ImageDecodeOptions;
import com.facebook.imagepipeline.common.ResizeOptions;
import com.facebook.imagepipeline.common.RotationOptions;
import java.util.Locale;
import javax.annotation.Nullable;

public class BitmapMemoryCacheKey implements CacheKey {
    private final long mCacheTime;
    private final Object mCallerContext;
    private final int mHash;
    private final ImageDecodeOptions mImageDecodeOptions;
    @Nullable
    private final CacheKey mPostprocessorCacheKey;
    @Nullable
    private final String mPostprocessorName;
    @Nullable
    private final ResizeOptions mResizeOptions;
    private final RotationOptions mRotationOptions;
    private final String mSourceString;

    public BitmapMemoryCacheKey(String sourceString, @Nullable ResizeOptions resizeOptions, RotationOptions rotationOptions, ImageDecodeOptions imageDecodeOptions, @Nullable CacheKey postprocessorCacheKey, @Nullable String postprocessorName, Object callerContext) {
        this.mSourceString = (String) Preconditions.checkNotNull(sourceString);
        this.mResizeOptions = resizeOptions;
        this.mRotationOptions = rotationOptions;
        this.mImageDecodeOptions = imageDecodeOptions;
        this.mPostprocessorCacheKey = postprocessorCacheKey;
        this.mPostprocessorName = postprocessorName;
        this.mHash = HashCodeUtil.hashCode((Object) Integer.valueOf(sourceString.hashCode()), (Object) Integer.valueOf(resizeOptions != null ? resizeOptions.hashCode() : 0), (Object) Integer.valueOf(rotationOptions.hashCode()), (Object) imageDecodeOptions, (Object) postprocessorCacheKey, (Object) postprocessorName);
        this.mCallerContext = callerContext;
        this.mCacheTime = RealtimeSinceBootClock.get().now();
    }

    public boolean equals(Object o) {
        if (!(o instanceof BitmapMemoryCacheKey)) {
            return false;
        }
        BitmapMemoryCacheKey otherKey = (BitmapMemoryCacheKey) o;
        if (this.mHash != otherKey.mHash || !this.mSourceString.equals(otherKey.mSourceString) || !Objects.equal(this.mResizeOptions, otherKey.mResizeOptions) || !Objects.equal(this.mRotationOptions, otherKey.mRotationOptions) || !Objects.equal(this.mImageDecodeOptions, otherKey.mImageDecodeOptions) || !Objects.equal(this.mPostprocessorCacheKey, otherKey.mPostprocessorCacheKey) || !Objects.equal(this.mPostprocessorName, otherKey.mPostprocessorName)) {
            return false;
        }
        return true;
    }

    public int hashCode() {
        return this.mHash;
    }

    public boolean containsUri(Uri uri) {
        return getUriString().contains(uri.toString());
    }

    public String getUriString() {
        return this.mSourceString;
    }

    @Nullable
    public String getPostprocessorName() {
        return this.mPostprocessorName;
    }

    public String toString() {
        return String.format((Locale) null, "%s_%s_%s_%s_%s_%s_%d", new Object[]{this.mSourceString, this.mResizeOptions, this.mRotationOptions, this.mImageDecodeOptions, this.mPostprocessorCacheKey, this.mPostprocessorName, Integer.valueOf(this.mHash)});
    }

    public boolean isResourceIdForDebugging() {
        return false;
    }

    public Object getCallerContext() {
        return this.mCallerContext;
    }

    public long getInBitmapCacheSince() {
        return this.mCacheTime;
    }
}
