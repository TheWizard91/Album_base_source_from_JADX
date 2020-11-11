package com.google.android.gms.measurement.internal;

import android.content.ComponentName;

/* compiled from: com.google.android.gms:play-services-measurement-impl@@17.4.4 */
final class zzjp implements Runnable {
    private final /* synthetic */ ComponentName zza;
    private final /* synthetic */ zzjn zzb;

    zzjp(zzjn zzjn, ComponentName componentName) {
        this.zzb = zzjn;
        this.zza = componentName;
    }

    public final void run() {
        this.zzb.zza.zza(this.zza);
    }
}
