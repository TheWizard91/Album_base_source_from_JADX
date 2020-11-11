package com.alexvasilkov.gestures.utils;

import android.graphics.Matrix;
import android.graphics.RectF;
import com.alexvasilkov.gestures.State;

public class MathUtils {
    private static final Matrix tmpMatrix = new Matrix();
    private static final Matrix tmpMatrixInverse = new Matrix();

    private MathUtils() {
    }

    public static float restrict(float value, float minValue, float maxValue) {
        return Math.max(minValue, Math.min(value, maxValue));
    }

    public static float interpolate(float start, float end, float factor) {
        return ((end - start) * factor) + start;
    }

    public static void interpolate(RectF out, RectF start, RectF end, float factor) {
        out.left = interpolate(start.left, end.left, factor);
        out.top = interpolate(start.top, end.top, factor);
        out.right = interpolate(start.right, end.right, factor);
        out.bottom = interpolate(start.bottom, end.bottom, factor);
    }

    public static void interpolate(State out, State start, State end, float factor) {
        interpolate(out, start, start.getX(), start.getY(), end, end.getX(), end.getY(), factor);
    }

    public static void interpolate(State out, State start, float startPivotX, float startPivotY, State end, float endPivotX, float endPivotY, float factor) {
        out.set(start);
        if (!State.equals(start.getZoom(), end.getZoom())) {
            out.zoomTo(interpolate(start.getZoom(), end.getZoom(), factor), startPivotX, startPivotY);
        }
        float startRotation = start.getRotation();
        float endRotation = end.getRotation();
        float rotation = Float.NaN;
        if (Math.abs(startRotation - endRotation) > 180.0f) {
            float startRotationPositive = startRotation < 0.0f ? startRotation + 360.0f : startRotation;
            float endRotationPositive = endRotation < 0.0f ? 360.0f + endRotation : endRotation;
            if (!State.equals(startRotationPositive, endRotationPositive)) {
                rotation = interpolate(startRotationPositive, endRotationPositive, factor);
            }
        } else if (!State.equals(startRotation, endRotation)) {
            rotation = interpolate(startRotation, endRotation, factor);
        }
        if (!Float.isNaN(rotation)) {
            out.rotateTo(rotation, startPivotX, startPivotY);
        }
        out.translateBy(interpolate(0.0f, endPivotX - startPivotX, factor), interpolate(0.0f, endPivotY - startPivotY, factor));
    }

    public static void computeNewPosition(float[] point, State initialState, State finalState) {
        Matrix matrix = tmpMatrix;
        initialState.get(matrix);
        Matrix matrix2 = tmpMatrixInverse;
        matrix.invert(matrix2);
        matrix2.mapPoints(point);
        finalState.get(matrix);
        matrix.mapPoints(point);
    }
}
