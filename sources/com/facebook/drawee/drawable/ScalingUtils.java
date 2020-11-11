package com.facebook.drawee.drawable;

import android.graphics.Matrix;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import javax.annotation.Nullable;

public class ScalingUtils {

    public interface ScaleType {
        public static final ScaleType CENTER = ScaleTypeCenter.INSTANCE;
        public static final ScaleType CENTER_CROP = ScaleTypeCenterCrop.INSTANCE;
        public static final ScaleType CENTER_INSIDE = ScaleTypeCenterInside.INSTANCE;
        public static final ScaleType FIT_BOTTOM_START = ScaleTypeFitBottomStart.INSTANCE;
        public static final ScaleType FIT_CENTER = ScaleTypeFitCenter.INSTANCE;
        public static final ScaleType FIT_END = ScaleTypeFitEnd.INSTANCE;
        public static final ScaleType FIT_START = ScaleTypeFitStart.INSTANCE;
        public static final ScaleType FIT_X = ScaleTypeFitX.INSTANCE;
        public static final ScaleType FIT_XY = ScaleTypeFitXY.INSTANCE;
        public static final ScaleType FIT_Y = ScaleTypeFitY.INSTANCE;
        public static final ScaleType FOCUS_CROP = ScaleTypeFocusCrop.INSTANCE;

        Matrix getTransform(Matrix matrix, Rect rect, int i, int i2, float f, float f2);
    }

    public interface StatefulScaleType {
        Object getState();
    }

    @Nullable
    public static ScaleTypeDrawable getActiveScaleTypeDrawable(@Nullable Drawable drawable) {
        if (drawable == null) {
            return null;
        }
        if (drawable instanceof ScaleTypeDrawable) {
            return (ScaleTypeDrawable) drawable;
        }
        if (drawable instanceof DrawableParent) {
            return getActiveScaleTypeDrawable(((DrawableParent) drawable).getDrawable());
        }
        if (drawable instanceof ArrayDrawable) {
            ArrayDrawable fadeDrawable = (ArrayDrawable) drawable;
            int numLayers = fadeDrawable.getNumberOfLayers();
            for (int i = 0; i < numLayers; i++) {
                ScaleTypeDrawable result = getActiveScaleTypeDrawable(fadeDrawable.getDrawable(i));
                if (result != null) {
                    return result;
                }
            }
        }
        return null;
    }

    public static abstract class AbstractScaleType implements ScaleType {
        public abstract void getTransformImpl(Matrix matrix, Rect rect, int i, int i2, float f, float f2, float f3, float f4);

        public Matrix getTransform(Matrix outTransform, Rect parentRect, int childWidth, int childHeight, float focusX, float focusY) {
            getTransformImpl(outTransform, parentRect, childWidth, childHeight, focusX, focusY, ((float) parentRect.width()) / ((float) childWidth), ((float) parentRect.height()) / ((float) childHeight));
            return outTransform;
        }
    }

    private static class ScaleTypeFitXY extends AbstractScaleType {
        public static final ScaleType INSTANCE = new ScaleTypeFitXY();

        private ScaleTypeFitXY() {
        }

        public void getTransformImpl(Matrix outTransform, Rect parentRect, int childWidth, int childHeight, float focusX, float focusY, float scaleX, float scaleY) {
            outTransform.setScale(scaleX, scaleY);
            outTransform.postTranslate((float) ((int) (((float) parentRect.left) + 0.5f)), (float) ((int) (0.5f + ((float) parentRect.top))));
        }

        public String toString() {
            return "fit_xy";
        }
    }

    private static class ScaleTypeFitStart extends AbstractScaleType {
        public static final ScaleType INSTANCE = new ScaleTypeFitStart();

        private ScaleTypeFitStart() {
        }

        public void getTransformImpl(Matrix outTransform, Rect parentRect, int childWidth, int childHeight, float focusX, float focusY, float scaleX, float scaleY) {
            float scale = Math.min(scaleX, scaleY);
            outTransform.setScale(scale, scale);
            outTransform.postTranslate((float) ((int) (((float) parentRect.left) + 0.5f)), (float) ((int) (0.5f + ((float) parentRect.top))));
        }

        public String toString() {
            return "fit_start";
        }
    }

