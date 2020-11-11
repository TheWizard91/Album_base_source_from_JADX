package com.facebook.imagepipeline.cache;

import com.facebook.common.internal.Predicate;
import com.facebook.common.references.CloseableReference;

public class InstrumentedMemoryCache<K, V> implements MemoryCache<K, V> {
    private final MemoryCache<K, V> mDelegate;
    private final MemoryCacheTracker mTracker;

    public InstrumentedMemoryCache(MemoryCache<K, V> delegate, MemoryCacheTracker tracker) {
        this.mDelegate = delegate;
        this.mTracker = tracker;
    }

    public CloseableReference<V> get(K key) {
        CloseableReference<V> result = this.mDelegate.get(key);
        if (result == null) {
            this.mTracker.onCacheMiss(key);
        } else {
            this.mTracker.onCacheHit(key);
        }
        return result;
    }

    public CloseableReference<V> cache(K key, CloseableReference<V> value) {
        this.mTracker.onCachePut(key);
        return this.mDelegate.cache(key, value);
    }

    public int removeAll(Predicate<K> predicate) {
        return this.mDelegate.removeAll(predicate);
    }

    public boolean contains(Predicate<K> predicate) {
        return this.mDelegate.contains(predicate);
    }

    public boolean contains(K key) {
        return this.mDelegate.contains(key);
    }

    public int getCount() {
        return this.mDelegate.getCount();
    }

    public int getSizeInBytes() {
        return this.mDelegate.getSizeInBytes();
    }
}
