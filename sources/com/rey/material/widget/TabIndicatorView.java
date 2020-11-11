package com.rey.material.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Checkable;
import android.widget.ImageView;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;
import com.rey.material.C2500R;
import com.rey.material.app.ThemeManager;
import com.rey.material.drawable.RippleDrawable;
import com.rey.material.util.ThemeUtil;
import com.rey.material.util.ViewUtil;

public class TabIndicatorView extends RecyclerView implements ThemeManager.OnThemeChangedListener {
    public static final int MODE_FIXED = 1;
    public static final int MODE_SCROLL = 0;
    public static final int SCROLL_STATE_DRAGGING = 1;
    public static final int SCROLL_STATE_IDLE = 0;
    public static final int SCROLL_STATE_SETTLING = 2;
    private Adapter mAdapter;
    private boolean mCenterCurrentTab;
    protected int mCurrentStyle = Integer.MIN_VALUE;
    private TabIndicatorFactory mFactory;
    private boolean mIndicatorAtTop;
    private int mIndicatorHeight;
    private int mIndicatorOffset;
    private int mIndicatorWidth;
    private boolean mIsRtl;
    /* access modifiers changed from: private */
    public RecyclerView.LayoutManager mLayoutManager;
    private int mMode;
    private Paint mPaint;
    /* access modifiers changed from: private */
    public boolean mScrolling;
    private boolean mScrollingToCenter = false;
    /* access modifiers changed from: private */
    public int mSelectedPosition;
    protected int mStyleId;
    /* access modifiers changed from: private */
    public Runnable mTabAnimSelector;
    /* access modifiers changed from: private */
    public int mTabPadding;
    /* access modifiers changed from: private */
    public int mTabRippleStyle;
    /* access modifiers changed from: private */
    public boolean mTabSingleLine;
    /* access modifiers changed from: private */
    public int mTextAppearance;

    public TabIndicatorView(Context context) {
        super(context);
        init(context, (AttributeSet) null, 0, 0);
    }

