package com.facebook.imagepipeline.memory;

import android.util.SparseArray;
import android.util.SparseIntArray;
import com.facebook.common.internal.Preconditions;
import com.facebook.common.internal.Sets;
import com.facebook.common.logging.FLog;
import com.facebook.common.memory.MemoryTrimType;
import com.facebook.common.memory.MemoryTrimmableRegistry;
import com.facebook.common.memory.Pool;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.Nullable;

public abstract class BasePool<V> implements Pool<V> {
    private final Class<?> TAG;
    private boolean mAllowNewBuckets;
    final SparseArray<Bucket<V>> mBuckets;
    final Counter mFree;
    private boolean mIgnoreHardCap;
    final Set<V> mInUseValues;
    final MemoryTrimmableRegistry mMemoryTrimmableRegistry;
    final PoolParams mPoolParams;
    private final PoolStatsTracker mPoolStatsTracker;
    final Counter mUsed;

    /* access modifiers changed from: protected */
    public abstract V alloc(int i);

    /* access modifiers changed from: protected */
    public abstract void free(V v);

    /* access modifiers changed from: protected */
    public abstract int getBucketedSize(int i);

    /* access modifiers changed from: protected */
    public abstract int getBucketedSizeForValue(V v);

    /* access modifiers changed from: protected */
    public abstract int getSizeInBytes(int i);

    public BasePool(MemoryTrimmableRegistry memoryTrimmableRegistry, PoolParams poolParams, PoolStatsTracker poolStatsTracker) {
        this.TAG = getClass();
        this.mMemoryTrimmableRegistry = (MemoryTrimmableRegistry) Preconditions.checkNotNull(memoryTrimmableRegistry);
        PoolParams poolParams2 = (PoolParams) Preconditions.checkNotNull(poolParams);
        this.mPoolParams = poolParams2;
        this.mPoolStatsTracker = (PoolStatsTracker) Preconditions.checkNotNull(poolStatsTracker);
        this.mBuckets = new SparseArray<>();
        if (poolParams2.fixBucketsReinitialization) {
            initBuckets();
        } else {
            legacyInitBuckets(new SparseIntArray(0));
        }
        this.mInUseValues = Sets.newIdentityHashSet();
        this.mFree = new Counter();
        this.mUsed = new Counter();
    }

    public BasePool(MemoryTrimmableRegistry memoryTrimmableRegistry, PoolParams poolParams, PoolStatsTracker poolStatsTracker, boolean ignoreHardCap) {
        this(memoryTrimmableRegistry, poolParams, poolStatsTracker);
        this.mIgnoreHardCap = ignoreHardCap;
    }

    /* access modifiers changed from: protected */
    public void initialize() {
        this.mMemoryTrimmableRegistry.registerMemoryTrimmable(this);
        this.mPoolStatsTracker.setBasePool(this);
    }

    /* access modifiers changed from: protected */
    @Nullable
    public synchronized V getValue(Bucket<V> bucket) {
        return bucket.get();
    }

