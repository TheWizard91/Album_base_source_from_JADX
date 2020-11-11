package com.google.android.play.core.assetpacks;

import android.os.Bundle;
import com.google.android.play.core.tasks.C3169i;

/* renamed from: com.google.android.play.core.assetpacks.ao */
final class C2872ao extends C2868ak<Void> {

    /* renamed from: c */
    final int f924c;

    /* renamed from: d */
    final String f925d;

    /* renamed from: e */
    final int f926e;

    /* renamed from: f */
    final /* synthetic */ C2875ar f927f;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    C2872ao(C2875ar arVar, C3169i<Void> iVar, int i, String str, int i2) {
        super(arVar, iVar);
        this.f927f = arVar;
        this.f924c = i;
        this.f925d = str;
        this.f926e = i2;
    }

    /* renamed from: a */
    public final void mo43908a(Bundle bundle) {
        this.f927f.f936e.mo44098a();
        int i = bundle.getInt("error_code");
        C2875ar.f932a.mo44089b("onError(%d), retrying notifyModuleCompleted...", Integer.valueOf(i));
        int i2 = this.f926e;
        if (i2 > 0) {
            this.f927f.m345a(this.f924c, this.f925d, i2 - 1);
        }
    }
}
