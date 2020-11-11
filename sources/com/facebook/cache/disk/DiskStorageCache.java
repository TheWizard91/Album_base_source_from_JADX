package com.facebook.cache.disk;

import com.facebook.binaryresource.BinaryResource;
import com.facebook.cache.common.CacheErrorLogger;
import com.facebook.cache.common.CacheEventListener;
import com.facebook.cache.common.CacheKey;
import com.facebook.cache.common.CacheKeyUtil;
import com.facebook.cache.common.WriterCallback;
import com.facebook.cache.disk.DiskStorage;
import com.facebook.common.disk.DiskTrimmable;
import com.facebook.common.disk.DiskTrimmableRegistry;
import com.facebook.common.logging.FLog;
import com.facebook.common.statfs.StatFsHelper;
import com.facebook.common.time.Clock;
import com.facebook.common.time.SystemClock;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;
import javax.annotation.Nullable;

public class DiskStorageCache implements FileCache, DiskTrimmable {
    private static final long FILECACHE_SIZE_UPDATE_PERIOD_MS = TimeUnit.MINUTES.toMillis(30);
    private static final long FUTURE_TIMESTAMP_THRESHOLD_MS = TimeUnit.HOURS.toMillis(2);
    public static final int START_OF_VERSIONING = 1;
    private static final Class<?> TAG = DiskStorageCache.class;
    private static final double TRIMMING_LOWER_BOUND = 0.02d;
    private static final long UNINITIALIZED = -1;
    private final CacheErrorLogger mCacheErrorLogger;
    private final CacheEventListener mCacheEventListener;
    private long mCacheSizeLastUpdateTime;
    private long mCacheSizeLimit;
    private final long mCacheSizeLimitMinimum;
    private final CacheStats mCacheStats;
    private final Clock mClock;
    /* access modifiers changed from: private */
    public final CountDownLatch mCountDownLatch;
    private final long mDefaultCacheSizeLimit;
    private final EntryEvictionComparatorSupplier mEntryEvictionComparatorSupplier;
    private final boolean mIndexPopulateAtStartupEnabled;
    /* access modifiers changed from: private */
    public boolean mIndexReady;
    /* access modifiers changed from: private */
    public final Object mLock = new Object();
    private final long mLowDiskSpaceCacheSizeLimit;
    final Set<String> mResourceIndex;
    private final StatFsHelper mStatFsHelper;
    private final DiskStorage mStorage;

    static class CacheStats {
        private long mCount = -1;
        private boolean mInitialized = false;
        private long mSize = -1;

        CacheStats() {
        }

        public synchronized boolean isInitialized() {
            return this.mInitialized;
        }

        public synchronized void reset() {
            this.mInitialized = false;
            this.mCount = -1;
            this.mSize = -1;
        }

        public synchronized void set(long size, long count) {
            this.mCount = count;
            this.mSize = size;
            this.mInitialized = true;
        }

        public synchronized void increment(long sizeIncrement, long countIncrement) {
            if (this.mInitialized) {
                this.mSize += sizeIncrement;
                this.mCount += countIncrement;
            }
        }

        public synchronized long getSize() {
            return this.mSize;
        }

        public synchronized long getCount() {
            return this.mCount;
        }
    }

    public static class Params {
        public final long mCacheSizeLimitMinimum;
        public final long mDefaultCacheSizeLimit;
        public final long mLowDiskSpaceCacheSizeLimit;

        public Params(long cacheSizeLimitMinimum, long lowDiskSpaceCacheSizeLimit, long defaultCacheSizeLimit) {
            this.mCacheSizeLimitMinimum = cacheSizeLimitMinimum;
            this.mLowDiskSpaceCacheSizeLimit = lowDiskSpaceCacheSizeLimit;
            this.mDefaultCacheSizeLimit = defaultCacheSizeLimit;
        }
    }

