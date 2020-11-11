package bolts;

import java.io.Closeable;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class CancellationTokenSource implements Closeable {
    private boolean cancellationRequested;
    private boolean closed;
    private final ScheduledExecutorService executor = BoltsExecutors.scheduled();
    /* access modifiers changed from: private */
    public final Object lock = new Object();
    private final List<CancellationTokenRegistration> registrations = new ArrayList();
    /* access modifiers changed from: private */
    public ScheduledFuture<?> scheduledCancellation;

    public boolean isCancellationRequested() {
        boolean z;
        synchronized (this.lock) {
            throwIfClosed();
            z = this.cancellationRequested;
        }
        return z;
    }

    public CancellationToken getToken() {
        CancellationToken cancellationToken;
        synchronized (this.lock) {
            throwIfClosed();
            cancellationToken = new CancellationToken(this);
        }
        return cancellationToken;
    }

    public void cancel() {
        synchronized (this.lock) {
            try {
                throwIfClosed();
                if (!this.cancellationRequested) {
                    cancelScheduledCancellation();
                    this.cancellationRequested = true;
                    List<CancellationTokenRegistration> registrations2 = new ArrayList<>(this.registrations);
                    try {
                        notifyListeners(registrations2);
                    } catch (Throwable th) {
                        th = th;
                        throw th;
                    }
                }
            } catch (Throwable th2) {
                th = th2;
                throw th;
            }
        }
    }

    public void cancelAfter(long delay) {
        cancelAfter(delay, TimeUnit.MILLISECONDS);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:17:0x002e, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void cancelAfter(long r5, java.util.concurrent.TimeUnit r7) {
        /*
            r4 = this;
            r0 = -1
            int r2 = (r5 > r0 ? 1 : (r5 == r0 ? 0 : -1))
            if (r2 < 0) goto L_0x0032
            r2 = 0
            int r2 = (r5 > r2 ? 1 : (r5 == r2 ? 0 : -1))
            if (r2 != 0) goto L_0x0010
            r4.cancel()
            return
        L_0x0010:
            java.lang.Object r2 = r4.lock
            monitor-enter(r2)
            boolean r3 = r4.cancellationRequested     // Catch:{ all -> 0x002f }
            if (r3 == 0) goto L_0x0019
            monitor-exit(r2)     // Catch:{ all -> 0x002f }
            return
        L_0x0019:
            r4.cancelScheduledCancellation()     // Catch:{ all -> 0x002f }
            int r0 = (r5 > r0 ? 1 : (r5 == r0 ? 0 : -1))
            if (r0 == 0) goto L_0x002d
            java.util.concurrent.ScheduledExecutorService r0 = r4.executor     // Catch:{ all -> 0x002f }
            bolts.CancellationTokenSource$1 r1 = new bolts.CancellationTokenSource$1     // Catch:{ all -> 0x002f }
            r1.<init>()     // Catch:{ all -> 0x002f }
            java.util.concurrent.ScheduledFuture r0 = r0.schedule(r1, r5, r7)     // Catch:{ all -> 0x002f }
            r4.scheduledCancellation = r0     // Catch:{ all -> 0x002f }
        L_0x002d:
            monitor-exit(r2)     // Catch:{ all -> 0x002f }
            return
        L_0x002f:
            r0 = move-exception
            monitor-exit(r2)     // Catch:{ all -> 0x002f }
            throw r0
        L_0x0032:
            java.lang.IllegalArgumentException r0 = new java.lang.IllegalArgumentException
            java.lang.String r1 = "Delay must be >= -1"
            r0.<init>(r1)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: bolts.CancellationTokenSource.cancelAfter(long, java.util.concurrent.TimeUnit):void");
    }

    public void close() {
        synchronized (this.lock) {
            if (!this.closed) {
                cancelScheduledCancellation();
                for (CancellationTokenRegistration registration : this.registrations) {
                    registration.close();
                }
                this.registrations.clear();
                this.closed = true;
            }
        }
    }

    /* access modifiers changed from: package-private */
    /* JADX WARNING: Code restructure failed: missing block: B:12:0x001b, code lost:
        return r1;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public bolts.CancellationTokenRegistration register(java.lang.Runnable r4) {
        /*
            r3 = this;
            java.lang.Object r0 = r3.lock
            monitor-enter(r0)
            r1 = 0
            r3.throwIfClosed()     // Catch:{ all -> 0x001c }
            bolts.CancellationTokenRegistration r2 = new bolts.CancellationTokenRegistration     // Catch:{ all -> 0x001c }
            r2.<init>(r3, r4)     // Catch:{ all -> 0x001c }
            r1 = r2
            boolean r2 = r3.cancellationRequested     // Catch:{ all -> 0x001f }
            if (r2 == 0) goto L_0x0015
            r1.runAction()     // Catch:{ all -> 0x001f }
            goto L_0x001a
        L_0x0015:
            java.util.List<bolts.CancellationTokenRegistration> r2 = r3.registrations     // Catch:{ all -> 0x001f }
            r2.add(r1)     // Catch:{ all -> 0x001f }
        L_0x001a:
            monitor-exit(r0)     // Catch:{ all -> 0x001f }
            return r1
        L_0x001c:
            r2 = move-exception
        L_0x001d:
            monitor-exit(r0)     // Catch:{ all -> 0x001f }
            throw r2
        L_0x001f:
            r2 = move-exception
            goto L_0x001d
        */
        throw new UnsupportedOperationException("Method not decompiled: bolts.CancellationTokenSource.register(java.lang.Runnable):bolts.CancellationTokenRegistration");
    }

    /* access modifiers changed from: package-private */
    public void throwIfCancellationRequested() throws CancellationException {
        synchronized (this.lock) {
            throwIfClosed();
            if (this.cancellationRequested) {
                throw new CancellationException();
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void unregister(CancellationTokenRegistration registration) {
        synchronized (this.lock) {
            throwIfClosed();
            this.registrations.remove(registration);
        }
    }

    private void notifyListeners(List<CancellationTokenRegistration> registrations2) {
        for (CancellationTokenRegistration registration : registrations2) {
            registration.runAction();
        }
    }

    public String toString() {
        return String.format(Locale.US, "%s@%s[cancellationRequested=%s]", new Object[]{getClass().getName(), Integer.toHexString(hashCode()), Boolean.toString(isCancellationRequested())});
    }

    private void throwIfClosed() {
        if (this.closed) {
            throw new IllegalStateException("Object already closed");
        }
    }

    private void cancelScheduledCancellation() {
        ScheduledFuture<?> scheduledFuture = this.scheduledCancellation;
        if (scheduledFuture != null) {
            scheduledFuture.cancel(true);
            this.scheduledCancellation = null;
        }
    }
}
