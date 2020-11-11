package com.google.android.play.core.tasks;

import java.util.concurrent.Executor;

/* renamed from: com.google.android.play.core.tasks.b */
final class C3162b<ResultT> implements C3167g<ResultT> {

    /* renamed from: a */
    private final Executor f1528a;
    /* access modifiers changed from: private */

    /* renamed from: b */
    public final Object f1529b = new Object();
    /* access modifiers changed from: private */

    /* renamed from: c */
    public final OnCompleteListener<ResultT> f1530c;

    public C3162b(Executor executor, OnCompleteListener<ResultT> onCompleteListener) {
        this.f1528a = executor;
        this.f1530c = onCompleteListener;
    }

    /* renamed from: a */
    public final void mo44323a(Task<ResultT> task) {
        synchronized (this.f1529b) {
            if (this.f1530c != null) {
                this.f1528a.execute(new C3161a(this, task));
            }
        }
    }
}
