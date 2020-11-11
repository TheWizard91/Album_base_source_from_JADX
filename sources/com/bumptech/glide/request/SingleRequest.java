package com.bumptech.glide.request;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import com.bumptech.glide.GlideContext;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.Engine;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.engine.Resource;
import com.bumptech.glide.load.resource.drawable.DrawableDecoderCompat;
import com.bumptech.glide.request.target.SizeReadyCallback;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.TransitionFactory;
import com.bumptech.glide.util.LogTime;
import com.bumptech.glide.util.Util;
import com.bumptech.glide.util.pool.StateVerifier;
import java.util.List;
import java.util.concurrent.Executor;

public final class SingleRequest<R> implements Request, SizeReadyCallback, ResourceCallback {
    private static final String GLIDE_TAG = "Glide";
    private static final boolean IS_VERBOSE_LOGGABLE = Log.isLoggable(TAG, 2);
    private static final String TAG = "Request";
    private final TransitionFactory<? super R> animationFactory;
    private final Executor callbackExecutor;
    private final Context context;
    private volatile Engine engine;
    private Drawable errorDrawable;
    private Drawable fallbackDrawable;
    private final GlideContext glideContext;
    private int height;
    private boolean isCallingCallbacks;
    private Engine.LoadStatus loadStatus;
    private final Object model;
    private final int overrideHeight;
    private final int overrideWidth;
    private Drawable placeholderDrawable;
    private final Priority priority;
    private final RequestCoordinator requestCoordinator;
    private final List<RequestListener<R>> requestListeners;
    private final Object requestLock;
    private final BaseRequestOptions<?> requestOptions;
    private RuntimeException requestOrigin;
    private Resource<R> resource;
    private long startTime;
    private final StateVerifier stateVerifier;
    private Status status;
    private final String tag;
    private final Target<R> target;
    private final RequestListener<R> targetListener;
    private final Class<R> transcodeClass;
    private int width;

    private enum Status {
        PENDING,
        RUNNING,
        WAITING_FOR_SIZE,
        COMPLETE,
        FAILED,
        CLEARED
    }

    public static <R> SingleRequest<R> obtain(Context context2, GlideContext glideContext2, Object requestLock2, Object model2, Class<R> transcodeClass2, BaseRequestOptions<?> requestOptions2, int overrideWidth2, int overrideHeight2, Priority priority2, Target<R> target2, RequestListener<R> targetListener2, List<RequestListener<R>> requestListeners2, RequestCoordinator requestCoordinator2, Engine engine2, TransitionFactory<? super R> animationFactory2, Executor callbackExecutor2) {
        return new SingleRequest(context2, glideContext2, requestLock2, model2, transcodeClass2, requestOptions2, overrideWidth2, overrideHeight2, priority2, target2, targetListener2, requestListeners2, requestCoordinator2, engine2, animationFactory2, callbackExecutor2);
    }

