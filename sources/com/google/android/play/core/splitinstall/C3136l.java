package com.google.android.play.core.splitinstall;

import com.google.android.play.core.tasks.Task;

/* renamed from: com.google.android.play.core.splitinstall.l */
final /* synthetic */ class C3136l implements C3145t {

    /* renamed from: a */
    private final SplitInstallRequest f1463a;

    C3136l(SplitInstallRequest splitInstallRequest) {
        this.f1463a = splitInstallRequest;
    }

    /* renamed from: a */
    public final Task mo44296a(SplitInstallManager splitInstallManager) {
        return splitInstallManager.startInstall(this.f1463a);
    }
}
