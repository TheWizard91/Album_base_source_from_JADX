package com.google.android.play.core.assetpacks;

import com.google.android.play.core.internal.C3047ce;
import java.io.File;
import java.util.concurrent.Executor;

/* renamed from: com.google.android.play.core.assetpacks.dj */
final class C2948dj {

    /* renamed from: a */
    private final C2886bb f1179a;

    /* renamed from: b */
    private final C3047ce<C2982w> f1180b;

    /* renamed from: c */
    private final C2929cr f1181c;

    /* renamed from: d */
    private final C3047ce<Executor> f1182d;

    /* renamed from: e */
    private final C2913cb f1183e;

    C2948dj(C2886bb bbVar, C3047ce<C2982w> ceVar, C2929cr crVar, C3047ce<Executor> ceVar2, C2913cb cbVar) {
        this.f1179a = bbVar;
        this.f1180b = ceVar;
        this.f1181c = crVar;
        this.f1182d = ceVar2;
        this.f1183e = cbVar;
    }

    /* renamed from: a */
    public final void mo44048a(C2946dh dhVar) {
        File c = this.f1179a.mo43950c(dhVar.f1129k, dhVar.f1176a, dhVar.f1177b);
        File e = this.f1179a.mo43959e(dhVar.f1129k, dhVar.f1176a, dhVar.f1177b);
        if (!c.exists() || !e.exists()) {
            throw new C2909by(String.format("Cannot find pack files to move for pack %s.", new Object[]{dhVar.f1129k}), dhVar.f1128j);
        }
        File a = this.f1179a.mo43941a(dhVar.f1129k, dhVar.f1176a, dhVar.f1177b);
        a.mkdirs();
        if (c.renameTo(a)) {
            File b = this.f1179a.mo43947b(dhVar.f1129k, dhVar.f1176a, dhVar.f1177b);
            b.mkdirs();
            if (e.renameTo(b)) {
                C2886bb bbVar = this.f1179a;
                bbVar.getClass();
                this.f1182d.mo44145a().execute(C2947di.m537a(bbVar));
                this.f1181c.mo44022a(dhVar.f1129k, dhVar.f1176a, dhVar.f1177b);
                this.f1183e.mo44015a(dhVar.f1129k);
                this.f1180b.mo44145a().mo43922a(dhVar.f1128j, dhVar.f1129k);
                return;
            }
            throw new C2909by("Cannot move metadata files to final location.", dhVar.f1128j);
        }
        throw new C2909by("Cannot move merged pack files to final location.", dhVar.f1128j);
    }
}
