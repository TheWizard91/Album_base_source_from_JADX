package com.rey.material.drawable;

import android.content.res.ColorStateList;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RadialGradient;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.os.SystemClock;
import com.rey.material.util.ColorUtil;
import com.rey.material.util.ViewUtil;

public class OvalShadowDrawable extends Drawable implements Animatable {
    private static final int COLOR_SHADOW_END = 0;
    private static final int COLOR_SHADOW_START = 1275068416;
    private int mAnimDuration;
    private boolean mAnimEnable = true;
    private float mAnimProgress;
    private ColorStateList mColorStateList;
    private int mCurColor;
    private boolean mEnable = true;
    private Paint mGlowPaint;
    private Path mGlowPath;
    private boolean mInEditMode = false;
    private boolean mNeedBuildShadow = true;
    private Paint mPaint;
    private int mPrevColor;
    private int mRadius;
    private boolean mRunning = false;
    private float mShadowOffset;
    private Paint mShadowPaint;
    private Path mShadowPath;
    private float mShadowSize;
    private long mStartTime;
    private RectF mTempRect = new RectF();
    private final Runnable mUpdater = new Runnable() {
        public void run() {
            OvalShadowDrawable.this.update();
        }
    };

    public OvalShadowDrawable(int radius, ColorStateList colorStateList, float shadowSize, float shadowOffset, int animDuration) {
        this.mAnimDuration = animDuration;
        Paint paint = new Paint(5);
        this.mPaint = paint;
        paint.setStyle(Paint.Style.FILL);
        setColor(colorStateList);
        setRadius(radius);
        setShadow(shadowSize, shadowOffset);
    }

    public boolean setRadius(int radius) {
        if (this.mRadius == radius) {
            return false;
        }
        this.mRadius = radius;
        this.mNeedBuildShadow = true;
        invalidateSelf();
        return true;
    }

    public boolean setShadow(float size, float offset) {
        if (this.mShadowSize == size && this.mShadowOffset == offset) {
            return false;
        }
        this.mShadowSize = size;
        this.mShadowOffset = offset;
        this.mNeedBuildShadow = true;
        invalidateSelf();
        return true;
    }

    public boolean setAnimationDuration(int duration) {
        if (this.mAnimDuration == duration) {
            return false;
        }
        this.mAnimDuration = duration;
        return true;
    }

    public void setColor(ColorStateList colorStateList) {
        this.mColorStateList = colorStateList;
        onStateChange(getState());
    }

    public void setColor(int color) {
        this.mColorStateList = ColorStateList.valueOf(color);
        onStateChange(getState());
    }

    public ColorStateList getColor() {
        return this.mColorStateList;
    }

    public void setInEditMode(boolean b) {
        this.mInEditMode = b;
    }

    public void setAnimEnable(boolean b) {
        this.mAnimEnable = b;
    }

    public int getRadius() {
        return this.mRadius;
    }

    public float getShadowSize() {
        return this.mShadowSize;
    }

    public float getShadowOffset() {
        return this.mShadowOffset;
    }

    public float getPaddingLeft() {
        return this.mShadowSize;
    }

    public float getPaddingTop() {
        return this.mShadowSize;
    }

    public float getPaddingRight() {
        return this.mShadowSize;
    }

    public float getPaddingBottom() {
        return this.mShadowSize + this.mShadowOffset;
    }

    public float getCenterX() {
        return ((float) this.mRadius) + this.mShadowSize;
    }

    public float getCenterY() {
        return ((float) this.mRadius) + this.mShadowSize;
    }

    public boolean isPointerOver(float x, float y) {
        return ((float) Math.sqrt(Math.pow((double) (x - getCenterX()), 2.0d) + Math.pow((double) (y - getCenterY()), 2.0d))) < ((float) this.mRadius);
    }

    public int getIntrinsicWidth() {
        return (int) (((((float) this.mRadius) + this.mShadowSize) * 2.0f) + 0.5f);
    }

    public int getIntrinsicHeight() {
        return (int) (((((float) this.mRadius) + this.mShadowSize) * 2.0f) + this.mShadowOffset + 0.5f);
    }

