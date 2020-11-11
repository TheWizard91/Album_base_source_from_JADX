package com.rey.material.app;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.format.DateUtils;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import androidx.core.view.ViewCompat;
import com.google.common.primitives.Ints;
import com.rey.material.C2500R;
import com.rey.material.app.Dialog;
import com.rey.material.util.ThemeUtil;
import com.rey.material.widget.CircleCheckedTextView;
import com.rey.material.widget.TimePicker;
import java.text.DateFormat;
import java.util.Calendar;

public class TimePickerDialog extends Dialog {
    /* access modifiers changed from: private */
    public float mCornerRadius;
    private TimePickerLayout mTimePickerLayout;

    public interface OnTimeChangedListener {
        void onTimeChanged(int i, int i2, int i3, int i4);
    }

    public TimePickerDialog(Context context) {
        super(context, C2500R.C2505style.Material_App_Dialog_TimePicker_Light);
    }

    public TimePickerDialog(Context context, int style) {
        super(context, style);
    }

    /* access modifiers changed from: protected */
    public void onCreate() {
        TimePickerLayout timePickerLayout = new TimePickerLayout(getContext());
        this.mTimePickerLayout = timePickerLayout;
        contentView((View) timePickerLayout);
    }

    public Dialog applyStyle(int resId) {
        super.applyStyle(resId);
        if (resId == 0) {
            return this;
        }
        this.mTimePickerLayout.applyStyle(resId);
        layoutParams(-1, -1);
        return this;
    }

    public Dialog layoutParams(int width, int height) {
        return super.layoutParams(-1, -1);
    }

    public Dialog cornerRadius(float radius) {
        this.mCornerRadius = radius;
        return super.cornerRadius(radius);
    }

    public TimePickerDialog hour(int hour) {
        this.mTimePickerLayout.setHour(hour);
        return this;
    }

    public TimePickerDialog minute(int minute) {
        this.mTimePickerLayout.setMinute(minute);
        return this;
    }

    public TimePickerDialog onTimeChangedListener(OnTimeChangedListener listener) {
        this.mTimePickerLayout.setOnTimeChangedListener(listener);
        return this;
    }

    public int getHour() {
        return this.mTimePickerLayout.getHour();
    }

    public int getMinute() {
        return this.mTimePickerLayout.getMinute();
    }

    public String getFormattedTime(DateFormat formatter) {
        return this.mTimePickerLayout.getFormattedTime(formatter);
    }

    private class TimePickerLayout extends FrameLayout implements View.OnClickListener, TimePicker.OnTimeChangedListener {
        private static final String BASE_TEXT = "0";
        private static final String FORMAT = "%02d";
        private static final String FORMAT_NO_LEADING_ZERO = "%d";
        private static final String TIME_DIVIDER = ":";
        private CircleCheckedTextView mAmView;
        private float mBaseHeight;
        private float mBaseY;
        private int mCheckBoxSize;
        private float mDividerX;
        private Path mHeaderBackground;
        private int mHeaderHeight;
        private int mHeaderRealHeight;
        private int mHeaderRealWidth;
        private String mHour;
        private float mHourWidth;
        private float mHourX;
        private boolean mIsAm = true;
        private boolean mIsLeadingZero = false;
        private boolean mLocationDirty = true;
        private String mMidday;
        private float mMiddayX;
        private String mMinute;
        private float mMinuteWidth;
        private float mMinuteX;
        private OnTimeChangedListener mOnTimeChangedListener;
        private Paint mPaint;
        private CircleCheckedTextView mPmView;
        private RectF mRect;
        private int mTextTimeColor = ViewCompat.MEASURED_STATE_MASK;
        private int mTextTimeSize;
        private TimePicker mTimePicker;

