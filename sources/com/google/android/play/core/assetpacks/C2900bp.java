package com.google.android.play.core.assetpacks;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

/* renamed from: com.google.android.play.core.assetpacks.bp */
final class C2900bp extends FilterInputStream {

    /* renamed from: a */
    private byte[] f1011a = new byte[4096];

    /* renamed from: b */
    private long f1012b;

    /* renamed from: c */
    private boolean f1013c = false;

    /* renamed from: d */
    private boolean f1014d = false;

    C2900bp(InputStream inputStream) {
        super(inputStream);
    }

    /* renamed from: a */
    private final int m437a(byte[] bArr, int i, int i2) throws IOException {
        return Math.max(0, super.read(bArr, i, i2));
    }

    /* renamed from: a */
    private final C2962dx m438a(int i, String str, long j, int i2, boolean z) {
        return C2962dx.m567a(str, j, i2, z, Arrays.copyOf(this.f1011a, i));
    }

    /* renamed from: a */
    private final void m439a(long j) {
        int length = this.f1011a.length;
        if (j > ((long) length)) {
            do {
                length += length;
            } while (((long) length) < j);
            this.f1011a = Arrays.copyOf(this.f1011a, length);
        }
    }

    /* renamed from: a */
    private final boolean m440a(int i, int i2) throws IOException {
        return m437a(this.f1011a, i, i2) == i2;
    }

    /* access modifiers changed from: package-private */
    /* renamed from: a */
    public final C2962dx mo44001a() throws IOException {
        byte[] bArr;
        if (this.f1012b > 0) {
            do {
                bArr = this.f1011a;
            } while (read(bArr, 0, bArr.length) != -1);
        }
        if (this.f1013c || this.f1014d) {
            return C2962dx.m567a((String) null, -1, -1, false, (byte[]) null);
        }
        int a = m437a(this.f1011a, 0, 30);
        if (a != 30 && !m440a(a, 30 - a)) {
            this.f1013c = true;
            return m438a(a, (String) null, -1, -1, true);
        } else if (C2942dd.m530b(this.f1011a, 0) != 67324752) {
            this.f1014d = true;
            return m438a(a, (String) null, -1, -1, false);
        } else {
            long b = C2942dd.m530b(this.f1011a, 18);
            if (b != 4294967295L) {
                int c = C2942dd.m532c(this.f1011a, 8);
                int c2 = C2942dd.m532c(this.f1011a, 26);
                int i = c2 + 30;
                m439a((long) i);
                int a2 = m437a(this.f1011a, 30, c2);
                int i2 = a2 + 30;
                if (a2 == c2 || m440a(i2, c2 - a2)) {
                    String str = new String(this.f1011a, 30, c2);
                    int c3 = C2942dd.m532c(this.f1011a, 28);
                    int i3 = i + c3;
                    m439a((long) i3);
                    int a3 = m437a(this.f1011a, i, c3);
                    int i4 = i + a3;
                    if (a3 == c3 || m440a(i4, c3 - a3)) {
                        this.f1012b = C2942dd.m530b(this.f1011a, 18);
                        return m438a(i3, str, b, c, false);
                    }
                    this.f1013c = true;
                    return m438a(i4, str, b, c, true);
                }
                this.f1013c = true;
                return m438a(i2, (String) null, b, c, true);
            }
            throw new C2909by("Files bigger than 4GiB are not supported.");
        }
    }

    /* access modifiers changed from: package-private */
    /* renamed from: b */
    public final boolean mo44002b() {
        return this.f1013c;
    }

    /* access modifiers changed from: package-private */
    /* renamed from: c */
    public final boolean mo44003c() {
        return this.f1014d;
    }

    /* access modifiers changed from: package-private */
    /* renamed from: d */
    public final long mo44004d() {
        return this.f1012b;
    }

    public final int read(byte[] bArr) throws IOException {
        return read(bArr, 0, bArr.length);
    }

    public final int read(byte[] bArr, int i, int i2) throws IOException {
        long j = this.f1012b;
        if (j <= 0 || this.f1013c) {
            return -1;
        }
        int a = m437a(bArr, i, (int) Math.min(j, (long) i2));
        this.f1012b -= (long) a;
        if (a != 0) {
            return a;
        }
        this.f1013c = true;
        return 0;
    }
}
