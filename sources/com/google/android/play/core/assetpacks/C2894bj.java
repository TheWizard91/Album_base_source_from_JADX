package com.google.android.play.core.assetpacks;

import java.util.Map;

/* renamed from: com.google.android.play.core.assetpacks.bj */
final class C2894bj extends AssetPackStates {

    /* renamed from: a */
    private final long f995a;

    /* renamed from: b */
    private final Map<String, AssetPackState> f996b;

    C2894bj(long j, Map<String, AssetPackState> map) {
        this.f995a = j;
        this.f996b = map;
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof AssetPackStates) {
            AssetPackStates assetPackStates = (AssetPackStates) obj;
            return this.f995a == assetPackStates.totalBytes() && this.f996b.equals(assetPackStates.packStates());
        }
    }

    public final int hashCode() {
        long j = this.f995a;
        return ((((int) (j ^ (j >>> 32))) ^ 1000003) * 1000003) ^ this.f996b.hashCode();
    }

    public final Map<String, AssetPackState> packStates() {
        return this.f996b;
    }

    public final String toString() {
        long j = this.f995a;
        String valueOf = String.valueOf(this.f996b);
        StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 61);
        sb.append("AssetPackStates{totalBytes=");
        sb.append(j);
        sb.append(", packStates=");
        sb.append(valueOf);
        sb.append("}");
        return sb.toString();
    }

    public final long totalBytes() {
        return this.f995a;
    }
}
