package androidx.recyclerview.selection;

import androidx.recyclerview.selection.ItemDetailsLookup;

public abstract class FocusDelegate<K> {
    public abstract void clearFocus();

    public abstract void focusItem(ItemDetailsLookup.ItemDetails<K> itemDetails);

    public abstract int getFocusedPosition();

    public abstract boolean hasFocusedItem();

    static <K> FocusDelegate<K> dummy() {
        return new FocusDelegate<K>() {
            public void focusItem(ItemDetailsLookup.ItemDetails<K> itemDetails) {
            }

            public boolean hasFocusedItem() {
                return false;
            }

            public int getFocusedPosition() {
                return -1;
            }

            public void clearFocus() {
            }
        };
    }
}
