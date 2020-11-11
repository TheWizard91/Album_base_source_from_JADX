package com.google.android.play.core.internal;

import com.google.android.play.core.tasks.C3169i;

/* renamed from: com.google.android.play.core.internal.ab */
public abstract class C2990ab implements Runnable {

    /* renamed from: a */
    private final C3169i<?> f1276a;

    C2990ab() {
        this.f1276a = null;
    }

    public C2990ab(C3169i<?> iVar) {
        this.f1276a = iVar;
    }

    /* access modifiers changed from: protected */
    /* renamed from: a */
    public abstract void mo43839a();

    /* access modifiers changed from: package-private */
    /* renamed from: b */
    public final C3169i<?> mo44092b() {
        return this.f1276a;
    }

    public final void run() {
        try {
            mo43839a();
        } catch (Exception e) {
            C3169i<?> iVar = this.f1276a;
            if (iVar != null) {
                iVar.mo44331b(e);
            }
        }
    }
}
