package com.felipecsl.asymmetricgridview.library.widget;

import com.felipecsl.asymmetricgridview.library.model.AsymmetricItem;

public final class RowItem<T extends AsymmetricItem> {
    private final int index;
    private final T item;

    public RowItem(int index2, T item2) {
        this.item = item2;
        this.index = index2;
    }

    public T getItem() {
        return this.item;
    }

    public int getIndex() {
        return this.index;
    }
}
