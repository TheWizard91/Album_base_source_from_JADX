package com.google.android.play.core.splitinstall;

import java.util.concurrent.atomic.AtomicReference;

/* renamed from: com.google.android.play.core.splitinstall.y */
public final class C3159y {

    /* renamed from: a */
    private static final AtomicReference<C3158x> f1519a = new AtomicReference<>((Object) null);

    /* renamed from: a */
    static C3158x m1057a() {
        return f1519a.get();
    }

    /* renamed from: a */
    public static void m1058a(C3158x xVar) {
        f1519a.compareAndSet((Object) null, xVar);
    }
}
