package com.google.android.gms.measurement.internal;

/* compiled from: com.google.android.gms:play-services-measurement-impl@@17.4.4 */
final class zzjz implements Runnable {
    private final /* synthetic */ long zza;
    private final /* synthetic */ zzjw zzb;

    zzjz(zzjw zzjw, long j) {
        this.zzb = zzjw;
        this.zza = j;
    }

    public final void run() {
        this.zzb.zzb(this.zza);
    }
}
