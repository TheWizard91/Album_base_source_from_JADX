package com.stfalcon.frescoimageviewer;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.view.View;
import android.view.ViewConfiguration;

final class AnimationUtils {
    private AnimationUtils() {
        throw new AssertionError();
    }

    static void animateVisibility(final View view) {
        boolean isVisible = view.getVisibility() == 0;
        float to = 1.0f;
        float from = isVisible ? 1.0f : 0.0f;
        if (isVisible) {
            to = 0.0f;
        }
        ObjectAnimator animation = ObjectAnimator.ofFloat(view, "alpha", new float[]{from, to});
        animation.setDuration((long) ViewConfiguration.getDoubleTapTimeout());
        if (isVisible) {
            animation.addListener(new AnimatorListenerAdapter() {
                public void onAnimationEnd(Animator animation) {
                    view.setVisibility(8);
                }
            });
        } else {
            view.setVisibility(0);
        }
        animation.start();
    }
}
