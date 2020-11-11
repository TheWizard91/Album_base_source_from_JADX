package com.google.android.gms.internal.measurement;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.RandomAccess;

/* compiled from: com.google.android.gms:play-services-measurement-base@@17.4.4 */
final class zzke {
    private static final Class<?> zza = zzd();
    private static final zzku<?, ?> zzb = zza(false);
    private static final zzku<?, ?> zzc = zza(true);
    private static final zzku<?, ?> zzd = new zzkw();

    public static void zza(Class<?> cls) {
        Class<?> cls2;
        if (!zzib.class.isAssignableFrom(cls) && (cls2 = zza) != null && !cls2.isAssignableFrom(cls)) {
            throw new IllegalArgumentException("Message classes must extend GeneratedMessage or GeneratedMessageLite");
        }
    }

    public static void zza(int i, List<Double> list, zzln zzln, boolean z) throws IOException {
        if (list != null && !list.isEmpty()) {
            zzln.zzg(i, list, z);
        }
    }

    public static void zzb(int i, List<Float> list, zzln zzln, boolean z) throws IOException {
        if (list != null && !list.isEmpty()) {
            zzln.zzf(i, list, z);
        }
    }

    public static void zzc(int i, List<Long> list, zzln zzln, boolean z) throws IOException {
        if (list != null && !list.isEmpty()) {
            zzln.zzc(i, list, z);
        }
    }

    public static void zzd(int i, List<Long> list, zzln zzln, boolean z) throws IOException {
        if (list != null && !list.isEmpty()) {
            zzln.zzd(i, list, z);
        }
    }

    public static void zze(int i, List<Long> list, zzln zzln, boolean z) throws IOException {
        if (list != null && !list.isEmpty()) {
            zzln.zzn(i, list, z);
        }
    }

    public static void zzf(int i, List<Long> list, zzln zzln, boolean z) throws IOException {
        if (list != null && !list.isEmpty()) {
            zzln.zze(i, list, z);
        }
    }

    public static void zzg(int i, List<Long> list, zzln zzln, boolean z) throws IOException {
        if (list != null && !list.isEmpty()) {
            zzln.zzl(i, list, z);
        }
    }

    public static void zzh(int i, List<Integer> list, zzln zzln, boolean z) throws IOException {
        if (list != null && !list.isEmpty()) {
            zzln.zza(i, list, z);
        }
    }

    public static void zzi(int i, List<Integer> list, zzln zzln, boolean z) throws IOException {
        if (list != null && !list.isEmpty()) {
            zzln.zzj(i, list, z);
        }
    }

    public static void zzj(int i, List<Integer> list, zzln zzln, boolean z) throws IOException {
        if (list != null && !list.isEmpty()) {
            zzln.zzm(i, list, z);
        }
    }

    public static void zzk(int i, List<Integer> list, zzln zzln, boolean z) throws IOException {
        if (list != null && !list.isEmpty()) {
            zzln.zzb(i, list, z);
        }
    }

    public static void zzl(int i, List<Integer> list, zzln zzln, boolean z) throws IOException {
        if (list != null && !list.isEmpty()) {
            zzln.zzk(i, list, z);
        }
    }

    public static void zzm(int i, List<Integer> list, zzln zzln, boolean z) throws IOException {
        if (list != null && !list.isEmpty()) {
            zzln.zzh(i, list, z);
        }
    }

    public static void zzn(int i, List<Boolean> list, zzln zzln, boolean z) throws IOException {
        if (list != null && !list.isEmpty()) {
            zzln.zzi(i, list, z);
        }
    }

    public static void zza(int i, List<String> list, zzln zzln) throws IOException {
        if (list != null && !list.isEmpty()) {
            zzln.zza(i, list);
        }
    }

    public static void zzb(int i, List<zzgt> list, zzln zzln) throws IOException {
        if (list != null && !list.isEmpty()) {
            zzln.zzb(i, list);
        }
    }

