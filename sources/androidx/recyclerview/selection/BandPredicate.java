package androidx.recyclerview.selection;

import android.view.MotionEvent;
import android.view.View;
import androidx.core.util.Preconditions;
import androidx.recyclerview.selection.ItemDetailsLookup;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public abstract class BandPredicate {
    public abstract boolean canInitiate(MotionEvent motionEvent);

    static boolean hasSupportedLayoutManager(RecyclerView recyclerView) {
        RecyclerView.LayoutManager lm = recyclerView.getLayoutManager();
        return (lm instanceof GridLayoutManager) || (lm instanceof LinearLayoutManager);
    }

    public static final class EmptyArea extends BandPredicate {
        private final RecyclerView mRecyclerView;

        public EmptyArea(RecyclerView recyclerView) {
            Preconditions.checkArgument(recyclerView != null);
            this.mRecyclerView = recyclerView;
        }

        public boolean canInitiate(MotionEvent e) {
            if (!hasSupportedLayoutManager(this.mRecyclerView) || this.mRecyclerView.hasPendingAdapterUpdates()) {
                return false;
            }
            View itemView = this.mRecyclerView.findChildViewUnder(e.getX(), e.getY());
            if ((itemView != null ? this.mRecyclerView.getChildAdapterPosition(itemView) : -1) == -1) {
                return true;
            }
            return false;
        }
    }

    public static final class NonDraggableArea extends BandPredicate {
        private final ItemDetailsLookup<?> mDetailsLookup;
        private final RecyclerView mRecyclerView;

        public NonDraggableArea(RecyclerView recyclerView, ItemDetailsLookup<?> detailsLookup) {
            boolean z = true;
            Preconditions.checkArgument(recyclerView != null);
            Preconditions.checkArgument(detailsLookup == null ? false : z);
            this.mRecyclerView = recyclerView;
            this.mDetailsLookup = detailsLookup;
        }

        public boolean canInitiate(MotionEvent e) {
            if (!hasSupportedLayoutManager(this.mRecyclerView) || this.mRecyclerView.hasPendingAdapterUpdates()) {
                return false;
            }
            ItemDetailsLookup.ItemDetails<?> details = this.mDetailsLookup.getItemDetails(e);
            if (details == null || !details.inDragRegion(e)) {
                return true;
            }
            return false;
        }
    }
}
