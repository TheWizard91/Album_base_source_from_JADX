package com.google.android.play.core.splitcompat;

import java.io.IOException;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.zip.ZipFile;

/* renamed from: com.google.android.play.core.splitcompat.g */
final class C3085g implements C3088j {

    /* renamed from: a */
    final /* synthetic */ C3096r f1366a;

    /* renamed from: b */
    final /* synthetic */ Set f1367b;

    /* renamed from: c */
    final /* synthetic */ AtomicBoolean f1368c;

    /* renamed from: d */
    final /* synthetic */ C3091m f1369d;

    C3085g(C3091m mVar, C3096r rVar, Set set, AtomicBoolean atomicBoolean) {
        this.f1369d = mVar;
        this.f1366a = rVar;
        this.f1367b = set;
        this.f1368c = atomicBoolean;
    }

    /* renamed from: a */
    public final void mo44234a(ZipFile zipFile, Set<C3090l> set) throws IOException {
        this.f1369d.m930a(this.f1366a, set, new C3084f(this));
    }
}
