package com.google.android.play.core.splitinstall;

import android.content.Context;
import android.content.Intent;

/* renamed from: com.google.android.play.core.splitinstall.aa */
final class C3098aa implements C3124b {

    /* renamed from: a */
    final /* synthetic */ SplitInstallSessionState f1401a;

    /* renamed from: b */
    final /* synthetic */ Intent f1402b;

    /* renamed from: c */
    final /* synthetic */ Context f1403c;

    /* renamed from: d */
    final /* synthetic */ C3100ac f1404d;

    C3098aa(C3100ac acVar, SplitInstallSessionState splitInstallSessionState, Intent intent, Context context) {
        this.f1404d = acVar;
        this.f1401a = splitInstallSessionState;
        this.f1402b = intent;
        this.f1403c = context;
    }

    /* renamed from: a */
    public final void mo44276a() {
        this.f1404d.f1410d.post(new C3099ab(this.f1404d, this.f1401a, 5, 0));
    }

    /* renamed from: a */
    public final void mo44277a(int i) {
        this.f1404d.f1410d.post(new C3099ab(this.f1404d, this.f1401a, 6, i));
    }

    /* renamed from: b */
    public final void mo44278b() {
        if (!this.f1402b.getBooleanExtra("triggered_from_app_after_verification", false)) {
            this.f1402b.putExtra("triggered_from_app_after_verification", true);
            this.f1403c.sendBroadcast(this.f1402b);
            return;
        }
        this.f1404d.f1339a.mo44089b("Splits copied and verified more than once.", new Object[0]);
    }
}
