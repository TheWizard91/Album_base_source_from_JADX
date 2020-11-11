package com.facebook.imagepipeline.producers;

import com.facebook.common.internal.Preconditions;
import com.facebook.common.memory.PooledByteBuffer;
import com.facebook.common.memory.PooledByteBufferFactory;
import com.facebook.common.memory.PooledByteBufferOutputStream;
import com.facebook.common.references.CloseableReference;
import com.facebook.common.util.TriState;
import com.facebook.imageformat.DefaultImageFormats;
import com.facebook.imageformat.ImageFormat;
import com.facebook.imageformat.ImageFormatChecker;
import com.facebook.imagepipeline.image.EncodedImage;
import com.facebook.imagepipeline.nativecode.WebpTranscoder;
import com.facebook.imagepipeline.nativecode.WebpTranscoderFactory;
import java.io.InputStream;
import java.util.concurrent.Executor;
import javax.annotation.Nullable;

public class WebpTranscodeProducer implements Producer<EncodedImage> {
    private static final int DEFAULT_JPEG_QUALITY = 80;
    public static final String PRODUCER_NAME = "WebpTranscodeProducer";
    private final Executor mExecutor;
    private final Producer<EncodedImage> mInputProducer;
    /* access modifiers changed from: private */
    public final PooledByteBufferFactory mPooledByteBufferFactory;

