package com.facebook.drawee.components;

import android.os.Handler;
import android.os.Looper;
import com.facebook.drawee.components.DeferredReleaser;
import java.util.ArrayList;

class DeferredReleaserConcurrentImpl extends DeferredReleaser {
    /* access modifiers changed from: private */
    public final Object mLock = new Object();
    /* access modifiers changed from: private */
    public ArrayList<DeferredReleaser.Releasable> mPendingReleasables = new ArrayList<>();
    /* access modifiers changed from: private */
    public ArrayList<DeferredReleaser.Releasable> mTempList = new ArrayList<>();
    private final Handler mUiHandler = new Handler(Looper.getMainLooper());
    private final Runnable releaseRunnable = new Runnable() {
        public void run() {
            synchronized (DeferredReleaserConcurrentImpl.this.mLock) {
                ArrayList<DeferredReleaser.Releasable> tmp = DeferredReleaserConcurrentImpl.this.mTempList;
                DeferredReleaserConcurrentImpl deferredReleaserConcurrentImpl = DeferredReleaserConcurrentImpl.this;
                ArrayList unused = deferredReleaserConcurrentImpl.mTempList = deferredReleaserConcurrentImpl.mPendingReleasables;
                ArrayList unused2 = DeferredReleaserConcurrentImpl.this.mPendingReleasables = tmp;
            }
            int size = DeferredReleaserConcurrentImpl.this.mTempList.size();
            for (int i = 0; i < size; i++) {
                ((DeferredReleaser.Releasable) DeferredReleaserConcurrentImpl.this.mTempList.get(i)).release();
            }
            DeferredReleaserConcurrentImpl.this.mTempList.clear();
        }
    };

    /* JADX WARNING: Code restructure failed: missing block: B:17:0x0029, code lost:
        if (r1 == false) goto L_?;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:18:0x002b, code lost:
        r3.mUiHandler.post(r3.releaseRunnable);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:26:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:27:?, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void scheduleDeferredRelease(com.facebook.drawee.components.DeferredReleaser.Releasable r4) {
        /*
            r3 = this;
            boolean r0 = isOnUiThread()
            if (r0 != 0) goto L_0x000a
            r4.release()
            return
        L_0x000a:
            java.lang.Object r0 = r3.mLock
            monitor-enter(r0)
            java.util.ArrayList<com.facebook.drawee.components.DeferredReleaser$Releasable> r1 = r3.mPendingReleasables     // Catch:{ all -> 0x0033 }
            boolean r1 = r1.contains(r4)     // Catch:{ all -> 0x0033 }
            if (r1 == 0) goto L_0x0017
            monitor-exit(r0)     // Catch:{ all -> 0x0033 }
            return
        L_0x0017:
            java.util.ArrayList<com.facebook.drawee.components.DeferredReleaser$Releasable> r1 = r3.mPendingReleasables     // Catch:{ all -> 0x0033 }
            r1.add(r4)     // Catch:{ all -> 0x0033 }
            java.util.ArrayList<com.facebook.drawee.components.DeferredReleaser$Releasable> r1 = r3.mPendingReleasables     // Catch:{ all -> 0x0033 }
            int r1 = r1.size()     // Catch:{ all -> 0x0033 }
            r2 = 1
            if (r1 != r2) goto L_0x0026
            goto L_0x0027
        L_0x0026:
            r2 = 0
        L_0x0027:
            r1 = r2
            monitor-exit(r0)     // Catch:{ all -> 0x0033 }
            if (r1 == 0) goto L_0x0032
            android.os.Handler r0 = r3.mUiHandler
            java.lang.Runnable r2 = r3.releaseRunnable
            r0.post(r2)
        L_0x0032:
            return
        L_0x0033:
            r1 = move-exception
            monitor-exit(r0)     // Catch:{ all -> 0x0033 }
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.facebook.drawee.components.DeferredReleaserConcurrentImpl.scheduleDeferredRelease(com.facebook.drawee.components.DeferredReleaser$Releasable):void");
    }

    public void cancelDeferredRelease(DeferredReleaser.Releasable releasable) {
        synchronized (this.mLock) {
            this.mPendingReleasables.remove(releasable);
        }
    }
}
