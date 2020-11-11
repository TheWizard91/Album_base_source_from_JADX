package com.google.android.gms.internal.measurement;

/* compiled from: com.google.android.gms:play-services-measurement-impl@@17.4.4 */
public final class zzmk implements zzmh {
    private static final zzcw<Boolean> zza;
    private static final zzcw<Boolean> zzb;

    public final boolean zza() {
        return zza.zzc().booleanValue();
    }

    public final boolean zzb() {
        return zzb.zzc().booleanValue();
    }

    static {
        zzdf zzdf = new zzdf(zzcx.zza("com.google.android.gms.measurement"));
        zza = zzdf.zza("measurement.client.consent_state_v1.dev", false);
        zzb = zzdf.zza("measurement.service.consent_state_v1", false);
    }
}
