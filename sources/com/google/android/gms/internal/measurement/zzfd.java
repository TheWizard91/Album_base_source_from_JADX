package com.google.android.gms.internal.measurement;

import java.util.Comparator;
import java.util.Iterator;
import java.util.NavigableSet;
import java.util.SortedSet;

/* compiled from: com.google.android.gms:play-services-measurement-impl@@17.4.4 */
public abstract class zzfd<E> extends zzfc<E> implements zzfx<E>, NavigableSet<E> {
    final transient Comparator<? super E> zza;
    private transient zzfd<E> zzb;

    static <E> zzfs<E> zza(Comparator<? super E> comparator) {
        if (zzfi.zza.equals(comparator)) {
            return zzfs.zzb;
        }
        return new zzfs<>(zzeq.zza(), comparator);
    }

    /* access modifiers changed from: package-private */
    public abstract zzfd<E> zza(E e, boolean z);

    /* access modifiers changed from: package-private */
    public abstract zzfd<E> zza(E e, boolean z, E e2, boolean z2);

    /* access modifiers changed from: package-private */
    public abstract zzfd<E> zzb(E e, boolean z);

    /* access modifiers changed from: package-private */
    public abstract zzfd<E> zzi();

    /* renamed from: zzj */
    public abstract zzfz<E> descendingIterator();

    /* access modifiers changed from: package-private */
    public final int zza(Object obj, Object obj2) {
        return this.zza.compare(obj, obj2);
    }

    zzfd(Comparator<? super E> comparator) {
        this.zza = comparator;
    }

    public Comparator<? super E> comparator() {
        return this.zza;
    }

    public E lower(E e) {
        return zzfe.zza((zzfz) ((zzfd) headSet(e, false)).descendingIterator(), null);
    }

    public E floor(E e) {
        return zzfe.zza((zzfz) ((zzfd) headSet(e, true)).descendingIterator(), null);
    }

    public E ceiling(E e) {
        return zzff.zza((zzfd) tailSet(e, true), null);
    }

    public E higher(E e) {
        return zzff.zza((zzfd) tailSet(e, false), null);
    }

    public E first() {
        return ((zzfz) iterator()).next();
    }

    public E last() {
        return ((zzfz) descendingIterator()).next();
    }

    @Deprecated
    public final E pollFirst() {
        throw new UnsupportedOperationException();
    }

    @Deprecated
    public final E pollLast() {
        throw new UnsupportedOperationException();
    }

    public /* synthetic */ Iterator iterator() {
        return iterator();
    }

    public /* synthetic */ SortedSet tailSet(Object obj) {
        return (zzfd) tailSet(obj, true);
    }

    public /* synthetic */ SortedSet headSet(Object obj) {
        return (zzfd) headSet(obj, false);
    }

    public /* synthetic */ SortedSet subSet(Object obj, Object obj2) {
        return (zzfd) subSet(obj, true, obj2, false);
    }

    public /* synthetic */ NavigableSet tailSet(Object obj, boolean z) {
        return zzb(zzdq.zza(obj), z);
    }

    public /* synthetic */ NavigableSet headSet(Object obj, boolean z) {
        return zza(zzdq.zza(obj), z);
    }

    public /* synthetic */ NavigableSet subSet(Object obj, boolean z, Object obj2, boolean z2) {
        zzdq.zza(obj);
        zzdq.zza(obj2);
        if (this.zza.compare(obj, obj2) <= 0) {
            return zza(obj, z, obj2, z2);
        }
        throw new IllegalArgumentException();
    }

    public /* synthetic */ NavigableSet descendingSet() {
        zzfd<E> zzfd = this.zzb;
        if (zzfd != null) {
            return zzfd;
        }
        zzfd<E> zzi = zzi();
        this.zzb = zzi;
        zzi.zzb = this;
        return zzi;
    }
}
