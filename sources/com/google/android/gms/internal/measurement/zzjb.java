package com.google.android.gms.internal.measurement;

import java.io.IOException;

/* compiled from: com.google.android.gms:play-services-measurement-base@@17.4.4 */
public final class zzjb<K, V> {
    static <K, V> void zza(zzhi zzhi, zzje<K, V> zzje, K k, V v) throws IOException {
        zzhr.zza(zzhi, zzje.zza, 1, k);
        zzhr.zza(zzhi, zzje.zzc, 2, v);
    }

    static <K, V> int zza(zzje<K, V> zzje, K k, V v) {
        return zzhr.zza(zzje.zza, 1, k) + zzhr.zza(zzje.zzc, 2, v);
    }
}
