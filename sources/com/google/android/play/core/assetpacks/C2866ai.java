package com.google.android.play.core.assetpacks;

import android.os.RemoteException;
import com.google.android.play.core.internal.C2990ab;
import com.google.android.play.core.internal.C3067s;
import com.google.android.play.core.tasks.C3169i;

/* renamed from: com.google.android.play.core.assetpacks.ai */
final class C2866ai extends C2990ab {

    /* renamed from: a */
    final /* synthetic */ int f912a;

    /* renamed from: b */
    final /* synthetic */ String f913b;

    /* renamed from: c */
    final /* synthetic */ String f914c;

    /* renamed from: d */
    final /* synthetic */ int f915d;

    /* renamed from: e */
    final /* synthetic */ C3169i f916e;

    /* renamed from: f */
    final /* synthetic */ C2875ar f917f;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    C2866ai(C2875ar arVar, C3169i iVar, int i, String str, String str2, int i2, C3169i iVar2) {
        super(iVar);
        this.f917f = arVar;
        this.f912a = i;
        this.f913b = str;
        this.f914c = str2;
        this.f915d = i2;
        this.f916e = iVar2;
    }

    /* access modifiers changed from: protected */
    /* renamed from: a */
    public final void mo43839a() {
        try {
            ((C3067s) this.f917f.f936e.mo44100b()).mo44187d(this.f917f.f934c, C2875ar.m353c(this.f912a, this.f913b, this.f914c, this.f915d), C2875ar.m350c(), new C2869al(this.f917f, this.f916e));
        } catch (RemoteException e) {
            C2875ar.f932a.mo44089b("getChunkFileDescriptor(%s, %s, %d, session=%d)", this.f913b, this.f914c, Integer.valueOf(this.f915d), Integer.valueOf(this.f912a));
            this.f916e.mo44331b((Exception) new RuntimeException(e));
        }
    }
}
