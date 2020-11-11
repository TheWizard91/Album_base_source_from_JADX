package androidx.recyclerview.selection;

import android.view.MotionEvent;
import androidx.core.util.Preconditions;
import java.util.Arrays;
import java.util.List;

final class ToolHandlerRegistry<T> {
    private final T mDefault;
    private final List<T> mHandlers = Arrays.asList(new Object[]{null, null, null, null, null});

    ToolHandlerRegistry(T defaultDelegate) {
        boolean z = false;
        Preconditions.checkArgument(defaultDelegate != null ? true : z);
        this.mDefault = defaultDelegate;
    }

    /* access modifiers changed from: package-private */
    public void set(int toolType, T delegate) {
        boolean z = true;
        Preconditions.checkArgument(toolType >= 0 && toolType <= 4);
        if (this.mHandlers.get(toolType) != null) {
            z = false;
        }
        Preconditions.checkState(z);
        this.mHandlers.set(toolType, delegate);
    }

    /* access modifiers changed from: package-private */
    public T get(MotionEvent e) {
        T d = this.mHandlers.get(e.getToolType(0));
        return d != null ? d : this.mDefault;
    }
}
