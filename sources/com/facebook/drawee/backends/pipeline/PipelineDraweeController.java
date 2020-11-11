package com.facebook.drawee.backends.pipeline;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import com.facebook.cache.common.CacheKey;
import com.facebook.common.internal.ImmutableList;
import com.facebook.common.internal.Objects;
import com.facebook.common.internal.Preconditions;
import com.facebook.common.internal.Supplier;
import com.facebook.common.logging.FLog;
import com.facebook.common.references.CloseableReference;
import com.facebook.common.time.AwakeTimeSinceBootClock;
import com.facebook.datasource.DataSource;
import com.facebook.drawable.base.DrawableWithCaches;
import com.facebook.drawee.backends.pipeline.debug.DebugOverlayImageOriginColor;
import com.facebook.drawee.backends.pipeline.debug.DebugOverlayImageOriginListener;
import com.facebook.drawee.backends.pipeline.info.ForwardingImageOriginListener;
import com.facebook.drawee.backends.pipeline.info.ImageOriginListener;
import com.facebook.drawee.backends.pipeline.info.ImageOriginUtils;
import com.facebook.drawee.backends.pipeline.info.ImagePerfDataListener;
import com.facebook.drawee.backends.pipeline.info.ImagePerfMonitor;
import com.facebook.drawee.components.DeferredReleaser;
import com.facebook.drawee.controller.AbstractDraweeController;
import com.facebook.drawee.controller.AbstractDraweeControllerBuilder;
import com.facebook.drawee.debug.DebugControllerOverlayDrawable;
import com.facebook.drawee.debug.listener.ImageLoadingTimeControllerListener;
import com.facebook.drawee.drawable.ScaleTypeDrawable;
import com.facebook.drawee.drawable.ScalingUtils;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.interfaces.DraweeHierarchy;
import com.facebook.imagepipeline.cache.MemoryCache;
import com.facebook.imagepipeline.drawable.DrawableFactory;
import com.facebook.imagepipeline.image.CloseableImage;
import com.facebook.imagepipeline.image.ImageInfo;
import com.facebook.imagepipeline.listener.RequestListener;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.systrace.FrescoSystrace;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.Executor;
import javax.annotation.Nullable;

public class PipelineDraweeController extends AbstractDraweeController<CloseableReference<CloseableImage>, ImageInfo> {
    private static final Class<?> TAG = PipelineDraweeController.class;
    private CacheKey mCacheKey;
    @Nullable
    private ImmutableList<DrawableFactory> mCustomDrawableFactories;
    private Supplier<DataSource<CloseableReference<CloseableImage>>> mDataSourceSupplier;
    private DebugOverlayImageOriginListener mDebugOverlayImageOriginListener;
    private final DrawableFactory mDefaultDrawableFactory;
    private boolean mDrawDebugOverlay;
    @Nullable
    private final ImmutableList<DrawableFactory> mGlobalDrawableFactories;
    @Nullable
    private ImageOriginListener mImageOriginListener;
    @Nullable
    private ImagePerfMonitor mImagePerfMonitor;
    @Nullable
    private final MemoryCache<CacheKey, CloseableImage> mMemoryCache;
    @Nullable
    private Set<RequestListener> mRequestListeners;
    private final Resources mResources;

    public PipelineDraweeController(Resources resources, DeferredReleaser deferredReleaser, DrawableFactory animatedDrawableFactory, Executor uiThreadExecutor, @Nullable MemoryCache<CacheKey, CloseableImage> memoryCache, @Nullable ImmutableList<DrawableFactory> globalDrawableFactories) {
        super(deferredReleaser, uiThreadExecutor, (String) null, (Object) null);
        this.mResources = resources;
        this.mDefaultDrawableFactory = new DefaultDrawableFactory(resources, animatedDrawableFactory);
        this.mGlobalDrawableFactories = globalDrawableFactories;
        this.mMemoryCache = memoryCache;
    }

    public void initialize(Supplier<DataSource<CloseableReference<CloseableImage>>> dataSourceSupplier, String id, CacheKey cacheKey, Object callerContext, @Nullable ImmutableList<DrawableFactory> customDrawableFactories, @Nullable ImageOriginListener imageOriginListener) {
        if (FrescoSystrace.isTracing()) {
            FrescoSystrace.beginSection("PipelineDraweeController#initialize");
        }
        super.initialize(id, callerContext);
        init(dataSourceSupplier);
        this.mCacheKey = cacheKey;
        setCustomDrawableFactories(customDrawableFactories);
        clearImageOriginListeners();
        maybeUpdateDebugOverlay((CloseableImage) null);
        addImageOriginListener(imageOriginListener);
        if (FrescoSystrace.isTracing()) {
            FrescoSystrace.endSection();
        }
    }

