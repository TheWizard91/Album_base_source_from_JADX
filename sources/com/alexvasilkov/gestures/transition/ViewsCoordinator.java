package com.alexvasilkov.gestures.transition;

import android.util.Log;
import android.view.View;
import com.alexvasilkov.gestures.animation.ViewPosition;
import com.alexvasilkov.gestures.internal.GestureDebug;
import com.alexvasilkov.gestures.views.interfaces.AnimatorView;

public class ViewsCoordinator<ID> {
    private static final String TAG = ViewsCoordinator.class.getSimpleName();
    private ID fromId;
    private OnRequestViewListener<ID> fromListener;
    private ViewPosition fromPos;
    private View fromView;
    private OnViewsReadyListener<ID> readyListener;
    private ID requestedId;
    private ID toId;
    private OnRequestViewListener<ID> toListener;
    private AnimatorView toView;

    public interface OnRequestViewListener<ID> {
        void onRequestView(ID id);
    }

    public interface OnViewsReadyListener<ID> {
        void onViewsReady(ID id);
    }

    public void setFromListener(OnRequestViewListener<ID> listener) {
        this.fromListener = listener;
    }

    public void setToListener(OnRequestViewListener<ID> listener) {
        this.toListener = listener;
    }

    public void setReadyListener(OnViewsReadyListener<ID> listener) {
        this.readyListener = listener;
    }

    public void request(ID id) {
        if (this.fromListener == null) {
            throw new RuntimeException("'from' listener is not set");
        } else if (this.toListener != null) {
            cleanupRequest();
            if (GestureDebug.isDebugAnimator()) {
                Log.d(TAG, "Requesting " + id);
            }
            this.requestedId = id;
            this.fromListener.onRequestView(id);
            this.toListener.onRequestView(id);
        } else {
            throw new RuntimeException("'to' listener is not set");
        }
    }

    public ID getRequestedId() {
        return this.requestedId;
    }

    public View getFromView() {
        return this.fromView;
    }

    public ViewPosition getFromPos() {
        return this.fromPos;
    }

    public AnimatorView getToView() {
        return this.toView;
    }

    public void setFromView(ID id, View fromView2) {
        setFromInternal(id, fromView2, (ViewPosition) null);
    }

    public void setFromPos(ID id, ViewPosition fromPos2) {
        setFromInternal(id, (View) null, fromPos2);
    }

    public void setFromNone(ID id) {
        setFromInternal(id, (View) null, (ViewPosition) null);
    }

    private void setFromInternal(ID id, View fromView2, ViewPosition fromPos2) {
        ID id2 = this.requestedId;
        if (id2 != null && id2.equals(id)) {
            if (this.fromView != fromView2 || fromView2 == null) {
                if (GestureDebug.isDebugAnimator()) {
                    Log.d(TAG, "Setting 'from' view for " + id);
                }
                onFromViewChanged(fromView2, fromPos2);
                this.fromId = id;
                this.fromView = fromView2;
                this.fromPos = fromPos2;
                notifyWhenReady();
            }
        }
    }

    /* access modifiers changed from: protected */
    public void onFromViewChanged(View fromView2, ViewPosition fromPos2) {
    }

    public void setToView(ID id, AnimatorView toView2) {
        ID id2 = this.requestedId;
        if (id2 != null && id2.equals(id) && this.toView != toView2) {
            if (GestureDebug.isDebugAnimator()) {
                Log.d(TAG, "Setting 'to' view for " + id);
            }
            onToViewChanged(this.toView, toView2);
            this.toId = id;
            this.toView = toView2;
            notifyWhenReady();
        }
    }

    /* access modifiers changed from: protected */
    public void onToViewChanged(AnimatorView old, AnimatorView view) {
    }

    public boolean isReady() {
        ID id = this.requestedId;
        return id != null && id.equals(this.fromId) && this.requestedId.equals(this.toId);
    }

    private void notifyWhenReady() {
        if (isReady()) {
            onViewsReady(this.requestedId);
        }
    }

    /* access modifiers changed from: protected */
    public void onViewsReady(ID id) {
        OnViewsReadyListener<ID> onViewsReadyListener = this.readyListener;
        if (onViewsReadyListener != null) {
            onViewsReadyListener.onViewsReady(id);
        }
    }

    /* access modifiers changed from: protected */
    public void cleanupRequest() {
        if (this.requestedId != null) {
            if (GestureDebug.isDebugAnimator()) {
                Log.d(TAG, "Cleaning up request " + this.requestedId);
            }
            this.fromView = null;
            this.fromPos = null;
            this.toView = null;
            this.toId = null;
            this.fromId = null;
            this.requestedId = null;
        }
    }
}
