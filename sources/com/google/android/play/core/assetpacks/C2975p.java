package com.google.android.play.core.assetpacks;

import android.content.ComponentName;
import android.content.Context;
import com.google.android.play.core.common.PlayCoreDialogWrapperActivity;
import com.google.android.play.core.internal.C3027bl;
import com.google.android.play.core.internal.C3051ci;

/* renamed from: com.google.android.play.core.assetpacks.p */
public final class C2975p implements C3051ci<AssetPackManager> {

    /* renamed from: a */
    private final C3051ci<C2967i> f1253a;

    /* renamed from: b */
    private final C3051ci<Context> f1254b;

    public C2975p(C3051ci<C2967i> ciVar, C3051ci<Context> ciVar2) {
        this.f1253a = ciVar;
        this.f1254b = ciVar2;
    }

    /* renamed from: a */
    public final /* bridge */ /* synthetic */ Object mo43928a() {
        C2967i a = this.f1253a.mo43928a();
        Context b = ((C2977r) this.f1254b).mo43928a();
        C2967i iVar = a;
        C3027bl.m717a(b.getPackageManager(), new ComponentName(b.getPackageName(), "com.google.android.play.core.assetpacks.AssetPackExtractionService"));
        PlayCoreDialogWrapperActivity.m611a(b);
        C3027bl.m719a(iVar, "Cannot return null from a non-@Nullable @Provides method");
        return iVar;
    }
}
