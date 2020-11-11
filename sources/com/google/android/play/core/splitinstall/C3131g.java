package com.google.android.play.core.splitinstall;

import com.google.android.play.core.tasks.Task;
import com.google.android.play.core.tasks.Tasks;

/* renamed from: com.google.android.play.core.splitinstall.g */
final /* synthetic */ class C3131g implements C3145t {

    /* renamed from: a */
    private final SplitInstallStateUpdatedListener f1456a;

    C3131g(SplitInstallStateUpdatedListener splitInstallStateUpdatedListener) {
        this.f1456a = splitInstallStateUpdatedListener;
    }

    /* renamed from: a */
    public final Task mo44296a(SplitInstallManager splitInstallManager) {
        splitInstallManager.registerListener(this.f1456a);
        return Tasks.m1067a(null);
    }
}
