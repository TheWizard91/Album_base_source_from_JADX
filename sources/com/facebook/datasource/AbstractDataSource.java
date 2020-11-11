package com.facebook.datasource;

import android.util.Pair;
import java.util.Iterator;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executor;
import javax.annotation.Nullable;

public abstract class AbstractDataSource<T> implements DataSource<T> {
    @Nullable
    private static volatile DataSourceInstrumenter sDataSourceInstrumenter;
    private DataSourceStatus mDataSourceStatus = DataSourceStatus.IN_PROGRESS;
    private Throwable mFailureThrowable = null;
    private boolean mIsClosed = false;
    private float mProgress = 0.0f;
    @Nullable
    private T mResult = null;
    private final ConcurrentLinkedQueue<Pair<DataSubscriber<T>, Executor>> mSubscribers = new ConcurrentLinkedQueue<>();

    public interface DataSourceInstrumenter {
        Runnable decorateRunnable(Runnable runnable, String str);
    }

    private enum DataSourceStatus {
        IN_PROGRESS,
        SUCCESS,
        FAILURE
    }

    public static void provideInstrumenter(@Nullable DataSourceInstrumenter dataSourceInstrumenter) {
        sDataSourceInstrumenter = dataSourceInstrumenter;
    }

    protected AbstractDataSource() {
    }

    public synchronized boolean isClosed() {
        return this.mIsClosed;
    }

    public synchronized boolean isFinished() {
        return this.mDataSourceStatus != DataSourceStatus.IN_PROGRESS;
    }

    public synchronized boolean hasResult() {
        return this.mResult != null;
    }

    @Nullable
    public synchronized T getResult() {
        return this.mResult;
    }

    public synchronized boolean hasFailed() {
        return this.mDataSourceStatus == DataSourceStatus.FAILURE;
    }

    @Nullable
    public synchronized Throwable getFailureCause() {
        return this.mFailureThrowable;
    }

