package com.google.android.gms.measurement.internal;

/* compiled from: com.google.android.gms:play-services-measurement@@17.4.4 */
final class zzfi implements Runnable {
    private final /* synthetic */ boolean zza;
    private final /* synthetic */ zzfj zzb;

    zzfi(zzfj zzfj, boolean z) {
        this.zzb = zzfj;
        this.zza = z;
    }

    public final void run() {
        this.zzb.zzb.zza(this.zza);
    }
}