    public DiskStorageCache(DiskStorage diskStorage, EntryEvictionComparatorSupplier entryEvictionComparatorSupplier, Params params, CacheEventListener cacheEventListener, CacheErrorLogger cacheErrorLogger, @Nullable DiskTrimmableRegistry diskTrimmableRegistry, Executor executorForBackgrountInit, boolean indexPopulateAtStartupEnabled) {
        this.mLowDiskSpaceCacheSizeLimit = params.mLowDiskSpaceCacheSizeLimit;
        this.mDefaultCacheSizeLimit = params.mDefaultCacheSizeLimit;
        this.mCacheSizeLimit = params.mDefaultCacheSizeLimit;
        this.mStatFsHelper = StatFsHelper.getInstance();
        this.mStorage = diskStorage;
        this.mEntryEvictionComparatorSupplier = entryEvictionComparatorSupplier;
        this.mCacheSizeLastUpdateTime = -1;
        this.mCacheEventListener = cacheEventListener;
        this.mCacheSizeLimitMinimum = params.mCacheSizeLimitMinimum;
        this.mCacheErrorLogger = cacheErrorLogger;
        this.mCacheStats = new CacheStats();
        this.mClock = SystemClock.get();
        this.mIndexPopulateAtStartupEnabled = indexPopulateAtStartupEnabled;
        this.mResourceIndex = new HashSet();
        if (diskTrimmableRegistry != null) {
            diskTrimmableRegistry.registerDiskTrimmable(this);
        }
        if (indexPopulateAtStartupEnabled) {
            this.mCountDownLatch = new CountDownLatch(1);
            executorForBackgrountInit.execute(new Runnable() {
                public void run() {
                    synchronized (DiskStorageCache.this.mLock) {
                        boolean unused = DiskStorageCache.this.maybeUpdateFileCacheSize();
                    }
                    boolean unused2 = DiskStorageCache.this.mIndexReady = true;
                    DiskStorageCache.this.mCountDownLatch.countDown();
                }
            });
            return;
        }
        this.mCountDownLatch = new CountDownLatch(0);
    }

    public DiskStorage.DiskDumpInfo getDumpInfo() throws IOException {
        return this.mStorage.getDumpInfo();
    }

    public boolean isEnabled() {
        return this.mStorage.isEnabled();
    }

    /* access modifiers changed from: protected */
    public void awaitIndex() {
        try {
            this.mCountDownLatch.await();
        } catch (InterruptedException e) {
            FLog.m56e(TAG, "Memory Index is not ready yet. ");
        }
    }

    public boolean isIndexReady() {
        return this.mIndexReady || !this.mIndexPopulateAtStartupEnabled;
    }

    @Nullable
    public BinaryResource getResource(CacheKey key) {
        BinaryResource resource;
        String resourceId = null;
        SettableCacheEvent cacheEvent = SettableCacheEvent.obtain().setCacheKey(key);
        try {
            synchronized (this.mLock) {
                resource = null;
                List<String> resourceIds = CacheKeyUtil.getResourceIds(key);
                int i = 0;
                while (true) {
                    if (i >= resourceIds.size()) {
                        break;
                    }
                    resourceId = resourceIds.get(i);
                    cacheEvent.setResourceId(resourceId);
                    resource = this.mStorage.getResource(resourceId, key);
                    if (resource != null) {
                        break;
                    }
                    i++;
                }
                if (resource == null) {
                    this.mCacheEventListener.onMiss(cacheEvent);
                    this.mResourceIndex.remove(resourceId);
                } else {
                    this.mCacheEventListener.onHit(cacheEvent);
                    this.mResourceIndex.add(resourceId);
                }
            }
            cacheEvent.recycle();
            return resource;
        } catch (IOException ioe) {
            try {
                this.mCacheErrorLogger.logError(CacheErrorLogger.CacheErrorCategory.GENERIC_IO, TAG, "getResource", ioe);
                cacheEvent.setException(ioe);
                this.mCacheEventListener.onReadException(cacheEvent);
                return null;
            } finally {
                cacheEvent.recycle();
            }
        }
    }

