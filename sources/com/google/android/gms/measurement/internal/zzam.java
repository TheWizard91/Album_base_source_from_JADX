package com.google.android.gms.measurement.internal;

import java.util.Iterator;

/* compiled from: com.google.android.gms:play-services-measurement-impl@@17.4.4 */
final class zzam implements Iterator<String> {
    private Iterator<String> zza;
    private final /* synthetic */ zzan zzb;

    zzam(zzan zzan) {
        this.zzb = zzan;
        this.zza = zzan.zza.keySet().iterator();
    }

    public final boolean hasNext() {
        return this.zza.hasNext();
    }

    public final void remove() {
        throw new UnsupportedOperationException("Remove not supported");
    }

    public final /* synthetic */ Object next() {
        return this.zza.next();
    }
}
