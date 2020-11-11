package com.alexvasilkov.gestures.utils;

import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.view.View;
import com.alexvasilkov.gestures.State;
import com.alexvasilkov.gestures.views.interfaces.ClipView;

public class ClipHelper implements ClipView {
    private static final Matrix tmpMatrix = new Matrix();
    private final RectF clipBounds = new RectF();
    private final RectF clipBoundsOld = new RectF();
    private final RectF clipRect = new RectF();
    private float clipRotation;
    private boolean isClipping;
    private final View view;

    public ClipHelper(View view2) {
        this.view = view2;
    }

    public void clipView(RectF rect, float rotation) {
        if (rect != null) {
            if (this.isClipping) {
                this.clipBoundsOld.set(this.clipBounds);
            } else {
                this.clipBoundsOld.set(0.0f, 0.0f, (float) this.view.getWidth(), (float) this.view.getHeight());
            }
            this.isClipping = true;
            this.clipRect.set(rect);
            this.clipRotation = rotation;
            this.clipBounds.set(this.clipRect);
            if (!State.equals(rotation, 0.0f)) {
                Matrix matrix = tmpMatrix;
                matrix.setRotate(rotation, this.clipRect.centerX(), this.clipRect.centerY());
                matrix.mapRect(this.clipBounds);
            }
            this.view.invalidate((int) Math.min(this.clipBounds.left, this.clipBoundsOld.left), (int) Math.min(this.clipBounds.top, this.clipBoundsOld.top), ((int) Math.max(this.clipBounds.right, this.clipBoundsOld.right)) + 1, ((int) Math.max(this.clipBounds.bottom, this.clipBoundsOld.bottom)) + 1);
        } else if (this.isClipping) {
            this.isClipping = false;
            this.view.invalidate();
        }
    }

    public void onPreDraw(Canvas canvas) {
        if (this.isClipping) {
            canvas.save();
            if (State.equals(this.clipRotation, 0.0f)) {
                canvas.clipRect(this.clipRect);
                return;
            }
            canvas.rotate(this.clipRotation, this.clipRect.centerX(), this.clipRect.centerY());
            canvas.clipRect(this.clipRect);
            canvas.rotate(-this.clipRotation, this.clipRect.centerX(), this.clipRect.centerY());
        }
    }

    public void onPostDraw(Canvas canvas) {
        if (this.isClipping) {
            canvas.restore();
        }
    }
}
