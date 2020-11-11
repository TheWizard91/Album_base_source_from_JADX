package com.google.android.play.core.assetpacks;

import android.os.Bundle;
import android.os.RemoteException;
import com.google.android.play.core.internal.C2990ab;
import com.google.android.play.core.internal.C3067s;
import com.google.android.play.core.internal.C3069u;
import com.google.android.play.core.tasks.C3169i;
import java.util.Collection;
import java.util.List;

/* renamed from: com.google.android.play.core.assetpacks.ac */
final class C2860ac extends C2990ab {

    /* renamed from: a */
    final /* synthetic */ List f889a;

    /* renamed from: b */
    final /* synthetic */ C3169i f890b;

    /* renamed from: c */
    final /* synthetic */ C2875ar f891c;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    C2860ac(C2875ar arVar, C3169i iVar, List list, C3169i iVar2) {
        super(iVar);
        this.f891c = arVar;
        this.f889a = list;
        this.f890b = iVar2;
    }

    /* access modifiers changed from: protected */
    /* renamed from: a */
    public final void mo43839a() {
        try {
            ((C3067s) this.f891c.f936e.mo44100b()).mo44184b(this.f891c.f934c, (List<Bundle>) C2875ar.m343a((Collection) this.f889a), C2875ar.m350c(), (C3069u) new C2868ak(this.f891c, this.f890b, (byte[]) null));
        } catch (RemoteException e) {
            C2875ar.f932a.mo44088a((Throwable) e, "cancelDownloads(%s)", this.f889a);
        }
    }
}
