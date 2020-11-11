package com.google.android.play.core.splitinstall;

import com.google.android.play.core.tasks.Task;
import java.util.List;

/* renamed from: com.google.android.play.core.splitinstall.q */
final /* synthetic */ class C3142q implements C3145t {

    /* renamed from: a */
    private final List f1470a;

    C3142q(List list) {
        this.f1470a = list;
    }

    /* renamed from: a */
    public final Task mo44296a(SplitInstallManager splitInstallManager) {
        return splitInstallManager.deferredInstall(this.f1470a);
    }
}
