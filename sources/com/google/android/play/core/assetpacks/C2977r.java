package com.google.android.play.core.assetpacks;

import android.content.Context;
import com.google.android.play.core.internal.C3027bl;
import com.google.android.play.core.internal.C3051ci;

/* renamed from: com.google.android.play.core.assetpacks.r */
public final class C2977r implements C3051ci<Context> {

    /* renamed from: a */
    private final C2971m f1258a;

    public C2977r(C2971m mVar) {
        this.f1258a = mVar;
    }

    /* renamed from: a */
    public static Context m591a(C2971m mVar) {
        Context a = mVar.mo44072a();
        C3027bl.m719a(a, "Cannot return null from a non-@Nullable @Provides method");
        return a;
    }

    /* renamed from: b */
    public final Context mo43928a() {
        return m591a(this.f1258a);
    }
}
