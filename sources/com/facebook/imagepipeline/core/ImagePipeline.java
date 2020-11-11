package com.facebook.imagepipeline.core;

import android.net.Uri;
import bolts.Continuation;
import bolts.Task;
import com.facebook.cache.common.CacheKey;
import com.facebook.callercontext.CallerContextVerifier;
import com.facebook.common.internal.Objects;
import com.facebook.common.internal.Preconditions;
import com.facebook.common.internal.Predicate;
import com.facebook.common.internal.Supplier;
import com.facebook.common.memory.PooledByteBuffer;
import com.facebook.common.references.CloseableReference;
import com.facebook.datasource.DataSource;
import com.facebook.datasource.DataSources;
import com.facebook.datasource.SimpleDataSource;
import com.facebook.imagepipeline.cache.BufferedDiskCache;
import com.facebook.imagepipeline.cache.CacheKeyFactory;
import com.facebook.imagepipeline.cache.MemoryCache;
import com.facebook.imagepipeline.common.Priority;
import com.facebook.imagepipeline.common.ResizeOptions;
import com.facebook.imagepipeline.datasource.CloseableProducerToDataSourceAdapter;
import com.facebook.imagepipeline.datasource.ProducerToDataSourceAdapter;
import com.facebook.imagepipeline.image.CloseableImage;
import com.facebook.imagepipeline.listener.ForwardingRequestListener;
import com.facebook.imagepipeline.listener.ForwardingRequestListener2;
import com.facebook.imagepipeline.listener.RequestListener;
import com.facebook.imagepipeline.listener.RequestListener2;
import com.facebook.imagepipeline.producers.InternalRequestListener;
import com.facebook.imagepipeline.producers.Producer;
import com.facebook.imagepipeline.producers.SettableProducerContext;
import com.facebook.imagepipeline.producers.ThreadHandoffProducerQueue;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import com.facebook.imagepipeline.systrace.FrescoSystrace;
import java.util.Set;
import java.util.concurrent.CancellationException;
import java.util.concurrent.atomic.AtomicLong;
import javax.annotation.Nullable;

public class ImagePipeline {
    private static final CancellationException PREFETCH_EXCEPTION = new CancellationException("Prefetching is not enabled");
    private final MemoryCache<CacheKey, CloseableImage> mBitmapMemoryCache;
    private final CacheKeyFactory mCacheKeyFactory;
    @Nullable
    private final CallerContextVerifier mCallerContextVerifier;
    private final ImagePipelineConfig mConfig;
    private final MemoryCache<CacheKey, PooledByteBuffer> mEncodedMemoryCache;
    private AtomicLong mIdCounter = new AtomicLong();
    private final Supplier<Boolean> mIsPrefetchEnabledSupplier;
    private final Supplier<Boolean> mLazyDataSource;
    private final BufferedDiskCache mMainBufferedDiskCache;
    private final ProducerSequenceFactory mProducerSequenceFactory;
    private final RequestListener mRequestListener;
    private final RequestListener2 mRequestListener2;
    /* access modifiers changed from: private */
    public final BufferedDiskCache mSmallImageBufferedDiskCache;
    private final Supplier<Boolean> mSuppressBitmapPrefetchingSupplier;
    private final ThreadHandoffProducerQueue mThreadHandoffProducerQueue;

