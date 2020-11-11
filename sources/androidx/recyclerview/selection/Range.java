package androidx.recyclerview.selection;

import android.util.Log;
import androidx.core.util.Preconditions;

final class Range {
    private static final String TAG = "Range";
    static final int TYPE_PRIMARY = 0;
    static final int TYPE_PROVISIONAL = 1;
    private final int mBegin;
    private final Callbacks mCallbacks;
    private int mEnd = -1;

    Range(int position, Callbacks callbacks) {
        this.mBegin = position;
        this.mCallbacks = callbacks;
    }

    /* access modifiers changed from: package-private */
    public void extendRange(int position, int type) {
        Preconditions.checkArgument(position != -1, "Position cannot be NO_POSITION.");
        int i = this.mEnd;
        if (i == -1 || i == this.mBegin) {
            this.mEnd = -1;
            establishRange(position, type);
            return;
        }
        reviseRange(position, type);
    }

    private void establishRange(int position, int type) {
        Preconditions.checkArgument(this.mEnd == -1, "End has already been set.");
        this.mEnd = position;
        int i = this.mBegin;
        if (position > i) {
            updateRange(i + 1, position, true, type);
        } else if (position < i) {
            updateRange(position, i - 1, true, type);
        }
    }

    private void reviseRange(int position, int type) {
        boolean z = true;
        Preconditions.checkArgument(this.mEnd != -1, "End must already be set.");
        if (this.mBegin == this.mEnd) {
            z = false;
        }
        Preconditions.checkArgument(z, "Beging and end point to same position.");
        int i = this.mEnd;
        int i2 = this.mBegin;
        if (i > i2) {
            reviseAscending(position, type);
        } else if (i < i2) {
            reviseDescending(position, type);
        }
        this.mEnd = position;
    }

    private void reviseAscending(int position, int type) {
        int i = this.mEnd;
        if (position < i) {
            int i2 = this.mBegin;
            if (position < i2) {
                updateRange(i2 + 1, i, false, type);
                updateRange(position, this.mBegin - 1, true, type);
                return;
            }
            updateRange(position + 1, i, false, type);
        } else if (position > i) {
            updateRange(i + 1, position, true, type);
        }
    }

    private void reviseDescending(int position, int type) {
        int i = this.mEnd;
        if (position > i) {
            int i2 = this.mBegin;
            if (position > i2) {
                updateRange(i, i2 - 1, false, type);
                updateRange(this.mBegin + 1, position, true, type);
                return;
            }
            updateRange(i, position - 1, false, type);
        } else if (position < i) {
            updateRange(position, i - 1, true, type);
        }
    }

    private void updateRange(int begin, int end, boolean selected, int type) {
        this.mCallbacks.updateForRange(begin, end, selected, type);
    }

    public String toString() {
        return "Range{begin=" + this.mBegin + ", end=" + this.mEnd + "}";
    }

    private void log(int type, String message) {
        Log.d("Range", String.valueOf(this) + ": " + message + " (" + (type == 0 ? "PRIMARY" : "PROVISIONAL") + ")");
    }

    static abstract class Callbacks {
        /* access modifiers changed from: package-private */
        public abstract void updateForRange(int i, int i2, boolean z, int i3);

        Callbacks() {
        }
    }
}
