package com.google.android.play.core.assetpacks;

import android.os.RemoteException;
import com.google.android.play.core.internal.C2990ab;
import com.google.android.play.core.internal.C3067s;
import com.google.android.play.core.tasks.C3169i;

/* renamed from: com.google.android.play.core.assetpacks.ad */
final class C2861ad extends C2990ab {

    /* renamed from: a */
    final /* synthetic */ C3169i f892a;

    /* renamed from: b */
    final /* synthetic */ C2875ar f893b;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    C2861ad(C2875ar arVar, C3169i iVar, C3169i iVar2) {
        super(iVar);
        this.f893b = arVar;
        this.f892a = iVar2;
    }

    /* access modifiers changed from: protected */
    /* renamed from: a */
    public final void mo43839a() {
        try {
            ((C3067s) this.f893b.f936e.mo44100b()).mo44180a(this.f893b.f934c, C2875ar.m350c(), new C2870am(this.f893b, this.f892a));
        } catch (RemoteException e) {
            C2875ar.f932a.mo44088a((Throwable) e, "syncPacks", new Object[0]);
            this.f892a.mo44331b((Exception) new RuntimeException(e));
        }
    }
}
