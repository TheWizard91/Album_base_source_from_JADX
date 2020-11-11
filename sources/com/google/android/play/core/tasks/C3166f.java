package com.google.android.play.core.tasks;

import java.util.concurrent.Executor;

/* renamed from: com.google.android.play.core.tasks.f */
final class C3166f<ResultT> implements C3167g<ResultT> {

    /* renamed from: a */
    private final Executor f1538a;
    /* access modifiers changed from: private */

    /* renamed from: b */
    public final Object f1539b = new Object();
    /* access modifiers changed from: private */

    /* renamed from: c */
    public final OnSuccessListener<? super ResultT> f1540c;

    public C3166f(Executor executor, OnSuccessListener<? super ResultT> onSuccessListener) {
        this.f1538a = executor;
        this.f1540c = onSuccessListener;
    }

    /* renamed from: a */
    public final void mo44323a(Task<ResultT> task) {
        if (task.isSuccessful()) {
            synchronized (this.f1539b) {
                if (this.f1540c != null) {
                    this.f1538a.execute(new C3165e(this, task));
                }
            }
        }
    }
}