    private SingleRequest(Context context2, GlideContext glideContext2, Object requestLock2, Object model2, Class<R> transcodeClass2, BaseRequestOptions<?> requestOptions2, int overrideWidth2, int overrideHeight2, Priority priority2, Target<R> target2, RequestListener<R> targetListener2, List<RequestListener<R>> requestListeners2, RequestCoordinator requestCoordinator2, Engine engine2, TransitionFactory<? super R> animationFactory2, Executor callbackExecutor2) {
        this.tag = IS_VERBOSE_LOGGABLE ? String.valueOf(super.hashCode()) : null;
        this.stateVerifier = StateVerifier.newInstance();
        this.requestLock = requestLock2;
        this.context = context2;
        this.glideContext = glideContext2;
        this.model = model2;
        this.transcodeClass = transcodeClass2;
        this.requestOptions = requestOptions2;
        this.overrideWidth = overrideWidth2;
        this.overrideHeight = overrideHeight2;
        this.priority = priority2;
        this.target = target2;
        this.targetListener = targetListener2;
        this.requestListeners = requestListeners2;
        this.requestCoordinator = requestCoordinator2;
        this.engine = engine2;
        this.animationFactory = animationFactory2;
        this.callbackExecutor = callbackExecutor2;
        this.status = Status.PENDING;
        if (this.requestOrigin == null && glideContext2.isLoggingRequestOriginsEnabled()) {
            this.requestOrigin = new RuntimeException("Glide request origin trace");
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:37:0x00a8, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void begin() {
        /*
            r4 = this;
            java.lang.Object r0 = r4.requestLock
            monitor-enter(r0)
            r4.assertNotCallingCallbacks()     // Catch:{ all -> 0x00b1 }
            com.bumptech.glide.util.pool.StateVerifier r1 = r4.stateVerifier     // Catch:{ all -> 0x00b1 }
            r1.throwIfRecycled()     // Catch:{ all -> 0x00b1 }
            long r1 = com.bumptech.glide.util.LogTime.getLogTime()     // Catch:{ all -> 0x00b1 }
            r4.startTime = r1     // Catch:{ all -> 0x00b1 }
            java.lang.Object r1 = r4.model     // Catch:{ all -> 0x00b1 }
            if (r1 != 0) goto L_0x003c
            int r1 = r4.overrideWidth     // Catch:{ all -> 0x00b1 }
            int r2 = r4.overrideHeight     // Catch:{ all -> 0x00b1 }
            boolean r1 = com.bumptech.glide.util.Util.isValidDimensions(r1, r2)     // Catch:{ all -> 0x00b1 }
            if (r1 == 0) goto L_0x0027
            int r1 = r4.overrideWidth     // Catch:{ all -> 0x00b1 }
            r4.width = r1     // Catch:{ all -> 0x00b1 }
            int r1 = r4.overrideHeight     // Catch:{ all -> 0x00b1 }
            r4.height = r1     // Catch:{ all -> 0x00b1 }
        L_0x0027:
            android.graphics.drawable.Drawable r1 = r4.getFallbackDrawable()     // Catch:{ all -> 0x00b1 }
            if (r1 != 0) goto L_0x002f
            r1 = 5
            goto L_0x0030
        L_0x002f:
            r1 = 3
        L_0x0030:
            com.bumptech.glide.load.engine.GlideException r2 = new com.bumptech.glide.load.engine.GlideException     // Catch:{ all -> 0x00b1 }
            java.lang.String r3 = "Received null model"
            r2.<init>(r3)     // Catch:{ all -> 0x00b1 }
            r4.onLoadFailed(r2, r1)     // Catch:{ all -> 0x00b1 }
            monitor-exit(r0)     // Catch:{ all -> 0x00b1 }
            return
        L_0x003c:
            com.bumptech.glide.request.SingleRequest$Status r1 = r4.status     // Catch:{ all -> 0x00b1 }
            com.bumptech.glide.request.SingleRequest$Status r2 = com.bumptech.glide.request.SingleRequest.Status.RUNNING     // Catch:{ all -> 0x00b1 }
            if (r1 == r2) goto L_0x00a9
            com.bumptech.glide.request.SingleRequest$Status r1 = r4.status     // Catch:{ all -> 0x00b1 }
            com.bumptech.glide.request.SingleRequest$Status r2 = com.bumptech.glide.request.SingleRequest.Status.COMPLETE     // Catch:{ all -> 0x00b1 }
            if (r1 != r2) goto L_0x0051
            com.bumptech.glide.load.engine.Resource<R> r1 = r4.resource     // Catch:{ all -> 0x00b1 }
            com.bumptech.glide.load.DataSource r2 = com.bumptech.glide.load.DataSource.MEMORY_CACHE     // Catch:{ all -> 0x00b1 }
            r4.onResourceReady(r1, r2)     // Catch:{ all -> 0x00b1 }
            monitor-exit(r0)     // Catch:{ all -> 0x00b1 }
            return
        L_0x0051:
            com.bumptech.glide.request.SingleRequest$Status r1 = com.bumptech.glide.request.SingleRequest.Status.WAITING_FOR_SIZE     // Catch:{ all -> 0x00b1 }
            r4.status = r1     // Catch:{ all -> 0x00b1 }
            int r1 = r4.overrideWidth     // Catch:{ all -> 0x00b1 }
            int r2 = r4.overrideHeight     // Catch:{ all -> 0x00b1 }
            boolean r1 = com.bumptech.glide.util.Util.isValidDimensions(r1, r2)     // Catch:{ all -> 0x00b1 }
            if (r1 == 0) goto L_0x0067
            int r1 = r4.overrideWidth     // Catch:{ all -> 0x00b1 }
            int r2 = r4.overrideHeight     // Catch:{ all -> 0x00b1 }
            r4.onSizeReady(r1, r2)     // Catch:{ all -> 0x00b1 }
            goto L_0x006c
        L_0x0067:
            com.bumptech.glide.request.target.Target<R> r1 = r4.target     // Catch:{ all -> 0x00b1 }
            r1.getSize(r4)     // Catch:{ all -> 0x00b1 }
        L_0x006c:
            com.bumptech.glide.request.SingleRequest$Status r1 = r4.status     // Catch:{ all -> 0x00b1 }
            com.bumptech.glide.request.SingleRequest$Status r2 = com.bumptech.glide.request.SingleRequest.Status.RUNNING     // Catch:{ all -> 0x00b1 }
            if (r1 == r2) goto L_0x0078
            com.bumptech.glide.request.SingleRequest$Status r1 = r4.status     // Catch:{ all -> 0x00b1 }
            com.bumptech.glide.request.SingleRequest$Status r2 = com.bumptech.glide.request.SingleRequest.Status.WAITING_FOR_SIZE     // Catch:{ all -> 0x00b1 }
            if (r1 != r2) goto L_0x0087
        L_0x0078:
            boolean r1 = r4.canNotifyStatusChanged()     // Catch:{ all -> 0x00b1 }
            if (r1 == 0) goto L_0x0087
            com.bumptech.glide.request.target.Target<R> r1 = r4.target     // Catch:{ all -> 0x00b1 }
            android.graphics.drawable.Drawable r2 = r4.getPlaceholderDrawable()     // Catch:{ all -> 0x00b1 }
            r1.onLoadStarted(r2)     // Catch:{ all -> 0x00b1 }
        L_0x0087:
            boolean r1 = IS_VERBOSE_LOGGABLE     // Catch:{ all -> 0x00b1 }
            if (r1 == 0) goto L_0x00a7
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch:{ all -> 0x00b1 }
            r1.<init>()     // Catch:{ all -> 0x00b1 }
            java.lang.String r2 = "finished run method in "
            java.lang.StringBuilder r1 = r1.append(r2)     // Catch:{ all -> 0x00b1 }
            long r2 = r4.startTime     // Catch:{ all -> 0x00b1 }
            double r2 = com.bumptech.glide.util.LogTime.getElapsedMillis(r2)     // Catch:{ all -> 0x00b1 }
            java.lang.StringBuilder r1 = r1.append(r2)     // Catch:{ all -> 0x00b1 }
            java.lang.String r1 = r1.toString()     // Catch:{ all -> 0x00b1 }
            r4.logV(r1)     // Catch:{ all -> 0x00b1 }
        L_0x00a7:
            monitor-exit(r0)     // Catch:{ all -> 0x00b1 }
            return
        L_0x00a9:
            java.lang.IllegalArgumentException r1 = new java.lang.IllegalArgumentException     // Catch:{ all -> 0x00b1 }
            java.lang.String r2 = "Cannot restart a running request"
            r1.<init>(r2)     // Catch:{ all -> 0x00b1 }
            throw r1     // Catch:{ all -> 0x00b1 }
        L_0x00b1:
            r1 = move-exception
            monitor-exit(r0)     // Catch:{ all -> 0x00b1 }
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.bumptech.glide.request.SingleRequest.begin():void");
    }

    private void cancel() {
        assertNotCallingCallbacks();
        this.stateVerifier.throwIfRecycled();
        this.target.removeCallback(this);
        Engine.LoadStatus loadStatus2 = this.loadStatus;
        if (loadStatus2 != null) {
            loadStatus2.cancel();
            this.loadStatus = null;
        }
    }

    private void assertNotCallingCallbacks() {
        if (this.isCallingCallbacks) {
            throw new IllegalStateException("You can't start or clear loads in RequestListener or Target callbacks. If you're trying to start a fallback request when a load fails, use RequestBuilder#error(RequestBuilder). Otherwise consider posting your into() or clear() calls to the main thread using a Handler instead.");
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:15:0x0033, code lost:
        if (r0 == null) goto L_?;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:16:0x0035, code lost:
        r4.engine.release(r0);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:24:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:25:?, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void clear() {
        /*
            r4 = this;
            r0 = 0
            java.lang.Object r1 = r4.requestLock
            monitor-enter(r1)
            r4.assertNotCallingCallbacks()     // Catch:{ all -> 0x003b }
            com.bumptech.glide.util.pool.StateVerifier r2 = r4.stateVerifier     // Catch:{ all -> 0x003b }
            r2.throwIfRecycled()     // Catch:{ all -> 0x003b }
            com.bumptech.glide.request.SingleRequest$Status r2 = r4.status     // Catch:{ all -> 0x003b }
            com.bumptech.glide.request.SingleRequest$Status r3 = com.bumptech.glide.request.SingleRequest.Status.CLEARED     // Catch:{ all -> 0x003b }
            if (r2 != r3) goto L_0x0014
            monitor-exit(r1)     // Catch:{ all -> 0x003b }
            return
        L_0x0014:
            r4.cancel()     // Catch:{ all -> 0x003b }
            com.bumptech.glide.load.engine.Resource<R> r2 = r4.resource     // Catch:{ all -> 0x003b }
            if (r2 == 0) goto L_0x001f
            r0 = r2
            r2 = 0
            r4.resource = r2     // Catch:{ all -> 0x003b }
        L_0x001f:
            boolean r2 = r4.canNotifyCleared()     // Catch:{ all -> 0x003b }
            if (r2 == 0) goto L_0x002e
            com.bumptech.glide.request.target.Target<R> r2 = r4.target     // Catch:{ all -> 0x003b }
            android.graphics.drawable.Drawable r3 = r4.getPlaceholderDrawable()     // Catch:{ all -> 0x003b }
            r2.onLoadCleared(r3)     // Catch:{ all -> 0x003b }
        L_0x002e:
            com.bumptech.glide.request.SingleRequest$Status r2 = com.bumptech.glide.request.SingleRequest.Status.CLEARED     // Catch:{ all -> 0x003b }
            r4.status = r2     // Catch:{ all -> 0x003b }
            monitor-exit(r1)     // Catch:{ all -> 0x003b }
            if (r0 == 0) goto L_0x003a
            com.bumptech.glide.load.engine.Engine r1 = r4.engine
            r1.release(r0)
        L_0x003a:
            return
        L_0x003b:
            r2 = move-exception
            monitor-exit(r1)     // Catch:{ all -> 0x003b }
            throw r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.bumptech.glide.request.SingleRequest.clear():void");
    }

    public void pause() {
        synchronized (this.requestLock) {
            if (isRunning()) {
                clear();
            }
        }
    }

    public boolean isRunning() {
        boolean z;
        synchronized (this.requestLock) {
            if (this.status != Status.RUNNING) {
                if (this.status != Status.WAITING_FOR_SIZE) {
                    z = false;
                }
            }
            z = true;
        }
        return z;
    }

    public boolean isComplete() {
        boolean z;
        synchronized (this.requestLock) {
            z = this.status == Status.COMPLETE;
        }
        return z;
    }

    public boolean isCleared() {
        boolean z;
        synchronized (this.requestLock) {
            z = this.status == Status.CLEARED;
        }
        return z;
    }

    public boolean isAnyResourceSet() {
        boolean z;
        synchronized (this.requestLock) {
            z = this.status == Status.COMPLETE;
        }
        return z;
    }

    private Drawable getErrorDrawable() {
        if (this.errorDrawable == null) {
            Drawable errorPlaceholder = this.requestOptions.getErrorPlaceholder();
            this.errorDrawable = errorPlaceholder;
            if (errorPlaceholder == null && this.requestOptions.getErrorId() > 0) {
                this.errorDrawable = loadDrawable(this.requestOptions.getErrorId());
            }
        }
        return this.errorDrawable;
    }

    private Drawable getPlaceholderDrawable() {
        if (this.placeholderDrawable == null) {
            Drawable placeholderDrawable2 = this.requestOptions.getPlaceholderDrawable();
            this.placeholderDrawable = placeholderDrawable2;
            if (placeholderDrawable2 == null && this.requestOptions.getPlaceholderId() > 0) {
                this.placeholderDrawable = loadDrawable(this.requestOptions.getPlaceholderId());
            }
        }
        return this.placeholderDrawable;
    }

    private Drawable getFallbackDrawable() {
        if (this.fallbackDrawable == null) {
            Drawable fallbackDrawable2 = this.requestOptions.getFallbackDrawable();
            this.fallbackDrawable = fallbackDrawable2;
            if (fallbackDrawable2 == null && this.requestOptions.getFallbackId() > 0) {
                this.fallbackDrawable = loadDrawable(this.requestOptions.getFallbackId());
            }
        }
        return this.fallbackDrawable;
    }

    private Drawable loadDrawable(int resourceId) {
        return DrawableDecoderCompat.getDrawable((Context) this.glideContext, resourceId, this.requestOptions.getTheme() != null ? this.requestOptions.getTheme() : this.context.getTheme());
    }

    private void setErrorPlaceholder() {
        if (canNotifyStatusChanged()) {
            Drawable error = null;
            if (this.model == null) {
                error = getFallbackDrawable();
            }
            if (error == null) {
                error = getErrorDrawable();
            }
            if (error == null) {
                error = getPlaceholderDrawable();
            }
            this.target.onLoadFailed(error);
        }
    }

    public void onSizeReady(int width2, int height2) {
        Object obj;
        this.stateVerifier.throwIfRecycled();
        Object obj2 = this.requestLock;
        synchronized (obj2) {
            try {
                boolean z = IS_VERBOSE_LOGGABLE;
                if (z) {
                    logV("Got onSizeReady in " + LogTime.getElapsedMillis(this.startTime));
                }
                if (this.status == Status.WAITING_FOR_SIZE) {
                    this.status = Status.RUNNING;
                    float sizeMultiplier = this.requestOptions.getSizeMultiplier();
                    this.width = maybeApplySizeMultiplier(width2, sizeMultiplier);
                    this.height = maybeApplySizeMultiplier(height2, sizeMultiplier);
                    if (z) {
                        logV("finished setup for calling load in " + LogTime.getElapsedMillis(this.startTime));
                    }
                    float sizeMultiplier2 = sizeMultiplier;
                    boolean z2 = z;
                    float f = sizeMultiplier2;
                    obj = obj2;
                    try {
                        this.loadStatus = this.engine.load(this.glideContext, this.model, this.requestOptions.getSignature(), this.width, this.height, this.requestOptions.getResourceClass(), this.transcodeClass, this.priority, this.requestOptions.getDiskCacheStrategy(), this.requestOptions.getTransformations(), this.requestOptions.isTransformationRequired(), this.requestOptions.isScaleOnlyOrNoTransform(), this.requestOptions.getOptions(), this.requestOptions.isMemoryCacheable(), this.requestOptions.getUseUnlimitedSourceGeneratorsPool(), this.requestOptions.getUseAnimationPool(), this.requestOptions.getOnlyRetrieveFromCache(), this, this.callbackExecutor);
                        if (this.status != Status.RUNNING) {
                            this.loadStatus = null;
                        }
                        if (z2) {
                            logV("finished onSizeReady in " + LogTime.getElapsedMillis(this.startTime));
                        }
                    } catch (Throwable th) {
                        th = th;
                        throw th;
                    }
                }
            } catch (Throwable th2) {
                th = th2;
                obj = obj2;
                throw th;
            }
        }
    }

    private static int maybeApplySizeMultiplier(int size, float sizeMultiplier) {
        return size == Integer.MIN_VALUE ? size : Math.round(((float) size) * sizeMultiplier);
    }

    private boolean canSetResource() {
        RequestCoordinator requestCoordinator2 = this.requestCoordinator;
        return requestCoordinator2 == null || requestCoordinator2.canSetImage(this);
    }

    private boolean canNotifyCleared() {
        RequestCoordinator requestCoordinator2 = this.requestCoordinator;
        return requestCoordinator2 == null || requestCoordinator2.canNotifyCleared(this);
    }

    private boolean canNotifyStatusChanged() {
        RequestCoordinator requestCoordinator2 = this.requestCoordinator;
        return requestCoordinator2 == null || requestCoordinator2.canNotifyStatusChanged(this);
    }

    private boolean isFirstReadyResource() {
        RequestCoordinator requestCoordinator2 = this.requestCoordinator;
        return requestCoordinator2 == null || !requestCoordinator2.getRoot().isAnyResourceSet();
    }

    private void notifyLoadSuccess() {
        RequestCoordinator requestCoordinator2 = this.requestCoordinator;
        if (requestCoordinator2 != null) {
            requestCoordinator2.onRequestSuccess(this);
        }
    }

    private void notifyLoadFailed() {
        RequestCoordinator requestCoordinator2 = this.requestCoordinator;
        if (requestCoordinator2 != null) {
            requestCoordinator2.onRequestFailed(this);
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:10:0x0032, code lost:
        if (0 == 0) goto L_?;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:11:0x0034, code lost:
        r6.engine.release((com.bumptech.glide.load.engine.Resource<?>) null);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:22:0x005b, code lost:
        if (r0 == null) goto L_?;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:23:0x005d, code lost:
        r6.engine.release(r0);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:27:0x0067, code lost:
        if (0 == 0) goto L_?;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:28:0x0069, code lost:
        r6.engine.release((com.bumptech.glide.load.engine.Resource<?>) null);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:41:0x00cb, code lost:
        if (r0 == null) goto L_?;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:42:0x00cd, code lost:
        r6.engine.release(r0);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:55:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:56:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:57:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:58:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:59:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:60:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:61:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:62:?, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void onResourceReady(com.bumptech.glide.load.engine.Resource<?> r7, com.bumptech.glide.load.DataSource r8) {
        /*
            r6 = this;
            com.bumptech.glide.util.pool.StateVerifier r0 = r6.stateVerifier
            r0.throwIfRecycled()
            r0 = 0
            java.lang.Object r1 = r6.requestLock     // Catch:{ all -> 0x00d6 }
            monitor-enter(r1)     // Catch:{ all -> 0x00d6 }
            r2 = 0
            r6.loadStatus = r2     // Catch:{ all -> 0x00d3 }
            if (r7 != 0) goto L_0x003a
            com.bumptech.glide.load.engine.GlideException r2 = new com.bumptech.glide.load.engine.GlideException     // Catch:{ all -> 0x00d3 }
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ all -> 0x00d3 }
            r3.<init>()     // Catch:{ all -> 0x00d3 }
            java.lang.String r4 = "Expected to receive a Resource<R> with an object of "
            java.lang.StringBuilder r3 = r3.append(r4)     // Catch:{ all -> 0x00d3 }
            java.lang.Class<R> r4 = r6.transcodeClass     // Catch:{ all -> 0x00d3 }
            java.lang.StringBuilder r3 = r3.append(r4)     // Catch:{ all -> 0x00d3 }
            java.lang.String r4 = " inside, but instead got null."
            java.lang.StringBuilder r3 = r3.append(r4)     // Catch:{ all -> 0x00d3 }
            java.lang.String r3 = r3.toString()     // Catch:{ all -> 0x00d3 }
            r2.<init>(r3)     // Catch:{ all -> 0x00d3 }
            r6.onLoadFailed(r2)     // Catch:{ all -> 0x00d3 }
            monitor-exit(r1)     // Catch:{ all -> 0x00d3 }
            if (r0 == 0) goto L_0x0039
            com.bumptech.glide.load.engine.Engine r1 = r6.engine
            r1.release(r0)
        L_0x0039:
            return
        L_0x003a:
            java.lang.Object r3 = r7.get()     // Catch:{ all -> 0x00d3 }
            if (r3 == 0) goto L_0x006f
            java.lang.Class<R> r4 = r6.transcodeClass     // Catch:{ all -> 0x00d3 }
            java.lang.Class r5 = r3.getClass()     // Catch:{ all -> 0x00d3 }
            boolean r4 = r4.isAssignableFrom(r5)     // Catch:{ all -> 0x00d3 }
            if (r4 != 0) goto L_0x004d
            goto L_0x006f
        L_0x004d:
            boolean r4 = r6.canSetResource()     // Catch:{ all -> 0x00d3 }
            if (r4 != 0) goto L_0x0063
            r0 = r7
            r6.resource = r2     // Catch:{ all -> 0x00d3 }
            com.bumptech.glide.request.SingleRequest$Status r2 = com.bumptech.glide.request.SingleRequest.Status.COMPLETE     // Catch:{ all -> 0x00d3 }
            r6.status = r2     // Catch:{ all -> 0x00d3 }
            monitor-exit(r1)     // Catch:{ all -> 0x00d3 }
            if (r0 == 0) goto L_0x0062
            com.bumptech.glide.load.engine.Engine r1 = r6.engine
            r1.release(r0)
        L_0x0062:
            return
        L_0x0063:
            r6.onResourceReady(r7, r3, r8)     // Catch:{ all -> 0x00d3 }
            monitor-exit(r1)     // Catch:{ all -> 0x00d3 }
            if (r0 == 0) goto L_0x006e
            com.bumptech.glide.load.engine.Engine r1 = r6.engine
            r1.release(r0)
        L_0x006e:
            return
        L_0x006f:
            r0 = r7
            r6.resource = r2     // Catch:{ all -> 0x00d3 }
            com.bumptech.glide.load.engine.GlideException r2 = new com.bumptech.glide.load.engine.GlideException     // Catch:{ all -> 0x00d3 }
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ all -> 0x00d3 }
            r4.<init>()     // Catch:{ all -> 0x00d3 }
            java.lang.String r5 = "Expected to receive an object of "
            java.lang.StringBuilder r4 = r4.append(r5)     // Catch:{ all -> 0x00d3 }
            java.lang.Class<R> r5 = r6.transcodeClass     // Catch:{ all -> 0x00d3 }
            java.lang.StringBuilder r4 = r4.append(r5)     // Catch:{ all -> 0x00d3 }
            java.lang.String r5 = " but instead got "
            java.lang.StringBuilder r4 = r4.append(r5)     // Catch:{ all -> 0x00d3 }
            if (r3 == 0) goto L_0x0092
            java.lang.Class r5 = r3.getClass()     // Catch:{ all -> 0x00d3 }
            goto L_0x0094
        L_0x0092:
            java.lang.String r5 = ""
        L_0x0094:
            java.lang.StringBuilder r4 = r4.append(r5)     // Catch:{ all -> 0x00d3 }
            java.lang.String r5 = "{"
            java.lang.StringBuilder r4 = r4.append(r5)     // Catch:{ all -> 0x00d3 }
            java.lang.StringBuilder r4 = r4.append(r3)     // Catch:{ all -> 0x00d3 }
            java.lang.String r5 = "} inside Resource{"
            java.lang.StringBuilder r4 = r4.append(r5)     // Catch:{ all -> 0x00d3 }
            java.lang.StringBuilder r4 = r4.append(r7)     // Catch:{ all -> 0x00d3 }
            java.lang.String r5 = "}."
            java.lang.StringBuilder r4 = r4.append(r5)     // Catch:{ all -> 0x00d3 }
            if (r3 == 0) goto L_0x00ba
            java.lang.String r5 = ""
            goto L_0x00bc
        L_0x00ba:
            java.lang.String r5 = " To indicate failure return a null Resource object, rather than a Resource object containing null data."
        L_0x00bc:
            java.lang.StringBuilder r4 = r4.append(r5)     // Catch:{ all -> 0x00d3 }
            java.lang.String r4 = r4.toString()     // Catch:{ all -> 0x00d3 }
            r2.<init>(r4)     // Catch:{ all -> 0x00d3 }
            r6.onLoadFailed(r2)     // Catch:{ all -> 0x00d3 }
            monitor-exit(r1)     // Catch:{ all -> 0x00d3 }
            if (r0 == 0) goto L_0x00d2
            com.bumptech.glide.load.engine.Engine r1 = r6.engine
            r1.release(r0)
        L_0x00d2:
            return
        L_0x00d3:
            r2 = move-exception
            monitor-exit(r1)     // Catch:{ all -> 0x00d3 }
            throw r2     // Catch:{ all -> 0x00d6 }
        L_0x00d6:
            r1 = move-exception
            if (r0 == 0) goto L_0x00de
            com.bumptech.glide.load.engine.Engine r2 = r6.engine
            r2.release(r0)
        L_0x00de:
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.bumptech.glide.request.SingleRequest.onResourceReady(com.bumptech.glide.load.engine.Resource, com.bumptech.glide.load.DataSource):void");
    }

    /* JADX INFO: finally extract failed */
    /* JADX WARNING: Removed duplicated region for block: B:20:0x00b7 A[Catch:{ all -> 0x00c9 }] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void onResourceReady(com.bumptech.glide.load.engine.Resource<R> r12, R r13, com.bumptech.glide.load.DataSource r14) {
        /*
            r11 = this;
            boolean r6 = r11.isFirstReadyResource()
            com.bumptech.glide.request.SingleRequest$Status r0 = com.bumptech.glide.request.SingleRequest.Status.COMPLETE
            r11.status = r0
            r11.resource = r12
            com.bumptech.glide.GlideContext r0 = r11.glideContext
            int r0 = r0.getLogLevel()
            r1 = 3
            if (r0 > r1) goto L_0x0078
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            java.lang.String r1 = "Finished loading "
            java.lang.StringBuilder r0 = r0.append(r1)
            java.lang.Class r1 = r13.getClass()
            java.lang.String r1 = r1.getSimpleName()
            java.lang.StringBuilder r0 = r0.append(r1)
            java.lang.String r1 = " from "
            java.lang.StringBuilder r0 = r0.append(r1)
            java.lang.StringBuilder r0 = r0.append(r14)
            java.lang.String r1 = " for "
            java.lang.StringBuilder r0 = r0.append(r1)
            java.lang.Object r1 = r11.model
            java.lang.StringBuilder r0 = r0.append(r1)
            java.lang.String r1 = " with size ["
            java.lang.StringBuilder r0 = r0.append(r1)
            int r1 = r11.width
            java.lang.StringBuilder r0 = r0.append(r1)
            java.lang.String r1 = "x"
            java.lang.StringBuilder r0 = r0.append(r1)
            int r1 = r11.height
            java.lang.StringBuilder r0 = r0.append(r1)
            java.lang.String r1 = "] in "
            java.lang.StringBuilder r0 = r0.append(r1)
            long r1 = r11.startTime
            double r1 = com.bumptech.glide.util.LogTime.getElapsedMillis(r1)
            java.lang.StringBuilder r0 = r0.append(r1)
            java.lang.String r1 = " ms"
            java.lang.StringBuilder r0 = r0.append(r1)
            java.lang.String r0 = r0.toString()
            java.lang.String r1 = "Glide"
            android.util.Log.d(r1, r0)
        L_0x0078:
            r7 = 1
            r11.isCallingCallbacks = r7
            r0 = 0
            r8 = 0
            java.util.List<com.bumptech.glide.request.RequestListener<R>> r1 = r11.requestListeners     // Catch:{ all -> 0x00c9 }
            if (r1 == 0) goto L_0x009f
            java.util.Iterator r9 = r1.iterator()     // Catch:{ all -> 0x00c9 }
            r10 = r0
        L_0x0086:
            boolean r0 = r9.hasNext()     // Catch:{ all -> 0x00c9 }
            if (r0 == 0) goto L_0x00a0
            java.lang.Object r0 = r9.next()     // Catch:{ all -> 0x00c9 }
            com.bumptech.glide.request.RequestListener r0 = (com.bumptech.glide.request.RequestListener) r0     // Catch:{ all -> 0x00c9 }
            java.lang.Object r2 = r11.model     // Catch:{ all -> 0x00c9 }
            com.bumptech.glide.request.target.Target<R> r3 = r11.target     // Catch:{ all -> 0x00c9 }
            r1 = r13
            r4 = r14
            r5 = r6
            boolean r1 = r0.onResourceReady(r1, r2, r3, r4, r5)     // Catch:{ all -> 0x00c9 }
            r10 = r10 | r1
            goto L_0x0086
        L_0x009f:
            r10 = r0
        L_0x00a0:
            com.bumptech.glide.request.RequestListener<R> r0 = r11.targetListener     // Catch:{ all -> 0x00c9 }
            if (r0 == 0) goto L_0x00b2
            java.lang.Object r2 = r11.model     // Catch:{ all -> 0x00c9 }
            com.bumptech.glide.request.target.Target<R> r3 = r11.target     // Catch:{ all -> 0x00c9 }
            r1 = r13
            r4 = r14
            r5 = r6
            boolean r0 = r0.onResourceReady(r1, r2, r3, r4, r5)     // Catch:{ all -> 0x00c9 }
            if (r0 == 0) goto L_0x00b2
            goto L_0x00b3
        L_0x00b2:
            r7 = r8
        L_0x00b3:
            r0 = r10 | r7
            if (r0 != 0) goto L_0x00c2
            com.bumptech.glide.request.transition.TransitionFactory<? super R> r1 = r11.animationFactory     // Catch:{ all -> 0x00c9 }
            com.bumptech.glide.request.transition.Transition r1 = r1.build(r14, r6)     // Catch:{ all -> 0x00c9 }
            com.bumptech.glide.request.target.Target<R> r2 = r11.target     // Catch:{ all -> 0x00c9 }
            r2.onResourceReady(r13, r1)     // Catch:{ all -> 0x00c9 }
        L_0x00c2:
            r11.isCallingCallbacks = r8
            r11.notifyLoadSuccess()
            return
        L_0x00c9:
            r0 = move-exception
            r11.isCallingCallbacks = r8
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.bumptech.glide.request.SingleRequest.onResourceReady(com.bumptech.glide.load.engine.Resource, java.lang.Object, com.bumptech.glide.load.DataSource):void");
    }

    public void onLoadFailed(GlideException e) {
        onLoadFailed(e, 5);
    }

    public Object getLock() {
        this.stateVerifier.throwIfRecycled();
        return this.requestLock;
    }

    /* JADX INFO: finally extract failed */
    private void onLoadFailed(GlideException e, int maxLogLevel) {
        this.stateVerifier.throwIfRecycled();
        synchronized (this.requestLock) {
            e.setOrigin(this.requestOrigin);
            int logLevel = this.glideContext.getLogLevel();
            if (logLevel <= maxLogLevel) {
                Log.w(GLIDE_TAG, "Load failed for " + this.model + " with size [" + this.width + "x" + this.height + "]", e);
                if (logLevel <= 4) {
                    e.logRootCauses(GLIDE_TAG);
                }
            }
            this.loadStatus = null;
            this.status = Status.FAILED;
            boolean z = true;
            this.isCallingCallbacks = true;
            boolean anyListenerHandledUpdatingTarget = false;
            try {
                List<RequestListener<R>> list = this.requestListeners;
                if (list != null) {
                    for (RequestListener<R> listener : list) {
                        anyListenerHandledUpdatingTarget |= listener.onLoadFailed(e, this.model, this.target, isFirstReadyResource());
                    }
                }
                RequestListener<R> requestListener = this.targetListener;
                if (requestListener == null || !requestListener.onLoadFailed(e, this.model, this.target, isFirstReadyResource())) {
                    z = false;
                }
                if (!z && !anyListenerHandledUpdatingTarget) {
                    setErrorPlaceholder();
                }
                this.isCallingCallbacks = false;
                notifyLoadFailed();
            } catch (Throwable th) {
                this.isCallingCallbacks = false;
                throw th;
            }
        }
    }

    public boolean isEquivalentTo(Request o) {
        int localOverrideWidth;
        int localOverrideHeight;
        Object localModel;
        Class<R> cls;
        BaseRequestOptions<?> localRequestOptions;
        Priority localPriority;
        int localListenerCount;
        int otherLocalOverrideWidth;
        int otherLocalOverrideHeight;
        Object otherLocalModel;
        Class<R> cls2;
        BaseRequestOptions<?> otherLocalRequestOptions;
        Priority otherLocalPriority;
        int otherLocalListenerCount;
        Request request = o;
        if (!(request instanceof SingleRequest)) {
            return false;
        }
        synchronized (this.requestLock) {
            localOverrideWidth = this.overrideWidth;
            localOverrideHeight = this.overrideHeight;
            localModel = this.model;
            cls = this.transcodeClass;
            localRequestOptions = this.requestOptions;
            localPriority = this.priority;
            List<RequestListener<R>> list = this.requestListeners;
            localListenerCount = list != null ? list.size() : 0;
        }
        SingleRequest<?> other = (SingleRequest) request;
        synchronized (other.requestLock) {
            otherLocalOverrideWidth = other.overrideWidth;
            otherLocalOverrideHeight = other.overrideHeight;
            otherLocalModel = other.model;
            cls2 = other.transcodeClass;
            otherLocalRequestOptions = other.requestOptions;
            otherLocalPriority = other.priority;
            List<RequestListener<R>> list2 = other.requestListeners;
            otherLocalListenerCount = list2 != null ? list2.size() : 0;
        }
        return localOverrideWidth == otherLocalOverrideWidth && localOverrideHeight == otherLocalOverrideHeight && Util.bothModelsNullEquivalentOrEquals(localModel, otherLocalModel) && cls.equals(cls2) && localRequestOptions.equals(otherLocalRequestOptions) && localPriority == otherLocalPriority && localListenerCount == otherLocalListenerCount;
    }

    private void logV(String message) {
        Log.v(TAG, message + " this: " + this.tag);
    }
}