    /* JADX WARNING: Code restructure failed: missing block: B:11:0x0055, code lost:
        return r4;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:21:0x0071, code lost:
        r5 = alloc(r0);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:22:0x0074, code lost:
        r4 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:23:0x0075, code lost:
        monitor-enter(r8);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:25:?, code lost:
        r8.mUsed.decrement(r1);
        r5 = getBucket(r0);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:26:0x007f, code lost:
        if (r5 != null) goto L_0x0081;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:27:0x0081, code lost:
        r5.decrementInUseCount();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:29:0x0085, code lost:
        com.facebook.common.internal.Throwables.propagateIfPossible(r4);
        r5 = null;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:30:0x0089, code lost:
        monitor-enter(r8);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:32:?, code lost:
        com.facebook.common.internal.Preconditions.checkState(r8.mInUseValues.add(r5));
        trimToSoftCap();
        r8.mPoolStatsTracker.onAlloc(r1);
        logStats();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:33:0x00a2, code lost:
        if (com.facebook.common.logging.FLog.isLoggable(2) == false) goto L_0x00b7;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:34:0x00a4, code lost:
        com.facebook.common.logging.FLog.m82v(r8.TAG, "get (alloc) (object, size) = (%x, %s)", (java.lang.Object) java.lang.Integer.valueOf(java.lang.System.identityHashCode(r5)), (java.lang.Object) java.lang.Integer.valueOf(r0));
     */
    /* JADX WARNING: Code restructure failed: missing block: B:36:0x00b8, code lost:
        return r5;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public V get(int r9) {
        /*
            r8 = this;
            r8.ensurePoolSizeInvariant()
            int r0 = r8.getBucketedSize(r9)
            r1 = -1
            monitor-enter(r8)
            com.facebook.imagepipeline.memory.Bucket r2 = r8.getBucket(r0)     // Catch:{ all -> 0x00d1 }
            r3 = 2
            if (r2 == 0) goto L_0x0056
            java.lang.Object r4 = r8.getValue(r2)     // Catch:{ all -> 0x00d1 }
            if (r4 == 0) goto L_0x0056
            java.util.Set<V> r5 = r8.mInUseValues     // Catch:{ all -> 0x00d1 }
            boolean r5 = r5.add(r4)     // Catch:{ all -> 0x00d1 }
            com.facebook.common.internal.Preconditions.checkState(r5)     // Catch:{ all -> 0x00d1 }
            int r5 = r8.getBucketedSizeForValue(r4)     // Catch:{ all -> 0x00d1 }
            r0 = r5
            int r5 = r8.getSizeInBytes(r0)     // Catch:{ all -> 0x00d1 }
            r1 = r5
            com.facebook.imagepipeline.memory.BasePool$Counter r5 = r8.mUsed     // Catch:{ all -> 0x00d1 }
            r5.increment(r1)     // Catch:{ all -> 0x00d1 }
            com.facebook.imagepipeline.memory.BasePool$Counter r5 = r8.mFree     // Catch:{ all -> 0x00d1 }
            r5.decrement(r1)     // Catch:{ all -> 0x00d1 }
            com.facebook.imagepipeline.memory.PoolStatsTracker r5 = r8.mPoolStatsTracker     // Catch:{ all -> 0x00d1 }
            r5.onValueReuse(r1)     // Catch:{ all -> 0x00d1 }
            r8.logStats()     // Catch:{ all -> 0x00d1 }
            boolean r3 = com.facebook.common.logging.FLog.isLoggable(r3)     // Catch:{ all -> 0x00d1 }
            if (r3 == 0) goto L_0x0054
            java.lang.Class<?> r3 = r8.TAG     // Catch:{ all -> 0x00d1 }
            java.lang.String r5 = "get (reuse) (object, size) = (%x, %s)"
            int r6 = java.lang.System.identityHashCode(r4)     // Catch:{ all -> 0x00d1 }
            java.lang.Integer r6 = java.lang.Integer.valueOf(r6)     // Catch:{ all -> 0x00d1 }
            java.lang.Integer r7 = java.lang.Integer.valueOf(r0)     // Catch:{ all -> 0x00d1 }
            com.facebook.common.logging.FLog.m82v((java.lang.Class<?>) r3, (java.lang.String) r5, (java.lang.Object) r6, (java.lang.Object) r7)     // Catch:{ all -> 0x00d1 }
        L_0x0054:
            monitor-exit(r8)     // Catch:{ all -> 0x00d1 }
            return r4
        L_0x0056:
            int r4 = r8.getSizeInBytes(r0)     // Catch:{ all -> 0x00d1 }
            r1 = r4
            boolean r4 = r8.canAllocate(r1)     // Catch:{ all -> 0x00d1 }
            if (r4 == 0) goto L_0x00bf
            com.facebook.imagepipeline.memory.BasePool$Counter r4 = r8.mUsed     // Catch:{ all -> 0x00d1 }
            r4.increment(r1)     // Catch:{ all -> 0x00d1 }
            if (r2 == 0) goto L_0x006b
            r2.incrementInUseCount()     // Catch:{ all -> 0x00d1 }
        L_0x006b:
            monitor-exit(r8)     // Catch:{ all -> 0x00d1 }
            r2 = 0
            java.lang.Object r4 = r8.alloc(r0)     // Catch:{ all -> 0x0074 }
            r2 = r4
            r5 = r2
            goto L_0x0089
        L_0x0074:
            r4 = move-exception
            monitor-enter(r8)
            com.facebook.imagepipeline.memory.BasePool$Counter r5 = r8.mUsed     // Catch:{ all -> 0x00bc }
            r5.decrement(r1)     // Catch:{ all -> 0x00bc }
            com.facebook.imagepipeline.memory.Bucket r5 = r8.getBucket(r0)     // Catch:{ all -> 0x00bc }
            if (r5 == 0) goto L_0x0084
            r5.decrementInUseCount()     // Catch:{ all -> 0x00bc }
        L_0x0084:
            monitor-exit(r8)     // Catch:{ all -> 0x00bc }
            com.facebook.common.internal.Throwables.propagateIfPossible(r4)
            r5 = r2
        L_0x0089:
            monitor-enter(r8)
            java.util.Set<V> r2 = r8.mInUseValues     // Catch:{ all -> 0x00b9 }
            boolean r2 = r2.add(r5)     // Catch:{ all -> 0x00b9 }
            com.facebook.common.internal.Preconditions.checkState(r2)     // Catch:{ all -> 0x00b9 }
            r8.trimToSoftCap()     // Catch:{ all -> 0x00b9 }
            com.facebook.imagepipeline.memory.PoolStatsTracker r2 = r8.mPoolStatsTracker     // Catch:{ all -> 0x00b9 }
            r2.onAlloc(r1)     // Catch:{ all -> 0x00b9 }
            r8.logStats()     // Catch:{ all -> 0x00b9 }
            boolean r2 = com.facebook.common.logging.FLog.isLoggable(r3)     // Catch:{ all -> 0x00b9 }
            if (r2 == 0) goto L_0x00b7
            java.lang.Class<?> r2 = r8.TAG     // Catch:{ all -> 0x00b9 }
            java.lang.String r3 = "get (alloc) (object, size) = (%x, %s)"
            int r4 = java.lang.System.identityHashCode(r5)     // Catch:{ all -> 0x00b9 }
            java.lang.Integer r4 = java.lang.Integer.valueOf(r4)     // Catch:{ all -> 0x00b9 }
            java.lang.Integer r6 = java.lang.Integer.valueOf(r0)     // Catch:{ all -> 0x00b9 }
            com.facebook.common.logging.FLog.m82v((java.lang.Class<?>) r2, (java.lang.String) r3, (java.lang.Object) r4, (java.lang.Object) r6)     // Catch:{ all -> 0x00b9 }
        L_0x00b7:
            monitor-exit(r8)     // Catch:{ all -> 0x00b9 }
            return r5
        L_0x00b9:
            r2 = move-exception
            monitor-exit(r8)     // Catch:{ all -> 0x00b9 }
            throw r2
        L_0x00bc:
            r3 = move-exception
            monitor-exit(r8)     // Catch:{ all -> 0x00bc }
            throw r3
        L_0x00bf:
            com.facebook.imagepipeline.memory.BasePool$PoolSizeViolationException r3 = new com.facebook.imagepipeline.memory.BasePool$PoolSizeViolationException     // Catch:{ all -> 0x00d1 }
            com.facebook.imagepipeline.memory.PoolParams r4 = r8.mPoolParams     // Catch:{ all -> 0x00d1 }
            int r4 = r4.maxSizeHardCap     // Catch:{ all -> 0x00d1 }
            com.facebook.imagepipeline.memory.BasePool$Counter r5 = r8.mUsed     // Catch:{ all -> 0x00d1 }
            int r5 = r5.mNumBytes     // Catch:{ all -> 0x00d1 }
            com.facebook.imagepipeline.memory.BasePool$Counter r6 = r8.mFree     // Catch:{ all -> 0x00d1 }
            int r6 = r6.mNumBytes     // Catch:{ all -> 0x00d1 }
            r3.<init>(r4, r5, r6, r1)     // Catch:{ all -> 0x00d1 }
            throw r3     // Catch:{ all -> 0x00d1 }
        L_0x00d1:
            r2 = move-exception
            monitor-exit(r8)     // Catch:{ all -> 0x00d1 }
            throw r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.facebook.imagepipeline.memory.BasePool.get(int):java.lang.Object");
    }

    public void release(V value) {
        Preconditions.checkNotNull(value);
        int bucketedSize = getBucketedSizeForValue(value);
        int sizeInBytes = getSizeInBytes(bucketedSize);
        synchronized (this) {
            Bucket<V> bucket = getBucketIfPresent(bucketedSize);
            if (!this.mInUseValues.remove(value)) {
                FLog.m58e(this.TAG, "release (free, value unrecognized) (object, size) = (%x, %s)", Integer.valueOf(System.identityHashCode(value)), Integer.valueOf(bucketedSize));
                free(value);
                this.mPoolStatsTracker.onFree(sizeInBytes);
            } else {
                if (bucket != null && !bucket.isMaxLengthExceeded() && !isMaxSizeSoftCapExceeded()) {
                    if (isReusable(value)) {
                        bucket.release(value);
                        this.mFree.increment(sizeInBytes);
                        this.mUsed.decrement(sizeInBytes);
                        this.mPoolStatsTracker.onValueRelease(sizeInBytes);
                        if (FLog.isLoggable(2)) {
                            FLog.m82v(this.TAG, "release (reuse) (object, size) = (%x, %s)", (Object) Integer.valueOf(System.identityHashCode(value)), (Object) Integer.valueOf(bucketedSize));
                        }
                    }
                }
                if (bucket != null) {
                    bucket.decrementInUseCount();
                }
                if (FLog.isLoggable(2)) {
                    FLog.m82v(this.TAG, "release (free) (object, size) = (%x, %s)", (Object) Integer.valueOf(System.identityHashCode(value)), (Object) Integer.valueOf(bucketedSize));
                }
                free(value);
                this.mUsed.decrement(sizeInBytes);
                this.mPoolStatsTracker.onFree(sizeInBytes);
            }
            logStats();
        }
    }

    public void trim(MemoryTrimType memoryTrimType) {
        trimToNothing();
    }

    /* access modifiers changed from: protected */
    public void onParamsChanged() {
    }

