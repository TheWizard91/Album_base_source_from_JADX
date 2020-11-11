package com.google.android.play.core.internal;

import com.google.android.play.core.splitinstall.C3156v;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/* renamed from: com.google.android.play.core.internal.ba */
final class C3016ba implements C3004ap {
    C3016ba() {
    }

    /* renamed from: a */
    static C3009au m683a() {
        return new C3012ax();
    }

    /* renamed from: a */
    public static void m684a(ClassLoader classLoader, Set<File> set, C3014az azVar) {
        if (!set.isEmpty()) {
            HashSet hashSet = new HashSet();
            for (File parentFile : set) {
                hashSet.add(parentFile.getParentFile());
            }
            Object a = C3010av.m670a(classLoader);
            List a2 = C3027bl.m710a(a, "nativeLibraryDirectories", List.class).mo44112a();
            hashSet.removeAll(a2);
            a2.addAll(hashSet);
            ArrayList arrayList = new ArrayList();
            Object[] a3 = azVar.mo44111a(a, new ArrayList(hashSet), arrayList);
            if (!arrayList.isEmpty()) {
                C3025bj bjVar = new C3025bj("Error in makePathElements");
                int size = arrayList.size();
                for (int i = 0; i < size; i++) {
                    C3046cd.m768a(bjVar, (IOException) arrayList.get(i));
                }
                throw bjVar;
            }
            synchronized (C3156v.class) {
                C3027bl.m720b(a, "nativeLibraryPathElements", Object.class).mo44116b(Arrays.asList(a3));
            }
        }
    }

    /* renamed from: a */
    public static boolean m685a(ClassLoader classLoader, File file, File file2, boolean z, String str) {
        return C3010av.m671a(classLoader, file, file2, z, m683a(), str, C3010av.m672b());
    }

    /* renamed from: b */
    static C3014az m686b() {
        return new C3013ay();
    }

    /* renamed from: a */
    public final void mo44107a(ClassLoader classLoader, Set<File> set) {
        m684a(classLoader, set, m686b());
    }

    /* renamed from: a */
    public final boolean mo44108a(ClassLoader classLoader, File file, File file2, boolean z) {
        return m685a(classLoader, file, file2, z, "zip");
    }
}
