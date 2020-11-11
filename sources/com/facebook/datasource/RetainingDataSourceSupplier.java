package com.facebook.datasource;

import com.facebook.common.internal.Supplier;
import java.util.Collections;
import java.util.Set;
import java.util.WeakHashMap;
import javax.annotation.Nullable;

public class RetainingDataSourceSupplier<T> implements Supplier<DataSource<T>> {
    @Nullable
    private Supplier<DataSource<T>> mCurrentDataSourceSupplier = null;
    private final Set<RetainingDataSource> mDataSources = Collections.newSetFromMap(new WeakHashMap());

    public DataSource<T> get() {
        RetainingDataSource dataSource = new RetainingDataSource();
        dataSource.setSupplier(this.mCurrentDataSourceSupplier);
        this.mDataSources.add(dataSource);
        return dataSource;
    }

    public void replaceSupplier(Supplier<DataSource<T>> supplier) {
        this.mCurrentDataSourceSupplier = supplier;
        for (RetainingDataSource dataSource : this.mDataSources) {
            if (!dataSource.isClosed()) {
                dataSource.setSupplier(supplier);
            }
        }
    }

    private static class RetainingDataSource<T> extends AbstractDataSource<T> {
        @Nullable
        private DataSource<T> mDataSource;

        private RetainingDataSource() {
            this.mDataSource = null;
        }

        /* JADX WARNING: Code restructure failed: missing block: B:16:0x0023, code lost:
            if (r1 == null) goto L_0x0031;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:17:0x0025, code lost:
            r1.subscribe(new com.facebook.datasource.RetainingDataSourceSupplier.RetainingDataSource.InternalDataSubscriber(r4, (com.facebook.datasource.RetainingDataSourceSupplier.C06881) null), com.facebook.common.executors.CallerThreadExecutor.getInstance());
         */
        /* JADX WARNING: Code restructure failed: missing block: B:18:0x0031, code lost:
            closeSafely(r2);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:19:0x0034, code lost:
            return;
         */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public void setSupplier(@javax.annotation.Nullable com.facebook.common.internal.Supplier<com.facebook.datasource.DataSource<T>> r5) {
            /*
                r4 = this;
                boolean r0 = r4.isClosed()
                if (r0 == 0) goto L_0x0007
                return
            L_0x0007:
                r0 = 0
                if (r5 == 0) goto L_0x0011
                java.lang.Object r1 = r5.get()
                com.facebook.datasource.DataSource r1 = (com.facebook.datasource.DataSource) r1
                goto L_0x0012
            L_0x0011:
                r1 = r0
            L_0x0012:
                monitor-enter(r4)
                boolean r2 = r4.isClosed()     // Catch:{ all -> 0x0035 }
                if (r2 == 0) goto L_0x001e
                closeSafely(r1)     // Catch:{ all -> 0x0035 }
                monitor-exit(r4)     // Catch:{ all -> 0x0035 }
                return
            L_0x001e:
                com.facebook.datasource.DataSource<T> r2 = r4.mDataSource     // Catch:{ all -> 0x0035 }
                r4.mDataSource = r1     // Catch:{ all -> 0x0035 }
                monitor-exit(r4)     // Catch:{ all -> 0x0035 }
                if (r1 == 0) goto L_0x0031
                com.facebook.datasource.RetainingDataSourceSupplier$RetainingDataSource$InternalDataSubscriber r3 = new com.facebook.datasource.RetainingDataSourceSupplier$RetainingDataSource$InternalDataSubscriber
                r3.<init>()
                com.facebook.common.executors.CallerThreadExecutor r0 = com.facebook.common.executors.CallerThreadExecutor.getInstance()
                r1.subscribe(r3, r0)
            L_0x0031:
                closeSafely(r2)
                return
            L_0x0035:
                r0 = move-exception
                monitor-exit(r4)     // Catch:{ all -> 0x0035 }
                throw r0
            */
            throw new UnsupportedOperationException("Method not decompiled: com.facebook.datasource.RetainingDataSourceSupplier.RetainingDataSource.setSupplier(com.facebook.common.internal.Supplier):void");
        }

        @Nullable
        public synchronized T getResult() {
            DataSource<T> dataSource;
            dataSource = this.mDataSource;
            return dataSource != null ? dataSource.getResult() : null;
        }

        public synchronized boolean hasResult() {
            DataSource<T> dataSource;
            dataSource = this.mDataSource;
            return dataSource != null && dataSource.hasResult();
        }

        public boolean close() {
            synchronized (this) {
                if (!super.close()) {
                    return false;
                }
                DataSource<T> dataSource = this.mDataSource;
                this.mDataSource = null;
                closeSafely(dataSource);
                return true;
            }
        }

        /* access modifiers changed from: private */
        public void onDataSourceNewResult(DataSource<T> dataSource) {
            if (dataSource == this.mDataSource) {
                setResult(null, false);
            }
        }

        /* access modifiers changed from: private */
        public void onDataSourceFailed(DataSource<T> dataSource) {
        }

        /* access modifiers changed from: private */
        public void onDatasourceProgress(DataSource<T> dataSource) {
            if (dataSource == this.mDataSource) {
                setProgress(dataSource.getProgress());
            }
        }

        private static <T> void closeSafely(DataSource<T> dataSource) {
            if (dataSource != null) {
                dataSource.close();
            }
        }

        private class InternalDataSubscriber implements DataSubscriber<T> {
            private InternalDataSubscriber() {
            }

            public void onNewResult(DataSource<T> dataSource) {
                if (dataSource.hasResult()) {
                    RetainingDataSource.this.onDataSourceNewResult(dataSource);
                } else if (dataSource.isFinished()) {
                    RetainingDataSource.this.onDataSourceFailed(dataSource);
                }
            }

            public void onFailure(DataSource<T> dataSource) {
                RetainingDataSource.this.onDataSourceFailed(dataSource);
            }

            public void onCancellation(DataSource<T> dataSource) {
            }

            public void onProgressUpdate(DataSource<T> dataSource) {
                RetainingDataSource.this.onDatasourceProgress(dataSource);
            }
        }

        public boolean hasMultipleResults() {
            return true;
        }
    }
}
