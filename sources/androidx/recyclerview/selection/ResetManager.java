package androidx.recyclerview.selection;

import android.view.MotionEvent;
import androidx.recyclerview.selection.SelectionTracker;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

final class ResetManager<K> {
    private static final String TAG = "ResetManager";
    private final RecyclerView.OnItemTouchListener mInputListener = new RecyclerView.OnItemTouchListener() {
        public boolean onInterceptTouchEvent(RecyclerView unused, MotionEvent e) {
            if (!MotionEvents.isActionCancel(e)) {
                return false;
            }
            ResetManager.this.callResetHandlers();
            return false;
        }

        public void onTouchEvent(RecyclerView rv, MotionEvent e) {
        }

        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {
        }
    };
    private final List<Resettable> mResetHandlers = new ArrayList();
    private final SelectionTracker.SelectionObserver<K> mSelectionObserver = new SelectionTracker.SelectionObserver<K>() {
        /* access modifiers changed from: protected */
        public void onSelectionCleared() {
            ResetManager.this.callResetHandlers();
        }
    };

    ResetManager() {
    }

    /* access modifiers changed from: package-private */
    public SelectionTracker.SelectionObserver<K> getSelectionObserver() {
        return this.mSelectionObserver;
    }

    /* access modifiers changed from: package-private */
    public RecyclerView.OnItemTouchListener getInputListener() {
        return this.mInputListener;
    }

    /* access modifiers changed from: package-private */
    public void addResetHandler(Resettable handler) {
        this.mResetHandlers.add(handler);
    }

    /* access modifiers changed from: package-private */
    public void callResetHandlers() {
        for (Resettable handler : this.mResetHandlers) {
            if (handler.isResetRequired()) {
                handler.reset();
            }
        }
    }
}
