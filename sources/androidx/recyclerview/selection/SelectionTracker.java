package androidx.recyclerview.selection;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import androidx.core.util.Preconditions;
import androidx.recyclerview.selection.BandPredicate;
import androidx.recyclerview.selection.ItemDetailsLookup;
import androidx.recyclerview.widget.RecyclerView;
import java.util.Set;

public abstract class SelectionTracker<K> {
    public static final String SELECTION_CHANGED_MARKER = "Selection-Changed";
    private static final String TAG = "SelectionTracker";

    public static abstract class SelectionPredicate<K> {
        public abstract boolean canSelectMultiple();

        public abstract boolean canSetStateAtPosition(int i, boolean z);

        public abstract boolean canSetStateForKey(K k, boolean z);
    }

    public abstract void addObserver(SelectionObserver<K> selectionObserver);

    public abstract void anchorRange(int i);

    /* access modifiers changed from: protected */
    public abstract void clearProvisionalSelection();

    public abstract boolean clearSelection();

    public abstract void copySelection(MutableSelection<K> mutableSelection);

    public abstract boolean deselect(K k);

    public abstract void endRange();

    /* access modifiers changed from: protected */
    public abstract void extendProvisionalRange(int i);

    public abstract void extendRange(int i);

    /* access modifiers changed from: protected */
    public abstract RecyclerView.AdapterDataObserver getAdapterDataObserver();

    public abstract Selection<K> getSelection();

    public abstract boolean hasSelection();

    public abstract boolean isRangeActive();

    public abstract boolean isSelected(K k);

    /* access modifiers changed from: protected */
    public abstract void mergeProvisionalSelection();

    public abstract void onRestoreInstanceState(Bundle bundle);

    public abstract void onSaveInstanceState(Bundle bundle);

    /* access modifiers changed from: protected */
    public abstract void restoreSelection(Selection<K> selection);

    public abstract boolean select(K k);

    public abstract boolean setItemsSelected(Iterable<K> iterable, boolean z);

    /* access modifiers changed from: protected */
    public abstract void setProvisionalSelection(Set<K> set);

    public abstract void startRange(int i);

    public static abstract class SelectionObserver<K> {
        public void onItemStateChanged(K k, boolean selected) {
        }

        /* access modifiers changed from: protected */
        public void onSelectionCleared() {
        }

        public void onSelectionRefresh() {
        }

        public void onSelectionChanged() {
        }

        public void onSelectionRestored() {
        }
    }

    public static final class Builder<K> {
        private final RecyclerView.Adapter<?> mAdapter;
        private int mBandOverlayId = C2225R.C2226drawable.selection_band_overlay;
        private BandPredicate mBandPredicate;
        private final Context mContext;
        private ItemDetailsLookup<K> mDetailsLookup;
        private FocusDelegate<K> mFocusDelegate = FocusDelegate.dummy();
        private int[] mGestureToolTypes;
        private ItemKeyProvider<K> mKeyProvider;
        private OperationMonitor mMonitor = new OperationMonitor();
        private OnContextClickListener mOnContextClickListener;
        private OnDragInitiatedListener mOnDragInitiatedListener;
        private OnItemActivatedListener<K> mOnItemActivatedListener;
        private int[] mPointerToolTypes;
        final RecyclerView mRecyclerView;
        private final String mSelectionId;
        SelectionPredicate<K> mSelectionPredicate = SelectionPredicates.createSelectAnything();
        private final StorageStrategy<K> mStorage;

        public Builder(String selectionId, RecyclerView recyclerView, ItemKeyProvider<K> keyProvider, ItemDetailsLookup<K> detailsLookup, StorageStrategy<K> storage) {
            boolean z = true;
            this.mGestureToolTypes = new int[]{1};
            this.mPointerToolTypes = new int[]{3};
            Preconditions.checkArgument(selectionId != null);
            Preconditions.checkArgument(!selectionId.trim().isEmpty());
            Preconditions.checkArgument(recyclerView != null);
            this.mSelectionId = selectionId;
            this.mRecyclerView = recyclerView;
            this.mContext = recyclerView.getContext();
            RecyclerView.Adapter<?> adapter = recyclerView.getAdapter();
            this.mAdapter = adapter;
            Preconditions.checkArgument(adapter != null);
            Preconditions.checkArgument(keyProvider != null);
            Preconditions.checkArgument(detailsLookup != null);
            Preconditions.checkArgument(storage == null ? false : z);
            this.mDetailsLookup = detailsLookup;
            this.mKeyProvider = keyProvider;
            this.mStorage = storage;
            this.mBandPredicate = new BandPredicate.NonDraggableArea(recyclerView, detailsLookup);
        }

        public Builder<K> withSelectionPredicate(SelectionPredicate<K> predicate) {
            Preconditions.checkArgument(predicate != null);
            this.mSelectionPredicate = predicate;
            return this;
        }

        public Builder<K> withOperationMonitor(OperationMonitor monitor) {
            Preconditions.checkArgument(monitor != null);
            this.mMonitor = monitor;
            return this;
        }

        public Builder<K> withFocusDelegate(FocusDelegate<K> delegate) {
            Preconditions.checkArgument(delegate != null);
            this.mFocusDelegate = delegate;
            return this;
        }

        public Builder<K> withOnItemActivatedListener(OnItemActivatedListener<K> listener) {
            Preconditions.checkArgument(listener != null);
            this.mOnItemActivatedListener = listener;
            return this;
        }

        public Builder<K> withOnContextClickListener(OnContextClickListener listener) {
            Preconditions.checkArgument(listener != null);
            this.mOnContextClickListener = listener;
            return this;
        }

