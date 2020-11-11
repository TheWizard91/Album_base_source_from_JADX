package com.google.android.play.core.internal;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;

/* renamed from: com.google.android.play.core.internal.by */
final class C3040by extends WeakReference<Throwable> {

    /* renamed from: a */
    private final int f1316a;

    public C3040by(Throwable th, ReferenceQueue<Throwable> referenceQueue) {
        super(th, referenceQueue);
        this.f1316a = System.identityHashCode(th);
    }

    public final boolean equals(Object obj) {
        if (obj == null || obj.getClass() != getClass()) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        C3040by byVar = (C3040by) obj;
        return this.f1316a == byVar.f1316a && get() == byVar.get();
    }

    public final int hashCode() {
        return this.f1316a;
    }
}
