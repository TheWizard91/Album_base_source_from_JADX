package p023me.relex.photodraweeview;

import android.content.Context;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.VelocityTracker;
import android.view.ViewConfiguration;
import androidx.core.view.MotionEventCompat;

/* renamed from: me.relex.photodraweeview.ScaleDragDetector */
public class ScaleDragDetector implements ScaleGestureDetector.OnScaleGestureListener {
    private static final int INVALID_POINTER_ID = -1;
    private int mActivePointerId = -1;
    private int mActivePointerIndex = 0;
    private boolean mIsDragging;
    float mLastTouchX;
    float mLastTouchY;
    private final float mMinimumVelocity;
    private final ScaleGestureDetector mScaleDetector;
    private final OnScaleDragGestureListener mScaleDragGestureListener;
    private final float mTouchSlop;
    private VelocityTracker mVelocityTracker;

    public ScaleDragDetector(Context context, OnScaleDragGestureListener scaleDragGestureListener) {
        this.mScaleDetector = new ScaleGestureDetector(context, this);
        this.mScaleDragGestureListener = scaleDragGestureListener;
        ViewConfiguration configuration = ViewConfiguration.get(context);
        this.mMinimumVelocity = (float) configuration.getScaledMinimumFlingVelocity();
        this.mTouchSlop = (float) configuration.getScaledTouchSlop();
    }

    public boolean onScale(ScaleGestureDetector detector) {
        float scaleFactor = detector.getScaleFactor();
        if (Float.isNaN(scaleFactor) || Float.isInfinite(scaleFactor)) {
            return false;
        }
        this.mScaleDragGestureListener.onScale(scaleFactor, detector.getFocusX(), detector.getFocusY());
        return true;
    }

    public boolean onScaleBegin(ScaleGestureDetector detector) {
        return true;
    }

    public void onScaleEnd(ScaleGestureDetector detector) {
        this.mScaleDragGestureListener.onScaleEnd();
    }

    public boolean isScaling() {
        return this.mScaleDetector.isInProgress();
    }

    public boolean isDragging() {
        return this.mIsDragging;
    }

    private float getActiveX(MotionEvent ev) {
        try {
            return MotionEventCompat.getX(ev, this.mActivePointerIndex);
        } catch (Exception e) {
            return ev.getX();
        }
    }

    private float getActiveY(MotionEvent ev) {
        try {
            return MotionEventCompat.getY(ev, this.mActivePointerIndex);
        } catch (Exception e) {
            return ev.getY();
        }
    }

    public boolean onTouchEvent(MotionEvent ev) {
        this.mScaleDetector.onTouchEvent(ev);
        int action = MotionEventCompat.getActionMasked(ev);
        onTouchActivePointer(action, ev);
        onTouchDragEvent(action, ev);
        return true;
    }

    private void onTouchActivePointer(int action, MotionEvent ev) {
        int i = 0;
        if (action != 0) {
            int newPointerIndex = 1;
            if (action == 1 || action == 3) {
                this.mActivePointerId = -1;
            } else if (action == 6) {
                int pointerIndex = MotionEventCompat.getActionIndex(ev);
                if (MotionEventCompat.getPointerId(ev, pointerIndex) == this.mActivePointerId) {
                    if (pointerIndex != 0) {
                        newPointerIndex = 0;
                    }
                    this.mActivePointerId = MotionEventCompat.getPointerId(ev, newPointerIndex);
                    this.mLastTouchX = MotionEventCompat.getX(ev, newPointerIndex);
                    this.mLastTouchY = MotionEventCompat.getY(ev, newPointerIndex);
                }
            }
        } else {
            this.mActivePointerId = ev.getPointerId(0);
        }
        int i2 = this.mActivePointerId;
        if (i2 != -1) {
            i = i2;
        }
        this.mActivePointerIndex = MotionEventCompat.findPointerIndex(ev, i);
    }

    private void onTouchDragEvent(int action, MotionEvent ev) {
        VelocityTracker velocityTracker;
        boolean z = false;
        if (action == 0) {
            VelocityTracker obtain = VelocityTracker.obtain();
            this.mVelocityTracker = obtain;
            if (obtain != null) {
                obtain.addMovement(ev);
            }
            this.mLastTouchX = getActiveX(ev);
            this.mLastTouchY = getActiveY(ev);
            this.mIsDragging = false;
        } else if (action == 1) {
            if (this.mIsDragging && this.mVelocityTracker != null) {
                this.mLastTouchX = getActiveX(ev);
                this.mLastTouchY = getActiveY(ev);
                this.mVelocityTracker.addMovement(ev);
                this.mVelocityTracker.computeCurrentVelocity(1000);
                float vX = this.mVelocityTracker.getXVelocity();
                float vY = this.mVelocityTracker.getYVelocity();
                if (Math.max(Math.abs(vX), Math.abs(vY)) >= this.mMinimumVelocity) {
                    this.mScaleDragGestureListener.onFling(this.mLastTouchX, this.mLastTouchY, -vX, -vY);
                }
            }
            VelocityTracker velocityTracker2 = this.mVelocityTracker;
            if (velocityTracker2 != null) {
                velocityTracker2.recycle();
                this.mVelocityTracker = null;
            }
        } else if (action == 2) {
            float x = getActiveX(ev);
            float y = getActiveY(ev);
            float dx = x - this.mLastTouchX;
            float dy = y - this.mLastTouchY;
            if (!this.mIsDragging) {
                if (Math.sqrt((double) ((dx * dx) + (dy * dy))) >= ((double) this.mTouchSlop)) {
                    z = true;
                }
                this.mIsDragging = z;
            }
            if (this.mIsDragging) {
                this.mScaleDragGestureListener.onDrag(dx, dy);
                this.mLastTouchX = x;
                this.mLastTouchY = y;
                VelocityTracker velocityTracker3 = this.mVelocityTracker;
                if (velocityTracker3 != null) {
                    velocityTracker3.addMovement(ev);
                }
            }
        } else if (action == 3 && (velocityTracker = this.mVelocityTracker) != null) {
            velocityTracker.recycle();
            this.mVelocityTracker = null;
        }
    }
}
