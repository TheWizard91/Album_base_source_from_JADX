package androidx.recyclerview.selection;

import android.graphics.Point;
import android.graphics.Rect;
import android.view.MotionEvent;
import androidx.core.util.Preconditions;
import androidx.recyclerview.selection.GridModel;
import androidx.recyclerview.selection.SelectionTracker;
import androidx.recyclerview.widget.RecyclerView;
import java.util.Set;

class BandSelectionHelper<K> implements RecyclerView.OnItemTouchListener, Resettable {
    static final boolean DEBUG = false;
    static final String TAG = "BandSelectionHelper";
    private final BandPredicate mBandPredicate;
    private Point mCurrentPosition;
    private final FocusDelegate<K> mFocusDelegate;
    private final GridModel.SelectionObserver<K> mGridObserver;
    private final BandHost<K> mHost;
    private final ItemKeyProvider<K> mKeyProvider;
    private final OperationMonitor mLock;
    private GridModel<K> mModel;
    private Point mOrigin;
    private final AutoScroller mScroller;
    final SelectionTracker<K> mSelectionTracker;

    BandSelectionHelper(BandHost<K> host, AutoScroller scroller, ItemKeyProvider<K> keyProvider, SelectionTracker<K> selectionTracker, BandPredicate bandPredicate, FocusDelegate<K> focusDelegate, OperationMonitor lock) {
        boolean z = true;
        Preconditions.checkArgument(host != null);
        Preconditions.checkArgument(scroller != null);
        Preconditions.checkArgument(keyProvider != null);
        Preconditions.checkArgument(selectionTracker != null);
        Preconditions.checkArgument(bandPredicate != null);
        Preconditions.checkArgument(focusDelegate != null);
        Preconditions.checkArgument(lock == null ? false : z);
        this.mHost = host;
        this.mKeyProvider = keyProvider;
        this.mSelectionTracker = selectionTracker;
        this.mBandPredicate = bandPredicate;
        this.mFocusDelegate = focusDelegate;
        this.mLock = lock;
        host.addOnScrollListener(new RecyclerView.OnScrollListener() {
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                BandSelectionHelper.this.onScrolled(recyclerView, dx, dy);
            }
        });
        this.mScroller = scroller;
        this.mGridObserver = new GridModel.SelectionObserver<K>() {
            public void onSelectionChanged(Set<K> updatedSelection) {
                BandSelectionHelper.this.mSelectionTracker.setProvisionalSelection(updatedSelection);
            }
        };
    }

    static <K> BandSelectionHelper<K> create(RecyclerView recyclerView, AutoScroller scroller, int bandOverlayId, ItemKeyProvider<K> keyProvider, SelectionTracker<K> selectionTracker, SelectionTracker.SelectionPredicate<K> selectionPredicate, BandPredicate bandPredicate, FocusDelegate<K> focusDelegate, OperationMonitor lock) {
        RecyclerView recyclerView2 = recyclerView;
        int i = bandOverlayId;
        return new BandSelectionHelper(new DefaultBandHost(recyclerView, bandOverlayId, keyProvider, selectionPredicate), scroller, keyProvider, selectionTracker, bandPredicate, focusDelegate, lock);
    }

    private boolean isActive() {
        return this.mModel != null;
    }

    public void reset() {
        if (isActive()) {
            this.mHost.hideBand();
            GridModel<K> gridModel = this.mModel;
            if (gridModel != null) {
                gridModel.stopCapturing();
                this.mModel.onDestroy();
            }
            this.mModel = null;
            this.mOrigin = null;
            this.mScroller.reset();
        }
    }

    public boolean isResetRequired() {
        return isActive();
    }

    private boolean shouldStart(MotionEvent e) {
        return MotionEvents.isPrimaryMouseButtonPressed(e) && MotionEvents.isActionMove(e) && this.mBandPredicate.canInitiate(e) && !isActive();
    }

    private boolean shouldStop(MotionEvent e) {
        return isActive() && MotionEvents.isActionUp(e);
    }

    public boolean onInterceptTouchEvent(RecyclerView unused, MotionEvent e) {
        if (shouldStart(e)) {
            startBandSelect(e);
        } else if (shouldStop(e)) {
            endBandSelect();
        }
        return isActive();
    }

    public void onTouchEvent(RecyclerView unused, MotionEvent e) {
        if (shouldStop(e)) {
            endBandSelect();
        } else if (isActive()) {
            Point origin = MotionEvents.getOrigin(e);
            this.mCurrentPosition = origin;
            this.mModel.resizeSelection(origin);
            resizeBand();
            this.mScroller.scroll(this.mCurrentPosition);
        }
    }

    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {
    }

    private void startBandSelect(MotionEvent e) {
        if (!MotionEvents.isCtrlKeyPressed(e)) {
            this.mSelectionTracker.clearSelection();
        }
        Point origin = MotionEvents.getOrigin(e);
        GridModel<K> createGridModel = this.mHost.createGridModel();
        this.mModel = createGridModel;
        createGridModel.addOnSelectionChangedListener(this.mGridObserver);
        this.mLock.start();
        this.mFocusDelegate.clearFocus();
        this.mOrigin = origin;
        this.mModel.startCapturing(origin);
    }

    private void resizeBand() {
        this.mHost.showBand(new Rect(Math.min(this.mOrigin.x, this.mCurrentPosition.x), Math.min(this.mOrigin.y, this.mCurrentPosition.y), Math.max(this.mOrigin.x, this.mCurrentPosition.x), Math.max(this.mOrigin.y, this.mCurrentPosition.y)));
    }

    private void endBandSelect() {
        int firstSelected = this.mModel.getPositionNearestOrigin();
        if (firstSelected != -1 && this.mSelectionTracker.isSelected(this.mKeyProvider.getKey(firstSelected))) {
            this.mSelectionTracker.anchorRange(firstSelected);
        }
        this.mSelectionTracker.mergeProvisionalSelection();
        this.mLock.stop();
        this.mHost.hideBand();
        GridModel<K> gridModel = this.mModel;
        if (gridModel != null) {
            gridModel.stopCapturing();
            this.mModel.onDestroy();
        }
        this.mModel = null;
        this.mOrigin = null;
        this.mScroller.reset();
    }

    /* access modifiers changed from: package-private */
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        if (isActive()) {
            this.mOrigin.y -= dy;
            resizeBand();
        }
    }

    static abstract class BandHost<K> {
        /* access modifiers changed from: package-private */
        public abstract void addOnScrollListener(RecyclerView.OnScrollListener onScrollListener);

        /* access modifiers changed from: package-private */
        public abstract GridModel<K> createGridModel();

        /* access modifiers changed from: package-private */
        public abstract void hideBand();

        /* access modifiers changed from: package-private */
        public abstract void showBand(Rect rect);

        BandHost() {
        }
    }
}
