package com.facebook.imagepipeline.producers;

import android.graphics.Bitmap;
import com.facebook.common.references.CloseableReference;
import com.facebook.imagepipeline.image.CloseableImage;
import com.facebook.imagepipeline.image.CloseableStaticBitmap;

public class BitmapPrepareProducer implements Producer<CloseableReference<CloseableImage>> {
    public static final String PRODUCER_NAME = "BitmapPrepareProducer";
    private final Producer<CloseableReference<CloseableImage>> mInputProducer;
    private final int mMaxBitmapSizeBytes;
    private final int mMinBitmapSizeBytes;
    private final boolean mPreparePrefetch;

    /* JADX WARNING: type inference failed for: r2v0, types: [com.facebook.imagepipeline.producers.Producer<com.facebook.common.references.CloseableReference<com.facebook.imagepipeline.image.CloseableImage>>, java.lang.Object] */
    /* JADX WARNING: Unknown variable types count: 1 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public BitmapPrepareProducer(com.facebook.imagepipeline.producers.Producer<com.facebook.common.references.CloseableReference<com.facebook.imagepipeline.image.CloseableImage>> r2, int r3, int r4, boolean r5) {
        /*
            r1 = this;
            r1.<init>()
            if (r3 > r4) goto L_0x0007
            r0 = 1
            goto L_0x0008
        L_0x0007:
            r0 = 0
        L_0x0008:
            com.facebook.common.internal.Preconditions.checkArgument(r0)
            java.lang.Object r0 = com.facebook.common.internal.Preconditions.checkNotNull(r2)
            com.facebook.imagepipeline.producers.Producer r0 = (com.facebook.imagepipeline.producers.Producer) r0
            r1.mInputProducer = r0
            r1.mMinBitmapSizeBytes = r3
            r1.mMaxBitmapSizeBytes = r4
            r1.mPreparePrefetch = r5
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.facebook.imagepipeline.producers.BitmapPrepareProducer.<init>(com.facebook.imagepipeline.producers.Producer, int, int, boolean):void");
    }

    public void produceResults(Consumer<CloseableReference<CloseableImage>> consumer, ProducerContext producerContext) {
        if (!producerContext.isPrefetch() || this.mPreparePrefetch) {
            this.mInputProducer.produceResults(new BitmapPrepareConsumer(consumer, this.mMinBitmapSizeBytes, this.mMaxBitmapSizeBytes), producerContext);
        } else {
            this.mInputProducer.produceResults(consumer, producerContext);
        }
    }

    private static class BitmapPrepareConsumer extends DelegatingConsumer<CloseableReference<CloseableImage>, CloseableReference<CloseableImage>> {
        private final int mMaxBitmapSizeBytes;
        private final int mMinBitmapSizeBytes;

        BitmapPrepareConsumer(Consumer<CloseableReference<CloseableImage>> consumer, int minBitmapSizeBytes, int maxBitmapSizeBytes) {
            super(consumer);
            this.mMinBitmapSizeBytes = minBitmapSizeBytes;
            this.mMaxBitmapSizeBytes = maxBitmapSizeBytes;
        }

        /* access modifiers changed from: protected */
        public void onNewResultImpl(CloseableReference<CloseableImage> newResult, int status) {
            internalPrepareBitmap(newResult);
            getConsumer().onNewResult(newResult, status);
        }

        private void internalPrepareBitmap(CloseableReference<CloseableImage> newResult) {
            CloseableImage closeableImage;
            Bitmap bitmap;
            int bitmapByteCount;
            if (newResult != null && newResult.isValid() && (closeableImage = newResult.get()) != null && !closeableImage.isClosed() && (closeableImage instanceof CloseableStaticBitmap) && (bitmap = ((CloseableStaticBitmap) closeableImage).getUnderlyingBitmap()) != null && (bitmapByteCount = bitmap.getRowBytes() * bitmap.getHeight()) >= this.mMinBitmapSizeBytes && bitmapByteCount <= this.mMaxBitmapSizeBytes) {
                bitmap.prepareToDraw();
            }
        }
    }
}
