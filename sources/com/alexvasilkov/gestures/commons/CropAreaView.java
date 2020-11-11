package com.alexvasilkov.gestures.commons;

import android.content.Context;
import android.content.res.TypedArray;
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
import com.alexvasilkov.gestures.C2271R;
import com.alexvasilkov.gestures.Settings;
import com.alexvasilkov.gestures.internal.AnimationEngine;
import com.alexvasilkov.gestures.internal.UnitsUtils;
import com.alexvasilkov.gestures.utils.FloatScroller;
import com.alexvasilkov.gestures.utils.GravityUtils;
import com.alexvasilkov.gestures.utils.MathUtils;
import com.alexvasilkov.gestures.views.GestureImageView;

public class CropAreaView extends View {
    private static final int BACK_COLOR = Color.argb(160, 0, 0, 0);
    private static final int BORDER_COLOR = -1;
    private static final float BORDER_WIDTH_DP = 2.0f;
    private static final float NO_ASPECT = 0.0f;
    public static final float ORIGINAL_ASPECT = -1.0f;
    private static final Rect tmpRect = new Rect();
    private static final RectF tmpRectF = new RectF();
    private final AnimationEngine animationEngine;
    /* access modifiers changed from: private */
    public final RectF areaRect;
    private float aspect;
    private int backColor;
    private int borderColor;
    private float borderWidth;
    /* access modifiers changed from: private */
    public final RectF endRect;
    /* access modifiers changed from: private */
    public float endRounding;
    private int horizontalRules;
    private GestureImageView imageView;
    private final Paint paint;
    private final Paint paintClear;
    private float rounding;
    private float rulesWidth;
    /* access modifiers changed from: private */
    public final RectF startRect;
    /* access modifiers changed from: private */
    public float startRounding;
    /* access modifiers changed from: private */
    public final FloatScroller stateScroller;
    private final RectF strokeRect;
    private int verticalRules;

    public CropAreaView(Context context) {
        this(context, (AttributeSet) null);
    }

