package com.google.android.gms.internal.measurement;

import java.io.Serializable;

/* compiled from: com.google.android.gms:play-services-measurement-impl@@17.4.4 */
public abstract class zzdr<T> implements Serializable {
    public static <T> zzdr<T> zzc() {
        return zzdn.zza;
    }

    public abstract boolean zza();

    public abstract T zzb();

    public static <T> zzdr<T> zza(T t) {
        return new zzdt(zzdq.zza(t));
    }

    zzdr() {
    }
}
