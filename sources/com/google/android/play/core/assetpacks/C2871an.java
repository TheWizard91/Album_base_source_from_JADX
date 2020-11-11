package com.google.android.play.core.assetpacks;

import android.os.Bundle;
import com.google.android.play.core.tasks.C3169i;

/* renamed from: com.google.android.play.core.assetpacks.an */
final class C2871an extends C2868ak<Void> {

    /* renamed from: c */
    final /* synthetic */ C2875ar f923c;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    C2871an(C2875ar arVar, C3169i<Void> iVar) {
        super(arVar, iVar);
        this.f923c = arVar;
    }

    /* renamed from: a */
    public final void mo43909a(Bundle bundle, Bundle bundle2) {
        super.mo43909a(bundle, bundle2);
        if (!this.f923c.f938g.compareAndSet(true, false)) {
            C2875ar.f932a.mo44091d("Expected keepingAlive to be true, but was false.", new Object[0]);
        }
        if (bundle.getBoolean("keep_alive")) {
            this.f923c.mo43927b();
        }
    }
}
