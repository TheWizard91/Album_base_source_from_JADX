package com.google.android.play.core.assetpacks;

/* renamed from: com.google.android.play.core.assetpacks.bi */
final class C2893bi extends AssetPackState {

    /* renamed from: a */
    private final String f989a;

    /* renamed from: b */
    private final int f990b;

    /* renamed from: c */
    private final int f991c;

    /* renamed from: d */
    private final long f992d;

    /* renamed from: e */
    private final long f993e;

    /* renamed from: f */
    private final int f994f;

    C2893bi(String str, int i, int i2, long j, long j2, int i3) {
        if (str != null) {
            this.f989a = str;
            this.f990b = i;
            this.f991c = i2;
            this.f992d = j;
            this.f993e = j2;
            this.f994f = i3;
            return;
        }
        throw new NullPointerException("Null name");
    }

    public final long bytesDownloaded() {
        return this.f992d;
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof AssetPackState) {
            AssetPackState assetPackState = (AssetPackState) obj;
            return this.f989a.equals(assetPackState.name()) && this.f990b == assetPackState.status() && this.f991c == assetPackState.errorCode() && this.f992d == assetPackState.bytesDownloaded() && this.f993e == assetPackState.totalBytesToDownload() && this.f994f == assetPackState.transferProgressPercentage();
        }
    }

    public final int errorCode() {
        return this.f991c;
    }

    public final int hashCode() {
        int hashCode = this.f989a.hashCode();
        int i = this.f990b;
        int i2 = this.f991c;
        long j = this.f992d;
        long j2 = this.f993e;
        return ((((((((((hashCode ^ 1000003) * 1000003) ^ i) * 1000003) ^ i2) * 1000003) ^ ((int) ((j >>> 32) ^ j))) * 1000003) ^ ((int) ((j2 >>> 32) ^ j2))) * 1000003) ^ this.f994f;
    }

    public final String name() {
        return this.f989a;
    }

    public final int status() {
        return this.f990b;
    }

    public final String toString() {
        String str = this.f989a;
        int i = this.f990b;
        int i2 = this.f991c;
        long j = this.f992d;
        long j2 = this.f993e;
        int i3 = this.f994f;
        StringBuilder sb = new StringBuilder(str.length() + 185);
        sb.append("AssetPackState{name=");
        sb.append(str);
        sb.append(", status=");
        sb.append(i);
        sb.append(", errorCode=");
        sb.append(i2);
        sb.append(", bytesDownloaded=");
        sb.append(j);
        sb.append(", totalBytesToDownload=");
        sb.append(j2);
        sb.append(", transferProgressPercentage=");
        sb.append(i3);
        sb.append("}");
        return sb.toString();
    }

    public final long totalBytesToDownload() {
        return this.f993e;
    }

    public final int transferProgressPercentage() {
        return this.f994f;
    }
}
