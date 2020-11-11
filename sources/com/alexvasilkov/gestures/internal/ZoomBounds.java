package com.alexvasilkov.gestures.internal;

import android.graphics.Matrix;
import android.graphics.RectF;
import com.alexvasilkov.gestures.Settings;
import com.alexvasilkov.gestures.State;
import com.alexvasilkov.gestures.utils.MathUtils;

public class ZoomBounds {
    private static final Matrix tmpMatrix = new Matrix();
    private static final RectF tmpRectF = new RectF();
    private float fitZoom;
    private float maxZoom;
    private float minZoom;
    private final Settings settings;

    public ZoomBounds(Settings settings2) {
        this.settings = settings2;
    }

    public ZoomBounds set(State state) {
        float imageWidth = (float) this.settings.getImageW();
        float imageHeight = (float) this.settings.getImageH();
        float areaWidth = (float) this.settings.getMovementAreaW();
        float areaHeight = (float) this.settings.getMovementAreaH();
        float f = 1.0f;
        if (imageWidth == 0.0f || imageHeight == 0.0f || areaWidth == 0.0f || areaHeight == 0.0f) {
            this.fitZoom = 1.0f;
            this.maxZoom = 1.0f;
            this.minZoom = 1.0f;
            return this;
        }
        this.minZoom = this.settings.getMinZoom();
        this.maxZoom = this.settings.getMaxZoom();
        float rotation = state.getRotation();
        if (!State.equals(rotation, 0.0f)) {
            if (this.settings.getFitMethod() == Settings.Fit.OUTSIDE) {
                Matrix matrix = tmpMatrix;
                matrix.setRotate(-rotation);
                RectF rectF = tmpRectF;
                rectF.set(0.0f, 0.0f, areaWidth, areaHeight);
                matrix.mapRect(rectF);
                areaWidth = rectF.width();
                areaHeight = rectF.height();
            } else {
                Matrix matrix2 = tmpMatrix;
                matrix2.setRotate(rotation);
                RectF rectF2 = tmpRectF;
                rectF2.set(0.0f, 0.0f, imageWidth, imageHeight);
                matrix2.mapRect(rectF2);
                imageWidth = rectF2.width();
                imageHeight = rectF2.height();
            }
        }
        int i = C05781.$SwitchMap$com$alexvasilkov$gestures$Settings$Fit[this.settings.getFitMethod().ordinal()];
        if (i == 1) {
            this.fitZoom = areaWidth / imageWidth;
        } else if (i == 2) {
            this.fitZoom = areaHeight / imageHeight;
        } else if (i == 3) {
            this.fitZoom = Math.min(areaWidth / imageWidth, areaHeight / imageHeight);
        } else if (i != 4) {
            float f2 = this.minZoom;
            if (f2 > 0.0f) {
                f = f2;
            }
            this.fitZoom = f;
        } else {
            this.fitZoom = Math.max(areaWidth / imageWidth, areaHeight / imageHeight);
        }
        if (this.minZoom <= 0.0f) {
            this.minZoom = this.fitZoom;
        }
        if (this.maxZoom <= 0.0f) {
            this.maxZoom = this.fitZoom;
        }
        if (this.fitZoom > this.maxZoom) {
            if (this.settings.isFillViewport()) {
                this.maxZoom = this.fitZoom;
            } else {
                this.fitZoom = this.maxZoom;
            }
        }
        float f3 = this.minZoom;
        float f4 = this.maxZoom;
        if (f3 > f4) {
            this.minZoom = f4;
        }
        if (this.fitZoom < this.minZoom) {
            if (this.settings.isFillViewport()) {
                this.minZoom = this.fitZoom;
            } else {
                this.fitZoom = this.minZoom;
            }
        }
        return this;
    }

    /* renamed from: com.alexvasilkov.gestures.internal.ZoomBounds$1 */
    static /* synthetic */ class C05781 {
        static final /* synthetic */ int[] $SwitchMap$com$alexvasilkov$gestures$Settings$Fit;

        static {
            int[] iArr = new int[Settings.Fit.values().length];
            $SwitchMap$com$alexvasilkov$gestures$Settings$Fit = iArr;
            try {
                iArr[Settings.Fit.HORIZONTAL.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$com$alexvasilkov$gestures$Settings$Fit[Settings.Fit.VERTICAL.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$com$alexvasilkov$gestures$Settings$Fit[Settings.Fit.INSIDE.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$com$alexvasilkov$gestures$Settings$Fit[Settings.Fit.OUTSIDE.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
            try {
                $SwitchMap$com$alexvasilkov$gestures$Settings$Fit[Settings.Fit.NONE.ordinal()] = 5;
            } catch (NoSuchFieldError e5) {
            }
        }
    }

    public float getMinZoom() {
        return this.minZoom;
    }

    public float getMaxZoom() {
        return this.maxZoom;
    }

    public float getFitZoom() {
        return this.fitZoom;
    }

    public float restrict(float zoom, float extraZoom) {
        return MathUtils.restrict(zoom, this.minZoom / extraZoom, this.maxZoom * extraZoom);
    }
}