    public ImagePipeline(ProducerSequenceFactory producerSequenceFactory, Set<RequestListener> requestListeners, Set<RequestListener2> requestListener2s, Supplier<Boolean> isPrefetchEnabledSupplier, MemoryCache<CacheKey, CloseableImage> bitmapMemoryCache, MemoryCache<CacheKey, PooledByteBuffer> encodedMemoryCache, BufferedDiskCache mainBufferedDiskCache, BufferedDiskCache smallImageBufferedDiskCache, CacheKeyFactory cacheKeyFactory, ThreadHandoffProducerQueue threadHandoffProducerQueue, Supplier<Boolean> suppressBitmapPrefetchingSupplier, Supplier<Boolean> lazyDataSource, @Nullable CallerContextVerifier callerContextVerifier, ImagePipelineConfig config) {
        this.mProducerSequenceFactory = producerSequenceFactory;
        this.mRequestListener = new ForwardingRequestListener(requestListeners);
        this.mRequestListener2 = new ForwardingRequestListener2(requestListener2s);
        this.mIsPrefetchEnabledSupplier = isPrefetchEnabledSupplier;
        this.mBitmapMemoryCache = bitmapMemoryCache;
        this.mEncodedMemoryCache = encodedMemoryCache;
        this.mMainBufferedDiskCache = mainBufferedDiskCache;
        this.mSmallImageBufferedDiskCache = smallImageBufferedDiskCache;
        this.mCacheKeyFactory = cacheKeyFactory;
        this.mThreadHandoffProducerQueue = threadHandoffProducerQueue;
        this.mSuppressBitmapPrefetchingSupplier = suppressBitmapPrefetchingSupplier;
        this.mLazyDataSource = lazyDataSource;
        this.mCallerContextVerifier = callerContextVerifier;
        this.mConfig = config;
    }

    public String generateUniqueFutureId() {
        return String.valueOf(this.mIdCounter.getAndIncrement());
    }

    public Supplier<DataSource<CloseableReference<CloseableImage>>> getDataSourceSupplier(final ImageRequest imageRequest, final Object callerContext, final ImageRequest.RequestLevel requestLevel) {
        return new Supplier<DataSource<CloseableReference<CloseableImage>>>() {
            public DataSource<CloseableReference<CloseableImage>> get() {
                return ImagePipeline.this.fetchDecodedImage(imageRequest, callerContext, requestLevel);
            }

            public String toString() {
                return Objects.toStringHelper((Object) this).add("uri", (Object) imageRequest.getSourceUri()).toString();
            }
        };
    }

    public Supplier<DataSource<CloseableReference<CloseableImage>>> getDataSourceSupplier(ImageRequest imageRequest, Object callerContext, ImageRequest.RequestLevel requestLevel, @Nullable RequestListener requestListener) {
        final ImageRequest imageRequest2 = imageRequest;
        final Object obj = callerContext;
        final ImageRequest.RequestLevel requestLevel2 = requestLevel;
        final RequestListener requestListener2 = requestListener;
        return new Supplier<DataSource<CloseableReference<CloseableImage>>>() {
            public DataSource<CloseableReference<CloseableImage>> get() {
                return ImagePipeline.this.fetchDecodedImage(imageRequest2, obj, requestLevel2, requestListener2);
            }

            public String toString() {
                return Objects.toStringHelper((Object) this).add("uri", (Object) imageRequest2.getSourceUri()).toString();
            }
        };
    }

    public Supplier<DataSource<CloseableReference<CloseableImage>>> getDataSourceSupplier(ImageRequest imageRequest, Object callerContext, ImageRequest.RequestLevel requestLevel, @Nullable RequestListener requestListener, @Nullable String uiComponentId) {
        final ImageRequest imageRequest2 = imageRequest;
        final Object obj = callerContext;
        final ImageRequest.RequestLevel requestLevel2 = requestLevel;
        final RequestListener requestListener2 = requestListener;
        final String str = uiComponentId;
        return new Supplier<DataSource<CloseableReference<CloseableImage>>>() {
            public DataSource<CloseableReference<CloseableImage>> get() {
                return ImagePipeline.this.fetchDecodedImage(imageRequest2, obj, requestLevel2, requestListener2, str);
            }

            public String toString() {
                return Objects.toStringHelper((Object) this).add("uri", (Object) imageRequest2.getSourceUri()).toString();
            }
        };
    }

    public Supplier<DataSource<CloseableReference<PooledByteBuffer>>> getEncodedImageDataSourceSupplier(final ImageRequest imageRequest, final Object callerContext) {
        return new Supplier<DataSource<CloseableReference<PooledByteBuffer>>>() {
            public DataSource<CloseableReference<PooledByteBuffer>> get() {
                return ImagePipeline.this.fetchEncodedImage(imageRequest, callerContext);
            }

            public String toString() {
                return Objects.toStringHelper((Object) this).add("uri", (Object) imageRequest.getSourceUri()).toString();
            }
        };
    }

