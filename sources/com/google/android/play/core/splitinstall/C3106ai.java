package com.google.android.play.core.splitinstall;

import android.os.Bundle;
import android.os.RemoteException;
import com.google.android.play.core.internal.C2990ab;
import com.google.android.play.core.internal.C3033br;
import com.google.android.play.core.tasks.C3169i;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/* renamed from: com.google.android.play.core.splitinstall.ai */
final class C3106ai extends C2990ab {

    /* renamed from: a */
    final /* synthetic */ Collection f1421a;

    /* renamed from: b */
    final /* synthetic */ Collection f1422b;

    /* renamed from: c */
    final /* synthetic */ C3169i f1423c;

    /* renamed from: d */
    final /* synthetic */ C3123az f1424d;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    C3106ai(C3123az azVar, C3169i iVar, Collection collection, Collection collection2, C3169i iVar2) {
        super(iVar);
        this.f1424d = azVar;
        this.f1421a = collection;
        this.f1422b = collection2;
        this.f1423c = iVar2;
    }

    /* access modifiers changed from: protected */
    /* renamed from: a */
    public final void mo43839a() {
        ArrayList a = C3123az.m985a(this.f1421a);
        a.addAll(C3123az.m987b(this.f1422b));
        try {
            this.f1424d.f1449a.mo44100b().mo44120a(this.f1424d.f1450d, (List<Bundle>) a, C3123az.m986b(), (C3033br) new C3121ax(this.f1424d, this.f1423c));
        } catch (RemoteException e) {
            C3123az.f1447b.mo44088a((Throwable) e, "startInstall(%s,%s)", this.f1421a, this.f1422b);
            this.f1423c.mo44331b((Exception) new RuntimeException(e));
        }
    }
}
