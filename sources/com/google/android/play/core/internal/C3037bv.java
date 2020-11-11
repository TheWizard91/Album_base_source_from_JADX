package com.google.android.play.core.internal;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;

/* renamed from: com.google.android.play.core.internal.bv */
public abstract class C3037bv implements Closeable {
    /* renamed from: a */
    public abstract long mo43969a();

    /* access modifiers changed from: protected */
    /* renamed from: a */
    public abstract InputStream mo43970a(long j, long j2) throws IOException;

    /* renamed from: b */
    public synchronized InputStream mo44139b() throws IOException {
        return mo43970a(0, mo43969a());
    }
}