        public TimePickerLayout(Context context) {
            super(context);
            Paint paint = new Paint(1);
            this.mPaint = paint;
            paint.setTextAlign(Paint.Align.LEFT);
            this.mHeaderBackground = new Path();
            this.mRect = new RectF();
            this.mAmView = new CircleCheckedTextView(context);
            this.mPmView = new CircleCheckedTextView(context);
            TimePicker timePicker = new TimePicker(context);
            this.mTimePicker = timePicker;
            timePicker.setPadding(TimePickerDialog.this.mContentPadding, TimePickerDialog.this.mContentPadding, TimePickerDialog.this.mContentPadding, TimePickerDialog.this.mContentPadding);
            this.mTimePicker.setOnTimeChangedListener(this);
            this.mAmView.setGravity(17);
            this.mPmView.setGravity(17);
            if (Build.VERSION.SDK_INT >= 17) {
                this.mAmView.setTextAlignment(4);
                this.mPmView.setTextAlignment(4);
            }
            this.mAmView.setCheckedImmediately(this.mIsAm);
            this.mPmView.setCheckedImmediately(true ^ this.mIsAm);
            this.mAmView.setOnClickListener(this);
            this.mPmView.setOnClickListener(this);
            addView(this.mTimePicker);
            addView(this.mAmView);
            addView(this.mPmView);
            setWillNotDraw(false);
            this.mCheckBoxSize = ThemeUtil.dpToPx(context, 48);
            this.mHeaderHeight = ThemeUtil.dpToPx(context, 120);
            this.mTextTimeSize = context.getResources().getDimensionPixelOffset(C2500R.dimen.abc_text_size_headline_material);
        }

        public void applyStyle(int resId) {
            this.mTimePicker.applyStyle(resId);
            TypedArray a = getContext().obtainStyledAttributes(resId, C2500R.styleable.TimePickerDialog);
            String am = null;
            String pm = null;
            int count = a.getIndexCount();
            for (int i = 0; i < count; i++) {
                int attr = a.getIndex(i);
                if (attr == C2500R.styleable.TimePickerDialog_tp_headerHeight) {
                    this.mHeaderHeight = a.getDimensionPixelSize(attr, 0);
                } else if (attr == C2500R.styleable.TimePickerDialog_tp_textTimeColor) {
                    this.mTextTimeColor = a.getColor(attr, 0);
                } else if (attr == C2500R.styleable.TimePickerDialog_tp_textTimeSize) {
                    this.mTextTimeSize = a.getDimensionPixelSize(attr, 0);
                } else if (attr == C2500R.styleable.TimePickerDialog_tp_leadingZero) {
                    this.mIsLeadingZero = a.getBoolean(attr, false);
                } else if (attr == C2500R.styleable.TimePickerDialog_tp_am) {
                    am = a.getString(attr);
                } else if (attr == C2500R.styleable.TimePickerDialog_tp_pm) {
                    pm = a.getString(attr);
                }
            }
            a.recycle();
            if (am == null) {
                am = DateUtils.getAMPMString(0).toUpperCase();
            }
            if (pm == null) {
                pm = DateUtils.getAMPMString(1).toUpperCase();
            }
            int[][] states = {new int[]{-16842912}, new int[]{16842912}};
            int[] colors = {this.mTimePicker.getTextColor(), this.mTimePicker.getTextHighlightColor()};
            this.mAmView.setBackgroundColor(this.mTimePicker.getSelectionColor());
            this.mAmView.setAnimDuration(this.mTimePicker.getAnimDuration());
            this.mAmView.setInterpolator(this.mTimePicker.getInInterpolator(), this.mTimePicker.getOutInterpolator());
            this.mAmView.setTypeface(this.mTimePicker.getTypeface());
            this.mAmView.setTextSize(0, (float) this.mTimePicker.getTextSize());
            this.mAmView.setTextColor(new ColorStateList(states, colors));
            this.mAmView.setText(am);
            this.mPmView.setBackgroundColor(this.mTimePicker.getSelectionColor());
            this.mPmView.setAnimDuration(this.mTimePicker.getAnimDuration());
            this.mPmView.setInterpolator(this.mTimePicker.getInInterpolator(), this.mTimePicker.getOutInterpolator());
            this.mPmView.setTypeface(this.mTimePicker.getTypeface());
            this.mPmView.setTextSize(0, (float) this.mTimePicker.getTextSize());
            this.mPmView.setTextColor(new ColorStateList(states, colors));
            this.mPmView.setText(pm);
            this.mPaint.setTypeface(this.mTimePicker.getTypeface());
            String str = this.mIsLeadingZero ? FORMAT : FORMAT_NO_LEADING_ZERO;
            Object[] objArr = new Object[1];
            objArr[0] = Integer.valueOf((this.mTimePicker.is24Hour() || this.mTimePicker.getHour() != 0) ? this.mTimePicker.getHour() : 12);
            this.mHour = String.format(str, objArr);
            this.mMinute = String.format(FORMAT, new Object[]{Integer.valueOf(this.mTimePicker.getMinute())});
            if (!this.mTimePicker.is24Hour()) {
                this.mMidday = (this.mIsAm ? this.mAmView : this.mPmView).getText().toString();
            }
            this.mLocationDirty = true;
            invalidate(0, 0, this.mHeaderRealWidth, this.mHeaderRealHeight);
        }

