package com.alexvasilkov.gestures.commons;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import com.alexvasilkov.gestures.Settings;
import com.alexvasilkov.gestures.internal.UnitsUtils;
import com.alexvasilkov.gestures.utils.GravityUtils;

@Deprecated
public class FinderView extends View {
    public static final int DEFAULT_BACK_COLOR = Color.argb(128, 0, 0, 0);
    public static final int DEFAULT_BORDER_COLOR = -1;
    public static final float DEFAULT_BORDER_WIDTH = 2.0f;
    private static final Rect tmpRect = new Rect();
    private int backColor;
    private final Paint paintClear;
    private final Paint paintStroke;
    private final RectF rect;
    private float rounding;
    private Settings settings;
    private final RectF strokeRect;

    public FinderView(Context context) {
        this(context, (AttributeSet) null);
    }

    public FinderView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.rect = new RectF();
        this.rounding = 0.0f;
        this.strokeRect = new RectF();
        Paint paint = new Paint();
        this.paintStroke = paint;
        Paint paint2 = new Paint();
        this.paintClear = paint2;
        paint2.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
        paint2.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);
        paint.setAntiAlias(true);
        setBackColor(DEFAULT_BACK_COLOR);
        setBorderColor(-1);
        setBorderWidth(1, 2.0f);
    }

    public void setBackColor(int color) {
        this.backColor = color;
    }

    public void setBorderColor(int color) {
        this.paintStroke.setColor(color);
    }

    public void setBorderWidth(float width) {
        this.paintStroke.setStrokeWidth(width);
    }

    public void setBorderWidth(int unit, float width) {
        setBorderWidth(UnitsUtils.toPixels(getContext(), unit, width));
    }

    public void setSettings(Settings settings2) {
        this.settings = settings2;
        update();
    }

    public void setRounded(boolean rounded) {
        this.rounding = rounded ? 1.0f : 0.0f;
        update();
    }

    public void update(boolean animate) {
        update();
    }

    public void update() {
        if (this.settings != null && getWidth() > 0 && getHeight() > 0) {
            Settings settings2 = this.settings;
            Rect rect2 = tmpRect;
            GravityUtils.getMovementAreaPosition(settings2, rect2);
            this.rect.set(rect2);
            this.rect.offset((float) getPaddingLeft(), (float) getPaddingTop());
            this.strokeRect.set(this.rect);
            float halfStroke = this.paintStroke.getStrokeWidth() * 0.5f;
            this.strokeRect.inset(-halfStroke, -halfStroke);
            invalidate();
        }
    }

    /* access modifiers changed from: protected */
    public void onSizeChanged(int width, int height, int oldWidth, int oldHeight) {
        update();
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        float rx = this.rounding * 0.5f * this.rect.width();
        float ry = this.rounding * 0.5f * this.rect.height();
        if (Build.VERSION.SDK_INT >= 21) {
            canvas.saveLayer(0.0f, 0.0f, (float) canvas.getWidth(), (float) canvas.getHeight(), (Paint) null);
        } else {
            canvas.saveLayer(0.0f, 0.0f, (float) canvas.getWidth(), (float) canvas.getHeight(), (Paint) null, 0);
        }
        canvas.drawColor(this.backColor);
        canvas.drawRoundRect(this.rect, rx, ry, this.paintClear);
        canvas.restore();
        canvas.drawRoundRect(this.strokeRect, rx, ry, this.paintStroke);
    }
}
