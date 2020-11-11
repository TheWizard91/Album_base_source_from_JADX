package com.facebook.imagepipeline.bitmaps;

import android.graphics.Bitmap;
import android.graphics.Rect;
import com.facebook.common.logging.FLog;
import com.facebook.common.memory.PooledByteBuffer;
import com.facebook.common.references.CloseableReference;
import com.facebook.imageformat.DefaultImageFormats;
import com.facebook.imagepipeline.core.CloseableReferenceFactory;
import com.facebook.imagepipeline.image.EncodedImage;
import com.facebook.imagepipeline.platform.PlatformDecoder;

public class HoneycombBitmapFactory extends PlatformBitmapFactory {
    private static final String TAG = HoneycombBitmapFactory.class.getSimpleName();
    private final CloseableReferenceFactory mCloseableReferenceFactory;
    private boolean mImmutableBitmapFallback;
    private final EmptyJpegGenerator mJpegGenerator;
    private final PlatformDecoder mPurgeableDecoder;

    public HoneycombBitmapFactory(EmptyJpegGenerator jpegGenerator, PlatformDecoder purgeableDecoder, CloseableReferenceFactory closeableReferenceFactory) {
        this.mJpegGenerator = jpegGenerator;
        this.mPurgeableDecoder = purgeableDecoder;
        this.mCloseableReferenceFactory = closeableReferenceFactory;
    }

    public CloseableReference<Bitmap> createBitmapInternal(int width, int height, Bitmap.Config bitmapConfig) {
        EncodedImage encodedImage;
        if (this.mImmutableBitmapFallback) {
            return createFallbackBitmap(width, height, bitmapConfig);
        }
        CloseableReference<PooledByteBuffer> jpgRef = this.mJpegGenerator.generate((short) width, (short) height);
        try {
            encodedImage = new EncodedImage(jpgRef);
            encodedImage.setImageFormat(DefaultImageFormats.JPEG);
            CloseableReference<Bitmap> bitmapRef = this.mPurgeableDecoder.decodeJPEGFromEncodedImage(encodedImage, bitmapConfig, (Rect) null, jpgRef.get().size());
            if (!bitmapRef.get().isMutable()) {
                CloseableReference.closeSafely((CloseableReference<?>) bitmapRef);
                this.mImmutableBitmapFallback = true;
                FLog.wtf(TAG, "Immutable bitmap returned by decoder");
                CloseableReference<Bitmap> createFallbackBitmap = createFallbackBitmap(width, height, bitmapConfig);
                EncodedImage.closeSafely(encodedImage);
                jpgRef.close();
                return createFallbackBitmap;
            }
            bitmapRef.get().setHasAlpha(true);
            bitmapRef.get().eraseColor(0);
            EncodedImage.closeSafely(encodedImage);
            jpgRef.close();
            return bitmapRef;
        } catch (Throwable th) {
            jpgRef.close();
            throw th;
        }
    }

    private CloseableReference<Bitmap> createFallbackBitmap(int width, int height, Bitmap.Config bitmapConfig) {
        return this.mCloseableReferenceFactory.create(Bitmap.createBitmap(width, height, bitmapConfig), SimpleBitmapReleaser.getInstance());
    }
}
