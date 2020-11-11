package com.rey.material.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.SystemClock;
import android.text.format.DateFormat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import androidx.core.view.ViewCompat;
import com.rey.material.C2500R;
import com.rey.material.app.ThemeManager;
import com.rey.material.util.ColorUtil;
import com.rey.material.util.ThemeUtil;
import com.rey.material.util.TypefaceUtil;
import com.rey.material.util.ViewUtil;

public class TimePicker extends View implements ThemeManager.OnThemeChangedListener {
    public static final int MODE_HOUR = 0;
    public static final int MODE_MINUTE = 1;
    private boolean m24Hour = true;
    private int mAnimDuration = -1;
    private float mAnimProgress;
    private int mBackgroundColor;
    private PointF mCenterPoint;
    protected int mCurrentStyle = Integer.MIN_VALUE;
    private boolean mEdited = false;
    private int mHour = 0;
    private Interpolator mInInterpolator;
    private float mInnerRadius;
    private float[] mLocations = new float[72];
    private int mMinute = 0;
    private int mMode = 0;
    private OnTimeChangedListener mOnTimeChangedListener;
    private Interpolator mOutInterpolator;
    private float mOuterRadius;
    private Paint mPaint;
    private Rect mRect;
    private boolean mRunning;
    private float mSecondInnerRadius;
    private int mSelectionColor;
    private int mSelectionRadius = -1;
    private long mStartTime;
    protected int mStyleId;
    private int mTextColor = ViewCompat.MEASURED_STATE_MASK;
    private int mTextHighlightColor = -1;
    private int mTextSize = -1;
    private int mTickSize = -1;
    private String[] mTicks;
    private Typeface mTypeface = Typeface.DEFAULT;
    private final Runnable mUpdater = new Runnable() {
        public void run() {
            TimePicker.this.update();
        }
    };

    public interface OnTimeChangedListener {
        void onHourChanged(int i, int i2);

        void onMinuteChanged(int i, int i2);

        void onModeChanged(int i);
    }

    public TimePicker(Context context) {
        super(context);
        init(context, (AttributeSet) null, 0, 0);
    }

