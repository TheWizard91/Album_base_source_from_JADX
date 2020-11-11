package com.google.android.play.core.tasks;

import com.google.android.play.core.splitcompat.C3082d;
import java.util.concurrent.Executor;

/* renamed from: com.google.android.play.core.tasks.m */
final class C3173m<ResultT> extends Task<ResultT> {

    /* renamed from: a */
    private final Object f1546a = new Object();

    /* renamed from: b */
    private final C3168h<ResultT> f1547b = new C3168h<>();

    /* renamed from: c */
    private boolean f1548c;

    /* renamed from: d */
    private ResultT f1549d;

    /* renamed from: e */
    private Exception f1550e;

    C3173m() {
    }

    /* renamed from: a */
    private final void m1087a() {
        C3082d.m897a(this.f1548c, (Object) "Task is not yet complete");
    }

    /* renamed from: b */
    private final void m1088b() {
        C3082d.m897a(!this.f1548c, (Object) "Task is already complete");
    }

    /* renamed from: c */
    private final void m1089c() {
        synchronized (this.f1546a) {
            if (this.f1548c) {
                this.f1547b.mo44326a(this);
            }
        }
    }

    /* renamed from: a */
    public final void mo44335a(Exception exc) {
        C3082d.m896a(exc, (Object) "Exception must not be null");
        synchronized (this.f1546a) {
            m1088b();
            this.f1548c = true;
            this.f1550e = exc;
        }
        this.f1547b.mo44326a(this);
    }

    /* renamed from: a */
    public final void mo44336a(ResultT resultt) {
        synchronized (this.f1546a) {
            m1088b();
            this.f1548c = true;
            this.f1549d = resultt;
        }
        this.f1547b.mo44326a(this);
    }

    public final Task<ResultT> addOnCompleteListener(OnCompleteListener<ResultT> onCompleteListener) {
        this.f1547b.mo44327a(new C3162b(TaskExecutors.MAIN_THREAD, onCompleteListener));
        m1089c();
        return this;
    }

    public final Task<ResultT> addOnCompleteListener(Executor executor, OnCompleteListener<ResultT> onCompleteListener) {
        this.f1547b.mo44327a(new C3162b(executor, onCompleteListener));
        m1089c();
        return this;
    }

    public final Task<ResultT> addOnFailureListener(OnFailureListener onFailureListener) {
        addOnFailureListener(TaskExecutors.MAIN_THREAD, onFailureListener);
        return this;
    }

    public final Task<ResultT> addOnFailureListener(Executor executor, OnFailureListener onFailureListener) {
        this.f1547b.mo44327a(new C3164d(executor, onFailureListener));
        m1089c();
        return this;
    }

    public final Task<ResultT> addOnSuccessListener(OnSuccessListener<? super ResultT> onSuccessListener) {
        addOnSuccessListener(TaskExecutors.MAIN_THREAD, onSuccessListener);
        return this;
    }

    public final Task<ResultT> addOnSuccessListener(Executor executor, OnSuccessListener<? super ResultT> onSuccessListener) {
        this.f1547b.mo44327a(new C3166f(executor, onSuccessListener));
        m1089c();
        return this;
    }

    /* renamed from: b */
    public final boolean mo44337b(Exception exc) {
        C3082d.m896a(exc, (Object) "Exception must not be null");
        synchronized (this.f1546a) {
            if (this.f1548c) {
                return false;
            }
            this.f1548c = true;
            this.f1550e = exc;
            this.f1547b.mo44326a(this);
            return true;
        }
    }

    /* renamed from: b */
    public final boolean mo44338b(ResultT resultt) {
        synchronized (this.f1546a) {
            if (this.f1548c) {
                return false;
            }
            this.f1548c = true;
            this.f1549d = resultt;
            this.f1547b.mo44326a(this);
            return true;
        }
    }

    public final Exception getException() {
        Exception exc;
        synchronized (this.f1546a) {
            exc = this.f1550e;
        }
        return exc;
    }

    public final ResultT getResult() {
        ResultT resultt;
        synchronized (this.f1546a) {
            m1087a();
            Exception exc = this.f1550e;
            if (exc == null) {
                resultt = this.f1549d;
            } else {
                throw new RuntimeExecutionException(exc);
            }
        }
        return resultt;
    }

    public final <X extends Throwable> ResultT getResult(Class<X> cls) throws Throwable {
        ResultT resultt;
        synchronized (this.f1546a) {
            m1087a();
            if (!cls.isInstance(this.f1550e)) {
                Exception exc = this.f1550e;
                if (exc == null) {
                    resultt = this.f1549d;
                } else {
                    throw new RuntimeExecutionException(exc);
                }
            } else {
                throw ((Throwable) cls.cast(this.f1550e));
            }
        }
        return resultt;
    }

    public final boolean isComplete() {
        boolean z;
        synchronized (this.f1546a) {
            z = this.f1548c;
        }
        return z;
    }

    public final boolean isSuccessful() {
        boolean z;
        synchronized (this.f1546a) {
            z = false;
            if (this.f1548c && this.f1550e == null) {
                z = true;
            }
        }
        return z;
    }
}
