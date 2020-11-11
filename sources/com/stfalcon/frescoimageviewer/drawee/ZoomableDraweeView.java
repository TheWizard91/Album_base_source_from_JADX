package com.stfalcon.frescoimageviewer.drawee;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.view.SimpleDraweeView;
import p023me.relex.photodraweeview.IAttacher;
import p023me.relex.photodraweeview.OnPhotoTapListener;
import p023me.relex.photodraweeview.OnScaleChangeListener;
import p023me.relex.photodraweeview.OnViewTapListener;

public class ZoomableDraweeView extends SimpleDraweeView implements IAttacher {
    private NonInterceptableAttacher attacher;

    public ZoomableDraweeView(Context context, GenericDraweeHierarchy hierarchy) {
        super(context, hierarchy);
        init();
    }

    public ZoomableDraweeView(Context context) {
        super(context);
        init();
    }

    public ZoomableDraweeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ZoomableDraweeView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    /* access modifiers changed from: protected */
    public void init() {
        NonInterceptableAttacher nonInterceptableAttacher = this.attacher;
        if (nonInterceptableAttacher == null || nonInterceptableAttacher.getDraweeView() == null) {
            this.attacher = new NonInterceptableAttacher(this);
        }
    }

    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        int saveCount = canvas.save();
        canvas.concat(this.attacher.getDrawMatrix());
        super.onDraw(canvas);
        canvas.restoreToCount(saveCount);
    }

    /* access modifiers changed from: protected */
    public void onAttachedToWindow() {
        init();
        super.onAttachedToWindow();
    }

    /* access modifiers changed from: protected */
    public void onDetachedFromWindow() {
        this.attacher.onDetachedFromWindow();
        super.onDetachedFromWindow();
    }

    public float getMinimumScale() {
        return this.attacher.getMinimumScale();
    }

    public float getMediumScale() {
        return this.attacher.getMediumScale();
    }

    public float getMaximumScale() {
        return this.attacher.getMaximumScale();
    }

    public void setMinimumScale(float minimumScale) {
        this.attacher.setMinimumScale(minimumScale);
    }

    public void setMediumScale(float mediumScale) {
        this.attacher.setMediumScale(mediumScale);
    }

    public void setMaximumScale(float maximumScale) {
        this.attacher.setMaximumScale(maximumScale);
    }

    public float getScale() {
        return this.attacher.getScale();
    }

    public void setScale(float scale) {
        this.attacher.setScale(scale);
    }

    public void setScale(float scale, boolean animate) {
        setScale(scale, (float) (getRight() / 2), (float) (getBottom() / 2), animate);
    }

    public void setScale(float scale, float focalX, float focalY, boolean animate) {
        this.attacher.setScale(scale, focalX, focalY, animate);
    }

    public void setZoomTransitionDuration(long duration) {
        this.attacher.setZoomTransitionDuration(duration);
    }

    public void setAllowParentInterceptOnEdge(boolean allow) {
        this.attacher.setAllowParentInterceptOnEdge(allow);
    }

    public void setOnDoubleTapListener(GestureDetector.OnDoubleTapListener listener) {
        this.attacher.setOnDoubleTapListener(listener);
    }

    public void setOnScaleChangeListener(OnScaleChangeListener listener) {
        this.attacher.setOnScaleChangeListener(listener);
    }

    public void setOnLongClickListener(View.OnLongClickListener listener) {
        this.attacher.setOnLongClickListener(listener);
    }

    public void setOnPhotoTapListener(OnPhotoTapListener listener) {
        this.attacher.setOnPhotoTapListener(listener);
    }

    public void setOnViewTapListener(OnViewTapListener listener) {
        this.attacher.setOnViewTapListener(listener);
    }

    public OnPhotoTapListener getOnPhotoTapListener() {
        return this.attacher.getOnPhotoTapListener();
    }

    public OnViewTapListener getOnViewTapListener() {
        return this.attacher.getOnViewTapListener();
    }

    public void update(int imageInfoWidth, int imageInfoHeight) {
        this.attacher.update(imageInfoWidth, imageInfoHeight);
    }
}
