package com.google.android.play.core.assetpacks;

import android.os.Bundle;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class AssetPackStates {
    /* renamed from: a */
    public static AssetPackStates m305a(long j, Map<String, AssetPackState> map) {
        return new C2894bj(j, map);
    }

    /* renamed from: a */
    public static AssetPackStates m306a(Bundle bundle, C2913cb cbVar) {
        return m308a(bundle, cbVar, (List<String>) new ArrayList());
    }

    /* renamed from: a */
    public static AssetPackStates m307a(Bundle bundle, C2913cb cbVar, C2883az azVar) {
        return m309a(bundle, cbVar, new ArrayList(), azVar);
    }

    /* renamed from: a */
    public static AssetPackStates m308a(Bundle bundle, C2913cb cbVar, List<String> list) {
        return m309a(bundle, cbVar, list, C2885ba.f969a);
    }

    /* renamed from: a */
    private static AssetPackStates m309a(Bundle bundle, C2913cb cbVar, List<String> list, C2883az azVar) {
        Bundle bundle2 = bundle;
        ArrayList<String> stringArrayList = bundle.getStringArrayList("pack_names");
        HashMap hashMap = new HashMap();
        int size = stringArrayList.size();
        for (int i = 0; i < size; i++) {
            String str = stringArrayList.get(i);
            hashMap.put(str, AssetPackState.m303a(bundle, str, cbVar, azVar));
        }
        int size2 = list.size();
        for (int i2 = 0; i2 < size2; i2++) {
            String str2 = list.get(i2);
            hashMap.put(str2, AssetPackState.m304a(str2, 4, 0, 0, 0, 0.0d));
        }
        return m305a(bundle.getLong("total_bytes_to_download"), (Map<String, AssetPackState>) hashMap);
    }

    public abstract Map<String, AssetPackState> packStates();

    public abstract long totalBytes();
}
