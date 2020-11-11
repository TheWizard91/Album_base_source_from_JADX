package com.alexvasilkov.gestures.transition;

@Deprecated
public abstract class SimpleViewsTracker implements ViewsTracker<Integer> {
    public int getPositionForId(Integer id) {
        return id.intValue();
    }

    public Integer getIdForPosition(int position) {
        return Integer.valueOf(position);
    }
}
