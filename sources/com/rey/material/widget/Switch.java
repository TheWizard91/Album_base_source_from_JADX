package com.rey.material.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RadialGradient;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.Checkable;
import androidx.core.view.ViewCompat;
import com.rey.material.C2500R;
import com.rey.material.app.ThemeManager;
import com.rey.material.drawable.RippleDrawable;
import com.rey.material.util.ColorUtil;
import com.rey.material.util.ThemeUtil;
import com.rey.material.util.ViewUtil;

public class Switch extends View implements Checkable, ThemeManager.OnThemeChangedListener {
    private static final int COLOR_SHADOW_END = 0;
    private static final int COLOR_SHADOW_START = 1275068416;
    private int mAnimDuration;
    private boolean mChecked = false;
    protected int mCurrentStyle = Integer.MIN_VALUE;
    private RectF mDrawRect;
    private float mFlingVelocity;
    private int mGravity = 16;
    private Interpolator mInterpolator;
    private boolean mIsRtl = false;
    private int mMaxAnimDuration = -1;
    private float mMemoX;
    private OnCheckedChangeListener mOnCheckedChangeListener;
    private Paint mPaint;
    private RippleManager mRippleManager;
    private boolean mRunning = false;
    private int mShadowOffset = -1;
    private Paint mShadowPaint;
    private Path mShadowPath;
    private int mShadowSize = -1;
    private float mStartPosition;
    private long mStartTime;
    private float mStartX;
    protected int mStyleId;
    private RectF mTempRect;
    private int[] mTempStates = new int[2];
    private ColorStateList mThumbColors;
    private float mThumbPosition;
    private int mThumbRadius = -1;
    private Paint.Cap mTrackCap = Paint.Cap.ROUND;
    private ColorStateList mTrackColors;
    private Path mTrackPath;
    private int mTrackSize = -1;
    private final Runnable mUpdater = new Runnable() {
        public void run() {
            Switch.this.update();
        }
    };

    public interface OnCheckedChangeListener {
        void onCheckedChanged(Switch switchR, boolean z);
    }

    public Switch(Context context) {
        super(context);
        init(context, (AttributeSet) null, 0, 0);
    }

