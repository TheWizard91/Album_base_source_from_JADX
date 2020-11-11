package com.facebook.imagepipeline.producers;

import com.facebook.common.internal.Preconditions;
import java.util.concurrent.Executor;

public class ExperimentalThreadHandoffProducerQueueImpl implements ThreadHandoffProducerQueue {
    private final Executor mExecutor;

    public ExperimentalThreadHandoffProducerQueueImpl(Executor executor) {
        this.mExecutor = (Executor) Preconditions.checkNotNull(executor);
    }

    public void addToQueueOrExecute(Runnable runnable) {
        this.mExecutor.execute(runnable);
    }

    public void startQueueing() {
        throw new UnsupportedOperationException();
    }

    public void stopQueuing() {
        throw new UnsupportedOperationException();
    }

    public void remove(Runnable runnable) {
    }

    public boolean isQueueing() {
        return false;
    }
}
