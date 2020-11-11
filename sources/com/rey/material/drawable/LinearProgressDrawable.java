package com.rey.material.drawable;

import android.content.Context;
import android.content.res.TypedArray;
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
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import androidx.core.view.ViewCompat;
import com.google.logging.type.LogSeverity;
import com.rey.material.C2500R;
import com.rey.material.util.ColorUtil;
import com.rey.material.util.ThemeUtil;

public class LinearProgressDrawable extends Drawable implements Animatable {
    public static final int ALIGN_BOTTOM = 2;
    public static final int ALIGN_CENTER = 1;
    public static final int ALIGN_TOP = 0;
    private static final int PROGRESS_STATE_KEEP_SHRINK = 3;
    private static final int PROGRESS_STATE_KEEP_STRETCH = 1;
    private static final int PROGRESS_STATE_SHRINK = 2;
    private static final int PROGRESS_STATE_STRETCH = 0;
    private static final int RUN_STATE_RUNNING = 3;
    private static final int RUN_STATE_STARTED = 2;
    private static final int RUN_STATE_STARTING = 1;
    private static final int RUN_STATE_STOPPED = 0;
    private static final int RUN_STATE_STOPPING = 4;
    private float mAnimTime;
    private int mInAnimationDuration;
    private int mKeepDuration;
    private long mLastProgressStateTime;
    private long mLastRunStateTime;
    private long mLastUpdateTime;
    private float mLineWidth;
    private int mMaxLineWidth;
    private float mMaxLineWidthPercent;
    private int mMinLineWidth;
    private float mMinLineWidthPercent;
    private int mOutAnimationDuration;
    private Paint mPaint;
    private Path mPath;
    private DashPathEffect mPathEffect;
    private int mProgressMode;
    private float mProgressPercent;
    private int mProgressState;
    private boolean mReverse;
    private int mRunState;
    private float mSecondaryProgressPercent;
    private float mStartLine;
    private int mStrokeColorIndex;
    private int[] mStrokeColors;
    private int mStrokeSecondaryColor;
    private int mStrokeSize;
    private int mTransformDuration;
    private Interpolator mTransformInterpolator;
    private int mTravelDuration;
    private final Runnable mUpdater;
    private int mVerticalAlign;

    private LinearProgressDrawable(float progressPercent, float secondaryProgressPercent, int maxLineWidth, float maxLineWidthPercent, int minLineWidth, float minLineWidthPercent, int strokeSize, int verticalAlign, int[] strokeColors, int strokeSecondaryColor, boolean reverse, int travelDuration, int transformDuration, int keepDuration, Interpolator transformInterpolator, int progressMode, int inAnimDuration, int outAnimDuration) {
        this.mRunState = 0;
        this.mUpdater = new Runnable() {
            public void run() {
                LinearProgressDrawable.this.update();
            }
        };
        setProgress(progressPercent);
        setSecondaryProgress(secondaryProgressPercent);
        this.mMaxLineWidth = maxLineWidth;
        this.mMaxLineWidthPercent = maxLineWidthPercent;
        this.mMinLineWidth = minLineWidth;
        this.mMinLineWidthPercent = minLineWidthPercent;
        this.mStrokeSize = strokeSize;
        this.mVerticalAlign = verticalAlign;
        this.mStrokeColors = strokeColors;
        this.mStrokeSecondaryColor = strokeSecondaryColor;
        this.mReverse = reverse;
        this.mTravelDuration = travelDuration;
        this.mTransformDuration = transformDuration;
        this.mKeepDuration = keepDuration;
        this.mTransformInterpolator = transformInterpolator;
        this.mProgressMode = progressMode;
        this.mInAnimationDuration = inAnimDuration;
        this.mOutAnimationDuration = outAnimDuration;
        Paint paint = new Paint();
        this.mPaint = paint;
        paint.setAntiAlias(true);
        this.mPaint.setStrokeCap(Paint.Cap.ROUND);
        this.mPaint.setStrokeJoin(Paint.Join.ROUND);
        this.mPath = new Path();
    }

