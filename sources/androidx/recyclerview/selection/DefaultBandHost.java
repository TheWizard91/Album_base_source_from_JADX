package androidx.recyclerview.selection;

import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.view.View;
import androidx.core.content.ContextCompat;
import androidx.core.util.Preconditions;
import androidx.recyclerview.selection.GridModel;
import androidx.recyclerview.selection.SelectionTracker;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

final class DefaultBandHost<K> extends GridModel.GridHost<K> {
    private static final Rect NILL_RECT = new Rect(0, 0, 0, 0);
    private final Drawable mBand;
    private final ItemKeyProvider<K> mKeyProvider;
    private final RecyclerView mRecyclerView;
    private final SelectionTracker.SelectionPredicate<K> mSelectionPredicate;

    DefaultBandHost(RecyclerView recyclerView, int bandOverlayId, ItemKeyProvider<K> keyProvider, SelectionTracker.SelectionPredicate<K> selectionPredicate) {
        boolean z = true;
        Preconditions.checkArgument(recyclerView != null);
        this.mRecyclerView = recyclerView;
        Drawable drawable = ContextCompat.getDrawable(recyclerView.getContext(), bandOverlayId);
        this.mBand = drawable;
        Preconditions.checkArgument(drawable != null);
        Preconditions.checkArgument(keyProvider != null);
        Preconditions.checkArgument(selectionPredicate == null ? false : z);
        this.mKeyProvider = keyProvider;
        this.mSelectionPredicate = selectionPredicate;
        recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            public void onDrawOver(Canvas canvas, RecyclerView unusedParent, RecyclerView.State unusedState) {
                DefaultBandHost.this.onDrawBand(canvas);
            }
        });
    }

    /* access modifiers changed from: package-private */
    public GridModel<K> createGridModel() {
        return new GridModel<>(this, this.mKeyProvider, this.mSelectionPredicate);
    }

    /* access modifiers changed from: package-private */
    public int getAdapterPositionAt(int index) {
        RecyclerView recyclerView = this.mRecyclerView;
        return recyclerView.getChildAdapterPosition(recyclerView.getChildAt(index));
    }

    /* access modifiers changed from: package-private */
    public void addOnScrollListener(RecyclerView.OnScrollListener listener) {
        this.mRecyclerView.addOnScrollListener(listener);
    }

    /* access modifiers changed from: package-private */
    public void removeOnScrollListener(RecyclerView.OnScrollListener listener) {
        this.mRecyclerView.removeOnScrollListener(listener);
    }

    /* access modifiers changed from: package-private */
    public Point createAbsolutePoint(Point relativePoint) {
        return new Point(relativePoint.x + this.mRecyclerView.computeHorizontalScrollOffset(), relativePoint.y + this.mRecyclerView.computeVerticalScrollOffset());
    }

    /* access modifiers changed from: package-private */
    public Rect getAbsoluteRectForChildViewAt(int index) {
        View child = this.mRecyclerView.getChildAt(index);
        Rect childRect = new Rect();
        child.getHitRect(childRect);
        childRect.left += this.mRecyclerView.computeHorizontalScrollOffset();
        childRect.right += this.mRecyclerView.computeHorizontalScrollOffset();
        childRect.top += this.mRecyclerView.computeVerticalScrollOffset();
        childRect.bottom += this.mRecyclerView.computeVerticalScrollOffset();
        return childRect;
    }

    /* access modifiers changed from: package-private */
    public int getVisibleChildCount() {
        return this.mRecyclerView.getChildCount();
    }

    /* access modifiers changed from: package-private */
    public int getColumnCount() {
        RecyclerView.LayoutManager layoutManager = this.mRecyclerView.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            return ((GridLayoutManager) layoutManager).getSpanCount();
        }
        return 1;
    }

    /* access modifiers changed from: package-private */
    public void showBand(Rect rect) {
        this.mBand.setBounds(rect);
        this.mRecyclerView.invalidate();
    }

    /* access modifiers changed from: package-private */
    public void hideBand() {
        this.mBand.setBounds(NILL_RECT);
        this.mRecyclerView.invalidate();
    }

    /* access modifiers changed from: package-private */
    public void onDrawBand(Canvas c) {
        this.mBand.draw(c);
    }

    /* access modifiers changed from: package-private */
    public boolean hasView(int pos) {
        return this.mRecyclerView.findViewHolderForAdapterPosition(pos) != null;
    }
}
