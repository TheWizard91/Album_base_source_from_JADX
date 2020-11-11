package com.facebook.imagepipeline.memory;

import android.graphics.Bitmap;
import com.facebook.common.memory.MemoryTrimType;
import com.facebook.common.memory.MemoryTrimmableRegistry;
import javax.annotation.Nullable;

public class LruBitmapPool implements BitmapPool {
    private int mCurrentSize;
    private int mMaxBitmapSize;
    private final int mMaxPoolSize;
    private final PoolStatsTracker mPoolStatsTracker;
    protected final PoolBackend<Bitmap> mStrategy = new BitmapPoolBackend();

    public LruBitmapPool(int maxPoolSize, int maxBitmapSize, PoolStatsTracker poolStatsTracker, @Nullable MemoryTrimmableRegistry memoryTrimmableRegistry) {
        this.mMaxPoolSize = maxPoolSize;
        this.mMaxBitmapSize = maxBitmapSize;
        this.mPoolStatsTracker = poolStatsTracker;
        if (memoryTrimmableRegistry != null) {
            memoryTrimmableRegistry.registerMemoryTrimmable(this);
        }
    }

    public void trim(MemoryTrimType trimType) {
        trimTo((int) (((double) this.mMaxPoolSize) * (1.0d - trimType.getSuggestedTrimRatio())));
    }

    private synchronized void trimTo(int maxSize) {
        while (true) {
            if (this.mCurrentSize <= maxSize) {
                break;
            }
            Bitmap removed = this.mStrategy.pop();
            if (removed == null) {
                break;
            }
            int size = this.mStrategy.getSize(removed);
            this.mCurrentSize -= size;
            this.mPoolStatsTracker.onFree(size);
        }
    }

    public synchronized Bitmap get(int size) {
        int i = this.mCurrentSize;
        int i2 = this.mMaxPoolSize;
        if (i > i2) {
            trimTo(i2);
        }
        Bitmap cached = this.mStrategy.get(size);
        if (cached != null) {
            int reusedSize = this.mStrategy.getSize(cached);
            this.mCurrentSize -= reusedSize;
            this.mPoolStatsTracker.onValueReuse(reusedSize);
            return cached;
        }
        return alloc(size);
    }

    private Bitmap alloc(int size) {
        this.mPoolStatsTracker.onAlloc(size);
        return Bitmap.createBitmap(1, size, Bitmap.Config.ALPHA_8);
    }

    public void release(Bitmap value) {
        int size = this.mStrategy.getSize(value);
        if (size <= this.mMaxBitmapSize) {
            this.mPoolStatsTracker.onValueRelease(size);
            this.mStrategy.put(value);
            synchronized (this) {
                this.mCurrentSize += size;
            }
        }
    }
}
