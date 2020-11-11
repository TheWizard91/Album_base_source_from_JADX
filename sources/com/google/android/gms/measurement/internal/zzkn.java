package com.google.android.gms.measurement.internal;

/* compiled from: com.google.android.gms:play-services-measurement@@17.4.4 */
final class zzkn implements Runnable {
    private final /* synthetic */ zzkq zza;
    private final /* synthetic */ zzkk zzb;

    zzkn(zzkk zzkk, zzkq zzkq) {
        this.zzb = zzkk;
        this.zza = zzkq;
    }

    public final void run() {
        this.zzb.zza(this.zza);
        this.zzb.zza();
    }
}
