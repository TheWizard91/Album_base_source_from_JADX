package com.google.android.play.core.splitinstall;

import android.os.RemoteException;
import com.google.android.play.core.internal.C2990ab;
import com.google.android.play.core.tasks.C3169i;
import java.util.Collection;
import java.util.List;

/* renamed from: com.google.android.play.core.splitinstall.aj */
final class C3107aj extends C2990ab {

    /* renamed from: a */
    final /* synthetic */ List f1425a;

    /* renamed from: b */
    final /* synthetic */ C3169i f1426b;

    /* renamed from: c */
    final /* synthetic */ C3123az f1427c;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    C3107aj(C3123az azVar, C3169i iVar, List list, C3169i iVar2) {
        super(iVar);
        this.f1427c = azVar;
        this.f1425a = list;
        this.f1426b = iVar2;
    }

    /* access modifiers changed from: protected */
    /* renamed from: a */
    public final void mo43839a() {
        try {
            this.f1427c.f1449a.mo44100b().mo44121b(this.f1427c.f1450d, C3123az.m985a((Collection) this.f1425a), C3123az.m986b(), new C3118au(this.f1427c, this.f1426b));
        } catch (RemoteException e) {
            C3123az.f1447b.mo44088a((Throwable) e, "deferredUninstall(%s)", this.f1425a);
            this.f1426b.mo44331b((Exception) new RuntimeException(e));
        }
    }
}
