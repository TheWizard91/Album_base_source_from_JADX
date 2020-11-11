package com.felipecsl.asymmetricgridview.library.widget;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import com.felipecsl.asymmetricgridview.library.Utils;

public class AsymmetricGridView extends ListView {
    private static final int DEFAULT_COLUMN_COUNT = 2;
    private static final String TAG = "AsymmetricGridView";
    protected boolean allowReordering;
    protected boolean debugging;
    protected AsymmetricGridViewAdapter gridAdapter;
    protected int numColumns = 2;
    protected AdapterView.OnItemClickListener onItemClickListener;
    protected AdapterView.OnItemLongClickListener onItemLongClickListener;
    protected int requestedColumnCount;
    protected int requestedColumnWidth;
    protected int requestedHorizontalSpacing;

    public AsymmetricGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.requestedHorizontalSpacing = Utils.dpToPx(context, 5.0f);
        ViewTreeObserver vto = getViewTreeObserver();
        if (vto != null) {
            vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                public void onGlobalLayout() {
                    AsymmetricGridView.this.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                    AsymmetricGridView.this.determineColumns();
                    if (AsymmetricGridView.this.gridAdapter != null) {
                        AsymmetricGridView.this.gridAdapter.recalculateItemsPerRow();
                    }
                }
            });
        }
    }

    public void setOnItemClickListener(AdapterView.OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    /* access modifiers changed from: protected */
    public void fireOnItemClick(int position, View v) {
        AdapterView.OnItemClickListener onItemClickListener2 = this.onItemClickListener;
        if (onItemClickListener2 != null) {
            onItemClickListener2.onItemClick(this, v, position, (long) v.getId());
        }
    }

    public void setOnItemLongClickListener(AdapterView.OnItemLongClickListener listener) {
        this.onItemLongClickListener = listener;
    }

    /* access modifiers changed from: protected */
    public boolean fireOnItemLongClick(int position, View v) {
        AdapterView.OnItemLongClickListener onItemLongClickListener2 = this.onItemLongClickListener;
        if (onItemLongClickListener2 != null) {
            if (onItemLongClickListener2.onItemLongClick(this, v, position, (long) v.getId())) {
                return true;
            }
        }
        return false;
    }

    public void setAdapter(ListAdapter adapter) {
        if (adapter instanceof AsymmetricGridViewAdapter) {
            this.gridAdapter = (AsymmetricGridViewAdapter) adapter;
            super.setAdapter(adapter);
            this.gridAdapter.recalculateItemsPerRow();
            return;
        }
        throw new UnsupportedOperationException("Adapter must be an instance of AsymmetricGridViewAdapter");
    }

    public void setRequestedColumnWidth(int width) {
        this.requestedColumnWidth = width;
    }

    public void setRequestedColumnCount(int requestedColumnCount2) {
        this.requestedColumnCount = requestedColumnCount2;
    }

    public int getRequestedHorizontalSpacing() {
        return this.requestedHorizontalSpacing;
    }

    public void setRequestedHorizontalSpacing(int spacing) {
        this.requestedHorizontalSpacing = spacing;
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        determineColumns();
    }

    public int determineColumns() {
        int numColumns2;
        int availableSpace = getAvailableSpace();
        int i = this.requestedColumnWidth;
        if (i > 0) {
            int i2 = this.requestedHorizontalSpacing;
            numColumns2 = (availableSpace + i2) / (i + i2);
        } else if (this.requestedColumnCount > 0) {
            numColumns2 = this.requestedColumnCount;
        } else {
            numColumns2 = 2;
        }
        if (numColumns2 <= 0) {
            numColumns2 = 1;
        }
        this.numColumns = numColumns2;
        return numColumns2;
    }

    public Parcelable onSaveInstanceState() {
        SavedState ss = new SavedState(super.onSaveInstanceState());
        ss.allowReordering = this.allowReordering;
        ss.debugging = this.debugging;
        ss.numColumns = this.numColumns;
        ss.requestedColumnCount = this.requestedColumnCount;
        ss.requestedColumnWidth = this.requestedColumnWidth;
        ss.requestedHorizontalSpacing = this.requestedHorizontalSpacing;
        return ss;
    }

    public void onRestoreInstanceState(Parcelable state) {
        if (!(state instanceof SavedState)) {
            super.onRestoreInstanceState(state);
            return;
        }
        SavedState ss = (SavedState) state;
        super.onRestoreInstanceState(ss.getSuperState());
        this.allowReordering = ss.allowReordering;
        this.debugging = ss.debugging;
        this.numColumns = ss.numColumns;
        this.requestedColumnCount = ss.requestedColumnCount;
        this.requestedColumnWidth = ss.requestedColumnWidth;
        this.requestedHorizontalSpacing = ss.requestedHorizontalSpacing;
        setSelectionFromTop(20, 0);
    }

    public int getNumColumns() {
        return this.numColumns;
    }

    public int getColumnWidth() {
        int availableSpace = getAvailableSpace();
        int i = this.numColumns;
        return (availableSpace - ((i - 1) * this.requestedHorizontalSpacing)) / i;
    }

    public int getAvailableSpace() {
        return (getMeasuredWidth() - getPaddingLeft()) - getPaddingRight();
    }

    public boolean isAllowReordering() {
        return this.allowReordering;
    }

    public void setAllowReordering(boolean allowReordering2) {
        this.allowReordering = allowReordering2;
        AsymmetricGridViewAdapter asymmetricGridViewAdapter = this.gridAdapter;
        if (asymmetricGridViewAdapter != null) {
            asymmetricGridViewAdapter.recalculateItemsPerRow();
        }
    }

    public boolean isDebugging() {
        return this.debugging;
    }

    public void setDebugging(boolean debugging2) {
        this.debugging = debugging2;
    }

    public static class SavedState extends View.BaseSavedState {
        public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.Creator<SavedState>() {
            public SavedState createFromParcel(Parcel in) {
                return new SavedState(in);
            }

            public SavedState[] newArray(int size) {
                return new SavedState[size];
            }
        };
        Parcelable adapterState;
        boolean allowReordering;
        boolean debugging;
        int defaultPadding;
        ClassLoader loader;
        int numColumns;
        int requestedColumnCount;
        int requestedColumnWidth;
        int requestedHorizontalSpacing;
        int requestedVerticalSpacing;

        public SavedState(Parcelable superState) {
            super(superState);
        }

        SavedState(Parcel in) {
            super(in);
            this.numColumns = in.readInt();
            this.requestedColumnWidth = in.readInt();
            this.requestedColumnCount = in.readInt();
            this.requestedVerticalSpacing = in.readInt();
            this.requestedHorizontalSpacing = in.readInt();
            this.defaultPadding = in.readInt();
            boolean z = false;
            this.debugging = in.readByte() == 1;
            this.allowReordering = in.readByte() == 1 ? true : z;
            this.adapterState = in.readParcelable(this.loader);
        }

        public void writeToParcel(Parcel dest, int flags) {
            super.writeToParcel(dest, flags);
            dest.writeInt(this.numColumns);
            dest.writeInt(this.requestedColumnWidth);
            dest.writeInt(this.requestedColumnCount);
            dest.writeInt(this.requestedVerticalSpacing);
            dest.writeInt(this.requestedHorizontalSpacing);
            dest.writeInt(this.defaultPadding);
            dest.writeByte(this.debugging ? (byte) 1 : 0);
            dest.writeByte(this.allowReordering ? (byte) 1 : 0);
            dest.writeParcelable(this.adapterState, flags);
        }
    }
}
