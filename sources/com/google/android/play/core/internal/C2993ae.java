package com.google.android.play.core.internal;

/* renamed from: com.google.android.play.core.internal.ae */
final class C2993ae extends C2990ab {

    /* renamed from: a */
    final /* synthetic */ C2999ak f1280a;

    C2993ae(C2999ak akVar) {
        this.f1280a = akVar;
    }

    /* renamed from: a */
    public final void mo43839a() {
        if (this.f1280a.f1296l != null) {
            this.f1280a.f1287c.mo44090c("Unbind from service.", new Object[0]);
            this.f1280a.f1286b.unbindService(this.f1280a.f1295k);
            this.f1280a.f1290f = false;
            this.f1280a.f1296l = null;
            this.f1280a.f1295k = null;
        }
    }
}
