package com.google.android.play.core.assetpacks;

/* renamed from: com.google.android.play.core.assetpacks.bh */
final class C2892bh extends AssetPackLocation {

    /* renamed from: a */
    private final int f986a;

    /* renamed from: b */
    private final String f987b;

    /* renamed from: c */
    private final String f988c;

    C2892bh(int i, String str, String str2) {
        this.f986a = i;
        this.f987b = str;
        this.f988c = str2;
    }

    public final String assetsPath() {
        return this.f988c;
    }

    public final boolean equals(Object obj) {
        String str;
        if (obj == this) {
            return true;
        }
        if (obj instanceof AssetPackLocation) {
            AssetPackLocation assetPackLocation = (AssetPackLocation) obj;
            if (this.f986a == assetPackLocation.packStorageMethod() && ((str = this.f987b) == null ? assetPackLocation.path() == null : str.equals(assetPackLocation.path()))) {
                String str2 = this.f988c;
                String assetsPath = assetPackLocation.assetsPath();
                if (str2 == null ? assetsPath == null : str2.equals(assetsPath)) {
                    return true;
                }
            }
        }
        return false;
    }

    public final int hashCode() {
        int i = (this.f986a ^ 1000003) * 1000003;
        String str = this.f987b;
        int i2 = 0;
        int hashCode = (i ^ (str != null ? str.hashCode() : 0)) * 1000003;
        String str2 = this.f988c;
        if (str2 != null) {
            i2 = str2.hashCode();
        }
        return hashCode ^ i2;
    }

    public final int packStorageMethod() {
        return this.f986a;
    }

    public final String path() {
        return this.f987b;
    }

    public final String toString() {
        int i = this.f986a;
        String str = this.f987b;
        String str2 = this.f988c;
        StringBuilder sb = new StringBuilder(String.valueOf(str).length() + 68 + String.valueOf(str2).length());
        sb.append("AssetPackLocation{packStorageMethod=");
        sb.append(i);
        sb.append(", path=");
        sb.append(str);
        sb.append(", assetsPath=");
        sb.append(str2);
        sb.append("}");
        return sb.toString();
    }
}
