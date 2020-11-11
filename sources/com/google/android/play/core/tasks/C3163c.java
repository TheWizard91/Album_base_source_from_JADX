package com.google.android.play.core.tasks;

/* renamed from: com.google.android.play.core.tasks.c */
final class C3163c implements Runnable {

    /* renamed from: a */
    final /* synthetic */ Task f1531a;

    /* renamed from: b */
    final /* synthetic */ C3164d f1532b;

    C3163c(C3164d dVar, Task task) {
        this.f1532b = dVar;
        this.f1531a = task;
    }

    public final void run() {
        synchronized (this.f1532b.f1534b) {
            if (this.f1532b.f1535c != null) {
                this.f1532b.f1535c.onFailure(this.f1531a.getException());
            }
        }
    }
}
