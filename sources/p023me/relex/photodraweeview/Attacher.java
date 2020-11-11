package p023me.relex.photodraweeview;

import android.content.Context;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.os.Build;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewParent;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Interpolator;
import androidx.core.view.GestureDetectorCompat;
import androidx.core.view.MotionEventCompat;
import androidx.core.widget.ScrollerCompat;
import com.facebook.drawee.drawable.ScalingUtils;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.view.DraweeView;
import java.lang.ref.WeakReference;

/* renamed from: me.relex.photodraweeview.Attacher */
public class Attacher implements IAttacher, View.OnTouchListener, OnScaleDragGestureListener {
    private static final int EDGE_BOTH = 2;
    private static final int EDGE_LEFT = 0;
    private static final int EDGE_NONE = -1;
    private static final int EDGE_RIGHT = 1;
    private boolean mAllowParentInterceptOnEdge = true;
    private boolean mBlockParentIntercept = false;
    private FlingRunnable mCurrentFlingRunnable;
    private final RectF mDisplayRect = new RectF();
    private WeakReference<DraweeView<GenericDraweeHierarchy>> mDraweeView;
    private GestureDetectorCompat mGestureDetector;
    private int mImageInfoHeight = -1;
    private int mImageInfoWidth = -1;
    /* access modifiers changed from: private */
    public View.OnLongClickListener mLongClickListener;
    /* access modifiers changed from: private */
    public final Matrix mMatrix = new Matrix();
    private final float[] mMatrixValues = new float[9];
    private float mMaxScale = 3.0f;
    private float mMidScale = 1.75f;
    private float mMinScale = 1.0f;
    private OnPhotoTapListener mPhotoTapListener;
    private OnScaleChangeListener mScaleChangeListener;
    private ScaleDragDetector mScaleDragDetector;
    private int mScrollEdge = 2;
    private OnViewTapListener mViewTapListener;
    /* access modifiers changed from: private */
    public long mZoomDuration = 200;
    /* access modifiers changed from: private */
    public final Interpolator mZoomInterpolator = new AccelerateDecelerateInterpolator();

    public Attacher(DraweeView<GenericDraweeHierarchy> draweeView) {
        this.mDraweeView = new WeakReference<>(draweeView);
        draweeView.getHierarchy().setActualImageScaleType(ScalingUtils.ScaleType.FIT_CENTER);
        draweeView.setOnTouchListener(this);
        this.mScaleDragDetector = new ScaleDragDetector(draweeView.getContext(), this);
        GestureDetectorCompat gestureDetectorCompat = new GestureDetectorCompat(draweeView.getContext(), new GestureDetector.SimpleOnGestureListener() {
            public void onLongPress(MotionEvent e) {
                super.onLongPress(e);
                if (Attacher.this.mLongClickListener != null) {
                    Attacher.this.mLongClickListener.onLongClick(Attacher.this.getDraweeView());
                }
            }
        });
        this.mGestureDetector = gestureDetectorCompat;
        gestureDetectorCompat.setOnDoubleTapListener(new DefaultOnDoubleTapListener(this));
    }

    public void setOnDoubleTapListener(GestureDetector.OnDoubleTapListener newOnDoubleTapListener) {
        if (newOnDoubleTapListener != null) {
            this.mGestureDetector.setOnDoubleTapListener(newOnDoubleTapListener);
        } else {
            this.mGestureDetector.setOnDoubleTapListener(new DefaultOnDoubleTapListener(this));
        }
    }

    public DraweeView<GenericDraweeHierarchy> getDraweeView() {
        return (DraweeView) this.mDraweeView.get();
    }

    public float getMinimumScale() {
        return this.mMinScale;
    }

    public float getMediumScale() {
        return this.mMidScale;
    }

    public float getMaximumScale() {
        return this.mMaxScale;
    }

    public void setMaximumScale(float maximumScale) {
        checkZoomLevels(this.mMinScale, this.mMidScale, maximumScale);
        this.mMaxScale = maximumScale;
    }

    public void setMediumScale(float mediumScale) {
        checkZoomLevels(this.mMinScale, mediumScale, this.mMaxScale);
        this.mMidScale = mediumScale;
    }

    public void setMinimumScale(float minimumScale) {
        checkZoomLevels(minimumScale, this.mMidScale, this.mMaxScale);
        this.mMinScale = minimumScale;
    }

    public float getScale() {
        return (float) Math.sqrt((double) (((float) Math.pow((double) getMatrixValue(this.mMatrix, 0), 2.0d)) + ((float) Math.pow((double) getMatrixValue(this.mMatrix, 3), 2.0d))));
    }

