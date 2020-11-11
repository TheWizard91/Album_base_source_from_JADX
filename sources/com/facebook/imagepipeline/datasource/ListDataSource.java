package com.facebook.imagepipeline.datasource;

import com.facebook.common.executors.CallerThreadExecutor;
import com.facebook.common.internal.Preconditions;
import com.facebook.common.references.CloseableReference;
import com.facebook.datasource.AbstractDataSource;
import com.facebook.datasource.DataSource;
import com.facebook.datasource.DataSubscriber;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CancellationException;
import javax.annotation.Nullable;

public class ListDataSource<T> extends AbstractDataSource<List<CloseableReference<T>>> {
    private final DataSource<CloseableReference<T>>[] mDataSources;
    private int mFinishedDataSources = 0;

    protected ListDataSource(DataSource<CloseableReference<T>>[] dataSources) {
        this.mDataSources = dataSources;
    }

    public static <T> ListDataSource<T> create(DataSource<CloseableReference<T>>... dataSources) {
        Preconditions.checkNotNull(dataSources);
        Preconditions.checkState(dataSources.length > 0);
        ListDataSource<T> listDataSource = new ListDataSource<>(dataSources);
        for (DataSource<CloseableReference<T>> dataSource : dataSources) {
            if (dataSource != null) {
                listDataSource.getClass();
                dataSource.subscribe(new InternalDataSubscriber(), CallerThreadExecutor.getInstance());
            }
        }
        return listDataSource;
    }

    @Nullable
    public synchronized List<CloseableReference<T>> getResult() {
        if (!hasResult()) {
            return null;
        }
        List<CloseableReference<T>> results = new ArrayList<>(this.mDataSources.length);
        for (DataSource<CloseableReference<T>> dataSource : this.mDataSources) {
            results.add(dataSource.getResult());
        }
        return results;
    }

    public synchronized boolean hasResult() {
        return !isClosed() && this.mFinishedDataSources == this.mDataSources.length;
    }

    public boolean close() {
        if (!super.close()) {
            return false;
        }
        for (DataSource<CloseableReference<T>> close : this.mDataSources) {
            close.close();
        }
        return true;
    }

    /* access modifiers changed from: private */
    public void onDataSourceFinished() {
        if (increaseAndCheckIfLast()) {
            setResult(null, true);
        }
    }

    private synchronized boolean increaseAndCheckIfLast() {
        boolean z;
        z = true;
        int i = this.mFinishedDataSources + 1;
        this.mFinishedDataSources = i;
        if (i != this.mDataSources.length) {
            z = false;
        }
        return z;
    }

    /* access modifiers changed from: private */
    public void onDataSourceFailed(DataSource<CloseableReference<T>> dataSource) {
        setFailure(dataSource.getFailureCause());
    }

    /* access modifiers changed from: private */
    public void onDataSourceCancelled() {
        setFailure(new CancellationException());
    }

    /* access modifiers changed from: private */
    public void onDataSourceProgress() {
        float progress = 0.0f;
        for (DataSource<CloseableReference<T>> progress2 : this.mDataSources) {
            progress += progress2.getProgress();
        }
        setProgress(progress / ((float) this.mDataSources.length));
    }

    private class InternalDataSubscriber implements DataSubscriber<CloseableReference<T>> {
        boolean mFinished;

        private InternalDataSubscriber() {
            this.mFinished = false;
        }

        private synchronized boolean tryFinish() {
            if (this.mFinished) {
                return false;
            }
            this.mFinished = true;
            return true;
        }

        public void onFailure(DataSource<CloseableReference<T>> dataSource) {
            ListDataSource.this.onDataSourceFailed(dataSource);
        }

        public void onCancellation(DataSource<CloseableReference<T>> dataSource) {
            ListDataSource.this.onDataSourceCancelled();
        }

        public void onNewResult(DataSource<CloseableReference<T>> dataSource) {
            if (dataSource.isFinished() && tryFinish()) {
                ListDataSource.this.onDataSourceFinished();
            }
        }

        public void onProgressUpdate(DataSource<CloseableReference<T>> dataSource) {
            ListDataSource.this.onDataSourceProgress();
        }
    }
}
