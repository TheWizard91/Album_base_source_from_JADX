package com.google.android.play.core.assetpacks;

import java.io.IOException;
import java.io.InputStream;

/* renamed from: com.google.android.play.core.assetpacks.be */
final class C2889be extends InputStream {

    /* renamed from: a */
    private final InputStream f978a;

    /* renamed from: b */
    private long f979b;

    C2889be(InputStream inputStream, long j) {
        this.f978a = inputStream;
        this.f979b = j;
    }

    public final int read() throws IOException {
        long j = this.f979b;
        if (j <= 0) {
            return -1;
        }
        this.f979b = j - 1;
        return this.f978a.read();
    }

    public final int read(byte[] bArr, int i, int i2) throws IOException {
        long j = this.f979b;
        if (j <= 0) {
            return -1;
        }
        int read = this.f978a.read(bArr, i, (int) Math.min((long) i2, j));
        if (read != -1) {
            this.f979b -= (long) read;
        }
        return read;
    }
}
