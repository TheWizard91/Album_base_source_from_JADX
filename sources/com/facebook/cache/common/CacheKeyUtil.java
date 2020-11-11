package com.facebook.cache.common;

import com.bumptech.glide.load.Key;
import com.facebook.common.util.SecureHashUtil;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public final class CacheKeyUtil {
    public static List<String> getResourceIds(CacheKey key) {
        List<String> ids;
        try {
            if (key instanceof MultiCacheKey) {
                List<CacheKey> keys = ((MultiCacheKey) key).getCacheKeys();
                ids = new ArrayList<>(keys.size());
                for (int i = 0; i < keys.size(); i++) {
                    ids.add(secureHashKey(keys.get(i)));
                }
            } else {
                ids = new ArrayList<>(1);
                ids.add(key.isResourceIdForDebugging() ? key.getUriString() : secureHashKey(key));
            }
            return ids;
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    public static String getFirstResourceId(CacheKey key) {
        try {
            if (key instanceof MultiCacheKey) {
                return secureHashKey(((MultiCacheKey) key).getCacheKeys().get(0));
            }
            return secureHashKey(key);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    private static String secureHashKey(CacheKey key) throws UnsupportedEncodingException {
        return SecureHashUtil.makeSHA1HashBase64(key.getUriString().getBytes(Key.STRING_CHARSET_NAME));
    }
}