    public TabIndicatorView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0, 0);
    }

    public TabIndicatorView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr, 0);
    }

    /* access modifiers changed from: protected */
    public void init(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        setHorizontalScrollBarEnabled(false);
        this.mTabPadding = -1;
        this.mTabSingleLine = true;
        this.mCenterCurrentTab = false;
        this.mIndicatorHeight = -1;
        this.mIndicatorAtTop = false;
        this.mScrolling = false;
        this.mIsRtl = false;
        Paint paint = new Paint(1);
        this.mPaint = paint;
        paint.setStyle(Paint.Style.FILL);
        this.mPaint.setColor(ThemeUtil.colorAccent(context, -1));
        Adapter adapter = new Adapter();
        this.mAdapter = adapter;
        setAdapter(adapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context, 0, this.mIsRtl);
        this.mLayoutManager = linearLayoutManager;
        setLayoutManager(linearLayoutManager);
        setItemAnimator(new DefaultItemAnimator());
        addOnScrollListener(new RecyclerView.OnScrollListener() {
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                if (newState == 0) {
                    TabIndicatorView tabIndicatorView = TabIndicatorView.this;
                    tabIndicatorView.updateIndicator(tabIndicatorView.mLayoutManager.findViewByPosition(TabIndicatorView.this.mSelectedPosition));
                }
            }

            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                TabIndicatorView tabIndicatorView = TabIndicatorView.this;
                tabIndicatorView.updateIndicator(tabIndicatorView.mLayoutManager.findViewByPosition(TabIndicatorView.this.mSelectedPosition));
            }
        });
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
        Context context2 = context;
        TypedArray a = context2.obtainStyledAttributes(attrs, C2500R.styleable.TabPageIndicator, defStyleAttr, defStyleRes);
        int tabPadding = -1;
        int textAppearance = 0;
        int mode = -1;
        int rippleStyle = 0;
        boolean tabSingleLine = false;
        boolean singleLineDefined = false;
        int i = 0;
        int count = a.getIndexCount();
        while (i < count) {
            int attr = a.getIndex(i);
            if (attr == C2500R.styleable.TabPageIndicator_tpi_tabPadding) {
                tabPadding = a.getDimensionPixelSize(attr, 0);
            } else if (attr == C2500R.styleable.TabPageIndicator_tpi_tabRipple) {
                rippleStyle = a.getResourceId(attr, 0);
            } else if (attr == C2500R.styleable.TabPageIndicator_tpi_indicatorColor) {
                this.mPaint.setColor(a.getColor(attr, 0));
            } else if (attr == C2500R.styleable.TabPageIndicator_tpi_indicatorHeight) {
                this.mIndicatorHeight = a.getDimensionPixelSize(attr, 0);
            } else if (attr == C2500R.styleable.TabPageIndicator_tpi_indicatorAtTop) {
                this.mIndicatorAtTop = a.getBoolean(attr, true);
            } else if (attr == C2500R.styleable.TabPageIndicator_tpi_tabSingleLine) {
                singleLineDefined = true;
                tabSingleLine = a.getBoolean(attr, true);
            } else if (attr == C2500R.styleable.TabPageIndicator_tpi_centerCurrentTab) {
                this.mCenterCurrentTab = a.getBoolean(attr, true);
            } else if (attr == C2500R.styleable.TabPageIndicator_android_textAppearance) {
                textAppearance = a.getResourceId(attr, 0);
            } else if (attr == C2500R.styleable.TabPageIndicator_tpi_mode) {
                mode = a.getInteger(attr, 0);
            }
            i++;
            AttributeSet attributeSet = attrs;
        }
        a.recycle();
        if (this.mIndicatorHeight < 0) {
            this.mIndicatorHeight = ThemeUtil.dpToPx(context2, 2);
        }
        boolean shouldNotify = false;
        if (tabPadding >= 0 && this.mTabPadding != tabPadding) {
            this.mTabPadding = tabPadding;
            shouldNotify = true;
        }
        if (singleLineDefined && this.mTabSingleLine != tabSingleLine) {
            this.mTabSingleLine = tabSingleLine;
            shouldNotify = true;
        }
        if (mode >= 0 && this.mMode != mode) {
            this.mMode = mode;
            this.mAdapter.setFixedWidth(0, 0);
            shouldNotify = true;
        }
        if (!(textAppearance == 0 || this.mTextAppearance == textAppearance)) {
            this.mTextAppearance = textAppearance;
            shouldNotify = true;
        }
        if (!(rippleStyle == 0 || rippleStyle == this.mTabRippleStyle)) {
            this.mTabRippleStyle = rippleStyle;
            shouldNotify = true;
        }
        if (shouldNotify) {
            Adapter adapter = this.mAdapter;
            adapter.notifyItemRangeChanged(0, adapter.getItemCount());
        }
        invalidate();
    }

    public void setTabIndicatorFactory(TabIndicatorFactory factory) {
        this.mFactory = factory;
        this.mAdapter.setFactory(factory);
    }

    private void animateToTab(final int position) {
        if (position >= 0 && position < this.mAdapter.getItemCount()) {
            Runnable runnable = this.mTabAnimSelector;
            if (runnable != null) {
                removeCallbacks(runnable);
            }
            C14472 r0 = new Runnable() {
                public void run() {
                    View v = TabIndicatorView.this.mLayoutManager.findViewByPosition(position);
                    if (!TabIndicatorView.this.mScrolling) {
                        TabIndicatorView.this.updateIndicator(v);
                    }
                    TabIndicatorView tabIndicatorView = TabIndicatorView.this;
                    tabIndicatorView.smoothScrollToPosition(tabIndicatorView.mSelectedPosition);
                    Runnable unused = TabIndicatorView.this.mTabAnimSelector = null;
                }
            };
            this.mTabAnimSelector = r0;
            post(r0);
        }
    }

    private void updateIndicator(int offset, int width) {
        this.mIndicatorOffset = offset;
        this.mIndicatorWidth = width;
        invalidate();
    }

    /* access modifiers changed from: private */
    public void updateIndicator(View anchorView) {
        if (anchorView != null) {
            updateIndicator(anchorView.getLeft(), anchorView.getMeasuredWidth());
            ((Checkable) anchorView).setChecked(true);
            return;
        }
        updateIndicator(getWidth(), 0);
    }

    public void setCurrentTab(int position) {
        View v;
        int i = this.mSelectedPosition;
        if (!(i == position || (v = this.mLayoutManager.findViewByPosition(i)) == null)) {
            ((Checkable) v).setChecked(false);
        }
        this.mSelectedPosition = position;
        View v2 = this.mLayoutManager.findViewByPosition(position);
        if (v2 != null) {
            ((Checkable) v2).setChecked(true);
        }
        animateToTab(position);
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
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), 0, this.mIsRtl);
            this.mLayoutManager = linearLayoutManager;
            setLayoutManager(linearLayoutManager);
            requestLayout();
        }
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int widthSpec, int heightSpec) {
        super.onMeasure(widthSpec, heightSpec);
        if (this.mMode == 1) {
            int totalWidth = (getMeasuredWidth() - getPaddingLeft()) - getPaddingRight();
            int count = this.mAdapter.getItemCount();
            if (count > 0) {
                int width = totalWidth / count;
                this.mAdapter.setFixedWidth(width, totalWidth - ((count - 1) * width));
                return;
            }
            this.mAdapter.setFixedWidth(totalWidth, totalWidth);
        }
    }

    /* access modifiers changed from: protected */
    public void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        updateIndicator(this.mLayoutManager.findViewByPosition(this.mSelectedPosition));
    }

    public void draw(Canvas canvas) {
        super.draw(canvas);
        int x = this.mIndicatorOffset;
        int y = this.mIndicatorAtTop ? 0 : getHeight() - this.mIndicatorHeight;
        canvas.drawRect((float) x, (float) y, (float) (this.mIndicatorWidth + x), (float) (this.mIndicatorHeight + y), this.mPaint);
    }

    /* access modifiers changed from: protected */
    public void onTabScrollStateChanged(int state) {
        View v;
        int scrollNeeded;
        if (this.mCenterCurrentTab) {
            if (state == 0 && !this.mScrollingToCenter && (v = this.mLayoutManager.findViewByPosition(this.mSelectedPosition)) != null && (scrollNeeded = ((v.getLeft() + v.getRight()) / 2) - ((((getLeft() + getPaddingLeft()) + getRight()) - getPaddingRight()) / 2)) != 0) {
                smoothScrollBy(scrollNeeded, 0);
                this.mScrollingToCenter = true;
            }
            if (state == 1 || state == 2) {
                this.mScrollingToCenter = false;
            }
        }
        if (state == 0) {
            this.mScrolling = false;
            updateIndicator(this.mLayoutManager.findViewByPosition(this.mSelectedPosition));
            return;
        }
        this.mScrolling = true;
    }

    /* access modifiers changed from: protected */
    public void onTabScrolled(int position, float positionOffset) {
        View scrollView = this.mLayoutManager.findViewByPosition(position);
        View nextView = this.mLayoutManager.findViewByPosition(position + 1);
        if (scrollView != null && nextView != null) {
            int width_scroll = scrollView.getMeasuredWidth();
            int width_next = nextView.getMeasuredWidth();
            int width = (int) (((float) width_scroll) + (((float) (width_next - width_scroll)) * positionOffset) + 0.5f);
            updateIndicator((int) ((((((float) scrollView.getLeft()) + (((float) width_scroll) / 2.0f)) + ((((float) (width_scroll + width_next)) / 2.0f) * positionOffset)) - (((float) width) / 2.0f)) + 0.5f), width);
        }
    }

    /* access modifiers changed from: protected */
    public void onTabSelected(int position) {
        setCurrentTab(position);
    }

    public static abstract class TabIndicatorFactory {
        private TabIndicatorView mView;

        public abstract int getCurrentTabIndicator();

        public abstract Drawable getIcon(int i);

        public abstract int getTabIndicatorCount();

        public abstract CharSequence getText(int i);

        public abstract boolean isIconTabIndicator(int i);

        public abstract void onTabIndicatorSelected(int i);

        /* access modifiers changed from: protected */
        public void setTabIndicatorView(TabIndicatorView view) {
            this.mView = view;
        }

        public final void notifyTabScrollStateChanged(int state) {
            this.mView.onTabScrollStateChanged(state);
        }

        public final void notifyTabScrolled(int position, float positionOffset) {
            this.mView.onTabScrolled(position, positionOffset);
        }

        public final void notifyTabSelected(int position) {
            this.mView.onTabSelected(position);
        }

        public final void notifyDataSetChanged() {
            this.mView.getAdapter().notifyDataSetChanged();
        }

        public final void notifyTabChanged(int position) {
            this.mView.getAdapter().notifyItemRangeChanged(position, 1);
        }

        public final void notifyTabRangeChanged(int positionStart, int itemCount) {
            this.mView.getAdapter().notifyItemRangeChanged(positionStart, itemCount);
        }

        public final void notifyTabInserted(int position) {
            this.mView.getAdapter().notifyItemRangeInserted(position, 1);
        }

        public final void notifyTabMoved(int fromPosition, int toPosition) {
            this.mView.getAdapter().notifyItemMoved(fromPosition, toPosition);
        }

        public final void notifyTabRangeInserted(int positionStart, int itemCount) {
            this.mView.getAdapter().notifyItemRangeInserted(positionStart, itemCount);
        }

        public final void notifyTabRemoved(int position) {
            this.mView.getAdapter().notifyItemRangeRemoved(position, 1);
        }

        public final void notifyTabRangeRemoved(int positionStart, int itemCount) {
            this.mView.getAdapter().notifyItemRangeRemoved(positionStart, itemCount);
        }
    }

    class Adapter extends RecyclerView.Adapter<ViewHolder> implements View.OnClickListener {
        static final int TYPE_ICON = 1;
        static final int TYPE_TEXT = 0;
        TabIndicatorFactory mFactory;
        int mFixedWidth;
        int mLastFixedWidth;

        Adapter() {
        }

        public void setFactory(TabIndicatorFactory factory) {
            TabIndicatorFactory tabIndicatorFactory = this.mFactory;
            if (tabIndicatorFactory != null) {
                tabIndicatorFactory.setTabIndicatorView((TabIndicatorView) null);
            }
            int prevCount = getItemCount();
            if (prevCount > 0) {
                notifyItemRangeRemoved(0, prevCount);
            }
            this.mFactory = factory;
            if (factory != null) {
                factory.setTabIndicatorView(TabIndicatorView.this);
            }
            int count = getItemCount();
            if (count > 0) {
                notifyItemRangeInserted(0, count);
            }
            TabIndicatorFactory tabIndicatorFactory2 = this.mFactory;
            if (tabIndicatorFactory2 != null) {
                TabIndicatorView.this.onTabSelected(tabIndicatorFactory2.getCurrentTabIndicator());
            }
        }

        public void setFixedWidth(int width, int lastWidth) {
            if (this.mFixedWidth != width || this.mLastFixedWidth != lastWidth) {
                this.mFixedWidth = width;
                this.mLastFixedWidth = lastWidth;
                int count = getItemCount();
                if (count > 0) {
                    notifyItemRangeChanged(0, count);
                }
            }
        }

        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = null;
            if (viewType == 0) {
                v = new CheckedTextView(parent.getContext());
            } else if (viewType == 1) {
                v = new CheckedImageView(parent.getContext());
            }
            ViewHolder holder = new ViewHolder(v);
            v.setTag(holder);
            v.setLayoutParams(new ViewGroup.LayoutParams(-2, -1));
            v.setOnClickListener(this);
            if (viewType == 0) {
                holder.textView.setCheckMarkDrawable((Drawable) null);
                if (Build.VERSION.SDK_INT >= 17) {
                    holder.textView.setTextAlignment(1);
                }
                holder.textView.setGravity(17);
                holder.textView.setEllipsize(TextUtils.TruncateAt.END);
                holder.textView.setSingleLine(true);
            } else if (viewType == 1) {
                holder.iconView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
            }
            return holder;
        }

        public void onBindViewHolder(ViewHolder holder, int position) {
            int viewType = getItemViewType(position);
            ViewGroup.LayoutParams params = holder.itemView.getLayoutParams();
            boolean z = true;
            if (this.mFixedWidth > 0) {
                params.width = position == getItemCount() - 1 ? this.mLastFixedWidth : this.mFixedWidth;
            } else {
                params.width = -2;
            }
            holder.itemView.setLayoutParams(params);
            if (holder.padding != TabIndicatorView.this.mTabPadding) {
                holder.padding = TabIndicatorView.this.mTabPadding;
                holder.itemView.setPadding(TabIndicatorView.this.mTabPadding, 0, TabIndicatorView.this.mTabPadding, 0);
            }
            if (holder.rippleStyle != TabIndicatorView.this.mTabRippleStyle) {
                holder.rippleStyle = TabIndicatorView.this.mTabRippleStyle;
                if (TabIndicatorView.this.mTabRippleStyle > 0) {
                    ViewUtil.setBackground(holder.itemView, new RippleDrawable.Builder(TabIndicatorView.this.getContext(), TabIndicatorView.this.mTabRippleStyle).build());
                }
            }
            if (viewType == 0) {
                if (holder.textAppearance != TabIndicatorView.this.mTextAppearance) {
                    holder.textAppearance = TabIndicatorView.this.mTextAppearance;
                    holder.textView.setTextAppearance(TabIndicatorView.this.getContext(), TabIndicatorView.this.mTextAppearance);
                }
                if (holder.singleLine != TabIndicatorView.this.mTabSingleLine) {
                    holder.singleLine = TabIndicatorView.this.mTabSingleLine;
                    if (TabIndicatorView.this.mTabSingleLine) {
                        holder.textView.setSingleLine(true);
                    } else {
                        holder.textView.setSingleLine(false);
                        holder.textView.setMaxLines(2);
                    }
                }
                holder.textView.setText(this.mFactory.getText(position));
                CheckedTextView checkedTextView = holder.textView;
                if (position != TabIndicatorView.this.mSelectedPosition) {
                    z = false;
                }
                checkedTextView.setChecked(z);
            } else if (viewType == 1) {
                holder.iconView.setImageDrawable(this.mFactory.getIcon(position));
                CheckedImageView checkedImageView = holder.iconView;
                if (position != TabIndicatorView.this.mSelectedPosition) {
                    z = false;
                }
                checkedImageView.setChecked(z);
            }
        }

        public int getItemViewType(int position) {
            return this.mFactory.isIconTabIndicator(position) ? 1 : 0;
        }

        public int getItemCount() {
            TabIndicatorFactory tabIndicatorFactory = this.mFactory;
            if (tabIndicatorFactory == null) {
                return 0;
            }
            return tabIndicatorFactory.getTabIndicatorCount();
        }

        public void onClick(View view) {
            this.mFactory.onTabIndicatorSelected(((ViewHolder) view.getTag()).getAdapterPosition());
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        CheckedImageView iconView;
        int padding = 0;
        int rippleStyle = 0;
        boolean singleLine = true;
        int textAppearance = 0;
        CheckedTextView textView;

        public ViewHolder(View itemView) {
            super(itemView);
            if (itemView instanceof CheckedImageView) {
                this.iconView = (CheckedImageView) itemView;
            } else if (itemView instanceof CheckedTextView) {
                this.textView = (CheckedTextView) itemView;
            }
        }
    }

    public static class ViewPagerIndicatorFactory extends TabIndicatorFactory implements ViewPager.OnPageChangeListener {
        ViewPager mViewPager;

        public ViewPagerIndicatorFactory(ViewPager vp) {
            this.mViewPager = vp;
            vp.addOnPageChangeListener(this);
        }

        public int getTabIndicatorCount() {
            return this.mViewPager.getAdapter().getCount();
        }

        public boolean isIconTabIndicator(int position) {
            return false;
        }

        public Drawable getIcon(int position) {
            return null;
        }

        public CharSequence getText(int position) {
            return this.mViewPager.getAdapter().getPageTitle(position);
        }

        public void onTabIndicatorSelected(int position) {
            this.mViewPager.setCurrentItem(position, true);
        }

        public int getCurrentTabIndicator() {
            return this.mViewPager.getCurrentItem();
        }

        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            notifyTabScrolled(position, positionOffset);
        }

        public void onPageSelected(int position) {
            notifyTabSelected(position);
        }

        public void onPageScrollStateChanged(int state) {
            if (state == 0) {
                notifyTabScrollStateChanged(0);
            } else if (state == 1) {
                notifyTabScrollStateChanged(1);
            } else if (state == 2) {
                notifyTabScrollStateChanged(2);
            }
        }
    }
}
