package com.google.android.play.core.assetpacks;

/* renamed from: com.google.android.play.core.assetpacks.bg */
final class C2891bg extends AssetLocation {

    /* renamed from: a */
    private final String f983a;

    /* renamed from: b */
    private final long f984b;

    /* renamed from: c */
    private final long f985c;

    C2891bg(String str, long j, long j2) {
        if (str != null) {
            this.f983a = str;
            this.f984b = j;
            this.f985c = j2;
            return;
        }
        throw new NullPointerException("Null path");
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof AssetLocation) {
            AssetLocation assetLocation = (AssetLocation) obj;
            return this.f983a.equals(assetLocation.path()) && this.f984b == assetLocation.offset() && this.f985c == assetLocation.size();
        }
    }

    public final int hashCode() {
        int hashCode = this.f983a.hashCode();
        long j = this.f984b;
        long j2 = this.f985c;
        return ((((hashCode ^ 1000003) * 1000003) ^ ((int) (j ^ (j >>> 32)))) * 1000003) ^ ((int) ((j2 >>> 32) ^ j2));
    }

    public final long offset() {
        return this.f984b;
    }

    public final String path() {
        return this.f983a;
    }

    public final long size() {
        return this.f985c;
    }

    public final String toString() {
        String str = this.f983a;
        long j = this.f984b;
        long j2 = this.f985c;
        StringBuilder sb = new StringBuilder(str.length() + 76);
        sb.append("AssetLocation{path=");
        sb.append(str);
        sb.append(", offset=");
        sb.append(j);
        sb.append(", size=");
        sb.append(j2);
        sb.append("}");
        return sb.toString();
    }
}
