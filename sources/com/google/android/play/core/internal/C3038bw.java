package com.google.android.play.core.internal;

import java.io.IOException;
import java.io.InputStream;

/* renamed from: com.google.android.play.core.internal.bw */
public final class C3038bw extends C3037bv {

    /* renamed from: a */
    private final C3037bv f1313a;

    /* renamed from: b */
    private final long f1314b;

    /* renamed from: c */
    private final long f1315c;

    public C3038bw(C3037bv bvVar, long j, long j2) {
        this.f1313a = bvVar;
        long a = m758a(j);
        this.f1314b = a;
        this.f1315c = m758a(a + j2);
    }

    /* renamed from: a */
    private final long m758a(long j) {
        if (j >= 0) {
            return j > this.f1313a.mo43969a() ? this.f1313a.mo43969a() : j;
        }
        return 0;
    }

    /* renamed from: a */
    public final long mo43969a() {
        return this.f1315c - this.f1314b;
    }

    /* access modifiers changed from: protected */
    /* renamed from: a */
    public final InputStream mo43970a(long j, long j2) throws IOException {
        long a = m758a(this.f1314b + j);
        return this.f1313a.mo43970a(a, m758a(j2 + a) - a);
    }

    public final void close() throws IOException {
    }
}
