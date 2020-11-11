package com.google.android.play.core.assetpacks;

import android.os.Bundle;
import com.google.android.play.core.tasks.C3169i;

/* renamed from: com.google.android.play.core.assetpacks.ap */
final class C2873ap extends C2868ak<AssetPackStates> {

    /* renamed from: c */
    private final C2913cb f928c;

    /* renamed from: d */
    private final C2883az f929d;

    C2873ap(C2875ar arVar, C3169i<AssetPackStates> iVar, C2913cb cbVar, C2883az azVar) {
        super(arVar, iVar);
        this.f928c = cbVar;
        this.f929d = azVar;
    }

    /* renamed from: c */
    public final void mo43916c(Bundle bundle, Bundle bundle2) {
        super.mo43916c(bundle, bundle2);
        this.f920a.mo44332b(AssetPackStates.m307a(bundle, this.f928c, this.f929d));
    }
}
