package com.alexvasilkov.gestures;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import com.alexvasilkov.gestures.internal.UnitsUtils;

public class Settings {
    public static final long ANIMATIONS_DURATION = 300;
    public static final float MAX_ZOOM = 2.0f;
    public static final float OVERZOOM_FACTOR = 2.0f;
    private long animationsDuration = 300;
    private int boundsDisableCount;
    private Bounds boundsType = Bounds.NORMAL;
    private float doubleTapZoom = -1.0f;
    private ExitType exitType = ExitType.ALL;
    private Fit fitMethod = Fit.INSIDE;
    private int gesturesDisableCount;
    private int gravity = 17;
    private int imageH;
    private int imageW;
    private boolean isDoubleTapEnabled = true;
    private boolean isFillViewport = false;
    private boolean isFlingEnabled = true;
    private boolean isMovementAreaSpecified;
    private boolean isPanEnabled = true;
    private boolean isRestrictRotation = false;
    private boolean isRotationEnabled = false;
    private boolean isZoomEnabled = true;
    private float maxZoom = 2.0f;
    private float minZoom = 0.0f;
    private int movementAreaH;
    private int movementAreaW;
    private float overscrollDistanceX;
    private float overscrollDistanceY;
    private float overzoomFactor = 2.0f;
    private int viewportH;
    private int viewportW;

    public enum Bounds {
        NORMAL,
        INSIDE,
        OUTSIDE,
        PIVOT,
        NONE
    }

    public enum ExitType {
        ALL,
        SCROLL,
        ZOOM,
        NONE
    }

    public enum Fit {
        HORIZONTAL,
        VERTICAL,
        INSIDE,
        OUTSIDE,
        NONE
    }

    Settings() {
    }

    public void initFromAttributes(Context context, AttributeSet attrs) {
        if (attrs != null) {
            TypedArray arr = context.obtainStyledAttributes(attrs, C2271R.styleable.GestureView);
            this.movementAreaW = arr.getDimensionPixelSize(C2271R.styleable.GestureView_gest_movementAreaWidth, this.movementAreaW);
            int dimensionPixelSize = arr.getDimensionPixelSize(C2271R.styleable.GestureView_gest_movementAreaHeight, this.movementAreaH);
            this.movementAreaH = dimensionPixelSize;
            this.isMovementAreaSpecified = this.movementAreaW > 0 && dimensionPixelSize > 0;
            this.minZoom = arr.getFloat(C2271R.styleable.GestureView_gest_minZoom, this.minZoom);
            this.maxZoom = arr.getFloat(C2271R.styleable.GestureView_gest_maxZoom, this.maxZoom);
            this.doubleTapZoom = arr.getFloat(C2271R.styleable.GestureView_gest_doubleTapZoom, this.doubleTapZoom);
            this.overzoomFactor = arr.getFloat(C2271R.styleable.GestureView_gest_overzoomFactor, this.overzoomFactor);
            this.overscrollDistanceX = arr.getDimension(C2271R.styleable.GestureView_gest_overscrollX, this.overscrollDistanceX);
            this.overscrollDistanceY = arr.getDimension(C2271R.styleable.GestureView_gest_overscrollY, this.overscrollDistanceY);
            this.isFillViewport = arr.getBoolean(C2271R.styleable.GestureView_gest_fillViewport, this.isFillViewport);
            this.gravity = arr.getInt(C2271R.styleable.GestureView_gest_gravity, this.gravity);
            this.fitMethod = Fit.values()[arr.getInteger(C2271R.styleable.GestureView_gest_fitMethod, this.fitMethod.ordinal())];
            this.boundsType = Bounds.values()[arr.getInteger(C2271R.styleable.GestureView_gest_boundsType, this.boundsType.ordinal())];
            this.isPanEnabled = arr.getBoolean(C2271R.styleable.GestureView_gest_panEnabled, this.isPanEnabled);
            this.isFlingEnabled = arr.getBoolean(C2271R.styleable.GestureView_gest_flingEnabled, this.isFlingEnabled);
            this.isZoomEnabled = arr.getBoolean(C2271R.styleable.GestureView_gest_zoomEnabled, this.isZoomEnabled);
            this.isRotationEnabled = arr.getBoolean(C2271R.styleable.GestureView_gest_rotationEnabled, this.isRotationEnabled);
            this.isRestrictRotation = arr.getBoolean(C2271R.styleable.GestureView_gest_restrictRotation, this.isRestrictRotation);
            this.isDoubleTapEnabled = arr.getBoolean(C2271R.styleable.GestureView_gest_doubleTapEnabled, this.isDoubleTapEnabled);
            this.exitType = arr.getBoolean(C2271R.styleable.GestureView_gest_exitEnabled, true) ? this.exitType : ExitType.NONE;
            this.animationsDuration = (long) arr.getInt(C2271R.styleable.GestureView_gest_animationDuration, (int) this.animationsDuration);
            if (arr.getBoolean(C2271R.styleable.GestureView_gest_disableGestures, false)) {
                disableGestures();
            }
            if (arr.getBoolean(C2271R.styleable.GestureView_gest_disableBounds, false)) {
                disableBounds();
            }
            arr.recycle();
        }
    }

