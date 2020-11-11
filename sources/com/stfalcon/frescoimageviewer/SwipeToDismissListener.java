package com.stfalcon.frescoimageviewer;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateInterpolator;

class SwipeToDismissListener implements View.OnTouchListener {
    private static final String PROPERTY_TRANSLATION_X = "translationY";
    private OnDismissListener dismissListener;
    private OnViewMoveListener moveListener;
    private float startY;
    private final View swipeView;
    private boolean tracking = false;
    /* access modifiers changed from: private */
    public int translationLimit;

    interface OnViewMoveListener {
        void onViewMove(float f, int i);
    }

    public SwipeToDismissListener(View swipeView2, OnDismissListener dismissListener2, OnViewMoveListener moveListener2) {
        this.swipeView = swipeView2;
        this.dismissListener = dismissListener2;
        this.moveListener = moveListener2;
    }

    public boolean onTouch(View v, MotionEvent event) {
        this.translationLimit = v.getHeight() / 4;
        int action = event.getAction();
        if (action != 0) {
            if (action != 1) {
                if (action == 2) {
                    if (this.tracking) {
                        float translationY = event.getY() - this.startY;
                        this.swipeView.setTranslationY(translationY);
                        callMoveListener(translationY, this.translationLimit);
                    }
                    return true;
                } else if (action != 3) {
                    return false;
                }
            }
            if (this.tracking) {
                this.tracking = false;
                animateSwipeView(v.getHeight());
            }
            return true;
        }
        Rect hitRect = new Rect();
        this.swipeView.getHitRect(hitRect);
        if (hitRect.contains((int) event.getX(), (int) event.getY())) {
            this.tracking = true;
        }
        this.startY = event.getY();
        return true;
    }

    private void animateSwipeView(int parentHeight) {
        float currentPosition = this.swipeView.getTranslationY();
        float animateTo = 0.0f;
        int i = this.translationLimit;
        if (currentPosition < ((float) (-i))) {
            animateTo = (float) (-parentHeight);
        } else if (currentPosition > ((float) i)) {
            animateTo = (float) parentHeight;
        }
        final boolean isDismissed = animateTo != 0.0f;
        ObjectAnimator animator = ObjectAnimator.ofFloat(this.swipeView, PROPERTY_TRANSLATION_X, new float[]{currentPosition, animateTo});
        animator.setDuration(200);
        animator.setInterpolator(new AccelerateInterpolator());
        animator.addListener(new AnimatorListenerAdapter() {
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                if (isDismissed) {
                    SwipeToDismissListener.this.callDismissListener();
                }
            }
        });
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            public void onAnimationUpdate(ValueAnimator animation) {
                SwipeToDismissListener.this.callMoveListener(((Float) animation.getAnimatedValue()).floatValue(), SwipeToDismissListener.this.translationLimit);
            }
        });
        animator.start();
    }

    /* access modifiers changed from: private */
    public void callDismissListener() {
        OnDismissListener onDismissListener = this.dismissListener;
        if (onDismissListener != null) {
            onDismissListener.onDismiss();
        }
    }

    /* access modifiers changed from: private */
    public void callMoveListener(float translationY, int translationLimit2) {
        OnViewMoveListener onViewMoveListener = this.moveListener;
        if (onViewMoveListener != null) {
            onViewMoveListener.onViewMove(translationY, translationLimit2);
        }
    }
}
