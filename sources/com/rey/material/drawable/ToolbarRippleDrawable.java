package com.rey.material.drawable;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.RadialGradient;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import com.rey.material.C2500R;
import com.rey.material.util.ColorUtil;
import com.rey.material.util.ThemeUtil;
import com.rey.material.util.ViewUtil;

public class ToolbarRippleDrawable extends Drawable implements Animatable {
    private static final float GRADIENT_RADIUS = 16.0f;
    private static final float[] GRADIENT_STOPS = {0.0f, 0.99f, 1.0f};
    private static final int STATE_HOVER = 2;
    private static final int STATE_OUT = 0;
    private static final int STATE_PRESS = 1;
    private static final int STATE_RELEASE = 4;
    private static final int STATE_RELEASE_ON_HOLD = 3;
    private static final int TYPE_TOUCH = 0;
    private static final int TYPE_TOUCH_MATCH_VIEW = -1;
    private static final int TYPE_WAVE = 1;
    private int mAlpha;
    private Path mBackground;
    private float mBackgroundAlphaPercent;
    private int mBackgroundAnimDuration;
    private RectF mBackgroundBounds;
    private int mBackgroundColor;
    private int mDelayClickType;
    private Paint mFillPaint;
    private Interpolator mInInterpolator;
    private RadialGradient mInShader;
    private Matrix mMatrix;
    private int mMaxRippleRadius;
    private Interpolator mOutInterpolator;
    private RadialGradient mOutShader;
    private boolean mPressed;
    private float mRippleAlphaPercent;
    private int mRippleAnimDuration;
    private int mRippleColor;
    private PointF mRipplePoint;
    private float mRippleRadius;
    /* access modifiers changed from: private */
    public int mRippleType;
    private boolean mRunning;
    private Paint mShaderPaint;
    private long mStartTime;
    private int mState;
    private final Runnable mUpdater;

    private ToolbarRippleDrawable(int backgroundAnimDuration, int backgroundColor, int rippleType, int delayClickType, int maxTouchRadius, int touchAnimDuration, int touchColor, Interpolator inInterpolator, Interpolator outInterpolator) {
        int i = rippleType;
        int i2 = maxTouchRadius;
        this.mRunning = false;
        this.mAlpha = 255;
        this.mPressed = false;
        this.mState = 0;
        this.mUpdater = new Runnable() {
            public void run() {
                int access$000 = ToolbarRippleDrawable.this.mRippleType;
                if (access$000 == -1 || access$000 == 0) {
                    ToolbarRippleDrawable.this.updateTouch();
                } else if (access$000 == 1) {
                    ToolbarRippleDrawable.this.updateWave();
                }
            }
        };
        this.mBackgroundAnimDuration = backgroundAnimDuration;
        this.mBackgroundColor = backgroundColor;
        this.mRippleType = i;
        this.mMaxRippleRadius = i2;
        this.mRippleAnimDuration = touchAnimDuration;
        this.mRippleColor = touchColor;
        this.mDelayClickType = delayClickType;
        if (i == 0 && i2 <= 0) {
            this.mRippleType = -1;
        }
        this.mInInterpolator = inInterpolator;
        this.mOutInterpolator = outInterpolator;
        Paint paint = new Paint(1);
        this.mFillPaint = paint;
        paint.setStyle(Paint.Style.FILL);
        Paint paint2 = new Paint(1);
        this.mShaderPaint = paint2;
        paint2.setStyle(Paint.Style.FILL);
        this.mBackground = new Path();
        this.mBackgroundBounds = new RectF();
        this.mRipplePoint = new PointF();
        this.mMatrix = new Matrix();
        int i3 = this.mRippleColor;
        float[] fArr = GRADIENT_STOPS;
        this.mInShader = new RadialGradient(0.0f, 0.0f, GRADIENT_RADIUS, new int[]{i3, i3, 0}, fArr, Shader.TileMode.CLAMP);
        if (this.mRippleType == 1) {
            this.mOutShader = new RadialGradient(0.0f, 0.0f, GRADIENT_RADIUS, new int[]{0, ColorUtil.getColor(this.mRippleColor, 0.0f), this.mRippleColor}, fArr, Shader.TileMode.CLAMP);
        }
    }

    public int getDelayClickType() {
        return this.mDelayClickType;
    }

    public void setDelayClickType(int type) {
        this.mDelayClickType = type;
    }

    public void setAlpha(int alpha) {
        this.mAlpha = alpha;
    }

    public void setColorFilter(ColorFilter filter) {
        this.mFillPaint.setColorFilter(filter);
        this.mShaderPaint.setColorFilter(filter);
    }

    public int getOpacity() {
        return -3;
    }

