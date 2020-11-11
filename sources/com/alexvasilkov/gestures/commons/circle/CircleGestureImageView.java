package com.alexvasilkov.gestures.commons.circle;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import com.alexvasilkov.gestures.State;
import com.alexvasilkov.gestures.animation.ViewPositionAnimator;
import com.alexvasilkov.gestures.internal.DebugOverlay;
import com.alexvasilkov.gestures.internal.GestureDebug;
import com.alexvasilkov.gestures.utils.MathUtils;
import com.alexvasilkov.gestures.views.GestureImageView;

public class CircleGestureImageView extends GestureImageView {
    private static final int DEFAULT_PAINT_FLAGS = 3;
    private static final Matrix tmpMatrix = new Matrix();
    private final Paint bitmapPaint;
    private final RectF clipRect;
    private float clipRotation;
    /* access modifiers changed from: private */
    public float cornersState;
    private boolean isCircle;

    public CircleGestureImageView(Context context) {
        this(context, (AttributeSet) null, 0);
    }

    public CircleGestureImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CircleGestureImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.bitmapPaint = new Paint(3);
        this.clipRect = new RectF();
        this.isCircle = true;
        getPositionAnimator().addPositionUpdateListener(new ViewPositionAnimator.PositionUpdateListener() {
            public void onPositionUpdate(float position, boolean isLeaving) {
                float unused = CircleGestureImageView.this.cornersState = MathUtils.restrict(position / CircleGestureImageView.this.getPositionAnimator().getToPosition(), 0.0f, 1.0f);
            }
        });
    }

    public void draw(Canvas canvas) {
        if (this.cornersState == 1.0f || this.clipRect.isEmpty() || this.bitmapPaint.getShader() == null) {
            super.draw(canvas);
            return;
        }
        float rx = this.clipRect.width() * 0.5f * (1.0f - this.cornersState);
        float ry = this.clipRect.height() * 0.5f * (1.0f - this.cornersState);
        canvas.rotate(this.clipRotation, this.clipRect.centerX(), this.clipRect.centerY());
        canvas.drawRoundRect(this.clipRect, rx, ry, this.bitmapPaint);
        canvas.rotate(-this.clipRotation, this.clipRect.centerX(), this.clipRect.centerY());
        if (GestureDebug.isDrawDebugOverlay()) {
            DebugOverlay.drawDebug(this, canvas);
        }
    }

    public void clipView(RectF rect, float rotation) {
        if (rect == null) {
            this.clipRect.setEmpty();
        } else {
            this.clipRect.set(rect);
        }
        this.clipRotation = rotation;
        updateShaderMatrix();
        super.clipView(rect, rotation);
    }

    public void setImageDrawable(Drawable drawable) {
        super.setImageDrawable(drawable);
        setup();
    }

    public void setCircle(boolean isCircle2) {
        this.isCircle = isCircle2;
        setup();
    }

    public void setImageMatrix(Matrix matrix) {
        super.setImageMatrix(matrix);
        updateShaderMatrix();
    }

    private void setup() {
        Bitmap bitmap = this.isCircle ? getBitmapFromDrawable(getDrawable()) : null;
        if (bitmap != null) {
            this.bitmapPaint.setShader(new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP));
            updateShaderMatrix();
        } else {
            this.bitmapPaint.setShader((Shader) null);
        }
        invalidate();
    }

    private void updateShaderMatrix() {
        if (!this.clipRect.isEmpty() && this.bitmapPaint.getShader() != null) {
            State state = getController().getState();
            Matrix matrix = tmpMatrix;
            state.get(matrix);
            matrix.postTranslate((float) getPaddingLeft(), (float) getPaddingTop());
            matrix.postRotate(-this.clipRotation, this.clipRect.centerX(), this.clipRect.centerY());
            this.bitmapPaint.getShader().setLocalMatrix(matrix);
        }
    }

    /* access modifiers changed from: protected */
    public Bitmap getBitmapFromDrawable(Drawable drawable) {
        if (drawable == null) {
            return null;
        }
        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable) drawable).getBitmap();
        }
        throw new RuntimeException("For better performance only BitmapDrawables are supported, but you can override getBitmapFromDrawable() and build bitmap on your own");
    }
}