    /* access modifiers changed from: protected */
    public synchronized void initializePerformanceMonitoring(@Nullable ImagePerfDataListener imagePerfDataListener, AbstractDraweeControllerBuilder<PipelineDraweeControllerBuilder, ImageRequest, CloseableReference<CloseableImage>, ImageInfo> builder) {
        ImagePerfMonitor imagePerfMonitor = this.mImagePerfMonitor;
        if (imagePerfMonitor != null) {
            imagePerfMonitor.reset();
        }
        if (imagePerfDataListener != null) {
            if (this.mImagePerfMonitor == null) {
                this.mImagePerfMonitor = new ImagePerfMonitor(AwakeTimeSinceBootClock.get(), this);
            }
            this.mImagePerfMonitor.addImagePerfDataListener(imagePerfDataListener);
            this.mImagePerfMonitor.setEnabled(true);
            this.mImagePerfMonitor.updateImageRequestData(builder);
        }
    }

    public void setDrawDebugOverlay(boolean drawDebugOverlay) {
        this.mDrawDebugOverlay = drawDebugOverlay;
    }

    public void setCustomDrawableFactories(@Nullable ImmutableList<DrawableFactory> customDrawableFactories) {
        this.mCustomDrawableFactories = customDrawableFactories;
    }

    public synchronized void addRequestListener(RequestListener requestListener) {
        if (this.mRequestListeners == null) {
            this.mRequestListeners = new HashSet();
        }
        this.mRequestListeners.add(requestListener);
    }

    public synchronized void removeRequestListener(RequestListener requestListener) {
        Set<RequestListener> set = this.mRequestListeners;
        if (set != null) {
            set.remove(requestListener);
        }
    }

