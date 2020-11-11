package com.rey.material.drawable;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import androidx.core.view.ViewCompat;
import com.rey.material.C2500R;
import com.rey.material.util.ColorUtil;
import com.rey.material.util.ThemeUtil;

public class CircularProgressDrawable extends Drawable implements Animatable {
    private static final int PROGRESS_STATE_HIDE = -1;
    private static final int PROGRESS_STATE_KEEP_SHRINK = 3;
    private static final int PROGRESS_STATE_KEEP_STRETCH = 1;
    private static final int PROGRESS_STATE_SHRINK = 2;
    private static final int PROGRESS_STATE_STRETCH = 0;
    private static final int RUN_STATE_RUNNING = 3;
    private static final int RUN_STATE_STARTED = 2;
    private static final int RUN_STATE_STARTING = 1;
    private static final int RUN_STATE_STOPPED = 0;
    private static final int RUN_STATE_STOPPING = 4;
    private int mInAnimationDuration;
    private int[] mInColors;
    private float mInStepPercent;
    private float mInitialAngle;
    private int mKeepDuration;
    private long mLastProgressStateTime;
    private long mLastRunStateTime;
    private long mLastUpdateTime;
    private float mMaxSweepAngle;
    private float mMinSweepAngle;
    private int mOutAnimationDuration;
    private int mPadding;
    private Paint mPaint;
    private int mProgressMode;
    private float mProgressPercent;
    private int mProgressState;
    private RectF mRect;
    private boolean mReverse;
    private int mRotateDuration;
    private int mRunState;
    private float mSecondaryProgressPercent;
    private float mStartAngle;
    private int mStrokeColorIndex;
    private int[] mStrokeColors;
    private int mStrokeSecondaryColor;
    private int mStrokeSize;
    private float mSweepAngle;
    private int mTransformDuration;
    private Interpolator mTransformInterpolator;
    private final Runnable mUpdater;

    private CircularProgressDrawable(int padding, float initialAngle, float progressPercent, float secondaryProgressPercent, float maxSweepAngle, float minSweepAngle, int strokeSize, int[] strokeColors, int strokeSecondaryColor, boolean reverse, int rotateDuration, int transformDuration, int keepDuration, Interpolator transformInterpolator, int progressMode, int inAnimDuration, float inStepPercent, int[] inStepColors, int outAnimDuration) {
        this.mRunState = 0;
        this.mUpdater = new Runnable() {
            public void run() {
                CircularProgressDrawable.this.update();
            }
        };
        this.mPadding = padding;
        this.mInitialAngle = initialAngle;
        setProgress(progressPercent);
        setSecondaryProgress(secondaryProgressPercent);
        this.mMaxSweepAngle = maxSweepAngle;
        this.mMinSweepAngle = minSweepAngle;
        this.mStrokeSize = strokeSize;
        this.mStrokeColors = strokeColors;
        this.mStrokeSecondaryColor = strokeSecondaryColor;
        this.mReverse = reverse;
        this.mRotateDuration = rotateDuration;
        this.mTransformDuration = transformDuration;
        this.mKeepDuration = keepDuration;
        this.mTransformInterpolator = transformInterpolator;
        this.mProgressMode = progressMode;
        this.mInAnimationDuration = inAnimDuration;
        this.mInStepPercent = inStepPercent;
        this.mInColors = inStepColors;
        this.mOutAnimationDuration = outAnimDuration;
        Paint paint = new Paint();
        this.mPaint = paint;
        paint.setAntiAlias(true);
        this.mPaint.setStrokeCap(Paint.Cap.ROUND);
        this.mPaint.setStrokeJoin(Paint.Join.ROUND);
        this.mRect = new RectF();
    }

