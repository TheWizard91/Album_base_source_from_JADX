package com.alexvasilkov.gestures.internal.detectors;

import android.content.Context;
import android.view.MotionEvent;

public class RotationGestureDetector {
    private static final float ROTATION_SLOP = 5.0f;
    private float currAngle;
    private float focusX;
    private float focusY;
    private float initialAngle;
    private boolean isGestureAccepted;
    private boolean isInProgress;
    private final OnRotationGestureListener listener;
    private float prevAngle;

    public interface OnRotationGestureListener {
        boolean onRotate(RotationGestureDetector rotationGestureDetector);

        boolean onRotationBegin(RotationGestureDetector rotationGestureDetector);

        void onRotationEnd(RotationGestureDetector rotationGestureDetector);
    }

    public RotationGestureDetector(Context context, OnRotationGestureListener listener2) {
        this.listener = listener2;
    }

    public boolean onTouchEvent(MotionEvent event) {
        int actionMasked = event.getActionMasked();
        if (!(actionMasked == 0 || actionMasked == 1)) {
            if (actionMasked != 2) {
                if (actionMasked != 3) {
                    if (actionMasked != 5) {
                        if (actionMasked == 6 && event.getPointerCount() == 2) {
                            cancelRotation();
                        }
                    } else if (event.getPointerCount() == 2) {
                        float computeRotation = computeRotation(event);
                        this.currAngle = computeRotation;
                        this.prevAngle = computeRotation;
                        this.initialAngle = computeRotation;
                    }
                }
            } else if (event.getPointerCount() >= 2 && (!this.isInProgress || this.isGestureAccepted)) {
                this.currAngle = computeRotation(event);
                boolean isAccepted = false;
                this.focusX = (event.getX(1) + event.getX(0)) * 0.5f;
                this.focusY = (event.getY(1) + event.getY(0)) * 0.5f;
                boolean isAlreadyStarted = this.isInProgress;
                tryStartRotation();
                if (!isAlreadyStarted || processRotation()) {
                    isAccepted = true;
                }
                if (isAccepted) {
                    this.prevAngle = this.currAngle;
                }
            }
            return true;
        }
        cancelRotation();
        return true;
    }

    private void tryStartRotation() {
        if (!this.isInProgress && Math.abs(this.initialAngle - this.currAngle) >= ROTATION_SLOP) {
            this.isInProgress = true;
            this.isGestureAccepted = this.listener.onRotationBegin(this);
        }
    }

    private void cancelRotation() {
        if (this.isInProgress) {
            this.isInProgress = false;
            if (this.isGestureAccepted) {
                this.listener.onRotationEnd(this);
                this.isGestureAccepted = false;
            }
        }
    }

    private boolean processRotation() {
        return this.isInProgress && this.isGestureAccepted && this.listener.onRotate(this);
    }

    private float computeRotation(MotionEvent event) {
        return (float) Math.toDegrees(Math.atan2((double) (event.getY(1) - event.getY(0)), (double) (event.getX(1) - event.getX(0))));
    }

    public boolean isInProgress() {
        return this.isInProgress;
    }

    public float getFocusX() {
        return this.focusX;
    }

    public float getFocusY() {
        return this.focusY;
    }

    public float getRotationDelta() {
        return this.currAngle - this.prevAngle;
    }
}
