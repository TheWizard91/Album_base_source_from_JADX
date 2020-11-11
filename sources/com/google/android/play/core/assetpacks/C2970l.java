package com.google.android.play.core.assetpacks;

import java.util.concurrent.ThreadFactory;

/* renamed from: com.google.android.play.core.assetpacks.l */
final /* synthetic */ class C2970l implements ThreadFactory {

    /* renamed from: a */
    static final ThreadFactory f1248a = new C2970l();

    private C2970l() {
    }

    public final Thread newThread(Runnable runnable) {
        return new Thread(runnable, "UpdateListenerExecutor");
    }
}
