package com.google.android.gms.internal.p014firebaseiid;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;

/* renamed from: com.google.android.gms.internal.firebase-iid.zzc */
/* compiled from: com.google.firebase:firebase-iid@@20.2.3 */
final class zzc implements zzb {
    private zzc() {
    }

    public final ScheduledExecutorService zza(int i, ThreadFactory threadFactory, int i2) {
        return Executors.unconfigurableScheduledExecutorService(Executors.newScheduledThreadPool(1, threadFactory));
    }
}
