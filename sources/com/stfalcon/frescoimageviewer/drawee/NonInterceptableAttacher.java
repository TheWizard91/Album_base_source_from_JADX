package com.stfalcon.frescoimageviewer.drawee;

import android.view.ViewParent;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.view.DraweeView;
import p023me.relex.photodraweeview.Attacher;
import p023me.relex.photodraweeview.OnScaleChangeListener;

class NonInterceptableAttacher extends Attacher {
    private OnScaleChangeListener scaleChangeListener;

    public NonInterceptableAttacher(DraweeView<GenericDraweeHierarchy> draweeView) {
        super(draweeView);
    }

    public void onDrag(float dx, float dy) {
        DraweeView<GenericDraweeHierarchy> draweeView = getDraweeView();
        if (draweeView != null) {
            getDrawMatrix().postTranslate(dx, dy);
            checkMatrixAndInvalidate();
            ViewParent parent = draweeView.getParent();
            if (parent != null) {
                if (getScale() == 1.0f) {
                    parent.requestDisallowInterceptTouchEvent(false);
                } else {
                    parent.requestDisallowInterceptTouchEvent(true);
                }
            }
        }
    }

    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
    }

    public void setOnScaleChangeListener(OnScaleChangeListener listener) {
        this.scaleChangeListener = listener;
    }

    public void onScale(float scaleFactor, float focusX, float focusY) {
        super.onScale(scaleFactor, focusX, focusY);
        OnScaleChangeListener onScaleChangeListener = this.scaleChangeListener;
        if (onScaleChangeListener != null) {
            onScaleChangeListener.onScaleChange(scaleFactor, focusX, focusY);
        }
    }
}
