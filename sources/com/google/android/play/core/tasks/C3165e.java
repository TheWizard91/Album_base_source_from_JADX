package com.google.android.play.core.tasks;

/* renamed from: com.google.android.play.core.tasks.e */
final class C3165e implements Runnable {

    /* renamed from: a */
    final /* synthetic */ Task f1536a;

    /* renamed from: b */
    final /* synthetic */ C3166f f1537b;

    C3165e(C3166f fVar, Task task) {
        this.f1537b = fVar;
        this.f1536a = task;
    }

    public final void run() {
        synchronized (this.f1537b.f1539b) {
            if (this.f1537b.f1540c != null) {
                this.f1537b.f1540c.onSuccess(this.f1536a.getResult());
            }
        }
    }
}
