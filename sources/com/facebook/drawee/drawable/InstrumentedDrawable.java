package com.facebook.drawee.drawable;

import android.graphics.Canvas;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;

public class InstrumentedDrawable extends ForwardingDrawable {
    private boolean mIsChecked = false;
    private final Listener mListener;

    public interface Listener {
        void track(int i, int i2, int i3, int i4, int i5, int i6);
    }

    public InstrumentedDrawable(Drawable drawable, Listener listener) {
        super(drawable);
        this.mListener = listener;
    }

    public void draw(Canvas canvas) {
        if (!this.mIsChecked) {
            this.mIsChecked = true;
            RectF bounds = new RectF();
            getRootBounds(bounds);
            int viewWidth = (int) bounds.width();
            int viewHeight = (int) bounds.height();
            getTransformedBounds(bounds);
            int scaledWidth = (int) bounds.width();
            int scaledHeight = (int) bounds.height();
            int imageWidth = getIntrinsicWidth();
            int imageHeight = getIntrinsicHeight();
            Listener listener = this.mListener;
            if (listener != null) {
                listener.track(viewWidth, viewHeight, imageWidth, imageHeight, scaledWidth, scaledHeight);
            }
        }
        super.draw(canvas);
    }
}
