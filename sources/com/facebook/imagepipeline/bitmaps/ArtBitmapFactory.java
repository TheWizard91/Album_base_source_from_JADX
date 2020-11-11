package com.facebook.imagepipeline.bitmaps;

import android.graphics.Bitmap;
import com.facebook.common.internal.Preconditions;
import com.facebook.common.references.CloseableReference;
import com.facebook.imagepipeline.core.CloseableReferenceFactory;
import com.facebook.imagepipeline.memory.BitmapPool;
import com.facebook.imageutils.BitmapUtil;

public class ArtBitmapFactory extends PlatformBitmapFactory {
    private final BitmapPool mBitmapPool;
    private final CloseableReferenceFactory mCloseableReferenceFactory;

    public ArtBitmapFactory(BitmapPool bitmapPool, CloseableReferenceFactory closeableReferenceFactory) {
        this.mBitmapPool = bitmapPool;
        this.mCloseableReferenceFactory = closeableReferenceFactory;
    }

    public CloseableReference<Bitmap> createBitmapInternal(int width, int height, Bitmap.Config bitmapConfig) {
        Bitmap bitmap = (Bitmap) this.mBitmapPool.get(BitmapUtil.getSizeInByteForBitmap(width, height, bitmapConfig));
        Preconditions.checkArgument(bitmap.getAllocationByteCount() >= (width * height) * BitmapUtil.getPixelSizeForBitmapConfig(bitmapConfig));
        bitmap.reconfigure(width, height, bitmapConfig);
        return this.mCloseableReferenceFactory.create(bitmap, this.mBitmapPool);
    }
}
