package com.google.android.play.core.splitinstall;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import com.google.android.play.core.internal.C2989aa;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/* renamed from: com.google.android.play.core.splitinstall.z */
public final class C3160z {

    /* renamed from: a */
    private static final C2989aa f1520a = new C2989aa("SplitInstallInfoProvider");

    /* renamed from: b */
    private final Context f1521b;

    /* renamed from: c */
    private final String f1522c;

    public C3160z(Context context, String str) {
        this.f1521b = context;
        this.f1522c = str;
    }

    /* renamed from: a */
    public static boolean m1059a(String str) {
        return str.startsWith("config.");
    }

    /* renamed from: b */
    public static boolean m1060b(String str) {
        return m1059a(str) || str.contains(".config.");
    }

    /* renamed from: d */
    private final Set<String> m1061d() {
        HashSet hashSet = new HashSet();
        Bundle e = m1062e();
        if (e != null) {
            String string = e.getString("com.android.dynamic.apk.fused.modules");
            if (string == null || string.isEmpty()) {
                f1520a.mo44087a("App has no fused modules.", new Object[0]);
            } else {
                Collections.addAll(hashSet, string.split(",", -1));
                hashSet.remove("");
            }
        }
        if (Build.VERSION.SDK_INT >= 21) {
            String[] strArr = null;
            try {
                PackageInfo packageInfo = this.f1521b.getPackageManager().getPackageInfo(this.f1522c, 0);
                if (packageInfo != null) {
                    strArr = packageInfo.splitNames;
                }
            } catch (PackageManager.NameNotFoundException e2) {
                f1520a.mo44091d("App is not found in PackageManager", new Object[0]);
            }
            if (strArr != null) {
                f1520a.mo44087a("Adding splits from package manager: %s", Arrays.toString(strArr));
                Collections.addAll(hashSet, strArr);
            } else {
                f1520a.mo44087a("No splits are found or app cannot be found in package manager.", new Object[0]);
            }
            C3158x a = C3159y.m1057a();
            if (a != null) {
                hashSet.addAll(a.mo44237a());
            }
        }
        return hashSet;
    }

    /* renamed from: e */
    private final Bundle m1062e() {
        try {
            ApplicationInfo applicationInfo = this.f1521b.getPackageManager().getApplicationInfo(this.f1522c, 128);
            if (applicationInfo != null && applicationInfo.metaData != null) {
                return applicationInfo.metaData;
            }
            f1520a.mo44087a("App has no applicationInfo or metaData", new Object[0]);
            return null;
        } catch (PackageManager.NameNotFoundException e) {
            f1520a.mo44091d("App is not found in PackageManager", new Object[0]);
            return null;
        }
    }

    /* renamed from: a */
    public final Set<String> mo44307a() {
        HashSet hashSet = new HashSet();
        for (String next : m1061d()) {
            if (!m1060b(next)) {
                hashSet.add(next);
            }
        }
        return hashSet;
    }

    /* access modifiers changed from: package-private */
    /* renamed from: b */
    public final Set<String> mo44308b() {
        C3130f c = mo44309c();
        if (c == null) {
            return null;
        }
        HashSet hashSet = new HashSet();
        Set<String> d = m1061d();
        d.add("");
        Set<String> a = mo44307a();
        a.add("");
        for (Map.Entry next : c.mo44295a(a).entrySet()) {
            if (d.containsAll((Collection) next.getValue())) {
                hashSet.add((String) next.getKey());
            }
        }
        return hashSet;
    }

    /* renamed from: c */
    public final C3130f mo44309c() {
        Bundle e = m1062e();
        if (e == null) {
            f1520a.mo44091d("No metadata found in Context.", new Object[0]);
            return null;
        }
        int i = e.getInt("com.android.vending.splits");
        if (i == 0) {
            f1520a.mo44091d("No metadata found in AndroidManifest.", new Object[0]);
            return null;
        }
        try {
            C3130f a = new C3126bb(this.f1521b.getResources().getXml(i)).mo44291a();
            if (a == null) {
                f1520a.mo44091d("Can't parse languages metadata.", new Object[0]);
            }
            return a;
        } catch (Resources.NotFoundException e2) {
            f1520a.mo44091d("Resource with languages metadata doesn't exist.", new Object[0]);
            return null;
        }
    }
}
