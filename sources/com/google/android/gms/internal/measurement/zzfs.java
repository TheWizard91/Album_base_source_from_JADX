package com.google.android.gms.internal.measurement;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

/* compiled from: com.google.android.gms:play-services-measurement-impl@@17.4.4 */
final class zzfs<E> extends zzfd<E> {
    static final zzfs<Comparable> zzb = new zzfs<>(zzeq.zza(), zzfi.zza);
    private final transient zzeq<E> zzc;

    zzfs(zzeq<E> zzeq, Comparator<? super E> comparator) {
        super(comparator);
        this.zzc = zzeq;
    }

    /* access modifiers changed from: package-private */
    public final Object[] zze() {
        return this.zzc.zze();
    }

    /* access modifiers changed from: package-private */
    public final int zzf() {
        return this.zzc.zzf();
    }

    /* access modifiers changed from: package-private */
    public final int zzg() {
        return this.zzc.zzg();
    }

    public final zzfz<E> zzb() {
        return (zzfz) this.zzc.iterator();
    }

    public final zzfz<E> zzj() {
        return (zzfz) this.zzc.zzd().iterator();
    }

    public final int size() {
        return this.zzc.size();
    }

    public final boolean contains(@NullableDecl Object obj) {
        if (obj != null) {
            try {
                if (Collections.binarySearch(this.zzc, obj, this.zza) >= 0) {
                    return true;
                }
            } catch (ClassCastException e) {
                return false;
            }
        }
        return false;
    }

    public final boolean containsAll(Collection<?> collection) {
        if (collection instanceof zzfj) {
            collection = ((zzfj) collection).zza();
        }
        if (!zzfw.zza(comparator(), collection) || collection.size() <= 1) {
            return super.containsAll(collection);
        }
        zzfz zzfz = (zzfz) iterator();
        Iterator<?> it = collection.iterator();
        if (!zzfz.hasNext()) {
            return false;
        }
        Object next = it.next();
        Object next2 = zzfz.next();
        while (true) {
            try {
                int zza = zza(next2, (Object) next);
                if (zza < 0) {
                    if (!zzfz.hasNext()) {
                        return false;
                    }
                    next2 = zzfz.next();
                } else if (zza == 0) {
                    if (!it.hasNext()) {
                        return true;
                    }
                    next = it.next();
                } else if (zza > 0) {
                    return false;
                }
            } catch (ClassCastException | NullPointerException e) {
                return false;
            }
        }
    }

    /* access modifiers changed from: package-private */
    public final boolean zzh() {
        return this.zzc.zzh();
    }

    /* access modifiers changed from: package-private */
    public final int zzb(Object[] objArr, int i) {
        return this.zzc.zzb(objArr, i);
    }

