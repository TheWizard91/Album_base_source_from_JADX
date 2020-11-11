package com.google.android.play.core.internal;

import java.io.File;
import java.util.Set;

/* renamed from: com.google.android.play.core.internal.bf */
final class C3021bf implements C3004ap {
    C3021bf() {
    }

    /* renamed from: b */
    static void m695b(ClassLoader classLoader, Set<File> set) {
        C3016ba.m684a(classLoader, set, new C3019bd());
    }

    /* renamed from: b */
    static boolean m696b(ClassLoader classLoader, File file, File file2, boolean z) {
        return C3010av.m671a(classLoader, file, file2, z, C3016ba.m683a(), "path", new C3020be());
    }

    /* renamed from: a */
    public final void mo44107a(ClassLoader classLoader, Set<File> set) {
        m695b(classLoader, set);
    }

    /* renamed from: a */
    public final boolean mo44108a(ClassLoader classLoader, File file, File file2, boolean z) {
        return m696b(classLoader, file, file2, z);
    }
}
