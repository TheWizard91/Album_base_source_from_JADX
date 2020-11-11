package com.facebook.common.statfs;

import android.os.Build;
import android.os.Environment;
import android.os.StatFs;
import android.os.SystemClock;
import com.facebook.common.internal.Throwables;
import java.io.File;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import javax.annotation.Nullable;

public class StatFsHelper {
    public static final long DEFAULT_DISK_RED_LEVEL_IN_BYTES = 104857600;
    public static final int DEFAULT_DISK_RED_LEVEL_IN_MB = 100;
    public static final long DEFAULT_DISK_YELLOW_LEVEL_IN_BYTES = 419430400;
    public static final int DEFAULT_DISK_YELLOW_LEVEL_IN_MB = 400;
    private static final long RESTAT_INTERVAL_MS = TimeUnit.MINUTES.toMillis(2);
    private static StatFsHelper sStatsFsHelper;
    private final Lock lock = new ReentrantLock();
    private volatile File mExternalPath;
    @Nullable
    private volatile StatFs mExternalStatFs = null;
    private volatile boolean mInitialized = false;
    private volatile File mInternalPath;
    @Nullable
    private volatile StatFs mInternalStatFs = null;
    private long mLastRestatTime;

    public enum StorageType {
        INTERNAL,
        EXTERNAL
    }

    public static synchronized StatFsHelper getInstance() {
        StatFsHelper statFsHelper;
        synchronized (StatFsHelper.class) {
            if (sStatsFsHelper == null) {
                sStatsFsHelper = new StatFsHelper();
            }
            statFsHelper = sStatsFsHelper;
        }
        return statFsHelper;
    }

    protected StatFsHelper() {
    }

    private void ensureInitialized() {
        if (!this.mInitialized) {
            this.lock.lock();
            try {
                if (!this.mInitialized) {
                    this.mInternalPath = Environment.getDataDirectory();
                    this.mExternalPath = Environment.getExternalStorageDirectory();
                    updateStats();
                    this.mInitialized = true;
                }
            } finally {
                this.lock.unlock();
            }
        }
    }

    public boolean testLowDiskSpace(StorageType storageType, long freeSpaceThreshold) {
        ensureInitialized();
        long availableStorageSpace = getAvailableStorageSpace(storageType);
        if (availableStorageSpace <= 0 || availableStorageSpace < freeSpaceThreshold) {
            return true;
        }
        return false;
    }

    public long getFreeStorageSpace(StorageType storageType) {
        long availableBlocks;
        long blockSize;
        ensureInitialized();
        maybeUpdateStats();
        StatFs statFS = storageType == StorageType.INTERNAL ? this.mInternalStatFs : this.mExternalStatFs;
        if (statFS == null) {
            return -1;
        }
        if (Build.VERSION.SDK_INT >= 18) {
            blockSize = statFS.getBlockSizeLong();
            availableBlocks = statFS.getFreeBlocksLong();
        } else {
            blockSize = (long) statFS.getBlockSize();
            availableBlocks = (long) statFS.getFreeBlocks();
        }
        return blockSize * availableBlocks;
    }

    public long getTotalStorageSpace(StorageType storageType) {
        long totalBlocks;
        long blockSize;
        ensureInitialized();
        maybeUpdateStats();
        StatFs statFS = storageType == StorageType.INTERNAL ? this.mInternalStatFs : this.mExternalStatFs;
        if (statFS == null) {
            return -1;
        }
        if (Build.VERSION.SDK_INT >= 18) {
            blockSize = statFS.getBlockSizeLong();
            totalBlocks = statFS.getBlockCountLong();
        } else {
            blockSize = (long) statFS.getBlockSize();
            totalBlocks = (long) statFS.getBlockCount();
        }
        return blockSize * totalBlocks;
    }

    public long getAvailableStorageSpace(StorageType storageType) {
        long availableBlocks;
        long blockSize;
        ensureInitialized();
        maybeUpdateStats();
        StatFs statFS = storageType == StorageType.INTERNAL ? this.mInternalStatFs : this.mExternalStatFs;
        if (statFS == null) {
            return 0;
        }
        if (Build.VERSION.SDK_INT >= 18) {
            blockSize = statFS.getBlockSizeLong();
            availableBlocks = statFS.getAvailableBlocksLong();
        } else {
            blockSize = (long) statFS.getBlockSize();
            availableBlocks = (long) statFS.getAvailableBlocks();
        }
        return blockSize * availableBlocks;
    }

    public boolean isLowSpaceCondition() {
        return getAvailableStorageSpace(StorageType.INTERNAL) < DEFAULT_DISK_YELLOW_LEVEL_IN_BYTES;
    }

    public boolean isVeryLowSpaceCondition() {
        return getAvailableStorageSpace(StorageType.INTERNAL) < DEFAULT_DISK_RED_LEVEL_IN_BYTES;
    }

    private void maybeUpdateStats() {
        if (this.lock.tryLock()) {
            try {
                if (SystemClock.uptimeMillis() - this.mLastRestatTime > RESTAT_INTERVAL_MS) {
                    updateStats();
                }
            } finally {
                this.lock.unlock();
            }
        }
    }

    public void resetStats() {
        if (this.lock.tryLock()) {
            try {
                ensureInitialized();
                updateStats();
            } finally {
                this.lock.unlock();
            }
        }
    }

    private void updateStats() {
        this.mInternalStatFs = updateStatsHelper(this.mInternalStatFs, this.mInternalPath);
        this.mExternalStatFs = updateStatsHelper(this.mExternalStatFs, this.mExternalPath);
        this.mLastRestatTime = SystemClock.uptimeMillis();
    }

    @Nullable
    private StatFs updateStatsHelper(@Nullable StatFs statfs, @Nullable File dir) {
        if (dir == null || !dir.exists()) {
            return null;
        }
        if (statfs == null) {
            try {
                return createStatFs(dir.getAbsolutePath());
            } catch (IllegalArgumentException e) {
                return null;
            } catch (Throwable ex) {
                throw Throwables.propagate(ex);
            }
        } else {
            statfs.restat(dir.getAbsolutePath());
            return statfs;
        }
    }

    protected static StatFs createStatFs(String path) {
        return new StatFs(path);
    }
}
