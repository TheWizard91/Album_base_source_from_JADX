package com.google.android.play.core.splitinstall.testing;

import java.util.List;

/* renamed from: com.google.android.play.core.splitinstall.testing.c */
final class C3148c implements Runnable {

    /* renamed from: a */
    final /* synthetic */ List f1495a;

    /* renamed from: b */
    final /* synthetic */ List f1496b;

    /* renamed from: c */
    final /* synthetic */ FakeSplitInstallManager f1497c;

    C3148c(FakeSplitInstallManager fakeSplitInstallManager, List list, List list2) {
        this.f1497c = fakeSplitInstallManager;
        this.f1495a = list;
        this.f1496b = list2;
    }

    public final void run() {
        FakeSplitInstallManager.m1030a(this.f1497c, this.f1495a, this.f1496b);
    }
}
