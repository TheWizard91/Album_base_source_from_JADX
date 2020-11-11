package com.facebook.imagepipeline.producers;

import android.graphics.Bitmap;
import android.os.Build;
import com.facebook.common.internal.ImmutableMap;
import com.facebook.common.internal.Preconditions;
import com.facebook.common.memory.ByteArrayPool;
import com.facebook.common.references.CloseableReference;
import com.facebook.common.util.ExceptionWithNoStacktrace;
import com.facebook.common.util.UriUtil;
import com.facebook.imageformat.DefaultImageFormats;
import com.facebook.imagepipeline.common.ImageDecodeOptions;
import com.facebook.imagepipeline.core.CloseableReferenceFactory;
import com.facebook.imagepipeline.decoder.ImageDecoder;
import com.facebook.imagepipeline.decoder.ProgressiveJpegConfig;
import com.facebook.imagepipeline.decoder.ProgressiveJpegParser;
import com.facebook.imagepipeline.image.CloseableImage;
import com.facebook.imagepipeline.image.CloseableStaticBitmap;
import com.facebook.imagepipeline.image.EncodedImage;
import com.facebook.imagepipeline.image.ImmutableQualityInfo;
import com.facebook.imagepipeline.image.QualityInfo;
import com.facebook.imagepipeline.producers.JobScheduler;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.systrace.FrescoSystrace;
import com.facebook.imagepipeline.transcoder.DownsampleUtil;
import com.facebook.imageutils.BitmapUtil;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executor;
import javax.annotation.Nullable;

public class DecodeProducer implements Producer<CloseableReference<CloseableImage>> {
    public static final String ENCODED_IMAGE_SIZE = "encodedImageSize";
    public static final String EXTRA_BITMAP_BYTES = "byteCount";
    public static final String EXTRA_BITMAP_SIZE = "bitmapSize";
    public static final String EXTRA_HAS_GOOD_QUALITY = "hasGoodQuality";
    public static final String EXTRA_IMAGE_FORMAT_NAME = "imageFormat";
    public static final String EXTRA_IS_FINAL = "isFinal";
    private static final int MAX_BITMAP_SIZE = 104857600;
    public static final String PRODUCER_NAME = "DecodeProducer";
    public static final String REQUESTED_IMAGE_SIZE = "requestedImageSize";
    public static final String SAMPLE_SIZE = "sampleSize";
    private final ByteArrayPool mByteArrayPool;
    /* access modifiers changed from: private */
    public final CloseableReferenceFactory mCloseableReferenceFactory;
    private final boolean mDecodeCancellationEnabled;
    /* access modifiers changed from: private */
    public final boolean mDownsampleEnabled;
    /* access modifiers changed from: private */
    public final boolean mDownsampleEnabledForNetwork;
    /* access modifiers changed from: private */
    public final Executor mExecutor;
    /* access modifiers changed from: private */
    public final ImageDecoder mImageDecoder;
    private final Producer<EncodedImage> mInputProducer;
    private final int mMaxBitmapSize;
    private final ProgressiveJpegConfig mProgressiveJpegConfig;

