package com.facebook.imagepipeline.producers;

import android.net.Uri;
import bolts.Continuation;
import bolts.Task;
import com.facebook.cache.common.CacheKey;
import com.facebook.common.internal.ImmutableMap;
import com.facebook.common.logging.FLog;
import com.facebook.common.memory.ByteArrayPool;
import com.facebook.common.memory.PooledByteBuffer;
import com.facebook.common.memory.PooledByteBufferFactory;
import com.facebook.common.memory.PooledByteBufferOutputStream;
import com.facebook.common.references.CloseableReference;
import com.facebook.imageformat.ImageFormat;
import com.facebook.imagepipeline.cache.BufferedDiskCache;
import com.facebook.imagepipeline.cache.CacheKeyFactory;
import com.facebook.imagepipeline.common.BytesRange;
import com.facebook.imagepipeline.image.EncodedImage;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.CancellationException;
import java.util.concurrent.atomic.AtomicBoolean;
import javax.annotation.Nullable;

public class PartialDiskCacheProducer implements Producer<EncodedImage> {
    public static final String ENCODED_IMAGE_SIZE = "encodedImageSize";
    public static final String EXTRA_CACHED_VALUE_FOUND = "cached_value_found";
    public static final String PRODUCER_NAME = "PartialDiskCacheProducer";
    private final ByteArrayPool mByteArrayPool;
    private final CacheKeyFactory mCacheKeyFactory;
    private final BufferedDiskCache mDefaultBufferedDiskCache;
    private final Producer<EncodedImage> mInputProducer;
    private final PooledByteBufferFactory mPooledByteBufferFactory;

    public PartialDiskCacheProducer(BufferedDiskCache defaultBufferedDiskCache, CacheKeyFactory cacheKeyFactory, PooledByteBufferFactory pooledByteBufferFactory, ByteArrayPool byteArrayPool, Producer<EncodedImage> inputProducer) {
        this.mDefaultBufferedDiskCache = defaultBufferedDiskCache;
        this.mCacheKeyFactory = cacheKeyFactory;
        this.mPooledByteBufferFactory = pooledByteBufferFactory;
        this.mByteArrayPool = byteArrayPool;
        this.mInputProducer = inputProducer;
    }

    public void produceResults(Consumer<EncodedImage> consumer, ProducerContext producerContext) {
        ImageRequest imageRequest = producerContext.getImageRequest();
        if (!imageRequest.isDiskCacheEnabled()) {
            this.mInputProducer.produceResults(consumer, producerContext);
            return;
        }
        producerContext.getProducerListener().onProducerStart(producerContext, PRODUCER_NAME);
        CacheKey partialImageCacheKey = this.mCacheKeyFactory.getEncodedCacheKey(imageRequest, createUriForPartialCacheKey(imageRequest), producerContext.getCallerContext());
        AtomicBoolean isCancelled = new AtomicBoolean(false);
        this.mDefaultBufferedDiskCache.get(partialImageCacheKey, isCancelled).continueWith(onFinishDiskReads(consumer, producerContext, partialImageCacheKey));
        subscribeTaskForRequestCancellation(isCancelled, producerContext);
    }

