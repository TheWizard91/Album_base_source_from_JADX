package com.alexvasilkov.gestures.internal;

import android.os.Build;
import android.view.View;

public abstract class AnimationEngine implements Runnable {
    private static final long FRAME_TIME = 10;
    private final Fps fps;
    private final View view;

    public abstract boolean onStep();

    public AnimationEngine(View view2) {
        this.view = view2;
        this.fps = GestureDebug.isDebugFps() ? new Fps() : null;
    }

    public final void run() {
        boolean continueAnimation = onStep();
        Fps fps2 = this.fps;
        if (fps2 != null) {
            fps2.step();
            if (!continueAnimation) {
                this.fps.stop();
            }
        }
        if (continueAnimation) {
            scheduleNextStep();
        }
    }

    private void scheduleNextStep() {
        this.view.removeCallbacks(this);
        if (Build.VERSION.SDK_INT >= 16) {
            this.view.postOnAnimationDelayed(this, FRAME_TIME);
        } else {
            this.view.postDelayed(this, FRAME_TIME);
        }
    }

    public void start() {
        Fps fps2 = this.fps;
        if (fps2 != null) {
            fps2.start();
        }
        scheduleNextStep();
    }
}
