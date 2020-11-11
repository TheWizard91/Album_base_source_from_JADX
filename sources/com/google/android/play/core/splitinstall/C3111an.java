package com.google.android.play.core.splitinstall;

import android.os.RemoteException;
import com.google.android.play.core.internal.C2990ab;
import com.google.android.play.core.tasks.C3169i;

/* renamed from: com.google.android.play.core.splitinstall.an */
final class C3111an extends C2990ab {

    /* renamed from: a */
    final /* synthetic */ int f1437a;

    /* renamed from: b */
    final /* synthetic */ C3169i f1438b;

    /* renamed from: c */
    final /* synthetic */ C3123az f1439c;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    C3111an(C3123az azVar, C3169i iVar, int i, C3169i iVar2) {
        super(iVar);
        this.f1439c = azVar;
        this.f1437a = i;
        this.f1438b = iVar2;
    }

    /* access modifiers changed from: protected */
    /* renamed from: a */
    public final void mo43839a() {
        try {
            this.f1439c.f1449a.mo44100b().mo44118a(this.f1439c.f1450d, this.f1437a, new C3119av(this.f1439c, this.f1438b));
        } catch (RemoteException e) {
            C3123az.f1447b.mo44088a((Throwable) e, "getSessionState(%d)", Integer.valueOf(this.f1437a));
            this.f1438b.mo44331b((Exception) new RuntimeException(e));
        }
    }
}