    public static void zza(int i, List<?> list, zzln zzln, zzkc zzkc) throws IOException {
        if (list != null && !list.isEmpty()) {
            zzln.zza(i, list, zzkc);
        }
    }

    public static void zzb(int i, List<?> list, zzln zzln, zzkc zzkc) throws IOException {
        if (list != null && !list.isEmpty()) {
            zzln.zzb(i, list, zzkc);
        }
    }

    static int zza(List<Long> list) {
        int i;
        int size = list.size();
        int i2 = 0;
        if (size == 0) {
            return 0;
        }
        if (list instanceof zzix) {
            zzix zzix = (zzix) list;
            i = 0;
            while (i2 < size) {
                i += zzhi.zzd(zzix.zzb(i2));
                i2++;
            }
        } else {
            int i3 = 0;
            while (i2 < size) {
                i3 = i + zzhi.zzd(list.get(i2).longValue());
                i2++;
            }
        }
        return i;
    }

    static int zza(int i, List<Long> list, boolean z) {
        if (list.size() == 0) {
            return 0;
        }
        return zza(list) + (list.size() * zzhi.zze(i));
    }

    static int zzb(List<Long> list) {
        int i;
        int size = list.size();
        int i2 = 0;
        if (size == 0) {
            return 0;
        }
        if (list instanceof zzix) {
            zzix zzix = (zzix) list;
            i = 0;
            while (i2 < size) {
                i += zzhi.zze(zzix.zzb(i2));
                i2++;
            }
        } else {
            int i3 = 0;
            while (i2 < size) {
                i3 = i + zzhi.zze(list.get(i2).longValue());
                i2++;
            }
        }
        return i;
    }

    static int zzb(int i, List<Long> list, boolean z) {
        int size = list.size();
        if (size == 0) {
            return 0;
        }
        return zzb(list) + (size * zzhi.zze(i));
    }

    static int zzc(List<Long> list) {
        int i;
        int size = list.size();
        int i2 = 0;
        if (size == 0) {
            return 0;
        }
        if (list instanceof zzix) {
            zzix zzix = (zzix) list;
            i = 0;
            while (i2 < size) {
                i += zzhi.zzf(zzix.zzb(i2));
                i2++;
            }
        } else {
            int i3 = 0;
            while (i2 < size) {
                i3 = i + zzhi.zzf(list.get(i2).longValue());
                i2++;
            }
        }
        return i;
    }

    static int zzc(int i, List<Long> list, boolean z) {
        int size = list.size();
        if (size == 0) {
            return 0;
        }
        return zzc(list) + (size * zzhi.zze(i));
    }

    static int zzd(List<Integer> list) {
        int i;
        int size = list.size();
        int i2 = 0;
        if (size == 0) {
            return 0;
        }
        if (list instanceof zzic) {
            zzic zzic = (zzic) list;
            i = 0;
            while (i2 < size) {
                i += zzhi.zzk(zzic.zzc(i2));
                i2++;
            }
        } else {
            int i3 = 0;
            while (i2 < size) {
                i3 = i + zzhi.zzk(list.get(i2).intValue());
                i2++;
            }
        }
        return i;
    }

    static int zzd(int i, List<Integer> list, boolean z) {
        int size = list.size();
        if (size == 0) {
            return 0;
        }
        return zzd(list) + (size * zzhi.zze(i));
    }

    static int zze(List<Integer> list) {
        int i;
        int size = list.size();
        int i2 = 0;
        if (size == 0) {
            return 0;
        }
        if (list instanceof zzic) {
            zzic zzic = (zzic) list;
            i = 0;
            while (i2 < size) {
                i += zzhi.zzf(zzic.zzc(i2));
                i2++;
            }
        } else {
            int i3 = 0;
            while (i2 < size) {
                i3 = i + zzhi.zzf(list.get(i2).intValue());
                i2++;
            }
        }
        return i;
    }

