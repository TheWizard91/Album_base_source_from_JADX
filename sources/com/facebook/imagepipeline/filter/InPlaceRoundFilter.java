package com.facebook.imagepipeline.filter;

import android.graphics.Bitmap;
import com.facebook.common.internal.Preconditions;

public final class InPlaceRoundFilter {
    private InPlaceRoundFilter() {
    }

    public static void roundBitmapInPlace(Bitmap bitmap) {
        Preconditions.checkNotNull(bitmap);
        int w = bitmap.getWidth();
        int h = bitmap.getHeight();
        int radius = Math.min(w, h) / 2;
        int offC = w / 2;
        int centerY = h / 2;
        if (radius != 0) {
            Preconditions.checkArgument(radius >= 1);
            Preconditions.checkArgument(w > 0 && ((float) w) <= 2048.0f);
            Preconditions.checkArgument(h > 0 && ((float) h) <= 2048.0f);
            Preconditions.checkArgument(offC > 0 && offC < w);
            Preconditions.checkArgument(centerY > 0 && centerY < h);
            int[] pixels = new int[(w * h)];
            bitmap.getPixels(pixels, 0, w, 0, 0, w, h);
            int x = radius - 1;
            int offB = offC + x;
            int maxY = centerY + x;
            Preconditions.checkArgument(offC - x >= 0 && centerY - x >= 0 && offB < w && maxY < h);
            int rInc = (-radius) * 2;
            int[] transparentColor = new int[w];
            int dx = 1;
            int dy = 1;
            int err = 1 + rInc;
            int x2 = x;
            int y = 0;
            while (x2 >= y) {
                int cXpX = offC + x2;
                int cXmX = offC - x2;
                int cXpY = offC + y;
                int cXmY = offC - y;
                int cYpX = centerY + x2;
                int cYmX = centerY - x2;
                int cYpY = centerY + y;
                int cYmY = centerY - y;
                Preconditions.checkArgument(x2 >= 0 && cXpY < w && cXmY >= 0 && cYpY < h && cYmY >= 0);
                int maxY2 = maxY;
                int offA = w * cYpY;
                int maxX = offB;
                int maxX2 = w * cYmY;
                int centerX = offC;
                int centerX2 = w * cYpX;
                int i = cYpY;
                int cYpY2 = w * cYmX;
                int h2 = h;
                System.arraycopy(transparentColor, 0, pixels, offA, cXmX);
                System.arraycopy(transparentColor, 0, pixels, maxX2, cXmX);
                System.arraycopy(transparentColor, 0, pixels, centerX2, cXmY);
                System.arraycopy(transparentColor, 0, pixels, cYpY2, cXmY);
                int i2 = cXmX;
                int i3 = offA;
                System.arraycopy(transparentColor, 0, pixels, offA + cXpX, w - cXpX);
                System.arraycopy(transparentColor, 0, pixels, maxX2 + cXpX, w - cXpX);
                System.arraycopy(transparentColor, 0, pixels, centerX2 + cXpY, w - cXpY);
                System.arraycopy(transparentColor, 0, pixels, cYpY2 + cXpY, w - cXpY);
                if (err <= 0) {
                    y++;
                    dy += 2;
                    err += dy;
                }
                if (err > 0) {
                    x2--;
                    dx += 2;
                    err += dx + rInc;
                    maxY = maxY2;
                    offB = maxX;
                    offC = centerX;
                    h = h2;
                } else {
                    maxY = maxY2;
                    offB = maxX;
                    offC = centerX;
                    h = h2;
                }
            }
            int maxY3 = maxY;
            int maxX3 = offB;
            int h3 = h;
            int i4 = offC;
            for (int i5 = centerY - radius; i5 >= 0; i5--) {
                System.arraycopy(transparentColor, 0, pixels, i5 * w, w);
            }
            int i6 = centerY + radius;
            while (true) {
                int h4 = h3;
                if (i6 < h4) {
                    System.arraycopy(transparentColor, 0, pixels, i6 * w, w);
                    i6++;
                    h3 = h4;
                } else {
                    int i7 = y;
                    int i8 = x2;
                    int[] iArr = transparentColor;
                    int i9 = maxY3;
                    int i10 = maxX3;
                    bitmap.setPixels(pixels, 0, w, 0, 0, w, h4);
                    return;
                }
            }
        }
    }
}
