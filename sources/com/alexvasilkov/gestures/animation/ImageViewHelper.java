package com.alexvasilkov.gestures.animation;

import android.graphics.Matrix;
import android.graphics.RectF;
import android.widget.ImageView;

class ImageViewHelper {
    private static final RectF tmpDst = new RectF();
    private static final RectF tmpSrc = new RectF();

    private ImageViewHelper() {
    }

    static void applyScaleType(ImageView.ScaleType type, int dwidth, int dheight, int vwidth, int vheight, Matrix imageMatrix, Matrix outMatrix) {
        float scale;
        float scale2;
        if (ImageView.ScaleType.CENTER == type) {
            outMatrix.setTranslate(((float) (vwidth - dwidth)) * 0.5f, ((float) (vheight - dheight)) * 0.5f);
        } else if (ImageView.ScaleType.CENTER_CROP == type) {
            float dx = 0.0f;
            float dy = 0.0f;
            if (dwidth * vheight > vwidth * dheight) {
                scale2 = ((float) vheight) / ((float) dheight);
                dx = (((float) vwidth) - (((float) dwidth) * scale2)) * 0.5f;
            } else {
                scale2 = ((float) vwidth) / ((float) dwidth);
                dy = (((float) vheight) - (((float) dheight) * scale2)) * 0.5f;
            }
            outMatrix.setScale(scale2, scale2);
            outMatrix.postTranslate(dx, dy);
        } else if (ImageView.ScaleType.CENTER_INSIDE == type) {
            if (dwidth > vwidth || dheight > vheight) {
                scale = Math.min(((float) vwidth) / ((float) dwidth), ((float) vheight) / ((float) dheight));
            } else {
                scale = 1.0f;
            }
            outMatrix.setScale(scale, scale);
            outMatrix.postTranslate((((float) vwidth) - (((float) dwidth) * scale)) * 0.5f, (((float) vheight) - (((float) dheight) * scale)) * 0.5f);
        } else {
            Matrix.ScaleToFit scaleToFit = scaleTypeToScaleToFit(type);
            if (scaleToFit == null) {
                outMatrix.set(imageMatrix);
                return;
            }
            RectF rectF = tmpSrc;
            rectF.set(0.0f, 0.0f, (float) dwidth, (float) dheight);
            RectF rectF2 = tmpDst;
            rectF2.set(0.0f, 0.0f, (float) vwidth, (float) vheight);
            outMatrix.setRectToRect(rectF, rectF2, scaleToFit);
        }
    }

    /* renamed from: com.alexvasilkov.gestures.animation.ImageViewHelper$1 */
    static /* synthetic */ class C05721 {
        static final /* synthetic */ int[] $SwitchMap$android$widget$ImageView$ScaleType;

        static {
            int[] iArr = new int[ImageView.ScaleType.values().length];
            $SwitchMap$android$widget$ImageView$ScaleType = iArr;
            try {
                iArr[ImageView.ScaleType.FIT_XY.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$android$widget$ImageView$ScaleType[ImageView.ScaleType.FIT_START.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$android$widget$ImageView$ScaleType[ImageView.ScaleType.FIT_CENTER.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$android$widget$ImageView$ScaleType[ImageView.ScaleType.FIT_END.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
        }
    }

    private static Matrix.ScaleToFit scaleTypeToScaleToFit(ImageView.ScaleType type) {
        int i = C05721.$SwitchMap$android$widget$ImageView$ScaleType[type.ordinal()];
        if (i == 1) {
            return Matrix.ScaleToFit.FILL;
        }
        if (i == 2) {
            return Matrix.ScaleToFit.START;
        }
        if (i == 3) {
            return Matrix.ScaleToFit.CENTER;
        }
        if (i != 4) {
            return null;
        }
        return Matrix.ScaleToFit.END;
    }
}
