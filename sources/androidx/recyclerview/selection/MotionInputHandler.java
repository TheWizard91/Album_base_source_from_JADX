package androidx.recyclerview.selection;

import android.view.GestureDetector;
import android.view.MotionEvent;
import androidx.core.util.Preconditions;
import androidx.recyclerview.selection.ItemDetailsLookup;

abstract class MotionInputHandler<K> extends GestureDetector.SimpleOnGestureListener {
    private final FocusDelegate<K> mFocusDelegate;
    private final ItemKeyProvider<K> mKeyProvider;
    protected final SelectionTracker<K> mSelectionTracker;

    MotionInputHandler(SelectionTracker<K> selectionTracker, ItemKeyProvider<K> keyProvider, FocusDelegate<K> focusDelegate) {
        boolean z = true;
        Preconditions.checkArgument(selectionTracker != null);
        Preconditions.checkArgument(keyProvider != null);
        Preconditions.checkArgument(focusDelegate == null ? false : z);
        this.mSelectionTracker = selectionTracker;
        this.mKeyProvider = keyProvider;
        this.mFocusDelegate = focusDelegate;
    }

    /* access modifiers changed from: package-private */
    public final boolean selectItem(ItemDetailsLookup.ItemDetails<K> details) {
        Preconditions.checkArgument(details != null);
        Preconditions.checkArgument(hasPosition(details));
        Preconditions.checkArgument(hasSelectionKey(details));
        if (this.mSelectionTracker.select(details.getSelectionKey())) {
            this.mSelectionTracker.anchorRange(details.getPosition());
        }
        if (this.mSelectionTracker.getSelection().size() == 1) {
            this.mFocusDelegate.focusItem(details);
        } else {
            this.mFocusDelegate.clearFocus();
        }
        return true;
    }

    /* access modifiers changed from: protected */
    public final boolean focusItem(ItemDetailsLookup.ItemDetails<K> details) {
        Preconditions.checkArgument(details != null);
        Preconditions.checkArgument(hasSelectionKey(details));
        this.mSelectionTracker.clearSelection();
        this.mFocusDelegate.focusItem(details);
        return true;
    }

    /* access modifiers changed from: protected */
    public final void extendSelectionRange(ItemDetailsLookup.ItemDetails<K> details) {
        Preconditions.checkState(this.mKeyProvider.hasAccess(0));
        Preconditions.checkArgument(hasPosition(details));
        Preconditions.checkArgument(hasSelectionKey(details));
        this.mSelectionTracker.extendRange(details.getPosition());
        this.mFocusDelegate.focusItem(details);
    }

    /* access modifiers changed from: package-private */
    public final boolean shouldExtendRange(MotionEvent e) {
        if (!MotionEvents.isShiftKeyPressed(e) || !this.mSelectionTracker.isRangeActive() || !this.mKeyProvider.hasAccess(0)) {
            return false;
        }
        return true;
    }

    /* access modifiers changed from: package-private */
    public boolean shouldClearSelection(MotionEvent e, ItemDetailsLookup.ItemDetails<K> item) {
        return !MotionEvents.isCtrlKeyPressed(e) && !item.inSelectionHotspot(e) && !this.mSelectionTracker.isSelected(item.getSelectionKey());
    }

    static boolean hasSelectionKey(ItemDetailsLookup.ItemDetails<?> item) {
        return (item == null || item.getSelectionKey() == null) ? false : true;
    }

    static boolean hasPosition(ItemDetailsLookup.ItemDetails<?> item) {
        return (item == null || item.getPosition() == -1) ? false : true;
    }
}