    private static class ScaleTypeFitBottomStart extends AbstractScaleType {
        public static final ScaleType INSTANCE = new ScaleTypeFitBottomStart();

        private ScaleTypeFitBottomStart() {
        }

        public void getTransformImpl(Matrix outTransform, Rect parentRect, int childWidth, int childHeight, float focusX, float focusY, float scaleX, float scaleY) {
            float scale = Math.min(scaleX, scaleY);
            outTransform.setScale(scale, scale);
            outTransform.postTranslate((float) ((int) (((float) parentRect.left) + 0.5f)), (float) ((int) (0.5f + ((float) parentRect.top) + (((float) parentRect.height()) - (((float) childHeight) * scale)))));
        }

        public String toString() {
            return "fit_bottom_start";
        }
    }

    private static class ScaleTypeFitCenter extends AbstractScaleType {
        public static final ScaleType INSTANCE = new ScaleTypeFitCenter();

        private ScaleTypeFitCenter() {
        }

        public void getTransformImpl(Matrix outTransform, Rect parentRect, int childWidth, int childHeight, float focusX, float focusY, float scaleX, float scaleY) {
            float scale = Math.min(scaleX, scaleY);
            float dx = ((float) parentRect.left) + ((((float) parentRect.width()) - (((float) childWidth) * scale)) * 0.5f);
            float dy = ((float) parentRect.top) + ((((float) parentRect.height()) - (((float) childHeight) * scale)) * 0.5f);
            outTransform.setScale(scale, scale);
            outTransform.postTranslate((float) ((int) (dx + 0.5f)), (float) ((int) (0.5f + dy)));
        }

        public String toString() {
            return "fit_center";
        }
    }

    private static class ScaleTypeFitEnd extends AbstractScaleType {
        public static final ScaleType INSTANCE = new ScaleTypeFitEnd();

        private ScaleTypeFitEnd() {
        }

        public void getTransformImpl(Matrix outTransform, Rect parentRect, int childWidth, int childHeight, float focusX, float focusY, float scaleX, float scaleY) {
            float scale = Math.min(scaleX, scaleY);
            float dx = ((float) parentRect.left) + (((float) parentRect.width()) - (((float) childWidth) * scale));
            outTransform.setScale(scale, scale);
            outTransform.postTranslate((float) ((int) (dx + 0.5f)), (float) ((int) (0.5f + ((float) parentRect.top) + (((float) parentRect.height()) - (((float) childHeight) * scale)))));
        }

        public String toString() {
            return "fit_end";
        }
    }

    private static class ScaleTypeCenter extends AbstractScaleType {
        public static final ScaleType INSTANCE = new ScaleTypeCenter();

        private ScaleTypeCenter() {
        }

        public void getTransformImpl(Matrix outTransform, Rect parentRect, int childWidth, int childHeight, float focusX, float focusY, float scaleX, float scaleY) {
            outTransform.setTranslate((float) ((int) (((float) parentRect.left) + (((float) (parentRect.width() - childWidth)) * 0.5f) + 0.5f)), (float) ((int) (0.5f + ((float) parentRect.top) + (((float) (parentRect.height() - childHeight)) * 0.5f))));
        }

        public String toString() {
            return "center";
        }
    }

    private static class ScaleTypeCenterInside extends AbstractScaleType {
        public static final ScaleType INSTANCE = new ScaleTypeCenterInside();

        private ScaleTypeCenterInside() {
        }

        public void getTransformImpl(Matrix outTransform, Rect parentRect, int childWidth, int childHeight, float focusX, float focusY, float scaleX, float scaleY) {
            float scale = Math.min(Math.min(scaleX, scaleY), 1.0f);
            float dx = ((float) parentRect.left) + ((((float) parentRect.width()) - (((float) childWidth) * scale)) * 0.5f);
            float dy = ((float) parentRect.top) + ((((float) parentRect.height()) - (((float) childHeight) * scale)) * 0.5f);
            outTransform.setScale(scale, scale);
            outTransform.postTranslate((float) ((int) (dx + 0.5f)), (float) ((int) (0.5f + dy)));
        }

        public String toString() {
            return "center_inside";
        }
    }

    private static class ScaleTypeCenterCrop extends AbstractScaleType {
        public static final ScaleType INSTANCE = new ScaleTypeCenterCrop();

