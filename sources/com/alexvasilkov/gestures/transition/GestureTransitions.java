package com.alexvasilkov.gestures.transition;

import android.view.View;
import android.widget.ListView;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;
import com.alexvasilkov.gestures.transition.ViewsTransitionAnimator;
import com.alexvasilkov.gestures.transition.internal.FromListViewListener;
import com.alexvasilkov.gestures.transition.internal.FromRecyclerViewListener;
import com.alexvasilkov.gestures.transition.internal.IntoViewPagerListener;
import com.alexvasilkov.gestures.transition.tracker.FromTracker;
import com.alexvasilkov.gestures.transition.tracker.IntoTracker;
import com.alexvasilkov.gestures.views.interfaces.AnimatorView;

public class GestureTransitions<ID> {
    private final ViewsTransitionAnimator<ID> animator = new ViewsTransitionAnimator<>();

    private GestureTransitions() {
    }

    public static <ID> GestureTransitions<ID> from(ViewsTransitionAnimator.RequestListener<ID> listener) {
        GestureTransitions<ID> builder = new GestureTransitions<>();
        builder.animator.setFromListener(listener);
        return builder;
    }

    public static <ID> GestureTransitions<ID> from(final View view) {
        return from(new ViewsTransitionAnimator.RequestListener<ID>() {
            public void onRequestView(ID id) {
                getAnimator().setFromView(id, view);
            }
        });
    }

    public static <ID> GestureTransitions<ID> from(RecyclerView recyclerView, FromTracker<ID> tracker) {
        return from(recyclerView, tracker, true);
    }

    public static <ID> GestureTransitions<ID> from(RecyclerView recyclerView, FromTracker<ID> tracker, boolean autoScroll) {
        return from(new FromRecyclerViewListener(recyclerView, tracker, autoScroll));
    }

    public static <ID> GestureTransitions<ID> from(ListView listView, FromTracker<ID> tracker) {
        return from(listView, tracker, true);
    }

    public static <ID> GestureTransitions<ID> from(ListView listView, FromTracker<ID> tracker, boolean autoScroll) {
        return from(new FromListViewListener(listView, tracker, autoScroll));
    }

    public static <ID> GestureTransitions<ID> fromNone() {
        return from(new ViewsTransitionAnimator.RequestListener<ID>() {
            public void onRequestView(ID id) {
                getAnimator().setFromNone(id);
            }
        });
    }

    public ViewsTransitionAnimator<ID> into(ViewsTransitionAnimator.RequestListener<ID> listener) {
        this.animator.setToListener(listener);
        return this.animator;
    }

    public ViewsTransitionAnimator<ID> into(final AnimatorView view) {
        return into(new ViewsTransitionAnimator.RequestListener<ID>() {
            public void onRequestView(ID id) {
                getAnimator().setToView(id, view);
            }
        });
    }

    public ViewsTransitionAnimator<ID> into(ViewPager viewPager, IntoTracker<ID> tracker) {
        return into(new IntoViewPagerListener(viewPager, tracker));
    }
}
