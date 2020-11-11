package com.google.android.gms.internal.measurement;

import java.util.Comparator;
import java.util.SortedSet;

/* compiled from: com.google.android.gms:play-services-measurement-impl@@17.4.4 */
final class zzfw {
    public static boolean zza(Comparator<?> comparator, Iterable<?> iterable) {
        Object obj;
        zzdq.zza(comparator);
        zzdq.zza(iterable);
        if (iterable instanceof SortedSet) {
            obj = ((SortedSet) iterable).comparator();
            if (obj == null) {
                obj = zzfi.zza;
            }
        } else if (!(iterable instanceof zzfx)) {
            return false;
        } else {
            obj = ((zzfx) iterable).comparator();
        }
        return comparator.equals(obj);
    }
}