    public void applyStyle(Context context, int resId) {
        TypedArray a = context.obtainStyledAttributes(resId, C2500R.styleable.CircularProgressDrawable);
        int strokeColor = 0;
        boolean strokeColorDefined = false;
        int[] strokeColors = null;
        int count = a.getIndexCount();
        for (int i = 0; i < count; i++) {
            int attr = a.getIndex(i);
            if (attr == C2500R.styleable.CircularProgressDrawable_cpd_padding) {
                this.mPadding = a.getDimensionPixelSize(attr, 0);
            } else if (attr == C2500R.styleable.CircularProgressDrawable_cpd_initialAngle) {
                this.mInitialAngle = (float) a.getInteger(attr, 0);
            } else if (attr == C2500R.styleable.CircularProgressDrawable_pv_progress) {
                setProgress(a.getFloat(attr, 0.0f));
            } else if (attr == C2500R.styleable.CircularProgressDrawable_pv_secondaryProgress) {
                setSecondaryProgress(a.getFloat(attr, 0.0f));
            } else if (attr == C2500R.styleable.CircularProgressDrawable_cpd_maxSweepAngle) {
                this.mMaxSweepAngle = (float) a.getInteger(attr, 0);
            } else if (attr == C2500R.styleable.CircularProgressDrawable_cpd_minSweepAngle) {
                this.mMinSweepAngle = (float) a.getInteger(attr, 0);
            } else if (attr == C2500R.styleable.CircularProgressDrawable_cpd_strokeSize) {
                this.mStrokeSize = a.getDimensionPixelSize(attr, 0);
            } else if (attr == C2500R.styleable.CircularProgressDrawable_cpd_strokeColor) {
                strokeColor = a.getColor(attr, 0);
                strokeColorDefined = true;
            } else if (attr == C2500R.styleable.CircularProgressDrawable_cpd_strokeColors) {
                TypedArray ta = context.getResources().obtainTypedArray(a.getResourceId(attr, 0));
                strokeColors = new int[ta.length()];
                for (int j = 0; j < ta.length(); j++) {
                    strokeColors[j] = ta.getColor(j, 0);
                }
                ta.recycle();
            } else if (attr == C2500R.styleable.CircularProgressDrawable_cpd_strokeSecondaryColor) {
                this.mStrokeSecondaryColor = a.getColor(attr, 0);
            } else if (attr == C2500R.styleable.CircularProgressDrawable_cpd_reverse) {
                this.mReverse = a.getBoolean(attr, false);
            } else if (attr == C2500R.styleable.CircularProgressDrawable_cpd_rotateDuration) {
                this.mRotateDuration = a.getInteger(attr, 0);
            } else if (attr == C2500R.styleable.CircularProgressDrawable_cpd_transformDuration) {
                this.mTransformDuration = a.getInteger(attr, 0);
            } else if (attr == C2500R.styleable.CircularProgressDrawable_cpd_keepDuration) {
                this.mKeepDuration = a.getInteger(attr, 0);
            } else if (attr == C2500R.styleable.CircularProgressDrawable_cpd_transformInterpolator) {
                this.mTransformInterpolator = AnimationUtils.loadInterpolator(context, a.getResourceId(attr, 0));
            } else if (attr == C2500R.styleable.CircularProgressDrawable_pv_progressMode) {
                this.mProgressMode = a.getInteger(attr, 0);
            } else if (attr == C2500R.styleable.CircularProgressDrawable_cpd_inAnimDuration) {
                this.mInAnimationDuration = a.getInteger(attr, 0);
            } else if (attr == C2500R.styleable.CircularProgressDrawable_cpd_inStepColors) {
                TypedArray ta2 = context.getResources().obtainTypedArray(a.getResourceId(attr, 0));
                this.mInColors = new int[ta2.length()];
                for (int j2 = 0; j2 < ta2.length(); j2++) {
                    this.mInColors[j2] = ta2.getColor(j2, 0);
                }
                ta2.recycle();
            } else if (attr == C2500R.styleable.CircularProgressDrawable_cpd_inStepPercent) {
                this.mInStepPercent = a.getFloat(attr, 0.0f);
            } else if (attr == C2500R.styleable.CircularProgressDrawable_cpd_outAnimDuration) {
                this.mOutAnimationDuration = a.getInteger(attr, 0);
            }
        }
        a.recycle();
        if (strokeColors != null) {
            this.mStrokeColors = strokeColors;
        } else if (strokeColorDefined) {
            this.mStrokeColors = new int[]{strokeColor};
        }
        if (this.mStrokeColorIndex >= this.mStrokeColors.length) {
            this.mStrokeColorIndex = 0;
        }
        invalidateSelf();
    }