    static int zze(int i, List<Integer> list, boolean z) {
        int size = list.size();
        if (size == 0) {
            return 0;
        }
        return zze(list) + (size * zzhi.zze(i));
    }

    static int zzf(List<Integer> list) {
        int i;
        int size = list.size();
        int i2 = 0;
        if (size == 0) {
            return 0;
        }
        if (list instanceof zzic) {
            zzic zzic = (zzic) list;
            i = 0;
            while (i2 < size) {
                i += zzhi.zzg(zzic.zzc(i2));
                i2++;
            }
        } else {
            int i3 = 0;
            while (i2 < size) {
                i3 = i + zzhi.zzg(list.get(i2).intValue());
                i2++;
            }
        }
        return i;
    }

    static int zzf(int i, List<Integer> list, boolean z) {
        int size = list.size();
        if (size == 0) {
            return 0;
        }
        return zzf(list) + (size * zzhi.zze(i));
    }

    static int zzg(List<Integer> list) {
        int i;
        int size = list.size();
        int i2 = 0;
        if (size == 0) {
            return 0;
        }
        if (list instanceof zzic) {
            zzic zzic = (zzic) list;
            i = 0;
            while (i2 < size) {
                i += zzhi.zzh(zzic.zzc(i2));
                i2++;
            }
        } else {
            int i3 = 0;
            while (i2 < size) {
                i3 = i + zzhi.zzh(list.get(i2).intValue());
                i2++;
            }
        }
        return i;
    }

    static int zzg(int i, List<Integer> list, boolean z) {
        int size = list.size();
        if (size == 0) {
            return 0;
        }
        return zzg(list) + (size * zzhi.zze(i));
    }

    static int zzh(List<?> list) {
        return list.size() << 2;
    }

    static int zzh(int i, List<?> list, boolean z) {
        int size = list.size();
        if (size == 0) {
            return 0;
        }
        return size * zzhi.zzi(i, 0);
    }

    static int zzi(List<?> list) {
        return list.size() << 3;
    }

    static int zzi(int i, List<?> list, boolean z) {
        int size = list.size();
        if (size == 0) {
            return 0;
        }
        return size * zzhi.zzg(i, 0);
    }

    static int zzj(List<?> list) {
        return list.size();
    }

    static int zzj(int i, List<?> list, boolean z) {
        int size = list.size();
        if (size == 0) {
            return 0;
        }
        return size * zzhi.zzb(i, true);
    }

    static int zza(int i, List<?> list) {
        int i2;
        int i3;
        int size = list.size();
        int i4 = 0;
        if (size == 0) {
            return 0;
        }
        int zze = zzhi.zze(i) * size;
        if (list instanceof zziu) {
            zziu zziu = (zziu) list;
            while (i4 < size) {
                Object zzb2 = zziu.zzb(i4);
                if (zzb2 instanceof zzgt) {
                    i3 = zzhi.zzb((zzgt) zzb2);
                } else {
                    i3 = zzhi.zzb((String) zzb2);
                }
                zze += i3;
                i4++;
            }
        } else {
            while (i4 < size) {
                Object obj = list.get(i4);
                if (obj instanceof zzgt) {
                    i2 = zzhi.zzb((zzgt) obj);
                } else {
                    i2 = zzhi.zzb((String) obj);
                }
                zze += i2;
                i4++;
            }
        }
        return zze;
    }

    static int zza(int i, Object obj, zzkc zzkc) {
        if (obj instanceof zzis) {
            return zzhi.zza(i, (zzis) obj);
        }
        return zzhi.zzb(i, (zzjj) obj, zzkc);
    }

    static int zza(int i, List<?> list, zzkc zzkc) {
        int i2;
        int size = list.size();
        if (size == 0) {
            return 0;
        }
        int zze = zzhi.zze(i) * size;
        for (int i3 = 0; i3 < size; i3++) {
            Object obj = list.get(i3);
            if (obj instanceof zzis) {
                i2 = zzhi.zza((zzis) obj);
            } else {
                i2 = zzhi.zza((zzjj) obj, zzkc);
            }
            zze += i2;
        }
        return zze;
    }

