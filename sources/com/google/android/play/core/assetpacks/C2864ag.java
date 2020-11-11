package com.google.android.play.core.assetpacks;

import android.os.RemoteException;
import com.google.android.play.core.internal.C2990ab;
import com.google.android.play.core.internal.C3067s;
import com.google.android.play.core.internal.C3069u;
import com.google.android.play.core.tasks.C3169i;

/* renamed from: com.google.android.play.core.assetpacks.ag */
final class C2864ag extends C2990ab {

    /* renamed from: a */
    final /* synthetic */ int f904a;

    /* renamed from: b */
    final /* synthetic */ String f905b;

    /* renamed from: c */
    final /* synthetic */ C3169i f906c;

    /* renamed from: d */
    final /* synthetic */ int f907d;

    /* renamed from: e */
    final /* synthetic */ C2875ar f908e;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    C2864ag(C2875ar arVar, C3169i iVar, int i, String str, C3169i iVar2, int i2) {
        super(iVar);
        this.f908e = arVar;
        this.f904a = i;
        this.f905b = str;
        this.f906c = iVar2;
        this.f907d = i2;
    }

    /* access modifiers changed from: protected */
    /* renamed from: a */
    public final void mo43839a() {
        try {
            ((C3067s) this.f908e.f936e.mo44100b()).mo44182b(this.f908e.f934c, C2875ar.m352c(this.f904a, this.f905b), C2875ar.m350c(), (C3069u) new C2872ao(this.f908e, this.f906c, this.f904a, this.f905b, this.f907d));
        } catch (RemoteException e) {
            C2875ar.f932a.mo44088a((Throwable) e, "notifyModuleCompleted", new Object[0]);
        }
    }
}
