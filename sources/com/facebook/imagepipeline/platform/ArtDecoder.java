package com.facebook.imagepipeline.platform;

import android.graphics.BitmapFactory;
import androidx.core.util.Pools;
import com.facebook.imagepipeline.memory.BitmapPool;
import com.facebook.imageutils.BitmapUtil;

public class ArtDecoder extends DefaultDecoder {
    public ArtDecoder(BitmapPool bitmapPool, int maxNumThreads, Pools.SynchronizedPool decodeBuffers) {
        super(bitmapPool, maxNumThreads, decodeBuffers);
    }

    public int getBitmapSize(int width, int height, BitmapFactory.Options options) {
        return BitmapUtil.getSizeInByteForBitmap(width, height, options.inPreferredConfig);
    }
}
