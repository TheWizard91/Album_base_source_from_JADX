package com.google.android.gms.internal.measurement;

/* compiled from: com.google.android.gms:play-services-measurement-impl@@17.4.4 */
public final class zzoy implements zzov {
    private static final zzcw<Long> zza;
    private static final zzcw<Long> zzb;

    public final long zza() {
        return zzb.zzc().longValue();
    }

    static {
        zzdf zzdf = new zzdf(zzcx.zza("com.google.android.gms.measurement"));
        zza = zzdf.zza("measurement.id.max_bundles_per_iteration", 0);
        zzb = zzdf.zza("measurement.max_bundles_per_iteration", 2);
    }
}
