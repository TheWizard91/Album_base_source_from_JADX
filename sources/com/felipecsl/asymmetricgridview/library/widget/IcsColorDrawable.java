package com.felipecsl.asymmetricgridview.library.widget;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import androidx.core.view.ViewCompat;

public class IcsColorDrawable extends Drawable {
    private int color;
    private final Paint paint = new Paint();

    public IcsColorDrawable(ColorDrawable drawable) {
        Bitmap bitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888);
        drawable.draw(new Canvas(bitmap));
        this.color = bitmap.getPixel(0, 0);
        bitmap.recycle();
    }

    public IcsColorDrawable(int color2) {
        this.color = color2;
    }

    public void draw(Canvas canvas) {
        int i = this.color;
        if ((i >>> 24) != 0) {
            this.paint.setColor(i);
            canvas.drawRect(getBounds(), this.paint);
        }
    }

    public void setAlpha(int alpha) {
        int i = this.color;
        if (alpha != (i >>> 24)) {
            this.color = (i & ViewCompat.MEASURED_SIZE_MASK) | (alpha << 24);
            invalidateSelf();
        }
    }

    public void setColorFilter(ColorFilter colorFilter) {
    }

    public int getOpacity() {
        return this.color >>> 24;
    }
}
