package com.facebook.drawee.view;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.MotionEvent;
import com.facebook.common.internal.Objects;
import com.facebook.common.internal.Preconditions;
import com.facebook.common.logging.FLog;
import com.facebook.drawee.components.DraweeEventTracker;
import com.facebook.drawee.drawable.VisibilityAwareDrawable;
import com.facebook.drawee.drawable.VisibilityCallback;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.interfaces.DraweeHierarchy;
import javax.annotation.Nullable;

public class DraweeHolder<DH extends DraweeHierarchy> implements VisibilityCallback {
    private DraweeController mController = null;
    private final DraweeEventTracker mEventTracker = DraweeEventTracker.newInstance();
    private DH mHierarchy;
    private boolean mIsControllerAttached = false;
    private boolean mIsHolderAttached = false;
    private boolean mIsVisible = true;

    public static <DH extends DraweeHierarchy> DraweeHolder<DH> create(@Nullable DH hierarchy, Context context) {
        DraweeHolder<DH> holder = new DraweeHolder<>(hierarchy);
        holder.registerWithContext(context);
        return holder;
    }

    public void registerWithContext(Context context) {
    }

    public DraweeHolder(@Nullable DH hierarchy) {
        if (hierarchy != null) {
            setHierarchy(hierarchy);
        }
    }

    public void onAttach() {
        this.mEventTracker.recordEvent(DraweeEventTracker.Event.ON_HOLDER_ATTACH);
        this.mIsHolderAttached = true;
        attachOrDetachController();
    }

    public boolean isAttached() {
        return this.mIsHolderAttached;
    }

    public void onDetach() {
        this.mEventTracker.recordEvent(DraweeEventTracker.Event.ON_HOLDER_DETACH);
        this.mIsHolderAttached = false;
        attachOrDetachController();
    }

    public boolean onTouchEvent(MotionEvent event) {
        if (!isControllerValid()) {
            return false;
        }
        return this.mController.onTouchEvent(event);
    }

    public void onVisibilityChange(boolean isVisible) {
        if (this.mIsVisible != isVisible) {
            this.mEventTracker.recordEvent(isVisible ? DraweeEventTracker.Event.ON_DRAWABLE_SHOW : DraweeEventTracker.Event.ON_DRAWABLE_HIDE);
            this.mIsVisible = isVisible;
            attachOrDetachController();
        }
    }

    public void onDraw() {
        if (!this.mIsControllerAttached) {
            FLog.m98w((Class<?>) DraweeEventTracker.class, "%x: Draw requested for a non-attached controller %x. %s", Integer.valueOf(System.identityHashCode(this)), Integer.valueOf(System.identityHashCode(this.mController)), toString());
            this.mIsHolderAttached = true;
            this.mIsVisible = true;
            attachOrDetachController();
        }
    }

    private void setVisibilityCallback(@Nullable VisibilityCallback visibilityCallback) {
        Drawable drawable = getTopLevelDrawable();
        if (drawable instanceof VisibilityAwareDrawable) {
            ((VisibilityAwareDrawable) drawable).setVisibilityCallback(visibilityCallback);
        }
    }

    public void setController(@Nullable DraweeController draweeController) {
        boolean wasAttached = this.mIsControllerAttached;
        if (wasAttached) {
            detachController();
        }
        if (isControllerValid()) {
            this.mEventTracker.recordEvent(DraweeEventTracker.Event.ON_CLEAR_OLD_CONTROLLER);
            this.mController.setHierarchy((DraweeHierarchy) null);
        }
        this.mController = draweeController;
        if (draweeController != null) {
            this.mEventTracker.recordEvent(DraweeEventTracker.Event.ON_SET_CONTROLLER);
            this.mController.setHierarchy(this.mHierarchy);
        } else {
            this.mEventTracker.recordEvent(DraweeEventTracker.Event.ON_CLEAR_CONTROLLER);
        }
        if (wasAttached) {
            attachController();
        }
    }

    @Nullable
    public DraweeController getController() {
        return this.mController;
    }

    public void setHierarchy(DH hierarchy) {
        this.mEventTracker.recordEvent(DraweeEventTracker.Event.ON_SET_HIERARCHY);
        boolean isControllerValid = isControllerValid();
        setVisibilityCallback((VisibilityCallback) null);
        DH dh = (DraweeHierarchy) Preconditions.checkNotNull(hierarchy);
        this.mHierarchy = dh;
        Drawable drawable = dh.getTopLevelDrawable();
        onVisibilityChange(drawable == null || drawable.isVisible());
        setVisibilityCallback(this);
        if (isControllerValid) {
            this.mController.setHierarchy(hierarchy);
        }
    }

    public DH getHierarchy() {
        return (DraweeHierarchy) Preconditions.checkNotNull(this.mHierarchy);
    }

    public boolean hasHierarchy() {
        return this.mHierarchy != null;
    }

    @Nullable
    public Drawable getTopLevelDrawable() {
        DH dh = this.mHierarchy;
        if (dh == null) {
            return null;
        }
        return dh.getTopLevelDrawable();
    }

    public boolean isControllerValid() {
        DraweeController draweeController = this.mController;
        return draweeController != null && draweeController.getHierarchy() == this.mHierarchy;
    }

    /* access modifiers changed from: protected */
    public DraweeEventTracker getDraweeEventTracker() {
        return this.mEventTracker;
    }

    private void attachController() {
        if (!this.mIsControllerAttached) {
            this.mEventTracker.recordEvent(DraweeEventTracker.Event.ON_ATTACH_CONTROLLER);
            this.mIsControllerAttached = true;
            DraweeController draweeController = this.mController;
            if (draweeController != null && draweeController.getHierarchy() != null) {
                this.mController.onAttach();
            }
        }
    }

    private void detachController() {
        if (this.mIsControllerAttached) {
            this.mEventTracker.recordEvent(DraweeEventTracker.Event.ON_DETACH_CONTROLLER);
            this.mIsControllerAttached = false;
            if (isControllerValid()) {
                this.mController.onDetach();
            }
        }
    }

    private void attachOrDetachController() {
        if (!this.mIsHolderAttached || !this.mIsVisible) {
            detachController();
        } else {
            attachController();
        }
    }

    public String toString() {
        return Objects.toStringHelper((Object) this).add("controllerAttached", this.mIsControllerAttached).add("holderAttached", this.mIsHolderAttached).add("drawableVisible", this.mIsVisible).add("events", (Object) this.mEventTracker.toString()).toString();
    }
}