    public long getClickDelayTime() {
        int i = this.mDelayClickType;
        if (i != 1) {
            if (i != 2) {
                return -1;
            }
            int i2 = this.mState;
            if (i2 == 3) {
                return ((long) (Math.max(this.mBackgroundAnimDuration, this.mRippleAnimDuration) * 2)) - (SystemClock.uptimeMillis() - this.mStartTime);
            }
            if (i2 == 4) {
                return ((long) Math.max(this.mBackgroundAnimDuration, this.mRippleAnimDuration)) - (SystemClock.uptimeMillis() - this.mStartTime);
            }
            return -1;
        } else if (this.mState == 3) {
            return ((long) Math.max(this.mBackgroundAnimDuration, this.mRippleAnimDuration)) - (SystemClock.uptimeMillis() - this.mStartTime);
        } else {
            return -1;
        }
    }

    private void setRippleState(int state) {
        if (this.mState != state) {
            this.mState = state;
            if (state == 0) {
                stop();
            } else if (state != 2) {
                start();
            } else {
                stop();
            }
        }
    }

    private boolean setRippleEffect(float x, float y, float radius) {
        if (this.mRipplePoint.x == x && this.mRipplePoint.y == y && this.mRippleRadius == radius) {
            return false;
        }
        this.mRipplePoint.set(x, y);
        this.mRippleRadius = radius;
        float radius2 = radius / GRADIENT_RADIUS;
        this.mMatrix.reset();
        this.mMatrix.postTranslate(x, y);
        this.mMatrix.postScale(radius2, radius2, x, y);
        this.mInShader.setLocalMatrix(this.mMatrix);
        RadialGradient radialGradient = this.mOutShader;
        if (radialGradient == null) {
            return true;
        }
        radialGradient.setLocalMatrix(this.mMatrix);
        return true;
    }

    /* access modifiers changed from: protected */
    public void onBoundsChange(Rect bounds) {
        this.mBackgroundBounds.set((float) bounds.left, (float) bounds.top, (float) bounds.right, (float) bounds.bottom);
        this.mBackground.reset();
        this.mBackground.addRect(this.mBackgroundBounds, Path.Direction.CW);
    }

    public boolean isStateful() {
        return true;
    }

    /* access modifiers changed from: protected */
    public boolean onStateChange(int[] state) {
        boolean pressed = ViewUtil.hasState(state, 16842919);
        if (this.mPressed == pressed) {
            return false;
        }
        this.mPressed = pressed;
        if (pressed) {
            Rect bounds = getBounds();
            int i = this.mState;
            if (i == 0 || i == 4) {
                int i2 = this.mRippleType;
                if (i2 == 1 || i2 == -1) {
                    this.mMaxRippleRadius = getMaxRippleRadius(bounds.exactCenterX(), bounds.exactCenterY());
                }
                setRippleEffect(bounds.exactCenterX(), bounds.exactCenterY(), 0.0f);
                setRippleState(1);
            } else if (this.mRippleType == 0) {
                setRippleEffect(bounds.exactCenterX(), bounds.exactCenterY(), this.mRippleRadius);
            }
        } else {
            int i3 = this.mState;
            if (i3 != 0) {
                if (i3 == 2) {
                    int i4 = this.mRippleType;
                    if (i4 == 1 || i4 == -1) {
                        setRippleEffect(this.mRipplePoint.x, this.mRipplePoint.y, 0.0f);
                    }
                    setRippleState(4);
                } else {
                    setRippleState(3);
                }
            }
        }
        return true;
    }

    public void draw(Canvas canvas) {
        int i = this.mRippleType;
        if (i == -1 || i == 0) {
            drawTouch(canvas);
        } else if (i == 1) {
            drawWave(canvas);
        }
    }

    private void drawTouch(Canvas canvas) {
        if (this.mState != 0) {
            if (this.mBackgroundAlphaPercent > 0.0f) {
                this.mFillPaint.setColor(this.mBackgroundColor);
                this.mFillPaint.setAlpha(Math.round(((float) this.mAlpha) * this.mBackgroundAlphaPercent));
                canvas.drawPath(this.mBackground, this.mFillPaint);
            }
            if (this.mRippleRadius > 0.0f) {
                float f = this.mRippleAlphaPercent;
                if (f > 0.0f) {
                    this.mShaderPaint.setAlpha(Math.round(((float) this.mAlpha) * f));
                    this.mShaderPaint.setShader(this.mInShader);
                    canvas.drawPath(this.mBackground, this.mShaderPaint);
                }
            }
        }
    }

