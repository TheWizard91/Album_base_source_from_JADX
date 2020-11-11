package com.rey.material.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
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
import androidx.core.view.ViewCompat;
import com.rey.material.C2500R;
import com.rey.material.app.ThemeManager;
import com.rey.material.drawable.RippleDrawable;
import com.rey.material.util.ColorUtil;
import com.rey.material.util.ThemeUtil;
import com.rey.material.util.TypefaceUtil;
import com.rey.material.util.ViewUtil;

public class Slider extends View implements ThemeManager.OnThemeChangedListener {
    /* access modifiers changed from: private */
    public boolean mAlwaysFillThumb = false;
    private int mBaselineOffset;
    protected int mCurrentStyle = Integer.MIN_VALUE;
    /* access modifiers changed from: private */
    public boolean mDiscreteMode = false;
    private RectF mDrawRect;
    private int mGravity = 17;
    /* access modifiers changed from: private */
    public Interpolator mInterpolator;
    /* access modifiers changed from: private */
    public boolean mIsDragging;
    private boolean mIsRtl = false;
    private Path mLeftTrackPath;
    private Path mMarkPath;
    private int mMaxValue = 100;
    private PointF mMemoPoint;
    private int mMemoValue;
    private int mMinValue = 0;
    private OnPositionChangeListener mOnPositionChangeListener;
    private Paint mPaint;
    private int mPrimaryColor;
    private Path mRightTrackPath;
    private RippleManager mRippleManager;
    private int mSecondaryColor;
    private int mStepValue = 1;
    protected int mStyleId;
    private RectF mTempRect;
    private int mTextColor = -1;
    private int mTextHeight;
    private int mTextSize = -1;
    /* access modifiers changed from: private */
    public int mThumbBorderSize = -1;
    /* access modifiers changed from: private */
    public float mThumbCurrentRadius;
    /* access modifiers changed from: private */
    public float mThumbFillPercent;
    private int mThumbFocusRadius = -1;
    private ThumbMoveAnimator mThumbMoveAnimator;
    /* access modifiers changed from: private */
    public float mThumbPosition = -1.0f;
    /* access modifiers changed from: private */
    public int mThumbRadius = -1;
    private ThumbRadiusAnimator mThumbRadiusAnimator;
    private ThumbStrokeAnimator mThumbStrokeAnimator;
    private int mThumbTouchRadius = -1;
    private int mTouchSlop;
    private Paint.Cap mTrackCap = Paint.Cap.BUTT;
    private int mTrackSize = -1;
    /* access modifiers changed from: private */
    public int mTransformAnimationDuration = -1;
    /* access modifiers changed from: private */
    public int mTravelAnimationDuration = -1;
    private Typeface mTypeface = Typeface.DEFAULT;
    private ValueDescriptionProvider mValueDescriptionProvider;
    private String mValueText;

    public interface OnPositionChangeListener {
        void onPositionChanged(Slider slider, boolean z, float f, float f2, int i, int i2);
    }

    public interface ValueDescriptionProvider {
        String getDescription(int i);
    }

    public Slider(Context context) {
        super(context);
        init(context, (AttributeSet) null, 0, 0);
    }

