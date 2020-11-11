package com.google.android.play.core.splitcompat;

import java.io.File;
import java.io.IOException;

/* renamed from: com.google.android.play.core.splitcompat.f */
final class C3084f implements C3089k {

    /* renamed from: a */
    final /* synthetic */ C3085g f1365a;

    C3084f(C3085g gVar) {
        this.f1365a = gVar;
    }

    /* renamed from: a */
    public final void mo44233a(C3090l lVar, File file, boolean z) throws IOException {
        this.f1365a.f1367b.add(file);
        if (!z) {
            this.f1365a.f1368c.set(false);
        }
    }
}
