package com.alexvasilkov.gestures.internal;

import android.os.SystemClock;
import android.util.Log;

class Fps {
    private static final long ERROR_TIME = 40;
    private static final String TAG = "GestureFps";
    private static final long WARNING_TIME = 20;
    private long animationStart;
    private long frameStart;
    private int framesCount;

    Fps() {
    }

    /* access modifiers changed from: package-private */
    public void start() {
        if (GestureDebug.isDebugFps()) {
            long uptimeMillis = SystemClock.uptimeMillis();
            this.frameStart = uptimeMillis;
            this.animationStart = uptimeMillis;
            this.framesCount = 0;
        }
    }

    /* access modifiers changed from: package-private */
    public void stop() {
        if (GestureDebug.isDebugFps() && this.framesCount > 0) {
            Log.d(TAG, "Average FPS: " + Math.round((((float) this.framesCount) * 1000.0f) / ((float) ((int) (SystemClock.uptimeMillis() - this.animationStart)))));
        }
    }

    /* access modifiers changed from: package-private */
    public void step() {
        if (GestureDebug.isDebugFps()) {
            long frameTime = SystemClock.uptimeMillis() - this.frameStart;
            if (frameTime > ERROR_TIME) {
                Log.e(TAG, "Frame time: " + frameTime);
            } else if (frameTime > WARNING_TIME) {
                Log.w(TAG, "Frame time: " + frameTime);
            }
            this.framesCount++;
            this.frameStart = SystemClock.uptimeMillis();
        }
    }
}
