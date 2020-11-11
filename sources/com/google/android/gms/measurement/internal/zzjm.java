package com.google.android.gms.measurement.internal;

/* compiled from: com.google.android.gms:play-services-measurement-impl@@17.4.4 */
final class zzjm implements Runnable {
    private final /* synthetic */ zzer zza;
    private final /* synthetic */ zzjn zzb;

    zzjm(zzjn zzjn, zzer zzer) {
        this.zzb = zzjn;
        this.zza = zzer;
    }

    public final void run() {
        synchronized (this.zzb) {
            boolean unused = this.zzb.zzb = false;
            if (!this.zzb.zza.zzab()) {
                this.zzb.zza.zzr().zzx().zza("Connected to service");
                this.zzb.zza.zza(this.zza);
            }
        }
    }
}
