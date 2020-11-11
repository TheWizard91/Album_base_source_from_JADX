package com.google.android.gms.internal.measurement;

/* compiled from: com.google.android.gms:play-services-measurement-impl@@17.4.4 */
public final class zznu implements zznr {
    private static final zzcw<Boolean> zza;
    private static final zzcw<Boolean> zzb;
    private static final zzcw<Boolean> zzc;

    public final boolean zza() {
        return true;
    }

    public final boolean zzb() {
        return zza.zzc().booleanValue();
    }

    static {
        zzdf zzdf = new zzdf(zzcx.zza("com.google.android.gms.measurement"));
        zza = zzdf.zza("measurement.client.sessions.check_on_reset_and_enable2", true);
        zzb = zzdf.zza("measurement.client.sessions.check_on_startup", true);
        zzc = zzdf.zza("measurement.client.sessions.start_session_before_view_screen", true);
    }
}
