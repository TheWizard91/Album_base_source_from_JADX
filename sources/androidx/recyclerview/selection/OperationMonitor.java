package androidx.recyclerview.selection;

import android.util.Log;
import androidx.core.util.Preconditions;
import java.util.ArrayList;
import java.util.List;

public final class OperationMonitor {
    private static final String TAG = "OperationMonitor";
    private final List<OnChangeListener> mListeners = new ArrayList();
    private int mNumOps = 0;
    private final Resettable mResettable = new Resettable() {
        public boolean isResetRequired() {
            return OperationMonitor.this.isResetRequired();
        }

        public void reset() {
            OperationMonitor.this.reset();
        }
    };

    public interface OnChangeListener {
        void onChanged();
    }

    /* access modifiers changed from: package-private */
    public synchronized void start() {
        int i = this.mNumOps + 1;
        this.mNumOps = i;
        if (i == 1) {
            notifyStateChanged();
        }
    }

    /* access modifiers changed from: package-private */
    /* JADX WARNING: Code restructure failed: missing block: B:12:0x0011, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized void stop() {
        /*
            r1 = this;
            monitor-enter(r1)
            int r0 = r1.mNumOps     // Catch:{ all -> 0x0012 }
            if (r0 != 0) goto L_0x0007
            monitor-exit(r1)
            return
        L_0x0007:
            int r0 = r0 + -1
            r1.mNumOps = r0     // Catch:{ all -> 0x0012 }
            if (r0 != 0) goto L_0x0010
            r1.notifyStateChanged()     // Catch:{ all -> 0x0012 }
        L_0x0010:
            monitor-exit(r1)
            return
        L_0x0012:
            r0 = move-exception
            monitor-exit(r1)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.recyclerview.selection.OperationMonitor.stop():void");
    }

    /* access modifiers changed from: package-private */
    public synchronized void reset() {
        if (this.mNumOps > 0) {
            Log.w(TAG, "Resetting OperationMonitor with " + this.mNumOps + " active operations.");
        }
        this.mNumOps = 0;
        notifyStateChanged();
    }

    /* access modifiers changed from: package-private */
    public synchronized boolean isResetRequired() {
        return isStarted();
    }

    public synchronized boolean isStarted() {
        return this.mNumOps > 0;
    }

    public void addListener(OnChangeListener listener) {
        Preconditions.checkArgument(listener != null);
        this.mListeners.add(listener);
    }

    public void removeListener(OnChangeListener listener) {
        Preconditions.checkArgument(listener != null);
        this.mListeners.remove(listener);
    }

    /* access modifiers changed from: package-private */
    public void checkStarted(boolean started) {
        boolean z = true;
        if (started) {
            if (this.mNumOps <= 0) {
                z = false;
            }
            Preconditions.checkState(z);
            return;
        }
        if (this.mNumOps != 0) {
            z = false;
        }
        Preconditions.checkState(z);
    }

    private void notifyStateChanged() {
        for (OnChangeListener l : this.mListeners) {
            l.onChanged();
        }
    }

    /* access modifiers changed from: package-private */
    public Resettable asResettable() {
        return this.mResettable;
    }
}
