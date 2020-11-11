package com.google.android.play.core.splitinstall;

import com.google.android.play.core.tasks.Task;

/* renamed from: com.google.android.play.core.splitinstall.m */
final /* synthetic */ class C3137m implements C3145t {

    /* renamed from: a */
    private final int f1464a;

    C3137m(int i) {
        this.f1464a = i;
    }

    /* renamed from: a */
    public final Task mo44296a(SplitInstallManager splitInstallManager) {
        return splitInstallManager.cancelInstall(this.f1464a);
    }
}
