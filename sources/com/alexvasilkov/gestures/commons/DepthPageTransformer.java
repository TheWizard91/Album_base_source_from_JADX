package com.alexvasilkov.gestures.commons;

import android.view.View;
import androidx.viewpager.widget.ViewPager;

public class DepthPageTransformer implements ViewPager.PageTransformer {
    private static final float MIN_SCALE = 0.75f;

    public void transformPage(View view, float position) {
        if (0.0f >= position || position >= 1.0f) {
            view.setAlpha(1.0f);
            view.setTranslationX(0.0f);
            view.setScaleX(1.0f);
            view.setScaleY(1.0f);
            return;
        }
        view.setAlpha(1.0f - position);
        view.setTranslationX(((float) (-view.getWidth())) * position);
        float scaleFactor = 1.0f - (0.25f * position);
        view.setScaleX(scaleFactor);
        view.setScaleY(scaleFactor);
    }
}
