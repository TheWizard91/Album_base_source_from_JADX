package com.firebase.p008ui.common;

import android.util.LruCache;

/* renamed from: com.firebase.ui.common.BaseCachingSnapshotParser */
public abstract class BaseCachingSnapshotParser<S, T> implements BaseSnapshotParser<S, T> {
    private static final int MAX_CACHE_SIZE = 100;
    private final LruCache<String, T> mObjectCache = new LruCache<>(100);
    private final BaseSnapshotParser<S, T> mParser;

    public abstract String getId(S s);

    public BaseCachingSnapshotParser(BaseSnapshotParser<S, T> parser) {
        this.mParser = parser;
    }

    public T parseSnapshot(S snapshot) {
        String id = getId(snapshot);
        T result = this.mObjectCache.get(id);
        if (result != null) {
            return result;
        }
        T object = this.mParser.parseSnapshot(snapshot);
        this.mObjectCache.put(id, object);
        return object;
    }

    public void clear() {
        this.mObjectCache.evictAll();
    }

    public void invalidate(S snapshot) {
        this.mObjectCache.remove(getId(snapshot));
    }
}
