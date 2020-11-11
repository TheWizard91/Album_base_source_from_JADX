package com.google.android.play.core.internal;

/* renamed from: com.google.android.play.core.internal.cg */
public final class C3049cg<T> implements C3051ci, C3047ce {

    /* renamed from: a */
    private static final Object f1325a = new Object();

    /* renamed from: b */
    private volatile C3051ci<T> f1326b;

    /* renamed from: c */
    private volatile Object f1327c = f1325a;

    private C3049cg(C3051ci<T> ciVar) {
        this.f1326b = ciVar;
    }

    /* renamed from: a */
    public static <P extends C3051ci<T>, T> C3051ci<T> m772a(P p) {
        C3027bl.m718a(p);
        return !(p instanceof C3049cg) ? new C3049cg(p) : p;
    }

    /* renamed from: b */
    public static <P extends C3051ci<T>, T> C3047ce<T> m773b(P p) {
        if (p instanceof C3047ce) {
            return (C3047ce) p;
        }
        C3027bl.m718a(p);
        return new C3049cg(p);
    }

    /* renamed from: a */
    public final T mo43928a() {
        T t = this.f1327c;
        T t2 = f1325a;
        if (t == t2) {
            synchronized (this) {
                t = this.f1327c;
                if (t == t2) {
                    t = this.f1326b.mo43928a();
                    T t3 = this.f1327c;
                    if (t3 != t2) {
                        if (!(t3 instanceof C3050ch)) {
                            if (t3 != t) {
                                String valueOf = String.valueOf(t3);
                                String valueOf2 = String.valueOf(t);
                                StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 118 + String.valueOf(valueOf2).length());
                                sb.append("Scoped provider was invoked recursively returning different results: ");
                                sb.append(valueOf);
                                sb.append(" & ");
                                sb.append(valueOf2);
                                sb.append(". This is likely due to a circular dependency.");
                                throw new IllegalStateException(sb.toString());
                            }
                        }
                    }
                    this.f1327c = t;
                    this.f1326b = null;
                }
            }
        }
        return t;
    }
}
