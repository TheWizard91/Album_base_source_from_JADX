package com.google.android.play.core.tasks;

/* renamed from: com.google.android.play.core.tasks.a */
final class C3161a implements Runnable {

    /* renamed from: a */
    final /* synthetic */ Task f1526a;

    /* renamed from: b */
    final /* synthetic */ C3162b f1527b;

    C3161a(C3162b bVar, Task task) {
        this.f1527b = bVar;
        this.f1526a = task;
    }

    public final void run() {
        synchronized (this.f1527b.f1529b) {
            if (this.f1527b.f1530c != null) {
                this.f1527b.f1530c.onComplete(this.f1526a);
            }
        }
    }
}