    /* JADX WARNING: type inference failed for: r9v0, types: [com.facebook.imagepipeline.producers.Producer<com.facebook.imagepipeline.image.EncodedImage>, java.lang.Object] */
    /* JADX WARNING: Unknown variable types count: 1 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public DecodeProducer(com.facebook.common.memory.ByteArrayPool r2, java.util.concurrent.Executor r3, com.facebook.imagepipeline.decoder.ImageDecoder r4, com.facebook.imagepipeline.decoder.ProgressiveJpegConfig r5, boolean r6, boolean r7, boolean r8, com.facebook.imagepipeline.producers.Producer<com.facebook.imagepipeline.image.EncodedImage> r9, int r10, com.facebook.imagepipeline.core.CloseableReferenceFactory r11) {
        /*
            r1 = this;
            r1.<init>()
            java.lang.Object r0 = com.facebook.common.internal.Preconditions.checkNotNull(r2)
            com.facebook.common.memory.ByteArrayPool r0 = (com.facebook.common.memory.ByteArrayPool) r0
            r1.mByteArrayPool = r0
            java.lang.Object r0 = com.facebook.common.internal.Preconditions.checkNotNull(r3)
            java.util.concurrent.Executor r0 = (java.util.concurrent.Executor) r0
            r1.mExecutor = r0
            java.lang.Object r0 = com.facebook.common.internal.Preconditions.checkNotNull(r4)
            com.facebook.imagepipeline.decoder.ImageDecoder r0 = (com.facebook.imagepipeline.decoder.ImageDecoder) r0
            r1.mImageDecoder = r0
            java.lang.Object r0 = com.facebook.common.internal.Preconditions.checkNotNull(r5)
            com.facebook.imagepipeline.decoder.ProgressiveJpegConfig r0 = (com.facebook.imagepipeline.decoder.ProgressiveJpegConfig) r0
            r1.mProgressiveJpegConfig = r0
            r1.mDownsampleEnabled = r6
            r1.mDownsampleEnabledForNetwork = r7
            java.lang.Object r0 = com.facebook.common.internal.Preconditions.checkNotNull(r9)
            com.facebook.imagepipeline.producers.Producer r0 = (com.facebook.imagepipeline.producers.Producer) r0
            r1.mInputProducer = r0
            r1.mDecodeCancellationEnabled = r8
            r1.mMaxBitmapSize = r10
            r1.mCloseableReferenceFactory = r11
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.facebook.imagepipeline.producers.DecodeProducer.<init>(com.facebook.common.memory.ByteArrayPool, java.util.concurrent.Executor, com.facebook.imagepipeline.decoder.ImageDecoder, com.facebook.imagepipeline.decoder.ProgressiveJpegConfig, boolean, boolean, boolean, com.facebook.imagepipeline.producers.Producer, int, com.facebook.imagepipeline.core.CloseableReferenceFactory):void");
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v3, resolved type: com.facebook.imagepipeline.producers.DecodeProducer$LocalImagesProgressiveDecoder} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v5, resolved type: com.facebook.imagepipeline.producers.DecodeProducer$NetworkImagesProgressiveDecoder} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v6, resolved type: com.facebook.imagepipeline.producers.DecodeProducer$LocalImagesProgressiveDecoder} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v3, resolved type: com.facebook.imagepipeline.producers.DecodeProducer$LocalImagesProgressiveDecoder} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void produceResults(com.facebook.imagepipeline.producers.Consumer<com.facebook.common.references.CloseableReference<com.facebook.imagepipeline.image.CloseableImage>> r11, com.facebook.imagepipeline.producers.ProducerContext r12) {
        /*
            r10 = this;
            boolean r0 = com.facebook.imagepipeline.systrace.FrescoSystrace.isTracing()     // Catch:{ all -> 0x004d }
            if (r0 == 0) goto L_0x000b
            java.lang.String r0 = "DecodeProducer#produceResults"
            com.facebook.imagepipeline.systrace.FrescoSystrace.beginSection(r0)     // Catch:{ all -> 0x004d }
        L_0x000b:
            com.facebook.imagepipeline.request.ImageRequest r0 = r12.getImageRequest()     // Catch:{ all -> 0x004d }
            android.net.Uri r1 = r0.getSourceUri()     // Catch:{ all -> 0x004d }
            boolean r1 = com.facebook.common.util.UriUtil.isNetworkUri(r1)     // Catch:{ all -> 0x004d }
            if (r1 != 0) goto L_0x0027
            com.facebook.imagepipeline.producers.DecodeProducer$LocalImagesProgressiveDecoder r1 = new com.facebook.imagepipeline.producers.DecodeProducer$LocalImagesProgressiveDecoder     // Catch:{ all -> 0x004d }
            boolean r6 = r10.mDecodeCancellationEnabled     // Catch:{ all -> 0x004d }
            int r7 = r10.mMaxBitmapSize     // Catch:{ all -> 0x004d }
            r2 = r1
            r3 = r10
            r4 = r11
            r5 = r12
            r2.<init>(r4, r5, r6, r7)     // Catch:{ all -> 0x004d }
            goto L_0x003e
        L_0x0027:
            com.facebook.imagepipeline.decoder.ProgressiveJpegParser r5 = new com.facebook.imagepipeline.decoder.ProgressiveJpegParser     // Catch:{ all -> 0x004d }
            com.facebook.common.memory.ByteArrayPool r1 = r10.mByteArrayPool     // Catch:{ all -> 0x004d }
            r5.<init>(r1)     // Catch:{ all -> 0x004d }
            com.facebook.imagepipeline.producers.DecodeProducer$NetworkImagesProgressiveDecoder r9 = new com.facebook.imagepipeline.producers.DecodeProducer$NetworkImagesProgressiveDecoder     // Catch:{ all -> 0x004d }
            com.facebook.imagepipeline.decoder.ProgressiveJpegConfig r6 = r10.mProgressiveJpegConfig     // Catch:{ all -> 0x004d }
            boolean r7 = r10.mDecodeCancellationEnabled     // Catch:{ all -> 0x004d }
            int r8 = r10.mMaxBitmapSize     // Catch:{ all -> 0x004d }
            r1 = r9
            r2 = r10
            r3 = r11
            r4 = r12
            r1.<init>(r3, r4, r5, r6, r7, r8)     // Catch:{ all -> 0x004d }
            r1 = r9
        L_0x003e:
            com.facebook.imagepipeline.producers.Producer<com.facebook.imagepipeline.image.EncodedImage> r2 = r10.mInputProducer     // Catch:{ all -> 0x004d }
            r2.produceResults(r1, r12)     // Catch:{ all -> 0x004d }
            boolean r0 = com.facebook.imagepipeline.systrace.FrescoSystrace.isTracing()
            if (r0 == 0) goto L_0x004c
            com.facebook.imagepipeline.systrace.FrescoSystrace.endSection()
        L_0x004c:
            return
        L_0x004d:
            r0 = move-exception
            boolean r1 = com.facebook.imagepipeline.systrace.FrescoSystrace.isTracing()
            if (r1 == 0) goto L_0x0057
            com.facebook.imagepipeline.systrace.FrescoSystrace.endSection()
        L_0x0057:
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.facebook.imagepipeline.producers.DecodeProducer.produceResults(com.facebook.imagepipeline.producers.Consumer, com.facebook.imagepipeline.producers.ProducerContext):void");
    }

    private abstract class ProgressiveDecoder extends DelegatingConsumer<EncodedImage, CloseableReference<CloseableImage>> {
        private static final int DECODE_EXCEPTION_MESSAGE_NUM_HEADER_BYTES = 10;
        private final String TAG = "ProgressiveDecoder";
        private final ImageDecodeOptions mImageDecodeOptions;
        private boolean mIsFinished;
        /* access modifiers changed from: private */
        public final JobScheduler mJobScheduler;
        /* access modifiers changed from: private */
        public final ProducerContext mProducerContext;
        private final ProducerListener2 mProducerListener;

