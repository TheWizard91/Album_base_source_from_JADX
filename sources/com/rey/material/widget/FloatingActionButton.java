package com.rey.material.widget;

import android.app.Activity;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.FrameLayout;
import com.rey.material.C2500R;
import com.rey.material.app.ThemeManager;
import com.rey.material.drawable.LineMorphingDrawable;
import com.rey.material.drawable.OvalShadowDrawable;
import com.rey.material.drawable.RippleDrawable;
import com.rey.material.util.ThemeUtil;
import com.rey.material.util.ViewUtil;

public class FloatingActionButton extends View implements ThemeManager.OnThemeChangedListener {
    /* access modifiers changed from: private */
    public int mAnimDuration = -1;
    /* access modifiers changed from: private */
    public OvalShadowDrawable mBackground;
    protected int mCurrentStyle = Integer.MIN_VALUE;
    /* access modifiers changed from: private */
    public Drawable mIcon;
    /* access modifiers changed from: private */
    public int mIconSize = -1;
    /* access modifiers changed from: private */
    public Interpolator mInterpolator;
    /* access modifiers changed from: private */
    public Drawable mPrevIcon;
    private RippleManager mRippleManager;
    protected int mStyleId;
    private SwitchIconAnimator mSwitchIconAnimator;

    public static FloatingActionButton make(Context context, int resId) {
        return new FloatingActionButton(context, (AttributeSet) null, resId);
    }

    public FloatingActionButton(Context context) {
        super(context);
        init(context, (AttributeSet) null, 0, 0);
    }

