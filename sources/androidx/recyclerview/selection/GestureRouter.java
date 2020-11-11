package androidx.recyclerview.selection;

import android.view.GestureDetector;
import android.view.GestureDetector.OnDoubleTapListener;
import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;
import androidx.core.util.Preconditions;

final class GestureRouter<T extends GestureDetector.OnGestureListener & GestureDetector.OnDoubleTapListener> implements GestureDetector.OnGestureListener, GestureDetector.OnDoubleTapListener {
    private final ToolHandlerRegistry<T> mDelegates;

    GestureRouter(T defaultDelegate) {
        Preconditions.checkArgument(defaultDelegate != null);
        this.mDelegates = new ToolHandlerRegistry<>(defaultDelegate);
    }

    GestureRouter() {
        this(new GestureDetector.SimpleOnGestureListener());
    }

    public void register(int toolType, T delegate) {
        this.mDelegates.set(toolType, delegate);
    }

    public boolean onSingleTapConfirmed(MotionEvent e) {
        return ((GestureDetector.OnDoubleTapListener) ((GestureDetector.OnGestureListener) this.mDelegates.get(e))).onSingleTapConfirmed(e);
    }

    public boolean onDoubleTap(MotionEvent e) {
        return ((GestureDetector.OnDoubleTapListener) ((GestureDetector.OnGestureListener) this.mDelegates.get(e))).onDoubleTap(e);
    }

    public boolean onDoubleTapEvent(MotionEvent e) {
        return ((GestureDetector.OnDoubleTapListener) ((GestureDetector.OnGestureListener) this.mDelegates.get(e))).onDoubleTapEvent(e);
    }

    public boolean onDown(MotionEvent e) {
        return ((GestureDetector.OnGestureListener) this.mDelegates.get(e)).onDown(e);
    }

    public void onShowPress(MotionEvent e) {
        ((GestureDetector.OnGestureListener) this.mDelegates.get(e)).onShowPress(e);
    }

    public boolean onSingleTapUp(MotionEvent e) {
        return ((GestureDetector.OnGestureListener) this.mDelegates.get(e)).onSingleTapUp(e);
    }

    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return ((GestureDetector.OnGestureListener) this.mDelegates.get(e2)).onScroll(e1, e2, distanceX, distanceY);
    }

    public void onLongPress(MotionEvent e) {
        ((GestureDetector.OnGestureListener) this.mDelegates.get(e)).onLongPress(e);
    }

    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        return ((GestureDetector.OnGestureListener) this.mDelegates.get(e2)).onFling(e1, e2, velocityX, velocityY);
    }
}
