package com.rey.material.drawable;

import android.content.res.ColorStateList;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.os.SystemClock;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;

public class ArrowDrawable extends Drawable implements Animatable {
    public static int MODE_DOWN = 0;
    public static int MODE_UP = 1;
    private int mAnimDuration;
    private float mAnimProgress;
    private boolean mClockwise = true;
    private ColorStateList mColorStateList;
    private int mCurColor;
    private Interpolator mInterpolator;
    private int mMode;
    private Paint mPaint;
    private Path mPath;
    private boolean mRunning = false;
    private int mSize;
    private long mStartTime;
    private final Runnable mUpdater = new Runnable() {
        public void run() {
            ArrowDrawable.this.update();
        }
    };

    public ArrowDrawable(int mode, int size, ColorStateList colorStateList, int animDuration, Interpolator interpolator, boolean clockwise) {
        this.mSize = size;
        this.mAnimDuration = animDuration;
        this.mMode = mode;
        this.mInterpolator = interpolator;
        if (interpolator == null) {
            this.mInterpolator = new DecelerateInterpolator();
        }
        this.mClockwise = clockwise;
        Paint paint = new Paint();
        this.mPaint = paint;
        paint.setAntiAlias(true);
        this.mPaint.setStyle(Paint.Style.FILL);
        this.mPath = new Path();
        setColor(colorStateList);
    }

    public void setColor(ColorStateList colorStateList) {
        this.mColorStateList = colorStateList;
        onStateChange(getState());
    }

    public void setAnimationDuration(int duration) {
        this.mAnimDuration = duration;
    }

    public void setInterpolator(Interpolator interpolator) {
        this.mInterpolator = interpolator;
    }

    public void setClockwise(boolean clockwise) {
        this.mClockwise = clockwise;
    }

    public void setArrowSize(int size) {
        if (this.mSize != size) {
            this.mSize = size;
            invalidateSelf();
        }
    }

    public void setMode(int mode, boolean animation) {
        if (this.mMode != mode) {
            this.mMode = mode;
            if (!animation || this.mAnimDuration <= 0) {
                invalidateSelf();
            } else {
                start();
            }
        }
    }

    public int getMode() {
        return this.mMode;
    }

    /* access modifiers changed from: protected */
    public void onBoundsChange(Rect bounds) {
        float x = bounds.exactCenterX();
        float y = bounds.exactCenterY();
        this.mPath.reset();
        this.mPath.moveTo(x, (((float) this.mSize) / 2.0f) + y);
        Path path = this.mPath;
        int i = this.mSize;
        path.lineTo(x - ((float) i), y - (((float) i) / 2.0f));
        Path path2 = this.mPath;
        int i2 = this.mSize;
        path2.lineTo(((float) i2) + x, y - (((float) i2) / 2.0f));
        this.mPath.close();
    }

    public void draw(Canvas canvas) {
        float degree;
        int saveCount = canvas.save();
        Rect bounds = getBounds();
        if (isRunning()) {
            float value = this.mInterpolator.getInterpolation(this.mAnimProgress);
            if (this.mClockwise) {
                if (this.mMode == MODE_UP) {
                    degree = 180.0f * value;
                } else {
                    degree = 180.0f * (1.0f + value);
                }
            } else if (this.mMode == MODE_UP) {
                degree = value * -180.0f;
            } else {
                degree = (1.0f + value) * -180.0f;
            }
            canvas.rotate(degree, bounds.exactCenterX(), bounds.exactCenterY());
        } else if (this.mMode == MODE_UP) {
            canvas.rotate(180.0f, bounds.exactCenterX(), bounds.exactCenterY());
        }
        this.mPaint.setColor(this.mCurColor);
        canvas.drawPath(this.mPath, this.mPaint);
        canvas.restoreToCount(saveCount);
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

    public boolean isStateful() {
        return true;
    }

    /* access modifiers changed from: protected */
    public boolean onStateChange(int[] state) {
        int color = this.mColorStateList.getColorForState(state, this.mCurColor);
        if (this.mCurColor == color) {
            return false;
        }
        this.mCurColor = color;
        return true;
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
}
