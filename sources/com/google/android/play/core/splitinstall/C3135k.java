package com.google.android.play.core.splitinstall;

import com.google.android.play.core.tasks.Task;
import com.google.android.play.core.tasks.Tasks;

/* renamed from: com.google.android.play.core.splitinstall.k */
final /* synthetic */ class C3135k implements C3145t {

    /* renamed from: a */
    private final SplitInstallStateUpdatedListener f1462a;

    C3135k(SplitInstallStateUpdatedListener splitInstallStateUpdatedListener) {
        this.f1462a = splitInstallStateUpdatedListener;
    }

    /* renamed from: a */
    public final Task mo44296a(SplitInstallManager splitInstallManager) {
        splitInstallManager.unregisterListener(this.f1462a);
        return Tasks.m1067a(null);
    }
}
