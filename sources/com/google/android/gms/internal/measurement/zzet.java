package com.google.android.gms.internal.measurement;

/* compiled from: com.google.android.gms:play-services-measurement-impl@@17.4.4 */
final class zzet<E> extends zzdy<E> {
    private final zzeq<E> zza;

    zzet(zzeq<E> zzeq, int i) {
        super(zzeq.size(), i);
        this.zza = zzeq;
    }

    /* access modifiers changed from: protected */
    public final E zza(int i) {
        return this.zza.get(i);
    }
}