    static int zzb(int i, List<zzgt> list) {
        int size = list.size();
        if (size == 0) {
            return 0;
        }
        int zze = size * zzhi.zze(i);
        for (int i2 = 0; i2 < list.size(); i2++) {
            zze += zzhi.zzb(list.get(i2));
        }
        return zze;
    }

    static int zzb(int i, List<zzjj> list, zzkc zzkc) {
        int size = list.size();
        if (size == 0) {
            return 0;
        }
        int i2 = 0;
        for (int i3 = 0; i3 < size; i3++) {
            i2 += zzhi.zzc(i, list.get(i3), zzkc);
        }
        return i2;
    }

    public static zzku<?, ?> zza() {
        return zzb;
    }

    public static zzku<?, ?> zzb() {
        return zzc;
    }

    public static zzku<?, ?> zzc() {
        return zzd;
    }

    private static zzku<?, ?> zza(boolean z) {
        try {
            Class<?> zze = zze();
            if (zze == null) {
                return null;
            }
            return (zzku) zze.getConstructor(new Class[]{Boolean.TYPE}).newInstance(new Object[]{Boolean.valueOf(z)});
        } catch (Throwable th) {
            return null;
        }
    }

    private static Class<?> zzd() {
        try {
            return Class.forName("com.google.protobuf.GeneratedMessage");
        } catch (Throwable th) {
            return null;
        }
    }

    private static Class<?> zze() {
        try {
            return Class.forName("com.google.protobuf.UnknownFieldSetSchema");
        } catch (Throwable th) {
            return null;
        }
    }

    static boolean zza(Object obj, Object obj2) {
        if (obj != obj2) {
            return obj != null && obj.equals(obj2);
        }
        return true;
    }

    static <T> void zza(zzjg zzjg, T t, T t2, long j) {
        zzla.zza((Object) t, j, zzjg.zza(zzla.zzf(t, j), zzla.zzf(t2, j)));
    }

    static <T, FT extends zzht<FT>> void zza(zzhq<FT> zzhq, T t, T t2) {
        zzhr<FT> zza2 = zzhq.zza((Object) t2);
        if (!zza2.zza.isEmpty()) {
            zzhq.zzb(t).zza(zza2);
        }
    }

    static <T, UT, UB> void zza(zzku<UT, UB> zzku, T t, T t2) {
        zzku.zza((Object) t, zzku.zzc(zzku.zzb(t), zzku.zzb(t2)));
    }

    static <UT, UB> UB zza(int i, List<Integer> list, zzif zzif, UB ub, zzku<UT, UB> zzku) {
        if (zzif == null) {
            return ub;
        }
        if (list instanceof RandomAccess) {
            int size = list.size();
            int i2 = 0;
            for (int i3 = 0; i3 < size; i3++) {
                int intValue = list.get(i3).intValue();
                if (zzif.zza(intValue)) {
                    if (i3 != i2) {
                        list.set(i2, Integer.valueOf(intValue));
                    }
                    i2++;
                } else {
                    ub = zza(i, intValue, ub, zzku);
                }
            }
            if (i2 != size) {
                list.subList(i2, size).clear();
            }
        } else {
            Iterator<Integer> it = list.iterator();
            while (it.hasNext()) {
                int intValue2 = it.next().intValue();
                if (!zzif.zza(intValue2)) {
                    ub = zza(i, intValue2, ub, zzku);
                    it.remove();
                }
            }
        }
        return ub;
    }

    static <UT, UB> UB zza(int i, int i2, UB ub, zzku<UT, UB> zzku) {
        if (ub == null) {
            ub = zzku.zza();
        }
        zzku.zza(ub, i, (long) i2);
        return ub;
    }
}
