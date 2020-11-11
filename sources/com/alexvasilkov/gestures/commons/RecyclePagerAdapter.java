package com.alexvasilkov.gestures.commons;

import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import androidx.viewpager.widget.PagerAdapter;
import com.alexvasilkov.gestures.commons.RecyclePagerAdapter.ViewHolder;
import java.util.LinkedList;
import java.util.Queue;

public abstract class RecyclePagerAdapter<VH extends ViewHolder> extends PagerAdapter {
    private final SparseArray<VH> attached = new SparseArray<>();
    private final Queue<VH> cache = new LinkedList();

    public abstract void onBindViewHolder(VH vh, int i);

    public abstract VH onCreateViewHolder(ViewGroup viewGroup);

    public void onRecycleViewHolder(VH vh) {
    }

    public VH getViewHolder(int position) {
        return (ViewHolder) this.attached.get(position);
    }

    public Object instantiateItem(ViewGroup container, int position) {
        VH holder = (ViewHolder) this.cache.poll();
        if (holder == null) {
            holder = onCreateViewHolder(container);
        }
        this.attached.put(position, holder);
        container.addView(holder.itemView, (ViewGroup.LayoutParams) null);
        onBindViewHolder(holder, position);
        return holder;
    }

    public void destroyItem(ViewGroup container, int position, Object object) {
        VH holder = (ViewHolder) object;
        this.attached.remove(position);
        container.removeView(holder.itemView);
        this.cache.offer(holder);
        onRecycleViewHolder(holder);
    }

    public boolean isViewFromObject(View view, Object object) {
        return ((ViewHolder) object).itemView == view;
    }

    public int getItemPosition(Object object) {
        return -2;
    }

    public static class ViewHolder {
        public final View itemView;

        public ViewHolder(View itemView2) {
            this.itemView = itemView2;
        }
    }
}
