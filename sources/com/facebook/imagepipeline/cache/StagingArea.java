package com.facebook.imagepipeline.cache;

import com.facebook.cache.common.CacheKey;
import com.facebook.common.internal.Preconditions;
import com.facebook.common.logging.FLog;
import com.facebook.common.memory.PooledByteBuffer;
import com.facebook.common.references.CloseableReference;
import com.facebook.imagepipeline.image.EncodedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StagingArea {
    private static final Class<?> TAG = StagingArea.class;
    private Map<CacheKey, EncodedImage> mMap = new HashMap();

    private StagingArea() {
    }

    public static StagingArea getInstance() {
        return new StagingArea();
    }

    public synchronized void put(CacheKey key, EncodedImage encodedImage) {
        Preconditions.checkNotNull(key);
        Preconditions.checkArgument(EncodedImage.isValid(encodedImage));
        EncodedImage.closeSafely(this.mMap.put(key, EncodedImage.cloneOrNull(encodedImage)));
        logStats();
    }

    public void clearAll() {
        List<EncodedImage> old;
        synchronized (this) {
            old = new ArrayList<>(this.mMap.values());
            this.mMap.clear();
        }
        for (int i = 0; i < old.size(); i++) {
            EncodedImage encodedImage = old.get(i);
            if (encodedImage != null) {
                encodedImage.close();
            }
        }
    }

    public boolean remove(CacheKey key) {
        EncodedImage encodedImage;
        Preconditions.checkNotNull(key);
        synchronized (this) {
            encodedImage = this.mMap.remove(key);
        }
        if (encodedImage == null) {
            return false;
        }
        try {
            return encodedImage.isValid();
        } finally {
            encodedImage.close();
        }
    }

    /* JADX INFO: finally extract failed */
    public synchronized boolean remove(CacheKey key, EncodedImage encodedImage) {
        Preconditions.checkNotNull(key);
        Preconditions.checkNotNull(encodedImage);
        Preconditions.checkArgument(EncodedImage.isValid(encodedImage));
        EncodedImage oldValue = this.mMap.get(key);
        if (oldValue == null) {
            return false;
        }
        CloseableReference<PooledByteBuffer> oldRef = oldValue.getByteBufferRef();
        CloseableReference<PooledByteBuffer> ref = encodedImage.getByteBufferRef();
        if (!(oldRef == null || ref == null)) {
            try {
                if (oldRef.get() == ref.get()) {
                    this.mMap.remove(key);
                    CloseableReference.closeSafely((CloseableReference<?>) ref);
                    CloseableReference.closeSafely((CloseableReference<?>) oldRef);
                    EncodedImage.closeSafely(oldValue);
                    logStats();
                    return true;
                }
            } catch (Throwable th) {
                CloseableReference.closeSafely((CloseableReference<?>) ref);
                CloseableReference.closeSafely((CloseableReference<?>) oldRef);
                EncodedImage.closeSafely(oldValue);
                throw th;
            }
        }
        CloseableReference.closeSafely((CloseableReference<?>) ref);
        CloseableReference.closeSafely((CloseableReference<?>) oldRef);
        EncodedImage.closeSafely(oldValue);
        return false;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:19:0x004d, code lost:
        r0 = r1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:30:0x005b, code lost:
        return r0;
     */
    @javax.annotation.Nullable
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized com.facebook.imagepipeline.image.EncodedImage get(com.facebook.cache.common.CacheKey r8) {
        /*
            r7 = this;
            monitor-enter(r7)
            com.facebook.common.internal.Preconditions.checkNotNull(r8)     // Catch:{ all -> 0x005c }
            java.util.Map<com.facebook.cache.common.CacheKey, com.facebook.imagepipeline.image.EncodedImage> r0 = r7.mMap     // Catch:{ all -> 0x005c }
            java.lang.Object r0 = r0.get(r8)     // Catch:{ all -> 0x005c }
            com.facebook.imagepipeline.image.EncodedImage r0 = (com.facebook.imagepipeline.image.EncodedImage) r0     // Catch:{ all -> 0x005c }
            if (r0 == 0) goto L_0x005a
            monitor-enter(r0)     // Catch:{ all -> 0x005c }
            boolean r1 = com.facebook.imagepipeline.image.EncodedImage.isValid(r0)     // Catch:{ all -> 0x0054 }
            if (r1 != 0) goto L_0x0048
            java.util.Map<com.facebook.cache.common.CacheKey, com.facebook.imagepipeline.image.EncodedImage> r1 = r7.mMap     // Catch:{ all -> 0x0045 }
            r1.remove(r8)     // Catch:{ all -> 0x0045 }
            java.lang.Class<?> r1 = TAG     // Catch:{ all -> 0x0045 }
            java.lang.String r2 = "Found closed reference %d for key %s (%d)"
            r3 = 3
            java.lang.Object[] r3 = new java.lang.Object[r3]     // Catch:{ all -> 0x0045 }
            r4 = 0
            int r5 = java.lang.System.identityHashCode(r0)     // Catch:{ all -> 0x0045 }
            java.lang.Integer r5 = java.lang.Integer.valueOf(r5)     // Catch:{ all -> 0x0045 }
            r3[r4] = r5     // Catch:{ all -> 0x0045 }
            r4 = 1
            java.lang.String r5 = r8.getUriString()     // Catch:{ all -> 0x0045 }
            r3[r4] = r5     // Catch:{ all -> 0x0045 }
            r4 = 2
            int r5 = java.lang.System.identityHashCode(r8)     // Catch:{ all -> 0x0045 }
            java.lang.Integer r5 = java.lang.Integer.valueOf(r5)     // Catch:{ all -> 0x0045 }
            r3[r4] = r5     // Catch:{ all -> 0x0045 }
            com.facebook.common.logging.FLog.m98w((java.lang.Class<?>) r1, (java.lang.String) r2, (java.lang.Object[]) r3)     // Catch:{ all -> 0x0045 }
            r1 = 0
            monitor-exit(r0)     // Catch:{ all -> 0x0045 }
            monitor-exit(r7)
            return r1
        L_0x0045:
            r1 = move-exception
            r2 = r0
            goto L_0x0056
        L_0x0048:
            com.facebook.imagepipeline.image.EncodedImage r1 = com.facebook.imagepipeline.image.EncodedImage.cloneOrNull(r0)     // Catch:{ all -> 0x0054 }
            monitor-exit(r0)     // Catch:{ all -> 0x004f }
            r0 = r1
            goto L_0x005a
        L_0x004f:
            r2 = move-exception
            r6 = r2
            r2 = r1
            r1 = r6
            goto L_0x0056
        L_0x0054:
            r1 = move-exception
            r2 = r0
        L_0x0056:
            monitor-exit(r0)     // Catch:{ all -> 0x0058 }
            throw r1     // Catch:{ all -> 0x005c }
        L_0x0058:
            r1 = move-exception
            goto L_0x0056
        L_0x005a:
            monitor-exit(r7)
            return r0
        L_0x005c:
            r8 = move-exception
            monitor-exit(r7)
            throw r8
        */
        throw new UnsupportedOperationException("Method not decompiled: com.facebook.imagepipeline.cache.StagingArea.get(com.facebook.cache.common.CacheKey):com.facebook.imagepipeline.image.EncodedImage");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:26:0x0052, code lost:
        r1 = th;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized boolean containsKey(com.facebook.cache.common.CacheKey r8) {
        /*
            r7 = this;
            monitor-enter(r7)
            com.facebook.common.internal.Preconditions.checkNotNull(r8)     // Catch:{ all -> 0x0054 }
            java.util.Map<com.facebook.cache.common.CacheKey, com.facebook.imagepipeline.image.EncodedImage> r0 = r7.mMap     // Catch:{ all -> 0x0054 }
            boolean r0 = r0.containsKey(r8)     // Catch:{ all -> 0x0054 }
            r1 = 0
            if (r0 != 0) goto L_0x000f
            monitor-exit(r7)
            return r1
        L_0x000f:
            java.util.Map<com.facebook.cache.common.CacheKey, com.facebook.imagepipeline.image.EncodedImage> r0 = r7.mMap     // Catch:{ all -> 0x0054 }
            java.lang.Object r0 = r0.get(r8)     // Catch:{ all -> 0x0054 }
            com.facebook.imagepipeline.image.EncodedImage r0 = (com.facebook.imagepipeline.image.EncodedImage) r0     // Catch:{ all -> 0x0054 }
            monitor-enter(r0)     // Catch:{ all -> 0x0054 }
            boolean r2 = com.facebook.imagepipeline.image.EncodedImage.isValid(r0)     // Catch:{ all -> 0x004f }
            r3 = 1
            if (r2 != 0) goto L_0x004c
            java.util.Map<com.facebook.cache.common.CacheKey, com.facebook.imagepipeline.image.EncodedImage> r2 = r7.mMap     // Catch:{ all -> 0x004f }
            r2.remove(r8)     // Catch:{ all -> 0x004f }
            java.lang.Class<?> r2 = TAG     // Catch:{ all -> 0x004f }
            java.lang.String r4 = "Found closed reference %d for key %s (%d)"
            r5 = 3
            java.lang.Object[] r5 = new java.lang.Object[r5]     // Catch:{ all -> 0x004f }
            int r6 = java.lang.System.identityHashCode(r0)     // Catch:{ all -> 0x004f }
            java.lang.Integer r6 = java.lang.Integer.valueOf(r6)     // Catch:{ all -> 0x004f }
            r5[r1] = r6     // Catch:{ all -> 0x004f }
            java.lang.String r6 = r8.getUriString()     // Catch:{ all -> 0x004f }
            r5[r3] = r6     // Catch:{ all -> 0x004f }
            r3 = 2
            int r6 = java.lang.System.identityHashCode(r8)     // Catch:{ all -> 0x004f }
            java.lang.Integer r6 = java.lang.Integer.valueOf(r6)     // Catch:{ all -> 0x004f }
            r5[r3] = r6     // Catch:{ all -> 0x004f }
            com.facebook.common.logging.FLog.m98w((java.lang.Class<?>) r2, (java.lang.String) r4, (java.lang.Object[]) r5)     // Catch:{ all -> 0x004f }
            monitor-exit(r0)     // Catch:{ all -> 0x004f }
            monitor-exit(r7)
            return r1
        L_0x004c:
            monitor-exit(r0)     // Catch:{ all -> 0x004f }
            monitor-exit(r7)
            return r3
        L_0x004f:
            r1 = move-exception
        L_0x0050:
            monitor-exit(r0)     // Catch:{ all -> 0x0052 }
            throw r1     // Catch:{ all -> 0x0054 }
        L_0x0052:
            r1 = move-exception
            goto L_0x0050
        L_0x0054:
            r8 = move-exception
            monitor-exit(r7)
            throw r8
        */
        throw new UnsupportedOperationException("Method not decompiled: com.facebook.imagepipeline.cache.StagingArea.containsKey(com.facebook.cache.common.CacheKey):boolean");
    }

    private synchronized void logStats() {
        FLog.m81v(TAG, "Count = %d", (Object) Integer.valueOf(this.mMap.size()));
    }
}
