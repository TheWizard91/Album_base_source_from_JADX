package com.google.android.gms.measurement.internal;

/* compiled from: com.google.android.gms:play-services-measurement@@17.4.4 */
final class zzju implements Runnable {
    private final /* synthetic */ zzkk zza;
    private final /* synthetic */ Runnable zzb;

    zzju(zzjt zzjt, zzkk zzkk, Runnable runnable) {
        this.zza = zzkk;
        this.zzb = runnable;
    }

    public final void run() {
        this.zza.zzo();
        this.zza.zza(this.zzb);
        this.zza.zzl();
    }
}