    public synchronized void addImageOriginListener(ImageOriginListener imageOriginListener) {
        ImageOriginListener imageOriginListener2 = this.mImageOriginListener;
        if (imageOriginListener2 instanceof ForwardingImageOriginListener) {
            ((ForwardingImageOriginListener) imageOriginListener2).addImageOriginListener(imageOriginListener);
        } else if (imageOriginListener2 != null) {
            this.mImageOriginListener = new ForwardingImageOriginListener(this.mImageOriginListener, imageOriginListener);
        } else {
            this.mImageOriginListener = imageOriginListener;
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:12:0x0014, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized void removeImageOriginListener(com.facebook.drawee.backends.pipeline.info.ImageOriginListener r3) {
        /*
            r2 = this;
            monitor-enter(r2)
            com.facebook.drawee.backends.pipeline.info.ImageOriginListener r0 = r2.mImageOriginListener     // Catch:{ all -> 0x0015 }
            boolean r1 = r0 instanceof com.facebook.drawee.backends.pipeline.info.ForwardingImageOriginListener     // Catch:{ all -> 0x0015 }
            if (r1 == 0) goto L_0x000e
            com.facebook.drawee.backends.pipeline.info.ForwardingImageOriginListener r0 = (com.facebook.drawee.backends.pipeline.info.ForwardingImageOriginListener) r0     // Catch:{ all -> 0x0015 }
            r0.removeImageOriginListener(r3)     // Catch:{ all -> 0x0015 }
            monitor-exit(r2)
            return
        L_0x000e:
            if (r0 != r3) goto L_0x0013
            r0 = 0
            r2.mImageOriginListener = r0     // Catch:{ all -> 0x0015 }
        L_0x0013:
            monitor-exit(r2)
            return
        L_0x0015:
            r3 = move-exception
            monitor-exit(r2)
            throw r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.facebook.drawee.backends.pipeline.PipelineDraweeController.removeImageOriginListener(com.facebook.drawee.backends.pipeline.info.ImageOriginListener):void");
    }

    /* access modifiers changed from: protected */
    public void clearImageOriginListeners() {
        synchronized (this) {
            this.mImageOriginListener = null;
        }
    }

    private void init(Supplier<DataSource<CloseableReference<CloseableImage>>> dataSourceSupplier) {
        this.mDataSourceSupplier = dataSourceSupplier;
        maybeUpdateDebugOverlay((CloseableImage) null);
    }

    /* access modifiers changed from: protected */
    public Resources getResources() {
        return this.mResources;
    }

    /* access modifiers changed from: protected */
    public CacheKey getCacheKey() {
        return this.mCacheKey;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:12:0x0023, code lost:
        return r1;
     */
    @javax.annotation.Nullable
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized com.facebook.imagepipeline.listener.RequestListener getRequestListener() {
        /*
            r4 = this;
            monitor-enter(r4)
            r0 = 0
            com.facebook.drawee.backends.pipeline.info.ImageOriginListener r1 = r4.mImageOriginListener     // Catch:{ all -> 0x0026 }
            if (r1 == 0) goto L_0x0012
            com.facebook.drawee.backends.pipeline.info.ImageOriginRequestListener r1 = new com.facebook.drawee.backends.pipeline.info.ImageOriginRequestListener     // Catch:{ all -> 0x0026 }
            java.lang.String r2 = r4.getId()     // Catch:{ all -> 0x0026 }
            com.facebook.drawee.backends.pipeline.info.ImageOriginListener r3 = r4.mImageOriginListener     // Catch:{ all -> 0x0026 }
            r1.<init>(r2, r3)     // Catch:{ all -> 0x0026 }
            r0 = r1
        L_0x0012:
            java.util.Set<com.facebook.imagepipeline.listener.RequestListener> r1 = r4.mRequestListeners     // Catch:{ all -> 0x0026 }
            if (r1 == 0) goto L_0x0024
            com.facebook.imagepipeline.listener.ForwardingRequestListener r1 = new com.facebook.imagepipeline.listener.ForwardingRequestListener     // Catch:{ all -> 0x0026 }
            java.util.Set<com.facebook.imagepipeline.listener.RequestListener> r2 = r4.mRequestListeners     // Catch:{ all -> 0x0026 }
            r1.<init>((java.util.Set<com.facebook.imagepipeline.listener.RequestListener>) r2)     // Catch:{ all -> 0x0026 }
            if (r0 == 0) goto L_0x0022
            r1.addRequestListener(r0)     // Catch:{ all -> 0x0026 }
        L_0x0022:
            monitor-exit(r4)
            return r1
        L_0x0024:
            monitor-exit(r4)
            return r0
        L_0x0026:
            r0 = move-exception
            monitor-exit(r4)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.facebook.drawee.backends.pipeline.PipelineDraweeController.getRequestListener():com.facebook.imagepipeline.listener.RequestListener");
    }

    /* access modifiers changed from: protected */
    public DataSource<CloseableReference<CloseableImage>> getDataSource() {
        if (FrescoSystrace.isTracing()) {
            FrescoSystrace.beginSection("PipelineDraweeController#getDataSource");
        }
        if (FLog.isLoggable(2)) {
            FLog.m81v(TAG, "controller %x: getDataSource", (Object) Integer.valueOf(System.identityHashCode(this)));
        }
        DataSource<CloseableReference<CloseableImage>> result = this.mDataSourceSupplier.get();
        if (FrescoSystrace.isTracing()) {
            FrescoSystrace.endSection();
        }
        return result;
    }

    /* access modifiers changed from: protected */
    public Drawable createDrawable(CloseableReference<CloseableImage> image) {
        try {
            if (FrescoSystrace.isTracing()) {
                FrescoSystrace.beginSection("PipelineDraweeController#createDrawable");
            }
            Preconditions.checkState(CloseableReference.isValid(image));
            CloseableImage closeableImage = image.get();
            maybeUpdateDebugOverlay(closeableImage);
            Drawable drawable = maybeCreateDrawableFromFactories(this.mCustomDrawableFactories, closeableImage);
            if (drawable != null) {
                return drawable;
            }
            Drawable drawable2 = maybeCreateDrawableFromFactories(this.mGlobalDrawableFactories, closeableImage);
            if (drawable2 != null) {
                if (FrescoSystrace.isTracing()) {
                    FrescoSystrace.endSection();
                }
                return drawable2;
            }
            Drawable drawable3 = this.mDefaultDrawableFactory.createDrawable(closeableImage);
            if (drawable3 != null) {
                if (FrescoSystrace.isTracing()) {
                    FrescoSystrace.endSection();
                }
                return drawable3;
            }
            throw new UnsupportedOperationException("Unrecognized image class: " + closeableImage);
        } finally {
            if (FrescoSystrace.isTracing()) {
                FrescoSystrace.endSection();
            }
        }
    }

    @Nullable
    private Drawable maybeCreateDrawableFromFactories(@Nullable ImmutableList<DrawableFactory> drawableFactories, CloseableImage closeableImage) {
        Drawable drawable;
        if (drawableFactories == null) {
            return null;
        }
        Iterator it = drawableFactories.iterator();
        while (it.hasNext()) {
            DrawableFactory factory = (DrawableFactory) it.next();
            if (factory.supportsImageType(closeableImage) && (drawable = factory.createDrawable(closeableImage)) != null) {
                return drawable;
            }
        }
        return null;
    }

    public void setHierarchy(@Nullable DraweeHierarchy hierarchy) {
        super.setHierarchy(hierarchy);
        maybeUpdateDebugOverlay((CloseableImage) null);
    }

    public boolean isSameImageRequest(@Nullable DraweeController other) {
        CacheKey cacheKey = this.mCacheKey;
        if (cacheKey == null || !(other instanceof PipelineDraweeController)) {
            return false;
        }
        return Objects.equal(cacheKey, ((PipelineDraweeController) other).getCacheKey());
    }

    private void maybeUpdateDebugOverlay(@Nullable CloseableImage image) {
        if (this.mDrawDebugOverlay) {
            if (getControllerOverlay() == null) {
                DebugControllerOverlayDrawable controllerOverlay = new DebugControllerOverlayDrawable();
                ImageLoadingTimeControllerListener overlayImageLoadListener = new ImageLoadingTimeControllerListener(controllerOverlay);
                this.mDebugOverlayImageOriginListener = new DebugOverlayImageOriginListener();
                addControllerListener(overlayImageLoadListener);
                setControllerOverlay(controllerOverlay);
            }
            if (this.mImageOriginListener == null) {
                addImageOriginListener(this.mDebugOverlayImageOriginListener);
            }
            if (getControllerOverlay() instanceof DebugControllerOverlayDrawable) {
                updateDebugOverlay(image, (DebugControllerOverlayDrawable) getControllerOverlay());
            }
        }
    }

    /* access modifiers changed from: protected */
    public void updateDebugOverlay(@Nullable CloseableImage image, DebugControllerOverlayDrawable debugOverlay) {
        debugOverlay.setControllerId(getId());
        DraweeHierarchy draweeHierarchy = getHierarchy();
        ScalingUtils.ScaleType scaleType = null;
        if (draweeHierarchy != null) {
            ScaleTypeDrawable scaleTypeDrawable = ScalingUtils.getActiveScaleTypeDrawable(draweeHierarchy.getTopLevelDrawable());
            scaleType = scaleTypeDrawable != null ? scaleTypeDrawable.getScaleType() : null;
        }
        debugOverlay.setScaleType(scaleType);
        int origin = this.mDebugOverlayImageOriginListener.getImageOrigin();
        debugOverlay.setOrigin(ImageOriginUtils.toString(origin), DebugOverlayImageOriginColor.getImageOriginColor(origin));
        if (image != null) {
            debugOverlay.setDimensions(image.getWidth(), image.getHeight());
            debugOverlay.setImageSize(image.getSizeInBytes());
            return;
        }
        debugOverlay.reset();
    }

    /* access modifiers changed from: protected */
    public ImageInfo getImageInfo(CloseableReference<CloseableImage> image) {
        Preconditions.checkState(CloseableReference.isValid(image));
        return image.get();
    }

    /* access modifiers changed from: protected */
    public int getImageHash(@Nullable CloseableReference<CloseableImage> image) {
        if (image != null) {
            return image.getValueHash();
        }
        return 0;
    }

    /* access modifiers changed from: protected */
    public void releaseImage(@Nullable CloseableReference<CloseableImage> image) {
        CloseableReference.closeSafely((CloseableReference<?>) image);
    }

    /* access modifiers changed from: protected */
    public void releaseDrawable(@Nullable Drawable drawable) {
        if (drawable instanceof DrawableWithCaches) {
            ((DrawableWithCaches) drawable).dropCaches();
        }
    }

    /* access modifiers changed from: protected */
    @Nullable
    public CloseableReference<CloseableImage> getCachedImage() {
        if (FrescoSystrace.isTracing()) {
            FrescoSystrace.beginSection("PipelineDraweeController#getCachedImage");
        }
        try {
            MemoryCache<CacheKey, CloseableImage> memoryCache = this.mMemoryCache;
            if (memoryCache != null) {
                CacheKey cacheKey = this.mCacheKey;
                if (cacheKey != null) {
                    CloseableReference<CloseableImage> closeableImage = memoryCache.get(cacheKey);
                    if (closeableImage == null || closeableImage.get().getQualityInfo().isOfFullQuality()) {
                        if (FrescoSystrace.isTracing()) {
                            FrescoSystrace.endSection();
                        }
                        return closeableImage;
                    }
                    closeableImage.close();
                    if (FrescoSystrace.isTracing()) {
                        FrescoSystrace.endSection();
                    }
                    return null;
                }
            }
            return null;
        } finally {
            if (FrescoSystrace.isTracing()) {
                FrescoSystrace.endSection();
            }
        }
    }

    /* access modifiers changed from: protected */
    public void onImageLoadedFromCacheImmediately(String id, CloseableReference<CloseableImage> cachedImage) {
        super.onImageLoadedFromCacheImmediately(id, cachedImage);
        synchronized (this) {
            ImageOriginListener imageOriginListener = this.mImageOriginListener;
            if (imageOriginListener != null) {
                imageOriginListener.onImageLoaded(id, 5, true, "PipelineDraweeController");
            }
        }
    }

    /* access modifiers changed from: protected */
    public Supplier<DataSource<CloseableReference<CloseableImage>>> getDataSourceSupplier() {
        return this.mDataSourceSupplier;
    }

    public String toString() {
        return Objects.toStringHelper((Object) this).add("super", (Object) super.toString()).add("dataSourceSupplier", (Object) this.mDataSourceSupplier).toString();
    }
}
