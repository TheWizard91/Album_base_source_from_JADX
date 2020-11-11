package com.alexvasilkov.gestures;

import android.content.Context;
import android.graphics.PointF;
import android.graphics.RectF;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewParent;
import android.widget.OverScroller;
import com.alexvasilkov.gestures.internal.AnimationEngine;
import com.alexvasilkov.gestures.internal.ExitController;
import com.alexvasilkov.gestures.internal.MovementBounds;
import com.alexvasilkov.gestures.internal.detectors.RotationGestureDetector;
import com.alexvasilkov.gestures.internal.detectors.ScaleGestureDetectorFixed;
import com.alexvasilkov.gestures.utils.FloatScroller;
import com.alexvasilkov.gestures.utils.MathUtils;
import java.util.ArrayList;
import java.util.List;

public class GestureController implements View.OnTouchListener {
    private static final float FLING_COEFFICIENT = 0.9f;
    private static final float[] tmpPointArr = new float[2];
    private static final PointF tmpPointF = new PointF();
    private static final RectF tmpRectF = new RectF();
    private final AnimationEngine animationEngine;
    /* access modifiers changed from: private */
    public float endPivotX = Float.NaN;
    /* access modifiers changed from: private */
    public float endPivotY = Float.NaN;
    private final ExitController exitController;
    private final MovementBounds flingBounds;
    /* access modifiers changed from: private */
    public final OverScroller flingScroller;
    private final GestureDetector gestureDetector;
    private OnGestureListener gestureListener;
    private boolean isAnimatingInBounds;
    private boolean isInterceptTouchCalled;
    private boolean isInterceptTouchDisallowed;
    private boolean isRestrictRotationRequested;
    private boolean isRestrictZoomRequested;
    private boolean isRotationDetected;
    private boolean isScaleDetected;
    private boolean isScrollDetected;
    private boolean isStateChangedDuringTouch;
    private final int maxVelocity;
    private final int minVelocity;
    /* access modifiers changed from: private */
    public float pivotX = Float.NaN;
    /* access modifiers changed from: private */
    public float pivotY = Float.NaN;
    private final State prevState = new State();
    private final RotationGestureDetector rotateDetector;
    private final ScaleGestureDetector scaleDetector;
    private final Settings settings;
    private OnStateSourceChangeListener sourceListener;
    /* access modifiers changed from: private */
    public final State state = new State();
    private final StateController stateController;
    /* access modifiers changed from: private */
    public final State stateEnd = new State();
    private final List<OnStateChangeListener> stateListeners = new ArrayList();
    /* access modifiers changed from: private */
    public final FloatScroller stateScroller;
    private StateSource stateSource = StateSource.NONE;
    /* access modifiers changed from: private */
    public final State stateStart = new State();
    private final View targetView;
    private final int touchSlop;

    public interface OnGestureListener {
        boolean onDoubleTap(MotionEvent motionEvent);

        void onDown(MotionEvent motionEvent);

        void onLongPress(MotionEvent motionEvent);

        boolean onSingleTapConfirmed(MotionEvent motionEvent);

        boolean onSingleTapUp(MotionEvent motionEvent);

        void onUpOrCancel(MotionEvent motionEvent);
    }

    public interface OnStateChangeListener {
        void onStateChanged(State state);

        void onStateReset(State state, State state2);
    }

    public interface OnStateSourceChangeListener {
        void onStateSourceChanged(StateSource stateSource);
    }

    public enum StateSource {
        NONE,
        USER,
        ANIMATION
    }

    public GestureController(View view) {
        Context context = view.getContext();
        this.targetView = view;
        Settings settings2 = new Settings();
        this.settings = settings2;
        this.stateController = new StateController(settings2);
        this.animationEngine = new LocalAnimationEngine(view);
        InternalGesturesListener internalListener = new InternalGesturesListener();
        this.gestureDetector = new GestureDetector(context, internalListener);
        this.scaleDetector = new ScaleGestureDetectorFixed(context, internalListener);
        this.rotateDetector = new RotationGestureDetector(context, internalListener);
        this.exitController = new ExitController(view, this);
        this.flingScroller = new OverScroller(context);
        this.stateScroller = new FloatScroller();
        this.flingBounds = new MovementBounds(settings2);
        ViewConfiguration configuration = ViewConfiguration.get(context);
        this.touchSlop = configuration.getScaledTouchSlop();
        this.minVelocity = configuration.getScaledMinimumFlingVelocity();
        this.maxVelocity = configuration.getScaledMaximumFlingVelocity();
    }

