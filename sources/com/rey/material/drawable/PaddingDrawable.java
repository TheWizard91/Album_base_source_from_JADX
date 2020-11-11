package com.rey.material.drawable;

import android.content.res.ColorStateList;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.Region;
import android.graphics.drawable.Drawable;
import androidx.core.graphics.drawable.DrawableCompat;

public class PaddingDrawable extends Drawable implements Drawable.Callback {
    private Drawable mDrawable;
    private int mPaddingBottom;
    private int mPaddingLeft;
    private int mPaddingRight;
    private int mPaddingTop;

    public PaddingDrawable(Drawable drawable) {
        setWrappedDrawable(drawable);
    }

    public void setPadding(int paddingLeft, int paddingTop, int paddingRight, int paddingBottom) {
        this.mPaddingLeft = paddingLeft;
        this.mPaddingTop = paddingTop;
        this.mPaddingRight = paddingRight;
        this.mPaddingBottom = paddingBottom;
    }

    public int getPaddingLeft() {
        return this.mPaddingLeft;
    }

    public int getPaddingTop() {
        return this.mPaddingTop;
    }

    public int getPaddingRight() {
        return this.mPaddingRight;
    }

    public int getPaddingBottom() {
        return this.mPaddingBottom;
    }

    public void draw(Canvas canvas) {
        Drawable drawable = this.mDrawable;
        if (drawable != null) {
            drawable.draw(canvas);
        }
    }

    /* access modifiers changed from: protected */
    public void onBoundsChange(Rect bounds) {
        Drawable drawable = this.mDrawable;
        if (drawable != null) {
            drawable.setBounds(bounds.left + this.mPaddingLeft, bounds.top + this.mPaddingTop, bounds.right - this.mPaddingRight, bounds.bottom - this.mPaddingBottom);
        }
    }

    public void setChangingConfigurations(int configs) {
        Drawable drawable = this.mDrawable;
        if (drawable != null) {
            drawable.setChangingConfigurations(configs);
        }
    }

    public int getChangingConfigurations() {
        Drawable drawable = this.mDrawable;
        if (drawable != null) {
            return drawable.getChangingConfigurations();
        }
        return 0;
    }

    public void setDither(boolean dither) {
        Drawable drawable = this.mDrawable;
        if (drawable != null) {
            drawable.setDither(dither);
        }
    }

    public void setFilterBitmap(boolean filter) {
        Drawable drawable = this.mDrawable;
        if (drawable != null) {
            drawable.setFilterBitmap(filter);
        }
    }

    public void setAlpha(int alpha) {
        Drawable drawable = this.mDrawable;
        if (drawable != null) {
            drawable.setAlpha(alpha);
        }
    }

    public void setColorFilter(ColorFilter cf) {
        Drawable drawable = this.mDrawable;
        if (drawable != null) {
            drawable.setColorFilter(cf);
        }
    }

    public boolean isStateful() {
        Drawable drawable = this.mDrawable;
        return drawable != null && drawable.isStateful();
    }

    public boolean setState(int[] stateSet) {
        Drawable drawable = this.mDrawable;
        return drawable != null && drawable.setState(stateSet);
    }

    public int[] getState() {
        Drawable drawable = this.mDrawable;
        if (drawable != null) {
            return drawable.getState();
        }
        return null;
    }

    public void jumpToCurrentState() {
        Drawable drawable = this.mDrawable;
        if (drawable != null) {
            DrawableCompat.jumpToCurrentState(drawable);
        }
    }

