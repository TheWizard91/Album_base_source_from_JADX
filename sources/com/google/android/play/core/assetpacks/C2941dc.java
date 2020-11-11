package com.google.android.play.core.assetpacks;

import android.content.Context;
import com.google.android.play.core.internal.C3049cg;
import com.google.android.play.core.internal.C3051ci;
import java.io.File;
import java.util.concurrent.Executor;

/* renamed from: com.google.android.play.core.assetpacks.dc */
public final class C2941dc implements C3051ci<C2940db> {

    /* renamed from: a */
    private final C3051ci<String> f1163a;

    /* renamed from: b */
    private final C3051ci<C2880aw> f1164b;

    /* renamed from: c */
    private final C3051ci<C2913cb> f1165c;

    /* renamed from: d */
    private final C3051ci<Context> f1166d;

    /* renamed from: e */
    private final C3051ci<C2950dl> f1167e;

    /* renamed from: f */
    private final C3051ci<Executor> f1168f;

    public C2941dc(C3051ci<String> ciVar, C3051ci<C2880aw> ciVar2, C3051ci<C2913cb> ciVar3, C3051ci<Context> ciVar4, C3051ci<C2950dl> ciVar5, C3051ci<Executor> ciVar6) {
        this.f1163a = ciVar;
        this.f1164b = ciVar2;
        this.f1165c = ciVar3;
        this.f1166d = ciVar4;
        this.f1167e = ciVar5;
        this.f1168f = ciVar6;
    }

    /* renamed from: a */
    public final /* bridge */ /* synthetic */ Object mo43928a() {
        String a = this.f1163a.mo43928a();
        C2880aw a2 = this.f1164b.mo43928a();
        C2913cb a3 = this.f1165c.mo43928a();
        Context b = ((C2977r) this.f1166d).mo43928a();
        C2950dl a4 = this.f1167e.mo43928a();
        return new C2940db(a != null ? new File(b.getExternalFilesDir((String) null), a) : b.getExternalFilesDir((String) null), a2, a3, b, a4, C3049cg.m773b(this.f1168f));
    }
}