    public boolean probe(CacheKey key) {
        try {
            synchronized (this.mLock) {
                List<String> resourceIds = CacheKeyUtil.getResourceIds(key);
                for (int i = 0; i < resourceIds.size(); i++) {
                    String resourceId = resourceIds.get(i);
                    if (this.mStorage.touch(resourceId, key)) {
                        this.mResourceIndex.add(resourceId);
                        return true;
                    }
                }
                return false;
            }
        } catch (IOException e) {
            SettableCacheEvent cacheEvent = SettableCacheEvent.obtain().setCacheKey(key).setResourceId((String) null).setException(e);
            this.mCacheEventListener.onReadException(cacheEvent);
            cacheEvent.recycle();
            return false;
        }
    }

    private DiskStorage.Inserter startInsert(String resourceId, CacheKey key) throws IOException {
        maybeEvictFilesInCacheDir();
        return this.mStorage.insert(resourceId, key);
    }

    private BinaryResource endInsert(DiskStorage.Inserter inserter, CacheKey key, String resourceId) throws IOException {
        BinaryResource resource;
        synchronized (this.mLock) {
            resource = inserter.commit(key);
            this.mResourceIndex.add(resourceId);
            this.mCacheStats.increment(resource.size(), 1);
        }
        return resource;
    }

    public BinaryResource insert(CacheKey key, WriterCallback callback) throws IOException {
        String resourceId;
        DiskStorage.Inserter inserter;
        SettableCacheEvent cacheEvent = SettableCacheEvent.obtain().setCacheKey(key);
        this.mCacheEventListener.onWriteAttempt(cacheEvent);
        synchronized (this.mLock) {
            resourceId = CacheKeyUtil.getFirstResourceId(key);
        }
        cacheEvent.setResourceId(resourceId);
        try {
            inserter = startInsert(resourceId, key);
            inserter.writeData(callback, key);
            BinaryResource resource = endInsert(inserter, key, resourceId);
            cacheEvent.setItemSize(resource.size()).setCacheSize(this.mCacheStats.getSize());
            this.mCacheEventListener.onWriteSuccess(cacheEvent);
            if (!inserter.cleanUp()) {
                FLog.m56e(TAG, "Failed to delete temp file");
            }
            cacheEvent.recycle();
            return resource;
        } catch (IOException ioe) {
            try {
                cacheEvent.setException(ioe);
                this.mCacheEventListener.onWriteException(cacheEvent);
                FLog.m57e(TAG, "Failed inserting a file into the cache", (Throwable) ioe);
                throw ioe;
            } catch (Throwable th) {
                cacheEvent.recycle();
                throw th;
            }
        } catch (Throwable th2) {
            if (!inserter.cleanUp()) {
                FLog.m56e(TAG, "Failed to delete temp file");
            }
            throw th2;
        }
    }

    public void remove(CacheKey key) {
        synchronized (this.mLock) {
            try {
                List<String> resourceIds = CacheKeyUtil.getResourceIds(key);
                for (int i = 0; i < resourceIds.size(); i++) {
                    String resourceId = resourceIds.get(i);
                    this.mStorage.remove(resourceId);
                    this.mResourceIndex.remove(resourceId);
                }
            } catch (IOException e) {
                this.mCacheErrorLogger.logError(CacheErrorLogger.CacheErrorCategory.DELETE_FILE, TAG, "delete: " + e.getMessage(), e);
            }
        }
    }

