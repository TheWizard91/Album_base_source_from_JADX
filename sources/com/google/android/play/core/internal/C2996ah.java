package com.google.android.play.core.internal;

import android.os.IBinder;
import android.os.IInterface;

/* renamed from: com.google.android.play.core.internal.ah */
final class C2996ah extends C2990ab {

    /* renamed from: a */
    final /* synthetic */ IBinder f1281a;

    /* renamed from: b */
    final /* synthetic */ C2998aj f1282b;

    C2996ah(C2998aj ajVar, IBinder iBinder) {
        this.f1282b = ajVar;
        this.f1281a = iBinder;
    }

    /* renamed from: a */
    public final void mo43839a() {
        C2999ak akVar = this.f1282b.f1284a;
        akVar.f1296l = (IInterface) akVar.f1292h.mo43838a(this.f1281a);
        C2999ak.m640f(this.f1282b.f1284a);
        this.f1282b.f1284a.f1290f = false;
        for (Runnable run : this.f1282b.f1284a.f1289e) {
            run.run();
        }
        this.f1282b.f1284a.f1289e.clear();
    }
}
