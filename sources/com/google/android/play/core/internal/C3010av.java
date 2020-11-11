package com.google.android.play.core.internal;

import android.util.Log;
import com.google.android.play.core.splitinstall.C3156v;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/* renamed from: com.google.android.play.core.internal.av */
final class C3010av implements C3004ap {
    C3010av() {
    }

    /* renamed from: a */
    static C3009au m669a() {
        return new C3006ar();
    }

    /* renamed from: a */
    static Object m670a(ClassLoader classLoader) {
        return C3027bl.m710a((Object) classLoader, "pathList", Object.class).mo44112a();
    }

    /* renamed from: a */
    static boolean m671a(ClassLoader classLoader, File file, File file2, boolean z, C3009au auVar, String str, C3008at atVar) {
        ArrayList arrayList = new ArrayList();
        Object a = m670a(classLoader);
        C3026bk b = C3027bl.m720b(a, "dexElements", Object.class);
        List<Object> asList = Arrays.asList((Object[]) b.mo44112a());
        ArrayList arrayList2 = new ArrayList();
        for (Object a2 : asList) {
            arrayList2.add(C3027bl.m710a(a2, str, File.class).mo44112a());
        }
        if (!arrayList2.contains(file2)) {
            if (!z && !atVar.mo44110a(a, file2, file)) {
                String valueOf = String.valueOf(file2.getPath());
                Log.w("SplitCompat", valueOf.length() == 0 ? new String("Should be optimized ") : "Should be optimized ".concat(valueOf));
                return false;
            }
            b.mo44114a((Collection) Arrays.asList(auVar.mo44109a(a, new ArrayList(Collections.singleton(file2)), file, arrayList)));
            if (!arrayList.isEmpty()) {
                C3025bj bjVar = new C3025bj("DexPathList.makeDexElement failed");
                int size = arrayList.size();
                for (int i = 0; i < size; i++) {
                    IOException iOException = (IOException) arrayList.get(i);
                    Log.e("SplitCompat", "DexPathList.makeDexElement failed", iOException);
                    C3046cd.m768a(bjVar, iOException);
                }
                C3027bl.m720b(a, "dexElementsSuppressedExceptions", IOException.class).mo44114a((Collection) arrayList);
                throw bjVar;
            }
        }
        return true;
    }

    /* renamed from: b */
    static C3008at m672b() {
        return new C3007as();
    }

    /* renamed from: b */
    static void m673b(ClassLoader classLoader, Set<File> set) {
        if (!set.isEmpty()) {
            HashSet hashSet = new HashSet();
            for (File next : set) {
                String valueOf = String.valueOf(next.getParentFile().getAbsolutePath());
                Log.d("Splitcompat", valueOf.length() == 0 ? new String("Adding native library parent directory: ") : "Adding native library parent directory: ".concat(valueOf));
                hashSet.add(next.getParentFile());
            }
            C3026bk b = C3027bl.m720b(m670a(classLoader), "nativeLibraryDirectories", File.class);
            hashSet.removeAll(Arrays.asList((File[]) b.mo44112a()));
            synchronized (C3156v.class) {
                int size = hashSet.size();
                StringBuilder sb = new StringBuilder(30);
                sb.append("Adding directories ");
                sb.append(size);
                Log.d("Splitcompat", sb.toString());
                b.mo44116b(hashSet);
            }
        }
    }

    /* renamed from: a */
    public final void mo44107a(ClassLoader classLoader, Set<File> set) {
        m673b(classLoader, set);
    }

    /* renamed from: a */
    public final boolean mo44108a(ClassLoader classLoader, File file, File file2, boolean z) {
        return m671a(classLoader, file, file2, z, m669a(), "zip", m672b());
    }
}
