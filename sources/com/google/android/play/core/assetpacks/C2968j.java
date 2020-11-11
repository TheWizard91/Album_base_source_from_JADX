package com.google.android.play.core.assetpacks;

import com.google.android.play.core.internal.C3047ce;
import com.google.android.play.core.internal.C3049cg;
import com.google.android.play.core.internal.C3051ci;
import com.google.android.play.core.splitinstall.C3160z;
import java.util.concurrent.Executor;

/* renamed from: com.google.android.play.core.assetpacks.j */
public final class C2968j implements C3051ci<C2967i> {

    /* renamed from: a */
    private final C3051ci<C2886bb> f1239a;

    /* renamed from: b */
    private final C3051ci<C2982w> f1240b;

    /* renamed from: c */
    private final C3051ci<C2880aw> f1241c;

    /* renamed from: d */
    private final C3051ci<C3160z> f1242d;

    /* renamed from: e */
    private final C3051ci<C2929cr> f1243e;

    /* renamed from: f */
    private final C3051ci<C2913cb> f1244f;

    /* renamed from: g */
    private final C3051ci<C2901bq> f1245g;

    /* renamed from: h */
    private final C3051ci<Executor> f1246h;

    public C2968j(C3051ci<C2886bb> ciVar, C3051ci<C2982w> ciVar2, C3051ci<C2880aw> ciVar3, C3051ci<C3160z> ciVar4, C3051ci<C2929cr> ciVar5, C3051ci<C2913cb> ciVar6, C3051ci<C2901bq> ciVar7, C3051ci<Executor> ciVar8) {
        this.f1239a = ciVar;
        this.f1240b = ciVar2;
        this.f1241c = ciVar3;
        this.f1242d = ciVar4;
        this.f1243e = ciVar5;
        this.f1244f = ciVar6;
        this.f1245g = ciVar7;
        this.f1246h = ciVar8;
    }

    /* renamed from: a */
    public final /* bridge */ /* synthetic */ Object mo43928a() {
        C2886bb a = this.f1239a.mo43928a();
        C3047ce<C2982w> b = C3049cg.m773b(this.f1240b);
        C2880aw a2 = this.f1241c.mo43928a();
        return new C2967i(a, b, a2, this.f1242d.mo43928a(), this.f1243e.mo43928a(), this.f1244f.mo43928a(), this.f1245g.mo43928a(), C3049cg.m773b(this.f1246h));
    }
}