    public void setScale(float scale) {
        setScale(scale, false);
    }

    public void setScale(float scale, boolean animate) {
        DraweeView<GenericDraweeHierarchy> draweeView = getDraweeView();
        if (draweeView != null) {
            setScale(scale, (float) (draweeView.getRight() / 2), (float) (draweeView.getBottom() / 2), false);
        }
    }

    public void setScale(float scale, float focalX, float focalY, boolean animate) {
        DraweeView<GenericDraweeHierarchy> draweeView = getDraweeView();
        if (draweeView != null && scale >= this.mMinScale && scale <= this.mMaxScale) {
            if (animate) {
                draweeView.post(new AnimatedZoomRunnable(getScale(), scale, focalX, focalY));
                return;
            }
            this.mMatrix.setScale(scale, scale, focalX, focalY);
            checkMatrixAndInvalidate();
        }
    }

    public void setZoomTransitionDuration(long duration) {
        this.mZoomDuration = duration < 0 ? 200 : duration;
    }

    public void setAllowParentInterceptOnEdge(boolean allow) {
        this.mAllowParentInterceptOnEdge = allow;
    }

    public void setOnScaleChangeListener(OnScaleChangeListener listener) {
        this.mScaleChangeListener = listener;
    }

    public void setOnLongClickListener(View.OnLongClickListener listener) {
        this.mLongClickListener = listener;
    }

    public void setOnPhotoTapListener(OnPhotoTapListener listener) {
        this.mPhotoTapListener = listener;
    }

    public void setOnViewTapListener(OnViewTapListener listener) {
        this.mViewTapListener = listener;
    }

    public OnPhotoTapListener getOnPhotoTapListener() {
        return this.mPhotoTapListener;
    }

    public OnViewTapListener getOnViewTapListener() {
        return this.mViewTapListener;
    }

    public void update(int imageInfoWidth, int imageInfoHeight) {
        this.mImageInfoWidth = imageInfoWidth;
        this.mImageInfoHeight = imageInfoHeight;
        updateBaseMatrix();
    }

    private static void checkZoomLevels(float minZoom, float midZoom, float maxZoom) {
        if (minZoom >= midZoom) {
            throw new IllegalArgumentException("MinZoom has to be less than MidZoom");
        } else if (midZoom >= maxZoom) {
            throw new IllegalArgumentException("MidZoom has to be less than MaxZoom");
        }
    }

    private int getViewWidth() {
        DraweeView<GenericDraweeHierarchy> draweeView = getDraweeView();
        if (draweeView != null) {
            return (draweeView.getWidth() - draweeView.getPaddingLeft()) - draweeView.getPaddingRight();
        }
        return 0;
    }

    private int getViewHeight() {
        DraweeView<GenericDraweeHierarchy> draweeView = getDraweeView();
        if (draweeView != null) {
            return (draweeView.getHeight() - draweeView.getPaddingTop()) - draweeView.getPaddingBottom();
        }
        return 0;
    }

    private float getMatrixValue(Matrix matrix, int whichValue) {
        matrix.getValues(this.mMatrixValues);
        return this.mMatrixValues[whichValue];
    }

    public Matrix getDrawMatrix() {
        return this.mMatrix;
    }

    public RectF getDisplayRect() {
        checkMatrixBounds();
        return getDisplayRect(getDrawMatrix());
    }

    public void checkMatrixAndInvalidate() {
        DraweeView<GenericDraweeHierarchy> draweeView = getDraweeView();
        if (draweeView != null && checkMatrixBounds()) {
            draweeView.invalidate();
        }
    }

    public boolean checkMatrixBounds() {
        RectF rect = getDisplayRect(getDrawMatrix());
        if (rect == null) {
            return false;
        }
        float height = rect.height();
        float width = rect.width();
        float deltaX = 0.0f;
        float deltaY = 0.0f;
        int viewHeight = getViewHeight();
        if (height <= ((float) viewHeight)) {
            deltaY = ((((float) viewHeight) - height) / 2.0f) - rect.top;
        } else if (rect.top > 0.0f) {
            deltaY = -rect.top;
        } else if (rect.bottom < ((float) viewHeight)) {
            deltaY = ((float) viewHeight) - rect.bottom;
        }
        int viewWidth = getViewWidth();
        if (width <= ((float) viewWidth)) {
            deltaX = ((((float) viewWidth) - width) / 2.0f) - rect.left;
            this.mScrollEdge = 2;
        } else if (rect.left > 0.0f) {
            deltaX = -rect.left;
            this.mScrollEdge = 0;
        } else if (rect.right < ((float) viewWidth)) {
            deltaX = ((float) viewWidth) - rect.right;
            this.mScrollEdge = 1;
        } else {
            this.mScrollEdge = -1;
        }
        this.mMatrix.postTranslate(deltaX, deltaY);
        return true;
    }