        public void setHour(int hour) {
            if (!this.mTimePicker.is24Hour()) {
                if (hour <= 11 || hour >= 24) {
                    setAm(true, false);
                } else {
                    setAm(false, false);
                }
            }
            this.mTimePicker.setHour(hour);
        }

        public int getHour() {
            return (this.mTimePicker.is24Hour() || this.mIsAm) ? this.mTimePicker.getHour() : this.mTimePicker.getHour() + 12;
        }

        public void setMinute(int minute) {
            this.mTimePicker.setMinute(minute);
        }

        public int getMinute() {
            return this.mTimePicker.getMinute();
        }

        private void setAm(boolean am, boolean animation) {
            if (!this.mTimePicker.is24Hour() && this.mIsAm != am) {
                int oldHour = getHour();
                this.mIsAm = am;
                if (animation) {
                    this.mAmView.setChecked(am);
                    this.mPmView.setChecked(!this.mIsAm);
                } else {
                    this.mAmView.setCheckedImmediately(am);
                    this.mPmView.setCheckedImmediately(!this.mIsAm);
                }
                this.mMidday = (this.mIsAm ? this.mAmView : this.mPmView).getText().toString();
                invalidate(0, 0, this.mHeaderRealWidth, this.mHeaderRealHeight);
                OnTimeChangedListener onTimeChangedListener = this.mOnTimeChangedListener;
                if (onTimeChangedListener != null) {
                    onTimeChangedListener.onTimeChanged(oldHour, getMinute(), getHour(), getMinute());
                }
            }
        }

        public String getFormattedTime(DateFormat formatter) {
            Calendar cal = Calendar.getInstance();
            cal.set(11, getHour());
            cal.set(12, getMinute());
            cal.set(13, 0);
            cal.set(14, 0);
            return formatter.format(cal.getTime());
        }

        public void setOnTimeChangedListener(OnTimeChangedListener listener) {
            this.mOnTimeChangedListener = listener;
        }

        public void onClick(View v) {
            setAm(v == this.mAmView, true);
        }

        public void onModeChanged(int mode) {
            invalidate(0, 0, this.mHeaderRealWidth, this.mHeaderRealHeight);
        }

        public void onHourChanged(int oldValue, int newValue) {
            int oldHour = (this.mTimePicker.is24Hour() || this.mIsAm) ? oldValue : oldValue + 12;
            String str = this.mIsLeadingZero ? FORMAT : FORMAT_NO_LEADING_ZERO;
            Object[] objArr = new Object[1];
            objArr[0] = Integer.valueOf((this.mTimePicker.is24Hour() || newValue != 0) ? newValue : 12);
            this.mHour = String.format(str, objArr);
            this.mLocationDirty = true;
            invalidate(0, 0, this.mHeaderRealWidth, this.mHeaderRealHeight);
            OnTimeChangedListener onTimeChangedListener = this.mOnTimeChangedListener;
            if (onTimeChangedListener != null) {
                onTimeChangedListener.onTimeChanged(oldHour, getMinute(), getHour(), getMinute());
            }
        }

