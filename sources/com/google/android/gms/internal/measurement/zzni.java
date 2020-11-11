package com.google.android.gms.internal.measurement;

/* compiled from: com.google.android.gms:play-services-measurement-impl@@17.4.4 */
public final class zzni implements zznf {
    private static final zzcw<Boolean> zza;
    private static final zzcw<Long> zzb;

    public final boolean zza() {
        return true;
    }

    public final boolean zzb() {
        return zza.zzc().booleanValue();
    }

    static {
        zzdf zzdf = new zzdf(zzcx.zza("com.google.android.gms.measurement"));
        zza = zzdf.zza("measurement.sdk.referrer.delayed_install_referrer_api", false);
        zzb = zzdf.zza("measurement.id.sdk.referrer.delayed_install_referrer_api", 0);
    }
}
