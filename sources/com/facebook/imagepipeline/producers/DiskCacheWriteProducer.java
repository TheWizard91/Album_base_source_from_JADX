package com.facebook.imagepipeline.producers;

import com.facebook.cache.common.CacheKey;
import com.facebook.imageformat.ImageFormat;
import com.facebook.imagepipeline.cache.BufferedDiskCache;
import com.facebook.imagepipeline.cache.CacheKeyFactory;
import com.facebook.imagepipeline.image.EncodedImage;
import com.facebook.imagepipeline.request.ImageRequest;
import java.util.Map;

public class DiskCacheWriteProducer implements Producer<EncodedImage> {
    static final String PRODUCER_NAME = "DiskCacheWriteProducer";
    private final CacheKeyFactory mCacheKeyFactory;
    private final BufferedDiskCache mDefaultBufferedDiskCache;
    private final Producer<EncodedImage> mInputProducer;
    private final BufferedDiskCache mSmallImageBufferedDiskCache;

    public DiskCacheWriteProducer(BufferedDiskCache defaultBufferedDiskCache, BufferedDiskCache smallImageBufferedDiskCache, CacheKeyFactory cacheKeyFactory, Producer<EncodedImage> inputProducer) {
        this.mDefaultBufferedDiskCache = defaultBufferedDiskCache;
        this.mSmallImageBufferedDiskCache = smallImageBufferedDiskCache;
        this.mCacheKeyFactory = cacheKeyFactory;
        this.mInputProducer = inputProducer;
    }

    public void produceResults(Consumer<EncodedImage> consumer, ProducerContext producerContext) {
        maybeStartInputProducer(consumer, producerContext);
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v4, resolved type: com.facebook.imagepipeline.producers.DiskCacheWriteProducer$DiskCacheWriteConsumer} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v5, resolved type: com.facebook.imagepipeline.producers.DiskCacheWriteProducer$DiskCacheWriteConsumer} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v4, resolved type: com.facebook.imagepipeline.producers.DiskCacheWriteProducer$DiskCacheWriteConsumer} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void maybeStartInputProducer(com.facebook.imagepipeline.producers.Consumer<com.facebook.imagepipeline.image.EncodedImage> r9, com.facebook.imagepipeline.producers.ProducerContext r10) {
        /*
            r8 = this;
            com.facebook.imagepipeline.request.ImageRequest$RequestLevel r0 = r10.getLowestPermittedRequestLevel()
            int r0 = r0.getValue()
            com.facebook.imagepipeline.request.ImageRequest$RequestLevel r1 = com.facebook.imagepipeline.request.ImageRequest.RequestLevel.DISK_CACHE
            int r1 = r1.getValue()
            if (r0 < r1) goto L_0x0016
            r0 = 0
            r1 = 1
            r9.onNewResult(r0, r1)
            goto L_0x0036
        L_0x0016:
            com.facebook.imagepipeline.request.ImageRequest r0 = r10.getImageRequest()
            boolean r0 = r0.isDiskCacheEnabled()
            if (r0 == 0) goto L_0x0030
            com.facebook.imagepipeline.producers.DiskCacheWriteProducer$DiskCacheWriteConsumer r0 = new com.facebook.imagepipeline.producers.DiskCacheWriteProducer$DiskCacheWriteConsumer
            com.facebook.imagepipeline.cache.BufferedDiskCache r4 = r8.mDefaultBufferedDiskCache
            com.facebook.imagepipeline.cache.BufferedDiskCache r5 = r8.mSmallImageBufferedDiskCache
            com.facebook.imagepipeline.cache.CacheKeyFactory r6 = r8.mCacheKeyFactory
            r7 = 0
            r1 = r0
            r2 = r9
            r3 = r10
            r1.<init>(r2, r3, r4, r5, r6)
            goto L_0x0031
        L_0x0030:
            r0 = r9
        L_0x0031:
            com.facebook.imagepipeline.producers.Producer<com.facebook.imagepipeline.image.EncodedImage> r1 = r8.mInputProducer
            r1.produceResults(r0, r10)
        L_0x0036:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.facebook.imagepipeline.producers.DiskCacheWriteProducer.maybeStartInputProducer(com.facebook.imagepipeline.producers.Consumer, com.facebook.imagepipeline.producers.ProducerContext):void");
    }

    private static class DiskCacheWriteConsumer extends DelegatingConsumer<EncodedImage, EncodedImage> {
        private final CacheKeyFactory mCacheKeyFactory;
        private final BufferedDiskCache mDefaultBufferedDiskCache;
        private final ProducerContext mProducerContext;
        private final BufferedDiskCache mSmallImageBufferedDiskCache;

        private DiskCacheWriteConsumer(Consumer<EncodedImage> consumer, ProducerContext producerContext, BufferedDiskCache defaultBufferedDiskCache, BufferedDiskCache smallImageBufferedDiskCache, CacheKeyFactory cacheKeyFactory) {
            super(consumer);
            this.mProducerContext = producerContext;
            this.mDefaultBufferedDiskCache = defaultBufferedDiskCache;
            this.mSmallImageBufferedDiskCache = smallImageBufferedDiskCache;
            this.mCacheKeyFactory = cacheKeyFactory;
        }

        public void onNewResultImpl(EncodedImage newResult, int status) {
            this.mProducerContext.getProducerListener().onProducerStart(this.mProducerContext, DiskCacheWriteProducer.PRODUCER_NAME);
            if (isNotLast(status) || newResult == null || statusHasAnyFlag(status, 10) || newResult.getImageFormat() == ImageFormat.UNKNOWN) {
                this.mProducerContext.getProducerListener().onProducerFinishWithSuccess(this.mProducerContext, DiskCacheWriteProducer.PRODUCER_NAME, (Map<String, String>) null);
                getConsumer().onNewResult(newResult, status);
                return;
            }
            ImageRequest imageRequest = this.mProducerContext.getImageRequest();
            CacheKey cacheKey = this.mCacheKeyFactory.getEncodedCacheKey(imageRequest, this.mProducerContext.getCallerContext());
            if (imageRequest.getCacheChoice() == ImageRequest.CacheChoice.SMALL) {
                this.mSmallImageBufferedDiskCache.put(cacheKey, newResult);
            } else {
                this.mDefaultBufferedDiskCache.put(cacheKey, newResult);
            }
            this.mProducerContext.getProducerListener().onProducerFinishWithSuccess(this.mProducerContext, DiskCacheWriteProducer.PRODUCER_NAME, (Map<String, String>) null);
            getConsumer().onNewResult(newResult, status);
        }
    }
}
