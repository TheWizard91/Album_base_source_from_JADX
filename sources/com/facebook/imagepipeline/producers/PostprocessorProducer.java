package com.facebook.imagepipeline.producers;

import android.graphics.Bitmap;
import com.facebook.common.internal.ImmutableMap;
import com.facebook.common.internal.Preconditions;
import com.facebook.common.references.CloseableReference;
import com.facebook.imagepipeline.bitmaps.PlatformBitmapFactory;
import com.facebook.imagepipeline.image.CloseableImage;
import com.facebook.imagepipeline.image.CloseableStaticBitmap;
import com.facebook.imagepipeline.request.Postprocessor;
import com.facebook.imagepipeline.request.RepeatedPostprocessor;
import com.facebook.imagepipeline.request.RepeatedPostprocessorRunner;
import java.util.Map;
import java.util.concurrent.Executor;
import javax.annotation.Nullable;

public class PostprocessorProducer implements Producer<CloseableReference<CloseableImage>> {
    public static final String NAME = "PostprocessorProducer";
    static final String POSTPROCESSOR = "Postprocessor";
    /* access modifiers changed from: private */
    public final PlatformBitmapFactory mBitmapFactory;
    /* access modifiers changed from: private */
    public final Executor mExecutor;
    private final Producer<CloseableReference<CloseableImage>> mInputProducer;

    /* JADX WARNING: type inference failed for: r2v0, types: [com.facebook.imagepipeline.producers.Producer<com.facebook.common.references.CloseableReference<com.facebook.imagepipeline.image.CloseableImage>>, java.lang.Object] */
    /* JADX WARNING: Unknown variable types count: 1 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public PostprocessorProducer(com.facebook.imagepipeline.producers.Producer<com.facebook.common.references.CloseableReference<com.facebook.imagepipeline.image.CloseableImage>> r2, com.facebook.imagepipeline.bitmaps.PlatformBitmapFactory r3, java.util.concurrent.Executor r4) {
        /*
            r1 = this;
            r1.<init>()
            java.lang.Object r0 = com.facebook.common.internal.Preconditions.checkNotNull(r2)
            com.facebook.imagepipeline.producers.Producer r0 = (com.facebook.imagepipeline.producers.Producer) r0
            r1.mInputProducer = r0
            r1.mBitmapFactory = r3
            java.lang.Object r0 = com.facebook.common.internal.Preconditions.checkNotNull(r4)
            java.util.concurrent.Executor r0 = (java.util.concurrent.Executor) r0
            r1.mExecutor = r0
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.facebook.imagepipeline.producers.PostprocessorProducer.<init>(com.facebook.imagepipeline.producers.Producer, com.facebook.imagepipeline.bitmaps.PlatformBitmapFactory, java.util.concurrent.Executor):void");
    }

    public void produceResults(Consumer<CloseableReference<CloseableImage>> consumer, ProducerContext context) {
        Consumer<CloseableReference<CloseableImage>> postprocessorConsumer;
        ProducerListener2 listener = context.getProducerListener();
        Postprocessor postprocessor = context.getImageRequest().getPostprocessor();
        PostprocessorConsumer postprocessorConsumer2 = new PostprocessorConsumer(consumer, listener, postprocessor, context);
        if (postprocessor instanceof RepeatedPostprocessor) {
            postprocessorConsumer = new RepeatedPostprocessorConsumer(postprocessorConsumer2, (RepeatedPostprocessor) postprocessor, context);
        } else {
            postprocessorConsumer = new SingleUsePostprocessorConsumer(postprocessorConsumer2);
        }
        this.mInputProducer.produceResults(postprocessorConsumer, context);
    }

    private class PostprocessorConsumer extends DelegatingConsumer<CloseableReference<CloseableImage>, CloseableReference<CloseableImage>> {
        private boolean mIsClosed;
        /* access modifiers changed from: private */
        public boolean mIsDirty = false;
        private boolean mIsPostProcessingRunning = false;
        private final ProducerListener2 mListener;
        private final Postprocessor mPostprocessor;
        private final ProducerContext mProducerContext;
        /* access modifiers changed from: private */
        @Nullable
        public CloseableReference<CloseableImage> mSourceImageRef = null;
        /* access modifiers changed from: private */
        public int mStatus = 0;

