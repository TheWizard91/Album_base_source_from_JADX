package com.facebook.imagepipeline.memory;

import android.graphics.Bitmap;
import com.facebook.common.internal.Preconditions;
import com.facebook.common.memory.MemoryTrimType;

public class DummyBitmapPool implements BitmapPool {
    public void trim(MemoryTrimType trimType) {
    }

    public Bitmap get(int size) {
        return Bitmap.createBitmap(1, (int) Math.ceil(((double) size) / 2.0d), Bitmap.Config.RGB_565);
    }

    public void release(Bitmap value) {
        Preconditions.checkNotNull(value);
        value.recycle();
    }
}