    public long clearOldEntries(long cacheExpirationMs) {
        Iterator<DiskStorage.Entry> it;
        Collection<DiskStorage.Entry> allEntries;
        long oldestRemainingEntryAgeMs = 0;
        synchronized (this.mLock) {
            try {
                long entryAgeMs = this.mClock.now();
                Collection<DiskStorage.Entry> allEntries2 = this.mStorage.getEntries();
                long cacheSizeBeforeClearance = this.mCacheStats.getSize();
                int itemsRemovedCount = 0;
                long itemsRemovedSize = 0;
                Iterator<DiskStorage.Entry> it2 = allEntries2.iterator();
                while (it2.hasNext()) {
                    DiskStorage.Entry entry = it2.next();
                    long now = entryAgeMs;
                    long now2 = Math.max(1, Math.abs(entryAgeMs - entry.getTimestamp()));
                    if (now2 >= cacheExpirationMs) {
                        long entryRemovedSize = this.mStorage.remove(entry);
                        allEntries = allEntries2;
                        it = it2;
                        this.mResourceIndex.remove(entry.getId());
                        if (entryRemovedSize > 0) {
                            itemsRemovedCount++;
                            itemsRemovedSize += entryRemovedSize;
                            DiskStorage.Entry entry2 = entry;
                            SettableCacheEvent cacheEvent = SettableCacheEvent.obtain().setResourceId(entry.getId()).setEvictionReason(CacheEventListener.EvictionReason.CONTENT_STALE).setItemSize(entryRemovedSize).setCacheSize(cacheSizeBeforeClearance - itemsRemovedSize);
                            this.mCacheEventListener.onEviction(cacheEvent);
                            cacheEvent.recycle();
                        }
                    } else {
                        allEntries = allEntries2;
                        it = it2;
                        DiskStorage.Entry entry3 = entry;
                        oldestRemainingEntryAgeMs = Math.max(oldestRemainingEntryAgeMs, now2);
                    }
                    entryAgeMs = now;
                    allEntries2 = allEntries;
                    it2 = it;
                }
                long j = entryAgeMs;
                this.mStorage.purgeUnexpectedResources();
                if (itemsRemovedCount > 0) {
                    maybeUpdateFileCacheSize();
                    this.mCacheStats.increment(-itemsRemovedSize, (long) (-itemsRemovedCount));
                }
            } catch (IOException ioe) {
                this.mCacheErrorLogger.logError(CacheErrorLogger.CacheErrorCategory.EVICTION, TAG, "clearOldEntries: " + ioe.getMessage(), ioe);
            }
        }
        return oldestRemainingEntryAgeMs;
    }

    private void maybeEvictFilesInCacheDir() throws IOException {
        synchronized (this.mLock) {
            boolean calculatedRightNow = maybeUpdateFileCacheSize();
            updateFileCacheSizeLimit();
            long cacheSize = this.mCacheStats.getSize();
            if (cacheSize > this.mCacheSizeLimit && !calculatedRightNow) {
                this.mCacheStats.reset();
                maybeUpdateFileCacheSize();
            }
            long j = this.mCacheSizeLimit;
            if (cacheSize > j) {
                evictAboveSize((j * 9) / 10, CacheEventListener.EvictionReason.CACHE_FULL);
            }
        }
    }

    private void evictAboveSize(long desiredSize, CacheEventListener.EvictionReason reason) throws IOException {
        long deleteSize;
        long j = desiredSize;
        try {
            Collection<DiskStorage.Entry> entries = getSortedEntries(this.mStorage.getEntries());
            long cacheSizeBeforeClearance = this.mCacheStats.getSize();
            long deleteSize2 = cacheSizeBeforeClearance - j;
            int itemCount = 0;
            long sumItemSizes = 0;
            Iterator<DiskStorage.Entry> it = entries.iterator();
            while (true) {
                if (!it.hasNext()) {
                    CacheEventListener.EvictionReason evictionReason = reason;
                    Collection<DiskStorage.Entry> collection = entries;
                    long j2 = deleteSize2;
                    break;
                }
                DiskStorage.Entry entry = it.next();
                if (sumItemSizes > deleteSize2) {
                    CacheEventListener.EvictionReason evictionReason2 = reason;
                    Collection<DiskStorage.Entry> collection2 = entries;
                    long j3 = deleteSize2;
                    break;
                }
                long deletedSize = this.mStorage.remove(entry);
                Collection<DiskStorage.Entry> entries2 = entries;
                this.mResourceIndex.remove(entry.getId());
                if (deletedSize > 0) {
                    itemCount++;
                    sumItemSizes += deletedSize;
                    deleteSize = deleteSize2;
                    SettableCacheEvent cacheEvent = SettableCacheEvent.obtain().setResourceId(entry.getId()).setEvictionReason(reason).setItemSize(deletedSize).setCacheSize(cacheSizeBeforeClearance - sumItemSizes).setCacheLimit(j);
                    this.mCacheEventListener.onEviction(cacheEvent);
                    cacheEvent.recycle();
                } else {
                    CacheEventListener.EvictionReason evictionReason3 = reason;
                    deleteSize = deleteSize2;
                }
                entries = entries2;
                deleteSize2 = deleteSize;
            }
            this.mCacheStats.increment(-sumItemSizes, (long) (-itemCount));
            this.mStorage.purgeUnexpectedResources();
        } catch (IOException ioe) {
            CacheEventListener.EvictionReason evictionReason4 = reason;
            this.mCacheErrorLogger.logError(CacheErrorLogger.CacheErrorCategory.EVICTION, TAG, "evictAboveSize: " + ioe.getMessage(), ioe);
            throw ioe;
        }
    }

