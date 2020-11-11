package androidx.recyclerview.selection;

import android.view.MotionEvent;

public abstract class ItemDetailsLookup<K> {
    public abstract ItemDetails<K> getItemDetails(MotionEvent motionEvent);

    /* access modifiers changed from: package-private */
    public final boolean overItem(MotionEvent e) {
        return getItemPosition(e) != -1;
    }

    /* access modifiers changed from: protected */
    public boolean overItemWithSelectionKey(MotionEvent e) {
        return overItem(e) && hasSelectionKey(getItemDetails(e));
    }

    /* access modifiers changed from: package-private */
    public final boolean inItemDragRegion(MotionEvent e) {
        return overItem(e) && getItemDetails(e).inDragRegion(e);
    }

    /* access modifiers changed from: package-private */
    public final boolean inItemSelectRegion(MotionEvent e) {
        return overItem(e) && getItemDetails(e).inSelectionHotspot(e);
    }

    /* access modifiers changed from: package-private */
    public final int getItemPosition(MotionEvent e) {
        ItemDetails<?> item = getItemDetails(e);
        if (item != null) {
            return item.getPosition();
        }
        return -1;
    }

    private static boolean hasSelectionKey(ItemDetails<?> item) {
        return (item == null || item.getSelectionKey() == null) ? false : true;
    }

    public static abstract class ItemDetails<K> {
        public abstract int getPosition();

        public abstract K getSelectionKey();

        public boolean hasSelectionKey() {
            return getSelectionKey() != null;
        }

        public boolean inSelectionHotspot(MotionEvent e) {
            return false;
        }

        public boolean inDragRegion(MotionEvent e) {
            return false;
        }

        public boolean equals(Object obj) {
            return (obj instanceof ItemDetails) && isEqualTo((ItemDetails) obj);
        }

        private boolean isEqualTo(ItemDetails<?> other) {
            boolean sameKeys;
            K key = getSelectionKey();
            if (key == null) {
                sameKeys = other.getSelectionKey() == null;
            } else {
                sameKeys = key.equals(other.getSelectionKey());
            }
            if (!sameKeys || getPosition() != other.getPosition()) {
                return false;
            }
            return true;
        }

        public int hashCode() {
            return getPosition() >>> 8;
        }
    }
}
