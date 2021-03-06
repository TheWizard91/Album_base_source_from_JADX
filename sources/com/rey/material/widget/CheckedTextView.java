package com.rey.material.widget;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import androidx.appcompat.widget.AppCompatCheckedTextView;
import com.rey.material.app.ThemeManager;
import com.rey.material.drawable.RippleDrawable;
import com.rey.material.util.ViewUtil;

public class CheckedTextView extends AppCompatCheckedTextView implements ThemeManager.OnThemeChangedListener {
    protected int mCurrentStyle = Integer.MIN_VALUE;
    private OnCheckedChangeListener mOnCheckedChangeListener;
    private RippleManager mRippleManager;
    protected int mStyleId;

    public interface OnCheckedChangeListener {
        void onCheckedChanged(CheckedTextView checkedTextView, boolean z);
    }

    public CheckedTextView(Context context) {
        super(context);
        init(context, (AttributeSet) null, 0, 0);
    }

    public CheckedTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0, 0);
    }

    public CheckedTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr, 0);
    }

    /* access modifiers changed from: protected */
    public void init(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
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

    public void setOnCheckedChangeListener(OnCheckedChangeListener listener) {
        this.mOnCheckedChangeListener = listener;
    }

    public void setChecked(boolean checked) {
        OnCheckedChangeListener onCheckedChangeListener;
        boolean change = isChecked() != checked;
        super.setChecked(checked);
        if (change && (onCheckedChangeListener = this.mOnCheckedChangeListener) != null) {
            onCheckedChangeListener.onCheckedChanged(this, checked);
        }
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
}
