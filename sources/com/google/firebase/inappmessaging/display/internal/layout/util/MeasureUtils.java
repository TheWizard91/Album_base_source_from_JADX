package com.google.firebase.inappmessaging.display.internal.layout.util;

import android.view.View;
import com.google.common.primitives.Ints;
import com.google.firebase.inappmessaging.display.internal.Logging;

public class MeasureUtils {
    public static void measureAtMost(View child, int width, int height) {
        measure(child, width, height, Integer.MIN_VALUE, Integer.MIN_VALUE);
    }

    public static void measureExactly(View child, int width, int height) {
        measure(child, width, height, Ints.MAX_POWER_OF_TWO, Ints.MAX_POWER_OF_TWO);
    }

    public static void measureFullWidth(View child, int width, int height) {
        measure(child, width, height, Ints.MAX_POWER_OF_TWO, Integer.MIN_VALUE);
    }

    public static void measureFullHeight(View child, int width, int height) {
        measure(child, width, height, Integer.MIN_VALUE, Ints.MAX_POWER_OF_TWO);
    }

    private static void measure(View child, int width, int height, int widthSpec, int heightSpec) {
        Logging.logdPair("\tdesired (w,h)", (float) child.getMeasuredWidth(), (float) child.getMeasuredHeight());
        if (child.getVisibility() == 8) {
            width = 0;
            height = 0;
        }
        child.measure(View.MeasureSpec.makeMeasureSpec(width, widthSpec), View.MeasureSpec.makeMeasureSpec(height, heightSpec));
        Logging.logdPair("\tactual (w,h)", (float) child.getMeasuredWidth(), (float) child.getMeasuredHeight());
    }
}
