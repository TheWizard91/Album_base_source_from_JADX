package androidx.recyclerview.selection;

import androidx.recyclerview.selection.SelectionTracker;

public final class SelectionPredicates {
    private SelectionPredicates() {
    }

    public static <K> SelectionTracker.SelectionPredicate<K> createSelectAnything() {
        return new SelectionTracker.SelectionPredicate<K>() {
            public boolean canSetStateForKey(K k, boolean nextState) {
                return true;
            }

            public boolean canSetStateAtPosition(int position, boolean nextState) {
                return true;
            }

            public boolean canSelectMultiple() {
                return true;
            }
        };
    }

    public static <K> SelectionTracker.SelectionPredicate<K> createSelectSingleAnything() {
        return new SelectionTracker.SelectionPredicate<K>() {
            public boolean canSetStateForKey(K k, boolean nextState) {
                return true;
            }

            public boolean canSetStateAtPosition(int position, boolean nextState) {
                return true;
            }

            public boolean canSelectMultiple() {
                return false;
            }
        };
    }
}
