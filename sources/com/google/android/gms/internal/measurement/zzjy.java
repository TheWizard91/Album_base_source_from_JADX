package com.google.android.gms.internal.measurement;

import com.google.firebase.messaging.Constants;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/* compiled from: com.google.android.gms:play-services-measurement-base@@17.4.4 */
final class zzjy {
    private static final zzjy zza = new zzjy();
    private final zzkb zzb = new zzja();
    private final ConcurrentMap<Class<?>, zzkc<?>> zzc = new ConcurrentHashMap();

    public static zzjy zza() {
        return zza;
    }

    public final <T> zzkc<T> zza(Class<T> cls) {
        zzie.zza(cls, Constants.FirelogAnalytics.PARAM_MESSAGE_TYPE);
        zzkc<T> zzkc = (zzkc) this.zzc.get(cls);
        if (zzkc != null) {
            return zzkc;
        }
        zzkc<T> zza2 = this.zzb.zza(cls);
        zzie.zza(cls, Constants.FirelogAnalytics.PARAM_MESSAGE_TYPE);
        zzie.zza(zza2, "schema");
        zzkc<T> putIfAbsent = this.zzc.putIfAbsent(cls, zza2);
        if (putIfAbsent != null) {
            return putIfAbsent;
        }
        return zza2;
    }

    public final <T> zzkc<T> zza(T t) {
        return zza(t.getClass());
    }

    private zzjy() {
    }
}