    public DataSource<CloseableReference<CloseableImage>> fetchImageFromBitmapCache(ImageRequest imageRequest, Object callerContext) {
        return fetchDecodedImage(imageRequest, callerContext, ImageRequest.RequestLevel.BITMAP_MEMORY_CACHE);
    }

    public DataSource<CloseableReference<CloseableImage>> fetchDecodedImage(ImageRequest imageRequest, Object callerContext) {
        return fetchDecodedImage(imageRequest, callerContext, ImageRequest.RequestLevel.FULL_FETCH);
    }

    public DataSource<CloseableReference<CloseableImage>> fetchDecodedImage(ImageRequest imageRequest, Object callerContext, @Nullable RequestListener requestListener) {
        return fetchDecodedImage(imageRequest, callerContext, ImageRequest.RequestLevel.FULL_FETCH, requestListener);
    }

    public DataSource<CloseableReference<CloseableImage>> fetchDecodedImage(ImageRequest imageRequest, Object callerContext, ImageRequest.RequestLevel lowestPermittedRequestLevelOnSubmit) {
        return fetchDecodedImage(imageRequest, callerContext, lowestPermittedRequestLevelOnSubmit, (RequestListener) null);
    }

    public DataSource<CloseableReference<CloseableImage>> fetchDecodedImage(ImageRequest imageRequest, Object callerContext, ImageRequest.RequestLevel lowestPermittedRequestLevelOnSubmit, @Nullable RequestListener requestListener) {
        return fetchDecodedImage(imageRequest, callerContext, lowestPermittedRequestLevelOnSubmit, requestListener, (String) null);
    }

    public DataSource<CloseableReference<CloseableImage>> fetchDecodedImage(ImageRequest imageRequest, Object callerContext, ImageRequest.RequestLevel lowestPermittedRequestLevelOnSubmit, @Nullable RequestListener requestListener, @Nullable String uiComponentId) {
        try {
            return submitFetchRequest(this.mProducerSequenceFactory.getDecodedImageProducerSequence(imageRequest), imageRequest, lowestPermittedRequestLevelOnSubmit, callerContext, requestListener, uiComponentId);
        } catch (Exception exception) {
            return DataSources.immediateFailedDataSource(exception);
        }
    }

    public DataSource<CloseableReference<PooledByteBuffer>> fetchEncodedImage(ImageRequest imageRequest, Object callerContext) {
        return fetchEncodedImage(imageRequest, callerContext, (RequestListener) null);
    }

    public DataSource<CloseableReference<PooledByteBuffer>> fetchEncodedImage(ImageRequest imageRequest, Object callerContext, @Nullable RequestListener requestListener) {
        Preconditions.checkNotNull(imageRequest.getSourceUri());
        try {
            Producer<CloseableReference<PooledByteBuffer>> producerSequence = this.mProducerSequenceFactory.getEncodedImageProducerSequence(imageRequest);
            if (imageRequest.getResizeOptions() != null) {
                imageRequest = ImageRequestBuilder.fromRequest(imageRequest).setResizeOptions((ResizeOptions) null).build();
            }
            return submitFetchRequest(producerSequence, imageRequest, ImageRequest.RequestLevel.FULL_FETCH, callerContext, requestListener, (String) null);
        } catch (Exception exception) {
            return DataSources.immediateFailedDataSource(exception);
        }
    }

    public DataSource<Void> prefetchToBitmapCache(ImageRequest imageRequest, Object callerContext) {
        return prefetchToBitmapCache(imageRequest, callerContext, Priority.MEDIUM);
    }

    @Deprecated
    public DataSource<Void> prefetchToBitmapCacheWithHighPriority(ImageRequest imageRequest, Object callerContext) {
        return prefetchToBitmapCache(imageRequest, callerContext, Priority.HIGH);
    }

