package com.alexvasilkov.gestures.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.FrameLayout;
import com.alexvasilkov.gestures.GestureController;
import com.alexvasilkov.gestures.GestureControllerForPager;
import com.alexvasilkov.gestures.State;
import com.alexvasilkov.gestures.animation.ViewPositionAnimator;
import com.alexvasilkov.gestures.internal.DebugOverlay;
import com.alexvasilkov.gestures.internal.GestureDebug;
import com.alexvasilkov.gestures.views.interfaces.AnimatorView;
import com.alexvasilkov.gestures.views.interfaces.GestureView;

public class GestureFrameLayout extends FrameLayout implements GestureView, AnimatorView {
    private final GestureControllerForPager controller;
    private MotionEvent currentMotionEvent;
    private final Matrix matrix;
    private final Matrix matrixInverse;
    private ViewPositionAnimator positionAnimator;
    private final RectF tmpFloatRect;
    private final float[] tmpPointArray;

    public GestureFrameLayout(Context context) {
        this(context, (AttributeSet) null, 0);
    }

    public GestureFrameLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public GestureFrameLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.matrix = new Matrix();
        this.matrixInverse = new Matrix();
        this.tmpFloatRect = new RectF();
        this.tmpPointArray = new float[2];
        GestureControllerForPager gestureControllerForPager = new GestureControllerForPager(this);
        this.controller = gestureControllerForPager;
        gestureControllerForPager.getSettings().initFromAttributes(context, attrs);
        gestureControllerForPager.addOnStateChangeListener(new GestureController.OnStateChangeListener() {
            public void onStateChanged(State state) {
                GestureFrameLayout.this.applyState(state);
            }

            public void onStateReset(State oldState, State newState) {
                GestureFrameLayout.this.applyState(newState);
            }
        });
    }

    public GestureControllerForPager getController() {
        return this.controller;
    }

    public ViewPositionAnimator getPositionAnimator() {
        if (this.positionAnimator == null) {
            this.positionAnimator = new ViewPositionAnimator(this);
        }
        return this.positionAnimator;
    }

    public boolean dispatchTouchEvent(MotionEvent event) {
        this.currentMotionEvent = event;
        MotionEvent invertedEvent = applyMatrix(event, this.matrixInverse);
        try {
            return super.dispatchTouchEvent(invertedEvent);
        } finally {
            invertedEvent.recycle();
        }
    }

    public ViewParent invalidateChildInParent(int[] location, Rect dirty) {
        applyMatrix(dirty, this.matrix);
        return super.invalidateChildInParent(location, dirty);
    }

    public void requestDisallowInterceptTouchEvent(boolean disallowIntercept) {
        super.requestDisallowInterceptTouchEvent(disallowIntercept);
        if (disallowIntercept) {
            MotionEvent cancel = MotionEvent.obtain(this.currentMotionEvent);
            cancel.setAction(3);
            this.controller.onInterceptTouch(this, cancel);
            cancel.recycle();
        }
    }

    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return this.controller.onInterceptTouch(this, this.currentMotionEvent);
    }

    public boolean onTouchEvent(MotionEvent event) {
        return this.controller.onTouch(this, this.currentMotionEvent);
    }

    /* access modifiers changed from: protected */
    public void onSizeChanged(int width, int height, int oldWidth, int oldHeight) {
        super.onSizeChanged(width, height, oldWidth, oldHeight);
        this.controller.getSettings().setViewport((width - getPaddingLeft()) - getPaddingRight(), (height - getPaddingTop()) - getPaddingBottom());
        this.controller.updateState();
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        View child = getChildCount() == 0 ? null : getChildAt(0);
        if (child != null) {
            this.controller.getSettings().setImage(child.getMeasuredWidth(), child.getMeasuredHeight());
            this.controller.updateState();
        }
    }

    /* access modifiers changed from: protected */
    public void measureChildWithMargins(View child, int parentWidthMeasureSpec, int widthUsed, int parentHeightMeasureSpec, int heightUsed) {
        ViewGroup.MarginLayoutParams lp = (ViewGroup.MarginLayoutParams) child.getLayoutParams();
        child.measure(getChildMeasureSpecFixed(parentWidthMeasureSpec, getPaddingLeft() + getPaddingRight() + lp.leftMargin + lp.rightMargin + widthUsed, lp.width), getChildMeasureSpecFixed(parentHeightMeasureSpec, getPaddingTop() + getPaddingBottom() + lp.topMargin + lp.bottomMargin + heightUsed, lp.height));
    }

    /* access modifiers changed from: protected */
    public void applyState(State state) {
        state.get(this.matrix);
        this.matrix.invert(this.matrixInverse);
        invalidate();
    }

    /* access modifiers changed from: protected */
    public void dispatchDraw(Canvas canvas) {
        canvas.save();
        canvas.concat(this.matrix);
        super.dispatchDraw(canvas);
        canvas.restore();
        if (GestureDebug.isDrawDebugOverlay()) {
            DebugOverlay.drawDebug(this, canvas);
        }
    }

    public void addView(View child, int index, ViewGroup.LayoutParams params) {
        if (getChildCount() == 0) {
            super.addView(child, index, params);
            return;
        }
        throw new IllegalArgumentException("GestureFrameLayout can contain only one child");
    }

    private MotionEvent applyMatrix(MotionEvent event, Matrix matrix2) {
        this.tmpPointArray[0] = event.getX();
        this.tmpPointArray[1] = event.getY();
        matrix2.mapPoints(this.tmpPointArray);
        MotionEvent copy = MotionEvent.obtain(event);
        float[] fArr = this.tmpPointArray;
        copy.setLocation(fArr[0], fArr[1]);
        return copy;
    }

    private void applyMatrix(Rect rect, Matrix matrix2) {
        this.tmpFloatRect.set((float) rect.left, (float) rect.top, (float) rect.right, (float) rect.bottom);
        matrix2.mapRect(this.tmpFloatRect);
        rect.set(Math.round(this.tmpFloatRect.left), Math.round(this.tmpFloatRect.top), Math.round(this.tmpFloatRect.right), Math.round(this.tmpFloatRect.bottom));
    }

    protected static int getChildMeasureSpecFixed(int spec, int extra, int childDimension) {
        if (childDimension == -2) {
            return View.MeasureSpec.makeMeasureSpec(View.MeasureSpec.getSize(spec), 0);
        }
        return getChildMeasureSpec(spec, extra, childDimension);
    }
}
