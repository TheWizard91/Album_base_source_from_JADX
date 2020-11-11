package com.google.android.gms.internal.measurement;

import java.util.Iterator;
import java.util.Map;

/* compiled from: com.google.android.gms:play-services-measurement-base@@17.4.4 */
final class zzki extends zzko {
    private final /* synthetic */ zzkd zza;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    private zzki(zzkd zzkd) {
        super(zzkd, (zzkg) null);
        this.zza = zzkd;
    }

    public final Iterator<Map.Entry<K, V>> iterator() {
        return new zzkf(this.zza, (zzkg) null);
    }

    /* synthetic */ zzki(zzkd zzkd, zzkg zzkg) {
        this(zzkd);
    }
}
