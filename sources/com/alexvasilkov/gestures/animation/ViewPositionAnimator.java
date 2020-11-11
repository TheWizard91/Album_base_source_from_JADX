package com.alexvasilkov.gestures.animation;

import android.graphics.Matrix;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.Log;
import android.view.View;
import com.alexvasilkov.gestures.GestureController;
import com.alexvasilkov.gestures.GestureControllerForPager;
import com.alexvasilkov.gestures.Settings;
import com.alexvasilkov.gestures.State;
import com.alexvasilkov.gestures.animation.ViewPositionHolder;
import com.alexvasilkov.gestures.internal.AnimationEngine;
import com.alexvasilkov.gestures.internal.GestureDebug;
import com.alexvasilkov.gestures.utils.FloatScroller;
import com.alexvasilkov.gestures.utils.GravityUtils;
import com.alexvasilkov.gestures.utils.MathUtils;
import com.alexvasilkov.gestures.views.interfaces.ClipBounds;
import com.alexvasilkov.gestures.views.interfaces.ClipView;
import com.alexvasilkov.gestures.views.interfaces.GestureView;
import java.util.ArrayList;
import java.util.List;

public class ViewPositionAnimator {
    private static final String TAG = "ViewPositionAnimator";
    private static final Matrix tmpMatrix = new Matrix();
    private static final Point tmpPoint = new Point();
    private static final float[] tmpPointArr = new float[2];
    private final AnimationEngine animationEngine;
    private final RectF clipRectTmp;
    private final RectF fromBoundsClip;
    private final RectF fromClip;
    private boolean fromNonePos;
    private float fromPivotX;
    private float fromPivotY;
    /* access modifiers changed from: private */
    public ViewPosition fromPos;
    private final ViewPositionHolder fromPosHolder;
    private final ViewPositionHolder.OnViewPositionChangeListener fromPositionListener;
    /* access modifiers changed from: private */
    public final State fromState = new State();
    private View fromView;
    /* access modifiers changed from: private */
    public boolean isActivated;
    private boolean isAnimating;
    private boolean isApplyingPosition;
    private boolean isApplyingPositionScheduled;
    private boolean isFromUpdated;
    private boolean isLeaving;
    private boolean isToUpdated;
    private boolean iteratingListeners;
    private final List<PositionUpdateListener> listeners = new ArrayList();
    private final List<PositionUpdateListener> listenersToRemove = new ArrayList();
    /* access modifiers changed from: private */
    public float position;
    /* access modifiers changed from: private */
    public final FloatScroller positionScroller = new FloatScroller();
    private final RectF toBoundsClip;
    private final RectF toClip;
    private final ClipBounds toClipBounds;
    private final ClipView toClipView;
    /* access modifiers changed from: private */
    public final GestureController toController;
    private float toPivotX;
    private float toPivotY;
    /* access modifiers changed from: private */
    public ViewPosition toPos;
    private final ViewPositionHolder toPosHolder;
    private float toPosition;
    /* access modifiers changed from: private */
    public final State toState = new State();
    private final Rect windowRect;

    public interface PositionUpdateListener {
        void onPositionUpdate(float f, boolean z);
    }

