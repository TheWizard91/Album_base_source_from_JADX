package com.google.android.gms.measurement.internal;

/* compiled from: com.google.android.gms:play-services-measurement-impl@@17.4.4 */
final class zzho implements Runnable {
    private final /* synthetic */ zzhc zza;
    private final /* synthetic */ zzhh zzb;

    zzho(zzhh zzhh, zzhc zzhc) {
        this.zzb = zzhh;
        this.zza = zzhc;
    }

    public final void run() {
        this.zzb.zza(this.zza);
    }
}
