package com.google.android.play.core.assetpacks;

import com.google.android.play.core.internal.C3027bl;
import com.google.android.play.core.internal.C3051ci;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/* renamed from: com.google.android.play.core.assetpacks.v */
public final class C2981v implements C3051ci<Executor> {
    /* renamed from: b */
    public static Executor m597b() {
        ExecutorService newSingleThreadExecutor = Executors.newSingleThreadExecutor(C2970l.f1248a);
        C3027bl.m719a(newSingleThreadExecutor, "Cannot return null from a non-@Nullable @Provides method");
        return newSingleThreadExecutor;
    }

    /* renamed from: a */
    public final /* bridge */ /* synthetic */ Object mo43928a() {
        return m597b();
    }
}
