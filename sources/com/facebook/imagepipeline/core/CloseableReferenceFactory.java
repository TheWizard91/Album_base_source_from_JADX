package com.facebook.imagepipeline.core;

import com.facebook.common.logging.FLog;
import com.facebook.common.references.CloseableReference;
import com.facebook.common.references.ResourceReleaser;
import com.facebook.common.references.SharedReference;
import com.facebook.imagepipeline.debug.CloseableReferenceLeakTracker;
import java.io.Closeable;
import java.io.PrintWriter;
import java.io.StringWriter;
import javax.annotation.Nullable;

public class CloseableReferenceFactory {
    private final CloseableReference.LeakHandler mLeakHandler;

    public CloseableReferenceFactory(final CloseableReferenceLeakTracker closeableReferenceLeakTracker) {
        this.mLeakHandler = new CloseableReference.LeakHandler() {
            public void reportLeak(SharedReference<Object> reference, @Nullable Throwable stacktrace) {
                closeableReferenceLeakTracker.trackCloseableReferenceLeak(reference, stacktrace);
                FLog.m102w("Fresco", "Finalized without closing: %x %x (type = %s).\nStack:\n%s", Integer.valueOf(System.identityHashCode(this)), Integer.valueOf(System.identityHashCode(reference)), reference.get().getClass().getName(), CloseableReferenceFactory.getStackTraceString(stacktrace));
            }

            public boolean requiresStacktrace() {
                return closeableReferenceLeakTracker.isSet();
            }
        };
    }

    public <U extends Closeable> CloseableReference<U> create(U u) {
        return CloseableReference.m125of(u, this.mLeakHandler);
    }

    public <T> CloseableReference<T> create(T t, ResourceReleaser<T> resourceReleaser) {
        return CloseableReference.m127of(t, resourceReleaser, this.mLeakHandler);
    }

    /* access modifiers changed from: private */
    public static String getStackTraceString(@Nullable Throwable tr) {
        if (tr == null) {
            return "";
        }
        StringWriter sw = new StringWriter();
        tr.printStackTrace(new PrintWriter(sw));
        return sw.toString();
    }
}
