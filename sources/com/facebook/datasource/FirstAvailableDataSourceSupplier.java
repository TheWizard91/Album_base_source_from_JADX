package com.facebook.datasource;

import com.facebook.common.executors.CallerThreadExecutor;
import com.facebook.common.internal.Objects;
import com.facebook.common.internal.Preconditions;
import com.facebook.common.internal.Supplier;
import java.util.List;
import javax.annotation.Nullable;

public class FirstAvailableDataSourceSupplier<T> implements Supplier<DataSource<T>> {
    /* access modifiers changed from: private */
    public final List<Supplier<DataSource<T>>> mDataSourceSuppliers;

    private FirstAvailableDataSourceSupplier(List<Supplier<DataSource<T>>> dataSourceSuppliers) {
        Preconditions.checkArgument(!dataSourceSuppliers.isEmpty(), "List of suppliers is empty!");
        this.mDataSourceSuppliers = dataSourceSuppliers;
    }

    public static <T> FirstAvailableDataSourceSupplier<T> create(List<Supplier<DataSource<T>>> dataSourceSuppliers) {
        return new FirstAvailableDataSourceSupplier<>(dataSourceSuppliers);
    }

    public DataSource<T> get() {
        return new FirstAvailableDataSource();
    }

    public int hashCode() {
        return this.mDataSourceSuppliers.hashCode();
    }

    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof FirstAvailableDataSourceSupplier)) {
            return false;
        }
        return Objects.equal(this.mDataSourceSuppliers, ((FirstAvailableDataSourceSupplier) other).mDataSourceSuppliers);
    }

    public String toString() {
        return Objects.toStringHelper((Object) this).add("list", (Object) this.mDataSourceSuppliers).toString();
    }

    private class FirstAvailableDataSource extends AbstractDataSource<T> {
        private DataSource<T> mCurrentDataSource = null;
        private DataSource<T> mDataSourceWithResult = null;
        private int mIndex = 0;

        public FirstAvailableDataSource() {
            if (!startNextDataSource()) {
                setFailure(new RuntimeException("No data source supplier or supplier returned null."));
            }
        }

        @Nullable
        public synchronized T getResult() {
            DataSource<T> dataSourceWithResult;
            dataSourceWithResult = getDataSourceWithResult();
            return dataSourceWithResult != null ? dataSourceWithResult.getResult() : null;
        }

        public synchronized boolean hasResult() {
            DataSource<T> dataSourceWithResult;
            dataSourceWithResult = getDataSourceWithResult();
            return dataSourceWithResult != null && dataSourceWithResult.hasResult();
        }

        public boolean close() {
            synchronized (this) {
                if (!super.close()) {
                    return false;
                }
                DataSource<T> currentDataSource = this.mCurrentDataSource;
                this.mCurrentDataSource = null;
                DataSource<T> dataSourceWithResult = this.mDataSourceWithResult;
                this.mDataSourceWithResult = null;
                closeSafely(dataSourceWithResult);
                closeSafely(currentDataSource);
                return true;
            }
        }

        private boolean startNextDataSource() {
            Supplier<DataSource<T>> dataSourceSupplier = getNextSupplier();
            DataSource<T> dataSource = dataSourceSupplier != null ? dataSourceSupplier.get() : null;
            if (!setCurrentDataSource(dataSource) || dataSource == null) {
                closeSafely(dataSource);
                return false;
            }
            dataSource.subscribe(new InternalDataSubscriber(), CallerThreadExecutor.getInstance());
            return true;
        }

        @Nullable
        private synchronized Supplier<DataSource<T>> getNextSupplier() {
            if (isClosed() || this.mIndex >= FirstAvailableDataSourceSupplier.this.mDataSourceSuppliers.size()) {
                return null;
            }
            List access$100 = FirstAvailableDataSourceSupplier.this.mDataSourceSuppliers;
            int i = this.mIndex;
            this.mIndex = i + 1;
            return (Supplier) access$100.get(i);
        }

        private synchronized boolean setCurrentDataSource(DataSource<T> dataSource) {
            if (isClosed()) {
                return false;
            }
            this.mCurrentDataSource = dataSource;
            return true;
        }

        private synchronized boolean clearCurrentDataSource(DataSource<T> dataSource) {
            if (!isClosed()) {
                if (dataSource == this.mCurrentDataSource) {
                    this.mCurrentDataSource = null;
                    return true;
                }
            }
            return false;
        }

        @Nullable
        private synchronized DataSource<T> getDataSourceWithResult() {
            return this.mDataSourceWithResult;
        }

        /* JADX WARNING: Code restructure failed: missing block: B:12:0x0013, code lost:
            closeSafely(r0);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:13:0x0016, code lost:
            return;
         */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        private void maybeSetDataSourceWithResult(com.facebook.datasource.DataSource<T> r3, boolean r4) {
            /*
                r2 = this;
                r0 = 0
                monitor-enter(r2)
                com.facebook.datasource.DataSource<T> r1 = r2.mCurrentDataSource     // Catch:{ all -> 0x0019 }
                if (r3 != r1) goto L_0x0017
                com.facebook.datasource.DataSource<T> r1 = r2.mDataSourceWithResult     // Catch:{ all -> 0x0019 }
                if (r3 != r1) goto L_0x000b
                goto L_0x0017
            L_0x000b:
                if (r1 == 0) goto L_0x000f
                if (r4 == 0) goto L_0x0012
            L_0x000f:
                r0 = r1
                r2.mDataSourceWithResult = r3     // Catch:{ all -> 0x0019 }
            L_0x0012:
                monitor-exit(r2)     // Catch:{ all -> 0x0019 }
                r2.closeSafely(r0)
                return
            L_0x0017:
                monitor-exit(r2)     // Catch:{ all -> 0x0019 }
                return
            L_0x0019:
                r1 = move-exception
                monitor-exit(r2)     // Catch:{ all -> 0x0019 }
                throw r1
            */
            throw new UnsupportedOperationException("Method not decompiled: com.facebook.datasource.FirstAvailableDataSourceSupplier.FirstAvailableDataSource.maybeSetDataSourceWithResult(com.facebook.datasource.DataSource, boolean):void");
        }

        /* access modifiers changed from: private */
        public void onDataSourceFailed(DataSource<T> dataSource) {
            if (clearCurrentDataSource(dataSource)) {
                if (dataSource != getDataSourceWithResult()) {
                    closeSafely(dataSource);
                }
                if (!startNextDataSource()) {
                    setFailure(dataSource.getFailureCause());
                }
            }
        }

        /* access modifiers changed from: private */
        public void onDataSourceNewResult(DataSource<T> dataSource) {
            maybeSetDataSourceWithResult(dataSource, dataSource.isFinished());
            if (dataSource == getDataSourceWithResult()) {
                setResult(null, dataSource.isFinished());
            }
        }

        private void closeSafely(DataSource<T> dataSource) {
            if (dataSource != null) {
                dataSource.close();
            }
        }

        private class InternalDataSubscriber implements DataSubscriber<T> {
            private InternalDataSubscriber() {
            }

            public void onFailure(DataSource<T> dataSource) {
                FirstAvailableDataSource.this.onDataSourceFailed(dataSource);
            }

            public void onCancellation(DataSource<T> dataSource) {
            }

            public void onNewResult(DataSource<T> dataSource) {
                if (dataSource.hasResult()) {
                    FirstAvailableDataSource.this.onDataSourceNewResult(dataSource);
                } else if (dataSource.isFinished()) {
                    FirstAvailableDataSource.this.onDataSourceFailed(dataSource);
                }
            }

            public void onProgressUpdate(DataSource<T> dataSource) {
                FirstAvailableDataSource.this.setProgress(Math.max(FirstAvailableDataSource.this.getProgress(), dataSource.getProgress()));
            }
        }
    }
}