    public ViewPositionAnimator(GestureView to) {
        Rect rect = new Rect();
        this.windowRect = rect;
        this.fromClip = new RectF();
        this.toClip = new RectF();
        this.fromBoundsClip = new RectF();
        this.toBoundsClip = new RectF();
        this.clipRectTmp = new RectF();
        this.isActivated = false;
        this.toPosition = 1.0f;
        this.position = 0.0f;
        this.isLeaving = true;
        this.isAnimating = false;
        ViewPositionHolder viewPositionHolder = new ViewPositionHolder();
        this.fromPosHolder = viewPositionHolder;
        ViewPositionHolder viewPositionHolder2 = new ViewPositionHolder();
        this.toPosHolder = viewPositionHolder2;
        this.fromPositionListener = new ViewPositionHolder.OnViewPositionChangeListener() {
            public void onViewPositionChanged(ViewPosition position) {
                if (GestureDebug.isDebugAnimator()) {
                    Log.d(ViewPositionAnimator.TAG, "'From' view position updated: " + position.pack());
                }
                ViewPosition unused = ViewPositionAnimator.this.fromPos = position;
                ViewPositionAnimator.this.requestUpdateFromState();
                ViewPositionAnimator.this.applyCurrentPosition();
            }
        };
        if (to instanceof View) {
            View toView = (View) to;
            ClipBounds clipBounds = null;
            this.toClipView = to instanceof ClipView ? (ClipView) to : null;
            this.toClipBounds = to instanceof ClipBounds ? (ClipBounds) to : clipBounds;
            this.animationEngine = new LocalAnimationEngine(toView);
            toView.getWindowVisibleDisplayFrame(rect);
            GestureController controller = to.getController();
            this.toController = controller;
            controller.addOnStateChangeListener(new GestureController.OnStateChangeListener() {
                public void onStateChanged(State state) {
                    ViewPositionAnimator.this.toController.getStateController().applyZoomPatch(ViewPositionAnimator.this.fromState);
                    ViewPositionAnimator.this.toController.getStateController().applyZoomPatch(ViewPositionAnimator.this.toState);
                }

                public void onStateReset(State oldState, State newState) {
                    if (ViewPositionAnimator.this.isActivated) {
                        if (GestureDebug.isDebugAnimator()) {
                            Log.d(ViewPositionAnimator.TAG, "State reset in listener: " + newState);
                        }
                        ViewPositionAnimator.this.setToState(newState, 1.0f);
                        ViewPositionAnimator.this.applyCurrentPosition();
                    }
                }
            });
            viewPositionHolder2.init(toView, new ViewPositionHolder.OnViewPositionChangeListener() {
                public void onViewPositionChanged(ViewPosition position) {
                    if (GestureDebug.isDebugAnimator()) {
                        Log.d(ViewPositionAnimator.TAG, "'To' view position updated: " + position.pack());
                    }
                    ViewPosition unused = ViewPositionAnimator.this.toPos = position;
                    ViewPositionAnimator.this.requestUpdateToState();
                    ViewPositionAnimator.this.requestUpdateFromState();
                    ViewPositionAnimator.this.applyCurrentPosition();
                }
            });
            viewPositionHolder.pause(true);
            viewPositionHolder2.pause(true);
            return;
        }
        throw new IllegalArgumentException("Argument 'to' should be an instance of View");
    }

    public void enter(boolean withAnimation) {
        if (GestureDebug.isDebugAnimator()) {
            Log.d(TAG, "Entering from none position, with animation = " + withAnimation);
        }
        enterInternal(withAnimation);
        updateInternal();
    }

    public void enter(View from, boolean withAnimation) {
        if (GestureDebug.isDebugAnimator()) {
            Log.d(TAG, "Entering from view, with animation = " + withAnimation);
        }
        enterInternal(withAnimation);
        updateInternal(from);
    }

    public void enter(ViewPosition fromPos2, boolean withAnimation) {
        if (GestureDebug.isDebugAnimator()) {
            Log.d(TAG, "Entering from view position, with animation = " + withAnimation);
        }
        enterInternal(withAnimation);
        updateInternal(fromPos2);
    }

    public void update(View from) {
        if (GestureDebug.isDebugAnimator()) {
            Log.d(TAG, "Updating view");
        }
        updateInternal(from);
    }

    public void update(ViewPosition from) {
        if (GestureDebug.isDebugAnimator()) {
            Log.d(TAG, "Updating view position: " + from.pack());
        }
        updateInternal(from);
    }

    public void updateToNone() {
        if (GestureDebug.isDebugAnimator()) {
            Log.d(TAG, "Updating view to no specific position");
        }
        updateInternal();
    }

    public void exit(boolean withAnimation) {
        if (GestureDebug.isDebugAnimator()) {
            Log.d(TAG, "Exiting, with animation = " + withAnimation);
        }
        if (this.isActivated) {
            float f = 0.0f;
            if ((!this.isAnimating || this.position > this.toPosition) && this.position > 0.0f) {
                setToState(this.toController.getState(), this.position);
            }
            if (withAnimation) {
                f = this.position;
            }
            setState(f, true, withAnimation);
            return;
        }
        throw new IllegalStateException("You should call enter(...) before calling exit(...)");
    }

    private void enterInternal(boolean withAnimation) {
        this.isActivated = true;
        this.toController.updateState();
        float f = 1.0f;
        setToState(this.toController.getState(), 1.0f);
        if (withAnimation) {
            f = 0.0f;
        }
        setState(f, false, withAnimation);
    }

