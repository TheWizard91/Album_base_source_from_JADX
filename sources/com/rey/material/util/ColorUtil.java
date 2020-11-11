package com.rey.material.util;

import android.graphics.Color;

public class ColorUtil {
    private static int getMiddleValue(int prev, int next, float factor) {
        return Math.round(((float) prev) + (((float) (next - prev)) * factor));
    }

    public static int getMiddleColor(int prevColor, int curColor, float factor) {
        if (prevColor == curColor) {
            return curColor;
        }
        if (factor == 0.0f) {
            return prevColor;
        }
        if (factor == 1.0f) {
            return curColor;
        }
        return Color.argb(getMiddleValue(Color.alpha(prevColor), Color.alpha(curColor), factor), getMiddleValue(Color.red(prevColor), Color.red(curColor), factor), getMiddleValue(Color.green(prevColor), Color.green(curColor), factor), getMiddleValue(Color.blue(prevColor), Color.blue(curColor), factor));
    }

    public static int getColor(int baseColor, float alphaPercent) {
        return (16777215 & baseColor) | (Math.round(((float) Color.alpha(baseColor)) * alphaPercent) << 24);
    }
}
