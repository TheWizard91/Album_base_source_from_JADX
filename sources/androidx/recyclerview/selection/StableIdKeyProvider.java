package androidx.recyclerview.selection;

import android.util.SparseArray;
import android.view.View;
import androidx.collection.LongSparseArray;
import androidx.recyclerview.widget.RecyclerView;

public final class StableIdKeyProvider extends ItemKeyProvider<Long> {
    private static final String TAG = "StableIdKeyProvider";
    private final LongSparseArray<Integer> mKeyToPosition = new LongSparseArray<>();
    private final SparseArray<Long> mPositionToKey = new SparseArray<>();
    private final RecyclerView mRecyclerView;

    public StableIdKeyProvider(RecyclerView recyclerView) {
        super(1);
        this.mRecyclerView = recyclerView;
        recyclerView.addOnChildAttachStateChangeListener(new RecyclerView.OnChildAttachStateChangeListener() {
            public void onChildViewAttachedToWindow(View view) {
                StableIdKeyProvider.this.onAttached(view);
            }

            public void onChildViewDetachedFromWindow(View view) {
                StableIdKeyProvider.this.onDetached(view);
            }
        });
    }

    /* access modifiers changed from: package-private */
    public void onAttached(View view) {
        RecyclerView.ViewHolder holder = this.mRecyclerView.findContainingViewHolder(view);
        if (holder != null) {
            int position = holder.getAdapterPosition();
            long id = holder.getItemId();
            if (position != -1 && id != -1) {
                this.mPositionToKey.put(position, Long.valueOf(id));
                this.mKeyToPosition.put(id, Integer.valueOf(position));
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void onDetached(View view) {
        RecyclerView.ViewHolder holder = this.mRecyclerView.findContainingViewHolder(view);
        if (holder != null) {
            int position = holder.getAdapterPosition();
            long id = holder.getItemId();
            if (position != -1 && id != -1) {
                this.mPositionToKey.delete(position);
                this.mKeyToPosition.remove(id);
            }
        }
    }

    public Long getKey(int position) {
        return this.mPositionToKey.get(position, (Object) null);
    }

    public int getPosition(Long key) {
        return this.mKeyToPosition.get(key.longValue(), -1).intValue();
    }
}
