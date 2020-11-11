package com.rey.material.app;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Parcel;
import android.os.Parcelable;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.FrameLayout;
import androidx.core.view.ViewCompat;
import com.google.common.primitives.Ints;
import com.rey.material.C2500R;
import com.rey.material.app.Dialog;
import com.rey.material.util.ThemeUtil;
import com.rey.material.widget.DatePicker;
import com.rey.material.widget.YearPicker;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class DatePickerDialog extends Dialog {
    /* access modifiers changed from: private */
    public float mCornerRadius;
    private DatePickerLayout mDatePickerLayout;
    /* access modifiers changed from: private */
    public OnDateChangedListener mOnDateChangedListener;

    public interface OnDateChangedListener {
        void onDateChanged(int i, int i2, int i3, int i4, int i5, int i6);
    }

    public DatePickerDialog(Context context) {
        super(context, C2500R.C2505style.Material_App_Dialog_DatePicker_Light);
    }

    public DatePickerDialog(Context context, int style) {
        super(context, style);
    }

    /* access modifiers changed from: protected */
    public void onCreate() {
        DatePickerLayout datePickerLayout = new DatePickerLayout(getContext());
        this.mDatePickerLayout = datePickerLayout;
        contentView((View) datePickerLayout);
    }

    public Dialog applyStyle(int resId) {
        super.applyStyle(resId);
        if (resId == 0) {
            return this;
        }
        this.mDatePickerLayout.applyStyle(resId);
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

    public DatePickerDialog dateRange(int minDay, int minMonth, int minYear, int maxDay, int maxMonth, int maxYear) {
        this.mDatePickerLayout.setDateRange(minDay, minMonth, minYear, maxDay, maxMonth, maxYear);
        return this;
    }

    public DatePickerDialog dateRange(long minTime, long maxTime) {
        this.mDatePickerLayout.setDateRange(minTime, maxTime);
        return this;
    }

    public DatePickerDialog date(int day, int month, int year) {
        this.mDatePickerLayout.setDate(day, month, year);
        return this;
    }

    public DatePickerDialog date(long time) {
        this.mDatePickerLayout.setDate(time);
        return this;
    }

    public DatePickerDialog onDateChangedListener(OnDateChangedListener listener) {
        this.mOnDateChangedListener = listener;
        return this;
    }

    public int getDay() {
        return this.mDatePickerLayout.getDay();
    }

    public int getMonth() {
        return this.mDatePickerLayout.getMonth();
    }

    public int getYear() {
        return this.mDatePickerLayout.getYear();
    }

    public long getDate() {
        Calendar cal = getCalendar();
        cal.set(14, 0);
        cal.set(13, 0);
        cal.set(12, 0);
        cal.set(10, 0);
        cal.set(5, getDay());
        cal.set(2, getMonth());
        cal.set(1, getYear());
        return cal.getTimeInMillis();
    }

    public Calendar getCalendar() {
        return this.mDatePickerLayout.getCalendar();
    }

    public String getFormattedDate(DateFormat formatter) {
        return this.mDatePickerLayout.getFormattedDate(formatter);
    }

    private class DatePickerLayout extends FrameLayout implements DatePicker.OnDateChangedListener, YearPicker.OnYearChangedListener {
        private static final String BASE_TEXT = "0";
        private static final String DAY_FORMAT = "%2d";
        private static final String YEAR_FORMAT = "%4d";
        private float mBaseX;
        private float mCenterY;
        private DatePicker mDatePicker;
        private String mDay;
        private boolean mDaySelectMode = true;
        private float mDayY;
        private float mFirstWidth;
        private int mHeaderPrimaryColor;
        private int mHeaderPrimaryHeight;
        private int mHeaderPrimaryRealHeight;
        private int mHeaderPrimaryTextSize;
        private int mHeaderRealWidth;
        private Path mHeaderSecondaryBackground;
        private int mHeaderSecondaryColor;
        private int mHeaderSecondaryHeight;
        private int mHeaderSecondaryTextSize;
        private boolean mLocationDirty = true;
        private String mMonth;
        private boolean mMonthFirst = true;
        private float mMonthY;
        private int mPadding;
        private Paint mPaint;
        private RectF mRect;
        private float mSecondWidth;
        private int mTextHeaderColor = ViewCompat.MEASURED_STATE_MASK;
        private String mWeekDay;
        private float mWeekDayY;
        private String mYear;
        private YearPicker mYearPicker;
        private float mYearY;

        public DatePickerLayout(Context context) {
            super(context);
            Paint paint = new Paint(1);
            this.mPaint = paint;
            paint.setStyle(Paint.Style.FILL);
            this.mPaint.setTextAlign(Paint.Align.CENTER);
            this.mRect = new RectF();
            this.mHeaderSecondaryBackground = new Path();
            int i = 8;
            this.mPadding = ThemeUtil.dpToPx(context, 8);
            this.mYearPicker = new YearPicker(context);
            this.mDatePicker = new DatePicker(context);
            YearPicker yearPicker = this.mYearPicker;
            int i2 = this.mPadding;
            yearPicker.setPadding(i2, i2, i2, i2);
            this.mYearPicker.setOnYearChangedListener(this);
            DatePicker datePicker = this.mDatePicker;
            int i3 = this.mPadding;
            datePicker.setContentPadding(i3, i3, i3, i3);
            this.mDatePicker.setOnDateChangedListener(this);
            addView(this.mDatePicker);
            addView(this.mYearPicker);
            this.mYearPicker.setVisibility(this.mDaySelectMode ? 8 : 0);
            this.mDatePicker.setVisibility(this.mDaySelectMode ? 0 : i);
            this.mMonthFirst = isMonthFirst();
            setWillNotDraw(false);
            this.mHeaderPrimaryHeight = ThemeUtil.dpToPx(context, 144);
            this.mHeaderSecondaryHeight = ThemeUtil.dpToPx(context, 32);
            this.mHeaderPrimaryTextSize = context.getResources().getDimensionPixelOffset(C2500R.dimen.abc_text_size_display_2_material);
            this.mHeaderSecondaryTextSize = context.getResources().getDimensionPixelOffset(C2500R.dimen.abc_text_size_headline_material);
        }

        private boolean isMonthFirst() {
            String pattern = ((SimpleDateFormat) SimpleDateFormat.getDateInstance(0)).toLocalizedPattern();
            if (pattern.indexOf("M") < pattern.indexOf("d")) {
                return true;
            }
            return false;
        }

        public void setDateSelectMode(boolean enable) {
            if (this.mDaySelectMode != enable) {
                this.mDaySelectMode = enable;
                if (enable) {
                    DatePicker datePicker = this.mDatePicker;
                    datePicker.goTo(datePicker.getMonth(), this.mDatePicker.getYear());
                    animOut(this.mYearPicker);
                    animIn(this.mDatePicker);
                } else {
                    YearPicker yearPicker = this.mYearPicker;
                    yearPicker.goTo(yearPicker.getYear());
                    animOut(this.mDatePicker);
                    animIn(this.mYearPicker);
                }
                invalidate(0, 0, this.mHeaderRealWidth, this.mHeaderPrimaryRealHeight + this.mHeaderSecondaryHeight);
            }
        }

        private void animOut(final View v) {
            Animation anim = new AlphaAnimation(1.0f, 0.0f);
            anim.setDuration((long) getContext().getResources().getInteger(17694721));
            anim.setAnimationListener(new Animation.AnimationListener() {
                public void onAnimationStart(Animation animation) {
                }

                public void onAnimationEnd(Animation animation) {
                    v.setVisibility(8);
                }

                public void onAnimationRepeat(Animation animation) {
                }
            });
            v.startAnimation(anim);
        }

        private void animIn(final View v) {
            Animation anim = new AlphaAnimation(0.0f, 1.0f);
            anim.setDuration((long) getContext().getResources().getInteger(17694721));
            anim.setAnimationListener(new Animation.AnimationListener() {
                public void onAnimationStart(Animation animation) {
                    v.setVisibility(0);
                }

                public void onAnimationEnd(Animation animation) {
                }

                public void onAnimationRepeat(Animation animation) {
                }
            });
            v.startAnimation(anim);
        }

        public void applyStyle(int resId) {
            this.mYearPicker.applyStyle(resId);
            this.mDatePicker.applyStyle(resId);
            int selectionColor = this.mDatePicker.getSelectionColor();
            this.mHeaderPrimaryColor = selectionColor;
            this.mHeaderSecondaryColor = selectionColor;
            TypedArray a = getContext().obtainStyledAttributes(resId, C2500R.styleable.DatePickerDialog);
            int count = a.getIndexCount();
            for (int i = 0; i < count; i++) {
                int attr = a.getIndex(i);
                if (attr == C2500R.styleable.DatePickerDialog_dp_headerPrimaryHeight) {
                    this.mHeaderPrimaryHeight = a.getDimensionPixelSize(attr, 0);
                } else if (attr == C2500R.styleable.DatePickerDialog_dp_headerSecondaryHeight) {
                    this.mHeaderSecondaryHeight = a.getDimensionPixelSize(attr, 0);
                } else if (attr == C2500R.styleable.DatePickerDialog_dp_headerPrimaryColor) {
                    this.mHeaderPrimaryColor = a.getColor(attr, 0);
                } else if (attr == C2500R.styleable.DatePickerDialog_dp_headerSecondaryColor) {
                    this.mHeaderSecondaryColor = a.getColor(attr, 0);
                } else if (attr == C2500R.styleable.DatePickerDialog_dp_headerPrimaryTextSize) {
                    this.mHeaderPrimaryTextSize = a.getDimensionPixelSize(attr, 0);
                } else if (attr == C2500R.styleable.DatePickerDialog_dp_headerSecondaryTextSize) {
                    this.mHeaderSecondaryTextSize = a.getDimensionPixelSize(attr, 0);
                } else if (attr == C2500R.styleable.DatePickerDialog_dp_textHeaderColor) {
                    this.mTextHeaderColor = a.getColor(attr, 0);
                }
            }
            a.recycle();
            this.mPaint.setTypeface(this.mDatePicker.getTypeface());
        }

        public void setDateRange(int minDay, int minMonth, int minYear, int maxDay, int maxMonth, int maxYear) {
            this.mDatePicker.setDateRange(minDay, minMonth, minYear, maxDay, maxMonth, maxYear);
            this.mYearPicker.setYearRange(minYear, maxYear);
        }

        public void setDateRange(long minTime, long maxTime) {
            Calendar cal = this.mDatePicker.getCalendar();
            cal.setTimeInMillis(minTime);
            int minDay = cal.get(5);
            int minMonth = cal.get(2);
            int minYear = cal.get(1);
            cal.setTimeInMillis(maxTime);
            setDateRange(minDay, minMonth, minYear, cal.get(5), cal.get(2), cal.get(1));
        }

        public void setDate(int day, int month, int year) {
            this.mDatePicker.setDate(day, month, year);
        }

        public void setDate(long time) {
            Calendar cal = this.mDatePicker.getCalendar();
            cal.setTimeInMillis(time);
            this.mDatePicker.setDate(cal.get(5), cal.get(2), cal.get(1));
        }

        public int getDay() {
            return this.mDatePicker.getDay();
        }

        public int getMonth() {
            return this.mDatePicker.getMonth();
        }

        public int getYear() {
            return this.mDatePicker.getYear();
        }

        public String getFormattedDate(DateFormat formatter) {
            return this.mDatePicker.getFormattedDate(formatter);
        }

        public Calendar getCalendar() {
            return this.mDatePicker.getCalendar();
        }

        public void onYearChanged(int oldYear, int newYear) {
            if (!this.mDaySelectMode) {
                DatePicker datePicker = this.mDatePicker;
                datePicker.setDate(datePicker.getDay(), this.mDatePicker.getMonth(), newYear);
            }
        }

        public void onDateChanged(int oldDay, int oldMonth, int oldYear, int newDay, int newMonth, int newYear) {
            if (this.mDaySelectMode) {
                this.mYearPicker.setYear(newYear);
            }
            if (newDay < 0 || newMonth < 0 || newYear < 0) {
                this.mWeekDay = null;
                this.mMonth = null;
                this.mDay = null;
                this.mYear = null;
            } else {
                Calendar cal = this.mDatePicker.getCalendar();
                cal.set(1, newYear);
                cal.set(2, newMonth);
                cal.set(5, newDay);
                this.mWeekDay = cal.getDisplayName(7, 2, Locale.getDefault());
                this.mMonth = cal.getDisplayName(2, 1, Locale.getDefault());
                this.mDay = String.format(DAY_FORMAT, new Object[]{Integer.valueOf(newDay)});
                this.mYear = String.format(YEAR_FORMAT, new Object[]{Integer.valueOf(newYear)});
                if (!(oldMonth == newMonth && oldYear == newYear)) {
                    this.mDatePicker.goTo(newMonth, newYear);
                }
            }
            this.mLocationDirty = true;
            invalidate(0, 0, this.mHeaderRealWidth, this.mHeaderPrimaryRealHeight + this.mHeaderSecondaryHeight);
            if (DatePickerDialog.this.mOnDateChangedListener != null) {
                DatePickerDialog.this.mOnDateChangedListener.onDateChanged(oldDay, oldMonth, oldYear, newDay, newMonth, newYear);
            }
        }

        /* access modifiers changed from: protected */
        public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
            int widthSize = View.MeasureSpec.getSize(widthMeasureSpec);
            int mode = View.MeasureSpec.getMode(widthMeasureSpec);
            int heightSize = View.MeasureSpec.getSize(heightMeasureSpec);
            int heightMode = View.MeasureSpec.getMode(heightMeasureSpec);
            boolean isPortrait = true;
            if (getContext().getResources().getConfiguration().orientation != 1) {
                isPortrait = false;
            }
            if (isPortrait) {
                if (heightMode == Integer.MIN_VALUE) {
                    int ws = View.MeasureSpec.makeMeasureSpec(widthSize, Ints.MAX_POWER_OF_TWO);
                    this.mDatePicker.measure(ws, View.MeasureSpec.makeMeasureSpec(0, 0));
                    this.mYearPicker.measure(ws, ws);
                } else {
                    int height = Math.max((heightSize - this.mHeaderSecondaryHeight) - this.mHeaderPrimaryHeight, this.mDatePicker.getMeasuredHeight());
                    int ws2 = View.MeasureSpec.makeMeasureSpec(widthSize, Ints.MAX_POWER_OF_TWO);
                    this.mDatePicker.measure(ws2, View.MeasureSpec.makeMeasureSpec(height, Ints.MAX_POWER_OF_TWO));
                    this.mYearPicker.measure(ws2, View.MeasureSpec.makeMeasureSpec(0, 0));
                    if (this.mYearPicker.getMeasuredHeight() != height) {
                        YearPicker yearPicker = this.mYearPicker;
                        yearPicker.measure(ws2, View.MeasureSpec.makeMeasureSpec(Math.min(yearPicker.getMeasuredHeight(), height), Ints.MAX_POWER_OF_TWO));
                    }
                }
                setMeasuredDimension(widthSize, heightSize);
                return;
            }
            if (heightMode == Integer.MIN_VALUE) {
                int ws3 = View.MeasureSpec.makeMeasureSpec(widthSize / 2, Ints.MAX_POWER_OF_TWO);
                this.mDatePicker.measure(ws3, View.MeasureSpec.makeMeasureSpec(0, 0));
                this.mYearPicker.measure(ws3, ws3);
            } else {
                int height2 = Math.max(heightSize, this.mDatePicker.getMeasuredHeight());
                int ws4 = View.MeasureSpec.makeMeasureSpec(widthSize / 2, Ints.MAX_POWER_OF_TWO);
                this.mDatePicker.measure(ws4, View.MeasureSpec.makeMeasureSpec(height2, Ints.MAX_POWER_OF_TWO));
                this.mYearPicker.measure(ws4, View.MeasureSpec.makeMeasureSpec(0, 0));
                if (this.mYearPicker.getMeasuredHeight() != height2) {
                    YearPicker yearPicker2 = this.mYearPicker;
                    yearPicker2.measure(ws4, View.MeasureSpec.makeMeasureSpec(Math.min(yearPicker2.getMeasuredHeight(), height2), Ints.MAX_POWER_OF_TWO));
                }
            }
            setMeasuredDimension(widthSize, heightSize);
        }

        /* access modifiers changed from: protected */
        public void onSizeChanged(int w, int h, int oldw, int oldh) {
            int i = w;
            boolean isPortrait = true;
            if (getContext().getResources().getConfiguration().orientation != 1) {
                isPortrait = false;
            }
            if (isPortrait) {
                this.mHeaderRealWidth = i;
                this.mHeaderPrimaryRealHeight = (h - this.mHeaderSecondaryHeight) - this.mDatePicker.getMeasuredHeight();
                this.mHeaderSecondaryBackground.reset();
                if (DatePickerDialog.this.mCornerRadius == 0.0f) {
                    this.mHeaderSecondaryBackground.addRect(0.0f, 0.0f, (float) this.mHeaderRealWidth, (float) this.mHeaderSecondaryHeight, Path.Direction.CW);
                    return;
                }
                this.mHeaderSecondaryBackground.moveTo(0.0f, (float) this.mHeaderSecondaryHeight);
                this.mHeaderSecondaryBackground.lineTo(0.0f, DatePickerDialog.this.mCornerRadius);
                this.mRect.set(0.0f, 0.0f, DatePickerDialog.this.mCornerRadius * 2.0f, DatePickerDialog.this.mCornerRadius * 2.0f);
                this.mHeaderSecondaryBackground.arcTo(this.mRect, 180.0f, 90.0f, false);
                this.mHeaderSecondaryBackground.lineTo(((float) this.mHeaderRealWidth) - DatePickerDialog.this.mCornerRadius, 0.0f);
                this.mRect.set(((float) this.mHeaderRealWidth) - (DatePickerDialog.this.mCornerRadius * 2.0f), 0.0f, (float) this.mHeaderRealWidth, DatePickerDialog.this.mCornerRadius * 2.0f);
                this.mHeaderSecondaryBackground.arcTo(this.mRect, 270.0f, 90.0f, false);
                this.mHeaderSecondaryBackground.lineTo((float) this.mHeaderRealWidth, (float) this.mHeaderSecondaryHeight);
                this.mHeaderSecondaryBackground.close();
                return;
            }
            this.mHeaderRealWidth = i - this.mDatePicker.getMeasuredWidth();
            this.mHeaderPrimaryRealHeight = h - this.mHeaderSecondaryHeight;
            this.mHeaderSecondaryBackground.reset();
            if (DatePickerDialog.this.mCornerRadius == 0.0f) {
                this.mHeaderSecondaryBackground.addRect(0.0f, 0.0f, (float) this.mHeaderRealWidth, (float) this.mHeaderSecondaryHeight, Path.Direction.CW);
                return;
            }
            this.mHeaderSecondaryBackground.moveTo(0.0f, (float) this.mHeaderSecondaryHeight);
            this.mHeaderSecondaryBackground.lineTo(0.0f, DatePickerDialog.this.mCornerRadius);
            this.mRect.set(0.0f, 0.0f, DatePickerDialog.this.mCornerRadius * 2.0f, DatePickerDialog.this.mCornerRadius * 2.0f);
            this.mHeaderSecondaryBackground.arcTo(this.mRect, 180.0f, 90.0f, false);
            this.mHeaderSecondaryBackground.lineTo((float) this.mHeaderRealWidth, 0.0f);
            this.mHeaderSecondaryBackground.lineTo((float) this.mHeaderRealWidth, (float) this.mHeaderSecondaryHeight);
            this.mHeaderSecondaryBackground.close();
        }

        /* access modifiers changed from: protected */
        public void onLayout(boolean changed, int left, int top, int right, int bottom) {
            int childLeft = 0;
            int childTop = 0;
            int childRight = right - left;
            int childBottom = bottom - top;
            boolean isPortrait = true;
            if (getContext().getResources().getConfiguration().orientation != 1) {
                isPortrait = false;
            }
            if (isPortrait) {
                childTop = 0 + this.mHeaderPrimaryRealHeight + this.mHeaderSecondaryHeight;
            } else {
                childLeft = 0 + this.mHeaderRealWidth;
            }
            this.mDatePicker.layout(childLeft, childTop, childRight, childBottom);
            int childTop2 = ((childBottom + childTop) - this.mYearPicker.getMeasuredHeight()) / 2;
            YearPicker yearPicker = this.mYearPicker;
            yearPicker.layout(childLeft, childTop2, childRight, yearPicker.getMeasuredHeight() + childTop2);
        }

        private void measureHeaderText() {
            if (this.mLocationDirty) {
                if (this.mWeekDay == null) {
                    this.mLocationDirty = false;
                    return;
                }
                this.mBaseX = ((float) this.mHeaderRealWidth) / 2.0f;
                Rect bounds = new Rect();
                this.mPaint.setTextSize((float) this.mDatePicker.getTextSize());
                this.mPaint.getTextBounds(BASE_TEXT, 0, BASE_TEXT.length(), bounds);
                this.mWeekDayY = ((float) (this.mHeaderSecondaryHeight + bounds.height())) / 2.0f;
                this.mPaint.setTextSize((float) this.mHeaderPrimaryTextSize);
                this.mPaint.getTextBounds(BASE_TEXT, 0, BASE_TEXT.length(), bounds);
                int primaryTextHeight = bounds.height();
                if (this.mMonthFirst) {
                    Paint paint = this.mPaint;
                    String str = this.mDay;
                    this.mFirstWidth = paint.measureText(str, 0, str.length());
                } else {
                    Paint paint2 = this.mPaint;
                    String str2 = this.mMonth;
                    this.mFirstWidth = paint2.measureText(str2, 0, str2.length());
                }
                this.mPaint.setTextSize((float) this.mHeaderSecondaryTextSize);
                this.mPaint.getTextBounds(BASE_TEXT, 0, BASE_TEXT.length(), bounds);
                int secondaryTextHeight = bounds.height();
                if (this.mMonthFirst) {
                    float f = this.mFirstWidth;
                    Paint paint3 = this.mPaint;
                    String str3 = this.mMonth;
                    this.mFirstWidth = Math.max(f, paint3.measureText(str3, 0, str3.length()));
                } else {
                    float f2 = this.mFirstWidth;
                    Paint paint4 = this.mPaint;
                    String str4 = this.mDay;
                    this.mFirstWidth = Math.max(f2, paint4.measureText(str4, 0, str4.length()));
                }
                Paint paint5 = this.mPaint;
                String str5 = this.mYear;
                this.mSecondWidth = paint5.measureText(str5, 0, str5.length());
                int i = this.mHeaderSecondaryHeight;
                int i2 = this.mHeaderPrimaryRealHeight;
                float f3 = ((float) i) + (((float) (i2 + primaryTextHeight)) / 2.0f);
                this.mCenterY = f3;
                float y = ((((float) (i2 - primaryTextHeight)) / 2.0f) + ((float) secondaryTextHeight)) / 2.0f;
                float aboveY = ((float) i) + y;
                float belowY = f3 + y;
                if (this.mMonthFirst) {
                    this.mDayY = f3;
                    this.mMonthY = aboveY;
                } else {
                    this.mMonthY = f3;
                    this.mDayY = aboveY;
                }
                this.mYearY = belowY;
                this.mLocationDirty = false;
            }
        }

        public void draw(Canvas canvas) {
            super.draw(canvas);
            this.mPaint.setColor(this.mHeaderSecondaryColor);
            canvas.drawPath(this.mHeaderSecondaryBackground, this.mPaint);
            this.mPaint.setColor(this.mHeaderPrimaryColor);
            int i = this.mHeaderSecondaryHeight;
            canvas.drawRect(0.0f, (float) i, (float) this.mHeaderRealWidth, (float) (this.mHeaderPrimaryRealHeight + i), this.mPaint);
            measureHeaderText();
            if (this.mWeekDay != null) {
                this.mPaint.setTextSize((float) this.mDatePicker.getTextSize());
                this.mPaint.setColor(this.mDatePicker.getTextHighlightColor());
                String str = this.mWeekDay;
                canvas.drawText(str, 0, str.length(), this.mBaseX, this.mWeekDayY, this.mPaint);
                this.mPaint.setColor(this.mDaySelectMode ? this.mDatePicker.getTextHighlightColor() : this.mTextHeaderColor);
                this.mPaint.setTextSize((float) this.mHeaderPrimaryTextSize);
                if (this.mMonthFirst) {
                    String str2 = this.mDay;
                    canvas.drawText(str2, 0, str2.length(), this.mBaseX, this.mDayY, this.mPaint);
                } else {
                    String str3 = this.mMonth;
                    canvas.drawText(str3, 0, str3.length(), this.mBaseX, this.mMonthY, this.mPaint);
                }
                this.mPaint.setTextSize((float) this.mHeaderSecondaryTextSize);
                if (this.mMonthFirst) {
                    String str4 = this.mMonth;
                    canvas.drawText(str4, 0, str4.length(), this.mBaseX, this.mMonthY, this.mPaint);
                } else {
                    String str5 = this.mDay;
                    canvas.drawText(str5, 0, str5.length(), this.mBaseX, this.mDayY, this.mPaint);
                }
                this.mPaint.setColor(this.mDaySelectMode ? this.mTextHeaderColor : this.mDatePicker.getTextHighlightColor());
                String str6 = this.mYear;
                canvas.drawText(str6, 0, str6.length(), this.mBaseX, this.mYearY, this.mPaint);
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
                float f = this.mBaseX;
                float f2 = this.mFirstWidth;
                if (isTouched(f - (f2 / 2.0f), (float) this.mHeaderSecondaryHeight, f + (f2 / 2.0f), this.mCenterY, event.getX(), event.getY())) {
                    return !this.mDaySelectMode;
                }
                float f3 = this.mBaseX;
                float f4 = this.mSecondWidth;
                if (isTouched(f3 - (f4 / 2.0f), this.mCenterY, f3 + (f4 / 2.0f), (float) (this.mHeaderSecondaryHeight + this.mHeaderPrimaryRealHeight), event.getX(), event.getY())) {
                    return this.mDaySelectMode;
                }
            } else if (action == 1) {
                float f5 = this.mBaseX;
                float f6 = this.mFirstWidth;
                if (isTouched(f5 - (f6 / 2.0f), (float) this.mHeaderSecondaryHeight, f5 + (f6 / 2.0f), this.mCenterY, event.getX(), event.getY())) {
                    setDateSelectMode(true);
                    return true;
                }
                float f7 = this.mBaseX;
                float f8 = this.mSecondWidth;
                if (isTouched(f7 - (f8 / 2.0f), this.mCenterY, f7 + (f8 / 2.0f), (float) (this.mHeaderSecondaryHeight + this.mHeaderPrimaryRealHeight), event.getX(), event.getY())) {
                    setDateSelectMode(false);
                    return true;
                }
            }
            return false;
        }
    }

    public static class Builder extends Dialog.Builder implements OnDateChangedListener {
        public static final Parcelable.Creator<Builder> CREATOR = new Parcelable.Creator<Builder>() {
            public Builder createFromParcel(Parcel in) {
                return new Builder(in);
            }

            public Builder[] newArray(int size) {
                return new Builder[size];
            }
        };
        private Calendar mCalendar;
        protected int mDay;
        protected int mMaxDay;
        protected int mMaxMonth;
        protected int mMaxYear;
        protected int mMinDay;
        protected int mMinMonth;
        protected int mMinYear;
        protected int mMonth;
        protected int mYear;

        public Builder() {
            this(C2500R.C2505style.Material_App_Dialog_DatePicker_Light);
        }

        public Builder(int styleId) {
            super(styleId);
            Calendar cal = Calendar.getInstance();
            this.mDay = cal.get(5);
            this.mMonth = cal.get(2);
            int i = cal.get(1);
            this.mYear = i;
            int i2 = this.mDay;
            this.mMinDay = i2;
            int i3 = this.mMonth;
            this.mMinMonth = i3;
            this.mMinYear = i - 12;
            this.mMaxDay = i2;
            this.mMaxMonth = i3;
            this.mMaxYear = i + 12;
        }

        public Builder(int minDay, int minMonth, int minYear, int maxDay, int maxMonth, int maxYear, int day, int month, int year) {
            this(C2500R.C2505style.Material_App_Dialog_DatePicker_Light, minDay, minMonth, minYear, maxDay, maxMonth, maxYear, day, month, year);
        }

        public Builder(int styleId, int minDay, int minMonth, int minYear, int maxDay, int maxMonth, int maxYear, int day, int month, int year) {
            super(styleId);
            this.mMinDay = minDay;
            this.mMinMonth = minMonth;
            this.mMinYear = minYear;
            this.mMaxDay = maxDay;
            this.mMaxMonth = maxMonth;
            this.mMaxYear = maxYear;
            this.mDay = day;
            this.mMonth = month;
            this.mYear = year;
        }

        public Builder dateRange(int minDay, int minMonth, int minYear, int maxDay, int maxMonth, int maxYear) {
            this.mMinDay = minDay;
            this.mMinMonth = minMonth;
            this.mMinYear = minYear;
            this.mMaxDay = maxDay;
            this.mMaxMonth = maxMonth;
            this.mMaxYear = maxYear;
            return this;
        }

        public Builder dateRange(long minTime, long maxTime) {
            if (this.mCalendar == null) {
                this.mCalendar = Calendar.getInstance();
            }
            this.mCalendar.setTimeInMillis(minTime);
            int minDay = this.mCalendar.get(5);
            int minMonth = this.mCalendar.get(2);
            int minYear = this.mCalendar.get(1);
            this.mCalendar.setTimeInMillis(maxTime);
            return dateRange(minDay, minMonth, minYear, this.mCalendar.get(5), this.mCalendar.get(2), this.mCalendar.get(1));
        }

        public Builder date(int day, int month, int year) {
            this.mDay = day;
            this.mMonth = month;
            this.mYear = year;
            return this;
        }

        public Builder date(long time) {
            if (this.mCalendar == null) {
                this.mCalendar = Calendar.getInstance();
            }
            this.mCalendar.setTimeInMillis(time);
            return date(this.mCalendar.get(5), this.mCalendar.get(2), this.mCalendar.get(1));
        }

        public int getDay() {
            return this.mDay;
        }

        public int getMonth() {
            return this.mMonth;
        }

        public int getYear() {
            return this.mYear;
        }

        public Dialog.Builder contentView(int layoutId) {
            return this;
        }

        /* access modifiers changed from: protected */
        public Dialog onBuild(Context context, int styleId) {
            DatePickerDialog dialog = new DatePickerDialog(context, styleId);
            dialog.dateRange(this.mMinDay, this.mMinMonth, this.mMinYear, this.mMaxDay, this.mMaxMonth, this.mMaxYear).date(this.mDay, this.mMonth, this.mYear).onDateChangedListener(this);
            return dialog;
        }

        public void onDateChanged(int oldDay, int oldMonth, int oldYear, int newDay, int newMonth, int newYear) {
            date(newDay, newMonth, newYear);
        }

        protected Builder(Parcel in) {
            super(in);
        }

        /* access modifiers changed from: protected */
        public void onReadFromParcel(Parcel in) {
            this.mMinDay = in.readInt();
            this.mMinMonth = in.readInt();
            this.mMinYear = in.readInt();
            this.mMaxDay = in.readInt();
            this.mMaxMonth = in.readInt();
            this.mMaxYear = in.readInt();
            this.mDay = in.readInt();
            this.mMonth = in.readInt();
            this.mYear = in.readInt();
        }

        /* access modifiers changed from: protected */
        public void onWriteToParcel(Parcel dest, int flags) {
            dest.writeInt(this.mMinDay);
            dest.writeInt(this.mMinMonth);
            dest.writeInt(this.mMinYear);
            dest.writeInt(this.mMaxDay);
            dest.writeInt(this.mMaxMonth);
            dest.writeInt(this.mMaxYear);
            dest.writeInt(this.mDay);
            dest.writeInt(this.mMonth);
            dest.writeInt(this.mYear);
        }
    }
}
