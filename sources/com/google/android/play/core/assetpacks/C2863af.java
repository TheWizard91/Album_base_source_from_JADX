package com.google.android.play.core.assetpacks;

import android.os.RemoteException;
import com.google.android.play.core.internal.C2990ab;
import com.google.android.play.core.internal.C3067s;
import com.google.android.play.core.internal.C3069u;
import com.google.android.play.core.tasks.C3169i;

/* renamed from: com.google.android.play.core.assetpacks.af */
final class C2863af extends C2990ab {

    /* renamed from: a */
    final /* synthetic */ int f898a;

    /* renamed from: b */
    final /* synthetic */ String f899b;

    /* renamed from: c */
    final /* synthetic */ String f900c;

    /* renamed from: d */
    final /* synthetic */ int f901d;

    /* renamed from: e */
    final /* synthetic */ C3169i f902e;

    /* renamed from: f */
    final /* synthetic */ C2875ar f903f;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    C2863af(C2875ar arVar, C3169i iVar, int i, String str, String str2, int i2, C3169i iVar2) {
        super(iVar);
        this.f903f = arVar;
        this.f898a = i;
        this.f899b = str;
        this.f900c = str2;
        this.f901d = i2;
        this.f902e = iVar2;
    }

    /* access modifiers changed from: protected */
    /* renamed from: a */
    public final void mo43839a() {
        try {
            ((C3067s) this.f903f.f936e.mo44100b()).mo44179a(this.f903f.f934c, C2875ar.m353c(this.f898a, this.f899b, this.f900c, this.f901d), C2875ar.m350c(), (C3069u) new C2868ak(this.f903f, this.f902e, (char[]) null));
        } catch (RemoteException e) {
            C2875ar.f932a.mo44088a((Throwable) e, "notifyChunkTransferred", new Object[0]);
        }
    }
}
