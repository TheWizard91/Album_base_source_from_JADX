package com.google.android.gms.measurement.internal;

import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.internal.measurement.zzbu;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

/* compiled from: com.google.android.gms:play-services-measurement@@17.4.4 */
abstract class zzv {
    String zza;
    int zzb;
    Boolean zzc;
    Boolean zzd;
    Long zze;
    Long zzf;

    zzv(String str, int i) {
        this.zza = str;
        this.zzb = i;
    }

    /* access modifiers changed from: package-private */
    public abstract int zza();

    /* access modifiers changed from: package-private */
    public abstract boolean zzb();

    /* access modifiers changed from: package-private */
    public abstract boolean zzc();

    static Boolean zza(Boolean bool, boolean z) {
        if (bool == null) {
            return null;
        }
        return Boolean.valueOf(bool.booleanValue() != z);
    }

    static Boolean zza(String str, zzbu.zzf zzf2, zzez zzez) {
        String str2;
        List<String> list;
        String str3;
        Preconditions.checkNotNull(zzf2);
        if (str == null || !zzf2.zza() || zzf2.zzb() == zzbu.zzf.zza.UNKNOWN_MATCH_TYPE) {
            return null;
        }
        if (zzf2.zzb() == zzbu.zzf.zza.IN_LIST) {
            if (zzf2.zzh() == 0) {
                return null;
            }
        } else if (!zzf2.zzc()) {
            return null;
        }
        zzbu.zzf.zza zzb2 = zzf2.zzb();
        boolean zzf3 = zzf2.zzf();
        if (zzf3 || zzb2 == zzbu.zzf.zza.REGEXP || zzb2 == zzbu.zzf.zza.IN_LIST) {
            str2 = zzf2.zzd();
        } else {
            str2 = zzf2.zzd().toUpperCase(Locale.ENGLISH);
        }
        if (zzf2.zzh() == 0) {
            list = null;
        } else {
            List<String> zzg = zzf2.zzg();
            if (zzf3) {
                list = zzg;
            } else {
                ArrayList arrayList = new ArrayList(zzg.size());
                for (String upperCase : zzg) {
                    arrayList.add(upperCase.toUpperCase(Locale.ENGLISH));
                }
                list = Collections.unmodifiableList(arrayList);
            }
        }
        if (zzb2 == zzbu.zzf.zza.REGEXP) {
            str3 = str2;
        } else {
            str3 = null;
        }
        return zza(str, zzb2, zzf3, str2, list, str3, zzez);
    }

    private static Boolean zza(String str, zzbu.zzf.zza zza2, boolean z, String str2, List<String> list, String str3, zzez zzez) {
        if (str == null) {
            return null;
        }
        if (zza2 == zzbu.zzf.zza.IN_LIST) {
            if (list == null || list.size() == 0) {
                return null;
            }
        } else if (str2 == null) {
            return null;
        }
        if (!z && zza2 != zzbu.zzf.zza.REGEXP) {
            str = str.toUpperCase(Locale.ENGLISH);
        }
        switch (zzr.zza[zza2.ordinal()]) {
            case 1:
                try {
                    return Boolean.valueOf(Pattern.compile(str3, z ? 0 : 66).matcher(str).matches());
                } catch (PatternSyntaxException e) {
                    if (zzez != null) {
                        zzez.zzi().zza("Invalid regular expression in REGEXP audience filter. expression", str3);
                    }
                    return null;
                }
            case 2:
                return Boolean.valueOf(str.startsWith(str2));
            case 3:
                return Boolean.valueOf(str.endsWith(str2));
            case 4:
                return Boolean.valueOf(str.contains(str2));
            case 5:
                return Boolean.valueOf(str.equals(str2));
            case 6:
                return Boolean.valueOf(list.contains(str));
            default:
                return null;
        }
    }

