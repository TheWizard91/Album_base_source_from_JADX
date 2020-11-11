package com.google.android.play.core.splitinstall;

import android.os.RemoteException;
import com.google.android.play.core.internal.C2990ab;
import com.google.android.play.core.internal.C3033br;
import com.google.android.play.core.tasks.C3169i;

/* renamed from: com.google.android.play.core.splitinstall.ap */
final class C3113ap extends C2990ab {

    /* renamed from: a */
    final /* synthetic */ int f1442a;

    /* renamed from: b */
    final /* synthetic */ C3169i f1443b;

    /* renamed from: c */
    final /* synthetic */ C3123az f1444c;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    C3113ap(C3123az azVar, C3169i iVar, int i, C3169i iVar2) {
        super(iVar);
        this.f1444c = azVar;
        this.f1442a = i;
        this.f1443b = iVar2;
    }

    /* access modifiers changed from: protected */
    /* renamed from: a */
    public final void mo43839a() {
        try {
            this.f1444c.f1449a.mo44100b().mo44117a(this.f1444c.f1450d, this.f1442a, C3123az.m986b(), (C3033br) new C3114aq(this.f1444c, this.f1443b));
        } catch (RemoteException e) {
            C3123az.f1447b.mo44088a((Throwable) e, "cancelInstall(%d)", Integer.valueOf(this.f1442a));
            this.f1443b.mo44331b((Exception) new RuntimeException(e));
        }
    }
}