    private Collection<DiskStorage.Entry> getSortedEntries(Collection<DiskStorage.Entry> allEntries) {
        long threshold = this.mClock.now() + FUTURE_TIMESTAMP_THRESHOLD_MS;
        ArrayList<DiskStorage.Entry> sortedList = new ArrayList<>(allEntries.size());
        ArrayList<DiskStorage.Entry> listToSort = new ArrayList<>(allEntries.size());
        for (DiskStorage.Entry entry : allEntries) {
            if (entry.getTimestamp() > threshold) {
                sortedList.add(entry);
            } else {
                listToSort.add(entry);
            }
        }
        Collections.sort(listToSort, this.mEntryEvictionComparatorSupplier.get());
        sortedList.addAll(listToSort);
        return sortedList;
    }

    private void updateFileCacheSizeLimit() {
        if (this.mStatFsHelper.testLowDiskSpace(this.mStorage.isExternal() ? StatFsHelper.StorageType.EXTERNAL : StatFsHelper.StorageType.INTERNAL, this.mDefaultCacheSizeLimit - this.mCacheStats.getSize())) {
            this.mCacheSizeLimit = this.mLowDiskSpaceCacheSizeLimit;
        } else {
            this.mCacheSizeLimit = this.mDefaultCacheSizeLimit;
        }
    }

    public long getSize() {
        return this.mCacheStats.getSize();
    }

    public long getCount() {
        return this.mCacheStats.getCount();
    }

    public void clearAll() {
        synchronized (this.mLock) {
            try {
                this.mStorage.clearAll();
                this.mResourceIndex.clear();
                this.mCacheEventListener.onCleared();
            } catch (IOException | NullPointerException e) {
                this.mCacheErrorLogger.logError(CacheErrorLogger.CacheErrorCategory.EVICTION, TAG, "clearAll: " + e.getMessage(), e);
            }
            this.mCacheStats.reset();
        }
    }

    public boolean hasKeySync(CacheKey key) {
        synchronized (this.mLock) {
            List<String> resourceIds = CacheKeyUtil.getResourceIds(key);
            for (int i = 0; i < resourceIds.size(); i++) {
                if (this.mResourceIndex.contains(resourceIds.get(i))) {
                    return true;
                }
            }
            return false;
        }
    }

