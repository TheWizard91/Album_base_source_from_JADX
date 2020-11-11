package com.google.android.play.core.splitcompat;

import java.io.IOException;
import java.util.Set;
import java.util.zip.ZipFile;

/* renamed from: com.google.android.play.core.splitcompat.h */
final class C3086h implements C3088j {

    /* renamed from: a */
    final /* synthetic */ Set f1370a;

    /* renamed from: b */
    final /* synthetic */ C3096r f1371b;

    /* renamed from: c */
    final /* synthetic */ C3091m f1372c;

    C3086h(C3091m mVar, Set set, C3096r rVar) {
        this.f1372c = mVar;
        this.f1370a = set;
        this.f1371b = rVar;
    }

    /* renamed from: a */
    public final void mo44234a(ZipFile zipFile, Set<C3090l> set) throws IOException {
        this.f1370a.addAll(C3091m.m927a(this.f1372c, (Set) set, this.f1371b, zipFile));
    }
}