    /* JADX WARNING: Removed duplicated region for block: B:19:0x0036 A[Catch:{ ClassCastException -> 0x004c, NoSuchElementException -> 0x004a }] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final boolean equals(@org.checkerframework.checker.nullness.compatqual.NullableDecl java.lang.Object r6) {
        /*
            r5 = this;
            r0 = 1
            if (r6 != r5) goto L_0x0004
            return r0
        L_0x0004:
            boolean r1 = r6 instanceof java.util.Set
            r2 = 0
            if (r1 != 0) goto L_0x000a
            return r2
        L_0x000a:
            java.util.Set r6 = (java.util.Set) r6
            int r1 = r5.size()
            int r3 = r6.size()
            if (r1 == r3) goto L_0x0017
            return r2
        L_0x0017:
            boolean r1 = r5.isEmpty()
            if (r1 == 0) goto L_0x001e
            return r0
        L_0x001e:
            java.util.Comparator r1 = r5.zza
            boolean r1 = com.google.android.gms.internal.measurement.zzfw.zza(r1, r6)
            if (r1 == 0) goto L_0x004e
            java.util.Iterator r6 = r6.iterator()
            java.util.Iterator r1 = r5.iterator()     // Catch:{ ClassCastException -> 0x004c, NoSuchElementException -> 0x004a }
            com.google.android.gms.internal.measurement.zzfz r1 = (com.google.android.gms.internal.measurement.zzfz) r1     // Catch:{ ClassCastException -> 0x004c, NoSuchElementException -> 0x004a }
        L_0x0030:
            boolean r3 = r1.hasNext()     // Catch:{ ClassCastException -> 0x004c, NoSuchElementException -> 0x004a }
            if (r3 == 0) goto L_0x0049
            java.lang.Object r3 = r1.next()     // Catch:{ ClassCastException -> 0x004c, NoSuchElementException -> 0x004a }
            java.lang.Object r4 = r6.next()     // Catch:{ ClassCastException -> 0x004c, NoSuchElementException -> 0x004a }
            if (r4 == 0) goto L_0x0048
            int r3 = r5.zza((java.lang.Object) r3, (java.lang.Object) r4)     // Catch:{ ClassCastException -> 0x004c, NoSuchElementException -> 0x004a }
            if (r3 == 0) goto L_0x0047
            goto L_0x0048
        L_0x0047:
            goto L_0x0030
        L_0x0048:
            return r2
        L_0x0049:
            return r0
        L_0x004a:
            r6 = move-exception
            return r2
        L_0x004c:
            r6 = move-exception
            return r2
        L_0x004e:
            boolean r6 = r5.containsAll(r6)
            return r6
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.measurement.zzfs.equals(java.lang.Object):boolean");
    }

    public final E first() {
        if (!isEmpty()) {
            return this.zzc.get(0);
        }
        throw new NoSuchElementException();
    }

    public final E last() {
        if (!isEmpty()) {
            return this.zzc.get(size() - 1);
        }
        throw new NoSuchElementException();
    }

    public final E lower(E e) {
        int zzc2 = zzc(e, false) - 1;
        if (zzc2 == -1) {
            return null;
        }
        return this.zzc.get(zzc2);
    }

    public final E floor(E e) {
        int zzc2 = zzc(e, true) - 1;
        if (zzc2 == -1) {
            return null;
        }
        return this.zzc.get(zzc2);
    }

    public final E ceiling(E e) {
        int zzd = zzd(e, true);
        if (zzd == size()) {
            return null;
        }
        return this.zzc.get(zzd);
    }

    public final E higher(E e) {
        int zzd = zzd(e, false);
        if (zzd == size()) {
            return null;
        }
        return this.zzc.get(zzd);
    }

    /* access modifiers changed from: package-private */
    public final zzfd<E> zza(E e, boolean z) {
        return zza(0, zzc(e, z));
    }

    private final int zzc(E e, boolean z) {
        int binarySearch = Collections.binarySearch(this.zzc, zzdq.zza(e), comparator());
        if (binarySearch >= 0) {
            return z ? binarySearch + 1 : binarySearch;
        }
        return ~binarySearch;
    }

    /* access modifiers changed from: package-private */
    public final zzfd<E> zza(E e, boolean z, E e2, boolean z2) {
        return zzb(e, z).zza(e2, z2);
    }

    /* access modifiers changed from: package-private */
    public final zzfd<E> zzb(E e, boolean z) {
        return zza(zzd(e, z), size());
    }

    private final int zzd(E e, boolean z) {
        int binarySearch = Collections.binarySearch(this.zzc, zzdq.zza(e), comparator());
        if (binarySearch >= 0) {
            return z ? binarySearch : binarySearch + 1;
        }
        return ~binarySearch;
    }

    private final zzfs<E> zza(int i, int i2) {
        if (i == 0 && i2 == size()) {
            return this;
        }
        if (i < i2) {
            return new zzfs<>((zzeq) this.zzc.subList(i, i2), this.zza);
        }
        return zza(this.zza);
    }

    public final zzeq<E> zzc() {
        return this.zzc;
    }

    /* access modifiers changed from: package-private */
    public final zzfd<E> zzi() {
        Comparator reverseOrder = Collections.reverseOrder(this.zza);
        if (isEmpty()) {
            return zza(reverseOrder);
        }
        return new zzfs(this.zzc.zzd(), reverseOrder);
    }

    public final /* synthetic */ Iterator descendingIterator() {
        return descendingIterator();
    }

    public final /* synthetic */ Iterator iterator() {
        return iterator();
    }
}