        private ScaleTypeCenterCrop() {
        }

        public void getTransformImpl(Matrix outTransform, Rect parentRect, int childWidth, int childHeight, float focusX, float focusY, float scaleX, float scaleY) {
            float dy;
            float dx;
            float scale;
            if (scaleY > scaleX) {
                scale = scaleY;
                dx = ((float) parentRect.left) + ((((float) parentRect.width()) - (((float) childWidth) * scale)) * 0.5f);
                dy = (float) parentRect.top;
            } else {
                scale = scaleX;
                dx = (float) parentRect.left;
                dy = ((float) parentRect.top) + ((((float) parentRect.height()) - (((float) childHeight) * scale)) * 0.5f);
            }
            outTransform.setScale(scale, scale);
            outTransform.postTranslate((float) ((int) (dx + 0.5f)), (float) ((int) (0.5f + dy)));
        }

        public String toString() {
            return "center_crop";
        }
    }

    private static class ScaleTypeFocusCrop extends AbstractScaleType {
        public static final ScaleType INSTANCE = new ScaleTypeFocusCrop();

        private ScaleTypeFocusCrop() {
        }

        public void getTransformImpl(Matrix outTransform, Rect parentRect, int childWidth, int childHeight, float focusX, float focusY, float scaleX, float scaleY) {
            float dx;
            float dy;
            float scale;
            Matrix matrix = outTransform;
            Rect rect = parentRect;
            int i = childWidth;
            int i2 = childHeight;
            if (scaleY > scaleX) {
                scale = scaleY;
                dx = ((float) rect.left) + Math.max(Math.min((((float) parentRect.width()) * 0.5f) - ((((float) i) * scale) * focusX), 0.0f), ((float) parentRect.width()) - (((float) i) * scale));
                dy = (float) rect.top;
            } else {
                scale = scaleX;
                dx = (float) rect.left;
                dy = Math.max(Math.min((((float) parentRect.height()) * 0.5f) - ((((float) i2) * scale) * focusY), 0.0f), ((float) parentRect.height()) - (((float) i2) * scale)) + ((float) rect.top);
            }
            outTransform.setScale(scale, scale);
            outTransform.postTranslate((float) ((int) (dx + 0.5f)), (float) ((int) (0.5f + dy)));
        }

        public String toString() {
            return "focus_crop";
        }
    }

    private static class ScaleTypeFitX extends AbstractScaleType {
        public static final ScaleType INSTANCE = new ScaleTypeFitX();

        private ScaleTypeFitX() {
        }

        public void getTransformImpl(Matrix outTransform, Rect parentRect, int childWidth, int childHeight, float focusX, float focusY, float scaleX, float scaleY) {
            float scale = scaleX;
            float dy = ((float) parentRect.top) + ((((float) parentRect.height()) - (((float) childHeight) * scale)) * 0.5f);
            outTransform.setScale(scale, scale);
            outTransform.postTranslate((float) ((int) (((float) parentRect.left) + 0.5f)), (float) ((int) (0.5f + dy)));
        }

        public String toString() {
            return "fit_x";
        }
    }

    private static class ScaleTypeFitY extends AbstractScaleType {
        public static final ScaleType INSTANCE = new ScaleTypeFitY();

        private ScaleTypeFitY() {
        }

        public void getTransformImpl(Matrix outTransform, Rect parentRect, int childWidth, int childHeight, float focusX, float focusY, float scaleX, float scaleY) {
            float scale = scaleY;
            float dx = ((float) parentRect.left) + ((((float) parentRect.width()) - (((float) childWidth) * scale)) * 0.5f);
            outTransform.setScale(scale, scale);
            outTransform.postTranslate((float) ((int) (dx + 0.5f)), (float) ((int) (0.5f + ((float) parentRect.top))));
        }

        public String toString() {
            return "fit_y";
        }
    }

    public static class InterpolatingScaleType implements ScaleType, StatefulScaleType {
        @Nullable
        private final Rect mBoundsFrom;
        @Nullable
        private final Rect mBoundsTo;
        @Nullable
        private final PointF mFocusPointFrom;
        @Nullable
        private final PointF mFocusPointTo;
        private float mInterpolatingValue;
        private final float[] mMatrixValuesFrom;
        private final float[] mMatrixValuesInterpolated;
        private final float[] mMatrixValuesTo;
        private final ScaleType mScaleTypeFrom;
        private final ScaleType mScaleTypeTo;