        public PostprocessorConsumer(Consumer<CloseableReference<CloseableImage>> consumer, ProducerListener2 listener, Postprocessor postprocessor, ProducerContext producerContext) {
            super(consumer);
            this.mListener = listener;
            this.mPostprocessor = postprocessor;
            this.mProducerContext = producerContext;
            producerContext.addCallbacks(new BaseProducerContextCallbacks(PostprocessorProducer.this) {
                public void onCancellationRequested() {
                    PostprocessorConsumer.this.maybeNotifyOnCancellation();
                }
            });
        }

        /* access modifiers changed from: protected */
        public void onNewResultImpl(CloseableReference<CloseableImage> newResult, int status) {
            if (CloseableReference.isValid(newResult)) {
                updateSourceImageRef(newResult, status);
            } else if (isLast(status)) {
                maybeNotifyOnNewResult((CloseableReference<CloseableImage>) null, status);
            }
        }

        /* access modifiers changed from: protected */
        public void onFailureImpl(Throwable t) {
            maybeNotifyOnFailure(t);
        }

        /* access modifiers changed from: protected */
        public void onCancellationImpl() {
            maybeNotifyOnCancellation();
        }

        /* JADX WARNING: Code restructure failed: missing block: B:10:0x001e, code lost:
            submitPostprocessing();
         */
        /* JADX WARNING: Code restructure failed: missing block: B:18:?, code lost:
            return;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:19:?, code lost:
            return;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:8:0x0019, code lost:
            com.facebook.common.references.CloseableReference.closeSafely((com.facebook.common.references.CloseableReference<?>) r0);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:9:0x001c, code lost:
            if (r1 == false) goto L_?;
         */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        private void updateSourceImageRef(@javax.annotation.Nullable com.facebook.common.references.CloseableReference<com.facebook.imagepipeline.image.CloseableImage> r3, int r4) {
            /*
                r2 = this;
                monitor-enter(r2)
                boolean r0 = r2.mIsClosed     // Catch:{ all -> 0x0022 }
                if (r0 == 0) goto L_0x0007
                monitor-exit(r2)     // Catch:{ all -> 0x0022 }
                return
            L_0x0007:
                com.facebook.common.references.CloseableReference<com.facebook.imagepipeline.image.CloseableImage> r0 = r2.mSourceImageRef     // Catch:{ all -> 0x0022 }
                com.facebook.common.references.CloseableReference r1 = com.facebook.common.references.CloseableReference.cloneOrNull(r3)     // Catch:{ all -> 0x0022 }
                r2.mSourceImageRef = r1     // Catch:{ all -> 0x0022 }
                r2.mStatus = r4     // Catch:{ all -> 0x0022 }
                r1 = 1
                r2.mIsDirty = r1     // Catch:{ all -> 0x0022 }
                boolean r1 = r2.setRunningIfDirtyAndNotRunning()     // Catch:{ all -> 0x0022 }
                monitor-exit(r2)     // Catch:{ all -> 0x0022 }
                com.facebook.common.references.CloseableReference.closeSafely((com.facebook.common.references.CloseableReference<?>) r0)
                if (r1 == 0) goto L_0x0021
                r2.submitPostprocessing()
            L_0x0021:
                return
            L_0x0022:
                r0 = move-exception
                monitor-exit(r2)     // Catch:{ all -> 0x0022 }
                throw r0
            */
            throw new UnsupportedOperationException("Method not decompiled: com.facebook.imagepipeline.producers.PostprocessorProducer.PostprocessorConsumer.updateSourceImageRef(com.facebook.common.references.CloseableReference, int):void");
        }

        private void submitPostprocessing() {
            PostprocessorProducer.this.mExecutor.execute(new Runnable() {
                public void run() {
                    CloseableReference<CloseableImage> closeableImageRef;
                    int status;
                    synchronized (PostprocessorConsumer.this) {
                        closeableImageRef = PostprocessorConsumer.this.mSourceImageRef;
                        status = PostprocessorConsumer.this.mStatus;
                        CloseableReference unused = PostprocessorConsumer.this.mSourceImageRef = null;
                        boolean unused2 = PostprocessorConsumer.this.mIsDirty = false;
                    }
                    if (CloseableReference.isValid(closeableImageRef)) {
                        try {
                            PostprocessorConsumer.this.doPostprocessing(closeableImageRef, status);
                        } finally {
                            CloseableReference.closeSafely((CloseableReference<?>) closeableImageRef);
                        }
                    }
                    PostprocessorConsumer.this.clearRunningAndStartIfDirty();
                }
            });
        }

