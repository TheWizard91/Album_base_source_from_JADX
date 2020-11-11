package com.google.android.play.core.assetpacks;

import com.google.android.play.core.internal.C3049cg;
import com.google.android.play.core.internal.C3051ci;
import java.util.concurrent.Executor;

/* renamed from: com.google.android.play.core.assetpacks.dk */
public final class C2949dk implements C3051ci<C2948dj> {

    /* renamed from: a */
    private final C3051ci<C2886bb> f1184a;

    /* renamed from: b */
    private final C3051ci<C2982w> f1185b;

    /* renamed from: c */
    private final C3051ci<C2929cr> f1186c;

    /* renamed from: d */
    private final C3051ci<Executor> f1187d;

    /* renamed from: e */
    private final C3051ci<C2913cb> f1188e;

    public C2949dk(C3051ci<C2886bb> ciVar, C3051ci<C2982w> ciVar2, C3051ci<C2929cr> ciVar3, C3051ci<Executor> ciVar4, C3051ci<C2913cb> ciVar5) {
        this.f1184a = ciVar;
        this.f1185b = ciVar2;
        this.f1186c = ciVar3;
        this.f1187d = ciVar4;
        this.f1188e = ciVar5;
    }

    /* renamed from: a */
    public final /* bridge */ /* synthetic */ Object mo43928a() {
        C2886bb a = this.f1184a.mo43928a();
        return new C2948dj(a, C3049cg.m773b(this.f1185b), this.f1186c.mo43928a(), C3049cg.m773b(this.f1187d), this.f1188e.mo43928a());
    }
}
