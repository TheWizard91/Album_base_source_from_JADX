package com.facebook.imagepipeline.image;

import android.graphics.Bitmap;
import com.facebook.common.internal.Preconditions;
import com.facebook.common.references.CloseableReference;
import com.facebook.common.references.HasBitmap;
import com.facebook.common.references.ResourceReleaser;
import com.facebook.imageutils.BitmapUtil;
import javax.annotation.Nullable;

public class CloseableStaticBitmap extends CloseableBitmap implements HasBitmap {
    private volatile Bitmap mBitmap;
    private CloseableReference<Bitmap> mBitmapReference;
    private final int mExifOrientation;
    private final QualityInfo mQualityInfo;
    private final int mRotationAngle;

    public CloseableStaticBitmap(Bitmap bitmap, ResourceReleaser<Bitmap> resourceReleaser, QualityInfo qualityInfo, int rotationAngle) {
        this(bitmap, resourceReleaser, qualityInfo, rotationAngle, 0);
    }

    /* JADX WARNING: type inference failed for: r4v0, types: [java.lang.Object, com.facebook.common.references.ResourceReleaser<android.graphics.Bitmap>] */
    /* JADX WARNING: Unknown variable types count: 1 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public CloseableStaticBitmap(android.graphics.Bitmap r3, com.facebook.common.references.ResourceReleaser<android.graphics.Bitmap> r4, com.facebook.imagepipeline.image.QualityInfo r5, int r6, int r7) {
        /*
            r2 = this;
            r2.<init>()
            java.lang.Object r0 = com.facebook.common.internal.Preconditions.checkNotNull(r3)
            android.graphics.Bitmap r0 = (android.graphics.Bitmap) r0
            r2.mBitmap = r0
            android.graphics.Bitmap r0 = r2.mBitmap
            java.lang.Object r1 = com.facebook.common.internal.Preconditions.checkNotNull(r4)
            com.facebook.common.references.ResourceReleaser r1 = (com.facebook.common.references.ResourceReleaser) r1
            com.facebook.common.references.CloseableReference r0 = com.facebook.common.references.CloseableReference.m126of(r0, r1)
            r2.mBitmapReference = r0
            r2.mQualityInfo = r5
            r2.mRotationAngle = r6
            r2.mExifOrientation = r7
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.facebook.imagepipeline.image.CloseableStaticBitmap.<init>(android.graphics.Bitmap, com.facebook.common.references.ResourceReleaser, com.facebook.imagepipeline.image.QualityInfo, int, int):void");
    }

    public CloseableStaticBitmap(CloseableReference<Bitmap> bitmapReference, QualityInfo qualityInfo, int rotationAngle) {
        this(bitmapReference, qualityInfo, rotationAngle, 0);
    }

    /* JADX WARNING: type inference failed for: r2v0, types: [com.facebook.common.references.CloseableReference, com.facebook.common.references.CloseableReference<android.graphics.Bitmap>] */
    /* JADX WARNING: Unknown variable types count: 1 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public CloseableStaticBitmap(com.facebook.common.references.CloseableReference<android.graphics.Bitmap> r2, com.facebook.imagepipeline.image.QualityInfo r3, int r4, int r5) {
        /*
            r1 = this;
            r1.<init>()
            com.facebook.common.references.CloseableReference r0 = r2.cloneOrNull()
            java.lang.Object r0 = com.facebook.common.internal.Preconditions.checkNotNull(r0)
            com.facebook.common.references.CloseableReference r0 = (com.facebook.common.references.CloseableReference) r0
            r1.mBitmapReference = r0
            java.lang.Object r0 = r0.get()
            android.graphics.Bitmap r0 = (android.graphics.Bitmap) r0
            r1.mBitmap = r0
            r1.mQualityInfo = r3
            r1.mRotationAngle = r4
            r1.mExifOrientation = r5
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.facebook.imagepipeline.image.CloseableStaticBitmap.<init>(com.facebook.common.references.CloseableReference, com.facebook.imagepipeline.image.QualityInfo, int, int):void");
    }

    public void close() {
        CloseableReference<Bitmap> reference = detachBitmapReference();
        if (reference != null) {
            reference.close();
        }
    }

    private synchronized CloseableReference<Bitmap> detachBitmapReference() {
        CloseableReference<Bitmap> reference;
        reference = this.mBitmapReference;
        this.mBitmapReference = null;
        this.mBitmap = null;
        return reference;
    }

    public synchronized CloseableReference<Bitmap> convertToBitmapReference() {
        Preconditions.checkNotNull(this.mBitmapReference, "Cannot convert a closed static bitmap");
        return detachBitmapReference();
    }

    @Nullable
    public synchronized CloseableReference<Bitmap> cloneUnderlyingBitmapReference() {
        return CloseableReference.cloneOrNull(this.mBitmapReference);
    }

    public synchronized boolean isClosed() {
        return this.mBitmapReference == null;
    }

    public Bitmap getUnderlyingBitmap() {
        return this.mBitmap;
    }

    public int getSizeInBytes() {
        return BitmapUtil.getSizeInBytes(this.mBitmap);
    }

    public int getWidth() {
        int i;
        if (this.mRotationAngle % 180 != 0 || (i = this.mExifOrientation) == 5 || i == 7) {
            return getBitmapHeight(this.mBitmap);
        }
        return getBitmapWidth(this.mBitmap);
    }

    public int getHeight() {
        int i;
        if (this.mRotationAngle % 180 != 0 || (i = this.mExifOrientation) == 5 || i == 7) {
            return getBitmapWidth(this.mBitmap);
        }
        return getBitmapHeight(this.mBitmap);
    }

    private static int getBitmapWidth(@Nullable Bitmap bitmap) {
        if (bitmap == null) {
            return 0;
        }
        return bitmap.getWidth();
    }

    private static int getBitmapHeight(@Nullable Bitmap bitmap) {
        if (bitmap == null) {
            return 0;
        }
        return bitmap.getHeight();
    }

    public int getRotationAngle() {
        return this.mRotationAngle;
    }

    public int getExifOrientation() {
        return this.mExifOrientation;
    }

    public QualityInfo getQualityInfo() {
        return this.mQualityInfo;
    }
}
