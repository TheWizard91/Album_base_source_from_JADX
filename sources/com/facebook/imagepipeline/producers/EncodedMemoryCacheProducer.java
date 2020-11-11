package com.facebook.imagepipeline.producers;

import com.facebook.cache.common.CacheKey;
import com.facebook.common.internal.ImmutableMap;
import com.facebook.common.memory.PooledByteBuffer;
import com.facebook.common.references.CloseableReference;
import com.facebook.imageformat.ImageFormat;
import com.facebook.imagepipeline.cache.CacheKeyFactory;
import com.facebook.imagepipeline.cache.MemoryCache;
import com.facebook.imagepipeline.image.EncodedImage;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.systrace.FrescoSystrace;
import java.util.Map;

public class EncodedMemoryCacheProducer implements Producer<EncodedImage> {
    public static final String EXTRA_CACHED_VALUE_FOUND = "cached_value_found";
    public static final String PRODUCER_NAME = "EncodedMemoryCacheProducer";
    private final CacheKeyFactory mCacheKeyFactory;
    private final Producer<EncodedImage> mInputProducer;
    private final MemoryCache<CacheKey, PooledByteBuffer> mMemoryCache;

    public EncodedMemoryCacheProducer(MemoryCache<CacheKey, PooledByteBuffer> memoryCache, CacheKeyFactory cacheKeyFactory, Producer<EncodedImage> inputProducer) {
        this.mMemoryCache = memoryCache;
        this.mCacheKeyFactory = cacheKeyFactory;
        this.mInputProducer = inputProducer;
    }

    public void produceResults(Consumer<EncodedImage> consumer, ProducerContext producerContext) {
        EncodedImage cachedEncodedImage;
        Consumer<EncodedImage> consumer2 = consumer;
        ProducerContext producerContext2 = producerContext;
        try {
            if (FrescoSystrace.isTracing()) {
                FrescoSystrace.beginSection("EncodedMemoryCacheProducer#produceResults");
            }
            ProducerListener2 listener = producerContext.getProducerListener();
            listener.onProducerStart(producerContext2, PRODUCER_NAME);
            CacheKey cacheKey = this.mCacheKeyFactory.getEncodedCacheKey(producerContext.getImageRequest(), producerContext.getCallerContext());
            CloseableReference<PooledByteBuffer> cachedReference = this.mMemoryCache.get(cacheKey);
            Map map = null;
            if (cachedReference != null) {
                try {
                    cachedEncodedImage = new EncodedImage(cachedReference);
                    if (listener.requiresExtraMap(producerContext2, PRODUCER_NAME)) {
                        map = ImmutableMap.m32of("cached_value_found", "true");
                    }
                    listener.onProducerFinishWithSuccess(producerContext2, PRODUCER_NAME, map);
                    listener.onUltimateProducerReached(producerContext2, PRODUCER_NAME, true);
                    producerContext2.setExtra(1, "memory_encoded");
                    consumer2.onProgressUpdate(1.0f);
                    consumer2.onNewResult(cachedEncodedImage, 1);
                    EncodedImage.closeSafely(cachedEncodedImage);
                    CloseableReference.closeSafely((CloseableReference<?>) cachedReference);
                } catch (Throwable th) {
                    CloseableReference.closeSafely((CloseableReference<?>) cachedReference);
                    throw th;
                }
            } else if (producerContext.getLowestPermittedRequestLevel().getValue() >= ImageRequest.RequestLevel.ENCODED_MEMORY_CACHE.getValue()) {
                listener.onProducerFinishWithSuccess(producerContext2, PRODUCER_NAME, listener.requiresExtraMap(producerContext2, PRODUCER_NAME) ? ImmutableMap.m32of("cached_value_found", "false") : null);
                listener.onUltimateProducerReached(producerContext2, PRODUCER_NAME, false);
                producerContext2.setExtra(1, "memory_encoded");
                consumer2.onNewResult(null, 1);
                CloseableReference.closeSafely((CloseableReference<?>) cachedReference);
                if (FrescoSystrace.isTracing()) {
                    FrescoSystrace.endSection();
                }
            } else {
                Object obj = "false";
                Consumer consumerOfInputProducer = new EncodedMemoryCacheConsumer(consumer, this.mMemoryCache, cacheKey, producerContext.getImageRequest().isMemoryCacheEnabled(), producerContext.getImagePipelineConfig().getExperiments().isEncodedCacheEnabled());
                listener.onProducerFinishWithSuccess(producerContext2, PRODUCER_NAME, listener.requiresExtraMap(producerContext2, PRODUCER_NAME) ? ImmutableMap.m32of("cached_value_found", obj) : null);
                this.mInputProducer.produceResults(consumerOfInputProducer, producerContext2);
                CloseableReference.closeSafely((CloseableReference<?>) cachedReference);
                if (FrescoSystrace.isTracing()) {
                    FrescoSystrace.endSection();
                }
            }
        } finally {
            if (FrescoSystrace.isTracing()) {
                FrescoSystrace.endSection();
            }
        }
    }

