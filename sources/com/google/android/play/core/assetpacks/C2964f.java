package com.google.android.play.core.assetpacks;

import com.google.android.play.core.tasks.OnFailureListener;

/* renamed from: com.google.android.play.core.assetpacks.f */
final /* synthetic */ class C2964f implements OnFailureListener {

    /* renamed from: a */
    static final OnFailureListener f1224a = new C2964f();

    private C2964f() {
    }

    public final void onFailure(Exception exc) {
        C2967i.f1228a.mo44091d(String.format("Could not sync active asset packs. %s", new Object[]{exc}), new Object[0]);
    }
}
