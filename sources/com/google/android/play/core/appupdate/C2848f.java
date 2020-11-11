package com.google.android.play.core.appupdate;

import android.os.RemoteException;
import com.google.android.play.core.internal.C2990ab;
import com.google.android.play.core.tasks.C3169i;

/* renamed from: com.google.android.play.core.appupdate.f */
final class C2848f extends C2990ab {

    /* renamed from: a */
    final /* synthetic */ String f824a;

    /* renamed from: b */
    final /* synthetic */ C3169i f825b;

    /* renamed from: c */
    final /* synthetic */ C2853k f826c;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    C2848f(C2853k kVar, C3169i iVar, String str, C3169i iVar2) {
        super(iVar);
        this.f826c = kVar;
        this.f824a = str;
        this.f825b = iVar2;
    }

    /* access modifiers changed from: protected */
    /* renamed from: a */
    public final void mo43839a() {
        try {
            this.f826c.f837a.mo44100b().mo44177a(this.f826c.f838d, C2853k.m277a(this.f826c, this.f824a), new C2852j(this.f826c, this.f825b, this.f824a));
        } catch (RemoteException e) {
            C2853k.f835b.mo44088a((Throwable) e, "requestUpdateInfo(%s)", this.f824a);
            this.f825b.mo44331b((Exception) new RuntimeException(e));
        }
    }
}
