package com.alexvasilkov.gestures;

import android.graphics.Matrix;
import android.graphics.RectF;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.ViewConfiguration;
import androidx.viewpager.widget.ViewPager;
import com.alexvasilkov.gestures.internal.detectors.RotationGestureDetector;

public class GestureControllerForPager extends GestureController {
    private static final float OVERSCROLL_THRESHOLD_FACTOR = 4.0f;
    private static final View.OnTouchListener PAGER_TOUCH_LISTENER = new View.OnTouchListener() {
        private boolean isTouchInProgress;

        public boolean onTouch(View view, MotionEvent event) {
            if (this.isTouchInProgress || event.getActionMasked() != 0) {
                GestureControllerForPager.settleViewPagerIfFinished((ViewPager) view, event);
                return true;
            }
            this.isTouchInProgress = true;
            view.dispatchTouchEvent(event);
            this.isTouchInProgress = false;
            return true;
        }
    };
    private static final float SCROLL_THRESHOLD = 15.0f;
    private static final Matrix tmpMatrix = new Matrix();
    private static final RectF tmpRectF = new RectF();
    private boolean isScrollGestureDetected;
    private boolean isSkipViewPager;
    private boolean isViewPagerDisabled;
    private boolean isViewPagerInterceptedScroll;
    private float lastViewPagerEventX;
    private final int touchSlop;
    private ViewPager viewPager;
    private float viewPagerSkippedX;
    private int viewPagerX;

    public GestureControllerForPager(View view) {
        super(view);
        this.touchSlop = ViewConfiguration.get(view.getContext()).getScaledTouchSlop();
    }

    public void enableScrollInViewPager(ViewPager pager) {
        this.viewPager = pager;
        pager.setOnTouchListener(PAGER_TOUCH_LISTENER);
        pager.setMotionEventSplittingEnabled(false);
    }

    public void disableViewPager(boolean disable) {
        this.isViewPagerDisabled = disable;
    }

    public boolean onTouch(View view, MotionEvent event) {
        return this.viewPager != null || super.onTouch(view, event);
    }

    /* access modifiers changed from: protected */
    public boolean onTouchInternal(View view, MotionEvent event) {
        if (this.viewPager == null) {
            return super.onTouchInternal(view, event);
        }
        MotionEvent pagerEvent = MotionEvent.obtain(event);
        transformToPagerEvent(pagerEvent, view, this.viewPager);
        handleTouch(pagerEvent);
        boolean result = super.onTouchInternal(view, pagerEvent);
        pagerEvent.recycle();
        return result;
    }

    /* access modifiers changed from: protected */
    public boolean shouldDisallowInterceptTouch(MotionEvent event) {
        return this.viewPager != null || super.shouldDisallowInterceptTouch(event);
    }

    private void handleTouch(MotionEvent event) {
        if (event.getActionMasked() == 5 && event.getPointerCount() == 2) {
            this.isSkipViewPager = !hasViewPagerX();
        }
    }

    /* access modifiers changed from: protected */
    public boolean onDown(MotionEvent event) {
        if (this.viewPager == null) {
            return super.onDown(event);
        }
        this.isSkipViewPager = false;
        this.isViewPagerInterceptedScroll = false;
        this.isScrollGestureDetected = false;
        this.viewPagerX = computeInitialViewPagerScroll(event);
        this.lastViewPagerEventX = event.getX();
        this.viewPagerSkippedX = 0.0f;
        passEventToViewPager(event);
        return super.onDown(event);
    }

    /* access modifiers changed from: protected */
    public void onUpOrCancel(MotionEvent event) {
        passEventToViewPager(event);
        super.onUpOrCancel(event);
    }

