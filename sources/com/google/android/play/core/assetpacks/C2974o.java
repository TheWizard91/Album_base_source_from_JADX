package com.google.android.play.core.assetpacks;

import com.google.android.play.core.internal.C3027bl;
import com.google.android.play.core.internal.C3051ci;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/* renamed from: com.google.android.play.core.assetpacks.o */
public final class C2974o implements C3051ci<Executor> {
    /* renamed from: b */
    public static Executor m587b() {
        ExecutorService newSingleThreadExecutor = Executors.newSingleThreadExecutor(C2969k.f1247a);
        C3027bl.m719a(newSingleThreadExecutor, "Cannot return null from a non-@Nullable @Provides method");
        return newSingleThreadExecutor;
    }

    /* renamed from: a */
    public final /* bridge */ /* synthetic */ Object mo43928a() {
        return m587b();
    }
}