    private static class EncodedMemoryCacheConsumer extends DelegatingConsumer<EncodedImage, EncodedImage> {
        private final boolean mEncodedCacheEnabled;
        private final boolean mIsMemoryCacheEnabled;
        private final MemoryCache<CacheKey, PooledByteBuffer> mMemoryCache;
        private final CacheKey mRequestedCacheKey;

        public EncodedMemoryCacheConsumer(Consumer<EncodedImage> consumer, MemoryCache<CacheKey, PooledByteBuffer> memoryCache, CacheKey requestedCacheKey, boolean isMemoryCacheEnabled, boolean encodedCacheEnabled) {
            super(consumer);
            this.mMemoryCache = memoryCache;
            this.mRequestedCacheKey = requestedCacheKey;
            this.mIsMemoryCacheEnabled = isMemoryCacheEnabled;
            this.mEncodedCacheEnabled = encodedCacheEnabled;
        }

        public void onNewResultImpl(EncodedImage newResult, int status) {
            CloseableReference<PooledByteBuffer> ref;
            CloseableReference<PooledByteBuffer> cachedResult;
            EncodedImage cachedEncodedImage;
            try {
                if (FrescoSystrace.isTracing()) {
                    FrescoSystrace.beginSection("EncodedMemoryCacheProducer#onNewResultImpl");
                }
                if (!isNotLast(status) && newResult != null && !statusHasAnyFlag(status, 10)) {
                    if (newResult.getImageFormat() != ImageFormat.UNKNOWN) {
                        ref = newResult.getByteBufferRef();
                        if (ref != null) {
                            cachedResult = null;
                            if (this.mEncodedCacheEnabled && this.mIsMemoryCacheEnabled) {
                                cachedResult = this.mMemoryCache.cache(this.mRequestedCacheKey, ref);
                            }
                            CloseableReference.closeSafely((CloseableReference<?>) ref);
                            if (cachedResult != null) {
                                cachedEncodedImage = new EncodedImage(cachedResult);
                                cachedEncodedImage.copyMetaDataFrom(newResult);
                                CloseableReference.closeSafely((CloseableReference<?>) cachedResult);
                                getConsumer().onProgressUpdate(1.0f);
                                getConsumer().onNewResult(cachedEncodedImage, status);
                                EncodedImage.closeSafely(cachedEncodedImage);
                                if (FrescoSystrace.isTracing()) {
                                    FrescoSystrace.endSection();
                                    return;
                                }
                                return;
                            }
                        }
                        getConsumer().onNewResult(newResult, status);
                        if (FrescoSystrace.isTracing()) {
                            FrescoSystrace.endSection();
                            return;
                        }
                        return;
                    }
                }
                getConsumer().onNewResult(newResult, status);
                if (FrescoSystrace.isTracing()) {
                    FrescoSystrace.endSection();
                }
            } catch (Throwable th) {
                if (FrescoSystrace.isTracing()) {
                    FrescoSystrace.endSection();
                }
                throw th;
            }
        }
    }
}
