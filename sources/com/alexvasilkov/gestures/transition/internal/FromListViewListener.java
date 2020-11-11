package com.alexvasilkov.gestures.transition.internal;

import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;
import com.alexvasilkov.gestures.transition.tracker.FromTracker;

public class FromListViewListener<ID> extends FromBaseListener<ListView, ID> {
    public /* bridge */ /* synthetic */ void onRequestView(Object obj) {
        super.onRequestView(obj);
    }

    public FromListViewListener(ListView list, final FromTracker<ID> tracker, boolean autoScroll) {
        super(list, tracker, autoScroll);
        if (autoScroll) {
            list.setOnScrollListener(new AbsListView.OnScrollListener() {
                public void onScroll(AbsListView view, int firstVisible, int visibleCount, int total) {
                    int position;
                    View from;
                    ID id = FromListViewListener.this.getAnimator() == null ? null : FromListViewListener.this.getAnimator().getRequestedId();
                    if (id != null && (position = tracker.getPositionById(id)) >= firstVisible && position < firstVisible + visibleCount && (from = tracker.getViewById(id)) != null) {
                        FromListViewListener.this.getAnimator().setFromView(id, from);
                    }
                }

                public void onScrollStateChanged(AbsListView view, int scrollState) {
                }
            });
        }
    }

    /* access modifiers changed from: package-private */
    public boolean isShownInList(ListView list, int pos) {
        return pos >= list.getFirstVisiblePosition() && pos <= list.getLastVisiblePosition();
    }

    /* access modifiers changed from: package-private */
    public void scrollToPosition(ListView list, int pos) {
        list.setSelection(pos);
    }
}
