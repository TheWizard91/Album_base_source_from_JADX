package com.facebook.imagepipeline.memory;

import java.util.HashSet;
import java.util.Set;
import javax.annotation.Nullable;

public abstract class LruBucketsPoolBackend<T> implements PoolBackend<T> {
    private final Set<T> mCurrentItems = new HashSet();
    private final BucketMap<T> mMap = new BucketMap<>();

    @Nullable
    public T get(int size) {
        return maybeRemoveFromCurrentItems(this.mMap.acquire(size));
    }

    public void put(T item) {
        boolean wasAdded;
        synchronized (this) {
            wasAdded = this.mCurrentItems.add(item);
        }
        if (wasAdded) {
            this.mMap.release(getSize(item), item);
        }
    }

    @Nullable
    public T pop() {
        return maybeRemoveFromCurrentItems(this.mMap.removeFromEnd());
    }

    private T maybeRemoveFromCurrentItems(@Nullable T t) {
        if (t != null) {
            synchronized (this) {
                this.mCurrentItems.remove(t);
            }
        }
        return t;
    }

    /* access modifiers changed from: package-private */
    public int valueCount() {
        return this.mMap.valueCount();
    }
}
