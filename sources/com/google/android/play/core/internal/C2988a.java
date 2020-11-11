package com.google.android.play.core.internal;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.security.MessageDigest;

/* renamed from: com.google.android.play.core.internal.a */
final class C2988a implements C3015b {

    /* renamed from: a */
    private final ByteBuffer f1274a;

    public C2988a(ByteBuffer byteBuffer) {
        this.f1274a = byteBuffer.slice();
    }

    /* renamed from: a */
    public final long mo44085a() {
        return (long) this.f1274a.capacity();
    }

    /* renamed from: a */
    public final void mo44086a(MessageDigest[] messageDigestArr, long j, int i) throws IOException {
        ByteBuffer slice;
        synchronized (this.f1274a) {
            int i2 = (int) j;
            this.f1274a.position(i2);
            this.f1274a.limit(i2 + i);
            slice = this.f1274a.slice();
        }
        for (MessageDigest update : messageDigestArr) {
            slice.position(0);
            update.update(slice);
        }
    }
}
