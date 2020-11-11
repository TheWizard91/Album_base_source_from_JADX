package com.google.android.play.core.assetpacks;

import android.os.Bundle;

/* renamed from: com.google.android.play.core.assetpacks.au */
final /* synthetic */ class C2878au implements Runnable {

    /* renamed from: a */
    private final C2880aw f943a;

    /* renamed from: b */
    private final Bundle f944b;

    /* renamed from: c */
    private final AssetPackState f945c;

    C2878au(C2880aw awVar, Bundle bundle, AssetPackState assetPackState) {
        this.f943a = awVar;
        this.f944b = bundle;
        this.f945c = assetPackState;
    }

    public final void run() {
        this.f943a.mo43933a(this.f944b, this.f945c);
    }
}
