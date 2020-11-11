package com.facebook.datasource;

import com.facebook.common.executors.CallerThreadExecutor;
import com.facebook.common.internal.Objects;
import com.facebook.common.internal.Preconditions;
import com.facebook.common.internal.Supplier;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import javax.annotation.Nullable;

public class IncreasingQualityDataSourceSupplier<T> implements Supplier<DataSource<T>> {
    /* access modifiers changed from: private */
    public final boolean mDataSourceLazy;
    /* access modifiers changed from: private */
    public final List<Supplier<DataSource<T>>> mDataSourceSuppliers;

    private IncreasingQualityDataSourceSupplier(List<Supplier<DataSource<T>>> dataSourceSuppliers, boolean dataSourceLazy) {
        Preconditions.checkArgument(!dataSourceSuppliers.isEmpty(), "List of suppliers is empty!");
        this.mDataSourceSuppliers = dataSourceSuppliers;
        this.mDataSourceLazy = dataSourceLazy;
    }

    public static <T> IncreasingQualityDataSourceSupplier<T> create(List<Supplier<DataSource<T>>> dataSourceSuppliers) {
        return create(dataSourceSuppliers, false);
    }

    public static <T> IncreasingQualityDataSourceSupplier<T> create(List<Supplier<DataSource<T>>> dataSourceSuppliers, boolean dataSourceLazy) {
        return new IncreasingQualityDataSourceSupplier<>(dataSourceSuppliers, dataSourceLazy);
    }

    public DataSource<T> get() {
        return new IncreasingQualityDataSource();
    }

