package com.google.android.play.core.splitinstall;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/* renamed from: com.google.android.play.core.splitinstall.e */
public final class C3129e {

    /* renamed from: a */
    private final Map<String, Map<String, String>> f1454a = new HashMap();

    /* renamed from: a */
    public final C3130f mo44293a() {
        HashMap hashMap = new HashMap();
        for (Map.Entry next : this.f1454a.entrySet()) {
            hashMap.put((String) next.getKey(), Collections.unmodifiableMap(new HashMap((Map) next.getValue())));
        }
        return new C3130f(Collections.unmodifiableMap(hashMap));
    }

    /* renamed from: a */
    public final void mo44294a(String str, String str2, String str3) {
        if (!this.f1454a.containsKey(str2)) {
            this.f1454a.put(str2, new HashMap());
        }
        this.f1454a.get(str2).put(str, str3);
    }
}
