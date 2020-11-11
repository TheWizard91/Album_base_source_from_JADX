package com.google.android.play.core.internal;

/* renamed from: com.google.android.play.core.internal.cf */
public final class C3048cf<T> implements C3051ci<T> {

    /* renamed from: a */
    private C3051ci<T> f1324a;

    /* renamed from: a */
    public static <T> void m770a(C3051ci<T> ciVar, C3051ci<T> ciVar2) {
        C3027bl.m718a(ciVar2);
        C3048cf cfVar = (C3048cf) ciVar;
        if (cfVar.f1324a == null) {
            cfVar.f1324a = ciVar2;
            return;
        }
        throw new IllegalStateException();
    }

    /* renamed from: a */
    public final T mo43928a() {
        C3051ci<T> ciVar = this.f1324a;
        if (ciVar != null) {
            return ciVar.mo43928a();
        }
        throw new IllegalStateException();
    }
}
