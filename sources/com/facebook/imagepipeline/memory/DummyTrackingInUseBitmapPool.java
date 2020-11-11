package com.facebook.imagepipeline.memory;

import android.graphics.Bitmap;
import com.facebook.common.internal.Preconditions;
import com.facebook.common.internal.Sets;
import com.facebook.common.memory.MemoryTrimType;
import java.util.Set;

public class DummyTrackingInUseBitmapPool implements BitmapPool {
    private final Set<Bitmap> mInUseValues = Sets.newIdentityHashSet();

    public void trim(MemoryTrimType trimType) {
    }

    public Bitmap get(int size) {
        Bitmap result = Bitmap.createBitmap(1, (int) Math.ceil(((double) size) / 2.0d), Bitmap.Config.RGB_565);
        this.mInUseValues.add(result);
        return result;
    }

    public void release(Bitmap value) {
        Preconditions.checkNotNull(value);
        this.mInUseValues.remove(value);
        value.recycle();
    }
}
