package com.google.android.play.core.splitinstall;

import android.os.RemoteException;
import com.google.android.play.core.internal.C2990ab;
import com.google.android.play.core.tasks.C3169i;
import java.util.Collection;
import java.util.List;

/* renamed from: com.google.android.play.core.splitinstall.am */
final class C3110am extends C2990ab {

    /* renamed from: a */
    final /* synthetic */ List f1434a;

    /* renamed from: b */
    final /* synthetic */ C3169i f1435b;

    /* renamed from: c */
    final /* synthetic */ C3123az f1436c;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    C3110am(C3123az azVar, C3169i iVar, List list, C3169i iVar2) {
        super(iVar);
        this.f1436c = azVar;
        this.f1434a = list;
        this.f1435b = iVar2;
    }

    /* access modifiers changed from: protected */
    /* renamed from: a */
    public final void mo43839a() {
        try {
            this.f1436c.f1449a.mo44100b().mo44124e(this.f1436c.f1450d, C3123az.m987b((Collection) this.f1434a), C3123az.m986b(), new C3117at(this.f1436c, this.f1435b));
        } catch (RemoteException e) {
            C3123az.f1447b.mo44088a((Throwable) e, "deferredLanguageUninstall(%s)", this.f1434a);
            this.f1435b.mo44331b((Exception) new RuntimeException(e));
        }
    }
}
