package androidx.recyclerview.selection;

import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import androidx.core.util.Preconditions;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.selection.SelectionTracker;
import androidx.recyclerview.widget.RecyclerView;

final class GestureSelectionHelper implements RecyclerView.OnItemTouchListener, Resettable {
    private static final String TAG = "GestureSelectionHelper";
    private final OperationMonitor mLock;
    private final AutoScroller mScroller;
    private final SelectionTracker<?> mSelectionMgr;
    private final SelectionTracker.SelectionPredicate<?> mSelectionPredicate;
    private boolean mStarted = false;
    private final ViewDelegate mView;

    GestureSelectionHelper(SelectionTracker<?> selectionTracker, SelectionTracker.SelectionPredicate<?> selectionPredicate, ViewDelegate view, AutoScroller scroller, OperationMonitor lock) {
        boolean z = false;
        Preconditions.checkArgument(selectionTracker != null);
        Preconditions.checkArgument(selectionPredicate != null);
        Preconditions.checkArgument(view != null);
        Preconditions.checkArgument(scroller != null);
        Preconditions.checkArgument(lock != null ? true : z);
        this.mSelectionMgr = selectionTracker;
        this.mSelectionPredicate = selectionPredicate;
        this.mView = view;
        this.mScroller = scroller;
        this.mLock = lock;
    }

    /* access modifiers changed from: package-private */
    public void start() {
        if (!this.mStarted) {
            this.mStarted = true;
            this.mLock.start();
        }
    }

    public boolean onInterceptTouchEvent(RecyclerView unused, MotionEvent e) {
        if (this.mStarted) {
            onTouchEvent(unused, e);
        }
        int actionMasked = e.getActionMasked();
        if (actionMasked == 1 || actionMasked == 2) {
            return this.mStarted;
        }
        return false;
    }

    public void onTouchEvent(RecyclerView unused, MotionEvent e) {
        if (this.mStarted) {
            if (!this.mSelectionMgr.isRangeActive()) {
                Log.e(TAG, "Internal state of GestureSelectionHelper out of sync w/ SelectionTracker (isRangeActive is false). Ignoring event and resetting state.");
                endSelection();
            }
            int actionMasked = e.getActionMasked();
            if (actionMasked == 1) {
                handleUpEvent();
            } else if (actionMasked == 2) {
                handleMoveEvent(e);
            }
        }
    }

    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {
    }

    private void handleUpEvent() {
        this.mSelectionMgr.mergeProvisionalSelection();
        endSelection();
    }

    public void reset() {
        this.mStarted = false;
        this.mScroller.reset();
    }

    public boolean isResetRequired() {
        return this.mStarted;
    }

    private void endSelection() {
        this.mStarted = false;
        this.mScroller.reset();
        this.mLock.stop();
    }

    private void handleMoveEvent(MotionEvent e) {
        if (!this.mStarted) {
            Log.e(TAG, "Received event while not started.");
        }
        int lastGlidedItemPos = this.mView.getLastGlidedItemPosition(e);
        if (this.mSelectionPredicate.canSetStateAtPosition(lastGlidedItemPos, true)) {
            extendSelection(lastGlidedItemPos);
        }
        this.mScroller.scroll(MotionEvents.getOrigin(e));
    }

    static float getInboundY(float max, float y) {
        if (y < 0.0f) {
            return 0.0f;
        }
        if (y > max) {
            return max;
        }
        return y;
    }

    private void extendSelection(int endPos) {
        this.mSelectionMgr.extendProvisionalRange(endPos);
    }

    static GestureSelectionHelper create(SelectionTracker<?> selectionMgr, SelectionTracker.SelectionPredicate<?> selectionPredicate, RecyclerView recyclerView, AutoScroller scroller, OperationMonitor lock) {
        return new GestureSelectionHelper(selectionMgr, selectionPredicate, new RecyclerViewDelegate(recyclerView), scroller, lock);
    }

    static abstract class ViewDelegate {
        /* access modifiers changed from: package-private */
        public abstract int getHeight();

        /* access modifiers changed from: package-private */
        public abstract int getItemUnder(MotionEvent motionEvent);

        /* access modifiers changed from: package-private */
        public abstract int getLastGlidedItemPosition(MotionEvent motionEvent);

        ViewDelegate() {
        }
    }

    static final class RecyclerViewDelegate extends ViewDelegate {
        private final RecyclerView mRecyclerView;

        RecyclerViewDelegate(RecyclerView recyclerView) {
            Preconditions.checkArgument(recyclerView != null);
            this.mRecyclerView = recyclerView;
        }

        /* access modifiers changed from: package-private */
        public int getHeight() {
            return this.mRecyclerView.getHeight();
        }

        /* access modifiers changed from: package-private */
        public int getItemUnder(MotionEvent e) {
            View child = this.mRecyclerView.findChildViewUnder(e.getX(), e.getY());
            if (child != null) {
                return this.mRecyclerView.getChildAdapterPosition(child);
            }
            return -1;
        }

        /* access modifiers changed from: package-private */
        public int getLastGlidedItemPosition(MotionEvent e) {
            View lastItem = this.mRecyclerView.getLayoutManager().getChildAt(this.mRecyclerView.getLayoutManager().getChildCount() - 1);
            boolean pastLastItem = isPastLastItem(lastItem.getTop(), lastItem.getLeft(), lastItem.getRight(), e, ViewCompat.getLayoutDirection(this.mRecyclerView));
            float inboundY = GestureSelectionHelper.getInboundY((float) this.mRecyclerView.getHeight(), e.getY());
            RecyclerView recyclerView = this.mRecyclerView;
            if (pastLastItem) {
                return recyclerView.getAdapter().getItemCount() - 1;
            }
            return recyclerView.getChildAdapterPosition(recyclerView.findChildViewUnder(e.getX(), inboundY));
        }

        static boolean isPastLastItem(int top, int left, int right, MotionEvent e, int direction) {
            if (direction == 0) {
                if (e.getX() <= ((float) right) || e.getY() <= ((float) top)) {
                    return false;
                }
                return true;
            } else if (e.getX() >= ((float) left) || e.getY() <= ((float) top)) {
                return false;
            } else {
                return true;
            }
        }
    }
}
