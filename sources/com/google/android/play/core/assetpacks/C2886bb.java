package com.google.android.play.core.assetpacks;

import android.content.Context;
import android.content.pm.PackageInfo;
import com.google.android.play.core.internal.C2989aa;
import com.google.android.play.core.internal.C3046cd;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

/* renamed from: com.google.android.play.core.assetpacks.bb */
final class C2886bb {

    /* renamed from: a */
    private static final C2989aa f970a = new C2989aa("AssetPackStorage");

    /* renamed from: b */
    private static final long f971b = TimeUnit.DAYS.toMillis(14);

    /* renamed from: c */
    private static final long f972c = TimeUnit.DAYS.toMillis(28);

    /* renamed from: d */
    private final Context f973d;

    /* renamed from: e */
    private final C2950dl f974e;

    C2886bb(Context context, C2950dl dlVar) {
        this.f973d = context;
        this.f974e = dlVar;
    }

    /* renamed from: a */
    private final File m380a(String str, int i) {
        return new File(m386g(str), String.valueOf(i));
    }

    /* renamed from: a */
    private static List<String> m381a(PackageInfo packageInfo, String str) {
        ArrayList arrayList = new ArrayList();
        int i = (-Arrays.binarySearch(packageInfo.splitNames, str)) - 1;
        while (i < packageInfo.splitNames.length && packageInfo.splitNames[i].startsWith(str)) {
            arrayList.add(packageInfo.applicationInfo.splitSourceDirs[i]);
            i++;
        }
        return arrayList;
    }

    /* renamed from: a */
    private static void m382a(File file) {
        if (file.listFiles() != null && file.listFiles().length > 1) {
            long b = m383b(file);
            for (File file2 : file.listFiles()) {
                if (!file2.getName().equals(String.valueOf(b)) && !file2.getName().equals("stale.tmp")) {
                    m384c(file2);
                }
            }
        }
    }

    /* renamed from: b */
    private static long m383b(File file) {
        if (file.exists()) {
            ArrayList arrayList = new ArrayList();
            try {
                for (File file2 : file.listFiles()) {
                    if (!file2.getName().equals("stale.tmp")) {
                        arrayList.add(Long.valueOf(file2.getName()));
                    }
                }
            } catch (NumberFormatException e) {
                f970a.mo44088a((Throwable) e, "Corrupt asset pack directories.", new Object[0]);
            }
            if (!arrayList.isEmpty()) {
                Collections.sort(arrayList);
                return ((Long) arrayList.get(arrayList.size() - 1)).longValue();
            }
        }
        return -1;
    }

    /* renamed from: c */
    private static boolean m384c(File file) {
        File[] listFiles = file.listFiles();
        boolean z = true;
        if (listFiles != null) {
            for (File c : listFiles) {
                if (!m384c(c)) {
                    z = false;
                }
            }
        }
        if (file.delete()) {
            return z;
        }
        return false;
    }

    /* renamed from: d */
    private static long m385d(File file) {
        if (!file.isDirectory()) {
            return file.length();
        }
        File[] listFiles = file.listFiles();
        long j = 0;
        if (listFiles != null) {
            for (File d : listFiles) {
                j += m385d(d);
            }
        }
        return j;
    }

    /* renamed from: g */
    private final File m386g(String str) {
        return new File(m391i(), str);
    }

    /* renamed from: g */
    private final File m387g(String str, int i, long j) {
        return new File(mo43950c(str, i, j), "merge.tmp");
    }

    /* renamed from: g */
    private final List<File> m388g() {
        ArrayList arrayList = new ArrayList();
        try {
            if (!m391i().exists() || m391i().listFiles() == null) {
                return arrayList;
            }
            for (File file : m391i().listFiles()) {
                if (!file.getCanonicalPath().equals(m389h().getCanonicalPath())) {
                    arrayList.add(file);
                }
            }
            return arrayList;
        } catch (IOException e) {
            f970a.mo44089b("Could not process directory while scanning installed packs. %s", e);
        }
    }

