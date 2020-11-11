package androidx.recyclerview.selection;

import android.util.Log;
import androidx.core.util.Preconditions;
import androidx.recyclerview.selection.SelectionTracker;
import androidx.recyclerview.widget.RecyclerView;

public class EventBridge {
    private static final String TAG = "EventsRelays";

    public static <K> void install(RecyclerView.Adapter<?> adapter, SelectionTracker<K> selectionTracker, ItemKeyProvider<K> keyProvider) {
        new TrackerToAdapterBridge(selectionTracker, keyProvider, adapter);
        adapter.registerAdapterDataObserver(selectionTracker.getAdapterDataObserver());
    }

    private static final class TrackerToAdapterBridge<K> extends SelectionTracker.SelectionObserver<K> {
        private final RecyclerView.Adapter<?> mAdapter;
        private final ItemKeyProvider<K> mKeyProvider;

        TrackerToAdapterBridge(SelectionTracker<K> selectionTracker, ItemKeyProvider<K> keyProvider, RecyclerView.Adapter<?> adapter) {
            selectionTracker.addObserver(this);
            boolean z = true;
            Preconditions.checkArgument(keyProvider != null);
            Preconditions.checkArgument(adapter == null ? false : z);
            this.mKeyProvider = keyProvider;
            this.mAdapter = adapter;
        }

        public void onItemStateChanged(K key, boolean selected) {
            int position = this.mKeyProvider.getPosition(key);
            if (position < 0) {
                Log.w(EventBridge.TAG, "Item change notification received for unknown item: " + key);
            } else {
                this.mAdapter.notifyItemChanged(position, SelectionTracker.SELECTION_CHANGED_MARKER);
            }
        }
    }

    private EventBridge() {
    }
}
