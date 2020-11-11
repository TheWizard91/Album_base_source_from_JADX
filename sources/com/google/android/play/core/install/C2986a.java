package com.google.android.play.core.install;

/* renamed from: com.google.android.play.core.install.a */
final class C2986a extends InstallState {

    /* renamed from: a */
    private final int f1267a;

    /* renamed from: b */
    private final long f1268b;

    /* renamed from: c */
    private final long f1269c;

    /* renamed from: d */
    private final int f1270d;

    /* renamed from: e */
    private final String f1271e;

    C2986a(int i, long j, long j2, int i2, String str) {
        this.f1267a = i;
        this.f1268b = j;
        this.f1269c = j2;
        this.f1270d = i2;
        if (str != null) {
            this.f1271e = str;
            return;
        }
        throw new NullPointerException("Null packageName");
    }

    public final long bytesDownloaded() {
        return this.f1268b;
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof InstallState) {
            InstallState installState = (InstallState) obj;
            return this.f1267a == installState.installStatus() && this.f1268b == installState.bytesDownloaded() && this.f1269c == installState.totalBytesToDownload() && this.f1270d == installState.installErrorCode() && this.f1271e.equals(installState.packageName());
        }
    }

    public final int hashCode() {
        int i = this.f1267a;
        long j = this.f1268b;
        long j2 = this.f1269c;
        return ((((((((i ^ 1000003) * 1000003) ^ ((int) (j ^ (j >>> 32)))) * 1000003) ^ ((int) ((j2 >>> 32) ^ j2))) * 1000003) ^ this.f1270d) * 1000003) ^ this.f1271e.hashCode();
    }

    public final int installErrorCode() {
        return this.f1270d;
    }

    public final int installStatus() {
        return this.f1267a;
    }

    public final String packageName() {
        return this.f1271e;
    }

    public final String toString() {
        int i = this.f1267a;
        long j = this.f1268b;
        long j2 = this.f1269c;
        int i2 = this.f1270d;
        String str = this.f1271e;
        StringBuilder sb = new StringBuilder(str.length() + 164);
        sb.append("InstallState{installStatus=");
        sb.append(i);
        sb.append(", bytesDownloaded=");
        sb.append(j);
        sb.append(", totalBytesToDownload=");
        sb.append(j2);
        sb.append(", installErrorCode=");
        sb.append(i2);
        sb.append(", packageName=");
        sb.append(str);
        sb.append("}");
        return sb.toString();
    }

    public final long totalBytesToDownload() {
        return this.f1269c;
    }
}
