package com.google.android.play.core.assetpacks;

import android.content.Context;
import com.google.android.play.core.internal.C3027bl;
import com.google.android.play.core.internal.C3051ci;
import com.google.android.play.core.splitinstall.C3160z;

/* renamed from: com.google.android.play.core.assetpacks.t */
public final class C2979t implements C3051ci<C3160z> {

    /* renamed from: a */
    private final C3051ci<Context> f1260a;

    public C2979t(C3051ci<Context> ciVar) {
        this.f1260a = ciVar;
    }

    /* renamed from: a */
    public final /* bridge */ /* synthetic */ Object mo43928a() {
        Context b = ((C2977r) this.f1260a).mo43928a();
        C3160z zVar = new C3160z(b, b.getPackageName());
        C3027bl.m719a(zVar, "Cannot return null from a non-@Nullable @Provides method");
        return zVar;
    }
}
