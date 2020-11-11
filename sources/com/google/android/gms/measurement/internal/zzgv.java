package com.google.android.gms.measurement.internal;

/* compiled from: com.google.android.gms:play-services-measurement@@17.4.4 */
final class zzgv implements Runnable {
    private final /* synthetic */ String zza;
    private final /* synthetic */ String zzb;
    private final /* synthetic */ String zzc;
    private final /* synthetic */ long zzd;
    private final /* synthetic */ zzge zze;

    zzgv(zzge zzge, String str, String str2, String str3, long j) {
        this.zze = zzge;
        this.zza = str;
        this.zzb = str2;
        this.zzc = str3;
        this.zzd = j;
    }

    public final void run() {
        if (this.zza == null) {
            this.zze.zza.zzs().zzv().zza(this.zzb, (zzik) null);
            return;
        }
        this.zze.zza.zzs().zzv().zza(this.zzb, new zzik(this.zzc, this.zza, this.zzd));
    }
}