    private DataSource<Void> prefetchToBitmapCache(ImageRequest imageRequest, Object callerContext, Priority priority) {
        boolean skipBitmapCache;
        Producer<Void> producerSequence;
        if (!this.mIsPrefetchEnabledSupplier.get().booleanValue()) {
            return DataSources.immediateFailedDataSource(PREFETCH_EXCEPTION);
        }
        try {
            Boolean shouldDecodePrefetches = imageRequest.shouldDecodePrefetches();
            if (shouldDecodePrefetches != null) {
                skipBitmapCache = !shouldDecodePrefetches.booleanValue();
            } else {
                skipBitmapCache = this.mSuppressBitmapPrefetchingSupplier.get().booleanValue();
            }
            if (skipBitmapCache) {
                producerSequence = this.mProducerSequenceFactory.getEncodedImagePrefetchProducerSequence(imageRequest);
            } else {
                producerSequence = this.mProducerSequenceFactory.getDecodedImagePrefetchProducerSequence(imageRequest);
            }
            return submitPrefetchRequest(producerSequence, imageRequest, ImageRequest.RequestLevel.FULL_FETCH, callerContext, priority);
        } catch (Exception exception) {
            return DataSources.immediateFailedDataSource(exception);
        }
    }

    public DataSource<Void> prefetchToDiskCache(ImageRequest imageRequest, Object callerContext) {
        return prefetchToDiskCache(imageRequest, callerContext, Priority.MEDIUM);
    }

    public DataSource<Void> prefetchToDiskCache(ImageRequest imageRequest, Object callerContext, Priority priority) {
        if (!this.mIsPrefetchEnabledSupplier.get().booleanValue()) {
            return DataSources.immediateFailedDataSource(PREFETCH_EXCEPTION);
        }
        try {
            return submitPrefetchRequest(this.mProducerSequenceFactory.getEncodedImagePrefetchProducerSequence(imageRequest), imageRequest, ImageRequest.RequestLevel.FULL_FETCH, callerContext, priority);
        } catch (Exception exception) {
            return DataSources.immediateFailedDataSource(exception);
        }
    }

    public DataSource<Void> prefetchToEncodedCache(ImageRequest imageRequest, Object callerContext) {
        return prefetchToEncodedCache(imageRequest, callerContext, Priority.MEDIUM);
    }

    public DataSource<Void> prefetchToEncodedCache(ImageRequest imageRequest, Object callerContext, Priority priority) {
        if (!this.mIsPrefetchEnabledSupplier.get().booleanValue()) {
            return DataSources.immediateFailedDataSource(PREFETCH_EXCEPTION);
        }
        try {
            return submitPrefetchRequest(this.mProducerSequenceFactory.getEncodedImagePrefetchProducerSequence(imageRequest), imageRequest, ImageRequest.RequestLevel.FULL_FETCH, callerContext, priority);
        } catch (Exception exception) {
            return DataSources.immediateFailedDataSource(exception);
        }
    }

    public void evictFromMemoryCache(Uri uri) {
        Predicate<CacheKey> predicate = predicateForUri(uri);
        this.mBitmapMemoryCache.removeAll(predicate);
        this.mEncodedMemoryCache.removeAll(predicate);
    }

    public void evictFromDiskCache(Uri uri) {
        evictFromDiskCache(ImageRequest.fromUri(uri));
    }

    public void evictFromDiskCache(ImageRequest imageRequest) {
        CacheKey cacheKey = this.mCacheKeyFactory.getEncodedCacheKey(imageRequest, (Object) null);
        this.mMainBufferedDiskCache.remove(cacheKey);
        this.mSmallImageBufferedDiskCache.remove(cacheKey);
    }

    public void evictFromCache(Uri uri) {
        evictFromMemoryCache(uri);
        evictFromDiskCache(uri);
    }

