package com.facebook.imagepipeline.cache;

import android.graphics.Bitmap;
import com.facebook.common.internal.Preconditions;
import com.facebook.common.internal.Predicate;
import com.facebook.common.references.CloseableReference;
import com.facebook.imagepipeline.cache.CountingMemoryCache;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CountingMemoryCacheInspector<K, V> {
    private final CountingMemoryCache<K, V> mCountingBitmapCache;

    public static class DumpInfoEntry<K, V> {
        public final K key;
        public final CloseableReference<V> value;

        public DumpInfoEntry(K key2, CloseableReference<V> valueRef) {
            this.key = Preconditions.checkNotNull(key2);
            this.value = CloseableReference.cloneOrNull(valueRef);
        }

        public void release() {
            CloseableReference.closeSafely((CloseableReference<?>) this.value);
        }
    }

    public static class DumpInfo<K, V> {
        public final List<DumpInfoEntry<K, V>> lruEntries = new ArrayList();
        public final int lruSize;
        public final int maxEntriesCount;
        public final int maxEntrySize;
        public final int maxSize;
        public final Map<Bitmap, Object> otherEntries = new HashMap();
        public final List<DumpInfoEntry<K, V>> sharedEntries = new ArrayList();
        public final int size;

        public DumpInfo(int size2, int lruSize2, MemoryCacheParams params) {
            this.maxSize = params.maxCacheSize;
            this.maxEntriesCount = params.maxCacheEntries;
            this.maxEntrySize = params.maxCacheEntrySize;
            this.size = size2;
            this.lruSize = lruSize2;
        }

        public void release() {
            for (DumpInfoEntry<K, V> entry : this.lruEntries) {
                entry.release();
            }
            for (DumpInfoEntry<K, V> entry2 : this.sharedEntries) {
                entry2.release();
            }
        }
    }

    public CountingMemoryCacheInspector(CountingMemoryCache<K, V> countingBitmapCache) {
        this.mCountingBitmapCache = countingBitmapCache;
    }

    public DumpInfo dumpCacheContent() {
        DumpInfo<K, V> dumpInfo;
        synchronized (this.mCountingBitmapCache) {
            dumpInfo = new DumpInfo<>(this.mCountingBitmapCache.getSizeInBytes(), this.mCountingBitmapCache.getEvictionQueueSizeInBytes(), this.mCountingBitmapCache.mMemoryCacheParams);
            for (Map.Entry<K, CountingMemoryCache.Entry<K, V>> cachedEntry : this.mCountingBitmapCache.mCachedEntries.getMatchingEntries((Predicate) null)) {
                CountingMemoryCache.Entry<K, V> entry = cachedEntry.getValue();
                DumpInfoEntry<K, V> dumpEntry = new DumpInfoEntry<>(entry.key, entry.valueRef);
                if (entry.clientCount > 0) {
                    dumpInfo.sharedEntries.add(dumpEntry);
                } else {
                    dumpInfo.lruEntries.add(dumpEntry);
                }
            }
            for (Map.Entry<Bitmap, Object> entry2 : this.mCountingBitmapCache.mOtherEntries.entrySet()) {
                if (entry2 != null && !entry2.getKey().isRecycled()) {
                    dumpInfo.otherEntries.put(entry2.getKey(), entry2.getValue());
                }
            }
        }
        return dumpInfo;
    }
}
