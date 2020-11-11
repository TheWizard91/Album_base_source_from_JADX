package androidx.recyclerview.selection;

import android.view.MotionEvent;
import androidx.recyclerview.widget.RecyclerView;

final class DummyOnItemTouchListener implements RecyclerView.OnItemTouchListener {
    DummyOnItemTouchListener() {
    }

    public boolean onInterceptTouchEvent(RecyclerView unused, MotionEvent e) {
        return false;
    }

    public void onTouchEvent(RecyclerView unused, MotionEvent e) {
    }

    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {
    }
}
