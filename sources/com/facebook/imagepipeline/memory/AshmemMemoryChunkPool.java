package com.facebook.imagepipeline.memory;

import com.facebook.common.memory.MemoryTrimmableRegistry;

public class AshmemMemoryChunkPool extends MemoryChunkPool {
    public AshmemMemoryChunkPool(MemoryTrimmableRegistry memoryTrimmableRegistry, PoolParams poolParams, PoolStatsTracker ashmemMemoryChunkPoolStatsTracker) {
        super(memoryTrimmableRegistry, poolParams, ashmemMemoryChunkPoolStatsTracker);
    }

    public AshmemMemoryChunk alloc(int bucketedSize) {
        return new AshmemMemoryChunk(bucketedSize);
    }
}
