package com.google.android.gms.internal.measurement;

/* compiled from: com.google.android.gms:play-services-measurement-impl@@17.4.4 */
public final class zzml implements zzmm {
    private static final zzcw<Boolean> zza;
    private static final zzcw<Boolean> zzb;
    private static final zzcw<Boolean> zzc;
    private static final zzcw<Long> zzd;

    public final boolean zza() {
        return zza.zzc().booleanValue();
    }

    static {
        zzdf zzdf = new zzdf(zzcx.zza("com.google.android.gms.measurement"));
        zza = zzdf.zza("measurement.sdk.dynamite.allow_remote_dynamite2", false);
        zzb = zzdf.zza("measurement.collection.init_params_control_enabled", true);
        zzc = zzdf.zza("measurement.sdk.dynamite.use_dynamite3", true);
        zzd = zzdf.zza("measurement.id.sdk.dynamite.use_dynamite", 0);
    }
}
