package com.google.android.gms.measurement.internal;

import android.os.Bundle;

/* compiled from: com.google.android.gms:play-services-measurement-impl@@17.4.4 */
public final class zzfd {
    public String zza;
    public Bundle zzb;
    private String zzc;
    private long zzd;

    private zzfd(String str, String str2, Bundle bundle, long j) {
        this.zza = str;
        this.zzc = str2;
        this.zzb = bundle == null ? new Bundle() : bundle;
        this.zzd = j;
    }

    public static zzfd zza(zzao zzao) {
        return new zzfd(zzao.zza, zzao.zzc, zzao.zzb.zzb(), zzao.zzd);
    }

    public final zzao zza() {
        return new zzao(this.zza, new zzan(new Bundle(this.zzb)), this.zzc, this.zzd);
    }

    public final String toString() {
        String str = this.zzc;
        String str2 = this.zza;
        String valueOf = String.valueOf(this.zzb);
        return new StringBuilder(String.valueOf(str).length() + 21 + String.valueOf(str2).length() + String.valueOf(valueOf).length()).append("origin=").append(str).append(",name=").append(str2).append(",params=").append(valueOf).toString();
    }
}
