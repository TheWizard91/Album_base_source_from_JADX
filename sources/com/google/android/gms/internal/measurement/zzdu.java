package com.google.android.gms.internal.measurement;

import java.io.Serializable;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

/* compiled from: com.google.android.gms:play-services-measurement-impl@@17.4.4 */
public final class zzdu {
    public static <T> zzdv<T> zza(zzdv<T> zzdv) {
        if ((zzdv instanceof zzdw) || (zzdv instanceof zzdx)) {
            return zzdv;
        }
        if (zzdv instanceof Serializable) {
            return new zzdx(zzdv);
        }
        return new zzdw(zzdv);
    }

    public static <T> zzdv<T> zza(@NullableDecl T t) {
        return new zzdz(t);
    }
}
