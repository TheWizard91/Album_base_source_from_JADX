package com.alexvasilkov.gestures.transition.tracker;

import android.view.View;

interface AbstractTracker<ID> {
    public static final int NO_POSITION = -1;

    int getPositionById(ID id);

    View getViewById(ID id);
}
