package com.google.android.gms.measurement.internal;

/* compiled from: com.google.android.gms:play-services-measurement-impl@@17.4.4 */
final class zzir implements Runnable {
    private final /* synthetic */ long zza;
    private final /* synthetic */ zzin zzb;

    zzir(zzin zzin, long j) {
        this.zzb = zzin;
        this.zza = j;
    }

    public final void run() {
        this.zzb.zze().zza(this.zza);
        this.zzb.zza = null;
    }
}
