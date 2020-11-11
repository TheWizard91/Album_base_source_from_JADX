package com.alexvasilkov.gestures.animation;

import android.os.Build;
import android.view.View;
import android.view.ViewTreeObserver;

class ViewPositionHolder implements ViewTreeObserver.OnPreDrawListener {
    private boolean isPaused;
    private OnViewPositionChangeListener listener;
    private final ViewPosition pos = ViewPosition.newInstance();
    private View view;

    interface OnViewPositionChangeListener {
        void onViewPositionChanged(ViewPosition viewPosition);
    }

    ViewPositionHolder() {
    }

    public boolean onPreDraw() {
        update();
        return true;
    }

    /* access modifiers changed from: package-private */
    public void init(View view2, OnViewPositionChangeListener listener2) {
        this.view = view2;
        this.listener = listener2;
        view2.getViewTreeObserver().addOnPreDrawListener(this);
        if (isLaidOut()) {
            update();
        }
    }

    /* access modifiers changed from: package-private */
    public void clear() {
        View view2 = this.view;
        if (view2 != null) {
            view2.getViewTreeObserver().removeOnPreDrawListener(this);
        }
        this.pos.view.setEmpty();
        this.pos.viewport.setEmpty();
        this.pos.image.setEmpty();
        this.view = null;
        this.listener = null;
        this.isPaused = false;
    }

    /* access modifiers changed from: package-private */
    public void pause(boolean paused) {
        if (this.isPaused != paused) {
            this.isPaused = paused;
            update();
        }
    }

    private void update() {
        View view2 = this.view;
        if (view2 != null && this.listener != null && !this.isPaused && ViewPosition.apply(this.pos, view2)) {
            this.listener.onViewPositionChanged(this.pos);
        }
    }

    private boolean isLaidOut() {
        if (Build.VERSION.SDK_INT >= 19) {
            return this.view.isLaidOut();
        }
        return this.view.getWidth() > 0 && this.view.getHeight() > 0;
    }
}
