package com.google.android.play.core.splitinstall;

import com.google.android.play.core.tasks.C3169i;
import com.google.android.play.core.tasks.OnCompleteListener;
import com.google.android.play.core.tasks.Task;

/* renamed from: com.google.android.play.core.splitinstall.h */
final /* synthetic */ class C3132h implements OnCompleteListener {

    /* renamed from: a */
    private final C3145t f1457a;

    /* renamed from: b */
    private final C3169i f1458b;

    C3132h(C3145t tVar, C3169i iVar) {
        this.f1457a = tVar;
        this.f1458b = iVar;
    }

    public final void onComplete(Task task) {
        C3145t tVar = this.f1457a;
        C3169i iVar = this.f1458b;
        if (task.isSuccessful()) {
            tVar.mo44296a((SplitInstallManager) task.getResult()).addOnCompleteListener(new C3134j(iVar));
        } else {
            iVar.mo44329a(task.getException());
        }
    }
}
