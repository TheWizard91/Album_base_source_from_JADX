package com.facebook.drawee.drawable;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import javax.annotation.Nullable;

public class ForwardingDrawable extends Drawable implements Drawable.Callback, TransformCallback, TransformAwareDrawable, DrawableParent {
    private static final Matrix sTempTransform = new Matrix();
    @Nullable
    private Drawable mCurrentDelegate;
    private final DrawableProperties mDrawableProperties = new DrawableProperties();
    protected TransformCallback mTransformCallback;

    public ForwardingDrawable(@Nullable Drawable drawable) {
        this.mCurrentDelegate = drawable;
        DrawableUtils.setCallbacks(drawable, this, this);
    }

    @Nullable
    public Drawable setCurrent(@Nullable Drawable newDelegate) {
        Drawable previousDelegate = setCurrentWithoutInvalidate(newDelegate);
        invalidateSelf();
        return previousDelegate;
    }

    /* access modifiers changed from: protected */
    @Nullable
    public Drawable setCurrentWithoutInvalidate(@Nullable Drawable newDelegate) {
        Drawable previousDelegate = this.mCurrentDelegate;
        DrawableUtils.setCallbacks(previousDelegate, (Drawable.Callback) null, (TransformCallback) null);
        DrawableUtils.setCallbacks(newDelegate, (Drawable.Callback) null, (TransformCallback) null);
        DrawableUtils.setDrawableProperties(newDelegate, this.mDrawableProperties);
        DrawableUtils.copyProperties(newDelegate, this);
        DrawableUtils.setCallbacks(newDelegate, this, this);
        this.mCurrentDelegate = newDelegate;
        return previousDelegate;
    }

    public int getOpacity() {
        Drawable drawable = this.mCurrentDelegate;
        if (drawable == null) {
            return 0;
        }
        return drawable.getOpacity();
    }

    public void setAlpha(int alpha) {
        this.mDrawableProperties.setAlpha(alpha);
        Drawable drawable = this.mCurrentDelegate;
        if (drawable != null) {
            drawable.setAlpha(alpha);
        }
    }

    public void setColorFilter(ColorFilter colorFilter) {
        this.mDrawableProperties.setColorFilter(colorFilter);
        Drawable drawable = this.mCurrentDelegate;
        if (drawable != null) {
            drawable.setColorFilter(colorFilter);
        }
    }

    public void setDither(boolean dither) {
        this.mDrawableProperties.setDither(dither);
        Drawable drawable = this.mCurrentDelegate;
        if (drawable != null) {
            drawable.setDither(dither);
        }
    }

    public void setFilterBitmap(boolean filterBitmap) {
        this.mDrawableProperties.setFilterBitmap(filterBitmap);
        Drawable drawable = this.mCurrentDelegate;
        if (drawable != null) {
            drawable.setFilterBitmap(filterBitmap);
        }
    }

    public boolean setVisible(boolean visible, boolean restart) {
        boolean superResult = super.setVisible(visible, restart);
        Drawable drawable = this.mCurrentDelegate;
        if (drawable == null) {
            return superResult;
        }
        return drawable.setVisible(visible, restart);
    }

    /* access modifiers changed from: protected */
    public void onBoundsChange(Rect bounds) {
        Drawable drawable = this.mCurrentDelegate;
        if (drawable != null) {
            drawable.setBounds(bounds);
        }
    }

    @Nullable
    public Drawable.ConstantState getConstantState() {
        Drawable drawable = this.mCurrentDelegate;
        if (drawable == null) {
            return super.getConstantState();
        }
        return drawable.getConstantState();
    }

    public boolean isStateful() {
        Drawable drawable = this.mCurrentDelegate;
        if (drawable == null) {
            return false;
        }
        return drawable.isStateful();
    }

    /* access modifiers changed from: protected */
    public boolean onStateChange(int[] state) {
        Drawable drawable = this.mCurrentDelegate;
        if (drawable == null) {
            return super.onStateChange(state);
        }
        return drawable.setState(state);
    }

    /* access modifiers changed from: protected */
    public boolean onLevelChange(int level) {
        Drawable drawable = this.mCurrentDelegate;
        if (drawable == null) {
            return super.onLevelChange(level);
        }
        return drawable.setLevel(level);
    }

    public void draw(Canvas canvas) {
        Drawable drawable = this.mCurrentDelegate;
        if (drawable != null) {
            drawable.draw(canvas);
        }
    }

    public int getIntrinsicWidth() {
        Drawable drawable = this.mCurrentDelegate;
        if (drawable == null) {
            return super.getIntrinsicWidth();
        }
        return drawable.getIntrinsicWidth();
    }

    public int getIntrinsicHeight() {
        Drawable drawable = this.mCurrentDelegate;
        if (drawable == null) {
            return super.getIntrinsicHeight();
        }
        return drawable.getIntrinsicHeight();
    }

    public boolean getPadding(Rect padding) {
        Drawable drawable = this.mCurrentDelegate;
        if (drawable == null) {
            return super.getPadding(padding);
        }
        return drawable.getPadding(padding);
    }

    public Drawable mutate() {
        Drawable drawable = this.mCurrentDelegate;
        if (drawable != null) {
            drawable.mutate();
        }
        return this;
    }

    @Nullable
    public Drawable getCurrent() {
        return this.mCurrentDelegate;
    }

    public Drawable setDrawable(@Nullable Drawable newDrawable) {
        return setCurrent(newDrawable);
    }

    @Nullable
    public Drawable getDrawable() {
        return getCurrent();
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

    public void setTransformCallback(TransformCallback transformCallback) {
        this.mTransformCallback = transformCallback;
    }

    /* access modifiers changed from: protected */
    public void getParentTransform(Matrix transform) {
        TransformCallback transformCallback = this.mTransformCallback;
        if (transformCallback != null) {
            transformCallback.getTransform(transform);
        } else {
            transform.reset();
        }
    }

    public void getTransform(Matrix transform) {
        getParentTransform(transform);
    }

    public void getRootBounds(RectF bounds) {
        TransformCallback transformCallback = this.mTransformCallback;
        if (transformCallback != null) {
            transformCallback.getRootBounds(bounds);
        } else {
            bounds.set(getBounds());
        }
    }

    public void getTransformedBounds(RectF outBounds) {
        Matrix matrix = sTempTransform;
        getParentTransform(matrix);
        outBounds.set(getBounds());
        matrix.mapRect(outBounds);
    }

    public void setHotspot(float x, float y) {
        Drawable drawable = this.mCurrentDelegate;
        if (drawable != null) {
            drawable.setHotspot(x, y);
        }
    }
}
