package androidx.recyclerview.selection;

import android.view.GestureDetector;
import android.view.MotionEvent;
import androidx.core.util.Preconditions;
import androidx.recyclerview.widget.RecyclerView;

final class GestureDetectorOnItemTouchListenerAdapter implements RecyclerView.OnItemTouchListener {
    private final GestureDetector mDetector;

    GestureDetectorOnItemTouchListenerAdapter(GestureDetector detector) {
        Preconditions.checkArgument(detector != null);
        this.mDetector = detector;
    }

    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
        return this.mDetector.onTouchEvent(e);
    }

    public void onTouchEvent(RecyclerView rv, MotionEvent e) {
    }

    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {
    }
}
