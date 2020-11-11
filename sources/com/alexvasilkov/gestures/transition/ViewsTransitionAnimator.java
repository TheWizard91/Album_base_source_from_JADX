package com.alexvasilkov.gestures.transition;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import com.alexvasilkov.gestures.animation.ViewPosition;
import com.alexvasilkov.gestures.animation.ViewPositionAnimator;
import com.alexvasilkov.gestures.internal.GestureDebug;
import com.alexvasilkov.gestures.transition.ViewsCoordinator;
import com.alexvasilkov.gestures.views.interfaces.AnimatorView;
import java.util.ArrayList;
import java.util.List;

public class ViewsTransitionAnimator<ID> extends ViewsCoordinator<ID> {
    private static final Object NONE = new Object();
    private static final String TAG = ViewsTransitionAnimator.class.getSimpleName();
    private boolean enterWithAnimation;
    private boolean exitRequested;
    private boolean exitWithAnimation;
    private boolean isEntered;
    private final List<ViewPositionAnimator.PositionUpdateListener> listeners = new ArrayList();

    @Deprecated
    public ViewsTransitionAnimator() {
        addPositionUpdateListener(new ViewPositionAnimator.PositionUpdateListener() {
            public void onPositionUpdate(float position, boolean isLeaving) {
                if (position == 0.0f && isLeaving) {
                    ViewsTransitionAnimator.this.cleanupRequest();
                }
            }
        });
    }

    public void enter(ID id, boolean withAnimation) {
        if (GestureDebug.isDebugAnimator()) {
            Log.d(TAG, "Enter requested for " + id + ", with animation = " + withAnimation);
        }
        this.enterWithAnimation = withAnimation;
        request(id);
    }

    public void enterSingle(boolean withAnimation) {
        enter(NONE, withAnimation);
    }

    public void exit(boolean withAnimation) {
        if (getRequestedId() != null) {
            if (GestureDebug.isDebugAnimator()) {
                Log.d(TAG, "Exit requested from " + getRequestedId() + ", with animation = " + withAnimation);
            }
            this.exitRequested = true;
            this.exitWithAnimation = withAnimation;
            exitIfRequested();
            return;
        }
        throw new IllegalStateException("You should call enter(...) before calling exit(...)");
    }

    private void exitIfRequested() {
        if (this.exitRequested && isReady()) {
            this.exitRequested = false;
            if (GestureDebug.isDebugAnimator()) {
                Log.d(TAG, "Perform exit from " + getRequestedId());
            }
            getToView().getPositionAnimator().exit(this.exitWithAnimation);
        }
    }

    public boolean isLeaving() {
        return this.exitRequested || getRequestedId() == null || (isReady() && getToView().getPositionAnimator().isLeaving());
    }

    public void addPositionUpdateListener(ViewPositionAnimator.PositionUpdateListener listener) {
        this.listeners.add(listener);
        if (isReady()) {
            getToView().getPositionAnimator().addPositionUpdateListener(listener);
        }
    }

    public void removePositionUpdateListener(ViewPositionAnimator.PositionUpdateListener listener) {
        this.listeners.remove(listener);
        if (isReady()) {
            getToView().getPositionAnimator().removePositionUpdateListener(listener);
        }
    }

    public void setFromListener(ViewsCoordinator.OnRequestViewListener<ID> listener) {
        super.setFromListener(listener);
        if (listener instanceof RequestListener) {
            ((RequestListener) listener).initAnimator(this);
        }
    }

    public void setToListener(ViewsCoordinator.OnRequestViewListener<ID> listener) {
        super.setToListener(listener);
        if (listener instanceof RequestListener) {
            ((RequestListener) listener).initAnimator(this);
        }
    }

