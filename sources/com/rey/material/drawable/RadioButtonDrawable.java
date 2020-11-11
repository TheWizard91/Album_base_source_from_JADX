package com.rey.material.drawable;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.os.SystemClock;
import android.util.AttributeSet;
import androidx.core.view.ViewCompat;
import com.rey.material.C2500R;
import com.rey.material.util.ColorUtil;
import com.rey.material.util.ThemeUtil;
import com.rey.material.util.ViewUtil;

public class RadioButtonDrawable extends Drawable implements Animatable {
    private int mAnimDuration;
    private boolean mAnimEnable;
    private float mAnimProgress;
    private boolean mChecked;
    private int mCurColor;
    private int mHeight;
    private boolean mInEditMode;
    private int mInnerRadius;
    private Paint mPaint;
    private int mPrevColor;
    private int mRadius;
    private boolean mRunning;
    private long mStartTime;
    private ColorStateList mStrokeColor;
    private int mStrokeSize;
    private final Runnable mUpdater;
    private int mWidth;

    private RadioButtonDrawable(int width, int height, int strokeSize, ColorStateList strokeColor, int radius, int innerRadius, int animDuration) {
        this.mRunning = false;
        this.mChecked = false;
        this.mInEditMode = false;
        this.mAnimEnable = true;
        this.mUpdater = new Runnable() {
            public void run() {
                RadioButtonDrawable.this.update();
            }
        };
        this.mAnimDuration = animDuration;
        this.mStrokeSize = strokeSize;
        this.mWidth = width;
        this.mHeight = height;
        this.mRadius = radius;
        this.mInnerRadius = innerRadius;
        this.mStrokeColor = strokeColor;
        Paint paint = new Paint();
        this.mPaint = paint;
        paint.setAntiAlias(true);
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

    public void draw(Canvas canvas) {
        if (this.mChecked) {
            drawChecked(canvas);
        } else {
            drawUnchecked(canvas);
        }
    }

    private void drawChecked(Canvas canvas) {
        float cx = getBounds().exactCenterX();
        float cy = getBounds().exactCenterY();
        if (isRunning()) {
            int i = this.mStrokeSize;
            float halfStrokeSize = ((float) i) / 2.0f;
            int i2 = this.mRadius;
            int i3 = this.mInnerRadius;
            float inTime = (((float) i2) - halfStrokeSize) / ((((((float) i2) - halfStrokeSize) + ((float) i2)) - ((float) i)) - ((float) i3));
            float f = this.mAnimProgress;
            if (f < inTime) {
                float inProgress = f / inTime;
                float outerRadius = ((float) i2) + ((1.0f - inProgress) * halfStrokeSize);
                float innerRadius = (((float) i2) - halfStrokeSize) * (1.0f - inProgress);
                this.mPaint.setColor(ColorUtil.getMiddleColor(this.mPrevColor, this.mCurColor, inProgress));
                this.mPaint.setStrokeWidth(outerRadius - innerRadius);
                this.mPaint.setStyle(Paint.Style.STROKE);
                canvas.drawCircle(cx, cy, (outerRadius + innerRadius) / 2.0f, this.mPaint);
                return;
            }
            float outProgress = (f - inTime) / (1.0f - inTime);
            this.mPaint.setColor(this.mCurColor);
            this.mPaint.setStyle(Paint.Style.FILL);
            canvas.drawCircle(cx, cy, (((float) (i2 - i)) * (1.0f - outProgress)) + (((float) i3) * outProgress), this.mPaint);
            float outerRadius2 = ((float) this.mRadius) + (halfStrokeSize * outProgress);
            this.mPaint.setStrokeWidth((float) this.mStrokeSize);
            this.mPaint.setStyle(Paint.Style.STROKE);
            canvas.drawCircle(cx, cy, outerRadius2 - halfStrokeSize, this.mPaint);
            return;
        }
        this.mPaint.setColor(this.mCurColor);
        this.mPaint.setStrokeWidth((float) this.mStrokeSize);
        this.mPaint.setStyle(Paint.Style.STROKE);
        canvas.drawCircle(cx, cy, (float) this.mRadius, this.mPaint);
        this.mPaint.setStyle(Paint.Style.FILL);
        canvas.drawCircle(cx, cy, (float) this.mInnerRadius, this.mPaint);
    }

    private void drawUnchecked(Canvas canvas) {
        float cx = getBounds().exactCenterX();
        float cy = getBounds().exactCenterY();
        if (isRunning()) {
            int i = this.mStrokeSize;
            float halfStrokeSize = ((float) i) / 2.0f;
            int i2 = this.mRadius;
            int i3 = this.mInnerRadius;
            float inTime = ((float) ((i2 - i) - i3)) / ((((((float) i2) - halfStrokeSize) + ((float) i2)) - ((float) i)) - ((float) i3));
            float f = this.mAnimProgress;
            if (f < inTime) {
                float inProgress = f / inTime;
                this.mPaint.setColor(ColorUtil.getMiddleColor(this.mPrevColor, this.mCurColor, inProgress));
                this.mPaint.setStyle(Paint.Style.FILL);
                canvas.drawCircle(cx, cy, (((float) (i2 - i)) * inProgress) + (((float) i3) * (1.0f - inProgress)), this.mPaint);
                this.mPaint.setStrokeWidth((float) this.mStrokeSize);
                this.mPaint.setStyle(Paint.Style.STROKE);
                canvas.drawCircle(cx, cy, (((float) this.mRadius) + ((1.0f - inProgress) * halfStrokeSize)) - halfStrokeSize, this.mPaint);
                return;
            }
            float outProgress = (f - inTime) / (1.0f - inTime);
            float outerRadius = ((float) i2) + (halfStrokeSize * outProgress);
            float innerRadius = (((float) i2) - halfStrokeSize) * outProgress;
            this.mPaint.setColor(this.mCurColor);
            this.mPaint.setStrokeWidth(outerRadius - innerRadius);
            this.mPaint.setStyle(Paint.Style.STROKE);
            canvas.drawCircle(cx, cy, (outerRadius + innerRadius) / 2.0f, this.mPaint);
            return;
        }
        this.mPaint.setColor(this.mCurColor);
        this.mPaint.setStrokeWidth((float) this.mStrokeSize);
        this.mPaint.setStyle(Paint.Style.STROKE);
        canvas.drawCircle(cx, cy, (float) this.mRadius, this.mPaint);
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
        private int mHeight;
        private int mInnerRadius;
        private int mRadius;
        private ColorStateList mStrokeColor;
        private int mStrokeSize;
        private int mWidth;

        public Builder() {
            this.mAnimDuration = 400;
            this.mStrokeSize = 4;
            this.mWidth = 64;
            this.mHeight = 64;
            this.mRadius = 18;
            this.mInnerRadius = 10;
        }

        public Builder(Context context, int defStyleRes) {
            this(context, (AttributeSet) null, 0, defStyleRes);
        }

        public Builder(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
            this.mAnimDuration = 400;
            this.mStrokeSize = 4;
            this.mWidth = 64;
            this.mHeight = 64;
            this.mRadius = 18;
            this.mInnerRadius = 10;
            TypedArray a = context.obtainStyledAttributes(attrs, C2500R.styleable.RadioButtonDrawable, defStyleAttr, defStyleRes);
            width(a.getDimensionPixelSize(C2500R.styleable.RadioButtonDrawable_rbd_width, ThemeUtil.dpToPx(context, 32)));
            height(a.getDimensionPixelSize(C2500R.styleable.RadioButtonDrawable_rbd_height, ThemeUtil.dpToPx(context, 32)));
            strokeSize(a.getDimensionPixelSize(C2500R.styleable.RadioButtonDrawable_rbd_strokeSize, ThemeUtil.dpToPx(context, 2)));
            radius(a.getDimensionPixelSize(C2500R.styleable.RadioButtonDrawable_rbd_radius, ThemeUtil.dpToPx(context, 10)));
            innerRadius(a.getDimensionPixelSize(C2500R.styleable.RadioButtonDrawable_rbd_innerRadius, ThemeUtil.dpToPx(context, 5)));
            strokeColor(a.getColorStateList(C2500R.styleable.RadioButtonDrawable_rbd_strokeColor));
            animDuration(a.getInt(C2500R.styleable.RadioButtonDrawable_rbd_animDuration, context.getResources().getInteger(17694721)));
            a.recycle();
            if (this.mStrokeColor == null) {
                strokeColor(new ColorStateList(new int[][]{new int[]{-16842912}, new int[]{16842912}}, new int[]{ThemeUtil.colorControlNormal(context, ViewCompat.MEASURED_STATE_MASK), ThemeUtil.colorControlActivated(context, ViewCompat.MEASURED_STATE_MASK)}));
            }
        }

        public RadioButtonDrawable build() {
            if (this.mStrokeColor == null) {
                this.mStrokeColor = ColorStateList.valueOf(ViewCompat.MEASURED_STATE_MASK);
            }
            return new RadioButtonDrawable(this.mWidth, this.mHeight, this.mStrokeSize, this.mStrokeColor, this.mRadius, this.mInnerRadius, this.mAnimDuration);
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

        public Builder radius(int radius) {
            this.mRadius = radius;
            return this;
        }

        public Builder innerRadius(int radius) {
            this.mInnerRadius = radius;
            return this;
        }

        public Builder animDuration(int duration) {
            this.mAnimDuration = duration;
            return this;
        }
    }
}