    public void clearMemoryCaches() {
        Predicate<CacheKey> allPredicate = new Predicate<CacheKey>() {
            public boolean apply(CacheKey key) {
                return true;
            }
        };
        this.mBitmapMemoryCache.removeAll(allPredicate);
        this.mEncodedMemoryCache.removeAll(allPredicate);
    }

    public void clearDiskCaches() {
        this.mMainBufferedDiskCache.clearAll();
        this.mSmallImageBufferedDiskCache.clearAll();
    }

    public long getUsedDiskCacheSize() {
        return this.mMainBufferedDiskCache.getSize() + this.mSmallImageBufferedDiskCache.getSize();
    }

    public void clearCaches() {
        clearMemoryCaches();
        clearDiskCaches();
    }

    public boolean isInBitmapMemoryCache(Uri uri) {
        if (uri == null) {
            return false;
        }
        return this.mBitmapMemoryCache.contains(predicateForUri(uri));
    }

    public MemoryCache<CacheKey, CloseableImage> getBitmapMemoryCache() {
        return this.mBitmapMemoryCache;
    }

    public boolean isInBitmapMemoryCache(ImageRequest imageRequest) {
        if (imageRequest == null) {
            return false;
        }
        CloseableReference<CloseableImage> ref = this.mBitmapMemoryCache.get(this.mCacheKeyFactory.getBitmapCacheKey(imageRequest, (Object) null));
        try {
            return CloseableReference.isValid(ref);
        } finally {
            CloseableReference.closeSafely((CloseableReference<?>) ref);
        }
    }

    public boolean isInDiskCacheSync(Uri uri) {
        return isInDiskCacheSync(uri, ImageRequest.CacheChoice.SMALL) || isInDiskCacheSync(uri, ImageRequest.CacheChoice.DEFAULT);
    }

    public boolean isInDiskCacheSync(Uri uri, ImageRequest.CacheChoice cacheChoice) {
        return isInDiskCacheSync(ImageRequestBuilder.newBuilderWithSource(uri).setCacheChoice(cacheChoice).build());
    }

    public boolean isInDiskCacheSync(ImageRequest imageRequest) {
        CacheKey cacheKey = this.mCacheKeyFactory.getEncodedCacheKey(imageRequest, (Object) null);
        int i = C07239.f82x9ce557aa[imageRequest.getCacheChoice().ordinal()];
        if (i == 1) {
            return this.mMainBufferedDiskCache.diskCheckSync(cacheKey);
        }
        if (i != 2) {
            return false;
        }
        return this.mSmallImageBufferedDiskCache.diskCheckSync(cacheKey);
    }

