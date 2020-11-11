package com.google.android.gms.internal.firebase_auth;

import java.io.Serializable;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

/* compiled from: com.google.firebase:firebase-auth@@19.3.2 */
public abstract class zzbk<K, V> implements Serializable, Map<K, V> {
    private static final Map.Entry<?, ?>[] zza = new Map.Entry[0];
    private transient zzbm<Map.Entry<K, V>> zzb;
    private transient zzbm<K> zzc;
    private transient zzbg<V> zzd;

    public static <K, V> zzbk<K, V> zza() {
        return zzbo.zza;
    }

    public abstract V get(@NullableDecl Object obj);

    /* access modifiers changed from: package-private */
    public abstract zzbm<Map.Entry<K, V>> zzb();

    /* access modifiers changed from: package-private */
    public abstract zzbm<K> zzc();

    /* access modifiers changed from: package-private */
    public abstract zzbg<V> zzd();

    zzbk() {
    }

    @Deprecated
    public final V put(K k, V v) {
        throw new UnsupportedOperationException();
    }

    @Deprecated
    public final V remove(Object obj) {
        throw new UnsupportedOperationException();
    }

    @Deprecated
    public final void putAll(Map<? extends K, ? extends V> map) {
        throw new UnsupportedOperationException();
    }

    @Deprecated
    public final void clear() {
        throw new UnsupportedOperationException();
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    public boolean containsKey(@NullableDecl Object obj) {
        return get(obj) != null;
    }

    public boolean containsValue(@NullableDecl Object obj) {
        return ((zzbg) values()).contains(obj);
    }

    public final V getOrDefault(@NullableDecl Object obj, @NullableDecl V v) {
        V v2 = get(obj);
        return v2 != null ? v2 : v;
    }

    public boolean equals(@NullableDecl Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof Map) {
            return entrySet().equals(((Map) obj).entrySet());
        }
        return false;
    }

    public int hashCode() {
        return zzbv.zza((zzbm) entrySet());
    }

    public String toString() {
        int size = size();
        if (size >= 0) {
            StringBuilder append = new StringBuilder((int) Math.min(((long) size) << 3, 1073741824)).append('{');
            boolean z = true;
            for (Map.Entry entry : entrySet()) {
                if (!z) {
                    append.append(", ");
                }
                z = false;
                append.append(entry.getKey()).append('=').append(entry.getValue());
            }
            return append.append('}').toString();
        }
        throw new IllegalArgumentException(new StringBuilder(String.valueOf("size").length() + 40).append("size").append(" cannot be negative but was: ").append(size).toString());
    }

    public /* synthetic */ Set entrySet() {
        zzbm<Map.Entry<K, V>> zzbm = this.zzb;
        if (zzbm != null) {
            return zzbm;
        }
        zzbm<Map.Entry<K, V>> zzb2 = zzb();
        this.zzb = zzb2;
        return zzb2;
    }

    public /* synthetic */ Collection values() {
        zzbg<V> zzbg = this.zzd;
        if (zzbg != null) {
            return zzbg;
        }
        zzbg<V> zzd2 = zzd();
        this.zzd = zzd2;
        return zzd2;
    }

    public /* synthetic */ Set keySet() {
        zzbm<K> zzbm = this.zzc;
        if (zzbm != null) {
            return zzbm;
        }
        zzbm<K> zzc2 = zzc();
        this.zzc = zzc2;
        return zzc2;
    }
}