    public void setOnGesturesListener(OnGestureListener listener) {
        this.gestureListener = listener;
    }

    public void setOnStateSourceChangeListener(OnStateSourceChangeListener listener) {
        this.sourceListener = listener;
    }

    public void addOnStateChangeListener(OnStateChangeListener listener) {
        this.stateListeners.add(listener);
    }

    public void removeOnStateChangeListener(OnStateChangeListener listener) {
        this.stateListeners.remove(listener);
    }

    @Deprecated
    public void setLongPressEnabled(boolean enabled) {
        this.targetView.setLongClickable(true);
    }

    public Settings getSettings() {
        return this.settings;
    }

    public State getState() {
        return this.state;
    }

    public StateController getStateController() {
        return this.stateController;
    }

    public void updateState() {
        this.stateController.applyZoomPatch(this.state);
        this.stateController.applyZoomPatch(this.prevState);
        this.stateController.applyZoomPatch(this.stateStart);
        this.stateController.applyZoomPatch(this.stateEnd);
        this.exitController.applyZoomPatch();
        if (this.stateController.updateState(this.state)) {
            notifyStateReset();
        } else {
            notifyStateUpdated();
        }
    }

    public void resetState() {
        stopAllAnimations();
        if (this.stateController.resetState(this.state)) {
            notifyStateReset();
        } else {
            notifyStateUpdated();
        }
    }

    public void setPivot(float pivotX2, float pivotY2) {
        this.pivotX = pivotX2;
        this.pivotY = pivotY2;
    }

    public boolean animateKeepInBounds() {
        return animateStateTo(this.state, true);
    }

    public boolean animateStateTo(State endState) {
        return animateStateTo(endState, true);
    }

    private boolean animateStateTo(State endState, boolean keepInBounds) {
        if (endState == null) {
            return false;
        }
        State endStateRestricted = null;
        if (keepInBounds) {
            endStateRestricted = this.stateController.restrictStateBoundsCopy(endState, this.prevState, this.pivotX, this.pivotY, false, false, true);
        }
        if (endStateRestricted == null) {
            endStateRestricted = endState;
        }
        if (endStateRestricted.equals(this.state)) {
            return false;
        }
        stopAllAnimations();
        this.isAnimatingInBounds = keepInBounds;
        this.stateStart.set(this.state);
        this.stateEnd.set(endStateRestricted);
        if (!Float.isNaN(this.pivotX) && !Float.isNaN(this.pivotY)) {
            float[] fArr = tmpPointArr;
            fArr[0] = this.pivotX;
            fArr[1] = this.pivotY;
            MathUtils.computeNewPosition(fArr, this.stateStart, this.stateEnd);
            this.endPivotX = fArr[0];
            this.endPivotY = fArr[1];
        }
        this.stateScroller.setDuration(this.settings.getAnimationsDuration());
        this.stateScroller.startScroll(0.0f, 1.0f);
        this.animationEngine.start();
        notifyStateSourceChanged();
        return true;
    }

    public boolean isAnimatingState() {
        return !this.stateScroller.isFinished();
    }

    public boolean isAnimatingFling() {
        return !this.flingScroller.isFinished();
    }

    public boolean isAnimating() {
        return isAnimatingState() || isAnimatingFling();
    }

    public void stopStateAnimation() {
        if (isAnimatingState()) {
            this.stateScroller.forceFinished();
            onStateAnimationFinished(true);
        }
    }

    public void stopFlingAnimation() {
        if (isAnimatingFling()) {
            this.flingScroller.forceFinished(true);
            onFlingAnimationFinished(true);
        }
    }

    public void stopAllAnimations() {
        stopStateAnimation();
        stopFlingAnimation();
    }