    public void applyStyle(Context context, int resId) {
        TypedArray a = context.obtainStyledAttributes(resId, C2500R.styleable.LinearProgressDrawable);
        int strokeColor = 0;
        boolean strokeColorDefined = false;
        int[] strokeColors = null;
        int count = a.getIndexCount();
        for (int i = 0; i < count; i++) {
            int attr = a.getIndex(i);
            if (attr == C2500R.styleable.LinearProgressDrawable_pv_progress) {
                setProgress(a.getFloat(attr, 0.0f));
            } else if (attr == C2500R.styleable.LinearProgressDrawable_pv_secondaryProgress) {
                setSecondaryProgress(a.getFloat(attr, 0.0f));
            } else if (attr == C2500R.styleable.LinearProgressDrawable_lpd_maxLineWidth) {
                if (a.peekValue(attr).type == 6) {
                    this.mMaxLineWidthPercent = a.getFraction(attr, 1, 1, 0.75f);
                    this.mMaxLineWidth = 0;
                } else {
                    this.mMaxLineWidth = a.getDimensionPixelSize(attr, 0);
                    this.mMaxLineWidthPercent = 0.0f;
                }
            } else if (attr == C2500R.styleable.LinearProgressDrawable_lpd_minLineWidth) {
                if (a.peekValue(attr).type == 6) {
                    this.mMinLineWidthPercent = a.getFraction(attr, 1, 1, 0.25f);
                    this.mMinLineWidth = 0;
                } else {
                    this.mMinLineWidth = a.getDimensionPixelSize(attr, 0);
                    this.mMinLineWidthPercent = 0.0f;
                }
            } else if (attr == C2500R.styleable.LinearProgressDrawable_lpd_strokeSize) {
                this.mStrokeSize = a.getDimensionPixelSize(attr, 0);
            } else if (attr == C2500R.styleable.LinearProgressDrawable_lpd_verticalAlign) {
                this.mVerticalAlign = a.getInteger(attr, 0);
            } else if (attr == C2500R.styleable.LinearProgressDrawable_lpd_strokeColor) {
                strokeColor = a.getColor(attr, 0);
                strokeColorDefined = true;
            } else if (attr == C2500R.styleable.LinearProgressDrawable_lpd_strokeColors) {
                TypedArray ta = context.getResources().obtainTypedArray(a.getResourceId(attr, 0));
                strokeColors = new int[ta.length()];
                for (int j = 0; j < ta.length(); j++) {
                    strokeColors[j] = ta.getColor(j, 0);
                }
                ta.recycle();
            } else if (attr == C2500R.styleable.LinearProgressDrawable_lpd_strokeSecondaryColor) {
                this.mStrokeSecondaryColor = a.getColor(attr, 0);
            } else if (attr == C2500R.styleable.LinearProgressDrawable_lpd_reverse) {
                this.mReverse = a.getBoolean(attr, false);
            } else if (attr == C2500R.styleable.LinearProgressDrawable_lpd_travelDuration) {
                this.mTravelDuration = a.getInteger(attr, 0);
            } else if (attr == C2500R.styleable.LinearProgressDrawable_lpd_transformDuration) {
                this.mTransformDuration = a.getInteger(attr, 0);
            } else if (attr == C2500R.styleable.LinearProgressDrawable_lpd_keepDuration) {
                this.mKeepDuration = a.getInteger(attr, 0);
            } else if (attr == C2500R.styleable.LinearProgressDrawable_lpd_transformInterpolator) {
                this.mTransformInterpolator = AnimationUtils.loadInterpolator(context, a.getResourceId(attr, 0));
            } else if (attr == C2500R.styleable.LinearProgressDrawable_pv_progressMode) {
                this.mProgressMode = a.getInteger(attr, 0);
            } else if (attr == C2500R.styleable.LinearProgressDrawable_lpd_inAnimDuration) {
                this.mInAnimationDuration = a.getInteger(attr, 0);
            } else if (attr == C2500R.styleable.LinearProgressDrawable_lpd_outAnimDuration) {
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
        } else if (i == 2) {
            drawBuffer(canvas);
        } else if (i == 3) {
            drawQuery(canvas);
        }
    }

    private void drawLinePath(Canvas canvas, float x1, float y1, float x2, float y2, Paint paint) {
        this.mPath.reset();
        this.mPath.moveTo(x1, y1);
        this.mPath.lineTo(x2, y2);
        canvas.drawPath(this.mPath, paint);
    }

    private void drawDeterminate(Canvas canvas) {
        float size;
        float y;
        Rect bounds = getBounds();
        int width = bounds.width();
        int i = this.mRunState;
        if (i == 1) {
            size = (((float) this.mStrokeSize) * ((float) Math.min((long) this.mInAnimationDuration, SystemClock.uptimeMillis() - this.mLastRunStateTime))) / ((float) this.mInAnimationDuration);
        } else if (i == 4) {
            size = (((float) this.mStrokeSize) * ((float) Math.max(0, (((long) this.mOutAnimationDuration) - SystemClock.uptimeMillis()) + this.mLastRunStateTime))) / ((float) this.mOutAnimationDuration);
        } else if (i != 0) {
            size = (float) this.mStrokeSize;
        } else {
            size = 0.0f;
        }
        if (size > 0.0f) {
            float lineWidth = ((float) width) * this.mProgressPercent;
            int i2 = this.mVerticalAlign;
            if (i2 == 0) {
                y = size / 2.0f;
            } else if (i2 == 1) {
                y = ((float) bounds.height()) / 2.0f;
            } else if (i2 != 2) {
                y = 0.0f;
            } else {
                y = ((float) bounds.height()) - (size / 2.0f);
            }
            this.mPaint.setStrokeWidth(size);
            this.mPaint.setStyle(Paint.Style.STROKE);
            if (this.mProgressPercent != 1.0f) {
                this.mPaint.setColor(this.mStrokeSecondaryColor);
                if (this.mReverse) {
                    canvas.drawLine(0.0f, y, ((float) width) - lineWidth, y, this.mPaint);
                } else {
                    canvas.drawLine(lineWidth, y, (float) width, y, this.mPaint);
                }
            }
            if (this.mProgressPercent != 0.0f) {
                this.mPaint.setColor(this.mStrokeColors[0]);
                if (this.mReverse) {
                    drawLinePath(canvas, ((float) width) - lineWidth, y, (float) width, y, this.mPaint);
                    return;
                }
                drawLinePath(canvas, 0.0f, y, lineWidth, y, this.mPaint);
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
        Rect bounds = getBounds();
        int width = bounds.width();
        float size = 0.0f;
        int i = this.mRunState;
        if (i == 1) {
            size = (((float) this.mStrokeSize) * ((float) Math.min((long) this.mInAnimationDuration, SystemClock.uptimeMillis() - this.mLastRunStateTime))) / ((float) this.mInAnimationDuration);
        } else if (i == 4) {
            size = (((float) this.mStrokeSize) * ((float) Math.max(0, (((long) this.mOutAnimationDuration) - SystemClock.uptimeMillis()) + this.mLastRunStateTime))) / ((float) this.mOutAnimationDuration);
        } else if (i != 0) {
            size = (float) this.mStrokeSize;
        }
        if (size > 0.0f) {
            float y = 0.0f;
            int i2 = this.mVerticalAlign;
            if (i2 == 0) {
                y = size / 2.0f;
            } else if (i2 == 1) {
                y = ((float) bounds.height()) / 2.0f;
            } else if (i2 == 2) {
                y = ((float) bounds.height()) - (size / 2.0f);
            }
            this.mPaint.setStrokeWidth(size);
            this.mPaint.setStyle(Paint.Style.STROKE);
            float endLine = offset(this.mStartLine, this.mLineWidth, (float) width);
            if (this.mReverse) {
                if (endLine <= this.mStartLine) {
                    this.mPaint.setColor(this.mStrokeSecondaryColor);
                    if (endLine > 0.0f) {
                        canvas.drawLine(0.0f, y, endLine, y, this.mPaint);
                    }
                    float f = this.mStartLine;
                    if (f < ((float) width)) {
                        canvas.drawLine(f, y, (float) width, y, this.mPaint);
                    }
                    this.mPaint.setColor(getIndeterminateStrokeColor());
                    drawLinePath(canvas, endLine, y, this.mStartLine, y, this.mPaint);
                    return;
                }
                this.mPaint.setColor(this.mStrokeSecondaryColor);
                canvas.drawLine(this.mStartLine, y, endLine, y, this.mPaint);
                this.mPaint.setColor(getIndeterminateStrokeColor());
                this.mPath.reset();
                if (this.mStartLine > 0.0f) {
                    this.mPath.moveTo(0.0f, y);
                    this.mPath.lineTo(this.mStartLine, y);
                }
                if (endLine < ((float) width)) {
                    this.mPath.moveTo(endLine, y);
                    this.mPath.lineTo((float) width, y);
                }
                canvas.drawPath(this.mPath, this.mPaint);
            } else if (endLine >= this.mStartLine) {
                this.mPaint.setColor(this.mStrokeSecondaryColor);
                float f2 = this.mStartLine;
                if (f2 > 0.0f) {
                    canvas.drawLine(0.0f, y, f2, y, this.mPaint);
                }
                if (endLine < ((float) width)) {
                    canvas.drawLine(endLine, y, (float) width, y, this.mPaint);
                }
                this.mPaint.setColor(getIndeterminateStrokeColor());
                drawLinePath(canvas, this.mStartLine, y, endLine, y, this.mPaint);
            } else {
                this.mPaint.setColor(this.mStrokeSecondaryColor);
                canvas.drawLine(endLine, y, this.mStartLine, y, this.mPaint);
                this.mPaint.setColor(getIndeterminateStrokeColor());
                this.mPath.reset();
                if (endLine > 0.0f) {
                    this.mPath.moveTo(0.0f, y);
                    this.mPath.lineTo(endLine, y);
                }
                float f3 = this.mStartLine;
                if (f3 < ((float) width)) {
                    this.mPath.moveTo(f3, y);
                    this.mPath.lineTo((float) width, y);
                }
                canvas.drawPath(this.mPath, this.mPaint);
            }
        }
    }

    private PathEffect getPathEffect() {
        if (this.mPathEffect == null) {
            this.mPathEffect = new DashPathEffect(new float[]{0.1f, (float) (this.mStrokeSize * 2)}, 0.0f);
        }
        return this.mPathEffect;
    }

    private void drawBuffer(Canvas canvas) {
        float size;
        float y;
        Rect bounds = getBounds();
        int width = bounds.width();
        int i = this.mRunState;
        if (i == 1) {
            size = (((float) this.mStrokeSize) * ((float) Math.min((long) this.mInAnimationDuration, SystemClock.uptimeMillis() - this.mLastRunStateTime))) / ((float) this.mInAnimationDuration);
        } else if (i == 4) {
            size = (((float) this.mStrokeSize) * ((float) Math.max(0, (((long) this.mOutAnimationDuration) - SystemClock.uptimeMillis()) + this.mLastRunStateTime))) / ((float) this.mOutAnimationDuration);
        } else if (i != 0) {
            size = (float) this.mStrokeSize;
        } else {
            size = 0.0f;
        }
        if (size > 0.0f) {
            float lineWidth = ((float) width) * this.mProgressPercent;
            float secondaryLineWidth = ((float) width) * this.mSecondaryProgressPercent;
            int i2 = this.mVerticalAlign;
            if (i2 == 0) {
                y = size / 2.0f;
            } else if (i2 == 1) {
                y = ((float) bounds.height()) / 2.0f;
            } else if (i2 != 2) {
                y = 0.0f;
            } else {
                y = ((float) bounds.height()) - (size / 2.0f);
            }
            this.mPaint.setStyle(Paint.Style.STROKE);
            if (this.mProgressPercent != 1.0f) {
                this.mPaint.setStrokeWidth(size);
                this.mPaint.setColor(this.mStrokeSecondaryColor);
                this.mPaint.setPathEffect((PathEffect) null);
                if (this.mReverse) {
                    drawLinePath(canvas, ((float) width) - secondaryLineWidth, y, ((float) width) - lineWidth, y, this.mPaint);
                } else {
                    drawLinePath(canvas, secondaryLineWidth, y, lineWidth, y, this.mPaint);
                }
                this.mPaint.setStrokeWidth(this.mLineWidth);
                this.mPaint.setPathEffect(getPathEffect());
                float offset = ((float) (this.mStrokeSize * 2)) - this.mStartLine;
                if (this.mReverse) {
                    drawLinePath(canvas, -offset, y, ((float) width) - secondaryLineWidth, y, this.mPaint);
                } else {
                    drawLinePath(canvas, ((float) width) + offset, y, secondaryLineWidth, y, this.mPaint);
                }
            }
            if (this.mProgressPercent != 0.0f) {
                this.mPaint.setStrokeWidth(size);
                this.mPaint.setColor(this.mStrokeColors[0]);
                this.mPaint.setPathEffect((PathEffect) null);
                if (this.mReverse) {
                    drawLinePath(canvas, ((float) width) - lineWidth, y, (float) width, y, this.mPaint);
                    return;
                }
                drawLinePath(canvas, 0.0f, y, lineWidth, y, this.mPaint);
            }
        }
    }

    private int getQueryStrokeColor() {
        return ColorUtil.getColor(this.mStrokeColors[0], this.mAnimTime);
    }

    private void drawQuery(Canvas canvas) {
        Rect bounds = getBounds();
        int width = bounds.width();
        float size = 0.0f;
        int i = this.mRunState;
        if (i == 1) {
            size = (((float) this.mStrokeSize) * ((float) Math.min((long) this.mInAnimationDuration, SystemClock.uptimeMillis() - this.mLastRunStateTime))) / ((float) this.mInAnimationDuration);
        } else if (i == 4) {
            size = (((float) this.mStrokeSize) * ((float) Math.max(0, (((long) this.mOutAnimationDuration) - SystemClock.uptimeMillis()) + this.mLastRunStateTime))) / ((float) this.mOutAnimationDuration);
        } else if (i != 0) {
            size = (float) this.mStrokeSize;
        }
        if (size > 0.0f) {
            float y = 0.0f;
            int i2 = this.mVerticalAlign;
            if (i2 == 0) {
                y = size / 2.0f;
            } else if (i2 == 1) {
                y = ((float) bounds.height()) / 2.0f;
            } else if (i2 == 2) {
                y = ((float) bounds.height()) - (size / 2.0f);
            }
            this.mPaint.setStrokeWidth(size);
            this.mPaint.setStyle(Paint.Style.STROKE);
            if (this.mProgressPercent != 1.0f) {
                this.mPaint.setColor(this.mStrokeSecondaryColor);
                canvas.drawLine(0.0f, y, (float) width, y, this.mPaint);
                if (this.mAnimTime < 1.0f) {
                    float endLine = Math.max(0.0f, Math.min((float) width, this.mStartLine + this.mLineWidth));
                    this.mPaint.setColor(getQueryStrokeColor());
                    drawLinePath(canvas, this.mStartLine, y, endLine, y, this.mPaint);
                }
            }
            float endLine2 = this.mProgressPercent;
            if (endLine2 != 0.0f) {
                float lineWidth = ((float) width) * endLine2;
                this.mPaint.setColor(this.mStrokeColors[0]);
                if (this.mReverse) {
                    drawLinePath(canvas, ((float) width) - lineWidth, y, (float) width, y, this.mPaint);
                    return;
                }
                drawLinePath(canvas, 0.0f, y, lineWidth, y, this.mPaint);
            }
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
        int i = this.mProgressMode;
        float f = 0.0f;
        if (i == 1) {
            if (this.mReverse) {
                f = (float) getBounds().width();
            }
            this.mStartLine = f;
            this.mStrokeColorIndex = 0;
            this.mLineWidth = (float) (this.mReverse ? -this.mMinLineWidth : this.mMinLineWidth);
            this.mProgressState = 0;
        } else if (i == 2) {
            this.mStartLine = 0.0f;
        } else if (i == 3) {
            if (!this.mReverse) {
                f = (float) getBounds().width();
            }
            this.mStartLine = f;
            this.mStrokeColorIndex = 0;
            this.mLineWidth = (float) (!this.mReverse ? -this.mMaxLineWidth : this.mMaxLineWidth);
        }
    }

    public void start() {
        start(this.mInAnimationDuration > 0);
    }

    public void stop() {
        stop(this.mOutAnimationDuration > 0);
    }

    private void start(boolean withAnimation) {
        if (!isRunning()) {
            if (withAnimation) {
                this.mRunState = 1;
                this.mLastRunStateTime = SystemClock.uptimeMillis();
            }
            resetAnimation();
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
        } else if (i == 2) {
            updateBuffer();
        } else if (i == 3) {
            updateQuery();
        }
    }

    private void updateDeterminate() {
        long curTime = SystemClock.uptimeMillis();
        int i = this.mRunState;
        if (i == 1) {
            if (curTime - this.mLastRunStateTime > ((long) this.mInAnimationDuration)) {
                this.mRunState = 2;
                return;
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

    private float offset(float pos, float offset, float max) {
        float pos2 = pos + offset;
        if (pos2 > max) {
            return pos2 - max;
        }
        if (pos2 < 0.0f) {
            return max + pos2;
        }
        return pos2;
    }

    private void updateIndeterminate() {
        int width = getBounds().width();
        long curTime = SystemClock.uptimeMillis();
        float travelOffset = (((float) (curTime - this.mLastUpdateTime)) * ((float) width)) / ((float) this.mTravelDuration);
        boolean z = this.mReverse;
        if (z) {
            travelOffset = -travelOffset;
        }
        this.mLastUpdateTime = curTime;
        int i = this.mProgressState;
        if (i == 0) {
            int i2 = this.mTransformDuration;
            if (i2 <= 0) {
                int i3 = this.mMinLineWidth;
                float f = i3 == 0 ? ((float) width) * this.mMinLineWidthPercent : (float) i3;
                this.mLineWidth = f;
                if (z) {
                    this.mLineWidth = -f;
                }
                this.mStartLine = offset(this.mStartLine, travelOffset, (float) width);
                this.mProgressState = 1;
                this.mLastProgressStateTime = curTime;
            } else {
                float value = ((float) (curTime - this.mLastProgressStateTime)) / ((float) i2);
                int i4 = this.mMaxLineWidth;
                float maxWidth = i4 == 0 ? ((float) width) * this.mMaxLineWidthPercent : (float) i4;
                int i5 = this.mMinLineWidth;
                float minWidth = i5 == 0 ? ((float) width) * this.mMinLineWidthPercent : (float) i5;
                this.mStartLine = offset(this.mStartLine, travelOffset, (float) width);
                float interpolation = (this.mTransformInterpolator.getInterpolation(value) * (maxWidth - minWidth)) + minWidth;
                this.mLineWidth = interpolation;
                boolean z2 = this.mReverse;
                if (z2) {
                    this.mLineWidth = -interpolation;
                }
                if (value > 1.0f) {
                    this.mLineWidth = z2 ? -maxWidth : maxWidth;
                    this.mProgressState = 1;
                    this.mLastProgressStateTime = curTime;
                }
            }
        } else if (i == 1) {
            this.mStartLine = offset(this.mStartLine, travelOffset, (float) width);
            if (curTime - this.mLastProgressStateTime > ((long) this.mKeepDuration)) {
                this.mProgressState = 2;
                this.mLastProgressStateTime = curTime;
            }
        } else if (i == 2) {
            int i6 = this.mTransformDuration;
            if (i6 <= 0) {
                int i7 = this.mMinLineWidth;
                float f2 = i7 == 0 ? ((float) width) * this.mMinLineWidthPercent : (float) i7;
                this.mLineWidth = f2;
                if (z) {
                    this.mLineWidth = -f2;
                }
                this.mStartLine = offset(this.mStartLine, travelOffset, (float) width);
                this.mProgressState = 3;
                this.mLastProgressStateTime = curTime;
                this.mStrokeColorIndex = (this.mStrokeColorIndex + 1) % this.mStrokeColors.length;
            } else {
                float value2 = ((float) (curTime - this.mLastProgressStateTime)) / ((float) i6);
                int i8 = this.mMaxLineWidth;
                float maxWidth2 = i8 == 0 ? ((float) width) * this.mMaxLineWidthPercent : (float) i8;
                int i9 = this.mMinLineWidth;
                float minWidth2 = i9 == 0 ? ((float) width) * this.mMinLineWidthPercent : (float) i9;
                float newLineWidth = ((1.0f - this.mTransformInterpolator.getInterpolation(value2)) * (maxWidth2 - minWidth2)) + minWidth2;
                if (this.mReverse) {
                    newLineWidth = -newLineWidth;
                }
                this.mStartLine = offset(this.mStartLine, (this.mLineWidth + travelOffset) - newLineWidth, (float) width);
                this.mLineWidth = newLineWidth;
                if (value2 > 1.0f) {
                    this.mLineWidth = this.mReverse ? -minWidth2 : minWidth2;
                    this.mProgressState = 3;
                    this.mLastProgressStateTime = curTime;
                    this.mStrokeColorIndex = (this.mStrokeColorIndex + 1) % this.mStrokeColors.length;
                }
            }
        } else if (i == 3) {
            this.mStartLine = offset(this.mStartLine, travelOffset, (float) width);
            if (curTime - this.mLastProgressStateTime > ((long) this.mKeepDuration)) {
                this.mProgressState = 0;
                this.mLastProgressStateTime = curTime;
            }
        }
        int i10 = this.mRunState;
        if (i10 == 1) {
            if (curTime - this.mLastRunStateTime > ((long) this.mInAnimationDuration)) {
                this.mRunState = 3;
            }
        } else if (i10 == 4 && curTime - this.mLastRunStateTime > ((long) this.mOutAnimationDuration)) {
            stop(false);
            return;
        }
        if (isRunning()) {
            scheduleSelf(this.mUpdater, SystemClock.uptimeMillis() + 16);
        }
        invalidateSelf();
    }

    private void updateBuffer() {
        long curTime = SystemClock.uptimeMillis();
        float maxDistance = (float) (this.mStrokeSize * 2);
        this.mStartLine += (((float) (curTime - this.mLastUpdateTime)) * maxDistance) / ((float) this.mTravelDuration);
        while (true) {
            float f = this.mStartLine;
            if (f <= maxDistance) {
                break;
            }
            this.mStartLine = f - maxDistance;
        }
        this.mLastUpdateTime = curTime;
        int i = this.mProgressState;
        if (i == 0) {
            int i2 = this.mTransformDuration;
            if (i2 <= 0) {
                this.mProgressState = 1;
                this.mLastProgressStateTime = curTime;
            } else {
                float value = ((float) (curTime - this.mLastProgressStateTime)) / ((float) i2);
                float interpolation = this.mTransformInterpolator.getInterpolation(value);
                int i3 = this.mStrokeSize;
                this.mLineWidth = interpolation * ((float) i3);
                if (value > 1.0f) {
                    this.mLineWidth = (float) i3;
                    this.mProgressState = 1;
                    this.mLastProgressStateTime = curTime;
                }
            }
        } else if (i != 1) {
            if (i == 2) {
                int i4 = this.mTransformDuration;
                if (i4 <= 0) {
                    this.mProgressState = 3;
                    this.mLastProgressStateTime = curTime;
                } else {
                    float value2 = ((float) (curTime - this.mLastProgressStateTime)) / ((float) i4);
                    this.mLineWidth = (1.0f - this.mTransformInterpolator.getInterpolation(value2)) * ((float) this.mStrokeSize);
                    if (value2 > 1.0f) {
                        this.mLineWidth = 0.0f;
                        this.mProgressState = 3;
                        this.mLastProgressStateTime = curTime;
                    }
                }
            } else if (i == 3 && curTime - this.mLastProgressStateTime > ((long) this.mKeepDuration)) {
                this.mProgressState = 0;
                this.mLastProgressStateTime = curTime;
            }
        } else if (curTime - this.mLastProgressStateTime > ((long) this.mKeepDuration)) {
            this.mProgressState = 2;
            this.mLastProgressStateTime = curTime;
        }
        int i5 = this.mRunState;
        if (i5 == 1) {
            if (curTime - this.mLastRunStateTime > ((long) this.mInAnimationDuration)) {
                this.mRunState = 3;
            }
        } else if (i5 == 4 && curTime - this.mLastRunStateTime > ((long) this.mOutAnimationDuration)) {
            stop(false);
            return;
        }
        if (isRunning()) {
            scheduleSelf(this.mUpdater, SystemClock.uptimeMillis() + 16);
        }
        invalidateSelf();
    }

    private void updateQuery() {
        long curTime = SystemClock.uptimeMillis();
        int i = this.mTravelDuration;
        float f = ((float) (curTime - this.mLastProgressStateTime)) / ((float) i);
        this.mAnimTime = f;
        boolean requestUpdate = this.mRunState == 4 || this.mProgressPercent == 0.0f || f < 1.0f;
        if (f > 1.0f) {
            this.mLastProgressStateTime = (long) Math.round(((float) curTime) - ((f - 1.0f) * ((float) i)));
            this.mAnimTime -= 1.0f;
        }
        if (requestUpdate && this.mRunState != 4) {
            int width = getBounds().width();
            int i2 = this.mMaxLineWidth;
            float maxWidth = i2 == 0 ? ((float) width) * this.mMaxLineWidthPercent : (float) i2;
            int i3 = this.mMinLineWidth;
            float minWidth = i3 == 0 ? ((float) width) * this.mMinLineWidthPercent : (float) i3;
            float interpolation = (this.mTransformInterpolator.getInterpolation(this.mAnimTime) * (minWidth - maxWidth)) + maxWidth;
            this.mLineWidth = interpolation;
            boolean z = this.mReverse;
            if (z) {
                this.mLineWidth = -interpolation;
            }
            this.mStartLine = z ? this.mTransformInterpolator.getInterpolation(this.mAnimTime) * (((float) width) + minWidth) : ((1.0f - this.mTransformInterpolator.getInterpolation(this.mAnimTime)) * (((float) width) + minWidth)) - minWidth;
        }
        int i4 = this.mRunState;
        if (i4 == 1) {
            if (curTime - this.mLastRunStateTime > ((long) this.mInAnimationDuration)) {
                this.mRunState = 3;
            }
        } else if (i4 == 4 && curTime - this.mLastRunStateTime > ((long) this.mOutAnimationDuration)) {
            stop(false);
            return;
        }
        if (isRunning()) {
            if (requestUpdate) {
                scheduleSelf(this.mUpdater, SystemClock.uptimeMillis() + 16);
            } else if (this.mRunState == 3) {
                this.mRunState = 2;
            }
        }
        invalidateSelf();
    }

    public static class Builder {
        private int mInAnimationDuration;
        private int mKeepDuration;
        private int mMaxLineWidth;
        private float mMaxLineWidthPercent;
        private int mMinLineWidth;
        private float mMinLineWidthPercent;
        private int mOutAnimationDuration;
        private int mProgressMode;
        private float mProgressPercent;
        private boolean mReverse;
        private float mSecondaryProgressPercent;
        private int[] mStrokeColors;
        private int mStrokeSecondaryColor;
        private int mStrokeSize;
        private int mTransformDuration;
        private Interpolator mTransformInterpolator;
        private int mTravelDuration;
        private int mVerticalAlign;

        public Builder() {
            this.mProgressPercent = 0.0f;
            this.mSecondaryProgressPercent = 0.0f;
            this.mStrokeSize = 8;
            this.mVerticalAlign = 2;
            this.mReverse = false;
            this.mTravelDuration = 1000;
            this.mTransformDuration = LogSeverity.EMERGENCY_VALUE;
            this.mKeepDuration = 200;
            this.mProgressMode = 1;
            this.mInAnimationDuration = 400;
            this.mOutAnimationDuration = 400;
        }

        public Builder(Context context, int defStyleRes) {
            this(context, (AttributeSet) null, 0, defStyleRes);
        }

        public Builder(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
            this.mProgressPercent = 0.0f;
            this.mSecondaryProgressPercent = 0.0f;
            this.mStrokeSize = 8;
            this.mVerticalAlign = 2;
            this.mReverse = false;
            this.mTravelDuration = 1000;
            this.mTransformDuration = LogSeverity.EMERGENCY_VALUE;
            this.mKeepDuration = 200;
            this.mProgressMode = 1;
            this.mInAnimationDuration = 400;
            this.mOutAnimationDuration = 400;
            TypedArray a = context.obtainStyledAttributes(attrs, C2500R.styleable.LinearProgressDrawable, defStyleAttr, defStyleRes);
            progressPercent(a.getFloat(C2500R.styleable.LinearProgressDrawable_pv_progress, 0.0f));
            secondaryProgressPercent(a.getFloat(C2500R.styleable.LinearProgressDrawable_pv_secondaryProgress, 0.0f));
            TypedValue value = a.peekValue(C2500R.styleable.LinearProgressDrawable_lpd_maxLineWidth);
            if (value == null) {
                maxLineWidth(0.75f);
            } else if (value.type == 6) {
                maxLineWidth(a.getFraction(C2500R.styleable.LinearProgressDrawable_lpd_maxLineWidth, 1, 1, 0.75f));
            } else {
                maxLineWidth(a.getDimensionPixelSize(C2500R.styleable.LinearProgressDrawable_lpd_maxLineWidth, 0));
            }
            TypedValue value2 = a.peekValue(C2500R.styleable.LinearProgressDrawable_lpd_minLineWidth);
            if (value2 == null) {
                minLineWidth(0.25f);
            } else if (value2.type == 6) {
                minLineWidth(a.getFraction(C2500R.styleable.LinearProgressDrawable_lpd_minLineWidth, 1, 1, 0.25f));
            } else {
                minLineWidth(a.getDimensionPixelSize(C2500R.styleable.LinearProgressDrawable_lpd_minLineWidth, 0));
            }
            strokeSize(a.getDimensionPixelSize(C2500R.styleable.LinearProgressDrawable_lpd_strokeSize, ThemeUtil.dpToPx(context, 4)));
            verticalAlign(a.getInteger(C2500R.styleable.LinearProgressDrawable_lpd_verticalAlign, 2));
            strokeColors(a.getColor(C2500R.styleable.LinearProgressDrawable_lpd_strokeColor, ThemeUtil.colorPrimary(context, ViewCompat.MEASURED_STATE_MASK)));
            int resourceId = a.getResourceId(C2500R.styleable.LinearProgressDrawable_lpd_strokeColors, 0);
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
            strokeSecondaryColor(a.getColor(C2500R.styleable.LinearProgressDrawable_lpd_strokeSecondaryColor, 0));
            reverse(a.getBoolean(C2500R.styleable.LinearProgressDrawable_lpd_reverse, false));
            travelDuration(a.getInteger(C2500R.styleable.LinearProgressDrawable_lpd_travelDuration, context.getResources().getInteger(17694722)));
            transformDuration(a.getInteger(C2500R.styleable.LinearProgressDrawable_lpd_transformDuration, context.getResources().getInteger(17694721)));
            keepDuration(a.getInteger(C2500R.styleable.LinearProgressDrawable_lpd_keepDuration, context.getResources().getInteger(17694720)));
            int resourceId2 = a.getResourceId(C2500R.styleable.LinearProgressDrawable_lpd_transformInterpolator, 0);
            int resId2 = resourceId2;
            if (resourceId2 != 0) {
                transformInterpolator(AnimationUtils.loadInterpolator(context, resId2));
            }
            progressMode(a.getInteger(C2500R.styleable.LinearProgressDrawable_pv_progressMode, 1));
            inAnimDuration(a.getInteger(C2500R.styleable.LinearProgressDrawable_lpd_inAnimDuration, context.getResources().getInteger(17694721)));
            outAnimDuration(a.getInteger(C2500R.styleable.LinearProgressDrawable_lpd_outAnimDuration, context.getResources().getInteger(17694721)));
            a.recycle();
        }

        public LinearProgressDrawable build() {
            if (this.mStrokeColors == null) {
                this.mStrokeColors = new int[]{-16737793};
            }
            if (this.mTransformInterpolator == null) {
                this.mTransformInterpolator = new DecelerateInterpolator();
            }
            LinearProgressDrawable linearProgressDrawable = r2;
            LinearProgressDrawable linearProgressDrawable2 = new LinearProgressDrawable(this.mProgressPercent, this.mSecondaryProgressPercent, this.mMaxLineWidth, this.mMaxLineWidthPercent, this.mMinLineWidth, this.mMinLineWidthPercent, this.mStrokeSize, this.mVerticalAlign, this.mStrokeColors, this.mStrokeSecondaryColor, this.mReverse, this.mTravelDuration, this.mTransformDuration, this.mKeepDuration, this.mTransformInterpolator, this.mProgressMode, this.mInAnimationDuration, this.mOutAnimationDuration);
            return linearProgressDrawable;
        }

        public Builder secondaryProgressPercent(float percent) {
            this.mSecondaryProgressPercent = percent;
            return this;
        }

        public Builder progressPercent(float percent) {
            this.mProgressPercent = percent;
            return this;
        }

        public Builder maxLineWidth(int width) {
            this.mMaxLineWidth = width;
            return this;
        }

        public Builder maxLineWidth(float percent) {
            this.mMaxLineWidthPercent = Math.max(0.0f, Math.min(1.0f, percent));
            this.mMaxLineWidth = 0;
            return this;
        }

        public Builder minLineWidth(int width) {
            this.mMinLineWidth = width;
            return this;
        }

        public Builder minLineWidth(float percent) {
            this.mMinLineWidthPercent = Math.max(0.0f, Math.min(1.0f, percent));
            this.mMinLineWidth = 0;
            return this;
        }

        public Builder strokeSize(int strokeSize) {
            this.mStrokeSize = strokeSize;
            return this;
        }

        public Builder verticalAlign(int align) {
            this.mVerticalAlign = align;
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

        public Builder travelDuration(int duration) {
            this.mTravelDuration = duration;
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

        public Builder outAnimDuration(int duration) {
            this.mOutAnimationDuration = duration;
            return this;
        }
    }
}
