package com.rey.material.drawable;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import com.rey.material.C2500R;
import com.rey.material.drawable.LineMorphingDrawable;
import com.rey.material.drawable.ToolbarRippleDrawable;

public class NavigationDrawerDrawable extends Drawable implements Drawable.Callback {
    public static final int STATE_ARROW = 1;
    public static final int STATE_DRAWER = 0;
    private LineMorphingDrawable mLineDrawable;
    private ToolbarRippleDrawable mRippleDrawable;

    public NavigationDrawerDrawable(ToolbarRippleDrawable rippleDrawable, LineMorphingDrawable lineDrawable) {
        this.mRippleDrawable = rippleDrawable;
        this.mLineDrawable = lineDrawable;
        rippleDrawable.setCallback(this);
        this.mLineDrawable.setCallback(this);
    }

    public void switchIconState(int state, boolean animation) {
        this.mLineDrawable.switchLineState(state, animation);
    }

    public int getIconState() {
        return this.mLineDrawable.getLineState();
    }

    public boolean setIconState(int state, float progress) {
        return this.mLineDrawable.setLineState(state, progress);
    }

    public float getIconAnimProgress() {
        return this.mLineDrawable.getAnimProgress();
    }

    public void cancel() {
        ToolbarRippleDrawable toolbarRippleDrawable = this.mRippleDrawable;
        if (toolbarRippleDrawable != null) {
            toolbarRippleDrawable.cancel();
        }
        LineMorphingDrawable lineMorphingDrawable = this.mLineDrawable;
        if (lineMorphingDrawable != null) {
            lineMorphingDrawable.cancel();
        }
    }

    public void draw(Canvas canvas) {
        this.mRippleDrawable.draw(canvas);
        this.mLineDrawable.draw(canvas);
    }

    public void setAlpha(int alpha) {
        this.mRippleDrawable.setAlpha(alpha);
        this.mLineDrawable.setAlpha(alpha);
    }

    public void setColorFilter(ColorFilter cf) {
        this.mRippleDrawable.setColorFilter(cf);
        this.mLineDrawable.setColorFilter(cf);
    }

    public int getOpacity() {
        return -3;
    }

    public void setBounds(int left, int top, int right, int bottom) {
        this.mRippleDrawable.setBounds(left, top, right, bottom);
        this.mLineDrawable.setBounds(left, top, right, bottom);
    }

    public void setDither(boolean dither) {
        this.mRippleDrawable.setDither(dither);
        this.mLineDrawable.setDither(dither);
    }

    public void invalidateDrawable(Drawable who) {
        invalidateSelf();
    }

    public void scheduleDrawable(Drawable who, Runnable what, long when) {
        scheduleSelf(what, when);
    }

    public void unscheduleDrawable(Drawable who, Runnable what) {
        unscheduleSelf(what);
    }

    public boolean isStateful() {
        return true;
    }

    /* access modifiers changed from: protected */
    public boolean onStateChange(int[] state) {
        return this.mRippleDrawable.onStateChange(state);
    }

    public static class Builder {
        private LineMorphingDrawable mLineDrawable;
        private ToolbarRippleDrawable mRippleDrawable;

        public Builder() {
        }

        public Builder(Context context, int defStyleRes) {
            this(context, (AttributeSet) null, 0, defStyleRes);
        }

        public Builder(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
            TypedArray a = context.obtainStyledAttributes(attrs, C2500R.styleable.NavigationDrawerDrawable, defStyleAttr, defStyleRes);
            if (a != null) {
                int rippleId = a.getResourceId(C2500R.styleable.NavigationDrawerDrawable_nd_ripple, 0);
                int lineId = a.getResourceId(C2500R.styleable.NavigationDrawerDrawable_nd_icon, 0);
                if (rippleId > 0) {
                    ripple(new ToolbarRippleDrawable.Builder(context, rippleId).build());
                }
                if (lineId > 0) {
                    line(new LineMorphingDrawable.Builder(context, lineId).build());
                }
                a.recycle();
            }
        }

        public NavigationDrawerDrawable build() {
            return new NavigationDrawerDrawable(this.mRippleDrawable, this.mLineDrawable);
        }

        public Builder ripple(ToolbarRippleDrawable drawable) {
            this.mRippleDrawable = drawable;
            return this;
        }

        public Builder line(LineMorphingDrawable drawable) {
            this.mLineDrawable = drawable;
            return this;
        }
    }
}
