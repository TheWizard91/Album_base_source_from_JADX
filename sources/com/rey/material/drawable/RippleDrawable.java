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
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import com.rey.material.C2500R;
import com.rey.material.util.ColorUtil;
import com.rey.material.util.ThemeUtil;

public class RippleDrawable extends Drawable implements Animatable, View.OnTouchListener {
    public static final int DELAY_CLICK_AFTER_RELEASE = 2;
    public static final int DELAY_CLICK_NONE = 0;
    public static final int DELAY_CLICK_UNTIL_RELEASE = 1;
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
    private Drawable mBackgroundDrawable;
    private int mDelayClickType;
    private int mDelayRippleTime;
    private Paint mFillPaint;
    private Interpolator mInInterpolator;
    private RadialGradient mInShader;
    private Mask mMask;
    private Matrix mMatrix;
    private int mMaxRippleRadius;
    private Interpolator mOutInterpolator;
    private RadialGradient mOutShader;
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
    private long mTouchTime;
    private final Runnable mUpdater;

    private RippleDrawable(Drawable backgroundDrawable, int backgroundAnimDuration, int backgroundColor, int rippleType, int delayClickType, int delayRippleTime, int maxRippleRadius, int rippleAnimDuration, int rippleColor, Interpolator inInterpolator, Interpolator outInterpolator, int type, int topLeftCornerRadius, int topRightCornerRadius, int bottomRightCornerRadius, int bottomLeftCornerRadius, int left, int top, int right, int bottom) {
        int i = maxRippleRadius;
        this.mRunning = false;
        this.mAlpha = 255;
        this.mState = 0;
        this.mUpdater = new Runnable() {
            public void run() {
                int access$000 = RippleDrawable.this.mRippleType;
                if (access$000 == -1 || access$000 == 0) {
                    RippleDrawable.this.updateTouch();
                } else if (access$000 == 1) {
                    RippleDrawable.this.updateWave();
                }
            }
        };
        setBackgroundDrawable(backgroundDrawable);
        this.mBackgroundAnimDuration = backgroundAnimDuration;
        this.mBackgroundColor = backgroundColor;
        this.mRippleType = rippleType;
        setDelayClickType(delayClickType);
        this.mDelayRippleTime = delayRippleTime;
        this.mMaxRippleRadius = i;
        this.mRippleAnimDuration = rippleAnimDuration;
        this.mRippleColor = rippleColor;
        if (this.mRippleType == 0 && i <= 0) {
            this.mRippleType = -1;
        }
        this.mInInterpolator = inInterpolator;
        this.mOutInterpolator = outInterpolator;
        setMask(type, topLeftCornerRadius, topRightCornerRadius, bottomRightCornerRadius, bottomLeftCornerRadius, left, top, right, bottom);
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
        int i2 = this.mRippleColor;
        float[] fArr = GRADIENT_STOPS;
        this.mInShader = new RadialGradient(0.0f, 0.0f, GRADIENT_RADIUS, new int[]{i2, i2, 0}, fArr, Shader.TileMode.CLAMP);
        if (this.mRippleType == 1) {
            this.mOutShader = new RadialGradient(0.0f, 0.0f, GRADIENT_RADIUS, new int[]{0, ColorUtil.getColor(this.mRippleColor, 0.0f), this.mRippleColor}, fArr, Shader.TileMode.CLAMP);
        }
    }

    public void setBackgroundDrawable(Drawable backgroundDrawable) {
        this.mBackgroundDrawable = backgroundDrawable;
        if (backgroundDrawable != null) {
            backgroundDrawable.setBounds(getBounds());
        }
    }

    public Drawable getBackgroundDrawable() {
        return this.mBackgroundDrawable;
    }

    public int getDelayClickType() {
        return this.mDelayClickType;
    }

    public void setDelayClickType(int type) {
        this.mDelayClickType = type;
    }

    public void setMask(int type, int topLeftCornerRadius, int topRightCornerRadius, int bottomRightCornerRadius, int bottomLeftCornerRadius, int left, int top, int right, int bottom) {
        this.mMask = new Mask(type, topLeftCornerRadius, topRightCornerRadius, bottomRightCornerRadius, bottomLeftCornerRadius, left, top, right, bottom);
    }

