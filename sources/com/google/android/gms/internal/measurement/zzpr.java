package com.google.android.gms.internal.measurement;

/* compiled from: com.google.android.gms:play-services-measurement-impl@@17.4.4 */
public final class zzpr implements zzps {
    private static final zzcw<Boolean> zza;
    private static final zzcw<Long> zzb;

    public final boolean zza() {
        return zza.zzc().booleanValue();
    }

    static {
        zzdf zzdf = new zzdf(zzcx.zza("com.google.android.gms.measurement"));
        zza = zzdf.zza("measurement.service.ssaid_removal", true);
        zzb = zzdf.zza("measurement.id.ssaid_removal", 0);
    }
}
