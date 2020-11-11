package com.google.android.play.core.splitinstall.testing;

import com.google.android.play.core.splitinstall.SplitInstallSessionState;

/* renamed from: com.google.android.play.core.splitinstall.testing.f */
final class C3151f implements Runnable {

    /* renamed from: a */
    final /* synthetic */ SplitInstallSessionState f1500a;

    /* renamed from: b */
    final /* synthetic */ FakeSplitInstallManager f1501b;

    C3151f(FakeSplitInstallManager fakeSplitInstallManager, SplitInstallSessionState splitInstallSessionState) {
        this.f1501b = fakeSplitInstallManager;
        this.f1500a = splitInstallSessionState;
    }

    public final void run() {
        this.f1501b.f1479g.mo44193a(this.f1500a);
    }
}
