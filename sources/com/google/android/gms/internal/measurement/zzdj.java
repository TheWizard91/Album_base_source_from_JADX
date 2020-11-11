package com.google.android.gms.internal.measurement;

/* compiled from: com.google.android.gms:play-services-measurement-impl@@17.4.4 */
public final class zzdj {
    private final boolean zza;

    public zzdj(zzdi zzdi) {
        zzdq.zza(zzdi, (Object) "BuildInfo must be non-null");
        this.zza = !zzdi.zza();
    }

    public final boolean zza(String str) {
        zzdq.zza(str, (Object) "flagName must not be null");
        if (!this.zza) {
            return true;
        }
        return zzdl.zza.zza().zza(str);
    }
}