    /* access modifiers changed from: protected */
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float dx, float dy) {
        if (this.viewPager == null) {
            return super.onScroll(e1, e2, dx, dy);
        }
        if (!this.isScrollGestureDetected) {
            this.isScrollGestureDetected = true;
            return true;
        }
        return super.onScroll(e1, e2, -scrollBy(e2, -dx), hasViewPagerX() ? 0.0f : dy);
    }

    /* access modifiers changed from: protected */
    public boolean onFling(MotionEvent e1, MotionEvent e2, float vx, float vy) {
        return !hasViewPagerX() && super.onFling(e1, e2, vx, vy);
    }

    /* access modifiers changed from: protected */
    public boolean onScaleBegin(ScaleGestureDetector detector) {
        return !hasViewPagerX() && super.onScaleBegin(detector);
    }

    /* access modifiers changed from: protected */
    public boolean onRotationBegin(RotationGestureDetector detector) {
        return !hasViewPagerX() && super.onRotationBegin(detector);
    }

    /* access modifiers changed from: protected */
    public boolean onDoubleTapEvent(MotionEvent event) {
        return !hasViewPagerX() && super.onDoubleTapEvent(event);
    }

    private float scrollBy(MotionEvent event, float dx) {
        if (this.isSkipViewPager || this.isViewPagerDisabled) {
            return dx;
        }
        State state = getState();
        StateController stateController = getStateController();
        RectF rectF = tmpRectF;
        stateController.getMovementArea(state, rectF);
        float pagerDx = skipPagerMovement(splitPagerScroll(dx, state, rectF), state, rectF);
        float viewDx = dx - pagerDx;
        boolean shouldFixViewX = this.isViewPagerInterceptedScroll && this.viewPagerX == 0;
        int actualX = performViewPagerScroll(event, pagerDx);
        this.viewPagerX += actualX;
        if (shouldFixViewX) {
            return viewDx + ((float) (Math.round(pagerDx) - actualX));
        }
        return viewDx;
    }

    private float splitPagerScroll(float dx, State state, RectF movBounds) {
        float pagerMovementX;
        if (!getSettings().isPanEnabled()) {
            return dx;
        }
        float dir = Math.signum(dx);
        float movementX = Math.abs(dx);
        float viewX = state.getX();
        float availableViewX = dir < 0.0f ? viewX - movBounds.left : movBounds.right - viewX;
        int i = this.viewPagerX;
        float availablePagerX = ((float) i) * dir < 0.0f ? (float) Math.abs(i) : 0.0f;
        if (availableViewX < 0.0f) {
            availableViewX = 0.0f;
        }
        if (availablePagerX >= movementX) {
            pagerMovementX = movementX;
        } else if (availableViewX + availablePagerX >= movementX) {
            pagerMovementX = availablePagerX;
        } else {
            pagerMovementX = movementX - availableViewX;
        }
        return pagerMovementX * dir;
    }

    private float skipPagerMovement(float pagerDx, State state, RectF movBounds) {
        float overscrollDist = getSettings().getOverscrollDistanceY() * OVERSCROLL_THRESHOLD_FACTOR;
        float overscrollThreshold = 0.0f;
        if (state.getY() < movBounds.top) {
            overscrollThreshold = (movBounds.top - state.getY()) / overscrollDist;
        } else if (state.getY() > movBounds.bottom) {
            overscrollThreshold = (state.getY() - movBounds.bottom) / overscrollDist;
        }
        float minZoom = getStateController().getFitZoom(state);
        float pagerThreshold = ((float) Math.sqrt((double) Math.max(0.0f, Math.min(Math.max(overscrollThreshold, minZoom == 0.0f ? 0.0f : (state.getZoom() / minZoom) - 1.0f), 1.0f)))) * ((float) this.touchSlop) * SCROLL_THRESHOLD;
        if (this.viewPagerSkippedX * pagerDx < 0.0f && this.viewPagerX == 0) {
            this.viewPagerSkippedX = 0.0f;
        }
        if (hasViewPagerX()) {
            this.viewPagerSkippedX = Math.signum((float) this.viewPagerX) * pagerThreshold;
        }
        if (Math.abs(this.viewPagerSkippedX) < pagerThreshold) {
            float f = this.viewPagerSkippedX;
            if (pagerDx * f >= 0.0f) {
                float f2 = f + pagerDx;
                this.viewPagerSkippedX = f2;
                float over = Math.max(0.0f, Math.abs(f2) - pagerThreshold) * Math.signum(pagerDx);
                this.viewPagerSkippedX -= over;
                return over;
            }
        }
        return pagerDx;
    }

    private int computeInitialViewPagerScroll(MotionEvent downEvent) {
        int scroll = this.viewPager.getScrollX();
        int pageWidth = this.viewPager.getWidth() + this.viewPager.getPageMargin();
        while (scroll < 0) {
            scroll += pageWidth;
        }
        return (pageWidth * ((int) ((((float) scroll) + downEvent.getX()) / ((float) pageWidth)))) - scroll;
    }

    private boolean hasViewPagerX() {
        int i = this.viewPagerX;
        return i < -1 || i > 1;
    }

    private int performViewPagerScroll(MotionEvent event, float pagerDx) {
        int scrollBegin = this.viewPager.getScrollX();
        this.lastViewPagerEventX += pagerDx;
        passEventToViewPager(event);
        return scrollBegin - this.viewPager.getScrollX();
    }

    private void passEventToViewPager(MotionEvent event) {
        if (this.viewPager != null) {
            MotionEvent fixedEvent = obtainOnePointerEvent(event);
            fixedEvent.setLocation(this.lastViewPagerEventX, 0.0f);
            if (this.isViewPagerInterceptedScroll) {
                this.viewPager.onTouchEvent(fixedEvent);
            } else {
                this.isViewPagerInterceptedScroll = this.viewPager.onInterceptTouchEvent(fixedEvent);
            }
            if (!this.isViewPagerInterceptedScroll && hasViewPagerX()) {
                settleViewPagerIfFinished(this.viewPager, event);
            }
            try {
                ViewPager viewPager2 = this.viewPager;
                if (viewPager2 != null && viewPager2.isFakeDragging()) {
                    this.viewPager.endFakeDrag();
                }
            } catch (Exception e) {
            }
            fixedEvent.recycle();
        }
    }

    private static MotionEvent obtainOnePointerEvent(MotionEvent event) {
        return MotionEvent.obtain(event.getDownTime(), event.getEventTime(), event.getAction(), event.getX(), event.getY(), event.getMetaState());
    }

    /* access modifiers changed from: private */
    public static void settleViewPagerIfFinished(ViewPager pager, MotionEvent event) {
        if (event.getActionMasked() == 1 || event.getActionMasked() == 3) {
            try {
                pager.beginFakeDrag();
                if (pager.isFakeDragging()) {
                    pager.endFakeDrag();
                }
            } catch (Exception e) {
            }
        }
    }

    private static void transformToPagerEvent(MotionEvent event, View view, ViewPager pager) {
        Matrix matrix = tmpMatrix;
        matrix.reset();
        transformMatrixToPager(matrix, view, pager);
        event.transform(matrix);
    }

    private static void transformMatrixToPager(Matrix matrix, View view, ViewPager pager) {
        if (view.getParent() instanceof View) {
            View parent = (View) view.getParent();
            if (parent != pager) {
                transformMatrixToPager(matrix, parent, pager);
            }
            matrix.preTranslate((float) (-parent.getScrollX()), (float) (-parent.getScrollY()));
        }
        matrix.preTranslate((float) view.getLeft(), (float) view.getTop());
        matrix.preConcat(view.getMatrix());
    }
}
