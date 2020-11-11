package com.facebook.imagepipeline.producers;

import com.facebook.cache.common.CacheKey;
import com.facebook.common.internal.ImmutableMap;
import com.facebook.common.references.CloseableReference;
import com.facebook.imagepipeline.cache.CacheKeyFactory;
import com.facebook.imagepipeline.cache.MemoryCache;
import com.facebook.imagepipeline.image.CloseableImage;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.Postprocessor;
import com.facebook.imagepipeline.request.RepeatedPostprocessor;
import java.util.Map;

public class PostprocessedBitmapMemoryCacheProducer implements Producer<CloseableReference<CloseableImage>> {
    public static final String PRODUCER_NAME = "PostprocessedBitmapMemoryCacheProducer";
    static final String VALUE_FOUND = "cached_value_found";
    private final CacheKeyFactory mCacheKeyFactory;
    private final Producer<CloseableReference<CloseableImage>> mInputProducer;
    private final MemoryCache<CacheKey, CloseableImage> mMemoryCache;

    public PostprocessedBitmapMemoryCacheProducer(MemoryCache<CacheKey, CloseableImage> memoryCache, CacheKeyFactory cacheKeyFactory, Producer<CloseableReference<CloseableImage>> inputProducer) {
        this.mMemoryCache = memoryCache;
        this.mCacheKeyFactory = cacheKeyFactory;
        this.mInputProducer = inputProducer;
    }

    public void produceResults(Consumer<CloseableReference<CloseableImage>> consumer, ProducerContext producerContext) {
        Consumer<CloseableReference<CloseableImage>> consumer2 = consumer;
        ProducerContext producerContext2 = producerContext;
        ProducerListener2 listener = producerContext.getProducerListener();
        ImageRequest imageRequest = producerContext.getImageRequest();
        Object callerContext = producerContext.getCallerContext();
        Postprocessor postprocessor = imageRequest.getPostprocessor();
        if (postprocessor == null || postprocessor.getPostprocessorCacheKey() == null) {
            this.mInputProducer.produceResults(consumer2, producerContext2);
            return;
        }
        listener.onProducerStart(producerContext2, getProducerName());
        CacheKey cacheKey = this.mCacheKeyFactory.getPostprocessedBitmapCacheKey(imageRequest, callerContext);
        CloseableReference<CloseableImage> cachedReference = this.mMemoryCache.get(cacheKey);
        Map map = null;
        if (cachedReference != null) {
            String producerName = getProducerName();
            if (listener.requiresExtraMap(producerContext2, getProducerName())) {
                map = ImmutableMap.m32of("cached_value_found", "true");
            }
            listener.onProducerFinishWithSuccess(producerContext2, producerName, map);
            listener.onUltimateProducerReached(producerContext2, PRODUCER_NAME, true);
            consumer2.onProgressUpdate(1.0f);
            consumer2.onNewResult(cachedReference, 1);
            cachedReference.close();
            return;
        }
        boolean isRepeatedProcessor = postprocessor instanceof RepeatedPostprocessor;
        Consumer<CloseableReference<CloseableImage>> consumer3 = consumer;
        CacheKey cacheKey2 = cacheKey;
        boolean z = isRepeatedProcessor;
        boolean z2 = isRepeatedProcessor;
        Consumer<CloseableReference<CloseableImage>> cachedConsumer = new CachedPostprocessorConsumer(consumer3, cacheKey2, z, this.mMemoryCache, producerContext.getImageRequest().isMemoryCacheEnabled());
        String producerName2 = getProducerName();
        if (listener.requiresExtraMap(producerContext2, getProducerName())) {
            map = ImmutableMap.m32of("cached_value_found", "false");
        }
        listener.onProducerFinishWithSuccess(producerContext2, producerName2, map);
        this.mInputProducer.produceResults(cachedConsumer, producerContext2);
    }

    public static class CachedPostprocessorConsumer extends DelegatingConsumer<CloseableReference<CloseableImage>, CloseableReference<CloseableImage>> {
        private final CacheKey mCacheKey;
        private final boolean mIsMemoryCachedEnabled;
        private final boolean mIsRepeatedProcessor;
        private final MemoryCache<CacheKey, CloseableImage> mMemoryCache;

        public CachedPostprocessorConsumer(Consumer<CloseableReference<CloseableImage>> consumer, CacheKey cacheKey, boolean isRepeatedProcessor, MemoryCache<CacheKey, CloseableImage> memoryCache, boolean isMemoryCachedEnabled) {
            super(consumer);
            this.mCacheKey = cacheKey;
            this.mIsRepeatedProcessor = isRepeatedProcessor;
            this.mMemoryCache = memoryCache;
            this.mIsMemoryCachedEnabled = isMemoryCachedEnabled;
        }

        /* access modifiers changed from: protected */
        public void onNewResultImpl(CloseableReference<CloseableImage> newResult, int status) {
            if (newResult == null) {
                if (isLast(status)) {
                    getConsumer().onNewResult(null, status);
                }
            } else if (!isNotLast(status) || this.mIsRepeatedProcessor) {
                CloseableReference<CloseableImage> newCachedResult = null;
                if (this.mIsMemoryCachedEnabled) {
                    newCachedResult = this.mMemoryCache.cache(this.mCacheKey, newResult);
                }
                try {
                    getConsumer().onProgressUpdate(1.0f);
                    getConsumer().onNewResult(newCachedResult != null ? newCachedResult : newResult, status);
                } finally {
                    CloseableReference.closeSafely((CloseableReference<?>) newCachedResult);
                }
            }
        }
    }

    /* access modifiers changed from: protected */
    public String getProducerName() {
        return PRODUCER_NAME;
    }
}