    public void draw(Canvas canvas) {
        int i = this.mProgressMode;
        if (i == 0) {
            drawDeterminate(canvas);
        } else if (i == 1) {
            drawIndeterminate(canvas);
        }
    }

    private void drawDeterminate(Canvas canvas) {
        float size;
        float radius;
        Canvas canvas2 = canvas;
        Rect bounds = getBounds();
        int i = this.mRunState;
        if (i == 1) {
            float size2 = (((float) this.mStrokeSize) * ((float) Math.min((long) this.mInAnimationDuration, SystemClock.uptimeMillis() - this.mLastRunStateTime))) / ((float) this.mInAnimationDuration);
            if (size2 > 0.0f) {
                radius = (((float) ((Math.min(bounds.width(), bounds.height()) - (this.mPadding * 2)) - (this.mStrokeSize * 2))) + size2) / 2.0f;
                size = size2;
            } else {
                radius = 0.0f;
                size = size2;
            }
        } else if (i == 4) {
            float size3 = (((float) this.mStrokeSize) * ((float) Math.max(0, (((long) this.mOutAnimationDuration) - SystemClock.uptimeMillis()) + this.mLastRunStateTime))) / ((float) this.mOutAnimationDuration);
            if (size3 > 0.0f) {
                radius = (((float) ((Math.min(bounds.width(), bounds.height()) - (this.mPadding * 2)) - (this.mStrokeSize * 2))) + size3) / 2.0f;
                size = size3;
            } else {
                radius = 0.0f;
                size = size3;
            }
        } else if (i != 0) {
            float size4 = (float) this.mStrokeSize;
            radius = ((float) ((Math.min(bounds.width(), bounds.height()) - (this.mPadding * 2)) - this.mStrokeSize)) / 2.0f;
            size = size4;
        } else {
            radius = 0.0f;
            size = 0.0f;
        }
        if (radius > 0.0f) {
            float x = ((float) (bounds.left + bounds.right)) / 2.0f;
            float y = ((float) (bounds.top + bounds.bottom)) / 2.0f;
            this.mPaint.setStrokeWidth(size);
            this.mPaint.setStyle(Paint.Style.STROKE);
            float f = this.mProgressPercent;
            if (f == 1.0f) {
                this.mPaint.setColor(this.mStrokeColors[0]);
                canvas2.drawCircle(x, y, radius, this.mPaint);
            } else if (f == 0.0f) {
                this.mPaint.setColor(this.mStrokeSecondaryColor);
                canvas2.drawCircle(x, y, radius, this.mPaint);
            } else {
                int i2 = -360;
                float sweepAngle = ((float) (this.mReverse ? -360 : 360)) * f;
                this.mRect.set(x - radius, y - radius, x + radius, y + radius);
                this.mPaint.setColor(this.mStrokeSecondaryColor);
                RectF rectF = this.mRect;
                float f2 = this.mStartAngle + sweepAngle;
                if (!this.mReverse) {
                    i2 = 360;
                }
                canvas.drawArc(rectF, f2, ((float) i2) - sweepAngle, false, this.mPaint);
                this.mPaint.setColor(this.mStrokeColors[0]);
                canvas.drawArc(this.mRect, this.mStartAngle, sweepAngle, false, this.mPaint);
            }
        }
    }

    private int getIndeterminateStrokeColor() {
        if (this.mProgressState != 3 || this.mStrokeColors.length == 1) {
            return this.mStrokeColors[this.mStrokeColorIndex];
        }
        float value = Math.max(0.0f, Math.min(1.0f, ((float) (SystemClock.uptimeMillis() - this.mLastProgressStateTime)) / ((float) this.mKeepDuration)));
        int i = this.mStrokeColorIndex;
        int prev_index = i == 0 ? this.mStrokeColors.length - 1 : i - 1;
        int[] iArr = this.mStrokeColors;
        return ColorUtil.getMiddleColor(iArr[prev_index], iArr[i], value);
    }

