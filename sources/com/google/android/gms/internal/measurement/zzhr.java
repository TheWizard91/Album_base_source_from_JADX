package com.google.android.gms.internal.measurement;

import com.google.android.gms.internal.measurement.zzht;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/* compiled from: com.google.android.gms:play-services-measurement-base@@17.4.4 */
final class zzhr<T extends zzht<T>> {
    private static final zzhr zzd = new zzhr(true);
    final zzkd<T, Object> zza;
    private boolean zzb;
    private boolean zzc;

    private zzhr() {
        this.zza = zzkd.zza(16);
    }

    private zzhr(boolean z) {
        this(zzkd.zza(0));
        zzb();
    }

    private zzhr(zzkd<T, Object> zzkd) {
        this.zza = zzkd;
        zzb();
    }

    public static <T extends zzht<T>> zzhr<T> zza() {
        return zzd;
    }

    public final void zzb() {
        if (!this.zzb) {
            this.zza.zza();
            this.zzb = true;
        }
    }

    public final boolean zzc() {
        return this.zzb;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof zzhr)) {
            return false;
        }
        return this.zza.equals(((zzhr) obj).zza);
    }

    public final int hashCode() {
        return this.zza.hashCode();
    }

    public final Iterator<Map.Entry<T, Object>> zzd() {
        if (this.zzc) {
            return new zzip(this.zza.entrySet().iterator());
        }
        return this.zza.entrySet().iterator();
    }

    /* access modifiers changed from: package-private */
    public final Iterator<Map.Entry<T, Object>> zze() {
        if (this.zzc) {
            return new zzip(this.zza.zze().iterator());
        }
        return this.zza.zze().iterator();
    }

    private final Object zza(T t) {
        Object obj = this.zza.get(t);
        if (!(obj instanceof zzio)) {
            return obj;
        }
        zzio zzio = (zzio) obj;
        return zzio.zza();
    }

    private final void zzb(T t, Object obj) {
        if (!t.zzd()) {
            zza(t.zzb(), obj);
        } else if (obj instanceof List) {
            ArrayList arrayList = new ArrayList();
            arrayList.addAll((List) obj);
            ArrayList arrayList2 = arrayList;
            int size = arrayList2.size();
            int i = 0;
            while (i < size) {
                Object obj2 = arrayList2.get(i);
                i++;
                zza(t.zzb(), obj2);
            }
            obj = arrayList;
        } else {
            throw new IllegalArgumentException("Wrong object type used with protocol message reflection.");
        }
        if (obj instanceof zzio) {
            this.zzc = true;
        }
        this.zza.put(t, obj);
    }

    private static void zza(zzlh zzlh, Object obj) {
        zzie.zza(obj);
        boolean z = true;
        switch (zzhu.zza[zzlh.zza().ordinal()]) {
            case 1:
                z = obj instanceof Integer;
                break;
            case 2:
                z = obj instanceof Long;
                break;
            case 3:
                z = obj instanceof Float;
                break;
            case 4:
                z = obj instanceof Double;
                break;
            case 5:
                z = obj instanceof Boolean;
                break;
            case 6:
                z = obj instanceof String;
                break;
            case 7:
                if (!(obj instanceof zzgt) && !(obj instanceof byte[])) {
                    z = false;
                    break;
                }
            case 8:
                if (!(obj instanceof Integer) && !(obj instanceof zzid)) {
                    z = false;
                    break;
                }
            case 9:
                if (!(obj instanceof zzjj) && !(obj instanceof zzio)) {
                    z = false;
                    break;
                }
            default:
                z = false;
                break;
        }
        if (!z) {
            throw new IllegalArgumentException("Wrong object type used with protocol message reflection.");
        }
    }

    public final boolean zzf() {
        for (int i = 0; i < this.zza.zzc(); i++) {
            if (!zza(this.zza.zzb(i))) {
                return false;
            }
        }
        for (Map.Entry<T, Object> zza2 : this.zza.zzd()) {
            if (!zza(zza2)) {
                return false;
            }
        }
        return true;
    }

    private static <T extends zzht<T>> boolean zza(Map.Entry<T, Object> entry) {
        zzht zzht = (zzht) entry.getKey();
        if (zzht.zzc() == zzlo.MESSAGE) {
            if (zzht.zzd()) {
                for (zzjj g_ : (List) entry.getValue()) {
                    if (!g_.mo38045g_()) {
                        return false;
                    }
                }
            } else {
                Object value = entry.getValue();
                if (value instanceof zzjj) {
                    if (!((zzjj) value).mo38045g_()) {
                        return false;
                    }
                } else if (value instanceof zzio) {
                    return true;
                } else {
                    throw new IllegalArgumentException("Wrong object type used with protocol message reflection.");
                }
            }
        }
        return true;
    }

    public final void zza(zzhr<T> zzhr) {
        for (int i = 0; i < zzhr.zza.zzc(); i++) {
            zzb(zzhr.zza.zzb(i));
        }
        for (Map.Entry<T, Object> zzb2 : zzhr.zza.zzd()) {
            zzb(zzb2);
        }
    }

    private static Object zza(Object obj) {
        if (obj instanceof zzjs) {
            return ((zzjs) obj).zza();
        }
        if (!(obj instanceof byte[])) {
            return obj;
        }
        byte[] bArr = (byte[]) obj;
        byte[] bArr2 = new byte[bArr.length];
        System.arraycopy(bArr, 0, bArr2, 0, bArr.length);
        return bArr2;
    }

    private final void zzb(Map.Entry<T, Object> entry) {
        Object obj;
        zzht zzht = (zzht) entry.getKey();
        Object value = entry.getValue();
        if (value instanceof zzio) {
            zzio zzio = (zzio) value;
            value = zzio.zza();
        }
        if (zzht.zzd()) {
            Object zza2 = zza(zzht);
            if (zza2 == null) {
                zza2 = new ArrayList();
            }
            for (Object zza3 : (List) value) {
                ((List) zza2).add(zza(zza3));
            }
            this.zza.put(zzht, zza2);
        } else if (zzht.zzc() == zzlo.MESSAGE) {
            Object zza4 = zza(zzht);
            if (zza4 == null) {
                this.zza.put(zzht, zza(value));
                return;
            }
            if (zza4 instanceof zzjs) {
                obj = zzht.zza((zzjs) zza4, (zzjs) value);
            } else {
                obj = zzht.zza(((zzjj) zza4).zzbq(), (zzjj) value).zzv();
            }
            this.zza.put(zzht, obj);
        } else {
            this.zza.put(zzht, zza(value));
        }
    }

    static void zza(zzhi zzhi, zzlh zzlh, int i, Object obj) throws IOException {
        if (zzlh == zzlh.GROUP) {
            zzjj zzjj = (zzjj) obj;
            zzie.zza(zzjj);
            zzhi.zza(i, 3);
            zzjj.zza(zzhi);
            zzhi.zza(i, 4);
            return;
        }
        zzhi.zza(i, zzlh.zzb());
        switch (zzhu.zzb[zzlh.ordinal()]) {
            case 1:
                zzhi.zza(((Double) obj).doubleValue());
                return;
            case 2:
                zzhi.zza(((Float) obj).floatValue());
                return;
            case 3:
                zzhi.zza(((Long) obj).longValue());
                return;
            case 4:
                zzhi.zza(((Long) obj).longValue());
                return;
            case 5:
                zzhi.zza(((Integer) obj).intValue());
                return;
            case 6:
                zzhi.zzc(((Long) obj).longValue());
                return;
            case 7:
                zzhi.zzd(((Integer) obj).intValue());
                return;
            case 8:
                zzhi.zza(((Boolean) obj).booleanValue());
                return;
            case 9:
                ((zzjj) obj).zza(zzhi);
                return;
            case 10:
                zzhi.zza((zzjj) obj);
                return;
            case 11:
                if (obj instanceof zzgt) {
                    zzhi.zza((zzgt) obj);
                    return;
                } else {
                    zzhi.zza((String) obj);
                    return;
                }
            case 12:
                if (obj instanceof zzgt) {
                    zzhi.zza((zzgt) obj);
                    return;
                }
                byte[] bArr = (byte[]) obj;
                zzhi.zzb(bArr, 0, bArr.length);
                return;
            case 13:
                zzhi.zzb(((Integer) obj).intValue());
                return;
            case 14:
                zzhi.zzd(((Integer) obj).intValue());
                return;
            case 15:
                zzhi.zzc(((Long) obj).longValue());
                return;
            case 16:
                zzhi.zzc(((Integer) obj).intValue());
                return;
            case 17:
                zzhi.zzb(((Long) obj).longValue());
                return;
            case 18:
                if (obj instanceof zzid) {
                    zzhi.zza(((zzid) obj).zza());
                    return;
                } else {
                    zzhi.zza(((Integer) obj).intValue());
                    return;
                }
            default:
                return;
        }
    }

    public final int zzg() {
        int i = 0;
        for (int i2 = 0; i2 < this.zza.zzc(); i2++) {
            i += zzc(this.zza.zzb(i2));
        }
        for (Map.Entry<T, Object> zzc2 : this.zza.zzd()) {
            i += zzc(zzc2);
        }
        return i;
    }

    private static int zzc(Map.Entry<T, Object> entry) {
        zzht zzht = (zzht) entry.getKey();
        Object value = entry.getValue();
        if (zzht.zzc() != zzlo.MESSAGE || zzht.zzd() || zzht.zze()) {
            return zza((zzht<?>) zzht, value);
        }
        if (value instanceof zzio) {
            return zzhi.zzb(((zzht) entry.getKey()).zza(), (zzis) (zzio) value);
        }
        return zzhi.zzb(((zzht) entry.getKey()).zza(), (zzjj) value);
    }

    static int zza(zzlh zzlh, int i, Object obj) {
        int zze = zzhi.zze(i);
        if (zzlh == zzlh.GROUP) {
            zzie.zza((zzjj) obj);
            zze <<= 1;
        }
        return zze + zzb(zzlh, obj);
    }

    private static int zzb(zzlh zzlh, Object obj) {
        switch (zzhu.zzb[zzlh.ordinal()]) {
            case 1:
                return zzhi.zzb(((Double) obj).doubleValue());
            case 2:
                return zzhi.zzb(((Float) obj).floatValue());
            case 3:
                return zzhi.zzd(((Long) obj).longValue());
            case 4:
                return zzhi.zze(((Long) obj).longValue());
            case 5:
                return zzhi.zzf(((Integer) obj).intValue());
            case 6:
                return zzhi.zzg(((Long) obj).longValue());
            case 7:
                return zzhi.zzi(((Integer) obj).intValue());
            case 8:
                return zzhi.zzb(((Boolean) obj).booleanValue());
            case 9:
                return zzhi.zzc((zzjj) obj);
            case 10:
                if (obj instanceof zzio) {
                    return zzhi.zza((zzis) (zzio) obj);
                }
                return zzhi.zzb((zzjj) obj);
            case 11:
                if (obj instanceof zzgt) {
                    return zzhi.zzb((zzgt) obj);
                }
                return zzhi.zzb((String) obj);
            case 12:
                if (obj instanceof zzgt) {
                    return zzhi.zzb((zzgt) obj);
                }
                return zzhi.zzb((byte[]) obj);
            case 13:
                return zzhi.zzg(((Integer) obj).intValue());
            case 14:
                return zzhi.zzj(((Integer) obj).intValue());
            case 15:
                return zzhi.zzh(((Long) obj).longValue());
            case 16:
                return zzhi.zzh(((Integer) obj).intValue());
            case 17:
                return zzhi.zzf(((Long) obj).longValue());
            case 18:
                if (obj instanceof zzid) {
                    return zzhi.zzk(((zzid) obj).zza());
                }
                return zzhi.zzk(((Integer) obj).intValue());
            default:
                throw new RuntimeException("There is no way to get here, but the compiler thinks otherwise.");
        }
    }

    public static int zza(zzht<?> zzht, Object obj) {
        zzlh zzb2 = zzht.zzb();
        int zza2 = zzht.zza();
        if (!zzht.zzd()) {
            return zza(zzb2, zza2, obj);
        }
        int i = 0;
        if (zzht.zze()) {
            for (Object zzb3 : (List) obj) {
                i += zzb(zzb2, zzb3);
            }
            return zzhi.zze(zza2) + i + zzhi.zzl(i);
        }
        for (Object zza3 : (List) obj) {
            i += zza(zzb2, zza2, zza3);
        }
        return i;
    }

    public final /* synthetic */ Object clone() throws CloneNotSupportedException {
        zzhr zzhr = new zzhr();
        for (int i = 0; i < this.zza.zzc(); i++) {
            Map.Entry<T, Object> zzb2 = this.zza.zzb(i);
            zzhr.zzb((zzht) zzb2.getKey(), zzb2.getValue());
        }
        for (Map.Entry next : this.zza.zzd()) {
            zzhr.zzb((zzht) next.getKey(), next.getValue());
        }
        zzhr.zzc = this.zzc;
        return zzhr;
    }
}
