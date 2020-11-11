package com.rey.material.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.BaseAdapter;
import androidx.core.view.ViewCompat;
import com.google.common.primitives.Ints;
import com.rey.material.C2500R;
import com.rey.material.drawable.BlankDrawable;
import com.rey.material.util.ThemeUtil;
import com.rey.material.util.TypefaceUtil;
import java.util.Calendar;

public class YearPicker extends ListView {
    /* access modifiers changed from: private */
    public static final int[][] STATES = {new int[]{-16842912}, new int[]{16842912}};
    private static final String YEAR_FORMAT = "%4d";
    private YearAdapter mAdapter;
    /* access modifiers changed from: private */
    public int mAnimDuration;
    private int mDistanceShift;
    /* access modifiers changed from: private */
    public Interpolator mInInterpolator;
    private int mItemHeight;
    /* access modifiers changed from: private */
    public int mItemRealHeight;
    /* access modifiers changed from: private */
    public OnYearChangedListener mOnYearChangedListener;
    /* access modifiers changed from: private */
    public Interpolator mOutInterpolator;
    private int mPadding;
    private Paint mPaint;
    private int mPositionShift;
    /* access modifiers changed from: private */
    public int mSelectionColor;
    /* access modifiers changed from: private */
    public int[] mTextColors = {ViewCompat.MEASURED_STATE_MASK, -1};
    /* access modifiers changed from: private */
    public int mTextSize;
    /* access modifiers changed from: private */
    public Typeface mTypeface;

    public interface OnYearChangedListener {
        void onYearChanged(int i, int i2);
    }

    public YearPicker(Context context) {
        super(context);
    }