    /* access modifiers changed from: protected */
    public void onStateAnimationFinished(boolean forced) {
        this.isAnimatingInBounds = false;
        this.pivotX = Float.NaN;
        this.pivotY = Float.NaN;
        notifyStateSourceChanged();
    }

    /* access modifiers changed from: protected */
    public void onFlingAnimationFinished(boolean forced) {
        if (!forced) {
            animateKeepInBounds();
        }
        notifyStateSourceChanged();
    }

    /* access modifiers changed from: protected */
    public void notifyStateUpdated() {
        this.prevState.set(this.state);
        for (OnStateChangeListener listener : this.stateListeners) {
            listener.onStateChanged(this.state);
        }
    }

    /* access modifiers changed from: protected */
    public void notifyStateReset() {
        this.exitController.stopDetection();
        for (OnStateChangeListener listener : this.stateListeners) {
            listener.onStateReset(this.prevState, this.state);
        }
        notifyStateUpdated();
    }

    private void notifyStateSourceChanged() {
        StateSource type = StateSource.NONE;
        if (isAnimating()) {
            type = StateSource.ANIMATION;
        } else if (this.isScrollDetected || this.isScaleDetected || this.isRotationDetected) {
            type = StateSource.USER;
        }
        if (this.stateSource != type) {
            this.stateSource = type;
            OnStateSourceChangeListener onStateSourceChangeListener = this.sourceListener;
            if (onStateSourceChangeListener != null) {
                onStateSourceChangeListener.onStateSourceChanged(type);
            }
        }
    }

    public boolean onInterceptTouch(View view, MotionEvent event) {
        this.isInterceptTouchCalled = true;
        return onTouchInternal(view, event);
    }

    public boolean onTouch(View view, MotionEvent event) {
        if (!this.isInterceptTouchCalled) {
            onTouchInternal(view, event);
        }
        this.isInterceptTouchCalled = false;
        return this.settings.isEnabled();
    }

    /* access modifiers changed from: protected */
    public boolean onTouchInternal(View view, MotionEvent event) {
        MotionEvent viewportEvent = MotionEvent.obtain(event);
        viewportEvent.offsetLocation((float) (-view.getPaddingLeft()), (float) (-view.getPaddingTop()));
        this.gestureDetector.setIsLongpressEnabled(view.isLongClickable());
        boolean result = this.gestureDetector.onTouchEvent(viewportEvent);
        this.scaleDetector.onTouchEvent(viewportEvent);
        this.rotateDetector.onTouchEvent(viewportEvent);
        boolean result2 = result || this.isScaleDetected || this.isRotationDetected;
        notifyStateSourceChanged();
        if (this.exitController.isExitDetected() && !this.state.equals(this.prevState)) {
            notifyStateUpdated();
        }
        if (this.isStateChangedDuringTouch) {
            this.isStateChangedDuringTouch = false;
            this.stateController.restrictStateBounds(this.state, this.prevState, this.pivotX, this.pivotY, true, true, false);
            if (!this.state.equals(this.prevState)) {
                notifyStateUpdated();
            }
        }
        if (this.isRestrictZoomRequested || this.isRestrictRotationRequested) {
            this.isRestrictZoomRequested = false;
            this.isRestrictRotationRequested = false;
            if (!this.exitController.isExitDetected()) {
                animateStateTo(this.stateController.restrictStateBoundsCopy(this.state, this.prevState, this.pivotX, this.pivotY, true, false, true), false);
            }
        }
        if (viewportEvent.getActionMasked() == 1 || viewportEvent.getActionMasked() == 3) {
            onUpOrCancel(viewportEvent);
            notifyStateSourceChanged();
        }
        if (!this.isInterceptTouchDisallowed && shouldDisallowInterceptTouch(viewportEvent)) {
            this.isInterceptTouchDisallowed = true;
            ViewParent parent = view.getParent();
            if (parent != null) {
                parent.requestDisallowInterceptTouchEvent(true);
            }
        }
        viewportEvent.recycle();
        return result2;
    }