    public synchronized float getProgress() {
        return this.mProgress;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:10:0x0013, code lost:
        closeResult(r1);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:12:0x001a, code lost:
        if (isFinished() != false) goto L_0x001f;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:13:0x001c, code lost:
        notifyDataSubscribers();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:14:0x001f, code lost:
        monitor-enter(r3);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:16:?, code lost:
        r3.mSubscribers.clear();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:17:0x0025, code lost:
        monitor-exit(r3);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:18:0x0026, code lost:
        return true;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:9:0x0011, code lost:
        if (r1 == null) goto L_0x0016;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean close() {
        /*
            r3 = this;
            monitor-enter(r3)
            boolean r0 = r3.mIsClosed     // Catch:{ all -> 0x002a }
            if (r0 == 0) goto L_0x0008
            r0 = 0
            monitor-exit(r3)     // Catch:{ all -> 0x002a }
            return r0
        L_0x0008:
            r0 = 1
            r3.mIsClosed = r0     // Catch:{ all -> 0x002a }
            T r1 = r3.mResult     // Catch:{ all -> 0x002a }
            r2 = 0
            r3.mResult = r2     // Catch:{ all -> 0x002a }
            monitor-exit(r3)     // Catch:{ all -> 0x002a }
            if (r1 == 0) goto L_0x0016
            r3.closeResult(r1)
        L_0x0016:
            boolean r2 = r3.isFinished()
            if (r2 != 0) goto L_0x001f
            r3.notifyDataSubscribers()
        L_0x001f:
            monitor-enter(r3)
            java.util.concurrent.ConcurrentLinkedQueue<android.util.Pair<com.facebook.datasource.DataSubscriber<T>, java.util.concurrent.Executor>> r2 = r3.mSubscribers     // Catch:{ all -> 0x0027 }
            r2.clear()     // Catch:{ all -> 0x0027 }
            monitor-exit(r3)     // Catch:{ all -> 0x0027 }
            return r0
        L_0x0027:
            r0 = move-exception
            monitor-exit(r3)     // Catch:{ all -> 0x0027 }
            throw r0
        L_0x002a:
            r0 = move-exception
            monitor-exit(r3)     // Catch:{ all -> 0x002a }
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.facebook.datasource.AbstractDataSource.close():boolean");
    }

    /* access modifiers changed from: protected */
    public void closeResult(@Nullable T t) {
    }

    /* JADX WARNING: Code restructure failed: missing block: B:20:0x0033, code lost:
        if (r0 == false) goto L_?;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:21:0x0035, code lost:
        notifyDataSubscriber(r4, r5, hasFailed(), wasCancelled());
     */
    /* JADX WARNING: Code restructure failed: missing block: B:29:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:30:?, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void subscribe(com.facebook.datasource.DataSubscriber<T> r4, java.util.concurrent.Executor r5) {
        /*
            r3 = this;
            com.facebook.common.internal.Preconditions.checkNotNull(r4)
            com.facebook.common.internal.Preconditions.checkNotNull(r5)
            monitor-enter(r3)
            boolean r0 = r3.mIsClosed     // Catch:{ all -> 0x0041 }
            if (r0 == 0) goto L_0x000d
            monitor-exit(r3)     // Catch:{ all -> 0x0041 }
            return
        L_0x000d:
            com.facebook.datasource.AbstractDataSource$DataSourceStatus r0 = r3.mDataSourceStatus     // Catch:{ all -> 0x0041 }
            com.facebook.datasource.AbstractDataSource$DataSourceStatus r1 = com.facebook.datasource.AbstractDataSource.DataSourceStatus.IN_PROGRESS     // Catch:{ all -> 0x0041 }
            if (r0 != r1) goto L_0x001c
            java.util.concurrent.ConcurrentLinkedQueue<android.util.Pair<com.facebook.datasource.DataSubscriber<T>, java.util.concurrent.Executor>> r0 = r3.mSubscribers     // Catch:{ all -> 0x0041 }
            android.util.Pair r1 = android.util.Pair.create(r4, r5)     // Catch:{ all -> 0x0041 }
            r0.add(r1)     // Catch:{ all -> 0x0041 }
        L_0x001c:
            boolean r0 = r3.hasResult()     // Catch:{ all -> 0x0041 }
            if (r0 != 0) goto L_0x0031
            boolean r0 = r3.isFinished()     // Catch:{ all -> 0x0041 }
            if (r0 != 0) goto L_0x0031
            boolean r0 = r3.wasCancelled()     // Catch:{ all -> 0x0041 }
            if (r0 == 0) goto L_0x002f
            goto L_0x0031
        L_0x002f:
            r0 = 0
            goto L_0x0032
        L_0x0031:
            r0 = 1
        L_0x0032:
            monitor-exit(r3)     // Catch:{ all -> 0x0041 }
            if (r0 == 0) goto L_0x0040
            boolean r1 = r3.hasFailed()
            boolean r2 = r3.wasCancelled()
            r3.notifyDataSubscriber(r4, r5, r1, r2)
        L_0x0040:
            return
        L_0x0041:
            r0 = move-exception
            monitor-exit(r3)     // Catch:{ all -> 0x0041 }
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.facebook.datasource.AbstractDataSource.subscribe(com.facebook.datasource.DataSubscriber, java.util.concurrent.Executor):void");
    }

    private void notifyDataSubscribers() {
        boolean isFailure = hasFailed();
        boolean isCancellation = wasCancelled();
        Iterator<Pair<DataSubscriber<T>, Executor>> it = this.mSubscribers.iterator();
        while (it.hasNext()) {
            Pair<DataSubscriber<T>, Executor> pair = it.next();
            notifyDataSubscriber((DataSubscriber) pair.first, (Executor) pair.second, isFailure, isCancellation);
        }
    }

    /* access modifiers changed from: protected */
    public void notifyDataSubscriber(final DataSubscriber<T> dataSubscriber, Executor executor, final boolean isFailure, final boolean isCancellation) {
        Runnable runnable = new Runnable() {
            public void run() {
                if (isFailure) {
                    dataSubscriber.onFailure(AbstractDataSource.this);
                } else if (isCancellation) {
                    dataSubscriber.onCancellation(AbstractDataSource.this);
                } else {
                    dataSubscriber.onNewResult(AbstractDataSource.this);
                }
            }
        };
        DataSourceInstrumenter instrumenter = getDataSourceInstrumenter();
        if (instrumenter != null) {
            runnable = instrumenter.decorateRunnable(runnable, "AbstractDataSource_notifyDataSubscriber");
        }
        executor.execute(runnable);
    }

    private synchronized boolean wasCancelled() {
        return isClosed() && !isFinished();
    }

    /* access modifiers changed from: protected */
    public boolean setResult(@Nullable T value, boolean isLast) {
        boolean result = setResultInternal(value, isLast);
        if (result) {
            notifyDataSubscribers();
        }
        return result;
    }

    /* access modifiers changed from: protected */
    public boolean setFailure(Throwable throwable) {
        boolean result = setFailureInternal(throwable);
        if (result) {
            notifyDataSubscribers();
        }
        return result;
    }

    /* access modifiers changed from: protected */
    public boolean setProgress(float progress) {
        boolean result = setProgressInternal(progress);
        if (result) {
            notifyProgressUpdate();
        }
        return result;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:16:0x0020, code lost:
        if (r0 == null) goto L_0x0025;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:17:0x0022, code lost:
        closeResult(r0);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:18:0x0025, code lost:
        return true;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:22:0x0029, code lost:
        if (r0 == null) goto L_0x002e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:23:0x002b, code lost:
        closeResult(r0);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:24:0x002e, code lost:
        return false;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private boolean setResultInternal(@javax.annotation.Nullable T r4, boolean r5) {
        /*
            r3 = this;
            r0 = 0
            monitor-enter(r3)     // Catch:{ all -> 0x0032 }
            boolean r1 = r3.mIsClosed     // Catch:{ all -> 0x002f }
            if (r1 != 0) goto L_0x0026
            com.facebook.datasource.AbstractDataSource$DataSourceStatus r1 = r3.mDataSourceStatus     // Catch:{ all -> 0x002f }
            com.facebook.datasource.AbstractDataSource$DataSourceStatus r2 = com.facebook.datasource.AbstractDataSource.DataSourceStatus.IN_PROGRESS     // Catch:{ all -> 0x002f }
            if (r1 == r2) goto L_0x000d
            goto L_0x0026
        L_0x000d:
            if (r5 == 0) goto L_0x0017
            com.facebook.datasource.AbstractDataSource$DataSourceStatus r1 = com.facebook.datasource.AbstractDataSource.DataSourceStatus.SUCCESS     // Catch:{ all -> 0x002f }
            r3.mDataSourceStatus = r1     // Catch:{ all -> 0x002f }
            r1 = 1065353216(0x3f800000, float:1.0)
            r3.mProgress = r1     // Catch:{ all -> 0x002f }
        L_0x0017:
            T r1 = r3.mResult     // Catch:{ all -> 0x002f }
            if (r1 == r4) goto L_0x001e
            r0 = r1
            r3.mResult = r4     // Catch:{ all -> 0x002f }
        L_0x001e:
            r1 = 1
            monitor-exit(r3)     // Catch:{ all -> 0x002f }
            if (r0 == 0) goto L_0x0025
            r3.closeResult(r0)
        L_0x0025:
            return r1
        L_0x0026:
            r0 = r4
            r1 = 0
            monitor-exit(r3)     // Catch:{ all -> 0x002f }
            if (r0 == 0) goto L_0x002e
            r3.closeResult(r0)
        L_0x002e:
            return r1
        L_0x002f:
            r1 = move-exception
            monitor-exit(r3)     // Catch:{ all -> 0x002f }
            throw r1     // Catch:{ all -> 0x0032 }
        L_0x0032:
            r1 = move-exception
            if (r0 == 0) goto L_0x0038
            r3.closeResult(r0)
        L_0x0038:
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.facebook.datasource.AbstractDataSource.setResultInternal(java.lang.Object, boolean):boolean");
    }

    private synchronized boolean setFailureInternal(Throwable throwable) {
        if (!this.mIsClosed) {
            if (this.mDataSourceStatus == DataSourceStatus.IN_PROGRESS) {
                this.mDataSourceStatus = DataSourceStatus.FAILURE;
                this.mFailureThrowable = throwable;
                return true;
            }
        }
        return false;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:18:0x001b, code lost:
        return false;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private synchronized boolean setProgressInternal(float r4) {
        /*
            r3 = this;
            monitor-enter(r3)
            boolean r0 = r3.mIsClosed     // Catch:{ all -> 0x001c }
            r1 = 0
            if (r0 != 0) goto L_0x001a
            com.facebook.datasource.AbstractDataSource$DataSourceStatus r0 = r3.mDataSourceStatus     // Catch:{ all -> 0x001c }
            com.facebook.datasource.AbstractDataSource$DataSourceStatus r2 = com.facebook.datasource.AbstractDataSource.DataSourceStatus.IN_PROGRESS     // Catch:{ all -> 0x001c }
            if (r0 == r2) goto L_0x000d
            goto L_0x001a
        L_0x000d:
            float r0 = r3.mProgress     // Catch:{ all -> 0x001c }
            int r0 = (r4 > r0 ? 1 : (r4 == r0 ? 0 : -1))
            if (r0 >= 0) goto L_0x0015
            monitor-exit(r3)
            return r1
        L_0x0015:
            r3.mProgress = r4     // Catch:{ all -> 0x001c }
            r0 = 1
            monitor-exit(r3)
            return r0
        L_0x001a:
            monitor-exit(r3)
            return r1
        L_0x001c:
            r4 = move-exception
            monitor-exit(r3)
            throw r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.facebook.datasource.AbstractDataSource.setProgressInternal(float):boolean");
    }

    /* access modifiers changed from: protected */
    public void notifyProgressUpdate() {
        Iterator<Pair<DataSubscriber<T>, Executor>> it = this.mSubscribers.iterator();
        while (it.hasNext()) {
            Pair<DataSubscriber<T>, Executor> pair = it.next();
            final DataSubscriber<T> subscriber = (DataSubscriber) pair.first;
            ((Executor) pair.second).execute(new Runnable() {
                public void run() {
                    subscriber.onProgressUpdate(AbstractDataSource.this);
                }
            });
        }
    }

    @Nullable
    public static DataSourceInstrumenter getDataSourceInstrumenter() {
        return sDataSourceInstrumenter;
    }

    public boolean hasMultipleResults() {
        return false;
    }
}