        public void onMinuteChanged(int oldValue, int newValue) {
            this.mMinute = String.format(FORMAT, new Object[]{Integer.valueOf(newValue)});
            this.mLocationDirty = true;
            invalidate(0, 0, this.mHeaderRealWidth, this.mHeaderRealHeight);
            OnTimeChangedListener onTimeChangedListener = this.mOnTimeChangedListener;
            if (onTimeChangedListener != null) {
                onTimeChangedListener.onTimeChanged(getHour(), oldValue, getHour(), newValue);
            }
        }

        /* access modifiers changed from: protected */
        public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
            int widthSize = View.MeasureSpec.getSize(widthMeasureSpec);
            int mode = View.MeasureSpec.getMode(widthMeasureSpec);
            int heightSize = View.MeasureSpec.getSize(heightMeasureSpec);
            int heightMode = View.MeasureSpec.getMode(heightMeasureSpec);
            boolean z = true;
            if (getContext().getResources().getConfiguration().orientation != 1) {
                z = false;
            }
            boolean isPortrait = z;
            int checkboxSize = this.mTimePicker.is24Hour() ? 0 : this.mCheckBoxSize;
            if (isPortrait) {
                if (heightMode == Integer.MIN_VALUE) {
                    heightSize = Math.min(heightSize, checkboxSize + widthSize + this.mHeaderHeight);
                }
                if (checkboxSize > 0) {
                    int spec = View.MeasureSpec.makeMeasureSpec(this.mCheckBoxSize, Ints.MAX_POWER_OF_TWO);
                    this.mAmView.setVisibility(0);
                    this.mPmView.setVisibility(0);
                    this.mAmView.measure(spec, spec);
                    this.mPmView.measure(spec, spec);
                } else {
                    this.mAmView.setVisibility(8);
                    this.mPmView.setVisibility(8);
                }
                int spec2 = View.MeasureSpec.makeMeasureSpec(widthSize, Ints.MAX_POWER_OF_TWO);
                this.mTimePicker.measure(spec2, spec2);
                setMeasuredDimension(widthSize, heightSize);
                return;
            }
            int halfWidth = widthSize / 2;
            if (heightMode == Integer.MIN_VALUE) {
                int i = this.mHeaderHeight;
                if (checkboxSize > 0) {
                    i = i + checkboxSize + TimePickerDialog.this.mContentPadding;
                }
                heightSize = Math.min(heightSize, Math.max(i, halfWidth));
            }
            if (checkboxSize > 0) {
                int spec3 = View.MeasureSpec.makeMeasureSpec(checkboxSize, Ints.MAX_POWER_OF_TWO);
                this.mAmView.setVisibility(0);
                this.mPmView.setVisibility(0);
                this.mAmView.measure(spec3, spec3);
                this.mPmView.measure(spec3, spec3);
            } else {
                this.mAmView.setVisibility(8);
                this.mPmView.setVisibility(8);
            }
            int spec4 = View.MeasureSpec.makeMeasureSpec(Math.min(halfWidth, heightSize), Ints.MAX_POWER_OF_TWO);
            this.mTimePicker.measure(spec4, spec4);
            setMeasuredDimension(widthSize, heightSize);
        }

