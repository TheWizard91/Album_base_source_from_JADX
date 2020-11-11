package com.google.android.play.core.appupdate;

import android.os.RemoteException;
import com.google.android.play.core.internal.C2990ab;
import com.google.android.play.core.tasks.C3169i;

/* renamed from: com.google.android.play.core.appupdate.g */
final class C2849g extends C2990ab {

    /* renamed from: a */
    final /* synthetic */ C3169i f827a;

    /* renamed from: b */
    final /* synthetic */ String f828b;

    /* renamed from: c */
    final /* synthetic */ C2853k f829c;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    C2849g(C2853k kVar, C3169i iVar, C3169i iVar2, String str) {
        super(iVar);
        this.f829c = kVar;
        this.f827a = iVar2;
        this.f828b = str;
    }

    /* access modifiers changed from: protected */
    /* renamed from: a */
    public final void mo43839a() {
        try {
            this.f829c.f837a.mo44100b().mo44178b(this.f829c.f838d, C2853k.m283d(), new C2851i(this.f829c, this.f827a));
        } catch (RemoteException e) {
            C2853k.f835b.mo44088a((Throwable) e, "completeUpdate(%s)", this.f828b);
            this.f827a.mo44331b((Exception) new RuntimeException(e));
        }
    }
}