    /* access modifiers changed from: protected */
    public boolean shouldDisallowInterceptTouch(MotionEvent event) {
        if (this.exitController.isExitDetected()) {
            return true;
        }
        int actionMasked = event.getActionMasked();
        if (actionMasked == 0 || actionMasked == 2) {
            StateController stateController2 = this.stateController;
            State state2 = this.state;
            RectF rectF = tmpRectF;
            stateController2.getMovementArea(state2, rectF);
            boolean isPannable = State.compare(rectF.width(), 0.0f) > 0 || State.compare(rectF.height(), 0.0f) > 0;
            if (!this.settings.isPanEnabled() || (!isPannable && this.settings.isRestrictBounds())) {
                return false;
            }
            return true;
        } else if (actionMasked == 5) {
            if (this.settings.isZoomEnabled() || this.settings.isRotationEnabled()) {
                return true;
            }
            return false;
        }
        return false;
    }

    /* access modifiers changed from: protected */
    public boolean onDown(MotionEvent event) {
        this.isInterceptTouchDisallowed = false;
        stopFlingAnimation();
        OnGestureListener onGestureListener = this.gestureListener;
        if (onGestureListener != null) {
            onGestureListener.onDown(event);
        }
        return false;
    }

    /* access modifiers changed from: protected */
    public void onUpOrCancel(MotionEvent event) {
        this.isScrollDetected = false;
        this.isScaleDetected = false;
        this.isRotationDetected = false;
        this.exitController.onUpOrCancel();
        if (!isAnimatingFling() && !this.isAnimatingInBounds) {
            animateKeepInBounds();
        }
        OnGestureListener onGestureListener = this.gestureListener;
        if (onGestureListener != null) {
            onGestureListener.onUpOrCancel(event);
        }
    }

    /* access modifiers changed from: protected */
    public boolean onSingleTapUp(MotionEvent event) {
        if (!this.settings.isDoubleTapEnabled()) {
            this.targetView.performClick();
        }
        OnGestureListener onGestureListener = this.gestureListener;
        return onGestureListener != null && onGestureListener.onSingleTapUp(event);
    }

    /* access modifiers changed from: protected */
    public void onLongPress(MotionEvent event) {
        if (this.settings.isEnabled()) {
            this.targetView.performLongClick();
            OnGestureListener onGestureListener = this.gestureListener;
            if (onGestureListener != null) {
                onGestureListener.onLongPress(event);
            }
        }
    }

