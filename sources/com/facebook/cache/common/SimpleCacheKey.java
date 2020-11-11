package com.facebook.cache.common;

import android.net.Uri;
import com.facebook.common.internal.Preconditions;

public class SimpleCacheKey implements CacheKey {
    final boolean mIsResourceIdForDebugging;
    final String mKey;

    public SimpleCacheKey(String key) {
        this(key, false);
    }

    public SimpleCacheKey(String key, boolean isResourceIdForDebugging) {
        this.mKey = (String) Preconditions.checkNotNull(key);
        this.mIsResourceIdForDebugging = isResourceIdForDebugging;
    }

    public String toString() {
        return this.mKey;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (o instanceof SimpleCacheKey) {
            return this.mKey.equals(((SimpleCacheKey) o).mKey);
        }
        return false;
    }

    public int hashCode() {
        return this.mKey.hashCode();
    }

    public boolean containsUri(Uri uri) {
        return this.mKey.contains(uri.toString());
    }

    public String getUriString() {
        return this.mKey;
    }

    public boolean isResourceIdForDebugging() {
        return this.mIsResourceIdForDebugging;
    }
}