        /* access modifiers changed from: protected */
        public void onSizeChanged(int w, int h, int oldw, int oldh) {
            int i = w;
            boolean isPortrait = getContext().getResources().getConfiguration().orientation == 1;
            this.mLocationDirty = true;
            int checkboxSize = this.mTimePicker.is24Hour() ? 0 : this.mCheckBoxSize;
            if (isPortrait) {
                this.mHeaderRealWidth = i;
                this.mHeaderRealHeight = (h - checkboxSize) - i;
                this.mHeaderBackground.reset();
                if (TimePickerDialog.this.mCornerRadius == 0.0f) {
                    this.mHeaderBackground.addRect(0.0f, 0.0f, (float) this.mHeaderRealWidth, (float) this.mHeaderRealHeight, Path.Direction.CW);
                    return;
                }
                this.mHeaderBackground.moveTo(0.0f, (float) this.mHeaderRealHeight);
                this.mHeaderBackground.lineTo(0.0f, TimePickerDialog.this.mCornerRadius);
                this.mRect.set(0.0f, 0.0f, TimePickerDialog.this.mCornerRadius * 2.0f, TimePickerDialog.this.mCornerRadius * 2.0f);
                this.mHeaderBackground.arcTo(this.mRect, 180.0f, 90.0f, false);
                this.mHeaderBackground.lineTo(((float) this.mHeaderRealWidth) - TimePickerDialog.this.mCornerRadius, 0.0f);
                this.mRect.set(((float) this.mHeaderRealWidth) - (TimePickerDialog.this.mCornerRadius * 2.0f), 0.0f, (float) this.mHeaderRealWidth, TimePickerDialog.this.mCornerRadius * 2.0f);
                this.mHeaderBackground.arcTo(this.mRect, 270.0f, 90.0f, false);
                this.mHeaderBackground.lineTo((float) this.mHeaderRealWidth, (float) this.mHeaderRealHeight);
                this.mHeaderBackground.close();
                return;
            }
            this.mHeaderRealWidth = i / 2;
            this.mHeaderRealHeight = checkboxSize > 0 ? (h - checkboxSize) - TimePickerDialog.this.mContentPadding : h;
            this.mHeaderBackground.reset();
            if (TimePickerDialog.this.mCornerRadius == 0.0f) {
                this.mHeaderBackground.addRect(0.0f, 0.0f, (float) this.mHeaderRealWidth, (float) this.mHeaderRealHeight, Path.Direction.CW);
                return;
            }
            this.mHeaderBackground.moveTo(0.0f, (float) this.mHeaderRealHeight);
            this.mHeaderBackground.lineTo(0.0f, TimePickerDialog.this.mCornerRadius);
            this.mRect.set(0.0f, 0.0f, TimePickerDialog.this.mCornerRadius * 2.0f, TimePickerDialog.this.mCornerRadius * 2.0f);
            this.mHeaderBackground.arcTo(this.mRect, 180.0f, 90.0f, false);
            this.mHeaderBackground.lineTo((float) this.mHeaderRealWidth, 0.0f);
            this.mHeaderBackground.lineTo((float) this.mHeaderRealWidth, (float) this.mHeaderRealHeight);
            this.mHeaderBackground.close();
        }