    private Continuation<EncodedImage, Void> onFinishDiskReads(Consumer<EncodedImage> consumer, ProducerContext producerContext, CacheKey partialImageCacheKey) {
        final ProducerListener2 producerListener = producerContext.getProducerListener();
        final ProducerContext producerContext2 = producerContext;
        final Consumer<EncodedImage> consumer2 = consumer;
        final CacheKey cacheKey = partialImageCacheKey;
        return new Continuation<EncodedImage, Void>() {
            public Void then(Task<EncodedImage> task) throws Exception {
                if (PartialDiskCacheProducer.isTaskCancelled(task)) {
                    producerListener.onProducerFinishWithCancellation(producerContext2, PartialDiskCacheProducer.PRODUCER_NAME, (Map<String, String>) null);
                    consumer2.onCancellation();
                } else if (task.isFaulted()) {
                    producerListener.onProducerFinishWithFailure(producerContext2, PartialDiskCacheProducer.PRODUCER_NAME, task.getError(), (Map<String, String>) null);
                    PartialDiskCacheProducer.this.startInputProducer(consumer2, producerContext2, cacheKey, (EncodedImage) null);
                } else {
                    EncodedImage cachedReference = task.getResult();
                    if (cachedReference != null) {
                        ProducerListener2 producerListener2 = producerListener;
                        ProducerContext producerContext = producerContext2;
                        producerListener2.onProducerFinishWithSuccess(producerContext, PartialDiskCacheProducer.PRODUCER_NAME, PartialDiskCacheProducer.getExtraMap(producerListener2, producerContext, true, cachedReference.getSize()));
                        BytesRange cachedRange = BytesRange.toMax(cachedReference.getSize() - 1);
                        cachedReference.setBytesRange(cachedRange);
                        int cachedLength = cachedReference.getSize();
                        ImageRequest originalRequest = producerContext2.getImageRequest();
                        if (cachedRange.contains(originalRequest.getBytesRange())) {
                            producerListener.onUltimateProducerReached(producerContext2, PartialDiskCacheProducer.PRODUCER_NAME, true);
                            consumer2.onNewResult(cachedReference, 9);
                        } else {
                            consumer2.onNewResult(cachedReference, 8);
                            PartialDiskCacheProducer.this.startInputProducer(consumer2, new SettableProducerContext(ImageRequestBuilder.fromRequest(originalRequest).setBytesRange(BytesRange.from(cachedLength - 1)).build(), producerContext2), cacheKey, cachedReference);
                        }
                    } else {
                        ProducerListener2 producerListener22 = producerListener;
                        ProducerContext producerContext2 = producerContext2;
                        producerListener22.onProducerFinishWithSuccess(producerContext2, PartialDiskCacheProducer.PRODUCER_NAME, PartialDiskCacheProducer.getExtraMap(producerListener22, producerContext2, false, 0));
                        PartialDiskCacheProducer.this.startInputProducer(consumer2, producerContext2, cacheKey, cachedReference);
                    }
                }
                return null;
            }
        };
    }

    /* access modifiers changed from: private */
    public void startInputProducer(Consumer<EncodedImage> consumerOfPartialDiskCacheProducer, ProducerContext producerContext, CacheKey partialImageCacheKey, @Nullable EncodedImage partialResultFromCache) {
        this.mInputProducer.produceResults(new PartialDiskCacheConsumer(consumerOfPartialDiskCacheProducer, this.mDefaultBufferedDiskCache, partialImageCacheKey, this.mPooledByteBufferFactory, this.mByteArrayPool, partialResultFromCache), producerContext);
    }

    /* access modifiers changed from: private */
    public static boolean isTaskCancelled(Task<?> task) {
        return task.isCancelled() || (task.isFaulted() && (task.getError() instanceof CancellationException));
    }

    @Nullable
    static Map<String, String> getExtraMap(ProducerListener2 listener, ProducerContext producerContext, boolean valueFound, int sizeInBytes) {
        if (!listener.requiresExtraMap(producerContext, PRODUCER_NAME)) {
            return null;
        }
        if (valueFound) {
            return ImmutableMap.m33of("cached_value_found", String.valueOf(valueFound), "encodedImageSize", String.valueOf(sizeInBytes));
        }
        return ImmutableMap.m32of("cached_value_found", String.valueOf(valueFound));
    }

    private void subscribeTaskForRequestCancellation(final AtomicBoolean isCancelled, ProducerContext producerContext) {
        producerContext.addCallbacks(new BaseProducerContextCallbacks() {
            public void onCancellationRequested() {
                isCancelled.set(true);
            }
        });
    }

    private static Uri createUriForPartialCacheKey(ImageRequest imageRequest) {
        return imageRequest.getSourceUri().buildUpon().appendQueryParameter("fresco_partial", "true").build();
    }

    private static class PartialDiskCacheConsumer extends DelegatingConsumer<EncodedImage, EncodedImage> {
        private static final int READ_SIZE = 16384;
        private final ByteArrayPool mByteArrayPool;
        private final BufferedDiskCache mDefaultBufferedDiskCache;
        @Nullable
        private final EncodedImage mPartialEncodedImageFromCache;
        private final CacheKey mPartialImageCacheKey;
        private final PooledByteBufferFactory mPooledByteBufferFactory;

