package com.google.android.gms.internal.measurement;

import java.util.Iterator;

/* compiled from: com.google.android.gms:play-services-measurement-base@@17.4.4 */
final class zzkx implements Iterator<String> {
    private Iterator<String> zza;
    private final /* synthetic */ zzkv zzb;

    zzkx(zzkv zzkv) {
        this.zzb = zzkv;
        this.zza = zzkv.zza.iterator();
    }

    public final boolean hasNext() {
        return this.zza.hasNext();
    }

    public final void remove() {
        throw new UnsupportedOperationException();
    }

    public final /* synthetic */ Object next() {
        return this.zza.next();
    }
}
