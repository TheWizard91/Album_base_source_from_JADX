package com.rey.material.drawable;

import android.content.res.ColorStateList;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathEffect;
import android.graphics.Rect;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.os.SystemClock;
import com.rey.material.util.ViewUtil;

public class DividerDrawable extends Drawable implements Animatable {
    private int mAnimDuration;
    private boolean mAnimEnable;
    private float mAnimProgress;
    private ColorStateList mColorStateList;
    private int mCurColor;
    private boolean mEnable;
    private int mHeight;
    private boolean mInEditMode;
    private int mPaddingLeft;
    private int mPaddingRight;
    private Paint mPaint;
    private Path mPath;
    private PathEffect mPathEffect;
    private int mPrevColor;
    private boolean mRunning;
    private long mStartTime;
    private final Runnable mUpdater;

    public DividerDrawable(int height, ColorStateList colorStateList, int animDuration) {
        this(height, 0, 0, colorStateList, animDuration);
    }

    public DividerDrawable(int height, int paddingLeft, int paddingRight, ColorStateList colorStateList, int animDuration) {
        this.mRunning = false;
        this.mEnable = true;
        this.mInEditMode = false;
        this.mAnimEnable = true;
        this.mUpdater = new Runnable() {
            public void run() {
                DividerDrawable.this.update();
            }
        };
        this.mHeight = height;
        this.mPaddingLeft = paddingLeft;
        this.mPaddingRight = paddingRight;
        this.mAnimDuration = animDuration;
        Paint paint = new Paint();
        this.mPaint = paint;
        paint.setAntiAlias(true);
        this.mPaint.setStyle(Paint.Style.STROKE);
        this.mPaint.setStrokeWidth((float) this.mHeight);
        this.mPaint.setStrokeCap(Paint.Cap.ROUND);
        this.mPaint.setStrokeJoin(Paint.Join.ROUND);
        this.mPath = new Path();
        this.mAnimEnable = false;
        setColor(colorStateList);
        this.mAnimEnable = true;
    }

    public void setDividerHeight(int height) {
        if (this.mHeight != height) {
            this.mHeight = height;
            this.mPaint.setStrokeWidth((float) height);
            invalidateSelf();
        }
    }

    public int getDividerHeight() {
        return this.mHeight;
    }

    public void setPadding(int left, int right) {
        if (this.mPaddingLeft != left || this.mPaddingRight != right) {
            this.mPaddingLeft = left;
            this.mPaddingRight = right;
            invalidateSelf();
        }
    }

    public int getPaddingLeft() {
        return this.mPaddingLeft;
    }

    public int getPaddingRight() {
        return this.mPaddingRight;
    }

    public void setInEditMode(boolean b) {
        this.mInEditMode = b;
    }

    public void setAnimEnable(boolean b) {
        this.mAnimEnable = b;
    }

    public void setColor(ColorStateList colorStateList) {
        this.mColorStateList = colorStateList;
        onStateChange(getState());
    }

    public void setAnimationDuration(int duration) {
        this.mAnimDuration = duration;
    }

    private PathEffect getPathEffect() {
        if (this.mPathEffect == null) {
            this.mPathEffect = new DashPathEffect(new float[]{0.2f, (float) (this.mHeight * 2)}, 0.0f);
        }
        return this.mPathEffect;
    }

    public void draw(Canvas canvas) {
        if (this.mHeight != 0) {
            Rect bounds = getBounds();
            float y = (float) (bounds.bottom - (this.mHeight / 2));
            PathEffect pathEffect = null;
            if (!isRunning()) {
                this.mPath.reset();
                this.mPath.moveTo((float) (bounds.left + this.mPaddingLeft), y);
                this.mPath.lineTo((float) (bounds.right - this.mPaddingRight), y);
                Paint paint = this.mPaint;
                if (!this.mEnable) {
                    pathEffect = getPathEffect();
                }
                paint.setPathEffect(pathEffect);
                this.mPaint.setColor(this.mCurColor);
                canvas.drawPath(this.mPath, this.mPaint);
                return;
            }
            float centerX = ((float) (((bounds.right + bounds.left) - this.mPaddingRight) + this.mPaddingLeft)) / 2.0f;
            float f = this.mAnimProgress;
            float start = ((1.0f - this.mAnimProgress) * centerX) + (((float) (bounds.left + this.mPaddingLeft)) * f);
            float end = ((1.0f - f) * centerX) + (((float) (bounds.right + this.mPaddingRight)) * this.mAnimProgress);
            this.mPaint.setPathEffect((PathEffect) null);
            if (this.mAnimProgress < 1.0f) {
                this.mPaint.setColor(this.mPrevColor);
                this.mPath.reset();
                this.mPath.moveTo((float) (bounds.left + this.mPaddingLeft), y);
                this.mPath.lineTo(start, y);
                this.mPath.moveTo((float) (bounds.right - this.mPaddingRight), y);
                this.mPath.lineTo(end, y);
                canvas.drawPath(this.mPath, this.mPaint);
            }
            this.mPaint.setColor(this.mCurColor);
            this.mPath.reset();
            this.mPath.moveTo(start, y);
            this.mPath.lineTo(end, y);
            canvas.drawPath(this.mPath, this.mPaint);
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

    public boolean isStateful() {
        return true;
    }

    /* access modifiers changed from: protected */
    public boolean onStateChange(int[] state) {
        this.mEnable = ViewUtil.hasState(state, 16842910);
        int color = this.mColorStateList.getColorForState(state, this.mCurColor);
        if (this.mCurColor != color) {
            if (this.mInEditMode || !this.mAnimEnable || !this.mEnable || this.mAnimDuration <= 0) {
                this.mPrevColor = color;
                this.mCurColor = color;
                return true;
            }
            this.mPrevColor = isRunning() ? this.mPrevColor : this.mCurColor;
            this.mCurColor = color;
            start();
            return true;
        } else if (isRunning()) {
            return false;
        } else {
            this.mPrevColor = color;
            return false;
        }
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
