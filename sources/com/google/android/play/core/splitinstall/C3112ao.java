package com.google.android.play.core.splitinstall;

import android.os.RemoteException;
import com.google.android.play.core.internal.C2990ab;
import com.google.android.play.core.tasks.C3169i;

/* renamed from: com.google.android.play.core.splitinstall.ao */
final class C3112ao extends C2990ab {

    /* renamed from: a */
    final /* synthetic */ C3169i f1440a;

    /* renamed from: b */
    final /* synthetic */ C3123az f1441b;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    C3112ao(C3123az azVar, C3169i iVar, C3169i iVar2) {
        super(iVar);
        this.f1441b = azVar;
        this.f1440a = iVar2;
    }

    /* access modifiers changed from: protected */
    /* renamed from: a */
    public final void mo43839a() {
        try {
            this.f1441b.f1449a.mo44100b().mo44119a(this.f1441b.f1450d, new C3120aw(this.f1441b, this.f1440a));
        } catch (RemoteException e) {
            C3123az.f1447b.mo44088a((Throwable) e, "getSessionStates", new Object[0]);
            this.f1440a.mo44331b((Exception) new RuntimeException(e));
        }
    }
}
