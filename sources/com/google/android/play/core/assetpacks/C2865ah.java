package com.google.android.play.core.assetpacks;

import android.os.RemoteException;
import com.google.android.play.core.internal.C2990ab;
import com.google.android.play.core.internal.C3067s;
import com.google.android.play.core.internal.C3069u;
import com.google.android.play.core.tasks.C3169i;

/* renamed from: com.google.android.play.core.assetpacks.ah */
final class C2865ah extends C2990ab {

    /* renamed from: a */
    final /* synthetic */ int f909a;

    /* renamed from: b */
    final /* synthetic */ C3169i f910b;

    /* renamed from: c */
    final /* synthetic */ C2875ar f911c;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    C2865ah(C2875ar arVar, C3169i iVar, int i, C3169i iVar2) {
        super(iVar);
        this.f911c = arVar;
        this.f909a = i;
        this.f910b = iVar2;
    }

    /* access modifiers changed from: protected */
    /* renamed from: a */
    public final void mo43839a() {
        try {
            ((C3067s) this.f911c.f936e.mo44100b()).mo44185c(this.f911c.f934c, C2875ar.m351c(this.f909a), C2875ar.m350c(), (C3069u) new C2868ak(this.f911c, this.f910b, (int[]) null));
        } catch (RemoteException e) {
            C2875ar.f932a.mo44088a((Throwable) e, "notifySessionFailed", new Object[0]);
        }
    }
}
