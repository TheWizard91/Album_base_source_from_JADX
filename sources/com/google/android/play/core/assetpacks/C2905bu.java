package com.google.android.play.core.assetpacks;

import android.content.Context;
import com.google.android.play.core.internal.C3048cf;
import com.google.android.play.core.internal.C3049cg;
import com.google.android.play.core.internal.C3051ci;
import com.google.android.play.core.splitinstall.C3160z;
import java.util.concurrent.Executor;

/* renamed from: com.google.android.play.core.assetpacks.bu */
public final class C2905bu implements C2857a {

    /* renamed from: a */
    private final C2971m f1018a;

    /* renamed from: b */
    private C3051ci<Context> f1019b;

    /* renamed from: c */
    private C3051ci<C2950dl> f1020c;

    /* renamed from: d */
    private C3051ci<C2886bb> f1021d;

    /* renamed from: e */
    private C3051ci<C2913cb> f1022e;

    /* renamed from: f */
    private C3051ci<C2875ar> f1023f;

    /* renamed from: g */
    private C3051ci<String> f1024g = C3049cg.m772a(new C2978s(this.f1019b));

    /* renamed from: h */
    private C3051ci<C2982w> f1025h = new C3048cf();

    /* renamed from: i */
    private C3051ci<Executor> f1026i;

    /* renamed from: j */
    private C3051ci<C2929cr> f1027j;

    /* renamed from: k */
    private C3051ci<C2880aw> f1028k;

    /* renamed from: l */
    private C3051ci<C2907bw> f1029l;

    /* renamed from: m */
    private C3051ci<C2960dv> f1030m;

    /* renamed from: n */
    private C3051ci<C2944df> f1031n;

    /* renamed from: o */
    private C3051ci<C2948dj> f1032o;

    /* renamed from: p */
    private C3051ci<C2953do> f1033p;

    /* renamed from: q */
    private C3051ci<C2898bn> f1034q;

    /* renamed from: r */
    private C3051ci<C2932cu> f1035r;

    /* renamed from: s */
    private C3051ci<C2910bz> f1036s;

    /* renamed from: t */
    private C3051ci<C2901bq> f1037t;

    /* renamed from: u */
    private C3051ci<Executor> f1038u;

    /* renamed from: v */
    private C3051ci<C2940db> f1039v;

    /* renamed from: w */
    private C3051ci<C3160z> f1040w;

    /* renamed from: x */
    private C3051ci<C2967i> f1041x;

    /* renamed from: y */
    private C3051ci<AssetPackManager> f1042y;

    /* synthetic */ C2905bu(C2971m mVar) {
        this.f1018a = mVar;
        C2977r rVar = new C2977r(mVar);
        this.f1019b = rVar;
        C3051ci<C2950dl> a = C3049cg.m772a(new C2951dm(rVar));
        this.f1020c = a;
        this.f1021d = C3049cg.m772a(new C2888bd(this.f1019b, a));
        C3051ci<C2913cb> a2 = C3049cg.m772a(C2914cc.f1083a);
        this.f1022e = a2;
        this.f1023f = C3049cg.m772a(new C2876as(this.f1019b, a2));
        C3051ci<Executor> a3 = C3049cg.m772a(C2973n.f1252a);
        this.f1026i = a3;
        this.f1027j = C3049cg.m772a(new C2930cs(this.f1021d, this.f1025h, this.f1022e, a3));
        C3048cf cfVar = new C3048cf();
        this.f1028k = cfVar;
        this.f1029l = C3049cg.m772a(new C2908bx(this.f1021d, this.f1025h, cfVar, this.f1022e));
        this.f1030m = C3049cg.m772a(new C2961dw(this.f1021d));
        this.f1031n = C3049cg.m772a(new C2945dg(this.f1021d));
        this.f1032o = C3049cg.m772a(new C2949dk(this.f1021d, this.f1025h, this.f1027j, this.f1026i, this.f1022e));
        this.f1033p = C3049cg.m772a(new C2954dp(this.f1021d, this.f1025h));
        C3051ci<C2898bn> a4 = C3049cg.m772a(new C2899bo(this.f1025h));
        this.f1034q = a4;
        C3051ci<C2932cu> a5 = C3049cg.m772a(new C2933cv(this.f1027j, this.f1021d, a4));
        this.f1035r = a5;
        this.f1036s = C3049cg.m772a(new C2912ca(this.f1027j, this.f1025h, this.f1029l, this.f1030m, this.f1031n, this.f1032o, this.f1033p, a5));
        this.f1037t = C3049cg.m772a(C2902br.f1016a);
        C3051ci<Executor> a6 = C3049cg.m772a(C2980u.f1261a);
        this.f1038u = a6;
        C3048cf.m770a(this.f1028k, C3049cg.m772a(new C2881ax(this.f1019b, this.f1027j, this.f1036s, this.f1025h, this.f1022e, this.f1037t, this.f1026i, a6)));
        C3051ci<C2940db> a7 = C3049cg.m772a(new C2941dc(this.f1024g, this.f1028k, this.f1022e, this.f1019b, this.f1020c, this.f1026i));
        this.f1039v = a7;
        C3048cf.m770a(this.f1025h, C3049cg.m772a(new C2976q(this.f1019b, this.f1023f, a7)));
        C3051ci<C3160z> a8 = C3049cg.m772a(new C2979t(this.f1019b));
        this.f1040w = a8;
        C3051ci<C2967i> a9 = C3049cg.m772a(new C2968j(this.f1021d, this.f1025h, this.f1028k, a8, this.f1027j, this.f1022e, this.f1037t, this.f1026i));
        this.f1041x = a9;
        this.f1042y = C3049cg.m772a(new C2975p(a9, this.f1019b));
    }

    /* renamed from: a */
    public final AssetPackManager mo43902a() {
        return this.f1042y.mo43928a();
    }

    /* renamed from: a */
    public final void mo43903a(AssetPackExtractionService assetPackExtractionService) {
        assetPackExtractionService.f875a = C2977r.m591a(this.f1018a);
        assetPackExtractionService.f876b = this.f1041x.mo43928a();
        assetPackExtractionService.f877c = this.f1021d.mo43928a();
    }

    /* renamed from: b */
    public final C2886bb mo43904b() {
        return this.f1021d.mo43928a();
    }
}
