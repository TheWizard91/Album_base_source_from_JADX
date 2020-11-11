package com.google.android.gms.measurement.internal;

/* compiled from: com.google.android.gms:play-services-measurement-impl@@17.4.4 */
final class zzhz implements Runnable {
    private final /* synthetic */ boolean zza;
    private final /* synthetic */ zzhh zzb;

    zzhz(zzhh zzhh, boolean z) {
        this.zzb = zzhh;
        this.zza = z;
    }

    public final void run() {
        this.zzb.zzc(this.zza);
    }
}