    private RectF getDisplayRect(Matrix matrix) {
        DraweeView<GenericDraweeHierarchy> draweeView = getDraweeView();
        if (draweeView == null) {
            return null;
        }
        int i = this.mImageInfoWidth;
        if (i == -1 && this.mImageInfoHeight == -1) {
            return null;
        }
        this.mDisplayRect.set(0.0f, 0.0f, (float) i, (float) this.mImageInfoHeight);
        draweeView.getHierarchy().getActualImageBounds(this.mDisplayRect);
        matrix.mapRect(this.mDisplayRect);
        return this.mDisplayRect;
    }

    private void updateBaseMatrix() {
        if (this.mImageInfoWidth != -1 || this.mImageInfoHeight != -1) {
            resetMatrix();
        }
    }

    private void resetMatrix() {
        this.mMatrix.reset();
        checkMatrixBounds();
        DraweeView<GenericDraweeHierarchy> draweeView = getDraweeView();
        if (draweeView != null) {
            draweeView.invalidate();
        }
    }

    private void checkMinScale() {
        RectF rect;
        DraweeView<GenericDraweeHierarchy> draweeView = getDraweeView();
        if (draweeView != null && getScale() < this.mMinScale && (rect = getDisplayRect()) != null) {
            draweeView.post(new AnimatedZoomRunnable(getScale(), this.mMinScale, rect.centerX(), rect.centerY()));
        }
    }

    public void onScale(float scaleFactor, float focusX, float focusY) {
        if (getScale() < this.mMaxScale || scaleFactor < 1.0f) {
            OnScaleChangeListener onScaleChangeListener = this.mScaleChangeListener;
            if (onScaleChangeListener != null) {
                onScaleChangeListener.onScaleChange(scaleFactor, focusX, focusY);
            }
            this.mMatrix.postScale(scaleFactor, scaleFactor, focusX, focusY);
            checkMatrixAndInvalidate();
        }
    }

    public void onScaleEnd() {
        checkMinScale();
    }

    public void onDrag(float dx, float dy) {
        DraweeView<GenericDraweeHierarchy> draweeView = getDraweeView();
        if (draweeView != null && !this.mScaleDragDetector.isScaling()) {
            this.mMatrix.postTranslate(dx, dy);
            checkMatrixAndInvalidate();
            ViewParent parent = draweeView.getParent();
            if (parent != null) {
                if (!this.mAllowParentInterceptOnEdge || this.mScaleDragDetector.isScaling() || this.mBlockParentIntercept) {
                    parent.requestDisallowInterceptTouchEvent(true);
                    return;
                }
                int i = this.mScrollEdge;
                if (i == 2 || ((i == 0 && dx >= 1.0f) || (i == 1 && dx <= -1.0f))) {
                    parent.requestDisallowInterceptTouchEvent(false);
                }
            }
        }
    }

    public void onFling(float startX, float startY, float velocityX, float velocityY) {
        DraweeView<GenericDraweeHierarchy> draweeView = getDraweeView();
        if (draweeView != null) {
            FlingRunnable flingRunnable = new FlingRunnable(draweeView.getContext());
            this.mCurrentFlingRunnable = flingRunnable;
            flingRunnable.fling(getViewWidth(), getViewHeight(), (int) velocityX, (int) velocityY);
            draweeView.post(this.mCurrentFlingRunnable);
        }
    }

    public boolean onTouch(View v, MotionEvent event) {
        ViewParent parent;
        int action = MotionEventCompat.getActionMasked(event);
        boolean z = false;
        if (action == 0) {
            ViewParent parent2 = v.getParent();
            if (parent2 != null) {
                parent2.requestDisallowInterceptTouchEvent(true);
            }
            cancelFling();
        } else if ((action == 1 || action == 3) && (parent = v.getParent()) != null) {
            parent.requestDisallowInterceptTouchEvent(false);
        }
        boolean wasScaling = this.mScaleDragDetector.isScaling();
        boolean wasDragging = this.mScaleDragDetector.isDragging();
        boolean handled = this.mScaleDragDetector.onTouchEvent(event);
        boolean noScale = !wasScaling && !this.mScaleDragDetector.isScaling();
        boolean noDrag = !wasDragging && !this.mScaleDragDetector.isDragging();
        if (noScale && noDrag) {
            z = true;
        }
        this.mBlockParentIntercept = z;
        if (this.mGestureDetector.onTouchEvent(event)) {
            return true;
        }
        return handled;
    }

