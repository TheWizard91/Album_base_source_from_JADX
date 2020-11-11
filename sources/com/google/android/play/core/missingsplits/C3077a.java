package com.google.android.play.core.missingsplits;

import android.content.ComponentName;
import android.content.Context;
import android.content.pm.ComponentInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import com.google.android.play.core.internal.C2989aa;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/* renamed from: com.google.android.play.core.missingsplits.a */
final class C3077a {

    /* renamed from: a */
    private static final C2989aa f1346a = new C2989aa("MissingSplitsAppComponentsHelper");

    /* renamed from: b */
    private final Context f1347b;

    /* renamed from: c */
    private final PackageManager f1348c;

    C3077a(Context context, PackageManager packageManager) {
        this.f1347b = context;
        this.f1348c = packageManager;
    }

    /* renamed from: a */
    private final void m873a(List<ComponentInfo> list, int i) {
        for (ComponentInfo next : list) {
            this.f1348c.setComponentEnabledSetting(new ComponentName(next.packageName, next.name), i, 1);
        }
    }

    /* renamed from: d */
    private final List<ComponentInfo> m874d() {
        try {
            ArrayList arrayList = new ArrayList();
            PackageInfo packageInfo = this.f1348c.getPackageInfo(this.f1347b.getPackageName(), 526);
            if (packageInfo.providers != null) {
                Collections.addAll(arrayList, packageInfo.providers);
            }
            if (packageInfo.receivers != null) {
                Collections.addAll(arrayList, packageInfo.receivers);
            }
            if (packageInfo.services != null) {
                Collections.addAll(arrayList, packageInfo.services);
            }
            return arrayList;
        } catch (PackageManager.NameNotFoundException e) {
            f1346a.mo44091d("Failed to resolve own package : %s", e);
            return Collections.emptyList();
        }
    }

    /* access modifiers changed from: package-private */
    /* renamed from: a */
    public final boolean mo44208a() {
        for (ComponentInfo next : m874d()) {
            if (this.f1348c.getComponentEnabledSetting(new ComponentName(next.packageName, next.name)) != 2) {
                f1346a.mo44087a("Not all non-activity components are disabled", new Object[0]);
                return false;
            }
        }
        f1346a.mo44087a("All non-activity components are disabled", new Object[0]);
        return true;
    }

    /* access modifiers changed from: package-private */
    /* renamed from: b */
    public final void mo44209b() {
        f1346a.mo44090c("Disabling all non-activity components", new Object[0]);
        m873a(m874d(), 2);
    }

    /* access modifiers changed from: package-private */
    /* renamed from: c */
    public final void mo44210c() {
        f1346a.mo44090c("Resetting enabled state of all non-activity components", new Object[0]);
        m873a(m874d(), 0);
    }
}