    private void updateInternal(View from) {
        cleanBeforeUpdateInternal();
        this.fromView = from;
        this.fromPosHolder.init(from, this.fromPositionListener);
        from.setVisibility(4);
    }

    private void updateInternal(ViewPosition from) {
        cleanBeforeUpdateInternal();
        this.fromPos = from;
        applyCurrentPosition();
    }

    private void updateInternal() {
        cleanBeforeUpdateInternal();
        this.fromNonePos = true;
        applyCurrentPosition();
    }

    private void cleanBeforeUpdateInternal() {
        if (this.isActivated) {
            cleanup();
            requestUpdateFromState();
            return;
        }
        throw new IllegalStateException("You should call enter(...) before calling update(...)");
    }

    private void cleanup() {
        if (GestureDebug.isDebugAnimator()) {
            Log.d(TAG, "Cleaning up");
        }
        View view = this.fromView;
        if (view != null) {
            view.setVisibility(0);
        }
        ClipView clipView = this.toClipView;
        if (clipView != null) {
            clipView.clipView((RectF) null, 0.0f);
        }
        this.fromPosHolder.clear();
        this.fromView = null;
        this.fromPos = null;
        this.fromNonePos = false;
        this.isToUpdated = false;
        this.isFromUpdated = false;
    }

    public void addPositionUpdateListener(PositionUpdateListener listener) {
        this.listeners.add(listener);
        this.listenersToRemove.remove(listener);
    }

    public void removePositionUpdateListener(PositionUpdateListener listener) {
        if (this.iteratingListeners) {
            this.listenersToRemove.add(listener);
        } else {
            this.listeners.remove(listener);
        }
    }

    private void ensurePositionUpdateListenersRemoved() {
        this.listeners.removeAll(this.listenersToRemove);
        this.listenersToRemove.clear();
    }

    @Deprecated
    public long getDuration() {
        return this.toController.getSettings().getAnimationsDuration();
    }

    @Deprecated
    public void setDuration(long duration) {
        this.toController.getSettings().setAnimationsDuration(duration);
    }

    public float getToPosition() {
        return this.toPosition;
    }

    public float getPosition() {
        return this.position;
    }

    @Deprecated
    public float getPositionState() {
        return this.position;
    }

    public boolean isLeaving() {
        return this.isLeaving;
    }

    public void setToState(State state, float position2) {
        if (position2 <= 0.0f) {
            throw new IllegalArgumentException("'To' position cannot be <= 0");
        } else if (position2 <= 1.0f) {
            if (GestureDebug.isDebugAnimator()) {
                Log.d(TAG, "State reset: " + state + " at " + position2);
            }
            this.toPosition = position2;
            this.toState.set(state);
            requestUpdateToState();
            requestUpdateFromState();
        } else {
            throw new IllegalArgumentException("'To' position cannot be > 1");
        }
    }

    public void setState(float pos, boolean leaving, boolean animate) {
        if (this.isActivated) {
            stopAnimation();
            float f = 0.0f;
            if (pos >= 0.0f) {
                f = pos > 1.0f ? 1.0f : pos;
            }
            this.position = f;
            this.isLeaving = leaving;
            if (animate) {
                startAnimationInternal();
            }
            applyCurrentPosition();
            return;
        }
        throw new IllegalStateException("You should call enter(...) before calling setState(...)");
    }

