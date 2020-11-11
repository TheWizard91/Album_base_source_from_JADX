package com.google.android.play.core.splitinstall;

import com.google.android.play.core.tasks.Task;
import java.util.List;

/* renamed from: com.google.android.play.core.splitinstall.s */
final /* synthetic */ class C3144s implements C3145t {

    /* renamed from: a */
    private final List f1472a;

    C3144s(List list) {
        this.f1472a = list;
    }

    /* renamed from: a */
    public final Task mo44296a(SplitInstallManager splitInstallManager) {
        return splitInstallManager.deferredLanguageUninstall(this.f1472a);
    }
}
