package com.google.android.play.core.assetpacks;

import android.os.Bundle;
import android.os.RemoteException;
import com.google.android.play.core.internal.C2990ab;
import com.google.android.play.core.internal.C3067s;
import com.google.android.play.core.internal.C3069u;
import com.google.android.play.core.tasks.C3169i;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/* renamed from: com.google.android.play.core.assetpacks.ab */
final class C2859ab extends C2990ab {

    /* renamed from: a */
    final /* synthetic */ List f885a;

    /* renamed from: b */
    final /* synthetic */ C3169i f886b;

    /* renamed from: c */
    final /* synthetic */ List f887c;

    /* renamed from: d */
    final /* synthetic */ C2875ar f888d;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    C2859ab(C2875ar arVar, C3169i iVar, List list, C3169i iVar2, List list2) {
        super(iVar);
        this.f888d = arVar;
        this.f885a = list;
        this.f886b = iVar2;
        this.f887c = list2;
    }

    /* access modifiers changed from: protected */
    /* renamed from: a */
    public final void mo43839a() {
        ArrayList a = C2875ar.m343a((Collection) this.f885a);
        try {
            String a2 = this.f888d.f934c;
            Bundle c = C2875ar.m350c();
            C2875ar arVar = this.f888d;
            ((C3067s) this.f888d.f936e.mo44100b()).mo44181a(a2, (List<Bundle>) a, c, (C3069u) new C2874aq(arVar, this.f886b, arVar.f935d, this.f887c));
        } catch (RemoteException e) {
            C2875ar.f932a.mo44088a((Throwable) e, "startDownload(%s)", this.f885a);
            this.f886b.mo44331b((Exception) new RuntimeException(e));
        }
    }
}
