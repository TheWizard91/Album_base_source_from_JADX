package com.google.android.gms.measurement.internal;

/* compiled from: com.google.android.gms:play-services-measurement-impl@@17.4.4 */
final class zziq implements Runnable {
    private final /* synthetic */ zzik zza;
    private final /* synthetic */ long zzb;
    private final /* synthetic */ zzin zzc;

    zziq(zzin zzin, zzik zzik, long j) {
        this.zzc = zzin;
        this.zza = zzik;
        this.zzb = j;
    }

    public final void run() {
        this.zzc.zza(this.zza, false, this.zzb);
        this.zzc.zza = null;
        this.zzc.zzh().zza((zzik) null);
    }
}
