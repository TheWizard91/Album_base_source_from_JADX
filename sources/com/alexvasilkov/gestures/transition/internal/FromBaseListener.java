package com.alexvasilkov.gestures.transition.internal;

import android.graphics.Rect;
import android.view.View;
import com.alexvasilkov.gestures.animation.ViewPositionAnimator;
import com.alexvasilkov.gestures.transition.ViewsTransitionAnimator;
import com.alexvasilkov.gestures.transition.tracker.FromTracker;

abstract class FromBaseListener<P extends View, ID> extends ViewsTransitionAnimator.RequestListener<ID> {
    private static final Rect locationChild = new Rect();
    private static final Rect locationParent = new Rect();
    private final boolean autoScroll;
    /* access modifiers changed from: private */
    public boolean isFullyOpened;
    /* access modifiers changed from: private */
    public final P parentView;
    private final FromTracker<ID> tracker;

    /* access modifiers changed from: package-private */
    public abstract boolean isShownInList(P p, int i);

    /* access modifiers changed from: package-private */
    public abstract void scrollToPosition(P p, int i);

    FromBaseListener(P parentView2, FromTracker<ID> tracker2, boolean autoScroll2) {
        this.parentView = parentView2;
        this.tracker = tracker2;
        this.autoScroll = autoScroll2;
    }

    /* access modifiers changed from: protected */
    public void initAnimator(ViewsTransitionAnimator<ID> animator) {
        super.initAnimator(animator);
        animator.addPositionUpdateListener(new ViewPositionAnimator.PositionUpdateListener() {
            public void onPositionUpdate(float pos, boolean isLeaving) {
                boolean z = false;
                FromBaseListener.this.parentView.setVisibility((pos != 1.0f || isLeaving) ? 0 : 4);
                FromBaseListener fromBaseListener = FromBaseListener.this;
                if (pos == 1.0f) {
                    z = true;
                }
                boolean unused = fromBaseListener.isFullyOpened = z;
            }
        });
    }

    public void onRequestView(ID id) {
        int position = this.tracker.getPositionById(id);
        if (position == -1) {
            getAnimator().setFromNone(id);
        } else if (isShownInList(this.parentView, position)) {
            View view = this.tracker.getViewById(id);
            if (view == null) {
                getAnimator().setFromNone(id);
                return;
            }
            getAnimator().setFromView(id, view);
            if (this.autoScroll && this.isFullyOpened && !isFullyVisible(this.parentView, view)) {
                scrollToPosition(this.parentView, position);
            }
        } else {
            getAnimator().setFromNone(id);
            if (this.autoScroll) {
                scrollToPosition(this.parentView, position);
            }
        }
    }

    private static boolean isFullyVisible(View parent, View child) {
        Rect rect = locationParent;
        parent.getGlobalVisibleRect(rect);
        rect.left += parent.getPaddingLeft();
        rect.right -= parent.getPaddingRight();
        rect.top += parent.getPaddingTop();
        rect.bottom -= parent.getPaddingBottom();
        Rect rect2 = locationChild;
        child.getGlobalVisibleRect(rect2);
        return rect.contains(rect2) && child.getWidth() == rect2.width() && child.getHeight() == rect2.height();
    }
}