    public Drawable getCurrent() {
        Drawable drawable = this.mDrawable;
        if (drawable != null) {
            return drawable.getCurrent();
        }
        return null;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:2:0x0006, code lost:
        r0 = r1.mDrawable;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean setVisible(boolean r2, boolean r3) {
        /*
            r1 = this;
            boolean r0 = super.setVisible(r2, r3)
            if (r0 != 0) goto L_0x0013
            android.graphics.drawable.Drawable r0 = r1.mDrawable
            if (r0 == 0) goto L_0x0011
            boolean r0 = r0.setVisible(r2, r3)
            if (r0 == 0) goto L_0x0011
            goto L_0x0013
        L_0x0011:
            r0 = 0
            goto L_0x0014
        L_0x0013:
            r0 = 1
        L_0x0014:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.rey.material.drawable.PaddingDrawable.setVisible(boolean, boolean):boolean");
    }

    public int getOpacity() {
        Drawable drawable = this.mDrawable;
        if (drawable != null) {
            return drawable.getOpacity();
        }
        return 0;
    }

    public Region getTransparentRegion() {
        Drawable drawable = this.mDrawable;
        if (drawable != null) {
            return drawable.getTransparentRegion();
        }
        return null;
    }

    public int getIntrinsicWidth() {
        Drawable drawable = this.mDrawable;
        return (drawable != null ? drawable.getIntrinsicWidth() : 0) + this.mPaddingLeft + this.mPaddingRight;
    }

    public int getIntrinsicHeight() {
        Drawable drawable = this.mDrawable;
        return (drawable != null ? drawable.getIntrinsicHeight() : 0) + this.mPaddingTop + this.mPaddingBottom;
    }

    public int getMinimumWidth() {
        Drawable drawable = this.mDrawable;
        return (drawable != null ? drawable.getMinimumWidth() : 0) + this.mPaddingLeft + this.mPaddingRight;
    }

    public int getMinimumHeight() {
        Drawable drawable = this.mDrawable;
        return (drawable != null ? drawable.getMinimumHeight() : 0) + this.mPaddingTop + this.mPaddingBottom;
    }

    public boolean getPadding(Rect padding) {
        Drawable drawable = this.mDrawable;
        boolean hasPadding = true;
        boolean hasPadding2 = drawable != null && drawable.getPadding(padding);
        if (hasPadding2) {
            padding.left += this.mPaddingLeft;
            padding.top += this.mPaddingTop;
            padding.right += this.mPaddingRight;
            padding.bottom += this.mPaddingBottom;
            return hasPadding2;
        }
        padding.set(this.mPaddingLeft, this.mPaddingTop, this.mPaddingRight, this.mPaddingBottom);
        if (this.mPaddingLeft == 0 && this.mPaddingTop == 0 && this.mPaddingRight == 0 && this.mPaddingBottom == 0) {
            hasPadding = false;
        }
        return hasPadding;
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

    /* access modifiers changed from: protected */
    public boolean onLevelChange(int level) {
        Drawable drawable = this.mDrawable;
        return drawable != null && drawable.setLevel(level);
    }

    public void setAutoMirrored(boolean mirrored) {
        Drawable drawable = this.mDrawable;
        if (drawable != null) {
            DrawableCompat.setAutoMirrored(drawable, mirrored);
        }
    }

    public boolean isAutoMirrored() {
        Drawable drawable = this.mDrawable;
        return drawable != null && DrawableCompat.isAutoMirrored(drawable);
    }

    public void setTint(int tint) {
        Drawable drawable = this.mDrawable;
        if (drawable != null) {
            DrawableCompat.setTint(drawable, tint);
        }
    }

    public void setTintList(ColorStateList tint) {
        Drawable drawable = this.mDrawable;
        if (drawable != null) {
            DrawableCompat.setTintList(drawable, tint);
        }
    }

    public void setTintMode(PorterDuff.Mode tintMode) {
        Drawable drawable = this.mDrawable;
        if (drawable != null) {
            DrawableCompat.setTintMode(drawable, tintMode);
        }
    }

    public void setHotspot(float x, float y) {
        Drawable drawable = this.mDrawable;
        if (drawable != null) {
            DrawableCompat.setHotspot(drawable, x, y);
        }
    }

    public void setHotspotBounds(int left, int top, int right, int bottom) {
        Drawable drawable = this.mDrawable;
        if (drawable != null) {
            DrawableCompat.setHotspotBounds(drawable, left, top, right, bottom);
        }
    }

    public Drawable getWrappedDrawable() {
        return this.mDrawable;
    }

    public void setWrappedDrawable(Drawable drawable) {
        Drawable drawable2 = this.mDrawable;
        if (drawable2 != null) {
            drawable2.setCallback((Drawable.Callback) null);
        }
        this.mDrawable = drawable;
        if (drawable != null) {
            drawable.setCallback(this);
        }
        onBoundsChange(getBounds());
        invalidateSelf();
    }
}