    /* access modifiers changed from: protected */
    public boolean isReusable(V value) {
        Preconditions.checkNotNull(value);
        return true;
    }

    private synchronized void ensurePoolSizeInvariant() {
        boolean z;
        if (isMaxSizeSoftCapExceeded()) {
            if (this.mFree.mNumBytes != 0) {
                z = false;
                Preconditions.checkState(z);
            }
        }
        z = true;
        Preconditions.checkState(z);
    }

    private synchronized void legacyInitBuckets(SparseIntArray inUseCounts) {
        Preconditions.checkNotNull(inUseCounts);
        this.mBuckets.clear();
        SparseIntArray bucketSizes = this.mPoolParams.bucketSizes;
        if (bucketSizes != null) {
            for (int i = 0; i < bucketSizes.size(); i++) {
                int bucketSize = bucketSizes.keyAt(i);
                this.mBuckets.put(bucketSize, new Bucket(getSizeInBytes(bucketSize), bucketSizes.valueAt(i), inUseCounts.get(bucketSize, 0), this.mPoolParams.fixBucketsReinitialization));
            }
            this.mAllowNewBuckets = false;
        } else {
            this.mAllowNewBuckets = true;
        }
    }

    private synchronized void initBuckets() {
        SparseIntArray bucketSizes = this.mPoolParams.bucketSizes;
        if (bucketSizes != null) {
            fillBuckets(bucketSizes);
            this.mAllowNewBuckets = false;
        } else {
            this.mAllowNewBuckets = true;
        }
    }

