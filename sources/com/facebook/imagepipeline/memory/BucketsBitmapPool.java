package com.facebook.imagepipeline.memory;

import android.graphics.Bitmap;
import com.facebook.common.internal.Preconditions;
import com.facebook.common.memory.MemoryTrimmableRegistry;

public class BucketsBitmapPool extends BasePool<Bitmap> implements BitmapPool {
    public BucketsBitmapPool(MemoryTrimmableRegistry memoryTrimmableRegistry, PoolParams poolParams, PoolStatsTracker poolStatsTracker, boolean ignoreHardCap) {
        super(memoryTrimmableRegistry, poolParams, poolStatsTracker, ignoreHardCap);
        initialize();
    }

    /* access modifiers changed from: protected */
    public Bitmap alloc(int size) {
        return Bitmap.createBitmap(1, (int) Math.ceil(((double) size) / 2.0d), Bitmap.Config.RGB_565);
    }

    /* access modifiers changed from: protected */
    public void free(Bitmap value) {
        Preconditions.checkNotNull(value);
        value.recycle();
    }

    /* access modifiers changed from: protected */
    public int getBucketedSize(int requestSize) {
        return requestSize;
    }

    /* access modifiers changed from: protected */
    public int getBucketedSizeForValue(Bitmap value) {
        Preconditions.checkNotNull(value);
        return value.getAllocationByteCount();
    }

    /* access modifiers changed from: protected */
    public int getSizeInBytes(int bucketedSize) {
        return bucketedSize;
    }

    /* access modifiers changed from: protected */
    public boolean isReusable(Bitmap value) {
        Preconditions.checkNotNull(value);
        return !value.isRecycled() && value.isMutable();
    }

    /* JADX WARNING: type inference failed for: r3v0, types: [com.facebook.imagepipeline.memory.Bucket, com.facebook.imagepipeline.memory.Bucket<android.graphics.Bitmap>] */
    /* access modifiers changed from: protected */
    /* JADX WARNING: Unknown variable types count: 1 */
    @javax.annotation.Nullable
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public android.graphics.Bitmap getValue(com.facebook.imagepipeline.memory.Bucket<android.graphics.Bitmap> r3) {
        /*
            r2 = this;
            java.lang.Object r0 = super.getValue(r3)
            android.graphics.Bitmap r0 = (android.graphics.Bitmap) r0
            if (r0 == 0) goto L_0x000c
            r1 = 0
            r0.eraseColor(r1)
        L_0x000c:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.facebook.imagepipeline.memory.BucketsBitmapPool.getValue(com.facebook.imagepipeline.memory.Bucket):android.graphics.Bitmap");
    }
}
