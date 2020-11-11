package com.alexvasilkov.gestures.internal;

import android.graphics.Matrix;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;
import com.alexvasilkov.gestures.Settings;
import com.alexvasilkov.gestures.State;
import com.alexvasilkov.gestures.utils.GravityUtils;
import com.alexvasilkov.gestures.utils.MathUtils;

public class MovementBounds {
    private static final Matrix tmpMatrix = new Matrix();
    private static final Point tmpPoint = new Point();
    private static final float[] tmpPointArr = new float[2];
    private static final Rect tmpRect = new Rect();
    private static final RectF tmpRectF = new RectF();
    private final RectF bounds = new RectF();
    private float boundsPivotX;
    private float boundsPivotY;
    private float boundsRotation;
    private final Settings settings;

    public MovementBounds(Settings settings2) {
        this.settings = settings2;
    }

    public MovementBounds set(State state) {
        RectF area = tmpRectF;
        Settings settings2 = this.settings;
        Rect rect = tmpRect;
        GravityUtils.getMovementAreaPosition(settings2, rect);
        area.set(rect);
        Rect pos = tmpRect;
        if (this.settings.getFitMethod() == Settings.Fit.OUTSIDE) {
            this.boundsRotation = state.getRotation();
            this.boundsPivotX = area.centerX();
            this.boundsPivotY = area.centerY();
            if (!State.equals(this.boundsRotation, 0.0f)) {
                Matrix matrix = tmpMatrix;
                matrix.setRotate(-this.boundsRotation, this.boundsPivotX, this.boundsPivotY);
                matrix.mapRect(area);
            }
        } else {
            this.boundsRotation = 0.0f;
            this.boundsPivotY = 0.0f;
            this.boundsPivotX = 0.0f;
        }
        Matrix matrix2 = tmpMatrix;
        state.get(matrix2);
        if (!State.equals(this.boundsRotation, 0.0f)) {
            matrix2.postRotate(-this.boundsRotation, this.boundsPivotX, this.boundsPivotY);
        }
        GravityUtils.getImagePosition(matrix2, this.settings, pos);
        int i = C05771.$SwitchMap$com$alexvasilkov$gestures$Settings$Bounds[this.settings.getBoundsType().ordinal()];
        if (i == 1) {
            calculateNormalBounds(area, pos);
        } else if (i == 2) {
            calculateInsideBounds(area, pos);
        } else if (i == 3) {
            calculateOutsideBounds(area, pos);
        } else if (i != 4) {
            this.bounds.set(-5.3687091E8f, -5.3687091E8f, 5.3687091E8f, 5.3687091E8f);
        } else {
            calculatePivotBounds(pos);
        }
        if (this.settings.getFitMethod() != Settings.Fit.OUTSIDE) {
            state.get(matrix2);
            RectF imageRect = tmpRectF;
            imageRect.set(0.0f, 0.0f, (float) this.settings.getImageW(), (float) this.settings.getImageH());
            matrix2.mapRect(imageRect);
            float[] fArr = tmpPointArr;
            fArr[1] = 0.0f;
            fArr[0] = 0.0f;
            matrix2.mapPoints(fArr);
            this.bounds.offset(fArr[0] - imageRect.left, fArr[1] - imageRect.top);
        }
        return this;
    }