    public int hashCode() {
        return this.mDataSourceSuppliers.hashCode();
    }

    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof IncreasingQualityDataSourceSupplier)) {
            return false;
        }
        return Objects.equal(this.mDataSourceSuppliers, ((IncreasingQualityDataSourceSupplier) other).mDataSourceSuppliers);
    }

    public String toString() {
        return Objects.toStringHelper((Object) this).add("list", (Object) this.mDataSourceSuppliers).toString();
    }

    private class IncreasingQualityDataSource extends AbstractDataSource<T> {
        @Nullable
        private ArrayList<DataSource<T>> mDataSources;
        @Nullable
        private Throwable mDelayedError;
        private AtomicInteger mFinishedDataSources;
        private int mIndexOfDataSourceWithResult;
        private int mNumberOfDataSources;

        public IncreasingQualityDataSource() {
            if (!IncreasingQualityDataSourceSupplier.this.mDataSourceLazy) {
                ensureDataSourceInitialized();
            }
        }

        private void ensureDataSourceInitialized() {
            if (this.mFinishedDataSources == null) {
                synchronized (this) {
                    if (this.mFinishedDataSources == null) {
                        this.mFinishedDataSources = new AtomicInteger(0);
                        int n = IncreasingQualityDataSourceSupplier.this.mDataSourceSuppliers.size();
                        this.mNumberOfDataSources = n;
                        this.mIndexOfDataSourceWithResult = n;
                        this.mDataSources = new ArrayList<>(n);
                        int i = 0;
                        while (true) {
                            if (i >= n) {
                                break;
                            }
                            DataSource<T> dataSource = (DataSource) ((Supplier) IncreasingQualityDataSourceSupplier.this.mDataSourceSuppliers.get(i)).get();
                            this.mDataSources.add(dataSource);
                            dataSource.subscribe(new InternalDataSubscriber(i), CallerThreadExecutor.getInstance());
                            if (dataSource.hasResult()) {
                                break;
                            }
                            i++;
                        }
                    }
                }
            }
        }

        @Nullable
        private synchronized DataSource<T> getDataSource(int i) {
            ArrayList<DataSource<T>> arrayList;
            arrayList = this.mDataSources;
            return (arrayList == null || i >= arrayList.size()) ? null : this.mDataSources.get(i);
        }

        @Nullable
        private synchronized DataSource<T> getAndClearDataSource(int i) {
            DataSource<T> dataSource;
            ArrayList<DataSource<T>> arrayList = this.mDataSources;
            dataSource = null;
            if (arrayList != null && i < arrayList.size()) {
                dataSource = this.mDataSources.set(i, (Object) null);
            }
            return dataSource;
        }

        @Nullable
        private synchronized DataSource<T> getDataSourceWithResult() {
            return getDataSource(this.mIndexOfDataSourceWithResult);
        }

        @Nullable
        public synchronized T getResult() {
            DataSource<T> dataSourceWithResult;
            if (IncreasingQualityDataSourceSupplier.this.mDataSourceLazy) {
                ensureDataSourceInitialized();
            }
            dataSourceWithResult = getDataSourceWithResult();
            return dataSourceWithResult != null ? dataSourceWithResult.getResult() : null;
        }

        public synchronized boolean hasResult() {
            DataSource<T> dataSourceWithResult;
            if (IncreasingQualityDataSourceSupplier.this.mDataSourceLazy) {
                ensureDataSourceInitialized();
            }
            dataSourceWithResult = getDataSourceWithResult();
            return dataSourceWithResult != null && dataSourceWithResult.hasResult();
        }

        /* JADX WARNING: Code restructure failed: missing block: B:12:0x001b, code lost:
            if (r0 == null) goto L_?;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:13:0x001d, code lost:
            r1 = 0;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:15:0x0022, code lost:
            if (r1 >= r0.size()) goto L_0x0030;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:16:0x0024, code lost:
            closeSafely(r0.get(r1));
            r1 = r1 + 1;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:25:?, code lost:
            return true;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:26:?, code lost:
            return true;
         */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public boolean close() {
            /*
                r3 = this;
                com.facebook.datasource.IncreasingQualityDataSourceSupplier r0 = com.facebook.datasource.IncreasingQualityDataSourceSupplier.this
                boolean r0 = r0.mDataSourceLazy
                if (r0 == 0) goto L_0x000b
                r3.ensureDataSourceInitialized()
            L_0x000b:
                monitor-enter(r3)
                boolean r0 = super.close()     // Catch:{ all -> 0x0032 }
                if (r0 != 0) goto L_0x0015
                r0 = 0
                monitor-exit(r3)     // Catch:{ all -> 0x0032 }
                return r0
            L_0x0015:
                java.util.ArrayList<com.facebook.datasource.DataSource<T>> r0 = r3.mDataSources     // Catch:{ all -> 0x0032 }
                r1 = 0
                r3.mDataSources = r1     // Catch:{ all -> 0x0032 }
                monitor-exit(r3)     // Catch:{ all -> 0x0032 }
                if (r0 == 0) goto L_0x0030
                r1 = 0
            L_0x001e:
                int r2 = r0.size()
                if (r1 >= r2) goto L_0x0030
                java.lang.Object r2 = r0.get(r1)
                com.facebook.datasource.DataSource r2 = (com.facebook.datasource.DataSource) r2
                r3.closeSafely(r2)
                int r1 = r1 + 1
                goto L_0x001e
            L_0x0030:
                r1 = 1
                return r1
            L_0x0032:
                r0 = move-exception
                monitor-exit(r3)     // Catch:{ all -> 0x0032 }
                throw r0
            */
            throw new UnsupportedOperationException("Method not decompiled: com.facebook.datasource.IncreasingQualityDataSourceSupplier.IncreasingQualityDataSource.close():boolean");
        }

        /* access modifiers changed from: private */
        public void onDataSourceNewResult(int index, DataSource<T> dataSource) {
            maybeSetIndexOfDataSourceWithResult(index, dataSource, dataSource.isFinished());
            if (dataSource == getDataSourceWithResult()) {
                setResult(null, index == 0 && dataSource.isFinished());
            }
            maybeSetFailure();
        }

        /* access modifiers changed from: private */
        public void onDataSourceFailed(int index, DataSource<T> dataSource) {
            closeSafely(tryGetAndClearDataSource(index, dataSource));
            if (index == 0) {
                this.mDelayedError = dataSource.getFailureCause();
            }
            maybeSetFailure();
        }

        private void maybeSetFailure() {
            Throwable th;
            if (this.mFinishedDataSources.incrementAndGet() == this.mNumberOfDataSources && (th = this.mDelayedError) != null) {
                setFailure(th);
            }
        }

        /* JADX WARNING: Code restructure failed: missing block: B:14:0x0020, code lost:
            r2 = r1;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:15:0x0021, code lost:
            if (r2 <= r0) goto L_0x002d;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:16:0x0023, code lost:
            closeSafely(getAndClearDataSource(r2));
            r2 = r2 - 1;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:17:0x002d, code lost:
            return;
         */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        private void maybeSetIndexOfDataSourceWithResult(int r5, com.facebook.datasource.DataSource<T> r6, boolean r7) {
            /*
                r4 = this;
                monitor-enter(r4)
                int r0 = r4.mIndexOfDataSourceWithResult     // Catch:{ all -> 0x0030 }
                r1 = r0
                com.facebook.datasource.DataSource r2 = r4.getDataSource(r5)     // Catch:{ all -> 0x0030 }
                if (r6 != r2) goto L_0x002e
                int r2 = r4.mIndexOfDataSourceWithResult     // Catch:{ all -> 0x0030 }
                if (r5 != r2) goto L_0x0010
                goto L_0x002e
            L_0x0010:
                com.facebook.datasource.DataSource r2 = r4.getDataSourceWithResult()     // Catch:{ all -> 0x0030 }
                if (r2 == 0) goto L_0x001c
                if (r7 == 0) goto L_0x001f
                int r2 = r4.mIndexOfDataSourceWithResult     // Catch:{ all -> 0x0030 }
                if (r5 >= r2) goto L_0x001f
            L_0x001c:
                r0 = r5
                r4.mIndexOfDataSourceWithResult = r5     // Catch:{ all -> 0x0030 }
            L_0x001f:
                monitor-exit(r4)     // Catch:{ all -> 0x0030 }
                r2 = r1
            L_0x0021:
                if (r2 <= r0) goto L_0x002d
                com.facebook.datasource.DataSource r3 = r4.getAndClearDataSource(r2)
                r4.closeSafely(r3)
                int r2 = r2 + -1
                goto L_0x0021
            L_0x002d:
                return
            L_0x002e:
                monitor-exit(r4)     // Catch:{ all -> 0x0030 }
                return
            L_0x0030:
                r0 = move-exception
                monitor-exit(r4)     // Catch:{ all -> 0x0030 }
                throw r0
            */
            throw new UnsupportedOperationException("Method not decompiled: com.facebook.datasource.IncreasingQualityDataSourceSupplier.IncreasingQualityDataSource.maybeSetIndexOfDataSourceWithResult(int, com.facebook.datasource.DataSource, boolean):void");
        }

        @Nullable
        private synchronized DataSource<T> tryGetAndClearDataSource(int i, DataSource<T> dataSource) {
            if (dataSource == getDataSourceWithResult()) {
                return null;
            }
            if (dataSource != getDataSource(i)) {
                return dataSource;
            }
            return getAndClearDataSource(i);
        }

        private void closeSafely(DataSource<T> dataSource) {
            if (dataSource != null) {
                dataSource.close();
            }
        }

        private class InternalDataSubscriber implements DataSubscriber<T> {
            private int mIndex;

            public InternalDataSubscriber(int index) {
                this.mIndex = index;
            }

            public void onNewResult(DataSource<T> dataSource) {
                if (dataSource.hasResult()) {
                    IncreasingQualityDataSource.this.onDataSourceNewResult(this.mIndex, dataSource);
                } else if (dataSource.isFinished()) {
                    IncreasingQualityDataSource.this.onDataSourceFailed(this.mIndex, dataSource);
                }
            }

            public void onFailure(DataSource<T> dataSource) {
                IncreasingQualityDataSource.this.onDataSourceFailed(this.mIndex, dataSource);
            }

            public void onCancellation(DataSource<T> dataSource) {
            }

            public void onProgressUpdate(DataSource<T> dataSource) {
                if (this.mIndex == 0) {
                    IncreasingQualityDataSource.this.setProgress(dataSource.getProgress());
                }
            }
        }
    }
}
