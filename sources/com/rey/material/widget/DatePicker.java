package com.rey.material.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Handler;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import androidx.core.view.ViewCompat;
import androidx.exifinterface.media.ExifInterface;
import com.fasterxml.jackson.core.util.MinimalPrettyPrinter;
import com.rey.material.C2500R;
import com.rey.material.drawable.BlankDrawable;
import com.rey.material.util.ThemeUtil;
import com.rey.material.util.TypefaceUtil;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class DatePicker extends ListView implements AbsListView.OnScrollListener {
    private static final String DAY_FORMAT = "%2d";
    protected static final int LIST_TOP_OFFSET = -1;
    protected static final int SCROLL_CHANGE_DELAY = 40;
    protected static final int SCROLL_DURATION = 250;
    private static final String YEAR_FORMAT = "%4d";
    private static String[] mDayTexts;
    /* access modifiers changed from: private */
    public MonthAdapter mAdapter;
    /* access modifiers changed from: private */
    public int mAnimDuration;
    /* access modifiers changed from: private */
    public Calendar mCalendar;
    protected int mCurrentScrollState = 0;
    /* access modifiers changed from: private */
    public float mDayBaseHeight;
    private float mDayBaseWidth;
    /* access modifiers changed from: private */
    public float mDayHeight;
    /* access modifiers changed from: private */
    public int mDayPadding;
    /* access modifiers changed from: private */
    public float mDayWidth;
    /* access modifiers changed from: private */
    public int mFirstDayOfWeek;
    protected float mFriction = 1.0f;
    protected Handler mHandler = new Handler();
    /* access modifiers changed from: private */
    public Interpolator mInInterpolator;
    /* access modifiers changed from: private */
    public String[] mLabels;
    /* access modifiers changed from: private */
    public int mMonthRealHeight;
    /* access modifiers changed from: private */
    public int mMonthRealWidth;
    /* access modifiers changed from: private */
    public OnDateChangedListener mOnDateChangedListener;
    /* access modifiers changed from: private */
    public Interpolator mOutInterpolator;
    /* access modifiers changed from: private */
    public int mPaddingBottom;
    /* access modifiers changed from: private */
    public int mPaddingLeft;
    /* access modifiers changed from: private */
    public int mPaddingRight;
    /* access modifiers changed from: private */
    public int mPaddingTop;
    /* access modifiers changed from: private */
    public Paint mPaint;
    protected long mPreviousScrollPosition;
    protected int mPreviousScrollState = 0;
    protected ScrollStateRunnable mScrollStateChangedRunnable = new ScrollStateRunnable();
    /* access modifiers changed from: private */
    public int mSelectionColor;
    /* access modifiers changed from: private */
    public float mSelectionRadius;
    /* access modifiers changed from: private */
    public int mTextColor;
    /* access modifiers changed from: private */
    public int mTextDisableColor;
    /* access modifiers changed from: private */
    public int mTextHighlightColor;
    /* access modifiers changed from: private */
    public int mTextLabelColor;
    /* access modifiers changed from: private */
    public int mTextSize;
    /* access modifiers changed from: private */
    public Typeface mTypeface;

    public interface OnDateChangedListener {
        void onDateChanged(int i, int i2, int i3, int i4, int i5, int i6);
    }

    public DatePicker(Context context) {
        super(context);
        init(context, (AttributeSet) null, 0, 0);
    }

    public DatePicker(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0, 0);
    }

    public DatePicker(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr, 0);
    }

    /* access modifiers changed from: protected */
    public void init(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        this.mTypeface = Typeface.DEFAULT;
        this.mTextSize = -1;
        this.mTextColor = ViewCompat.MEASURED_STATE_MASK;
        this.mTextLabelColor = -9013642;
        this.mTextHighlightColor = -1;
        this.mAnimDuration = -1;
        this.mLabels = new String[7];
        this.mFriction = 1.0f;
        setWillNotDraw(false);
        setSelector(BlankDrawable.getInstance());
        setCacheColorHint(0);
        setDivider((Drawable) null);
        setItemsCanFocus(true);
        setFastScrollEnabled(false);
        setVerticalScrollBarEnabled(false);
        setOnScrollListener(this);
        setFadingEdgeLength(0);
        setFrictionIfSupported(ViewConfiguration.getScrollFriction() * this.mFriction);
        Paint paint = new Paint(1);
        this.mPaint = paint;
        paint.setStyle(Paint.Style.FILL);
        this.mPaint.setTextAlign(Paint.Align.CENTER);
        this.mDayPadding = ThemeUtil.dpToPx(context, 4);
        this.mSelectionColor = ThemeUtil.colorPrimary(context, ViewCompat.MEASURED_STATE_MASK);
        Calendar instance = Calendar.getInstance();
        this.mCalendar = instance;
        this.mFirstDayOfWeek = instance.getFirstDayOfWeek();
        int index = this.mCalendar.get(7) - 1;
        DateFormat format = new SimpleDateFormat(Build.VERSION.SDK_INT >= 18 ? "EEEEE" : ExifInterface.LONGITUDE_EAST);
        for (int i = 0; i < 7; i++) {
            this.mLabels[index] = format.format(this.mCalendar.getTime());
            index = (index + 1) % 7;
            this.mCalendar.add(5, 1);
        }
        MonthAdapter monthAdapter = new MonthAdapter();
        this.mAdapter = monthAdapter;
        setAdapter(monthAdapter);
        super.init(context, attrs, defStyleAttr, defStyleRes);
    }

    /* access modifiers changed from: protected */
    public void applyStyle(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        Context context2 = context;
        super.applyStyle(context, attrs, defStyleAttr, defStyleRes);
        TypedArray a = context2.obtainStyledAttributes(attrs, C2500R.styleable.DatePicker, defStyleAttr, defStyleRes);
        String familyName = null;
        int style = -1;
        int padding = -1;
        int paddingLeft = -1;
        int paddingRight = -1;
        int paddingTop = -1;
        int paddingBottom = -1;
        boolean paddingDefined = false;
        int i = 0;
        int count = a.getIndexCount();
        while (i < count) {
            int attr = a.getIndex(i);
            if (attr == C2500R.styleable.DatePicker_dp_dayTextSize) {
                this.mTextSize = a.getDimensionPixelSize(attr, 0);
            } else if (attr == C2500R.styleable.DatePicker_dp_textColor) {
                this.mTextColor = a.getColor(attr, 0);
            } else if (attr == C2500R.styleable.DatePicker_dp_textHighlightColor) {
                this.mTextHighlightColor = a.getColor(attr, 0);
            } else if (attr == C2500R.styleable.DatePicker_dp_textLabelColor) {
                this.mTextLabelColor = a.getColor(attr, 0);
            } else if (attr == C2500R.styleable.DatePicker_dp_textDisableColor) {
                this.mTextDisableColor = a.getColor(attr, 0);
            } else if (attr == C2500R.styleable.DatePicker_dp_selectionColor) {
                this.mSelectionColor = a.getColor(attr, 0);
            } else if (attr == C2500R.styleable.DatePicker_dp_animDuration) {
                this.mAnimDuration = a.getInteger(attr, 0);
            } else if (attr == C2500R.styleable.DatePicker_dp_inInterpolator) {
                this.mInInterpolator = AnimationUtils.loadInterpolator(context2, a.getResourceId(attr, 0));
            } else if (attr == C2500R.styleable.DatePicker_dp_outInterpolator) {
                this.mOutInterpolator = AnimationUtils.loadInterpolator(context2, a.getResourceId(attr, 0));
            } else if (attr == C2500R.styleable.DatePicker_dp_fontFamily) {
                familyName = a.getString(attr);
            } else if (attr == C2500R.styleable.DatePicker_dp_textStyle) {
                style = a.getInteger(attr, 0);
            } else if (attr == C2500R.styleable.DatePicker_android_padding) {
                padding = a.getDimensionPixelSize(attr, 0);
                paddingDefined = true;
            } else if (attr == C2500R.styleable.DatePicker_android_paddingLeft) {
                paddingLeft = a.getDimensionPixelSize(attr, 0);
                paddingDefined = true;
            } else if (attr == C2500R.styleable.DatePicker_android_paddingTop) {
                paddingTop = a.getDimensionPixelSize(attr, 0);
                paddingDefined = true;
            } else if (attr == C2500R.styleable.DatePicker_android_paddingRight) {
                paddingRight = a.getDimensionPixelSize(attr, 0);
                paddingDefined = true;
            } else if (attr == C2500R.styleable.DatePicker_android_paddingBottom) {
                paddingBottom = a.getDimensionPixelSize(attr, 0);
                paddingDefined = true;
            }
            i++;
            AttributeSet attributeSet = attrs;
            int i2 = defStyleAttr;
            int i3 = defStyleRes;
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
        if (familyName != null || style >= 0) {
            this.mTypeface = TypefaceUtil.load(context2, familyName, style);
        }
        a.recycle();
        if (paddingDefined) {
            if (padding >= 0) {
                setContentPadding(padding, padding, padding, padding);
            }
            if (paddingLeft >= 0) {
                this.mPaddingLeft = paddingLeft;
            }
            if (paddingTop >= 0) {
                this.mPaddingTop = paddingTop;
            }
            if (paddingRight >= 0) {
                this.mPaddingRight = paddingRight;
            }
            if (paddingBottom >= 0) {
                this.mPaddingBottom = paddingBottom;
            }
        }
        requestLayout();
        this.mAdapter.notifyDataSetInvalidated();
    }

    private void setFrictionIfSupported(float friction) {
        if (Build.VERSION.SDK_INT >= 11) {
            setFriction(friction);
        }
    }

    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        MonthView child = (MonthView) view.getChildAt(0);
        if (child != null) {
            this.mPreviousScrollPosition = (long) ((getFirstVisiblePosition() * child.getHeight()) - child.getBottom());
            this.mPreviousScrollState = this.mCurrentScrollState;
        }
    }

    public void onScrollStateChanged(AbsListView absListView, int scroll) {
        this.mScrollStateChangedRunnable.doScrollStateChange(absListView, scroll);
    }

    private void measureBaseSize() {
        this.mPaint.setTextSize((float) this.mTextSize);
        this.mPaint.setTypeface(this.mTypeface);
        this.mDayBaseWidth = this.mPaint.measureText("88", 0, 2) + ((float) (this.mDayPadding * 2));
        Rect bounds = new Rect();
        this.mPaint.getTextBounds("88", 0, 2, bounds);
        this.mDayBaseHeight = (float) bounds.height();
    }

    private void measureMonthView(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode = View.MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = View.MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = View.MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = View.MeasureSpec.getSize(heightMeasureSpec);
        measureBaseSize();
        int size = Math.round(Math.max(this.mDayBaseWidth, this.mDayBaseHeight));
        int width = (size * 7) + this.mPaddingLeft + this.mPaddingRight;
        int height = Math.round(((float) (size * 7)) + this.mDayBaseHeight + ((float) (this.mDayPadding * 2)) + ((float) this.mPaddingTop) + ((float) this.mPaddingBottom));
        if (widthMode == Integer.MIN_VALUE) {
            width = Math.min(width, widthSize);
        } else if (widthMode == 1073741824) {
            width = widthSize;
        }
        if (heightMode == Integer.MIN_VALUE) {
            height = Math.min(height, heightSize);
        } else if (heightMode == 1073741824) {
            height = heightSize;
        }
        this.mMonthRealWidth = width;
        this.mMonthRealHeight = height;
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        measureMonthView(widthMeasureSpec, heightMeasureSpec);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    /* access modifiers changed from: protected */
    public void onSizeChanged(int w, int h, int oldw, int oldh) {
        float f = ((float) ((w - this.mPaddingLeft) - this.mPaddingRight)) / 7.0f;
        this.mDayWidth = f;
        float f2 = ((((((float) h) - this.mDayBaseHeight) - ((float) (this.mDayPadding * 2))) - ((float) this.mPaddingTop)) - ((float) this.mPaddingBottom)) / 7.0f;
        this.mDayHeight = f2;
        this.mSelectionRadius = Math.min(f, f2) / 2.0f;
    }

    public void setPadding(int left, int top, int right, int bottom) {
        super.setPadding(0, 0, 0, 0);
    }

    public void setContentPadding(int left, int top, int right, int bottom) {
        this.mPaddingLeft = left;
        this.mPaddingTop = top;
        this.mPaddingRight = right;
        this.mPaddingBottom = bottom;
    }

    /* access modifiers changed from: private */
    public String getDayText(int day) {
        if (mDayTexts == null) {
            synchronized (DatePicker.class) {
                if (mDayTexts == null) {
                    mDayTexts = new String[31];
                }
            }
        }
        String[] strArr = mDayTexts;
        if (strArr[day - 1] == null) {
            strArr[day - 1] = String.format(DAY_FORMAT, new Object[]{Integer.valueOf(day)});
        }
        return mDayTexts[day - 1];
    }

    public void setDateRange(int minDay, int minMonth, int minYear, int maxDay, int maxMonth, int maxYear) {
        this.mAdapter.setDateRange(minDay, minMonth, minYear, maxDay, maxMonth, maxYear);
    }

    public void goTo(int month, int year) {
        postSetSelectionFromTop(this.mAdapter.positionOfMonth(month, year), 0);
    }

    public void postSetSelectionFromTop(final int position, final int offset) {
        post(new Runnable() {
            public void run() {
                DatePicker.this.setSelectionFromTop(position, offset);
                DatePicker.this.requestLayout();
            }
        });
    }

    public void setDate(int day, int month, int year) {
        if (this.mAdapter.getYear() != year || this.mAdapter.getMonth() != month || this.mAdapter.getDay() != day) {
            this.mAdapter.setDate(day, month, year, false);
            goTo(month, year);
        }
    }

    public void setOnDateChangedListener(OnDateChangedListener listener) {
        this.mOnDateChangedListener = listener;
    }

    public int getDay() {
        return this.mAdapter.getDay();
    }

    public int getMonth() {
        return this.mAdapter.getMonth();
    }

    public int getYear() {
        return this.mAdapter.getYear();
    }

    public String getFormattedDate(DateFormat formatter) {
        this.mCalendar.set(1, this.mAdapter.getYear());
        this.mCalendar.set(2, this.mAdapter.getMonth());
        this.mCalendar.set(5, this.mAdapter.getDay());
        return formatter.format(this.mCalendar.getTime());
    }

    public int getSelectionColor() {
        return this.mSelectionColor;
    }

    public int getTextSize() {
        return this.mTextSize;
    }

    public Typeface getTypeface() {
        return this.mTypeface;
    }

    public int getTextColor() {
        return this.mTextColor;
    }

    public int getTextLabelColor() {
        return this.mTextLabelColor;
    }

    public int getTextHighlightColor() {
        return this.mTextHighlightColor;
    }

    public int getTextDisableColor() {
        return this.mTextDisableColor;
    }

    public Calendar getCalendar() {
        return this.mCalendar;
    }

    private class ScrollStateRunnable implements Runnable {
        private int mNewState;

        private ScrollStateRunnable() {
        }

        public void doScrollStateChange(AbsListView view, int scrollState) {
            DatePicker.this.mHandler.removeCallbacks(this);
            this.mNewState = scrollState;
            DatePicker.this.mHandler.postDelayed(this, 40);
        }

        public void run() {
            DatePicker.this.mCurrentScrollState = this.mNewState;
            if (this.mNewState == 0 && DatePicker.this.mPreviousScrollState != 0) {
                boolean scroll = true;
                if (DatePicker.this.mPreviousScrollState != 1) {
                    DatePicker.this.mPreviousScrollState = this.mNewState;
                    int i = 0;
                    View child = DatePicker.this.getChildAt(0);
                    while (child != null && child.getBottom() <= 0) {
                        i++;
                        child = DatePicker.this.getChildAt(i);
                    }
                    if (child != null) {
                        int firstPosition = DatePicker.this.getFirstVisiblePosition();
                        int lastPosition = DatePicker.this.getLastVisiblePosition();
                        if (firstPosition == 0 || lastPosition == DatePicker.this.getCount() - 1) {
                            scroll = false;
                        }
                        int top = child.getTop();
                        int bottom = child.getBottom();
                        int midpoint = DatePicker.this.getHeight() / 2;
                        if (scroll && top < -1) {
                            if (bottom > midpoint) {
                                DatePicker.this.smoothScrollBy(top, 250);
                                return;
                            } else {
                                DatePicker.this.smoothScrollBy(bottom, 250);
                                return;
                            }
                        } else {
                            return;
                        }
                    } else {
                        return;
                    }
                }
            }
            DatePicker.this.mPreviousScrollState = this.mNewState;
        }
    }

    private class MonthView extends View {
        private float mAnimProgress;
        private int mFirstDayCol;
        private int mMaxAvailDay = -1;
        private int mMaxDay;
        private int mMinAvailDay = -1;
        private int mMonth;
        private String mMonthText;
        private int mPreviousSelectedDay = -1;
        private boolean mRunning;
        private int mSelectedDay = -1;
        private long mStartTime;
        private int mToday = -1;
        private int mTouchedDay = -1;
        private final Runnable mUpdater = new Runnable() {
            public void run() {
                MonthView.this.update();
            }
        };
        private int mYear;

        public MonthView(Context context) {
            super(context);
            setWillNotDraw(false);
        }

        public void setMonth(int month, int year) {
            if (this.mMonth != month || this.mYear != year) {
                this.mMonth = month;
                this.mYear = year;
                calculateMonthView();
                invalidate();
            }
        }

        public void setSelectedDay(int day, boolean animation) {
            int i = this.mSelectedDay;
            if (i != day) {
                this.mPreviousSelectedDay = i;
                this.mSelectedDay = day;
                if (animation) {
                    startAnimation();
                } else {
                    invalidate();
                }
            }
        }

        public void setToday(int day) {
            if (this.mToday != day) {
                this.mToday = day;
                invalidate();
            }
        }

        public void setAvailableDay(int min, int max) {
            if (this.mMinAvailDay != min || this.mMaxAvailDay != max) {
                this.mMinAvailDay = min;
                this.mMaxAvailDay = max;
                invalidate();
            }
        }

        private void calculateMonthView() {
            DatePicker.this.mCalendar.set(5, 1);
            DatePicker.this.mCalendar.set(2, this.mMonth);
            DatePicker.this.mCalendar.set(1, this.mYear);
            this.mMaxDay = DatePicker.this.mCalendar.getActualMaximum(5);
            int dayOfWeek = DatePicker.this.mCalendar.get(7);
            this.mFirstDayCol = dayOfWeek < DatePicker.this.mFirstDayOfWeek ? (dayOfWeek + 7) - DatePicker.this.mFirstDayOfWeek : dayOfWeek - DatePicker.this.mFirstDayOfWeek;
            this.mMonthText = DatePicker.this.mCalendar.getDisplayName(2, 2, Locale.getDefault()) + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR + String.format(DatePicker.YEAR_FORMAT, new Object[]{Integer.valueOf(this.mYear)});
        }

        /* access modifiers changed from: protected */
        public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
            setMeasuredDimension(DatePicker.this.mMonthRealWidth, DatePicker.this.mMonthRealHeight);
        }

        /* access modifiers changed from: protected */
        public void onDraw(Canvas canvas) {
            int i;
            DatePicker.this.mPaint.setTextSize((float) DatePicker.this.mTextSize);
            DatePicker.this.mPaint.setTypeface(DatePicker.this.mTypeface);
            float x = (DatePicker.this.mDayWidth * 3.5f) + ((float) getPaddingLeft());
            float y = ((float) (DatePicker.this.mDayPadding * 2)) + DatePicker.this.mDayBaseHeight + ((float) getPaddingTop());
            DatePicker.this.mPaint.setFakeBoldText(true);
            DatePicker.this.mPaint.setColor(DatePicker.this.mTextColor);
            canvas.drawText(this.mMonthText, x, y, DatePicker.this.mPaint);
            float paddingLeft = (float) getPaddingLeft();
            float paddingTop = ((float) (DatePicker.this.mDayPadding * 2)) + DatePicker.this.mDayBaseHeight + ((float) getPaddingTop());
            int i2 = this.mSelectedDay;
            if (i2 > 0) {
                int i3 = this.mFirstDayCol;
                float x2 = ((((float) (((i3 + i2) - 1) % 7)) + 0.5f) * DatePicker.this.mDayWidth) + paddingLeft;
                float y2 = ((((float) ((((i3 + i2) - 1) / 7) + 1)) + 0.5f) * DatePicker.this.mDayHeight) + paddingTop;
                float radius = this.mRunning ? DatePicker.this.mInInterpolator.getInterpolation(this.mAnimProgress) * DatePicker.this.mSelectionRadius : DatePicker.this.mSelectionRadius;
                DatePicker.this.mPaint.setColor(DatePicker.this.mSelectionColor);
                canvas.drawCircle(x2, y2, radius, DatePicker.this.mPaint);
            }
            if (this.mRunning && (i = this.mPreviousSelectedDay) > 0) {
                int i4 = this.mFirstDayCol;
                float x3 = ((((float) (((i4 + i) - 1) % 7)) + 0.5f) * DatePicker.this.mDayWidth) + paddingLeft;
                float y3 = ((((float) ((((i4 + i) - 1) / 7) + 1)) + 0.5f) * DatePicker.this.mDayHeight) + paddingTop;
                float radius2 = (1.0f - DatePicker.this.mOutInterpolator.getInterpolation(this.mAnimProgress)) * DatePicker.this.mSelectionRadius;
                DatePicker.this.mPaint.setColor(DatePicker.this.mSelectionColor);
                canvas.drawCircle(x3, y3, radius2, DatePicker.this.mPaint);
            }
            DatePicker.this.mPaint.setFakeBoldText(false);
            DatePicker.this.mPaint.setColor(DatePicker.this.mTextLabelColor);
            float paddingTop2 = paddingTop + ((DatePicker.this.mDayHeight + DatePicker.this.mDayBaseHeight) / 2.0f);
            for (int i5 = 0; i5 < 7; i5++) {
                canvas.drawText(DatePicker.this.mLabels[((DatePicker.this.mFirstDayOfWeek + i5) - 1) % 7], ((((float) i5) + 0.5f) * DatePicker.this.mDayWidth) + paddingLeft, paddingTop2, DatePicker.this.mPaint);
            }
            int col = this.mFirstDayCol;
            int row = 1;
            int i6 = this.mMaxAvailDay;
            int maxDay = i6 > 0 ? Math.min(i6, this.mMaxDay) : this.mMaxDay;
            for (int day = 1; day <= this.mMaxDay; day++) {
                if (day == this.mSelectedDay) {
                    DatePicker.this.mPaint.setColor(DatePicker.this.mTextHighlightColor);
                } else if (day < this.mMinAvailDay || day > maxDay) {
                    DatePicker.this.mPaint.setColor(DatePicker.this.mTextDisableColor);
                } else if (day == this.mToday) {
                    DatePicker.this.mPaint.setColor(DatePicker.this.mSelectionColor);
                } else {
                    DatePicker.this.mPaint.setColor(DatePicker.this.mTextColor);
                }
                canvas.drawText(DatePicker.this.getDayText(day), ((((float) col) + 0.5f) * DatePicker.this.mDayWidth) + paddingLeft, (((float) row) * DatePicker.this.mDayHeight) + paddingTop2, DatePicker.this.mPaint);
                col++;
                if (col == 7) {
                    col = 0;
                    row++;
                }
            }
        }

        private int getTouchedDay(float x, float y) {
            float paddingTop = ((float) (DatePicker.this.mDayPadding * 2)) + DatePicker.this.mDayBaseHeight + ((float) getPaddingTop()) + DatePicker.this.mDayHeight;
            if (x < ((float) getPaddingLeft()) || x > ((float) (getWidth() - getPaddingRight())) || y < paddingTop || y > ((float) (getHeight() - getPaddingBottom()))) {
                return -1;
            }
            int col = (int) Math.floor((double) ((x - ((float) getPaddingLeft())) / DatePicker.this.mDayWidth));
            int row = (int) Math.floor((double) ((y - paddingTop) / DatePicker.this.mDayHeight));
            int i = this.mMaxAvailDay;
            int maxDay = i > 0 ? Math.min(i, this.mMaxDay) : this.mMaxDay;
            int day = (((row * 7) + col) - this.mFirstDayCol) + 1;
            if (day < 0 || day < this.mMinAvailDay || day > maxDay) {
                return -1;
            }
            return day;
        }

        public boolean onTouchEvent(MotionEvent event) {
            int action = event.getAction();
            if (action == 0) {
                this.mTouchedDay = getTouchedDay(event.getX(), event.getY());
                return true;
            } else if (action == 1) {
                int touchedDay = getTouchedDay(event.getX(), event.getY());
                int i = this.mTouchedDay;
                if (touchedDay == i && i > 0) {
                    DatePicker.this.mAdapter.setDate(this.mTouchedDay, this.mMonth, this.mYear, true);
                    this.mTouchedDay = -1;
                }
                return true;
            } else if (action != 3) {
                return true;
            } else {
                this.mTouchedDay = -1;
                return true;
            }
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
            float min = Math.min(1.0f, ((float) (SystemClock.uptimeMillis() - this.mStartTime)) / ((float) DatePicker.this.mAnimDuration));
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
    }

    private class MonthAdapter extends BaseAdapter {
        private int mDay;
        private int mMaxDay;
        private int mMaxMonth;
        private int mMaxMonthValue;
        private int mMaxYear;
        private int mMinDay;
        private int mMinMonth;
        private int mMinMonthValue;
        private int mMinYear;
        private int mMonth;
        private int mToday;
        private int mTodayMonth;
        private int mTodayYear;
        private int mYear;

        private MonthAdapter() {
            this.mDay = -1;
            this.mMonth = -1;
            this.mYear = -1;
            this.mMinDay = -1;
            this.mMinMonth = -1;
            this.mMinYear = -1;
            this.mMaxDay = -1;
            this.mMaxMonth = -1;
            this.mMaxYear = -1;
        }

        public void setDateRange(int minDay, int minMonth, int minYear, int maxDay, int maxMonth, int maxYear) {
            int minMonthValue = (minDay < 0 || minMonth < 0 || minYear < 0) ? 0 : (minYear * 12) + minMonth;
            int maxMonthValue = (maxDay < 0 || maxMonth < 0 || maxYear < 0) ? 2147483646 : (maxYear * 12) + maxMonth;
            if (minDay != this.mMinDay || this.mMinMonthValue != minMonthValue || maxDay != this.mMaxDay || this.mMaxMonthValue != maxMonthValue) {
                this.mMinDay = minDay;
                this.mMinMonth = minMonth;
                this.mMinYear = minYear;
                this.mMaxDay = maxDay;
                this.mMaxMonth = maxMonth;
                this.mMaxYear = maxYear;
                this.mMinMonthValue = minMonthValue;
                this.mMaxMonthValue = maxMonthValue;
                notifyDataSetChanged();
            }
        }

        public void setDate(int day, int month, int year, boolean animation) {
            int i;
            int i2 = this.mMonth;
            if (i2 != month || (i = this.mYear) != year) {
                MonthView v = (MonthView) DatePicker.this.getChildAt(positionOfMonth(i2, this.mYear) - DatePicker.this.getFirstVisiblePosition());
                if (v != null) {
                    v.setSelectedDay(-1, false);
                }
                int oldDay = this.mDay;
                int oldMonth = this.mMonth;
                int oldYear = this.mYear;
                this.mDay = day;
                this.mMonth = month;
                this.mYear = year;
                MonthView v2 = (MonthView) DatePicker.this.getChildAt(positionOfMonth(month, year) - DatePicker.this.getFirstVisiblePosition());
                if (v2 != null) {
                    v2.setSelectedDay(this.mDay, animation);
                }
                if (DatePicker.this.mOnDateChangedListener != null) {
                    DatePicker.this.mOnDateChangedListener.onDateChanged(oldDay, oldMonth, oldYear, this.mDay, this.mMonth, this.mYear);
                }
            } else if (day != this.mDay) {
                int oldDay2 = this.mDay;
                this.mDay = day;
                MonthView v3 = (MonthView) DatePicker.this.getChildAt(positionOfMonth(i2, i) - DatePicker.this.getFirstVisiblePosition());
                if (v3 != null) {
                    v3.setSelectedDay(this.mDay, animation);
                }
                if (DatePicker.this.mOnDateChangedListener != null) {
                    OnDateChangedListener access$2600 = DatePicker.this.mOnDateChangedListener;
                    int i3 = this.mMonth;
                    int i4 = this.mYear;
                    access$2600.onDateChanged(oldDay2, i3, i4, this.mDay, i3, i4);
                }
            }
        }

        public int positionOfMonth(int month, int year) {
            return ((year * 12) + month) - this.mMinMonthValue;
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

        private void calToday() {
            DatePicker.this.mCalendar.setTimeInMillis(System.currentTimeMillis());
            this.mToday = DatePicker.this.mCalendar.get(5);
            this.mTodayMonth = DatePicker.this.mCalendar.get(2);
            this.mTodayYear = DatePicker.this.mCalendar.get(1);
        }

        public int getCount() {
            return (this.mMaxMonthValue - this.mMinMonthValue) + 1;
        }

        public Object getItem(int position) {
            return Integer.valueOf(this.mMinMonthValue + position);
        }

        public long getItemId(int position) {
            return 0;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            MonthView v = (MonthView) convertView;
            if (v == null) {
                v = new MonthView(parent.getContext());
                v.setPadding(DatePicker.this.mPaddingLeft, DatePicker.this.mPaddingTop, DatePicker.this.mPaddingRight, DatePicker.this.mPaddingBottom);
            }
            calToday();
            int monthValue = ((Integer) getItem(position)).intValue();
            int year = monthValue / 12;
            int month = monthValue % 12;
            int day = -1;
            int minDay = (month == this.mMinMonth && year == this.mMinYear) ? this.mMinDay : -1;
            int maxDay = (month == this.mMaxMonth && year == this.mMaxYear) ? this.mMaxDay : -1;
            int today = (this.mTodayMonth == month && this.mTodayYear == year) ? this.mToday : -1;
            if (month == this.mMonth && year == this.mYear) {
                day = this.mDay;
            }
            v.setMonth(month, year);
            v.setToday(today);
            v.setAvailableDay(minDay, maxDay);
            v.setSelectedDay(day, false);
            return v;
        }
    }
}
