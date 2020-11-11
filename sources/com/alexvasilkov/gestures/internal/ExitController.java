package com.alexvasilkov.gestures.internal;

import android.graphics.Point;
import android.graphics.RectF;
import android.view.View;
import com.alexvasilkov.gestures.GestureController;
import com.alexvasilkov.gestures.GestureControllerForPager;
import com.alexvasilkov.gestures.Settings;
import com.alexvasilkov.gestures.State;
import com.alexvasilkov.gestures.StateController;
import com.alexvasilkov.gestures.animation.ViewPositionAnimator;
import com.alexvasilkov.gestures.utils.GravityUtils;
import com.alexvasilkov.gestures.utils.MathUtils;
import com.alexvasilkov.gestures.views.interfaces.AnimatorView;

public class ExitController {
    private static final float EXIT_THRESHOLD = 0.75f;
    private static final float MIN_EXIT_STATE = 0.01f;
    private static final float SCROLL_FACTOR = 0.5f;
    private static final float SCROLL_THRESHOLD = 30.0f;
    private static final float ZOOM_FACTOR = 0.75f;
    private static final float ZOOM_THRESHOLD = 0.75f;
    private static final RectF tmpArea = new RectF();
    private static final Point tmpPivot = new Point();
    private final AnimatorView animatorView;
    private final GestureController controller;
    private float exitState = 1.0f;
    private float initialY;
    private float initialZoom;
    private boolean isRotationInAction;
    private boolean isScrollDetected;
    private boolean isZoomDetected;
    private boolean isZoomInAction;
    private float scrollDirection;
    private final float scrollThresholdScaled;
    private boolean skipScrollDetection;
    private boolean skipZoomDetection;
    private float totalScrollX;
    private float totalScrollY;
    private float zoomAccumulator = 1.0f;

    public ExitController(View view, GestureController gestureController) {
        this.controller = gestureController;
        this.animatorView = view instanceof AnimatorView ? (AnimatorView) view : null;
        this.scrollThresholdScaled = UnitsUtils.toPixels(view.getContext(), 30.0f);
    }

    public boolean isExitDetected() {
        return this.isScrollDetected || this.isZoomDetected;
    }

    public void stopDetection() {
        if (isExitDetected()) {
            this.exitState = 1.0f;
            updateState();
            finishDetection();
        }
    }

    public void onUpOrCancel() {
        finishDetection();
    }

    public boolean onScroll(float dx, float dy) {
        if (!this.skipScrollDetection && !isExitDetected() && canDetectExit() && canDetectScroll() && !canScroll(dy)) {
            this.totalScrollX += dx;
            float f = this.totalScrollY + dy;
            this.totalScrollY = f;
            if (Math.abs(f) > this.scrollThresholdScaled) {
                this.isScrollDetected = true;
                this.initialY = this.controller.getState().getY();
                startDetection();
            } else if (Math.abs(this.totalScrollX) > this.scrollThresholdScaled) {
                this.skipScrollDetection = true;
            }
        }
        if (!this.isScrollDetected) {
            return isExitDetected();
        }
        if (this.scrollDirection == 0.0f) {
            this.scrollDirection = Math.signum(dy);
        }
        if (this.exitState < 0.75f && Math.signum(dy) == this.scrollDirection) {
            dy *= this.exitState / 0.75f;
        }
        float y = 1.0f - (((this.controller.getState().getY() + dy) - this.initialY) / ((this.scrollDirection * SCROLL_FACTOR) * ((float) Math.max(this.controller.getSettings().getMovementAreaW(), this.controller.getSettings().getMovementAreaH()))));
        this.exitState = y;
        float restrict = MathUtils.restrict(y, MIN_EXIT_STATE, 1.0f);
        this.exitState = restrict;
        if (restrict == 1.0f) {
            this.controller.getState().translateTo(this.controller.getState().getX(), this.initialY);
        } else {
            this.controller.getState().translateBy(0.0f, dy);
        }
        updateState();
        if (this.exitState == 1.0f) {
            finishDetection();
        }
        return true;
    }

    public boolean onFling() {
        return isExitDetected();
    }

    public void onScaleBegin() {
        this.isZoomInAction = true;
    }

    public void onScaleEnd() {
        this.isZoomInAction = false;
        this.skipZoomDetection = false;
        if (this.isZoomDetected) {
            finishDetection();
        }
    }

    public boolean onScale(float scaleFactor) {
        if (!canDetectZoom()) {
            this.skipZoomDetection = true;
        }
        if (!this.skipZoomDetection && !isExitDetected() && canDetectExit() && scaleFactor < 1.0f) {
            float f = this.zoomAccumulator * scaleFactor;
            this.zoomAccumulator = f;
            if (f < 0.75f) {
                this.isZoomDetected = true;
                this.initialZoom = this.controller.getState().getZoom();
                startDetection();
            }
        }
        if (this.isZoomDetected) {
            float zoom = (this.controller.getState().getZoom() * scaleFactor) / this.initialZoom;
            this.exitState = zoom;
            this.exitState = MathUtils.restrict(zoom, MIN_EXIT_STATE, 1.0f);
            Settings settings = this.controller.getSettings();
            Point point = tmpPivot;
            GravityUtils.getDefaultPivot(settings, point);
            if (this.exitState == 1.0f) {
                this.controller.getState().zoomTo(this.initialZoom, (float) point.x, (float) point.y);
            } else {
                this.controller.getState().zoomBy(((scaleFactor - 1.0f) * 0.75f) + 1.0f, (float) point.x, (float) point.y);
            }
            updateState();
            if (this.exitState == 1.0f) {
                finishDetection();
                return true;
            }
        }
        return isExitDetected();
    }

