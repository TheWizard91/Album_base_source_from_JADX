package com.alexvasilkov.gestures.transition;

import android.view.View;

@Deprecated
public interface ViewsTracker<ID> {
    public static final int NO_POSITION = -1;

    ID getIdForPosition(int i);

    int getPositionForId(ID id);

    View getViewForPosition(int i);
}