    public Switch(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0, 0);
    }

    public Switch(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr, 0);
    }

    /* access modifiers changed from: protected */
    public void init(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        this.mPaint = new Paint(1);
        this.mDrawRect = new RectF();
        this.mTempRect = new RectF();
        this.mTrackPath = new Path();
        this.mFlingVelocity = (float) ViewConfiguration.get(context).getScaledMinimumFlingVelocity();
        applyStyle(context, attrs, defStyleAttr, defStyleRes);
        if (!isInEditMode()) {
            this.mStyleId = ThemeManager.getStyleId(context, attrs, defStyleAttr, defStyleRes);
        }
    }

    public void applyStyle(int resId) {
        ViewUtil.applyStyle(this, resId);
        applyStyle(getContext(), (AttributeSet) null, 0, resId);
    }

    /* access modifiers changed from: protected */
    public void applyStyle(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        int resId;
        getRippleManager().onCreate(this, context, attrs, defStyleAttr, defStyleRes);
        TypedArray a = context.obtainStyledAttributes(attrs, C2500R.styleable.Switch, defStyleAttr, defStyleRes);
        int count = a.getIndexCount();
        for (int i = 0; i < count; i++) {
            int attr = a.getIndex(i);
            if (attr == C2500R.styleable.Switch_sw_trackSize) {
                this.mTrackSize = a.getDimensionPixelSize(attr, 0);
            } else if (attr == C2500R.styleable.Switch_sw_trackColor) {
                this.mTrackColors = a.getColorStateList(attr);
            } else if (attr == C2500R.styleable.Switch_sw_trackCap) {
                int cap = a.getInteger(attr, 0);
                if (cap == 0) {
                    this.mTrackCap = Paint.Cap.BUTT;
                } else if (cap == 1) {
                    this.mTrackCap = Paint.Cap.ROUND;
                } else {
                    this.mTrackCap = Paint.Cap.SQUARE;
                }
            } else if (attr == C2500R.styleable.Switch_sw_thumbColor) {
                this.mThumbColors = a.getColorStateList(attr);
            } else if (attr == C2500R.styleable.Switch_sw_thumbRadius) {
                this.mThumbRadius = a.getDimensionPixelSize(attr, 0);
            } else if (attr == C2500R.styleable.Switch_sw_thumbElevation) {
                int dimensionPixelSize = a.getDimensionPixelSize(attr, 0);
                this.mShadowSize = dimensionPixelSize;
                this.mShadowOffset = dimensionPixelSize / 2;
            } else if (attr == C2500R.styleable.Switch_sw_animDuration) {
                this.mMaxAnimDuration = a.getInt(attr, 0);
            } else if (attr == C2500R.styleable.Switch_android_gravity) {
                this.mGravity = a.getInt(attr, 0);
            } else if (attr == C2500R.styleable.Switch_android_checked) {
                setCheckedImmediately(a.getBoolean(attr, this.mChecked));
            } else if (attr == C2500R.styleable.Switch_sw_interpolator && (resId = a.getResourceId(C2500R.styleable.Switch_sw_interpolator, 0)) != 0) {
                this.mInterpolator = AnimationUtils.loadInterpolator(context, resId);
            }
        }
        a.recycle();
        if (this.mTrackSize < 0) {
            this.mTrackSize = ThemeUtil.dpToPx(context, 2);
        }
        if (this.mThumbRadius < 0) {
            this.mThumbRadius = ThemeUtil.dpToPx(context, 8);
        }
        if (this.mShadowSize < 0) {
            int dpToPx = ThemeUtil.dpToPx(context, 2);
            this.mShadowSize = dpToPx;
            this.mShadowOffset = dpToPx / 2;
        }
        if (this.mMaxAnimDuration < 0) {
            this.mMaxAnimDuration = context.getResources().getInteger(17694721);
        }
        if (this.mInterpolator == null) {
            this.mInterpolator = new DecelerateInterpolator();
        }
        if (this.mTrackColors == null) {
            this.mTrackColors = new ColorStateList(new int[][]{new int[]{-16842912}, new int[]{16842912}}, new int[]{ColorUtil.getColor(ThemeUtil.colorControlNormal(context, ViewCompat.MEASURED_STATE_MASK), 0.5f), ColorUtil.getColor(ThemeUtil.colorControlActivated(context, ViewCompat.MEASURED_STATE_MASK), 0.5f)});
        }
        if (this.mThumbColors == null) {
            this.mThumbColors = new ColorStateList(new int[][]{new int[]{-16842912}, new int[]{16842912}}, new int[]{16448250, ThemeUtil.colorControlActivated(context, ViewCompat.MEASURED_STATE_MASK)});
        }
        this.mPaint.setStrokeCap(this.mTrackCap);
        buildShadow();
        invalidate();
    }

    public void onThemeChanged(ThemeManager.OnThemeChangedEvent event) {
        int style = ThemeManager.getInstance().getCurrentStyle(this.mStyleId);
        if (this.mCurrentStyle != style) {
            this.mCurrentStyle = style;
            applyStyle(style);
        }
    }

    /* access modifiers changed from: protected */
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (this.mStyleId != 0) {
            ThemeManager.getInstance().registerOnThemeChangedListener(this);
            onThemeChanged((ThemeManager.OnThemeChangedEvent) null);
        }
    }

    /* access modifiers changed from: protected */
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        RippleManager.cancelRipple(this);
        if (this.mStyleId != 0) {
            ThemeManager.getInstance().unregisterOnThemeChangedListener(this);
        }
    }

    public void setBackgroundDrawable(Drawable drawable) {
        Drawable background = getBackground();
        if (!(background instanceof RippleDrawable) || (drawable instanceof RippleDrawable)) {
            super.setBackgroundDrawable(drawable);
        } else {
            ((RippleDrawable) background).setBackgroundDrawable(drawable);
        }
    }

    /* access modifiers changed from: protected */
    public RippleManager getRippleManager() {
        if (this.mRippleManager == null) {
            synchronized (RippleManager.class) {
                if (this.mRippleManager == null) {
                    this.mRippleManager = new RippleManager();
                }
            }
        }
        return this.mRippleManager;
    }

    public void setOnClickListener(View.OnClickListener l) {
        RippleManager rippleManager = getRippleManager();
        if (l == rippleManager) {
            super.setOnClickListener(l);
            return;
        }
        rippleManager.setOnClickListener(l);
        setOnClickListener(rippleManager);
    }

    public void setOnCheckedChangeListener(OnCheckedChangeListener listener) {
        this.mOnCheckedChangeListener = listener;
    }

    public void setChecked(boolean checked) {
        if (this.mChecked != checked) {
            this.mChecked = checked;
            OnCheckedChangeListener onCheckedChangeListener = this.mOnCheckedChangeListener;
            if (onCheckedChangeListener != null) {
                onCheckedChangeListener.onCheckedChanged(this, checked);
            }
        }
        if (this.mThumbPosition != (this.mChecked ? 1.0f : 0.0f)) {
            startAnimation();
        }
    }

    public void setCheckedImmediately(boolean checked) {
        if (this.mChecked != checked) {
            this.mChecked = checked;
            OnCheckedChangeListener onCheckedChangeListener = this.mOnCheckedChangeListener;
            if (onCheckedChangeListener != null) {
                onCheckedChangeListener.onCheckedChanged(this, checked);
            }
        }
        this.mThumbPosition = this.mChecked ? 1.0f : 0.0f;
        invalidate();
    }

    public boolean isChecked() {
        return this.mChecked;
    }

    public void toggle() {
        if (isEnabled()) {
            setChecked(!this.mChecked);
        }
    }

    public void onRtlPropertiesChanged(int layoutDirection) {
        boolean rtl = true;
        if (layoutDirection != 1) {
            rtl = false;
        }
        if (this.mIsRtl != rtl) {
            this.mIsRtl = rtl;
            invalidate();
        }
    }

    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);
        getRippleManager().onTouchEvent(this, event);
        float x = event.getX();
        if (this.mIsRtl) {
            x = (this.mDrawRect.centerX() * 2.0f) - x;
        }
        int action = event.getAction();
        if (action != 0) {
            boolean z = false;
            if (action == 1) {
                if (getParent() != null) {
                    getParent().requestDisallowInterceptTouchEvent(false);
                }
                float velocity = ((x - this.mStartX) / ((float) (SystemClock.uptimeMillis() - this.mStartTime))) * 1000.0f;
                if (Math.abs(velocity) >= this.mFlingVelocity) {
                    if (velocity > 0.0f) {
                        z = true;
                    }
                    setChecked(z);
                } else {
                    boolean z2 = this.mChecked;
                    if ((z2 || this.mThumbPosition >= 0.1f) && (!z2 || this.mThumbPosition <= 0.9f)) {
                        if (this.mThumbPosition > 0.5f) {
                            z = true;
                        }
                        setChecked(z);
                    } else {
                        toggle();
                    }
                }
            } else if (action == 2) {
                this.mThumbPosition = Math.min(1.0f, Math.max(0.0f, this.mThumbPosition + ((x - this.mMemoX) / (this.mDrawRect.width() - ((float) (this.mThumbRadius * 2))))));
                this.mMemoX = x;
                invalidate();
            } else if (action == 3) {
                if (getParent() != null) {
                    getParent().requestDisallowInterceptTouchEvent(false);
                }
                if (this.mThumbPosition > 0.5f) {
                    z = true;
                }
                setChecked(z);
            }
        } else {
            if (getParent() != null) {
                getParent().requestDisallowInterceptTouchEvent(true);
            }
            this.mMemoX = x;
            this.mStartX = x;
            this.mStartTime = SystemClock.uptimeMillis();
        }
        return true;
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthSize = View.MeasureSpec.getSize(widthMeasureSpec);
        int widthMode = View.MeasureSpec.getMode(widthMeasureSpec);
        int heightSize = View.MeasureSpec.getSize(heightMeasureSpec);
        int heightMode = View.MeasureSpec.getMode(heightMeasureSpec);
        if (widthMode == Integer.MIN_VALUE) {
            widthSize = Math.min(widthSize, getSuggestedMinimumWidth());
        } else if (widthMode == 0) {
            widthSize = getSuggestedMinimumWidth();
        }
        if (heightMode == Integer.MIN_VALUE) {
            heightSize = Math.min(heightSize, getSuggestedMinimumHeight());
        } else if (heightMode == 0) {
            heightSize = getSuggestedMinimumHeight();
        }
        setMeasuredDimension(widthSize, heightSize);
    }

    public int getSuggestedMinimumWidth() {
        return (this.mThumbRadius * 4) + Math.max(this.mShadowSize, getPaddingLeft()) + Math.max(this.mShadowSize, getPaddingRight());
    }

    public int getSuggestedMinimumHeight() {
        return (this.mThumbRadius * 2) + Math.max(this.mShadowSize - this.mShadowOffset, getPaddingTop()) + Math.max(this.mShadowSize + this.mShadowOffset, getPaddingBottom());
    }

    /* access modifiers changed from: protected */
    public void onSizeChanged(int w, int h, int oldw, int oldh) {
        this.mDrawRect.left = (float) Math.max(this.mShadowSize, getPaddingLeft());
        this.mDrawRect.right = (float) (w - Math.max(this.mShadowSize, getPaddingRight()));
        int height = this.mThumbRadius * 2;
        int align = this.mGravity & 112;
        if (align == 48) {
            this.mDrawRect.top = (float) Math.max(this.mShadowSize - this.mShadowOffset, getPaddingTop());
            RectF rectF = this.mDrawRect;
            rectF.bottom = rectF.top + ((float) height);
        } else if (align != 80) {
            this.mDrawRect.top = ((float) (h - height)) / 2.0f;
            RectF rectF2 = this.mDrawRect;
            rectF2.bottom = rectF2.top + ((float) height);
        } else {
            this.mDrawRect.bottom = (float) (h - Math.max(this.mShadowSize + this.mShadowOffset, getPaddingBottom()));
            RectF rectF3 = this.mDrawRect;
            rectF3.top = rectF3.bottom - ((float) height);
        }
    }

    private int getTrackColor(boolean checked) {
        this.mTempStates[0] = isEnabled() ? 16842910 : -16842910;
        int[] iArr = this.mTempStates;
        iArr[1] = checked ? 16842912 : -16842912;
        return this.mTrackColors.getColorForState(iArr, 0);
    }

    private int getThumbColor(boolean checked) {
        this.mTempStates[0] = isEnabled() ? 16842910 : -16842910;
        int[] iArr = this.mTempStates;
        iArr[1] = checked ? 16842912 : -16842912;
        return this.mThumbColors.getColorForState(iArr, 0);
    }

    private void buildShadow() {
        if (this.mShadowSize > 0) {
            if (this.mShadowPaint == null) {
                Paint paint = new Paint(5);
                this.mShadowPaint = paint;
                paint.setStyle(Paint.Style.FILL);
                this.mShadowPaint.setDither(true);
            }
            int i = this.mThumbRadius;
            float startRatio = ((float) i) / ((float) ((i + this.mShadowSize) + this.mShadowOffset));
            this.mShadowPaint.setShader(new RadialGradient(0.0f, 0.0f, (float) (this.mThumbRadius + this.mShadowSize), new int[]{COLOR_SHADOW_START, COLOR_SHADOW_START, 0}, new float[]{0.0f, startRatio, 1.0f}, Shader.TileMode.CLAMP));
            Path path = this.mShadowPath;
            if (path == null) {
                Path path2 = new Path();
                this.mShadowPath = path2;
                path2.setFillType(Path.FillType.EVEN_ODD);
            } else {
                path.reset();
            }
            float radius = (float) (this.mThumbRadius + this.mShadowSize);
            this.mTempRect.set(-radius, -radius, radius, radius);
            this.mShadowPath.addOval(this.mTempRect, Path.Direction.CW);
            float radius2 = (float) (this.mThumbRadius - 1);
            int i2 = this.mShadowOffset;
            this.mTempRect.set(-radius2, (-radius2) - ((float) i2), radius2, radius2 - ((float) i2));
            this.mShadowPath.addOval(this.mTempRect, Path.Direction.CW);
        }
    }

    private void getTrackPath(float x, float y, float radius) {
        float f = y;
        float halfStroke = ((float) this.mTrackSize) / 2.0f;
        this.mTrackPath.reset();
        if (this.mTrackCap != Paint.Cap.ROUND) {
            this.mTempRect.set((x - radius) + 1.0f, (f - radius) + 1.0f, (x + radius) - 1.0f, (f + radius) - 1.0f);
            float angle = (float) ((Math.asin((double) (halfStroke / (radius - 1.0f))) / 3.141592653589793d) * 180.0d);
            if (x - radius > this.mDrawRect.left) {
                this.mTrackPath.moveTo(this.mDrawRect.left, f - halfStroke);
                this.mTrackPath.arcTo(this.mTempRect, 180.0f + angle, (-angle) * 2.0f);
                this.mTrackPath.lineTo(this.mDrawRect.left, f + halfStroke);
                this.mTrackPath.close();
            }
            if (x + radius < this.mDrawRect.right) {
                this.mTrackPath.moveTo(this.mDrawRect.right, f - halfStroke);
                this.mTrackPath.arcTo(this.mTempRect, -angle, 2.0f * angle);
                this.mTrackPath.lineTo(this.mDrawRect.right, f + halfStroke);
                this.mTrackPath.close();
                return;
            }
            return;
        }
        float angle2 = (float) ((Math.asin((double) (halfStroke / (radius - 1.0f))) / 3.141592653589793d) * 180.0d);
        if (x - radius > this.mDrawRect.left) {
            float angle22 = (float) ((Math.acos((double) Math.max(0.0f, (((this.mDrawRect.left + halfStroke) - x) + radius) / halfStroke)) / 3.141592653589793d) * 180.0d);
            this.mTempRect.set(this.mDrawRect.left, f - halfStroke, this.mDrawRect.left + ((float) this.mTrackSize), f + halfStroke);
            this.mTrackPath.arcTo(this.mTempRect, 180.0f - angle22, angle22 * 2.0f);
            this.mTempRect.set((x - radius) + 1.0f, (f - radius) + 1.0f, (x + radius) - 1.0f, (f + radius) - 1.0f);
            this.mTrackPath.arcTo(this.mTempRect, 180.0f + angle2, (-angle2) * 2.0f);
            this.mTrackPath.close();
        }
        if (x + radius < this.mDrawRect.right) {
            float angle23 = (float) Math.acos((double) Math.max(0.0f, (((x + radius) - this.mDrawRect.right) + halfStroke) / halfStroke));
            this.mTrackPath.moveTo((float) (((double) (this.mDrawRect.right - halfStroke)) + (Math.cos((double) angle23) * ((double) halfStroke))), (float) (((double) f) + (Math.sin((double) angle23) * ((double) halfStroke))));
            float angle24 = (float) ((((double) angle23) / 3.141592653589793d) * 180.0d);
            this.mTempRect.set(this.mDrawRect.right - ((float) this.mTrackSize), f - halfStroke, this.mDrawRect.right, f + halfStroke);
            this.mTrackPath.arcTo(this.mTempRect, angle24, (-angle24) * 2.0f);
            this.mTempRect.set((x - radius) + 1.0f, (f - radius) + 1.0f, (x + radius) - 1.0f, (f + radius) - 1.0f);
            float angle3 = angle2;
            this.mTrackPath.arcTo(this.mTempRect, -angle3, 2.0f * angle3);
            this.mTrackPath.close();
            return;
        }
    }

    public void draw(Canvas canvas) {
        super.draw(canvas);
        float x = ((this.mDrawRect.width() - ((float) (this.mThumbRadius * 2))) * this.mThumbPosition) + this.mDrawRect.left + ((float) this.mThumbRadius);
        if (this.mIsRtl) {
            x = (this.mDrawRect.centerX() * 2.0f) - x;
        }
        float y = this.mDrawRect.centerY();
        getTrackPath(x, y, (float) this.mThumbRadius);
        this.mPaint.setColor(ColorUtil.getMiddleColor(getTrackColor(false), getTrackColor(true), this.mThumbPosition));
        this.mPaint.setStyle(Paint.Style.FILL);
        canvas.drawPath(this.mTrackPath, this.mPaint);
        if (this.mShadowSize > 0) {
            int saveCount = canvas.save();
            canvas.translate(x, ((float) this.mShadowOffset) + y);
            canvas.drawPath(this.mShadowPath, this.mShadowPaint);
            canvas.restoreToCount(saveCount);
        }
        this.mPaint.setColor(ColorUtil.getMiddleColor(getThumbColor(false), getThumbColor(true), this.mThumbPosition));
        this.mPaint.setStyle(Paint.Style.FILL);
        canvas.drawCircle(x, y, (float) this.mThumbRadius, this.mPaint);
    }

    private void resetAnimation() {
        this.mStartTime = SystemClock.uptimeMillis();
        float f = this.mThumbPosition;
        this.mStartPosition = f;
        float f2 = (float) this.mMaxAnimDuration;
        if (this.mChecked) {
            f = 1.0f - f;
        }
        this.mAnimDuration = (int) (f2 * f);
    }

    private void startAnimation() {
        if (getHandler() != null) {
            resetAnimation();
            this.mRunning = true;
            getHandler().postAtTime(this.mUpdater, SystemClock.uptimeMillis() + 16);
        } else {
            this.mThumbPosition = this.mChecked ? 1.0f : 0.0f;
        }
        invalidate();
    }

    private void stopAnimation() {
        this.mRunning = false;
        this.mThumbPosition = this.mChecked ? 1.0f : 0.0f;
        if (getHandler() != null) {
            getHandler().removeCallbacks(this.mUpdater);
        }
        invalidate();
    }

    /* access modifiers changed from: private */
    public void update() {
        float progress = Math.min(1.0f, ((float) (SystemClock.uptimeMillis() - this.mStartTime)) / ((float) this.mAnimDuration));
        float value = this.mInterpolator.getInterpolation(progress);
        this.mThumbPosition = this.mChecked ? (this.mStartPosition * (1.0f - value)) + value : this.mStartPosition * (1.0f - value);
        if (progress == 1.0f) {
            stopAnimation();
        }
        if (this.mRunning) {
            if (getHandler() != null) {
                getHandler().postAtTime(this.mUpdater, SystemClock.uptimeMillis() + 16);
            } else {
                stopAnimation();
            }
        }
        invalidate();
    }

    /* access modifiers changed from: protected */
    public Parcelable onSaveInstanceState() {
        SavedState ss = new SavedState(super.onSaveInstanceState());
        ss.checked = isChecked();
        return ss;
    }

    /* access modifiers changed from: protected */
    public void onRestoreInstanceState(Parcelable state) {
        SavedState ss = (SavedState) state;
        super.onRestoreInstanceState(ss.getSuperState());
        setChecked(ss.checked);
        requestLayout();
    }

    static class SavedState extends View.BaseSavedState {
        public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.Creator<SavedState>() {
            public SavedState createFromParcel(Parcel in) {
                return new SavedState(in);
            }

            public SavedState[] newArray(int size) {
                return new SavedState[size];
            }
        };
        boolean checked;

        SavedState(Parcelable superState) {
            super(superState);
        }

        private SavedState(Parcel in) {
            super(in);
            this.checked = ((Boolean) in.readValue((ClassLoader) null)).booleanValue();
        }

        public void writeToParcel(Parcel out, int flags) {
            super.writeToParcel(out, flags);
            out.writeValue(Boolean.valueOf(this.checked));
        }

        public String toString() {
            return "Switch.SavedState{" + Integer.toHexString(System.identityHashCode(this)) + " checked=" + this.checked + "}";
        }
    }
}
