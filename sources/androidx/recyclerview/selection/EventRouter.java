package androidx.recyclerview.selection;

import android.view.MotionEvent;
import androidx.core.util.Preconditions;
import androidx.recyclerview.widget.RecyclerView;

final class EventRouter implements RecyclerView.OnItemTouchListener {
    private final ToolHandlerRegistry<RecyclerView.OnItemTouchListener> mDelegates = new ToolHandlerRegistry<>(new DummyOnItemTouchListener());

    EventRouter() {
    }

    /* access modifiers changed from: package-private */
    public void set(int toolType, RecyclerView.OnItemTouchListener delegate) {
        Preconditions.checkArgument(delegate != null);
        this.mDelegates.set(toolType, delegate);
    }

    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
        return this.mDelegates.get(e).onInterceptTouchEvent(rv, e);
    }

    public void onTouchEvent(RecyclerView rv, MotionEvent e) {
        this.mDelegates.get(e).onTouchEvent(rv, e);
    }

    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {
    }
}
