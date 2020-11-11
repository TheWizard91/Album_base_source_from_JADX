package com.google.android.play.core.assetpacks;

import android.os.RemoteException;
import com.google.android.play.core.internal.C2990ab;
import com.google.android.play.core.internal.C3067s;
import com.google.android.play.core.tasks.C3169i;

/* renamed from: com.google.android.play.core.assetpacks.aa */
final class C2858aa extends C2990ab {

    /* renamed from: a */
    final /* synthetic */ String f882a;

    /* renamed from: b */
    final /* synthetic */ C3169i f883b;

    /* renamed from: c */
    final /* synthetic */ C2875ar f884c;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    C2858aa(C2875ar arVar, C3169i iVar, String str, C3169i iVar2) {
        super(iVar);
        this.f884c = arVar;
        this.f882a = str;
        this.f883b = iVar2;
    }

    /* access modifiers changed from: protected */
    /* renamed from: a */
    public final void mo43839a() {
        try {
            ((C3067s) this.f884c.f936e.mo44100b()).mo44188e(this.f884c.f934c, C2875ar.m352c(0, this.f882a), C2875ar.m350c(), new C2868ak(this.f884c, this.f883b, (short[]) null));
        } catch (RemoteException e) {
            C2875ar.f932a.mo44088a((Throwable) e, "removePack(%s)", this.f882a);
        }
    }
}
