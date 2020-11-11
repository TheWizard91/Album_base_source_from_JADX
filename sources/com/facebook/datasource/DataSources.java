package com.facebook.datasource;

import com.facebook.common.internal.Supplier;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;
import javax.annotation.Nullable;

public class DataSources {
    private DataSources() {
    }

    public static <T> DataSource<T> immediateFailedDataSource(Throwable failure) {
        SimpleDataSource<T> simpleDataSource = SimpleDataSource.create();
        simpleDataSource.setFailure(failure);
        return simpleDataSource;
    }

    public static <T> DataSource<T> immediateDataSource(T result) {
        SimpleDataSource<T> simpleDataSource = SimpleDataSource.create();
        simpleDataSource.setResult(result);
        return simpleDataSource;
    }

    public static <T> Supplier<DataSource<T>> getFailedDataSourceSupplier(final Throwable failure) {
        return new Supplier<DataSource<T>>() {
            public DataSource<T> get() {
                return DataSources.immediateFailedDataSource(failure);
            }
        };
    }

    @Nullable
    public static <T> T waitForFinalResult(DataSource<T> dataSource) throws Throwable {
        final CountDownLatch latch = new CountDownLatch(1);
        final ValueHolder<T> resultHolder = new ValueHolder<>();
        final ValueHolder<Throwable> pendingException = new ValueHolder<>();
        dataSource.subscribe(new DataSubscriber<T>() {
            public void onNewResult(DataSource<T> dataSource) {
                if (dataSource.isFinished()) {
                    try {
                        resultHolder.value = dataSource.getResult();
                    } finally {
                        latch.countDown();
                    }
                }
            }

            public void onFailure(DataSource<T> dataSource) {
                try {
                    pendingException.value = dataSource.getFailureCause();
                } finally {
                    latch.countDown();
                }
            }

            public void onCancellation(DataSource<T> dataSource) {
                latch.countDown();
            }

            public void onProgressUpdate(DataSource<T> dataSource) {
            }
        }, new Executor() {
            public void execute(Runnable command) {
                command.run();
            }
        });
        latch.await();
        if (pendingException.value == null) {
            return resultHolder.value;
        }
        throw ((Throwable) pendingException.value);
    }

    private static class ValueHolder<T> {
        @Nullable
        public T value;

        private ValueHolder() {
            this.value = null;
        }
    }
}
