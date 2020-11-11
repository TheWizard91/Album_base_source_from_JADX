package com.facebook.imagepipeline.memory;

import android.util.SparseIntArray;
import com.facebook.common.internal.Preconditions;
import com.facebook.common.memory.MemoryTrimmableRegistry;
import com.facebook.imagepipeline.memory.BasePool;

public abstract class MemoryChunkPool extends BasePool<MemoryChunk> {
    private final int[] mBucketSizes;

    /* access modifiers changed from: protected */
    public abstract MemoryChunk alloc(int i);

    MemoryChunkPool(MemoryTrimmableRegistry memoryTrimmableRegistry, PoolParams poolParams, PoolStatsTracker memoryChunkPoolStatsTracker) {
        super(memoryTrimmableRegistry, poolParams, memoryChunkPoolStatsTracker);
        SparseIntArray bucketSizes = poolParams.bucketSizes;
        this.mBucketSizes = new int[bucketSizes.size()];
        int i = 0;
        while (true) {
            int[] iArr = this.mBucketSizes;
            if (i < iArr.length) {
                iArr[i] = bucketSizes.keyAt(i);
                i++;
            } else {
                initialize();
                return;
            }
        }
    }

    /* access modifiers changed from: package-private */
    public int getMinBufferSize() {
        return this.mBucketSizes[0];
    }

    /* access modifiers changed from: protected */
    public void free(MemoryChunk value) {
        Preconditions.checkNotNull(value);
        value.close();
    }

    /* access modifiers changed from: protected */
    public int getSizeInBytes(int bucketedSize) {
        return bucketedSize;
    }

    /* access modifiers changed from: protected */
    public int getBucketedSize(int requestSize) {
        if (requestSize > 0) {
            for (int bucketedSize : this.mBucketSizes) {
                if (bucketedSize >= requestSize) {
                    return bucketedSize;
                }
            }
            return requestSize;
        }
        throw new BasePool.InvalidSizeException(Integer.valueOf(requestSize));
    }

    /* access modifiers changed from: protected */
    public int getBucketedSizeForValue(MemoryChunk value) {
        Preconditions.checkNotNull(value);
        return value.getSize();
    }

    /* access modifiers changed from: protected */
    public boolean isReusable(MemoryChunk value) {
        Preconditions.checkNotNull(value);
        return !value.isClosed();
    }
}