    /* access modifiers changed from: protected */
    public void onFromViewChanged(View fromView, ViewPosition fromPos) {
        super.onFromViewChanged(fromView, fromPos);
        if (isReady()) {
            if (GestureDebug.isDebugAnimator()) {
                Log.d(TAG, "Updating 'from' view for " + getRequestedId());
            }
            if (fromView != null) {
                getToView().getPositionAnimator().update(fromView);
            } else if (fromPos != null) {
                getToView().getPositionAnimator().update(fromPos);
            } else {
                getToView().getPositionAnimator().updateToNone();
            }
        }
    }

    /* access modifiers changed from: protected */
    public void onToViewChanged(AnimatorView old, AnimatorView view) {
        super.onToViewChanged(old, view);
        if (!isReady() || old == null) {
            if (old != null) {
                cleanupAnimator(old.getPositionAnimator());
            }
            initAnimator(view.getPositionAnimator());
            return;
        }
        swapAnimator(old.getPositionAnimator(), view.getPositionAnimator());
    }

    /* access modifiers changed from: protected */
    public void onViewsReady(ID id) {
        if (!this.isEntered) {
            this.isEntered = true;
            if (GestureDebug.isDebugAnimator()) {
                Log.d(TAG, "Ready to enter for " + getRequestedId());
            }
            if (getFromView() != null) {
                getToView().getPositionAnimator().enter(getFromView(), this.enterWithAnimation);
            } else if (getFromPos() != null) {
                getToView().getPositionAnimator().enter(getFromPos(), this.enterWithAnimation);
            } else {
                getToView().getPositionAnimator().enter(this.enterWithAnimation);
            }
            exitIfRequested();
        }
        if ((getFromView() instanceof ImageView) && (getToView() instanceof ImageView)) {
            ImageView from = (ImageView) getFromView();
            ImageView to = (ImageView) getToView();
            if (to.getDrawable() == null) {
                to.setImageDrawable(from.getDrawable());
            }
        }
        super.onViewsReady(id);
    }

    /* access modifiers changed from: protected */
    public void cleanupRequest() {
        if (getToView() != null) {
            cleanupAnimator(getToView().getPositionAnimator());
        }
        this.isEntered = false;
        this.exitRequested = false;
        super.cleanupRequest();
    }

    private void initAnimator(ViewPositionAnimator animator) {
        for (ViewPositionAnimator.PositionUpdateListener listener : this.listeners) {
            animator.addPositionUpdateListener(listener);
        }
    }

    private void cleanupAnimator(ViewPositionAnimator animator) {
        for (ViewPositionAnimator.PositionUpdateListener listener : this.listeners) {
            animator.removePositionUpdateListener(listener);
        }
        if (!animator.isLeaving() || animator.getPosition() != 0.0f) {
            if (GestureDebug.isDebugAnimator()) {
                Log.d(TAG, "Exiting from cleaned animator for " + getRequestedId());
            }
            animator.exit(false);
        }
    }

    private void swapAnimator(ViewPositionAnimator old, ViewPositionAnimator next) {
        float position = old.getPosition();
        boolean isLeaving = old.isLeaving();
        boolean isAnimating = old.isAnimating();
        if (GestureDebug.isDebugAnimator()) {
            Log.d(TAG, "Swapping animator for " + getRequestedId());
        }
        cleanupAnimator(old);
        if (getFromView() != null) {
            next.enter(getFromView(), false);
        } else if (getFromPos() != null) {
            next.enter(getFromPos(), false);
        }
        initAnimator(next);
        next.setState(position, isLeaving, isAnimating);
    }

    public static abstract class RequestListener<ID> implements ViewsCoordinator.OnRequestViewListener<ID> {
        private ViewsTransitionAnimator<ID> animator;

        /* access modifiers changed from: protected */
        public void initAnimator(ViewsTransitionAnimator<ID> animator2) {
            this.animator = animator2;
        }

        /* access modifiers changed from: protected */
        public ViewsTransitionAnimator<ID> getAnimator() {
            return this.animator;
        }
    }
}
