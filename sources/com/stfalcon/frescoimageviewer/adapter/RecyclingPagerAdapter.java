package com.stfalcon.frescoimageviewer.adapter;

import android.os.Bundle;
import android.os.Parcelable;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import androidx.viewpager.widget.PagerAdapter;
import com.stfalcon.frescoimageviewer.adapter.ViewHolder;
import java.util.ArrayList;
import java.util.List;

public abstract class RecyclingPagerAdapter<VH extends ViewHolder> extends PagerAdapter {
    public static boolean DEBUG = false;
    private static final String STATE;
    private static final String TAG;
    private SparseArray<RecycleCache> mRecycleTypeCaches = new SparseArray<>();
    private SparseArray<Parcelable> mSavedStates = new SparseArray<>();

    public abstract int getItemCount();

    public abstract void onBindViewHolder(VH vh, int i);

    public abstract VH onCreateViewHolder(ViewGroup viewGroup, int i);

    static {
        Class<RecyclingPagerAdapter> cls = RecyclingPagerAdapter.class;
        STATE = cls.getSimpleName();
        TAG = cls.getSimpleName();
    }

    public void destroyItem(ViewGroup parent, int position, Object object) {
        if (object instanceof ViewHolder) {
            ((ViewHolder) object).detach(parent);
        }
    }

    public int getCount() {
        return getItemCount();
    }

    public int getItemPosition(Object object) {
        return -2;
    }

    public Object instantiateItem(ViewGroup parent, int position) {
        int viewType = getItemViewType(position);
        if (this.mRecycleTypeCaches.get(viewType) == null) {
            this.mRecycleTypeCaches.put(viewType, new RecycleCache(this));
        }
        ViewHolder viewHolder = this.mRecycleTypeCaches.get(viewType).getFreeViewHolder(parent, viewType);
        viewHolder.attach(parent, position);
        onBindViewHolder(viewHolder, position);
        viewHolder.onRestoreInstanceState(this.mSavedStates.get(getItemId(position)));
        return viewHolder;
    }

    public boolean isViewFromObject(View view, Object object) {
        return (object instanceof ViewHolder) && ((ViewHolder) object).itemView == view;
    }

    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
        for (ViewHolder viewHolder : getAttachedViewHolders()) {
            onNotifyItemChanged(viewHolder);
        }
    }

    public void restoreState(Parcelable state, ClassLoader loader) {
        if (state instanceof Bundle) {
            Bundle bundle = (Bundle) state;
            bundle.setClassLoader(loader);
            String str = STATE;
            SparseArray<Parcelable> ss = bundle.containsKey(str) ? bundle.getSparseParcelableArray(str) : null;
            this.mSavedStates = ss != null ? ss : new SparseArray<>();
        }
        super.restoreState(state, loader);
    }

    public Parcelable saveState() {
        Bundle bundle = new Bundle();
        for (ViewHolder viewHolder : getAttachedViewHolders()) {
            this.mSavedStates.put(getItemId(viewHolder.mPosition), viewHolder.onSaveInstanceState());
        }
        bundle.putSparseParcelableArray(STATE, this.mSavedStates);
        return bundle;
    }

    public int getItemId(int position) {
        return position;
    }

    public int getItemViewType(int position) {
        return 0;
    }

    /* access modifiers changed from: protected */
    public void onNotifyItemChanged(ViewHolder viewHolder) {
    }

    private List<ViewHolder> getAttachedViewHolders() {
        List<ViewHolder> attachedViewHolders = new ArrayList<>();
        int n = this.mRecycleTypeCaches.size();
        for (int i = 0; i < n; i++) {
            SparseArray<RecycleCache> sparseArray = this.mRecycleTypeCaches;
            for (ViewHolder viewHolder : sparseArray.get(sparseArray.keyAt(i)).mCaches) {
                if (viewHolder.mIsAttached) {
                    attachedViewHolders.add(viewHolder);
                }
            }
        }
        return attachedViewHolders;
    }

    private static class RecycleCache {
        private final RecyclingPagerAdapter mAdapter;
        /* access modifiers changed from: private */
        public final List<ViewHolder> mCaches = new ArrayList();

        RecycleCache(RecyclingPagerAdapter adapter) {
            this.mAdapter = adapter;
        }

        /* access modifiers changed from: package-private */
        public ViewHolder getFreeViewHolder(ViewGroup parent, int viewType) {
            int n = this.mCaches.size();
            for (int i = 0; i < n; i++) {
                ViewHolder viewHolder = this.mCaches.get(i);
                if (!viewHolder.mIsAttached) {
                    return viewHolder;
                }
            }
            ViewHolder viewHolder2 = this.mAdapter.onCreateViewHolder(parent, viewType);
            this.mCaches.add(viewHolder2);
            return viewHolder2;
        }
    }
}
