package com.google.android.play.core.splitinstall;

import android.os.RemoteException;
import com.google.android.play.core.internal.C2990ab;
import com.google.android.play.core.tasks.C3169i;
import java.util.Collection;
import java.util.List;

/* renamed from: com.google.android.play.core.splitinstall.al */
final class C3109al extends C2990ab {

    /* renamed from: a */
    final /* synthetic */ List f1431a;

    /* renamed from: b */
    final /* synthetic */ C3169i f1432b;

    /* renamed from: c */
    final /* synthetic */ C3123az f1433c;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    C3109al(C3123az azVar, C3169i iVar, List list, C3169i iVar2) {
        super(iVar);
        this.f1433c = azVar;
        this.f1431a = list;
        this.f1432b = iVar2;
    }

    /* access modifiers changed from: protected */
    /* renamed from: a */
    public final void mo43839a() {
        try {
            this.f1433c.f1449a.mo44100b().mo44123d(this.f1433c.f1450d, C3123az.m987b((Collection) this.f1431a), C3123az.m986b(), new C3116as(this.f1433c, this.f1432b));
        } catch (RemoteException e) {
            C3123az.f1447b.mo44088a((Throwable) e, "deferredLanguageInstall(%s)", this.f1431a);
            this.f1432b.mo44331b((Exception) new RuntimeException(e));
        }
    }
}
