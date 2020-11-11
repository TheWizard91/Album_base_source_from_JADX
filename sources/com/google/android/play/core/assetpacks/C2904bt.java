package com.google.android.play.core.assetpacks;

import com.google.android.play.core.internal.C3027bl;

/* renamed from: com.google.android.play.core.assetpacks.bt */
public final class C2904bt {

    /* renamed from: a */
    private C2971m f1017a;

    private C2904bt() {
    }

    /* synthetic */ C2904bt(byte[] bArr) {
    }

    /* renamed from: a */
    public final C2857a mo44009a() {
        C2971m mVar = this.f1017a;
        if (mVar != null) {
            return new C2905bu(mVar);
        }
        throw new IllegalStateException(String.valueOf(C2971m.class.getCanonicalName()).concat(" must be set"));
    }

    /* renamed from: a */
    public final void mo44010a(C2971m mVar) {
        C3027bl.m718a(mVar);
        this.f1017a = mVar;
    }
}
