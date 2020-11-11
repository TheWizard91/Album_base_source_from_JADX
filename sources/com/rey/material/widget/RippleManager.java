package com.rey.material.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import com.rey.material.C2500R;
import com.rey.material.drawable.RippleDrawable;
import com.rey.material.drawable.ToolbarRippleDrawable;
import com.rey.material.util.ViewUtil;

public final class RippleManager implements View.OnClickListener {
    private View.OnClickListener mClickListener;
    /* access modifiers changed from: private */
    public boolean mClickScheduled = false;

    public void onCreate(View v, Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        if (!v.isInEditMode()) {
            TypedArray a = context.obtainStyledAttributes(attrs, C2500R.styleable.RippleView, defStyleAttr, defStyleRes);
            int rippleStyle = a.getResourceId(C2500R.styleable.RippleView_rd_style, 0);
            RippleDrawable drawable = null;
            if (rippleStyle != 0) {
                drawable = new RippleDrawable.Builder(context, rippleStyle).backgroundDrawable(getBackground(v)).build();
            } else if (a.getBoolean(C2500R.styleable.RippleView_rd_enable, false)) {
                drawable = new RippleDrawable.Builder(context, attrs, defStyleAttr, defStyleRes).backgroundDrawable(getBackground(v)).build();
            }
            a.recycle();
            if (drawable != null) {
                ViewUtil.setBackground(v, drawable);
            }
        }
    }

    private Drawable getBackground(View v) {
        Drawable background = v.getBackground();
        if (background == null) {
            return null;
        }
        if (background instanceof RippleDrawable) {
            return ((RippleDrawable) background).getBackgroundDrawable();
        }
        return background;
    }

    public void setOnClickListener(View.OnClickListener l) {
        this.mClickListener = l;
    }

    public boolean onTouchEvent(View v, MotionEvent event) {
        Drawable background = v.getBackground();
        return background != null && (background instanceof RippleDrawable) && ((RippleDrawable) background).onTouch(v, event);
    }

    public void onClick(View v) {
        Drawable background = v.getBackground();
        long delay = 0;
        if (background != null) {
            if (background instanceof RippleDrawable) {
                delay = ((RippleDrawable) background).getClickDelayTime();
            } else if (background instanceof ToolbarRippleDrawable) {
                delay = ((ToolbarRippleDrawable) background).getClickDelayTime();
            }
        }
        if (delay <= 0 || v.getHandler() == null) {
            dispatchClickEvent(v);
        } else if (!this.mClickScheduled) {
            this.mClickScheduled = true;
            v.getHandler().postDelayed(new ClickRunnable(v), delay);
        }
    }

    /* access modifiers changed from: private */
    public void dispatchClickEvent(View v) {
        View.OnClickListener onClickListener = this.mClickListener;
        if (onClickListener != null) {
            onClickListener.onClick(v);
        }
    }

    public static void cancelRipple(View v) {
        Drawable background = v.getBackground();
        if (background instanceof RippleDrawable) {
            ((RippleDrawable) background).cancel();
        } else if (background instanceof ToolbarRippleDrawable) {
            ((ToolbarRippleDrawable) background).cancel();
        }
        if (v instanceof ViewGroup) {
            ViewGroup vg = (ViewGroup) v;
            int count = vg.getChildCount();
            for (int i = 0; i < count; i++) {
                cancelRipple(vg.getChildAt(i));
            }
        }
    }

    class ClickRunnable implements Runnable {
        View mView;

        public ClickRunnable(View v) {
            this.mView = v;
        }

        public void run() {
            boolean unused = RippleManager.this.mClickScheduled = false;
            RippleManager.this.dispatchClickEvent(this.mView);
        }
    }
}