    private void fillBuckets(SparseIntArray bucketSizes) {
        this.mBuckets.clear();
        for (int i = 0; i < bucketSizes.size(); i++) {
            int bucketSize = bucketSizes.keyAt(i);
            this.mBuckets.put(bucketSize, new Bucket(getSizeInBytes(bucketSize), bucketSizes.valueAt(i), 0, this.mPoolParams.fixBucketsReinitialization));
        }
    }

    private List<Bucket<V>> refillBuckets() {
        List<Bucket<V>> bucketsToTrim = new ArrayList<>(this.mBuckets.size());
        int len = this.mBuckets.size();
        for (int i = 0; i < len; i++) {
            Bucket<V> oldBucket = this.mBuckets.valueAt(i);
            int bucketSize = oldBucket.mItemSize;
            int maxLength = oldBucket.mMaxLength;
            int bucketInUseCount = oldBucket.getInUseCount();
            if (oldBucket.getFreeListSize() > 0) {
                bucketsToTrim.add(oldBucket);
            }
            this.mBuckets.setValueAt(i, new Bucket(getSizeInBytes(bucketSize), maxLength, bucketInUseCount, this.mPoolParams.fixBucketsReinitialization));
        }
        return bucketsToTrim;
    }

    /* access modifiers changed from: package-private */
    public void trimToNothing() {
        List<Bucket<V>> bucketsToTrim;
        synchronized (this) {
            if (this.mPoolParams.fixBucketsReinitialization) {
                bucketsToTrim = refillBuckets();
            } else {
                bucketsToTrim = new ArrayList<>(this.mBuckets.size());
                SparseIntArray inUseCounts = new SparseIntArray();
                for (int i = 0; i < this.mBuckets.size(); i++) {
                    Bucket<V> bucket = this.mBuckets.valueAt(i);
                    if (bucket.getFreeListSize() > 0) {
                        bucketsToTrim.add(bucket);
                    }
                    inUseCounts.put(this.mBuckets.keyAt(i), bucket.getInUseCount());
                }
                legacyInitBuckets(inUseCounts);
            }
            this.mFree.reset();
            logStats();
        }
        onParamsChanged();
        for (int i2 = 0; i2 < bucketsToTrim.size(); i2++) {
            Bucket<V> bucket2 = bucketsToTrim.get(i2);
            while (true) {
                V item = bucket2.pop();
                if (item == null) {
                    break;
                }
                free(item);
            }
        }
    }

