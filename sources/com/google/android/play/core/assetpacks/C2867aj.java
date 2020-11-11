package com.google.android.play.core.assetpacks;

import android.os.RemoteException;
import com.google.android.play.core.internal.C2990ab;
import com.google.android.play.core.internal.C3067s;
import com.google.android.play.core.tasks.C3169i;

/* renamed from: com.google.android.play.core.assetpacks.aj */
final class C2867aj extends C2990ab {

    /* renamed from: a */
    final /* synthetic */ C3169i f918a;

    /* renamed from: b */
    final /* synthetic */ C2875ar f919b;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    C2867aj(C2875ar arVar, C3169i iVar, C3169i iVar2) {
        super(iVar);
        this.f919b = arVar;
        this.f918a = iVar2;
    }

    /* access modifiers changed from: protected */
    /* renamed from: a */
    public final void mo43839a() {
        try {
            ((C3067s) this.f919b.f937f.mo44100b()).mo44183b(this.f919b.f934c, C2875ar.m350c(), new C2871an(this.f919b, this.f918a));
        } catch (RemoteException e) {
            C2875ar.f932a.mo44088a((Throwable) e, "keepAlive", new Object[0]);
        }
    }
}
