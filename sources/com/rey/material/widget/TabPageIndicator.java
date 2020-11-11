package com.rey.material.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.database.DataSetObserver;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import com.google.common.primitives.Ints;
import com.rey.material.C2500R;
import com.rey.material.app.ThemeManager;
import com.rey.material.drawable.RippleDrawable;
import com.rey.material.util.ThemeUtil;
import com.rey.material.util.ViewUtil;

public class TabPageIndicator extends HorizontalScrollView implements ViewPager.OnPageChangeListener, View.OnClickListener, ThemeManager.OnThemeChangedListener {
    public static final int MODE_FIXED = 1;
    public static final int MODE_SCROLL = 0;
    protected int mCurrentStyle = Integer.MIN_VALUE;
    private boolean mIndicatorAtTop;
    private int mIndicatorHeight;
    private int mIndicatorOffset;
    private int mIndicatorWidth;
    /* access modifiers changed from: private */
    public boolean mIsRtl;
    private ViewPager.OnPageChangeListener mListener;
    /* access modifiers changed from: private */
    public int mMode;
    private DataSetObserver mObserver = new DataSetObserver() {
        public void onChanged() {
            TabPageIndicator.this.notifyDataSetChanged();
        }

        public void onInvalidated() {
            TabPageIndicator.this.notifyDataSetInvalidated();
        }
    };
    private Paint mPaint;
    /* access modifiers changed from: private */
    public boolean mScrolling;
    private int mSelectedPosition;
    protected int mStyleId;
    /* access modifiers changed from: private */
    public Runnable mTabAnimSelector;
    private TabContainerLayout mTabContainer;
    private int mTabPadding;
    private int mTabRippleStyle;
    private boolean mTabSingleLine;
    private int mTextAppearance;
    private ViewPager mViewPager;

    public TabPageIndicator(Context context) {
        super(context);
        init(context, (AttributeSet) null, 0, 0);
    }

