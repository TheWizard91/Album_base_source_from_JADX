package com.google.android.gms.measurement.internal;

/* compiled from: com.google.android.gms:play-services-measurement-impl@@17.4.4 */
final class zzgf implements Runnable {
    private final /* synthetic */ zzhe zza;
    private final /* synthetic */ zzgd zzb;

    zzgf(zzgd zzgd, zzhe zzhe) {
        this.zzb = zzgd;
        this.zza = zzhe;
    }

    public final void run() {
        this.zzb.zza(this.zza);
        this.zzb.zza();
    }
}
