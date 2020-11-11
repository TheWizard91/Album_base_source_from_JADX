package com.alexvasilkov.gestures.utils;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import com.alexvasilkov.gestures.GestureController;
import com.alexvasilkov.gestures.Settings;
import com.alexvasilkov.gestures.State;

public class CropUtils {
    private CropUtils() {
    }

    public static Bitmap crop(Drawable drawable, GestureController controller) {
        controller.stopAllAnimations();
        controller.updateState();
        return crop(drawable, controller.getState(), controller.getSettings());
    }

    @Deprecated
    public static Bitmap crop(Drawable drawable, State state, Settings settings) {
        if (drawable == null) {
            return null;
        }
        float zoom = state.getZoom();
        int width = Math.round(((float) settings.getMovementAreaW()) / zoom);
        int height = Math.round(((float) settings.getMovementAreaH()) / zoom);
        Rect pos = new Rect();
        GravityUtils.getMovementAreaPosition(settings, pos);
        Matrix matrix = new Matrix();
        state.get(matrix);
        matrix.postScale(1.0f / zoom, 1.0f / zoom, (float) pos.left, (float) pos.top);
        matrix.postTranslate((float) (-pos.left), (float) (-pos.top));
        try {
            Bitmap dst = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(dst);
            canvas.concat(matrix);
            drawable.draw(canvas);
            return dst;
        } catch (OutOfMemoryError e) {
            return null;
        }
    }
}
