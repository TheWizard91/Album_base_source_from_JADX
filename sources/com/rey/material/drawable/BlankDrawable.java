package com.rey.material.drawable;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.drawable.Drawable;

public class BlankDrawable extends Drawable {
    private static BlankDrawable mInstance;

    public static BlankDrawable getInstance() {
        if (mInstance == null) {
            synchronized (BlankDrawable.class) {
                if (mInstance == null) {
                    mInstance = new BlankDrawable();
                }
            }
        }
        return mInstance;
    }

    public void draw(Canvas canvas) {
    }

    public void setAlpha(int alpha) {
    }

    public void setColorFilter(ColorFilter cf) {
    }

    public int getOpacity() {
        return -2;
    }
}
