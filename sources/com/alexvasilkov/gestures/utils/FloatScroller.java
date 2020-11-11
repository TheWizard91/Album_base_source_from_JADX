package com.alexvasilkov.gestures.utils;

import android.os.SystemClock;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Interpolator;

public class FloatScroller {
    private static final long DEFAULT_DURATION = 250;
    private float currValue;
    private long duration = DEFAULT_DURATION;
    private float finalValue;
    private boolean finished = true;
    private final Interpolator interpolator = new AccelerateDecelerateInterpolator();
    private long startRtc;
    private float startValue;

    public long getDuration() {
        return this.duration;
    }

    public void setDuration(long duration2) {
        this.duration = duration2;
    }

    public void forceFinished() {
        this.finished = true;
    }

    public void abortAnimation() {
        this.finished = true;
        this.currValue = this.finalValue;
    }

    public void startScroll(float startValue2, float finalValue2) {
        this.finished = false;
        this.startRtc = SystemClock.elapsedRealtime();
        this.startValue = startValue2;
        this.finalValue = finalValue2;
        this.currValue = startValue2;
    }

    public boolean computeScroll() {
        if (this.finished) {
            return false;
        }
        long elapsed = SystemClock.elapsedRealtime() - this.startRtc;
        long j = this.duration;
        if (elapsed >= j) {
            this.finished = true;
            this.currValue = this.finalValue;
            return false;
        }
        this.currValue = interpolate(this.startValue, this.finalValue, this.interpolator.getInterpolation(((float) elapsed) / ((float) j)));
        return true;
    }

    public boolean isFinished() {
        return this.finished;
    }

    public float getStart() {
        return this.startValue;
    }

    public float getFinal() {
        return this.finalValue;
    }

    public float getCurr() {
        return this.currValue;
    }

    private static float interpolate(float x1, float x2, float state) {
        return ((x2 - x1) * state) + x1;
    }
}
