package com.google.android.play.core.assetpacks;

import java.util.concurrent.ThreadFactory;

/* renamed from: com.google.android.play.core.assetpacks.k */
final /* synthetic */ class C2969k implements ThreadFactory {

    /* renamed from: a */
    static final ThreadFactory f1247a = new C2969k();

    private C2969k() {
    }

    public final Thread newThread(Runnable runnable) {
        return new Thread(runnable, "AssetPackBackgroundExecutor");
    }
}