    public TabPageIndicator(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0, 0);
    }

    public TabPageIndicator(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr, 0);
    }

    /* access modifiers changed from: protected */
    public void init(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        setHorizontalScrollBarEnabled(false);
        this.mTabPadding = -1;
        this.mTabSingleLine = true;
        this.mIndicatorHeight = -1;
        this.mIndicatorAtTop = false;
        this.mScrolling = false;
        this.mIsRtl = false;
        Paint paint = new Paint(1);
        this.mPaint = paint;
        paint.setStyle(Paint.Style.FILL);
        this.mPaint.setColor(ThemeUtil.colorAccent(context, -1));
        this.mTabContainer = new TabContainerLayout(context);
        applyStyle(context, attrs, defStyleAttr, defStyleRes);
        if (isInEditMode()) {
            addTemporaryTab();
        }
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
        TypedArray a = context.obtainStyledAttributes(attrs, C2500R.styleable.TabPageIndicator, defStyleAttr, defStyleRes);
        int textAppearance = 0;
        int mode = -1;
        int rippleStyle = 0;
        int count = a.getIndexCount();
        for (int i = 0; i < count; i++) {
            int attr = a.getIndex(i);
            if (attr == C2500R.styleable.TabPageIndicator_tpi_tabPadding) {
                this.mTabPadding = a.getDimensionPixelSize(attr, 0);
            } else if (attr == C2500R.styleable.TabPageIndicator_tpi_tabRipple) {
                rippleStyle = a.getResourceId(attr, 0);
            } else if (attr == C2500R.styleable.TabPageIndicator_tpi_indicatorColor) {
                this.mPaint.setColor(a.getColor(attr, 0));
            } else if (attr == C2500R.styleable.TabPageIndicator_tpi_indicatorHeight) {
                this.mIndicatorHeight = a.getDimensionPixelSize(attr, 0);
            } else if (attr == C2500R.styleable.TabPageIndicator_tpi_indicatorAtTop) {
                this.mIndicatorAtTop = a.getBoolean(attr, true);
            } else if (attr == C2500R.styleable.TabPageIndicator_tpi_tabSingleLine) {
                this.mTabSingleLine = a.getBoolean(attr, true);
            } else if (attr == C2500R.styleable.TabPageIndicator_android_textAppearance) {
                textAppearance = a.getResourceId(attr, 0);
            } else if (attr == C2500R.styleable.TabPageIndicator_tpi_mode) {
                mode = a.getInteger(attr, 0);
            }
        }
        a.recycle();
        if (this.mTabPadding < 0) {
            this.mTabPadding = ThemeUtil.dpToPx(context, 12);
        }
        if (this.mIndicatorHeight < 0) {
            this.mIndicatorHeight = ThemeUtil.dpToPx(context, 2);
        }
        if (mode >= 0 && (this.mMode != mode || getChildCount() == 0)) {
            this.mMode = mode;
            removeAllViews();
            int i2 = this.mMode;
            if (i2 == 0) {
                addView(this.mTabContainer, new ViewGroup.LayoutParams(-2, -1));
                setFillViewport(false);
            } else if (i2 == 1) {
                addView(this.mTabContainer, new ViewGroup.LayoutParams(-1, -1));
                setFillViewport(true);
            }
        }
        if (!(textAppearance == 0 || this.mTextAppearance == textAppearance)) {
            this.mTextAppearance = textAppearance;
            int count2 = this.mTabContainer.getChildCount();
            for (int i3 = 0; i3 < count2; i3++) {
                ((CheckedTextView) this.mTabContainer.getChildAt(i3)).setTextAppearance(context, this.mTextAppearance);
            }
        }
        if (!(rippleStyle == 0 || rippleStyle == this.mTabRippleStyle)) {
            this.mTabRippleStyle = rippleStyle;
            int count3 = this.mTabContainer.getChildCount();
            for (int i4 = 0; i4 < count3; i4++) {
                ViewUtil.setBackground(this.mTabContainer.getChildAt(i4), new RippleDrawable.Builder(getContext(), this.mTabRippleStyle).build());
            }
        }
        if (this.mViewPager != null) {
            notifyDataSetChanged();
        }
        requestLayout();
    }

    public void onThemeChanged(ThemeManager.OnThemeChangedEvent event) {
        int style = ThemeManager.getInstance().getCurrentStyle(this.mStyleId);
        if (this.mCurrentStyle != style) {
            this.mCurrentStyle = style;
            applyStyle(style);
        }
    }

    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        Runnable runnable = this.mTabAnimSelector;
        if (runnable != null) {
            post(runnable);
        }
        if (this.mStyleId != 0) {
            ThemeManager.getInstance().registerOnThemeChangedListener(this);
            onThemeChanged((ThemeManager.OnThemeChangedEvent) null);
        }
    }

    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        Runnable runnable = this.mTabAnimSelector;
        if (runnable != null) {
            removeCallbacks(runnable);
        }
        if (this.mStyleId != 0) {
            ThemeManager.getInstance().unregisterOnThemeChangedListener(this);
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

    /* access modifiers changed from: protected */
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode = View.MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = View.MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = View.MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = View.MeasureSpec.getSize(heightMeasureSpec);
        int ws = widthMeasureSpec;
        if (ws != 0) {
            ws = View.MeasureSpec.makeMeasureSpec((widthSize - getPaddingLeft()) - getPaddingRight(), widthMode);
        }
        int hs = heightMeasureSpec;
        if (heightMode != 0) {
            hs = View.MeasureSpec.makeMeasureSpec((heightSize - getPaddingTop()) - getPaddingBottom(), heightMode);
        }
        this.mTabContainer.measure(ws, hs);
        int width = 0;
        if (widthMode == Integer.MIN_VALUE) {
            width = Math.min(this.mTabContainer.getMeasuredWidth() + getPaddingLeft() + getPaddingRight(), widthSize);
        } else if (widthMode == 0) {
            width = this.mTabContainer.getMeasuredWidth() + getPaddingLeft() + getPaddingRight();
        } else if (widthMode == 1073741824) {
            width = widthSize;
        }
        int height = 0;
        if (heightMode == Integer.MIN_VALUE) {
            height = Math.min(this.mTabContainer.getMeasuredHeight() + getPaddingTop() + getPaddingBottom(), heightSize);
        } else if (heightMode == 0) {
            height = this.mTabContainer.getMeasuredHeight() + getPaddingTop() + getPaddingBottom();
        } else if (heightMode == 1073741824) {
            height = heightSize;
        }
        if (!(this.mTabContainer.getMeasuredWidth() == (width - getPaddingLeft()) - getPaddingRight() && this.mTabContainer.getMeasuredHeight() == (height - getPaddingTop()) - getPaddingBottom())) {
            this.mTabContainer.measure(View.MeasureSpec.makeMeasureSpec((width - getPaddingLeft()) - getPaddingRight(), Ints.MAX_POWER_OF_TWO), View.MeasureSpec.makeMeasureSpec((height - getPaddingTop()) - getPaddingBottom(), Ints.MAX_POWER_OF_TWO));
        }
        setMeasuredDimension(width, height);
    }

    /* access modifiers changed from: protected */
    public void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        TextView tv = getTabView(this.mSelectedPosition);
        if (tv != null) {
            updateIndicator(tv.getLeft(), tv.getMeasuredWidth());
        }
    }

    /* access modifiers changed from: private */
    public CheckedTextView getTabView(int position) {
        return (CheckedTextView) this.mTabContainer.getChildAt(position);
    }

    private void animateToTab(final int position) {
        if (getTabView(position) != null) {
            Runnable runnable = this.mTabAnimSelector;
            if (runnable != null) {
                removeCallbacks(runnable);
            }
            C14492 r0 = new Runnable() {
                public void run() {
                    CheckedTextView tv = TabPageIndicator.this.getTabView(position);
                    if (tv != null) {
                        if (!TabPageIndicator.this.mScrolling) {
                            TabPageIndicator.this.updateIndicator(tv.getLeft(), tv.getMeasuredWidth());
                        }
                        TabPageIndicator.this.smoothScrollTo((tv.getLeft() - ((TabPageIndicator.this.getWidth() - tv.getWidth()) / 2)) + TabPageIndicator.this.getPaddingLeft(), 0);
                    }
                    Runnable unused = TabPageIndicator.this.mTabAnimSelector = null;
                }
            };
            this.mTabAnimSelector = r0;
            post(r0);
        }
    }

    public void setOnPageChangeListener(ViewPager.OnPageChangeListener listener) {
        this.mListener = listener;
    }

    public void setViewPager(ViewPager view) {
        ViewPager viewPager = this.mViewPager;
        if (viewPager != view) {
            if (viewPager != null) {
                viewPager.removeOnPageChangeListener(this);
                PagerAdapter adapter = this.mViewPager.getAdapter();
                if (adapter != null) {
                    adapter.unregisterDataSetObserver(this.mObserver);
                }
            }
            this.mViewPager = view;
            if (view != null) {
                PagerAdapter adapter2 = view.getAdapter();
                if (adapter2 != null) {
                    adapter2.registerDataSetObserver(this.mObserver);
                    this.mViewPager.addOnPageChangeListener(this);
                    notifyDataSetChanged();
                    onPageSelected(this.mViewPager.getCurrentItem());
                    return;
                }
                throw new IllegalStateException("ViewPager does not have adapter instance.");
            }
            this.mTabContainer.removeAllViews();
        }
    }

    public void setViewPager(ViewPager view, int initialPosition) {
        setViewPager(view);
        setCurrentItem(initialPosition);
    }

    /* access modifiers changed from: private */
    public void updateIndicator(int offset, int width) {
        this.mIndicatorOffset = offset;
        this.mIndicatorWidth = width;
        invalidate();
    }

    public void draw(Canvas canvas) {
        super.draw(canvas);
        int x = this.mIndicatorOffset + getPaddingLeft();
        int y = this.mIndicatorAtTop ? 0 : getHeight() - this.mIndicatorHeight;
        canvas.drawRect((float) x, (float) y, (float) (this.mIndicatorWidth + x), (float) (this.mIndicatorHeight + y), this.mPaint);
        if (isInEditMode()) {
            canvas.drawRect((float) getPaddingLeft(), (float) y, (float) (getPaddingLeft() + this.mTabContainer.getChildAt(0).getWidth()), (float) (this.mIndicatorHeight + y), this.mPaint);
        }
    }

    public void onPageScrollStateChanged(int state) {
        if (state == 0) {
            this.mScrolling = false;
            TextView tv = getTabView(this.mSelectedPosition);
            if (tv != null) {
                updateIndicator(tv.getLeft(), tv.getMeasuredWidth());
            }
        } else {
            this.mScrolling = true;
        }
        ViewPager.OnPageChangeListener onPageChangeListener = this.mListener;
        if (onPageChangeListener != null) {
            onPageChangeListener.onPageScrollStateChanged(state);
        }
    }

    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        ViewPager.OnPageChangeListener onPageChangeListener = this.mListener;
        if (onPageChangeListener != null) {
            onPageChangeListener.onPageScrolled(position, positionOffset, positionOffsetPixels);
        }
        CheckedTextView tv_scroll = getTabView(position);
        CheckedTextView tv_next = getTabView(position + 1);
        if (tv_scroll != null && tv_next != null) {
            int width_scroll = tv_scroll.getMeasuredWidth();
            int width_next = tv_next.getMeasuredWidth();
            int width = (int) (((float) width_scroll) + (((float) (width_next - width_scroll)) * positionOffset) + 0.5f);
            updateIndicator((int) ((((((float) tv_scroll.getLeft()) + (((float) width_scroll) / 2.0f)) + ((((float) (width_scroll + width_next)) / 2.0f) * positionOffset)) - (((float) width) / 2.0f)) + 0.5f), width);
        }
    }

    public void onPageSelected(int position) {
        setCurrentItem(position);
        ViewPager.OnPageChangeListener onPageChangeListener = this.mListener;
        if (onPageChangeListener != null) {
            onPageChangeListener.onPageSelected(position);
        }
    }

    public void onClick(View v) {
        ViewPager.OnPageChangeListener onPageChangeListener;
        int position = ((Integer) v.getTag()).intValue();
        if (position == this.mSelectedPosition && (onPageChangeListener = this.mListener) != null) {
            onPageChangeListener.onPageSelected(position);
        }
        this.mViewPager.setCurrentItem(position, true);
    }

    public void setCurrentItem(int position) {
        CheckedTextView tv;
        int i = this.mSelectedPosition;
        if (!(i == position || (tv = getTabView(i)) == null)) {
            tv.setChecked(false);
        }
        this.mSelectedPosition = position;
        CheckedTextView tv2 = getTabView(position);
        if (tv2 != null) {
            tv2.setChecked(true);
        }
        animateToTab(position);
    }

    /* access modifiers changed from: private */
    public void notifyDataSetChanged() {
        this.mTabContainer.removeAllViews();
        PagerAdapter adapter = this.mViewPager.getAdapter();
        int count = adapter.getCount();
        if (this.mSelectedPosition > count) {
            this.mSelectedPosition = count - 1;
        }
        for (int i = 0; i < count; i++) {
            CharSequence title = adapter.getPageTitle(i);
            if (title == null) {
                title = "NULL";
            }
            CheckedTextView tv = new CheckedTextView(getContext());
            tv.setCheckMarkDrawable((Drawable) null);
            tv.setText(title);
            tv.setGravity(17);
            if (Build.VERSION.SDK_INT >= 17) {
                tv.setTextAlignment(1);
            }
            tv.setTextAppearance(getContext(), this.mTextAppearance);
            if (this.mTabSingleLine) {
                tv.setSingleLine(true);
            } else {
                tv.setSingleLine(false);
                tv.setMaxLines(2);
            }
            tv.setEllipsize(TextUtils.TruncateAt.END);
            tv.setOnClickListener(this);
            tv.setTag(Integer.valueOf(i));
            if (this.mTabRippleStyle > 0) {
                ViewUtil.setBackground(tv, new RippleDrawable.Builder(getContext(), this.mTabRippleStyle).build());
            }
            int i2 = this.mTabPadding;
            tv.setPadding(i2, 0, i2, 0);
            this.mTabContainer.addView(tv, new ViewGroup.LayoutParams(-2, -1));
        }
        setCurrentItem(this.mSelectedPosition);
        requestLayout();
    }

    /* access modifiers changed from: private */
    public void notifyDataSetInvalidated() {
        PagerAdapter adapter = this.mViewPager.getAdapter();
        int count = adapter.getCount();
        for (int i = 0; i < count; i++) {
            TextView tv = getTabView(i);
            if (tv != null) {
                CharSequence title = adapter.getPageTitle(i);
                if (title == null) {
                    title = "NULL";
                }
                tv.setText(title);
            }
        }
        requestLayout();
    }

    private void addTemporaryTab() {
        int i = 0;
        while (i < 3) {
            CharSequence title = null;
            if (i == 0) {
                title = "TAB ONE";
            } else if (i == 1) {
                title = "TAB TWO";
            } else if (i == 2) {
                title = "TAB THREE";
            }
            CheckedTextView tv = new CheckedTextView(getContext());
            tv.setCheckMarkDrawable((Drawable) null);
            tv.setText(title);
            tv.setGravity(17);
            if (Build.VERSION.SDK_INT >= 17) {
                tv.setTextAlignment(1);
            }
            tv.setTextAppearance(getContext(), this.mTextAppearance);
            tv.setSingleLine(true);
            tv.setEllipsize(TextUtils.TruncateAt.END);
            tv.setTag(Integer.valueOf(i));
            tv.setChecked(i == 0);
            int i2 = this.mMode;
            if (i2 == 0) {
                int i3 = this.mTabPadding;
                tv.setPadding(i3, 0, i3, 0);
                this.mTabContainer.addView(tv, new ViewGroup.LayoutParams(-2, -1));
            } else if (i2 == 1) {
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, -1);
                params.weight = 1.0f;
                this.mTabContainer.addView(tv, params);
            }
            i++;
        }
    }

    private class TabContainerLayout extends FrameLayout {
        public TabContainerLayout(Context context) {
            super(context);
        }

        /* access modifiers changed from: protected */
        public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
            int widthMode = View.MeasureSpec.getMode(widthMeasureSpec);
            int widthSize = View.MeasureSpec.getSize(widthMeasureSpec);
            int width = 0;
            int height = 0;
            int childWidth = 0;
            if (TabPageIndicator.this.mMode == 0) {
                int ws = View.MeasureSpec.makeMeasureSpec(0, 0);
                for (int i = 0; i < getChildCount(); i++) {
                    View child = getChildAt(i);
                    child.measure(ws, heightMeasureSpec);
                    width += child.getMeasuredWidth();
                    height = Math.max(height, child.getMeasuredHeight());
                }
                setMeasuredDimension(width, height);
            } else if (widthMode != 1073741824) {
                int ws2 = View.MeasureSpec.makeMeasureSpec(0, 0);
                for (int i2 = 0; i2 < getChildCount(); i2++) {
                    View child2 = getChildAt(i2);
                    child2.measure(ws2, heightMeasureSpec);
                    width += child2.getMeasuredWidth();
                    height = Math.max(height, child2.getMeasuredHeight());
                }
                if (widthMode == 0 || width < widthSize) {
                    setMeasuredDimension(widthSize, height);
                } else {
                    int count = getChildCount();
                    if (count != 0) {
                        childWidth = widthSize / count;
                    }
                    for (int i3 = 0; i3 < count; i3++) {
                        View child3 = getChildAt(i3);
                        if (i3 != count - 1) {
                            child3.measure(View.MeasureSpec.makeMeasureSpec(childWidth, Ints.MAX_POWER_OF_TWO), heightMeasureSpec);
                        } else {
                            child3.measure(View.MeasureSpec.makeMeasureSpec(widthSize - ((count - 1) * childWidth), Ints.MAX_POWER_OF_TWO), heightMeasureSpec);
                        }
                    }
                    setMeasuredDimension(widthSize, height);
                }
            } else {
                int count2 = getChildCount();
                if (count2 != 0) {
                    childWidth = widthSize / count2;
                }
                for (int i4 = 0; i4 < count2; i4++) {
                    View child4 = getChildAt(i4);
                    if (i4 != count2 - 1) {
                        child4.measure(View.MeasureSpec.makeMeasureSpec(childWidth, Ints.MAX_POWER_OF_TWO), heightMeasureSpec);
                    } else {
                        child4.measure(View.MeasureSpec.makeMeasureSpec(widthSize - ((count2 - 1) * childWidth), Ints.MAX_POWER_OF_TWO), heightMeasureSpec);
                    }
                    height = Math.max(height, child4.getMeasuredHeight());
                }
                setMeasuredDimension(widthSize, height);
            }
            int hs = View.MeasureSpec.makeMeasureSpec(height, Ints.MAX_POWER_OF_TWO);
            for (int i5 = 0; i5 < getChildCount(); i5++) {
                View child5 = getChildAt(i5);
                if (child5.getMeasuredHeight() != height) {
                    child5.measure(View.MeasureSpec.makeMeasureSpec(child5.getMeasuredWidth(), Ints.MAX_POWER_OF_TWO), hs);
                }
            }
        }

        /* access modifiers changed from: protected */
        public void onLayout(boolean changed, int left, int top, int right, int bottom) {
            int childLeft = 0;
            int childRight = right - left;
            int childBottom = bottom - top;
            if (TabPageIndicator.this.mIsRtl) {
                int count = getChildCount();
                for (int i = 0; i < count; i++) {
                    View child = getChildAt(i);
                    child.layout(childRight - child.getMeasuredWidth(), 0, childRight, childBottom);
                    childRight -= child.getMeasuredWidth();
                }
                return;
            }
            int count2 = getChildCount();
            for (int i2 = 0; i2 < count2; i2++) {
                View child2 = getChildAt(i2);
                child2.layout(childLeft, 0, child2.getMeasuredWidth() + childLeft, childBottom);
                childLeft += child2.getMeasuredWidth();
            }
        }
    }
}
