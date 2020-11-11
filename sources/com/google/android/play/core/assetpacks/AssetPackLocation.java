package com.google.android.play.core.assetpacks;

import com.google.android.play.core.splitcompat.C3082d;

public abstract class AssetPackLocation {

    /* renamed from: a */
    private static final AssetPackLocation f881a = new C2892bh(1, (String) null, (String) null);

    /* renamed from: a */
    static AssetPackLocation m301a() {
        return f881a;
    }

    /* renamed from: a */
    static AssetPackLocation m302a(String str, String str2) {
        C3082d.m896a(str, (Object) "STORAGE_FILES location path must be non-null");
        C3082d.m896a(str, (Object) "STORAGE_FILES assetsPath must be non-null");
        return new C2892bh(0, str, str2);
    }

    public abstract String assetsPath();

    public abstract int packStorageMethod();

    public abstract String path();
}
