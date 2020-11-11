package com.google.android.play.core.splitinstall;

import com.google.android.play.core.tasks.C3169i;
import com.google.android.play.core.tasks.OnCompleteListener;
import com.google.android.play.core.tasks.Task;

/* renamed from: com.google.android.play.core.splitinstall.j */
final /* synthetic */ class C3134j implements OnCompleteListener {

    /* renamed from: a */
    private final C3169i f1461a;

    C3134j(C3169i iVar) {
        this.f1461a = iVar;
    }

    public final void onComplete(Task task) {
        C3169i iVar = this.f1461a;
        if (task.isSuccessful()) {
            iVar.mo44330a(task.getResult());
        } else {
            iVar.mo44329a(task.getException());
        }
    }
}
