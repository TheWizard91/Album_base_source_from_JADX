package androidx.recyclerview.selection;

import android.os.Bundle;
import android.util.Log;
import androidx.core.util.Preconditions;
import androidx.recyclerview.selection.Range;
import androidx.recyclerview.selection.SelectionTracker;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class DefaultSelectionTracker<K> extends SelectionTracker<K> implements Resettable {
    private static final String EXTRA_SELECTION_PREFIX = "androidx.recyclerview.selection";
    private static final String TAG = "DefaultSelectionTracker";
    private final AdapterObserver mAdapterObserver;
    private final ItemKeyProvider<K> mKeyProvider;
    private final List<SelectionTracker.SelectionObserver<K>> mObservers = new ArrayList(1);
    private Range mRange;
    private final DefaultSelectionTracker<K>.RangeCallbacks mRangeCallbacks;
    private final Selection<K> mSelection = new Selection<>();
    private final String mSelectionId;
    private final SelectionTracker.SelectionPredicate<K> mSelectionPredicate;
    private final boolean mSingleSelect;
    private final StorageStrategy<K> mStorage;

    public DefaultSelectionTracker(String selectionId, ItemKeyProvider<K> keyProvider, SelectionTracker.SelectionPredicate<K> selectionPredicate, StorageStrategy<K> storage) {
        boolean z = false;
        Preconditions.checkArgument(selectionId != null);
        Preconditions.checkArgument(!selectionId.trim().isEmpty());
        Preconditions.checkArgument(keyProvider != null);
        Preconditions.checkArgument(selectionPredicate != null);
        Preconditions.checkArgument(storage != null ? true : z);
        this.mSelectionId = selectionId;
        this.mKeyProvider = keyProvider;
        this.mSelectionPredicate = selectionPredicate;
        this.mStorage = storage;
        this.mRangeCallbacks = new RangeCallbacks();
        this.mSingleSelect = !selectionPredicate.canSelectMultiple();
        this.mAdapterObserver = new AdapterObserver(this);
    }

    public void addObserver(SelectionTracker.SelectionObserver<K> callback) {
        Preconditions.checkArgument(callback != null);
        this.mObservers.add(callback);
    }

    public boolean hasSelection() {
        return !this.mSelection.isEmpty();
    }

    public Selection<K> getSelection() {
        return this.mSelection;
    }

    public void copySelection(MutableSelection<K> dest) {
        dest.copyFrom(this.mSelection);
    }

    public boolean isSelected(K key) {
        return this.mSelection.contains(key);
    }

    /* access modifiers changed from: protected */
    public void restoreSelection(Selection<K> other) {
        Preconditions.checkArgument(other != null);
        setItemsSelectedQuietly(other.mSelection, true);
        notifySelectionRestored();
    }

    public boolean setItemsSelected(Iterable<K> keys, boolean selected) {
        boolean changed = setItemsSelectedQuietly(keys, selected);
        notifySelectionChanged();
        return changed;
    }

    private boolean setItemsSelectedQuietly(Iterable<K> keys, boolean selected) {
        boolean changed = false;
        for (K key : keys) {
            boolean itemChanged = true;
            if (selected) {
                if (!canSetState(key, true) || !this.mSelection.add(key)) {
                    itemChanged = false;
                }
            } else if (!canSetState(key, false) || !this.mSelection.remove(key)) {
                itemChanged = false;
            }
            if (itemChanged) {
                notifyItemStateChanged(key, selected);
            }
            changed |= itemChanged;
        }
        return changed;
    }

    public boolean clearSelection() {
        if (!hasSelection()) {
            return false;
        }
        clearProvisionalSelection();
        clearPrimarySelection();
        notifySelectionCleared();
        return true;
    }

    private void clearPrimarySelection() {
        if (hasSelection()) {
            notifySelectionCleared(clearSelectionQuietly());
            notifySelectionChanged();
        }
    }

    private Selection<K> clearSelectionQuietly() {
        this.mRange = null;
        MutableSelection<K> prevSelection = new MutableSelection<>();
        if (hasSelection()) {
            copySelection(prevSelection);
            this.mSelection.clear();
        }
        return prevSelection;
    }

    public void reset() {
        clearSelection();
        this.mRange = null;
    }

    public boolean isResetRequired() {
        return hasSelection() || isRangeActive();
    }

    public boolean select(K key) {
        Preconditions.checkArgument(key != null);
        if (this.mSelection.contains(key) || !canSetState(key, true)) {
            return false;
        }
        if (this.mSingleSelect && hasSelection()) {
            notifySelectionCleared(clearSelectionQuietly());
        }
        this.mSelection.add(key);
        notifyItemStateChanged(key, true);
        notifySelectionChanged();
        return true;
    }

    public boolean deselect(K key) {
        Preconditions.checkArgument(key != null);
        if (!this.mSelection.contains(key) || !canSetState(key, false)) {
            return false;
        }
        this.mSelection.remove(key);
        notifyItemStateChanged(key, false);
        notifySelectionChanged();
        if (this.mSelection.isEmpty() && isRangeActive()) {
            endRange();
        }
        return true;
    }

    public void startRange(int position) {
        if (this.mSelection.contains(this.mKeyProvider.getKey(position)) || select(this.mKeyProvider.getKey(position))) {
            anchorRange(position);
        }
    }

    public void extendRange(int position) {
        extendRange(position, 0);
    }

    public void endRange() {
        this.mRange = null;
        clearProvisionalSelection();
    }

    public void anchorRange(int position) {
        Preconditions.checkArgument(position != -1);
        Preconditions.checkArgument(this.mSelection.contains(this.mKeyProvider.getKey(position)));
        this.mRange = new Range(position, this.mRangeCallbacks);
    }

    public void extendProvisionalRange(int position) {
        if (!this.mSingleSelect) {
            extendRange(position, 1);
        }
    }

    private void extendRange(int position, int type) {
        if (!isRangeActive()) {
            Log.e(TAG, "Ignoring attempt to extend unestablished range. Ignoring.");
        } else if (position == -1) {
            Log.w(TAG, "Ignoring attempt to extend range to invalid position: " + position);
        } else {
            this.mRange.extendRange(position, type);
            notifySelectionChanged();
        }
    }

    public void setProvisionalSelection(Set<K> newSelection) {
        if (!this.mSingleSelect) {
            for (Map.Entry<K, Boolean> entry : this.mSelection.setProvisionalSelection(newSelection).entrySet()) {
                notifyItemStateChanged(entry.getKey(), entry.getValue().booleanValue());
            }
            notifySelectionChanged();
        }
    }

    public void mergeProvisionalSelection() {
        this.mSelection.mergeProvisionalSelection();
        notifySelectionChanged();
    }

    public void clearProvisionalSelection() {
        for (K key : this.mSelection.mProvisionalSelection) {
            notifyItemStateChanged(key, false);
        }
        this.mSelection.clearProvisionalSelection();
    }

    public boolean isRangeActive() {
        return this.mRange != null;
    }

    private boolean canSetState(K key, boolean nextState) {
        return this.mSelectionPredicate.canSetStateForKey(key, nextState);
    }

    /* access modifiers changed from: protected */
    public RecyclerView.AdapterDataObserver getAdapterDataObserver() {
        return this.mAdapterObserver;
    }

    /* access modifiers changed from: package-private */
    public void onDataSetChanged() {
        this.mSelection.clearProvisionalSelection();
        notifySelectionRefresh();
        List<K> toRemove = null;
        Iterator<K> it = this.mSelection.iterator();
        while (it.hasNext()) {
            K key = it.next();
            if (!canSetState(key, true)) {
                if (toRemove == null) {
                    toRemove = new ArrayList<>();
                }
                toRemove.add(key);
            } else {
                for (int i = this.mObservers.size() - 1; i >= 0; i--) {
                    this.mObservers.get(i).onItemStateChanged(key, true);
                }
            }
        }
        if (toRemove != null) {
            for (K key2 : toRemove) {
                deselect(key2);
            }
        }
        notifySelectionChanged();
    }

    private void notifyItemStateChanged(K key, boolean selected) {
        Preconditions.checkArgument(key != null);
        for (int i = this.mObservers.size() - 1; i >= 0; i--) {
            this.mObservers.get(i).onItemStateChanged(key, selected);
        }
    }

    private void notifySelectionCleared() {
        for (SelectionTracker.SelectionObserver<K> observer : this.mObservers) {
            observer.onSelectionCleared();
        }
    }

    private void notifySelectionCleared(Selection<K> selection) {
        for (K key : selection.mSelection) {
            notifyItemStateChanged(key, false);
        }
        for (K key2 : selection.mProvisionalSelection) {
            notifyItemStateChanged(key2, false);
        }
    }

    private void notifySelectionChanged() {
        for (int i = this.mObservers.size() - 1; i >= 0; i--) {
            this.mObservers.get(i).onSelectionChanged();
        }
    }

    private void notifySelectionRestored() {
        for (int i = this.mObservers.size() - 1; i >= 0; i--) {
            this.mObservers.get(i).onSelectionRestored();
        }
    }

    private void notifySelectionRefresh() {
        for (int i = this.mObservers.size() - 1; i >= 0; i--) {
            this.mObservers.get(i).onSelectionRefresh();
        }
    }

    /* access modifiers changed from: package-private */
    public void updateForRegularRange(int begin, int end, boolean selected) {
        Preconditions.checkArgument(end >= begin);
        for (int i = begin; i <= end; i++) {
            K key = this.mKeyProvider.getKey(i);
            if (key != null) {
                if (selected) {
                    select(key);
                } else {
                    deselect(key);
                }
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void updateForProvisionalRange(int begin, int end, boolean selected) {
        Preconditions.checkArgument(end >= begin);
        for (int i = begin; i <= end; i++) {
            K key = this.mKeyProvider.getKey(i);
            if (key != null) {
                boolean changedState = false;
                if (!selected) {
                    this.mSelection.mProvisionalSelection.remove(key);
                    changedState = true;
                } else if (canSetState(key, true) && !this.mSelection.mSelection.contains(key)) {
                    this.mSelection.mProvisionalSelection.add(key);
                    changedState = true;
                }
                if (changedState) {
                    notifyItemStateChanged(key, selected);
                }
            }
        }
        notifySelectionChanged();
    }

    /* access modifiers changed from: package-private */
    public String getInstanceStateKey() {
        return "androidx.recyclerview.selection:" + this.mSelectionId;
    }

    public final void onSaveInstanceState(Bundle state) {
        if (!this.mSelection.isEmpty()) {
            state.putBundle(getInstanceStateKey(), this.mStorage.asBundle(this.mSelection));
        }
    }

    public final void onRestoreInstanceState(Bundle state) {
        Bundle selectionState;
        Selection<K> selection;
        if (state != null && (selectionState = state.getBundle(getInstanceStateKey())) != null && (selection = this.mStorage.asSelection(selectionState)) != null && !selection.isEmpty()) {
            restoreSelection(selection);
        }
    }

    private final class RangeCallbacks extends Range.Callbacks {
        RangeCallbacks() {
        }

        /* access modifiers changed from: package-private */
        public void updateForRange(int begin, int end, boolean selected, int type) {
            if (type == 0) {
                DefaultSelectionTracker.this.updateForRegularRange(begin, end, selected);
            } else if (type == 1) {
                DefaultSelectionTracker.this.updateForProvisionalRange(begin, end, selected);
            } else {
                throw new IllegalArgumentException("Invalid range type: " + type);
            }
        }
    }

    private static final class AdapterObserver extends RecyclerView.AdapterDataObserver {
        private final DefaultSelectionTracker<?> mSelectionTracker;

        AdapterObserver(DefaultSelectionTracker<?> selectionTracker) {
            Preconditions.checkArgument(selectionTracker != null);
            this.mSelectionTracker = selectionTracker;
        }

        public void onChanged() {
            this.mSelectionTracker.onDataSetChanged();
        }

        public void onItemRangeChanged(int startPosition, int itemCount, Object payload) {
            if (!SelectionTracker.SELECTION_CHANGED_MARKER.equals(payload)) {
                this.mSelectionTracker.onDataSetChanged();
            }
        }

        public void onItemRangeInserted(int startPosition, int itemCount) {
            this.mSelectionTracker.endRange();
        }

        public void onItemRangeRemoved(int startPosition, int itemCount) {
            this.mSelectionTracker.endRange();
        }

        public void onItemRangeMoved(int fromPosition, int toPosition, int itemCount) {
            this.mSelectionTracker.endRange();
        }
    }
}
