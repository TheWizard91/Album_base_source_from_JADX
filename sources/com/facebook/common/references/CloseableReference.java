package com.facebook.common.references;

import android.graphics.Bitmap;
import com.facebook.common.internal.Closeables;
import com.facebook.common.internal.Preconditions;
import com.facebook.common.logging.FLog;
import java.io.Closeable;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.annotation.Nullable;

public abstract class CloseableReference<T> implements Cloneable, Closeable {
    private static final ResourceReleaser<Closeable> DEFAULT_CLOSEABLE_RELEASER = new ResourceReleaser<Closeable>() {
        public void release(Closeable value) {
            try {
                Closeables.close(value, true);
            } catch (IOException e) {
            }
        }
    };
    private static final LeakHandler DEFAULT_LEAK_HANDLER = new LeakHandler() {
        public void reportLeak(SharedReference<Object> reference, @Nullable Throwable stacktrace) {
            FLog.m98w((Class<?>) CloseableReference.TAG, "Finalized without closing: %x %x (type = %s)", Integer.valueOf(System.identityHashCode(this)), Integer.valueOf(System.identityHashCode(reference)), reference.get().getClass().getName());
        }

        public boolean requiresStacktrace() {
            return false;
        }
    };
    public static final int REF_TYPE_DEFAULT = 0;
    public static final int REF_TYPE_FINALIZER = 1;
    public static final int REF_TYPE_NOOP = 3;
    public static final int REF_TYPE_REF_COUNT = 2;
    /* access modifiers changed from: private */
    public static Class<CloseableReference> TAG = CloseableReference.class;
    private static int sBitmapCloseableRefType = 0;
    protected boolean mIsClosed = false;
    protected final LeakHandler mLeakHandler;
    protected final SharedReference<T> mSharedReference;
    @Nullable
    protected final Throwable mStacktrace;

    public @interface CloseableRefType {
    }

    public interface LeakHandler {
        void reportLeak(SharedReference<Object> sharedReference, @Nullable Throwable th);

        boolean requiresStacktrace();
    }

    public abstract CloseableReference<T> clone();

    public static boolean useGc() {
        return sBitmapCloseableRefType == 3;
    }

    public static void setDisableCloseableReferencesForBitmaps(int bitmapCloseableRefType) {
        sBitmapCloseableRefType = bitmapCloseableRefType;
    }

    protected CloseableReference(SharedReference<T> sharedReference, LeakHandler leakHandler, @Nullable Throwable stacktrace) {
        this.mSharedReference = (SharedReference) Preconditions.checkNotNull(sharedReference);
        sharedReference.addReference();
        this.mLeakHandler = leakHandler;
        this.mStacktrace = stacktrace;
    }

    protected CloseableReference(T t, ResourceReleaser<T> resourceReleaser, LeakHandler leakHandler, @Nullable Throwable stacktrace) {
        this.mSharedReference = new SharedReference<>(t, resourceReleaser);
        this.mLeakHandler = leakHandler;
        this.mStacktrace = stacktrace;
    }

    /* renamed from: of */
    public static <T extends Closeable> CloseableReference<T> m124of(T t) {
        return m126of(t, DEFAULT_CLOSEABLE_RELEASER);
    }

    /* renamed from: of */
    public static <T> CloseableReference<T> m126of(T t, ResourceReleaser<T> resourceReleaser) {
        return m127of(t, resourceReleaser, DEFAULT_LEAK_HANDLER);
    }

    /* renamed from: of */
    public static <T extends Closeable> CloseableReference<T> m125of(T t, LeakHandler leakHandler) {
        Throwable th = null;
        if (t == null) {
            return null;
        }
        ResourceReleaser<Closeable> resourceReleaser = DEFAULT_CLOSEABLE_RELEASER;
        if (leakHandler.requiresStacktrace()) {
            th = new Throwable();
        }
        return m128of(t, resourceReleaser, leakHandler, th);
    }

    /* renamed from: of */
    public static <T> CloseableReference<T> m127of(T t, ResourceReleaser<T> resourceReleaser, LeakHandler leakHandler) {
        Throwable th = null;
        if (t == null) {
            return null;
        }
        if (leakHandler.requiresStacktrace()) {
            th = new Throwable();
        }
        return m128of(t, resourceReleaser, leakHandler, th);
    }

    /* renamed from: of */
    public static <T> CloseableReference<T> m128of(T t, ResourceReleaser<T> resourceReleaser, LeakHandler leakHandler, @Nullable Throwable stacktrace) {
        if (t == null) {
            return null;
        }
        if ((t instanceof Bitmap) || (t instanceof HasBitmap)) {
            int i = sBitmapCloseableRefType;
            if (i == 1) {
                return new FinalizerCloseableReference(t, resourceReleaser, leakHandler, stacktrace);
            }
            if (i == 2) {
                return new RefCountCloseableReference(t, resourceReleaser, leakHandler, stacktrace);
            }
            if (i == 3) {
                return new NoOpCloseableReference(t, resourceReleaser, leakHandler, stacktrace);
            }
        }
        return new DefaultCloseableReference(t, resourceReleaser, leakHandler, stacktrace);
    }

    public synchronized T get() {
        Preconditions.checkState(!this.mIsClosed);
        return this.mSharedReference.get();
    }

    @Nullable
    public synchronized CloseableReference<T> cloneOrNull() {
        if (!isValid()) {
            return null;
        }
        return clone();
    }

    public synchronized boolean isValid() {
        return !this.mIsClosed;
    }

    public synchronized SharedReference<T> getUnderlyingReferenceTestOnly() {
        return this.mSharedReference;
    }

    public int getValueHash() {
        if (isValid()) {
            return System.identityHashCode(this.mSharedReference.get());
        }
        return 0;
    }

    public void close() {
        synchronized (this) {
            if (!this.mIsClosed) {
                this.mIsClosed = true;
                this.mSharedReference.deleteReference();
            }
        }
    }

    public static boolean isValid(@Nullable CloseableReference<?> ref) {
        return ref != null && ref.isValid();
    }

    @Nullable
    public static <T> CloseableReference<T> cloneOrNull(@Nullable CloseableReference<T> ref) {
        if (ref != null) {
            return ref.cloneOrNull();
        }
        return null;
    }

    public static <T> List<CloseableReference<T>> cloneOrNull(Collection<CloseableReference<T>> refs) {
        if (refs == null) {
            return null;
        }
        List<CloseableReference<T>> ret = new ArrayList<>(refs.size());
        for (CloseableReference<T> ref : refs) {
            ret.add(cloneOrNull(ref));
        }
        return ret;
    }

    public static void closeSafely(@Nullable CloseableReference<?> ref) {
        if (ref != null) {
            ref.close();
        }
    }

    public static void closeSafely(@Nullable Iterable<? extends CloseableReference<?>> references) {
        if (references != null) {
            for (CloseableReference<?> ref : references) {
                closeSafely(ref);
            }
        }
    }

    /* access modifiers changed from: protected */
    public void finalize() throws Throwable {
        try {
            synchronized (this) {
                if (this.mIsClosed) {
                    super.finalize();
                    return;
                }
                this.mLeakHandler.reportLeak(this.mSharedReference, this.mStacktrace);
                close();
                super.finalize();
            }
        } catch (Throwable th) {
            super.finalize();
            throw th;
        }
    }
}
