package com.alexvasilkov.gestures.transition.internal;

import android.view.View;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.alexvasilkov.gestures.transition.tracker.FromTracker;

public class FromRecyclerViewListener<ID> extends FromBaseListener<RecyclerView, ID> {
    public /* bridge */ /* synthetic */ void onRequestView(Object obj) {
        super.onRequestView(obj);
    }

    public FromRecyclerViewListener(final RecyclerView list, final FromTracker<ID> tracker, boolean autoScroll) {
        super(list, tracker, autoScroll);
        if (autoScroll) {
            list.addOnChildAttachStateChangeListener(new RecyclerView.OnChildAttachStateChangeListener() {
                public void onChildViewAttachedToWindow(View view) {
                    View from;
                    ID id = FromRecyclerViewListener.this.getAnimator() == null ? null : FromRecyclerViewListener.this.getAnimator().getRequestedId();
                    if (id != null && list.getChildAdapterPosition(view) == tracker.getPositionById(id) && (from = tracker.getViewById(id)) != null) {
                        FromRecyclerViewListener.this.getAnimator().setFromView(id, from);
                    }
                }

                public void onChildViewDetachedFromWindow(View view) {
                }
            });
        }
    }

    /* access modifiers changed from: package-private */
    public boolean isShownInList(RecyclerView list, int pos) {
        return list.findViewHolderForLayoutPosition(pos) != null;
    }

    /* access modifiers changed from: package-private */
    public void scrollToPosition(RecyclerView list, int pos) {
        int offset;
        if (list.getLayoutManager() instanceof LinearLayoutManager) {
            LinearLayoutManager manager = (LinearLayoutManager) list.getLayoutManager();
            boolean isHorizontal = manager.getOrientation() == 0;
            if (isHorizontal) {
                offset = ((list.getWidth() - list.getPaddingLeft()) - list.getPaddingRight()) / 2;
            } else {
                offset = ((list.getHeight() - list.getPaddingTop()) - list.getPaddingBottom()) / 2;
            }
            RecyclerView.ViewHolder holder = list.findViewHolderForAdapterPosition(pos);
            if (holder != null) {
                View view = holder.itemView;
                offset -= (isHorizontal ? view.getWidth() : view.getHeight()) / 2;
            }
            manager.scrollToPositionWithOffset(pos, offset);
            return;
        }
        list.scrollToPosition(pos);
    }
}
