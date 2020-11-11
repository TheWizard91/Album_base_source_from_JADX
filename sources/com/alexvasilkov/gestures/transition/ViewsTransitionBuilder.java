package com.alexvasilkov.gestures.transition;

import android.view.View;
import android.widget.ListView;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;
import com.alexvasilkov.gestures.transition.internal.FromListViewListener;
import com.alexvasilkov.gestures.transition.internal.FromRecyclerViewListener;
import com.alexvasilkov.gestures.transition.internal.IntoViewPagerListener;
import com.alexvasilkov.gestures.transition.tracker.FromTracker;
import com.alexvasilkov.gestures.transition.tracker.IntoTracker;

@Deprecated
public class ViewsTransitionBuilder<ID> {
    private final ViewsTransitionAnimator<ID> animator = new ViewsTransitionAnimator<>();

    public ViewsTransitionBuilder<ID> fromRecyclerView(RecyclerView recyclerView, ViewsTracker<ID> tracker) {
        this.animator.setFromListener(new FromRecyclerViewListener(recyclerView, toFromTracker(tracker), true));
        return this;
    }

    public ViewsTransitionBuilder<ID> fromListView(ListView listView, ViewsTracker<ID> tracker) {
        this.animator.setFromListener(new FromListViewListener(listView, toFromTracker(tracker), true));
        return this;
    }

    public ViewsTransitionBuilder<ID> intoViewPager(ViewPager viewPager, ViewsTracker<ID> tracker) {
        this.animator.setToListener(new IntoViewPagerListener(viewPager, toIntoTracker(tracker)));
        return this;
    }

    public ViewsTransitionAnimator<ID> build() {
        return this.animator;
    }

    private static <ID> FromTracker<ID> toFromTracker(final ViewsTracker<ID> tracker) {
        return new FromTracker<ID>() {
            public int getPositionById(ID id) {
                return tracker.getPositionForId(id);
            }

            public View getViewById(ID id) {
                ViewsTracker viewsTracker = tracker;
                return viewsTracker.getViewForPosition(viewsTracker.getPositionForId(id));
            }
        };
    }

    private static <ID> IntoTracker<ID> toIntoTracker(final ViewsTracker<ID> tracker) {
        return new IntoTracker<ID>() {
            public ID getIdByPosition(int position) {
                return tracker.getIdForPosition(position);
            }

            public int getPositionById(ID id) {
                return tracker.getPositionForId(id);
            }

            public View getViewById(ID id) {
                ViewsTracker viewsTracker = tracker;
                return viewsTracker.getViewForPosition(viewsTracker.getPositionForId(id));
            }
        };
    }
}
