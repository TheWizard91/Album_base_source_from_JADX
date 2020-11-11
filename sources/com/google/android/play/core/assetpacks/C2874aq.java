package com.google.android.play.core.assetpacks;

import android.os.Bundle;
import com.google.android.play.core.tasks.C3169i;
import java.util.List;

/* renamed from: com.google.android.play.core.assetpacks.aq */
final class C2874aq extends C2868ak<AssetPackStates> {

    /* renamed from: c */
    private final List<String> f930c;

    /* renamed from: d */
    private final C2913cb f931d;

    C2874aq(C2875ar arVar, C3169i<AssetPackStates> iVar, C2913cb cbVar, List<String> list) {
        super(arVar, iVar);
        this.f931d = cbVar;
        this.f930c = list;
    }

    /* renamed from: a */
    public final void mo43907a(int i, Bundle bundle) {
        super.mo43907a(i, bundle);
        this.f920a.mo44332b(AssetPackStates.m308a(bundle, this.f931d, this.f930c));
    }
}