        /* access modifiers changed from: protected */
        public void onLayout(boolean changed, int left, int top, int right, int bottom) {
            int childRight = right - left;
            int childBottom = bottom - top;
            int checkboxSize = 0;
            boolean z = true;
            if (getContext().getResources().getConfiguration().orientation != 1) {
                z = false;
            }
            boolean isPortrait = z;
            if (!this.mTimePicker.is24Hour()) {
                checkboxSize = this.mCheckBoxSize;
            }
            if (isPortrait) {
                int paddingHorizontal = TimePickerDialog.this.mContentPadding + TimePickerDialog.this.mActionPadding;
                int paddingVertical = TimePickerDialog.this.mContentPadding - TimePickerDialog.this.mActionPadding;
                if (checkboxSize > 0) {
                    this.mAmView.layout(0 + paddingHorizontal, (childBottom - paddingVertical) - checkboxSize, 0 + paddingHorizontal + checkboxSize, childBottom - paddingVertical);
                    this.mPmView.layout((childRight - paddingHorizontal) - checkboxSize, (childBottom - paddingVertical) - checkboxSize, childRight - paddingHorizontal, childBottom - paddingVertical);
                }
                this.mTimePicker.layout(0, 0 + this.mHeaderRealHeight, childRight, childBottom - checkboxSize);
                return;
            }
            int paddingHorizontal2 = ((childRight / 2) - this.mTimePicker.getMeasuredWidth()) / 2;
            int paddingVertical2 = (childBottom - this.mTimePicker.getMeasuredHeight()) / 2;
            TimePicker timePicker = this.mTimePicker;
            timePicker.layout((childRight - paddingHorizontal2) - timePicker.getMeasuredWidth(), 0 + paddingVertical2, childRight - paddingHorizontal2, 0 + paddingVertical2 + this.mTimePicker.getMeasuredHeight());
            if (checkboxSize > 0) {
                int childRight2 = childRight / 2;
                int paddingHorizontal3 = TimePickerDialog.this.mContentPadding + TimePickerDialog.this.mActionPadding;
                int paddingVertical3 = TimePickerDialog.this.mContentPadding - TimePickerDialog.this.mActionPadding;
                this.mAmView.layout(0 + paddingHorizontal3, (childBottom - paddingVertical3) - checkboxSize, 0 + paddingHorizontal3 + checkboxSize, childBottom - paddingVertical3);
                this.mPmView.layout((childRight2 - paddingHorizontal3) - checkboxSize, (childBottom - paddingVertical3) - checkboxSize, childRight2 - paddingHorizontal3, childBottom - paddingVertical3);
            }
        }

        private void measureTimeText() {
            if (this.mLocationDirty) {
                this.mPaint.setTextSize((float) this.mTextTimeSize);
                Rect bounds = new Rect();
                this.mPaint.getTextBounds(BASE_TEXT, 0, BASE_TEXT.length(), bounds);
                float height = (float) bounds.height();
                this.mBaseHeight = height;
                this.mBaseY = (((float) this.mHeaderRealHeight) + height) / 2.0f;
                float dividerWidth = this.mPaint.measureText(TIME_DIVIDER, 0, TIME_DIVIDER.length());
                Paint paint = this.mPaint;
                String str = this.mHour;
                this.mHourWidth = paint.measureText(str, 0, str.length());
                Paint paint2 = this.mPaint;
                String str2 = this.mMinute;
                float measureText = paint2.measureText(str2, 0, str2.length());
                this.mMinuteWidth = measureText;
                float f = (((float) this.mHeaderRealWidth) - dividerWidth) / 2.0f;
                this.mDividerX = f;
                this.mHourX = f - this.mHourWidth;
                float f2 = f + dividerWidth;
                this.mMinuteX = f2;
                this.mMiddayX = f2 + measureText;
                this.mLocationDirty = false;
            }
        }

        public void draw(Canvas canvas) {
            super.draw(canvas);
            this.mPaint.setStyle(Paint.Style.FILL);
            this.mPaint.setColor(this.mTimePicker.getSelectionColor());
            canvas.drawPath(this.mHeaderBackground, this.mPaint);
            measureTimeText();
            this.mPaint.setTextSize((float) this.mTextTimeSize);
            this.mPaint.setColor(this.mTimePicker.getMode() == 0 ? this.mTimePicker.getTextHighlightColor() : this.mTextTimeColor);
            canvas.drawText(this.mHour, this.mHourX, this.mBaseY, this.mPaint);
            this.mPaint.setColor(this.mTextTimeColor);
            canvas.drawText(TIME_DIVIDER, this.mDividerX, this.mBaseY, this.mPaint);
            this.mPaint.setColor(this.mTimePicker.getMode() == 1 ? this.mTimePicker.getTextHighlightColor() : this.mTextTimeColor);
            canvas.drawText(this.mMinute, this.mMinuteX, this.mBaseY, this.mPaint);
            if (!this.mTimePicker.is24Hour()) {
                this.mPaint.setTextSize((float) this.mTimePicker.getTextSize());
                this.mPaint.setColor(this.mTextTimeColor);
                canvas.drawText(this.mMidday, this.mMiddayX, this.mBaseY, this.mPaint);
            }
        }