        /* access modifiers changed from: private */
        public void clearRunningAndStartIfDirty() {
            boolean shouldExecuteAgain;
            synchronized (this) {
                this.mIsPostProcessingRunning = false;
                shouldExecuteAgain = setRunningIfDirtyAndNotRunning();
            }
            if (shouldExecuteAgain) {
                submitPostprocessing();
            }
        }

        private synchronized boolean setRunningIfDirtyAndNotRunning() {
            if (this.mIsClosed || !this.mIsDirty || this.mIsPostProcessingRunning || !CloseableReference.isValid(this.mSourceImageRef)) {
                return false;
            }
            this.mIsPostProcessingRunning = true;
            return true;
        }

        /* access modifiers changed from: private */
        public void doPostprocessing(CloseableReference<CloseableImage> sourceImageRef, int status) {
            Preconditions.checkArgument(CloseableReference.isValid(sourceImageRef));
            if (!shouldPostprocess(sourceImageRef.get())) {
                maybeNotifyOnNewResult(sourceImageRef, status);
                return;
            }
            this.mListener.onProducerStart(this.mProducerContext, PostprocessorProducer.NAME);
            CloseableReference<CloseableImage> destImageRef = null;
            try {
                destImageRef = postprocessInternal(sourceImageRef.get());
                ProducerListener2 producerListener2 = this.mListener;
                ProducerContext producerContext = this.mProducerContext;
                producerListener2.onProducerFinishWithSuccess(producerContext, PostprocessorProducer.NAME, getExtraMap(producerListener2, producerContext, this.mPostprocessor));
                maybeNotifyOnNewResult(destImageRef, status);
            } catch (Exception e) {
                ProducerListener2 producerListener22 = this.mListener;
                ProducerContext producerContext2 = this.mProducerContext;
                producerListener22.onProducerFinishWithFailure(producerContext2, PostprocessorProducer.NAME, e, getExtraMap(producerListener22, producerContext2, this.mPostprocessor));
                maybeNotifyOnFailure(e);
            } finally {
                CloseableReference.closeSafely((CloseableReference<?>) destImageRef);
            }
        }

        @Nullable
        private Map<String, String> getExtraMap(ProducerListener2 listener, ProducerContext producerContext, Postprocessor postprocessor) {
            if (!listener.requiresExtraMap(producerContext, PostprocessorProducer.NAME)) {
                return null;
            }
            return ImmutableMap.m32of(PostprocessorProducer.POSTPROCESSOR, postprocessor.getName());
        }

        private boolean shouldPostprocess(CloseableImage sourceImage) {
            return sourceImage instanceof CloseableStaticBitmap;
        }

        private CloseableReference<CloseableImage> postprocessInternal(CloseableImage sourceImage) {
            CloseableStaticBitmap staticBitmap = (CloseableStaticBitmap) sourceImage;
            CloseableReference<Bitmap> bitmapRef = this.mPostprocessor.process(staticBitmap.getUnderlyingBitmap(), PostprocessorProducer.this.mBitmapFactory);
            try {
                CloseableStaticBitmap closeableStaticBitmap = new CloseableStaticBitmap(bitmapRef, sourceImage.getQualityInfo(), staticBitmap.getRotationAngle(), staticBitmap.getExifOrientation());
                closeableStaticBitmap.setOriginalEncodedImageInfo(staticBitmap.getOriginalEncodedImageInfo());
                return CloseableReference.m124of(closeableStaticBitmap);
            } finally {
                CloseableReference.closeSafely((CloseableReference<?>) bitmapRef);
            }
        }

        private void maybeNotifyOnNewResult(CloseableReference<CloseableImage> newRef, int status) {
            boolean isLast = isLast(status);
            if ((!isLast && !isClosed()) || (isLast && close())) {
                getConsumer().onNewResult(newRef, status);
            }
        }

