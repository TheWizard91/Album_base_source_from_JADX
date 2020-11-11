package com.rey.material.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.animation.Interpolator;
import androidx.appcompat.widget.AppCompatCheckedTextView;
import com.rey.material.drawable.CircleDrawable;
import com.rey.material.util.ViewUtil;

public class CircleCheckedTextView extends AppCompatCheckedTextView {
    private CircleDrawable mBackground;
    private OnCheckedChangeListener mCheckedChangeListener;

    public interface OnCheckedChangeListener {
        void onCheckedChanged(CircleCheckedTextView circleCheckedTextView, boolean z);
    }

    public CircleCheckedTextView(Context context) {
        super(context);
        init(context, (AttributeSet) null, 0, 0);
    }

    public CircleCheckedTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0, 0);
    }

    public CircleCheckedTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr, 0);
    }

    /* access modifiers changed from: protected */
    public void init(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        setGravity(17);
        setPadding(0, 0, 0, 0);
        CircleDrawable circleDrawable = new CircleDrawable();
        this.mBackground = circleDrawable;
        circleDrawable.setInEditMode(isInEditMode());
        this.mBackground.setAnimEnable(false);
        ViewUtil.setBackground(this, this.mBackground);
        this.mBackground.setAnimEnable(true);
    }

    public void setOnCheckedChangeListener(OnCheckedChangeListener listener) {
        this.mCheckedChangeListener = listener;
    }

    public void setTextAppearance(int resId) {
        ViewUtil.applyTextAppearance(this, resId);
    }

    public void setTextAppearance(Context context, int resId) {
        ViewUtil.applyTextAppearance(this, resId);
    }

    public void setBackgroundColor(int color) {
        this.mBackground.setColor(color);
    }

    public void setAnimDuration(int duration) {
        this.mBackground.setAnimDuration(duration);
    }

    public void setInterpolator(Interpolator in, Interpolator out) {
        this.mBackground.setInterpolator(in, out);
    }

    public void setChecked(boolean checked) {
        if (isChecked() != checked) {
            super.setChecked(checked);
            OnCheckedChangeListener onCheckedChangeListener = this.mCheckedChangeListener;
            if (onCheckedChangeListener != null) {
                onCheckedChangeListener.onCheckedChanged(this, checked);
            }
        }
    }

    public void setCheckedImmediately(boolean checked) {
        this.mBackground.setAnimEnable(false);
        setChecked(checked);
        this.mBackground.setAnimEnable(true);
    }
}
