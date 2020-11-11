package com.google.android.play.core.assetpacks;

import com.google.android.play.core.internal.C3049cg;
import com.google.android.play.core.internal.C3051ci;
import java.util.concurrent.Executor;

/* renamed from: com.google.android.play.core.assetpacks.cs */
public final class C2930cs implements C3051ci<C2929cr> {

    /* renamed from: a */
    private final C3051ci<C2886bb> f1124a;

    /* renamed from: b */
    private final C3051ci<C2982w> f1125b;

    /* renamed from: c */
    private final C3051ci<C2913cb> f1126c;

    /* renamed from: d */
    private final C3051ci<Executor> f1127d;

    public C2930cs(C3051ci<C2886bb> ciVar, C3051ci<C2982w> ciVar2, C3051ci<C2913cb> ciVar3, C3051ci<Executor> ciVar4) {
        this.f1124a = ciVar;
        this.f1125b = ciVar2;
        this.f1126c = ciVar3;
        this.f1127d = ciVar4;
    }

    /* renamed from: a */
    public final /* bridge */ /* synthetic */ Object mo43928a() {
        C2886bb a = this.f1124a.mo43928a();
        return new C2929cr(a, C3049cg.m773b(this.f1125b), this.f1126c.mo43928a(), C3049cg.m773b(this.f1127d));
    }
}
