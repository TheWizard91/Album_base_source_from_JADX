package com.facebook.imagepipeline.postprocessors;

import android.graphics.Bitmap;
import com.facebook.cache.common.CacheKey;
import com.facebook.cache.common.SimpleCacheKey;
import com.facebook.imagepipeline.nativecode.NativeRoundingFilter;
import com.facebook.imagepipeline.request.BasePostprocessor;
import javax.annotation.Nullable;

public class RoundedCornersPostprocessor extends BasePostprocessor {
    @Nullable
    private CacheKey mCacheKey;

    public void process(Bitmap bitmap) {
        int radius = Math.min(bitmap.getHeight(), bitmap.getWidth());
        NativeRoundingFilter.addRoundedCorners(bitmap, radius / 2, radius / 3, radius / 4, radius / 5);
    }

    @Nullable
    public CacheKey getPostprocessorCacheKey() {
        if (this.mCacheKey == null) {
            this.mCacheKey = new SimpleCacheKey("RoundedCornersPostprocessor");
        }
        return this.mCacheKey;
    }
}
