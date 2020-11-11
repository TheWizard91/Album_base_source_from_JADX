package com.google.android.play.core.splitinstall;

import com.google.android.play.core.tasks.C3169i;

/* renamed from: com.google.android.play.core.splitinstall.i */
final /* synthetic */ class C3133i implements Runnable {

    /* renamed from: a */
    private final C3155u f1459a;

    /* renamed from: b */
    private final C3169i f1460b;

    C3133i(C3155u uVar, C3169i iVar) {
        this.f1459a = uVar;
        this.f1460b = iVar;
    }

    public final void run() {
        C3155u uVar = this.f1459a;
        C3169i iVar = this.f1460b;
        try {
            iVar.mo44330a(uVar.mo44305a());
        } catch (Exception e) {
            iVar.mo44329a(e);
        }
    }
}
