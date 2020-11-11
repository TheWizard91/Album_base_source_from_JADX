package com.facebook.imagepipeline.platform;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ColorSpace;
import android.graphics.Rect;
import android.os.Build;
import androidx.core.util.Pools;
import com.facebook.common.internal.Preconditions;
import com.facebook.common.references.CloseableReference;
import com.facebook.common.streams.LimitedInputStream;
import com.facebook.common.streams.TailAppendingInputStream;
import com.facebook.imagepipeline.image.EncodedImage;
import com.facebook.imagepipeline.memory.BitmapPool;
import java.io.InputStream;
import java.nio.ByteBuffer;
import javax.annotation.Nullable;

public abstract class DefaultDecoder implements PlatformDecoder {
    private static final int DECODE_BUFFER_SIZE = 16384;
    private static final byte[] EOI_TAIL = {-1, -39};
    private static final Class<?> TAG = DefaultDecoder.class;
    private final BitmapPool mBitmapPool;
    final Pools.SynchronizedPool<ByteBuffer> mDecodeBuffers;
    @Nullable
    private final PreverificationHelper mPreverificationHelper;

    public abstract int getBitmapSize(int i, int i2, BitmapFactory.Options options);

    public DefaultDecoder(BitmapPool bitmapPool, int maxNumThreads, Pools.SynchronizedPool decodeBuffers) {
        this.mPreverificationHelper = Build.VERSION.SDK_INT >= 26 ? new PreverificationHelper() : null;
        this.mBitmapPool = bitmapPool;
        this.mDecodeBuffers = decodeBuffers;
        for (int i = 0; i < maxNumThreads; i++) {
            this.mDecodeBuffers.release(ByteBuffer.allocate(16384));
        }
    }

    public CloseableReference<Bitmap> decodeFromEncodedImage(EncodedImage encodedImage, Bitmap.Config bitmapConfig, @Nullable Rect regionToDecode) {
        return decodeFromEncodedImageWithColorSpace(encodedImage, bitmapConfig, regionToDecode, (ColorSpace) null);
    }

    public CloseableReference<Bitmap> decodeJPEGFromEncodedImage(EncodedImage encodedImage, Bitmap.Config bitmapConfig, @Nullable Rect regionToDecode, int length) {
        return decodeJPEGFromEncodedImageWithColorSpace(encodedImage, bitmapConfig, regionToDecode, length, (ColorSpace) null);
    }

    public CloseableReference<Bitmap> decodeFromEncodedImageWithColorSpace(EncodedImage encodedImage, Bitmap.Config bitmapConfig, @Nullable Rect regionToDecode, @Nullable ColorSpace colorSpace) {
        BitmapFactory.Options options = getDecodeOptionsForStream(encodedImage, bitmapConfig);
        boolean retryOnFail = options.inPreferredConfig != Bitmap.Config.ARGB_8888;
        try {
            return decodeFromStream(encodedImage.getInputStream(), options, regionToDecode, colorSpace);
        } catch (RuntimeException re) {
            if (retryOnFail) {
                return decodeFromEncodedImageWithColorSpace(encodedImage, Bitmap.Config.ARGB_8888, regionToDecode, colorSpace);
            }
            throw re;
        }
    }

    public CloseableReference<Bitmap> decodeJPEGFromEncodedImageWithColorSpace(EncodedImage encodedImage, Bitmap.Config bitmapConfig, @Nullable Rect regionToDecode, int length, @Nullable ColorSpace colorSpace) {
        InputStream jpegDataStream;
        int i = length;
        boolean isJpegComplete = encodedImage.isCompleteAt(i);
        BitmapFactory.Options options = getDecodeOptionsForStream(encodedImage, bitmapConfig);
        InputStream jpegDataStream2 = encodedImage.getInputStream();
        Preconditions.checkNotNull(jpegDataStream2);
        if (encodedImage.getSize() > i) {
            jpegDataStream2 = new LimitedInputStream(jpegDataStream2, i);
        }
        if (!isJpegComplete) {
            jpegDataStream = new TailAppendingInputStream(jpegDataStream2, EOI_TAIL);
        } else {
            jpegDataStream = jpegDataStream2;
        }
        boolean retryOnFail = options.inPreferredConfig != Bitmap.Config.ARGB_8888;
        try {
            return decodeFromStream(jpegDataStream, options, regionToDecode, colorSpace);
        } catch (RuntimeException e) {
            RuntimeException re = e;
            if (retryOnFail) {
                return decodeJPEGFromEncodedImageWithColorSpace(encodedImage, Bitmap.Config.ARGB_8888, regionToDecode, length, colorSpace);
            }
            throw re;
        }
    }

