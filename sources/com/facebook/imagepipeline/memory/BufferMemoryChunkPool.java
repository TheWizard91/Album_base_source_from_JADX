package com.facebook.imagepipeline.memory;

import com.facebook.common.memory.MemoryTrimmableRegistry;

public class BufferMemoryChunkPool extends MemoryChunkPool {
    public BufferMemoryChunkPool(MemoryTrimmableRegistry memoryTrimmableRegistry, PoolParams poolParams, PoolStatsTracker bufferMemoryChunkPoolStatsTracker) {
        super(memoryTrimmableRegistry, poolParams, bufferMemoryChunkPoolStatsTracker);
    }

    /* access modifiers changed from: protected */
    public BufferMemoryChunk alloc(int bucketedSize) {
        return new BufferMemoryChunk(bucketedSize);
    }
}