    public boolean hasKey(CacheKey key) {
        synchronized (this.mLock) {
            if (hasKeySync(key)) {
                return true;
            }
            try {
                List<String> resourceIds = CacheKeyUtil.getResourceIds(key);
                for (int i = 0; i < resourceIds.size(); i++) {
                    String resourceId = resourceIds.get(i);
                    if (this.mStorage.contains(resourceId, key)) {
                        this.mResourceIndex.add(resourceId);
                        return true;
                    }
                }
                return false;
            } catch (IOException e) {
                return false;
            }
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:14:0x0030, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:16:0x0032, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void trimToMinimum() {
        /*
            r9 = this;
            java.lang.Object r0 = r9.mLock
            monitor-enter(r0)
            r9.maybeUpdateFileCacheSize()     // Catch:{ all -> 0x0033 }
            com.facebook.cache.disk.DiskStorageCache$CacheStats r1 = r9.mCacheStats     // Catch:{ all -> 0x0033 }
            long r1 = r1.getSize()     // Catch:{ all -> 0x0033 }
            long r3 = r9.mCacheSizeLimitMinimum     // Catch:{ all -> 0x0033 }
            r5 = 0
            int r7 = (r3 > r5 ? 1 : (r3 == r5 ? 0 : -1))
            if (r7 <= 0) goto L_0x0031
            int r5 = (r1 > r5 ? 1 : (r1 == r5 ? 0 : -1))
            if (r5 <= 0) goto L_0x0031
            int r5 = (r1 > r3 ? 1 : (r1 == r3 ? 0 : -1))
            if (r5 >= 0) goto L_0x001d
            goto L_0x0031
        L_0x001d:
            r5 = 4607182418800017408(0x3ff0000000000000, double:1.0)
            double r3 = (double) r3     // Catch:{ all -> 0x0033 }
            double r7 = (double) r1     // Catch:{ all -> 0x0033 }
            double r3 = r3 / r7
            double r5 = r5 - r3
            r3 = 4581421828931458171(0x3f947ae147ae147b, double:0.02)
            int r3 = (r5 > r3 ? 1 : (r5 == r3 ? 0 : -1))
            if (r3 <= 0) goto L_0x002f
            r9.trimBy(r5)     // Catch:{ all -> 0x0033 }
        L_0x002f:
            monitor-exit(r0)     // Catch:{ all -> 0x0033 }
            return
        L_0x0031:
            monitor-exit(r0)     // Catch:{ all -> 0x0033 }
            return
        L_0x0033:
            r1 = move-exception
            monitor-exit(r0)     // Catch:{ all -> 0x0033 }
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.facebook.cache.disk.DiskStorageCache.trimToMinimum():void");
    }

    public void trimToNothing() {
        clearAll();
    }

    private void trimBy(double trimRatio) {
        synchronized (this.mLock) {
            try {
                this.mCacheStats.reset();
                maybeUpdateFileCacheSize();
                long cacheSize = this.mCacheStats.getSize();
                evictAboveSize(cacheSize - ((long) (((double) cacheSize) * trimRatio)), CacheEventListener.EvictionReason.CACHE_MANAGER_TRIMMED);
            } catch (IOException ioe) {
                this.mCacheErrorLogger.logError(CacheErrorLogger.CacheErrorCategory.EVICTION, TAG, "trimBy: " + ioe.getMessage(), ioe);
            }
        }
    }

    /* access modifiers changed from: private */
    public boolean maybeUpdateFileCacheSize() {
        long now = this.mClock.now();
        if (this.mCacheStats.isInitialized()) {
            long j = this.mCacheSizeLastUpdateTime;
            if (j != -1 && now - j <= FILECACHE_SIZE_UPDATE_PERIOD_MS) {
                return false;
            }
        }
        return maybeUpdateFileCacheSizeAndIndex();
    }

    /* JADX WARNING: Code restructure failed: missing block: B:56:0x00fb, code lost:
        if (r1.mCacheStats.getSize() != r2) goto L_0x00fd;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private boolean maybeUpdateFileCacheSizeAndIndex() {
        /*
            r21 = this;
            r1 = r21
            r2 = 0
            r4 = 0
            r5 = 0
            r6 = 0
            r7 = 0
            r8 = -1
            com.facebook.common.time.Clock r0 = r1.mClock
            long r10 = r0.now()
            long r12 = FUTURE_TIMESTAMP_THRESHOLD_MS
            long r12 = r12 + r10
            boolean r0 = r1.mIndexPopulateAtStartupEnabled
            if (r0 == 0) goto L_0x0023
            java.util.Set<java.lang.String> r0 = r1.mResourceIndex
            boolean r0 = r0.isEmpty()
            if (r0 == 0) goto L_0x0023
            java.util.Set<java.lang.String> r0 = r1.mResourceIndex
            r14 = r0
            goto L_0x0030
        L_0x0023:
            boolean r0 = r1.mIndexPopulateAtStartupEnabled
            if (r0 == 0) goto L_0x002e
            java.util.HashSet r0 = new java.util.HashSet
            r0.<init>()
            r14 = r0
            goto L_0x0030
        L_0x002e:
            r0 = 0
            r14 = r0
        L_0x0030:
            com.facebook.cache.disk.DiskStorage r0 = r1.mStorage     // Catch:{ IOException -> 0x0129 }
            java.util.Collection r0 = r0.getEntries()     // Catch:{ IOException -> 0x0129 }
            java.util.Iterator r15 = r0.iterator()     // Catch:{ IOException -> 0x0129 }
        L_0x003a:
            boolean r16 = r15.hasNext()     // Catch:{ IOException -> 0x0122 }
            if (r16 == 0) goto L_0x0091
            java.lang.Object r16 = r15.next()     // Catch:{ IOException -> 0x008c }
            com.facebook.cache.disk.DiskStorage$Entry r16 = (com.facebook.cache.disk.DiskStorage.Entry) r16     // Catch:{ IOException -> 0x008c }
            int r4 = r4 + 1
            long r17 = r16.getSize()     // Catch:{ IOException -> 0x008c }
            long r2 = r2 + r17
            long r17 = r16.getTimestamp()     // Catch:{ IOException -> 0x0085 }
            int r17 = (r17 > r12 ? 1 : (r17 == r12 ? 0 : -1))
            if (r17 <= 0) goto L_0x006e
            r5 = 1
            int r6 = r6 + 1
            r17 = r2
            long r2 = (long) r7
            long r19 = r16.getSize()     // Catch:{ IOException -> 0x007e }
            long r2 = r2 + r19
            int r7 = (int) r2     // Catch:{ IOException -> 0x007e }
            long r2 = r16.getTimestamp()     // Catch:{ IOException -> 0x007e }
            long r2 = r2 - r10
            long r2 = java.lang.Math.max(r2, r8)     // Catch:{ IOException -> 0x007e }
            r8 = r2
            goto L_0x007b
        L_0x006e:
            r17 = r2
            boolean r2 = r1.mIndexPopulateAtStartupEnabled     // Catch:{ IOException -> 0x007e }
            if (r2 == 0) goto L_0x007b
            java.lang.String r2 = r16.getId()     // Catch:{ IOException -> 0x007e }
            r14.add(r2)     // Catch:{ IOException -> 0x007e }
        L_0x007b:
            r2 = r17
            goto L_0x003a
        L_0x007e:
            r0 = move-exception
            r2 = r17
            r18 = r12
            goto L_0x012c
        L_0x0085:
            r0 = move-exception
            r17 = r2
            r18 = r12
            goto L_0x012c
        L_0x008c:
            r0 = move-exception
            r18 = r12
            goto L_0x012c
        L_0x0091:
            if (r5 == 0) goto L_0x00e1
            com.facebook.cache.common.CacheErrorLogger r15 = r1.mCacheErrorLogger     // Catch:{ IOException -> 0x00db }
            r16 = r0
            com.facebook.cache.common.CacheErrorLogger$CacheErrorCategory r0 = com.facebook.cache.common.CacheErrorLogger.CacheErrorCategory.READ_INVALID_ENTRY     // Catch:{ IOException -> 0x00db }
            r17 = r5
            java.lang.Class<?> r5 = TAG     // Catch:{ IOException -> 0x00d5 }
            r18 = r12
            java.lang.StringBuilder r12 = new java.lang.StringBuilder     // Catch:{ IOException -> 0x00d1 }
            r12.<init>()     // Catch:{ IOException -> 0x00d1 }
            java.lang.String r13 = "Future timestamp found in "
            java.lang.StringBuilder r12 = r12.append(r13)     // Catch:{ IOException -> 0x00d1 }
            java.lang.StringBuilder r12 = r12.append(r6)     // Catch:{ IOException -> 0x00d1 }
            java.lang.String r13 = " files , with a total size of "
            java.lang.StringBuilder r12 = r12.append(r13)     // Catch:{ IOException -> 0x00d1 }
            java.lang.StringBuilder r12 = r12.append(r7)     // Catch:{ IOException -> 0x00d1 }
            java.lang.String r13 = " bytes, and a maximum time delta of "
            java.lang.StringBuilder r12 = r12.append(r13)     // Catch:{ IOException -> 0x00d1 }
            java.lang.StringBuilder r12 = r12.append(r8)     // Catch:{ IOException -> 0x00d1 }
            java.lang.String r13 = "ms"
            java.lang.StringBuilder r12 = r12.append(r13)     // Catch:{ IOException -> 0x00d1 }
            java.lang.String r12 = r12.toString()     // Catch:{ IOException -> 0x00d1 }
            r13 = 0
            r15.logError(r0, r5, r12, r13)     // Catch:{ IOException -> 0x00d1 }
            goto L_0x00e7
        L_0x00d1:
            r0 = move-exception
            r5 = r17
            goto L_0x012c
        L_0x00d5:
            r0 = move-exception
            r18 = r12
            r5 = r17
            goto L_0x012c
        L_0x00db:
            r0 = move-exception
            r17 = r5
            r18 = r12
            goto L_0x012c
        L_0x00e1:
            r16 = r0
            r17 = r5
            r18 = r12
        L_0x00e7:
            com.facebook.cache.disk.DiskStorageCache$CacheStats r0 = r1.mCacheStats     // Catch:{ IOException -> 0x011d }
            long r12 = r0.getCount()     // Catch:{ IOException -> 0x011d }
            r15 = r6
            long r5 = (long) r4
            int r0 = (r12 > r5 ? 1 : (r12 == r5 ? 0 : -1))
            if (r0 != 0) goto L_0x00fd
            com.facebook.cache.disk.DiskStorageCache$CacheStats r0 = r1.mCacheStats     // Catch:{ IOException -> 0x0118 }
            long r5 = r0.getSize()     // Catch:{ IOException -> 0x0118 }
            int r0 = (r5 > r2 ? 1 : (r5 == r2 ? 0 : -1))
            if (r0 == 0) goto L_0x0113
        L_0x00fd:
            boolean r0 = r1.mIndexPopulateAtStartupEnabled     // Catch:{ IOException -> 0x0118 }
            if (r0 == 0) goto L_0x010d
            java.util.Set<java.lang.String> r0 = r1.mResourceIndex     // Catch:{ IOException -> 0x0118 }
            if (r0 == r14) goto L_0x010d
            r0.clear()     // Catch:{ IOException -> 0x0118 }
            java.util.Set<java.lang.String> r0 = r1.mResourceIndex     // Catch:{ IOException -> 0x0118 }
            r0.addAll(r14)     // Catch:{ IOException -> 0x0118 }
        L_0x010d:
            com.facebook.cache.disk.DiskStorageCache$CacheStats r0 = r1.mCacheStats     // Catch:{ IOException -> 0x0118 }
            long r5 = (long) r4     // Catch:{ IOException -> 0x0118 }
            r0.set(r2, r5)     // Catch:{ IOException -> 0x0118 }
        L_0x0113:
            r1.mCacheSizeLastUpdateTime = r10
            r0 = 1
            return r0
        L_0x0118:
            r0 = move-exception
            r6 = r15
            r5 = r17
            goto L_0x012c
        L_0x011d:
            r0 = move-exception
            r15 = r6
            r5 = r17
            goto L_0x012c
        L_0x0122:
            r0 = move-exception
            r17 = r5
            r15 = r6
            r18 = r12
            goto L_0x012c
        L_0x0129:
            r0 = move-exception
            r18 = r12
        L_0x012c:
            com.facebook.cache.common.CacheErrorLogger r12 = r1.mCacheErrorLogger
            com.facebook.cache.common.CacheErrorLogger$CacheErrorCategory r13 = com.facebook.cache.common.CacheErrorLogger.CacheErrorCategory.GENERIC_IO
            java.lang.Class<?> r15 = TAG
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            r16 = r2
            java.lang.String r2 = "calcFileCacheSize: "
            java.lang.StringBuilder r1 = r1.append(r2)
            java.lang.String r2 = r0.getMessage()
            java.lang.StringBuilder r1 = r1.append(r2)
            java.lang.String r1 = r1.toString()
            r12.logError(r13, r15, r1, r0)
            r1 = 0
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.facebook.cache.disk.DiskStorageCache.maybeUpdateFileCacheSizeAndIndex():boolean");
    }
}
