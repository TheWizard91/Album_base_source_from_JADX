package com.alexvasilkov.gestures.utils;

import android.graphics.Matrix;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.RectF;
import android.view.Gravity;
import com.alexvasilkov.gestures.Settings;
import com.alexvasilkov.gestures.State;

public class GravityUtils {
    private static final Matrix tmpMatrix = new Matrix();
    private static final Rect tmpRect1 = new Rect();
    private static final Rect tmpRect2 = new Rect();
    private static final RectF tmpRectF = new RectF();

    private GravityUtils() {
    }

    public static void getImagePosition(State state, Settings settings, Rect out) {
        Matrix matrix = tmpMatrix;
        state.get(matrix);
        getImagePosition(matrix, settings, out);
    }

    public static void getImagePosition(Matrix matrix, Settings settings, Rect out) {
        RectF rectF = tmpRectF;
        rectF.set(0.0f, 0.0f, (float) settings.getImageW(), (float) settings.getImageH());
        matrix.mapRect(rectF);
        int w = Math.round(rectF.width());
        int h = Math.round(rectF.height());
        Rect rect = tmpRect1;
        rect.set(0, 0, settings.getViewportW(), settings.getViewportH());
        Gravity.apply(settings.getGravity(), w, h, rect, out);
    }

    public static void getMovementAreaPosition(Settings settings, Rect out) {
        Rect rect = tmpRect1;
        rect.set(0, 0, settings.getViewportW(), settings.getViewportH());
        Gravity.apply(settings.getGravity(), settings.getMovementAreaW(), settings.getMovementAreaH(), rect, out);
    }

    public static void getDefaultPivot(Settings settings, Point out) {
        Rect rect = tmpRect2;
        getMovementAreaPosition(settings, rect);
        int gravity = settings.getGravity();
        Rect rect2 = tmpRect1;
        Gravity.apply(gravity, 0, 0, rect, rect2);
        out.set(rect2.left, rect2.top);
    }
}
