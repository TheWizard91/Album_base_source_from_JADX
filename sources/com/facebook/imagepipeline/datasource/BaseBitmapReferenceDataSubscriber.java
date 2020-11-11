package com.facebook.imagepipeline.datasource;

import android.graphics.Bitmap;
import com.facebook.common.references.CloseableReference;
import com.facebook.datasource.BaseDataSubscriber;
import com.facebook.datasource.DataSource;
import com.facebook.imagepipeline.image.CloseableImage;
import com.facebook.imagepipeline.image.CloseableStaticBitmap;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public abstract class BaseBitmapReferenceDataSubscriber extends BaseDataSubscriber<CloseableReference<CloseableImage>> {
    /* access modifiers changed from: protected */
    public abstract void onNewResultImpl(@Nullable CloseableReference<Bitmap> closeableReference);

    public void onNewResultImpl(@Nonnull DataSource<CloseableReference<CloseableImage>> dataSource) {
        if (dataSource.isFinished()) {
            CloseableReference<CloseableImage> closeableImageRef = dataSource.getResult();
            CloseableReference<Bitmap> bitmapReference = null;
            if (closeableImageRef != null && (closeableImageRef.get() instanceof CloseableStaticBitmap)) {
                bitmapReference = ((CloseableStaticBitmap) closeableImageRef.get()).cloneUnderlyingBitmapReference();
            }
            try {
                onNewResultImpl(bitmapReference);
            } finally {
                CloseableReference.closeSafely((CloseableReference<?>) bitmapReference);
                CloseableReference.closeSafely((CloseableReference<?>) closeableImageRef);
            }
        }
    }
}