    private void drawIndeterminate(Canvas canvas) {
        float stepTime;
        Rect bounds;
        Canvas canvas2 = canvas;
        int i = this.mRunState;
        if (i == 1) {
            Rect bounds2 = getBounds();
            float x = ((float) (bounds2.left + bounds2.right)) / 2.0f;
            float y = ((float) (bounds2.top + bounds2.bottom)) / 2.0f;
            float maxRadius = ((float) (Math.min(bounds2.width(), bounds2.height()) - (this.mPadding * 2))) / 2.0f;
            float f = 1.0f;
            float stepTime2 = 1.0f / ((this.mInStepPercent * ((float) (this.mInColors.length + 2))) + 1.0f);
            float time = ((float) (SystemClock.uptimeMillis() - this.mLastRunStateTime)) / ((float) this.mInAnimationDuration);
            float steps = time / stepTime2;
            float outerRadius = 0.0f;
            float innerRadius = 0.0f;
            int i2 = (int) Math.floor((double) steps);
            while (true) {
                if (i2 < 0) {
                    float f2 = stepTime2;
                    float f3 = outerRadius;
                    float f4 = innerRadius;
                    break;
                }
                innerRadius = outerRadius;
                outerRadius = Math.min(f, (steps - ((float) i2)) * this.mInStepPercent) * maxRadius;
                int[] iArr = this.mInColors;
                if (i2 < iArr.length) {
                    if (innerRadius != 0.0f) {
                        if (outerRadius <= innerRadius) {
                            float f5 = stepTime2;
                            float f6 = outerRadius;
                            float f7 = innerRadius;
                            break;
                        }
                        float radius = (innerRadius + outerRadius) / 2.0f;
                        bounds = bounds2;
                        stepTime = stepTime2;
                        this.mRect.set(x - radius, y - radius, x + radius, y + radius);
                        this.mPaint.setStrokeWidth(outerRadius - innerRadius);
                        this.mPaint.setStyle(Paint.Style.STROKE);
                        this.mPaint.setColor(this.mInColors[i2]);
                        canvas2.drawCircle(x, y, radius, this.mPaint);
                    } else {
                        this.mPaint.setColor(iArr[i2]);
                        this.mPaint.setStyle(Paint.Style.FILL);
                        canvas2.drawCircle(x, y, outerRadius, this.mPaint);
                        bounds = bounds2;
                        stepTime = stepTime2;
                    }
                } else {
                    bounds = bounds2;
                    stepTime = stepTime2;
                }
                i2--;
                bounds2 = bounds;
                stepTime2 = stepTime;
                f = 1.0f;
            }
            if (this.mProgressState != -1) {
                float radius2 = maxRadius - (((float) this.mStrokeSize) / 2.0f);
                this.mRect.set(x - radius2, y - radius2, x + radius2, y + radius2);
                this.mPaint.setStrokeWidth((float) this.mStrokeSize);
                this.mPaint.setStyle(Paint.Style.STROKE);
                this.mPaint.setColor(getIndeterminateStrokeColor());
                canvas.drawArc(this.mRect, this.mStartAngle, this.mSweepAngle, false, this.mPaint);
            } else if (steps >= 1.0f / this.mInStepPercent || time >= 1.0f) {
                resetAnimation();
                this.mProgressState = 0;
            }
        } else if (i == 4) {
            float size = (((float) this.mStrokeSize) * ((float) Math.max(0, (((long) this.mOutAnimationDuration) - SystemClock.uptimeMillis()) + this.mLastRunStateTime))) / ((float) this.mOutAnimationDuration);
            if (size > 0.0f) {
                Rect bounds3 = getBounds();
                float radius3 = (((float) ((Math.min(bounds3.width(), bounds3.height()) - (this.mPadding * 2)) - (this.mStrokeSize * 2))) + size) / 2.0f;
                float x2 = ((float) (bounds3.left + bounds3.right)) / 2.0f;
                float y2 = ((float) (bounds3.top + bounds3.bottom)) / 2.0f;
                this.mRect.set(x2 - radius3, y2 - radius3, x2 + radius3, y2 + radius3);
                this.mPaint.setStrokeWidth(size);
                this.mPaint.setStyle(Paint.Style.STROKE);
                this.mPaint.setColor(getIndeterminateStrokeColor());
                canvas.drawArc(this.mRect, this.mStartAngle, this.mSweepAngle, false, this.mPaint);
            }
        } else if (i != 0) {
            Rect bounds4 = getBounds();
            float radius4 = ((float) ((Math.min(bounds4.width(), bounds4.height()) - (this.mPadding * 2)) - this.mStrokeSize)) / 2.0f;
            float x3 = ((float) (bounds4.left + bounds4.right)) / 2.0f;
            float y3 = ((float) (bounds4.top + bounds4.bottom)) / 2.0f;
            this.mRect.set(x3 - radius4, y3 - radius4, x3 + radius4, y3 + radius4);
            this.mPaint.setStrokeWidth((float) this.mStrokeSize);
            this.mPaint.setStyle(Paint.Style.STROKE);
            this.mPaint.setColor(getIndeterminateStrokeColor());
            canvas.drawArc(this.mRect, this.mStartAngle, this.mSweepAngle, false, this.mPaint);
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

    public int getProgressMode() {
        return this.mProgressMode;
    }

    public void setProgressMode(int mode) {
        if (this.mProgressMode != mode) {
            this.mProgressMode = mode;
            invalidateSelf();
        }
    }

    public float getProgress() {
        return this.mProgressPercent;
    }

    public float getSecondaryProgress() {
        return this.mSecondaryProgressPercent;
    }

    public void setProgress(float percent) {
        float percent2 = Math.min(1.0f, Math.max(0.0f, percent));
        if (this.mProgressPercent != percent2) {
            this.mProgressPercent = percent2;
            if (isRunning()) {
                invalidateSelf();
            } else if (this.mProgressPercent != 0.0f) {
                start();
            }
        }
    }

    public void setSecondaryProgress(float percent) {
        float percent2 = Math.min(1.0f, Math.max(0.0f, percent));
        if (this.mSecondaryProgressPercent != percent2) {
            this.mSecondaryProgressPercent = percent2;
            if (isRunning()) {
                invalidateSelf();
            } else if (this.mSecondaryProgressPercent != 0.0f) {
                start();
            }
        }
    }

    private void resetAnimation() {
        long uptimeMillis = SystemClock.uptimeMillis();
        this.mLastUpdateTime = uptimeMillis;
        this.mLastProgressStateTime = uptimeMillis;
        this.mStartAngle = this.mInitialAngle;
        this.mStrokeColorIndex = 0;
        this.mSweepAngle = this.mReverse ? -this.mMinSweepAngle : this.mMinSweepAngle;
    }

    public void start() {
        start(this.mInAnimationDuration > 0);
    }

    public void stop() {
        stop(this.mOutAnimationDuration > 0);
    }

    private void start(boolean withAnimation) {
        if (!isRunning()) {
            resetAnimation();
            if (withAnimation) {
                this.mRunState = 1;
                this.mLastRunStateTime = SystemClock.uptimeMillis();
                this.mProgressState = -1;
            }
            scheduleSelf(this.mUpdater, SystemClock.uptimeMillis() + 16);
            invalidateSelf();
        }
    }

    private void stop(boolean withAnimation) {
        if (isRunning()) {
            if (withAnimation) {
                this.mLastRunStateTime = SystemClock.uptimeMillis();
                if (this.mRunState == 2) {
                    scheduleSelf(this.mUpdater, SystemClock.uptimeMillis() + 16);
                    invalidateSelf();
                }
                this.mRunState = 4;
                return;
            }
            this.mRunState = 0;
            unscheduleSelf(this.mUpdater);
            invalidateSelf();
        }
    }

    public boolean isRunning() {
        return this.mRunState != 0;
    }

    public void scheduleSelf(Runnable what, long when) {
        if (this.mRunState == 0) {
            this.mRunState = this.mInAnimationDuration > 0 ? 1 : 3;
        }
        super.scheduleSelf(what, when);
    }

    /* access modifiers changed from: private */
    public void update() {
        int i = this.mProgressMode;
        if (i == 0) {
            updateDeterminate();
        } else if (i == 1) {
            updateIndeterminate();
        }
    }

    private void updateDeterminate() {
        long curTime = SystemClock.uptimeMillis();
        float rotateOffset = (((float) (curTime - this.mLastUpdateTime)) * 360.0f) / ((float) this.mRotateDuration);
        if (this.mReverse) {
            rotateOffset = -rotateOffset;
        }
        this.mLastUpdateTime = curTime;
        this.mStartAngle += rotateOffset;
        int i = this.mRunState;
        if (i == 1) {
            if (curTime - this.mLastRunStateTime > ((long) this.mInAnimationDuration)) {
                this.mRunState = 3;
            }
        } else if (i == 4 && curTime - this.mLastRunStateTime > ((long) this.mOutAnimationDuration)) {
            stop(false);
            return;
        }
        if (isRunning()) {
            scheduleSelf(this.mUpdater, SystemClock.uptimeMillis() + 16);
        }
        invalidateSelf();
    }

    private void updateIndeterminate() {
        long curTime = SystemClock.uptimeMillis();
        float rotateOffset = (((float) (curTime - this.mLastUpdateTime)) * 360.0f) / ((float) this.mRotateDuration);
        boolean z = this.mReverse;
        if (z) {
            rotateOffset = -rotateOffset;
        }
        this.mLastUpdateTime = curTime;
        int i = this.mProgressState;
        if (i == 0) {
            int i2 = this.mTransformDuration;
            if (i2 <= 0) {
                this.mSweepAngle = z ? -this.mMinSweepAngle : this.mMinSweepAngle;
                this.mProgressState = 1;
                this.mStartAngle += rotateOffset;
                this.mLastProgressStateTime = curTime;
            } else {
                float value = ((float) (curTime - this.mLastProgressStateTime)) / ((float) i2);
                float maxAngle = this.mMaxSweepAngle;
                if (z) {
                    maxAngle = -maxAngle;
                }
                float minAngle = z ? -this.mMinSweepAngle : this.mMinSweepAngle;
                this.mStartAngle += rotateOffset;
                this.mSweepAngle = (this.mTransformInterpolator.getInterpolation(value) * (maxAngle - minAngle)) + minAngle;
                if (value > 1.0f) {
                    this.mSweepAngle = maxAngle;
                    this.mProgressState = 1;
                    this.mLastProgressStateTime = curTime;
                }
            }
        } else if (i == 1) {
            this.mStartAngle += rotateOffset;
            if (curTime - this.mLastProgressStateTime > ((long) this.mKeepDuration)) {
                this.mProgressState = 2;
                this.mLastProgressStateTime = curTime;
            }
        } else if (i == 2) {
            int i3 = this.mTransformDuration;
            if (i3 <= 0) {
                this.mSweepAngle = z ? -this.mMinSweepAngle : this.mMinSweepAngle;
                this.mProgressState = 3;
                this.mStartAngle += rotateOffset;
                this.mLastProgressStateTime = curTime;
                this.mStrokeColorIndex = (this.mStrokeColorIndex + 1) % this.mStrokeColors.length;
            } else {
                float value2 = ((float) (curTime - this.mLastProgressStateTime)) / ((float) i3);
                float maxAngle2 = this.mMaxSweepAngle;
                if (z) {
                    maxAngle2 = -maxAngle2;
                }
                float minAngle2 = z ? -this.mMinSweepAngle : this.mMinSweepAngle;
                float newSweepAngle = ((1.0f - this.mTransformInterpolator.getInterpolation(value2)) * (maxAngle2 - minAngle2)) + minAngle2;
                this.mStartAngle += (this.mSweepAngle + rotateOffset) - newSweepAngle;
                this.mSweepAngle = newSweepAngle;
                if (value2 > 1.0f) {
                    this.mSweepAngle = minAngle2;
                    this.mProgressState = 3;
                    this.mLastProgressStateTime = curTime;
                    this.mStrokeColorIndex = (this.mStrokeColorIndex + 1) % this.mStrokeColors.length;
                }
            }
        } else if (i == 3) {
            this.mStartAngle += rotateOffset;
            if (curTime - this.mLastProgressStateTime > ((long) this.mKeepDuration)) {
                this.mProgressState = 0;
                this.mLastProgressStateTime = curTime;
            }
        }
        int i4 = this.mRunState;
        if (i4 == 1) {
            if (curTime - this.mLastRunStateTime > ((long) this.mInAnimationDuration)) {
                this.mRunState = 3;
                if (this.mProgressState == -1) {
                    resetAnimation();
                    this.mProgressState = 0;
                }
            }
        } else if (i4 == 4 && curTime - this.mLastRunStateTime > ((long) this.mOutAnimationDuration)) {
            stop(false);
            return;
        }
        if (isRunning()) {
            scheduleSelf(this.mUpdater, SystemClock.uptimeMillis() + 16);
        }
        invalidateSelf();
    }

    public static class Builder {
        private int mInAnimationDuration;
        private int[] mInColors;
        private float mInStepPercent;
        private float mInitialAngle;
        private int mKeepDuration;
        private float mMaxSweepAngle;
        private float mMinSweepAngle;
        private int mOutAnimationDuration;
        private int mPadding;
        private int mProgressMode;
        private float mProgressPercent;
        private boolean mReverse;
        private int mRotateDuration;
        private float mSecondaryProgressPercent;
        private int[] mStrokeColors;
        private int mStrokeSecondaryColor;
        private int mStrokeSize;
        private int mTransformDuration;
        private Interpolator mTransformInterpolator;

        public Builder() {
        }

        public Builder(Context context, int defStyleRes) {
            this(context, (AttributeSet) null, 0, defStyleRes);
        }

        public Builder(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
            TypedArray a = context.obtainStyledAttributes(attrs, C2500R.styleable.CircularProgressDrawable, defStyleAttr, defStyleRes);
            padding(a.getDimensionPixelSize(C2500R.styleable.CircularProgressDrawable_cpd_padding, 0));
            initialAngle((float) a.getInteger(C2500R.styleable.CircularProgressDrawable_cpd_initialAngle, 0));
            progressPercent(a.getFloat(C2500R.styleable.CircularProgressDrawable_pv_progress, 0.0f));
            secondaryProgressPercent(a.getFloat(C2500R.styleable.CircularProgressDrawable_pv_secondaryProgress, 0.0f));
            maxSweepAngle((float) a.getInteger(C2500R.styleable.CircularProgressDrawable_cpd_maxSweepAngle, 270));
            minSweepAngle((float) a.getInteger(C2500R.styleable.CircularProgressDrawable_cpd_minSweepAngle, 1));
            strokeSize(a.getDimensionPixelSize(C2500R.styleable.CircularProgressDrawable_cpd_strokeSize, ThemeUtil.dpToPx(context, 4)));
            strokeColors(a.getColor(C2500R.styleable.CircularProgressDrawable_cpd_strokeColor, ThemeUtil.colorPrimary(context, ViewCompat.MEASURED_STATE_MASK)));
            int resourceId = a.getResourceId(C2500R.styleable.CircularProgressDrawable_cpd_strokeColors, 0);
            int resId = resourceId;
            if (resourceId != 0) {
                TypedArray ta = context.getResources().obtainTypedArray(resId);
                int[] colors = new int[ta.length()];
                for (int j = 0; j < ta.length(); j++) {
                    colors[j] = ta.getColor(j, 0);
                }
                ta.recycle();
                strokeColors(colors);
            }
            strokeSecondaryColor(a.getColor(C2500R.styleable.CircularProgressDrawable_cpd_strokeSecondaryColor, 0));
            reverse(a.getBoolean(C2500R.styleable.CircularProgressDrawable_cpd_reverse, false));
            rotateDuration(a.getInteger(C2500R.styleable.CircularProgressDrawable_cpd_rotateDuration, context.getResources().getInteger(17694722)));
            transformDuration(a.getInteger(C2500R.styleable.CircularProgressDrawable_cpd_transformDuration, context.getResources().getInteger(17694721)));
            keepDuration(a.getInteger(C2500R.styleable.CircularProgressDrawable_cpd_keepDuration, context.getResources().getInteger(17694720)));
            int resourceId2 = a.getResourceId(C2500R.styleable.CircularProgressDrawable_cpd_transformInterpolator, 0);
            int resId2 = resourceId2;
            if (resourceId2 != 0) {
                transformInterpolator(AnimationUtils.loadInterpolator(context, resId2));
            }
            progressMode(a.getInteger(C2500R.styleable.CircularProgressDrawable_pv_progressMode, 1));
            inAnimDuration(a.getInteger(C2500R.styleable.CircularProgressDrawable_cpd_inAnimDuration, context.getResources().getInteger(17694721)));
            int resourceId3 = a.getResourceId(C2500R.styleable.CircularProgressDrawable_cpd_inStepColors, 0);
            int resId3 = resourceId3;
            if (resourceId3 != 0) {
                TypedArray ta2 = context.getResources().obtainTypedArray(resId3);
                int[] colors2 = new int[ta2.length()];
                for (int j2 = 0; j2 < ta2.length(); j2++) {
                    colors2[j2] = ta2.getColor(j2, 0);
                }
                ta2.recycle();
                inStepColors(colors2);
            }
            inStepPercent(a.getFloat(C2500R.styleable.CircularProgressDrawable_cpd_inStepPercent, 0.5f));
            outAnimDuration(a.getInteger(C2500R.styleable.CircularProgressDrawable_cpd_outAnimDuration, context.getResources().getInteger(17694721)));
            a.recycle();
        }

        public CircularProgressDrawable build() {
            if (this.mStrokeColors == null) {
                this.mStrokeColors = new int[]{-16737793};
            }
            if (this.mInColors == null && this.mInAnimationDuration > 0) {
                this.mInColors = new int[]{-4860673, -2168068, -327682};
            }
            if (this.mTransformInterpolator == null) {
                this.mTransformInterpolator = new DecelerateInterpolator();
            }
            CircularProgressDrawable circularProgressDrawable = r2;
            CircularProgressDrawable circularProgressDrawable2 = new CircularProgressDrawable(this.mPadding, this.mInitialAngle, this.mProgressPercent, this.mSecondaryProgressPercent, this.mMaxSweepAngle, this.mMinSweepAngle, this.mStrokeSize, this.mStrokeColors, this.mStrokeSecondaryColor, this.mReverse, this.mRotateDuration, this.mTransformDuration, this.mKeepDuration, this.mTransformInterpolator, this.mProgressMode, this.mInAnimationDuration, this.mInStepPercent, this.mInColors, this.mOutAnimationDuration);
            return circularProgressDrawable;
        }

        public Builder padding(int padding) {
            this.mPadding = padding;
            return this;
        }

        public Builder initialAngle(float angle) {
            this.mInitialAngle = angle;
            return this;
        }

        public Builder progressPercent(float percent) {
            this.mProgressPercent = percent;
            return this;
        }

        public Builder secondaryProgressPercent(float percent) {
            this.mSecondaryProgressPercent = percent;
            return this;
        }

        public Builder maxSweepAngle(float angle) {
            this.mMaxSweepAngle = angle;
            return this;
        }

        public Builder minSweepAngle(float angle) {
            this.mMinSweepAngle = angle;
            return this;
        }

        public Builder strokeSize(int strokeSize) {
            this.mStrokeSize = strokeSize;
            return this;
        }

        public Builder strokeColors(int... strokeColors) {
            this.mStrokeColors = strokeColors;
            return this;
        }

        public Builder strokeSecondaryColor(int color) {
            this.mStrokeSecondaryColor = color;
            return this;
        }

        public Builder reverse(boolean reverse) {
            this.mReverse = reverse;
            return this;
        }

        public Builder reverse() {
            return reverse(true);
        }

        public Builder rotateDuration(int duration) {
            this.mRotateDuration = duration;
            return this;
        }

        public Builder transformDuration(int duration) {
            this.mTransformDuration = duration;
            return this;
        }

        public Builder keepDuration(int duration) {
            this.mKeepDuration = duration;
            return this;
        }

        public Builder transformInterpolator(Interpolator interpolator) {
            this.mTransformInterpolator = interpolator;
            return this;
        }

        public Builder progressMode(int mode) {
            this.mProgressMode = mode;
            return this;
        }

        public Builder inAnimDuration(int duration) {
            this.mInAnimationDuration = duration;
            return this;
        }

        public Builder inStepPercent(float percent) {
            this.mInStepPercent = percent;
            return this;
        }

        public Builder inStepColors(int... colors) {
            this.mInColors = colors;
            return this;
        }

        public Builder outAnimDuration(int duration) {
            this.mOutAnimationDuration = duration;
            return this;
        }
    }
}
