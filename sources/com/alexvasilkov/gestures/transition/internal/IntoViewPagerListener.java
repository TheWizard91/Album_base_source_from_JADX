package com.alexvasilkov.gestures.transition.internal;

import android.view.View;
import android.view.ViewGroup;
import androidx.viewpager.widget.ViewPager;
import com.alexvasilkov.gestures.animation.ViewPositionAnimator;
import com.alexvasilkov.gestures.transition.ViewsTransitionAnimator;
import com.alexvasilkov.gestures.transition.tracker.IntoTracker;
import com.alexvasilkov.gestures.views.interfaces.AnimatorView;

public class IntoViewPagerListener<ID> extends ViewsTransitionAnimator.RequestListener<ID> {
    /* access modifiers changed from: private */
    public boolean preventExit;
    private final IntoTracker<ID> tracker;
    /* access modifiers changed from: private */
    public final ViewPager viewPager;

    public IntoViewPagerListener(ViewPager viewPager2, IntoTracker<ID> tracker2) {
        this.viewPager = viewPager2;
        this.tracker = tracker2;
        viewPager2.setVisibility(8);
        viewPager2.addOnPageChangeListener(new PagerListener());
        viewPager2.setOnHierarchyChangeListener(new ChildStateListener());
    }

    /* access modifiers changed from: protected */
    public void initAnimator(ViewsTransitionAnimator<ID> animator) {
        super.initAnimator(animator);
        animator.addPositionUpdateListener(new ViewPositionAnimator.PositionUpdateListener() {
            public void onPositionUpdate(float pos, boolean isLeaving) {
                if (pos == 1.0f && isLeaving && IntoViewPagerListener.this.getAnimator().getRequestedId() != null) {
                    if (IntoViewPagerListener.this.preventExit) {
                        IntoViewPagerListener.this.skipExit();
                    }
                    IntoViewPagerListener.this.switchToCurrentPage();
                }
                IntoViewPagerListener.this.viewPager.setVisibility((pos != 0.0f || !isLeaving) ? 0 : 4);
            }
        });
    }

    public void onRequestView(ID id) {
        if (this.viewPager.getVisibility() == 8) {
            this.viewPager.setVisibility(4);
        }
        int position = this.tracker.getPositionById(id);
        if (position != -1) {
            if (this.viewPager.getCurrentItem() == position) {
                applyCurrentPage();
            } else {
                this.viewPager.setCurrentItem(position, false);
            }
        }
    }

    /* access modifiers changed from: private */
    public void applyCurrentPage() {
        ID id = getAnimator().getRequestedId();
        if (id != null && this.viewPager.getAdapter() != null && this.viewPager.getAdapter().getCount() != 0) {
            int position = this.tracker.getPositionById(id);
            if (position == -1) {
                switchToCurrentPage();
            } else if (position == this.viewPager.getCurrentItem()) {
                View view = this.tracker.getViewById(id);
                if (view instanceof AnimatorView) {
                    getAnimator().setToView(id, (AnimatorView) view);
                } else if (view != null) {
                    throw new IllegalArgumentException("View for " + id + " should be AnimatorView");
                }
            }
        }
    }

    /* access modifiers changed from: private */
    public void switchToCurrentPage() {
        if (this.viewPager.getAdapter() != null && this.viewPager.getAdapter().getCount() != 0) {
            ID id = getAnimator().getRequestedId();
            ID currentId = this.tracker.getIdByPosition(this.viewPager.getCurrentItem());
            if (id != null && currentId != null && !id.equals(currentId)) {
                AnimatorView toView = getAnimator().getToView();
                ViewPositionAnimator toAnimator = toView == null ? null : toView.getPositionAnimator();
                boolean isAnimating = true;
                boolean isLeaving = toAnimator != null && toAnimator.isLeaving();
                float position = toAnimator == null ? 0.0f : toAnimator.getPosition();
                if (toAnimator == null || !toAnimator.isAnimating()) {
                    isAnimating = false;
                }
                skipExit();
                getAnimator().enter(currentId, false);
                if (isLeaving && position > 0.0f) {
                    getAnimator().exit(isAnimating);
                }
            }
        }
    }

    /* access modifiers changed from: private */
    public void skipExit() {
        if (getAnimator().getToView() != null) {
            ViewPositionAnimator toAnimator = getAnimator().getToView().getPositionAnimator();
            if (toAnimator.isLeaving() && toAnimator.getPosition() == 1.0f) {
                toAnimator.setState(1.0f, false, false);
            }
        }
    }

    private class PagerListener implements ViewPager.OnPageChangeListener {
        private PagerListener() {
        }

        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        }

        public void onPageSelected(int position) {
            IntoViewPagerListener.this.applyCurrentPage();
        }

        public void onPageScrollStateChanged(int state) {
            IntoViewPagerListener intoViewPagerListener = IntoViewPagerListener.this;
            boolean z = true;
            if (state != 1 || intoViewPagerListener.getAnimator().isLeaving()) {
                z = false;
            }
            boolean unused = intoViewPagerListener.preventExit = z;
            if (state == 0 && IntoViewPagerListener.this.getAnimator().getRequestedId() != null) {
                IntoViewPagerListener.this.switchToCurrentPage();
            }
        }
    }

    private class ChildStateListener implements ViewGroup.OnHierarchyChangeListener {
        private ChildStateListener() {
        }

        public void onChildViewAdded(View parent, View child) {
            IntoViewPagerListener.this.applyCurrentPage();
        }

        public void onChildViewRemoved(View parent, View child) {
        }
    }
}
