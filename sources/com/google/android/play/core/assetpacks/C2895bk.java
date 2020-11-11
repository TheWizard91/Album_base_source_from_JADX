package com.google.android.play.core.assetpacks;

/* renamed from: com.google.android.play.core.assetpacks.bk */
final class C2895bk extends C2955dq {

    /* renamed from: a */
    private final int f997a;

    /* renamed from: b */
    private final String f998b;

    /* renamed from: c */
    private final long f999c;

    /* renamed from: d */
    private final long f1000d;

    /* renamed from: e */
    private final int f1001e;

    C2895bk(int i, String str, long j, long j2, int i2) {
        this.f997a = i;
        this.f998b = str;
        this.f999c = j;
        this.f1000d = j2;
        this.f1001e = i2;
    }

    /* access modifiers changed from: package-private */
    /* renamed from: a */
    public final int mo43984a() {
        return this.f997a;
    }

    /* access modifiers changed from: package-private */
    /* renamed from: b */
    public final String mo43985b() {
        return this.f998b;
    }

    /* access modifiers changed from: package-private */
    /* renamed from: c */
    public final long mo43986c() {
        return this.f999c;
    }

    /* access modifiers changed from: package-private */
    /* renamed from: d */
    public final long mo43987d() {
        return this.f1000d;
    }

    /* access modifiers changed from: package-private */
    /* renamed from: e */
    public final int mo43988e() {
        return this.f1001e;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:6:0x0012, code lost:
        r1 = r7.f998b;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final boolean equals(java.lang.Object r8) {
        /*
            r7 = this;
            r0 = 1
            if (r8 == r7) goto L_0x0046
            boolean r1 = r8 instanceof com.google.android.play.core.assetpacks.C2955dq
            r2 = 0
            if (r1 == 0) goto L_0x0045
            com.google.android.play.core.assetpacks.dq r8 = (com.google.android.play.core.assetpacks.C2955dq) r8
            int r1 = r7.f997a
            int r3 = r8.mo43984a()
            if (r1 != r3) goto L_0x0045
            java.lang.String r1 = r7.f998b
            if (r1 == 0) goto L_0x0021
            java.lang.String r3 = r8.mo43985b()
            boolean r1 = r1.equals(r3)
            if (r1 == 0) goto L_0x0045
            goto L_0x0028
        L_0x0021:
            java.lang.String r1 = r8.mo43985b()
            if (r1 == 0) goto L_0x0028
            goto L_0x0045
        L_0x0028:
            long r3 = r7.f999c
            long r5 = r8.mo43986c()
            int r1 = (r3 > r5 ? 1 : (r3 == r5 ? 0 : -1))
            if (r1 != 0) goto L_0x0045
            long r3 = r7.f1000d
            long r5 = r8.mo43987d()
            int r1 = (r3 > r5 ? 1 : (r3 == r5 ? 0 : -1))
            if (r1 != 0) goto L_0x0045
            int r1 = r7.f1001e
            int r8 = r8.mo43988e()
            if (r1 != r8) goto L_0x0045
            return r0
        L_0x0045:
            return r2
        L_0x0046:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.play.core.assetpacks.C2895bk.equals(java.lang.Object):boolean");
    }

    public final int hashCode() {
        int i = (this.f997a ^ 1000003) * 1000003;
        String str = this.f998b;
        int hashCode = str != null ? str.hashCode() : 0;
        long j = this.f999c;
        long j2 = this.f1000d;
        return ((((((i ^ hashCode) * 1000003) ^ ((int) (j ^ (j >>> 32)))) * 1000003) ^ ((int) ((j2 >>> 32) ^ j2))) * 1000003) ^ this.f1001e;
    }

    public final String toString() {
        int i = this.f997a;
        String str = this.f998b;
        long j = this.f999c;
        long j2 = this.f1000d;
        int i2 = this.f1001e;
        StringBuilder sb = new StringBuilder(String.valueOf(str).length() + 157);
        sb.append("SliceCheckpoint{fileExtractionStatus=");
        sb.append(i);
        sb.append(", filePath=");
        sb.append(str);
        sb.append(", fileOffset=");
        sb.append(j);
        sb.append(", remainingBytes=");
        sb.append(j2);
        sb.append(", previousChunk=");
        sb.append(i2);
        sb.append("}");
        return sb.toString();
    }
}
