package com.rey.material.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import com.rey.material.C2500R;
import com.rey.material.app.ThemeManager;
import com.rey.material.drawable.CircularProgressDrawable;
import com.rey.material.drawable.LinearProgressDrawable;
import com.rey.material.util.ViewUtil;

public class ProgressView extends View implements ThemeManager.OnThemeChangedListener {
    public static final int MODE_BUFFER = 2;
    public static final int MODE_DETERMINATE = 0;
    public static final int MODE_INDETERMINATE = 1;
    public static final int MODE_QUERY = 3;
    private boolean mAutostart = false;
    private boolean mCircular = true;
    protected int mCurrentStyle = Integer.MIN_VALUE;
    private Drawable mProgressDrawable;
    private int mProgressId;
    protected int mStyleId;

    public ProgressView(Context context) {
        super(context);
        init(context, (AttributeSet) null, 0, 0);
    }

    public ProgressView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0, 0);
    }

    public ProgressView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr, 0);
    }

    /* access modifiers changed from: protected */
    public void init(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        applyStyle(context, attrs, defStyleAttr, defStyleRes);
        if (!isInEditMode()) {
            this.mStyleId = ThemeManager.getStyleId(context, attrs, defStyleAttr, defStyleRes);
        }
    }

    public void applyStyle(int resId) {
        ViewUtil.applyStyle(this, resId);
        applyStyle(getContext(), (AttributeSet) null, 0, resId);
    }

    private boolean needCreateProgress(boolean circular) {
        Drawable drawable = this.mProgressDrawable;
        if (drawable == null) {
            return true;
        }
        if (circular) {
            return !(drawable instanceof CircularProgressDrawable);
        }
        return !(drawable instanceof LinearProgressDrawable);
    }

    /* access modifiers changed from: protected */
    public void applyStyle(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        boolean z;
        Context context2 = context;
        TypedArray a = context2.obtainStyledAttributes(attrs, C2500R.styleable.ProgressView, defStyleAttr, defStyleRes);
        int progressId = 0;
        int progressMode = -1;
        float progress = -1.0f;
        float secondaryProgress = -1.0f;
        int i = 0;
        int count = a.getIndexCount();
        while (true) {
            z = true;
            if (i >= count) {
                break;
            }
            int attr = a.getIndex(i);
            if (attr == C2500R.styleable.ProgressView_pv_autostart) {
                this.mAutostart = a.getBoolean(attr, false);
            } else if (attr == C2500R.styleable.ProgressView_pv_circular) {
                this.mCircular = a.getBoolean(attr, true);
            } else if (attr == C2500R.styleable.ProgressView_pv_progressStyle) {
                progressId = a.getResourceId(attr, 0);
            } else if (attr == C2500R.styleable.ProgressView_pv_progressMode) {
                progressMode = a.getInteger(attr, 0);
            } else if (attr == C2500R.styleable.ProgressView_pv_progress) {
                progress = a.getFloat(attr, 0.0f);
            } else if (attr == C2500R.styleable.ProgressView_pv_secondaryProgress) {
                secondaryProgress = a.getFloat(attr, 0.0f);
            }
            i++;
        }
        a.recycle();
        boolean needStart = false;
        if (needCreateProgress(this.mCircular)) {
            this.mProgressId = progressId;
            if (progressId == 0) {
                this.mProgressId = this.mCircular ? C2500R.C2505style.Material_Drawable_CircularProgress : C2500R.C2505style.Material_Drawable_LinearProgress;
            }
            Drawable drawable = this.mProgressDrawable;
            if (drawable == null || !((Animatable) drawable).isRunning()) {
                z = false;
            }
            needStart = z;
            Drawable build = this.mCircular ? new CircularProgressDrawable.Builder(context2, this.mProgressId).build() : new LinearProgressDrawable.Builder(context2, this.mProgressId).build();
            this.mProgressDrawable = build;
            ViewUtil.setBackground(this, build);
        } else if (this.mProgressId != progressId) {
            this.mProgressId = progressId;
            Drawable drawable2 = this.mProgressDrawable;
            if (drawable2 instanceof CircularProgressDrawable) {
                ((CircularProgressDrawable) drawable2).applyStyle(context2, progressId);
            } else {
                ((LinearProgressDrawable) drawable2).applyStyle(context2, progressId);
            }
        }
        if (progressMode >= 0) {
            Drawable drawable3 = this.mProgressDrawable;
            if (drawable3 instanceof CircularProgressDrawable) {
                ((CircularProgressDrawable) drawable3).setProgressMode(progressMode);
            } else {
                ((LinearProgressDrawable) drawable3).setProgressMode(progressMode);
            }
        }
        if (progress >= 0.0f) {
            setProgress(progress);
        }
        if (secondaryProgress >= 0.0f) {
            setSecondaryProgress(secondaryProgress);
        }
        if (needStart) {
            start();
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
    public void onVisibilityChanged(View changedView, int visibility) {
        super.onVisibilityChanged(changedView, visibility);
        if (changedView != this || !this.mAutostart) {
            return;
        }
        if (visibility == 8 || visibility == 4) {
            stop();
        } else {
            start();
        }
    }

    /* access modifiers changed from: protected */
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (getVisibility() == 0 && this.mAutostart) {
            start();
        }
        if (this.mStyleId != 0) {
            ThemeManager.getInstance().registerOnThemeChangedListener(this);
            onThemeChanged((ThemeManager.OnThemeChangedEvent) null);
        }
    }

    /* access modifiers changed from: protected */
    public void onDetachedFromWindow() {
        if (this.mAutostart) {
            stop();
        }
        super.onDetachedFromWindow();
        if (this.mStyleId != 0) {
            ThemeManager.getInstance().unregisterOnThemeChangedListener(this);
        }
    }

    public int getProgressMode() {
        if (this.mCircular) {
            return ((CircularProgressDrawable) this.mProgressDrawable).getProgressMode();
        }
        return ((LinearProgressDrawable) this.mProgressDrawable).getProgressMode();
    }

    public float getProgress() {
        if (this.mCircular) {
            return ((CircularProgressDrawable) this.mProgressDrawable).getProgress();
        }
        return ((LinearProgressDrawable) this.mProgressDrawable).getProgress();
    }

    public float getSecondaryProgress() {
        if (this.mCircular) {
            return ((CircularProgressDrawable) this.mProgressDrawable).getSecondaryProgress();
        }
        return ((LinearProgressDrawable) this.mProgressDrawable).getSecondaryProgress();
    }

    public void setProgress(float percent) {
        if (this.mCircular) {
            ((CircularProgressDrawable) this.mProgressDrawable).setProgress(percent);
        } else {
            ((LinearProgressDrawable) this.mProgressDrawable).setProgress(percent);
        }
    }

    public void setSecondaryProgress(float percent) {
        if (this.mCircular) {
            ((CircularProgressDrawable) this.mProgressDrawable).setSecondaryProgress(percent);
        } else {
            ((LinearProgressDrawable) this.mProgressDrawable).setSecondaryProgress(percent);
        }
    }

    public void start() {
        Drawable drawable = this.mProgressDrawable;
        if (drawable != null) {
            ((Animatable) drawable).start();
        }
    }

    public void stop() {
        Drawable drawable = this.mProgressDrawable;
        if (drawable != null) {
            ((Animatable) drawable).stop();
        }
    }
}
