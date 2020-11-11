package com.google.android.play.core.assetpacks;

import android.content.Context;
import com.google.android.play.core.internal.C3027bl;
import com.google.android.play.core.internal.C3049cg;
import com.google.android.play.core.internal.C3051ci;

/* renamed from: com.google.android.play.core.assetpacks.q */
public final class C2976q implements C3051ci<C2982w> {

    /* renamed from: a */
    private final C3051ci<Context> f1255a;

    /* renamed from: b */
    private final C3051ci<C2875ar> f1256b;

    /* renamed from: c */
    private final C3051ci<C2940db> f1257c;

    public C2976q(C3051ci<Context> ciVar, C3051ci<C2875ar> ciVar2, C3051ci<C2940db> ciVar3) {
        this.f1255a = ciVar;
        this.f1256b = ciVar2;
        this.f1257c = ciVar3;
    }

    /* renamed from: a */
    public final /* bridge */ /* synthetic */ Object mo43928a() {
        C2982w wVar = (C2982w) (C2971m.m583a(((C2977r) this.f1255a).mo43928a()) == null ? C3049cg.m773b(this.f1256b).mo44145a() : C3049cg.m773b(this.f1257c).mo44145a());
        C3027bl.m719a(wVar, "Cannot return null from a non-@Nullable @Provides method");
        return wVar;
    }
}