    /* access modifiers changed from: package-private */
    public synchronized void trimToSoftCap() {
        if (isMaxSizeSoftCapExceeded()) {
            trimToSize(this.mPoolParams.maxSizeSoftCap);
        }
    }

    /* access modifiers changed from: package-private */
    /* JADX WARNING: Code restructure failed: missing block: B:27:0x008f, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized void trimToSize(int r8) {
        /*
            r7 = this;
            monitor-enter(r7)
            com.facebook.imagepipeline.memory.BasePool$Counter r0 = r7.mUsed     // Catch:{ all -> 0x0090 }
            int r0 = r0.mNumBytes     // Catch:{ all -> 0x0090 }
            com.facebook.imagepipeline.memory.BasePool$Counter r1 = r7.mFree     // Catch:{ all -> 0x0090 }
            int r1 = r1.mNumBytes     // Catch:{ all -> 0x0090 }
            int r0 = r0 + r1
            int r0 = r0 - r8
            com.facebook.imagepipeline.memory.BasePool$Counter r1 = r7.mFree     // Catch:{ all -> 0x0090 }
            int r1 = r1.mNumBytes     // Catch:{ all -> 0x0090 }
            int r0 = java.lang.Math.min(r0, r1)     // Catch:{ all -> 0x0090 }
            if (r0 > 0) goto L_0x0017
            monitor-exit(r7)
            return
        L_0x0017:
            r1 = 2
            boolean r2 = com.facebook.common.logging.FLog.isLoggable(r1)     // Catch:{ all -> 0x0090 }
            if (r2 == 0) goto L_0x003b
            java.lang.Class<?> r2 = r7.TAG     // Catch:{ all -> 0x0090 }
            java.lang.String r3 = "trimToSize: TargetSize = %d; Initial Size = %d; Bytes to free = %d"
            java.lang.Integer r4 = java.lang.Integer.valueOf(r8)     // Catch:{ all -> 0x0090 }
            com.facebook.imagepipeline.memory.BasePool$Counter r5 = r7.mUsed     // Catch:{ all -> 0x0090 }
            int r5 = r5.mNumBytes     // Catch:{ all -> 0x0090 }
            com.facebook.imagepipeline.memory.BasePool$Counter r6 = r7.mFree     // Catch:{ all -> 0x0090 }
            int r6 = r6.mNumBytes     // Catch:{ all -> 0x0090 }
            int r5 = r5 + r6
            java.lang.Integer r5 = java.lang.Integer.valueOf(r5)     // Catch:{ all -> 0x0090 }
            java.lang.Integer r6 = java.lang.Integer.valueOf(r0)     // Catch:{ all -> 0x0090 }
            com.facebook.common.logging.FLog.m83v((java.lang.Class<?>) r2, (java.lang.String) r3, (java.lang.Object) r4, (java.lang.Object) r5, (java.lang.Object) r6)     // Catch:{ all -> 0x0090 }
        L_0x003b:
            r7.logStats()     // Catch:{ all -> 0x0090 }
            r2 = 0
        L_0x003f:
            android.util.SparseArray<com.facebook.imagepipeline.memory.Bucket<V>> r3 = r7.mBuckets     // Catch:{ all -> 0x0090 }
            int r3 = r3.size()     // Catch:{ all -> 0x0090 }
            if (r2 >= r3) goto L_0x006c
            if (r0 > 0) goto L_0x004a
            goto L_0x006c
        L_0x004a:
            android.util.SparseArray<com.facebook.imagepipeline.memory.Bucket<V>> r3 = r7.mBuckets     // Catch:{ all -> 0x0090 }
            java.lang.Object r3 = r3.valueAt(r2)     // Catch:{ all -> 0x0090 }
            com.facebook.imagepipeline.memory.Bucket r3 = (com.facebook.imagepipeline.memory.Bucket) r3     // Catch:{ all -> 0x0090 }
        L_0x0052:
            if (r0 <= 0) goto L_0x0069
            java.lang.Object r4 = r3.pop()     // Catch:{ all -> 0x0090 }
            if (r4 != 0) goto L_0x005b
            goto L_0x0069
        L_0x005b:
            r7.free(r4)     // Catch:{ all -> 0x0090 }
            int r5 = r3.mItemSize     // Catch:{ all -> 0x0090 }
            int r0 = r0 - r5
            com.facebook.imagepipeline.memory.BasePool$Counter r5 = r7.mFree     // Catch:{ all -> 0x0090 }
            int r6 = r3.mItemSize     // Catch:{ all -> 0x0090 }
            r5.decrement(r6)     // Catch:{ all -> 0x0090 }
            goto L_0x0052
        L_0x0069:
            int r2 = r2 + 1
            goto L_0x003f
        L_0x006c:
            r7.logStats()     // Catch:{ all -> 0x0090 }
            boolean r1 = com.facebook.common.logging.FLog.isLoggable(r1)     // Catch:{ all -> 0x0090 }
            if (r1 == 0) goto L_0x008e
            java.lang.Class<?> r1 = r7.TAG     // Catch:{ all -> 0x0090 }
            java.lang.String r2 = "trimToSize: TargetSize = %d; Final Size = %d"
            java.lang.Integer r3 = java.lang.Integer.valueOf(r8)     // Catch:{ all -> 0x0090 }
            com.facebook.imagepipeline.memory.BasePool$Counter r4 = r7.mUsed     // Catch:{ all -> 0x0090 }
            int r4 = r4.mNumBytes     // Catch:{ all -> 0x0090 }
            com.facebook.imagepipeline.memory.BasePool$Counter r5 = r7.mFree     // Catch:{ all -> 0x0090 }
            int r5 = r5.mNumBytes     // Catch:{ all -> 0x0090 }
            int r4 = r4 + r5
            java.lang.Integer r4 = java.lang.Integer.valueOf(r4)     // Catch:{ all -> 0x0090 }
            com.facebook.common.logging.FLog.m82v((java.lang.Class<?>) r1, (java.lang.String) r2, (java.lang.Object) r3, (java.lang.Object) r4)     // Catch:{ all -> 0x0090 }
        L_0x008e:
            monitor-exit(r7)
            return
        L_0x0090:
            r8 = move-exception
            monitor-exit(r7)
            throw r8
        */
        throw new UnsupportedOperationException("Method not decompiled: com.facebook.imagepipeline.memory.BasePool.trimToSize(int):void");
    }

    private synchronized Bucket<V> getBucketIfPresent(int bucketedSize) {
        return this.mBuckets.get(bucketedSize);
    }

    /* access modifiers changed from: package-private */
    /* JADX WARNING: Code restructure failed: missing block: B:14:0x002e, code lost:
        return r0;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized com.facebook.imagepipeline.memory.Bucket<V> getBucket(int r5) {
        /*
            r4 = this;
            monitor-enter(r4)
            android.util.SparseArray<com.facebook.imagepipeline.memory.Bucket<V>> r0 = r4.mBuckets     // Catch:{ all -> 0x002f }
            java.lang.Object r0 = r0.get(r5)     // Catch:{ all -> 0x002f }
            com.facebook.imagepipeline.memory.Bucket r0 = (com.facebook.imagepipeline.memory.Bucket) r0     // Catch:{ all -> 0x002f }
            if (r0 != 0) goto L_0x002d
            boolean r1 = r4.mAllowNewBuckets     // Catch:{ all -> 0x002f }
            if (r1 != 0) goto L_0x0010
            goto L_0x002d
        L_0x0010:
            r1 = 2
            boolean r1 = com.facebook.common.logging.FLog.isLoggable(r1)     // Catch:{ all -> 0x002f }
            if (r1 == 0) goto L_0x0022
            java.lang.Class<?> r1 = r4.TAG     // Catch:{ all -> 0x002f }
            java.lang.String r2 = "creating new bucket %s"
            java.lang.Integer r3 = java.lang.Integer.valueOf(r5)     // Catch:{ all -> 0x002f }
            com.facebook.common.logging.FLog.m81v((java.lang.Class<?>) r1, (java.lang.String) r2, (java.lang.Object) r3)     // Catch:{ all -> 0x002f }
        L_0x0022:
            com.facebook.imagepipeline.memory.Bucket r1 = r4.newBucket(r5)     // Catch:{ all -> 0x002f }
            android.util.SparseArray<com.facebook.imagepipeline.memory.Bucket<V>> r2 = r4.mBuckets     // Catch:{ all -> 0x002f }
            r2.put(r5, r1)     // Catch:{ all -> 0x002f }
            monitor-exit(r4)
            return r1
        L_0x002d:
            monitor-exit(r4)
            return r0
        L_0x002f:
            r5 = move-exception
            monitor-exit(r4)
            throw r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.facebook.imagepipeline.memory.BasePool.getBucket(int):com.facebook.imagepipeline.memory.Bucket");
    }

    /* access modifiers changed from: package-private */
    public Bucket<V> newBucket(int bucketedSize) {
        return new Bucket<>(getSizeInBytes(bucketedSize), Integer.MAX_VALUE, 0, this.mPoolParams.fixBucketsReinitialization);
    }

    /* access modifiers changed from: package-private */
    public synchronized boolean isMaxSizeSoftCapExceeded() {
        boolean isMaxSizeSoftCapExceeded;
        isMaxSizeSoftCapExceeded = this.mUsed.mNumBytes + this.mFree.mNumBytes > this.mPoolParams.maxSizeSoftCap;
        if (isMaxSizeSoftCapExceeded) {
            this.mPoolStatsTracker.onSoftCapReached();
        }
        return isMaxSizeSoftCapExceeded;
    }

    /* access modifiers changed from: package-private */
    public synchronized boolean canAllocate(int sizeInBytes) {
        if (this.mIgnoreHardCap) {
            return true;
        }
        int hardCap = this.mPoolParams.maxSizeHardCap;
        if (sizeInBytes > hardCap - this.mUsed.mNumBytes) {
            this.mPoolStatsTracker.onHardCapReached();
            return false;
        }
        int softCap = this.mPoolParams.maxSizeSoftCap;
        if (sizeInBytes > softCap - (this.mUsed.mNumBytes + this.mFree.mNumBytes)) {
            trimToSize(softCap - sizeInBytes);
        }
        if (sizeInBytes <= hardCap - (this.mUsed.mNumBytes + this.mFree.mNumBytes)) {
            return true;
        }
        this.mPoolStatsTracker.onHardCapReached();
        return false;
    }

    private void logStats() {
        if (FLog.isLoggable(2)) {
            FLog.m84v(this.TAG, "Used = (%d, %d); Free = (%d, %d)", (Object) Integer.valueOf(this.mUsed.mCount), (Object) Integer.valueOf(this.mUsed.mNumBytes), (Object) Integer.valueOf(this.mFree.mCount), (Object) Integer.valueOf(this.mFree.mNumBytes));
        }
    }

    public synchronized Map<String, Integer> getStats() {
        Map<String, Integer> stats;
        stats = new HashMap<>();
        for (int i = 0; i < this.mBuckets.size(); i++) {
            stats.put(PoolStatsTracker.BUCKETS_USED_PREFIX + getSizeInBytes(this.mBuckets.keyAt(i)), Integer.valueOf(this.mBuckets.valueAt(i).getInUseCount()));
        }
        stats.put(PoolStatsTracker.SOFT_CAP, Integer.valueOf(this.mPoolParams.maxSizeSoftCap));
        stats.put(PoolStatsTracker.HARD_CAP, Integer.valueOf(this.mPoolParams.maxSizeHardCap));
        stats.put(PoolStatsTracker.USED_COUNT, Integer.valueOf(this.mUsed.mCount));
        stats.put(PoolStatsTracker.USED_BYTES, Integer.valueOf(this.mUsed.mNumBytes));
        stats.put(PoolStatsTracker.FREE_COUNT, Integer.valueOf(this.mFree.mCount));
        stats.put(PoolStatsTracker.FREE_BYTES, Integer.valueOf(this.mFree.mNumBytes));
        return stats;
    }

    static class Counter {
        private static final String TAG = "com.facebook.imagepipeline.memory.BasePool.Counter";
        int mCount;
        int mNumBytes;

        Counter() {
        }

        public void increment(int numBytes) {
            this.mCount++;
            this.mNumBytes += numBytes;
        }

        public void decrement(int numBytes) {
            int i;
            int i2 = this.mNumBytes;
            if (i2 < numBytes || (i = this.mCount) <= 0) {
                FLog.wtf(TAG, "Unexpected decrement of %d. Current numBytes = %d, count = %d", Integer.valueOf(numBytes), Integer.valueOf(this.mNumBytes), Integer.valueOf(this.mCount));
                return;
            }
            this.mCount = i - 1;
            this.mNumBytes = i2 - numBytes;
        }

        public void reset() {
            this.mCount = 0;
            this.mNumBytes = 0;
        }
    }

    public static class InvalidValueException extends RuntimeException {
        public InvalidValueException(Object value) {
            super("Invalid value: " + value.toString());
        }
    }

    public static class InvalidSizeException extends RuntimeException {
        public InvalidSizeException(Object size) {
            super("Invalid size: " + size.toString());
        }
    }

    public static class SizeTooLargeException extends InvalidSizeException {
        public SizeTooLargeException(Object size) {
            super(size);
        }
    }

    public static class PoolSizeViolationException extends RuntimeException {
        public PoolSizeViolationException(int hardCap, int usedBytes, int freeBytes, int allocSize) {
            super("Pool hard cap violation? Hard cap = " + hardCap + " Used size = " + usedBytes + " Free size = " + freeBytes + " Request size = " + allocSize);
        }
    }
}
