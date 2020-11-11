package com.google.android.gms.internal.measurement;

import java.util.Iterator;

/* compiled from: com.google.android.gms:play-services-measurement-impl@@17.4.4 */
final class zzfu<E> extends zzey<E> {
    private final transient E zza;
    private transient int zzb;

    zzfu(E e) {
        this.zza = zzdq.zza(e);
    }

    zzfu(E e, int i) {
        this.zza = e;
        this.zzb = i;
    }

    public final int size() {
        return 1;
    }

    public final boolean contains(Object obj) {
        return this.zza.equals(obj);
    }

    public final zzfz<E> zzb() {
        return new zzfh(this.zza);
    }

    /* access modifiers changed from: package-private */
    public final zzeq<E> zzd() {
        return zzeq.zza(this.zza);
    }

    /* access modifiers changed from: package-private */
    public final boolean zzh() {
        return false;
    }

    /* access modifiers changed from: package-private */
    public final int zzb(Object[] objArr, int i) {
        objArr[i] = this.zza;
        return i + 1;
    }

    public final int hashCode() {
        int i = this.zzb;
        if (i != 0) {
            return i;
        }
        int hashCode = this.zza.hashCode();
        this.zzb = hashCode;
        return hashCode;
    }

    /* access modifiers changed from: package-private */
    public final boolean zza() {
        return this.zzb != 0;
    }

    public final String toString() {
        String obj = this.zza.toString();
        return new StringBuilder(String.valueOf(obj).length() + 2).append('[').append(obj).append(']').toString();
    }

    public final /* synthetic */ Iterator iterator() {
        return iterator();
    }
}
