package com.davemorrissey.labs.subscaleview;

import android.content.Context;
import android.content.res.TypedArray;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewParent;
import androidx.core.internal.view.SupportMenu;
import androidx.exifinterface.media.ExifInterface;
import com.davemorrissey.labs.subscaleview.decoder.CompatDecoderFactory;
import com.davemorrissey.labs.subscaleview.decoder.DecoderFactory;
import com.davemorrissey.labs.subscaleview.decoder.ImageDecoder;
import com.davemorrissey.labs.subscaleview.decoder.ImageRegionDecoder;
import com.davemorrissey.labs.subscaleview.decoder.SkiaImageDecoder;
import com.davemorrissey.labs.subscaleview.decoder.SkiaImageRegionDecoder;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class SubsamplingScaleImageView extends View {
    public static final int EASE_IN_OUT_QUAD = 2;
    public static final int EASE_OUT_QUAD = 1;
    private static final int MESSAGE_LONG_CLICK = 1;
    public static final int ORIENTATION_0 = 0;
    public static final int ORIENTATION_180 = 180;
    public static final int ORIENTATION_270 = 270;
    public static final int ORIENTATION_90 = 90;
    public static final int ORIENTATION_USE_EXIF = -1;
    public static final int ORIGIN_ANIM = 1;
    public static final int ORIGIN_DOUBLE_TAP_ZOOM = 4;
    public static final int ORIGIN_FLING = 3;
    public static final int ORIGIN_TOUCH = 2;
    public static final int PAN_LIMIT_CENTER = 3;
    public static final int PAN_LIMIT_INSIDE = 1;
    public static final int PAN_LIMIT_OUTSIDE = 2;
    public static final int SCALE_TYPE_CENTER_CROP = 2;
    public static final int SCALE_TYPE_CENTER_INSIDE = 1;
    public static final int SCALE_TYPE_CUSTOM = 3;
    public static final int SCALE_TYPE_START = 4;
    /* access modifiers changed from: private */
    public static final String TAG = SubsamplingScaleImageView.class.getSimpleName();
    public static final int TILE_SIZE_AUTO = Integer.MAX_VALUE;
    /* access modifiers changed from: private */
    public static final List<Integer> VALID_EASING_STYLES = Arrays.asList(new Integer[]{2, 1});
    private static final List<Integer> VALID_ORIENTATIONS = Arrays.asList(new Integer[]{0, 90, 180, 270, -1});
    private static final List<Integer> VALID_PAN_LIMITS = Arrays.asList(new Integer[]{1, 2, 3});
    private static final List<Integer> VALID_SCALE_TYPES = Arrays.asList(new Integer[]{2, 1, 3, 4});
    private static final List<Integer> VALID_ZOOM_STYLES = Arrays.asList(new Integer[]{1, 2, 3});
    public static final int ZOOM_FOCUS_CENTER = 2;
    public static final int ZOOM_FOCUS_CENTER_IMMEDIATE = 3;
    public static final int ZOOM_FOCUS_FIXED = 1;
    private static Bitmap.Config preferredBitmapConfig;
    /* access modifiers changed from: private */
    public Anim anim;
    private Bitmap bitmap;
    private DecoderFactory<? extends ImageDecoder> bitmapDecoderFactory;
    private boolean bitmapIsCached;
    private boolean bitmapIsPreview;
    private Paint bitmapPaint;
    private boolean debug;
    private Paint debugLinePaint;
    private Paint debugTextPaint;
    private ImageRegionDecoder decoder;
    /* access modifiers changed from: private */
    public final ReadWriteLock decoderLock;
    private final float density;
    private GestureDetector detector;
    private int doubleTapZoomDuration;
    private float doubleTapZoomScale;
    private int doubleTapZoomStyle;
    private final float[] dstArray;
    private boolean eagerLoadingEnabled;
    private Executor executor;
    private int fullImageSampleSize;
    private final Handler handler;
    private boolean imageLoadedSent;
    private boolean isPanning;
    /* access modifiers changed from: private */
    public boolean isQuickScaling;
    /* access modifiers changed from: private */
    public boolean isZooming;
    private Matrix matrix;
    private float maxScale;
    private int maxTileHeight;
    private int maxTileWidth;
    /* access modifiers changed from: private */
    public int maxTouchCount;
    private float minScale;
    private int minimumScaleType;
    private int minimumTileDpi;
    /* access modifiers changed from: private */
    public OnImageEventListener onImageEventListener;
    /* access modifiers changed from: private */
    public View.OnLongClickListener onLongClickListener;
    private OnStateChangedListener onStateChangedListener;
    private int orientation;
    private Rect pRegion;
    /* access modifiers changed from: private */
    public boolean panEnabled;
    private int panLimit;
    private Float pendingScale;
    /* access modifiers changed from: private */
    public boolean quickScaleEnabled;
    /* access modifiers changed from: private */
    public float quickScaleLastDistance;
    /* access modifiers changed from: private */
    public boolean quickScaleMoved;
    /* access modifiers changed from: private */
    public PointF quickScaleSCenter;
    private final float quickScaleThreshold;
    /* access modifiers changed from: private */
    public PointF quickScaleVLastPoint;
    /* access modifiers changed from: private */
    public PointF quickScaleVStart;
    /* access modifiers changed from: private */
    public boolean readySent;
    private DecoderFactory<? extends ImageRegionDecoder> regionDecoderFactory;
    private int sHeight;
    private int sOrientation;
    private PointF sPendingCenter;
    private RectF sRect;
    /* access modifiers changed from: private */
    public Rect sRegion;
    private PointF sRequestedCenter;
    private int sWidth;
    private ScaleAndTranslate satTemp;
    /* access modifiers changed from: private */
    public float scale;
    /* access modifiers changed from: private */
    public float scaleStart;
    private GestureDetector singleDetector;
    private final float[] srcArray;
    private Paint tileBgPaint;
    private Map<Integer, List<Tile>> tileMap;
    private Uri uri;
    /* access modifiers changed from: private */
    public PointF vCenterStart;
    private float vDistStart;
    /* access modifiers changed from: private */
    public PointF vTranslate;
    private PointF vTranslateBefore;
    /* access modifiers changed from: private */
    public PointF vTranslateStart;
    /* access modifiers changed from: private */
    public boolean zoomEnabled;

    public interface OnAnimationEventListener {
        void onComplete();

        void onInterruptedByNewAnim();

        void onInterruptedByUser();
    }

    public interface OnImageEventListener {
        void onImageLoadError(Exception exc);

        void onImageLoaded();

        void onPreviewLoadError(Exception exc);

        void onPreviewReleased();

        void onReady();

        void onTileLoadError(Exception exc);
    }

    public interface OnStateChangedListener {
        void onCenterChanged(PointF pointF, int i);

        void onScaleChanged(float f, int i);
    }

    public SubsamplingScaleImageView(Context context, AttributeSet attr) {
        super(context, attr);
        int resId;
        String assetName;
        this.orientation = 0;
        this.maxScale = 2.0f;
        this.minScale = minScale();
        this.minimumTileDpi = -1;
        this.panLimit = 1;
        this.minimumScaleType = 1;
        this.maxTileWidth = Integer.MAX_VALUE;
        this.maxTileHeight = Integer.MAX_VALUE;
        this.executor = AsyncTask.THREAD_POOL_EXECUTOR;
        this.eagerLoadingEnabled = true;
        this.panEnabled = true;
        this.zoomEnabled = true;
        this.quickScaleEnabled = true;
        this.doubleTapZoomScale = 1.0f;
        this.doubleTapZoomStyle = 1;
        this.doubleTapZoomDuration = 500;
        this.decoderLock = new ReentrantReadWriteLock(true);
        this.bitmapDecoderFactory = new CompatDecoderFactory(SkiaImageDecoder.class);
        this.regionDecoderFactory = new CompatDecoderFactory(SkiaImageRegionDecoder.class);
        this.srcArray = new float[8];
        this.dstArray = new float[8];
        this.density = getResources().getDisplayMetrics().density;
        setMinimumDpi(160);
        setDoubleTapZoomDpi(160);
        setMinimumTileDpi(320);
        setGestureDetector(context);
        this.handler = new Handler(new Handler.Callback() {
            public boolean handleMessage(Message message) {
                if (message.what == 1 && SubsamplingScaleImageView.this.onLongClickListener != null) {
                    int unused = SubsamplingScaleImageView.this.maxTouchCount = 0;
                    SubsamplingScaleImageView subsamplingScaleImageView = SubsamplingScaleImageView.this;
                    SubsamplingScaleImageView.super.setOnLongClickListener(subsamplingScaleImageView.onLongClickListener);
                    SubsamplingScaleImageView.this.performLongClick();
                    SubsamplingScaleImageView.super.setOnLongClickListener((View.OnLongClickListener) null);
                }
                return true;
            }
        });
        if (attr != null) {
            TypedArray typedAttr = getContext().obtainStyledAttributes(attr, C2281R.styleable.SubsamplingScaleImageView);
            if (typedAttr.hasValue(C2281R.styleable.SubsamplingScaleImageView_assetName) && (assetName = typedAttr.getString(C2281R.styleable.SubsamplingScaleImageView_assetName)) != null && assetName.length() > 0) {
                setImage(ImageSource.asset(assetName).tilingEnabled());
            }
            if (typedAttr.hasValue(C2281R.styleable.SubsamplingScaleImageView_src) && (resId = typedAttr.getResourceId(C2281R.styleable.SubsamplingScaleImageView_src, 0)) > 0) {
                setImage(ImageSource.resource(resId).tilingEnabled());
            }
            if (typedAttr.hasValue(C2281R.styleable.SubsamplingScaleImageView_panEnabled)) {
                setPanEnabled(typedAttr.getBoolean(C2281R.styleable.SubsamplingScaleImageView_panEnabled, true));
            }
            if (typedAttr.hasValue(C2281R.styleable.SubsamplingScaleImageView_zoomEnabled)) {
                setZoomEnabled(typedAttr.getBoolean(C2281R.styleable.SubsamplingScaleImageView_zoomEnabled, true));
            }
            if (typedAttr.hasValue(C2281R.styleable.SubsamplingScaleImageView_quickScaleEnabled)) {
                setQuickScaleEnabled(typedAttr.getBoolean(C2281R.styleable.SubsamplingScaleImageView_quickScaleEnabled, true));
            }
            if (typedAttr.hasValue(C2281R.styleable.SubsamplingScaleImageView_tileBackgroundColor)) {
                setTileBackgroundColor(typedAttr.getColor(C2281R.styleable.SubsamplingScaleImageView_tileBackgroundColor, Color.argb(0, 0, 0, 0)));
            }
            typedAttr.recycle();
        }
        this.quickScaleThreshold = TypedValue.applyDimension(1, 20.0f, context.getResources().getDisplayMetrics());
    }

    public SubsamplingScaleImageView(Context context) {
        this(context, (AttributeSet) null);
    }

    public static Bitmap.Config getPreferredBitmapConfig() {
        return preferredBitmapConfig;
    }

    public static void setPreferredBitmapConfig(Bitmap.Config preferredBitmapConfig2) {
        preferredBitmapConfig = preferredBitmapConfig2;
    }

    public final void setOrientation(int orientation2) {
        if (VALID_ORIENTATIONS.contains(Integer.valueOf(orientation2))) {
            this.orientation = orientation2;
            reset(false);
            invalidate();
            requestLayout();
            return;
        }
        throw new IllegalArgumentException("Invalid orientation: " + orientation2);
    }

    public final void setImage(ImageSource imageSource) {
        setImage(imageSource, (ImageSource) null, (ImageViewState) null);
    }

    public final void setImage(ImageSource imageSource, ImageViewState state) {
        setImage(imageSource, (ImageSource) null, state);
    }

    public final void setImage(ImageSource imageSource, ImageSource previewSource) {
        setImage(imageSource, previewSource, (ImageViewState) null);
    }

    public final void setImage(ImageSource imageSource, ImageSource previewSource, ImageViewState state) {
        if (imageSource != null) {
            reset(true);
            if (state != null) {
                restoreState(state);
            }
            if (previewSource != null) {
                if (imageSource.getBitmap() != null) {
                    throw new IllegalArgumentException("Preview image cannot be used when a bitmap is provided for the main image");
                } else if (imageSource.getSWidth() <= 0 || imageSource.getSHeight() <= 0) {
                    throw new IllegalArgumentException("Preview image cannot be used unless dimensions are provided for the main image");
                } else {
                    this.sWidth = imageSource.getSWidth();
                    this.sHeight = imageSource.getSHeight();
                    this.pRegion = previewSource.getSRegion();
                    if (previewSource.getBitmap() != null) {
                        this.bitmapIsCached = previewSource.isCached();
                        onPreviewLoaded(previewSource.getBitmap());
                    } else {
                        Uri uri2 = previewSource.getUri();
                        if (uri2 == null && previewSource.getResource() != null) {
                            uri2 = Uri.parse("android.resource://" + getContext().getPackageName() + "/" + previewSource.getResource());
                        }
                        execute(new BitmapLoadTask(this, getContext(), this.bitmapDecoderFactory, uri2, true));
                    }
                }
            }
            if (imageSource.getBitmap() != null && imageSource.getSRegion() != null) {
                onImageLoaded(Bitmap.createBitmap(imageSource.getBitmap(), imageSource.getSRegion().left, imageSource.getSRegion().top, imageSource.getSRegion().width(), imageSource.getSRegion().height()), 0, false);
            } else if (imageSource.getBitmap() != null) {
                onImageLoaded(imageSource.getBitmap(), 0, imageSource.isCached());
            } else {
                this.sRegion = imageSource.getSRegion();
                Uri uri3 = imageSource.getUri();
                this.uri = uri3;
                if (uri3 == null && imageSource.getResource() != null) {
                    this.uri = Uri.parse("android.resource://" + getContext().getPackageName() + "/" + imageSource.getResource());
                }
                if (imageSource.getTile() || this.sRegion != null) {
                    execute(new TilesInitTask(this, getContext(), this.regionDecoderFactory, this.uri));
                    return;
                }
                execute(new BitmapLoadTask(this, getContext(), this.bitmapDecoderFactory, this.uri, false));
            }
        } else {
            throw new NullPointerException("imageSource must not be null");
        }
    }

    /* JADX INFO: finally extract failed */
    private void reset(boolean newImage) {
        OnImageEventListener onImageEventListener2;
        debug("reset newImage=" + newImage, new Object[0]);
        this.scale = 0.0f;
        this.scaleStart = 0.0f;
        this.vTranslate = null;
        this.vTranslateStart = null;
        this.vTranslateBefore = null;
        this.pendingScale = Float.valueOf(0.0f);
        this.sPendingCenter = null;
        this.sRequestedCenter = null;
        this.isZooming = false;
        this.isPanning = false;
        this.isQuickScaling = false;
        this.maxTouchCount = 0;
        this.fullImageSampleSize = 0;
        this.vCenterStart = null;
        this.vDistStart = 0.0f;
        this.quickScaleLastDistance = 0.0f;
        this.quickScaleMoved = false;
        this.quickScaleSCenter = null;
        this.quickScaleVLastPoint = null;
        this.quickScaleVStart = null;
        this.anim = null;
        this.satTemp = null;
        this.matrix = null;
        this.sRect = null;
        if (newImage) {
            this.uri = null;
            this.decoderLock.writeLock().lock();
            try {
                ImageRegionDecoder imageRegionDecoder = this.decoder;
                if (imageRegionDecoder != null) {
                    imageRegionDecoder.recycle();
                    this.decoder = null;
                }
                this.decoderLock.writeLock().unlock();
                Bitmap bitmap2 = this.bitmap;
                if (bitmap2 != null && !this.bitmapIsCached) {
                    bitmap2.recycle();
                }
                if (!(this.bitmap == null || !this.bitmapIsCached || (onImageEventListener2 = this.onImageEventListener) == null)) {
                    onImageEventListener2.onPreviewReleased();
                }
                this.sWidth = 0;
                this.sHeight = 0;
                this.sOrientation = 0;
                this.sRegion = null;
                this.pRegion = null;
                this.readySent = false;
                this.imageLoadedSent = false;
                this.bitmap = null;
                this.bitmapIsPreview = false;
                this.bitmapIsCached = false;
            } catch (Throwable th) {
                this.decoderLock.writeLock().unlock();
                throw th;
            }
        }
        Map<Integer, List<Tile>> map = this.tileMap;
        if (map != null) {
            for (Map.Entry<Integer, List<Tile>> tileMapEntry : map.entrySet()) {
                for (Tile tile : tileMapEntry.getValue()) {
                    boolean unused = tile.visible = false;
                    if (tile.bitmap != null) {
                        tile.bitmap.recycle();
                        Bitmap unused2 = tile.bitmap = null;
                    }
                }
            }
            this.tileMap = null;
        }
        setGestureDetector(getContext());
    }

    /* access modifiers changed from: private */
    public void setGestureDetector(final Context context) {
        this.detector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                if (!SubsamplingScaleImageView.this.panEnabled || !SubsamplingScaleImageView.this.readySent || SubsamplingScaleImageView.this.vTranslate == null || e1 == null || e2 == null || ((Math.abs(e1.getX() - e2.getX()) <= 50.0f && Math.abs(e1.getY() - e2.getY()) <= 50.0f) || ((Math.abs(velocityX) <= 500.0f && Math.abs(velocityY) <= 500.0f) || SubsamplingScaleImageView.this.isZooming))) {
                    return super.onFling(e1, e2, velocityX, velocityY);
                }
                PointF vTranslateEnd = new PointF(SubsamplingScaleImageView.this.vTranslate.x + (velocityX * 0.25f), SubsamplingScaleImageView.this.vTranslate.y + (0.25f * velocityY));
                new AnimationBuilder(new PointF((((float) (SubsamplingScaleImageView.this.getWidth() / 2)) - vTranslateEnd.x) / SubsamplingScaleImageView.this.scale, (((float) (SubsamplingScaleImageView.this.getHeight() / 2)) - vTranslateEnd.y) / SubsamplingScaleImageView.this.scale)).withEasing(1).withPanLimited(false).withOrigin(3).start();
                return true;
            }

            public boolean onSingleTapConfirmed(MotionEvent e) {
                SubsamplingScaleImageView.this.performClick();
                return true;
            }

            public boolean onDoubleTap(MotionEvent e) {
                if (!SubsamplingScaleImageView.this.zoomEnabled || !SubsamplingScaleImageView.this.readySent || SubsamplingScaleImageView.this.vTranslate == null) {
                    return super.onDoubleTapEvent(e);
                }
                SubsamplingScaleImageView.this.setGestureDetector(context);
                if (SubsamplingScaleImageView.this.quickScaleEnabled) {
                    PointF unused = SubsamplingScaleImageView.this.vCenterStart = new PointF(e.getX(), e.getY());
                    PointF unused2 = SubsamplingScaleImageView.this.vTranslateStart = new PointF(SubsamplingScaleImageView.this.vTranslate.x, SubsamplingScaleImageView.this.vTranslate.y);
                    SubsamplingScaleImageView subsamplingScaleImageView = SubsamplingScaleImageView.this;
                    float unused3 = subsamplingScaleImageView.scaleStart = subsamplingScaleImageView.scale;
                    boolean unused4 = SubsamplingScaleImageView.this.isQuickScaling = true;
                    boolean unused5 = SubsamplingScaleImageView.this.isZooming = true;
                    float unused6 = SubsamplingScaleImageView.this.quickScaleLastDistance = -1.0f;
                    SubsamplingScaleImageView subsamplingScaleImageView2 = SubsamplingScaleImageView.this;
                    PointF unused7 = subsamplingScaleImageView2.quickScaleSCenter = subsamplingScaleImageView2.viewToSourceCoord(subsamplingScaleImageView2.vCenterStart);
                    PointF unused8 = SubsamplingScaleImageView.this.quickScaleVStart = new PointF(e.getX(), e.getY());
                    PointF unused9 = SubsamplingScaleImageView.this.quickScaleVLastPoint = new PointF(SubsamplingScaleImageView.this.quickScaleSCenter.x, SubsamplingScaleImageView.this.quickScaleSCenter.y);
                    boolean unused10 = SubsamplingScaleImageView.this.quickScaleMoved = false;
                    return false;
                }
                SubsamplingScaleImageView subsamplingScaleImageView3 = SubsamplingScaleImageView.this;
                subsamplingScaleImageView3.doubleTapZoom(subsamplingScaleImageView3.viewToSourceCoord(new PointF(e.getX(), e.getY())), new PointF(e.getX(), e.getY()));
                return true;
            }
        });
        this.singleDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
            public boolean onSingleTapConfirmed(MotionEvent e) {
                SubsamplingScaleImageView.this.performClick();
                return true;
            }
        });
    }

    /* access modifiers changed from: protected */
    public void onSizeChanged(int w, int h, int oldw, int oldh) {
        debug("onSizeChanged %dx%d -> %dx%d", Integer.valueOf(oldw), Integer.valueOf(oldh), Integer.valueOf(w), Integer.valueOf(h));
        PointF sCenter = getCenter();
        if (this.readySent && sCenter != null) {
            this.anim = null;
            this.pendingScale = Float.valueOf(this.scale);
            this.sPendingCenter = sCenter;
        }
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthSpecMode = View.MeasureSpec.getMode(widthMeasureSpec);
        int heightSpecMode = View.MeasureSpec.getMode(heightMeasureSpec);
        int parentWidth = View.MeasureSpec.getSize(widthMeasureSpec);
        int parentHeight = View.MeasureSpec.getSize(heightMeasureSpec);
        boolean resizeHeight = true;
        boolean resizeWidth = widthSpecMode != 1073741824;
        if (heightSpecMode == 1073741824) {
            resizeHeight = false;
        }
        int width = parentWidth;
        int height = parentHeight;
        if (this.sWidth > 0 && this.sHeight > 0) {
            if (resizeWidth && resizeHeight) {
                width = sWidth();
                height = sHeight();
            } else if (resizeHeight) {
                height = (int) ((((double) sHeight()) / ((double) sWidth())) * ((double) width));
            } else if (resizeWidth) {
                width = (int) ((((double) sWidth()) / ((double) sHeight())) * ((double) height));
            }
        }
        setMeasuredDimension(Math.max(width, getSuggestedMinimumWidth()), Math.max(height, getSuggestedMinimumHeight()));
    }

    public boolean onTouchEvent(MotionEvent event) {
        GestureDetector gestureDetector;
        Anim anim2 = this.anim;
        if (anim2 == null || anim2.interruptible) {
            Anim anim3 = this.anim;
            if (!(anim3 == null || anim3.listener == null)) {
                try {
                    this.anim.listener.onInterruptedByUser();
                } catch (Exception e) {
                    Log.w(TAG, "Error thrown by animation listener", e);
                }
            }
            this.anim = null;
            if (this.vTranslate == null) {
                GestureDetector gestureDetector2 = this.singleDetector;
                if (gestureDetector2 != null) {
                    gestureDetector2.onTouchEvent(event);
                }
                return true;
            } else if (this.isQuickScaling || ((gestureDetector = this.detector) != null && !gestureDetector.onTouchEvent(event))) {
                if (this.vTranslateStart == null) {
                    this.vTranslateStart = new PointF(0.0f, 0.0f);
                }
                if (this.vTranslateBefore == null) {
                    this.vTranslateBefore = new PointF(0.0f, 0.0f);
                }
                if (this.vCenterStart == null) {
                    this.vCenterStart = new PointF(0.0f, 0.0f);
                }
                float scaleBefore = this.scale;
                this.vTranslateBefore.set(this.vTranslate);
                boolean handled = onTouchEventInternal(event);
                sendStateChanged(scaleBefore, this.vTranslateBefore, 2);
                if (handled || super.onTouchEvent(event)) {
                    return true;
                }
                return false;
            } else {
                this.isZooming = false;
                this.isPanning = false;
                this.maxTouchCount = 0;
                return true;
            }
        } else {
            requestDisallowInterceptTouchEvent(true);
            return true;
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:137:0x03fd  */
    /* JADX WARNING: Removed duplicated region for block: B:180:? A[ORIG_RETURN, RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private boolean onTouchEventInternal(android.view.MotionEvent r19) {
        /*
            r18 = this;
            r0 = r18
            r1 = r19
            int r2 = r19.getPointerCount()
            int r3 = r19.getAction()
            r4 = 1073741824(0x40000000, float:2.0)
            r5 = 2
            r6 = 0
            r7 = 1
            if (r3 == 0) goto L_0x002b
            if (r3 == r7) goto L_0x0027
            if (r3 == r5) goto L_0x002e
            r8 = 5
            if (r3 == r8) goto L_0x002b
            r8 = 6
            if (r3 == r8) goto L_0x0027
            r8 = 261(0x105, float:3.66E-43)
            if (r3 == r8) goto L_0x002b
            r4 = 262(0x106, float:3.67E-43)
            if (r3 == r4) goto L_0x0027
            goto L_0x0407
        L_0x0027:
            r3 = r6
            r6 = r7
            goto L_0x0409
        L_0x002b:
            r3 = r7
            goto L_0x0482
        L_0x002e:
            r3 = 0
            int r8 = r0.maxTouchCount
            if (r8 <= 0) goto L_0x03f7
            r8 = 1084227584(0x40a00000, float:5.0)
            if (r2 < r5) goto L_0x019a
            float r9 = r1.getX(r6)
            float r10 = r1.getX(r7)
            float r11 = r1.getY(r6)
            float r12 = r1.getY(r7)
            float r9 = r0.distance(r9, r10, r11, r12)
            float r10 = r1.getX(r6)
            float r11 = r1.getX(r7)
            float r10 = r10 + r11
            float r10 = r10 / r4
            float r11 = r1.getY(r6)
            float r12 = r1.getY(r7)
            float r11 = r11 + r12
            float r11 = r11 / r4
            boolean r4 = r0.zoomEnabled
            if (r4 == 0) goto L_0x0198
            android.graphics.PointF r4 = r0.vCenterStart
            float r4 = r4.x
            android.graphics.PointF r12 = r0.vCenterStart
            float r12 = r12.y
            float r4 = r0.distance(r4, r10, r12, r11)
            int r4 = (r4 > r8 ? 1 : (r4 == r8 ? 0 : -1))
            if (r4 > 0) goto L_0x0083
            float r4 = r0.vDistStart
            float r4 = r9 - r4
            float r4 = java.lang.Math.abs(r4)
            int r4 = (r4 > r8 ? 1 : (r4 == r8 ? 0 : -1))
            if (r4 > 0) goto L_0x0083
            boolean r4 = r0.isPanning
            if (r4 == 0) goto L_0x0198
        L_0x0083:
            r0.isZooming = r7
            r0.isPanning = r7
            r3 = 1
            float r4 = r0.scale
            double r12 = (double) r4
            float r4 = r0.maxScale
            float r8 = r0.vDistStart
            float r8 = r9 / r8
            float r14 = r0.scaleStart
            float r8 = r8 * r14
            float r4 = java.lang.Math.min(r4, r8)
            r0.scale = r4
            float r8 = r18.minScale()
            int r4 = (r4 > r8 ? 1 : (r4 == r8 ? 0 : -1))
            if (r4 > 0) goto L_0x00b8
            r0.vDistStart = r9
            float r4 = r18.minScale()
            r0.scaleStart = r4
            android.graphics.PointF r4 = r0.vCenterStart
            r4.set(r10, r11)
            android.graphics.PointF r4 = r0.vTranslateStart
            android.graphics.PointF r5 = r0.vTranslate
            r4.set(r5)
            goto L_0x018f
        L_0x00b8:
            boolean r4 = r0.panEnabled
            if (r4 == 0) goto L_0x013e
            android.graphics.PointF r4 = r0.vCenterStart
            float r4 = r4.x
            android.graphics.PointF r5 = r0.vTranslateStart
            float r5 = r5.x
            float r4 = r4 - r5
            android.graphics.PointF r5 = r0.vCenterStart
            float r5 = r5.y
            android.graphics.PointF r8 = r0.vTranslateStart
            float r8 = r8.y
            float r5 = r5 - r8
            float r8 = r0.scale
            float r14 = r0.scaleStart
            float r15 = r8 / r14
            float r15 = r15 * r4
            float r8 = r8 / r14
            float r8 = r8 * r5
            android.graphics.PointF r14 = r0.vTranslate
            float r6 = r10 - r15
            r14.x = r6
            android.graphics.PointF r6 = r0.vTranslate
            float r14 = r11 - r8
            r6.y = r14
            int r6 = r18.sHeight()
            r16 = r8
            double r7 = (double) r6
            double r7 = r7 * r12
            int r6 = r18.getHeight()
            r17 = r15
            double r14 = (double) r6
            int r6 = (r7 > r14 ? 1 : (r7 == r14 ? 0 : -1))
            if (r6 >= 0) goto L_0x0107
            float r6 = r0.scale
            int r7 = r18.sHeight()
            float r7 = (float) r7
            float r6 = r6 * r7
            int r7 = r18.getHeight()
            float r7 = (float) r7
            int r6 = (r6 > r7 ? 1 : (r6 == r7 ? 0 : -1))
            if (r6 >= 0) goto L_0x0127
        L_0x0107:
            int r6 = r18.sWidth()
            double r6 = (double) r6
            double r6 = r6 * r12
            int r8 = r18.getWidth()
            double r14 = (double) r8
            int r6 = (r6 > r14 ? 1 : (r6 == r14 ? 0 : -1))
            if (r6 >= 0) goto L_0x013d
            float r6 = r0.scale
            int r7 = r18.sWidth()
            float r7 = (float) r7
            float r6 = r6 * r7
            int r7 = r18.getWidth()
            float r7 = (float) r7
            int r6 = (r6 > r7 ? 1 : (r6 == r7 ? 0 : -1))
            if (r6 < 0) goto L_0x013d
        L_0x0127:
            r6 = 1
            r0.fitToBounds(r6)
            android.graphics.PointF r6 = r0.vCenterStart
            r6.set(r10, r11)
            android.graphics.PointF r6 = r0.vTranslateStart
            android.graphics.PointF r7 = r0.vTranslate
            r6.set(r7)
            float r6 = r0.scale
            r0.scaleStart = r6
            r0.vDistStart = r9
        L_0x013d:
            goto L_0x018f
        L_0x013e:
            android.graphics.PointF r4 = r0.sRequestedCenter
            if (r4 == 0) goto L_0x0167
            android.graphics.PointF r4 = r0.vTranslate
            int r6 = r18.getWidth()
            int r6 = r6 / r5
            float r6 = (float) r6
            float r7 = r0.scale
            android.graphics.PointF r8 = r0.sRequestedCenter
            float r8 = r8.x
            float r7 = r7 * r8
            float r6 = r6 - r7
            r4.x = r6
            android.graphics.PointF r4 = r0.vTranslate
            int r6 = r18.getHeight()
            int r6 = r6 / r5
            float r5 = (float) r6
            float r6 = r0.scale
            android.graphics.PointF r7 = r0.sRequestedCenter
            float r7 = r7.y
            float r6 = r6 * r7
            float r5 = r5 - r6
            r4.y = r5
            goto L_0x018f
        L_0x0167:
            android.graphics.PointF r4 = r0.vTranslate
            int r6 = r18.getWidth()
            int r6 = r6 / r5
            float r6 = (float) r6
            float r7 = r0.scale
            int r8 = r18.sWidth()
            int r8 = r8 / r5
            float r8 = (float) r8
            float r7 = r7 * r8
            float r6 = r6 - r7
            r4.x = r6
            android.graphics.PointF r4 = r0.vTranslate
            int r6 = r18.getHeight()
            int r6 = r6 / r5
            float r6 = (float) r6
            float r7 = r0.scale
            int r8 = r18.sHeight()
            int r8 = r8 / r5
            float r5 = (float) r8
            float r7 = r7 * r5
            float r6 = r6 - r7
            r4.y = r6
        L_0x018f:
            r4 = 1
            r0.fitToBounds(r4)
            boolean r4 = r0.eagerLoadingEnabled
            r0.refreshRequiredTiles(r4)
        L_0x0198:
            goto L_0x03fb
        L_0x019a:
            boolean r6 = r0.isQuickScaling
            if (r6 == 0) goto L_0x030f
            android.graphics.PointF r6 = r0.quickScaleVStart
            float r6 = r6.y
            float r7 = r19.getY()
            float r6 = r6 - r7
            float r6 = java.lang.Math.abs(r6)
            float r6 = r6 * r4
            float r4 = r0.quickScaleThreshold
            float r6 = r6 + r4
            float r4 = r0.quickScaleLastDistance
            r7 = -1082130432(0xffffffffbf800000, float:-1.0)
            int r4 = (r4 > r7 ? 1 : (r4 == r7 ? 0 : -1))
            if (r4 != 0) goto L_0x01b9
            r0.quickScaleLastDistance = r6
        L_0x01b9:
            float r4 = r19.getY()
            android.graphics.PointF r7 = r0.quickScaleVLastPoint
            float r7 = r7.y
            int r4 = (r4 > r7 ? 1 : (r4 == r7 ? 0 : -1))
            if (r4 <= 0) goto L_0x01c7
            r4 = 1
            goto L_0x01c8
        L_0x01c7:
            r4 = 0
        L_0x01c8:
            android.graphics.PointF r7 = r0.quickScaleVLastPoint
            float r8 = r19.getY()
            r9 = 0
            r7.set(r9, r8)
            float r7 = r0.quickScaleLastDistance
            float r7 = r6 / r7
            r8 = 1065353216(0x3f800000, float:1.0)
            float r7 = r8 - r7
            float r7 = java.lang.Math.abs(r7)
            r10 = 1056964608(0x3f000000, float:0.5)
            float r7 = r7 * r10
            r10 = 1022739087(0x3cf5c28f, float:0.03)
            int r10 = (r7 > r10 ? 1 : (r7 == r10 ? 0 : -1))
            if (r10 > 0) goto L_0x01f3
            boolean r10 = r0.quickScaleMoved
            if (r10 == 0) goto L_0x01ed
            goto L_0x01f3
        L_0x01ed:
            r16 = r3
            r17 = r4
            goto L_0x0301
        L_0x01f3:
            r10 = 1
            r0.quickScaleMoved = r10
            r10 = 1065353216(0x3f800000, float:1.0)
            float r11 = r0.quickScaleLastDistance
            int r9 = (r11 > r9 ? 1 : (r11 == r9 ? 0 : -1))
            if (r9 <= 0) goto L_0x0204
            if (r4 == 0) goto L_0x0202
            float r8 = r8 + r7
            goto L_0x0203
        L_0x0202:
            float r8 = r8 - r7
        L_0x0203:
            r10 = r8
        L_0x0204:
            float r8 = r0.scale
            double r8 = (double) r8
            float r11 = r18.minScale()
            float r12 = r0.maxScale
            float r13 = r0.scale
            float r13 = r13 * r10
            float r12 = java.lang.Math.min(r12, r13)
            float r11 = java.lang.Math.max(r11, r12)
            r0.scale = r11
            boolean r11 = r0.panEnabled
            if (r11 == 0) goto L_0x02ac
            android.graphics.PointF r5 = r0.vCenterStart
            float r5 = r5.x
            android.graphics.PointF r11 = r0.vTranslateStart
            float r11 = r11.x
            float r5 = r5 - r11
            android.graphics.PointF r11 = r0.vCenterStart
            float r11 = r11.y
            android.graphics.PointF r12 = r0.vTranslateStart
            float r12 = r12.y
            float r11 = r11 - r12
            float r12 = r0.scale
            float r13 = r0.scaleStart
            float r15 = r12 / r13
            float r15 = r15 * r5
            float r12 = r12 / r13
            float r12 = r12 * r11
            android.graphics.PointF r13 = r0.vTranslate
            android.graphics.PointF r14 = r0.vCenterStart
            float r14 = r14.x
            float r14 = r14 - r15
            r13.x = r14
            android.graphics.PointF r13 = r0.vTranslate
            android.graphics.PointF r14 = r0.vCenterStart
            float r14 = r14.y
            float r14 = r14 - r12
            r13.y = r14
            int r13 = r18.sHeight()
            double r13 = (double) r13
            double r13 = r13 * r8
            r16 = r3
            int r3 = r18.getHeight()
            r17 = r4
            double r3 = (double) r3
            int r3 = (r13 > r3 ? 1 : (r13 == r3 ? 0 : -1))
            if (r3 >= 0) goto L_0x026f
            float r3 = r0.scale
            int r4 = r18.sHeight()
            float r4 = (float) r4
            float r3 = r3 * r4
            int r4 = r18.getHeight()
            float r4 = (float) r4
            int r3 = (r3 > r4 ? 1 : (r3 == r4 ? 0 : -1))
            if (r3 >= 0) goto L_0x028f
        L_0x026f:
            int r3 = r18.sWidth()
            double r3 = (double) r3
            double r3 = r3 * r8
            int r13 = r18.getWidth()
            double r13 = (double) r13
            int r3 = (r3 > r13 ? 1 : (r3 == r13 ? 0 : -1))
            if (r3 >= 0) goto L_0x02ab
            float r3 = r0.scale
            int r4 = r18.sWidth()
            float r4 = (float) r4
            float r3 = r3 * r4
            int r4 = r18.getWidth()
            float r4 = (float) r4
            int r3 = (r3 > r4 ? 1 : (r3 == r4 ? 0 : -1))
            if (r3 < 0) goto L_0x02ab
        L_0x028f:
            r3 = 1
            r0.fitToBounds(r3)
            android.graphics.PointF r3 = r0.vCenterStart
            android.graphics.PointF r4 = r0.quickScaleSCenter
            android.graphics.PointF r4 = r0.sourceToViewCoord(r4)
            r3.set(r4)
            android.graphics.PointF r3 = r0.vTranslateStart
            android.graphics.PointF r4 = r0.vTranslate
            r3.set(r4)
            float r3 = r0.scale
            r0.scaleStart = r3
            r3 = 0
            r6 = r3
        L_0x02ab:
            goto L_0x0301
        L_0x02ac:
            r16 = r3
            r17 = r4
            android.graphics.PointF r3 = r0.sRequestedCenter
            if (r3 == 0) goto L_0x02d9
            android.graphics.PointF r3 = r0.vTranslate
            int r4 = r18.getWidth()
            int r4 = r4 / r5
            float r4 = (float) r4
            float r11 = r0.scale
            android.graphics.PointF r12 = r0.sRequestedCenter
            float r12 = r12.x
            float r11 = r11 * r12
            float r4 = r4 - r11
            r3.x = r4
            android.graphics.PointF r3 = r0.vTranslate
            int r4 = r18.getHeight()
            int r4 = r4 / r5
            float r4 = (float) r4
            float r5 = r0.scale
            android.graphics.PointF r11 = r0.sRequestedCenter
            float r11 = r11.y
            float r5 = r5 * r11
            float r4 = r4 - r5
            r3.y = r4
            goto L_0x0301
        L_0x02d9:
            android.graphics.PointF r3 = r0.vTranslate
            int r4 = r18.getWidth()
            int r4 = r4 / r5
            float r4 = (float) r4
            float r11 = r0.scale
            int r12 = r18.sWidth()
            int r12 = r12 / r5
            float r12 = (float) r12
            float r11 = r11 * r12
            float r4 = r4 - r11
            r3.x = r4
            android.graphics.PointF r3 = r0.vTranslate
            int r4 = r18.getHeight()
            int r4 = r4 / r5
            float r4 = (float) r4
            float r11 = r0.scale
            int r12 = r18.sHeight()
            int r12 = r12 / r5
            float r5 = (float) r12
            float r11 = r11 * r5
            float r4 = r4 - r11
            r3.y = r4
        L_0x0301:
            r0.quickScaleLastDistance = r6
            r3 = 1
            r0.fitToBounds(r3)
            boolean r3 = r0.eagerLoadingEnabled
            r0.refreshRequiredTiles(r3)
            r3 = 1
            goto L_0x03fb
        L_0x030f:
            r16 = r3
            boolean r3 = r0.isZooming
            if (r3 != 0) goto L_0x03f9
            float r3 = r19.getX()
            android.graphics.PointF r4 = r0.vCenterStart
            float r4 = r4.x
            float r3 = r3 - r4
            float r3 = java.lang.Math.abs(r3)
            float r4 = r19.getY()
            android.graphics.PointF r5 = r0.vCenterStart
            float r5 = r5.y
            float r4 = r4 - r5
            float r4 = java.lang.Math.abs(r4)
            float r5 = r0.density
            float r5 = r5 * r8
            int r6 = (r3 > r5 ? 1 : (r3 == r5 ? 0 : -1))
            if (r6 > 0) goto L_0x033e
            int r6 = (r4 > r5 ? 1 : (r4 == r5 ? 0 : -1))
            if (r6 > 0) goto L_0x033e
            boolean r6 = r0.isPanning
            if (r6 == 0) goto L_0x03f9
        L_0x033e:
            r6 = 1
            android.graphics.PointF r7 = r0.vTranslate
            android.graphics.PointF r8 = r0.vTranslateStart
            float r8 = r8.x
            float r9 = r19.getX()
            android.graphics.PointF r10 = r0.vCenterStart
            float r10 = r10.x
            float r9 = r9 - r10
            float r8 = r8 + r9
            r7.x = r8
            android.graphics.PointF r7 = r0.vTranslate
            android.graphics.PointF r8 = r0.vTranslateStart
            float r8 = r8.y
            float r9 = r19.getY()
            android.graphics.PointF r10 = r0.vCenterStart
            float r10 = r10.y
            float r9 = r9 - r10
            float r8 = r8 + r9
            r7.y = r8
            android.graphics.PointF r7 = r0.vTranslate
            float r7 = r7.x
            android.graphics.PointF r8 = r0.vTranslate
            float r8 = r8.y
            r9 = 1
            r0.fitToBounds(r9)
            android.graphics.PointF r9 = r0.vTranslate
            float r9 = r9.x
            int r9 = (r7 > r9 ? 1 : (r7 == r9 ? 0 : -1))
            if (r9 == 0) goto L_0x0379
            r9 = 1
            goto L_0x037a
        L_0x0379:
            r9 = 0
        L_0x037a:
            android.graphics.PointF r10 = r0.vTranslate
            float r10 = r10.y
            int r10 = (r8 > r10 ? 1 : (r8 == r10 ? 0 : -1))
            if (r10 == 0) goto L_0x0384
            r10 = 1
            goto L_0x0385
        L_0x0384:
            r10 = 0
        L_0x0385:
            if (r9 == 0) goto L_0x0391
            int r11 = (r3 > r4 ? 1 : (r3 == r4 ? 0 : -1))
            if (r11 <= 0) goto L_0x0391
            boolean r11 = r0.isPanning
            if (r11 != 0) goto L_0x0391
            r11 = 1
            goto L_0x0392
        L_0x0391:
            r11 = 0
        L_0x0392:
            if (r10 == 0) goto L_0x039e
            int r12 = (r4 > r3 ? 1 : (r4 == r3 ? 0 : -1))
            if (r12 <= 0) goto L_0x039e
            boolean r12 = r0.isPanning
            if (r12 != 0) goto L_0x039e
            r12 = 1
            goto L_0x039f
        L_0x039e:
            r12 = 0
        L_0x039f:
            android.graphics.PointF r13 = r0.vTranslate
            float r13 = r13.y
            int r13 = (r8 > r13 ? 1 : (r8 == r13 ? 0 : -1))
            if (r13 != 0) goto L_0x03b0
            r13 = 1077936128(0x40400000, float:3.0)
            float r13 = r13 * r5
            int r13 = (r4 > r13 ? 1 : (r4 == r13 ? 0 : -1))
            if (r13 <= 0) goto L_0x03b0
            r13 = 1
            goto L_0x03b1
        L_0x03b0:
            r13 = 0
        L_0x03b1:
            if (r11 != 0) goto L_0x03c3
            if (r12 != 0) goto L_0x03c3
            if (r9 == 0) goto L_0x03bf
            if (r10 == 0) goto L_0x03bf
            if (r13 != 0) goto L_0x03bf
            boolean r15 = r0.isPanning
            if (r15 == 0) goto L_0x03c3
        L_0x03bf:
            r14 = 1
            r0.isPanning = r14
            goto L_0x03d8
        L_0x03c3:
            int r15 = (r3 > r5 ? 1 : (r3 == r5 ? 0 : -1))
            if (r15 > 0) goto L_0x03cb
            int r15 = (r4 > r5 ? 1 : (r4 == r5 ? 0 : -1))
            if (r15 <= 0) goto L_0x03d8
        L_0x03cb:
            r15 = 0
            r0.maxTouchCount = r15
            android.os.Handler r14 = r0.handler
            r15 = 1
            r14.removeMessages(r15)
            r15 = 0
            r0.requestDisallowInterceptTouchEvent(r15)
        L_0x03d8:
            boolean r15 = r0.panEnabled
            if (r15 != 0) goto L_0x03f0
            android.graphics.PointF r15 = r0.vTranslate
            android.graphics.PointF r14 = r0.vTranslateStart
            float r14 = r14.x
            r15.x = r14
            android.graphics.PointF r14 = r0.vTranslate
            android.graphics.PointF r15 = r0.vTranslateStart
            float r15 = r15.y
            r14.y = r15
            r14 = 0
            r0.requestDisallowInterceptTouchEvent(r14)
        L_0x03f0:
            boolean r14 = r0.eagerLoadingEnabled
            r0.refreshRequiredTiles(r14)
            r3 = r6
            goto L_0x03fb
        L_0x03f7:
            r16 = r3
        L_0x03f9:
            r3 = r16
        L_0x03fb:
            if (r3 == 0) goto L_0x0407
            android.os.Handler r4 = r0.handler
            r6 = 1
            r4.removeMessages(r6)
            r18.invalidate()
            return r6
        L_0x0407:
            r3 = 0
            return r3
        L_0x0409:
            android.os.Handler r4 = r0.handler
            r4.removeMessages(r6)
            boolean r4 = r0.isQuickScaling
            if (r4 == 0) goto L_0x041f
            r0.isQuickScaling = r3
            boolean r3 = r0.quickScaleMoved
            if (r3 != 0) goto L_0x041f
            android.graphics.PointF r3 = r0.quickScaleSCenter
            android.graphics.PointF r4 = r0.vCenterStart
            r0.doubleTapZoom(r3, r4)
        L_0x041f:
            int r3 = r0.maxTouchCount
            if (r3 <= 0) goto L_0x0477
            boolean r3 = r0.isZooming
            if (r3 != 0) goto L_0x042e
            boolean r4 = r0.isPanning
            if (r4 == 0) goto L_0x042c
            goto L_0x042e
        L_0x042c:
            r3 = 1
            goto L_0x0478
        L_0x042e:
            if (r3 == 0) goto L_0x0464
            if (r2 != r5) goto L_0x0464
            r3 = 1
            r0.isPanning = r3
            android.graphics.PointF r4 = r0.vTranslateStart
            android.graphics.PointF r6 = r0.vTranslate
            float r6 = r6.x
            android.graphics.PointF r7 = r0.vTranslate
            float r7 = r7.y
            r4.set(r6, r7)
            int r4 = r19.getActionIndex()
            if (r4 != r3) goto L_0x0457
            android.graphics.PointF r4 = r0.vCenterStart
            r6 = 0
            float r7 = r1.getX(r6)
            float r8 = r1.getY(r6)
            r4.set(r7, r8)
            goto L_0x0464
        L_0x0457:
            android.graphics.PointF r4 = r0.vCenterStart
            float r6 = r1.getX(r3)
            float r7 = r1.getY(r3)
            r4.set(r6, r7)
        L_0x0464:
            r3 = 3
            if (r2 >= r3) goto L_0x046b
            r3 = 0
            r0.isZooming = r3
            goto L_0x046c
        L_0x046b:
            r3 = 0
        L_0x046c:
            if (r2 >= r5) goto L_0x0472
            r0.isPanning = r3
            r0.maxTouchCount = r3
        L_0x0472:
            r3 = 1
            r0.refreshRequiredTiles(r3)
            return r3
        L_0x0477:
            r3 = 1
        L_0x0478:
            if (r2 != r3) goto L_0x0481
            r4 = 0
            r0.isZooming = r4
            r0.isPanning = r4
            r0.maxTouchCount = r4
        L_0x0481:
            return r3
        L_0x0482:
            r6 = 0
            r0.anim = r6
            r0.requestDisallowInterceptTouchEvent(r3)
            int r3 = r0.maxTouchCount
            int r3 = java.lang.Math.max(r3, r2)
            r0.maxTouchCount = r3
            if (r2 < r5) goto L_0x04e6
            boolean r3 = r0.zoomEnabled
            if (r3 == 0) goto L_0x04db
            r3 = 0
            float r5 = r1.getX(r3)
            r6 = 1
            float r7 = r1.getX(r6)
            float r8 = r1.getY(r3)
            float r3 = r1.getY(r6)
            float r3 = r0.distance(r5, r7, r8, r3)
            float r5 = r0.scale
            r0.scaleStart = r5
            r0.vDistStart = r3
            android.graphics.PointF r5 = r0.vTranslateStart
            android.graphics.PointF r6 = r0.vTranslate
            float r6 = r6.x
            android.graphics.PointF r7 = r0.vTranslate
            float r7 = r7.y
            r5.set(r6, r7)
            android.graphics.PointF r5 = r0.vCenterStart
            r6 = 0
            float r7 = r1.getX(r6)
            r8 = 1
            float r9 = r1.getX(r8)
            float r7 = r7 + r9
            float r7 = r7 / r4
            float r6 = r1.getY(r6)
            float r9 = r1.getY(r8)
            float r6 = r6 + r9
            float r6 = r6 / r4
            r5.set(r7, r6)
            goto L_0x04df
        L_0x04db:
            r6 = 0
            r8 = 1
            r0.maxTouchCount = r6
        L_0x04df:
            android.os.Handler r3 = r0.handler
            r3.removeMessages(r8)
            r6 = r8
            goto L_0x050e
        L_0x04e6:
            boolean r3 = r0.isQuickScaling
            if (r3 != 0) goto L_0x050d
            android.graphics.PointF r3 = r0.vTranslateStart
            android.graphics.PointF r4 = r0.vTranslate
            float r4 = r4.x
            android.graphics.PointF r5 = r0.vTranslate
            float r5 = r5.y
            r3.set(r4, r5)
            android.graphics.PointF r3 = r0.vCenterStart
            float r4 = r19.getX()
            float r5 = r19.getY()
            r3.set(r4, r5)
            android.os.Handler r3 = r0.handler
            r4 = 600(0x258, double:2.964E-321)
            r6 = 1
            r3.sendEmptyMessageDelayed(r6, r4)
            goto L_0x050e
        L_0x050d:
            r6 = 1
        L_0x050e:
            return r6
        */
        throw new UnsupportedOperationException("Method not decompiled: com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView.onTouchEventInternal(android.view.MotionEvent):boolean");
    }

    private void requestDisallowInterceptTouchEvent(boolean disallowIntercept) {
        ViewParent parent = getParent();
        if (parent != null) {
            parent.requestDisallowInterceptTouchEvent(disallowIntercept);
        }
    }

    /* access modifiers changed from: private */
    public void doubleTapZoom(PointF sCenter, PointF vFocus) {
        if (!this.panEnabled) {
            PointF pointF = this.sRequestedCenter;
            if (pointF != null) {
                sCenter.x = pointF.x;
                sCenter.y = this.sRequestedCenter.y;
            } else {
                sCenter.x = (float) (sWidth() / 2);
                sCenter.y = (float) (sHeight() / 2);
            }
        }
        float doubleTapZoomScale2 = Math.min(this.maxScale, this.doubleTapZoomScale);
        float f = this.scale;
        boolean zoomIn = ((double) f) <= ((double) doubleTapZoomScale2) * 0.9d || f == this.minScale;
        float targetScale = zoomIn ? doubleTapZoomScale2 : minScale();
        int i = this.doubleTapZoomStyle;
        if (i == 3) {
            setScaleAndCenter(targetScale, sCenter);
        } else if (i == 2 || !zoomIn || !this.panEnabled) {
            new AnimationBuilder(targetScale, sCenter).withInterruptible(false).withDuration((long) this.doubleTapZoomDuration).withOrigin(4).start();
        } else if (i == 1) {
            new AnimationBuilder(targetScale, sCenter, vFocus).withInterruptible(false).withDuration((long) this.doubleTapZoomDuration).withOrigin(4).start();
        }
        invalidate();
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        int i;
        int i2;
        int i3;
        int i4;
        int i5;
        Canvas canvas2 = canvas;
        super.onDraw(canvas);
        createPaints();
        if (this.sWidth != 0 && this.sHeight != 0 && getWidth() != 0 && getHeight() != 0) {
            if (this.tileMap == null && this.decoder != null) {
                initialiseBaseLayer(getMaxBitmapDimensions(canvas));
            }
            if (checkReady()) {
                preDraw();
                Anim anim2 = this.anim;
                if (!(anim2 == null || anim2.vFocusStart == null)) {
                    float scaleBefore = this.scale;
                    if (this.vTranslateBefore == null) {
                        this.vTranslateBefore = new PointF(0.0f, 0.0f);
                    }
                    this.vTranslateBefore.set(this.vTranslate);
                    long scaleElapsed = System.currentTimeMillis() - this.anim.time;
                    boolean finished = scaleElapsed > this.anim.duration;
                    long min = Math.min(scaleElapsed, this.anim.duration);
                    this.scale = ease(this.anim.easing, min, this.anim.scaleStart, this.anim.scaleEnd - this.anim.scaleStart, this.anim.duration);
                    float vFocusNowX = ease(this.anim.easing, min, this.anim.vFocusStart.x, this.anim.vFocusEnd.x - this.anim.vFocusStart.x, this.anim.duration);
                    float vFocusNowY = ease(this.anim.easing, min, this.anim.vFocusStart.y, this.anim.vFocusEnd.y - this.anim.vFocusStart.y, this.anim.duration);
                    this.vTranslate.x -= sourceToViewX(this.anim.sCenterEnd.x) - vFocusNowX;
                    this.vTranslate.y -= sourceToViewY(this.anim.sCenterEnd.y) - vFocusNowY;
                    fitToBounds(finished || this.anim.scaleStart == this.anim.scaleEnd);
                    sendStateChanged(scaleBefore, this.vTranslateBefore, this.anim.origin);
                    refreshRequiredTiles(finished);
                    if (finished) {
                        if (this.anim.listener != null) {
                            try {
                                this.anim.listener.onComplete();
                            } catch (Exception e) {
                                Log.w(TAG, "Error thrown by animation listener", e);
                            }
                        }
                        this.anim = null;
                    }
                    invalidate();
                }
                int i6 = 35;
                int i7 = 90;
                int i8 = 180;
                if (this.tileMap == null || !isBaseLayerReady()) {
                    i2 = 35;
                    i = 15;
                    Bitmap bitmap2 = this.bitmap;
                    if (bitmap2 != null) {
                        float xScale = this.scale;
                        float yScale = this.scale;
                        if (this.bitmapIsPreview) {
                            xScale = this.scale * (((float) this.sWidth) / ((float) bitmap2.getWidth()));
                            yScale = this.scale * (((float) this.sHeight) / ((float) this.bitmap.getHeight()));
                        }
                        if (this.matrix == null) {
                            this.matrix = new Matrix();
                        }
                        this.matrix.reset();
                        this.matrix.postScale(xScale, yScale);
                        this.matrix.postRotate((float) getRequiredRotation());
                        this.matrix.postTranslate(this.vTranslate.x, this.vTranslate.y);
                        if (getRequiredRotation() == 180) {
                            Matrix matrix2 = this.matrix;
                            float f = this.scale;
                            matrix2.postTranslate(((float) this.sWidth) * f, f * ((float) this.sHeight));
                        } else if (getRequiredRotation() == 90) {
                            this.matrix.postTranslate(this.scale * ((float) this.sHeight), 0.0f);
                        } else if (getRequiredRotation() == 270) {
                            this.matrix.postTranslate(0.0f, this.scale * ((float) this.sWidth));
                        }
                        if (this.tileBgPaint != null) {
                            if (this.sRect == null) {
                                this.sRect = new RectF();
                            }
                            this.sRect.set(0.0f, 0.0f, (float) (this.bitmapIsPreview ? this.bitmap.getWidth() : this.sWidth), (float) (this.bitmapIsPreview ? this.bitmap.getHeight() : this.sHeight));
                            this.matrix.mapRect(this.sRect);
                            canvas2.drawRect(this.sRect, this.tileBgPaint);
                        }
                        canvas2.drawBitmap(this.bitmap, this.matrix, this.bitmapPaint);
                    }
                } else {
                    int sampleSize = Math.min(this.fullImageSampleSize, calculateInSampleSize(this.scale));
                    boolean hasMissingTiles = false;
                    for (Map.Entry<Integer, List<Tile>> tileMapEntry : this.tileMap.entrySet()) {
                        if (tileMapEntry.getKey().intValue() == sampleSize) {
                            for (Tile tile : tileMapEntry.getValue()) {
                                if (tile.visible && (tile.loading || tile.bitmap == null)) {
                                    hasMissingTiles = true;
                                }
                            }
                        }
                    }
                    for (Map.Entry<Integer, List<Tile>> tileMapEntry2 : this.tileMap.entrySet()) {
                        if (tileMapEntry2.getKey().intValue() == sampleSize || hasMissingTiles) {
                            for (Tile tile2 : tileMapEntry2.getValue()) {
                                sourceToViewRect(tile2.sRect, tile2.vRect);
                                if (tile2.loading || tile2.bitmap == null) {
                                    i3 = i8;
                                    i4 = i7;
                                    if (!tile2.loading || !this.debug) {
                                        i5 = 35;
                                    } else {
                                        i5 = 35;
                                        canvas2.drawText("LOADING", (float) (tile2.vRect.left + m29px(5)), (float) (tile2.vRect.top + m29px(35)), this.debugTextPaint);
                                    }
                                } else {
                                    if (this.tileBgPaint != null) {
                                        canvas2.drawRect(tile2.vRect, this.tileBgPaint);
                                    }
                                    if (this.matrix == null) {
                                        this.matrix = new Matrix();
                                    }
                                    this.matrix.reset();
                                    i3 = i8;
                                    i4 = i7;
                                    setMatrixArray(this.srcArray, 0.0f, 0.0f, (float) tile2.bitmap.getWidth(), 0.0f, (float) tile2.bitmap.getWidth(), (float) tile2.bitmap.getHeight(), 0.0f, (float) tile2.bitmap.getHeight());
                                    if (getRequiredRotation() == 0) {
                                        setMatrixArray(this.dstArray, (float) tile2.vRect.left, (float) tile2.vRect.top, (float) tile2.vRect.right, (float) tile2.vRect.top, (float) tile2.vRect.right, (float) tile2.vRect.bottom, (float) tile2.vRect.left, (float) tile2.vRect.bottom);
                                    } else if (getRequiredRotation() == i4) {
                                        setMatrixArray(this.dstArray, (float) tile2.vRect.right, (float) tile2.vRect.top, (float) tile2.vRect.right, (float) tile2.vRect.bottom, (float) tile2.vRect.left, (float) tile2.vRect.bottom, (float) tile2.vRect.left, (float) tile2.vRect.top);
                                    } else if (getRequiredRotation() == i3) {
                                        setMatrixArray(this.dstArray, (float) tile2.vRect.right, (float) tile2.vRect.bottom, (float) tile2.vRect.left, (float) tile2.vRect.bottom, (float) tile2.vRect.left, (float) tile2.vRect.top, (float) tile2.vRect.right, (float) tile2.vRect.top);
                                    } else if (getRequiredRotation() == 270) {
                                        setMatrixArray(this.dstArray, (float) tile2.vRect.left, (float) tile2.vRect.bottom, (float) tile2.vRect.left, (float) tile2.vRect.top, (float) tile2.vRect.right, (float) tile2.vRect.top, (float) tile2.vRect.right, (float) tile2.vRect.bottom);
                                    }
                                    this.matrix.setPolyToPoly(this.srcArray, 0, this.dstArray, 0, 4);
                                    canvas2.drawBitmap(tile2.bitmap, this.matrix, this.bitmapPaint);
                                    if (this.debug) {
                                        canvas2.drawRect(tile2.vRect, this.debugLinePaint);
                                        i5 = 35;
                                    } else {
                                        i5 = 35;
                                    }
                                }
                                if (tile2.visible && this.debug) {
                                    canvas2.drawText("ISS " + tile2.sampleSize + " RECT " + tile2.sRect.top + "," + tile2.sRect.left + "," + tile2.sRect.bottom + "," + tile2.sRect.right, (float) (tile2.vRect.left + m29px(5)), (float) (tile2.vRect.top + m29px(15)), this.debugTextPaint);
                                }
                                i6 = i5;
                                i7 = i4;
                                i8 = i3;
                            }
                        }
                        i6 = i6;
                        i7 = i7;
                        i8 = i8;
                    }
                    i2 = i6;
                    i = 15;
                }
                if (this.debug) {
                    canvas2.drawText("Scale: " + String.format(Locale.ENGLISH, "%.2f", new Object[]{Float.valueOf(this.scale)}) + " (" + String.format(Locale.ENGLISH, "%.2f", new Object[]{Float.valueOf(minScale())}) + " - " + String.format(Locale.ENGLISH, "%.2f", new Object[]{Float.valueOf(this.maxScale)}) + ")", (float) m29px(5), (float) m29px(i), this.debugTextPaint);
                    canvas2.drawText("Translate: " + String.format(Locale.ENGLISH, "%.2f", new Object[]{Float.valueOf(this.vTranslate.x)}) + ":" + String.format(Locale.ENGLISH, "%.2f", new Object[]{Float.valueOf(this.vTranslate.y)}), (float) m29px(5), (float) m29px(30), this.debugTextPaint);
                    PointF center = getCenter();
                    canvas2.drawText("Source center: " + String.format(Locale.ENGLISH, "%.2f", new Object[]{Float.valueOf(center.x)}) + ":" + String.format(Locale.ENGLISH, "%.2f", new Object[]{Float.valueOf(center.y)}), (float) m29px(5), (float) m29px(45), this.debugTextPaint);
                    Anim anim3 = this.anim;
                    if (anim3 != null) {
                        PointF vCenterStart2 = sourceToViewCoord(anim3.sCenterStart);
                        PointF vCenterEndRequested = sourceToViewCoord(this.anim.sCenterEndRequested);
                        PointF vCenterEnd = sourceToViewCoord(this.anim.sCenterEnd);
                        canvas2.drawCircle(vCenterStart2.x, vCenterStart2.y, (float) m29px(10), this.debugLinePaint);
                        this.debugLinePaint.setColor(SupportMenu.CATEGORY_MASK);
                        canvas2.drawCircle(vCenterEndRequested.x, vCenterEndRequested.y, (float) m29px(20), this.debugLinePaint);
                        this.debugLinePaint.setColor(-16776961);
                        canvas2.drawCircle(vCenterEnd.x, vCenterEnd.y, (float) m29px(25), this.debugLinePaint);
                        this.debugLinePaint.setColor(-16711681);
                        canvas2.drawCircle((float) (getWidth() / 2), (float) (getHeight() / 2), (float) m29px(30), this.debugLinePaint);
                    }
                    if (this.vCenterStart != null) {
                        this.debugLinePaint.setColor(SupportMenu.CATEGORY_MASK);
                        canvas2.drawCircle(this.vCenterStart.x, this.vCenterStart.y, (float) m29px(20), this.debugLinePaint);
                    }
                    if (this.quickScaleSCenter != null) {
                        this.debugLinePaint.setColor(-16776961);
                        canvas2.drawCircle(sourceToViewX(this.quickScaleSCenter.x), sourceToViewY(this.quickScaleSCenter.y), (float) m29px(i2), this.debugLinePaint);
                    }
                    if (this.quickScaleVStart != null && this.isQuickScaling) {
                        this.debugLinePaint.setColor(-16711681);
                        canvas2.drawCircle(this.quickScaleVStart.x, this.quickScaleVStart.y, (float) m29px(30), this.debugLinePaint);
                    }
                    this.debugLinePaint.setColor(-65281);
                }
            }
        }
    }

    private void setMatrixArray(float[] array, float f0, float f1, float f2, float f3, float f4, float f5, float f6, float f7) {
        array[0] = f0;
        array[1] = f1;
        array[2] = f2;
        array[3] = f3;
        array[4] = f4;
        array[5] = f5;
        array[6] = f6;
        array[7] = f7;
    }

    private boolean isBaseLayerReady() {
        if (this.bitmap != null && !this.bitmapIsPreview) {
            return true;
        }
        Map<Integer, List<Tile>> map = this.tileMap;
        if (map == null) {
            return false;
        }
        boolean baseLayerReady = true;
        for (Map.Entry<Integer, List<Tile>> tileMapEntry : map.entrySet()) {
            if (tileMapEntry.getKey().intValue() == this.fullImageSampleSize) {
                for (Tile tile : tileMapEntry.getValue()) {
                    if (tile.loading || tile.bitmap == null) {
                        baseLayerReady = false;
                    }
                }
            }
        }
        return baseLayerReady;
    }

    private boolean checkReady() {
        boolean ready = getWidth() > 0 && getHeight() > 0 && this.sWidth > 0 && this.sHeight > 0 && (this.bitmap != null || isBaseLayerReady());
        if (!this.readySent && ready) {
            preDraw();
            this.readySent = true;
            onReady();
            OnImageEventListener onImageEventListener2 = this.onImageEventListener;
            if (onImageEventListener2 != null) {
                onImageEventListener2.onReady();
            }
        }
        return ready;
    }

    private boolean checkImageLoaded() {
        boolean imageLoaded = isBaseLayerReady();
        if (!this.imageLoadedSent && imageLoaded) {
            preDraw();
            this.imageLoadedSent = true;
            onImageLoaded();
            OnImageEventListener onImageEventListener2 = this.onImageEventListener;
            if (onImageEventListener2 != null) {
                onImageEventListener2.onImageLoaded();
            }
        }
        return imageLoaded;
    }

    private void createPaints() {
        if (this.bitmapPaint == null) {
            Paint paint = new Paint();
            this.bitmapPaint = paint;
            paint.setAntiAlias(true);
            this.bitmapPaint.setFilterBitmap(true);
            this.bitmapPaint.setDither(true);
        }
        if ((this.debugTextPaint == null || this.debugLinePaint == null) && this.debug) {
            Paint paint2 = new Paint();
            this.debugTextPaint = paint2;
            paint2.setTextSize((float) m29px(12));
            this.debugTextPaint.setColor(-65281);
            this.debugTextPaint.setStyle(Paint.Style.FILL);
            Paint paint3 = new Paint();
            this.debugLinePaint = paint3;
            paint3.setColor(-65281);
            this.debugLinePaint.setStyle(Paint.Style.STROKE);
            this.debugLinePaint.setStrokeWidth((float) m29px(1));
        }
    }

    private synchronized void initialiseBaseLayer(Point maxTileDimensions) {
        debug("initialiseBaseLayer maxTileDimensions=%dx%d", Integer.valueOf(maxTileDimensions.x), Integer.valueOf(maxTileDimensions.y));
        ScaleAndTranslate scaleAndTranslate = new ScaleAndTranslate(0.0f, new PointF(0.0f, 0.0f));
        this.satTemp = scaleAndTranslate;
        fitToBounds(true, scaleAndTranslate);
        int calculateInSampleSize = calculateInSampleSize(this.satTemp.scale);
        this.fullImageSampleSize = calculateInSampleSize;
        if (calculateInSampleSize > 1) {
            this.fullImageSampleSize = calculateInSampleSize / 2;
        }
        if (this.fullImageSampleSize != 1 || this.sRegion != null || sWidth() >= maxTileDimensions.x || sHeight() >= maxTileDimensions.y) {
            initialiseTileMap(maxTileDimensions);
            for (Tile baseTile : this.tileMap.get(Integer.valueOf(this.fullImageSampleSize))) {
                execute(new TileLoadTask(this, this.decoder, baseTile));
            }
            refreshRequiredTiles(true);
        } else {
            this.decoder.recycle();
            this.decoder = null;
            execute(new BitmapLoadTask(this, getContext(), this.bitmapDecoderFactory, this.uri, false));
        }
    }

    private void refreshRequiredTiles(boolean load) {
        if (this.decoder != null && this.tileMap != null) {
            int sampleSize = Math.min(this.fullImageSampleSize, calculateInSampleSize(this.scale));
            for (Map.Entry<Integer, List<Tile>> tileMapEntry : this.tileMap.entrySet()) {
                for (Tile tile : tileMapEntry.getValue()) {
                    if (tile.sampleSize < sampleSize || (tile.sampleSize > sampleSize && tile.sampleSize != this.fullImageSampleSize)) {
                        boolean unused = tile.visible = false;
                        if (tile.bitmap != null) {
                            tile.bitmap.recycle();
                            Bitmap unused2 = tile.bitmap = null;
                        }
                    }
                    if (tile.sampleSize == sampleSize) {
                        if (tileVisible(tile)) {
                            boolean unused3 = tile.visible = true;
                            if (!tile.loading && tile.bitmap == null && load) {
                                execute(new TileLoadTask(this, this.decoder, tile));
                            }
                        } else if (tile.sampleSize != this.fullImageSampleSize) {
                            boolean unused4 = tile.visible = false;
                            if (tile.bitmap != null) {
                                tile.bitmap.recycle();
                                Bitmap unused5 = tile.bitmap = null;
                            }
                        }
                    } else if (tile.sampleSize == this.fullImageSampleSize) {
                        boolean unused6 = tile.visible = true;
                    }
                }
            }
        }
    }

    private boolean tileVisible(Tile tile) {
        return viewToSourceX(0.0f) <= ((float) tile.sRect.right) && ((float) tile.sRect.left) <= viewToSourceX((float) getWidth()) && viewToSourceY(0.0f) <= ((float) tile.sRect.bottom) && ((float) tile.sRect.top) <= viewToSourceY((float) getHeight());
    }

    private void preDraw() {
        Float f;
        if (getWidth() != 0 && getHeight() != 0 && this.sWidth > 0 && this.sHeight > 0) {
            if (!(this.sPendingCenter == null || (f = this.pendingScale) == null)) {
                this.scale = f.floatValue();
                if (this.vTranslate == null) {
                    this.vTranslate = new PointF();
                }
                this.vTranslate.x = ((float) (getWidth() / 2)) - (this.scale * this.sPendingCenter.x);
                this.vTranslate.y = ((float) (getHeight() / 2)) - (this.scale * this.sPendingCenter.y);
                this.sPendingCenter = null;
                this.pendingScale = null;
                fitToBounds(true);
                refreshRequiredTiles(true);
            }
            fitToBounds(false);
        }
    }

    private int calculateInSampleSize(float scale2) {
        if (this.minimumTileDpi > 0) {
            DisplayMetrics metrics = getResources().getDisplayMetrics();
            scale2 *= ((float) this.minimumTileDpi) / ((metrics.xdpi + metrics.ydpi) / 2.0f);
        }
        int reqWidth = (int) (((float) sWidth()) * scale2);
        int reqHeight = (int) (((float) sHeight()) * scale2);
        int inSampleSize = 1;
        if (reqWidth == 0 || reqHeight == 0) {
            return 32;
        }
        if (sHeight() > reqHeight || sWidth() > reqWidth) {
            int heightRatio = Math.round(((float) sHeight()) / ((float) reqHeight));
            int widthRatio = Math.round(((float) sWidth()) / ((float) reqWidth));
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        int power = 1;
        while (power * 2 < inSampleSize) {
            power *= 2;
        }
        return power;
    }

    /* access modifiers changed from: private */
    public void fitToBounds(boolean center, ScaleAndTranslate sat) {
        float maxTx;
        float maxTy;
        if (this.panLimit == 2 && isReady()) {
            center = false;
        }
        PointF vTranslate2 = sat.vTranslate;
        float scale2 = limitedScale(sat.scale);
        float scaleWidth = ((float) sWidth()) * scale2;
        float scaleHeight = ((float) sHeight()) * scale2;
        if (this.panLimit == 3 && isReady()) {
            vTranslate2.x = Math.max(vTranslate2.x, ((float) (getWidth() / 2)) - scaleWidth);
            vTranslate2.y = Math.max(vTranslate2.y, ((float) (getHeight() / 2)) - scaleHeight);
        } else if (center) {
            vTranslate2.x = Math.max(vTranslate2.x, ((float) getWidth()) - scaleWidth);
            vTranslate2.y = Math.max(vTranslate2.y, ((float) getHeight()) - scaleHeight);
        } else {
            vTranslate2.x = Math.max(vTranslate2.x, -scaleWidth);
            vTranslate2.y = Math.max(vTranslate2.y, -scaleHeight);
        }
        float yPaddingRatio = 0.5f;
        float xPaddingRatio = (getPaddingLeft() > 0 || getPaddingRight() > 0) ? ((float) getPaddingLeft()) / ((float) (getPaddingLeft() + getPaddingRight())) : 0.5f;
        if (getPaddingTop() > 0 || getPaddingBottom() > 0) {
            yPaddingRatio = ((float) getPaddingTop()) / ((float) (getPaddingTop() + getPaddingBottom()));
        }
        if (this.panLimit == 3 && isReady()) {
            maxTx = (float) Math.max(0, getWidth() / 2);
            maxTy = (float) Math.max(0, getHeight() / 2);
        } else if (center) {
            maxTx = Math.max(0.0f, (((float) getWidth()) - scaleWidth) * xPaddingRatio);
            maxTy = Math.max(0.0f, (((float) getHeight()) - scaleHeight) * yPaddingRatio);
        } else {
            maxTx = (float) Math.max(0, getWidth());
            maxTy = (float) Math.max(0, getHeight());
        }
        vTranslate2.x = Math.min(vTranslate2.x, maxTx);
        vTranslate2.y = Math.min(vTranslate2.y, maxTy);
        float unused = sat.scale = scale2;
    }

    private void fitToBounds(boolean center) {
        boolean init = false;
        if (this.vTranslate == null) {
            init = true;
            this.vTranslate = new PointF(0.0f, 0.0f);
        }
        if (this.satTemp == null) {
            this.satTemp = new ScaleAndTranslate(0.0f, new PointF(0.0f, 0.0f));
        }
        float unused = this.satTemp.scale = this.scale;
        this.satTemp.vTranslate.set(this.vTranslate);
        fitToBounds(center, this.satTemp);
        this.scale = this.satTemp.scale;
        this.vTranslate.set(this.satTemp.vTranslate);
        if (init && this.minimumScaleType != 4) {
            this.vTranslate.set(vTranslateForSCenter((float) (sWidth() / 2), (float) (sHeight() / 2), this.scale));
        }
    }

    private void initialiseTileMap(Point maxTileDimensions) {
        Point point = maxTileDimensions;
        boolean z = false;
        boolean z2 = true;
        debug("initialiseTileMap maxTileDimensions=%dx%d", Integer.valueOf(point.x), Integer.valueOf(point.y));
        this.tileMap = new LinkedHashMap();
        int sampleSize = this.fullImageSampleSize;
        int xTiles = 1;
        int yTiles = 1;
        while (true) {
            int sTileWidth = sWidth() / xTiles;
            int sTileHeight = sHeight() / yTiles;
            int subTileWidth = sTileWidth / sampleSize;
            int subTileHeight = sTileHeight / sampleSize;
            while (true) {
                if (subTileWidth + xTiles + (z2 ? 1 : 0) > point.x || (((double) subTileWidth) > ((double) getWidth()) * 1.25d && sampleSize < this.fullImageSampleSize)) {
                    int i = sTileWidth;
                    xTiles++;
                    sTileWidth = sWidth() / xTiles;
                    subTileWidth = sTileWidth / sampleSize;
                    z2 = z2;
                    point = maxTileDimensions;
                }
            }
            while (true) {
                if (subTileHeight + yTiles + (z2 ? 1 : 0) > point.y || (((double) subTileHeight) > ((double) getHeight()) * 1.25d && sampleSize < this.fullImageSampleSize)) {
                    yTiles++;
                    sTileHeight = sHeight() / yTiles;
                    subTileHeight = sTileHeight / sampleSize;
                    z2 = z2;
                    sTileWidth = sTileWidth;
                    point = maxTileDimensions;
                }
            }
            List<Tile> tileGrid = new ArrayList<>(xTiles * yTiles);
            int x = 0;
            while (x < xTiles) {
                int y = 0;
                while (y < yTiles) {
                    Tile tile = new Tile();
                    int unused = tile.sampleSize = sampleSize;
                    boolean unused2 = tile.visible = sampleSize == this.fullImageSampleSize ? z2 : z;
                    int sTileWidth2 = sTileWidth;
                    Rect unused3 = tile.sRect = new Rect(x * sTileWidth, y * sTileHeight, x == xTiles + -1 ? sWidth() : (x + 1) * sTileWidth, y == yTiles + -1 ? sHeight() : (y + 1) * sTileHeight);
                    z = false;
                    Rect unused4 = tile.vRect = new Rect(0, 0, 0, 0);
                    Rect unused5 = tile.fileSRect = new Rect(tile.sRect);
                    tileGrid.add(tile);
                    y++;
                    Point point2 = maxTileDimensions;
                    sTileWidth = sTileWidth2;
                    z2 = true;
                }
                x++;
                Point point3 = maxTileDimensions;
                z2 = true;
            }
            this.tileMap.put(Integer.valueOf(sampleSize), tileGrid);
            if (sampleSize != 1) {
                sampleSize /= 2;
                z2 = true;
                point = maxTileDimensions;
            } else {
                return;
            }
        }
    }

    private static class TilesInitTask extends AsyncTask<Void, Void, int[]> {
        private final WeakReference<Context> contextRef;
        private ImageRegionDecoder decoder;
        private final WeakReference<DecoderFactory<? extends ImageRegionDecoder>> decoderFactoryRef;
        private Exception exception;
        private final Uri source;
        private final WeakReference<SubsamplingScaleImageView> viewRef;

        TilesInitTask(SubsamplingScaleImageView view, Context context, DecoderFactory<? extends ImageRegionDecoder> decoderFactory, Uri source2) {
            this.viewRef = new WeakReference<>(view);
            this.contextRef = new WeakReference<>(context);
            this.decoderFactoryRef = new WeakReference<>(decoderFactory);
            this.source = source2;
        }

        /* access modifiers changed from: protected */
        public int[] doInBackground(Void... params) {
            try {
                String sourceUri = this.source.toString();
                Context context = (Context) this.contextRef.get();
                DecoderFactory<? extends ImageRegionDecoder> decoderFactory = (DecoderFactory) this.decoderFactoryRef.get();
                SubsamplingScaleImageView view = (SubsamplingScaleImageView) this.viewRef.get();
                if (context == null || decoderFactory == null || view == null) {
                    return null;
                }
                view.debug("TilesInitTask.doInBackground", new Object[0]);
                ImageRegionDecoder imageRegionDecoder = (ImageRegionDecoder) decoderFactory.make();
                this.decoder = imageRegionDecoder;
                Point dimensions = imageRegionDecoder.init(context, this.source);
                int sWidth = dimensions.x;
                int sHeight = dimensions.y;
                int exifOrientation = view.getExifOrientation(context, sourceUri);
                if (view.sRegion != null) {
                    view.sRegion.left = Math.max(0, view.sRegion.left);
                    view.sRegion.top = Math.max(0, view.sRegion.top);
                    view.sRegion.right = Math.min(sWidth, view.sRegion.right);
                    view.sRegion.bottom = Math.min(sHeight, view.sRegion.bottom);
                    sWidth = view.sRegion.width();
                    sHeight = view.sRegion.height();
                }
                return new int[]{sWidth, sHeight, exifOrientation};
            } catch (Exception e) {
                Log.e(SubsamplingScaleImageView.TAG, "Failed to initialise bitmap decoder", e);
                this.exception = e;
                return null;
            }
        }

        /* access modifiers changed from: protected */
        public void onPostExecute(int[] xyo) {
            SubsamplingScaleImageView view = (SubsamplingScaleImageView) this.viewRef.get();
            if (view != null) {
                ImageRegionDecoder imageRegionDecoder = this.decoder;
                if (imageRegionDecoder != null && xyo != null && xyo.length == 3) {
                    view.onTilesInited(imageRegionDecoder, xyo[0], xyo[1], xyo[2]);
                } else if (this.exception != null && view.onImageEventListener != null) {
                    view.onImageEventListener.onImageLoadError(this.exception);
                }
            }
        }
    }

    /* access modifiers changed from: private */
    public synchronized void onTilesInited(ImageRegionDecoder decoder2, int sWidth2, int sHeight2, int sOrientation2) {
        int i;
        int i2;
        int i3;
        debug("onTilesInited sWidth=%d, sHeight=%d, sOrientation=%d", Integer.valueOf(sWidth2), Integer.valueOf(sHeight2), Integer.valueOf(this.orientation));
        int i4 = this.sWidth;
        if (i4 > 0 && (i3 = this.sHeight) > 0 && !(i4 == sWidth2 && i3 == sHeight2)) {
            reset(false);
            Bitmap bitmap2 = this.bitmap;
            if (bitmap2 != null) {
                if (!this.bitmapIsCached) {
                    bitmap2.recycle();
                }
                this.bitmap = null;
                OnImageEventListener onImageEventListener2 = this.onImageEventListener;
                if (onImageEventListener2 != null && this.bitmapIsCached) {
                    onImageEventListener2.onPreviewReleased();
                }
                this.bitmapIsPreview = false;
                this.bitmapIsCached = false;
            }
        }
        this.decoder = decoder2;
        this.sWidth = sWidth2;
        this.sHeight = sHeight2;
        this.sOrientation = sOrientation2;
        checkReady();
        if (!checkImageLoaded() && (i = this.maxTileWidth) > 0 && i != Integer.MAX_VALUE && (i2 = this.maxTileHeight) > 0 && i2 != Integer.MAX_VALUE && getWidth() > 0 && getHeight() > 0) {
            initialiseBaseLayer(new Point(this.maxTileWidth, this.maxTileHeight));
        }
        invalidate();
        requestLayout();
    }

    private static class TileLoadTask extends AsyncTask<Void, Void, Bitmap> {
        private final WeakReference<ImageRegionDecoder> decoderRef;
        private Exception exception;
        private final WeakReference<Tile> tileRef;
        private final WeakReference<SubsamplingScaleImageView> viewRef;

        TileLoadTask(SubsamplingScaleImageView view, ImageRegionDecoder decoder, Tile tile) {
            this.viewRef = new WeakReference<>(view);
            this.decoderRef = new WeakReference<>(decoder);
            this.tileRef = new WeakReference<>(tile);
            boolean unused = tile.loading = true;
        }

        /* access modifiers changed from: protected */
        public Bitmap doInBackground(Void... params) {
            SubsamplingScaleImageView view;
            try {
                view = (SubsamplingScaleImageView) this.viewRef.get();
                ImageRegionDecoder decoder = (ImageRegionDecoder) this.decoderRef.get();
                Tile tile = (Tile) this.tileRef.get();
                if (decoder != null && tile != null && view != null && decoder.isReady() && tile.visible) {
                    view.debug("TileLoadTask.doInBackground, tile.sRect=%s, tile.sampleSize=%d", tile.sRect, Integer.valueOf(tile.sampleSize));
                    view.decoderLock.readLock().lock();
                    if (decoder.isReady()) {
                        view.fileSRect(tile.sRect, tile.fileSRect);
                        if (view.sRegion != null) {
                            tile.fileSRect.offset(view.sRegion.left, view.sRegion.top);
                        }
                        Bitmap decodeRegion = decoder.decodeRegion(tile.fileSRect, tile.sampleSize);
                        view.decoderLock.readLock().unlock();
                        return decodeRegion;
                    }
                    boolean unused = tile.loading = false;
                    view.decoderLock.readLock().unlock();
                    return null;
                } else if (tile == null) {
                    return null;
                } else {
                    boolean unused2 = tile.loading = false;
                    return null;
                }
            } catch (Exception e) {
                Log.e(SubsamplingScaleImageView.TAG, "Failed to decode tile", e);
                this.exception = e;
                return null;
            } catch (OutOfMemoryError e2) {
                Log.e(SubsamplingScaleImageView.TAG, "Failed to decode tile - OutOfMemoryError", e2);
                this.exception = new RuntimeException(e2);
                return null;
            } catch (Throwable th) {
                view.decoderLock.readLock().unlock();
                throw th;
            }
        }

        /* access modifiers changed from: protected */
        public void onPostExecute(Bitmap bitmap) {
            SubsamplingScaleImageView subsamplingScaleImageView = (SubsamplingScaleImageView) this.viewRef.get();
            Tile tile = (Tile) this.tileRef.get();
            if (subsamplingScaleImageView != null && tile != null) {
                if (bitmap != null) {
                    Bitmap unused = tile.bitmap = bitmap;
                    boolean unused2 = tile.loading = false;
                    subsamplingScaleImageView.onTileLoaded();
                } else if (this.exception != null && subsamplingScaleImageView.onImageEventListener != null) {
                    subsamplingScaleImageView.onImageEventListener.onTileLoadError(this.exception);
                }
            }
        }
    }

    /* access modifiers changed from: private */
    public synchronized void onTileLoaded() {
        Bitmap bitmap2;
        debug("onTileLoaded", new Object[0]);
        checkReady();
        checkImageLoaded();
        if (isBaseLayerReady() && (bitmap2 = this.bitmap) != null) {
            if (!this.bitmapIsCached) {
                bitmap2.recycle();
            }
            this.bitmap = null;
            OnImageEventListener onImageEventListener2 = this.onImageEventListener;
            if (onImageEventListener2 != null && this.bitmapIsCached) {
                onImageEventListener2.onPreviewReleased();
            }
            this.bitmapIsPreview = false;
            this.bitmapIsCached = false;
        }
        invalidate();
    }

    private static class BitmapLoadTask extends AsyncTask<Void, Void, Integer> {
        private Bitmap bitmap;
        private final WeakReference<Context> contextRef;
        private final WeakReference<DecoderFactory<? extends ImageDecoder>> decoderFactoryRef;
        private Exception exception;
        private final boolean preview;
        private final Uri source;
        private final WeakReference<SubsamplingScaleImageView> viewRef;

        BitmapLoadTask(SubsamplingScaleImageView view, Context context, DecoderFactory<? extends ImageDecoder> decoderFactory, Uri source2, boolean preview2) {
            this.viewRef = new WeakReference<>(view);
            this.contextRef = new WeakReference<>(context);
            this.decoderFactoryRef = new WeakReference<>(decoderFactory);
            this.source = source2;
            this.preview = preview2;
        }

        /* access modifiers changed from: protected */
        public Integer doInBackground(Void... params) {
            try {
                String sourceUri = this.source.toString();
                Context context = (Context) this.contextRef.get();
                DecoderFactory<? extends ImageDecoder> decoderFactory = (DecoderFactory) this.decoderFactoryRef.get();
                SubsamplingScaleImageView view = (SubsamplingScaleImageView) this.viewRef.get();
                if (context == null || decoderFactory == null || view == null) {
                    return null;
                }
                view.debug("BitmapLoadTask.doInBackground", new Object[0]);
                this.bitmap = ((ImageDecoder) decoderFactory.make()).decode(context, this.source);
                return Integer.valueOf(view.getExifOrientation(context, sourceUri));
            } catch (Exception e) {
                Log.e(SubsamplingScaleImageView.TAG, "Failed to load bitmap", e);
                this.exception = e;
                return null;
            } catch (OutOfMemoryError e2) {
                Log.e(SubsamplingScaleImageView.TAG, "Failed to load bitmap - OutOfMemoryError", e2);
                this.exception = new RuntimeException(e2);
                return null;
            }
        }

        /* access modifiers changed from: protected */
        public void onPostExecute(Integer orientation) {
            SubsamplingScaleImageView subsamplingScaleImageView = (SubsamplingScaleImageView) this.viewRef.get();
            if (subsamplingScaleImageView != null) {
                Bitmap bitmap2 = this.bitmap;
                if (bitmap2 == null || orientation == null) {
                    if (this.exception != null && subsamplingScaleImageView.onImageEventListener != null) {
                        if (this.preview) {
                            subsamplingScaleImageView.onImageEventListener.onPreviewLoadError(this.exception);
                        } else {
                            subsamplingScaleImageView.onImageEventListener.onImageLoadError(this.exception);
                        }
                    }
                } else if (this.preview) {
                    subsamplingScaleImageView.onPreviewLoaded(bitmap2);
                } else {
                    subsamplingScaleImageView.onImageLoaded(bitmap2, orientation.intValue(), false);
                }
            }
        }
    }

    /* access modifiers changed from: private */
    /* JADX WARNING: Code restructure failed: missing block: B:16:0x0041, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized void onPreviewLoaded(android.graphics.Bitmap r5) {
        /*
            r4 = this;
            monitor-enter(r4)
            java.lang.String r0 = "onPreviewLoaded"
            r1 = 0
            java.lang.Object[] r1 = new java.lang.Object[r1]     // Catch:{ all -> 0x0047 }
            r4.debug(r0, r1)     // Catch:{ all -> 0x0047 }
            android.graphics.Bitmap r0 = r4.bitmap     // Catch:{ all -> 0x0047 }
            if (r0 != 0) goto L_0x0042
            boolean r0 = r4.imageLoadedSent     // Catch:{ all -> 0x0047 }
            if (r0 == 0) goto L_0x0012
            goto L_0x0042
        L_0x0012:
            android.graphics.Rect r0 = r4.pRegion     // Catch:{ all -> 0x0047 }
            if (r0 == 0) goto L_0x002f
            int r0 = r0.left     // Catch:{ all -> 0x0047 }
            android.graphics.Rect r1 = r4.pRegion     // Catch:{ all -> 0x0047 }
            int r1 = r1.top     // Catch:{ all -> 0x0047 }
            android.graphics.Rect r2 = r4.pRegion     // Catch:{ all -> 0x0047 }
            int r2 = r2.width()     // Catch:{ all -> 0x0047 }
            android.graphics.Rect r3 = r4.pRegion     // Catch:{ all -> 0x0047 }
            int r3 = r3.height()     // Catch:{ all -> 0x0047 }
            android.graphics.Bitmap r0 = android.graphics.Bitmap.createBitmap(r5, r0, r1, r2, r3)     // Catch:{ all -> 0x0047 }
            r4.bitmap = r0     // Catch:{ all -> 0x0047 }
            goto L_0x0031
        L_0x002f:
            r4.bitmap = r5     // Catch:{ all -> 0x0047 }
        L_0x0031:
            r0 = 1
            r4.bitmapIsPreview = r0     // Catch:{ all -> 0x0047 }
            boolean r0 = r4.checkReady()     // Catch:{ all -> 0x0047 }
            if (r0 == 0) goto L_0x0040
            r4.invalidate()     // Catch:{ all -> 0x0047 }
            r4.requestLayout()     // Catch:{ all -> 0x0047 }
        L_0x0040:
            monitor-exit(r4)
            return
        L_0x0042:
            r5.recycle()     // Catch:{ all -> 0x0047 }
            monitor-exit(r4)
            return
        L_0x0047:
            r5 = move-exception
            monitor-exit(r4)
            throw r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView.onPreviewLoaded(android.graphics.Bitmap):void");
    }

    /* access modifiers changed from: private */
    public synchronized void onImageLoaded(Bitmap bitmap2, int sOrientation2, boolean bitmapIsCached2) {
        OnImageEventListener onImageEventListener2;
        debug("onImageLoaded", new Object[0]);
        int i = this.sWidth;
        if (i > 0 && this.sHeight > 0 && !(i == bitmap2.getWidth() && this.sHeight == bitmap2.getHeight())) {
            reset(false);
        }
        Bitmap bitmap3 = this.bitmap;
        if (bitmap3 != null && !this.bitmapIsCached) {
            bitmap3.recycle();
        }
        if (!(this.bitmap == null || !this.bitmapIsCached || (onImageEventListener2 = this.onImageEventListener) == null)) {
            onImageEventListener2.onPreviewReleased();
        }
        this.bitmapIsPreview = false;
        this.bitmapIsCached = bitmapIsCached2;
        this.bitmap = bitmap2;
        this.sWidth = bitmap2.getWidth();
        this.sHeight = bitmap2.getHeight();
        this.sOrientation = sOrientation2;
        boolean ready = checkReady();
        boolean imageLoaded = checkImageLoaded();
        if (ready || imageLoaded) {
            invalidate();
            requestLayout();
        }
    }

    /* access modifiers changed from: private */
    public int getExifOrientation(Context context, String sourceUri) {
        int exifOrientation = 0;
        if (sourceUri.startsWith("content")) {
            Cursor cursor = null;
            try {
                cursor = context.getContentResolver().query(Uri.parse(sourceUri), new String[]{"orientation"}, (String) null, (String[]) null, (String) null);
                if (cursor != null && cursor.moveToFirst()) {
                    int orientation2 = cursor.getInt(0);
                    if (!VALID_ORIENTATIONS.contains(Integer.valueOf(orientation2)) || orientation2 == -1) {
                        Log.w(TAG, "Unsupported orientation: " + orientation2);
                    } else {
                        exifOrientation = orientation2;
                    }
                }
                if (cursor == null) {
                    return exifOrientation;
                }
            } catch (Exception e) {
                Log.w(TAG, "Could not get orientation of image from media store");
                if (cursor == null) {
                    return 0;
                }
            } catch (Throwable th) {
                if (cursor != null) {
                    cursor.close();
                }
                throw th;
            }
            cursor.close();
            return exifOrientation;
        } else if (!sourceUri.startsWith("file:///") || sourceUri.startsWith("file:///android_asset/")) {
            return 0;
        } else {
            try {
                int orientationAttr = new ExifInterface(sourceUri.substring("file:///".length() - 1)).getAttributeInt(ExifInterface.TAG_ORIENTATION, 1);
                if (orientationAttr != 1) {
                    if (orientationAttr != 0) {
                        if (orientationAttr == 6) {
                            return 90;
                        }
                        if (orientationAttr == 3) {
                            return 180;
                        }
                        if (orientationAttr == 8) {
                            return 270;
                        }
                        Log.w(TAG, "Unsupported EXIF orientation: " + orientationAttr);
                        return 0;
                    }
                }
                return 0;
            } catch (Exception e2) {
                Log.w(TAG, "Could not get EXIF orientation of image");
                return 0;
            }
        }
    }

    private void execute(AsyncTask<Void, Void, ?> asyncTask) {
        asyncTask.executeOnExecutor(this.executor, new Void[0]);
    }

    private static class Tile {
        /* access modifiers changed from: private */
        public Bitmap bitmap;
        /* access modifiers changed from: private */
        public Rect fileSRect;
        /* access modifiers changed from: private */
        public boolean loading;
        /* access modifiers changed from: private */
        public Rect sRect;
        /* access modifiers changed from: private */
        public int sampleSize;
        /* access modifiers changed from: private */
        public Rect vRect;
        /* access modifiers changed from: private */
        public boolean visible;

        private Tile() {
        }
    }

    private static class Anim {
        /* access modifiers changed from: private */
        public long duration;
        /* access modifiers changed from: private */
        public int easing;
        /* access modifiers changed from: private */
        public boolean interruptible;
        /* access modifiers changed from: private */
        public OnAnimationEventListener listener;
        /* access modifiers changed from: private */
        public int origin;
        /* access modifiers changed from: private */
        public PointF sCenterEnd;
        /* access modifiers changed from: private */
        public PointF sCenterEndRequested;
        /* access modifiers changed from: private */
        public PointF sCenterStart;
        /* access modifiers changed from: private */
        public float scaleEnd;
        /* access modifiers changed from: private */
        public float scaleStart;
        /* access modifiers changed from: private */
        public long time;
        /* access modifiers changed from: private */
        public PointF vFocusEnd;
        /* access modifiers changed from: private */
        public PointF vFocusStart;

        private Anim() {
            this.duration = 500;
            this.interruptible = true;
            this.easing = 2;
            this.origin = 1;
            this.time = System.currentTimeMillis();
        }
    }

    private static class ScaleAndTranslate {
        /* access modifiers changed from: private */
        public float scale;
        /* access modifiers changed from: private */
        public final PointF vTranslate;

        private ScaleAndTranslate(float scale2, PointF vTranslate2) {
            this.scale = scale2;
            this.vTranslate = vTranslate2;
        }
    }

    private void restoreState(ImageViewState state) {
        if (state != null && VALID_ORIENTATIONS.contains(Integer.valueOf(state.getOrientation()))) {
            this.orientation = state.getOrientation();
            this.pendingScale = Float.valueOf(state.getScale());
            this.sPendingCenter = state.getCenter();
            invalidate();
        }
    }

    public void setMaxTileSize(int maxPixels) {
        this.maxTileWidth = maxPixels;
        this.maxTileHeight = maxPixels;
    }

    public void setMaxTileSize(int maxPixelsX, int maxPixelsY) {
        this.maxTileWidth = maxPixelsX;
        this.maxTileHeight = maxPixelsY;
    }

    private Point getMaxBitmapDimensions(Canvas canvas) {
        return new Point(Math.min(canvas.getMaximumBitmapWidth(), this.maxTileWidth), Math.min(canvas.getMaximumBitmapHeight(), this.maxTileHeight));
    }

    private int sWidth() {
        int rotation = getRequiredRotation();
        if (rotation == 90 || rotation == 270) {
            return this.sHeight;
        }
        return this.sWidth;
    }

    private int sHeight() {
        int rotation = getRequiredRotation();
        if (rotation == 90 || rotation == 270) {
            return this.sWidth;
        }
        return this.sHeight;
    }

    /* access modifiers changed from: private */
    public void fileSRect(Rect sRect2, Rect target) {
        if (getRequiredRotation() == 0) {
            target.set(sRect2);
        } else if (getRequiredRotation() == 90) {
            target.set(sRect2.top, this.sHeight - sRect2.right, sRect2.bottom, this.sHeight - sRect2.left);
        } else if (getRequiredRotation() == 180) {
            target.set(this.sWidth - sRect2.right, this.sHeight - sRect2.bottom, this.sWidth - sRect2.left, this.sHeight - sRect2.top);
        } else {
            target.set(this.sWidth - sRect2.bottom, sRect2.left, this.sWidth - sRect2.top, sRect2.right);
        }
    }

    private int getRequiredRotation() {
        int i = this.orientation;
        if (i == -1) {
            return this.sOrientation;
        }
        return i;
    }

    private float distance(float x0, float x1, float y0, float y1) {
        float x = x0 - x1;
        float y = y0 - y1;
        return (float) Math.sqrt((double) ((x * x) + (y * y)));
    }

    public void recycle() {
        reset(true);
        this.bitmapPaint = null;
        this.debugTextPaint = null;
        this.debugLinePaint = null;
        this.tileBgPaint = null;
    }

    private float viewToSourceX(float vx) {
        PointF pointF = this.vTranslate;
        if (pointF == null) {
            return Float.NaN;
        }
        return (vx - pointF.x) / this.scale;
    }

    private float viewToSourceY(float vy) {
        PointF pointF = this.vTranslate;
        if (pointF == null) {
            return Float.NaN;
        }
        return (vy - pointF.y) / this.scale;
    }

    public void viewToFileRect(Rect vRect, Rect fRect) {
        if (this.vTranslate != null && this.readySent) {
            fRect.set((int) viewToSourceX((float) vRect.left), (int) viewToSourceY((float) vRect.top), (int) viewToSourceX((float) vRect.right), (int) viewToSourceY((float) vRect.bottom));
            fileSRect(fRect, fRect);
            fRect.set(Math.max(0, fRect.left), Math.max(0, fRect.top), Math.min(this.sWidth, fRect.right), Math.min(this.sHeight, fRect.bottom));
            Rect rect = this.sRegion;
            if (rect != null) {
                fRect.offset(rect.left, this.sRegion.top);
            }
        }
    }

    public void visibleFileRect(Rect fRect) {
        if (this.vTranslate != null && this.readySent) {
            fRect.set(0, 0, getWidth(), getHeight());
            viewToFileRect(fRect, fRect);
        }
    }

    public final PointF viewToSourceCoord(PointF vxy) {
        return viewToSourceCoord(vxy.x, vxy.y, new PointF());
    }

    public final PointF viewToSourceCoord(float vx, float vy) {
        return viewToSourceCoord(vx, vy, new PointF());
    }

    public final PointF viewToSourceCoord(PointF vxy, PointF sTarget) {
        return viewToSourceCoord(vxy.x, vxy.y, sTarget);
    }

    public final PointF viewToSourceCoord(float vx, float vy, PointF sTarget) {
        if (this.vTranslate == null) {
            return null;
        }
        sTarget.set(viewToSourceX(vx), viewToSourceY(vy));
        return sTarget;
    }

    private float sourceToViewX(float sx) {
        PointF pointF = this.vTranslate;
        if (pointF == null) {
            return Float.NaN;
        }
        return (this.scale * sx) + pointF.x;
    }

    private float sourceToViewY(float sy) {
        PointF pointF = this.vTranslate;
        if (pointF == null) {
            return Float.NaN;
        }
        return (this.scale * sy) + pointF.y;
    }

    public final PointF sourceToViewCoord(PointF sxy) {
        return sourceToViewCoord(sxy.x, sxy.y, new PointF());
    }

    public final PointF sourceToViewCoord(float sx, float sy) {
        return sourceToViewCoord(sx, sy, new PointF());
    }

    public final PointF sourceToViewCoord(PointF sxy, PointF vTarget) {
        return sourceToViewCoord(sxy.x, sxy.y, vTarget);
    }

    public final PointF sourceToViewCoord(float sx, float sy, PointF vTarget) {
        if (this.vTranslate == null) {
            return null;
        }
        vTarget.set(sourceToViewX(sx), sourceToViewY(sy));
        return vTarget;
    }

    private void sourceToViewRect(Rect sRect2, Rect vTarget) {
        vTarget.set((int) sourceToViewX((float) sRect2.left), (int) sourceToViewY((float) sRect2.top), (int) sourceToViewX((float) sRect2.right), (int) sourceToViewY((float) sRect2.bottom));
    }

    private PointF vTranslateForSCenter(float sCenterX, float sCenterY, float scale2) {
        int vxCenter = getPaddingLeft() + (((getWidth() - getPaddingRight()) - getPaddingLeft()) / 2);
        int vyCenter = getPaddingTop() + (((getHeight() - getPaddingBottom()) - getPaddingTop()) / 2);
        if (this.satTemp == null) {
            this.satTemp = new ScaleAndTranslate(0.0f, new PointF(0.0f, 0.0f));
        }
        float unused = this.satTemp.scale = scale2;
        this.satTemp.vTranslate.set(((float) vxCenter) - (sCenterX * scale2), ((float) vyCenter) - (sCenterY * scale2));
        fitToBounds(true, this.satTemp);
        return this.satTemp.vTranslate;
    }

    /* access modifiers changed from: private */
    public PointF limitedSCenter(float sCenterX, float sCenterY, float scale2, PointF sTarget) {
        PointF vTranslate2 = vTranslateForSCenter(sCenterX, sCenterY, scale2);
        sTarget.set((((float) (getPaddingLeft() + (((getWidth() - getPaddingRight()) - getPaddingLeft()) / 2))) - vTranslate2.x) / scale2, (((float) (getPaddingTop() + (((getHeight() - getPaddingBottom()) - getPaddingTop()) / 2))) - vTranslate2.y) / scale2);
        return sTarget;
    }

    private float minScale() {
        int vPadding = getPaddingBottom() + getPaddingTop();
        int hPadding = getPaddingLeft() + getPaddingRight();
        int i = this.minimumScaleType;
        if (i == 2 || i == 4) {
            return Math.max(((float) (getWidth() - hPadding)) / ((float) sWidth()), ((float) (getHeight() - vPadding)) / ((float) sHeight()));
        }
        if (i == 3) {
            float f = this.minScale;
            if (f > 0.0f) {
                return f;
            }
        }
        return Math.min(((float) (getWidth() - hPadding)) / ((float) sWidth()), ((float) (getHeight() - vPadding)) / ((float) sHeight()));
    }

    /* access modifiers changed from: private */
    public float limitedScale(float targetScale) {
        return Math.min(this.maxScale, Math.max(minScale(), targetScale));
    }

    private float ease(int type, long time, float from, float change, long duration) {
        if (type == 1) {
            return easeOutQuad(time, from, change, duration);
        }
        if (type == 2) {
            return easeInOutQuad(time, from, change, duration);
        }
        throw new IllegalStateException("Unexpected easing type: " + type);
    }

    private float easeOutQuad(long time, float from, float change, long duration) {
        float progress = ((float) time) / ((float) duration);
        return ((-change) * progress * (progress - 2.0f)) + from;
    }

    private float easeInOutQuad(long time, float from, float change, long duration) {
        float timeF = ((float) time) / (((float) duration) / 2.0f);
        if (timeF < 1.0f) {
            return ((change / 2.0f) * timeF * timeF) + from;
        }
        float timeF2 = timeF - 1.0f;
        return (((-change) / 2.0f) * (((timeF2 - 2.0f) * timeF2) - 1.0f)) + from;
    }

    /* access modifiers changed from: private */
    public void debug(String message, Object... args) {
        if (this.debug) {
            Log.d(TAG, String.format(message, args));
        }
    }

    /* renamed from: px */
    private int m29px(int px) {
        return (int) (this.density * ((float) px));
    }

    public final void setRegionDecoderClass(Class<? extends ImageRegionDecoder> regionDecoderClass) {
        if (regionDecoderClass != null) {
            this.regionDecoderFactory = new CompatDecoderFactory(regionDecoderClass);
            return;
        }
        throw new IllegalArgumentException("Decoder class cannot be set to null");
    }

    public final void setRegionDecoderFactory(DecoderFactory<? extends ImageRegionDecoder> regionDecoderFactory2) {
        if (regionDecoderFactory2 != null) {
            this.regionDecoderFactory = regionDecoderFactory2;
            return;
        }
        throw new IllegalArgumentException("Decoder factory cannot be set to null");
    }

    public final void setBitmapDecoderClass(Class<? extends ImageDecoder> bitmapDecoderClass) {
        if (bitmapDecoderClass != null) {
            this.bitmapDecoderFactory = new CompatDecoderFactory(bitmapDecoderClass);
            return;
        }
        throw new IllegalArgumentException("Decoder class cannot be set to null");
    }

    public final void setBitmapDecoderFactory(DecoderFactory<? extends ImageDecoder> bitmapDecoderFactory2) {
        if (bitmapDecoderFactory2 != null) {
            this.bitmapDecoderFactory = bitmapDecoderFactory2;
            return;
        }
        throw new IllegalArgumentException("Decoder factory cannot be set to null");
    }

    public final void getPanRemaining(RectF vTarget) {
        if (isReady()) {
            float scaleWidth = this.scale * ((float) sWidth());
            float scaleHeight = this.scale * ((float) sHeight());
            int i = this.panLimit;
            if (i == 3) {
                vTarget.top = Math.max(0.0f, -(this.vTranslate.y - ((float) (getHeight() / 2))));
                vTarget.left = Math.max(0.0f, -(this.vTranslate.x - ((float) (getWidth() / 2))));
                vTarget.bottom = Math.max(0.0f, this.vTranslate.y - (((float) (getHeight() / 2)) - scaleHeight));
                vTarget.right = Math.max(0.0f, this.vTranslate.x - (((float) (getWidth() / 2)) - scaleWidth));
            } else if (i == 2) {
                vTarget.top = Math.max(0.0f, -(this.vTranslate.y - ((float) getHeight())));
                vTarget.left = Math.max(0.0f, -(this.vTranslate.x - ((float) getWidth())));
                vTarget.bottom = Math.max(0.0f, this.vTranslate.y + scaleHeight);
                vTarget.right = Math.max(0.0f, this.vTranslate.x + scaleWidth);
            } else {
                vTarget.top = Math.max(0.0f, -this.vTranslate.y);
                vTarget.left = Math.max(0.0f, -this.vTranslate.x);
                vTarget.bottom = Math.max(0.0f, (this.vTranslate.y + scaleHeight) - ((float) getHeight()));
                vTarget.right = Math.max(0.0f, (this.vTranslate.x + scaleWidth) - ((float) getWidth()));
            }
        }
    }

    public final void setPanLimit(int panLimit2) {
        if (VALID_PAN_LIMITS.contains(Integer.valueOf(panLimit2))) {
            this.panLimit = panLimit2;
            if (isReady()) {
                fitToBounds(true);
                invalidate();
                return;
            }
            return;
        }
        throw new IllegalArgumentException("Invalid pan limit: " + panLimit2);
    }

    public final void setMinimumScaleType(int scaleType) {
        if (VALID_SCALE_TYPES.contains(Integer.valueOf(scaleType))) {
            this.minimumScaleType = scaleType;
            if (isReady()) {
                fitToBounds(true);
                invalidate();
                return;
            }
            return;
        }
        throw new IllegalArgumentException("Invalid scale type: " + scaleType);
    }

    public final void setMaxScale(float maxScale2) {
        this.maxScale = maxScale2;
    }

    public final void setMinScale(float minScale2) {
        this.minScale = minScale2;
    }

    public final void setMinimumDpi(int dpi) {
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        setMaxScale(((metrics.xdpi + metrics.ydpi) / 2.0f) / ((float) dpi));
    }

    public final void setMaximumDpi(int dpi) {
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        setMinScale(((metrics.xdpi + metrics.ydpi) / 2.0f) / ((float) dpi));
    }

    public float getMaxScale() {
        return this.maxScale;
    }

    public final float getMinScale() {
        return minScale();
    }

    public void setMinimumTileDpi(int minimumTileDpi2) {
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        this.minimumTileDpi = (int) Math.min((metrics.xdpi + metrics.ydpi) / 2.0f, (float) minimumTileDpi2);
        if (isReady()) {
            reset(false);
            invalidate();
        }
    }

    public final PointF getCenter() {
        return viewToSourceCoord((float) (getWidth() / 2), (float) (getHeight() / 2));
    }

    public final float getScale() {
        return this.scale;
    }

    public final void setScaleAndCenter(float scale2, PointF sCenter) {
        this.anim = null;
        this.pendingScale = Float.valueOf(scale2);
        this.sPendingCenter = sCenter;
        this.sRequestedCenter = sCenter;
        invalidate();
    }

    public final void resetScaleAndCenter() {
        this.anim = null;
        this.pendingScale = Float.valueOf(limitedScale(0.0f));
        if (isReady()) {
            this.sPendingCenter = new PointF((float) (sWidth() / 2), (float) (sHeight() / 2));
        } else {
            this.sPendingCenter = new PointF(0.0f, 0.0f);
        }
        invalidate();
    }

    public final boolean isReady() {
        return this.readySent;
    }

    /* access modifiers changed from: protected */
    public void onReady() {
    }

    public final boolean isImageLoaded() {
        return this.imageLoadedSent;
    }

    /* access modifiers changed from: protected */
    public void onImageLoaded() {
    }

    public final int getSWidth() {
        return this.sWidth;
    }

    public final int getSHeight() {
        return this.sHeight;
    }

    public final int getOrientation() {
        return this.orientation;
    }

    public final int getAppliedOrientation() {
        return getRequiredRotation();
    }

    public final ImageViewState getState() {
        if (this.vTranslate == null || this.sWidth <= 0 || this.sHeight <= 0) {
            return null;
        }
        return new ImageViewState(getScale(), getCenter(), getOrientation());
    }

    public final boolean isZoomEnabled() {
        return this.zoomEnabled;
    }

    public final void setZoomEnabled(boolean zoomEnabled2) {
        this.zoomEnabled = zoomEnabled2;
    }

    public final boolean isQuickScaleEnabled() {
        return this.quickScaleEnabled;
    }

    public final void setQuickScaleEnabled(boolean quickScaleEnabled2) {
        this.quickScaleEnabled = quickScaleEnabled2;
    }

    public final boolean isPanEnabled() {
        return this.panEnabled;
    }

    public final void setPanEnabled(boolean panEnabled2) {
        PointF pointF;
        this.panEnabled = panEnabled2;
        if (!panEnabled2 && (pointF = this.vTranslate) != null) {
            pointF.x = ((float) (getWidth() / 2)) - (this.scale * ((float) (sWidth() / 2)));
            this.vTranslate.y = ((float) (getHeight() / 2)) - (this.scale * ((float) (sHeight() / 2)));
            if (isReady()) {
                refreshRequiredTiles(true);
                invalidate();
            }
        }
    }

    public final void setTileBackgroundColor(int tileBgColor) {
        if (Color.alpha(tileBgColor) == 0) {
            this.tileBgPaint = null;
        } else {
            Paint paint = new Paint();
            this.tileBgPaint = paint;
            paint.setStyle(Paint.Style.FILL);
            this.tileBgPaint.setColor(tileBgColor);
        }
        invalidate();
    }

    public final void setDoubleTapZoomScale(float doubleTapZoomScale2) {
        this.doubleTapZoomScale = doubleTapZoomScale2;
    }

    public final void setDoubleTapZoomDpi(int dpi) {
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        setDoubleTapZoomScale(((metrics.xdpi + metrics.ydpi) / 2.0f) / ((float) dpi));
    }

    public final void setDoubleTapZoomStyle(int doubleTapZoomStyle2) {
        if (VALID_ZOOM_STYLES.contains(Integer.valueOf(doubleTapZoomStyle2))) {
            this.doubleTapZoomStyle = doubleTapZoomStyle2;
            return;
        }
        throw new IllegalArgumentException("Invalid zoom style: " + doubleTapZoomStyle2);
    }

    public final void setDoubleTapZoomDuration(int durationMs) {
        this.doubleTapZoomDuration = Math.max(0, durationMs);
    }

    public void setExecutor(Executor executor2) {
        if (executor2 != null) {
            this.executor = executor2;
            return;
        }
        throw new NullPointerException("Executor must not be null");
    }

    public void setEagerLoadingEnabled(boolean eagerLoadingEnabled2) {
        this.eagerLoadingEnabled = eagerLoadingEnabled2;
    }

    public final void setDebug(boolean debug2) {
        this.debug = debug2;
    }

    public boolean hasImage() {
        return (this.uri == null && this.bitmap == null) ? false : true;
    }

    public void setOnLongClickListener(View.OnLongClickListener onLongClickListener2) {
        this.onLongClickListener = onLongClickListener2;
    }

    public void setOnImageEventListener(OnImageEventListener onImageEventListener2) {
        this.onImageEventListener = onImageEventListener2;
    }

    public void setOnStateChangedListener(OnStateChangedListener onStateChangedListener2) {
        this.onStateChangedListener = onStateChangedListener2;
    }

    private void sendStateChanged(float oldScale, PointF oldVTranslate, int origin) {
        OnStateChangedListener onStateChangedListener2 = this.onStateChangedListener;
        if (onStateChangedListener2 != null) {
            float f = this.scale;
            if (f != oldScale) {
                onStateChangedListener2.onScaleChanged(f, origin);
            }
        }
        if (this.onStateChangedListener != null && !this.vTranslate.equals(oldVTranslate)) {
            this.onStateChangedListener.onCenterChanged(getCenter(), origin);
        }
    }

    public AnimationBuilder animateCenter(PointF sCenter) {
        if (!isReady()) {
            return null;
        }
        return new AnimationBuilder(sCenter);
    }

    public AnimationBuilder animateScale(float scale2) {
        if (!isReady()) {
            return null;
        }
        return new AnimationBuilder(scale2);
    }

    public AnimationBuilder animateScaleAndCenter(float scale2, PointF sCenter) {
        if (!isReady()) {
            return null;
        }
        return new AnimationBuilder(scale2, sCenter);
    }

    public final class AnimationBuilder {
        private long duration;
        private int easing;
        private boolean interruptible;
        private OnAnimationEventListener listener;
        private int origin;
        private boolean panLimited;
        private final PointF targetSCenter;
        private final float targetScale;
        private final PointF vFocus;

        private AnimationBuilder(PointF sCenter) {
            this.duration = 500;
            this.easing = 2;
            this.origin = 1;
            this.interruptible = true;
            this.panLimited = true;
            this.targetScale = SubsamplingScaleImageView.this.scale;
            this.targetSCenter = sCenter;
            this.vFocus = null;
        }

        private AnimationBuilder(float scale) {
            this.duration = 500;
            this.easing = 2;
            this.origin = 1;
            this.interruptible = true;
            this.panLimited = true;
            this.targetScale = scale;
            this.targetSCenter = SubsamplingScaleImageView.this.getCenter();
            this.vFocus = null;
        }

        private AnimationBuilder(float scale, PointF sCenter) {
            this.duration = 500;
            this.easing = 2;
            this.origin = 1;
            this.interruptible = true;
            this.panLimited = true;
            this.targetScale = scale;
            this.targetSCenter = sCenter;
            this.vFocus = null;
        }

        private AnimationBuilder(float scale, PointF sCenter, PointF vFocus2) {
            this.duration = 500;
            this.easing = 2;
            this.origin = 1;
            this.interruptible = true;
            this.panLimited = true;
            this.targetScale = scale;
            this.targetSCenter = sCenter;
            this.vFocus = vFocus2;
        }

        public AnimationBuilder withDuration(long duration2) {
            this.duration = duration2;
            return this;
        }

        public AnimationBuilder withInterruptible(boolean interruptible2) {
            this.interruptible = interruptible2;
            return this;
        }

        public AnimationBuilder withEasing(int easing2) {
            if (SubsamplingScaleImageView.VALID_EASING_STYLES.contains(Integer.valueOf(easing2))) {
                this.easing = easing2;
                return this;
            }
            throw new IllegalArgumentException("Unknown easing type: " + easing2);
        }

        public AnimationBuilder withOnAnimationEventListener(OnAnimationEventListener listener2) {
            this.listener = listener2;
            return this;
        }

        /* access modifiers changed from: private */
        public AnimationBuilder withPanLimited(boolean panLimited2) {
            this.panLimited = panLimited2;
            return this;
        }

        /* access modifiers changed from: private */
        public AnimationBuilder withOrigin(int origin2) {
            this.origin = origin2;
            return this;
        }

        public void start() {
            if (!(SubsamplingScaleImageView.this.anim == null || SubsamplingScaleImageView.this.anim.listener == null)) {
                try {
                    SubsamplingScaleImageView.this.anim.listener.onInterruptedByNewAnim();
                } catch (Exception e) {
                    Log.w(SubsamplingScaleImageView.TAG, "Error thrown by animation listener", e);
                }
            }
            int vxCenter = SubsamplingScaleImageView.this.getPaddingLeft() + (((SubsamplingScaleImageView.this.getWidth() - SubsamplingScaleImageView.this.getPaddingRight()) - SubsamplingScaleImageView.this.getPaddingLeft()) / 2);
            int vyCenter = SubsamplingScaleImageView.this.getPaddingTop() + (((SubsamplingScaleImageView.this.getHeight() - SubsamplingScaleImageView.this.getPaddingBottom()) - SubsamplingScaleImageView.this.getPaddingTop()) / 2);
            float targetScale2 = SubsamplingScaleImageView.this.limitedScale(this.targetScale);
            PointF targetSCenter2 = this.panLimited ? SubsamplingScaleImageView.this.limitedSCenter(this.targetSCenter.x, this.targetSCenter.y, targetScale2, new PointF()) : this.targetSCenter;
            Anim unused = SubsamplingScaleImageView.this.anim = new Anim();
            float unused2 = SubsamplingScaleImageView.this.anim.scaleStart = SubsamplingScaleImageView.this.scale;
            float unused3 = SubsamplingScaleImageView.this.anim.scaleEnd = targetScale2;
            long unused4 = SubsamplingScaleImageView.this.anim.time = System.currentTimeMillis();
            PointF unused5 = SubsamplingScaleImageView.this.anim.sCenterEndRequested = targetSCenter2;
            PointF unused6 = SubsamplingScaleImageView.this.anim.sCenterStart = SubsamplingScaleImageView.this.getCenter();
            PointF unused7 = SubsamplingScaleImageView.this.anim.sCenterEnd = targetSCenter2;
            PointF unused8 = SubsamplingScaleImageView.this.anim.vFocusStart = SubsamplingScaleImageView.this.sourceToViewCoord(targetSCenter2);
            PointF unused9 = SubsamplingScaleImageView.this.anim.vFocusEnd = new PointF((float) vxCenter, (float) vyCenter);
            long unused10 = SubsamplingScaleImageView.this.anim.duration = this.duration;
            boolean unused11 = SubsamplingScaleImageView.this.anim.interruptible = this.interruptible;
            int unused12 = SubsamplingScaleImageView.this.anim.easing = this.easing;
            int unused13 = SubsamplingScaleImageView.this.anim.origin = this.origin;
            long unused14 = SubsamplingScaleImageView.this.anim.time = System.currentTimeMillis();
            OnAnimationEventListener unused15 = SubsamplingScaleImageView.this.anim.listener = this.listener;
            PointF pointF = this.vFocus;
            if (pointF != null) {
                float vTranslateXEnd = pointF.x - (SubsamplingScaleImageView.this.anim.sCenterStart.x * targetScale2);
                float vTranslateYEnd = this.vFocus.y - (SubsamplingScaleImageView.this.anim.sCenterStart.y * targetScale2);
                ScaleAndTranslate satEnd = new ScaleAndTranslate(targetScale2, new PointF(vTranslateXEnd, vTranslateYEnd));
                SubsamplingScaleImageView.this.fitToBounds(true, satEnd);
                PointF unused16 = SubsamplingScaleImageView.this.anim.vFocusEnd = new PointF(this.vFocus.x + (satEnd.vTranslate.x - vTranslateXEnd), this.vFocus.y + (satEnd.vTranslate.y - vTranslateYEnd));
            }
            SubsamplingScaleImageView.this.invalidate();
        }
    }

    public static class DefaultOnAnimationEventListener implements OnAnimationEventListener {
        public void onComplete() {
        }

        public void onInterruptedByUser() {
        }

        public void onInterruptedByNewAnim() {
        }
    }

    public static class DefaultOnImageEventListener implements OnImageEventListener {
        public void onReady() {
        }

        public void onImageLoaded() {
        }

        public void onPreviewLoadError(Exception e) {
        }

        public void onImageLoadError(Exception e) {
        }

        public void onTileLoadError(Exception e) {
        }

        public void onPreviewReleased() {
        }
    }

    public static class DefaultOnStateChangedListener implements OnStateChangedListener {
        public void onCenterChanged(PointF newCenter, int origin) {
        }

        public void onScaleChanged(float newScale, int origin) {
        }
    }
}