    public void applyZoomPatch() {
        this.initialZoom = this.controller.getStateController().applyZoomPatch(this.initialZoom);
    }

    public void onRotationBegin() {
        this.isRotationInAction = true;
    }

    public void onRotationEnd() {
        this.isRotationInAction = false;
    }

    public boolean onRotate() {
        return isExitDetected();
    }

    /* JADX WARNING: Code restructure failed: missing block: B:2:0x000c, code lost:
        r0 = r1.animatorView;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private boolean canDetectExit() {
        /*
            r1 = this;
            com.alexvasilkov.gestures.GestureController r0 = r1.controller
            com.alexvasilkov.gestures.Settings r0 = r0.getSettings()
            boolean r0 = r0.isExitEnabled()
            if (r0 == 0) goto L_0x001c
            com.alexvasilkov.gestures.views.interfaces.AnimatorView r0 = r1.animatorView
            if (r0 == 0) goto L_0x001c
            com.alexvasilkov.gestures.animation.ViewPositionAnimator r0 = r0.getPositionAnimator()
            boolean r0 = r0.isLeaving()
            if (r0 != 0) goto L_0x001c
            r0 = 1
            goto L_0x001d
        L_0x001c:
            r0 = 0
        L_0x001d:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alexvasilkov.gestures.internal.ExitController.canDetectExit():boolean");
    }

    private boolean canDetectScroll() {
        Settings.ExitType exitType = this.controller.getSettings().getExitType();
        return (exitType == Settings.ExitType.ALL || exitType == Settings.ExitType.SCROLL) && !this.isZoomInAction && !this.isRotationInAction && isZoomedOut();
    }

    private boolean canDetectZoom() {
        Settings.ExitType exitType = this.controller.getSettings().getExitType();
        return (exitType == Settings.ExitType.ALL || exitType == Settings.ExitType.ZOOM) && !this.isRotationInAction && isZoomedOut();
    }

    private boolean canScroll(float dy) {
        if (!this.controller.getSettings().isRestrictBounds()) {
            return true;
        }
        State state = this.controller.getState();
        StateController stateController = this.controller.getStateController();
        RectF rectF = tmpArea;
        stateController.getMovementArea(state, rectF);
        if (dy > 0.0f && ((float) State.compare(state.getY(), rectF.bottom)) < 0.0f) {
            return true;
        }
        if (dy >= 0.0f || ((float) State.compare(state.getY(), rectF.top)) <= 0.0f) {
            return false;
        }
        return true;
    }

    private boolean isZoomedOut() {
        State state = this.controller.getState();
        return State.compare(state.getZoom(), this.controller.getStateController().getMinZoom(state)) <= 0;
    }

    private void startDetection() {
        this.controller.getSettings().disableBounds();
        GestureController gestureController = this.controller;
        if (gestureController instanceof GestureControllerForPager) {
            ((GestureControllerForPager) gestureController).disableViewPager(true);
        }
    }

    private void finishDetection() {
        if (isExitDetected()) {
            GestureController gestureController = this.controller;
            if (gestureController instanceof GestureControllerForPager) {
                ((GestureControllerForPager) gestureController).disableViewPager(false);
            }
            this.controller.getSettings().enableBounds();
            ViewPositionAnimator animator = this.animatorView.getPositionAnimator();
            if (!animator.isAnimating() && canDetectExit()) {
                float position = animator.getPosition();
                if (position < 0.75f) {
                    animator.exit(true);
                } else {
                    float y = this.controller.getState().getY();
                    float zoom = this.controller.getState().getZoom();
                    boolean isScrolledBack = this.isScrollDetected && State.equals(y, this.initialY);
                    boolean isZoomedBack = this.isZoomDetected && State.equals(zoom, this.initialZoom);
                    if (position < 1.0f) {
                        animator.setState(position, false, true);
                        if (!isScrolledBack && !isZoomedBack) {
                            this.controller.getSettings().enableBounds();
                            this.controller.animateKeepInBounds();
                            this.controller.getSettings().disableBounds();
                        }
                    }
                }
            }
        }
        this.isScrollDetected = false;
        this.isZoomDetected = false;
        this.skipScrollDetection = false;
        this.exitState = 1.0f;
        this.scrollDirection = 0.0f;
        this.totalScrollX = 0.0f;
        this.totalScrollY = 0.0f;
        this.zoomAccumulator = 1.0f;
    }

    private void updateState() {
        if (canDetectExit()) {
            this.animatorView.getPositionAnimator().setToState(this.controller.getState(), this.exitState);
            this.animatorView.getPositionAnimator().setState(this.exitState, false, false);
        }
    }
}