    /* access modifiers changed from: private */
    public void applyCurrentPosition() {
        if (this.isActivated) {
            if (this.isApplyingPosition) {
                this.isApplyingPositionScheduled = true;
                return;
            }
            this.isApplyingPosition = true;
            boolean paused = !this.isLeaving ? this.position == 1.0f : this.position == 0.0f;
            this.fromPosHolder.pause(paused);
            this.toPosHolder.pause(paused);
            if (!this.isToUpdated) {
                updateToState();
            }
            if (!this.isFromUpdated) {
                updateFromState();
            }
            if (GestureDebug.isDebugAnimator()) {
                Log.d(TAG, "Applying state: " + this.position + " / " + this.isLeaving + ", 'to' ready = " + this.isToUpdated + ", 'from' ready = " + this.isFromUpdated);
            }
            float f = this.position;
            float f2 = this.toPosition;
            boolean canUpdate = f < f2 || (this.isAnimating && f == f2);
            if (this.isToUpdated && this.isFromUpdated && canUpdate) {
                State state = this.toController.getState();
                MathUtils.interpolate(state, this.fromState, this.fromPivotX, this.fromPivotY, this.toState, this.toPivotX, this.toPivotY, this.position / this.toPosition);
                this.toController.updateState();
                float f3 = this.position;
                float f4 = this.toPosition;
                boolean skipClip = f3 >= f4 || (f3 == 0.0f && this.isLeaving);
                float clipPosition = f3 / f4;
                RectF rectF = null;
                if (this.toClipView != null) {
                    MathUtils.interpolate(this.clipRectTmp, this.fromClip, this.toClip, clipPosition);
                    this.toClipView.clipView(skipClip ? null : this.clipRectTmp, state.getRotation());
                }
                if (this.toClipBounds != null) {
                    MathUtils.interpolate(this.clipRectTmp, this.fromBoundsClip, this.toBoundsClip, clipPosition);
                    ClipBounds clipBounds = this.toClipBounds;
                    if (!skipClip) {
                        rectF = this.clipRectTmp;
                    }
                    clipBounds.clipBounds(rectF);
                }
            }
            this.iteratingListeners = true;
            int size = this.listeners.size();
            for (int i = 0; i < size && !this.isApplyingPositionScheduled; i++) {
                this.listeners.get(i).onPositionUpdate(this.position, this.isLeaving);
            }
            this.iteratingListeners = false;
            ensurePositionUpdateListenersRemoved();
            if (this.position == 0.0f && this.isLeaving) {
                cleanup();
                this.isActivated = false;
                this.toController.resetState();
            }
            this.isApplyingPosition = false;
            if (this.isApplyingPositionScheduled) {
                this.isApplyingPositionScheduled = false;
                applyCurrentPosition();
            }
        }
    }

    public boolean isAnimating() {
        return this.isAnimating;
    }

    private void startAnimationInternal() {
        float durationFraction;
        float f;
        long duration = this.toController.getSettings().getAnimationsDuration();
        float f2 = this.toPosition;
        float f3 = 1.0f;
        if (f2 == 1.0f) {
            durationFraction = this.isLeaving ? this.position : 1.0f - this.position;
        } else {
            if (this.isLeaving) {
                f = this.position;
            } else {
                f = 1.0f - this.position;
                f2 = 1.0f - f2;
            }
            durationFraction = f / f2;
        }
        this.positionScroller.setDuration((long) (((float) duration) * durationFraction));
        FloatScroller floatScroller = this.positionScroller;
        float f4 = this.position;
        if (this.isLeaving) {
            f3 = 0.0f;
        }
        floatScroller.startScroll(f4, f3);
        this.animationEngine.start();
        onAnimationStarted();
    }

    public void stopAnimation() {
        this.positionScroller.forceFinished();
        onAnimationStopped();
    }

    private void onAnimationStarted() {
        if (!this.isAnimating) {
            this.isAnimating = true;
            if (GestureDebug.isDebugAnimator()) {
                Log.d(TAG, "Animation started");
            }
            this.toController.getSettings().disableBounds().disableGestures();
            this.toController.stopAllAnimations();
            GestureController gestureController = this.toController;
            if (gestureController instanceof GestureControllerForPager) {
                ((GestureControllerForPager) gestureController).disableViewPager(true);
            }
        }
    }

    /* access modifiers changed from: private */
    public void onAnimationStopped() {
        if (this.isAnimating) {
            this.isAnimating = false;
            if (GestureDebug.isDebugAnimator()) {
                Log.d(TAG, "Animation stopped");
            }
            this.toController.getSettings().enableBounds().enableGestures();
            GestureController gestureController = this.toController;
            if (gestureController instanceof GestureControllerForPager) {
                ((GestureControllerForPager) gestureController).disableViewPager(false);
            }
            this.toController.animateKeepInBounds();
        }
    }

    /* access modifiers changed from: private */
    public void requestUpdateToState() {
        this.isToUpdated = false;
    }

    /* access modifiers changed from: private */
    public void requestUpdateFromState() {
        this.isFromUpdated = false;
    }

