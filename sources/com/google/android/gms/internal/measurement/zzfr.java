package com.google.android.gms.internal.measurement;

import java.util.Iterator;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

/* compiled from: com.google.android.gms:play-services-measurement-impl@@17.4.4 */
final class zzfr<K> extends zzey<K> {
    private final transient zzeu<K, ?> zza;
    private final transient zzeq<K> zzb;

    zzfr(zzeu<K, ?> zzeu, zzeq<K> zzeq) {
        this.zza = zzeu;
        this.zzb = zzeq;
    }

    public final zzfz<K> zzb() {
        return (zzfz) zzc().iterator();
    }

    /* access modifiers changed from: package-private */
    public final int zzb(Object[] objArr, int i) {
        return zzc().zzb(objArr, i);
    }

    public final zzeq<K> zzc() {
        return this.zzb;
    }

    public final boolean contains(@NullableDecl Object obj) {
        return this.zza.get(obj) != null;
    }

    /* access modifiers changed from: package-private */
    public final boolean zzh() {
        return true;
    }

    public final int size() {
        return this.zza.size();
    }

    public final /* synthetic */ Iterator iterator() {
        return iterator();
    }
}