    public CropAreaView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.areaRect = new RectF();
        float f = 0.0f;
        this.rounding = 0.0f;
        this.strokeRect = new RectF();
        this.startRect = new RectF();
        this.endRect = new RectF();
        Paint paint2 = new Paint();
        this.paint = paint2;
        Paint paint3 = new Paint();
        this.paintClear = paint3;
        this.stateScroller = new FloatScroller();
        this.animationEngine = new LocalAnimationEngine();
        paint3.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
        paint3.setAntiAlias(true);
        paint2.setAntiAlias(true);
        float defaultBorderWidth = UnitsUtils.toPixels(getContext(), 1, 2.0f);
        TypedArray arr = context.obtainStyledAttributes(attrs, C2271R.styleable.CropAreaView);
        this.backColor = arr.getColor(C2271R.styleable.CropAreaView_gest_backgroundColor, BACK_COLOR);
        this.borderColor = arr.getColor(C2271R.styleable.CropAreaView_gest_borderColor, -1);
        this.borderWidth = arr.getDimension(C2271R.styleable.CropAreaView_gest_borderWidth, defaultBorderWidth);
        this.horizontalRules = arr.getInt(C2271R.styleable.CropAreaView_gest_rulesHorizontal, 0);
        this.verticalRules = arr.getInt(C2271R.styleable.CropAreaView_gest_rulesVertical, 0);
        this.rulesWidth = arr.getDimension(C2271R.styleable.CropAreaView_gest_rulesWidth, 0.0f);
        boolean rounded = arr.getBoolean(C2271R.styleable.CropAreaView_gest_rounded, false);
        this.aspect = arr.getFloat(C2271R.styleable.CropAreaView_gest_aspect, 0.0f);
        arr.recycle();
        f = rounded ? 1.0f : f;
        this.endRounding = f;
        this.rounding = f;
    }

    public void setBackColor(int color) {
        this.backColor = color;
        invalidate();
    }

    public void setBorderColor(int color) {
        this.borderColor = color;
        invalidate();
    }

    public void setBorderWidth(float width) {
        this.borderWidth = width;
        invalidate();
    }

    public void setRulesCount(int horizontalRules2, int verticalRules2) {
        this.horizontalRules = horizontalRules2;
        this.verticalRules = verticalRules2;
        invalidate();
    }

    public void setRulesWidth(float width) {
        this.rulesWidth = width;
        invalidate();
    }

    public void setRounded(boolean rounded) {
        this.startRounding = this.rounding;
        this.endRounding = rounded ? 1.0f : 0.0f;
    }

    public void setAspect(float aspect2) {
        this.aspect = aspect2;
    }

    public void setImageView(GestureImageView imageView2) {
        this.imageView = imageView2;
        imageView2.getController().getSettings().setFitMethod(Settings.Fit.OUTSIDE).setFillViewport(true).setFlingEnabled(false);
        update(false);
    }

    public void update(boolean animate) {
        Settings settings;
        GestureImageView gestureImageView = this.imageView;
        if (gestureImageView == null) {
            settings = null;
        } else {
            settings = gestureImageView.getController().getSettings();
        }
        if (settings != null && getWidth() > 0 && getHeight() > 0) {
            float f = this.aspect;
            if (f > 0.0f || f == -1.0f) {
                int width = (getWidth() - getPaddingLeft()) - getPaddingRight();
                int height = (getHeight() - getPaddingTop()) - getPaddingBottom();
                float f2 = this.aspect;
                if (f2 == -1.0f) {
                    f2 = ((float) settings.getImageW()) / ((float) settings.getImageH());
                }
                float realAspect = f2;
                if (realAspect > ((float) width) / ((float) height)) {
                    settings.setMovementArea(width, (int) (((float) width) / realAspect));
                } else {
                    settings.setMovementArea((int) (((float) height) * realAspect), height);
                }
                if (animate) {
                    this.imageView.getController().animateKeepInBounds();
                } else {
                    this.imageView.getController().updateState();
                }
            }
            this.startRect.set(this.areaRect);
            Rect rect = tmpRect;
            GravityUtils.getMovementAreaPosition(settings, rect);
            this.endRect.set(rect);
            this.stateScroller.forceFinished();
            if (animate) {
                this.stateScroller.setDuration(settings.getAnimationsDuration());
                this.stateScroller.startScroll(0.0f, 1.0f);
                this.animationEngine.start();
                return;
            }
            setBounds(this.endRect, this.endRounding);
        }
    }

    /* access modifiers changed from: private */
    public void setBounds(RectF rect, float rounding2) {
        this.areaRect.set(rect);
        this.rounding = rounding2;
        this.strokeRect.set(rect);
        float halfStroke = this.borderWidth * 0.5f;
        this.strokeRect.inset(-halfStroke, -halfStroke);
        invalidate();
    }

    /* access modifiers changed from: protected */
    public void onSizeChanged(int width, int height, int oldWidth, int oldHeight) {
        float sizeY;
        float sizeX;
        update(false);
        GestureImageView gestureImageView = this.imageView;
        if (gestureImageView != null) {
            gestureImageView.getController().resetState();
        }
        if (isInEditMode()) {
            float w = (float) ((width - getPaddingLeft()) - getPaddingRight());
            float h = (float) ((height - getPaddingTop()) - getPaddingBottom());
            float sizeY2 = this.aspect;
            if (sizeY2 <= 0.0f) {
                sizeX = (float) width;
                sizeY = (float) height;
            } else if (sizeY2 > w / h) {
                sizeY = w / sizeY2;
                sizeX = w;
            } else {
                sizeX = sizeY2 * h;
                sizeY = h;
            }
            this.areaRect.set((((float) width) - sizeX) * 0.5f, (((float) height) - sizeY) * 0.5f, (((float) width) + sizeX) * 0.5f, (((float) height) + sizeY) * 0.5f);
            this.strokeRect.set(this.areaRect);
        }
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        if (this.rounding == 0.0f || isInEditMode()) {
            drawRectHole(canvas);
        } else {
            drawRoundedHole(canvas);
        }
        drawBorderAndRules(canvas);
    }

    private void drawRectHole(Canvas canvas) {
        this.paint.setStyle(Paint.Style.FILL);
        this.paint.setColor(this.backColor);
        RectF rectF = tmpRectF;
        rectF.set(0.0f, 0.0f, (float) canvas.getWidth(), this.areaRect.top);
        canvas.drawRect(rectF, this.paint);
        rectF.set(0.0f, this.areaRect.bottom, (float) canvas.getWidth(), (float) canvas.getHeight());
        canvas.drawRect(rectF, this.paint);
        rectF.set(0.0f, this.areaRect.top, this.areaRect.left, this.areaRect.bottom);
        canvas.drawRect(rectF, this.paint);
        rectF.set(this.areaRect.right, this.areaRect.top, (float) canvas.getWidth(), this.areaRect.bottom);
        canvas.drawRect(rectF, this.paint);
    }

    private void drawRoundedHole(Canvas canvas) {
        this.paint.setStyle(Paint.Style.FILL);
        this.paint.setColor(this.backColor);
        if (Build.VERSION.SDK_INT >= 21) {
            canvas.saveLayer(0.0f, 0.0f, (float) canvas.getWidth(), (float) canvas.getHeight(), (Paint) null);
        } else {
            canvas.saveLayer(0.0f, 0.0f, (float) canvas.getWidth(), (float) canvas.getHeight(), (Paint) null, 0);
        }
        canvas.drawPaint(this.paint);
        canvas.drawRoundRect(this.areaRect, this.rounding * 0.5f * this.areaRect.width(), this.rounding * 0.5f * this.areaRect.height(), this.paintClear);
        canvas.restore();
    }

    private void drawBorderAndRules(Canvas canvas) {
        this.paint.setStyle(Paint.Style.STROKE);
        this.paint.setColor(this.borderColor);
        Paint paint2 = this.paint;
        float f = this.rulesWidth;
        if (f == 0.0f) {
            f = this.borderWidth * 0.5f;
        }
        paint2.setStrokeWidth(f);
        float rx = this.rounding * 0.5f * this.areaRect.width();
        float ry = this.rounding * 0.5f * this.areaRect.height();
        for (int i = 0; i < this.verticalRules; i++) {
            float px = this.areaRect.left + (((float) (i + 1)) * (this.areaRect.width() / ((float) (this.verticalRules + 1))));
            float dy = getRulesOffset(px, rx, ry, this.areaRect.left, this.areaRect.right);
            canvas.drawLine(px, this.areaRect.top + dy, px, this.areaRect.bottom - dy, this.paint);
        }
        for (int i2 = 0; i2 < this.horizontalRules; i2++) {
            float py = this.areaRect.top + (((float) (i2 + 1)) * (this.areaRect.height() / ((float) (this.horizontalRules + 1))));
            float dx = getRulesOffset(py, ry, rx, this.areaRect.top, this.areaRect.bottom);
            canvas.drawLine(this.areaRect.left + dx, py, this.areaRect.right - dx, py, this.paint);
        }
        this.paint.setStyle(Paint.Style.STROKE);
        this.paint.setColor(this.borderColor);
        this.paint.setStrokeWidth(this.borderWidth);
        canvas.drawRoundRect(this.strokeRect, rx, ry, this.paint);
    }

    private float getRulesOffset(float x, float rx, float ry, float start, float end) {
        float dx = 0.0f;
        if (x - start < rx) {
            dx = (start + rx) - x;
        } else if (end - x < rx) {
            dx = (x - end) + rx;
        }
        if (rx == 0.0f) {
            return 0.0f;
        }
        return ry * (1.0f - ((float) Math.sqrt((double) (1.0f - (((dx * dx) / rx) / rx)))));
    }

    private class LocalAnimationEngine extends AnimationEngine {
        LocalAnimationEngine() {
            super(CropAreaView.this);
        }

        public boolean onStep() {
            if (CropAreaView.this.stateScroller.isFinished()) {
                return false;
            }
            CropAreaView.this.stateScroller.computeScroll();
            float state = CropAreaView.this.stateScroller.getCurr();
            MathUtils.interpolate(CropAreaView.this.areaRect, CropAreaView.this.startRect, CropAreaView.this.endRect, state);
            float rounding = MathUtils.interpolate(CropAreaView.this.startRounding, CropAreaView.this.endRounding, state);
            CropAreaView cropAreaView = CropAreaView.this;
            cropAreaView.setBounds(cropAreaView.areaRect, rounding);
            return true;
        }
    }
}
