package com.rey.material.drawable;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.os.SystemClock;
import android.util.AttributeSet;
import androidx.core.view.ViewCompat;
import com.rey.material.C2500R;
import com.rey.material.util.ColorUtil;
import com.rey.material.util.ThemeUtil;
import com.rey.material.util.ViewUtil;

public class CheckBoxDrawable extends Drawable implements Animatable {
    private static final float FILL_TIME = 0.4f;
    private static final float[] TICK_DATA = {0.0f, 0.473f, 0.367f, 0.839f, 1.0f, 0.207f};
    private int mAnimDuration;
    private boolean mAnimEnable;
    private float mAnimProgress;
    private RectF mBoxRect;
    private int mBoxSize;
    private boolean mChecked;
    private int mCornerRadius;
    private int mCurColor;
    private int mHeight;
    private boolean mInEditMode;
    private Paint mPaint;
    private int mPrevColor;
    private boolean mRunning;
    private long mStartTime;
    private ColorStateList mStrokeColor;
    private int mStrokeSize;
    private int mTickColor;
    private Path mTickPath;
    private float mTickPathProgress;
    private final Runnable mUpdater;
    private int mWidth;

    private CheckBoxDrawable(int width, int height, int boxSize, int cornerRadius, int strokeSize, ColorStateList strokeColor, int tickColor, int animDuration) {
        this.mRunning = false;
        this.mTickPathProgress = -1.0f;
        this.mChecked = false;
        this.mInEditMode = false;
        this.mAnimEnable = true;
        this.mUpdater = new Runnable() {
            public void run() {
                CheckBoxDrawable.this.update();
            }
        };
        this.mWidth = width;
        this.mHeight = height;
        this.mBoxSize = boxSize;
        this.mCornerRadius = cornerRadius;
        this.mStrokeSize = strokeSize;
        this.mStrokeColor = strokeColor;
        this.mTickColor = tickColor;
        this.mAnimDuration = animDuration;
        Paint paint = new Paint();
        this.mPaint = paint;
        paint.setAntiAlias(true);
        this.mBoxRect = new RectF();
        this.mTickPath = new Path();
    }

    public void setInEditMode(boolean b) {
        this.mInEditMode = b;
    }

    public void setAnimEnable(boolean b) {
        this.mAnimEnable = b;
    }

    public boolean isAnimEnable() {
        return this.mAnimEnable;
    }

    public int getIntrinsicWidth() {
        return this.mWidth;
    }

    public int getIntrinsicHeight() {
        return this.mHeight;
    }

    public int getMinimumWidth() {
        return this.mWidth;
    }

    public int getMinimumHeight() {
        return this.mHeight;
    }

    public boolean isStateful() {
        return true;
    }

    /* access modifiers changed from: protected */
    public void onBoundsChange(Rect bounds) {
        this.mBoxRect.set(bounds.exactCenterX() - ((float) (this.mBoxSize / 2)), bounds.exactCenterY() - ((float) (this.mBoxSize / 2)), bounds.exactCenterX() + ((float) (this.mBoxSize / 2)), bounds.exactCenterY() + ((float) (this.mBoxSize / 2)));
    }

    public void draw(Canvas canvas) {
        if (this.mChecked) {
            drawChecked(canvas);
        } else {
            drawUnchecked(canvas);
        }
    }

