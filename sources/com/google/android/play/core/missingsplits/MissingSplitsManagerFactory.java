package com.google.android.play.core.missingsplits;

import android.content.Context;
import java.util.concurrent.atomic.AtomicReference;

public class MissingSplitsManagerFactory {

    /* renamed from: a */
    private static final AtomicReference<Boolean> f1345a = new AtomicReference<>((Object) null);

    public static MissingSplitsManager create(Context context) {
        return new C3078b(context, Runtime.getRuntime(), new C3077a(context, context.getPackageManager()), f1345a);
    }
}
