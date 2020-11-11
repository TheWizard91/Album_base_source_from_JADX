package com.google.android.play.core.internal;

import android.os.IBinder;

/* renamed from: com.google.android.play.core.internal.ac */
final /* synthetic */ class C2991ac implements IBinder.DeathRecipient {

    /* renamed from: a */
    private final C2999ak f1277a;

    C2991ac(C2999ak akVar) {
        this.f1277a = akVar;
    }

    public final void binderDied() {
        this.f1277a.mo44101c();
    }
}
