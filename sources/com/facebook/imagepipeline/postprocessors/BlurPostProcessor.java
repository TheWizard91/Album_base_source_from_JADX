package com.facebook.imagepipeline.postprocessors;

import android.content.Context;
import android.graphics.Bitmap;
import com.facebook.cache.common.CacheKey;
import com.facebook.cache.common.SimpleCacheKey;
import com.facebook.common.internal.Preconditions;
import com.facebook.imagepipeline.filter.IterativeBoxBlurFilter;
import com.facebook.imagepipeline.filter.RenderScriptBlurFilter;
import com.facebook.imagepipeline.request.BasePostprocessor;
import java.util.Locale;
import javax.annotation.Nullable;

public class BlurPostProcessor extends BasePostprocessor {
    private static final int DEFAULT_ITERATIONS = 3;
    private static final boolean canUseRenderScript = RenderScriptBlurFilter.canUseRenderScript();
    private final int mBlurRadius;
    private CacheKey mCacheKey;
    private final Context mContext;
    private final int mIterations;

    public BlurPostProcessor(int blurRadius, Context context, int iterations) {
        boolean z = true;
        Preconditions.checkArgument(blurRadius > 0 && blurRadius <= 25);
        Preconditions.checkArgument(iterations <= 0 ? false : z);
        Preconditions.checkNotNull(context);
        this.mIterations = iterations;
        this.mBlurRadius = blurRadius;
        this.mContext = context;
    }

    public BlurPostProcessor(int blurRadius, Context context) {
        this(blurRadius, context, 3);
    }

    public void process(Bitmap destBitmap, Bitmap sourceBitmap) {
        if (canUseRenderScript) {
            RenderScriptBlurFilter.blurBitmap(destBitmap, sourceBitmap, this.mContext, this.mBlurRadius);
        } else {
            super.process(destBitmap, sourceBitmap);
        }
    }

    public void process(Bitmap bitmap) {
        IterativeBoxBlurFilter.boxBlurBitmapInPlace(bitmap, this.mIterations, this.mBlurRadius);
    }

    @Nullable
    public CacheKey getPostprocessorCacheKey() {
        String key;
        if (this.mCacheKey == null) {
            if (canUseRenderScript) {
                key = String.format((Locale) null, "IntrinsicBlur;%d", new Object[]{Integer.valueOf(this.mBlurRadius)});
            } else {
                key = String.format((Locale) null, "IterativeBoxBlur;%d;%d", new Object[]{Integer.valueOf(this.mIterations), Integer.valueOf(this.mBlurRadius)});
            }
            this.mCacheKey = new SimpleCacheKey(key);
        }
        return this.mCacheKey;
    }
}