    /* renamed from: me.relex.photodraweeview.Attacher$AnimatedZoomRunnable */
    private class AnimatedZoomRunnable implements Runnable {
        private final float mFocalX;
        private final float mFocalY;
        private final long mStartTime = System.currentTimeMillis();
        private final float mZoomEnd;
        private final float mZoomStart;

        public AnimatedZoomRunnable(float currentZoom, float targetZoom, float focalX, float focalY) {
            this.mFocalX = focalX;
            this.mFocalY = focalY;
            this.mZoomStart = currentZoom;
            this.mZoomEnd = targetZoom;
        }

        public void run() {
            DraweeView<GenericDraweeHierarchy> draweeView = Attacher.this.getDraweeView();
            if (draweeView != null) {
                float t = interpolate();
                float f = this.mZoomStart;
                Attacher.this.onScale((f + ((this.mZoomEnd - f) * t)) / Attacher.this.getScale(), this.mFocalX, this.mFocalY);
                if (t < 1.0f) {
                    Attacher.this.postOnAnimation(draweeView, this);
                }
            }
        }

        private float interpolate() {
            return Attacher.this.mZoomInterpolator.getInterpolation(Math.min(1.0f, (((float) (System.currentTimeMillis() - this.mStartTime)) * 1.0f) / ((float) Attacher.this.mZoomDuration)));
        }
    }

    /* renamed from: me.relex.photodraweeview.Attacher$FlingRunnable */
    private class FlingRunnable implements Runnable {
        private int mCurrentX;
        private int mCurrentY;
        private final ScrollerCompat mScroller;

        public FlingRunnable(Context context) {
            this.mScroller = ScrollerCompat.create(context);
        }

        public void cancelFling() {
            this.mScroller.abortAnimation();
        }

        public void fling(int viewWidth, int viewHeight, int velocityX, int velocityY) {
            int minX;
            int maxX;
            int minY;
            int maxY;
            int i = viewWidth;
            int i2 = viewHeight;
            RectF rect = Attacher.this.getDisplayRect();
            if (rect != null) {
                int startX = Math.round(-rect.left);
                if (((float) i) < rect.width()) {
                    minX = 0;
                    maxX = Math.round(rect.width() - ((float) i));
                } else {
                    minX = startX;
                    maxX = startX;
                }
                int startY = Math.round(-rect.top);
                if (((float) i2) < rect.height()) {
                    minY = 0;
                    maxY = Math.round(rect.height() - ((float) i2));
                } else {
                    minY = startY;
                    maxY = startY;
                }
                this.mCurrentX = startX;
                this.mCurrentY = startY;
                if (startX == maxX && startY == maxY) {
                    int i3 = maxY;
                    int i4 = startY;
                    int i5 = maxX;
                    return;
                }
                int i6 = maxY;
                int i7 = startY;
                int i8 = maxX;
                this.mScroller.fling(startX, startY, velocityX, velocityY, minX, maxX, minY, maxY, 0, 0);
            }
        }

        public void run() {
            DraweeView<GenericDraweeHierarchy> draweeView;
            if (!this.mScroller.isFinished() && (draweeView = Attacher.this.getDraweeView()) != null && this.mScroller.computeScrollOffset()) {
                int newX = this.mScroller.getCurrX();
                int newY = this.mScroller.getCurrY();
                Attacher.this.mMatrix.postTranslate((float) (this.mCurrentX - newX), (float) (this.mCurrentY - newY));
                draweeView.invalidate();
                this.mCurrentX = newX;
                this.mCurrentY = newY;
                Attacher.this.postOnAnimation(draweeView, this);
            }
        }
    }

    private void cancelFling() {
        FlingRunnable flingRunnable = this.mCurrentFlingRunnable;
        if (flingRunnable != null) {
            flingRunnable.cancelFling();
            this.mCurrentFlingRunnable = null;
        }
    }

    /* access modifiers changed from: private */
    public void postOnAnimation(View view, Runnable runnable) {
        if (Build.VERSION.SDK_INT >= 16) {
            view.postOnAnimation(runnable);
        } else {
            view.postDelayed(runnable, 16);
        }
    }

    /* access modifiers changed from: protected */
    public void onDetachedFromWindow() {
        cancelFling();
    }
}
