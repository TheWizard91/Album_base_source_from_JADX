package com.facebook.imagepipeline.filter;

import android.graphics.Bitmap;
import com.facebook.common.internal.Preconditions;
import com.facebook.common.logging.FLog;
import java.util.Locale;

public abstract class IterativeBoxBlurFilter {
    private static final String TAG = "IterativeBoxBlurFilter";

    public static void boxBlurBitmapInPlace(Bitmap bitmap, int iterations, int radius) {
        Preconditions.checkNotNull(bitmap);
        Preconditions.checkArgument(bitmap.isMutable());
        Preconditions.checkArgument(((float) bitmap.getHeight()) <= 2048.0f);
        Preconditions.checkArgument(((float) bitmap.getWidth()) <= 2048.0f);
        Preconditions.checkArgument(radius > 0 && radius <= 25);
        Preconditions.checkArgument(iterations > 0);
        try {
            fastBoxBlur(bitmap, iterations, radius);
        } catch (OutOfMemoryError oom) {
            FLog.m60e(TAG, String.format((Locale) null, "OOM: %d iterations on %dx%d with %d radius", new Object[]{Integer.valueOf(iterations), Integer.valueOf(bitmap.getWidth()), Integer.valueOf(bitmap.getHeight()), Integer.valueOf(radius)}));
            throw oom;
        }
    }

    private static void fastBoxBlur(Bitmap bitmap, int iterations, int radius) {
        int w = bitmap.getWidth();
        int h = bitmap.getHeight();
        int[] pixels = new int[(w * h)];
        bitmap.getPixels(pixels, 0, w, 0, 0, w, h);
        int diameter = radius + 1 + radius;
        int[] div = new int[(diameter * 256)];
        int ptr = radius + 1;
        for (int b = 1; b <= 255; b++) {
            for (int d = 0; d < diameter; d++) {
                div[ptr] = b;
                ptr++;
            }
        }
        int[] tempRowOrColumn = new int[Math.max(w, h)];
        for (int i = 0; i < iterations; i++) {
            for (int row = 0; row < h; row++) {
                internalHorizontalBlur(pixels, tempRowOrColumn, w, row, diameter, div);
                System.arraycopy(tempRowOrColumn, 0, pixels, row * w, w);
            }
            int col = 0;
            while (col < w) {
                int col2 = col;
                internalVerticalBlur(pixels, tempRowOrColumn, w, h, col, diameter, div);
                int pos = col2;
                for (int row2 = 0; row2 < h; row2++) {
                    pixels[pos] = tempRowOrColumn[row2];
                    pos += w;
                }
                col = col2 + 1;
            }
            int i2 = col;
        }
        bitmap.setPixels(pixels, 0, w, 0, 0, w, h);
    }

    private static void internalHorizontalBlur(int[] pixels, int[] outRow, int w, int row, int diameter, int[] div) {
        int firstInByte = w * row;
        int lastInByte = ((row + 1) * w) - 1;
        int radius = diameter >> 1;
        int a = 0;
        int r = 0;
        int g = 0;
        int b = 0;
        for (int i = -radius; i < w + radius; i++) {
            int pixel = pixels[bound(firstInByte + i, firstInByte, lastInByte)];
            r += (pixel >> 16) & 255;
            g += (pixel >> 8) & 255;
            b += pixel & 255;
            a += pixel >>> 24;
            if (i >= radius) {
                outRow[i - radius] = (div[a] << 24) | (div[r] << 16) | (div[g] << 8) | div[b];
                int pixel2 = pixels[bound(firstInByte + (i - (diameter - 1)), firstInByte, lastInByte)];
                r -= (pixel2 >> 16) & 255;
                g -= (pixel2 >> 8) & 255;
                b -= pixel2 & 255;
                a -= pixel2 >>> 24;
            }
        }
    }

    private static void internalVerticalBlur(int[] pixels, int[] outCol, int w, int h, int col, int diameter, int[] div) {
        int i = col;
        int lastInByte = ((h - 1) * w) + i;
        int radiusTimesW = (diameter >> 1) * w;
        int diameterMinusOneTimesW = (diameter - 1) * w;
        int a = 0;
        int r = 0;
        int g = 0;
        int b = 0;
        int outColPos = 0;
        int i2 = i - radiusTimesW;
        while (i2 <= lastInByte + radiusTimesW) {
            int pixel = pixels[bound(i2, i, lastInByte)];
            r += (pixel >> 16) & 255;
            g += (pixel >> 8) & 255;
            b += pixel & 255;
            a += pixel >>> 24;
            if (i2 - radiusTimesW >= i) {
                outCol[outColPos] = (div[a] << 24) | (div[r] << 16) | (div[g] << 8) | div[b];
                outColPos++;
                int pixel2 = pixels[bound(i2 - diameterMinusOneTimesW, i, lastInByte)];
                r -= (pixel2 >> 16) & 255;
                g -= (pixel2 >> 8) & 255;
                b -= pixel2 & 255;
                a -= pixel2 >>> 24;
            }
            i2 += w;
        }
    }

    private static int bound(int x, int l, int h) {
        if (x < l) {
            return l;
        }
        return x > h ? h : x;
    }
}
