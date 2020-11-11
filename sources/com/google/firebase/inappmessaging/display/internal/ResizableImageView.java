package com.google.firebase.inappmessaging.display.internal;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import androidx.appcompat.widget.AppCompatImageView;

public class ResizableImageView extends AppCompatImageView {
    private int mDensityDpi;

    public ResizableImageView(Context context) {
        super(context);
        init(context);
    }

    public ResizableImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ResizableImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        this.mDensityDpi = (int) (context.getResources().getDisplayMetrics().density * 160.0f);
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (Build.VERSION.SDK_INT > 16) {
            Drawable d = getDrawable();
            boolean adjustViewBounds = getAdjustViewBounds();
            if (d != null && adjustViewBounds) {
                scalePxToDp(d);
                checkMinDim();
            }
        }
    }

    private void checkMinDim() {
        int minWidth = Math.max(getMinimumWidth(), getSuggestedMinimumWidth());
        int minHeight = Math.max(getMinimumHeight(), getSuggestedMinimumHeight());
        int widthSpec = getMeasuredWidth();
        int heightSpec = getMeasuredHeight();
        Logging.logdPair("Image: min width, height", (float) minWidth, (float) minHeight);
        Logging.logdPair("Image: actual width, height", (float) widthSpec, (float) heightSpec);
        float scaleW = 1.0f;
        float scaleH = 1.0f;
        if (widthSpec < minWidth) {
            scaleW = ((float) minWidth) / ((float) widthSpec);
        }
        if (heightSpec < minHeight) {
            scaleH = ((float) minHeight) / ((float) heightSpec);
        }
        float scale = scaleW > scaleH ? scaleW : scaleH;
        if (((double) scale) > 1.0d) {
            int targetW = (int) Math.ceil((double) (((float) widthSpec) * scale));
            int targetH = (int) Math.ceil((double) (((float) heightSpec) * scale));
            Logging.logd("Measured dimension (" + widthSpec + "x" + heightSpec + ") too small.  Resizing to " + targetW + "x" + targetH);
            Dimensions t = bound(targetW, targetH);
            setMeasuredDimension(t.f1742w, t.f1741h);
        }
    }

    private void scalePxToDp(Drawable d) {
        int widthSpec = d.getIntrinsicWidth();
        int heightSpec = d.getIntrinsicHeight();
        Logging.logdPair("Image: intrinsic width, height", (float) widthSpec, (float) heightSpec);
        Dimensions t = bound((int) Math.ceil((double) ((this.mDensityDpi * widthSpec) / 160)), (int) Math.ceil((double) ((this.mDensityDpi * heightSpec) / 160)));
        Logging.logdPair("Image: new target dimensions", (float) t.f1742w, (float) t.f1741h);
        setMeasuredDimension(t.f1742w, t.f1741h);
    }

    private Dimensions bound(int targetW, int targetH) {
        int maxWidth = getMaxWidth();
        int maxHeight = getMaxHeight();
        if (targetW > maxWidth) {
            Logging.logdNumber("Image: capping width", (float) maxWidth);
            targetH = (targetH * maxWidth) / targetW;
            targetW = maxWidth;
        }
        if (targetH > maxHeight) {
            Logging.logdNumber("Image: capping height", (float) maxHeight);
            targetW = (targetW * maxHeight) / targetH;
            targetH = maxHeight;
        }
        return new Dimensions(targetW, targetH);
    }

    private static class Dimensions {

        /* renamed from: h */
        final int f1741h;

        /* renamed from: w */
        final int f1742w;

        private Dimensions(int w, int h) {
            this.f1742w = w;
            this.f1741h = h;
        }
    }
}
