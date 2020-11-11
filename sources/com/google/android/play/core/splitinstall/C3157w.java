package com.google.android.play.core.splitinstall;

import java.util.concurrent.atomic.AtomicReference;

/* renamed from: com.google.android.play.core.splitinstall.w */
public enum C3157w implements C3127c {
    ;
    

    /* renamed from: b */
    private static final AtomicReference<C3128d> f1518b = null;

    static {
        C3157w wVar = new C3157w("INSTANCE");
        f1517a = wVar;
        new C3157w[1][0] = wVar;
        f1518b = new AtomicReference<>((Object) null);
    }

    private C3157w(String str) {
    }

    /* renamed from: a */
    public final C3128d mo44292a() {
        return f1518b.get();
    }

    /* renamed from: a */
    public final void mo44306a(C3128d dVar) {
        f1518b.compareAndSet((Object) null, dVar);
    }
}
