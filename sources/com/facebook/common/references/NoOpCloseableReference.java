package com.facebook.common.references;

import com.facebook.common.references.CloseableReference;
import javax.annotation.Nullable;

public class NoOpCloseableReference<T> extends CloseableReference<T> {
    NoOpCloseableReference(T t, ResourceReleaser<T> resourceReleaser, CloseableReference.LeakHandler leakHandler, @Nullable Throwable stacktrace) {
        super(t, resourceReleaser, leakHandler, stacktrace);
    }

    public CloseableReference<T> clone() {
        return this;
    }

    public void close() {
    }
}