    public Slider(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0, 0);
    }

    public Slider(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr, 0);
    }

    /* access modifiers changed from: protected */
    public void init(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        this.mPaint = new Paint(1);
        this.mPrimaryColor = ThemeUtil.colorControlActivated(context, ViewCompat.MEASURED_STATE_MASK);
        this.mSecondaryColor = ThemeUtil.colorControlNormal(context, ViewCompat.MEASURED_STATE_MASK);
        this.mDrawRect = new RectF();
        this.mTempRect = new RectF();
        this.mLeftTrackPath = new Path();
        this.mRightTrackPath = new Path();
        this.mThumbRadiusAnimator = new ThumbRadiusAnimator();
        this.mThumbStrokeAnimator = new ThumbStrokeAnimator();
        this.mThumbMoveAnimator = new ThumbMoveAnimator();
        this.mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        this.mMemoPoint = new PointF();
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
        boolean z;
        Context context2 = context;
        getRippleManager().onCreate(this, context, attrs, defStyleAttr, defStyleRes);
        TypedArray a = context2.obtainStyledAttributes(attrs, C2500R.styleable.Slider, defStyleAttr, defStyleRes);
        int minValue = getMinValue();
        int maxValue = getMaxValue();
        boolean valueRangeDefined = false;
        int value = -1;
        boolean valueDefined = false;
        String familyName = null;
        int style = 0;
        boolean textStyleDefined = false;
        int i = 0;
        int count = a.getIndexCount();
        while (i < count) {
            int attr = a.getIndex(i);
            if (attr == C2500R.styleable.Slider_sl_discreteMode) {
                this.mDiscreteMode = a.getBoolean(attr, false);
            } else if (attr == C2500R.styleable.Slider_sl_primaryColor) {
                this.mPrimaryColor = a.getColor(attr, 0);
            } else if (attr == C2500R.styleable.Slider_sl_secondaryColor) {
                this.mSecondaryColor = a.getColor(attr, 0);
            } else if (attr == C2500R.styleable.Slider_sl_trackSize) {
                this.mTrackSize = a.getDimensionPixelSize(attr, 0);
            } else if (attr == C2500R.styleable.Slider_sl_trackCap) {
                int cap = a.getInteger(attr, 0);
                if (cap == 0) {
                    this.mTrackCap = Paint.Cap.BUTT;
                } else if (cap == 1) {
                    this.mTrackCap = Paint.Cap.ROUND;
                } else {
                    this.mTrackCap = Paint.Cap.SQUARE;
                }
            } else if (attr == C2500R.styleable.Slider_sl_thumbBorderSize) {
                this.mThumbBorderSize = a.getDimensionPixelSize(attr, 0);
            } else if (attr == C2500R.styleable.Slider_sl_thumbRadius) {
                this.mThumbRadius = a.getDimensionPixelSize(attr, 0);
            } else if (attr == C2500R.styleable.Slider_sl_thumbFocusRadius) {
                this.mThumbFocusRadius = a.getDimensionPixelSize(attr, 0);
            } else if (attr == C2500R.styleable.Slider_sl_thumbTouchRadius) {
                this.mThumbTouchRadius = a.getDimensionPixelSize(attr, 0);
            } else if (attr == C2500R.styleable.Slider_sl_travelAnimDuration) {
                int integer = a.getInteger(attr, 0);
                this.mTravelAnimationDuration = integer;
                this.mTransformAnimationDuration = integer;
            } else if (attr == C2500R.styleable.Slider_sl_alwaysFillThumb) {
                this.mAlwaysFillThumb = a.getBoolean(C2500R.styleable.Slider_sl_alwaysFillThumb, false);
            } else if (attr == C2500R.styleable.Slider_sl_interpolator) {
                this.mInterpolator = AnimationUtils.loadInterpolator(context2, a.getResourceId(C2500R.styleable.Slider_sl_interpolator, 0));
            } else if (attr == C2500R.styleable.Slider_android_gravity) {
                this.mGravity = a.getInteger(attr, 0);
            } else if (attr == C2500R.styleable.Slider_sl_minValue) {
                minValue = a.getInteger(attr, 0);
                valueRangeDefined = true;
            } else if (attr == C2500R.styleable.Slider_sl_maxValue) {
                maxValue = a.getInteger(attr, 0);
                valueRangeDefined = true;
            } else if (attr == C2500R.styleable.Slider_sl_stepValue) {
                this.mStepValue = a.getInteger(attr, 0);
            } else if (attr == C2500R.styleable.Slider_sl_value) {
                value = a.getInteger(attr, 0);
                valueDefined = true;
            } else if (attr == C2500R.styleable.Slider_sl_fontFamily) {
                familyName = a.getString(attr);
                textStyleDefined = true;
            } else if (attr == C2500R.styleable.Slider_sl_textStyle) {
                style = a.getInteger(attr, 0);
                textStyleDefined = true;
            } else if (attr == C2500R.styleable.Slider_sl_textColor) {
                this.mTextColor = a.getColor(attr, 0);
            } else if (attr == C2500R.styleable.Slider_sl_textSize) {
                this.mTextSize = a.getDimensionPixelSize(attr, 0);
            } else if (attr == C2500R.styleable.Slider_android_enabled) {
                setEnabled(a.getBoolean(attr, true));
            } else if (attr == C2500R.styleable.Slider_sl_baselineOffset) {
                this.mBaselineOffset = a.getDimensionPixelOffset(attr, 0);
            }
            i++;
            AttributeSet attributeSet = attrs;
            int i2 = defStyleAttr;
            int i3 = defStyleRes;
        }
        a.recycle();
        if (this.mTrackSize < 0) {
            this.mTrackSize = ThemeUtil.dpToPx(context2, 2);
        }
        if (this.mThumbBorderSize < 0) {
            this.mThumbBorderSize = ThemeUtil.dpToPx(context2, 2);
        }
        if (this.mThumbRadius < 0) {
            this.mThumbRadius = ThemeUtil.dpToPx(context2, 10);
        }
        if (this.mThumbFocusRadius < 0) {
            this.mThumbFocusRadius = ThemeUtil.dpToPx(context2, 14);
        }
        if (this.mTravelAnimationDuration < 0) {
            int integer2 = context.getResources().getInteger(17694721);
            this.mTravelAnimationDuration = integer2;
            this.mTransformAnimationDuration = integer2;
        }
        if (this.mInterpolator == null) {
            this.mInterpolator = new DecelerateInterpolator();
        }
        if (valueRangeDefined) {
            z = false;
            setValueRange(minValue, maxValue, false);
        } else {
            z = false;
        }
        if (valueDefined) {
            setValue((float) value, z);
        } else if (this.mThumbPosition < 0.0f) {
            setValue((float) this.mMinValue, z);
        }
        if (textStyleDefined) {
            this.mTypeface = TypefaceUtil.load(context2, familyName, style);
        }
        if (this.mTextSize < 0) {
            this.mTextSize = context.getResources().getDimensionPixelOffset(C2500R.dimen.abc_text_size_small_material);
        }
        this.mPaint.setTextSize((float) this.mTextSize);
        this.mPaint.setTextAlign(Paint.Align.CENTER);
        this.mPaint.setTypeface(this.mTypeface);
        measureText();
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

    private void measureText() {
        if (this.mValueText != null) {
            Rect temp = new Rect();
            this.mPaint.setTextSize((float) this.mTextSize);
            float width = this.mPaint.measureText(this.mValueText);
            float maxWidth = (float) (((((double) this.mThumbRadius) * Math.sqrt(2.0d)) * 2.0d) - ((double) ThemeUtil.dpToPx(getContext(), 8)));
            if (width > maxWidth) {
                this.mPaint.setTextSize((((float) this.mTextSize) * maxWidth) / width);
            }
            Paint paint = this.mPaint;
            String str = this.mValueText;
            paint.getTextBounds(str, 0, str.length(), temp);
            this.mTextHeight = temp.height();
        }
    }

    private String getValueText() {
        int value = getValue();
        if (this.mValueText == null || this.mMemoValue != value) {
            this.mMemoValue = value;
            ValueDescriptionProvider valueDescriptionProvider = this.mValueDescriptionProvider;
            this.mValueText = valueDescriptionProvider == null ? String.valueOf(value) : valueDescriptionProvider.getDescription(value);
            measureText();
        }
        return this.mValueText;
    }

    public int getMinValue() {
        return this.mMinValue;
    }

    public int getMaxValue() {
        return this.mMaxValue;
    }

    public int getStepValue() {
        return this.mStepValue;
    }

    public void setValueRange(int min, int max, boolean animation) {
        if (max < min) {
            return;
        }
        if (min != this.mMinValue || max != this.mMaxValue) {
            float oldValue = getExactValue();
            float oldPosition = getPosition();
            this.mMinValue = min;
            this.mMaxValue = max;
            setValue(oldValue, animation);
            if (this.mOnPositionChangeListener != null && oldPosition == getPosition() && oldValue != getExactValue()) {
                this.mOnPositionChangeListener.onPositionChanged(this, false, oldPosition, oldPosition, Math.round(oldValue), getValue());
            }
        }
    }

    public int getValue() {
        return Math.round(getExactValue());
    }

    public float getExactValue() {
        return (((float) (this.mMaxValue - this.mMinValue)) * getPosition()) + ((float) this.mMinValue);
    }

    public float getPosition() {
        return this.mThumbMoveAnimator.isRunning() ? this.mThumbMoveAnimator.getPosition() : this.mThumbPosition;
    }

    public void setPosition(float pos, boolean animation) {
        setPosition(pos, animation, animation, false);
    }

    private void setPosition(float pos, boolean moveAnimation, boolean transformAnimation, boolean fromUser) {
        OnPositionChangeListener onPositionChangeListener;
        float f = pos;
        int i = 1;
        boolean change = getPosition() != f;
        int oldValue = getValue();
        float oldPos = getPosition();
        if (!moveAnimation || !this.mThumbMoveAnimator.startAnimation(pos)) {
            this.mThumbPosition = f;
            float f2 = 0.0f;
            if (transformAnimation) {
                if (!this.mIsDragging) {
                    this.mThumbRadiusAnimator.startAnimation(this.mThumbRadius);
                }
                ThumbStrokeAnimator thumbStrokeAnimator = this.mThumbStrokeAnimator;
                if (f == 0.0f) {
                    i = 0;
                }
                thumbStrokeAnimator.startAnimation(i);
            } else {
                this.mThumbCurrentRadius = (float) this.mThumbRadius;
                if (this.mAlwaysFillThumb || f != 0.0f) {
                    f2 = 1.0f;
                }
                this.mThumbFillPercent = f2;
                invalidate();
            }
        }
        int newValue = getValue();
        float newPos = getPosition();
        if (change && (onPositionChangeListener = this.mOnPositionChangeListener) != null) {
            onPositionChangeListener.onPositionChanged(this, fromUser, oldPos, newPos, oldValue, newValue);
        }
    }

    public void setPrimaryColor(int color) {
        this.mPrimaryColor = color;
        invalidate();
    }

    public void setSecondaryColor(int color) {
        this.mSecondaryColor = color;
        invalidate();
    }

    public void setAlwaysFillThumb(boolean alwaysFillThumb) {
        this.mAlwaysFillThumb = alwaysFillThumb;
    }

    public void setValue(float value, boolean animation) {
        float value2 = Math.min((float) this.mMaxValue, Math.max(value, (float) this.mMinValue));
        int i = this.mMinValue;
        setPosition((value2 - ((float) i)) / ((float) (this.mMaxValue - i)), animation);
    }

    public void setOnPositionChangeListener(OnPositionChangeListener listener) {
        this.mOnPositionChangeListener = listener;
    }

    public void setValueDescriptionProvider(ValueDescriptionProvider provider) {
        this.mValueDescriptionProvider = provider;
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
        return ((this.mDiscreteMode ? (int) (((double) this.mThumbRadius) * Math.sqrt(2.0d)) : this.mThumbFocusRadius) * 4) + getPaddingLeft() + getPaddingRight();
    }

    public int getSuggestedMinimumHeight() {
        return (this.mDiscreteMode ? (int) (((double) this.mThumbRadius) * (Math.sqrt(2.0d) + 4.0d)) : this.mThumbFocusRadius * 2) + getPaddingTop() + getPaddingBottom();
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

    public int getBaseline() {
        int baseline;
        int align = this.mGravity & 112;
        if (this.mDiscreteMode) {
            int fullHeight = (int) (((double) this.mThumbRadius) * (Math.sqrt(2.0d) + 4.0d));
            int height = this.mThumbRadius * 2;
            if (align == 48) {
                baseline = Math.max(getPaddingTop(), fullHeight - height) + this.mThumbRadius;
            } else if (align != 80) {
                baseline = Math.round(Math.max(((float) (getMeasuredHeight() - height)) / 2.0f, (float) (fullHeight - height)) + ((float) this.mThumbRadius));
            } else {
                baseline = getMeasuredHeight() - getPaddingBottom();
            }
        } else {
            int height2 = this.mThumbFocusRadius * 2;
            if (align == 48) {
                baseline = getPaddingTop() + this.mThumbFocusRadius;
            } else if (align != 80) {
                baseline = Math.round((((float) (getMeasuredHeight() - height2)) / 2.0f) + ((float) this.mThumbFocusRadius));
            } else {
                baseline = getMeasuredHeight() - getPaddingBottom();
            }
        }
        return this.mBaselineOffset + baseline;
    }

    /* access modifiers changed from: protected */
    public void onSizeChanged(int w, int h, int oldw, int oldh) {
        this.mDrawRect.left = (float) (getPaddingLeft() + this.mThumbRadius);
        this.mDrawRect.right = (float) ((w - getPaddingRight()) - this.mThumbRadius);
        int align = this.mGravity & 112;
        if (this.mDiscreteMode) {
            int fullHeight = (int) (((double) this.mThumbRadius) * (Math.sqrt(2.0d) + 4.0d));
            int height = this.mThumbRadius * 2;
            if (align == 48) {
                this.mDrawRect.top = (float) Math.max(getPaddingTop(), fullHeight - height);
                RectF rectF = this.mDrawRect;
                rectF.bottom = rectF.top + ((float) height);
            } else if (align != 80) {
                this.mDrawRect.top = Math.max(((float) (h - height)) / 2.0f, (float) (fullHeight - height));
                RectF rectF2 = this.mDrawRect;
                rectF2.bottom = rectF2.top + ((float) height);
            } else {
                this.mDrawRect.bottom = (float) (h - getPaddingBottom());
                RectF rectF3 = this.mDrawRect;
                rectF3.top = rectF3.bottom - ((float) height);
            }
        } else {
            int height2 = this.mThumbFocusRadius * 2;
            if (align == 48) {
                this.mDrawRect.top = (float) getPaddingTop();
                RectF rectF4 = this.mDrawRect;
                rectF4.bottom = rectF4.top + ((float) height2);
            } else if (align != 80) {
                this.mDrawRect.top = ((float) (h - height2)) / 2.0f;
                RectF rectF5 = this.mDrawRect;
                rectF5.bottom = rectF5.top + ((float) height2);
            } else {
                this.mDrawRect.bottom = (float) (h - getPaddingBottom());
                RectF rectF6 = this.mDrawRect;
                rectF6.top = rectF6.bottom - ((float) height2);
            }
        }
    }

    private boolean isThumbHit(float x, float y, float radius) {
        float cx = (this.mDrawRect.width() * this.mThumbPosition) + this.mDrawRect.left;
        float cy = this.mDrawRect.centerY();
        return x >= cx - radius && x <= cx + radius && y >= cy - radius && y < cy + radius;
    }

    private double distance(float x1, float y1, float x2, float y2) {
        return Math.sqrt(Math.pow((double) (x1 - x2), 2.0d) + Math.pow((double) (y1 - y2), 2.0d));
    }

    private float correctPosition(float position) {
        if (!this.mDiscreteMode) {
            return position;
        }
        int totalOffset = this.mMaxValue - this.mMinValue;
        int valueOffset = Math.round(((float) totalOffset) * position);
        int i = this.mStepValue;
        int stepOffset = valueOffset / i;
        int lowerValue = stepOffset * i;
        int higherValue = Math.min(totalOffset, (stepOffset + 1) * i);
        if (valueOffset - lowerValue < higherValue - valueOffset) {
            return ((float) lowerValue) / ((float) totalOffset);
        }
        return ((float) higherValue) / ((float) totalOffset);
    }

    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);
        getRippleManager().onTouchEvent(this, event);
        int i = 0;
        if (!isEnabled()) {
            return false;
        }
        float x = event.getX();
        float y = event.getY();
        if (this.mIsRtl) {
            x = (this.mDrawRect.centerX() * 2.0f) - x;
        }
        int action = event.getAction();
        if (action == 0) {
            int i2 = this.mThumbTouchRadius;
            if (i2 <= 0) {
                i2 = this.mThumbRadius;
            }
            this.mIsDragging = isThumbHit(x, y, (float) i2) && !this.mThumbMoveAnimator.isRunning();
            this.mMemoPoint.set(x, y);
            if (this.mIsDragging) {
                ThumbRadiusAnimator thumbRadiusAnimator = this.mThumbRadiusAnimator;
                if (!this.mDiscreteMode) {
                    i = this.mThumbFocusRadius;
                }
                thumbRadiusAnimator.startAnimation(i);
                if (getParent() != null) {
                    getParent().requestDisallowInterceptTouchEvent(true);
                }
            }
        } else if (action != 1) {
            if (action != 2) {
                if (action == 3 && this.mIsDragging) {
                    this.mIsDragging = false;
                    setPosition(getPosition(), true, true, true);
                    if (getParent() != null) {
                        getParent().requestDisallowInterceptTouchEvent(false);
                    }
                }
            } else if (this.mIsDragging) {
                if (this.mDiscreteMode) {
                    setPosition(correctPosition(Math.min(1.0f, Math.max(0.0f, (x - this.mDrawRect.left) / this.mDrawRect.width()))), true, true, true);
                } else {
                    setPosition(Math.min(1.0f, Math.max(0.0f, this.mThumbPosition + ((x - this.mMemoPoint.x) / this.mDrawRect.width()))), false, true, true);
                    this.mMemoPoint.x = x;
                    invalidate();
                }
            }
        } else if (this.mIsDragging) {
            this.mIsDragging = false;
            setPosition(getPosition(), true, true, true);
            if (getParent() != null) {
                getParent().requestDisallowInterceptTouchEvent(false);
            }
        } else if (distance(this.mMemoPoint.x, this.mMemoPoint.y, x, y) <= ((double) this.mTouchSlop)) {
            setPosition(correctPosition(Math.min(1.0f, Math.max(0.0f, (x - this.mDrawRect.left) / this.mDrawRect.width()))), true, true, true);
        }
        return true;
    }

    private void getTrackPath(float x, float y, float radius) {
        float f = x;
        float f2 = y;
        float halfStroke = ((float) this.mTrackSize) / 2.0f;
        this.mLeftTrackPath.reset();
        this.mRightTrackPath.reset();
        if (radius - 1.0f < halfStroke) {
            if (this.mTrackCap != Paint.Cap.ROUND) {
                if (f > this.mDrawRect.left) {
                    this.mLeftTrackPath.moveTo(this.mDrawRect.left, f2 - halfStroke);
                    this.mLeftTrackPath.lineTo(f, f2 - halfStroke);
                    this.mLeftTrackPath.lineTo(f, f2 + halfStroke);
                    this.mLeftTrackPath.lineTo(this.mDrawRect.left, f2 + halfStroke);
                    this.mLeftTrackPath.close();
                }
                if (f < this.mDrawRect.right) {
                    this.mRightTrackPath.moveTo(this.mDrawRect.right, f2 + halfStroke);
                    this.mRightTrackPath.lineTo(f, f2 + halfStroke);
                    this.mRightTrackPath.lineTo(f, f2 - halfStroke);
                    this.mRightTrackPath.lineTo(this.mDrawRect.right, f2 - halfStroke);
                    this.mRightTrackPath.close();
                    return;
                }
                return;
            }
            if (f > this.mDrawRect.left) {
                this.mTempRect.set(this.mDrawRect.left, f2 - halfStroke, this.mDrawRect.left + ((float) this.mTrackSize), f2 + halfStroke);
                this.mLeftTrackPath.arcTo(this.mTempRect, 90.0f, 180.0f);
                this.mLeftTrackPath.lineTo(f, f2 - halfStroke);
                this.mLeftTrackPath.lineTo(f, f2 + halfStroke);
                this.mLeftTrackPath.close();
            }
            if (f < this.mDrawRect.right) {
                this.mTempRect.set(this.mDrawRect.right - ((float) this.mTrackSize), f2 - halfStroke, this.mDrawRect.right, f2 + halfStroke);
                this.mRightTrackPath.arcTo(this.mTempRect, 270.0f, 180.0f);
                this.mRightTrackPath.lineTo(f, f2 + halfStroke);
                this.mRightTrackPath.lineTo(f, f2 - halfStroke);
                this.mRightTrackPath.close();
            }
        } else if (this.mTrackCap != Paint.Cap.ROUND) {
            this.mTempRect.set((f - radius) + 1.0f, (f2 - radius) + 1.0f, (f + radius) - 1.0f, (f2 + radius) - 1.0f);
            float angle = (float) ((Math.asin((double) (halfStroke / (radius - 1.0f))) / 3.141592653589793d) * 180.0d);
            if (f - radius > this.mDrawRect.left) {
                this.mLeftTrackPath.moveTo(this.mDrawRect.left, f2 - halfStroke);
                this.mLeftTrackPath.arcTo(this.mTempRect, 180.0f + angle, (-angle) * 2.0f);
                this.mLeftTrackPath.lineTo(this.mDrawRect.left, f2 + halfStroke);
                this.mLeftTrackPath.close();
            }
            if (f + radius < this.mDrawRect.right) {
                this.mRightTrackPath.moveTo(this.mDrawRect.right, f2 - halfStroke);
                this.mRightTrackPath.arcTo(this.mTempRect, -angle, 2.0f * angle);
                this.mRightTrackPath.lineTo(this.mDrawRect.right, f2 + halfStroke);
                this.mRightTrackPath.close();
            }
        } else {
            float angle2 = (float) ((Math.asin((double) (halfStroke / (radius - 1.0f))) / 3.141592653589793d) * 180.0d);
            if (f - radius > this.mDrawRect.left) {
                float angle22 = (float) ((Math.acos((double) Math.max(0.0f, (((this.mDrawRect.left + halfStroke) - f) + radius) / halfStroke)) / 3.141592653589793d) * 180.0d);
                this.mTempRect.set(this.mDrawRect.left, f2 - halfStroke, this.mDrawRect.left + ((float) this.mTrackSize), f2 + halfStroke);
                this.mLeftTrackPath.arcTo(this.mTempRect, 180.0f - angle22, angle22 * 2.0f);
                this.mTempRect.set((f - radius) + 1.0f, (f2 - radius) + 1.0f, (f + radius) - 1.0f, (f2 + radius) - 1.0f);
                this.mLeftTrackPath.arcTo(this.mTempRect, 180.0f + angle2, (-angle2) * 2.0f);
                this.mLeftTrackPath.close();
            }
            if (f + radius < this.mDrawRect.right) {
                float angle23 = (float) Math.acos((double) Math.max(0.0f, (((f + radius) - this.mDrawRect.right) + halfStroke) / halfStroke));
                this.mRightTrackPath.moveTo((float) (((double) (this.mDrawRect.right - halfStroke)) + (Math.cos((double) angle23) * ((double) halfStroke))), (float) (((double) f2) + (Math.sin((double) angle23) * ((double) halfStroke))));
                float angle24 = (float) ((((double) angle23) / 3.141592653589793d) * 180.0d);
                this.mTempRect.set(this.mDrawRect.right - ((float) this.mTrackSize), f2 - halfStroke, this.mDrawRect.right, f2 + halfStroke);
                this.mRightTrackPath.arcTo(this.mTempRect, angle24, (-angle24) * 2.0f);
                this.mTempRect.set((f - radius) + 1.0f, (f2 - radius) + 1.0f, (f + radius) - 1.0f, (f2 + radius) - 1.0f);
                this.mRightTrackPath.arcTo(this.mTempRect, -angle2, 2.0f * angle2);
                this.mRightTrackPath.close();
            }
        }
    }

    private Path getMarkPath(Path path, float cx, float cy, float radius, float factor) {
        Path path2;
        if (path == null) {
            path2 = new Path();
        } else {
            path.reset();
            path2 = path;
        }
        float x1 = cx - radius;
        float y1 = cy;
        float x2 = cx + radius;
        float y2 = cy;
        float x3 = cx;
        float y3 = cy + radius;
        float nCx = cx;
        float nCy = cy - (radius * factor);
        float angle = (float) ((Math.atan2((double) (y2 - nCy), (double) (x2 - nCx)) * 180.0d) / 3.141592653589793d);
        float nRadius = (float) distance(nCx, nCy, x1, y1);
        float nCx2 = nCx;
        this.mTempRect.set(nCx - nRadius, nCy - nRadius, nCx + nRadius, nCy + nRadius);
        path2.moveTo(x1, y1);
        path2.arcTo(this.mTempRect, 180.0f - angle, (angle * 2.0f) + 180.0f);
        if (factor > 0.9f) {
            path2.lineTo(x3, y3);
            float f = nCx2;
            float f2 = x1;
            float f3 = y1;
            float f4 = x2;
            float f5 = y2;
            float y22 = angle;
            float y12 = nRadius;
            float nRadius2 = y3;
            float angle2 = nCy;
            float nCy2 = x3;
        } else {
            float x4 = (x2 + x3) / 2.0f;
            float y4 = (y2 + y3) / 2.0f;
            double d2 = distance(x2, y2, x4, y4) / Math.tan((((double) (1.0f - factor)) * 3.141592653589793d) / 4.0d);
            float f6 = nCy;
            float f7 = angle;
            float nCx3 = (float) (((double) x4) - (Math.cos(0.7853981633974483d) * d2));
            float f8 = nRadius;
            float nCy3 = (float) (((double) y4) - (Math.sin(0.7853981633974483d) * d2));
            float f9 = y4;
            double d = d2;
            float angle3 = (float) ((Math.atan2((double) (y2 - nCy3), (double) (x2 - nCx3)) * 180.0d) / 3.141592653589793d);
            float y13 = y1;
            float nRadius3 = (float) distance(nCx3, nCy3, x2, y2);
            float f10 = x2;
            float f11 = y2;
            this.mTempRect.set(nCx3 - nRadius3, nCy3 - nRadius3, nCx3 + nRadius3, nCy3 + nRadius3);
            path2.arcTo(this.mTempRect, angle3, ((float) ((Math.atan2((double) (y3 - nCy3), (double) (x3 - nCx3)) * 180.0d) / 3.141592653589793d)) - angle3);
            float nCx4 = (2.0f * cx) - nCx3;
            float f12 = x3;
            float angle4 = (float) ((Math.atan2((double) (y3 - nCy3), (double) (x3 - nCx4)) * 180.0d) / 3.141592653589793d);
            float f13 = y3;
            float angle22 = (float) ((Math.atan2((double) (y13 - nCy3), (double) (x1 - nCx4)) * 180.0d) / 3.141592653589793d);
            this.mTempRect.set(nCx4 - nRadius3, nCy3 - nRadius3, nCx4 + nRadius3, nCy3 + nRadius3);
            path2.arcTo(this.mTempRect, 0.7853982f + angle4, angle22 - angle4);
            float x42 = nCx4;
        }
        path2.close();
        return path2;
    }

    public void draw(Canvas canvas) {
        super.draw(canvas);
        float x = (this.mDrawRect.width() * this.mThumbPosition) + this.mDrawRect.left;
        if (this.mIsRtl) {
            x = (this.mDrawRect.centerX() * 2.0f) - x;
        }
        float y = this.mDrawRect.centerY();
        int filledPrimaryColor = ColorUtil.getMiddleColor(this.mSecondaryColor, isEnabled() ? this.mPrimaryColor : this.mSecondaryColor, this.mThumbFillPercent);
        getTrackPath(x, y, this.mThumbCurrentRadius);
        this.mPaint.setStyle(Paint.Style.FILL);
        this.mPaint.setColor(this.mIsRtl ? filledPrimaryColor : this.mSecondaryColor);
        canvas.drawPath(this.mRightTrackPath, this.mPaint);
        this.mPaint.setColor(this.mIsRtl ? this.mSecondaryColor : filledPrimaryColor);
        canvas.drawPath(this.mLeftTrackPath, this.mPaint);
        this.mPaint.setColor(filledPrimaryColor);
        if (this.mDiscreteMode) {
            float f = this.mThumbCurrentRadius;
            int i = this.mThumbRadius;
            float factor = 1.0f - (f / ((float) i));
            if (factor > 0.0f) {
                this.mMarkPath = getMarkPath(this.mMarkPath, x, y, (float) i, factor);
                this.mPaint.setStyle(Paint.Style.FILL);
                int saveCount = canvas.save();
                canvas.translate(0.0f, ((float) ((-this.mThumbRadius) * 2)) * factor);
                canvas.drawPath(this.mMarkPath, this.mPaint);
                this.mPaint.setColor(ColorUtil.getColor(this.mTextColor, factor));
                canvas.drawText(getValueText(), x, ((((float) this.mTextHeight) / 2.0f) + y) - (((float) this.mThumbRadius) * factor), this.mPaint);
                canvas.restoreToCount(saveCount);
            }
            float radius = isEnabled() ? this.mThumbCurrentRadius : this.mThumbCurrentRadius - ((float) this.mThumbBorderSize);
            if (radius > 0.0f) {
                this.mPaint.setColor(filledPrimaryColor);
                canvas.drawCircle(x, y, radius, this.mPaint);
                return;
            }
            return;
        }
        float radius2 = isEnabled() ? this.mThumbCurrentRadius : this.mThumbCurrentRadius - ((float) this.mThumbBorderSize);
        float f2 = this.mThumbFillPercent;
        if (f2 == 1.0f) {
            this.mPaint.setStyle(Paint.Style.FILL);
        } else {
            int i2 = this.mThumbBorderSize;
            float strokeWidth = ((radius2 - ((float) i2)) * f2) + ((float) i2);
            radius2 -= strokeWidth / 2.0f;
            this.mPaint.setStyle(Paint.Style.STROKE);
            this.mPaint.setStrokeWidth(strokeWidth);
        }
        canvas.drawCircle(x, y, radius2, this.mPaint);
    }

    class ThumbRadiusAnimator implements Runnable {
        int mRadius;
        boolean mRunning = false;
        float mStartRadius;
        long mStartTime;

        ThumbRadiusAnimator() {
        }

        public void resetAnimation() {
            this.mStartTime = SystemClock.uptimeMillis();
            this.mStartRadius = Slider.this.mThumbCurrentRadius;
        }

        public boolean startAnimation(int radius) {
            if (Slider.this.mThumbCurrentRadius == ((float) radius)) {
                return false;
            }
            this.mRadius = radius;
            if (Slider.this.getHandler() != null) {
                resetAnimation();
                this.mRunning = true;
                Slider.this.getHandler().postAtTime(this, SystemClock.uptimeMillis() + 16);
                Slider.this.invalidate();
                return true;
            }
            float unused = Slider.this.mThumbCurrentRadius = (float) this.mRadius;
            Slider.this.invalidate();
            return false;
        }

        public void stopAnimation() {
            this.mRunning = false;
            float unused = Slider.this.mThumbCurrentRadius = (float) this.mRadius;
            if (Slider.this.getHandler() != null) {
                Slider.this.getHandler().removeCallbacks(this);
            }
            Slider.this.invalidate();
        }

        public void run() {
            float progress = Math.min(1.0f, ((float) (SystemClock.uptimeMillis() - this.mStartTime)) / ((float) Slider.this.mTransformAnimationDuration));
            float value = Slider.this.mInterpolator.getInterpolation(progress);
            Slider slider = Slider.this;
            float f = this.mStartRadius;
            float unused = slider.mThumbCurrentRadius = ((((float) this.mRadius) - f) * value) + f;
            if (progress == 1.0f) {
                stopAnimation();
            }
            if (this.mRunning) {
                if (Slider.this.getHandler() != null) {
                    Slider.this.getHandler().postAtTime(this, SystemClock.uptimeMillis() + 16);
                } else {
                    stopAnimation();
                }
            }
            Slider.this.invalidate();
        }
    }

    class ThumbStrokeAnimator implements Runnable {
        int mFillPercent;
        boolean mRunning = false;
        float mStartFillPercent;
        long mStartTime;

        ThumbStrokeAnimator() {
        }

        public void resetAnimation() {
            this.mStartTime = SystemClock.uptimeMillis();
            this.mStartFillPercent = Slider.this.mThumbFillPercent;
        }

        public boolean startAnimation(int fillPercent) {
            if (Slider.this.mThumbFillPercent == ((float) fillPercent)) {
                return false;
            }
            this.mFillPercent = fillPercent;
            if (Slider.this.getHandler() != null) {
                resetAnimation();
                this.mRunning = true;
                Slider.this.getHandler().postAtTime(this, SystemClock.uptimeMillis() + 16);
                Slider.this.invalidate();
                return true;
            }
            Slider slider = Slider.this;
            float unused = slider.mThumbFillPercent = slider.mAlwaysFillThumb ? 1.0f : (float) this.mFillPercent;
            Slider.this.invalidate();
            return false;
        }

        public void stopAnimation() {
            this.mRunning = false;
            Slider slider = Slider.this;
            float unused = slider.mThumbFillPercent = slider.mAlwaysFillThumb ? 1.0f : (float) this.mFillPercent;
            if (Slider.this.getHandler() != null) {
                Slider.this.getHandler().removeCallbacks(this);
            }
            Slider.this.invalidate();
        }

        public void run() {
            float f;
            float progress = Math.min(1.0f, ((float) (SystemClock.uptimeMillis() - this.mStartTime)) / ((float) Slider.this.mTransformAnimationDuration));
            float value = Slider.this.mInterpolator.getInterpolation(progress);
            Slider slider = Slider.this;
            if (slider.mAlwaysFillThumb) {
                f = 1.0f;
            } else {
                float f2 = this.mStartFillPercent;
                f = ((((float) this.mFillPercent) - f2) * value) + f2;
            }
            float unused = slider.mThumbFillPercent = f;
            if (progress == 1.0f) {
                stopAnimation();
            }
            if (this.mRunning) {
                if (Slider.this.getHandler() != null) {
                    Slider.this.getHandler().postAtTime(this, SystemClock.uptimeMillis() + 16);
                } else {
                    stopAnimation();
                }
            }
            Slider.this.invalidate();
        }
    }

    class ThumbMoveAnimator implements Runnable {
        int mDuration;
        float mFillPercent;
        float mPosition;
        boolean mRunning = false;
        float mStartFillPercent;
        float mStartPosition;
        float mStartRadius;
        long mStartTime;

        ThumbMoveAnimator() {
        }

        public boolean isRunning() {
            return this.mRunning;
        }

        public float getPosition() {
            return this.mPosition;
        }

        public void resetAnimation() {
            this.mStartTime = SystemClock.uptimeMillis();
            this.mStartPosition = Slider.this.mThumbPosition;
            this.mStartFillPercent = Slider.this.mThumbFillPercent;
            this.mStartRadius = Slider.this.mThumbCurrentRadius;
            float f = 0.0f;
            if (this.mPosition != 0.0f) {
                f = 1.0f;
            }
            this.mFillPercent = f;
            this.mDuration = (!Slider.this.mDiscreteMode || Slider.this.mIsDragging) ? Slider.this.mTravelAnimationDuration : (Slider.this.mTransformAnimationDuration * 2) + Slider.this.mTravelAnimationDuration;
        }

        public boolean startAnimation(float position) {
            if (Slider.this.mThumbPosition == position) {
                return false;
            }
            this.mPosition = position;
            if (Slider.this.getHandler() != null) {
                resetAnimation();
                this.mRunning = true;
                Slider.this.getHandler().postAtTime(this, SystemClock.uptimeMillis() + 16);
                Slider.this.invalidate();
                return true;
            }
            float unused = Slider.this.mThumbPosition = position;
            Slider.this.invalidate();
            return false;
        }

        public void stopAnimation() {
            this.mRunning = false;
            Slider slider = Slider.this;
            float unused = slider.mThumbCurrentRadius = (!slider.mDiscreteMode || !Slider.this.mIsDragging) ? (float) Slider.this.mThumbRadius : 0.0f;
            Slider slider2 = Slider.this;
            float unused2 = slider2.mThumbFillPercent = slider2.mAlwaysFillThumb ? 1.0f : this.mFillPercent;
            float unused3 = Slider.this.mThumbPosition = this.mPosition;
            if (Slider.this.getHandler() != null) {
                Slider.this.getHandler().removeCallbacks(this);
            }
            Slider.this.invalidate();
        }

        public void run() {
            float f;
            float f2;
            float f3;
            float progress = Math.min(1.0f, ((float) (SystemClock.uptimeMillis() - this.mStartTime)) / ((float) this.mDuration));
            float value = Slider.this.mInterpolator.getInterpolation(progress);
            if (!Slider.this.mDiscreteMode) {
                Slider slider = Slider.this;
                float f4 = this.mPosition;
                float f5 = this.mStartPosition;
                float unused = slider.mThumbPosition = ((f4 - f5) * value) + f5;
                Slider slider2 = Slider.this;
                if (slider2.mAlwaysFillThumb) {
                    f = 1.0f;
                } else {
                    float f6 = this.mFillPercent;
                    float f7 = this.mStartFillPercent;
                    f = ((f6 - f7) * value) + f7;
                }
                float unused2 = slider2.mThumbFillPercent = f;
                if (((double) progress) < 0.2d) {
                    Slider slider3 = Slider.this;
                    float unused3 = slider3.mThumbCurrentRadius = Math.max(((float) slider3.mThumbRadius) + (((float) Slider.this.mThumbBorderSize) * progress * 5.0f), Slider.this.mThumbCurrentRadius);
                } else if (((double) progress) >= 0.8d) {
                    Slider slider4 = Slider.this;
                    float unused4 = slider4.mThumbCurrentRadius = ((float) slider4.mThumbRadius) + (((float) Slider.this.mThumbBorderSize) * (5.0f - (progress * 5.0f)));
                }
            } else if (Slider.this.mIsDragging) {
                Slider slider5 = Slider.this;
                float f8 = this.mPosition;
                float f9 = this.mStartPosition;
                float unused5 = slider5.mThumbPosition = ((f8 - f9) * value) + f9;
                Slider slider6 = Slider.this;
                if (slider6.mAlwaysFillThumb) {
                    f3 = 1.0f;
                } else {
                    float f10 = this.mFillPercent;
                    float f11 = this.mStartFillPercent;
                    f3 = ((f10 - f11) * value) + f11;
                }
                float unused6 = slider6.mThumbFillPercent = f3;
            } else {
                float p1 = ((float) Slider.this.mTravelAnimationDuration) / ((float) this.mDuration);
                float p2 = ((float) (Slider.this.mTravelAnimationDuration + Slider.this.mTransformAnimationDuration)) / ((float) this.mDuration);
                if (progress < p1) {
                    float value2 = Slider.this.mInterpolator.getInterpolation(progress / p1);
                    float unused7 = Slider.this.mThumbCurrentRadius = this.mStartRadius * (1.0f - value2);
                    Slider slider7 = Slider.this;
                    float f12 = this.mPosition;
                    float f13 = this.mStartPosition;
                    float unused8 = slider7.mThumbPosition = ((f12 - f13) * value2) + f13;
                    Slider slider8 = Slider.this;
                    if (slider8.mAlwaysFillThumb) {
                        f2 = 1.0f;
                    } else {
                        float f14 = this.mFillPercent;
                        float f15 = this.mStartFillPercent;
                        f2 = ((f14 - f15) * value2) + f15;
                    }
                    float unused9 = slider8.mThumbFillPercent = f2;
                } else if (progress > p2) {
                    Slider slider9 = Slider.this;
                    float unused10 = slider9.mThumbCurrentRadius = (((float) slider9.mThumbRadius) * (progress - p2)) / (1.0f - p2);
                }
            }
            if (progress == 1.0f) {
                stopAnimation();
            }
            if (this.mRunning) {
                if (Slider.this.getHandler() != null) {
                    Slider.this.getHandler().postAtTime(this, SystemClock.uptimeMillis() + 16);
                } else {
                    stopAnimation();
                }
            }
            Slider.this.invalidate();
        }
    }

    /* access modifiers changed from: protected */
    public Parcelable onSaveInstanceState() {
        SavedState ss = new SavedState(super.onSaveInstanceState());
        ss.position = getPosition();
        return ss;
    }

    /* access modifiers changed from: protected */
    public void onRestoreInstanceState(Parcelable state) {
        SavedState ss = (SavedState) state;
        super.onRestoreInstanceState(ss.getSuperState());
        setPosition(ss.position, false);
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
        float position;

        SavedState(Parcelable superState) {
            super(superState);
        }

        private SavedState(Parcel in) {
            super(in);
            this.position = in.readFloat();
        }

        public void writeToParcel(Parcel out, int flags) {
            super.writeToParcel(out, flags);
            out.writeFloat(this.position);
        }

        public String toString() {
            return "Slider.SavedState{" + Integer.toHexString(System.identityHashCode(this)) + " pos=" + this.position + "}";
        }
    }
}
