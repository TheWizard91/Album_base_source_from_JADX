package com.alexvasilkov.gestures;

import android.graphics.Matrix;

public class State {
    public static final float EPSILON = 0.001f;
    private final Matrix matrix = new Matrix();
    private final float[] matrixValues = new float[9];
    private float rotation;

    /* renamed from: x */
    private float f48x;

    /* renamed from: y */
    private float f49y;
    private float zoom = 1.0f;

    public float getX() {
        return this.f48x;
    }

    public float getY() {
        return this.f49y;
    }

    public float getZoom() {
        return this.zoom;
    }

    public float getRotation() {
        return this.rotation;
    }

    public boolean isEmpty() {
        return this.f48x == 0.0f && this.f49y == 0.0f && this.zoom == 1.0f && this.rotation == 0.0f;
    }

    public void get(Matrix matrix2) {
        matrix2.set(this.matrix);
    }

    public void translateBy(float dx, float dy) {
        this.matrix.postTranslate(dx, dy);
        updateFromMatrix(false, false);
    }

    public void translateTo(float x, float y) {
        this.matrix.postTranslate((-this.f48x) + x, (-this.f49y) + y);
        updateFromMatrix(false, false);
    }

    public void zoomBy(float factor, float pivotX, float pivotY) {
        this.matrix.postScale(factor, factor, pivotX, pivotY);
        updateFromMatrix(true, false);
    }

    public void zoomTo(float zoom2, float pivotX, float pivotY) {
        Matrix matrix2 = this.matrix;
        float f = this.zoom;
        matrix2.postScale(zoom2 / f, zoom2 / f, pivotX, pivotY);
        updateFromMatrix(true, false);
    }

    public void rotateBy(float angle, float pivotX, float pivotY) {
        this.matrix.postRotate(angle, pivotX, pivotY);
        updateFromMatrix(false, true);
    }

    public void rotateTo(float angle, float pivotX, float pivotY) {
        this.matrix.postRotate((-this.rotation) + angle, pivotX, pivotY);
        updateFromMatrix(false, true);
    }

    public void set(float x, float y, float zoom2, float rotation2) {
        while (rotation2 < -180.0f) {
            rotation2 += 360.0f;
        }
        while (rotation2 > 180.0f) {
            rotation2 -= 360.0f;
        }
        this.f48x = x;
        this.f49y = y;
        this.zoom = zoom2;
        this.rotation = rotation2;
        this.matrix.reset();
        if (zoom2 != 1.0f) {
            this.matrix.postScale(zoom2, zoom2);
        }
        if (rotation2 != 0.0f) {
            this.matrix.postRotate(rotation2);
        }
        this.matrix.postTranslate(x, y);
    }

    public void set(Matrix matrix2) {
        this.matrix.set(matrix2);
        updateFromMatrix(true, true);
    }

    public void set(State other) {
        this.f48x = other.f48x;
        this.f49y = other.f49y;
        this.zoom = other.zoom;
        this.rotation = other.rotation;
        this.matrix.set(other.matrix);
    }

    public State copy() {
        State copy = new State();
        copy.set(this);
        return copy;
    }

    private void updateFromMatrix(boolean updateZoom, boolean updateRotation) {
        this.matrix.getValues(this.matrixValues);
        float[] fArr = this.matrixValues;
        this.f48x = fArr[2];
        this.f49y = fArr[5];
        if (updateZoom) {
            this.zoom = (float) Math.hypot((double) fArr[1], (double) fArr[4]);
        }
        if (updateRotation) {
            float[] fArr2 = this.matrixValues;
            this.rotation = (float) Math.toDegrees(Math.atan2((double) fArr2[3], (double) fArr2[4]));
        }
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        State state = (State) obj;
        if (!equals(state.f48x, this.f48x) || !equals(state.f49y, this.f49y) || !equals(state.zoom, this.zoom) || !equals(state.rotation, this.rotation)) {
            return false;
        }
        return true;
    }

    public int hashCode() {
        float f = this.f48x;
        int i = 0;
        int floatToIntBits = (f != 0.0f ? Float.floatToIntBits(f) : 0) * 31;
        float f2 = this.f49y;
        int result = (floatToIntBits + (f2 != 0.0f ? Float.floatToIntBits(f2) : 0)) * 31;
        float f3 = this.zoom;
        int result2 = (result + (f3 != 0.0f ? Float.floatToIntBits(f3) : 0)) * 31;
        float f4 = this.rotation;
        if (f4 != 0.0f) {
            i = Float.floatToIntBits(f4);
        }
        return result2 + i;
    }

    public String toString() {
        return "{x=" + this.f48x + ",y=" + this.f49y + ",zoom=" + this.zoom + ",rotation=" + this.rotation + "}";
    }

    public static boolean equals(float v1, float v2) {
        return v1 >= v2 - 0.001f && v1 <= 0.001f + v2;
    }

    public static int compare(float v1, float v2) {
        if (v1 > v2 + 0.001f) {
            return 1;
        }
        return v1 < v2 - 0.001f ? -1 : 0;
    }
}
