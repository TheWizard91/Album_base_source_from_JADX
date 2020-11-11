package com.alexvasilkov.gestures.internal.detectors;

import android.content.Context;
import android.os.Build;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;

public class ScaleGestureDetectorFixed extends ScaleGestureDetector {
    private float currY;
    private float prevY;

    public ScaleGestureDetectorFixed(Context context, ScaleGestureDetector.OnScaleGestureListener listener) {
        super(context, listener);
        warmUpScaleDetector();
    }

    private void warmUpScaleDetector() {
        long time = System.currentTimeMillis();
        MotionEvent event = MotionEvent.obtain(time, time, 3, 0.0f, 0.0f, 0);
        onTouchEvent(event);
        event.recycle();
    }

    public boolean onTouchEvent(MotionEvent event) {
        boolean result = super.onTouchEvent(event);
        this.prevY = this.currY;
        this.currY = event.getY();
        if (event.getActionMasked() == 0) {
            this.prevY = event.getY();
        }
        return result;
    }

    private boolean isInDoubleTapMode() {
        return Build.VERSION.SDK_INT >= 19 && isQuickScaleEnabled() && getCurrentSpan() == getCurrentSpanY();
    }

    public float getScaleFactor() {
        float factor = super.getScaleFactor();
        if (!isInDoubleTapMode()) {
            return factor;
        }
        float f = this.currY;
        float f2 = this.prevY;
        if ((f <= f2 || factor <= 1.0f) && (f >= f2 || factor >= 1.0f)) {
            return 1.0f;
        }
        return Math.max(0.8f, Math.min(factor, 1.25f));
    }
}