    private Path getTickPath(Path path, float x, float y, float size, float progress, boolean in) {
        Path path2 = path;
        float progress2 = progress;
        if (this.mTickPathProgress == progress2) {
            return path2;
        }
        this.mTickPathProgress = progress2;
        float[] fArr = TICK_DATA;
        float x1 = x + (fArr[0] * size);
        float y1 = y + (fArr[1] * size);
        float x2 = x + (fArr[2] * size);
        float y2 = y + (fArr[3] * size);
        float y3 = y + (fArr[5] * size);
        float d1 = (float) Math.sqrt(Math.pow((double) (x1 - x2), 2.0d) + Math.pow((double) (y1 - y2), 2.0d));
        float y22 = y2;
        float x3 = x + (fArr[4] * size);
        float midProgress = d1 / (d1 + ((float) Math.sqrt(Math.pow((double) (x1 - x2), 2.0d) + Math.pow((double) (y1 - y2), 2.0d))));
        path.reset();
        if (in) {
            path2.moveTo(x1, y1);
            if (progress2 < midProgress) {
                float progress3 = progress2 / midProgress;
                path2.lineTo(((1.0f - progress3) * x1) + (x2 * progress3), ((1.0f - progress3) * y1) + (y22 * progress3));
                float f = y22;
                float f2 = x3;
            } else {
                float progress4 = (progress2 - midProgress) / (1.0f - midProgress);
                float y23 = y22;
                path2.lineTo(x2, y23);
                path2.lineTo(((1.0f - progress4) * x2) + (x3 * progress4), ((1.0f - progress4) * y23) + (y3 * progress4));
                float f3 = x3;
            }
        } else {
            float y24 = y22;
            float x32 = x3;
            path2.moveTo(x32, y3);
            if (progress2 < midProgress) {
                float progress5 = progress2 / midProgress;
                path2.lineTo(x2, y24);
                path2.lineTo(((1.0f - progress5) * x1) + (x2 * progress5), ((1.0f - progress5) * y1) + (y24 * progress5));
            } else {
                float progress6 = (progress2 - midProgress) / (1.0f - midProgress);
                path2.lineTo(((1.0f - progress6) * x2) + (x32 * progress6), ((1.0f - progress6) * y24) + (y3 * progress6));
            }
        }
        return path2;
    }

    private void drawChecked(Canvas canvas) {
        float size = (float) (this.mBoxSize - (this.mStrokeSize * 2));
        float x = this.mBoxRect.left + ((float) this.mStrokeSize);
        float y = this.mBoxRect.top + ((float) this.mStrokeSize);
        if (isRunning()) {
            float f = this.mAnimProgress;
            if (f < FILL_TIME) {
                float progress = f / FILL_TIME;
                int i = this.mBoxSize;
                int i2 = this.mStrokeSize;
                float fillWidth = (((float) (i - i2)) / 2.0f) * progress;
                float padding = ((((float) i2) / 2.0f) + (fillWidth / 2.0f)) - 0.5f;
                this.mPaint.setColor(ColorUtil.getMiddleColor(this.mPrevColor, this.mCurColor, progress));
                this.mPaint.setStrokeWidth(fillWidth);
                this.mPaint.setStyle(Paint.Style.STROKE);
                canvas.drawRect(this.mBoxRect.left + padding, this.mBoxRect.top + padding, this.mBoxRect.right - padding, this.mBoxRect.bottom - padding, this.mPaint);
                this.mPaint.setStrokeWidth((float) this.mStrokeSize);
                RectF rectF = this.mBoxRect;
                int i3 = this.mCornerRadius;
                canvas.drawRoundRect(rectF, (float) i3, (float) i3, this.mPaint);
                return;
            }
            float progress2 = (f - FILL_TIME) / 0.6f;
            this.mPaint.setColor(this.mCurColor);
            this.mPaint.setStrokeWidth((float) this.mStrokeSize);
            this.mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
            RectF rectF2 = this.mBoxRect;
            int i4 = this.mCornerRadius;
            canvas.drawRoundRect(rectF2, (float) i4, (float) i4, this.mPaint);
            this.mPaint.setStyle(Paint.Style.STROKE);
            this.mPaint.setStrokeJoin(Paint.Join.MITER);
            this.mPaint.setStrokeCap(Paint.Cap.BUTT);
            this.mPaint.setColor(this.mTickColor);
            canvas.drawPath(getTickPath(this.mTickPath, x, y, size, progress2, true), this.mPaint);
            return;
        }
        this.mPaint.setColor(this.mCurColor);
        this.mPaint.setStrokeWidth((float) this.mStrokeSize);
        this.mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        RectF rectF3 = this.mBoxRect;
        int i5 = this.mCornerRadius;
        canvas.drawRoundRect(rectF3, (float) i5, (float) i5, this.mPaint);
        this.mPaint.setStyle(Paint.Style.STROKE);
        this.mPaint.setStrokeJoin(Paint.Join.MITER);
        this.mPaint.setStrokeCap(Paint.Cap.BUTT);
        this.mPaint.setColor(this.mTickColor);
        canvas.drawPath(getTickPath(this.mTickPath, x, y, size, 1.0f, true), this.mPaint);
    }

