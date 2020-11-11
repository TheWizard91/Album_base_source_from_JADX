package com.google.android.play.core.splitinstall;

import android.content.Context;
import android.content.SharedPreferences;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/* renamed from: com.google.android.play.core.splitinstall.ba */
final class C3125ba {

    /* renamed from: a */
    private final Context f1451a;

    C3125ba(Context context) {
        this.f1451a = context;
    }

    /* renamed from: b */
    private final SharedPreferences m1001b() {
        return this.f1451a.getSharedPreferences("playcore_split_install_internal", 0);
    }

    /* access modifiers changed from: package-private */
    /* renamed from: a */
    public final synchronized Set<String> mo44289a() {
        Set<String> stringSet;
        try {
            stringSet = m1001b().getStringSet("deferred_uninstall_module_list", new HashSet());
            if (stringSet == null) {
                stringSet = new HashSet<>();
            }
        } catch (Exception e) {
            return new HashSet();
        }
        return stringSet;
    }

    /* access modifiers changed from: package-private */
    /* renamed from: a */
    public final synchronized void mo44290a(Collection<String> collection) {
        Set<String> a = mo44289a();
        boolean z = false;
        for (String add : collection) {
            if (a.add(add)) {
                z = true;
            }
        }
        if (z) {
            try {
                m1001b().edit().putStringSet("deferred_uninstall_module_list", a).apply();
            } catch (Exception e) {
            }
        }
    }
}
