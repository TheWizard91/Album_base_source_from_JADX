package com.google.android.play.core.splitcompat;

import android.content.Context;
import android.content.pm.PackageManager;
import android.util.Log;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/* renamed from: com.google.android.play.core.splitcompat.e */
public final class C3083e {

    /* renamed from: a */
    private final long f1362a;

    /* renamed from: b */
    private final Context f1363b;

    /* renamed from: c */
    private File f1364c;

    public C3083e(Context context) throws PackageManager.NameNotFoundException {
        this.f1363b = context;
        this.f1362a = (long) context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionCode;
    }

    /* renamed from: a */
    private static File m898a(File file, String str) throws IOException {
        File file2 = new File(file, str);
        if (file2.getCanonicalPath().startsWith(file.getCanonicalPath())) {
            return file2;
        }
        throw new IllegalArgumentException("split ID cannot be placed in target directory");
    }

    /* renamed from: c */
    public static void m899c(File file) throws IOException {
        File[] listFiles;
        if (file.isDirectory() && (listFiles = file.listFiles()) != null) {
            for (File c : listFiles) {
                m899c(c);
            }
        }
        if (file.exists() && !file.delete()) {
            throw new IOException(String.format("Failed to delete '%s'", new Object[]{file.getAbsolutePath()}));
        }
    }

    /* renamed from: d */
    private static void m900d(File file) throws IOException {
        if (!file.exists()) {
            file.mkdirs();
            if (!file.isDirectory()) {
                String valueOf = String.valueOf(file.getAbsolutePath());
                throw new IOException(valueOf.length() == 0 ? new String("Unable to create directory: ") : "Unable to create directory: ".concat(valueOf));
            }
        } else if (!file.isDirectory()) {
            throw new IllegalArgumentException("File input must be directory when it exists.");
        }
    }

    /* renamed from: f */
    private final File m901f() throws IOException {
        File file = new File(m902g(), "verified-splits");
        m900d(file);
        return file;
    }

    /* renamed from: g */
    private final File m902g() throws IOException {
        File file = new File(m904h(), Long.toString(this.f1362a));
        m900d(file);
        return file;
    }

    /* renamed from: g */
    private final File m903g(String str) throws IOException {
        File a = m898a(m906i(), str);
        m900d(a);
        return a;
    }

    /* renamed from: h */
    private final File m904h() throws IOException {
        if (this.f1364c == null) {
            Context context = this.f1363b;
            if (context != null) {
                this.f1364c = context.getFilesDir();
            } else {
                throw new IllegalStateException("context must be non-null to populate null filesDir");
            }
        }
        File file = new File(this.f1364c, "splitcompat");
        m900d(file);
        return file;
    }

    /* renamed from: h */
    private static String m905h(String str) {
        String valueOf = String.valueOf(str);
        return ".apk".length() == 0 ? new String(valueOf) : valueOf.concat(".apk");
    }

    /* renamed from: i */
    private final File m906i() throws IOException {
        File file = new File(m902g(), "native-libraries");
        m900d(file);
        return file;
    }

    /* renamed from: a */
    public final File mo44219a(File file) throws IOException {
        return m898a(m901f(), file.getName());
    }

    /* renamed from: a */
    public final File mo44220a(String str) throws IOException {
        return m898a(mo44226c(), m905h(str));
    }

    /* renamed from: a */
    public final File mo44221a(String str, String str2) throws IOException {
        return m898a(m903g(str), str2);
    }

    /* renamed from: a */
    public final void mo44222a() throws IOException {
        File h = m904h();
        String[] list = h.list();
        if (list != null) {
            for (String str : list) {
                if (!str.equals(Long.toString(this.f1362a))) {
                    File file = new File(h, str);
                    String valueOf = String.valueOf(file);
                    long j = this.f1362a;
                    StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 118);
                    sb.append("FileStorage: removing directory for different version code (directory = ");
                    sb.append(valueOf);
                    sb.append(", current version code = ");
                    sb.append(j);
                    sb.append(")");
                    Log.d("SplitCompat", sb.toString());
                    m899c(file);
                }
            }
        }
    }

    /* renamed from: b */
    public final File mo44223b() throws IOException {
        return new File(m902g(), "lock.tmp");
    }

    /* renamed from: b */
    public final File mo44224b(String str) throws IOException {
        return m898a(m901f(), m905h(str));
    }

    /* access modifiers changed from: package-private */
    /* renamed from: b */
    public final void mo44225b(File file) throws IOException {
        C3082d.m897a(file.getParentFile().getParentFile().equals(m906i()), (Object) "File to remove is not a native library");
        m899c(file);
    }

    /* renamed from: c */
    public final File mo44226c() throws IOException {
        File file = new File(m902g(), "unverified-splits");
        m900d(file);
        return file;
    }

    /* renamed from: c */
    public final File mo44227c(String str) throws IOException {
        File file = new File(m902g(), "dex");
        m900d(file);
        File a = m898a(file, str);
        m900d(a);
        return a;
    }

    /* access modifiers changed from: package-private */
    /* renamed from: d */
    public final Set<C3096r> mo44228d() throws IOException {
        File f = m901f();
        HashSet hashSet = new HashSet();
        File[] listFiles = f.listFiles();
        if (listFiles != null) {
            for (File file : listFiles) {
                if (file.isFile() && file.getName().endsWith(".apk")) {
                    String name = file.getName();
                    hashSet.add(new C3080b(file, name.substring(0, name.length() - 4)));
                }
            }
        }
        return hashSet;
    }

    /* access modifiers changed from: package-private */
    /* renamed from: d */
    public final void mo44229d(String str) throws IOException {
        m899c(m903g(str));
    }

    /* access modifiers changed from: package-private */
    /* renamed from: e */
    public final List<String> mo44230e() throws IOException {
        ArrayList arrayList = new ArrayList();
        File[] listFiles = m906i().listFiles();
        if (listFiles != null) {
            for (File file : listFiles) {
                if (file.isDirectory()) {
                    arrayList.add(file.getName());
                }
            }
        }
        return arrayList;
    }

    /* access modifiers changed from: package-private */
    /* renamed from: e */
    public final Set<File> mo44231e(String str) throws IOException {
        HashSet hashSet = new HashSet();
        File[] listFiles = m903g(str).listFiles();
        if (listFiles != null) {
            for (File file : listFiles) {
                if (file.isFile()) {
                    hashSet.add(file);
                }
            }
        }
        return hashSet;
    }

    /* access modifiers changed from: package-private */
    /* renamed from: f */
    public final void mo44232f(String str) throws IOException {
        m899c(mo44224b(str));
    }
}
