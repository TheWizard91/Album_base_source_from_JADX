package com.google.android.gms.internal.measurement;

/* compiled from: com.google.android.gms:play-services-measurement-impl@@17.4.4 */
final class zzfn<E> extends zzeq<E> {
    static final zzeq<Object> zza = new zzfn(new Object[0], 0);
    private final transient Object[] zzb;
    private final transient int zzc;

    zzfn(Object[] objArr, int i) {
        this.zzb = objArr;
        this.zzc = i;
    }

    public final int size() {
        return this.zzc;
    }

    /* access modifiers changed from: package-private */
    public final boolean zzh() {
        return false;
    }

    /* access modifiers changed from: package-private */
    public final Object[] zze() {
        return this.zzb;
    }

    /* access modifiers changed from: package-private */
    public final int zzf() {
        return 0;
    }

    /* access modifiers changed from: package-private */
    public final int zzg() {
        return this.zzc;
    }

    /* access modifiers changed from: package-private */
    public final int zzb(Object[] objArr, int i) {
        System.arraycopy(this.zzb, 0, objArr, i, this.zzc);
        return i + this.zzc;
    }

    public final E get(int i) {
        zzdq.zza(i, this.zzc);
        return this.zzb[i];
    }
}