        /* access modifiers changed from: protected */
        public abstract int getIntermediateImageEndOffset(EncodedImage encodedImage);

        /* access modifiers changed from: protected */
        public abstract QualityInfo getQualityInfo();

        public ProgressiveDecoder(Consumer<CloseableReference<CloseableImage>> consumer, final ProducerContext producerContext, final boolean decodeCancellationEnabled, final int maxBitmapSize) {
            super(consumer);
            this.mProducerContext = producerContext;
            this.mProducerListener = producerContext.getProducerListener();
            ImageDecodeOptions imageDecodeOptions = producerContext.getImageRequest().getImageDecodeOptions();
            this.mImageDecodeOptions = imageDecodeOptions;
            this.mIsFinished = false;
            this.mJobScheduler = new JobScheduler(DecodeProducer.this.mExecutor, new JobScheduler.JobRunnable(DecodeProducer.this) {
                public void run(EncodedImage encodedImage, int status) {
                    if (encodedImage != null) {
                        if (DecodeProducer.this.mDownsampleEnabled || !BaseConsumer.statusHasFlag(status, 16)) {
                            ImageRequest request = producerContext.getImageRequest();
                            if (DecodeProducer.this.mDownsampleEnabledForNetwork || !UriUtil.isNetworkUri(request.getSourceUri())) {
                                encodedImage.setSampleSize(DownsampleUtil.determineSampleSize(request.getRotationOptions(), request.getResizeOptions(), encodedImage, maxBitmapSize));
                            }
                        }
                        if (producerContext.getImagePipelineConfig().getExperiments().shouldDownsampleIfLargeBitmap()) {
                            ProgressiveDecoder.this.maybeIncreaseSampleSize(encodedImage);
                        }
                        ProgressiveDecoder.this.doDecode(encodedImage, status);
                    }
                }
            }, imageDecodeOptions.minDecodeIntervalMs);
            producerContext.addCallbacks(new BaseProducerContextCallbacks(DecodeProducer.this) {
                public void onIsIntermediateResultExpectedChanged() {
                    if (ProgressiveDecoder.this.mProducerContext.isIntermediateResultExpected()) {
                        ProgressiveDecoder.this.mJobScheduler.scheduleJob();
                    }
                }

                public void onCancellationRequested() {
                    if (decodeCancellationEnabled) {
                        ProgressiveDecoder.this.handleCancellation();
                    }
                }
            });
        }

        /* access modifiers changed from: private */
        public void maybeIncreaseSampleSize(EncodedImage encodedImage) {
            if (encodedImage.getImageFormat() == DefaultImageFormats.JPEG) {
                encodedImage.setSampleSize(DownsampleUtil.determineSampleSizeJPEG(encodedImage, BitmapUtil.getPixelSizeForBitmapConfig(this.mImageDecodeOptions.bitmapConfig), DecodeProducer.MAX_BITMAP_SIZE));
            }
        }

        public void onNewResultImpl(EncodedImage newResult, int status) {
            boolean isTracing;
            try {
                if (FrescoSystrace.isTracing()) {
                    FrescoSystrace.beginSection("DecodeProducer#onNewResultImpl");
                }
                boolean isLast = isLast(status);
                if (isLast) {
                    if (newResult == null) {
                        handleError(new ExceptionWithNoStacktrace("Encoded image is null."));
                        if (!isTracing) {
                            return;
                        }
                        return;
                    } else if (!newResult.isValid()) {
                        handleError(new ExceptionWithNoStacktrace("Encoded image is not valid."));
                        if (FrescoSystrace.isTracing()) {
                            FrescoSystrace.endSection();
                            return;
                        }
                        return;
                    }
                }
                if (updateDecodeJob(newResult, status)) {
                    boolean isPlaceholder = statusHasFlag(status, 4);
                    if (isLast || isPlaceholder || this.mProducerContext.isIntermediateResultExpected()) {
                        this.mJobScheduler.scheduleJob();
                    }
                    if (FrescoSystrace.isTracing()) {
                        FrescoSystrace.endSection();
                    }
                } else if (FrescoSystrace.isTracing()) {
                    FrescoSystrace.endSection();
                }
            } finally {
                if (FrescoSystrace.isTracing()) {
                    FrescoSystrace.endSection();
                }
            }
        }

