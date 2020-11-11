package com.facebook.imagepipeline.debug;

import com.facebook.common.references.SharedReference;
import com.facebook.imagepipeline.debug.CloseableReferenceLeakTracker;
import javax.annotation.Nullable;

public class NoOpCloseableReferenceLeakTracker implements CloseableReferenceLeakTracker {
    public void trackCloseableReferenceLeak(SharedReference<Object> sharedReference, @Nullable Throwable stacktrace) {
    }

    public void setListener(@Nullable CloseableReferenceLeakTracker.Listener listener) {
    }

    public boolean isSet() {
        return false;
    }
}
