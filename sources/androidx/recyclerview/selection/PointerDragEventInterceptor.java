package androidx.recyclerview.selection;

import android.view.MotionEvent;
import androidx.core.util.Preconditions;
import androidx.recyclerview.widget.RecyclerView;

final class PointerDragEventInterceptor implements RecyclerView.OnItemTouchListener {
    private RecyclerView.OnItemTouchListener mDelegate;
    private final OnDragInitiatedListener mDragListener;
    private final ItemDetailsLookup<?> mEventDetailsLookup;

    PointerDragEventInterceptor(ItemDetailsLookup<?> eventDetailsLookup, OnDragInitiatedListener dragListener, RecyclerView.OnItemTouchListener delegate) {
        boolean z = true;
        Preconditions.checkArgument(eventDetailsLookup != null);
        Preconditions.checkArgument(dragListener == null ? false : z);
        this.mEventDetailsLookup = eventDetailsLookup;
        this.mDragListener = dragListener;
        if (delegate != null) {
            this.mDelegate = delegate;
        } else {
            this.mDelegate = new DummyOnItemTouchListener();
        }
    }

    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
        if (!MotionEvents.isPointerDragEvent(e) || !this.mEventDetailsLookup.inItemDragRegion(e)) {
            return this.mDelegate.onInterceptTouchEvent(rv, e);
        }
        return this.mDragListener.onDragInitiated(e);
    }

    public void onTouchEvent(RecyclerView rv, MotionEvent e) {
        this.mDelegate.onTouchEvent(rv, e);
    }

    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {
        this.mDelegate.onRequestDisallowInterceptTouchEvent(disallowIntercept);
    }
}
