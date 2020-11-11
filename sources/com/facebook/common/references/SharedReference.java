package com.facebook.common.references;

import android.graphics.Bitmap;
import com.facebook.common.internal.Objects;
import com.facebook.common.internal.Preconditions;
import com.facebook.common.logging.FLog;
import java.util.IdentityHashMap;
import java.util.Map;

public class SharedReference<T> {
    private static final Map<Object, Integer> sLiveObjects = new IdentityHashMap();
    private int mRefCount = 1;
    private final ResourceReleaser<T> mResourceReleaser;
    private T mValue;

    public SharedReference(T value, ResourceReleaser<T> resourceReleaser) {
        this.mValue = Preconditions.checkNotNull(value);
        this.mResourceReleaser = (ResourceReleaser) Preconditions.checkNotNull(resourceReleaser);
        addLiveReference(value);
    }

    private static void addLiveReference(Object value) {
        if (!CloseableReference.useGc() || (!(value instanceof Bitmap) && !(value instanceof HasBitmap))) {
            Map<Object, Integer> map = sLiveObjects;
            synchronized (map) {
                Integer count = map.get(value);
                if (count == null) {
                    map.put(value, 1);
                } else {
                    map.put(value, Integer.valueOf(count.intValue() + 1));
                }
            }
        }
    }

    private static void removeLiveReference(Object value) {
        Map<Object, Integer> map = sLiveObjects;
        synchronized (map) {
            Integer count = map.get(value);
            if (count == null) {
                FLog.wtf("SharedReference", "No entry in sLiveObjects for value of type %s", value.getClass());
            } else if (count.intValue() == 1) {
                map.remove(value);
            } else {
                map.put(value, Integer.valueOf(count.intValue() - 1));
            }
        }
    }

    public synchronized T get() {
        return this.mValue;
    }

    public synchronized boolean isValid() {
        return this.mRefCount > 0;
    }

    public static boolean isValid(SharedReference<?> ref) {
        return ref != null && ref.isValid();
    }

    public synchronized void addReference() {
        ensureValid();
        this.mRefCount++;
    }

    public synchronized boolean addReferenceIfValid() {
        if (!isValid()) {
            return false;
        }
        addReference();
        return true;
    }

    public synchronized boolean deleteReferenceIfValid() {
        if (!isValid()) {
            return false;
        }
        deleteReference();
        return true;
    }

    public void deleteReference() {
        T deleted;
        if (decreaseRefCount() == 0) {
            synchronized (this) {
                deleted = this.mValue;
                this.mValue = null;
            }
            this.mResourceReleaser.release(deleted);
            removeLiveReference(deleted);
        }
    }

    private synchronized int decreaseRefCount() {
        int i;
        ensureValid();
        Preconditions.checkArgument(this.mRefCount > 0);
        i = this.mRefCount - 1;
        this.mRefCount = i;
        return i;
    }

    private void ensureValid() {
        if (!isValid(this)) {
            throw new NullReferenceException();
        }
    }

    public synchronized int getRefCountTestOnly() {
        return this.mRefCount;
    }

    public static class NullReferenceException extends RuntimeException {
        public NullReferenceException() {
            super("Null shared reference");
        }
    }

    public static String reportData() {
        return Objects.toStringHelper("SharedReference").add("live_objects_count", sLiveObjects.size()).toString();
    }
}
