package androidx.recyclerview.selection;

import android.util.Log;
import android.view.MotionEvent;
import androidx.core.util.Preconditions;
import androidx.recyclerview.selection.ItemDetailsLookup;

final class MouseInputHandler<K> extends MotionInputHandler<K> {
    private static final String TAG = "MouseInputDelegate";
    private final ItemDetailsLookup<K> mDetailsLookup;
    private final FocusDelegate<K> mFocusDelegate;
    private boolean mHandledOnDown;
    private boolean mHandledTapUp;
    private final OnContextClickListener mOnContextClickListener;
    private final OnItemActivatedListener<K> mOnItemActivatedListener;

    MouseInputHandler(SelectionTracker<K> selectionTracker, ItemKeyProvider<K> keyProvider, ItemDetailsLookup<K> detailsLookup, OnContextClickListener onContextClickListener, OnItemActivatedListener<K> onItemActivatedListener, FocusDelegate<K> focusDelegate) {
        super(selectionTracker, keyProvider, focusDelegate);
        boolean z = true;
        Preconditions.checkArgument(detailsLookup != null);
        Preconditions.checkArgument(onContextClickListener != null);
        Preconditions.checkArgument(onItemActivatedListener == null ? false : z);
        this.mDetailsLookup = detailsLookup;
        this.mOnContextClickListener = onContextClickListener;
        this.mOnItemActivatedListener = onItemActivatedListener;
        this.mFocusDelegate = focusDelegate;
    }

    public boolean onDown(MotionEvent e) {
        if ((!MotionEvents.isAltKeyPressed(e) || !MotionEvents.isPrimaryMouseButtonPressed(e)) && !MotionEvents.isSecondaryMouseButtonPressed(e)) {
            return false;
        }
        this.mHandledOnDown = true;
        return onRightClick(e);
    }

    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return !MotionEvents.isTouchpadScroll(e2);
    }

    public boolean onSingleTapUp(MotionEvent e) {
        if (this.mHandledOnDown) {
            this.mHandledOnDown = false;
            return false;
        } else if (!this.mDetailsLookup.overItemWithSelectionKey(e)) {
            this.mSelectionTracker.clearSelection();
            this.mFocusDelegate.clearFocus();
            return false;
        } else if (MotionEvents.isTertiaryMouseButtonPressed(e) || !this.mSelectionTracker.hasSelection()) {
            return false;
        } else {
            onItemClick(e, this.mDetailsLookup.getItemDetails(e));
            this.mHandledTapUp = true;
            return true;
        }
    }

    private void onItemClick(MotionEvent e, ItemDetailsLookup.ItemDetails<K> item) {
        if (!this.mSelectionTracker.hasSelection()) {
            Log.e(TAG, "Call to onItemClick w/o selection.");
            return;
        }
        Preconditions.checkArgument(item != null);
        if (shouldExtendRange(e)) {
            extendSelectionRange(item);
            return;
        }
        if (shouldClearSelection(e, item)) {
            this.mSelectionTracker.clearSelection();
        }
        if (!this.mSelectionTracker.isSelected(item.getSelectionKey())) {
            selectOrFocusItem(item, e);
        } else if (this.mSelectionTracker.deselect(item.getSelectionKey())) {
            this.mFocusDelegate.clearFocus();
        }
    }

    public boolean onSingleTapConfirmed(MotionEvent e) {
        ItemDetailsLookup.ItemDetails<K> item;
        if (this.mHandledTapUp) {
            this.mHandledTapUp = false;
            return false;
        } else if (this.mSelectionTracker.hasSelection() || !this.mDetailsLookup.overItem(e) || MotionEvents.isTertiaryMouseButtonPressed(e) || (item = this.mDetailsLookup.getItemDetails(e)) == null || !item.hasSelectionKey()) {
            return false;
        } else {
            if (!this.mFocusDelegate.hasFocusedItem() || !MotionEvents.isShiftKeyPressed(e)) {
                selectOrFocusItem(item, e);
                return true;
            }
            this.mSelectionTracker.startRange(this.mFocusDelegate.getFocusedPosition());
            this.mSelectionTracker.extendRange(item.getPosition());
            return true;
        }
    }

    public boolean onDoubleTap(MotionEvent e) {
        ItemDetailsLookup.ItemDetails<K> item;
        this.mHandledTapUp = false;
        if (this.mDetailsLookup.overItemWithSelectionKey(e) && !MotionEvents.isTertiaryMouseButtonPressed(e) && (item = this.mDetailsLookup.getItemDetails(e)) != null && this.mOnItemActivatedListener.onItemActivated(item, e)) {
            return true;
        }
        return false;
    }

    private boolean onRightClick(MotionEvent e) {
        ItemDetailsLookup.ItemDetails<K> item;
        if (this.mDetailsLookup.overItemWithSelectionKey(e) && (item = this.mDetailsLookup.getItemDetails(e)) != null && !this.mSelectionTracker.isSelected(item.getSelectionKey())) {
            this.mSelectionTracker.clearSelection();
            selectItem(item);
        }
        return this.mOnContextClickListener.onContextClick(e);
    }

    private void selectOrFocusItem(ItemDetailsLookup.ItemDetails<K> item, MotionEvent e) {
        if (item.inSelectionHotspot(e) || MotionEvents.isCtrlKeyPressed(e)) {
            selectItem(item);
        } else {
            focusItem(item);
        }
    }
}