    public Settings setViewport(int width, int height) {
        this.viewportW = width;
        this.viewportH = height;
        return this;
    }

    public Settings setMovementArea(int width, int height) {
        this.isMovementAreaSpecified = true;
        this.movementAreaW = width;
        this.movementAreaH = height;
        return this;
    }

    public Settings setImage(int width, int height) {
        this.imageW = width;
        this.imageH = height;
        return this;
    }

    public Settings setMinZoom(float minZoom2) {
        this.minZoom = minZoom2;
        return this;
    }

    public Settings setMaxZoom(float maxZoom2) {
        this.maxZoom = maxZoom2;
        return this;
    }

    public Settings setDoubleTapZoom(float doubleTapZoom2) {
        this.doubleTapZoom = doubleTapZoom2;
        return this;
    }

    public Settings setOverzoomFactor(float factor) {
        if (factor >= 1.0f) {
            this.overzoomFactor = factor;
            return this;
        }
        throw new IllegalArgumentException("Overzoom factor cannot be < 1");
    }

    public Settings setOverscrollDistance(float distanceX, float distanceY) {
        if (distanceX < 0.0f || distanceY < 0.0f) {
            throw new IllegalArgumentException("Overscroll distance cannot be < 0");
        }
        this.overscrollDistanceX = distanceX;
        this.overscrollDistanceY = distanceY;
        return this;
    }

    public Settings setOverscrollDistance(Context context, float distanceXDp, float distanceYDp) {
        return setOverscrollDistance(UnitsUtils.toPixels(context, distanceXDp), UnitsUtils.toPixels(context, distanceYDp));
    }

    public Settings setFillViewport(boolean isFitViewport) {
        this.isFillViewport = isFitViewport;
        return this;
    }

    public Settings setGravity(int gravity2) {
        this.gravity = gravity2;
        return this;
    }

    public Settings setFitMethod(Fit fitMethod2) {
        this.fitMethod = fitMethod2;
        return this;
    }

    public Settings setBoundsType(Bounds boundsType2) {
        this.boundsType = boundsType2;
        return this;
    }

    public Settings setPanEnabled(boolean enabled) {
        this.isPanEnabled = enabled;
        return this;
    }

    public Settings setFlingEnabled(boolean enabled) {
        this.isFlingEnabled = enabled;
        return this;
    }

    public Settings setZoomEnabled(boolean enabled) {
        this.isZoomEnabled = enabled;
        return this;
    }

    public Settings setRotationEnabled(boolean enabled) {
        this.isRotationEnabled = enabled;
        return this;
    }

    public Settings setRestrictRotation(boolean restrict) {
        this.isRestrictRotation = restrict;
        return this;
    }