    private void drawUnchecked(Canvas canvas) {
        if (isRunning()) {
            float f = this.mAnimProgress;
            if (f < 0.6f) {
                float x = this.mBoxRect.left + ((float) this.mStrokeSize);
                float y = this.mBoxRect.top + ((float) this.mStrokeSize);
                float progress = this.mAnimProgress / 0.6f;
                this.mPaint.setColor(this.mPrevColor);
                this.mPaint.setStrokeWidth((float) this.mStrokeSize);
                this.mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
                RectF rectF = this.mBoxRect;
                int i = this.mCornerRadius;
                canvas.drawRoundRect(rectF, (float) i, (float) i, this.mPaint);
                this.mPaint.setStyle(Paint.Style.STROKE);
                this.mPaint.setStrokeJoin(Paint.Join.MITER);
                this.mPaint.setStrokeCap(Paint.Cap.BUTT);
                this.mPaint.setColor(this.mTickColor);
                canvas.drawPath(getTickPath(this.mTickPath, x, y, (float) (this.mBoxSize - (this.mStrokeSize * 2)), progress, false), this.mPaint);
                return;
            }
            float progress2 = ((f + FILL_TIME) - 1.0f) / FILL_TIME;
            int i2 = this.mBoxSize;
            int i3 = this.mStrokeSize;
            float fillWidth = (((float) (i2 - i3)) / 2.0f) * (1.0f - progress2);
            float padding = ((((float) i3) / 2.0f) + (fillWidth / 2.0f)) - 0.5f;
            this.mPaint.setColor(ColorUtil.getMiddleColor(this.mPrevColor, this.mCurColor, progress2));
            this.mPaint.setStrokeWidth(fillWidth);
            this.mPaint.setStyle(Paint.Style.STROKE);
            canvas.drawRect(this.mBoxRect.left + padding, this.mBoxRect.top + padding, this.mBoxRect.right - padding, this.mBoxRect.bottom - padding, this.mPaint);
            this.mPaint.setStrokeWidth((float) this.mStrokeSize);
            RectF rectF2 = this.mBoxRect;
            int i4 = this.mCornerRadius;
            canvas.drawRoundRect(rectF2, (float) i4, (float) i4, this.mPaint);
            return;
        }
        this.mPaint.setColor(this.mCurColor);
        this.mPaint.setStrokeWidth((float) this.mStrokeSize);
        this.mPaint.setStyle(Paint.Style.STROKE);
        RectF rectF3 = this.mBoxRect;
        int i5 = this.mCornerRadius;
        canvas.drawRoundRect(rectF3, (float) i5, (float) i5, this.mPaint);
    }

    /* access modifiers changed from: protected */
    public boolean onStateChange(int[] state) {
        boolean checked = ViewUtil.hasState(state, 16842912);
        int color = this.mStrokeColor.getColorForState(state, this.mCurColor);
        boolean needRedraw = false;
        if (this.mChecked != checked) {
            this.mChecked = checked;
            needRedraw = true;
            if (!this.mInEditMode && this.mAnimEnable) {
                start();
            }
        }
        if (this.mCurColor != color) {
            this.mPrevColor = isRunning() ? this.mCurColor : color;
            this.mCurColor = color;
            return true;
        } else if (isRunning()) {
            return needRedraw;
        } else {
            this.mPrevColor = color;
            return needRedraw;
        }
    }

    public void setAlpha(int alpha) {
        this.mPaint.setAlpha(alpha);
    }

    public void setColorFilter(ColorFilter cf) {
        this.mPaint.setColorFilter(cf);
    }

    public int getOpacity() {
        return -3;
    }

    private void resetAnimation() {
        this.mStartTime = SystemClock.uptimeMillis();
        this.mAnimProgress = 0.0f;
    }

    public void start() {
        resetAnimation();
        scheduleSelf(this.mUpdater, SystemClock.uptimeMillis() + 16);
        invalidateSelf();
    }

    public void stop() {
        this.mRunning = false;
        unscheduleSelf(this.mUpdater);
        invalidateSelf();
    }

    public boolean isRunning() {
        return this.mRunning;
    }

    public void scheduleSelf(Runnable what, long when) {
        this.mRunning = true;
        super.scheduleSelf(what, when);
    }