    /* access modifiers changed from: protected */
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float dx, float dy) {
        if (!this.settings.isPanEnabled() || isAnimatingState()) {
            return false;
        }
        if (this.exitController.onScroll(-dx, -dy)) {
            return true;
        }
        if (!this.isScrollDetected) {
            boolean z = Math.abs(e2.getX() - e1.getX()) > ((float) this.touchSlop) || Math.abs(e2.getY() - e1.getY()) > ((float) this.touchSlop);
            this.isScrollDetected = z;
            if (z) {
                return false;
            }
        }
        if (this.isScrollDetected) {
            this.state.translateBy(-dx, -dy);
            this.isStateChangedDuringTouch = true;
        }
        return this.isScrollDetected;
    }

    /* access modifiers changed from: protected */
    public boolean onFling(MotionEvent e1, MotionEvent e2, float vx, float vy) {
        if (!this.settings.isPanEnabled() || !this.settings.isFlingEnabled() || isAnimatingState()) {
            return false;
        }
        if (this.exitController.onFling()) {
            return true;
        }
        stopFlingAnimation();
        this.flingBounds.set(this.state).extend(this.state.getX(), this.state.getY());
        this.flingScroller.fling(Math.round(this.state.getX()), Math.round(this.state.getY()), limitFlingVelocity(vx * FLING_COEFFICIENT), limitFlingVelocity(FLING_COEFFICIENT * vy), Integer.MIN_VALUE, Integer.MAX_VALUE, Integer.MIN_VALUE, Integer.MAX_VALUE);
        this.animationEngine.start();
        notifyStateSourceChanged();
        return true;
    }

    private int limitFlingVelocity(float velocity) {
        if (Math.abs(velocity) < ((float) this.minVelocity)) {
            return 0;
        }
        if (Math.abs(velocity) >= ((float) this.maxVelocity)) {
            return ((int) Math.signum(velocity)) * this.maxVelocity;
        }
        return Math.round(velocity);
    }

    /* access modifiers changed from: protected */
    public boolean onFlingScroll(int dx, int dy) {
        float prevX = this.state.getX();
        float prevY = this.state.getY();
        float toX = ((float) dx) + prevX;
        float toY = ((float) dy) + prevY;
        if (this.settings.isRestrictBounds()) {
            MovementBounds movementBounds = this.flingBounds;
            PointF pointF = tmpPointF;
            movementBounds.restrict(toX, toY, pointF);
            toX = pointF.x;
            toY = pointF.y;
        }
        this.state.translateTo(toX, toY);
        return !State.equals(prevX, toX) || !State.equals(prevY, toY);
    }

    /* access modifiers changed from: protected */
    public boolean onSingleTapConfirmed(MotionEvent event) {
        if (this.settings.isDoubleTapEnabled()) {
            this.targetView.performClick();
        }
        OnGestureListener onGestureListener = this.gestureListener;
        return onGestureListener != null && onGestureListener.onSingleTapConfirmed(event);
    }

    /* access modifiers changed from: protected */
    public boolean onDoubleTapEvent(MotionEvent event) {
        if (!this.settings.isDoubleTapEnabled() || event.getActionMasked() != 1 || this.isScaleDetected) {
            return false;
        }
        OnGestureListener onGestureListener = this.gestureListener;
        if (onGestureListener != null && onGestureListener.onDoubleTap(event)) {
            return true;
        }
        animateStateTo(this.stateController.toggleMinMaxZoom(this.state, event.getX(), event.getY()));
        return true;
    }

    /* access modifiers changed from: protected */
    public boolean onScaleBegin(ScaleGestureDetector detector) {
        boolean isZoomEnabled = this.settings.isZoomEnabled();
        this.isScaleDetected = isZoomEnabled;
        if (isZoomEnabled) {
            this.exitController.onScaleBegin();
        }
        return this.isScaleDetected;
    }

    /* access modifiers changed from: protected */
    public boolean onScale(ScaleGestureDetector detector) {
        if (!this.settings.isZoomEnabled() || isAnimatingState()) {
            return false;
        }
        float scaleFactor = detector.getScaleFactor();
        if (this.exitController.onScale(scaleFactor)) {
            return true;
        }
        this.pivotX = detector.getFocusX();
        float focusY = detector.getFocusY();
        this.pivotY = focusY;
        this.state.zoomBy(scaleFactor, this.pivotX, focusY);
        this.isStateChangedDuringTouch = true;
        return true;
    }

    /* access modifiers changed from: protected */
    public void onScaleEnd(ScaleGestureDetector detector) {
        if (this.isScaleDetected) {
            this.exitController.onScaleEnd();
        }
        this.isScaleDetected = false;
        this.isRestrictZoomRequested = true;
    }

    /* access modifiers changed from: protected */
    public boolean onRotationBegin(RotationGestureDetector detector) {
        boolean isRotationEnabled = this.settings.isRotationEnabled();
        this.isRotationDetected = isRotationEnabled;
        if (isRotationEnabled) {
            this.exitController.onRotationBegin();
        }
        return this.isRotationDetected;
    }

    /* access modifiers changed from: protected */
    public boolean onRotate(RotationGestureDetector detector) {
        if (!this.settings.isRotationEnabled() || isAnimatingState()) {
            return false;
        }
        if (this.exitController.onRotate()) {
            return true;
        }
        this.pivotX = detector.getFocusX();
        this.pivotY = detector.getFocusY();
        this.state.rotateBy(detector.getRotationDelta(), this.pivotX, this.pivotY);
        this.isStateChangedDuringTouch = true;
        return true;
    }

    /* access modifiers changed from: protected */
    public void onRotationEnd(RotationGestureDetector detector) {
        if (this.isRotationDetected) {
            this.exitController.onRotationEnd();
        }
        this.isRotationDetected = false;
        this.isRestrictRotationRequested = true;
    }

    private class LocalAnimationEngine extends AnimationEngine {
        LocalAnimationEngine(View view) {
            super(view);
        }

        public boolean onStep() {
            boolean shouldProceed = false;
            if (GestureController.this.isAnimatingFling()) {
                int prevX = GestureController.this.flingScroller.getCurrX();
                int prevY = GestureController.this.flingScroller.getCurrY();
                if (GestureController.this.flingScroller.computeScrollOffset()) {
                    if (!GestureController.this.onFlingScroll(GestureController.this.flingScroller.getCurrX() - prevX, GestureController.this.flingScroller.getCurrY() - prevY)) {
                        GestureController.this.stopFlingAnimation();
                    }
                    shouldProceed = true;
                }
                if (!GestureController.this.isAnimatingFling()) {
                    GestureController.this.onFlingAnimationFinished(false);
                }
            }
            if (GestureController.this.isAnimatingState()) {
                GestureController.this.stateScroller.computeScroll();
                float factor = GestureController.this.stateScroller.getCurr();
                if (Float.isNaN(GestureController.this.pivotX) || Float.isNaN(GestureController.this.pivotY) || Float.isNaN(GestureController.this.endPivotX) || Float.isNaN(GestureController.this.endPivotY)) {
                    MathUtils.interpolate(GestureController.this.state, GestureController.this.stateStart, GestureController.this.stateEnd, factor);
                } else {
                    MathUtils.interpolate(GestureController.this.state, GestureController.this.stateStart, GestureController.this.pivotX, GestureController.this.pivotY, GestureController.this.stateEnd, GestureController.this.endPivotX, GestureController.this.endPivotY, factor);
                }
                shouldProceed = true;
                if (!GestureController.this.isAnimatingState()) {
                    GestureController.this.onStateAnimationFinished(false);
                }
            }
            if (shouldProceed) {
                GestureController.this.notifyStateUpdated();
            }
            return shouldProceed;
        }
    }

    public static class SimpleOnGestureListener implements OnGestureListener {
        public void onDown(MotionEvent event) {
        }

        public void onUpOrCancel(MotionEvent event) {
        }

        public boolean onSingleTapUp(MotionEvent event) {
            return false;
        }

        public boolean onSingleTapConfirmed(MotionEvent event) {
            return false;
        }

        public void onLongPress(MotionEvent event) {
        }

        public boolean onDoubleTap(MotionEvent event) {
            return false;
        }
    }

    private class InternalGesturesListener implements GestureDetector.OnGestureListener, GestureDetector.OnDoubleTapListener, ScaleGestureDetector.OnScaleGestureListener, RotationGestureDetector.OnRotationGestureListener {
        private InternalGesturesListener() {
        }

        public boolean onSingleTapConfirmed(MotionEvent event) {
            return GestureController.this.onSingleTapConfirmed(event);
        }

        public boolean onDoubleTap(MotionEvent event) {
            return false;
        }

        public boolean onDoubleTapEvent(MotionEvent event) {
            return GestureController.this.onDoubleTapEvent(event);
        }

        public boolean onDown(MotionEvent event) {
            return GestureController.this.onDown(event);
        }

        public void onShowPress(MotionEvent event) {
        }

        public boolean onSingleTapUp(MotionEvent event) {
            return GestureController.this.onSingleTapUp(event);
        }

        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            return GestureController.this.onScroll(e1, e2, distanceX, distanceY);
        }

        public void onLongPress(MotionEvent event) {
            GestureController.this.onLongPress(event);
        }

        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            return GestureController.this.onFling(e1, e2, velocityX, velocityY);
        }

        public boolean onRotate(RotationGestureDetector detector) {
            return GestureController.this.onRotate(detector);
        }

        public boolean onRotationBegin(RotationGestureDetector detector) {
            return GestureController.this.onRotationBegin(detector);
        }

        public void onRotationEnd(RotationGestureDetector detector) {
            GestureController.this.onRotationEnd(detector);
        }

        public boolean onScale(ScaleGestureDetector detector) {
            return GestureController.this.onScale(detector);
        }

        public boolean onScaleBegin(ScaleGestureDetector detector) {
            return GestureController.this.onScaleBegin(detector);
        }

        public void onScaleEnd(ScaleGestureDetector detector) {
            GestureController.this.onScaleEnd(detector);
        }
    }
}
