package androidx.recyclerview.selection;

import android.view.MotionEvent;
import androidx.core.util.Preconditions;
import androidx.recyclerview.selection.ItemDetailsLookup;
import androidx.recyclerview.selection.SelectionTracker;

final class TouchInputHandler<K> extends MotionInputHandler<K> {
    private static final String TAG = "TouchInputDelegate";
    private final ItemDetailsLookup<K> mDetailsLookup;
    private final Runnable mGestureStarter;
    private final Runnable mHapticPerformer;
    private final OnDragInitiatedListener mOnDragInitiatedListener;
    private final OnItemActivatedListener<K> mOnItemActivatedListener;
    private final SelectionTracker.SelectionPredicate<K> mSelectionPredicate;

    TouchInputHandler(SelectionTracker<K> selectionTracker, ItemKeyProvider<K> keyProvider, ItemDetailsLookup<K> detailsLookup, SelectionTracker.SelectionPredicate<K> selectionPredicate, Runnable gestureStarter, OnDragInitiatedListener onDragInitiatedListener, OnItemActivatedListener<K> onItemActivatedListener, FocusDelegate<K> focusDelegate, Runnable hapticPerformer) {
        super(selectionTracker, keyProvider, focusDelegate);
        boolean z = true;
        Preconditions.checkArgument(detailsLookup != null);
        Preconditions.checkArgument(selectionPredicate != null);
        Preconditions.checkArgument(gestureStarter != null);
        Preconditions.checkArgument(onItemActivatedListener != null);
        Preconditions.checkArgument(onDragInitiatedListener != null);
        Preconditions.checkArgument(hapticPerformer == null ? false : z);
        this.mDetailsLookup = detailsLookup;
        this.mSelectionPredicate = selectionPredicate;
        this.mGestureStarter = gestureStarter;
        this.mOnItemActivatedListener = onItemActivatedListener;
        this.mOnDragInitiatedListener = onDragInitiatedListener;
        this.mHapticPerformer = hapticPerformer;
    }

    public boolean onSingleTapUp(MotionEvent e) {
        if (!this.mDetailsLookup.overItemWithSelectionKey(e)) {
            this.mSelectionTracker.clearSelection();
            return false;
        }
        ItemDetailsLookup.ItemDetails<K> item = this.mDetailsLookup.getItemDetails(e);
        if (item == null) {
            return false;
        }
        if (this.mSelectionTracker.hasSelection()) {
            if (shouldExtendRange(e)) {
                extendSelectionRange(item);
                return true;
            } else if (this.mSelectionTracker.isSelected(item.getSelectionKey())) {
                this.mSelectionTracker.deselect(item.getSelectionKey());
                return true;
            } else {
                selectItem(item);
                return true;
            }
        } else if (item.inSelectionHotspot(e)) {
            return selectItem(item);
        } else {
            return this.mOnItemActivatedListener.onItemActivated(item, e);
        }
    }

    public void onLongPress(MotionEvent e) {
        ItemDetailsLookup.ItemDetails<K> item;
        if (!this.mDetailsLookup.overItemWithSelectionKey(e) || (item = this.mDetailsLookup.getItemDetails(e)) == null) {
            return;
        }
        if (shouldExtendRange(e)) {
            extendSelectionRange(item);
            this.mHapticPerformer.run();
        } else if (this.mSelectionTracker.isSelected(item.getSelectionKey())) {
            this.mOnDragInitiatedListener.onDragInitiated(e);
            this.mHapticPerformer.run();
        } else if (this.mSelectionPredicate.canSetStateForKey(item.getSelectionKey(), true) && selectItem(item)) {
            if (this.mSelectionPredicate.canSelectMultiple() && this.mSelectionTracker.isRangeActive()) {
                this.mGestureStarter.run();
            }
            this.mHapticPerformer.run();
        }
    }
}