    public Settings setDoubleTapEnabled(boolean enabled) {
        this.isDoubleTapEnabled = enabled;
        return this;
    }

    public Settings setExitEnabled(boolean enabled) {
        this.exitType = enabled ? ExitType.ALL : ExitType.NONE;
        return this;
    }

    public Settings setExitType(ExitType type) {
        this.exitType = type;
        return this;
    }

    public Settings disableGestures() {
        this.gesturesDisableCount++;
        return this;
    }

    public Settings enableGestures() {
        this.gesturesDisableCount--;
        return this;
    }

    public Settings disableBounds() {
        this.boundsDisableCount++;
        return this;
    }

    public Settings enableBounds() {
        this.boundsDisableCount--;
        return this;
    }

    @Deprecated
    public Settings setRestrictBounds(boolean restrict) {
        int i = this.boundsDisableCount + (restrict ? -1 : 1);
        this.boundsDisableCount = i;
        if (i < 0) {
            this.boundsDisableCount = 0;
        }
        return this;
    }

    public Settings setAnimationsDuration(long duration) {
        if (duration >= 0) {
            this.animationsDuration = duration;
            return this;
        }
        throw new IllegalArgumentException("Animations duration should be >= 0");
    }

    public int getViewportW() {
        return this.viewportW;
    }

    public int getViewportH() {
        return this.viewportH;
    }

    public int getMovementAreaW() {
        return this.isMovementAreaSpecified ? this.movementAreaW : this.viewportW;
    }

    public int getMovementAreaH() {
        return this.isMovementAreaSpecified ? this.movementAreaH : this.viewportH;
    }

    public int getImageW() {
        return this.imageW;
    }

    public int getImageH() {
        return this.imageH;
    }

    public float getMinZoom() {
        return this.minZoom;
    }

    public float getMaxZoom() {
        return this.maxZoom;
    }

    public float getDoubleTapZoom() {
        return this.doubleTapZoom;
    }

    public float getOverzoomFactor() {
        return this.overzoomFactor;
    }

    public float getOverscrollDistanceX() {
        return this.overscrollDistanceX;
    }

    public float getOverscrollDistanceY() {
        return this.overscrollDistanceY;
    }

    public boolean isFillViewport() {
        return this.isFillViewport;
    }

    public int getGravity() {
        return this.gravity;
    }

    public Fit getFitMethod() {
        return this.fitMethod;
    }

    public Bounds getBoundsType() {
        return this.boundsType;
    }

    public boolean isPanEnabled() {
        return isGesturesEnabled() && this.isPanEnabled;
    }

    public boolean isFlingEnabled() {
        return isGesturesEnabled() && this.isFlingEnabled;
    }

    public boolean isZoomEnabled() {
        return isGesturesEnabled() && this.isZoomEnabled;
    }

    public boolean isRotationEnabled() {
        return isGesturesEnabled() && this.isRotationEnabled;
    }

    public boolean isRestrictRotation() {
        return this.isRestrictRotation;
    }

    public boolean isDoubleTapEnabled() {
        return isGesturesEnabled() && this.isDoubleTapEnabled;
    }

    public boolean isExitEnabled() {
        return getExitType() != ExitType.NONE;
    }

    public ExitType getExitType() {
        return isGesturesEnabled() ? this.exitType : ExitType.NONE;
    }

    public boolean isGesturesEnabled() {
        return this.gesturesDisableCount <= 0;
    }

    public boolean isRestrictBounds() {
        return this.boundsDisableCount <= 0;
    }

    public long getAnimationsDuration() {
        return this.animationsDuration;
    }

    public boolean isEnabled() {
        return isGesturesEnabled() && (this.isPanEnabled || this.isZoomEnabled || this.isRotationEnabled || this.isDoubleTapEnabled);
    }

    public boolean hasImageSize() {
        return (this.imageW == 0 || this.imageH == 0) ? false : true;
    }

    public boolean hasViewportSize() {
        return (this.viewportW == 0 || this.viewportH == 0) ? false : true;
    }
}
