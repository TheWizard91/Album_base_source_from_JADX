package com.google.firebase.inappmessaging.display.internal;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;

public class SwipeDismissTouchListener implements View.OnTouchListener {
    private long mAnimationTime;
    /* access modifiers changed from: private */
    public DismissCallbacks mDismissCallbacks;
    private float mDownX;
    private float mDownY;
    private int mMaxFlingVelocity;
    private int mMinFlingVelocity;
    private int mSlop;
    private boolean mSwiping;
    private int mSwipingSlop;
    /* access modifiers changed from: private */
    public Object mToken;
    private float mTranslationX;
    private VelocityTracker mVelocityTracker;
    /* access modifiers changed from: private */
    public View mView;
    private int mViewWidth = 1;

    public interface DismissCallbacks {
        boolean canDismiss(Object obj);

        void onDismiss(View view, Object obj);
    }

    public SwipeDismissTouchListener(View view, Object token, DismissCallbacks callbacks) {
        ViewConfiguration vc = ViewConfiguration.get(view.getContext());
        this.mSlop = vc.getScaledTouchSlop();
        this.mMinFlingVelocity = vc.getScaledMinimumFlingVelocity() * 16;
        this.mMaxFlingVelocity = vc.getScaledMaximumFlingVelocity();
        this.mAnimationTime = (long) view.getContext().getResources().getInteger(17694720);
        this.mView = view;
        this.mToken = token;
        this.mDismissCallbacks = callbacks;
    }

    public boolean onTouch(View view, MotionEvent motionEvent) {
        motionEvent.offsetLocation(this.mTranslationX, 0.0f);
        if (this.mViewWidth < 2) {
            this.mViewWidth = this.mView.getWidth();
        }
        int actionMasked = motionEvent.getActionMasked();
        if (actionMasked != 0) {
            boolean z = true;
            if (actionMasked != 1) {
                if (actionMasked == 2) {
                    VelocityTracker velocityTracker = this.mVelocityTracker;
                    if (velocityTracker != null) {
                        velocityTracker.addMovement(motionEvent);
                        float deltaX = motionEvent.getRawX() - this.mDownX;
                        float deltaY = motionEvent.getRawY() - this.mDownY;
                        if (Math.abs(deltaX) > ((float) this.mSlop) && Math.abs(deltaY) < Math.abs(deltaX) / 2.0f) {
                            this.mSwiping = true;
                            this.mSwipingSlop = deltaX > 0.0f ? this.mSlop : -this.mSlop;
                            this.mView.getParent().requestDisallowInterceptTouchEvent(true);
                            MotionEvent cancelEvent = MotionEvent.obtain(motionEvent);
                            cancelEvent.setAction(3 | (motionEvent.getActionIndex() << 8));
                            this.mView.onTouchEvent(cancelEvent);
                            cancelEvent.recycle();
                        }
                        if (this.mSwiping) {
                            this.mTranslationX = deltaX;
                            setTranslationX(deltaX - ((float) this.mSwipingSlop));
                            setAlpha(Math.max(0.0f, Math.min(1.0f, 1.0f - ((Math.abs(deltaX) * 2.0f) / ((float) this.mViewWidth)))));
                            return true;
                        }
                    }
                } else if (actionMasked == 3 && this.mVelocityTracker != null) {
                    startCancelAnimation();
                    this.mVelocityTracker.recycle();
                    this.mVelocityTracker = null;
                    this.mTranslationX = 0.0f;
                    this.mDownX = 0.0f;
                    this.mDownY = 0.0f;
                    this.mSwiping = false;
                }
            } else if (this.mVelocityTracker != null) {
                float deltaX2 = motionEvent.getRawX() - this.mDownX;
                this.mVelocityTracker.addMovement(motionEvent);
                this.mVelocityTracker.computeCurrentVelocity(1000);
                float velocityX = this.mVelocityTracker.getXVelocity();
                float absVelocityX = Math.abs(velocityX);
                float absVelocityY = Math.abs(this.mVelocityTracker.getYVelocity());
                boolean dismiss = false;
                boolean dismissRight = false;
                if (Math.abs(deltaX2) > ((float) (this.mViewWidth / 2)) && this.mSwiping) {
                    dismiss = true;
                    if (deltaX2 <= 0.0f) {
                        z = false;
                    }
                    dismissRight = z;
                } else if (((float) this.mMinFlingVelocity) <= absVelocityX && absVelocityX <= ((float) this.mMaxFlingVelocity) && absVelocityY < absVelocityX && absVelocityY < absVelocityX && this.mSwiping) {
                    dismiss = ((velocityX > 0.0f ? 1 : (velocityX == 0.0f ? 0 : -1)) < 0) == ((deltaX2 > 0.0f ? 1 : (deltaX2 == 0.0f ? 0 : -1)) < 0);
                    if (this.mVelocityTracker.getXVelocity() <= 0.0f) {
                        z = false;
                    }
                    dismissRight = z;
                }
                if (dismiss) {
                    startDismissAnimation(dismissRight);
                } else if (this.mSwiping) {
                    startCancelAnimation();
                }
                VelocityTracker velocityTracker2 = this.mVelocityTracker;
                if (velocityTracker2 != null) {
                    velocityTracker2.recycle();
                }
                this.mVelocityTracker = null;
                this.mTranslationX = 0.0f;
                this.mDownX = 0.0f;
                this.mDownY = 0.0f;
                this.mSwiping = false;
            }
            return false;
        }
        this.mDownX = motionEvent.getRawX();
        this.mDownY = motionEvent.getRawY();
        if (this.mDismissCallbacks.canDismiss(this.mToken)) {
            VelocityTracker obtain = VelocityTracker.obtain();
            this.mVelocityTracker = obtain;
            obtain.addMovement(motionEvent);
        }
        return false;
    }