        public InterpolatingScaleType(ScaleType scaleTypeFrom, ScaleType scaleTypeTo, @Nullable Rect boundsFrom, @Nullable Rect boundsTo, @Nullable PointF focusPointFrom, @Nullable PointF focusPointTo) {
            this.mMatrixValuesFrom = new float[9];
            this.mMatrixValuesTo = new float[9];
            this.mMatrixValuesInterpolated = new float[9];
            this.mScaleTypeFrom = scaleTypeFrom;
            this.mScaleTypeTo = scaleTypeTo;
            this.mBoundsFrom = boundsFrom;
            this.mBoundsTo = boundsTo;
            this.mFocusPointFrom = focusPointFrom;
            this.mFocusPointTo = focusPointTo;
        }

        public InterpolatingScaleType(ScaleType scaleTypeFrom, ScaleType scaleTypeTo, @Nullable Rect boundsFrom, @Nullable Rect boundsTo) {
            this(scaleTypeFrom, scaleTypeTo, boundsFrom, boundsTo, (PointF) null, (PointF) null);
        }

        public InterpolatingScaleType(ScaleType scaleTypeFrom, ScaleType scaleTypeTo) {
            this(scaleTypeFrom, scaleTypeTo, (Rect) null, (Rect) null);
        }

        public ScaleType getScaleTypeFrom() {
            return this.mScaleTypeFrom;
        }

        public ScaleType getScaleTypeTo() {
            return this.mScaleTypeTo;
        }

        @Nullable
        public Rect getBoundsFrom() {
            return this.mBoundsFrom;
        }

        @Nullable
        public Rect getBoundsTo() {
            return this.mBoundsTo;
        }

        @Nullable
        public PointF getFocusPointFrom() {
            return this.mFocusPointFrom;
        }

        @Nullable
        public PointF getFocusPointTo() {
            return this.mFocusPointTo;
        }

        public void setValue(float value) {
            this.mInterpolatingValue = value;
        }

        public float getValue() {
            return this.mInterpolatingValue;
        }

        public Object getState() {
            return Float.valueOf(this.mInterpolatingValue);
        }

        public Matrix getTransform(Matrix transform, Rect parentBounds, int childWidth, int childHeight, float focusX, float focusY) {
            Matrix matrix = transform;
            Rect rect = this.mBoundsFrom;
            Rect boundsFrom = rect != null ? rect : parentBounds;
            Rect rect2 = this.mBoundsTo;
            Rect boundsTo = rect2 != null ? rect2 : parentBounds;
            ScaleType scaleType = this.mScaleTypeFrom;
            PointF pointF = this.mFocusPointFrom;
            float f = pointF == null ? focusX : pointF.x;
            PointF pointF2 = this.mFocusPointFrom;
            scaleType.getTransform(transform, boundsFrom, childWidth, childHeight, f, pointF2 == null ? focusY : pointF2.y);
            matrix.getValues(this.mMatrixValuesFrom);
            ScaleType scaleType2 = this.mScaleTypeTo;
            PointF pointF3 = this.mFocusPointTo;
            float f2 = pointF3 == null ? focusX : pointF3.x;
            PointF pointF4 = this.mFocusPointTo;
            scaleType2.getTransform(transform, boundsTo, childWidth, childHeight, f2, pointF4 == null ? focusY : pointF4.y);
            matrix.getValues(this.mMatrixValuesTo);
            for (int i = 0; i < 9; i++) {
                float[] fArr = this.mMatrixValuesInterpolated;
                float f3 = this.mMatrixValuesFrom[i];
                float f4 = this.mInterpolatingValue;
                fArr[i] = (f3 * (1.0f - f4)) + (this.mMatrixValuesTo[i] * f4);
            }
            matrix.setValues(this.mMatrixValuesInterpolated);
            return matrix;
        }

        public String toString() {
            return String.format("InterpolatingScaleType(%s (%s) -> %s (%s))", new Object[]{String.valueOf(this.mScaleTypeFrom), String.valueOf(this.mFocusPointFrom), String.valueOf(this.mScaleTypeTo), String.valueOf(this.mFocusPointTo)});
        }
    }
}
