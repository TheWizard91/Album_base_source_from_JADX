package com.rey.material.drawable;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.os.SystemClock;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import com.rey.material.util.ViewUtil;

public class CircleDrawable extends Drawable implements Animatable {
    private int mAnimDuration = 1000;
    private boolean mAnimEnable = true;
    private float mAnimProgress;
    private boolean mInEditMode = false;
    private Interpolator mInInterpolator = new DecelerateInterpolator();
    private Interpolator mOutInterpolator = new DecelerateInterpolator();
    private Paint mPaint;
    private float mRadius;
    private boolean mRunning = false;
    private long mStartTime;
    private final Runnable mUpdater = new Runnable() {
        public void run() {
            CircleDrawable.this.update();
        }
    };
    private boolean mVisible;

    /* renamed from: mX */
    private float f178mX;

    /* renamed from: mY */
    private float f179mY;

    public CircleDrawable() {
        Paint paint = new Paint(1);
        this.mPaint = paint;
        paint.setStyle(Paint.Style.FILL);
    }

    public void setInEditMode(boolean b) {
        this.mInEditMode = b;
    }

    public void setAnimEnable(boolean b) {
        this.mAnimEnable = b;
    }

    public void setColor(int color) {
        this.mPaint.setColor(color);
        invalidateSelf();
    }

    public void setAnimDuration(int duration) {
        this.mAnimDuration = duration;
    }

    public void setInterpolator(Interpolator in, Interpolator out) {
        this.mInInterpolator = in;
        this.mOutInterpolator = out;
    }

    public boolean isStateful() {
        return true;
    }

    /* access modifiers changed from: protected */
    public boolean onStateChange(int[] state) {
        boolean visible = ViewUtil.hasState(state, 16842912) || ViewUtil.hasState(state, 16842919);
        if (this.mVisible == visible) {
            return false;
        }
        this.mVisible = visible;
        if (!this.mInEditMode && this.mAnimEnable) {
            start();
        }
        return true;
    }

    /* access modifiers changed from: protected */
    public void onBoundsChange(Rect bounds) {
        this.f178mX = bounds.exactCenterX();
        this.f179mY = bounds.exactCenterY();
        this.mRadius = ((float) Math.min(bounds.width(), bounds.height())) / 2.0f;
    }

    public void draw(Canvas canvas) {
        if (this.mRunning) {
            canvas.drawCircle(this.f178mX, this.f179mY, (this.mVisible ? this.mInInterpolator.getInterpolation(this.mAnimProgress) : 1.0f - this.mOutInterpolator.getInterpolation(this.mAnimProgress)) * this.mRadius, this.mPaint);
        } else if (this.mVisible) {
            canvas.drawCircle(this.f178mX, this.f179mY, this.mRadius, this.mPaint);
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
}
