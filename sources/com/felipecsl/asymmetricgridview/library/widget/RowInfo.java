package com.felipecsl.asymmetricgridview.library.widget;

import android.os.Parcel;
import android.os.Parcelable;
import com.felipecsl.asymmetricgridview.library.model.AsymmetricItem;
import java.util.ArrayList;
import java.util.List;

class RowInfo<T extends AsymmetricItem> implements Parcelable {
    public static final Parcelable.Creator<RowInfo> CREATOR = new Parcelable.Creator<RowInfo>() {
        public RowInfo createFromParcel(Parcel in) {
            return new RowInfo(in);
        }

        public RowInfo[] newArray(int size) {
            return new RowInfo[size];
        }
    };
    private final List<RowItem<T>> items;
    private final int rowHeight;
    private final float spaceLeft;

    public RowInfo(int rowHeight2, List<RowItem<T>> items2, float spaceLeft2) {
        this.rowHeight = rowHeight2;
        this.items = items2;
        this.spaceLeft = spaceLeft2;
    }

    public RowInfo(Parcel in) {
        this.rowHeight = in.readInt();
        this.spaceLeft = in.readFloat();
        int totalItems = in.readInt();
        this.items = new ArrayList();
        ClassLoader classLoader = AsymmetricItem.class.getClassLoader();
        for (int i = 0; i < totalItems; i++) {
            this.items.add(new RowItem(in.readInt(), (AsymmetricItem) in.readParcelable(classLoader)));
        }
    }

    public List<RowItem<T>> getItems() {
        return this.items;
    }

    public int getRowHeight() {
        return this.rowHeight;
    }

    public float getSpaceLeft() {
        return this.spaceLeft;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.rowHeight);
        dest.writeFloat(this.spaceLeft);
        dest.writeInt(this.items.size());
        for (RowItem rowItem : this.items) {
            dest.writeInt(rowItem.getIndex());
            dest.writeParcelable(rowItem.getItem(), 0);
        }
    }
}