    private void drawWave(Canvas canvas) {
        int i = this.mState;
        if (i == 0) {
            return;
        }
        if (i == 4) {
            if (this.mRippleRadius == 0.0f) {
                this.mFillPaint.setColor(this.mRippleColor);
                canvas.drawPath(this.mBackground, this.mFillPaint);
                return;
            }
            this.mShaderPaint.setShader(this.mOutShader);
            canvas.drawPath(this.mBackground, this.mShaderPaint);
        } else if (this.mRippleRadius > 0.0f) {
            this.mShaderPaint.setShader(this.mInShader);
            canvas.drawPath(this.mBackground, this.mShaderPaint);
        }
    }

    private int getMaxRippleRadius(float x, float y) {
        return (int) Math.round(Math.sqrt(Math.pow((double) ((x < this.mBackgroundBounds.centerX() ? this.mBackgroundBounds.right : this.mBackgroundBounds.left) - x), 2.0d) + Math.pow((double) ((y < this.mBackgroundBounds.centerY() ? this.mBackgroundBounds.bottom : this.mBackgroundBounds.top) - y), 2.0d)));
    }

    public void cancel() {
        setRippleState(0);
    }

    private void resetAnimation() {
        this.mStartTime = SystemClock.uptimeMillis();
    }

    public void start() {
        if (!isRunning()) {
            resetAnimation();
            scheduleSelf(this.mUpdater, SystemClock.uptimeMillis() + 16);
            invalidateSelf();
        }
    }

    public void stop() {
        if (isRunning()) {
            this.mRunning = false;
            unscheduleSelf(this.mUpdater);
            invalidateSelf();
        }
    }

    public boolean isRunning() {
        return this.mRunning;
    }

    public void scheduleSelf(Runnable what, long when) {
        this.mRunning = true;
        super.scheduleSelf(what, when);
    }

    /* access modifiers changed from: private */
    public void updateTouch() {
        int i = 4;
        if (this.mState != 4) {
            float backgroundProgress = Math.min(1.0f, ((float) (SystemClock.uptimeMillis() - this.mStartTime)) / ((float) this.mBackgroundAnimDuration));
            this.mBackgroundAlphaPercent = (this.mInInterpolator.getInterpolation(backgroundProgress) * ((float) Color.alpha(this.mBackgroundColor))) / 255.0f;
            float touchProgress = Math.min(1.0f, ((float) (SystemClock.uptimeMillis() - this.mStartTime)) / ((float) this.mRippleAnimDuration));
            this.mRippleAlphaPercent = this.mInInterpolator.getInterpolation(touchProgress);
            setRippleEffect(this.mRipplePoint.x, this.mRipplePoint.y, ((float) this.mMaxRippleRadius) * this.mInInterpolator.getInterpolation(touchProgress));
            if (backgroundProgress == 1.0f && touchProgress == 1.0f) {
                this.mStartTime = SystemClock.uptimeMillis();
                if (this.mState == 1) {
                    i = 2;
                }
                setRippleState(i);
            }
        } else {
            float backgroundProgress2 = Math.min(1.0f, ((float) (SystemClock.uptimeMillis() - this.mStartTime)) / ((float) this.mBackgroundAnimDuration));
            this.mBackgroundAlphaPercent = ((1.0f - this.mOutInterpolator.getInterpolation(backgroundProgress2)) * ((float) Color.alpha(this.mBackgroundColor))) / 255.0f;
            float touchProgress2 = Math.min(1.0f, ((float) (SystemClock.uptimeMillis() - this.mStartTime)) / ((float) this.mRippleAnimDuration));
            this.mRippleAlphaPercent = 1.0f - this.mOutInterpolator.getInterpolation(touchProgress2);
            setRippleEffect(this.mRipplePoint.x, this.mRipplePoint.y, ((float) this.mMaxRippleRadius) * ((this.mOutInterpolator.getInterpolation(touchProgress2) * 0.5f) + 1.0f));
            if (backgroundProgress2 == 1.0f && touchProgress2 == 1.0f) {
                setRippleState(0);
            }
        }
        if (isRunning()) {
            scheduleSelf(this.mUpdater, SystemClock.uptimeMillis() + 16);
        }
        invalidateSelf();
    }

    /* access modifiers changed from: private */
    public void updateWave() {
        float progress = Math.min(1.0f, ((float) (SystemClock.uptimeMillis() - this.mStartTime)) / ((float) this.mRippleAnimDuration));
        if (this.mState != 4) {
            setRippleEffect(this.mRipplePoint.x, this.mRipplePoint.y, ((float) this.mMaxRippleRadius) * this.mInInterpolator.getInterpolation(progress));
            if (progress == 1.0f) {
                this.mStartTime = SystemClock.uptimeMillis();
                if (this.mState == 1) {
                    setRippleState(2);
                } else {
                    setRippleEffect(this.mRipplePoint.x, this.mRipplePoint.y, 0.0f);
                    setRippleState(4);
                }
            }
        } else {
            setRippleEffect(this.mRipplePoint.x, this.mRipplePoint.y, ((float) this.mMaxRippleRadius) * this.mOutInterpolator.getInterpolation(progress));
            if (progress == 1.0f) {
                setRippleState(0);
            }
        }
        if (isRunning()) {
            scheduleSelf(this.mUpdater, SystemClock.uptimeMillis() + 16);
        }
        invalidateSelf();
    }