    /* renamed from: com.facebook.imagepipeline.core.ImagePipeline$9 */
    static /* synthetic */ class C07239 {

        /* renamed from: $SwitchMap$com$facebook$imagepipeline$request$ImageRequest$CacheChoice */
        static final /* synthetic */ int[] f82x9ce557aa;

        static {
            int[] iArr = new int[ImageRequest.CacheChoice.values().length];
            f82x9ce557aa = iArr;
            try {
                iArr[ImageRequest.CacheChoice.DEFAULT.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                f82x9ce557aa[ImageRequest.CacheChoice.SMALL.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
        }
    }

    public DataSource<Boolean> isInDiskCache(Uri uri) {
        return isInDiskCache(ImageRequest.fromUri(uri));
    }

    public DataSource<Boolean> isInDiskCache(ImageRequest imageRequest) {
        final CacheKey cacheKey = this.mCacheKeyFactory.getEncodedCacheKey(imageRequest, (Object) null);
        final SimpleDataSource<Boolean> dataSource = SimpleDataSource.create();
        this.mMainBufferedDiskCache.contains(cacheKey).continueWithTask(new Continuation<Boolean, Task<Boolean>>() {
            public Task<Boolean> then(Task<Boolean> task) throws Exception {
                if (task.isCancelled() || task.isFaulted() || !task.getResult().booleanValue()) {
                    return ImagePipeline.this.mSmallImageBufferedDiskCache.contains(cacheKey);
                }
                return Task.forResult(true);
            }
        }).continueWith(new Continuation<Boolean, Void>() {
            public Void then(Task<Boolean> task) throws Exception {
                dataSource.setResult(Boolean.valueOf(!task.isCancelled() && !task.isFaulted() && task.getResult().booleanValue()));
                return null;
            }
        });
        return dataSource;
    }

    @Nullable
    public CacheKey getCacheKey(@Nullable ImageRequest imageRequest, Object callerContext) {
        if (FrescoSystrace.isTracing()) {
            FrescoSystrace.beginSection("ImagePipeline#getCacheKey");
        }
        CacheKeyFactory cacheKeyFactory = this.mCacheKeyFactory;
        CacheKey cacheKey = null;
        if (!(cacheKeyFactory == null || imageRequest == null)) {
            cacheKey = imageRequest.getPostprocessor() != null ? cacheKeyFactory.getPostprocessedBitmapCacheKey(imageRequest, callerContext) : cacheKeyFactory.getBitmapCacheKey(imageRequest, callerContext);
        }
        if (FrescoSystrace.isTracing()) {
            FrescoSystrace.endSection();
        }
        return cacheKey;
    }

    @Nullable
    public CloseableReference<CloseableImage> getCachedImage(@Nullable CacheKey cacheKey) {
        MemoryCache<CacheKey, CloseableImage> memoryCache = this.mBitmapMemoryCache;
        if (memoryCache == null || cacheKey == null) {
            return null;
        }
        CloseableReference<CloseableImage> closeableImage = memoryCache.get(cacheKey);
        if (closeableImage == null || closeableImage.get().getQualityInfo().isOfFullQuality()) {
            return closeableImage;
        }
        closeableImage.close();
        return null;
    }

    public boolean hasCachedImage(@Nullable CacheKey cacheKey) {
        MemoryCache<CacheKey, CloseableImage> memoryCache = this.mBitmapMemoryCache;
        if (memoryCache == null || cacheKey == null) {
            return false;
        }
        return memoryCache.contains(cacheKey);
    }

    /* JADX WARNING: Removed duplicated region for block: B:30:0x0088  */
    /* JADX WARNING: Removed duplicated region for block: B:35:0x0093  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private <T> com.facebook.datasource.DataSource<com.facebook.common.references.CloseableReference<T>> submitFetchRequest(com.facebook.imagepipeline.producers.Producer<com.facebook.common.references.CloseableReference<T>> r19, com.facebook.imagepipeline.request.ImageRequest r20, com.facebook.imagepipeline.request.ImageRequest.RequestLevel r21, java.lang.Object r22, @javax.annotation.Nullable com.facebook.imagepipeline.listener.RequestListener r23, @javax.annotation.Nullable java.lang.String r24) {
        /*
            r18 = this;
            r1 = r18
            boolean r0 = com.facebook.imagepipeline.systrace.FrescoSystrace.isTracing()
            if (r0 == 0) goto L_0x000d
            java.lang.String r0 = "ImagePipeline#submitFetchRequest"
            com.facebook.imagepipeline.systrace.FrescoSystrace.beginSection(r0)
        L_0x000d:
            com.facebook.imagepipeline.producers.InternalRequestListener r0 = new com.facebook.imagepipeline.producers.InternalRequestListener
            r13 = r20
            r14 = r23
            com.facebook.imagepipeline.listener.RequestListener r2 = r1.getRequestListenerForRequest(r13, r14)
            com.facebook.imagepipeline.listener.RequestListener2 r3 = r1.mRequestListener2
            r0.<init>(r2, r3)
            r15 = r0
            com.facebook.callercontext.CallerContextVerifier r0 = r1.mCallerContextVerifier
            r2 = 0
            if (r0 == 0) goto L_0x0028
            r12 = r22
            r0.verifyCallerContext(r12, r2)
            goto L_0x002a
        L_0x0028:
            r12 = r22
        L_0x002a:
            com.facebook.imagepipeline.request.ImageRequest$RequestLevel r0 = r20.getLowestPermittedRequestLevel()     // Catch:{ Exception -> 0x007b, all -> 0x0077 }
            r11 = r21
            com.facebook.imagepipeline.request.ImageRequest$RequestLevel r8 = com.facebook.imagepipeline.request.ImageRequest.RequestLevel.getMax(r0, r11)     // Catch:{ Exception -> 0x007b, all -> 0x0077 }
            com.facebook.imagepipeline.producers.SettableProducerContext r0 = new com.facebook.imagepipeline.producers.SettableProducerContext     // Catch:{ Exception -> 0x007b, all -> 0x0077 }
            java.lang.String r4 = r18.generateUniqueFutureId()     // Catch:{ Exception -> 0x007b, all -> 0x0077 }
            r9 = 0
            boolean r3 = r20.getProgressiveRenderingEnabled()     // Catch:{ Exception -> 0x007b, all -> 0x0077 }
            if (r3 != 0) goto L_0x004c
            android.net.Uri r3 = r20.getSourceUri()     // Catch:{ Exception -> 0x007b, all -> 0x0077 }
            boolean r3 = com.facebook.common.util.UriUtil.isNetworkUri(r3)     // Catch:{ Exception -> 0x007b, all -> 0x0077 }
            if (r3 != 0) goto L_0x004d
        L_0x004c:
            r2 = 1
        L_0x004d:
            r10 = r2
            com.facebook.imagepipeline.common.Priority r16 = r20.getPriority()     // Catch:{ Exception -> 0x007b, all -> 0x0077 }
            com.facebook.imagepipeline.core.ImagePipelineConfig r7 = r1.mConfig     // Catch:{ Exception -> 0x007b, all -> 0x0077 }
            r2 = r0
            r3 = r20
            r5 = r24
            r6 = r15
            r17 = r7
            r7 = r22
            r11 = r16
            r12 = r17
            r2.<init>(r3, r4, r5, r6, r7, r8, r9, r10, r11, r12)     // Catch:{ Exception -> 0x007b, all -> 0x0077 }
            r2 = r19
            com.facebook.datasource.DataSource r3 = com.facebook.imagepipeline.datasource.CloseableProducerToDataSourceAdapter.create(r2, r0, r15)     // Catch:{ Exception -> 0x0075 }
            boolean r4 = com.facebook.imagepipeline.systrace.FrescoSystrace.isTracing()
            if (r4 == 0) goto L_0x0074
            com.facebook.imagepipeline.systrace.FrescoSystrace.endSection()
        L_0x0074:
            return r3
        L_0x0075:
            r0 = move-exception
            goto L_0x007e
        L_0x0077:
            r0 = move-exception
            r2 = r19
            goto L_0x008d
        L_0x007b:
            r0 = move-exception
            r2 = r19
        L_0x007e:
            com.facebook.datasource.DataSource r3 = com.facebook.datasource.DataSources.immediateFailedDataSource(r0)     // Catch:{ all -> 0x008c }
            boolean r4 = com.facebook.imagepipeline.systrace.FrescoSystrace.isTracing()
            if (r4 == 0) goto L_0x008b
            com.facebook.imagepipeline.systrace.FrescoSystrace.endSection()
        L_0x008b:
            return r3
        L_0x008c:
            r0 = move-exception
        L_0x008d:
            boolean r3 = com.facebook.imagepipeline.systrace.FrescoSystrace.isTracing()
            if (r3 == 0) goto L_0x0096
            com.facebook.imagepipeline.systrace.FrescoSystrace.endSection()
        L_0x0096:
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.facebook.imagepipeline.core.ImagePipeline.submitFetchRequest(com.facebook.imagepipeline.producers.Producer, com.facebook.imagepipeline.request.ImageRequest, com.facebook.imagepipeline.request.ImageRequest$RequestLevel, java.lang.Object, com.facebook.imagepipeline.listener.RequestListener, java.lang.String):com.facebook.datasource.DataSource");
    }

    public <T> DataSource<CloseableReference<T>> submitFetchRequest(Producer<CloseableReference<T>> producerSequence, SettableProducerContext settableProducerContext, RequestListener requestListener) {
        if (FrescoSystrace.isTracing()) {
            FrescoSystrace.beginSection("ImagePipeline#submitFetchRequest");
        }
        try {
            DataSource<CloseableReference<T>> create = CloseableProducerToDataSourceAdapter.create(producerSequence, settableProducerContext, new InternalRequestListener(requestListener, this.mRequestListener2));
            if (FrescoSystrace.isTracing()) {
                FrescoSystrace.endSection();
            }
            return create;
        } catch (Exception exception) {
            DataSource<CloseableReference<T>> immediateFailedDataSource = DataSources.immediateFailedDataSource(exception);
            if (FrescoSystrace.isTracing()) {
                FrescoSystrace.endSection();
            }
            return immediateFailedDataSource;
        } catch (Throwable th) {
            if (FrescoSystrace.isTracing()) {
                FrescoSystrace.endSection();
            }
            throw th;
        }
    }

    public ProducerSequenceFactory getProducerSequenceFactory() {
        return this.mProducerSequenceFactory;
    }

    private DataSource<Void> submitPrefetchRequest(Producer<Void> producerSequence, ImageRequest imageRequest, ImageRequest.RequestLevel lowestPermittedRequestLevelOnSubmit, Object callerContext, Priority priority) {
        RequestListener2 requestListener = new InternalRequestListener(getRequestListenerForRequest(imageRequest, (RequestListener) null), this.mRequestListener2);
        CallerContextVerifier callerContextVerifier = this.mCallerContextVerifier;
        if (callerContextVerifier != null) {
            callerContextVerifier.verifyCallerContext(callerContext, true);
        } else {
            Object obj = callerContext;
        }
        try {
            try {
            } catch (Exception e) {
                exception = e;
                Producer<Void> producer = producerSequence;
                return DataSources.immediateFailedDataSource(exception);
            }
            try {
                return ProducerToDataSourceAdapter.create(producerSequence, new SettableProducerContext(imageRequest, generateUniqueFutureId(), requestListener, callerContext, ImageRequest.RequestLevel.getMax(imageRequest.getLowestPermittedRequestLevel(), lowestPermittedRequestLevelOnSubmit), true, false, priority, this.mConfig), requestListener);
            } catch (Exception e2) {
                exception = e2;
                return DataSources.immediateFailedDataSource(exception);
            }
        } catch (Exception e3) {
            exception = e3;
            Producer<Void> producer2 = producerSequence;
            ImageRequest.RequestLevel requestLevel = lowestPermittedRequestLevelOnSubmit;
            return DataSources.immediateFailedDataSource(exception);
        }
    }

    public RequestListener getRequestListenerForRequest(ImageRequest imageRequest, @Nullable RequestListener requestListener) {
        if (requestListener == null) {
            if (imageRequest.getRequestListener() == null) {
                return this.mRequestListener;
            }
            return new ForwardingRequestListener(this.mRequestListener, imageRequest.getRequestListener());
        } else if (imageRequest.getRequestListener() == null) {
            return new ForwardingRequestListener(this.mRequestListener, requestListener);
        } else {
            return new ForwardingRequestListener(this.mRequestListener, requestListener, imageRequest.getRequestListener());
        }
    }

    private Predicate<CacheKey> predicateForUri(final Uri uri) {
        return new Predicate<CacheKey>() {
            public boolean apply(CacheKey key) {
                return key.containsUri(uri);
            }
        };
    }

    public void pause() {
        this.mThreadHandoffProducerQueue.startQueueing();
    }

    public void resume() {
        this.mThreadHandoffProducerQueue.stopQueuing();
    }

    public boolean isPaused() {
        return this.mThreadHandoffProducerQueue.isQueueing();
    }

    public Supplier<Boolean> isLazyDataSource() {
        return this.mLazyDataSource;
    }

    public CacheKeyFactory getCacheKeyFactory() {
        return this.mCacheKeyFactory;
    }

    public ImagePipelineConfig getConfig() {
        return this.mConfig;
    }
}