        /* access modifiers changed from: protected */
        public void onProgressUpdateImpl(float progress) {
            super.onProgressUpdateImpl(0.99f * progress);
        }

        public void onFailureImpl(Throwable t) {
            handleError(t);
        }

        public void onCancellationImpl() {
            handleCancellation();
        }

        /* access modifiers changed from: protected */
        public boolean updateDecodeJob(EncodedImage ref, int status) {
            return this.mJobScheduler.updateJob(ref, status);
        }

        /* access modifiers changed from: private */
        /* JADX WARNING: Code restructure failed: missing block: B:61:0x0159, code lost:
            r0 = e;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:62:0x015a, code lost:
            r25 = r12;
            r26 = r13;
            r2 = r9;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:63:0x0161, code lost:
            r0 = th;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:64:0x0162, code lost:
            r26 = r13;
         */
        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Removed duplicated region for block: B:63:0x0161 A[ExcHandler: all (th java.lang.Throwable), Splitter:B:43:0x00e3] */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public void doDecode(com.facebook.imagepipeline.image.EncodedImage r35, int r36) {
            /*
                r34 = this;
                r15 = r34
                r1 = r36
                java.lang.String r14 = "DecodeProducer"
                com.facebook.imageformat.ImageFormat r0 = r35.getImageFormat()
                com.facebook.imageformat.ImageFormat r2 = com.facebook.imageformat.DefaultImageFormats.JPEG
                if (r0 == r2) goto L_0x0015
                boolean r0 = isNotLast(r36)
                if (r0 == 0) goto L_0x0015
                return
            L_0x0015:
                boolean r0 = r34.isFinished()
                if (r0 != 0) goto L_0x01d1
                boolean r0 = com.facebook.imagepipeline.image.EncodedImage.isValid(r35)
                if (r0 != 0) goto L_0x0023
                goto L_0x01d1
            L_0x0023:
                com.facebook.imageformat.ImageFormat r16 = r35.getImageFormat()
                if (r16 == 0) goto L_0x0030
                java.lang.String r0 = r16.getName()
                r17 = r0
                goto L_0x0035
            L_0x0030:
                java.lang.String r0 = "unknown"
                r17 = r0
            L_0x0035:
                java.lang.StringBuilder r0 = new java.lang.StringBuilder
                r0.<init>()
                int r2 = r35.getWidth()
                java.lang.StringBuilder r0 = r0.append(r2)
                java.lang.String r2 = "x"
                java.lang.StringBuilder r0 = r0.append(r2)
                int r3 = r35.getHeight()
                java.lang.StringBuilder r0 = r0.append(r3)
                java.lang.String r18 = r0.toString()
                int r0 = r35.getSampleSize()
                java.lang.String r19 = java.lang.String.valueOf(r0)
                boolean r20 = isLast(r36)
                r5 = 0
                r6 = 1
                if (r20 == 0) goto L_0x006f
                r0 = 8
                boolean r0 = statusHasFlag(r1, r0)
                if (r0 != 0) goto L_0x006f
                r0 = r6
                goto L_0x0070
            L_0x006f:
                r0 = r5
            L_0x0070:
                r21 = r0
                r7 = 4
                boolean r22 = statusHasFlag(r1, r7)
                com.facebook.imagepipeline.producers.ProducerContext r0 = r15.mProducerContext
                com.facebook.imagepipeline.request.ImageRequest r0 = r0.getImageRequest()
                com.facebook.imagepipeline.common.ResizeOptions r13 = r0.getResizeOptions()
                if (r13 == 0) goto L_0x009f
                java.lang.StringBuilder r0 = new java.lang.StringBuilder
                r0.<init>()
                int r3 = r13.width
                java.lang.StringBuilder r0 = r0.append(r3)
                java.lang.StringBuilder r0 = r0.append(r2)
                int r2 = r13.height
                java.lang.StringBuilder r0 = r0.append(r2)
                java.lang.String r0 = r0.toString()
                r23 = r0
                goto L_0x00a4
            L_0x009f:
                java.lang.String r0 = "unknown"
                r23 = r0
            L_0x00a4:
                com.facebook.imagepipeline.producers.JobScheduler r0 = r15.mJobScheduler     // Catch:{ all -> 0x01c8 }
                long r3 = r0.getQueuedTime()     // Catch:{ all -> 0x01c8 }
                com.facebook.imagepipeline.producers.ProducerContext r0 = r15.mProducerContext     // Catch:{ all -> 0x01c8 }
                com.facebook.imagepipeline.request.ImageRequest r0 = r0.getImageRequest()     // Catch:{ all -> 0x01c8 }
                android.net.Uri r0 = r0.getSourceUri()     // Catch:{ all -> 0x01c8 }
                java.lang.String r0 = java.lang.String.valueOf(r0)     // Catch:{ all -> 0x01c8 }
                r24 = r0
                if (r21 != 0) goto L_0x00c9
                if (r22 == 0) goto L_0x00bf
                goto L_0x00c9
            L_0x00bf:
                int r0 = r34.getIntermediateImageEndOffset(r35)     // Catch:{ all -> 0x00c4 }
                goto L_0x00cd
            L_0x00c4:
                r0 = move-exception
                r26 = r13
                goto L_0x01cd
            L_0x00c9:
                int r0 = r35.getSize()     // Catch:{ all -> 0x01c8 }
            L_0x00cd:
                r12 = r0
                if (r21 != 0) goto L_0x00d8
                if (r22 == 0) goto L_0x00d3
                goto L_0x00d8
            L_0x00d3:
                com.facebook.imagepipeline.image.QualityInfo r0 = r34.getQualityInfo()     // Catch:{ all -> 0x00c4 }
                goto L_0x00da
            L_0x00d8:
                com.facebook.imagepipeline.image.QualityInfo r0 = com.facebook.imagepipeline.image.ImmutableQualityInfo.FULL_QUALITY     // Catch:{ all -> 0x01c8 }
            L_0x00da:
                r11 = r0
                com.facebook.imagepipeline.producers.ProducerListener2 r0 = r15.mProducerListener     // Catch:{ all -> 0x01c8 }
                com.facebook.imagepipeline.producers.ProducerContext r2 = r15.mProducerContext     // Catch:{ all -> 0x01c8 }
                r0.onProducerStart(r2, r14)     // Catch:{ all -> 0x01c8 }
                r2 = 0
                com.facebook.imagepipeline.producers.DecodeProducer r0 = com.facebook.imagepipeline.producers.DecodeProducer.this     // Catch:{ DecodeException -> 0x016c, Exception -> 0x0166, all -> 0x0161 }
                com.facebook.imagepipeline.decoder.ImageDecoder r0 = r0.mImageDecoder     // Catch:{ DecodeException -> 0x016c, Exception -> 0x0166, all -> 0x0161 }
                com.facebook.imagepipeline.common.ImageDecodeOptions r8 = r15.mImageDecodeOptions     // Catch:{ DecodeException -> 0x016c, Exception -> 0x0166, all -> 0x0161 }
                r10 = r35
                com.facebook.imagepipeline.image.CloseableImage r0 = r0.decode(r10, r12, r11, r8)     // Catch:{ DecodeException -> 0x016c, Exception -> 0x0166, all -> 0x0161 }
                r9 = r0
                int r0 = r35.getSampleSize()     // Catch:{ Exception -> 0x0159, all -> 0x0161 }
                if (r0 == r6) goto L_0x00fd
                r0 = r1 | 16
                r8 = r0
                goto L_0x00fe
            L_0x00fd:
                r8 = r1
            L_0x00fe:
                r1 = r34
                r2 = r9
                r5 = r11
                r6 = r20
                r7 = r17
                r25 = r12
                r12 = r8
                r8 = r18
                r26 = r13
                r13 = r9
                r9 = r23
                r10 = r19
                java.util.Map r0 = r1.getExtraMap(r2, r3, r5, r6, r7, r8, r9, r10)     // Catch:{ all -> 0x0155 }
                com.facebook.imagepipeline.producers.ProducerListener2 r1 = r15.mProducerListener     // Catch:{ all -> 0x0155 }
                com.facebook.imagepipeline.producers.ProducerContext r2 = r15.mProducerContext     // Catch:{ all -> 0x0155 }
                r1.onProducerFinishWithSuccess(r2, r14, r0)     // Catch:{ all -> 0x0155 }
                if (r13 == 0) goto L_0x014d
                com.facebook.imagepipeline.image.OriginalEncodedImageInfo r1 = new com.facebook.imagepipeline.image.OriginalEncodedImageInfo     // Catch:{ all -> 0x0155 }
                com.facebook.imagepipeline.producers.ProducerContext r2 = r15.mProducerContext     // Catch:{ all -> 0x0155 }
                com.facebook.imagepipeline.request.ImageRequest r2 = r2.getImageRequest()     // Catch:{ all -> 0x0155 }
                android.net.Uri r28 = r2.getSourceUri()     // Catch:{ all -> 0x0155 }
                com.facebook.imagepipeline.producers.ProducerContext r2 = r15.mProducerContext     // Catch:{ all -> 0x0155 }
                com.facebook.imagepipeline.image.EncodedImageOrigin r29 = r2.getEncodedImageOrigin()     // Catch:{ all -> 0x0155 }
                com.facebook.imagepipeline.producers.ProducerContext r2 = r15.mProducerContext     // Catch:{ all -> 0x0155 }
                java.lang.Object r30 = r2.getCallerContext()     // Catch:{ all -> 0x0155 }
                int r31 = r35.getWidth()     // Catch:{ all -> 0x0155 }
                int r32 = r35.getHeight()     // Catch:{ all -> 0x0155 }
                int r33 = r35.getSize()     // Catch:{ all -> 0x0155 }
                r27 = r1
                r27.<init>(r28, r29, r30, r31, r32, r33)     // Catch:{ all -> 0x0155 }
                r13.setOriginalEncodedImageInfo(r1)     // Catch:{ all -> 0x0155 }
            L_0x014d:
                r15.handleResult(r13, r12)     // Catch:{ all -> 0x0155 }
                com.facebook.imagepipeline.image.EncodedImage.closeSafely(r35)
                return
            L_0x0155:
                r0 = move-exception
                r1 = r12
                goto L_0x01cd
            L_0x0159:
                r0 = move-exception
                r25 = r12
                r26 = r13
                r13 = r9
                r2 = r13
                goto L_0x019f
            L_0x0161:
                r0 = move-exception
                r26 = r13
                goto L_0x01cd
            L_0x0166:
                r0 = move-exception
                r25 = r12
                r26 = r13
                goto L_0x019f
            L_0x016c:
                r0 = move-exception
                r25 = r12
                r26 = r13
                com.facebook.imagepipeline.image.EncodedImage r8 = r0.getEncodedImage()     // Catch:{ Exception -> 0x019e, all -> 0x019c }
                java.lang.String r9 = "ProgressiveDecoder"
                java.lang.String r10 = "%s, {uri: %s, firstEncodedBytes: %s, length: %d}"
                java.lang.Object[] r7 = new java.lang.Object[r7]     // Catch:{ Exception -> 0x019e, all -> 0x019c }
                java.lang.String r12 = r0.getMessage()     // Catch:{ Exception -> 0x019e, all -> 0x019c }
                r7[r5] = r12     // Catch:{ Exception -> 0x019e, all -> 0x019c }
                r7[r6] = r24     // Catch:{ Exception -> 0x019e, all -> 0x019c }
                r5 = 2
                r6 = 10
                java.lang.String r6 = r8.getFirstBytesAsHexString(r6)     // Catch:{ Exception -> 0x019e, all -> 0x019c }
                r7[r5] = r6     // Catch:{ Exception -> 0x019e, all -> 0x019c }
                r5 = 3
                int r6 = r8.getSize()     // Catch:{ Exception -> 0x019e, all -> 0x019c }
                java.lang.Integer r6 = java.lang.Integer.valueOf(r6)     // Catch:{ Exception -> 0x019e, all -> 0x019c }
                r7[r5] = r6     // Catch:{ Exception -> 0x019e, all -> 0x019c }
                com.facebook.common.logging.FLog.m102w((java.lang.String) r9, (java.lang.String) r10, (java.lang.Object[]) r7)     // Catch:{ Exception -> 0x019e, all -> 0x019c }
                throw r0     // Catch:{ Exception -> 0x019e, all -> 0x019c }
            L_0x019c:
                r0 = move-exception
                goto L_0x01cd
            L_0x019e:
                r0 = move-exception
            L_0x019f:
                r5 = r34
                r6 = r2
                r7 = r3
                r9 = r11
                r10 = r20
                r27 = r11
                r11 = r17
                r12 = r18
                r13 = r23
                r1 = r14
                r14 = r19
                java.util.Map r5 = r5.getExtraMap(r6, r7, r9, r10, r11, r12, r13, r14)     // Catch:{ all -> 0x01c4 }
                com.facebook.imagepipeline.producers.ProducerListener2 r6 = r15.mProducerListener     // Catch:{ all -> 0x01c4 }
                com.facebook.imagepipeline.producers.ProducerContext r7 = r15.mProducerContext     // Catch:{ all -> 0x01c4 }
                r6.onProducerFinishWithFailure(r7, r1, r0, r5)     // Catch:{ all -> 0x01c4 }
                r15.handleError(r0)     // Catch:{ all -> 0x01c4 }
                com.facebook.imagepipeline.image.EncodedImage.closeSafely(r35)
                return
            L_0x01c4:
                r0 = move-exception
                r1 = r36
                goto L_0x01cd
            L_0x01c8:
                r0 = move-exception
                r26 = r13
                r1 = r36
            L_0x01cd:
                com.facebook.imagepipeline.image.EncodedImage.closeSafely(r35)
                throw r0
            L_0x01d1:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.facebook.imagepipeline.producers.DecodeProducer.ProgressiveDecoder.doDecode(com.facebook.imagepipeline.image.EncodedImage, int):void");
        }

        @Nullable
        private Map<String, String> getExtraMap(@Nullable CloseableImage image, long queueTime, QualityInfo quality, boolean isFinal, String imageFormatName, String encodedImageSize, String requestImageSize, String sampleSize) {
            CloseableImage closeableImage = image;
            String str = imageFormatName;
            String str2 = encodedImageSize;
            String str3 = requestImageSize;
            String str4 = sampleSize;
            if (!this.mProducerListener.requiresExtraMap(this.mProducerContext, DecodeProducer.PRODUCER_NAME)) {
                return null;
            }
            String queueStr = String.valueOf(queueTime);
            String qualityStr = String.valueOf(quality.isOfGoodEnoughQuality());
            String finalStr = String.valueOf(isFinal);
            if (closeableImage instanceof CloseableStaticBitmap) {
                Bitmap bitmap = ((CloseableStaticBitmap) closeableImage).getUnderlyingBitmap();
                Bitmap bitmap2 = bitmap;
                Map<String, String> tmpMap = new HashMap<>(8);
                tmpMap.put(DecodeProducer.EXTRA_BITMAP_SIZE, bitmap.getWidth() + "x" + bitmap.getHeight());
                tmpMap.put("queueTime", queueStr);
                tmpMap.put(DecodeProducer.EXTRA_HAS_GOOD_QUALITY, qualityStr);
                tmpMap.put(DecodeProducer.EXTRA_IS_FINAL, finalStr);
                tmpMap.put("encodedImageSize", str2);
                tmpMap.put(DecodeProducer.EXTRA_IMAGE_FORMAT_NAME, str);
                tmpMap.put(DecodeProducer.REQUESTED_IMAGE_SIZE, str3);
                tmpMap.put(DecodeProducer.SAMPLE_SIZE, sampleSize);
                if (Build.VERSION.SDK_INT >= 12) {
                    tmpMap.put(DecodeProducer.EXTRA_BITMAP_BYTES, bitmap2.getByteCount() + "");
                }
                return ImmutableMap.copyOf(tmpMap);
            }
            Map<String, String> tmpMap2 = new HashMap<>(7);
            tmpMap2.put("queueTime", queueStr);
            tmpMap2.put(DecodeProducer.EXTRA_HAS_GOOD_QUALITY, qualityStr);
            tmpMap2.put(DecodeProducer.EXTRA_IS_FINAL, finalStr);
            tmpMap2.put("encodedImageSize", str2);
            tmpMap2.put(DecodeProducer.EXTRA_IMAGE_FORMAT_NAME, str);
            tmpMap2.put(DecodeProducer.REQUESTED_IMAGE_SIZE, str3);
            tmpMap2.put(DecodeProducer.SAMPLE_SIZE, str4);
            return ImmutableMap.copyOf(tmpMap2);
        }

        private synchronized boolean isFinished() {
            return this.mIsFinished;
        }

        private void maybeFinish(boolean shouldFinish) {
            synchronized (this) {
                if (shouldFinish) {
                    if (!this.mIsFinished) {
                        getConsumer().onProgressUpdate(1.0f);
                        this.mIsFinished = true;
                        this.mJobScheduler.clearJob();
                    }
                }
            }
        }

        private void handleResult(CloseableImage decodedImage, int status) {
            CloseableReference<CloseableImage> decodedImageRef = DecodeProducer.this.mCloseableReferenceFactory.create(decodedImage);
            try {
                maybeFinish(isLast(status));
                getConsumer().onNewResult(decodedImageRef, status);
            } finally {
                CloseableReference.closeSafely((CloseableReference<?>) decodedImageRef);
            }
        }

        private void handleError(Throwable t) {
            maybeFinish(true);
            getConsumer().onFailure(t);
        }

        /* access modifiers changed from: private */
        public void handleCancellation() {
            maybeFinish(true);
            getConsumer().onCancellation();
        }
    }