        public Builder<K> withOnDragInitiatedListener(OnDragInitiatedListener listener) {
            Preconditions.checkArgument(listener != null);
            this.mOnDragInitiatedListener = listener;
            return this;
        }

        @Deprecated
        public Builder<K> withGestureTooltypes(int... toolTypes) {
            Log.w(SelectionTracker.TAG, "Setting gestureTooltypes is likely to result in unexpected behavior.");
            this.mGestureToolTypes = toolTypes;
            return this;
        }

        public Builder<K> withBandOverlay(int bandOverlayId) {
            this.mBandOverlayId = bandOverlayId;
            return this;
        }

        public Builder<K> withBandPredicate(BandPredicate bandPredicate) {
            this.mBandPredicate = bandPredicate;
            return this;
        }

        @Deprecated
        public Builder<K> withPointerTooltypes(int... toolTypes) {
            Log.w(SelectionTracker.TAG, "Setting pointerTooltypes is likely to result in unexpected behavior.");
            this.mPointerToolTypes = toolTypes;
            return this;
        }

        public SelectionTracker<K> build() {
            BandSelectionHelper<K> bandHelper;
            DefaultSelectionTracker<K> tracker = new DefaultSelectionTracker<>(this.mSelectionId, this.mKeyProvider, this.mSelectionPredicate, this.mStorage);
            EventBridge.install(this.mAdapter, tracker, this.mKeyProvider);
            AutoScroller viewAutoScroller = new ViewAutoScroller(ViewAutoScroller.createScrollHost(this.mRecyclerView));
            GestureRouter<MotionInputHandler<K>> gestureRouter = new GestureRouter<>();
            GestureDetector gestureDetector = new GestureDetector(this.mContext, gestureRouter);
            final GestureSelectionHelper gestureHelper = GestureSelectionHelper.create(tracker, this.mSelectionPredicate, this.mRecyclerView, viewAutoScroller, this.mMonitor);
            EventRouter eventRouter = new EventRouter();
            this.mRecyclerView.addOnItemTouchListener(eventRouter);
            this.mRecyclerView.addOnItemTouchListener(new GestureDetectorOnItemTouchListenerAdapter(gestureDetector));
            ResetManager<K> resetMgr = new ResetManager<>();
            tracker.addObserver(resetMgr.getSelectionObserver());
            eventRouter.set(0, resetMgr.getInputListener());
            resetMgr.addResetHandler(tracker);
            resetMgr.addResetHandler(this.mMonitor.asResettable());
            resetMgr.addResetHandler(gestureHelper);
            OnDragInitiatedListener onDragInitiatedListener = this.mOnDragInitiatedListener;
            if (onDragInitiatedListener == null) {
                onDragInitiatedListener = new OnDragInitiatedListener() {
                    public boolean onDragInitiated(MotionEvent e) {
                        return false;
                    }
                };
            }
            this.mOnDragInitiatedListener = onDragInitiatedListener;
            OnItemActivatedListener<K> onItemActivatedListener = this.mOnItemActivatedListener;
            if (onItemActivatedListener == null) {
                onItemActivatedListener = new OnItemActivatedListener<K>() {
                    public boolean onItemActivated(ItemDetailsLookup.ItemDetails<K> itemDetails, MotionEvent e) {
                        return false;
                    }
                };
            }
            this.mOnItemActivatedListener = onItemActivatedListener;
            OnContextClickListener onContextClickListener = this.mOnContextClickListener;
            if (onContextClickListener == null) {
                onContextClickListener = new OnContextClickListener() {
                    public boolean onContextClick(MotionEvent e) {
                        return false;
                    }
                };
            }
            this.mOnContextClickListener = onContextClickListener;
            GestureDetector gestureDetector2 = gestureDetector;
            AutoScroller scroller = viewAutoScroller;
            ResetManager<K> resetMgr2 = resetMgr;
            EventRouter eventRouter2 = eventRouter;
            TouchInputHandler touchInputHandler = new TouchInputHandler(tracker, this.mKeyProvider, this.mDetailsLookup, this.mSelectionPredicate, new Runnable() {
                public void run() {
                    if (Builder.this.mSelectionPredicate.canSelectMultiple()) {
                        gestureHelper.start();
                    }
                }
            }, this.mOnDragInitiatedListener, this.mOnItemActivatedListener, this.mFocusDelegate, new Runnable() {
                public void run() {
                    Builder.this.mRecyclerView.performHapticFeedback(0);
                }
            });
            for (int toolType : this.mGestureToolTypes) {
                gestureRouter.register(toolType, touchInputHandler);
                eventRouter2.set(toolType, gestureHelper);
            }
            MouseInputHandler mouseInputHandler = new MouseInputHandler(tracker, this.mKeyProvider, this.mDetailsLookup, this.mOnContextClickListener, this.mOnItemActivatedListener, this.mFocusDelegate);
            for (int toolType2 : this.mPointerToolTypes) {
                gestureRouter.register(toolType2, mouseInputHandler);
            }
            if (!this.mKeyProvider.hasAccess(0)) {
            } else if (this.mSelectionPredicate.canSelectMultiple()) {
                bandHelper = BandSelectionHelper.create(this.mRecyclerView, scroller, this.mBandOverlayId, this.mKeyProvider, tracker, this.mSelectionPredicate, this.mBandPredicate, this.mFocusDelegate, this.mMonitor);
                resetMgr2.addResetHandler(bandHelper);
                eventRouter2.set(3, new PointerDragEventInterceptor(this.mDetailsLookup, this.mOnDragInitiatedListener, bandHelper));
                return tracker;
            }
            bandHelper = null;
            eventRouter2.set(3, new PointerDragEventInterceptor(this.mDetailsLookup, this.mOnDragInitiatedListener, bandHelper));
            return tracker;
        }
    }
}
