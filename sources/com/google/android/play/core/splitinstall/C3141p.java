package com.google.android.play.core.splitinstall;

import com.google.android.play.core.tasks.Task;
import java.util.List;

/* renamed from: com.google.android.play.core.splitinstall.p */
final /* synthetic */ class C3141p implements C3145t {

    /* renamed from: a */
    private final List f1469a;

    C3141p(List list) {
        this.f1469a = list;
    }

    /* renamed from: a */
    public final Task mo44296a(SplitInstallManager splitInstallManager) {
        return splitInstallManager.deferredUninstall(this.f1469a);
    }
}