    /* access modifiers changed from: protected */
    public void setTranslationX(float translationX) {
        this.mView.setTranslationX(translationX);
    }

    /* access modifiers changed from: protected */
    public float getTranslationX() {
        return this.mView.getTranslationX();
    }

    /* access modifiers changed from: protected */
    public void setAlpha(float alpha) {
        this.mView.setAlpha(alpha);
    }

    /* access modifiers changed from: protected */
    public void startDismissAnimation(boolean dismissRight) {
        int i = this.mViewWidth;
        if (!dismissRight) {
            i = -i;
        }
        animateTo((float) i, 0.0f, new AnimatorListenerAdapter() {
            public void onAnimationEnd(Animator animation) {
                SwipeDismissTouchListener.this.performDismiss();
            }
        });
    }

    /* access modifiers changed from: protected */
    public void startCancelAnimation() {
        animateTo(0.0f, 1.0f, (AnimatorListenerAdapter) null);
    }

    private void animateTo(float translationX, float alpha, AnimatorListenerAdapter listener) {
        float beginTranslation = getTranslationX();
        float beginAlpha = this.mView.getAlpha();
        ValueAnimator animator = ValueAnimator.ofFloat(new float[]{0.0f, 1.0f});
        animator.setDuration(this.mAnimationTime);
        final float f = beginTranslation;
        final float f2 = translationX - beginTranslation;
        final float f3 = beginAlpha;
        final float f4 = alpha - beginAlpha;
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float translationX = f + (valueAnimator.getAnimatedFraction() * f2);
                float alpha = f3 + (valueAnimator.getAnimatedFraction() * f4);
                SwipeDismissTouchListener.this.setTranslationX(translationX);
                SwipeDismissTouchListener.this.setAlpha(alpha);
            }
        });
        if (listener != null) {
            animator.addListener(listener);
        }
        animator.start();
    }

    /* access modifiers changed from: private */
    public void performDismiss() {
        final ViewGroup.LayoutParams lp = this.mView.getLayoutParams();
        final int originalHeight = this.mView.getHeight();
        ValueAnimator animator = ValueAnimator.ofInt(new int[]{originalHeight, 1}).setDuration(this.mAnimationTime);
        animator.addListener(new AnimatorListenerAdapter() {
            public void onAnimationEnd(Animator animation) {
                SwipeDismissTouchListener.this.mDismissCallbacks.onDismiss(SwipeDismissTouchListener.this.mView, SwipeDismissTouchListener.this.mToken);
                SwipeDismissTouchListener.this.mView.setAlpha(1.0f);
                SwipeDismissTouchListener.this.mView.setTranslationX(0.0f);
                lp.height = originalHeight;
                SwipeDismissTouchListener.this.mView.setLayoutParams(lp);
            }
        });
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                lp.height = ((Integer) valueAnimator.getAnimatedValue()).intValue();
                SwipeDismissTouchListener.this.mView.setLayoutParams(lp);
            }
        });
        animator.start();
    }
}