    public YearPicker(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public YearPicker(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /* access modifiers changed from: protected */
    public void init(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        this.mTextSize = -1;
        this.mItemHeight = -1;
        this.mAnimDuration = -1;
        this.mTypeface = Typeface.DEFAULT;
        this.mItemRealHeight = -1;
        setWillNotDraw(false);
        Paint paint = new Paint(1);
        this.mPaint = paint;
        paint.setStyle(Paint.Style.FILL);
        YearAdapter yearAdapter = new YearAdapter();
        this.mAdapter = yearAdapter;
        setAdapter(yearAdapter);
        setScrollBarStyle(33554432);
        setSelector(BlankDrawable.getInstance());
        setDividerHeight(0);
        setCacheColorHint(0);
        setClipToPadding(false);
        this.mPadding = ThemeUtil.dpToPx(context, 4);
        this.mSelectionColor = ThemeUtil.colorPrimary(context, ViewCompat.MEASURED_STATE_MASK);
        super.init(context, attrs, defStyleAttr, defStyleRes);
    }

    /* access modifiers changed from: protected */
    public void applyStyle(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        Context context2 = context;
        super.applyStyle(context, attrs, defStyleAttr, defStyleRes);
        TypedArray a = context2.obtainStyledAttributes(attrs, C2500R.styleable.YearPicker, defStyleAttr, defStyleRes);
        int year = -1;
        int yearMin = -1;
        int yearMax = -1;
        String familyName = null;
        int style = -1;
        int count = a.getIndexCount();
        for (int i = 0; i < count; i++) {
            int attr = a.getIndex(i);
            if (attr == C2500R.styleable.YearPicker_dp_yearTextSize) {
                this.mTextSize = a.getDimensionPixelSize(attr, 0);
            } else if (attr == C2500R.styleable.YearPicker_dp_year) {
                year = a.getInteger(attr, 0);
            } else if (attr == C2500R.styleable.YearPicker_dp_yearMin) {
                yearMin = a.getInteger(attr, 0);
            } else if (attr == C2500R.styleable.YearPicker_dp_yearMax) {
                yearMax = a.getInteger(attr, 0);
            } else if (attr == C2500R.styleable.YearPicker_dp_yearItemHeight) {
                this.mItemHeight = a.getDimensionPixelSize(attr, 0);
            } else if (attr == C2500R.styleable.YearPicker_dp_textColor) {
                this.mTextColors[0] = a.getColor(attr, 0);
            } else if (attr == C2500R.styleable.YearPicker_dp_textHighlightColor) {
                this.mTextColors[1] = a.getColor(attr, 0);
            } else if (attr == C2500R.styleable.YearPicker_dp_selectionColor) {
                this.mSelectionColor = a.getColor(attr, 0);
            } else if (attr == C2500R.styleable.YearPicker_dp_animDuration) {
                this.mAnimDuration = a.getInteger(attr, 0);
            } else if (attr == C2500R.styleable.YearPicker_dp_inInterpolator) {
                this.mInInterpolator = AnimationUtils.loadInterpolator(context2, a.getResourceId(attr, 0));
            } else if (attr == C2500R.styleable.YearPicker_dp_outInterpolator) {
                this.mOutInterpolator = AnimationUtils.loadInterpolator(context2, a.getResourceId(attr, 0));
            } else if (attr == C2500R.styleable.YearPicker_dp_fontFamily) {
                familyName = a.getString(attr);
            } else if (attr == C2500R.styleable.YearPicker_dp_textStyle) {
                style = a.getInteger(attr, 0);
            }
        }
        a.recycle();
        if (this.mTextSize < 0) {
            this.mTextSize = context.getResources().getDimensionPixelOffset(C2500R.dimen.abc_text_size_title_material);
        }
        if (this.mItemHeight < 0) {
            this.mItemHeight = ThemeUtil.dpToPx(context2, 48);
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
        if (yearMin >= 0 || yearMax >= 0) {
            if (yearMin < 0) {
                yearMin = this.mAdapter.getMinYear();
            }
            if (yearMax < 0) {
                yearMax = this.mAdapter.getMaxYear();
            }
            if (yearMax < yearMin) {
                yearMax = Integer.MAX_VALUE;
            }
            setYearRange(yearMin, yearMax);
        }
        if (this.mAdapter.getYear() < 0 && year < 0) {
            year = Calendar.getInstance().get(1);
        }
        if (year >= 0) {
            setYear(Math.max(yearMin, Math.min(yearMax, year)));
        }
        this.mAdapter.notifyDataSetChanged();
        requestLayout();
    }

    public void setYearRange(int min, int max) {
        this.mAdapter.setYearRange(min, max);
    }

    public void goTo(int year) {
        int position = this.mAdapter.positionOfYear(year) - this.mPositionShift;
        int offset = this.mDistanceShift;
        if (position < 0) {
            position = 0;
            offset = 0;
        }
        postSetSelectionFromTop(position, offset);
    }

    public void postSetSelectionFromTop(final int position, final int offset) {
        post(new Runnable() {
            public void run() {
                YearPicker.this.setSelectionFromTop(position, offset);
                YearPicker.this.requestLayout();
            }
        });
    }

    public void setYear(int year) {
        if (this.mAdapter.getYear() != year) {
            this.mAdapter.setYear(year);
            goTo(year);
        }
    }

    public int getYear() {
        return this.mAdapter.getYear();
    }

    public void setOnYearChangedListener(OnYearChangedListener listener) {
        this.mOnYearChangedListener = listener;
    }

    private void measureItemHeight() {
        if (this.mItemRealHeight <= 0) {
            this.mPaint.setTextSize((float) this.mTextSize);
            this.mItemRealHeight = Math.max(Math.round(this.mPaint.measureText("9999", 0, 4)) + (this.mPadding * 2), this.mItemHeight);
        }
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int heightMode = View.MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = View.MeasureSpec.getSize(heightMeasureSpec);
        measureItemHeight();
        if (heightMode != 1073741824) {
            if (heightMode == Integer.MIN_VALUE) {
                int num = Math.min(this.mAdapter.getCount(), heightSize / this.mItemRealHeight);
                if (num >= 3) {
                    heightSize = this.mItemRealHeight * (num % 2 == 0 ? num - 1 : num);
                }
            } else {
                heightSize = this.mItemRealHeight * this.mAdapter.getCount();
            }
            heightMeasureSpec = View.MeasureSpec.makeMeasureSpec(getPaddingTop() + heightSize + getPaddingBottom(), Ints.MAX_POWER_OF_TWO);
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    /* access modifiers changed from: protected */
    public void onSizeChanged(int w, int h, int oldw, int oldh) {
        float shift = ((((float) h) / ((float) this.mItemRealHeight)) - 1.0f) / 2.0f;
        int floor = (int) Math.floor((double) shift);
        this.mPositionShift = floor;
        if (shift > ((float) floor)) {
            floor++;
        }
        this.mPositionShift = floor;
        this.mDistanceShift = ((int) ((shift - ((float) floor)) * ((float) this.mItemRealHeight))) - getPaddingTop();
        goTo(this.mAdapter.getYear());
    }

    private class YearAdapter extends BaseAdapter implements View.OnClickListener {
        private int mCurYear = -1;
        private int mMaxYear = 2147483646;
        private int mMinYear = 1990;

        public YearAdapter() {
        }

        public int getMinYear() {
            return this.mMinYear;
        }

        public int getMaxYear() {
            return this.mMaxYear;
        }

        public void setYearRange(int min, int max) {
            if (this.mMinYear != min || this.mMaxYear != max) {
                this.mMinYear = min;
                this.mMaxYear = max;
                notifyDataSetChanged();
            }
        }

        public int positionOfYear(int year) {
            return year - this.mMinYear;
        }

        public int getCount() {
            return (this.mMaxYear - this.mMinYear) + 1;
        }

        public Object getItem(int position) {
            return Integer.valueOf(this.mMinYear + position);
        }

        public long getItemId(int position) {
            return 0;
        }

        public void setYear(int year) {
            if (this.mCurYear != year) {
                int old = this.mCurYear;
                this.mCurYear = year;
                CircleCheckedTextView child = (CircleCheckedTextView) YearPicker.this.getChildAt(positionOfYear(old) - YearPicker.this.getFirstVisiblePosition());
                if (child != null) {
                    child.setChecked(false);
                }
                CircleCheckedTextView child2 = (CircleCheckedTextView) YearPicker.this.getChildAt(positionOfYear(this.mCurYear) - YearPicker.this.getFirstVisiblePosition());
                if (child2 != null) {
                    child2.setChecked(true);
                }
                if (YearPicker.this.mOnYearChangedListener != null) {
                    YearPicker.this.mOnYearChangedListener.onYearChanged(old, this.mCurYear);
                }
            }
        }

        public int getYear() {
            return this.mCurYear;
        }

        public void onClick(View v) {
            setYear(((Integer) v.getTag()).intValue());
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            CircleCheckedTextView v = (CircleCheckedTextView) convertView;
            boolean z = false;
            if (v == null) {
                v = new CircleCheckedTextView(YearPicker.this.getContext());
                v.setGravity(17);
                if (Build.VERSION.SDK_INT >= 17) {
                    v.setTextAlignment(4);
                }
                v.setMinHeight(YearPicker.this.mItemRealHeight);
                v.setMaxHeight(YearPicker.this.mItemRealHeight);
                v.setAnimDuration(YearPicker.this.mAnimDuration);
                v.setInterpolator(YearPicker.this.mInInterpolator, YearPicker.this.mOutInterpolator);
                v.setBackgroundColor(YearPicker.this.mSelectionColor);
                v.setTypeface(YearPicker.this.mTypeface);
                v.setTextSize(0, (float) YearPicker.this.mTextSize);
                v.setTextColor(new ColorStateList(YearPicker.STATES, YearPicker.this.mTextColors));
                v.setOnClickListener(this);
            }
            int year = ((Integer) getItem(position)).intValue();
            v.setTag(Integer.valueOf(year));
            v.setText(String.format(YearPicker.YEAR_FORMAT, new Object[]{Integer.valueOf(year)}));
            if (year == this.mCurYear) {
                z = true;
            }
            v.setCheckedImmediately(z);
            return v;
        }
    }

    public Parcelable onSaveInstanceState() {
        SavedState ss = new SavedState(super.onSaveInstanceState());
        ss.yearMin = this.mAdapter.getMinYear();
        ss.yearMax = this.mAdapter.getMaxYear();
        ss.year = this.mAdapter.getYear();
        return ss;
    }

    public void onRestoreInstanceState(Parcelable state) {
        SavedState ss = (SavedState) state;
        super.onRestoreInstanceState(ss.getSuperState());
        setYearRange(ss.yearMin, ss.yearMax);
        setYear(ss.year);
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
        int year;
        int yearMax;
        int yearMin;

        SavedState(Parcelable superState) {
            super(superState);
        }

        private SavedState(Parcel in) {
            super(in);
            this.yearMin = in.readInt();
            this.yearMax = in.readInt();
            this.year = in.readInt();
        }

        public void writeToParcel(Parcel out, int flags) {
            super.writeToParcel(out, flags);
            out.writeValue(Integer.valueOf(this.yearMin));
            out.writeValue(Integer.valueOf(this.yearMax));
            out.writeValue(Integer.valueOf(this.year));
        }

        public String toString() {
            return "YearPicker.SavedState{" + Integer.toHexString(System.identityHashCode(this)) + " yearMin=" + this.yearMin + " yearMax=" + this.yearMax + " year=" + this.year + "}";
        }
    }
}
