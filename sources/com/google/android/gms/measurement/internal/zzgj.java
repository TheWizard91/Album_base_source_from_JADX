package com.google.android.gms.measurement.internal;

/* compiled from: com.google.android.gms:play-services-measurement@@17.4.4 */
final class zzgj implements Runnable {
    private final /* synthetic */ zzw zza;
    private final /* synthetic */ zzge zzb;

    zzgj(zzge zzge, zzw zzw) {
        this.zzb = zzge;
        this.zza = zzw;
    }

    public final void run() {
        this.zzb.zza.zzo();
        if (this.zza.zzc.zza() == null) {
            this.zzb.zza.zzb(this.zza);
        } else {
            this.zzb.zza.zza(this.zza);
        }
    }
}
