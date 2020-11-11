package com.facebook.imagepipeline.producers;

import com.facebook.common.internal.Closeables;
import com.facebook.common.memory.PooledByteBuffer;
import com.facebook.common.memory.PooledByteBufferFactory;
import com.facebook.common.references.CloseableReference;
import com.facebook.imagepipeline.image.EncodedImage;
import com.facebook.imagepipeline.request.ImageRequest;
import com.google.android.gms.common.internal.ImagesContract;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.Executor;
import javax.annotation.Nullable;

public abstract class LocalFetchProducer implements Producer<EncodedImage> {
    private final Executor mExecutor;
    private final PooledByteBufferFactory mPooledByteBufferFactory;

    /* access modifiers changed from: protected */
    public abstract EncodedImage getEncodedImage(ImageRequest imageRequest) throws IOException;

    /* access modifiers changed from: protected */
    public abstract String getProducerName();

    protected LocalFetchProducer(Executor executor, PooledByteBufferFactory pooledByteBufferFactory) {
        this.mExecutor = executor;
        this.mPooledByteBufferFactory = pooledByteBufferFactory;
    }

    public void produceResults(Consumer<EncodedImage> consumer, ProducerContext producerContext) {
        ProducerListener2 listener = producerContext.getProducerListener();
        final ImageRequest imageRequest = producerContext.getImageRequest();
        final ProducerListener2 producerListener2 = listener;
        final ProducerContext producerContext2 = producerContext;
        final StatefulProducerRunnable cancellableProducerRunnable = new StatefulProducerRunnable<EncodedImage>(consumer, listener, producerContext, getProducerName()) {
            /* access modifiers changed from: protected */
            @Nullable
            public EncodedImage getResult() throws Exception {
                EncodedImage encodedImage = LocalFetchProducer.this.getEncodedImage(imageRequest);
                if (encodedImage == null) {
                    producerListener2.onUltimateProducerReached(producerContext2, LocalFetchProducer.this.getProducerName(), false);
                    producerContext2.setExtra(1, ImagesContract.LOCAL);
                    return null;
                }
                encodedImage.parseMetaData();
                producerListener2.onUltimateProducerReached(producerContext2, LocalFetchProducer.this.getProducerName(), true);
                producerContext2.setExtra(1, ImagesContract.LOCAL);
                return encodedImage;
            }

            /* access modifiers changed from: protected */
            public void disposeResult(EncodedImage result) {
                EncodedImage.closeSafely(result);
            }
        };
        producerContext.addCallbacks(new BaseProducerContextCallbacks() {
            public void onCancellationRequested() {
                cancellableProducerRunnable.cancel();
            }
        });
        this.mExecutor.execute(cancellableProducerRunnable);
    }

    /* access modifiers changed from: protected */
    public EncodedImage getByteBufferBackedEncodedImage(InputStream inputStream, int length) throws IOException {
        CloseableReference<PooledByteBuffer> ref;
        if (length <= 0) {
            try {
                ref = CloseableReference.m124of(this.mPooledByteBufferFactory.newByteBuffer(inputStream));
            } catch (Throwable th) {
                Closeables.closeQuietly(inputStream);
                CloseableReference.closeSafely((CloseableReference<?>) null);
                throw th;
            }
        } else {
            ref = CloseableReference.m124of(this.mPooledByteBufferFactory.newByteBuffer(inputStream, length));
        }
        EncodedImage encodedImage = new EncodedImage(ref);
        Closeables.closeQuietly(inputStream);
        CloseableReference.closeSafely((CloseableReference<?>) ref);
        return encodedImage;
    }

    /* access modifiers changed from: protected */
    public EncodedImage getEncodedImage(InputStream inputStream, int length) throws IOException {
        return getByteBufferBackedEncodedImage(inputStream, length);
    }
}
