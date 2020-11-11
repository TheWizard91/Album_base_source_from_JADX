package com.google.firebase.inappmessaging.display.internal;

import android.view.GestureDetector;
import android.view.MotionEvent;

public class OnSwipeUpListener extends GestureDetector.SimpleOnGestureListener {
    private static final int SWIPE_MAX_OFF_PATH = 250;
    private static final int SWIPE_MIN_DISTANCE = 120;
    private static final int SWIPE_THRESHOLD_VELOCITY = 200;

    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        if (Math.abs(e1.getX() - e2.getX()) <= 250.0f && e1.getY() - e2.getY() > 120.0f && Math.abs(velocityY) > 200.0f) {
            return onSwipeUp();
        }
        return false;
    }

    public boolean onSwipeUp() {
        return false;
    }
}