    public FloatingActionButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0, 0);
    }

    public FloatingActionButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr, 0);
    }

    /* access modifiers changed from: protected */
    public void init(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        setClickable(true);
        this.mSwitchIconAnimator = new SwitchIconAnimator();
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
        int resId;
        Context context2 = context;
        TypedArray a = context2.obtainStyledAttributes(attrs, C2500R.styleable.FloatingActionButton, defStyleAttr, defStyleRes);
        int radius = -1;
        int elevation = -1;
        ColorStateList bgColor = null;
        int bgAnimDuration = -1;
        int count = a.getIndexCount();
        int iconLineMorphing = 0;
        int iconSrc = 0;
        int i = 0;
        while (i < count) {
            int attr = a.getIndex(i);
            if (attr == C2500R.styleable.FloatingActionButton_fab_radius) {
                radius = a.getDimensionPixelSize(attr, 0);
            } else if (attr == C2500R.styleable.FloatingActionButton_fab_elevation) {
                elevation = a.getDimensionPixelSize(attr, 0);
            } else if (attr == C2500R.styleable.FloatingActionButton_fab_backgroundColor) {
                bgColor = a.getColorStateList(attr);
            } else if (attr == C2500R.styleable.FloatingActionButton_fab_backgroundAnimDuration) {
                bgAnimDuration = a.getInteger(attr, 0);
            } else if (attr == C2500R.styleable.FloatingActionButton_fab_iconSrc) {
                iconSrc = a.getResourceId(attr, 0);
            } else if (attr == C2500R.styleable.FloatingActionButton_fab_iconLineMorphing) {
                iconLineMorphing = a.getResourceId(attr, 0);
            } else if (attr == C2500R.styleable.FloatingActionButton_fab_iconSize) {
                this.mIconSize = a.getDimensionPixelSize(attr, 0);
            } else if (attr == C2500R.styleable.FloatingActionButton_fab_animDuration) {
                this.mAnimDuration = a.getInteger(attr, 0);
            } else if (attr == C2500R.styleable.FloatingActionButton_fab_interpolator && (resId = a.getResourceId(C2500R.styleable.FloatingActionButton_fab_interpolator, 0)) != 0) {
                this.mInterpolator = AnimationUtils.loadInterpolator(context2, resId);
            }
            i++;
            AttributeSet attributeSet = attrs;
        }
        a.recycle();
        if (this.mIconSize < 0) {
            this.mIconSize = ThemeUtil.dpToPx(context2, 24);
        }
        if (this.mAnimDuration < 0) {
            this.mAnimDuration = context.getResources().getInteger(17694721);
        }
        if (this.mInterpolator == null) {
            this.mInterpolator = new DecelerateInterpolator();
        }
        OvalShadowDrawable ovalShadowDrawable = this.mBackground;
        if (ovalShadowDrawable == null) {
            if (radius < 0) {
                radius = ThemeUtil.dpToPx(context2, 28);
            }
            if (elevation < 0) {
                elevation = ThemeUtil.dpToPx(context2, 4);
            }
            if (bgColor == null) {
                bgColor = ColorStateList.valueOf(ThemeUtil.colorAccent(context2, 0));
            }
            if (bgAnimDuration < 0) {
                bgAnimDuration = 0;
            }
            OvalShadowDrawable ovalShadowDrawable2 = new OvalShadowDrawable(radius, bgColor, (float) elevation, (float) elevation, bgAnimDuration);
            this.mBackground = ovalShadowDrawable2;
            ovalShadowDrawable2.setInEditMode(isInEditMode());
            this.mBackground.setBounds(0, 0, getWidth(), getHeight());
            this.mBackground.setCallback(this);
            int i2 = radius;
            int i3 = elevation;
            ColorStateList colorStateList = bgColor;
            int i4 = bgAnimDuration;
        } else {
            if (radius >= 0) {
                ovalShadowDrawable.setRadius(radius);
            }
            if (bgColor != null) {
                this.mBackground.setColor(bgColor);
            }
            if (elevation >= 0) {
                this.mBackground.setShadow((float) elevation, (float) elevation);
            }
            if (bgAnimDuration >= 0) {
                this.mBackground.setAnimationDuration(bgAnimDuration);
            }
            int i5 = radius;
            int i6 = elevation;
            ColorStateList colorStateList2 = bgColor;
            int i7 = bgAnimDuration;
        }
        if (iconLineMorphing != 0) {
            setIcon(new LineMorphingDrawable.Builder(context2, iconLineMorphing).build(), false);
        } else if (iconSrc != 0) {
            setIcon(context.getResources().getDrawable(iconSrc), false);
        }
        getRippleManager().onCreate(this, context, attrs, defStyleAttr, defStyleRes);
        Drawable background = getBackground();
        if (background != null && (background instanceof RippleDrawable)) {
            RippleDrawable drawable = (RippleDrawable) background;
            drawable.setBackgroundDrawable((Drawable) null);
            drawable.setMask(1, 0, 0, 0, 0, (int) this.mBackground.getPaddingLeft(), (int) this.mBackground.getPaddingTop(), (int) this.mBackground.getPaddingRight(), (int) this.mBackground.getPaddingBottom());
        }
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

    public int getRadius() {
        return this.mBackground.getRadius();
    }

    public void setRadius(int radius) {
        if (this.mBackground.setRadius(radius)) {
            requestLayout();
        }
    }

    public float getElevation() {
        if (Build.VERSION.SDK_INT >= 21) {
            return super.getElevation();
        }
        return this.mBackground.getShadowSize();
    }

    public void setElevation(float elevation) {
        if (Build.VERSION.SDK_INT >= 21) {
            super.setElevation(elevation);
        } else if (this.mBackground.setShadow(elevation, elevation)) {
            requestLayout();
        }
    }

    public int getLineMorphingState() {
        Drawable drawable = this.mIcon;
        if (drawable == null || !(drawable instanceof LineMorphingDrawable)) {
            return -1;
        }
        return ((LineMorphingDrawable) drawable).getLineState();
    }

    public void setLineMorphingState(int state, boolean animation) {
        Drawable drawable = this.mIcon;
        if (drawable != null && (drawable instanceof LineMorphingDrawable)) {
            ((LineMorphingDrawable) drawable).switchLineState(state, animation);
        }
    }

    public ColorStateList getBackgroundColor() {
        return this.mBackground.getColor();
    }

    public Drawable getIcon() {
        return this.mIcon;
    }

    public void setIcon(Drawable icon, boolean animation) {
        if (icon != null) {
            if (animation) {
                this.mSwitchIconAnimator.startAnimation(icon);
                invalidate();
                return;
            }
            Drawable drawable = this.mIcon;
            if (drawable != null) {
                drawable.setCallback((Drawable.Callback) null);
                unscheduleDrawable(this.mIcon);
            }
            this.mIcon = icon;
            float half = ((float) this.mIconSize) / 2.0f;
            icon.setBounds((int) (this.mBackground.getCenterX() - half), (int) (this.mBackground.getCenterY() - half), (int) (this.mBackground.getCenterX() + half), (int) (this.mBackground.getCenterY() + half));
            this.mIcon.setCallback(this);
            invalidate();
        }
    }

    public void setBackgroundColor(ColorStateList color) {
        this.mBackground.setColor(color);
        invalidate();
    }

    public void setBackgroundColor(int color) {
        this.mBackground.setColor(color);
        invalidate();
    }

    public void show(Activity activity, int x, int y, int gravity) {
        if (getParent() == null) {
            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(this.mBackground.getIntrinsicWidth(), this.mBackground.getIntrinsicHeight());
            updateParams(x, y, gravity, params);
            activity.getWindow().addContentView(this, params);
            return;
        }
        updateLocation(x, y, gravity);
    }

    public void show(ViewGroup parent, int x, int y, int gravity) {
        if (getParent() == null) {
            ViewGroup.LayoutParams params = parent.generateLayoutParams((AttributeSet) null);
            params.width = this.mBackground.getIntrinsicWidth();
            params.height = this.mBackground.getIntrinsicHeight();
            updateParams(x, y, gravity, params);
            parent.addView(this, params);
            return;
        }
        updateLocation(x, y, gravity);
    }

    public void updateLocation(int x, int y, int gravity) {
        if (getParent() != null) {
            updateParams(x, y, gravity, getLayoutParams());
        } else {
            Log.v(FloatingActionButton.class.getSimpleName(), "updateLocation() is called without parent");
        }
    }

    private void updateParams(int x, int y, int gravity, ViewGroup.LayoutParams params) {
        int horizontalGravity = gravity & 7;
        if (horizontalGravity == 1) {
            setLeftMargin(params, (int) (((float) x) - this.mBackground.getCenterX()));
        } else if (horizontalGravity == 3) {
            setLeftMargin(params, (int) (((float) x) - this.mBackground.getPaddingLeft()));
        } else if (horizontalGravity != 5) {
            setLeftMargin(params, (int) (((float) x) - this.mBackground.getPaddingLeft()));
        } else {
            setLeftMargin(params, (int) ((((float) x) - this.mBackground.getPaddingLeft()) - ((float) (this.mBackground.getRadius() * 2))));
        }
        int verticalGravity = gravity & 112;
        if (verticalGravity == 16) {
            setTopMargin(params, (int) (((float) y) - this.mBackground.getCenterY()));
        } else if (verticalGravity == 48) {
            setTopMargin(params, (int) (((float) y) - this.mBackground.getPaddingTop()));
        } else if (verticalGravity != 80) {
            setTopMargin(params, (int) (((float) y) - this.mBackground.getPaddingTop()));
        } else {
            setTopMargin(params, (int) ((((float) y) - this.mBackground.getPaddingTop()) - ((float) (this.mBackground.getRadius() * 2))));
        }
        setLayoutParams(params);
    }

    private void setLeftMargin(ViewGroup.LayoutParams params, int value) {
        if (params instanceof ViewGroup.MarginLayoutParams) {
            ((ViewGroup.MarginLayoutParams) params).leftMargin = value;
        } else {
            Log.v(FloatingActionButton.class.getSimpleName(), "cannot recognize LayoutParams: " + params);
        }
    }

    private void setTopMargin(ViewGroup.LayoutParams params, int value) {
        if (params instanceof ViewGroup.MarginLayoutParams) {
            ((ViewGroup.MarginLayoutParams) params).topMargin = value;
        } else {
            Log.v(FloatingActionButton.class.getSimpleName(), "cannot recognize LayoutParams: " + params);
        }
    }

    public void dismiss() {
        if (getParent() != null) {
            ((ViewGroup) getParent()).removeView(this);
        }
    }

    /* access modifiers changed from: protected */
    public boolean verifyDrawable(Drawable who) {
        return super.verifyDrawable(who) || this.mBackground == who || this.mIcon == who || this.mPrevIcon == who;
    }

    /* access modifiers changed from: protected */
    public void drawableStateChanged() {
        super.drawableStateChanged();
        OvalShadowDrawable ovalShadowDrawable = this.mBackground;
        if (ovalShadowDrawable != null) {
            ovalShadowDrawable.setState(getDrawableState());
        }
        Drawable drawable = this.mIcon;
        if (drawable != null) {
            drawable.setState(getDrawableState());
        }
        Drawable drawable2 = this.mPrevIcon;
        if (drawable2 != null) {
            drawable2.setState(getDrawableState());
        }
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(this.mBackground.getIntrinsicWidth(), this.mBackground.getIntrinsicHeight());
    }

    /* access modifiers changed from: protected */
    public void onSizeChanged(int w, int h, int oldw, int oldh) {
        this.mBackground.setBounds(0, 0, w, h);
        Drawable drawable = this.mIcon;
        if (drawable != null) {
            float half = ((float) this.mIconSize) / 2.0f;
            drawable.setBounds((int) (this.mBackground.getCenterX() - half), (int) (this.mBackground.getCenterY() - half), (int) (this.mBackground.getCenterX() + half), (int) (this.mBackground.getCenterY() + half));
        }
        Drawable drawable2 = this.mPrevIcon;
        if (drawable2 != null) {
            float half2 = ((float) this.mIconSize) / 2.0f;
            drawable2.setBounds((int) (this.mBackground.getCenterX() - half2), (int) (this.mBackground.getCenterY() - half2), (int) (this.mBackground.getCenterX() + half2), (int) (this.mBackground.getCenterY() + half2));
        }
    }

    public void draw(Canvas canvas) {
        this.mBackground.draw(canvas);
        super.draw(canvas);
        Drawable drawable = this.mPrevIcon;
        if (drawable != null) {
            drawable.draw(canvas);
        }
        Drawable drawable2 = this.mIcon;
        if (drawable2 != null) {
            drawable2.draw(canvas);
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
        if (event.getActionMasked() == 0 && !this.mBackground.isPointerOver(event.getX(), event.getY())) {
            return false;
        }
        boolean result = super.onTouchEvent(event);
        if (getRippleManager().onTouchEvent(this, event) || result) {
            return true;
        }
        return false;
    }

    /* access modifiers changed from: protected */
    public Parcelable onSaveInstanceState() {
        SavedState ss = new SavedState(super.onSaveInstanceState());
        ss.state = getLineMorphingState();
        return ss;
    }

    /* access modifiers changed from: protected */
    public void onRestoreInstanceState(Parcelable state) {
        SavedState ss = (SavedState) state;
        super.onRestoreInstanceState(ss.getSuperState());
        if (ss.state >= 0) {
            setLineMorphingState(ss.state, false);
        }
        requestLayout();
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
        int state;

        SavedState(Parcelable superState) {
            super(superState);
        }

        private SavedState(Parcel in) {
            super(in);
            this.state = in.readInt();
        }

        public void writeToParcel(Parcel out, int flags) {
            super.writeToParcel(out, flags);
            out.writeInt(this.state);
        }

        public String toString() {
            return "FloatingActionButton.SavedState{" + Integer.toHexString(System.identityHashCode(this)) + " state=" + this.state + "}";
        }
    }

    class SwitchIconAnimator implements Runnable {
        boolean mRunning = false;
        long mStartTime;

        SwitchIconAnimator() {
        }

        public void resetAnimation() {
            this.mStartTime = SystemClock.uptimeMillis();
            FloatingActionButton.this.mIcon.setAlpha(0);
            FloatingActionButton.this.mPrevIcon.setAlpha(255);
        }

        public boolean startAnimation(Drawable icon) {
            if (FloatingActionButton.this.mIcon == icon) {
                return false;
            }
            FloatingActionButton floatingActionButton = FloatingActionButton.this;
            Drawable unused = floatingActionButton.mPrevIcon = floatingActionButton.mIcon;
            Drawable unused2 = FloatingActionButton.this.mIcon = icon;
            float half = ((float) FloatingActionButton.this.mIconSize) / 2.0f;
            FloatingActionButton.this.mIcon.setBounds((int) (FloatingActionButton.this.mBackground.getCenterX() - half), (int) (FloatingActionButton.this.mBackground.getCenterY() - half), (int) (FloatingActionButton.this.mBackground.getCenterX() + half), (int) (FloatingActionButton.this.mBackground.getCenterY() + half));
            FloatingActionButton.this.mIcon.setCallback(FloatingActionButton.this);
            if (FloatingActionButton.this.getHandler() != null) {
                resetAnimation();
                this.mRunning = true;
                FloatingActionButton.this.getHandler().postAtTime(this, SystemClock.uptimeMillis() + 16);
            } else {
                FloatingActionButton.this.mPrevIcon.setCallback((Drawable.Callback) null);
                FloatingActionButton floatingActionButton2 = FloatingActionButton.this;
                floatingActionButton2.unscheduleDrawable(floatingActionButton2.mPrevIcon);
                Drawable unused3 = FloatingActionButton.this.mPrevIcon = null;
            }
            FloatingActionButton.this.invalidate();
            return true;
        }

        public void stopAnimation() {
            this.mRunning = false;
            FloatingActionButton.this.mPrevIcon.setCallback((Drawable.Callback) null);
            FloatingActionButton floatingActionButton = FloatingActionButton.this;
            floatingActionButton.unscheduleDrawable(floatingActionButton.mPrevIcon);
            Drawable unused = FloatingActionButton.this.mPrevIcon = null;
            FloatingActionButton.this.mIcon.setAlpha(255);
            if (FloatingActionButton.this.getHandler() != null) {
                FloatingActionButton.this.getHandler().removeCallbacks(this);
            }
            FloatingActionButton.this.invalidate();
        }

        public void run() {
            float progress = Math.min(1.0f, ((float) (SystemClock.uptimeMillis() - this.mStartTime)) / ((float) FloatingActionButton.this.mAnimDuration));
            float value = FloatingActionButton.this.mInterpolator.getInterpolation(progress);
            FloatingActionButton.this.mIcon.setAlpha(Math.round(value * 255.0f));
            FloatingActionButton.this.mPrevIcon.setAlpha(Math.round((1.0f - value) * 255.0f));
            if (progress == 1.0f) {
                stopAnimation();
            }
            if (this.mRunning) {
                if (FloatingActionButton.this.getHandler() != null) {
                    FloatingActionButton.this.getHandler().postAtTime(this, SystemClock.uptimeMillis() + 16);
                } else {
                    stopAnimation();
                }
            }
            FloatingActionButton.this.invalidate();
        }
    }
}
