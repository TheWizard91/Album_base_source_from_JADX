package com.google.android.play.core.assetpacks;

import com.google.android.play.core.internal.C2989aa;
import com.google.android.play.core.internal.C3047ce;
import java.util.concurrent.atomic.AtomicBoolean;

/* renamed from: com.google.android.play.core.assetpacks.bz */
final class C2910bz {

    /* renamed from: a */
    private static final C2989aa f1063a = new C2989aa("ExtractorLooper");

    /* renamed from: b */
    private final C2929cr f1064b;

    /* renamed from: c */
    private final C2907bw f1065c;

    /* renamed from: d */
    private final C2960dv f1066d;

    /* renamed from: e */
    private final C2944df f1067e;

    /* renamed from: f */
    private final C2948dj f1068f;

    /* renamed from: g */
    private final C2953do f1069g;

    /* renamed from: h */
    private final C3047ce<C2982w> f1070h;

    /* renamed from: i */
    private final C2932cu f1071i;

    /* renamed from: j */
    private final AtomicBoolean f1072j = new AtomicBoolean(false);

    C2910bz(C2929cr crVar, C3047ce<C2982w> ceVar, C2907bw bwVar, C2960dv dvVar, C2944df dfVar, C2948dj djVar, C2953do doVar, C2932cu cuVar) {
        this.f1064b = crVar;
        this.f1070h = ceVar;
        this.f1065c = bwVar;
        this.f1066d = dvVar;
        this.f1067e = dfVar;
        this.f1068f = djVar;
        this.f1069g = doVar;
        this.f1071i = cuVar;
    }

    /* renamed from: a */
    private final void m459a(int i, Exception exc) {
        try {
            this.f1064b.mo44034d(i);
            this.f1064b.mo44021a(i);
        } catch (C2909by e) {
            f1063a.mo44089b("Error during error handling: %s", exc.getMessage());
        }
    }

    /* access modifiers changed from: package-private */
    /* renamed from: a */
    public final void mo44013a() {
        C2989aa aaVar = f1063a;
        aaVar.mo44087a("Run extractor loop", new Object[0]);
        if (this.f1072j.compareAndSet(false, true)) {
            while (true) {
                C2931ct ctVar = null;
                try {
                    ctVar = this.f1071i.mo44035a();
                } catch (C2909by e) {
                    f1063a.mo44089b("Error while getting next extraction task: %s", e.getMessage());
                    if (e.f1062a >= 0) {
                        this.f1070h.mo44145a().mo43921a(e.f1062a);
                        m459a(e.f1062a, e);
                    }
                }
                if (ctVar != null) {
                    try {
                        if (ctVar instanceof C2906bv) {
                            this.f1065c.mo44012a((C2906bv) ctVar);
                        } else if (ctVar instanceof C2959du) {
                            this.f1066d.mo44063a((C2959du) ctVar);
                        } else if (ctVar instanceof C2943de) {
                            this.f1067e.mo44046a((C2943de) ctVar);
                        } else if (ctVar instanceof C2946dh) {
                            this.f1068f.mo44048a((C2946dh) ctVar);
                        } else if (!(ctVar instanceof C2952dn)) {
                            f1063a.mo44089b("Unknown task type: %s", ctVar.getClass().getName());
                        } else {
                            this.f1069g.mo44050a((C2952dn) ctVar);
                        }
                    } catch (Exception e2) {
                        f1063a.mo44089b("Error during extraction task: %s", e2.getMessage());
                        this.f1070h.mo44145a().mo43921a(ctVar.f1128j);
                        m459a(ctVar.f1128j, e2);
                    }
                } else {
                    this.f1072j.set(false);
                    return;
                }
            }
        } else {
            aaVar.mo44091d("runLoop already looping; return", new Object[0]);
        }
    }
}
