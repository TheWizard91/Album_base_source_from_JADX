package com.rey.material.app;

import android.app.Dialog;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Handler;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.Transformation;
import android.widget.FrameLayout;
import com.google.common.primitives.Ints;
import com.rey.material.C2500R;
import com.rey.material.drawable.BlankDrawable;

public class BottomSheetDialog extends Dialog {
    /* access modifiers changed from: private */
    public Animation mAnimation;
    /* access modifiers changed from: private */
    public boolean mCancelable;
    /* access modifiers changed from: private */
    public boolean mCanceledOnTouchOutside;
    /* access modifiers changed from: private */
    public ContainerFrameLayout mContainer;
    /* access modifiers changed from: private */
    public View mContentView;
    /* access modifiers changed from: private */
    public final Runnable mDismissAction;
    /* access modifiers changed from: private */
    public boolean mDismissPending;
    /* access modifiers changed from: private */
    public GestureDetector mGestureDetector;
    /* access modifiers changed from: private */
    public final Handler mHandler;
    /* access modifiers changed from: private */
    public int mInDuration;
    /* access modifiers changed from: private */
    public Interpolator mInInterpolator;
    /* access modifiers changed from: private */
    public int mLayoutHeight;
    /* access modifiers changed from: private */
    public int mMinFlingVelocity;
    private int mOutDuration;
    private Interpolator mOutInterpolator;
    /* access modifiers changed from: private */
    public boolean mRunShowAnimation;

    public BottomSheetDialog(Context context) {
        this(context, C2500R.C2505style.Material_App_BottomSheetDialog);
    }

    public BottomSheetDialog(Context context, int style) {
        super(context, style);
        this.mCancelable = true;
        this.mCanceledOnTouchOutside = true;
        this.mLayoutHeight = -2;
        this.mHandler = new Handler();
        this.mDismissAction = new Runnable() {
            public void run() {
                try {
                    BottomSheetDialog.super.dismiss();
                } catch (IllegalArgumentException e) {
                }
            }
        };
        this.mRunShowAnimation = false;
        this.mDismissPending = false;
        requestWindowFeature(1);
        getWindow().setBackgroundDrawable(BlankDrawable.getInstance());
        WindowManager.LayoutParams layout = getWindow().getAttributes();
        layout.width = -1;
        layout.height = -1;
        layout.windowAnimations = C2500R.C2505style.DialogNoAnimation;
        getWindow().setAttributes(layout);
        init(context, style);
    }