        private boolean isTouched(float left, float top, float right, float bottom, float x, float y) {
            return x >= left && x <= right && y >= top && y <= bottom;
        }

        public boolean onTouchEvent(MotionEvent event) {
            boolean handled = super.onTouchEvent(event);
            if (handled) {
                return handled;
            }
            int action = event.getAction();
            if (action == 0) {
                float f = this.mHourX;
                float f2 = this.mBaseY;
                if (!isTouched(f, f2 - this.mBaseHeight, f + this.mHourWidth, f2, event.getX(), event.getY())) {
                    float f3 = this.mMinuteX;
                    float f4 = this.mBaseY;
                    if (!isTouched(f3, f4 - this.mBaseHeight, f3 + this.mMinuteWidth, f4, event.getX(), event.getY()) || this.mTimePicker.getMode() != 0) {
                        return false;
                    }
                    return true;
                } else if (this.mTimePicker.getMode() == 1) {
                    return true;
                } else {
                    return false;
                }
            } else if (action == 1) {
                float f5 = this.mHourX;
                float f6 = this.mBaseY;
                if (isTouched(f5, f6 - this.mBaseHeight, f5 + this.mHourWidth, f6, event.getX(), event.getY())) {
                    this.mTimePicker.setMode(0, true);
                }
                float f7 = this.mMinuteX;
                float f8 = this.mBaseY;
                if (isTouched(f7, f8 - this.mBaseHeight, f7 + this.mMinuteWidth, f8, event.getX(), event.getY())) {
                    this.mTimePicker.setMode(1, true);
                }
            }
            return false;
        }
    }

    public static class Builder extends Dialog.Builder implements OnTimeChangedListener {
        public static final Parcelable.Creator<Builder> CREATOR = new Parcelable.Creator<Builder>() {
            public Builder createFromParcel(Parcel in) {
                return new Builder(in);
            }

            public Builder[] newArray(int size) {
                return new Builder[size];
            }
        };
        protected int mHour;
        protected int mMinute;

        public Builder() {
            super(C2500R.C2505style.Material_App_Dialog_TimePicker_Light);
            Calendar cal = Calendar.getInstance();
            this.mHour = cal.get(11);
            this.mMinute = cal.get(12);
        }

        public Builder(int hourOfDay, int minute) {
            this(C2500R.C2505style.Material_App_Dialog_TimePicker_Light, hourOfDay, minute);
        }

        public Builder(int styleId, int hourOfDay, int minute) {
            super(styleId);
            hour(hourOfDay);
            minute(minute);
        }

        public Builder hour(int hour) {
            this.mHour = Math.min(Math.max(hour, 0), 24);
            return this;
        }

        public Builder minute(int minute) {
            this.mMinute = minute;
            return this;
        }

        public int getHour() {
            return this.mHour;
        }

        public int getMinute() {
            return this.mMinute;
        }

        public Dialog.Builder contentView(int layoutId) {
            return this;
        }

        /* access modifiers changed from: protected */
        public Dialog onBuild(Context context, int styleId) {
            TimePickerDialog dialog = new TimePickerDialog(context, styleId);
            dialog.hour(this.mHour).minute(this.mMinute).onTimeChangedListener(this);
            return dialog;
        }

        public void onTimeChanged(int oldHour, int oldMinute, int newHour, int newMinute) {
            hour(newHour).minute(newMinute);
        }

        protected Builder(Parcel in) {
            super(in);
        }

        /* access modifiers changed from: protected */
        public void onWriteToParcel(Parcel dest, int flags) {
            dest.writeInt(this.mHour);
            dest.writeInt(this.mMinute);
        }

        /* access modifiers changed from: protected */
        public void onReadFromParcel(Parcel in) {
            this.mHour = in.readInt();
            this.mMinute = in.readInt();
        }
    }
}