        private PartialDiskCacheConsumer(Consumer<EncodedImage> consumer, BufferedDiskCache defaultBufferedDiskCache, CacheKey partialImageCacheKey, PooledByteBufferFactory pooledByteBufferFactory, ByteArrayPool byteArrayPool, @Nullable EncodedImage partialEncodedImageFromCache) {
            super(consumer);
            this.mDefaultBufferedDiskCache = defaultBufferedDiskCache;
            this.mPartialImageCacheKey = partialImageCacheKey;
            this.mPooledByteBufferFactory = pooledByteBufferFactory;
            this.mByteArrayPool = byteArrayPool;
            this.mPartialEncodedImageFromCache = partialEncodedImageFromCache;
        }

        public void onNewResultImpl(EncodedImage newResult, int status) {
            if (!isNotLast(status)) {
                if (this.mPartialEncodedImageFromCache != null && newResult.getBytesRange() != null) {
                    try {
                        sendFinalResultToConsumer(merge(this.mPartialEncodedImageFromCache, newResult));
                    } catch (IOException e) {
                        FLog.m61e(PartialDiskCacheProducer.PRODUCER_NAME, "Error while merging image data", (Throwable) e);
                        getConsumer().onFailure(e);
                    } catch (Throwable th) {
                        newResult.close();
                        this.mPartialEncodedImageFromCache.close();
                        throw th;
                    }
                    newResult.close();
                    this.mPartialEncodedImageFromCache.close();
                    this.mDefaultBufferedDiskCache.remove(this.mPartialImageCacheKey);
                } else if (!statusHasFlag(status, 8) || !isLast(status) || newResult.getImageFormat() == ImageFormat.UNKNOWN) {
                    getConsumer().onNewResult(newResult, status);
                } else {
                    this.mDefaultBufferedDiskCache.put(this.mPartialImageCacheKey, newResult);
                    getConsumer().onNewResult(newResult, status);
                }
            }
        }

        private PooledByteBufferOutputStream merge(EncodedImage initialData, EncodedImage remainingData) throws IOException {
            PooledByteBufferOutputStream pooledOutputStream = this.mPooledByteBufferFactory.newOutputStream(remainingData.getSize() + remainingData.getBytesRange().from);
            copy(initialData.getInputStream(), pooledOutputStream, remainingData.getBytesRange().from);
            copy(remainingData.getInputStream(), pooledOutputStream, remainingData.getSize());
            return pooledOutputStream;
        }

        private void copy(InputStream from, OutputStream to, int length) throws IOException {
            int bytesStillToRead = length;
            byte[] ioArray = (byte[]) this.mByteArrayPool.get(16384);
            while (bytesStillToRead > 0) {
                try {
                    int read = from.read(ioArray, 0, Math.min(16384, bytesStillToRead));
                    int bufferLength = read;
                    if (read < 0) {
                        break;
                    } else if (bufferLength > 0) {
                        to.write(ioArray, 0, bufferLength);
                        bytesStillToRead -= bufferLength;
                    }
                } catch (Throwable th) {
                    this.mByteArrayPool.release(ioArray);
                    throw th;
                }
            }
            this.mByteArrayPool.release(ioArray);
            if (bytesStillToRead > 0) {
                throw new IOException(String.format((Locale) null, "Failed to read %d bytes - finished %d short", new Object[]{Integer.valueOf(length), Integer.valueOf(bytesStillToRead)}));
            }
        }

        private void sendFinalResultToConsumer(PooledByteBufferOutputStream pooledOutputStream) {
            CloseableReference<PooledByteBuffer> result = CloseableReference.m124of(pooledOutputStream.toByteBuffer());
            EncodedImage encodedImage = null;
            try {
                encodedImage = new EncodedImage(result);
                encodedImage.parseMetaData();
                getConsumer().onNewResult(encodedImage, 1);
            } finally {
                EncodedImage.closeSafely(encodedImage);
                CloseableReference.closeSafely((CloseableReference<?>) result);
            }
        }
    }
}