    private void init(Context context, int style) {
        this.mContainer = new ContainerFrameLayout(context);
        cancelable(true);
        canceledOnTouchOutside(true);
        onCreate();
        applyStyle(style);
        this.mMinFlingVelocity = ViewConfiguration.get(context).getScaledMinimumFlingVelocity() * 2;
        this.mGestureDetector = new GestureDetector(context, new GestureDetector.OnGestureListener() {
            public boolean onDown(MotionEvent e) {
                return false;
            }

            public void onShowPress(MotionEvent e) {
            }

            public boolean onSingleTapUp(MotionEvent e) {
                return false;
            }

            public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
                return false;
            }

            public void onLongPress(MotionEvent e) {
            }

            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                if (velocityY <= ((float) BottomSheetDialog.this.mMinFlingVelocity)) {
                    return false;
                }
                BottomSheetDialog.this.dismiss();
                return true;
            }
        });
        super.setContentView(this.mContainer);
    }

    /* access modifiers changed from: protected */
    public void onCreate() {
    }

    public BottomSheetDialog applyStyle(int styleId) {
        int resId;
        Context context = getContext();
        TypedArray a = context.obtainStyledAttributes(styleId, C2500R.styleable.BottomSheetDialog);
        int count = a.getIndexCount();
        for (int i = 0; i < count; i++) {
            int attr = a.getIndex(i);
            if (attr == C2500R.styleable.BottomSheetDialog_android_layout_height) {
                heightParam(a.getLayoutDimension(attr, -2));
            } else if (attr == C2500R.styleable.BottomSheetDialog_bsd_cancelable) {
                cancelable(a.getBoolean(attr, true));
            } else if (attr == C2500R.styleable.BottomSheetDialog_bsd_canceledOnTouchOutside) {
                canceledOnTouchOutside(a.getBoolean(attr, true));
            } else if (attr == C2500R.styleable.BottomSheetDialog_bsd_dimAmount) {
                dimAmount(a.getFloat(attr, 0.0f));
            } else if (attr == C2500R.styleable.BottomSheetDialog_bsd_inDuration) {
                inDuration(a.getInteger(attr, 0));
            } else if (attr == C2500R.styleable.BottomSheetDialog_bsd_inInterpolator) {
                int resId2 = a.getResourceId(attr, 0);
                if (resId2 != 0) {
                    inInterpolator(AnimationUtils.loadInterpolator(context, resId2));
                }
            } else if (attr == C2500R.styleable.BottomSheetDialog_bsd_outDuration) {
                outDuration(a.getInteger(attr, 0));
            } else if (attr == C2500R.styleable.BottomSheetDialog_bsd_outInterpolator && (resId = a.getResourceId(attr, 0)) != 0) {
                outInterpolator(AnimationUtils.loadInterpolator(context, resId));
            }
        }
        a.recycle();
        if (this.mInInterpolator == null) {
            this.mInInterpolator = new DecelerateInterpolator();
        }
        if (this.mOutInterpolator == null) {
            this.mOutInterpolator = new AccelerateInterpolator();
        }
        return this;
    }

    public BottomSheetDialog cancelable(boolean cancelable) {
        super.setCancelable(cancelable);
        this.mCancelable = cancelable;
        return this;
    }

    public BottomSheetDialog canceledOnTouchOutside(boolean cancel) {
        super.setCanceledOnTouchOutside(cancel);
        this.mCanceledOnTouchOutside = cancel;
        return this;
    }

    public BottomSheetDialog dimAmount(float amount) {
        Window window = getWindow();
        if (amount > 0.0f) {
            window.addFlags(2);
            WindowManager.LayoutParams lp = window.getAttributes();
            lp.dimAmount = amount;
            window.setAttributes(lp);
        } else {
            window.clearFlags(2);
        }
        return this;
    }

    public BottomSheetDialog contentView(View v) {
        this.mContentView = v;
        this.mContainer.removeAllViews();
        this.mContainer.addView(v);
        return this;
    }

    public BottomSheetDialog contentView(int layoutId) {
        if (layoutId == 0) {
            return this;
        }
        return contentView(LayoutInflater.from(getContext()).inflate(layoutId, (ViewGroup) null));
    }

    public BottomSheetDialog heightParam(int height) {
        if (this.mLayoutHeight != height) {
            this.mLayoutHeight = height;
            if (isShowing() && this.mContentView != null) {
                this.mRunShowAnimation = true;
                this.mContainer.forceLayout();
                this.mContainer.requestLayout();
            }
        }
        return this;
    }

    public BottomSheetDialog inDuration(int duration) {
        this.mInDuration = duration;
        return this;
    }

    public BottomSheetDialog inInterpolator(Interpolator interpolator) {
        this.mInInterpolator = interpolator;
        return this;
    }

    public BottomSheetDialog outDuration(int duration) {
        this.mOutDuration = duration;
        return this;
    }

    public BottomSheetDialog outInterpolator(Interpolator interpolator) {
        this.mOutInterpolator = interpolator;
        return this;
    }

    /* access modifiers changed from: protected */
    public void onStart() {
        super.onStart();
        if (this.mContentView != null) {
            this.mRunShowAnimation = true;
            this.mContainer.forceLayout();
        }
    }

    /* access modifiers changed from: protected */
    public void onStop() {
        super.onStop();
        this.mContainer = null;
        this.mContentView = null;
        this.mGestureDetector = null;
    }

    public void setCancelable(boolean flag) {
        cancelable(flag);
    }

    public void setCanceledOnTouchOutside(boolean cancel) {
        canceledOnTouchOutside(cancel);
    }

    public void setContentView(View v) {
        contentView(v);
    }

    public void setContentView(int layoutId) {
        contentView(layoutId);
    }

    public void setContentView(View v, ViewGroup.LayoutParams params) {
        contentView(v);
    }

    public void addContentView(View view, ViewGroup.LayoutParams params) {
        contentView(view);
    }

    public void dismissImmediately() {
        super.dismiss();
        Animation animation = this.mAnimation;
        if (animation != null) {
            animation.cancel();
        }
        Handler handler = this.mHandler;
        if (handler != null) {
            handler.removeCallbacks(this.mDismissAction);
        }
    }

    public void dismiss() {
        if (isShowing() && !this.mDismissPending) {
            if (this.mContentView != null) {
                SlideAnimation slideAnimation = new SlideAnimation(this.mContentView.getTop(), this.mContainer.getMeasuredHeight());
                this.mAnimation = slideAnimation;
                slideAnimation.setDuration((long) this.mOutDuration);
                this.mAnimation.setInterpolator(this.mOutInterpolator);
                this.mAnimation.setAnimationListener(new Animation.AnimationListener() {
                    public void onAnimationStart(Animation animation) {
                        boolean unused = BottomSheetDialog.this.mDismissPending = true;
                    }

                    public void onAnimationRepeat(Animation animation) {
                    }

                    public void onAnimationEnd(Animation animation) {
                        boolean unused = BottomSheetDialog.this.mDismissPending = false;
                        Animation unused2 = BottomSheetDialog.this.mAnimation = null;
                        BottomSheetDialog.this.mHandler.post(BottomSheetDialog.this.mDismissAction);
                    }
                });
                this.mContentView.startAnimation(this.mAnimation);
                return;
            }
            this.mHandler.post(this.mDismissAction);
        }
    }

    /* access modifiers changed from: protected */
    public int getContainerHeight() {
        ContainerFrameLayout containerFrameLayout = this.mContainer;
        if (containerFrameLayout == null) {
            return 0;
        }
        return containerFrameLayout.getHeight();
    }

    private class ContainerFrameLayout extends FrameLayout {
        private int mChildTop = -1;
        private boolean mClickOutside = false;

        public ContainerFrameLayout(Context context) {
            super(context);
        }

        public void setChildTop(int top) {
            this.mChildTop = top;
            View child = getChildAt(0);
            if (child != null) {
                child.offsetTopAndBottom(this.mChildTop - child.getTop());
            }
        }

        /* access modifiers changed from: protected */
        public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
            int widthSize = View.MeasureSpec.getSize(widthMeasureSpec);
            int heightSize = View.MeasureSpec.getSize(heightMeasureSpec);
            View child = getChildAt(0);
            if (child != null) {
                int access$600 = BottomSheetDialog.this.mLayoutHeight;
                if (access$600 == -2) {
                    child.measure(View.MeasureSpec.makeMeasureSpec(widthSize, Ints.MAX_POWER_OF_TWO), View.MeasureSpec.makeMeasureSpec(heightSize, Integer.MIN_VALUE));
                } else if (access$600 != -1) {
                    child.measure(View.MeasureSpec.makeMeasureSpec(widthSize, Ints.MAX_POWER_OF_TWO), View.MeasureSpec.makeMeasureSpec(Math.min(BottomSheetDialog.this.mLayoutHeight, heightSize), Ints.MAX_POWER_OF_TWO));
                } else {
                    child.measure(View.MeasureSpec.makeMeasureSpec(widthSize, Ints.MAX_POWER_OF_TWO), View.MeasureSpec.makeMeasureSpec(heightSize, Ints.MAX_POWER_OF_TWO));
                }
            }
            setMeasuredDimension(widthSize, heightSize);
        }

        /* access modifiers changed from: protected */
        public void onLayout(boolean changed, int left, int top, int right, int bottom) {
            View child = getChildAt(0);
            if (child != null) {
                if (this.mChildTop < 0) {
                    this.mChildTop = bottom - top;
                }
                child.layout(0, this.mChildTop, child.getMeasuredWidth(), Math.max(bottom - top, this.mChildTop + child.getMeasuredHeight()));
                if (BottomSheetDialog.this.mRunShowAnimation) {
                    boolean unused = BottomSheetDialog.this.mRunShowAnimation = false;
                    if (BottomSheetDialog.this.mAnimation != null) {
                        BottomSheetDialog.this.mAnimation.cancel();
                        Animation unused2 = BottomSheetDialog.this.mAnimation = null;
                    }
                    if (BottomSheetDialog.this.mContentView != null) {
                        int start = this.mChildTop < 0 ? getHeight() : child.getTop();
                        int end = getMeasuredHeight() - BottomSheetDialog.this.mContentView.getMeasuredHeight();
                        if (start != end) {
                            Animation unused3 = BottomSheetDialog.this.mAnimation = new SlideAnimation(start, end);
                            BottomSheetDialog.this.mAnimation.setDuration((long) BottomSheetDialog.this.mInDuration);
                            BottomSheetDialog.this.mAnimation.setInterpolator(BottomSheetDialog.this.mInInterpolator);
                            BottomSheetDialog.this.mAnimation.setAnimationListener(new Animation.AnimationListener() {
                                public void onAnimationStart(Animation animation) {
                                }

                                public void onAnimationRepeat(Animation animation) {
                                }

                                public void onAnimationEnd(Animation animation) {
                                    Animation unused = BottomSheetDialog.this.mAnimation = null;
                                }
                            });
                            BottomSheetDialog.this.mContentView.startAnimation(BottomSheetDialog.this.mAnimation);
                        }
                    }
                }
            }
        }

        private boolean isOutsideDialog(float x, float y) {
            if (y < ((float) this.mChildTop)) {
                return true;
            }
            View child = getChildAt(0);
            if (child == null || y <= ((float) (this.mChildTop + child.getMeasuredHeight()))) {
                return false;
            }
            return true;
        }

        public boolean dispatchTouchEvent(MotionEvent ev) {
            if (super.dispatchTouchEvent(ev) || BottomSheetDialog.this.mGestureDetector == null) {
                return true;
            }
            BottomSheetDialog.this.mGestureDetector.onTouchEvent(ev);
            return true;
        }

        public boolean onTouchEvent(MotionEvent event) {
            if (super.onTouchEvent(event)) {
                return true;
            }
            int action = event.getAction();
            if (action != 0) {
                if (action != 1) {
                    if (action == 2) {
                        return this.mClickOutside;
                    }
                    if (action != 3) {
                        return false;
                    }
                    this.mClickOutside = false;
                    return false;
                } else if (!this.mClickOutside || !isOutsideDialog(event.getX(), event.getY())) {
                    return false;
                } else {
                    this.mClickOutside = false;
                    if (BottomSheetDialog.this.mCancelable && BottomSheetDialog.this.mCanceledOnTouchOutside) {
                        BottomSheetDialog.this.dismiss();
                    }
                    return true;
                }
            } else if (!isOutsideDialog(event.getX(), event.getY())) {
                return false;
            } else {
                this.mClickOutside = true;
                return true;
            }
        }
    }

    private class SlideAnimation extends Animation {
        int mEnd;
        int mStart;

        public SlideAnimation(int start, int end) {
            this.mStart = start;
            this.mEnd = end;
        }

        /* access modifiers changed from: protected */
        public void applyTransformation(float interpolatedTime, Transformation t) {
            int i = this.mEnd;
            int i2 = this.mStart;
            int top = Math.round((((float) (i - i2)) * interpolatedTime) + ((float) i2));
            if (BottomSheetDialog.this.mContainer != null) {
                BottomSheetDialog.this.mContainer.setChildTop(top);
            } else {
                cancel();
            }
        }
    }
}