    static Boolean zza(long j, zzbu.zzd zzd2) {
        try {
            return zza(new BigDecimal(j), zzd2, 0.0d);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    static Boolean zza(double d, zzbu.zzd zzd2) {
        try {
            return zza(new BigDecimal(d), zzd2, Math.ulp(d));
        } catch (NumberFormatException e) {
            return null;
        }
    }

    static Boolean zza(String str, zzbu.zzd zzd2) {
        if (!zzks.zza(str)) {
            return null;
        }
        try {
            return zza(new BigDecimal(str), zzd2, 0.0d);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:36:0x008c, code lost:
        if (r2 != null) goto L_0x008e;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static java.lang.Boolean zza(java.math.BigDecimal r9, com.google.android.gms.internal.measurement.zzbu.zzd r10, double r11) {
        /*
            com.google.android.gms.common.internal.Preconditions.checkNotNull(r10)
            boolean r0 = r10.zza()
            r1 = 0
            if (r0 == 0) goto L_0x011e
            com.google.android.gms.internal.measurement.zzbu$zzd$zzb r0 = r10.zzb()
            com.google.android.gms.internal.measurement.zzbu$zzd$zzb r2 = com.google.android.gms.internal.measurement.zzbu.zzd.zzb.UNKNOWN_COMPARISON_TYPE
            if (r0 != r2) goto L_0x0014
            goto L_0x011e
        L_0x0014:
            com.google.android.gms.internal.measurement.zzbu$zzd$zzb r0 = r10.zzb()
            com.google.android.gms.internal.measurement.zzbu$zzd$zzb r2 = com.google.android.gms.internal.measurement.zzbu.zzd.zzb.BETWEEN
            if (r0 != r2) goto L_0x0029
            boolean r0 = r10.zzg()
            if (r0 == 0) goto L_0x0028
            boolean r0 = r10.zzi()
            if (r0 != 0) goto L_0x0030
        L_0x0028:
            return r1
        L_0x0029:
            boolean r0 = r10.zze()
            if (r0 != 0) goto L_0x0030
            return r1
        L_0x0030:
            com.google.android.gms.internal.measurement.zzbu$zzd$zzb r0 = r10.zzb()
            com.google.android.gms.internal.measurement.zzbu$zzd$zzb r2 = r10.zzb()
            com.google.android.gms.internal.measurement.zzbu$zzd$zzb r3 = com.google.android.gms.internal.measurement.zzbu.zzd.zzb.BETWEEN
            if (r2 != r3) goto L_0x006c
            java.lang.String r2 = r10.zzh()
            boolean r2 = com.google.android.gms.measurement.internal.zzks.zza((java.lang.String) r2)
            if (r2 == 0) goto L_0x006b
            java.lang.String r2 = r10.zzj()
            boolean r2 = com.google.android.gms.measurement.internal.zzks.zza((java.lang.String) r2)
            if (r2 != 0) goto L_0x0054
            goto L_0x006b
        L_0x0054:
            java.math.BigDecimal r2 = new java.math.BigDecimal     // Catch:{ NumberFormatException -> 0x0069 }
            java.lang.String r3 = r10.zzh()     // Catch:{ NumberFormatException -> 0x0069 }
            r2.<init>(r3)     // Catch:{ NumberFormatException -> 0x0069 }
            java.math.BigDecimal r3 = new java.math.BigDecimal     // Catch:{ NumberFormatException -> 0x0069 }
            java.lang.String r10 = r10.zzj()     // Catch:{ NumberFormatException -> 0x0069 }
            r3.<init>(r10)     // Catch:{ NumberFormatException -> 0x0069 }
            r10 = r2
            r2 = r1
            goto L_0x0082
        L_0x0069:
            r9 = move-exception
            return r1
        L_0x006b:
            return r1
        L_0x006c:
            java.lang.String r2 = r10.zzf()
            boolean r2 = com.google.android.gms.measurement.internal.zzks.zza((java.lang.String) r2)
            if (r2 != 0) goto L_0x0077
            return r1
        L_0x0077:
            java.math.BigDecimal r2 = new java.math.BigDecimal     // Catch:{ NumberFormatException -> 0x011c }
            java.lang.String r10 = r10.zzf()     // Catch:{ NumberFormatException -> 0x011c }
            r2.<init>(r10)     // Catch:{ NumberFormatException -> 0x011c }
            r10 = r1
            r3 = r10
        L_0x0082:
            com.google.android.gms.internal.measurement.zzbu$zzd$zzb r4 = com.google.android.gms.internal.measurement.zzbu.zzd.zzb.BETWEEN
            if (r0 != r4) goto L_0x008c
            if (r10 == 0) goto L_0x008b
            goto L_0x008e
        L_0x008b:
            return r1
        L_0x008c:
            if (r2 == 0) goto L_0x011a
        L_0x008e:
            int[] r4 = com.google.android.gms.measurement.internal.zzr.zzb
            int r0 = r0.ordinal()
            r0 = r4[r0]
            r4 = -1
            r5 = 0
            r6 = 1
            if (r0 == r6) goto L_0x010e
            r7 = 2
            if (r0 == r7) goto L_0x0102
            r8 = 3
            if (r0 == r8) goto L_0x00b8
            r11 = 4
            if (r0 == r11) goto L_0x00a6
            goto L_0x011a
        L_0x00a6:
            int r10 = r9.compareTo(r10)
            if (r10 == r4) goto L_0x00b3
            int r9 = r9.compareTo(r3)
            if (r9 == r6) goto L_0x00b3
            r5 = r6
        L_0x00b3:
            java.lang.Boolean r9 = java.lang.Boolean.valueOf(r5)
            return r9
        L_0x00b8:
            r0 = 0
            int r10 = (r11 > r0 ? 1 : (r11 == r0 ? 0 : -1))
            if (r10 == 0) goto L_0x00f6
            java.math.BigDecimal r10 = new java.math.BigDecimal
            r10.<init>(r11)
            java.math.BigDecimal r0 = new java.math.BigDecimal
            r0.<init>(r7)
            java.math.BigDecimal r10 = r10.multiply(r0)
            java.math.BigDecimal r10 = r2.subtract(r10)
            int r10 = r9.compareTo(r10)
            if (r10 != r6) goto L_0x00f0
            java.math.BigDecimal r10 = new java.math.BigDecimal
            r10.<init>(r11)
            java.math.BigDecimal r11 = new java.math.BigDecimal
            r11.<init>(r7)
            java.math.BigDecimal r10 = r10.multiply(r11)
            java.math.BigDecimal r10 = r2.add(r10)
            int r9 = r9.compareTo(r10)
            if (r9 != r4) goto L_0x00f0
            r5 = r6
            goto L_0x00f1
        L_0x00f0:
        L_0x00f1:
            java.lang.Boolean r9 = java.lang.Boolean.valueOf(r5)
            return r9
        L_0x00f6:
            int r9 = r9.compareTo(r2)
            if (r9 != 0) goto L_0x00fd
            r5 = r6
        L_0x00fd:
            java.lang.Boolean r9 = java.lang.Boolean.valueOf(r5)
            return r9
        L_0x0102:
            int r9 = r9.compareTo(r2)
            if (r9 != r6) goto L_0x0109
            r5 = r6
        L_0x0109:
            java.lang.Boolean r9 = java.lang.Boolean.valueOf(r5)
            return r9
        L_0x010e:
            int r9 = r9.compareTo(r2)
            if (r9 != r4) goto L_0x0115
            r5 = r6
        L_0x0115:
            java.lang.Boolean r9 = java.lang.Boolean.valueOf(r5)
            return r9
        L_0x011a:
            return r1
        L_0x011c:
            r9 = move-exception
            return r1
        L_0x011e:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.measurement.internal.zzv.zza(java.math.BigDecimal, com.google.android.gms.internal.measurement.zzbu$zzd, double):java.lang.Boolean");
    }
}