    public TimePicker(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0, 0);
    }

    public TimePicker(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr, 0);
    }

    /* access modifiers changed from: protected */
    public void init(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        this.mPaint = new Paint(1);
        this.mRect = new Rect();
        this.mBackgroundColor = ColorUtil.getColor(ThemeUtil.colorPrimary(context, ViewCompat.MEASURED_STATE_MASK), 0.25f);
        this.mSelectionColor = ThemeUtil.colorPrimary(context, ViewCompat.MEASURED_STATE_MASK);
        initTickLabels();
        setWillNotDraw(false);
        applyStyle(context, attrs, defStyleAttr, defStyleRes);
        if (!isInEditMode()) {
            this.mStyleId = ThemeManager.getStyleId(context, attrs, defStyleAttr, defStyleRes);
        }
    }

    private void initTickLabels() {
        this.mTicks = new String[36];
        for (int i = 0; i < 23; i++) {
            this.mTicks[i] = String.format("%2d", new Object[]{Integer.valueOf(i + 1)});
        }
        this.mTicks[23] = String.format("%2d", new Object[]{0});
        String[] strArr = this.mTicks;
        strArr[35] = strArr[23];
        for (int i2 = 24; i2 < 35; i2++) {
            this.mTicks[i2] = String.format("%2d", new Object[]{Integer.valueOf((i2 - 23) * 5)});
        }
    }

    public void applyStyle(int styleId) {
        ViewUtil.applyStyle(this, styleId);
        applyStyle(getContext(), (AttributeSet) null, 0, styleId);
    }

    /* access modifiers changed from: protected */
    public void applyStyle(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        TypedArray a = context.obtainStyledAttributes(attrs, C2500R.styleable.TimePicker, defStyleAttr, defStyleRes);
        boolean hourDefined = false;
        String familyName = null;
        int style = -1;
        int count = a.getIndexCount();
        for (int i = 0; i < count; i++) {
            int attr = a.getIndex(i);
            if (attr == C2500R.styleable.TimePicker_tp_backgroundColor) {
                this.mBackgroundColor = a.getColor(attr, 0);
            } else if (attr == C2500R.styleable.TimePicker_tp_selectionColor) {
                this.mSelectionColor = a.getColor(attr, 0);
            } else if (attr == C2500R.styleable.TimePicker_tp_selectionRadius) {
                this.mSelectionRadius = a.getDimensionPixelOffset(attr, 0);
            } else if (attr == C2500R.styleable.TimePicker_tp_tickSize) {
                this.mTickSize = a.getDimensionPixelOffset(attr, 0);
            } else if (attr == C2500R.styleable.TimePicker_tp_textSize) {
                this.mTextSize = a.getDimensionPixelOffset(attr, 0);
            } else if (attr == C2500R.styleable.TimePicker_tp_textColor) {
                this.mTextColor = a.getColor(attr, 0);
            } else if (attr == C2500R.styleable.TimePicker_tp_textHighlightColor) {
                this.mTextHighlightColor = a.getColor(attr, 0);
            } else if (attr == C2500R.styleable.TimePicker_tp_animDuration) {
                this.mAnimDuration = a.getInteger(attr, 0);
            } else if (attr == C2500R.styleable.TimePicker_tp_inInterpolator) {
                this.mInInterpolator = AnimationUtils.loadInterpolator(context, a.getResourceId(attr, 0));
            } else if (attr == C2500R.styleable.TimePicker_tp_outInterpolator) {
                this.mOutInterpolator = AnimationUtils.loadInterpolator(context, a.getResourceId(attr, 0));
            } else if (attr == C2500R.styleable.TimePicker_tp_mode) {
                setMode(a.getInteger(attr, 0), false);
            } else if (attr == C2500R.styleable.TimePicker_tp_24Hour) {
                set24Hour(a.getBoolean(attr, false));
                hourDefined = true;
            } else if (attr == C2500R.styleable.TimePicker_tp_hour) {
                setHour(a.getInteger(attr, 0));
            } else if (attr == C2500R.styleable.TimePicker_tp_minute) {
                setMinute(a.getInteger(attr, 0));
            } else if (attr == C2500R.styleable.TimePicker_tp_fontFamily) {
                familyName = a.getString(attr);
            } else if (attr == C2500R.styleable.TimePicker_tp_textStyle) {
                style = a.getInteger(attr, 0);
            }
        }
        a.recycle();
        if (this.mSelectionRadius < 0) {
            this.mSecondInnerRadius = (float) ThemeUtil.dpToPx(context, 8);
        }
        if (this.mTickSize < 0) {
            this.mTickSize = ThemeUtil.dpToPx(context, 1);
        }
        if (this.mTextSize < 0) {
            this.mTextSize = context.getResources().getDimensionPixelOffset(C2500R.dimen.abc_text_size_caption_material);
        }
        if (this.mAnimDuration < 0) {
            this.mAnimDuration = context.getResources().getInteger(17694721);
        }
        if (this.mInInterpolator == null) {
            this.mInInterpolator = new DecelerateInterpolator();
        }
        if (this.mOutInterpolator == null) {
            this.mOutInterpolator = new DecelerateInterpolator();
        }
        if (!hourDefined) {
            set24Hour(DateFormat.is24HourFormat(context));
        }
        if (familyName != null || style >= 0) {
            this.mTypeface = TypefaceUtil.load(context, familyName, style);
        }
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
        if (this.mStyleId != 0) {
            ThemeManager.getInstance().unregisterOnThemeChangedListener(this);
        }
    }

    public int getBackgroundColor() {
        return this.mBackgroundColor;
    }

    public int getSelectionColor() {
        return this.mSelectionColor;
    }

    public Typeface getTypeface() {
        return this.mTypeface;
    }

    public int getTextSize() {
        return this.mTextSize;
    }

    public int getTextColor() {
        return this.mTextColor;
    }

    public int getTextHighlightColor() {
        return this.mTextHighlightColor;
    }

    public int getAnimDuration() {
        return this.mAnimDuration;
    }

    public Interpolator getInInterpolator() {
        return this.mInInterpolator;
    }

    public Interpolator getOutInterpolator() {
        return this.mOutInterpolator;
    }

    public int getMode() {
        return this.mMode;
    }

    public int getHour() {
        return this.mHour;
    }

    public int getMinute() {
        return this.mMinute;
    }

    public boolean is24Hour() {
        return this.m24Hour;
    }

    public void setMode(int mode, boolean animation) {
        if (this.mMode != mode) {
            this.mMode = mode;
            OnTimeChangedListener onTimeChangedListener = this.mOnTimeChangedListener;
            if (onTimeChangedListener != null) {
                onTimeChangedListener.onModeChanged(mode);
            }
            if (animation) {
                startAnimation();
            } else {
                invalidate();
            }
        }
    }

    public void setHour(int hour) {
        int hour2;
        if (this.m24Hour) {
            hour2 = Math.max(hour, 0) % 24;
        } else {
            hour2 = Math.max(hour, 0) % 12;
        }
        if (this.mHour != hour2) {
            int old = this.mHour;
            this.mHour = hour2;
            OnTimeChangedListener onTimeChangedListener = this.mOnTimeChangedListener;
            if (onTimeChangedListener != null) {
                onTimeChangedListener.onHourChanged(old, hour2);
            }
            if (this.mMode == 0) {
                invalidate();
            }
        }
    }

    public void setMinute(int minute) {
        int minute2 = Math.min(Math.max(minute, 0), 59);
        if (this.mMinute != minute2) {
            int old = this.mMinute;
            this.mMinute = minute2;
            OnTimeChangedListener onTimeChangedListener = this.mOnTimeChangedListener;
            if (onTimeChangedListener != null) {
                onTimeChangedListener.onMinuteChanged(old, minute2);
            }
            if (this.mMode == 1) {
                invalidate();
            }
        }
    }

    public void setOnTimeChangedListener(OnTimeChangedListener listener) {
        this.mOnTimeChangedListener = listener;
    }

    public void set24Hour(boolean b) {
        int i;
        if (this.m24Hour != b) {
            this.m24Hour = b;
            if (!b && (i = this.mHour) > 11) {
                setHour(i - 12);
            }
            calculateTextLocation();
        }
    }

    private float getAngle(int value, int mode) {
        if (mode == 0) {
            return (float) ((((double) value) * 0.5235987755982988d) - 2.858407346410207d);
        }
        if (mode != 1) {
            return 0.0f;
        }
        return (float) ((((double) value) * 0.10471975511965977d) - 2.858407346410207d);
    }

    private int getSelectedTick(int value, int mode) {
        if (mode != 0) {
            if (mode != 1 || value % 5 != 0) {
                return -1;
            }
            if (value == 0) {
                return 35;
            }
            return 23 + (value / 5);
        } else if (value != 0) {
            return value - 1;
        } else {
            if (this.m24Hour) {
                return 23;
            }
            return 11;
        }
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode = View.MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = widthMode == 0 ? this.mSelectionRadius * 12 : (View.MeasureSpec.getSize(widthMeasureSpec) - getPaddingLeft()) - getPaddingRight();
        int heightMode = View.MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = heightMode == 0 ? this.mSelectionRadius * 12 : (View.MeasureSpec.getSize(heightMeasureSpec) - getPaddingTop()) - getPaddingBottom();
        int size = Math.min(widthSize, heightSize);
        setMeasuredDimension(getPaddingLeft() + (widthMode == 1073741824 ? widthSize : size) + getPaddingRight(), getPaddingTop() + (heightMode == 1073741824 ? heightSize : size) + getPaddingBottom());
    }

    private void calculateTextLocation() {
        if (this.mCenterPoint != null) {
            double angle = -1.0471975511965976d;
            this.mPaint.setTextSize((float) this.mTextSize);
            this.mPaint.setTypeface(this.mTypeface);
            this.mPaint.setTextAlign(Paint.Align.CENTER);
            if (this.m24Hour) {
                for (int i = 0; i < 12; i++) {
                    Paint paint = this.mPaint;
                    String[] strArr = this.mTicks;
                    paint.getTextBounds(strArr[i], 0, strArr[i].length(), this.mRect);
                    if (i == 0) {
                        this.mSecondInnerRadius = (this.mInnerRadius - ((float) this.mSelectionRadius)) - ((float) this.mRect.height());
                    }
                    float x = this.mCenterPoint.x + (((float) Math.cos(angle)) * this.mSecondInnerRadius);
                    float y = this.mCenterPoint.y + (((float) Math.sin(angle)) * this.mSecondInnerRadius);
                    float[] fArr = this.mLocations;
                    fArr[i * 2] = x;
                    fArr[(i * 2) + 1] = (((float) this.mRect.height()) / 2.0f) + y;
                    angle += 0.5235987755982988d;
                }
                for (int i2 = 12; i2 < this.mTicks.length; i2++) {
                    float x2 = this.mCenterPoint.x + (((float) Math.cos(angle)) * this.mInnerRadius);
                    float y2 = this.mCenterPoint.y + (((float) Math.sin(angle)) * this.mInnerRadius);
                    Paint paint2 = this.mPaint;
                    String[] strArr2 = this.mTicks;
                    paint2.getTextBounds(strArr2[i2], 0, strArr2[i2].length(), this.mRect);
                    float[] fArr2 = this.mLocations;
                    fArr2[i2 * 2] = x2;
                    fArr2[(i2 * 2) + 1] = (((float) this.mRect.height()) / 2.0f) + y2;
                    angle += 0.5235987755982988d;
                }
                return;
            }
            for (int i3 = 0; i3 < 12; i3++) {
                float x3 = this.mCenterPoint.x + (((float) Math.cos(angle)) * this.mInnerRadius);
                float y3 = this.mCenterPoint.y + (((float) Math.sin(angle)) * this.mInnerRadius);
                Paint paint3 = this.mPaint;
                String[] strArr3 = this.mTicks;
                paint3.getTextBounds(strArr3[i3], 0, strArr3[i3].length(), this.mRect);
                float[] fArr3 = this.mLocations;
                fArr3[i3 * 2] = x3;
                fArr3[(i3 * 2) + 1] = (((float) this.mRect.height()) / 2.0f) + y3;
                angle += 0.5235987755982988d;
            }
            for (int i4 = 24; i4 < this.mTicks.length; i4++) {
                float x4 = this.mCenterPoint.x + (((float) Math.cos(angle)) * this.mInnerRadius);
                float y4 = this.mCenterPoint.y + (((float) Math.sin(angle)) * this.mInnerRadius);
                Paint paint4 = this.mPaint;
                String[] strArr4 = this.mTicks;
                paint4.getTextBounds(strArr4[i4], 0, strArr4[i4].length(), this.mRect);
                float[] fArr4 = this.mLocations;
                fArr4[i4 * 2] = x4;
                fArr4[(i4 * 2) + 1] = (((float) this.mRect.height()) / 2.0f) + y4;
                angle += 0.5235987755982988d;
            }
        }
    }

    /* access modifiers changed from: protected */
    public void onSizeChanged(int w, int h, int oldw, int oldh) {
        int left = getPaddingLeft();
        int top = getPaddingTop();
        int size = Math.min((w - getPaddingLeft()) - getPaddingRight(), (h - getPaddingTop()) - getPaddingBottom());
        if (this.mCenterPoint == null) {
            this.mCenterPoint = new PointF();
        }
        float f = ((float) size) / 2.0f;
        this.mOuterRadius = f;
        this.mCenterPoint.set(((float) left) + f, ((float) top) + f);
        this.mInnerRadius = (this.mOuterRadius - ((float) this.mSelectionRadius)) - ((float) ThemeUtil.dpToPx(getContext(), 4));
        calculateTextLocation();
    }

    private int getPointedValue(float x, float y, boolean isDown) {
        float radius = (float) Math.sqrt(Math.pow((double) (x - this.mCenterPoint.x), 2.0d) + Math.pow((double) (y - this.mCenterPoint.y), 2.0d));
        if (isDown) {
            if (this.mMode != 0 || !this.m24Hour) {
                float f = this.mInnerRadius;
                int i = this.mSelectionRadius;
                if (radius > ((float) i) + f || radius < f - ((float) i)) {
                    return -1;
                }
            } else {
                float f2 = this.mInnerRadius;
                int i2 = this.mSelectionRadius;
                if (radius > f2 + ((float) i2) || radius < this.mSecondInnerRadius - ((float) i2)) {
                    return -1;
                }
            }
        }
        float angle = (float) Math.atan2((double) (y - this.mCenterPoint.y), (double) (x - this.mCenterPoint.x));
        if (angle < 0.0f) {
            angle = (float) (((double) angle) + 6.283185307179586d);
        }
        int i3 = this.mMode;
        if (i3 == 0) {
            if (!this.m24Hour) {
                int value = ((int) Math.round(((double) (6.0f * angle)) / 3.141592653589793d)) + 3;
                return value > 11 ? value - 12 : value;
            } else if (radius > this.mSecondInnerRadius + ((float) (this.mSelectionRadius / 2))) {
                int value2 = ((int) Math.round(((double) (6.0f * angle)) / 3.141592653589793d)) + 15;
                if (value2 == 24) {
                    return 0;
                }
                if (value2 > 24) {
                    return value2 - 12;
                }
                return value2;
            } else {
                int value3 = ((int) Math.round(((double) (6.0f * angle)) / 3.141592653589793d)) + 3;
                return value3 > 12 ? value3 - 12 : value3;
            }
        } else if (i3 != 1) {
            return -1;
        } else {
            int value4 = ((int) Math.round(((double) (30.0f * angle)) / 3.141592653589793d)) + 15;
            return value4 > 59 ? value4 - 60 : value4;
        }
    }

    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        if (action != 0) {
            if (action != 1) {
                if (action == 2) {
                    int value = getPointedValue(event.getX(), event.getY(), false);
                    if (value < 0) {
                        return true;
                    }
                    int i = this.mMode;
                    if (i == 0) {
                        setHour(value);
                    } else if (i == 1) {
                        setMinute(value);
                    }
                    this.mEdited = true;
                    return true;
                } else if (action == 3) {
                    this.mEdited = false;
                }
            } else if (this.mEdited != 0 && this.mMode == 0) {
                setMode(1, true);
                this.mEdited = false;
                return true;
            }
            return false;
        }
        int value2 = getPointedValue(event.getX(), event.getY(), true);
        if (value2 < 0) {
            return false;
        }
        int i2 = this.mMode;
        if (i2 == 0) {
            setHour(value2);
        } else if (i2 == 1) {
            setMinute(value2);
        }
        this.mEdited = true;
        return true;
    }

    public void draw(Canvas canvas) {
        int outStart;
        int inLength;
        float outOffset;
        float inRadius;
        float outRadius;
        float outOffset2;
        float outRadius2;
        int inStart;
        float inAngle;
        int outSelectedTick;
        int outLength;
        int inLength2;
        int start;
        int selectedTick;
        float radius;
        int length;
        float angle;
        Canvas canvas2 = canvas;
        super.draw(canvas);
        this.mPaint.setColor(this.mBackgroundColor);
        this.mPaint.setStyle(Paint.Style.FILL);
        canvas2.drawCircle(this.mCenterPoint.x, this.mCenterPoint.y, this.mOuterRadius, this.mPaint);
        if (!this.mRunning) {
            if (this.mMode == 0) {
                float angle2 = getAngle(this.mHour, 0);
                int selectedTick2 = getSelectedTick(this.mHour, 0);
                boolean z = this.m24Hour;
                angle = angle2;
                length = z ? 24 : 12;
                radius = (!z || selectedTick2 >= 12) ? this.mInnerRadius : this.mSecondInnerRadius;
                selectedTick = selectedTick2;
                start = 0;
            } else {
                float angle3 = getAngle(this.mMinute, 1);
                int selectedTick3 = getSelectedTick(this.mMinute, 1);
                angle = angle3;
                length = 12;
                radius = this.mInnerRadius;
                selectedTick = selectedTick3;
                start = 24;
            }
            this.mPaint.setColor(this.mSelectionColor);
            float x = this.mCenterPoint.x + (((float) Math.cos((double) angle)) * radius);
            float y = this.mCenterPoint.y + (((float) Math.sin((double) angle)) * radius);
            canvas2.drawCircle(x, y, (float) this.mSelectionRadius, this.mPaint);
            this.mPaint.setStyle(Paint.Style.STROKE);
            this.mPaint.setStrokeWidth((float) this.mTickSize);
            canvas.drawLine(this.mCenterPoint.x, this.mCenterPoint.y, x - (((float) Math.cos((double) angle)) * ((float) this.mSelectionRadius)), y - (((float) Math.sin((double) angle)) * ((float) this.mSelectionRadius)), this.mPaint);
            this.mPaint.setStyle(Paint.Style.FILL);
            this.mPaint.setColor(this.mTextColor);
            canvas2.drawCircle(this.mCenterPoint.x, this.mCenterPoint.y, (float) (this.mTickSize * 2), this.mPaint);
            this.mPaint.setTextSize((float) this.mTextSize);
            this.mPaint.setTypeface(this.mTypeface);
            this.mPaint.setTextAlign(Paint.Align.CENTER);
            for (int i = 0; i < length; i++) {
                int index = start + i;
                this.mPaint.setColor(index == selectedTick ? this.mTextHighlightColor : this.mTextColor);
                String str = this.mTicks[index];
                float[] fArr = this.mLocations;
                canvas2.drawText(str, fArr[index * 2], fArr[(index * 2) + 1], this.mPaint);
            }
            return;
        }
        float maxOffset = (this.mOuterRadius - this.mInnerRadius) + ((float) (this.mTextSize / 2));
        int textOutColor = ColorUtil.getColor(this.mTextColor, 1.0f - this.mAnimProgress);
        int textHighlightOutColor = ColorUtil.getColor(this.mTextHighlightColor, 1.0f - this.mAnimProgress);
        int textInColor = ColorUtil.getColor(this.mTextColor, this.mAnimProgress);
        int textHighlightInColor = ColorUtil.getColor(this.mTextHighlightColor, this.mAnimProgress);
        if (this.mMode == 1) {
            float outAngle = getAngle(this.mHour, 0);
            inAngle = getAngle(this.mMinute, 1);
            float outOffset3 = this.mOutInterpolator.getInterpolation(this.mAnimProgress) * maxOffset;
            float inOffset = (1.0f - this.mInInterpolator.getInterpolation(this.mAnimProgress)) * (-maxOffset);
            int outSelectedTick2 = getSelectedTick(this.mHour, 0);
            int inSelectedTick = getSelectedTick(this.mMinute, 1);
            boolean z2 = this.m24Hour;
            int outLength2 = z2 ? 24 : 12;
            float outRadius3 = (!z2 || outSelectedTick2 >= 12) ? this.mInnerRadius : this.mSecondInnerRadius;
            outStart = 0;
            outOffset = outOffset3;
            outOffset2 = inOffset;
            inLength2 = 12;
            inLength = 24;
            inStart = outSelectedTick2;
            outSelectedTick = inSelectedTick;
            outRadius = outRadius3;
            outLength = outLength2;
            inRadius = this.mInnerRadius;
            outRadius2 = outAngle;
        } else {
            float outAngle2 = getAngle(this.mMinute, 1);
            inAngle = getAngle(this.mHour, 0);
            float outOffset4 = this.mOutInterpolator.getInterpolation(this.mAnimProgress) * (-maxOffset);
            float inOffset2 = (1.0f - this.mInInterpolator.getInterpolation(this.mAnimProgress)) * maxOffset;
            inStart = getSelectedTick(this.mMinute, 1);
            int inSelectedTick2 = getSelectedTick(this.mHour, 0);
            float outRadius4 = this.mInnerRadius;
            inLength = 0;
            boolean z3 = this.m24Hour;
            outStart = 24;
            outOffset = outOffset4;
            outOffset2 = inOffset2;
            inLength2 = z3 ? 24 : 12;
            inRadius = (!z3 || inSelectedTick2 >= 12) ? this.mInnerRadius : this.mSecondInnerRadius;
            outLength = 12;
            outSelectedTick = inSelectedTick2;
            outRadius = outRadius4;
            outRadius2 = outAngle2;
        }
        int inLength3 = inLength2;
        this.mPaint.setColor(ColorUtil.getColor(this.mSelectionColor, 1.0f - this.mAnimProgress));
        float x2 = this.mCenterPoint.x + (((float) Math.cos((double) outRadius2)) * (outRadius + outOffset));
        float y2 = this.mCenterPoint.y + (((float) Math.sin((double) outRadius2)) * (outRadius + outOffset));
        canvas2.drawCircle(x2, y2, (float) this.mSelectionRadius, this.mPaint);
        this.mPaint.setStyle(Paint.Style.STROKE);
        this.mPaint.setStrokeWidth((float) this.mTickSize);
        float x3 = x2 - (((float) Math.cos((double) outRadius2)) * ((float) this.mSelectionRadius));
        float y3 = y2 - (((float) Math.sin((double) outRadius2)) * ((float) this.mSelectionRadius));
        float f = outRadius2;
        int inLength4 = inLength3;
        float f2 = maxOffset;
        int outLength3 = outLength;
        int textOutColor2 = textOutColor;
        float y4 = outSelectedTick;
        int textHighlightOutColor2 = textHighlightOutColor;
        float inAngle2 = inAngle;
        int textInColor2 = textInColor;
        int outSelectedTick3 = inStart;
        canvas.drawLine(this.mCenterPoint.x, this.mCenterPoint.y, x3, y3, this.mPaint);
        this.mPaint.setStyle(Paint.Style.FILL);
        this.mPaint.setColor(ColorUtil.getColor(this.mSelectionColor, this.mAnimProgress));
        float x4 = this.mCenterPoint.x + (((float) Math.cos((double) inAngle2)) * (inRadius + outOffset2));
        float y5 = this.mCenterPoint.y + (((float) Math.sin((double) inAngle2)) * (inRadius + outOffset2));
        canvas2.drawCircle(x4, y5, (float) this.mSelectionRadius, this.mPaint);
        this.mPaint.setStyle(Paint.Style.STROKE);
        this.mPaint.setStrokeWidth((float) this.mTickSize);
        canvas.drawLine(this.mCenterPoint.x, this.mCenterPoint.y, x4 - (((float) Math.cos((double) inAngle2)) * ((float) this.mSelectionRadius)), y5 - (((float) Math.sin((double) inAngle2)) * ((float) this.mSelectionRadius)), this.mPaint);
        this.mPaint.setStyle(Paint.Style.FILL);
        this.mPaint.setColor(this.mTextColor);
        canvas2.drawCircle(this.mCenterPoint.x, this.mCenterPoint.y, (float) (this.mTickSize * 2), this.mPaint);
        this.mPaint.setTextSize((float) this.mTextSize);
        this.mPaint.setTypeface(this.mTypeface);
        this.mPaint.setTextAlign(Paint.Align.CENTER);
        double angle4 = -1.0471975511965976d;
        int i2 = 0;
        while (i2 < outLength3) {
            int index2 = i2 + outStart;
            int outLength4 = outLength3;
            float inSelectedTick3 = y4;
            float inAngle3 = inAngle2;
            float x5 = this.mLocations[index2 * 2] + (((float) Math.cos(angle4)) * outOffset);
            int textHighlightInColor2 = textHighlightInColor;
            float inOffset3 = outOffset2;
            float y6 = this.mLocations[(index2 * 2) + 1] + (((float) Math.sin(angle4)) * outOffset);
            this.mPaint.setColor(index2 == outSelectedTick3 ? textHighlightOutColor2 : textOutColor2);
            canvas2.drawText(this.mTicks[index2], x5, y6, this.mPaint);
            angle4 += 0.5235987755982988d;
            i2++;
            float f3 = x5;
            float f4 = y6;
            textHighlightInColor = textHighlightInColor2;
            outLength3 = outLength4;
            y4 = inSelectedTick3;
            inAngle2 = inAngle3;
            outOffset2 = inOffset3;
        }
        float inLength5 = y4;
        float f5 = inAngle2;
        int textHighlightInColor3 = textHighlightInColor;
        float inOffset4 = outOffset2;
        int i3 = 0;
        while (i3 < inLength4) {
            int index3 = i3 + inLength;
            float x6 = this.mLocations[index3 * 2] + (((float) Math.cos(angle4)) * inOffset4);
            float outRadius5 = outRadius;
            float y7 = this.mLocations[(index3 * 2) + 1] + (((float) Math.sin(angle4)) * inOffset4);
            int inSelectedTick4 = inLength5;
            int inSelectedTick5 = inLength4;
            this.mPaint.setColor(index3 == inSelectedTick4 ? textHighlightInColor3 : textInColor2);
            canvas2.drawText(this.mTicks[index3], x6, y7, this.mPaint);
            angle4 += 0.5235987755982988d;
            i3++;
            float f6 = x6;
            float f7 = y7;
            inLength4 = inSelectedTick5;
            inLength5 = inSelectedTick4;
            outRadius = outRadius5;
        }
        float outRadius6 = inLength5;
        int inSelectedTick6 = inLength4;
    }

    private void resetAnimation() {
        this.mStartTime = SystemClock.uptimeMillis();
        this.mAnimProgress = 0.0f;
    }

    private void startAnimation() {
        if (getHandler() != null) {
            resetAnimation();
            this.mRunning = true;
            getHandler().postAtTime(this.mUpdater, SystemClock.uptimeMillis() + 16);
        }
        invalidate();
    }

    private void stopAnimation() {
        this.mRunning = false;
        this.mAnimProgress = 1.0f;
        if (getHandler() != null) {
            getHandler().removeCallbacks(this.mUpdater);
        }
        invalidate();
    }

    /* access modifiers changed from: private */
    public void update() {
        float min = Math.min(1.0f, ((float) (SystemClock.uptimeMillis() - this.mStartTime)) / ((float) this.mAnimDuration));
        this.mAnimProgress = min;
        if (min == 1.0f) {
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
        ss.mode = this.mMode;
        ss.hour = this.mHour;
        ss.minute = this.mMinute;
        ss.is24Hour = this.m24Hour;
        return ss;
    }

    /* access modifiers changed from: protected */
    public void onRestoreInstanceState(Parcelable state) {
        SavedState ss = (SavedState) state;
        super.onRestoreInstanceState(ss.getSuperState());
        set24Hour(ss.is24Hour);
        setMode(ss.mode, false);
        setHour(ss.hour);
        setMinute(ss.minute);
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
        int hour;
        boolean is24Hour;
        int minute;
        int mode;

        SavedState(Parcelable superState) {
            super(superState);
        }

        private SavedState(Parcel in) {
            super(in);
            this.mode = in.readInt();
            this.hour = in.readInt();
            this.minute = in.readInt();
            this.is24Hour = in.readInt() != 1 ? false : true;
        }

        public void writeToParcel(Parcel out, int flags) {
            super.writeToParcel(out, flags);
            out.writeValue(Integer.valueOf(this.mode));
            out.writeValue(Integer.valueOf(this.hour));
            out.writeValue(Integer.valueOf(this.minute));
            out.writeValue(Integer.valueOf(this.is24Hour ? 1 : 0));
        }

        public String toString() {
            return "TimePicker.SavedState{" + Integer.toHexString(System.identityHashCode(this)) + " mode=" + this.mode + " hour=" + this.hour + " minute=" + this.minute + "24hour=" + this.is24Hour + "}";
        }
    }
}
