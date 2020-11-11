package com.alexvasilkov.gestures.transition.tracker;

import android.view.View;

public abstract class SimpleTracker implements FromTracker<Integer>, IntoTracker<Integer> {
    /* access modifiers changed from: protected */
    public abstract View getViewAt(int i);

    public Integer getIdByPosition(int position) {
        return Integer.valueOf(position);
    }

    public int getPositionById(Integer id) {
        return id.intValue();
    }

    public View getViewById(Integer id) {
        return getViewAt(id.intValue());
    }
}
