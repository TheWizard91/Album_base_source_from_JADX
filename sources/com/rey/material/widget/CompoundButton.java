package com.rey.material.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import com.rey.material.app.ThemeManager;
import com.rey.material.drawable.PaddingDrawable;
import com.rey.material.drawable.RippleDrawable;
import com.rey.material.util.ViewUtil;

public class CompoundButton extends android.widget.CompoundButton implements ThemeManager.OnThemeChangedListener {
    protected int mCurrentStyle = Integer.MIN_VALUE;
    private boolean mIsRtl = false;
    private volatile PaddingDrawable mPaddingDrawable;
    private RippleManager mRippleManager;
    protected int mStyleId;

    public CompoundButton(Context context) {
        super(context);
        init(context, (AttributeSet) null, 0, 0);
    }

    public CompoundButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0, 0);
    }

    public CompoundButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr, 0);
    }

    /* access modifiers changed from: protected */
    public void init(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        if (Build.VERSION.SDK_INT >= 17) {
            applyPadding(context, attrs, defStyleAttr, defStyleRes);
        }
        setClickable(true);
        ViewUtil.applyFont(this, attrs, defStyleAttr, defStyleRes);
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
        getRippleManager().onCreate(this, context, attrs, defStyleAttr, defStyleRes);
    }

    private void applyPadding(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        int i;
        int i2;
        int i3;
        int i4;
        int i5;
        int i6;
        TypedArray a = context.obtainStyledAttributes(attrs, new int[]{16842965, 16842966, 16842967, 16842968, 16842969, 16843699, 16843700}, defStyleAttr, defStyleRes);
        int padding = -1;
        int leftPadding = -1;
        int topPadding = -1;
        int rightPadding = -1;
        int bottomPadding = -1;
        int startPadding = Integer.MIN_VALUE;
        int endPadding = Integer.MIN_VALUE;
        boolean startPaddingDefined = false;
        boolean endPaddingDefined = false;
        boolean leftPaddingDefined = false;
        boolean rightPaddingDefined = false;
        int count = a.getIndexCount();
        int i7 = 0;
        while (i7 < count) {
            int count2 = count;
            int count3 = a.getIndex(i7);
            if (count3 == 0) {
                rightPaddingDefined = true;
                leftPaddingDefined = true;
                padding = a.getDimensionPixelSize(count3, -1);
            } else if (count3 == 1) {
                leftPaddingDefined = true;
                leftPadding = a.getDimensionPixelSize(count3, -1);
            } else if (count3 == 2) {
                topPadding = a.getDimensionPixelSize(count3, -1);
            } else if (count3 == 3) {
                rightPaddingDefined = true;
                rightPadding = a.getDimensionPixelSize(count3, -1);
            } else if (count3 == 4) {
                bottomPadding = a.getDimensionPixelSize(count3, -1);
            } else if (count3 == 5) {
                if (Build.VERSION.SDK_INT >= 17) {
                    int startPadding2 = a.getDimensionPixelSize(count3, Integer.MIN_VALUE);
                    startPaddingDefined = startPadding2 != Integer.MIN_VALUE;
                    startPadding = startPadding2;
                }
            } else if (count3 == 6 && Build.VERSION.SDK_INT >= 17) {
                int endPadding2 = a.getDimensionPixelSize(count3, Integer.MIN_VALUE);
                endPaddingDefined = endPadding2 != Integer.MIN_VALUE;
                endPadding = endPadding2;
            }
            i7++;
            int i8 = defStyleAttr;
            int i9 = defStyleRes;
            count = count2;
        }
        a.recycle();
        if (padding >= 0) {
            setPadding(padding, padding, padding, padding);
            return;
        }
        if (leftPaddingDefined || rightPaddingDefined) {
            int paddingLeft = leftPaddingDefined ? leftPadding : getPaddingLeft();
            if (topPadding >= 0) {
                i4 = topPadding;
            } else {
                i4 = getPaddingTop();
            }
            if (rightPaddingDefined) {
                i5 = rightPadding;
            } else {
                i5 = getPaddingRight();
            }
            if (bottomPadding >= 0) {
                i6 = bottomPadding;
            } else {
                i6 = getPaddingBottom();
            }
            setPadding(paddingLeft, i4, i5, i6);
        }
        if (startPaddingDefined || endPaddingDefined) {
            int paddingStart = startPaddingDefined ? startPadding : getPaddingStart();
            if (topPadding >= 0) {
                i = topPadding;
            } else {
                i = getPaddingTop();
            }
            if (endPaddingDefined) {
                i2 = endPadding;
            } else {
                i2 = getPaddingEnd();
            }
            if (bottomPadding >= 0) {
                i3 = bottomPadding;
            } else {
                i3 = getPaddingBottom();
            }
            setPaddingRelative(paddingStart, i, i2, i3);
        }
    }

    private PaddingDrawable getPaddingDrawable() {
        if (this.mPaddingDrawable == null) {
            synchronized (this) {
                if (this.mPaddingDrawable == null) {
                    this.mPaddingDrawable = new PaddingDrawable((Drawable) null);
                }
            }
        }
        return this.mPaddingDrawable;
    }

    public void setTextAppearance(int resId) {
        ViewUtil.applyTextAppearance(this, resId);
    }

    public void setTextAppearance(Context context, int resId) {
        ViewUtil.applyTextAppearance(this, resId);
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

    public void onRtlPropertiesChanged(int layoutDirection) {
        boolean rtl = true;
        if (layoutDirection != 1) {
            rtl = false;
        }
        if (this.mIsRtl != rtl) {
            this.mIsRtl = rtl;
            if (Build.VERSION.SDK_INT >= 17) {
                setPaddingRelative(getPaddingStart(), getPaddingTop(), getPaddingEnd(), getPaddingBottom());
            } else {
                setPadding(getPaddingLeft(), getPaddingTop(), getPaddingRight(), getPaddingBottom());
            }
            setCompoundDrawablePadding(getCompoundDrawablePadding());
            invalidate();
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

    public boolean onTouchEvent(MotionEvent event) {
        return getRippleManager().onTouchEvent(this, event) || super.onTouchEvent(event);
    }

    public void setButtonDrawable(Drawable d) {
        super.setButtonDrawable((Drawable) null);
        getPaddingDrawable().setWrappedDrawable(d);
        super.setButtonDrawable(getPaddingDrawable());
    }

    public Drawable getButtonDrawable() {
        return getPaddingDrawable().getWrappedDrawable();
    }

    public void setPadding(int left, int top, int right, int bottom) {
        PaddingDrawable drawable = getPaddingDrawable();
        if (this.mIsRtl) {
            drawable.setPadding(drawable.getPaddingLeft(), top, right, bottom);
        } else {
            drawable.setPadding(left, top, drawable.getPaddingRight(), bottom);
        }
        super.setPadding(left, top, right, bottom);
    }

    public void setPaddingRelative(int start, int top, int end, int bottom) {
        PaddingDrawable drawable = getPaddingDrawable();
        if (this.mIsRtl) {
            drawable.setPadding(drawable.getPaddingLeft(), top, start, bottom);
        } else {
            drawable.setPadding(start, top, drawable.getPaddingRight(), bottom);
        }
        super.setPaddingRelative(start, top, end, bottom);
    }

    public void setCompoundDrawablePadding(int pad) {
        PaddingDrawable drawable = getPaddingDrawable();
        if (this.mIsRtl) {
            drawable.setPadding(pad, drawable.getPaddingTop(), drawable.getPaddingRight(), drawable.getPaddingBottom());
        } else {
            drawable.setPadding(drawable.getPaddingLeft(), drawable.getPaddingTop(), pad, drawable.getPaddingBottom());
        }
        super.setCompoundDrawablePadding(pad);
    }

    public int getCompoundPaddingLeft() {
        if (this.mIsRtl) {
            return getPaddingLeft();
        }
        return getPaddingDrawable().getIntrinsicWidth();
    }

    public int getCompoundPaddingRight() {
        if (!this.mIsRtl) {
            return getPaddingRight();
        }
        return getPaddingDrawable().getIntrinsicWidth();
    }
}