        private void maybeNotifyOnFailure(Throwable throwable) {
            if (close()) {
                getConsumer().onFailure(throwable);
            }
        }

        /* access modifiers changed from: private */
        public void maybeNotifyOnCancellation() {
            if (close()) {
                getConsumer().onCancellation();
            }
        }

        private synchronized boolean isClosed() {
            return this.mIsClosed;
        }

        private boolean close() {
            synchronized (this) {
                if (this.mIsClosed) {
                    return false;
                }
                CloseableReference<CloseableImage> oldSourceImageRef = this.mSourceImageRef;
                this.mSourceImageRef = null;
                this.mIsClosed = true;
                CloseableReference.closeSafely((CloseableReference<?>) oldSourceImageRef);
                return true;
            }
        }
    }

    class SingleUsePostprocessorConsumer extends DelegatingConsumer<CloseableReference<CloseableImage>, CloseableReference<CloseableImage>> {
        private SingleUsePostprocessorConsumer(PostprocessorConsumer postprocessorConsumer) {
            super(postprocessorConsumer);
        }

        /* access modifiers changed from: protected */
        public void onNewResultImpl(CloseableReference<CloseableImage> newResult, int status) {
            if (!isNotLast(status)) {
                getConsumer().onNewResult(newResult, status);
            }
        }
    }

    class RepeatedPostprocessorConsumer extends DelegatingConsumer<CloseableReference<CloseableImage>, CloseableReference<CloseableImage>> implements RepeatedPostprocessorRunner {
        private boolean mIsClosed;
        @Nullable
        private CloseableReference<CloseableImage> mSourceImageRef;

        private RepeatedPostprocessorConsumer(PostprocessorConsumer postprocessorConsumer, RepeatedPostprocessor repeatedPostprocessor, ProducerContext context) {
            super(postprocessorConsumer);
            this.mIsClosed = false;
            this.mSourceImageRef = null;
            repeatedPostprocessor.setCallback(this);
            context.addCallbacks(new BaseProducerContextCallbacks(PostprocessorProducer.this) {
                public void onCancellationRequested() {
                    if (RepeatedPostprocessorConsumer.this.close()) {
                        RepeatedPostprocessorConsumer.this.getConsumer().onCancellation();
                    }
                }
            });
        }

        /* access modifiers changed from: protected */
        public void onNewResultImpl(CloseableReference<CloseableImage> newResult, int status) {
            if (!isNotLast(status)) {
                setSourceImageRef(newResult);
                updateInternal();
            }
        }

        /* access modifiers changed from: protected */
        public void onFailureImpl(Throwable throwable) {
            if (close()) {
                getConsumer().onFailure(throwable);
            }
        }

        /* access modifiers changed from: protected */
        public void onCancellationImpl() {
            if (close()) {
                getConsumer().onCancellation();
            }
        }

        public synchronized void update() {
            updateInternal();
        }

        private void updateInternal() {
            synchronized (this) {
                if (!this.mIsClosed) {
                    CloseableReference<CloseableImage> sourceImageRef = CloseableReference.cloneOrNull(this.mSourceImageRef);
                    try {
                        getConsumer().onNewResult(sourceImageRef, 0);
                    } finally {
                        CloseableReference.closeSafely((CloseableReference<?>) sourceImageRef);
                    }
                }
            }
        }

        private void setSourceImageRef(CloseableReference<CloseableImage> sourceImageRef) {
            synchronized (this) {
                if (!this.mIsClosed) {
                    CloseableReference<CloseableImage> oldSourceImageRef = this.mSourceImageRef;
                    this.mSourceImageRef = CloseableReference.cloneOrNull(sourceImageRef);
                    CloseableReference.closeSafely((CloseableReference<?>) oldSourceImageRef);
                }
            }
        }

        /* access modifiers changed from: private */
        public boolean close() {
            synchronized (this) {
                if (this.mIsClosed) {
                    return false;
                }
                CloseableReference<CloseableImage> oldSourceImageRef = this.mSourceImageRef;
                this.mSourceImageRef = null;
                this.mIsClosed = true;
                CloseableReference.closeSafely((CloseableReference<?>) oldSourceImageRef);
                return true;
            }
        }
    }
}