    private class LocalImagesProgressiveDecoder extends ProgressiveDecoder {
        public LocalImagesProgressiveDecoder(Consumer<CloseableReference<CloseableImage>> consumer, ProducerContext producerContext, boolean decodeCancellationEnabled, int maxBitmapSize) {
            super(consumer, producerContext, decodeCancellationEnabled, maxBitmapSize);
        }

        /* access modifiers changed from: protected */
        public synchronized boolean updateDecodeJob(EncodedImage encodedImage, int status) {
            if (isNotLast(status)) {
                return false;
            }
            return super.updateDecodeJob(encodedImage, status);
        }

        /* access modifiers changed from: protected */
        public int getIntermediateImageEndOffset(EncodedImage encodedImage) {
            return encodedImage.getSize();
        }

        /* access modifiers changed from: protected */
        public QualityInfo getQualityInfo() {
            return ImmutableQualityInfo.m132of(0, false, false);
        }
    }

    private class NetworkImagesProgressiveDecoder extends ProgressiveDecoder {
        private int mLastScheduledScanNumber = 0;
        private final ProgressiveJpegConfig mProgressiveJpegConfig;
        private final ProgressiveJpegParser mProgressiveJpegParser;

        public NetworkImagesProgressiveDecoder(Consumer<CloseableReference<CloseableImage>> consumer, ProducerContext producerContext, ProgressiveJpegParser progressiveJpegParser, ProgressiveJpegConfig progressiveJpegConfig, boolean decodeCancellationEnabled, int maxBitmapSize) {
            super(consumer, producerContext, decodeCancellationEnabled, maxBitmapSize);
            this.mProgressiveJpegParser = (ProgressiveJpegParser) Preconditions.checkNotNull(progressiveJpegParser);
            this.mProgressiveJpegConfig = (ProgressiveJpegConfig) Preconditions.checkNotNull(progressiveJpegConfig);
        }

