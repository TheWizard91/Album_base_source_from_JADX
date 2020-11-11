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

/* renamed from: com.google.android.play.core.assetpacks.ae */
final class C2862ae extends C2990ab {

    /* renamed from: a */
    final /* synthetic */ List f894a;

    /* renamed from: b */
    final /* synthetic */ C3169i f895b;

    /* renamed from: c */
    final /* synthetic */ C2883az f896c;

    /* renamed from: d */
    final /* synthetic */ C2875ar f897d;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    C2862ae(C2875ar arVar, C3169i iVar, List list, C3169i iVar2, C2883az azVar) {
        super(iVar);
        this.f897d = arVar;
        this.f894a = list;
        this.f895b = iVar2;
        this.f896c = azVar;
    }

    /* access modifiers changed from: protected */
    /* renamed from: a */
    public final void mo43839a() {
        ArrayList a = C2875ar.m343a((Collection) this.f894a);
        try {
            String a2 = this.f897d.f934c;
            Bundle c = C2875ar.m350c();
            C2875ar arVar = this.f897d;
            ((C3067s) this.f897d.f936e.mo44100b()).mo44186c(a2, (List<Bundle>) a, c, (C3069u) new C2873ap(arVar, this.f895b, arVar.f935d, this.f896c));
        } catch (RemoteException e) {
            C2875ar.f932a.mo44088a((Throwable) e, "getPackStates(%s)", this.f894a);
            this.f895b.mo44331b((Exception) new RuntimeException(e));
        }
    }
}
