package com.facebook.drawee.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.graphics.PointF;
import android.graphics.Rect;
import android.transition.ChangeBounds;
import android.transition.Transition;
import android.transition.TransitionSet;
import android.transition.TransitionValues;
import android.view.ViewGroup;
import com.facebook.drawee.drawable.ScalingUtils;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import javax.annotation.Nullable;

public class DraweeTransition extends Transition {
    private static final String PROPNAME_BOUNDS = "draweeTransition:bounds";
    @Nullable
    private final PointF mFromFocusPoint;
    private final ScalingUtils.ScaleType mFromScale;
    /* access modifiers changed from: private */
    @Nullable
    public final PointF mToFocusPoint;
    /* access modifiers changed from: private */
    public final ScalingUtils.ScaleType mToScale;

    public static TransitionSet createTransitionSet(ScalingUtils.ScaleType fromScale, ScalingUtils.ScaleType toScale) {
        return createTransitionSet(fromScale, toScale, (PointF) null, (PointF) null);
    }

    public static TransitionSet createTransitionSet(ScalingUtils.ScaleType fromScale, ScalingUtils.ScaleType toScale, @Nullable PointF fromFocusPoint, @Nullable PointF toFocusPoint) {
        TransitionSet transitionSet = new TransitionSet();
        transitionSet.addTransition(new ChangeBounds());
        transitionSet.addTransition(new DraweeTransition(fromScale, toScale, fromFocusPoint, toFocusPoint));
        return transitionSet;
    }

    public DraweeTransition(ScalingUtils.ScaleType fromScale, ScalingUtils.ScaleType toScale, @Nullable PointF fromFocusPoint, @Nullable PointF toFocusPoint) {
        this.mFromScale = fromScale;
        this.mToScale = toScale;
        this.mFromFocusPoint = fromFocusPoint;
        this.mToFocusPoint = toFocusPoint;
    }

    public DraweeTransition(ScalingUtils.ScaleType fromScale, ScalingUtils.ScaleType toScale) {
        this(fromScale, toScale, (PointF) null, (PointF) null);
    }

    public void captureStartValues(TransitionValues transitionValues) {
        captureValues(transitionValues);
    }

    public void captureEndValues(TransitionValues transitionValues) {
        captureValues(transitionValues);
    }

    @Nullable
    public Animator createAnimator(ViewGroup sceneRoot, TransitionValues startValues, TransitionValues endValues) {
        if (startValues == null || endValues == null) {
            return null;
        }
        Rect startBounds = (Rect) startValues.values.get(PROPNAME_BOUNDS);
        Rect endBounds = (Rect) endValues.values.get(PROPNAME_BOUNDS);
        if (startBounds == null || endBounds == null) {
            return null;
        }
        if (this.mFromScale == this.mToScale && this.mFromFocusPoint == this.mToFocusPoint) {
            return null;
        }
        final GenericDraweeView draweeView = (GenericDraweeView) startValues.view;
        final ScalingUtils.InterpolatingScaleType scaleType = new ScalingUtils.InterpolatingScaleType(this.mFromScale, this.mToScale, startBounds, endBounds, this.mFromFocusPoint, this.mToFocusPoint);
        ((GenericDraweeHierarchy) draweeView.getHierarchy()).setActualImageScaleType(scaleType);
        ValueAnimator animator = ValueAnimator.ofFloat(new float[]{0.0f, 1.0f});
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            public void onAnimationUpdate(ValueAnimator animation) {
                scaleType.setValue(((Float) animation.getAnimatedValue()).floatValue());
            }
        });
        animator.addListener(new AnimatorListenerAdapter() {
            public void onAnimationEnd(Animator animation) {
                ((GenericDraweeHierarchy) draweeView.getHierarchy()).setActualImageScaleType(DraweeTransition.this.mToScale);
                if (DraweeTransition.this.mToFocusPoint != null) {
                    ((GenericDraweeHierarchy) draweeView.getHierarchy()).setActualImageFocusPoint(DraweeTransition.this.mToFocusPoint);
                }
            }
        });
        return animator;
    }

    private void captureValues(TransitionValues transitionValues) {
        if (transitionValues.view instanceof GenericDraweeView) {
            transitionValues.values.put(PROPNAME_BOUNDS, new Rect(0, 0, transitionValues.view.getWidth(), transitionValues.view.getHeight()));
        }
    }
}