    public void setAlpha(int alpha) {
        this.mAlpha = alpha;
        Drawable drawable = this.mBackgroundDrawable;
        if (drawable != null) {
            drawable.setAlpha(alpha);
        }
    }

    public void setColorFilter(ColorFilter filter) {
        Drawable drawable = this.mBackgroundDrawable;
        if (drawable != null) {
            drawable.setColorFilter(filter);
        }
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
        int i = this.mState;
        if (i == state) {
            return;
        }
        if (i != 0 || state == 1) {
            this.mState = state;
            if (state == 0 || state == 2) {
                stop();
            } else {
                start();
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
        Drawable drawable = this.mBackgroundDrawable;
        if (drawable != null) {
            drawable.setBounds(bounds);
        }
        this.mBackgroundBounds.set((float) (bounds.left + this.mMask.left), (float) (bounds.top + this.mMask.top), (float) (bounds.right - this.mMask.right), (float) (bounds.bottom - this.mMask.bottom));
        this.mBackground.reset();
        int i = this.mMask.type;
        if (i == 0) {
            this.mBackground.addRoundRect(this.mBackgroundBounds, this.mMask.cornerRadius, Path.Direction.CW);
        } else if (i == 1) {
            this.mBackground.addOval(this.mBackgroundBounds, Path.Direction.CW);
        }
    }

    public boolean isStateful() {
        Drawable drawable = this.mBackgroundDrawable;
        return drawable != null && drawable.isStateful();
    }

    /* access modifiers changed from: protected */
    public boolean onStateChange(int[] state) {
        Drawable drawable = this.mBackgroundDrawable;
        return drawable != null && drawable.setState(state);
    }

    public void draw(Canvas canvas) {
        Drawable drawable = this.mBackgroundDrawable;
        if (drawable != null) {
            drawable.draw(canvas);
        }
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

    /* JADX WARNING: Code restructure failed: missing block: B:5:0x0012, code lost:
        if (r0 != 3) goto L_0x00b1;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean onTouch(android.view.View r12, android.view.MotionEvent r13) {
        /*
            r11 = this;
            int r0 = r13.getAction()
            r1 = 0
            r2 = 4
            r3 = -1
            r4 = 0
            r6 = 1
            if (r0 == 0) goto L_0x0058
            r7 = 3
            r8 = 2
            if (r0 == r6) goto L_0x0016
            if (r0 == r8) goto L_0x0058
            if (r0 == r7) goto L_0x0037
            goto L_0x00b1
        L_0x0016:
            long r9 = r11.mTouchTime
            int r0 = (r9 > r4 ? 1 : (r9 == r4 ? 0 : -1))
            if (r0 <= 0) goto L_0x0037
            int r0 = r11.mState
            if (r0 != 0) goto L_0x0037
            int r0 = r11.mRippleType
            if (r0 == r6) goto L_0x0026
            if (r0 != r3) goto L_0x0034
        L_0x0026:
            float r0 = r13.getX()
            float r9 = r13.getY()
            int r0 = r11.getMaxRippleRadius(r0, r9)
            r11.mMaxRippleRadius = r0
        L_0x0034:
            r11.setRippleState(r6)
        L_0x0037:
            r11.mTouchTime = r4
            int r0 = r11.mState
            if (r0 == 0) goto L_0x00b1
            if (r0 != r8) goto L_0x0054
            int r0 = r11.mRippleType
            if (r0 == r6) goto L_0x0045
            if (r0 != r3) goto L_0x0050
        L_0x0045:
            android.graphics.PointF r0 = r11.mRipplePoint
            float r0 = r0.x
            android.graphics.PointF r3 = r11.mRipplePoint
            float r3 = r3.y
            r11.setRippleEffect(r0, r3, r1)
        L_0x0050:
            r11.setRippleState(r2)
            goto L_0x00b1
        L_0x0054:
            r11.setRippleState(r7)
            goto L_0x00b1
        L_0x0058:
            int r0 = r11.mState
            if (r0 == 0) goto L_0x0077
            if (r0 != r2) goto L_0x005f
            goto L_0x0077
        L_0x005f:
            int r0 = r11.mRippleType
            if (r0 != 0) goto L_0x00b1
            float r0 = r13.getX()
            float r1 = r13.getY()
            float r2 = r11.mRippleRadius
            boolean r0 = r11.setRippleEffect(r0, r1, r2)
            if (r0 == 0) goto L_0x00b1
            r11.invalidateSelf()
            goto L_0x00b1
        L_0x0077:
            long r7 = android.os.SystemClock.uptimeMillis()
            long r9 = r11.mTouchTime
            int r0 = (r9 > r4 ? 1 : (r9 == r4 ? 0 : -1))
            if (r0 != 0) goto L_0x0083
            r11.mTouchTime = r7
        L_0x0083:
            float r0 = r13.getX()
            float r2 = r13.getY()
            r11.setRippleEffect(r0, r2, r1)
            long r0 = r11.mTouchTime
            int r2 = r11.mDelayRippleTime
            long r4 = (long) r2
            long r4 = r7 - r4
            int r0 = (r0 > r4 ? 1 : (r0 == r4 ? 0 : -1))
            if (r0 > 0) goto L_0x00b0
            int r0 = r11.mRippleType
            if (r0 == r6) goto L_0x009f
            if (r0 != r3) goto L_0x00ad
        L_0x009f:
            float r0 = r13.getX()
            float r1 = r13.getY()
            int r0 = r11.getMaxRippleRadius(r0, r1)
            r11.mMaxRippleRadius = r0
        L_0x00ad:
            r11.setRippleState(r6)
        L_0x00b0:
        L_0x00b1:
            return r6
        */
        throw new UnsupportedOperationException("Method not decompiled: com.rey.material.drawable.RippleDrawable.onTouch(android.view.View, android.view.MotionEvent):boolean");
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
        this.mRunning = false;
        unscheduleSelf(this.mUpdater);
        invalidateSelf();
    }

    public boolean isRunning() {
        int i = this.mState;
        return (i == 0 || i == 2 || !this.mRunning) ? false : true;
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

    public static class Mask {
        public static final int TYPE_OVAL = 1;
        public static final int TYPE_RECTANGLE = 0;
        final int bottom;
        final float[] cornerRadius;
        final int left;
        final int right;
        final int top;
        final int type;

        public Mask(int type2, int topLeftCornerRadius, int topRightCornerRadius, int bottomRightCornerRadius, int bottomLeftCornerRadius, int left2, int top2, int right2, int bottom2) {
            float[] fArr = new float[8];
            this.cornerRadius = fArr;
            this.type = type2;
            fArr[0] = (float) topLeftCornerRadius;
            fArr[1] = (float) topLeftCornerRadius;
            fArr[2] = (float) topRightCornerRadius;
            fArr[3] = (float) topRightCornerRadius;
            fArr[4] = (float) bottomRightCornerRadius;
            fArr[5] = (float) bottomRightCornerRadius;
            fArr[6] = (float) bottomLeftCornerRadius;
            fArr[7] = (float) bottomLeftCornerRadius;
            this.left = left2;
            this.top = top2;
            this.right = right2;
            this.bottom = bottom2;
        }
    }

    public static class Builder {
        private int mBackgroundAnimDuration;
        private int mBackgroundColor;
        private Drawable mBackgroundDrawable;
        private int mDelayClickType;
        private int mDelayRippleTime;
        private Interpolator mInInterpolator;
        private int mMaskBottom;
        private int mMaskBottomLeftCornerRadius;
        private int mMaskBottomRightCornerRadius;
        private int mMaskLeft;
        private int mMaskRight;
        private int mMaskTop;
        private int mMaskTopLeftCornerRadius;
        private int mMaskTopRightCornerRadius;
        private int mMaskType;
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
            delayRippleTime(a.getInteger(C2500R.styleable.RippleDrawable_rd_delayRipple, 0));
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
            maskType(a.getInteger(C2500R.styleable.RippleDrawable_rd_maskType, 0));
            cornerRadius(a.getDimensionPixelSize(C2500R.styleable.RippleDrawable_rd_cornerRadius, 0));
            topLeftCornerRadius(a.getDimensionPixelSize(C2500R.styleable.RippleDrawable_rd_topLeftCornerRadius, this.mMaskTopLeftCornerRadius));
            topRightCornerRadius(a.getDimensionPixelSize(C2500R.styleable.RippleDrawable_rd_topRightCornerRadius, this.mMaskTopRightCornerRadius));
            bottomRightCornerRadius(a.getDimensionPixelSize(C2500R.styleable.RippleDrawable_rd_bottomRightCornerRadius, this.mMaskBottomRightCornerRadius));
            bottomLeftCornerRadius(a.getDimensionPixelSize(C2500R.styleable.RippleDrawable_rd_bottomLeftCornerRadius, this.mMaskBottomLeftCornerRadius));
            padding(a.getDimensionPixelSize(C2500R.styleable.RippleDrawable_rd_padding, 0));
            left(a.getDimensionPixelSize(C2500R.styleable.RippleDrawable_rd_leftPadding, this.mMaskLeft));
            right(a.getDimensionPixelSize(C2500R.styleable.RippleDrawable_rd_rightPadding, this.mMaskRight));
            top(a.getDimensionPixelSize(C2500R.styleable.RippleDrawable_rd_topPadding, this.mMaskTop));
            bottom(a.getDimensionPixelSize(C2500R.styleable.RippleDrawable_rd_bottomPadding, this.mMaskBottom));
            a.recycle();
        }

        public RippleDrawable build() {
            if (this.mInInterpolator == null) {
                this.mInInterpolator = new AccelerateInterpolator();
            }
            if (this.mOutInterpolator == null) {
                this.mOutInterpolator = new DecelerateInterpolator();
            }
            RippleDrawable rippleDrawable = r2;
            RippleDrawable rippleDrawable2 = new RippleDrawable(this.mBackgroundDrawable, this.mBackgroundAnimDuration, this.mBackgroundColor, this.mRippleType, this.mDelayClickType, this.mDelayRippleTime, this.mMaxRippleRadius, this.mRippleAnimDuration, this.mRippleColor, this.mInInterpolator, this.mOutInterpolator, this.mMaskType, this.mMaskTopLeftCornerRadius, this.mMaskTopRightCornerRadius, this.mMaskBottomRightCornerRadius, this.mMaskBottomLeftCornerRadius, this.mMaskLeft, this.mMaskTop, this.mMaskRight, this.mMaskBottom);
            return rippleDrawable;
        }

        public Builder backgroundDrawable(Drawable drawable) {
            this.mBackgroundDrawable = drawable;
            return this;
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

        public Builder delayRippleTime(int time) {
            this.mDelayRippleTime = time;
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

        public Builder maskType(int type) {
            this.mMaskType = type;
            return this;
        }

        public Builder cornerRadius(int radius) {
            this.mMaskTopLeftCornerRadius = radius;
            this.mMaskTopRightCornerRadius = radius;
            this.mMaskBottomLeftCornerRadius = radius;
            this.mMaskBottomRightCornerRadius = radius;
            return this;
        }

        public Builder topLeftCornerRadius(int radius) {
            this.mMaskTopLeftCornerRadius = radius;
            return this;
        }

        public Builder topRightCornerRadius(int radius) {
            this.mMaskTopRightCornerRadius = radius;
            return this;
        }

        public Builder bottomLeftCornerRadius(int radius) {
            this.mMaskBottomLeftCornerRadius = radius;
            return this;
        }

        public Builder bottomRightCornerRadius(int radius) {
            this.mMaskBottomRightCornerRadius = radius;
            return this;
        }

        public Builder padding(int padding) {
            this.mMaskLeft = padding;
            this.mMaskTop = padding;
            this.mMaskRight = padding;
            this.mMaskBottom = padding;
            return this;
        }

        public Builder left(int padding) {
            this.mMaskLeft = padding;
            return this;
        }

        public Builder top(int padding) {
            this.mMaskTop = padding;
            return this;
        }

        public Builder right(int padding) {
            this.mMaskRight = padding;
            return this;
        }

        public Builder bottom(int padding) {
            this.mMaskBottom = padding;
            return this;
        }
    }
}
