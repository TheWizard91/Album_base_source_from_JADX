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
import android.view.View;
import android.widget.ImageView;
import com.google.common.primitives.Ints;

public class CircleImageView extends ImageView {
    private static final int DEFAULT_PAINT_FLAGS = 3;
    private static final Matrix tmpMatrix = new Matrix();
    private Paint bitmapPaint;
    private boolean isCircle;
    private RectF rect;

    public CircleImageView(Context context) {
        this(context, (AttributeSet) null, 0);
    }

    public CircleImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CircleImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        if (this.bitmapPaint == null) {
            this.bitmapPaint = new Paint(3);
            this.rect = new RectF();
            this.isCircle = true;
        }
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = View.MeasureSpec.getSize(widthMeasureSpec);
        super.onMeasure(View.MeasureSpec.makeMeasureSpec(width, Ints.MAX_POWER_OF_TWO), View.MeasureSpec.makeMeasureSpec(width, Ints.MAX_POWER_OF_TWO));
    }

    /* access modifiers changed from: protected */
    public boolean setFrame(int left, int top, int right, int bottom) {
        boolean changed = super.setFrame(left, top, right, bottom);
        this.rect.set((float) getPaddingLeft(), (float) getPaddingTop(), (float) (getWidth() - getPaddingRight()), (float) (getHeight() - getPaddingBottom()));
        setup();
        return changed;
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        init();
        if (this.bitmapPaint.getShader() == null) {
            super.onDraw(canvas);
            return;
        }
        canvas.drawRoundRect(this.rect, this.rect.width() * 0.5f, this.rect.height() * 0.5f, this.bitmapPaint);
    }

    public void setImageResource(int resId) {
        super.setImageResource(resId);
        setup();
    }

    public void setImageDrawable(Drawable drawable) {
        super.setImageDrawable(drawable);
        setup();
    }

    public void setImageMatrix(Matrix matrix) {
        super.setImageMatrix(matrix);
        setup();
    }

    public void setCircle(boolean isCircle2) {
        this.isCircle = isCircle2;
        setup();
    }

    private void setup() {
        init();
        Bitmap bitmap = this.isCircle ? getBitmapFromDrawable(getDrawable()) : null;
        if (bitmap != null) {
            BitmapShader bitmapShader = new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
            Matrix matrix = tmpMatrix;
            matrix.set(getImageMatrix());
            matrix.postTranslate((float) getPaddingLeft(), (float) getPaddingTop());
            bitmapShader.setLocalMatrix(matrix);
            this.bitmapPaint.setShader(bitmapShader);
        } else {
            this.bitmapPaint.setShader((Shader) null);
        }
        invalidate();
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
