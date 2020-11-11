package com.alexvasilkov.gestures.transition.tracker;

public interface IntoTracker<ID> extends AbstractTracker<ID> {
    ID getIdByPosition(int i);
}
