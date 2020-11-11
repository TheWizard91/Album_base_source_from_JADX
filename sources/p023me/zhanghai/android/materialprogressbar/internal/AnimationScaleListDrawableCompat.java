package p023me.zhanghai.android.materialprogressbar.internal;

import android.content.res.Resources;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import p023me.zhanghai.android.materialprogressbar.internal.DrawableContainerCompat;

/* renamed from: me.zhanghai.android.materialprogressbar.internal.AnimationScaleListDrawableCompat */
public class AnimationScaleListDrawableCompat extends DrawableContainerCompat implements Animatable {
    private static final String TAG = "AnimationScaleListDrawableCompat";
    private AnimationScaleListState mAnimationScaleListState;
    private boolean mMutated;

    public AnimationScaleListDrawableCompat(Drawable[] drawables) {
        setConstantState(new AnimationScaleListState((AnimationScaleListState) null, this, (Resources) null));
        for (Drawable drawable : drawables) {
            this.mAnimationScaleListState.addDrawable(drawable);
        }
        onStateChange(getState());
    }

    private AnimationScaleListDrawableCompat(AnimationScaleListState state, Resources res) {
        setConstantState(new AnimationScaleListState(state, this, res));
        onStateChange(getState());
    }

    /* access modifiers changed from: protected */
    public boolean onStateChange(int[] stateSet) {
        return selectDrawable(this.mAnimationScaleListState.getCurrentDrawableIndexBasedOnScale()) || super.onStateChange(stateSet);
    }

    public Drawable mutate() {
        if (!this.mMutated && super.mutate() == this) {
            this.mAnimationScaleListState.mutate();
            this.mMutated = true;
        }
        return this;
    }

    public void clearMutated() {
        super.clearMutated();
        this.mMutated = false;
    }

    public void start() {
        Drawable dr = getCurrent();
        if (dr != null && (dr instanceof Animatable)) {
            ((Animatable) dr).start();
        }
    }

    public void stop() {
        Drawable dr = getCurrent();
        if (dr != null && (dr instanceof Animatable)) {
            ((Animatable) dr).stop();
        }
    }

    public boolean isRunning() {
        Drawable dr = getCurrent();
        if (dr == null || !(dr instanceof Animatable)) {
            return false;
        }
        return ((Animatable) dr).isRunning();
    }

    /* renamed from: me.zhanghai.android.materialprogressbar.internal.AnimationScaleListDrawableCompat$AnimationScaleListState */
    static class AnimationScaleListState extends DrawableContainerCompat.DrawableContainerState {
        int mAnimatableDrawableIndex = -1;
        int mStaticDrawableIndex = -1;
        int[] mThemeAttrs = null;

        AnimationScaleListState(AnimationScaleListState orig, AnimationScaleListDrawableCompat owner, Resources res) {
            super(orig, owner, res);
            if (orig != null) {
                this.mThemeAttrs = orig.mThemeAttrs;
                this.mStaticDrawableIndex = orig.mStaticDrawableIndex;
                this.mAnimatableDrawableIndex = orig.mAnimatableDrawableIndex;
            }
        }

        /* access modifiers changed from: package-private */
        public void mutate() {
            int[] iArr = this.mThemeAttrs;
            this.mThemeAttrs = iArr != null ? (int[]) iArr.clone() : null;
        }

        /* access modifiers changed from: package-private */
        public int addDrawable(Drawable drawable) {
            int pos = addChild(drawable);
            if (drawable instanceof Animatable) {
                this.mAnimatableDrawableIndex = pos;
            } else {
                this.mStaticDrawableIndex = pos;
            }
            return pos;
        }

        public Drawable newDrawable() {
            return new AnimationScaleListDrawableCompat(this, (Resources) null);
        }

        public Drawable newDrawable(Resources res) {
            return new AnimationScaleListDrawableCompat(this, res);
        }

        public boolean canApplyTheme() {
            return this.mThemeAttrs != null || super.canApplyTheme();
        }

        public int getCurrentDrawableIndexBasedOnScale() {
            if (!ValueAnimatorCompat.areAnimatorsEnabled()) {
                return this.mStaticDrawableIndex;
            }
            return this.mAnimatableDrawableIndex;
        }
    }

    public void applyTheme(Resources.Theme theme) {
        super.applyTheme(theme);
        onStateChange(getState());
    }

    /* access modifiers changed from: protected */
    public void setConstantState(DrawableContainerCompat.DrawableContainerState state) {
        super.setConstantState(state);
        if (state instanceof AnimationScaleListState) {
            this.mAnimationScaleListState = (AnimationScaleListState) state;
        }
    }
}