    /* renamed from: h */
    private final File m389h() {
        return new File(m391i(), "_tmp");
    }

    /* renamed from: h */
    private final File m390h(String str, int i, long j) {
        return new File(new File(new File(m389h(), str), String.valueOf(i)), String.valueOf(j));
    }

    /* renamed from: i */
    private final File m391i() {
        return new File(this.f973d.getFilesDir(), "assetpacks");
    }

    /* access modifiers changed from: package-private */
    /* renamed from: a */
    public final long mo43938a() {
        return m385d(m391i());
    }

    /* access modifiers changed from: package-private */
    /* JADX WARNING: Removed duplicated region for block: B:21:0x0068  */
    /* JADX WARNING: Removed duplicated region for block: B:22:0x0072  */
    /* renamed from: a */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final com.google.android.play.core.assetpacks.AssetLocation mo43939a(java.lang.String r9, java.lang.String r10) {
        /*
            r8 = this;
            r0 = 0
            r1 = 0
            android.content.Context r2 = r8.f973d     // Catch:{ NameNotFoundException -> 0x0013 }
            android.content.pm.PackageManager r2 = r2.getPackageManager()     // Catch:{ NameNotFoundException -> 0x0013 }
            android.content.Context r3 = r8.f973d     // Catch:{ NameNotFoundException -> 0x0013 }
            java.lang.String r3 = r3.getPackageName()     // Catch:{ NameNotFoundException -> 0x0013 }
            android.content.pm.PackageInfo r2 = r2.getPackageInfo(r3, r0)     // Catch:{ NameNotFoundException -> 0x0013 }
            goto L_0x001e
        L_0x0013:
            r2 = move-exception
            com.google.android.play.core.internal.aa r2 = f970a
            java.lang.Object[] r3 = new java.lang.Object[r0]
            java.lang.String r4 = "Could not find PackageInfo."
            r2.mo44089b(r4, r3)
            r2 = r1
        L_0x001e:
            r3 = 1
            if (r2 == 0) goto L_0x0087
            java.util.ArrayList r4 = new java.util.ArrayList
            r4.<init>()
            int r5 = android.os.Build.VERSION.SDK_INT
            r6 = 21
            if (r5 >= r6) goto L_0x0034
            android.content.pm.ApplicationInfo r2 = r2.applicationInfo
            java.lang.String r2 = r2.sourceDir
            r4.add(r2)
            goto L_0x0088
        L_0x0034:
            java.lang.String[] r5 = r2.splitNames
            if (r5 == 0) goto L_0x005a
            android.content.pm.ApplicationInfo r5 = r2.applicationInfo
            java.lang.String[] r5 = r5.splitSourceDirs
            if (r5 != 0) goto L_0x003f
            goto L_0x005a
        L_0x003f:
            java.lang.String[] r5 = r2.splitNames
            int r5 = java.util.Arrays.binarySearch(r5, r9)
            if (r5 >= 0) goto L_0x0053
            com.google.android.play.core.internal.aa r5 = f970a
            java.lang.Object[] r6 = new java.lang.Object[r3]
            r6[r0] = r9
            java.lang.String r7 = "Asset Pack '%s' is not installed."
            r5.mo44087a(r7, r6)
            goto L_0x0065
        L_0x0053:
            android.content.pm.ApplicationInfo r6 = r2.applicationInfo
            java.lang.String[] r6 = r6.splitSourceDirs
            r5 = r6[r5]
            goto L_0x0066
        L_0x005a:
            com.google.android.play.core.internal.aa r5 = f970a
            java.lang.Object[] r6 = new java.lang.Object[r3]
            r6[r0] = r9
            java.lang.String r7 = "No splits present for package %s."
            r5.mo44087a(r7, r6)
        L_0x0065:
            r5 = r1
        L_0x0066:
            if (r5 != 0) goto L_0x0072
            android.content.pm.ApplicationInfo r5 = r2.applicationInfo
            java.lang.String r5 = r5.sourceDir
            r4.add(r5)
            java.lang.String r5 = "config."
            goto L_0x007f
        L_0x0072:
            r4.add(r5)
            java.lang.String r5 = java.lang.String.valueOf(r9)
            java.lang.String r6 = ".config."
            java.lang.String r5 = r5.concat(r6)
        L_0x007f:
            java.util.List r2 = m381a((android.content.pm.PackageInfo) r2, (java.lang.String) r5)
            r4.addAll(r2)
            goto L_0x0088
        L_0x0087:
            r4 = r1
        L_0x0088:
            if (r4 == 0) goto L_0x00cd
            java.io.File r2 = new java.io.File
            java.lang.String r5 = "assets"
            r2.<init>(r5, r10)
            java.lang.String r2 = r2.getPath()
            java.util.Iterator r5 = r4.iterator()
        L_0x0099:
            boolean r6 = r5.hasNext()
            r7 = 2
            if (r6 == 0) goto L_0x00bd
            java.lang.Object r6 = r5.next()
            java.lang.String r6 = (java.lang.String) r6
            com.google.android.play.core.assetpacks.AssetLocation r6 = com.google.android.play.core.assetpacks.C2942dd.m524a((java.lang.String) r6, (java.lang.String) r2)     // Catch:{ IOException -> 0x00ae }
            if (r6 == 0) goto L_0x0099
            r1 = r6
            goto L_0x00cd
        L_0x00ae:
            r9 = move-exception
            com.google.android.play.core.internal.aa r2 = f970a
            java.lang.Object[] r4 = new java.lang.Object[r7]
            r4[r0] = r6
            r4[r3] = r10
            java.lang.String r10 = "Failed to parse APK file '%s' looking for asset '%s'."
            r2.mo44088a((java.lang.Throwable) r9, (java.lang.String) r10, (java.lang.Object[]) r4)
            goto L_0x00cd
        L_0x00bd:
            com.google.android.play.core.internal.aa r2 = f970a
            r5 = 3
            java.lang.Object[] r5 = new java.lang.Object[r5]
            r5[r0] = r10
            r5[r3] = r9
            r5[r7] = r4
            java.lang.String r9 = "The asset %s is not present in Asset Pack %s. Searched in APKs: %s"
            r2.mo44087a(r9, r5)
        L_0x00cd:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.play.core.assetpacks.C2886bb.mo43939a(java.lang.String, java.lang.String):com.google.android.play.core.assetpacks.AssetLocation");
    }

    /* access modifiers changed from: package-private */
    /* renamed from: a */
    public final AssetLocation mo43940a(String str, String str2, AssetPackLocation assetPackLocation) {
        File file = new File(assetPackLocation.assetsPath(), str2);
        if (file.exists()) {
            return AssetLocation.m297a(file.getPath(), 0, file.length());
        }
        f970a.mo44087a("The asset %s is not present in Asset Pack %s. Searched in folder: %s", str2, str, assetPackLocation.assetsPath());
        return null;
    }

    /* access modifiers changed from: package-private */
    /* renamed from: a */
    public final File mo43941a(String str, int i, long j) {
        return new File(m380a(str, i), String.valueOf(j));
    }

    /* access modifiers changed from: package-private */
    /* renamed from: a */
    public final File mo43942a(String str, int i, long j, String str2) {
        return new File(new File(new File(m390h(str, i, j), "_slices"), "_unverified"), str2);
    }

    /* access modifiers changed from: package-private */
    /* renamed from: a */
    public final void mo43943a(String str, int i, long j, int i2) throws IOException {
        File g = m387g(str, i, j);
        Properties properties = new Properties();
        properties.put("numberOfMerges", String.valueOf(i2));
        g.getParentFile().mkdirs();
        g.createNewFile();
        FileOutputStream fileOutputStream = new FileOutputStream(g);
        properties.store(fileOutputStream, (String) null);
        fileOutputStream.close();
    }

    /* access modifiers changed from: package-private */
    /* renamed from: a */
    public final void mo43944a(List<String> list) {
        int a = this.f974e.mo44049a();
        List<File> g = m388g();
        int size = g.size();
        for (int i = 0; i < size; i++) {
            File file = g.get(i);
            if (!list.contains(file.getName()) && m383b(file) != ((long) a)) {
                m384c(file);
            }
        }
    }

    /* access modifiers changed from: package-private */
    /* renamed from: a */
    public final boolean mo43945a(String str) {
        try {
            return mo43952c(str) != null;
        } catch (IOException e) {
            return false;
        }
    }

    /* access modifiers changed from: package-private */
    /* renamed from: b */
    public final AssetPackLocation mo43946b(String str) throws IOException {
        String c = mo43952c(str);
        if (c == null) {
            return null;
        }
        File file = new File(c, "assets");
        if (file.isDirectory()) {
            return AssetPackLocation.m302a(c, file.getCanonicalPath());
        }
        f970a.mo44089b("Failed to find assets directory: %s", file);
        return null;
    }

    /* access modifiers changed from: package-private */
    /* renamed from: b */
    public final File mo43947b(String str, int i, long j) {
        return new File(mo43941a(str, i, j), "_metadata");
    }

    /* access modifiers changed from: package-private */
    /* renamed from: b */
    public final File mo43948b(String str, int i, long j, String str2) {
        return new File(new File(new File(m390h(str, i, j), "_slices"), "_verified"), str2);
    }

    /* access modifiers changed from: package-private */
    /* renamed from: b */
    public final Map<String, AssetPackLocation> mo43949b() {
        HashMap hashMap = new HashMap();
        try {
            for (File next : m388g()) {
                AssetPackLocation b = mo43946b(next.getName());
                if (b != null) {
                    hashMap.put(next.getName(), b);
                }
            }
        } catch (IOException e) {
            f970a.mo44089b("Could not process directory while scanning installed packs: %s", e);
        }
        return hashMap;
    }

    /* access modifiers changed from: package-private */
    /* renamed from: c */
    public final File mo43950c(String str, int i, long j) {
        return new File(m390h(str, i, j), "_packs");
    }

    /* access modifiers changed from: package-private */
    /* renamed from: c */
    public final File mo43951c(String str, int i, long j, String str2) {
        return new File(mo43963f(str, i, j, str2), "checkpoint.dat");
    }

    /* access modifiers changed from: package-private */
    /* renamed from: c */
    public final String mo43952c(String str) throws IOException {
        int length;
        File file = new File(m391i(), str);
        if (!file.exists()) {
            f970a.mo44087a("Pack not found with pack name: %s", str);
            return null;
        }
        File file2 = new File(file, String.valueOf(this.f974e.mo44049a()));
        if (!file2.exists()) {
            f970a.mo44087a("Pack not found with pack name: %s app version: %s", str, Integer.valueOf(this.f974e.mo44049a()));
            return null;
        }
        File[] listFiles = file2.listFiles();
        if (listFiles == null || (length = listFiles.length) == 0) {
            f970a.mo44087a("No pack version found for pack name: %s app version: %s", str, Integer.valueOf(this.f974e.mo44049a()));
            return null;
        } else if (length <= 1) {
            return listFiles[0].getCanonicalPath();
        } else {
            f970a.mo44089b("Multiple pack versions found for pack name: %s app version: %s", str, Integer.valueOf(this.f974e.mo44049a()));
            return null;
        }
    }

    /* access modifiers changed from: package-private */
    /* renamed from: c */
    public final void mo43953c() {
        List<File> g = m388g();
        int size = g.size();
        for (int i = 0; i < size; i++) {
            File file = g.get(i);
            if (file.listFiles() != null) {
                m382a(file);
                long b = m383b(file);
                if (((long) this.f974e.mo44049a()) != b) {
                    try {
                        new File(new File(file, String.valueOf(b)), "stale.tmp").createNewFile();
                    } catch (IOException e) {
                        f970a.mo44089b("Could not write staleness marker.", new Object[0]);
                    }
                }
                for (File a : file.listFiles()) {
                    m382a(a);
                }
            }
        }
    }

    /* access modifiers changed from: package-private */
    /* renamed from: d */
    public final int mo43954d(String str, int i, long j) throws IOException {
        File g = m387g(str, i, j);
        if (!g.exists()) {
            return 0;
        }
        Properties properties = new Properties();
        FileInputStream fileInputStream = new FileInputStream(g);
        try {
            properties.load(fileInputStream);
            fileInputStream.close();
            if (properties.getProperty("numberOfMerges") != null) {
                try {
                    return Integer.parseInt(properties.getProperty("numberOfMerges"));
                } catch (NumberFormatException e) {
                    throw new C2909by("Merge checkpoint file corrupt.", (Exception) e);
                }
            } else {
                throw new C2909by("Merge checkpoint file corrupt.");
            }
        } catch (Throwable th) {
            C3046cd.m768a(th, th);
        }
        throw th;
    }

    /* access modifiers changed from: package-private */
    /* renamed from: d */
    public final File mo43955d(String str, int i, long j, String str2) {
        return new File(mo43963f(str, i, j, str2), "checkpoint_ext.dat");
    }

    /* access modifiers changed from: package-private */
    /* renamed from: d */
    public final void mo43956d() {
        List<File> g = m388g();
        int size = g.size();
        for (int i = 0; i < size; i++) {
            File file = g.get(i);
            if (file.listFiles() != null) {
                for (File file2 : file.listFiles()) {
                    File file3 = new File(file2, "stale.tmp");
                    if (file3.exists() && System.currentTimeMillis() - file3.lastModified() > f972c) {
                        m384c(file2);
                    }
                }
            }
        }
    }

    /* access modifiers changed from: package-private */
    /* renamed from: d */
    public final boolean mo43957d(String str) {
        if (m386g(str).exists()) {
            return m384c(m386g(str));
        }
        return true;
    }

    /* access modifiers changed from: package-private */
    /* renamed from: e */
    public final int mo43958e(String str) {
        return (int) m383b(m386g(str));
    }

    /* access modifiers changed from: package-private */
    /* renamed from: e */
    public final File mo43959e(String str, int i, long j) {
        return new File(new File(m390h(str, i, j), "_slices"), "_metadata");
    }

    /* access modifiers changed from: package-private */
    /* renamed from: e */
    public final File mo43960e(String str, int i, long j, String str2) {
        return new File(mo43963f(str, i, j, str2), "slice.zip");
    }

    /* access modifiers changed from: package-private */
    /* renamed from: e */
    public final void mo43961e() {
        if (m389h().exists()) {
            for (File file : m389h().listFiles()) {
                if (System.currentTimeMillis() - file.lastModified() > f971b) {
                    m384c(file);
                } else {
                    m382a(file);
                }
            }
        }
    }

    /* access modifiers changed from: package-private */
    /* renamed from: f */
    public final long mo43962f(String str) {
        return m383b(m380a(str, mo43958e(str)));
    }

    /* access modifiers changed from: package-private */
    /* renamed from: f */
    public final File mo43963f(String str, int i, long j, String str2) {
        return new File(mo43959e(str, i, j), str2);
    }

    /* access modifiers changed from: package-private */
    /* renamed from: f */
    public final void mo43964f() {
        m384c(m391i());
    }

    /* access modifiers changed from: package-private */
    /* renamed from: f */
    public final void mo43965f(String str, int i, long j) {
        if (m390h(str, i, j).exists()) {
            m384c(m390h(str, i, j));
        }
    }
}
