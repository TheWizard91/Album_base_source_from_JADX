package com.google.android.play.core.tasks;

import java.util.concurrent.Executor;

/* renamed from: com.google.android.play.core.tasks.d */
final class C3164d<ResultT> implements C3167g<ResultT> {

    /* renamed from: a */
    private final Executor f1533a;
    /* access modifiers changed from: private */

    /* renamed from: b */
    public final Object f1534b = new Object();
    /* access modifiers changed from: private */

    /* renamed from: c */
    public final OnFailureListener f1535c;

    public C3164d(Executor executor, OnFailureListener onFailureListener) {
        this.f1533a = executor;
        this.f1535c = onFailureListener;
    }

    /* renamed from: a */
    public final void mo44323a(Task<ResultT> task) {
        if (!task.isSuccessful()) {
            synchronized (this.f1534b) {
                if (this.f1535c != null) {
                    this.f1533a.execute(new C3163c(this, task));
                }
            }
        }
    }
}