    /* renamed from: com.alexvasilkov.gestures.internal.MovementBounds$1 */
    static /* synthetic */ class C05771 {
        static final /* synthetic */ int[] $SwitchMap$com$alexvasilkov$gestures$Settings$Bounds;

        static {
            int[] iArr = new int[Settings.Bounds.values().length];
            $SwitchMap$com$alexvasilkov$gestures$Settings$Bounds = iArr;
            try {
                iArr[Settings.Bounds.NORMAL.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$com$alexvasilkov$gestures$Settings$Bounds[Settings.Bounds.INSIDE.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$com$alexvasilkov$gestures$Settings$Bounds[Settings.Bounds.OUTSIDE.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$com$alexvasilkov$gestures$Settings$Bounds[Settings.Bounds.PIVOT.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
            try {
                $SwitchMap$com$alexvasilkov$gestures$Settings$Bounds[Settings.Bounds.NONE.ordinal()] = 5;
            } catch (NoSuchFieldError e5) {
            }
        }
    }

    private void calculateNormalBounds(RectF area, Rect pos) {
        if (area.width() < ((float) pos.width())) {
            this.bounds.left = area.left - (((float) pos.width()) - area.width());
            this.bounds.right = area.left;
        } else {
            RectF rectF = this.bounds;
            float f = (float) pos.left;
            rectF.right = f;
            rectF.left = f;
        }
        if (area.height() < ((float) pos.height())) {
            this.bounds.top = area.top - (((float) pos.height()) - area.height());
            this.bounds.bottom = area.top;
            return;
        }
        RectF rectF2 = this.bounds;
        float f2 = (float) pos.top;
        rectF2.bottom = f2;
        rectF2.top = f2;
    }

    private void calculateInsideBounds(RectF area, Rect pos) {
        if (area.width() < ((float) pos.width())) {
            this.bounds.left = area.left - (((float) pos.width()) - area.width());
            this.bounds.right = area.left;
        } else {
            this.bounds.left = area.left;
            this.bounds.right = area.right - ((float) pos.width());
        }
        if (area.height() < ((float) pos.height())) {
            this.bounds.top = area.top - (((float) pos.height()) - area.height());
            this.bounds.bottom = area.top;
            return;
        }
        this.bounds.top = area.top;
        this.bounds.bottom = area.bottom - ((float) pos.height());
    }

    private void calculateOutsideBounds(RectF area, Rect pos) {
        this.bounds.left = area.left - ((float) pos.width());
        this.bounds.right = area.right;
        this.bounds.top = area.top - ((float) pos.height());
        this.bounds.bottom = area.bottom;
    }

    private void calculatePivotBounds(Rect pos) {
        Settings settings2 = this.settings;
        Point point = tmpPoint;
        GravityUtils.getDefaultPivot(settings2, point);
        float[] fArr = tmpPointArr;
        fArr[0] = (float) point.x;
        fArr[1] = (float) point.y;
        if (!State.equals(this.boundsRotation, 0.0f)) {
            Matrix matrix = tmpMatrix;
            matrix.setRotate(-this.boundsRotation, this.boundsPivotX, this.boundsPivotY);
            matrix.mapPoints(fArr);
        }
        this.bounds.left = fArr[0] - ((float) pos.width());
        this.bounds.right = fArr[0];
        this.bounds.top = fArr[1] - ((float) pos.height());
        this.bounds.bottom = fArr[1];
    }

    public void extend(float x, float y) {
        float[] fArr = tmpPointArr;
        fArr[0] = x;
        fArr[1] = y;
        float f = this.boundsRotation;
        if (f != 0.0f) {
            Matrix matrix = tmpMatrix;
            matrix.setRotate(-f, this.boundsPivotX, this.boundsPivotY);
            matrix.mapPoints(fArr);
        }
        this.bounds.union(fArr[0], fArr[1]);
    }

    public void getExternalBounds(RectF out) {
        float f = this.boundsRotation;
        if (f == 0.0f) {
            out.set(this.bounds);
            return;
        }
        Matrix matrix = tmpMatrix;
        matrix.setRotate(f, this.boundsPivotX, this.boundsPivotY);
        matrix.mapRect(out, this.bounds);
    }

    public void restrict(float x, float y, float extraX, float extraY, PointF out) {
        float[] fArr = tmpPointArr;
        fArr[0] = x;
        fArr[1] = y;
        float f = this.boundsRotation;
        if (f != 0.0f) {
            Matrix matrix = tmpMatrix;
            matrix.setRotate(-f, this.boundsPivotX, this.boundsPivotY);
            matrix.mapPoints(fArr);
        }
        fArr[0] = MathUtils.restrict(fArr[0], this.bounds.left - extraX, this.bounds.right + extraX);
        fArr[1] = MathUtils.restrict(fArr[1], this.bounds.top - extraY, this.bounds.bottom + extraY);
        float f2 = this.boundsRotation;
        if (f2 != 0.0f) {
            Matrix matrix2 = tmpMatrix;
            matrix2.setRotate(f2, this.boundsPivotX, this.boundsPivotY);
            matrix2.mapPoints(fArr);
        }
        out.set(fArr[0], fArr[1]);
    }

    public void restrict(float x, float y, PointF out) {
        restrict(x, y, 0.0f, 0.0f, out);
    }
}