        /* access modifiers changed from: protected */
        /* JADX WARNING: Code restructure failed: missing block: B:32:0x0054, code lost:
            return r0;
         */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public synchronized boolean updateDecodeJob(com.facebook.imagepipeline.image.EncodedImage r6, int r7) {
            /*
                r5 = this;
                monitor-enter(r5)
                boolean r0 = super.updateDecodeJob(r6, r7)     // Catch:{ all -> 0x0055 }
                boolean r1 = isNotLast(r7)     // Catch:{ all -> 0x0055 }
                if (r1 != 0) goto L_0x0013
                r1 = 8
                boolean r1 = statusHasFlag(r7, r1)     // Catch:{ all -> 0x0055 }
                if (r1 == 0) goto L_0x0053
            L_0x0013:
                r1 = 4
                boolean r1 = statusHasFlag(r7, r1)     // Catch:{ all -> 0x0055 }
                if (r1 != 0) goto L_0x0053
                boolean r1 = com.facebook.imagepipeline.image.EncodedImage.isValid(r6)     // Catch:{ all -> 0x0055 }
                if (r1 == 0) goto L_0x0053
                com.facebook.imageformat.ImageFormat r1 = r6.getImageFormat()     // Catch:{ all -> 0x0055 }
                com.facebook.imageformat.ImageFormat r2 = com.facebook.imageformat.DefaultImageFormats.JPEG     // Catch:{ all -> 0x0055 }
                if (r1 != r2) goto L_0x0053
                com.facebook.imagepipeline.decoder.ProgressiveJpegParser r1 = r5.mProgressiveJpegParser     // Catch:{ all -> 0x0055 }
                boolean r1 = r1.parseMoreData(r6)     // Catch:{ all -> 0x0055 }
                r2 = 0
                if (r1 != 0) goto L_0x0033
                monitor-exit(r5)
                return r2
            L_0x0033:
                com.facebook.imagepipeline.decoder.ProgressiveJpegParser r1 = r5.mProgressiveJpegParser     // Catch:{ all -> 0x0055 }
                int r1 = r1.getBestScanNumber()     // Catch:{ all -> 0x0055 }
                int r3 = r5.mLastScheduledScanNumber     // Catch:{ all -> 0x0055 }
                if (r1 > r3) goto L_0x003f
                monitor-exit(r5)
                return r2
            L_0x003f:
                com.facebook.imagepipeline.decoder.ProgressiveJpegConfig r4 = r5.mProgressiveJpegConfig     // Catch:{ all -> 0x0055 }
                int r3 = r4.getNextScanNumberToDecode(r3)     // Catch:{ all -> 0x0055 }
                if (r1 >= r3) goto L_0x0051
                com.facebook.imagepipeline.decoder.ProgressiveJpegParser r3 = r5.mProgressiveJpegParser     // Catch:{ all -> 0x0055 }
                boolean r3 = r3.isEndMarkerRead()     // Catch:{ all -> 0x0055 }
                if (r3 != 0) goto L_0x0051
                monitor-exit(r5)
                return r2
            L_0x0051:
                r5.mLastScheduledScanNumber = r1     // Catch:{ all -> 0x0055 }
            L_0x0053:
                monitor-exit(r5)
                return r0
            L_0x0055:
                r6 = move-exception
                monitor-exit(r5)
                throw r6
            */
            throw new UnsupportedOperationException("Method not decompiled: com.facebook.imagepipeline.producers.DecodeProducer.NetworkImagesProgressiveDecoder.updateDecodeJob(com.facebook.imagepipeline.image.EncodedImage, int):boolean");
        }

        /* access modifiers changed from: protected */
        public int getIntermediateImageEndOffset(EncodedImage encodedImage) {
            return this.mProgressiveJpegParser.getBestScanEndOffset();
        }

        /* access modifiers changed from: protected */
        public QualityInfo getQualityInfo() {
            return this.mProgressiveJpegConfig.getQualityInfo(this.mProgressiveJpegParser.getBestScanNumber());
        }
    }
}
