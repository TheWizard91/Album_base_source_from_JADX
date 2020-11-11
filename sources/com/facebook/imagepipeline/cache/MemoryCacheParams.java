package com.facebook.imagepipeline.cache;

import java.util.concurrent.TimeUnit;

public class MemoryCacheParams {
    public final int maxCacheEntries;
    public final int maxCacheEntrySize;
    public final int maxCacheSize;
    public final int maxEvictionQueueEntries;
    public final int maxEvictionQueueSize;
    public final long paramsCheckIntervalMs;

    public MemoryCacheParams(int maxCacheSize2, int maxCacheEntries2, int maxEvictionQueueSize2, int maxEvictionQueueEntries2, int maxCacheEntrySize2) {
        this(maxCacheSize2, maxCacheEntries2, maxEvictionQueueSize2, maxEvictionQueueEntries2, maxCacheEntrySize2, TimeUnit.MINUTES.toMillis(5));
    }

    public MemoryCacheParams(int maxCacheSize2, int maxCacheEntries2, int maxEvictionQueueSize2, int maxEvictionQueueEntries2, int maxCacheEntrySize2, long paramsCheckIntervalMs2) {
        this.maxCacheSize = maxCacheSize2;
        this.maxCacheEntries = maxCacheEntries2;
        this.maxEvictionQueueSize = maxEvictionQueueSize2;
        this.maxEvictionQueueEntries = maxEvictionQueueEntries2;
        this.maxCacheEntrySize = maxCacheEntrySize2;
        this.paramsCheckIntervalMs = paramsCheckIntervalMs2;
    }
}