    public static class Builder {
        private int mBackgroundAnimDuration;
        private int mBackgroundColor;
        private int mDelayClickType;
        private Interpolator mInInterpolator;
        private int mMaxRippleRadius;
        private Interpolator mOutInterpolator;
        private int mRippleAnimDuration;
        private int mRippleColor;
        private int mRippleType;

        public Builder() {
            this.mBackgroundAnimDuration = 200;
            this.mRippleAnimDuration = 400;
        }

        public Builder(Context context, int defStyleRes) {
            this(context, (AttributeSet) null, 0, defStyleRes);
        }

        public Builder(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
            this.mBackgroundAnimDuration = 200;
            this.mRippleAnimDuration = 400;
            TypedArray a = context.obtainStyledAttributes(attrs, C2500R.styleable.RippleDrawable, defStyleAttr, defStyleRes);
            backgroundColor(a.getColor(C2500R.styleable.RippleDrawable_rd_backgroundColor, 0));
            backgroundAnimDuration(a.getInteger(C2500R.styleable.RippleDrawable_rd_backgroundAnimDuration, context.getResources().getInteger(17694721)));
            rippleType(a.getInteger(C2500R.styleable.RippleDrawable_rd_rippleType, 0));
            delayClickType(a.getInteger(C2500R.styleable.RippleDrawable_rd_delayClick, 0));
            int type = ThemeUtil.getType(a, C2500R.styleable.RippleDrawable_rd_maxRippleRadius);
            if (type < 16 || type > 31) {
                maxRippleRadius(a.getDimensionPixelSize(C2500R.styleable.RippleDrawable_rd_maxRippleRadius, ThemeUtil.dpToPx(context, 48)));
            } else {
                maxRippleRadius(a.getInteger(C2500R.styleable.RippleDrawable_rd_maxRippleRadius, -1));
            }
            rippleColor(a.getColor(C2500R.styleable.RippleDrawable_rd_rippleColor, ThemeUtil.colorControlHighlight(context, 0)));
            rippleAnimDuration(a.getInteger(C2500R.styleable.RippleDrawable_rd_rippleAnimDuration, context.getResources().getInteger(17694721)));
            int resourceId = a.getResourceId(C2500R.styleable.RippleDrawable_rd_inInterpolator, 0);
            int resId = resourceId;
            if (resourceId != 0) {
                inInterpolator(AnimationUtils.loadInterpolator(context, resId));
            }
            int resourceId2 = a.getResourceId(C2500R.styleable.RippleDrawable_rd_outInterpolator, 0);
            int resId2 = resourceId2;
            if (resourceId2 != 0) {
                outInterpolator(AnimationUtils.loadInterpolator(context, resId2));
            }
            a.recycle();
        }

        public ToolbarRippleDrawable build() {
            if (this.mInInterpolator == null) {
                this.mInInterpolator = new AccelerateInterpolator();
            }
            if (this.mOutInterpolator == null) {
                this.mOutInterpolator = new DecelerateInterpolator();
            }
            return new ToolbarRippleDrawable(this.mBackgroundAnimDuration, this.mBackgroundColor, this.mRippleType, this.mDelayClickType, this.mMaxRippleRadius, this.mRippleAnimDuration, this.mRippleColor, this.mInInterpolator, this.mOutInterpolator);
        }

        public Builder backgroundAnimDuration(int duration) {
            this.mBackgroundAnimDuration = duration;
            return this;
        }

        public Builder backgroundColor(int color) {
            this.mBackgroundColor = color;
            return this;
        }

        public Builder rippleType(int type) {
            this.mRippleType = type;
            return this;
        }

        public Builder delayClickType(int type) {
            this.mDelayClickType = type;
            return this;
        }

        public Builder maxRippleRadius(int radius) {
            this.mMaxRippleRadius = radius;
            return this;
        }

        public Builder rippleAnimDuration(int duration) {
            this.mRippleAnimDuration = duration;
            return this;
        }

        public Builder rippleColor(int color) {
            this.mRippleColor = color;
            return this;
        }

        public Builder inInterpolator(Interpolator interpolator) {
            this.mInInterpolator = interpolator;
            return this;
        }

        public Builder outInterpolator(Interpolator interpolator) {
            this.mOutInterpolator = interpolator;
            return this;
        }
    }
}
