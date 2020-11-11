package com.google.android.gms.measurement.internal;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

/* compiled from: com.google.android.gms:play-services-measurement-impl@@17.4.4 */
final class zzhm implements Runnable {
    private final /* synthetic */ AtomicReference zza;
    private final /* synthetic */ boolean zzb;
    private final /* synthetic */ zzhh zzc;

    zzhm(zzhh zzhh, AtomicReference atomicReference, boolean z) {
        this.zzc = zzhh;
        this.zza = atomicReference;
        this.zzb = z;
    }

    public final void run() {
        this.zzc.zzh().zza((AtomicReference<List<zzkr>>) this.zza, this.zzb);
    }
}
