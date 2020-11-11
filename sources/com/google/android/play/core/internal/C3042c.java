package com.google.android.play.core.internal;

import java.io.IOException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.security.MessageDigest;

/* renamed from: com.google.android.play.core.internal.c */
final class C3042c implements C3015b {

    /* renamed from: a */
    private final FileChannel f1319a;

    /* renamed from: b */
    private final long f1320b;

    /* renamed from: c */
    private final long f1321c;

    public C3042c(FileChannel fileChannel, long j, long j2) {
        this.f1319a = fileChannel;
        this.f1320b = j;
        this.f1321c = j2;
    }

    /* renamed from: a */
    public final long mo44085a() {
        return this.f1321c;
    }

    /* renamed from: a */
    public final void mo44086a(MessageDigest[] messageDigestArr, long j, int i) throws IOException {
        MappedByteBuffer map = this.f1319a.map(FileChannel.MapMode.READ_ONLY, this.f1320b + j, (long) i);
        map.load();
        for (MessageDigest update : messageDigestArr) {
            map.position(0);
            update.update(map);
        }
    }
}
