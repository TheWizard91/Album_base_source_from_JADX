package com.facebook.imagepipeline.producers;

import com.facebook.cache.common.CacheKey;
import com.facebook.common.internal.ImmutableMap;
import com.facebook.common.references.CloseableReference;
import com.facebook.imagepipeline.cache.CacheKeyFactory;
import com.facebook.imagepipeline.cache.MemoryCache;
import com.facebook.imagepipeline.image.CloseableImage;
import com.facebook.imagepipeline.image.QualityInfo;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.systrace.FrescoSystrace;
import java.util.Map;

public class BitmapMemoryCacheProducer implements Producer<CloseableReference<CloseableImage>> {
    public static final String EXTRA_CACHED_VALUE_FOUND = "cached_value_found";
    public static final String PRODUCER_NAME = "BitmapMemoryCacheProducer";
    private final CacheKeyFactory mCacheKeyFactory;
    private final Producer<CloseableReference<CloseableImage>> mInputProducer;
    /* access modifiers changed from: private */
    public final MemoryCache<CacheKey, CloseableImage> mMemoryCache;

    public BitmapMemoryCacheProducer(MemoryCache<CacheKey, CloseableImage> memoryCache, CacheKeyFactory cacheKeyFactory, Producer<CloseableReference<CloseableImage>> inputProducer) {
        this.mMemoryCache = memoryCache;
        this.mCacheKeyFactory = cacheKeyFactory;
        this.mInputProducer = inputProducer;
    }

    public void produceResults(Consumer<CloseableReference<CloseableImage>> consumer, ProducerContext producerContext) {
        boolean isTracing;
        try {
            if (FrescoSystrace.isTracing()) {
                FrescoSystrace.beginSection("BitmapMemoryCacheProducer#produceResults");
            }
            ProducerListener2 listener = producerContext.getProducerListener();
            listener.onProducerStart(producerContext, getProducerName());
            CacheKey cacheKey = this.mCacheKeyFactory.getBitmapCacheKey(producerContext.getImageRequest(), producerContext.getCallerContext());
            CloseableReference<CloseableImage> cachedReference = this.mMemoryCache.get(cacheKey);
            Map map = null;
            if (cachedReference != null) {
                boolean isFinal = cachedReference.get().getQualityInfo().isOfFullQuality();
                if (isFinal) {
                    listener.onProducerFinishWithSuccess(producerContext, getProducerName(), listener.requiresExtraMap(producerContext, getProducerName()) ? ImmutableMap.m32of("cached_value_found", "true") : null);
                    listener.onUltimateProducerReached(producerContext, getProducerName(), true);
                    producerContext.setExtra(1, "memory_bitmap");
                    consumer.onProgressUpdate(1.0f);
                }
                consumer.onNewResult(cachedReference, BaseConsumer.simpleStatusForIsLast(isFinal));
                cachedReference.close();
                if (isFinal) {
                    if (!isTracing) {
                        return;
                    }
                    return;
                }
            }
            if (producerContext.getLowestPermittedRequestLevel().getValue() >= ImageRequest.RequestLevel.BITMAP_MEMORY_CACHE.getValue()) {
                listener.onProducerFinishWithSuccess(producerContext, getProducerName(), listener.requiresExtraMap(producerContext, getProducerName()) ? ImmutableMap.m32of("cached_value_found", "false") : null);
                listener.onUltimateProducerReached(producerContext, getProducerName(), false);
                producerContext.setExtra(1, "memory_bitmap");
                consumer.onNewResult(null, 1);
                if (FrescoSystrace.isTracing()) {
                    FrescoSystrace.endSection();
                    return;
                }
                return;
            }
            Consumer<CloseableReference<CloseableImage>> wrappedConsumer = wrapConsumer(consumer, cacheKey, producerContext.getImageRequest().isMemoryCacheEnabled());
            String producerName = getProducerName();
            if (listener.requiresExtraMap(producerContext, getProducerName())) {
                map = ImmutableMap.m32of("cached_value_found", "false");
            }
            listener.onProducerFinishWithSuccess(producerContext, producerName, map);
            if (FrescoSystrace.isTracing()) {
                FrescoSystrace.beginSection("mInputProducer.produceResult");
            }
            this.mInputProducer.produceResults(wrappedConsumer, producerContext);
            if (FrescoSystrace.isTracing()) {
                FrescoSystrace.endSection();
            }
            if (FrescoSystrace.isTracing()) {
                FrescoSystrace.endSection();
            }
        } finally {
            if (FrescoSystrace.isTracing()) {
                FrescoSystrace.endSection();
            }
        }
    }

    /* access modifiers changed from: protected */
    public Consumer<CloseableReference<CloseableImage>> wrapConsumer(Consumer<CloseableReference<CloseableImage>> consumer, final CacheKey cacheKey, final boolean isMemoryCacheEnabled) {
        return new DelegatingConsumer<CloseableReference<CloseableImage>, CloseableReference<CloseableImage>>(consumer) {
            public void onNewResultImpl(CloseableReference<CloseableImage> newResult, int status) {
                CloseableReference<CloseableImage> newCachedResult;
                CloseableReference<CloseableImage> currentCachedResult;
                try {
                    if (FrescoSystrace.isTracing()) {
                        FrescoSystrace.beginSection("BitmapMemoryCacheProducer#onNewResultImpl");
                    }
                    boolean isLast = isLast(status);
                    if (newResult == null) {
                        if (isLast) {
                            getConsumer().onNewResult(null, status);
                        }
                        if (FrescoSystrace.isTracing()) {
                            FrescoSystrace.endSection();
                            return;
                        }
                        return;
                    }
                    if (!newResult.get().isStateful()) {
                        if (!statusHasFlag(status, 8)) {
                            if (!isLast && (currentCachedResult = BitmapMemoryCacheProducer.this.mMemoryCache.get(cacheKey)) != null) {
                                QualityInfo newInfo = newResult.get().getQualityInfo();
                                QualityInfo cachedInfo = currentCachedResult.get().getQualityInfo();
                                if (cachedInfo.isOfFullQuality() || cachedInfo.getQuality() >= newInfo.getQuality()) {
                                    getConsumer().onNewResult(currentCachedResult, status);
                                    CloseableReference.closeSafely((CloseableReference<?>) currentCachedResult);
                                    if (FrescoSystrace.isTracing()) {
                                        FrescoSystrace.endSection();
                                        return;
                                    }
                                    return;
                                }
                                CloseableReference.closeSafely((CloseableReference<?>) currentCachedResult);
                            }
                            newCachedResult = null;
                            if (isMemoryCacheEnabled) {
                                newCachedResult = BitmapMemoryCacheProducer.this.mMemoryCache.cache(cacheKey, newResult);
                            }
                            if (isLast) {
                                getConsumer().onProgressUpdate(1.0f);
                            }
                            getConsumer().onNewResult(newCachedResult != null ? newCachedResult : newResult, status);
                            CloseableReference.closeSafely((CloseableReference<?>) newCachedResult);
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
        };
    }

    /* access modifiers changed from: protected */
    public String getProducerName() {
        return PRODUCER_NAME;
    }
}
