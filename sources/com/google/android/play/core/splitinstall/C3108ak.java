package com.google.android.play.core.splitinstall;

import android.os.RemoteException;
import com.google.android.play.core.internal.C2990ab;
import com.google.android.play.core.tasks.C3169i;
import java.util.Collection;
import java.util.List;

/* renamed from: com.google.android.play.core.splitinstall.ak */
final class C3108ak extends C2990ab {

    /* renamed from: a */
    final /* synthetic */ List f1428a;

    /* renamed from: b */
    final /* synthetic */ C3169i f1429b;

    /* renamed from: c */
    final /* synthetic */ C3123az f1430c;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    C3108ak(C3123az azVar, C3169i iVar, List list, C3169i iVar2) {
        super(iVar);
        this.f1430c = azVar;
        this.f1428a = list;
        this.f1429b = iVar2;
    }

    /* access modifiers changed from: protected */
    /* renamed from: a */
    public final void mo43839a() {
        try {
            this.f1430c.f1449a.mo44100b().mo44122c(this.f1430c.f1450d, C3123az.m985a((Collection) this.f1428a), C3123az.m986b(), new C3115ar(this.f1430c, this.f1429b));
        } catch (RemoteException e) {
            C3123az.f1447b.mo44088a((Throwable) e, "deferredInstall(%s)", this.f1428a);
            this.f1429b.mo44331b((Exception) new RuntimeException(e));
        }
    }
}