    /* access modifiers changed from: protected */
    public CloseableReference<Bitmap> decodeStaticImageFromStream(InputStream inputStream, BitmapFactory.Options options, @Nullable Rect regionToDecode) {
        return decodeFromStream(inputStream, options, regionToDecode, (ColorSpace) null);
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r12v5, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v31, resolved type: android.graphics.Bitmap} */
    /* JADX WARNING: Code restructure failed: missing block: B:42:0x00a7, code lost:
        if (r13 != null) goto L_0x00a9;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:44:?, code lost:
        r13.recycle();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:49:0x00bb, code lost:
        if (r13 == null) goto L_0x00c5;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:64:0x00ed, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:84:?, code lost:
        throw r10;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:85:0x011e, code lost:
        r1.mDecodeBuffers.release(r12);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:86:0x0123, code lost:
        throw r0;
     */
    /* JADX WARNING: Exception block dominator not found, dom blocks: [B:67:0x00f2, B:74:0x0102] */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private com.facebook.common.references.CloseableReference<android.graphics.Bitmap> decodeFromStream(java.io.InputStream r17, android.graphics.BitmapFactory.Options r18, @javax.annotation.Nullable android.graphics.Rect r19, @javax.annotation.Nullable android.graphics.ColorSpace r20) {
        /*
            r16 = this;
            r1 = r16
            r2 = r17
            r3 = r18
            r4 = r19
            com.facebook.common.internal.Preconditions.checkNotNull(r17)
            int r0 = r3.outWidth
            int r5 = r3.outHeight
            if (r4 == 0) goto L_0x0024
            int r6 = r19.width()
            int r7 = r3.inSampleSize
            int r0 = r6 / r7
            int r6 = r19.height()
            int r7 = r3.inSampleSize
            int r5 = r6 / r7
            r6 = r5
            r5 = r0
            goto L_0x0026
        L_0x0024:
            r6 = r5
            r5 = r0
        L_0x0026:
            r0 = 0
            r7 = 0
            int r8 = android.os.Build.VERSION.SDK_INT
            r9 = 26
            r10 = 1
            r11 = 0
            if (r8 < r9) goto L_0x0040
            com.facebook.imagepipeline.platform.PreverificationHelper r8 = r1.mPreverificationHelper
            if (r8 == 0) goto L_0x003e
            android.graphics.Bitmap$Config r12 = r3.inPreferredConfig
            boolean r8 = r8.shouldUseHardwareBitmapConfig(r12)
            if (r8 == 0) goto L_0x003e
            r8 = r10
            goto L_0x003f
        L_0x003e:
            r8 = r11
        L_0x003f:
            r7 = r8
        L_0x0040:
            if (r4 != 0) goto L_0x0048
            if (r7 == 0) goto L_0x0048
            r3.inMutable = r11
            r8 = r0
            goto L_0x0060
        L_0x0048:
            if (r4 == 0) goto L_0x0050
            if (r7 == 0) goto L_0x0050
            android.graphics.Bitmap$Config r8 = android.graphics.Bitmap.Config.ARGB_8888
            r3.inPreferredConfig = r8
        L_0x0050:
            int r8 = r1.getBitmapSize(r5, r6, r3)
            com.facebook.imagepipeline.memory.BitmapPool r12 = r1.mBitmapPool
            java.lang.Object r12 = r12.get(r8)
            r0 = r12
            android.graphics.Bitmap r0 = (android.graphics.Bitmap) r0
            if (r0 == 0) goto L_0x0124
            r8 = r0
        L_0x0060:
            r3.inBitmap = r8
            int r0 = android.os.Build.VERSION.SDK_INT
            if (r0 < r9) goto L_0x0073
            if (r20 != 0) goto L_0x006f
            android.graphics.ColorSpace$Named r0 = android.graphics.ColorSpace.Named.SRGB
            android.graphics.ColorSpace r0 = android.graphics.ColorSpace.get(r0)
            goto L_0x0071
        L_0x006f:
            r0 = r20
        L_0x0071:
            r3.inPreferredColorSpace = r0
        L_0x0073:
            r9 = 0
            androidx.core.util.Pools$SynchronizedPool<java.nio.ByteBuffer> r0 = r1.mDecodeBuffers
            java.lang.Object r0 = r0.acquire()
            java.nio.ByteBuffer r0 = (java.nio.ByteBuffer) r0
            if (r0 != 0) goto L_0x0086
            r12 = 16384(0x4000, float:2.2959E-41)
            java.nio.ByteBuffer r0 = java.nio.ByteBuffer.allocate(r12)
            r12 = r0
            goto L_0x0087
        L_0x0086:
            r12 = r0
        L_0x0087:
            byte[] r0 = r12.array()     // Catch:{ IllegalArgumentException -> 0x00f9, RuntimeException -> 0x00ef }
            r3.inTempStorage = r0     // Catch:{ IllegalArgumentException -> 0x00f9, RuntimeException -> 0x00ef }
            int r0 = android.os.Build.VERSION.SDK_INT     // Catch:{ IllegalArgumentException -> 0x00f9, RuntimeException -> 0x00ef }
            r13 = 19
            if (r0 < r13) goto L_0x00c5
            if (r4 == 0) goto L_0x00c5
            if (r8 == 0) goto L_0x00c5
            r13 = 0
            android.graphics.Bitmap$Config r0 = r3.inPreferredConfig     // Catch:{ IOException -> 0x00af }
            r8.reconfigure(r5, r6, r0)     // Catch:{ IOException -> 0x00af }
            android.graphics.BitmapRegionDecoder r0 = android.graphics.BitmapRegionDecoder.newInstance(r2, r10)     // Catch:{ IOException -> 0x00af }
            r13 = r0
            android.graphics.Bitmap r0 = r13.decodeRegion(r4, r3)     // Catch:{ IOException -> 0x00af }
            r9 = r0
            if (r13 == 0) goto L_0x00c5
        L_0x00a9:
            r13.recycle()     // Catch:{ IllegalArgumentException -> 0x00f9, RuntimeException -> 0x00ef }
            goto L_0x00c5
        L_0x00ad:
            r0 = move-exception
            goto L_0x00be
        L_0x00af:
            r0 = move-exception
            java.lang.Class<?> r14 = TAG     // Catch:{ all -> 0x00ad }
            java.lang.String r15 = "Could not decode region %s, decoding full bitmap instead."
            java.lang.Object[] r10 = new java.lang.Object[r10]     // Catch:{ all -> 0x00ad }
            r10[r11] = r4     // Catch:{ all -> 0x00ad }
            com.facebook.common.logging.FLog.m58e((java.lang.Class<?>) r14, (java.lang.String) r15, (java.lang.Object[]) r10)     // Catch:{ all -> 0x00ad }
            if (r13 == 0) goto L_0x00c5
            goto L_0x00a9
        L_0x00be:
            if (r13 == 0) goto L_0x00c3
            r13.recycle()     // Catch:{ IllegalArgumentException -> 0x00f9, RuntimeException -> 0x00ef }
        L_0x00c3:
            throw r0     // Catch:{ IllegalArgumentException -> 0x00f9, RuntimeException -> 0x00ef }
        L_0x00c5:
            if (r9 != 0) goto L_0x00cd
            r0 = 0
            android.graphics.Bitmap r0 = android.graphics.BitmapFactory.decodeStream(r2, r0, r3)     // Catch:{ IllegalArgumentException -> 0x00f9, RuntimeException -> 0x00ef }
            r9 = r0
        L_0x00cd:
            androidx.core.util.Pools$SynchronizedPool<java.nio.ByteBuffer> r0 = r1.mDecodeBuffers
            r0.release(r12)
            if (r8 == 0) goto L_0x00e6
            if (r8 != r9) goto L_0x00d8
            goto L_0x00e6
        L_0x00d8:
            com.facebook.imagepipeline.memory.BitmapPool r0 = r1.mBitmapPool
            r0.release(r8)
            r9.recycle()
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
            r0.<init>()
            throw r0
        L_0x00e6:
            com.facebook.imagepipeline.memory.BitmapPool r0 = r1.mBitmapPool
            com.facebook.common.references.CloseableReference r0 = com.facebook.common.references.CloseableReference.m126of(r9, r0)
            return r0
        L_0x00ed:
            r0 = move-exception
            goto L_0x011e
        L_0x00ef:
            r0 = move-exception
            if (r8 == 0) goto L_0x00f7
            com.facebook.imagepipeline.memory.BitmapPool r10 = r1.mBitmapPool     // Catch:{ all -> 0x00ed }
            r10.release(r8)     // Catch:{ all -> 0x00ed }
        L_0x00f7:
            throw r0     // Catch:{ all -> 0x00ed }
        L_0x00f9:
            r0 = move-exception
            r10 = r0
            if (r8 == 0) goto L_0x0102
            com.facebook.imagepipeline.memory.BitmapPool r0 = r1.mBitmapPool     // Catch:{ all -> 0x00ed }
            r0.release(r8)     // Catch:{ all -> 0x00ed }
        L_0x0102:
            r17.reset()     // Catch:{ IOException -> 0x011b }
            android.graphics.Bitmap r0 = android.graphics.BitmapFactory.decodeStream(r17)     // Catch:{ IOException -> 0x011b }
            if (r0 == 0) goto L_0x0119
            com.facebook.imagepipeline.bitmaps.SimpleBitmapReleaser r11 = com.facebook.imagepipeline.bitmaps.SimpleBitmapReleaser.getInstance()     // Catch:{ IOException -> 0x011b }
            com.facebook.common.references.CloseableReference r11 = com.facebook.common.references.CloseableReference.m126of(r0, r11)     // Catch:{ IOException -> 0x011b }
            androidx.core.util.Pools$SynchronizedPool<java.nio.ByteBuffer> r13 = r1.mDecodeBuffers
            r13.release(r12)
            return r11
        L_0x0119:
            throw r10     // Catch:{ IOException -> 0x011b }
        L_0x011b:
            r0 = move-exception
            throw r10     // Catch:{ all -> 0x00ed }
        L_0x011e:
            androidx.core.util.Pools$SynchronizedPool<java.nio.ByteBuffer> r10 = r1.mDecodeBuffers
            r10.release(r12)
            throw r0
        L_0x0124:
            java.lang.NullPointerException r9 = new java.lang.NullPointerException
            java.lang.String r10 = "BitmapPool.get returned null"
            r9.<init>(r10)
            throw r9
        */
        throw new UnsupportedOperationException("Method not decompiled: com.facebook.imagepipeline.platform.DefaultDecoder.decodeFromStream(java.io.InputStream, android.graphics.BitmapFactory$Options, android.graphics.Rect, android.graphics.ColorSpace):com.facebook.common.references.CloseableReference");
    }

    private static BitmapFactory.Options getDecodeOptionsForStream(EncodedImage encodedImage, Bitmap.Config bitmapConfig) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = encodedImage.getSampleSize();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(encodedImage.getInputStream(), (Rect) null, options);
        if (options.outWidth == -1 || options.outHeight == -1) {
            throw new IllegalArgumentException();
        }
        options.inJustDecodeBounds = false;
        options.inDither = true;
        options.inPreferredConfig = bitmapConfig;
        options.inMutable = true;
        return options;
    }
}