    /* JADX WARNING: type inference failed for: r4v0, types: [com.facebook.imagepipeline.producers.Producer<com.facebook.imagepipeline.image.EncodedImage>, java.lang.Object] */
    /* JADX WARNING: Unknown variable types count: 1 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public WebpTranscodeProducer(java.util.concurrent.Executor r2, com.facebook.common.memory.PooledByteBufferFactory r3, com.facebook.imagepipeline.producers.Producer<com.facebook.imagepipeline.image.EncodedImage> r4) {
        /*
            r1 = this;
            r1.<init>()
            java.lang.Object r0 = com.facebook.common.internal.Preconditions.checkNotNull(r2)
            java.util.concurrent.Executor r0 = (java.util.concurrent.Executor) r0
            r1.mExecutor = r0
            java.lang.Object r0 = com.facebook.common.internal.Preconditions.checkNotNull(r3)
            com.facebook.common.memory.PooledByteBufferFactory r0 = (com.facebook.common.memory.PooledByteBufferFactory) r0
            r1.mPooledByteBufferFactory = r0
            java.lang.Object r0 = com.facebook.common.internal.Preconditions.checkNotNull(r4)
            com.facebook.imagepipeline.producers.Producer r0 = (com.facebook.imagepipeline.producers.Producer) r0
            r1.mInputProducer = r0
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.facebook.imagepipeline.producers.WebpTranscodeProducer.<init>(java.util.concurrent.Executor, com.facebook.common.memory.PooledByteBufferFactory, com.facebook.imagepipeline.producers.Producer):void");
    }

    public void produceResults(Consumer<EncodedImage> consumer, ProducerContext context) {
        this.mInputProducer.produceResults(new WebpTranscodeConsumer(consumer, context), context);
    }

    private class WebpTranscodeConsumer extends DelegatingConsumer<EncodedImage, EncodedImage> {
        private final ProducerContext mContext;
        private TriState mShouldTranscodeWhenFinished = TriState.UNSET;

        public WebpTranscodeConsumer(Consumer<EncodedImage> consumer, ProducerContext context) {
            super(consumer);
            this.mContext = context;
        }

        /* access modifiers changed from: protected */
        public void onNewResultImpl(@Nullable EncodedImage newResult, int status) {
            if (this.mShouldTranscodeWhenFinished == TriState.UNSET && newResult != null) {
                this.mShouldTranscodeWhenFinished = WebpTranscodeProducer.shouldTranscode(newResult);
            }
            if (this.mShouldTranscodeWhenFinished == TriState.NO) {
                getConsumer().onNewResult(newResult, status);
            } else if (!isLast(status)) {
            } else {
                if (this.mShouldTranscodeWhenFinished != TriState.YES || newResult == null) {
                    getConsumer().onNewResult(newResult, status);
                } else {
                    WebpTranscodeProducer.this.transcodeLastResult(newResult, getConsumer(), this.mContext);
                }
            }
        }
    }

    /* access modifiers changed from: private */
    public void transcodeLastResult(EncodedImage originalResult, Consumer<EncodedImage> consumer, ProducerContext producerContext) {
        Preconditions.checkNotNull(originalResult);
        final EncodedImage cloneOrNull = EncodedImage.cloneOrNull(originalResult);
        this.mExecutor.execute(new StatefulProducerRunnable<EncodedImage>(consumer, producerContext.getProducerListener(), producerContext, PRODUCER_NAME) {
            /* access modifiers changed from: protected */
            public EncodedImage getResult() throws Exception {
                CloseableReference<PooledByteBuffer> ref;
                PooledByteBufferOutputStream outputStream = WebpTranscodeProducer.this.mPooledByteBufferFactory.newOutputStream();
                try {
                    WebpTranscodeProducer.doTranscode(cloneOrNull, outputStream);
                    ref = CloseableReference.m124of(outputStream.toByteBuffer());
                    EncodedImage encodedImage = new EncodedImage(ref);
                    encodedImage.copyMetaDataFrom(cloneOrNull);
                    CloseableReference.closeSafely((CloseableReference<?>) ref);
                    outputStream.close();
                    return encodedImage;
                } catch (Throwable th) {
                    outputStream.close();
                    throw th;
                }
            }

            /* access modifiers changed from: protected */
            public void disposeResult(EncodedImage result) {
                EncodedImage.closeSafely(result);
            }

            /* access modifiers changed from: protected */
            public void onSuccess(EncodedImage result) {
                EncodedImage.closeSafely(cloneOrNull);
                super.onSuccess(result);
            }

            /* access modifiers changed from: protected */
            public void onFailure(Exception e) {
                EncodedImage.closeSafely(cloneOrNull);
                super.onFailure(e);
            }

            /* access modifiers changed from: protected */
            public void onCancellation() {
                EncodedImage.closeSafely(cloneOrNull);
                super.onCancellation();
            }
        });
    }

    /* access modifiers changed from: private */
    public static TriState shouldTranscode(EncodedImage encodedImage) {
        Preconditions.checkNotNull(encodedImage);
        ImageFormat imageFormat = ImageFormatChecker.getImageFormat_WrapIOException(encodedImage.getInputStream());
        if (DefaultImageFormats.isStaticWebpFormat(imageFormat)) {
            WebpTranscoder webpTranscoder = WebpTranscoderFactory.getWebpTranscoder();
            if (webpTranscoder == null) {
                return TriState.NO;
            }
            return TriState.valueOf(!webpTranscoder.isWebpNativelySupported(imageFormat));
        } else if (imageFormat == ImageFormat.UNKNOWN) {
            return TriState.UNSET;
        } else {
            return TriState.NO;
        }
    }

    /* access modifiers changed from: private */
    public static void doTranscode(EncodedImage encodedImage, PooledByteBufferOutputStream outputStream) throws Exception {
        InputStream imageInputStream = encodedImage.getInputStream();
        ImageFormat imageFormat = ImageFormatChecker.getImageFormat_WrapIOException(imageInputStream);
        if (imageFormat == DefaultImageFormats.WEBP_SIMPLE || imageFormat == DefaultImageFormats.WEBP_EXTENDED) {
            WebpTranscoderFactory.getWebpTranscoder().transcodeWebpToJpeg(imageInputStream, outputStream, 80);
            encodedImage.setImageFormat(DefaultImageFormats.JPEG);
        } else if (imageFormat == DefaultImageFormats.WEBP_LOSSLESS || imageFormat == DefaultImageFormats.WEBP_EXTENDED_WITH_ALPHA) {
            WebpTranscoderFactory.getWebpTranscoder().transcodeWebpToPng(imageInputStream, outputStream);
            encodedImage.setImageFormat(DefaultImageFormats.PNG);
        } else {
            throw new IllegalArgumentException("Wrong image format");
        }
    }
}