    private void buildShadow() {
        if (this.mShadowSize > 0.0f) {
            if (this.mShadowPaint == null) {
                Paint paint = new Paint(5);
                this.mShadowPaint = paint;
                paint.setStyle(Paint.Style.FILL);
                this.mShadowPaint.setDither(true);
            }
            int i = this.mRadius;
            float startRatio = ((float) i) / ((((float) i) + this.mShadowSize) + this.mShadowOffset);
            Paint paint2 = this.mShadowPaint;
            float f = this.mShadowSize;
            paint2.setShader(new RadialGradient(0.0f, 0.0f, f + ((float) this.mRadius), new int[]{COLOR_SHADOW_START, COLOR_SHADOW_START, 0}, new float[]{0.0f, startRatio, 1.0f}, Shader.TileMode.CLAMP));
            Path path = this.mShadowPath;
            if (path == null) {
                Path path2 = new Path();
                this.mShadowPath = path2;
                path2.setFillType(Path.FillType.EVEN_ODD);
            } else {
                path.reset();
            }
            float radius = ((float) this.mRadius) + this.mShadowSize;
            this.mTempRect.set(-radius, -radius, radius, radius);
            this.mShadowPath.addOval(this.mTempRect, Path.Direction.CW);
            float radius2 = (float) (this.mRadius - 1);
            float f2 = this.mShadowOffset;
            this.mTempRect.set(-radius2, (-radius2) - f2, radius2, radius2 - f2);
            this.mShadowPath.addOval(this.mTempRect, Path.Direction.CW);
            if (this.mGlowPaint == null) {
                Paint paint3 = new Paint(5);
                this.mGlowPaint = paint3;
                paint3.setStyle(Paint.Style.FILL);
                this.mGlowPaint.setDither(true);
            }
            int i2 = this.mRadius;
            float f3 = this.mShadowSize;
            float startRatio2 = (((float) i2) - (f3 / 2.0f)) / (((float) i2) + (f3 / 2.0f));
            this.mGlowPaint.setShader(new RadialGradient(0.0f, 0.0f, ((float) this.mRadius) + (this.mShadowSize / 2.0f), new int[]{COLOR_SHADOW_START, COLOR_SHADOW_START, 0}, new float[]{0.0f, startRatio2, 1.0f}, Shader.TileMode.CLAMP));
            Path path3 = this.mGlowPath;
            if (path3 == null) {
                Path path4 = new Path();
                this.mGlowPath = path4;
                path4.setFillType(Path.FillType.EVEN_ODD);
            } else {
                path3.reset();
            }
            float radius3 = ((float) this.mRadius) + (this.mShadowSize / 2.0f);
            this.mTempRect.set(-radius3, -radius3, radius3, radius3);
            this.mGlowPath.addOval(this.mTempRect, Path.Direction.CW);
            float radius4 = (float) (this.mRadius - 1);
            this.mTempRect.set(-radius4, -radius4, radius4, radius4);
            this.mGlowPath.addOval(this.mTempRect, Path.Direction.CW);
        }
    }

    public void draw(Canvas canvas) {
        if (this.mNeedBuildShadow) {
            buildShadow();
            this.mNeedBuildShadow = false;
        }
        if (this.mShadowSize > 0.0f) {
            int saveCount = canvas.save();
            float f = this.mShadowSize;
            int i = this.mRadius;
            canvas.translate(((float) i) + f, f + ((float) i) + this.mShadowOffset);
            canvas.drawPath(this.mShadowPath, this.mShadowPaint);
            canvas.restoreToCount(saveCount);
        }
        int saveCount2 = canvas.save();
        float f2 = this.mShadowSize;
        int i2 = this.mRadius;
        canvas.translate(((float) i2) + f2, f2 + ((float) i2));
        if (this.mShadowSize > 0.0f) {
            canvas.drawPath(this.mGlowPath, this.mGlowPaint);
        }
        RectF rectF = this.mTempRect;
        int i3 = this.mRadius;
        rectF.set((float) (-i3), (float) (-i3), (float) i3, (float) i3);
        if (!isRunning()) {
            this.mPaint.setColor(this.mCurColor);
        } else {
            this.mPaint.setColor(ColorUtil.getMiddleColor(this.mPrevColor, this.mCurColor, this.mAnimProgress));
        }
        canvas.drawOval(this.mTempRect, this.mPaint);
        canvas.restoreToCount(saveCount2);
    }

    public void setAlpha(int alpha) {
        this.mShadowPaint.setAlpha(alpha);
        this.mPaint.setAlpha(alpha);
    }

    public void setColorFilter(ColorFilter cf) {
        this.mShadowPaint.setColorFilter(cf);
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
                invalidateSelf();
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

    public void jumpToCurrentState() {
        super.jumpToCurrentState();
        stop();
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