    /* access modifiers changed from: private */
    public void update() {
        float min = Math.min(1.0f, ((float) (SystemClock.uptimeMillis() - this.mStartTime)) / ((float) this.mAnimDuration));
        this.mAnimProgress = min;
        if (min == 1.0f) {
            this.mRunning = false;
        }
        if (isRunning()) {
            scheduleSelf(this.mUpdater, SystemClock.uptimeMillis() + 16);
        }
        invalidateSelf();
    }

    public static class Builder {
        private int mAnimDuration;
        private int mBoxSize;
        private int mCornerRadius;
        private int mHeight;
        private ColorStateList mStrokeColor;
        private int mStrokeSize;
        private int mTickColor;
        private int mWidth;

        public Builder() {
            this.mAnimDuration = 400;
            this.mStrokeSize = 4;
            this.mWidth = 64;
            this.mHeight = 64;
            this.mCornerRadius = 8;
            this.mBoxSize = 32;
            this.mTickColor = -1;
        }

        public Builder(Context context, int defStyleRes) {
            this(context, (AttributeSet) null, 0, defStyleRes);
        }

        public Builder(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
            this.mAnimDuration = 400;
            this.mStrokeSize = 4;
            this.mWidth = 64;
            this.mHeight = 64;
            this.mCornerRadius = 8;
            this.mBoxSize = 32;
            this.mTickColor = -1;
            TypedArray a = context.obtainStyledAttributes(attrs, C2500R.styleable.CheckBoxDrawable, defStyleAttr, defStyleRes);
            width(a.getDimensionPixelSize(C2500R.styleable.CheckBoxDrawable_cbd_width, ThemeUtil.dpToPx(context, 32)));
            height(a.getDimensionPixelSize(C2500R.styleable.CheckBoxDrawable_cbd_height, ThemeUtil.dpToPx(context, 32)));
            boxSize(a.getDimensionPixelSize(C2500R.styleable.CheckBoxDrawable_cbd_boxSize, ThemeUtil.dpToPx(context, 18)));
            cornerRadius(a.getDimensionPixelSize(C2500R.styleable.CheckBoxDrawable_cbd_cornerRadius, ThemeUtil.dpToPx(context, 2)));
            strokeSize(a.getDimensionPixelSize(C2500R.styleable.CheckBoxDrawable_cbd_strokeSize, ThemeUtil.dpToPx(context, 2)));
            strokeColor(a.getColorStateList(C2500R.styleable.CheckBoxDrawable_cbd_strokeColor));
            tickColor(a.getColor(C2500R.styleable.CheckBoxDrawable_cbd_tickColor, -1));
            animDuration(a.getInt(C2500R.styleable.CheckBoxDrawable_cbd_animDuration, context.getResources().getInteger(17694721)));
            a.recycle();
            if (this.mStrokeColor == null) {
                strokeColor(new ColorStateList(new int[][]{new int[]{-16842912}, new int[]{16842912}}, new int[]{ThemeUtil.colorControlNormal(context, ViewCompat.MEASURED_STATE_MASK), ThemeUtil.colorControlActivated(context, ViewCompat.MEASURED_STATE_MASK)}));
            }
        }

        public CheckBoxDrawable build() {
            if (this.mStrokeColor == null) {
                this.mStrokeColor = ColorStateList.valueOf(ViewCompat.MEASURED_STATE_MASK);
            }
            return new CheckBoxDrawable(this.mWidth, this.mHeight, this.mBoxSize, this.mCornerRadius, this.mStrokeSize, this.mStrokeColor, this.mTickColor, this.mAnimDuration);
        }

        public Builder width(int width) {
            this.mWidth = width;
            return this;
        }

        public Builder height(int height) {
            this.mHeight = height;
            return this;
        }

        public Builder strokeSize(int size) {
            this.mStrokeSize = size;
            return this;
        }

        public Builder strokeColor(int color) {
            this.mStrokeColor = ColorStateList.valueOf(color);
            return this;
        }

        public Builder strokeColor(ColorStateList color) {
            this.mStrokeColor = color;
            return this;
        }

        public Builder tickColor(int color) {
            this.mTickColor = color;
            return this;
        }

        public Builder cornerRadius(int radius) {
            this.mCornerRadius = radius;
            return this;
        }

        public Builder boxSize(int size) {
            this.mBoxSize = size;
            return this;
        }

        public Builder animDuration(int duration) {
            this.mAnimDuration = duration;
            return this;
        }
    }
}
