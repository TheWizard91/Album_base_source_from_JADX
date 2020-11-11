package com.felipecsl.asymmetricgridview.library.widget;

import android.content.Context;
import android.database.CursorIndexOutOfBoundsException;
import android.database.DataSetObserver;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.WrapperListAdapter;
import androidx.collection.ArrayMap;
import com.felipecsl.asymmetricgridview.library.AsyncTaskCompat;
import com.felipecsl.asymmetricgridview.library.C2352R;
import com.felipecsl.asymmetricgridview.library.Utils;
import com.felipecsl.asymmetricgridview.library.model.AsymmetricItem;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class AsymmetricGridViewAdapter<T extends AsymmetricItem> extends BaseAdapter implements View.OnClickListener, View.OnLongClickListener, WrapperListAdapter {
    /* access modifiers changed from: private */
    public static final String TAG = AsymmetricGridViewAdapter.class.getSimpleName();
    private AsymmetricGridViewAdapter<T>.ProcessRowsTask asyncTask;
    private final Context context;
    private final Map<Integer, ObjectPool<View>> convertViewMap = new ArrayMap();
    /* access modifiers changed from: private */
    public final Map<Integer, RowInfo<T>> itemsPerRow = new HashMap();
    private final ObjectPool<IcsLinearLayout> linearLayoutPool;
    /* access modifiers changed from: private */
    public final AsymmetricGridView listView;
    private final ObjectPool<View> objectPool = new ObjectPool<>();
    /* access modifiers changed from: private */
    public final ListAdapter wrappedAdapter;

    class GridDataSetObserver extends DataSetObserver {
        GridDataSetObserver() {
        }

        public void onChanged() {
            AsymmetricGridViewAdapter.this.recalculateItemsPerRow();
        }

        public void onInvalidated() {
            AsymmetricGridViewAdapter.this.recalculateItemsPerRow();
        }
    }

    public AsymmetricGridViewAdapter(Context context2, AsymmetricGridView listView2, ListAdapter adapter) {
        this.linearLayoutPool = new ObjectPool<>(new LinearLayoutPoolObjectFactory(context2));
        this.wrappedAdapter = adapter;
        this.context = context2;
        this.listView = listView2;
        adapter.registerDataSetObserver(new GridDataSetObserver());
    }

    /* access modifiers changed from: protected */
    public int getRowHeight(AsymmetricItem item) {
        return getRowHeight(item.getRowSpan());
    }

    public T getItem(int position) {
        return (AsymmetricItem) this.wrappedAdapter.getItem(position);
    }

    public long getItemId(int position) {
        return this.wrappedAdapter.getItemId(position);
    }

    /* access modifiers changed from: protected */
    public int getRowHeight(int rowSpan) {
        return ((rowSpan - 1) * this.listView.getDividerHeight()) + (this.listView.getColumnWidth() * rowSpan);
    }

    /* access modifiers changed from: protected */
    public int getRowWidth(AsymmetricItem item) {
        return getRowWidth(item.getColumnSpan());
    }

    /* access modifiers changed from: protected */
    public int getRowWidth(int columnSpan) {
        return Math.min(((columnSpan - 1) * this.listView.getRequestedHorizontalSpacing()) + (this.listView.getColumnWidth() * columnSpan), Utils.getScreenWidth(this.context));
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (this.listView.isDebugging()) {
            Log.d(TAG, "getView(" + String.valueOf(position) + ")");
        }
        RowInfo<T> rowInfo = this.itemsPerRow.get(Integer.valueOf(position));
        if (rowInfo == null) {
            return view;
        }
        List<RowItem<T>> rowItems = new ArrayList<>(rowInfo.getItems());
        LinearLayout layout = findOrInitializeLayout(view);
        int columnIndex = 0;
        int currentIndex = 0;
        int spaceLeftInColumn = rowInfo.getRowHeight();
        while (true) {
            if (!rowItems.isEmpty() && columnIndex < this.listView.getNumColumns()) {
                RowItem<T> currentItem = rowItems.get(currentIndex);
                if (spaceLeftInColumn != 0) {
                    if (spaceLeftInColumn < currentItem.getItem().getRowSpan()) {
                        ViewGroup viewGroup = parent;
                        if (currentIndex >= rowItems.size() - 1) {
                            break;
                        }
                        currentIndex++;
                    } else {
                        rowItems.remove(currentItem);
                        int actualIndex = currentItem.getIndex();
                        ObjectPool<View> pool = this.convertViewMap.get(Integer.valueOf(this.wrappedAdapter.getItemViewType(actualIndex)));
                        if (pool == null) {
                            pool = new ObjectPool<>();
                            this.convertViewMap.put(Integer.valueOf(actualIndex), pool);
                        }
                        View view2 = this.wrappedAdapter.getView(actualIndex, pool.get(), parent);
                        view2.setTag(currentItem);
                        view2.setOnClickListener(this);
                        view2.setOnLongClickListener(this);
                        spaceLeftInColumn -= currentItem.getItem().getRowSpan();
                        currentIndex = 0;
                        view2.setLayoutParams(new LinearLayout.LayoutParams(getRowWidth((AsymmetricItem) currentItem.getItem()), getRowHeight((AsymmetricItem) currentItem.getItem())));
                        findOrInitializeChildLayout(layout, columnIndex).addView(view2);
                    }
                } else {
                    columnIndex++;
                    currentIndex = 0;
                    spaceLeftInColumn = rowInfo.getRowHeight();
                }
            } else {
                ViewGroup viewGroup2 = parent;
            }
        }
        ViewGroup viewGroup22 = parent;
        if (this.listView.isDebugging() && position % 20 == 0) {
            String str = TAG;
            Log.d(str, this.linearLayoutPool.getStats("LinearLayout"));
            Log.d(str, this.objectPool.getStats("Views"));
        }
        return layout;
    }

    private IcsLinearLayout findOrInitializeLayout(View convertView) {
        IcsLinearLayout layout;
        if (convertView == null || !(convertView instanceof IcsLinearLayout)) {
            layout = new IcsLinearLayout(this.context, (AttributeSet) null);
            if (this.listView.isDebugging()) {
                layout.setBackgroundColor(Color.parseColor("#83F27B"));
            }
            layout.setShowDividers(2);
            layout.setDividerDrawable(this.context.getResources().getDrawable(C2352R.C2353drawable.item_divider_horizontal));
            layout.setLayoutParams(new AbsListView.LayoutParams(-1, -2));
        } else {
            layout = (IcsLinearLayout) convertView;
        }
        for (int j = 0; j < layout.getChildCount(); j++) {
            IcsLinearLayout tempChild = (IcsLinearLayout) layout.getChildAt(j);
            this.linearLayoutPool.put(tempChild);
            for (int k = 0; k < tempChild.getChildCount(); k++) {
                this.objectPool.put(tempChild.getChildAt(k));
            }
            tempChild.removeAllViews();
        }
        layout.removeAllViews();
        return layout;
    }

    private IcsLinearLayout findOrInitializeChildLayout(LinearLayout parentLayout, int childIndex) {
        IcsLinearLayout childLayout = (IcsLinearLayout) parentLayout.getChildAt(childIndex);
        if (childLayout == null) {
            childLayout = this.linearLayoutPool.get();
            childLayout.setOrientation(1);
            if (this.listView.isDebugging()) {
                childLayout.setBackgroundColor(Color.parseColor("#837BF2"));
            }
            childLayout.setShowDividers(2);
            childLayout.setDividerDrawable(this.context.getResources().getDrawable(C2352R.C2353drawable.item_divider_vertical));
            childLayout.setLayoutParams(new AbsListView.LayoutParams(-2, -1));
            parentLayout.addView(childLayout);
        }
        return childLayout;
    }

    public void onClick(View v) {
        this.listView.fireOnItemClick(((RowItem) v.getTag()).getIndex(), v);
    }

    public boolean onLongClick(View v) {
        return this.listView.fireOnItemLongClick(((RowItem) v.getTag()).getIndex(), v);
    }

    public int getCount() {
        return getRowCount();
    }

    public int getRowCount() {
        return this.itemsPerRow.size();
    }

    public void recalculateItemsPerRow() {
        AsymmetricGridViewAdapter<T>.ProcessRowsTask processRowsTask = this.asyncTask;
        if (processRowsTask != null) {
            processRowsTask.cancel(true);
        }
        this.linearLayoutPool.clear();
        this.objectPool.clear();
        this.itemsPerRow.clear();
        AsymmetricGridViewAdapter<T>.ProcessRowsTask processRowsTask2 = new ProcessRowsTask();
        this.asyncTask = processRowsTask2;
        processRowsTask2.executeSerially(new Void[0]);
    }

    /* access modifiers changed from: private */
    public RowInfo<T> calculateItemsForRow(List<RowItem<T>> items) {
        return calculateItemsForRow(items, (float) this.listView.getNumColumns());
    }

    private RowInfo<T> calculateItemsForRow(List<RowItem<T>> items, float initialSpaceLeft) {
        List<RowItem<T>> itemsThatFit = new ArrayList<>();
        int currentItem = 0;
        int rowHeight = 1;
        float areaLeft = initialSpaceLeft;
        while (true) {
            if (areaLeft <= 0.0f || currentItem >= items.size()) {
                break;
            }
            int currentItem2 = currentItem + 1;
            RowItem<T> item = items.get(currentItem);
            float itemArea = (float) (item.getItem().getRowSpan() * item.getItem().getColumnSpan());
            if (this.listView.isDebugging()) {
                Log.d(TAG, String.format("item %s in row with height %s consumes %s area", new Object[]{item, Integer.valueOf(rowHeight), Float.valueOf(itemArea)}));
            }
            if (rowHeight < item.getItem().getRowSpan()) {
                itemsThatFit.clear();
                rowHeight = item.getItem().getRowSpan();
                currentItem = 0;
                areaLeft = ((float) item.getItem().getRowSpan()) * initialSpaceLeft;
            } else if (areaLeft >= itemArea) {
                areaLeft -= itemArea;
                itemsThatFit.add(item);
                currentItem = currentItem2;
            } else if (!this.listView.isAllowReordering()) {
                int i = currentItem2;
                break;
            } else {
                currentItem = currentItem2;
            }
        }
        return new RowInfo<>(rowHeight, itemsThatFit, areaLeft);
    }

    public ListAdapter getWrappedAdapter() {
        return this.wrappedAdapter;
    }

    class ProcessRowsTask extends AsyncTaskCompat<Void, Void, List<RowInfo<T>>> {
        ProcessRowsTask() {
        }

        /* access modifiers changed from: protected */
        public final List<RowInfo<T>> doInBackground(Void... params) {
            List<RowItem<T>> itemsToAdd = new ArrayList<>();
            for (int i = 0; i < AsymmetricGridViewAdapter.this.wrappedAdapter.getCount(); i++) {
                try {
                    itemsToAdd.add(new RowItem(i, (AsymmetricItem) AsymmetricGridViewAdapter.this.wrappedAdapter.getItem(i)));
                } catch (CursorIndexOutOfBoundsException e) {
                    Log.w(AsymmetricGridViewAdapter.TAG, e);
                }
            }
            return calculateItemsPerRow(itemsToAdd);
        }

        /* access modifiers changed from: protected */
        public void onPostExecute(List<RowInfo<T>> rows) {
            for (RowInfo<T> row : rows) {
                AsymmetricGridViewAdapter.this.itemsPerRow.put(Integer.valueOf(AsymmetricGridViewAdapter.this.getRowCount()), row);
            }
            if (AsymmetricGridViewAdapter.this.listView.isDebugging()) {
                for (Map.Entry<Integer, RowInfo<T>> e : AsymmetricGridViewAdapter.this.itemsPerRow.entrySet()) {
                    Log.d(AsymmetricGridViewAdapter.TAG, "row: " + e.getKey() + ", items: " + e.getValue().getItems().size());
                }
            }
            AsymmetricGridViewAdapter.this.notifyDataSetChanged();
        }

        private List<RowInfo<T>> calculateItemsPerRow(List<RowItem<T>> itemsToAdd) {
            List<RowInfo<T>> rows = new ArrayList<>();
            while (!itemsToAdd.isEmpty()) {
                RowInfo<T> stuffThatFit = AsymmetricGridViewAdapter.this.calculateItemsForRow(itemsToAdd);
                List<RowItem<T>> itemsThatFit = stuffThatFit.getItems();
                if (itemsThatFit.isEmpty()) {
                    break;
                }
                for (RowItem<T> entry : itemsThatFit) {
                    itemsToAdd.remove(entry);
                }
                rows.add(stuffThatFit);
            }
            return rows;
        }
    }
}
