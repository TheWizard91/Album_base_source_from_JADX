package com.google.android.play.core.assetpacks;

import java.util.Arrays;

/* renamed from: com.google.android.play.core.assetpacks.bl */
final class C2896bl extends C2962dx {

    /* renamed from: a */
    private final String f1002a;

    /* renamed from: b */
    private final long f1003b;

    /* renamed from: c */
    private final int f1004c;

    /* renamed from: d */
    private final boolean f1005d;

    /* renamed from: e */
    private final byte[] f1006e;

    C2896bl(String str, long j, int i, boolean z, byte[] bArr) {
        this.f1002a = str;
        this.f1003b = j;
        this.f1004c = i;
        this.f1005d = z;
        this.f1006e = bArr;
    }

    /* access modifiers changed from: package-private */
    /* renamed from: a */
    public final String mo43992a() {
        return this.f1002a;
    }

    /* access modifiers changed from: package-private */
    /* renamed from: b */
    public final long mo43993b() {
        return this.f1003b;
    }

    /* access modifiers changed from: package-private */
    /* renamed from: c */
    public final int mo43994c() {
        return this.f1004c;
    }

    /* access modifiers changed from: package-private */
    /* renamed from: d */
    public final boolean mo43995d() {
        return this.f1005d;
    }

    /* access modifiers changed from: package-private */
    /* renamed from: e */
    public final byte[] mo43996e() {
        return this.f1006e;
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof C2962dx) {
            C2962dx dxVar = (C2962dx) obj;
            String str = this.f1002a;
            if (str == null ? dxVar.mo43992a() == null : str.equals(dxVar.mo43992a())) {
                if (this.f1003b == dxVar.mo43993b() && this.f1004c == dxVar.mo43994c() && this.f1005d == dxVar.mo43995d()) {
                    if (Arrays.equals(this.f1006e, dxVar instanceof C2896bl ? ((C2896bl) dxVar).f1006e : dxVar.mo43996e())) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public final int hashCode() {
        String str = this.f1002a;
        int hashCode = str != null ? str.hashCode() : 0;
        long j = this.f1003b;
        return ((((((((hashCode ^ 1000003) * 1000003) ^ ((int) (j ^ (j >>> 32)))) * 1000003) ^ this.f1004c) * 1000003) ^ (!this.f1005d ? 1237 : 1231)) * 1000003) ^ Arrays.hashCode(this.f1006e);
    }

    public final String toString() {
        String str = this.f1002a;
        long j = this.f1003b;
        int i = this.f1004c;
        boolean z = this.f1005d;
        String arrays = Arrays.toString(this.f1006e);
        StringBuilder sb = new StringBuilder(String.valueOf(str).length() + 104 + String.valueOf(arrays).length());
        sb.append("ZipEntry{name=");
        sb.append(str);
        sb.append(", size=");
        sb.append(j);
        sb.append(", compressionMethod=");
        sb.append(i);
        sb.append(", isPartial=");
        sb.append(z);
        sb.append(", headerBytes=");
        sb.append(arrays);
        sb.append("}");
        return sb.toString();
    }
}
