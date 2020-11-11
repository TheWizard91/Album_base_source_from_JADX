package com.facebook.imagepipeline.memory;

import android.graphics.Bitmap;
import com.facebook.common.internal.Preconditions;
import com.facebook.common.references.ResourceReleaser;
import com.facebook.imageutils.BitmapUtil;

public class BitmapCounter {
    private int mCount;
    private final int mMaxCount;
    private final int mMaxSize;
    private long mSize;
    private final ResourceReleaser<Bitmap> mUnpooledBitmapsReleaser;

    public BitmapCounter(int maxCount, int maxSize) {
        boolean z = true;
        Preconditions.checkArgument(maxCount > 0);
        Preconditions.checkArgument(maxSize <= 0 ? false : z);
        this.mMaxCount = maxCount;
        this.mMaxSize = maxSize;
        this.mUnpooledBitmapsReleaser = new ResourceReleaser<Bitmap>() {
            public void release(Bitmap value) {
                try {
                    BitmapCounter.this.decrease(value);
                } finally {
                    value.recycle();
                }
            }
        };
    }

    public synchronized boolean increase(Bitmap bitmap) {
        int bitmapSize = BitmapUtil.getSizeInBytes(bitmap);
        int i = this.mCount;
        if (i < this.mMaxCount) {
            long j = this.mSize;
            if (((long) bitmapSize) + j <= ((long) this.mMaxSize)) {
                this.mCount = i + 1;
                this.mSize = j + ((long) bitmapSize);
                return true;
            }
        }
        return false;
    }

    public synchronized void decrease(Bitmap bitmap) {
        int bitmapSize = BitmapUtil.getSizeInBytes(bitmap);
        Preconditions.checkArgument(this.mCount > 0, "No bitmaps registered.");
        Preconditions.checkArgument(((long) bitmapSize) <= this.mSize, "Bitmap size bigger than the total registered size: %d, %d", Integer.valueOf(bitmapSize), Long.valueOf(this.mSize));
        this.mSize -= (long) bitmapSize;
        this.mCount--;
    }

    public synchronized int getCount() {
        return this.mCount;
    }

    public synchronized long getSize() {
        return this.mSize;
    }

    public synchronized int getMaxCount() {
        return this.mMaxCount;
    }

    public synchronized int getMaxSize() {
        return this.mMaxSize;
    }

    public ResourceReleaser<Bitmap> getReleaser() {
        return this.mUnpooledBitmapsReleaser;
    }
}