    private void updateToState() {
        if (!this.isToUpdated) {
            GestureController gestureController = this.toController;
            Settings settings = gestureController == null ? null : gestureController.getSettings();
            if (this.toPos != null && settings != null && settings.hasImageSize()) {
                State state = this.toState;
                Matrix matrix = tmpMatrix;
                state.get(matrix);
                this.toClip.set(0.0f, 0.0f, (float) settings.getImageW(), (float) settings.getImageH());
                float[] fArr = tmpPointArr;
                fArr[0] = this.toClip.centerX();
                fArr[1] = this.toClip.centerY();
                matrix.mapPoints(fArr);
                this.toPivotX = fArr[0];
                this.toPivotY = fArr[1];
                matrix.postRotate(-this.toState.getRotation(), this.toPivotX, this.toPivotY);
                matrix.mapRect(this.toClip);
                this.toClip.offset((float) (this.toPos.viewport.left - this.toPos.view.left), (float) (this.toPos.viewport.top - this.toPos.view.top));
                this.toBoundsClip.set((float) (this.windowRect.left - this.toPos.view.left), (float) (this.windowRect.top - this.toPos.view.top), (float) (this.windowRect.right - this.toPos.view.left), (float) (this.windowRect.bottom - this.toPos.view.top));
                this.isToUpdated = true;
                if (GestureDebug.isDebugAnimator()) {
                    Log.d(TAG, "'To' state updated");
                }
            }
        }
    }

    private void updateFromState() {
        if (!this.isFromUpdated) {
            GestureController gestureController = this.toController;
            Settings settings = gestureController == null ? null : gestureController.getSettings();
            if (!(!this.fromNonePos || settings == null || this.toPos == null)) {
                ViewPosition viewPosition = this.fromPos;
                if (viewPosition == null) {
                    viewPosition = ViewPosition.newInstance();
                }
                this.fromPos = viewPosition;
                Point point = tmpPoint;
                GravityUtils.getDefaultPivot(settings, point);
                point.offset(this.toPos.view.left, this.toPos.view.top);
                ViewPosition.apply(this.fromPos, point);
            }
            if (this.toPos != null && this.fromPos != null && settings != null && settings.hasImageSize()) {
                this.fromPivotX = (float) (this.fromPos.image.centerX() - this.toPos.viewport.left);
                this.fromPivotY = (float) (this.fromPos.image.centerY() - this.toPos.viewport.top);
                float imageWidth = (float) settings.getImageW();
                float imageHeight = (float) settings.getImageH();
                float zoomH = 1.0f;
                float zoomW = imageWidth == 0.0f ? 1.0f : ((float) this.fromPos.image.width()) / imageWidth;
                if (imageHeight != 0.0f) {
                    zoomH = ((float) this.fromPos.image.height()) / imageHeight;
                }
                float zoom = Math.max(zoomW, zoomH);
                this.fromState.set((((float) this.fromPos.image.centerX()) - ((imageWidth * 0.5f) * zoom)) - ((float) this.toPos.viewport.left), (((float) this.fromPos.image.centerY()) - ((0.5f * imageHeight) * zoom)) - ((float) this.toPos.viewport.top), zoom, 0.0f);
                this.fromClip.set(this.fromPos.viewport);
                this.fromClip.offset((float) (-this.toPos.view.left), (float) (-this.toPos.view.top));
                this.fromBoundsClip.set((float) (this.fromPos.visible.left - this.toPos.view.left), (float) (this.fromPos.visible.top - this.toPos.view.top), (float) (this.fromPos.visible.right - this.toPos.view.left), (float) (this.fromPos.visible.bottom - this.toPos.view.top));
                this.isFromUpdated = true;
                if (GestureDebug.isDebugAnimator()) {
                    Log.d(TAG, "'From' state updated");
                }
            }
        }
    }

    private class LocalAnimationEngine extends AnimationEngine {
        LocalAnimationEngine(View view) {
            super(view);
        }

        public boolean onStep() {
            if (ViewPositionAnimator.this.positionScroller.isFinished()) {
                return false;
            }
            ViewPositionAnimator.this.positionScroller.computeScroll();
            ViewPositionAnimator viewPositionAnimator = ViewPositionAnimator.this;
            float unused = viewPositionAnimator.position = viewPositionAnimator.positionScroller.getCurr();
            ViewPositionAnimator.this.applyCurrentPosition();
            if (!ViewPositionAnimator.this.positionScroller.isFinished()) {
                return true;
            }
            ViewPositionAnimator.this.onAnimationStopped();
            return true;
        }
    }
}
