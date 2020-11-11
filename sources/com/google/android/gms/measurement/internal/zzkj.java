package com.google.android.gms.measurement.internal;

/* compiled from: com.google.android.gms:play-services-measurement@@17.4.4 */
final class zzkj extends zzag {
    private final /* synthetic */ zzkk zza;
    private final /* synthetic */ zzkg zzb;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    zzkj(zzkg zzkg, zzgz zzgz, zzkk zzkk) {
        super(zzgz);
        this.zzb = zzkg;
        this.zza = zzkk;
    }

    public final void zza() {
        this.zzb.zzf();
        this.zzb.zzr().zzx().zza("Starting upload from DelayedRunnable");
        this.zza.zzl();
    }
}
