package com.google.android.play.core.internal;

/* renamed from: com.google.android.play.core.internal.ca */
final class C3043ca extends C3039bx {

    /* renamed from: a */
    private final C3041bz f1322a = new C3041bz();

    C3043ca() {
    }

    /* renamed from: a */
    public final void mo44141a(Throwable th, Throwable th2) {
        if (th2 == th) {
            throw new IllegalArgumentException("Self suppression is not allowed.", th2);
        } else if (th2 != null) {
            this.f1322a.mo44144a(th).add(th2);
        } else {
            throw new NullPointerException("The suppressed exception cannot be null.");
        }
    }
}
