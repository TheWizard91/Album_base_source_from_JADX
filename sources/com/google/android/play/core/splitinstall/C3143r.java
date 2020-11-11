package com.google.android.play.core.splitinstall;

import com.google.android.play.core.tasks.Task;
import java.util.List;

/* renamed from: com.google.android.play.core.splitinstall.r */
final /* synthetic */ class C3143r implements C3145t {

    /* renamed from: a */
    private final List f1471a;

    C3143r(List list) {
        this.f1471a = list;
    }

    /* renamed from: a */
    public final Task mo44296a(SplitInstallManager splitInstallManager) {
        return splitInstallManager.deferredLanguageInstall(this.f1471a);
    }
}
