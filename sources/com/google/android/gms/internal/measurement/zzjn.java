package com.google.android.gms.internal.measurement;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import sun.misc.Unsafe;

/* compiled from: com.google.android.gms:play-services-measurement-base@@17.4.4 */
final class zzjn<T> implements zzkc<T> {
    private static final int[] zza = new int[0];
    private static final Unsafe zzb = zzla.zzc();
    private final int[] zzc;
    private final Object[] zzd;
    private final int zze;
    private final int zzf;
    private final zzjj zzg;
    private final boolean zzh;
    private final boolean zzi;
    private final boolean zzj;
    private final boolean zzk;
    private final int[] zzl;
    private final int zzm;
    private final int zzn;
    private final zzjr zzo;
    private final zzit zzp;
    private final zzku<?, ?> zzq;
    private final zzhq<?> zzr;
    private final zzjg zzs;

    private zzjn(int[] iArr, Object[] objArr, int i, int i2, zzjj zzjj, boolean z, boolean z2, int[] iArr2, int i3, int i4, zzjr zzjr, zzit zzit, zzku<?, ?> zzku, zzhq<?> zzhq, zzjg zzjg) {
        this.zzc = iArr;
        this.zzd = objArr;
        this.zze = i;
        this.zzf = i2;
        this.zzi = zzjj instanceof zzib;
        this.zzj = z;
        this.zzh = zzhq != null && zzhq.zza(zzjj);
        this.zzk = false;
        this.zzl = iArr2;
        this.zzm = i3;
        this.zzn = i4;
        this.zzo = zzjr;
        this.zzp = zzit;
        this.zzq = zzku;
        this.zzr = zzhq;
        this.zzg = zzjj;
        this.zzs = zzjg;
    }

    /* JADX WARNING: Removed duplicated region for block: B:155:0x0349  */
    /* JADX WARNING: Removed duplicated region for block: B:171:0x03a3  */
    /* JADX WARNING: Removed duplicated region for block: B:175:0x03b0 A[ADDED_TO_REGION] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static <T> com.google.android.gms.internal.measurement.zzjn<T> zza(java.lang.Class<T> r33, com.google.android.gms.internal.measurement.zzjh r34, com.google.android.gms.internal.measurement.zzjr r35, com.google.android.gms.internal.measurement.zzit r36, com.google.android.gms.internal.measurement.zzku<?, ?> r37, com.google.android.gms.internal.measurement.zzhq<?> r38, com.google.android.gms.internal.measurement.zzjg r39) {
        /*
            r0 = r34
            boolean r1 = r0 instanceof com.google.android.gms.internal.measurement.zzka
            if (r1 == 0) goto L_0x0424
            com.google.android.gms.internal.measurement.zzka r0 = (com.google.android.gms.internal.measurement.zzka) r0
            int r1 = r0.zza()
            int r2 = com.google.android.gms.internal.measurement.zzib.zzf.zzi
            r3 = 0
            if (r1 != r2) goto L_0x0013
            r11 = 1
            goto L_0x0014
        L_0x0013:
            r11 = r3
        L_0x0014:
            java.lang.String r1 = r0.zzd()
            int r2 = r1.length()
            char r5 = r1.charAt(r3)
            r6 = 55296(0xd800, float:7.7486E-41)
            if (r5 < r6) goto L_0x0033
            r5 = 1
        L_0x0028:
            int r7 = r5 + 1
            char r5 = r1.charAt(r5)
            if (r5 < r6) goto L_0x0034
            r5 = r7
            goto L_0x0028
        L_0x0033:
            r7 = 1
        L_0x0034:
            int r5 = r7 + 1
            char r7 = r1.charAt(r7)
            if (r7 < r6) goto L_0x0053
            r7 = r7 & 8191(0x1fff, float:1.1478E-41)
            r9 = 13
        L_0x0040:
            int r10 = r5 + 1
            char r5 = r1.charAt(r5)
            if (r5 < r6) goto L_0x0050
            r5 = r5 & 8191(0x1fff, float:1.1478E-41)
            int r5 = r5 << r9
            r7 = r7 | r5
            int r9 = r9 + 13
            r5 = r10
            goto L_0x0040
        L_0x0050:
            int r5 = r5 << r9
            r7 = r7 | r5
            r5 = r10
        L_0x0053:
            if (r7 != 0) goto L_0x0067
            int[] r7 = zza
            r9 = r3
            r10 = r9
            r12 = r10
            r13 = r12
            r15 = r13
            r14 = r7
            r7 = r15
            goto L_0x0180
        L_0x0067:
            int r7 = r5 + 1
            char r5 = r1.charAt(r5)
            if (r5 < r6) goto L_0x0086
            r5 = r5 & 8191(0x1fff, float:1.1478E-41)
            r9 = 13
        L_0x0073:
            int r10 = r7 + 1
            char r7 = r1.charAt(r7)
            if (r7 < r6) goto L_0x0083
            r7 = r7 & 8191(0x1fff, float:1.1478E-41)
            int r7 = r7 << r9
            r5 = r5 | r7
            int r9 = r9 + 13
            r7 = r10
            goto L_0x0073
        L_0x0083:
            int r7 = r7 << r9
            r5 = r5 | r7
            r7 = r10
        L_0x0086:
            int r9 = r7 + 1
            char r7 = r1.charAt(r7)
            if (r7 < r6) goto L_0x00a6
            r7 = r7 & 8191(0x1fff, float:1.1478E-41)
            r10 = 13
        L_0x0093:
            int r12 = r9 + 1
            char r9 = r1.charAt(r9)
            if (r9 < r6) goto L_0x00a3
            r9 = r9 & 8191(0x1fff, float:1.1478E-41)
            int r9 = r9 << r10
            r7 = r7 | r9
            int r10 = r10 + 13
            r9 = r12
            goto L_0x0093
        L_0x00a3:
            int r9 = r9 << r10
            r7 = r7 | r9
            r9 = r12
        L_0x00a6:
            int r10 = r9 + 1
            char r9 = r1.charAt(r9)
            if (r9 < r6) goto L_0x00c6
            r9 = r9 & 8191(0x1fff, float:1.1478E-41)
            r12 = 13
        L_0x00b3:
            int r13 = r10 + 1
            char r10 = r1.charAt(r10)
            if (r10 < r6) goto L_0x00c3
            r10 = r10 & 8191(0x1fff, float:1.1478E-41)
            int r10 = r10 << r12
            r9 = r9 | r10
            int r12 = r12 + 13
            r10 = r13
            goto L_0x00b3
        L_0x00c3:
            int r10 = r10 << r12
            r9 = r9 | r10
            r10 = r13
        L_0x00c6:
            int r12 = r10 + 1
            char r10 = r1.charAt(r10)
            if (r10 < r6) goto L_0x00e6
            r10 = r10 & 8191(0x1fff, float:1.1478E-41)
            r13 = 13
        L_0x00d3:
            int r14 = r12 + 1
            char r12 = r1.charAt(r12)
            if (r12 < r6) goto L_0x00e3
            r12 = r12 & 8191(0x1fff, float:1.1478E-41)
            int r12 = r12 << r13
            r10 = r10 | r12
            int r13 = r13 + 13
            r12 = r14
            goto L_0x00d3
        L_0x00e3:
            int r12 = r12 << r13
            r10 = r10 | r12
            r12 = r14
        L_0x00e6:
            int r13 = r12 + 1
            char r12 = r1.charAt(r12)
            if (r12 < r6) goto L_0x0106
            r12 = r12 & 8191(0x1fff, float:1.1478E-41)
            r14 = 13
        L_0x00f3:
            int r15 = r13 + 1
            char r13 = r1.charAt(r13)
            if (r13 < r6) goto L_0x0103
            r13 = r13 & 8191(0x1fff, float:1.1478E-41)
            int r13 = r13 << r14
            r12 = r12 | r13
            int r14 = r14 + 13
            r13 = r15
            goto L_0x00f3
        L_0x0103:
            int r13 = r13 << r14
            r12 = r12 | r13
            r13 = r15
        L_0x0106:
            int r14 = r13 + 1
            char r13 = r1.charAt(r13)
            if (r13 < r6) goto L_0x0128
            r13 = r13 & 8191(0x1fff, float:1.1478E-41)
            r15 = 13
        L_0x0113:
            int r16 = r14 + 1
            char r14 = r1.charAt(r14)
            if (r14 < r6) goto L_0x0124
            r14 = r14 & 8191(0x1fff, float:1.1478E-41)
            int r14 = r14 << r15
            r13 = r13 | r14
            int r15 = r15 + 13
            r14 = r16
            goto L_0x0113
        L_0x0124:
            int r14 = r14 << r15
            r13 = r13 | r14
            r14 = r16
        L_0x0128:
            int r15 = r14 + 1
            char r14 = r1.charAt(r14)
            if (r14 < r6) goto L_0x014c
            r14 = r14 & 8191(0x1fff, float:1.1478E-41)
            r16 = 13
        L_0x0135:
            int r17 = r15 + 1
            char r15 = r1.charAt(r15)
            if (r15 < r6) goto L_0x0147
            r15 = r15 & 8191(0x1fff, float:1.1478E-41)
            int r15 = r15 << r16
            r14 = r14 | r15
            int r16 = r16 + 13
            r15 = r17
            goto L_0x0135
        L_0x0147:
            int r15 = r15 << r16
            r14 = r14 | r15
            r15 = r17
        L_0x014c:
            int r16 = r15 + 1
            char r15 = r1.charAt(r15)
            if (r15 < r6) goto L_0x0172
            r15 = r15 & 8191(0x1fff, float:1.1478E-41)
            r3 = r16
            r16 = 13
        L_0x015b:
            int r17 = r3 + 1
            char r3 = r1.charAt(r3)
            if (r3 < r6) goto L_0x016d
            r3 = r3 & 8191(0x1fff, float:1.1478E-41)
            int r3 = r3 << r16
            r15 = r15 | r3
            int r16 = r16 + 13
            r3 = r17
            goto L_0x015b
        L_0x016d:
            int r3 = r3 << r16
            r15 = r15 | r3
            r16 = r17
        L_0x0172:
            int r3 = r15 + r13
            int r3 = r3 + r14
            int[] r3 = new int[r3]
            int r14 = r5 << 1
            int r14 = r14 + r7
            r7 = r14
            r14 = r3
            r3 = r5
            r5 = r16
        L_0x0180:
            sun.misc.Unsafe r8 = zzb
            java.lang.Object[] r16 = r0.zze()
            com.google.android.gms.internal.measurement.zzjj r17 = r0.zzc()
            java.lang.Class r6 = r17.getClass()
            int r4 = r12 * 3
            int[] r4 = new int[r4]
            r17 = 1
            int r12 = r12 << 1
            java.lang.Object[] r12 = new java.lang.Object[r12]
            int r19 = r15 + r13
            r21 = r15
            r22 = r19
            r13 = 0
            r20 = 0
        L_0x01a3:
            if (r5 >= r2) goto L_0x03f9
            int r23 = r5 + 1
            char r5 = r1.charAt(r5)
            r24 = r2
            r2 = 55296(0xd800, float:7.7486E-41)
            if (r5 < r2) goto L_0x01d7
            r5 = r5 & 8191(0x1fff, float:1.1478E-41)
            r2 = r23
            r23 = 13
        L_0x01b8:
            int r25 = r2 + 1
            char r2 = r1.charAt(r2)
            r26 = r15
            r15 = 55296(0xd800, float:7.7486E-41)
            if (r2 < r15) goto L_0x01d1
            r2 = r2 & 8191(0x1fff, float:1.1478E-41)
            int r2 = r2 << r23
            r5 = r5 | r2
            int r23 = r23 + 13
            r2 = r25
            r15 = r26
            goto L_0x01b8
        L_0x01d1:
            int r2 = r2 << r23
            r5 = r5 | r2
            r2 = r25
            goto L_0x01db
        L_0x01d7:
            r26 = r15
            r2 = r23
        L_0x01db:
            int r15 = r2 + 1
            char r2 = r1.charAt(r2)
            r23 = r15
            r15 = 55296(0xd800, float:7.7486E-41)
            if (r2 < r15) goto L_0x020e
            r2 = r2 & 8191(0x1fff, float:1.1478E-41)
            r15 = r23
            r23 = 13
        L_0x01ef:
            int r25 = r15 + 1
            char r15 = r1.charAt(r15)
            r27 = r10
            r10 = 55296(0xd800, float:7.7486E-41)
            if (r15 < r10) goto L_0x0208
            r10 = r15 & 8191(0x1fff, float:1.1478E-41)
            int r10 = r10 << r23
            r2 = r2 | r10
            int r23 = r23 + 13
            r15 = r25
            r10 = r27
            goto L_0x01ef
        L_0x0208:
            int r10 = r15 << r23
            r2 = r2 | r10
            r15 = r25
            goto L_0x0212
        L_0x020e:
            r27 = r10
            r15 = r23
        L_0x0212:
            r10 = r2 & 255(0xff, float:3.57E-43)
            r23 = r9
            r9 = r2 & 1024(0x400, float:1.435E-42)
            if (r9 == 0) goto L_0x0220
            int r9 = r13 + 1
            r14[r13] = r20
            r13 = r9
        L_0x0220:
            r9 = 51
            r28 = r13
            if (r10 < r9) goto L_0x02bf
            int r9 = r15 + 1
            char r15 = r1.charAt(r15)
            r13 = 55296(0xd800, float:7.7486E-41)
            if (r15 < r13) goto L_0x024f
            r15 = r15 & 8191(0x1fff, float:1.1478E-41)
            r29 = 13
        L_0x0235:
            int r30 = r9 + 1
            char r9 = r1.charAt(r9)
            if (r9 < r13) goto L_0x024a
            r9 = r9 & 8191(0x1fff, float:1.1478E-41)
            int r9 = r9 << r29
            r15 = r15 | r9
            int r29 = r29 + 13
            r9 = r30
            r13 = 55296(0xd800, float:7.7486E-41)
            goto L_0x0235
        L_0x024a:
            int r9 = r9 << r29
            r15 = r15 | r9
            r9 = r30
        L_0x024f:
            int r13 = r10 + -51
            r29 = r9
            r9 = 9
            if (r13 == r9) goto L_0x0273
            r9 = 17
            if (r13 != r9) goto L_0x025d
            goto L_0x0273
        L_0x025d:
            r9 = 12
            if (r13 != r9) goto L_0x0271
            if (r11 != 0) goto L_0x0271
            int r9 = r20 / 3
            r13 = 1
            int r9 = r9 << r13
            int r9 = r9 + r13
            int r13 = r7 + 1
            r7 = r16[r7]
            r12[r9] = r7
            r7 = r13
            r13 = 1
            goto L_0x0280
        L_0x0271:
            r13 = 1
            goto L_0x0280
        L_0x0273:
            int r9 = r20 / 3
            r13 = 1
            int r9 = r9 << r13
            int r9 = r9 + r13
            int r17 = r7 + 1
            r7 = r16[r7]
            r12[r9] = r7
            r7 = r17
        L_0x0280:
            int r9 = r15 << 1
            r13 = r16[r9]
            boolean r15 = r13 instanceof java.lang.reflect.Field
            if (r15 == 0) goto L_0x028b
            java.lang.reflect.Field r13 = (java.lang.reflect.Field) r13
            goto L_0x0293
        L_0x028b:
            java.lang.String r13 = (java.lang.String) r13
            java.lang.reflect.Field r13 = zza((java.lang.Class<?>) r6, (java.lang.String) r13)
            r16[r9] = r13
        L_0x0293:
            r30 = r4
            r31 = r5
            long r4 = r8.objectFieldOffset(r13)
            int r4 = (int) r4
            int r9 = r9 + 1
            r5 = r16[r9]
            boolean r13 = r5 instanceof java.lang.reflect.Field
            if (r13 == 0) goto L_0x02a7
            java.lang.reflect.Field r5 = (java.lang.reflect.Field) r5
            goto L_0x02af
        L_0x02a7:
            java.lang.String r5 = (java.lang.String) r5
            java.lang.reflect.Field r5 = zza((java.lang.Class<?>) r6, (java.lang.String) r5)
            r16[r9] = r5
        L_0x02af:
            r9 = r4
            long r4 = r8.objectFieldOffset(r5)
            int r4 = (int) r4
            r17 = r6
            r5 = r29
            r13 = 0
            r6 = r4
            r4 = r9
            goto L_0x03bd
        L_0x02bf:
            r30 = r4
            r31 = r5
            int r4 = r7 + 1
            r5 = r16[r7]
            java.lang.String r5 = (java.lang.String) r5
            java.lang.reflect.Field r5 = zza((java.lang.Class<?>) r6, (java.lang.String) r5)
            r7 = 49
            r9 = 9
            if (r10 == r9) goto L_0x0332
            r9 = 17
            if (r10 != r9) goto L_0x02d9
            r13 = 1
            goto L_0x0333
        L_0x02d9:
            r9 = 27
            if (r10 == r9) goto L_0x0324
            if (r10 != r7) goto L_0x02e0
            goto L_0x0324
        L_0x02e0:
            r9 = 12
            if (r10 == r9) goto L_0x0315
            r9 = 30
            if (r10 == r9) goto L_0x0315
            r9 = 44
            if (r10 != r9) goto L_0x02ed
            goto L_0x0315
        L_0x02ed:
            r9 = 50
            if (r10 != r9) goto L_0x033d
            int r9 = r21 + 1
            r14[r21] = r20
            int r13 = r20 / 3
            r17 = 1
            int r13 = r13 << 1
            int r21 = r4 + 1
            r4 = r16[r4]
            r12[r13] = r4
            r4 = r2 & 2048(0x800, float:2.87E-42)
            if (r4 == 0) goto L_0x0310
            int r13 = r13 + 1
            int r4 = r21 + 1
            r21 = r16[r21]
            r12[r13] = r21
            r21 = r9
            goto L_0x033d
        L_0x0310:
            r4 = r21
            r21 = r9
            goto L_0x033d
        L_0x0315:
            if (r11 != 0) goto L_0x033d
            int r9 = r20 / 3
            r13 = 1
            int r9 = r9 << r13
            int r9 = r9 + r13
            int r13 = r4 + 1
            r4 = r16[r4]
            r12[r9] = r4
            r4 = r13
            goto L_0x033d
        L_0x0324:
            int r9 = r20 / 3
            r13 = 1
            int r9 = r9 << r13
            int r9 = r9 + r13
            int r17 = r4 + 1
            r4 = r16[r4]
            r12[r9] = r4
            r4 = r17
            goto L_0x033d
        L_0x0332:
            r13 = 1
        L_0x0333:
            int r9 = r20 / 3
            int r9 = r9 << r13
            int r9 = r9 + r13
            java.lang.Class r13 = r5.getType()
            r12[r9] = r13
        L_0x033d:
            r9 = r4
            long r4 = r8.objectFieldOffset(r5)
            int r4 = (int) r4
            r5 = r2 & 4096(0x1000, float:5.74E-42)
            r13 = 4096(0x1000, float:5.74E-42)
            if (r5 != r13) goto L_0x03a3
            r5 = 17
            if (r10 > r5) goto L_0x039d
            int r5 = r15 + 1
            char r13 = r1.charAt(r15)
            r15 = 55296(0xd800, float:7.7486E-41)
            if (r13 < r15) goto L_0x0372
            r13 = r13 & 8191(0x1fff, float:1.1478E-41)
            r18 = 13
        L_0x035c:
            int r25 = r5 + 1
            char r5 = r1.charAt(r5)
            if (r5 < r15) goto L_0x036e
            r5 = r5 & 8191(0x1fff, float:1.1478E-41)
            int r5 = r5 << r18
            r13 = r13 | r5
            int r18 = r18 + 13
            r5 = r25
            goto L_0x035c
        L_0x036e:
            int r5 = r5 << r18
            r13 = r13 | r5
            goto L_0x0374
        L_0x0372:
            r25 = r5
        L_0x0374:
            r5 = 1
            int r17 = r3 << 1
            int r18 = r13 / 32
            int r17 = r17 + r18
            r5 = r16[r17]
            boolean r15 = r5 instanceof java.lang.reflect.Field
            if (r15 == 0) goto L_0x0385
            java.lang.reflect.Field r5 = (java.lang.reflect.Field) r5
            goto L_0x038d
        L_0x0385:
            java.lang.String r5 = (java.lang.String) r5
            java.lang.reflect.Field r5 = zza((java.lang.Class<?>) r6, (java.lang.String) r5)
            r16[r17] = r5
        L_0x038d:
            r17 = r6
            long r5 = r8.objectFieldOffset(r5)
            int r5 = (int) r5
            int r13 = r13 % 32
            r6 = r5
            r15 = r25
            r5 = 55296(0xd800, float:7.7486E-41)
            goto L_0x03ac
        L_0x039d:
            r17 = r6
            r5 = 55296(0xd800, float:7.7486E-41)
            goto L_0x03a8
        L_0x03a3:
            r17 = r6
            r5 = 55296(0xd800, float:7.7486E-41)
        L_0x03a8:
            r6 = 1048575(0xfffff, float:1.469367E-39)
            r13 = 0
        L_0x03ac:
            r5 = 18
            if (r10 < r5) goto L_0x03bb
            if (r10 > r7) goto L_0x03bb
            int r5 = r22 + 1
            r14[r22] = r4
            r22 = r5
            r7 = r9
            r5 = r15
            goto L_0x03bd
        L_0x03bb:
            r7 = r9
            r5 = r15
        L_0x03bd:
            int r9 = r20 + 1
            r30[r20] = r31
            int r15 = r9 + 1
            r20 = r1
            r1 = r2 & 512(0x200, float:7.175E-43)
            if (r1 == 0) goto L_0x03cc
            r1 = 536870912(0x20000000, float:1.0842022E-19)
            goto L_0x03cd
        L_0x03cc:
            r1 = 0
        L_0x03cd:
            r2 = r2 & 256(0x100, float:3.59E-43)
            if (r2 == 0) goto L_0x03d4
            r2 = 268435456(0x10000000, float:2.5243549E-29)
            goto L_0x03d5
        L_0x03d4:
            r2 = 0
        L_0x03d5:
            r1 = r1 | r2
            int r2 = r10 << 20
            r1 = r1 | r2
            r1 = r1 | r4
            r30[r9] = r1
            int r1 = r15 + 1
            int r2 = r13 << 20
            r2 = r2 | r6
            r30[r15] = r2
            r6 = r17
            r9 = r23
            r2 = r24
            r15 = r26
            r10 = r27
            r13 = r28
            r4 = r30
            r32 = r20
            r20 = r1
            r1 = r32
            goto L_0x01a3
        L_0x03f9:
            r30 = r4
            r23 = r9
            r27 = r10
            r26 = r15
            com.google.android.gms.internal.measurement.zzjn r1 = new com.google.android.gms.internal.measurement.zzjn
            com.google.android.gms.internal.measurement.zzjj r10 = r0.zzc()
            r0 = 0
            r5 = r1
            r6 = r30
            r7 = r12
            r8 = r23
            r9 = r27
            r12 = r0
            r13 = r14
            r14 = r26
            r15 = r19
            r16 = r35
            r17 = r36
            r18 = r37
            r19 = r38
            r20 = r39
            r5.<init>(r6, r7, r8, r9, r10, r11, r12, r13, r14, r15, r16, r17, r18, r19, r20)
            return r1
        L_0x0424:
            com.google.android.gms.internal.measurement.zzkn r0 = (com.google.android.gms.internal.measurement.zzkn) r0
            int r0 = r0.zza()
            int r1 = com.google.android.gms.internal.measurement.zzib.zzf.zzi
            java.lang.NoSuchMethodError r0 = new java.lang.NoSuchMethodError
            r0.<init>()
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.measurement.zzjn.zza(java.lang.Class, com.google.android.gms.internal.measurement.zzjh, com.google.android.gms.internal.measurement.zzjr, com.google.android.gms.internal.measurement.zzit, com.google.android.gms.internal.measurement.zzku, com.google.android.gms.internal.measurement.zzhq, com.google.android.gms.internal.measurement.zzjg):com.google.android.gms.internal.measurement.zzjn");
    }

    private static Field zza(Class<?> cls, String str) {
        try {
            return cls.getDeclaredField(str);
        } catch (NoSuchFieldException e) {
            Field[] declaredFields = cls.getDeclaredFields();
            for (Field field : declaredFields) {
                if (str.equals(field.getName())) {
                    return field;
                }
            }
            String name = cls.getName();
            String arrays = Arrays.toString(declaredFields);
            throw new RuntimeException(new StringBuilder(String.valueOf(str).length() + 40 + String.valueOf(name).length() + String.valueOf(arrays).length()).append("Field ").append(str).append(" for ").append(name).append(" not found. Known fields are ").append(arrays).toString());
        }
    }

    public final T zza() {
        return this.zzo.zza(this.zzg);
    }

    public final boolean zza(T t, T t2) {
        int length = this.zzc.length;
        int i = 0;
        while (true) {
            boolean z = true;
            if (i < length) {
                int zzd2 = zzd(i);
                long j = (long) (zzd2 & 1048575);
                switch ((zzd2 & 267386880) >>> 20) {
                    case 0:
                        if (!zzc(t, t2, i) || Double.doubleToLongBits(zzla.zze(t, j)) != Double.doubleToLongBits(zzla.zze(t2, j))) {
                            z = false;
                            break;
                        }
                    case 1:
                        if (!zzc(t, t2, i) || Float.floatToIntBits(zzla.zzd(t, j)) != Float.floatToIntBits(zzla.zzd(t2, j))) {
                            z = false;
                            break;
                        }
                    case 2:
                        if (!zzc(t, t2, i) || zzla.zzb(t, j) != zzla.zzb(t2, j)) {
                            z = false;
                            break;
                        }
                    case 3:
                        if (!zzc(t, t2, i) || zzla.zzb(t, j) != zzla.zzb(t2, j)) {
                            z = false;
                            break;
                        }
                    case 4:
                        if (!zzc(t, t2, i) || zzla.zza((Object) t, j) != zzla.zza((Object) t2, j)) {
                            z = false;
                            break;
                        }
                    case 5:
                        if (!zzc(t, t2, i) || zzla.zzb(t, j) != zzla.zzb(t2, j)) {
                            z = false;
                            break;
                        }
                    case 6:
                        if (!zzc(t, t2, i) || zzla.zza((Object) t, j) != zzla.zza((Object) t2, j)) {
                            z = false;
                            break;
                        }
                    case 7:
                        if (!zzc(t, t2, i) || zzla.zzc(t, j) != zzla.zzc(t2, j)) {
                            z = false;
                            break;
                        }
                    case 8:
                        if (!zzc(t, t2, i) || !zzke.zza(zzla.zzf(t, j), zzla.zzf(t2, j))) {
                            z = false;
                            break;
                        }
                    case 9:
                        if (!zzc(t, t2, i) || !zzke.zza(zzla.zzf(t, j), zzla.zzf(t2, j))) {
                            z = false;
                            break;
                        }
                    case 10:
                        if (!zzc(t, t2, i) || !zzke.zza(zzla.zzf(t, j), zzla.zzf(t2, j))) {
                            z = false;
                            break;
                        }
                    case 11:
                        if (!zzc(t, t2, i) || zzla.zza((Object) t, j) != zzla.zza((Object) t2, j)) {
                            z = false;
                            break;
                        }
                    case 12:
                        if (!zzc(t, t2, i) || zzla.zza((Object) t, j) != zzla.zza((Object) t2, j)) {
                            z = false;
                            break;
                        }
                    case 13:
                        if (!zzc(t, t2, i) || zzla.zza((Object) t, j) != zzla.zza((Object) t2, j)) {
                            z = false;
                            break;
                        }
                    case 14:
                        if (!zzc(t, t2, i) || zzla.zzb(t, j) != zzla.zzb(t2, j)) {
                            z = false;
                            break;
                        }
                    case 15:
                        if (!zzc(t, t2, i) || zzla.zza((Object) t, j) != zzla.zza((Object) t2, j)) {
                            z = false;
                            break;
                        }
                    case 16:
                        if (!zzc(t, t2, i) || zzla.zzb(t, j) != zzla.zzb(t2, j)) {
                            z = false;
                            break;
                        }
                    case 17:
                        if (!zzc(t, t2, i) || !zzke.zza(zzla.zzf(t, j), zzla.zzf(t2, j))) {
                            z = false;
                            break;
                        }
                    case 18:
                    case 19:
                    case 20:
                    case 21:
                    case 22:
                    case 23:
                    case 24:
                    case 25:
                    case 26:
                    case 27:
                    case 28:
                    case 29:
                    case 30:
                    case 31:
                    case 32:
                    case 33:
                    case 34:
                    case 35:
                    case 36:
                    case 37:
                    case 38:
                    case 39:
                    case 40:
                    case 41:
                    case 42:
                    case 43:
                    case 44:
                    case 45:
                    case 46:
                    case 47:
                    case 48:
                    case 49:
                        z = zzke.zza(zzla.zzf(t, j), zzla.zzf(t2, j));
                        break;
                    case 50:
                        z = zzke.zza(zzla.zzf(t, j), zzla.zzf(t2, j));
                        break;
                    case 51:
                    case 52:
                    case 53:
                    case 54:
                    case 55:
                    case 56:
                    case 57:
                    case 58:
                    case 59:
                    case 60:
                    case 61:
                    case 62:
                    case 63:
                    case 64:
                    case 65:
                    case 66:
                    case 67:
                    case 68:
                        long zze2 = (long) (zze(i) & 1048575);
                        if (zzla.zza((Object) t, zze2) != zzla.zza((Object) t2, zze2) || !zzke.zza(zzla.zzf(t, j), zzla.zzf(t2, j))) {
                            z = false;
                            break;
                        }
                }
                if (!z) {
                    return false;
                }
                i += 3;
            } else if (!this.zzq.zzb(t).equals(this.zzq.zzb(t2))) {
                return false;
            } else {
                if (this.zzh) {
                    return this.zzr.zza((Object) t).equals(this.zzr.zza((Object) t2));
                }
                return true;
            }
        }
    }

    public final int zza(T t) {
        int length = this.zzc.length;
        int i = 0;
        for (int i2 = 0; i2 < length; i2 += 3) {
            int zzd2 = zzd(i2);
            int i3 = this.zzc[i2];
            long j = (long) (1048575 & zzd2);
            int i4 = 37;
            switch ((zzd2 & 267386880) >>> 20) {
                case 0:
                    i = (i * 53) + zzie.zza(Double.doubleToLongBits(zzla.zze(t, j)));
                    break;
                case 1:
                    i = (i * 53) + Float.floatToIntBits(zzla.zzd(t, j));
                    break;
                case 2:
                    i = (i * 53) + zzie.zza(zzla.zzb(t, j));
                    break;
                case 3:
                    i = (i * 53) + zzie.zza(zzla.zzb(t, j));
                    break;
                case 4:
                    i = (i * 53) + zzla.zza((Object) t, j);
                    break;
                case 5:
                    i = (i * 53) + zzie.zza(zzla.zzb(t, j));
                    break;
                case 6:
                    i = (i * 53) + zzla.zza((Object) t, j);
                    break;
                case 7:
                    i = (i * 53) + zzie.zza(zzla.zzc(t, j));
                    break;
                case 8:
                    i = (i * 53) + ((String) zzla.zzf(t, j)).hashCode();
                    break;
                case 9:
                    Object zzf2 = zzla.zzf(t, j);
                    if (zzf2 != null) {
                        i4 = zzf2.hashCode();
                    }
                    i = (i * 53) + i4;
                    break;
                case 10:
                    i = (i * 53) + zzla.zzf(t, j).hashCode();
                    break;
                case 11:
                    i = (i * 53) + zzla.zza((Object) t, j);
                    break;
                case 12:
                    i = (i * 53) + zzla.zza((Object) t, j);
                    break;
                case 13:
                    i = (i * 53) + zzla.zza((Object) t, j);
                    break;
                case 14:
                    i = (i * 53) + zzie.zza(zzla.zzb(t, j));
                    break;
                case 15:
                    i = (i * 53) + zzla.zza((Object) t, j);
                    break;
                case 16:
                    i = (i * 53) + zzie.zza(zzla.zzb(t, j));
                    break;
                case 17:
                    Object zzf3 = zzla.zzf(t, j);
                    if (zzf3 != null) {
                        i4 = zzf3.hashCode();
                    }
                    i = (i * 53) + i4;
                    break;
                case 18:
                case 19:
                case 20:
                case 21:
                case 22:
                case 23:
                case 24:
                case 25:
                case 26:
                case 27:
                case 28:
                case 29:
                case 30:
                case 31:
                case 32:
                case 33:
                case 34:
                case 35:
                case 36:
                case 37:
                case 38:
                case 39:
                case 40:
                case 41:
                case 42:
                case 43:
                case 44:
                case 45:
                case 46:
                case 47:
                case 48:
                case 49:
                    i = (i * 53) + zzla.zzf(t, j).hashCode();
                    break;
                case 50:
                    i = (i * 53) + zzla.zzf(t, j).hashCode();
                    break;
                case 51:
                    if (!zza(t, i3, i2)) {
                        break;
                    } else {
                        i = (i * 53) + zzie.zza(Double.doubleToLongBits(zzb(t, j)));
                        break;
                    }
                case 52:
                    if (!zza(t, i3, i2)) {
                        break;
                    } else {
                        i = (i * 53) + Float.floatToIntBits(zzc(t, j));
                        break;
                    }
                case 53:
                    if (!zza(t, i3, i2)) {
                        break;
                    } else {
                        i = (i * 53) + zzie.zza(zze(t, j));
                        break;
                    }
                case 54:
                    if (!zza(t, i3, i2)) {
                        break;
                    } else {
                        i = (i * 53) + zzie.zza(zze(t, j));
                        break;
                    }
                case 55:
                    if (!zza(t, i3, i2)) {
                        break;
                    } else {
                        i = (i * 53) + zzd(t, j);
                        break;
                    }
                case 56:
                    if (!zza(t, i3, i2)) {
                        break;
                    } else {
                        i = (i * 53) + zzie.zza(zze(t, j));
                        break;
                    }
                case 57:
                    if (!zza(t, i3, i2)) {
                        break;
                    } else {
                        i = (i * 53) + zzd(t, j);
                        break;
                    }
                case 58:
                    if (!zza(t, i3, i2)) {
                        break;
                    } else {
                        i = (i * 53) + zzie.zza(zzf(t, j));
                        break;
                    }
                case 59:
                    if (!zza(t, i3, i2)) {
                        break;
                    } else {
                        i = (i * 53) + ((String) zzla.zzf(t, j)).hashCode();
                        break;
                    }
                case 60:
                    if (!zza(t, i3, i2)) {
                        break;
                    } else {
                        i = (i * 53) + zzla.zzf(t, j).hashCode();
                        break;
                    }
                case 61:
                    if (!zza(t, i3, i2)) {
                        break;
                    } else {
                        i = (i * 53) + zzla.zzf(t, j).hashCode();
                        break;
                    }
                case 62:
                    if (!zza(t, i3, i2)) {
                        break;
                    } else {
                        i = (i * 53) + zzd(t, j);
                        break;
                    }
                case 63:
                    if (!zza(t, i3, i2)) {
                        break;
                    } else {
                        i = (i * 53) + zzd(t, j);
                        break;
                    }
                case 64:
                    if (!zza(t, i3, i2)) {
                        break;
                    } else {
                        i = (i * 53) + zzd(t, j);
                        break;
                    }
                case 65:
                    if (!zza(t, i3, i2)) {
                        break;
                    } else {
                        i = (i * 53) + zzie.zza(zze(t, j));
                        break;
                    }
                case 66:
                    if (!zza(t, i3, i2)) {
                        break;
                    } else {
                        i = (i * 53) + zzd(t, j);
                        break;
                    }
                case 67:
                    if (!zza(t, i3, i2)) {
                        break;
                    } else {
                        i = (i * 53) + zzie.zza(zze(t, j));
                        break;
                    }
                case 68:
                    if (!zza(t, i3, i2)) {
                        break;
                    } else {
                        i = (i * 53) + zzla.zzf(t, j).hashCode();
                        break;
                    }
            }
        }
        int hashCode = (i * 53) + this.zzq.zzb(t).hashCode();
        if (this.zzh) {
            return (hashCode * 53) + this.zzr.zza((Object) t).hashCode();
        }
        return hashCode;
    }

    public final void zzb(T t, T t2) {
        if (t2 != null) {
            for (int i = 0; i < this.zzc.length; i += 3) {
                int zzd2 = zzd(i);
                long j = (long) (1048575 & zzd2);
                int i2 = this.zzc[i];
                switch ((zzd2 & 267386880) >>> 20) {
                    case 0:
                        if (!zza(t2, i)) {
                            break;
                        } else {
                            zzla.zza((Object) t, j, zzla.zze(t2, j));
                            zzb(t, i);
                            break;
                        }
                    case 1:
                        if (!zza(t2, i)) {
                            break;
                        } else {
                            zzla.zza((Object) t, j, zzla.zzd(t2, j));
                            zzb(t, i);
                            break;
                        }
                    case 2:
                        if (!zza(t2, i)) {
                            break;
                        } else {
                            zzla.zza((Object) t, j, zzla.zzb(t2, j));
                            zzb(t, i);
                            break;
                        }
                    case 3:
                        if (!zza(t2, i)) {
                            break;
                        } else {
                            zzla.zza((Object) t, j, zzla.zzb(t2, j));
                            zzb(t, i);
                            break;
                        }
                    case 4:
                        if (!zza(t2, i)) {
                            break;
                        } else {
                            zzla.zza((Object) t, j, zzla.zza((Object) t2, j));
                            zzb(t, i);
                            break;
                        }
                    case 5:
                        if (!zza(t2, i)) {
                            break;
                        } else {
                            zzla.zza((Object) t, j, zzla.zzb(t2, j));
                            zzb(t, i);
                            break;
                        }
                    case 6:
                        if (!zza(t2, i)) {
                            break;
                        } else {
                            zzla.zza((Object) t, j, zzla.zza((Object) t2, j));
                            zzb(t, i);
                            break;
                        }
                    case 7:
                        if (!zza(t2, i)) {
                            break;
                        } else {
                            zzla.zza((Object) t, j, zzla.zzc(t2, j));
                            zzb(t, i);
                            break;
                        }
                    case 8:
                        if (!zza(t2, i)) {
                            break;
                        } else {
                            zzla.zza((Object) t, j, zzla.zzf(t2, j));
                            zzb(t, i);
                            break;
                        }
                    case 9:
                        zza(t, t2, i);
                        break;
                    case 10:
                        if (!zza(t2, i)) {
                            break;
                        } else {
                            zzla.zza((Object) t, j, zzla.zzf(t2, j));
                            zzb(t, i);
                            break;
                        }
                    case 11:
                        if (!zza(t2, i)) {
                            break;
                        } else {
                            zzla.zza((Object) t, j, zzla.zza((Object) t2, j));
                            zzb(t, i);
                            break;
                        }
                    case 12:
                        if (!zza(t2, i)) {
                            break;
                        } else {
                            zzla.zza((Object) t, j, zzla.zza((Object) t2, j));
                            zzb(t, i);
                            break;
                        }
                    case 13:
                        if (!zza(t2, i)) {
                            break;
                        } else {
                            zzla.zza((Object) t, j, zzla.zza((Object) t2, j));
                            zzb(t, i);
                            break;
                        }
                    case 14:
                        if (!zza(t2, i)) {
                            break;
                        } else {
                            zzla.zza((Object) t, j, zzla.zzb(t2, j));
                            zzb(t, i);
                            break;
                        }
                    case 15:
                        if (!zza(t2, i)) {
                            break;
                        } else {
                            zzla.zza((Object) t, j, zzla.zza((Object) t2, j));
                            zzb(t, i);
                            break;
                        }
                    case 16:
                        if (!zza(t2, i)) {
                            break;
                        } else {
                            zzla.zza((Object) t, j, zzla.zzb(t2, j));
                            zzb(t, i);
                            break;
                        }
                    case 17:
                        zza(t, t2, i);
                        break;
                    case 18:
                    case 19:
                    case 20:
                    case 21:
                    case 22:
                    case 23:
                    case 24:
                    case 25:
                    case 26:
                    case 27:
                    case 28:
                    case 29:
                    case 30:
                    case 31:
                    case 32:
                    case 33:
                    case 34:
                    case 35:
                    case 36:
                    case 37:
                    case 38:
                    case 39:
                    case 40:
                    case 41:
                    case 42:
                    case 43:
                    case 44:
                    case 45:
                    case 46:
                    case 47:
                    case 48:
                    case 49:
                        this.zzp.zza(t, t2, j);
                        break;
                    case 50:
                        zzke.zza(this.zzs, t, t2, j);
                        break;
                    case 51:
                    case 52:
                    case 53:
                    case 54:
                    case 55:
                    case 56:
                    case 57:
                    case 58:
                    case 59:
                        if (!zza(t2, i2, i)) {
                            break;
                        } else {
                            zzla.zza((Object) t, j, zzla.zzf(t2, j));
                            zzb(t, i2, i);
                            break;
                        }
                    case 60:
                        zzb(t, t2, i);
                        break;
                    case 61:
                    case 62:
                    case 63:
                    case 64:
                    case 65:
                    case 66:
                    case 67:
                        if (!zza(t2, i2, i)) {
                            break;
                        } else {
                            zzla.zza((Object) t, j, zzla.zzf(t2, j));
                            zzb(t, i2, i);
                            break;
                        }
                    case 68:
                        zzb(t, t2, i);
                        break;
                }
            }
            zzke.zza(this.zzq, t, t2);
            if (this.zzh) {
                zzke.zza(this.zzr, t, t2);
                return;
            }
            return;
        }
        throw new NullPointerException();
    }

    private final void zza(T t, T t2, int i) {
        long zzd2 = (long) (zzd(i) & 1048575);
        if (zza(t2, i)) {
            Object zzf2 = zzla.zzf(t, zzd2);
            Object zzf3 = zzla.zzf(t2, zzd2);
            if (zzf2 != null && zzf3 != null) {
                zzla.zza((Object) t, zzd2, zzie.zza(zzf2, zzf3));
                zzb(t, i);
            } else if (zzf3 != null) {
                zzla.zza((Object) t, zzd2, zzf3);
                zzb(t, i);
            }
        }
    }

    private final void zzb(T t, T t2, int i) {
        int zzd2 = zzd(i);
        int i2 = this.zzc[i];
        long j = (long) (zzd2 & 1048575);
        if (zza(t2, i2, i)) {
            Object zzf2 = zzla.zzf(t, j);
            Object zzf3 = zzla.zzf(t2, j);
            if (zzf2 != null && zzf3 != null) {
                zzla.zza((Object) t, j, zzie.zza(zzf2, zzf3));
                zzb(t, i2, i);
            } else if (zzf3 != null) {
                zzla.zza((Object) t, j, zzf3);
                zzb(t, i2, i);
            }
        }
    }

    public final int zzb(T t) {
        int i;
        int i2;
        long j;
        boolean z;
        int i3;
        int i4;
        T t2 = t;
        int i5 = 267386880;
        int i6 = 0;
        if (this.zzj) {
            Unsafe unsafe = zzb;
            int i7 = 0;
            int i8 = 0;
            while (i7 < this.zzc.length) {
                int zzd2 = zzd(i7);
                int i9 = (zzd2 & i5) >>> 20;
                int i10 = this.zzc[i7];
                long j2 = (long) (zzd2 & 1048575);
                if (i9 < zzhw.DOUBLE_LIST_PACKED.zza() || i9 > zzhw.SINT64_LIST_PACKED.zza()) {
                    i4 = 0;
                } else {
                    i4 = this.zzc[i7 + 2] & 1048575;
                }
                switch (i9) {
                    case 0:
                        if (!zza(t2, i7)) {
                            break;
                        } else {
                            i8 += zzhi.zzb(i10, 0.0d);
                            break;
                        }
                    case 1:
                        if (!zza(t2, i7)) {
                            break;
                        } else {
                            i8 += zzhi.zzb(i10, 0.0f);
                            break;
                        }
                    case 2:
                        if (!zza(t2, i7)) {
                            break;
                        } else {
                            i8 += zzhi.zzd(i10, zzla.zzb(t2, j2));
                            break;
                        }
                    case 3:
                        if (!zza(t2, i7)) {
                            break;
                        } else {
                            i8 += zzhi.zze(i10, zzla.zzb(t2, j2));
                            break;
                        }
                    case 4:
                        if (!zza(t2, i7)) {
                            break;
                        } else {
                            i8 += zzhi.zzf(i10, zzla.zza((Object) t2, j2));
                            break;
                        }
                    case 5:
                        if (!zza(t2, i7)) {
                            break;
                        } else {
                            i8 += zzhi.zzg(i10, 0);
                            break;
                        }
                    case 6:
                        if (!zza(t2, i7)) {
                            break;
                        } else {
                            i8 += zzhi.zzi(i10, 0);
                            break;
                        }
                    case 7:
                        if (!zza(t2, i7)) {
                            break;
                        } else {
                            i8 += zzhi.zzb(i10, true);
                            break;
                        }
                    case 8:
                        if (!zza(t2, i7)) {
                            break;
                        } else {
                            Object zzf2 = zzla.zzf(t2, j2);
                            if (!(zzf2 instanceof zzgt)) {
                                i8 += zzhi.zzb(i10, (String) zzf2);
                                break;
                            } else {
                                i8 += zzhi.zzc(i10, (zzgt) zzf2);
                                break;
                            }
                        }
                    case 9:
                        if (!zza(t2, i7)) {
                            break;
                        } else {
                            i8 += zzke.zza(i10, zzla.zzf(t2, j2), zza(i7));
                            break;
                        }
                    case 10:
                        if (!zza(t2, i7)) {
                            break;
                        } else {
                            i8 += zzhi.zzc(i10, (zzgt) zzla.zzf(t2, j2));
                            break;
                        }
                    case 11:
                        if (!zza(t2, i7)) {
                            break;
                        } else {
                            i8 += zzhi.zzg(i10, zzla.zza((Object) t2, j2));
                            break;
                        }
                    case 12:
                        if (!zza(t2, i7)) {
                            break;
                        } else {
                            i8 += zzhi.zzk(i10, zzla.zza((Object) t2, j2));
                            break;
                        }
                    case 13:
                        if (!zza(t2, i7)) {
                            break;
                        } else {
                            i8 += zzhi.zzj(i10, 0);
                            break;
                        }
                    case 14:
                        if (!zza(t2, i7)) {
                            break;
                        } else {
                            i8 += zzhi.zzh(i10, 0);
                            break;
                        }
                    case 15:
                        if (!zza(t2, i7)) {
                            break;
                        } else {
                            i8 += zzhi.zzh(i10, zzla.zza((Object) t2, j2));
                            break;
                        }
                    case 16:
                        if (!zza(t2, i7)) {
                            break;
                        } else {
                            i8 += zzhi.zzf(i10, zzla.zzb(t2, j2));
                            break;
                        }
                    case 17:
                        if (!zza(t2, i7)) {
                            break;
                        } else {
                            i8 += zzhi.zzc(i10, (zzjj) zzla.zzf(t2, j2), zza(i7));
                            break;
                        }
                    case 18:
                        i8 += zzke.zzi(i10, zza((Object) t2, j2), false);
                        break;
                    case 19:
                        i8 += zzke.zzh(i10, zza((Object) t2, j2), false);
                        break;
                    case 20:
                        i8 += zzke.zza(i10, (List<Long>) zza((Object) t2, j2), false);
                        break;
                    case 21:
                        i8 += zzke.zzb(i10, (List<Long>) zza((Object) t2, j2), false);
                        break;
                    case 22:
                        i8 += zzke.zze(i10, zza((Object) t2, j2), false);
                        break;
                    case 23:
                        i8 += zzke.zzi(i10, zza((Object) t2, j2), false);
                        break;
                    case 24:
                        i8 += zzke.zzh(i10, zza((Object) t2, j2), false);
                        break;
                    case 25:
                        i8 += zzke.zzj(i10, zza((Object) t2, j2), false);
                        break;
                    case 26:
                        i8 += zzke.zza(i10, zza((Object) t2, j2));
                        break;
                    case 27:
                        i8 += zzke.zza(i10, zza((Object) t2, j2), zza(i7));
                        break;
                    case 28:
                        i8 += zzke.zzb(i10, zza((Object) t2, j2));
                        break;
                    case 29:
                        i8 += zzke.zzf(i10, zza((Object) t2, j2), false);
                        break;
                    case 30:
                        i8 += zzke.zzd(i10, zza((Object) t2, j2), false);
                        break;
                    case 31:
                        i8 += zzke.zzh(i10, zza((Object) t2, j2), false);
                        break;
                    case 32:
                        i8 += zzke.zzi(i10, zza((Object) t2, j2), false);
                        break;
                    case 33:
                        i8 += zzke.zzg(i10, zza((Object) t2, j2), false);
                        break;
                    case 34:
                        i8 += zzke.zzc(i10, zza((Object) t2, j2), false);
                        break;
                    case 35:
                        int zzi2 = zzke.zzi((List) unsafe.getObject(t2, j2));
                        if (zzi2 > 0) {
                            if (this.zzk) {
                                unsafe.putInt(t2, (long) i4, zzi2);
                            }
                            i8 += zzhi.zze(i10) + zzhi.zzg(zzi2) + zzi2;
                            break;
                        } else {
                            break;
                        }
                    case 36:
                        int zzh2 = zzke.zzh((List) unsafe.getObject(t2, j2));
                        if (zzh2 > 0) {
                            if (this.zzk) {
                                unsafe.putInt(t2, (long) i4, zzh2);
                            }
                            i8 += zzhi.zze(i10) + zzhi.zzg(zzh2) + zzh2;
                            break;
                        } else {
                            break;
                        }
                    case 37:
                        int zza2 = zzke.zza((List<Long>) (List) unsafe.getObject(t2, j2));
                        if (zza2 > 0) {
                            if (this.zzk) {
                                unsafe.putInt(t2, (long) i4, zza2);
                            }
                            i8 += zzhi.zze(i10) + zzhi.zzg(zza2) + zza2;
                            break;
                        } else {
                            break;
                        }
                    case 38:
                        int zzb2 = zzke.zzb((List) unsafe.getObject(t2, j2));
                        if (zzb2 > 0) {
                            if (this.zzk) {
                                unsafe.putInt(t2, (long) i4, zzb2);
                            }
                            i8 += zzhi.zze(i10) + zzhi.zzg(zzb2) + zzb2;
                            break;
                        } else {
                            break;
                        }
                    case 39:
                        int zze2 = zzke.zze((List) unsafe.getObject(t2, j2));
                        if (zze2 > 0) {
                            if (this.zzk) {
                                unsafe.putInt(t2, (long) i4, zze2);
                            }
                            i8 += zzhi.zze(i10) + zzhi.zzg(zze2) + zze2;
                            break;
                        } else {
                            break;
                        }
                    case 40:
                        int zzi3 = zzke.zzi((List) unsafe.getObject(t2, j2));
                        if (zzi3 > 0) {
                            if (this.zzk) {
                                unsafe.putInt(t2, (long) i4, zzi3);
                            }
                            i8 += zzhi.zze(i10) + zzhi.zzg(zzi3) + zzi3;
                            break;
                        } else {
                            break;
                        }
                    case 41:
                        int zzh3 = zzke.zzh((List) unsafe.getObject(t2, j2));
                        if (zzh3 > 0) {
                            if (this.zzk) {
                                unsafe.putInt(t2, (long) i4, zzh3);
                            }
                            i8 += zzhi.zze(i10) + zzhi.zzg(zzh3) + zzh3;
                            break;
                        } else {
                            break;
                        }
                    case 42:
                        int zzj2 = zzke.zzj((List) unsafe.getObject(t2, j2));
                        if (zzj2 > 0) {
                            if (this.zzk) {
                                unsafe.putInt(t2, (long) i4, zzj2);
                            }
                            i8 += zzhi.zze(i10) + zzhi.zzg(zzj2) + zzj2;
                            break;
                        } else {
                            break;
                        }
                    case 43:
                        int zzf3 = zzke.zzf((List) unsafe.getObject(t2, j2));
                        if (zzf3 > 0) {
                            if (this.zzk) {
                                unsafe.putInt(t2, (long) i4, zzf3);
                            }
                            i8 += zzhi.zze(i10) + zzhi.zzg(zzf3) + zzf3;
                            break;
                        } else {
                            break;
                        }
                    case 44:
                        int zzd3 = zzke.zzd((List) unsafe.getObject(t2, j2));
                        if (zzd3 > 0) {
                            if (this.zzk) {
                                unsafe.putInt(t2, (long) i4, zzd3);
                            }
                            i8 += zzhi.zze(i10) + zzhi.zzg(zzd3) + zzd3;
                            break;
                        } else {
                            break;
                        }
                    case 45:
                        int zzh4 = zzke.zzh((List) unsafe.getObject(t2, j2));
                        if (zzh4 > 0) {
                            if (this.zzk) {
                                unsafe.putInt(t2, (long) i4, zzh4);
                            }
                            i8 += zzhi.zze(i10) + zzhi.zzg(zzh4) + zzh4;
                            break;
                        } else {
                            break;
                        }
                    case 46:
                        int zzi4 = zzke.zzi((List) unsafe.getObject(t2, j2));
                        if (zzi4 > 0) {
                            if (this.zzk) {
                                unsafe.putInt(t2, (long) i4, zzi4);
                            }
                            i8 += zzhi.zze(i10) + zzhi.zzg(zzi4) + zzi4;
                            break;
                        } else {
                            break;
                        }
                    case 47:
                        int zzg2 = zzke.zzg((List) unsafe.getObject(t2, j2));
                        if (zzg2 > 0) {
                            if (this.zzk) {
                                unsafe.putInt(t2, (long) i4, zzg2);
                            }
                            i8 += zzhi.zze(i10) + zzhi.zzg(zzg2) + zzg2;
                            break;
                        } else {
                            break;
                        }
                    case 48:
                        int zzc2 = zzke.zzc((List) unsafe.getObject(t2, j2));
                        if (zzc2 > 0) {
                            if (this.zzk) {
                                unsafe.putInt(t2, (long) i4, zzc2);
                            }
                            i8 += zzhi.zze(i10) + zzhi.zzg(zzc2) + zzc2;
                            break;
                        } else {
                            break;
                        }
                    case 49:
                        i8 += zzke.zzb(i10, (List<zzjj>) zza((Object) t2, j2), zza(i7));
                        break;
                    case 50:
                        i8 += this.zzs.zza(i10, zzla.zzf(t2, j2), zzb(i7));
                        break;
                    case 51:
                        if (!zza(t2, i10, i7)) {
                            break;
                        } else {
                            i8 += zzhi.zzb(i10, 0.0d);
                            break;
                        }
                    case 52:
                        if (!zza(t2, i10, i7)) {
                            break;
                        } else {
                            i8 += zzhi.zzb(i10, 0.0f);
                            break;
                        }
                    case 53:
                        if (!zza(t2, i10, i7)) {
                            break;
                        } else {
                            i8 += zzhi.zzd(i10, zze(t2, j2));
                            break;
                        }
                    case 54:
                        if (!zza(t2, i10, i7)) {
                            break;
                        } else {
                            i8 += zzhi.zze(i10, zze(t2, j2));
                            break;
                        }
                    case 55:
                        if (!zza(t2, i10, i7)) {
                            break;
                        } else {
                            i8 += zzhi.zzf(i10, zzd(t2, j2));
                            break;
                        }
                    case 56:
                        if (!zza(t2, i10, i7)) {
                            break;
                        } else {
                            i8 += zzhi.zzg(i10, 0);
                            break;
                        }
                    case 57:
                        if (!zza(t2, i10, i7)) {
                            break;
                        } else {
                            i8 += zzhi.zzi(i10, 0);
                            break;
                        }
                    case 58:
                        if (!zza(t2, i10, i7)) {
                            break;
                        } else {
                            i8 += zzhi.zzb(i10, true);
                            break;
                        }
                    case 59:
                        if (!zza(t2, i10, i7)) {
                            break;
                        } else {
                            Object zzf4 = zzla.zzf(t2, j2);
                            if (!(zzf4 instanceof zzgt)) {
                                i8 += zzhi.zzb(i10, (String) zzf4);
                                break;
                            } else {
                                i8 += zzhi.zzc(i10, (zzgt) zzf4);
                                break;
                            }
                        }
                    case 60:
                        if (!zza(t2, i10, i7)) {
                            break;
                        } else {
                            i8 += zzke.zza(i10, zzla.zzf(t2, j2), zza(i7));
                            break;
                        }
                    case 61:
                        if (!zza(t2, i10, i7)) {
                            break;
                        } else {
                            i8 += zzhi.zzc(i10, (zzgt) zzla.zzf(t2, j2));
                            break;
                        }
                    case 62:
                        if (!zza(t2, i10, i7)) {
                            break;
                        } else {
                            i8 += zzhi.zzg(i10, zzd(t2, j2));
                            break;
                        }
                    case 63:
                        if (!zza(t2, i10, i7)) {
                            break;
                        } else {
                            i8 += zzhi.zzk(i10, zzd(t2, j2));
                            break;
                        }
                    case 64:
                        if (!zza(t2, i10, i7)) {
                            break;
                        } else {
                            i8 += zzhi.zzj(i10, 0);
                            break;
                        }
                    case 65:
                        if (!zza(t2, i10, i7)) {
                            break;
                        } else {
                            i8 += zzhi.zzh(i10, 0);
                            break;
                        }
                    case 66:
                        if (!zza(t2, i10, i7)) {
                            break;
                        } else {
                            i8 += zzhi.zzh(i10, zzd(t2, j2));
                            break;
                        }
                    case 67:
                        if (!zza(t2, i10, i7)) {
                            break;
                        } else {
                            i8 += zzhi.zzf(i10, zze(t2, j2));
                            break;
                        }
                    case 68:
                        if (!zza(t2, i10, i7)) {
                            break;
                        } else {
                            i8 += zzhi.zzc(i10, (zzjj) zzla.zzf(t2, j2), zza(i7));
                            break;
                        }
                }
                i7 += 3;
                i5 = 267386880;
            }
            return i8 + zza(this.zzq, t2);
        }
        Unsafe unsafe2 = zzb;
        int i11 = 1048575;
        int i12 = 0;
        int i13 = 0;
        int i14 = 0;
        while (i12 < this.zzc.length) {
            int zzd4 = zzd(i12);
            int[] iArr = this.zzc;
            int i15 = iArr[i12];
            int i16 = (zzd4 & 267386880) >>> 20;
            if (i16 <= 17) {
                int i17 = iArr[i12 + 2];
                int i18 = i17 & 1048575;
                i = 1 << (i17 >>> 20);
                if (i18 != i11) {
                    i14 = unsafe2.getInt(t2, (long) i18);
                    i11 = i18;
                }
                i2 = i17;
            } else if (!this.zzk || i16 < zzhw.DOUBLE_LIST_PACKED.zza() || i16 > zzhw.SINT64_LIST_PACKED.zza()) {
                i2 = 0;
                i = 0;
            } else {
                i2 = this.zzc[i12 + 2] & 1048575;
                i = 0;
            }
            long j3 = (long) (zzd4 & 1048575);
            switch (i16) {
                case 0:
                    i3 = 0;
                    z = false;
                    j = 0;
                    if ((i14 & i) == 0) {
                        break;
                    } else {
                        i13 += zzhi.zzb(i15, 0.0d);
                        break;
                    }
                case 1:
                    i3 = 0;
                    j = 0;
                    if ((i14 & i) == 0) {
                        z = false;
                        break;
                    } else {
                        z = false;
                        i13 += zzhi.zzb(i15, 0.0f);
                        break;
                    }
                case 2:
                    i3 = 0;
                    j = 0;
                    if ((i14 & i) == 0) {
                        z = false;
                        break;
                    } else {
                        i13 += zzhi.zzd(i15, unsafe2.getLong(t2, j3));
                        z = false;
                        break;
                    }
                case 3:
                    i3 = 0;
                    j = 0;
                    if ((i14 & i) == 0) {
                        z = false;
                        break;
                    } else {
                        i13 += zzhi.zze(i15, unsafe2.getLong(t2, j3));
                        z = false;
                        break;
                    }
                case 4:
                    i3 = 0;
                    j = 0;
                    if ((i14 & i) == 0) {
                        z = false;
                        break;
                    } else {
                        i13 += zzhi.zzf(i15, unsafe2.getInt(t2, j3));
                        z = false;
                        break;
                    }
                case 5:
                    i3 = 0;
                    if ((i14 & i) == 0) {
                        j = 0;
                        z = false;
                        break;
                    } else {
                        j = 0;
                        i13 += zzhi.zzg(i15, 0);
                        z = false;
                        break;
                    }
                case 6:
                    if ((i14 & i) == 0) {
                        i3 = 0;
                        z = false;
                        j = 0;
                        break;
                    } else {
                        i3 = 0;
                        i13 += zzhi.zzi(i15, 0);
                        z = false;
                        j = 0;
                        break;
                    }
                case 7:
                    if ((i14 & i) == 0) {
                        i3 = 0;
                        z = false;
                        j = 0;
                        break;
                    } else {
                        i13 += zzhi.zzb(i15, true);
                        i3 = 0;
                        z = false;
                        j = 0;
                        break;
                    }
                case 8:
                    if ((i14 & i) == 0) {
                        i3 = 0;
                        z = false;
                        j = 0;
                        break;
                    } else {
                        Object object = unsafe2.getObject(t2, j3);
                        if (!(object instanceof zzgt)) {
                            i13 += zzhi.zzb(i15, (String) object);
                            i3 = 0;
                            z = false;
                            j = 0;
                            break;
                        } else {
                            i13 += zzhi.zzc(i15, (zzgt) object);
                            i3 = 0;
                            z = false;
                            j = 0;
                            break;
                        }
                    }
                case 9:
                    if ((i14 & i) == 0) {
                        i3 = 0;
                        z = false;
                        j = 0;
                        break;
                    } else {
                        i13 += zzke.zza(i15, unsafe2.getObject(t2, j3), zza(i12));
                        i3 = 0;
                        z = false;
                        j = 0;
                        break;
                    }
                case 10:
                    if ((i14 & i) == 0) {
                        i3 = 0;
                        z = false;
                        j = 0;
                        break;
                    } else {
                        i13 += zzhi.zzc(i15, (zzgt) unsafe2.getObject(t2, j3));
                        i3 = 0;
                        z = false;
                        j = 0;
                        break;
                    }
                case 11:
                    if ((i14 & i) == 0) {
                        i3 = 0;
                        z = false;
                        j = 0;
                        break;
                    } else {
                        i13 += zzhi.zzg(i15, unsafe2.getInt(t2, j3));
                        i3 = 0;
                        z = false;
                        j = 0;
                        break;
                    }
                case 12:
                    if ((i14 & i) == 0) {
                        i3 = 0;
                        z = false;
                        j = 0;
                        break;
                    } else {
                        i13 += zzhi.zzk(i15, unsafe2.getInt(t2, j3));
                        i3 = 0;
                        z = false;
                        j = 0;
                        break;
                    }
                case 13:
                    if ((i14 & i) == 0) {
                        i3 = 0;
                        z = false;
                        j = 0;
                        break;
                    } else {
                        i13 += zzhi.zzj(i15, 0);
                        i3 = 0;
                        z = false;
                        j = 0;
                        break;
                    }
                case 14:
                    if ((i14 & i) == 0) {
                        i3 = 0;
                        z = false;
                        j = 0;
                        break;
                    } else {
                        i13 += zzhi.zzh(i15, 0);
                        i3 = 0;
                        z = false;
                        j = 0;
                        break;
                    }
                case 15:
                    if ((i14 & i) == 0) {
                        i3 = 0;
                        z = false;
                        j = 0;
                        break;
                    } else {
                        i13 += zzhi.zzh(i15, unsafe2.getInt(t2, j3));
                        i3 = 0;
                        z = false;
                        j = 0;
                        break;
                    }
                case 16:
                    if ((i14 & i) == 0) {
                        i3 = 0;
                        z = false;
                        j = 0;
                        break;
                    } else {
                        i13 += zzhi.zzf(i15, unsafe2.getLong(t2, j3));
                        i3 = 0;
                        z = false;
                        j = 0;
                        break;
                    }
                case 17:
                    if ((i14 & i) == 0) {
                        i3 = 0;
                        z = false;
                        j = 0;
                        break;
                    } else {
                        i13 += zzhi.zzc(i15, (zzjj) unsafe2.getObject(t2, j3), zza(i12));
                        i3 = 0;
                        z = false;
                        j = 0;
                        break;
                    }
                case 18:
                    i13 += zzke.zzi(i15, (List) unsafe2.getObject(t2, j3), false);
                    i3 = 0;
                    z = false;
                    j = 0;
                    break;
                case 19:
                    i13 += zzke.zzh(i15, (List) unsafe2.getObject(t2, j3), false);
                    i3 = 0;
                    z = false;
                    j = 0;
                    break;
                case 20:
                    i13 += zzke.zza(i15, (List<Long>) (List) unsafe2.getObject(t2, j3), false);
                    i3 = 0;
                    z = false;
                    j = 0;
                    break;
                case 21:
                    i13 += zzke.zzb(i15, (List<Long>) (List) unsafe2.getObject(t2, j3), false);
                    i3 = 0;
                    z = false;
                    j = 0;
                    break;
                case 22:
                    i13 += zzke.zze(i15, (List) unsafe2.getObject(t2, j3), false);
                    i3 = 0;
                    z = false;
                    j = 0;
                    break;
                case 23:
                    i13 += zzke.zzi(i15, (List) unsafe2.getObject(t2, j3), false);
                    i3 = 0;
                    z = false;
                    j = 0;
                    break;
                case 24:
                    i13 += zzke.zzh(i15, (List) unsafe2.getObject(t2, j3), false);
                    i3 = 0;
                    z = false;
                    j = 0;
                    break;
                case 25:
                    i13 += zzke.zzj(i15, (List) unsafe2.getObject(t2, j3), false);
                    i3 = 0;
                    z = false;
                    j = 0;
                    break;
                case 26:
                    i13 += zzke.zza(i15, (List<?>) (List) unsafe2.getObject(t2, j3));
                    i3 = 0;
                    z = false;
                    j = 0;
                    break;
                case 27:
                    i13 += zzke.zza(i15, (List<?>) (List) unsafe2.getObject(t2, j3), zza(i12));
                    i3 = 0;
                    z = false;
                    j = 0;
                    break;
                case 28:
                    i13 += zzke.zzb(i15, (List) unsafe2.getObject(t2, j3));
                    i3 = 0;
                    z = false;
                    j = 0;
                    break;
                case 29:
                    i13 += zzke.zzf(i15, (List) unsafe2.getObject(t2, j3), false);
                    i3 = 0;
                    z = false;
                    j = 0;
                    break;
                case 30:
                    i13 += zzke.zzd(i15, (List) unsafe2.getObject(t2, j3), false);
                    i3 = 0;
                    z = false;
                    j = 0;
                    break;
                case 31:
                    i13 += zzke.zzh(i15, (List) unsafe2.getObject(t2, j3), false);
                    i3 = 0;
                    z = false;
                    j = 0;
                    break;
                case 32:
                    i13 += zzke.zzi(i15, (List) unsafe2.getObject(t2, j3), false);
                    i3 = 0;
                    z = false;
                    j = 0;
                    break;
                case 33:
                    i13 += zzke.zzg(i15, (List) unsafe2.getObject(t2, j3), false);
                    i3 = 0;
                    z = false;
                    j = 0;
                    break;
                case 34:
                    i13 += zzke.zzc(i15, (List) unsafe2.getObject(t2, j3), false);
                    i3 = 0;
                    z = false;
                    j = 0;
                    break;
                case 35:
                    int zzi5 = zzke.zzi((List) unsafe2.getObject(t2, j3));
                    if (zzi5 <= 0) {
                        i3 = 0;
                        z = false;
                        j = 0;
                        break;
                    } else {
                        if (this.zzk) {
                            unsafe2.putInt(t2, (long) i2, zzi5);
                        }
                        i13 += zzhi.zze(i15) + zzhi.zzg(zzi5) + zzi5;
                        i3 = 0;
                        z = false;
                        j = 0;
                        break;
                    }
                case 36:
                    int zzh5 = zzke.zzh((List) unsafe2.getObject(t2, j3));
                    if (zzh5 <= 0) {
                        i3 = 0;
                        z = false;
                        j = 0;
                        break;
                    } else {
                        if (this.zzk) {
                            unsafe2.putInt(t2, (long) i2, zzh5);
                        }
                        i13 += zzhi.zze(i15) + zzhi.zzg(zzh5) + zzh5;
                        i3 = 0;
                        z = false;
                        j = 0;
                        break;
                    }
                case 37:
                    int zza3 = zzke.zza((List<Long>) (List) unsafe2.getObject(t2, j3));
                    if (zza3 <= 0) {
                        i3 = 0;
                        z = false;
                        j = 0;
                        break;
                    } else {
                        if (this.zzk) {
                            unsafe2.putInt(t2, (long) i2, zza3);
                        }
                        i13 += zzhi.zze(i15) + zzhi.zzg(zza3) + zza3;
                        i3 = 0;
                        z = false;
                        j = 0;
                        break;
                    }
                case 38:
                    int zzb3 = zzke.zzb((List) unsafe2.getObject(t2, j3));
                    if (zzb3 <= 0) {
                        i3 = 0;
                        z = false;
                        j = 0;
                        break;
                    } else {
                        if (this.zzk) {
                            unsafe2.putInt(t2, (long) i2, zzb3);
                        }
                        i13 += zzhi.zze(i15) + zzhi.zzg(zzb3) + zzb3;
                        i3 = 0;
                        z = false;
                        j = 0;
                        break;
                    }
                case 39:
                    int zze3 = zzke.zze((List) unsafe2.getObject(t2, j3));
                    if (zze3 <= 0) {
                        i3 = 0;
                        z = false;
                        j = 0;
                        break;
                    } else {
                        if (this.zzk) {
                            unsafe2.putInt(t2, (long) i2, zze3);
                        }
                        i13 += zzhi.zze(i15) + zzhi.zzg(zze3) + zze3;
                        i3 = 0;
                        z = false;
                        j = 0;
                        break;
                    }
                case 40:
                    int zzi6 = zzke.zzi((List) unsafe2.getObject(t2, j3));
                    if (zzi6 <= 0) {
                        i3 = 0;
                        z = false;
                        j = 0;
                        break;
                    } else {
                        if (this.zzk) {
                            unsafe2.putInt(t2, (long) i2, zzi6);
                        }
                        i13 += zzhi.zze(i15) + zzhi.zzg(zzi6) + zzi6;
                        i3 = 0;
                        z = false;
                        j = 0;
                        break;
                    }
                case 41:
                    int zzh6 = zzke.zzh((List) unsafe2.getObject(t2, j3));
                    if (zzh6 <= 0) {
                        i3 = 0;
                        z = false;
                        j = 0;
                        break;
                    } else {
                        if (this.zzk) {
                            unsafe2.putInt(t2, (long) i2, zzh6);
                        }
                        i13 += zzhi.zze(i15) + zzhi.zzg(zzh6) + zzh6;
                        i3 = 0;
                        z = false;
                        j = 0;
                        break;
                    }
                case 42:
                    int zzj3 = zzke.zzj((List) unsafe2.getObject(t2, j3));
                    if (zzj3 <= 0) {
                        i3 = 0;
                        z = false;
                        j = 0;
                        break;
                    } else {
                        if (this.zzk) {
                            unsafe2.putInt(t2, (long) i2, zzj3);
                        }
                        i13 += zzhi.zze(i15) + zzhi.zzg(zzj3) + zzj3;
                        i3 = 0;
                        z = false;
                        j = 0;
                        break;
                    }
                case 43:
                    int zzf5 = zzke.zzf((List) unsafe2.getObject(t2, j3));
                    if (zzf5 <= 0) {
                        i3 = 0;
                        z = false;
                        j = 0;
                        break;
                    } else {
                        if (this.zzk) {
                            unsafe2.putInt(t2, (long) i2, zzf5);
                        }
                        i13 += zzhi.zze(i15) + zzhi.zzg(zzf5) + zzf5;
                        i3 = 0;
                        z = false;
                        j = 0;
                        break;
                    }
                case 44:
                    int zzd5 = zzke.zzd((List) unsafe2.getObject(t2, j3));
                    if (zzd5 <= 0) {
                        i3 = 0;
                        z = false;
                        j = 0;
                        break;
                    } else {
                        if (this.zzk) {
                            unsafe2.putInt(t2, (long) i2, zzd5);
                        }
                        i13 += zzhi.zze(i15) + zzhi.zzg(zzd5) + zzd5;
                        i3 = 0;
                        z = false;
                        j = 0;
                        break;
                    }
                case 45:
                    int zzh7 = zzke.zzh((List) unsafe2.getObject(t2, j3));
                    if (zzh7 <= 0) {
                        i3 = 0;
                        z = false;
                        j = 0;
                        break;
                    } else {
                        if (this.zzk) {
                            unsafe2.putInt(t2, (long) i2, zzh7);
                        }
                        i13 += zzhi.zze(i15) + zzhi.zzg(zzh7) + zzh7;
                        i3 = 0;
                        z = false;
                        j = 0;
                        break;
                    }
                case 46:
                    int zzi7 = zzke.zzi((List) unsafe2.getObject(t2, j3));
                    if (zzi7 <= 0) {
                        i3 = 0;
                        z = false;
                        j = 0;
                        break;
                    } else {
                        if (this.zzk) {
                            unsafe2.putInt(t2, (long) i2, zzi7);
                        }
                        i13 += zzhi.zze(i15) + zzhi.zzg(zzi7) + zzi7;
                        i3 = 0;
                        z = false;
                        j = 0;
                        break;
                    }
                case 47:
                    int zzg3 = zzke.zzg((List) unsafe2.getObject(t2, j3));
                    if (zzg3 <= 0) {
                        i3 = 0;
                        z = false;
                        j = 0;
                        break;
                    } else {
                        if (this.zzk) {
                            unsafe2.putInt(t2, (long) i2, zzg3);
                        }
                        i13 += zzhi.zze(i15) + zzhi.zzg(zzg3) + zzg3;
                        i3 = 0;
                        z = false;
                        j = 0;
                        break;
                    }
                case 48:
                    int zzc3 = zzke.zzc((List) unsafe2.getObject(t2, j3));
                    if (zzc3 <= 0) {
                        i3 = 0;
                        z = false;
                        j = 0;
                        break;
                    } else {
                        if (this.zzk) {
                            unsafe2.putInt(t2, (long) i2, zzc3);
                        }
                        i13 += zzhi.zze(i15) + zzhi.zzg(zzc3) + zzc3;
                        i3 = 0;
                        z = false;
                        j = 0;
                        break;
                    }
                case 49:
                    i13 += zzke.zzb(i15, (List<zzjj>) (List) unsafe2.getObject(t2, j3), zza(i12));
                    i3 = 0;
                    z = false;
                    j = 0;
                    break;
                case 50:
                    i13 += this.zzs.zza(i15, unsafe2.getObject(t2, j3), zzb(i12));
                    i3 = 0;
                    z = false;
                    j = 0;
                    break;
                case 51:
                    if (!zza(t2, i15, i12)) {
                        i3 = 0;
                        z = false;
                        j = 0;
                        break;
                    } else {
                        i13 += zzhi.zzb(i15, 0.0d);
                        i3 = 0;
                        z = false;
                        j = 0;
                        break;
                    }
                case 52:
                    if (!zza(t2, i15, i12)) {
                        i3 = 0;
                        z = false;
                        j = 0;
                        break;
                    } else {
                        i13 += zzhi.zzb(i15, 0.0f);
                        i3 = 0;
                        z = false;
                        j = 0;
                        break;
                    }
                case 53:
                    if (!zza(t2, i15, i12)) {
                        i3 = 0;
                        z = false;
                        j = 0;
                        break;
                    } else {
                        i13 += zzhi.zzd(i15, zze(t2, j3));
                        i3 = 0;
                        z = false;
                        j = 0;
                        break;
                    }
                case 54:
                    if (!zza(t2, i15, i12)) {
                        i3 = 0;
                        z = false;
                        j = 0;
                        break;
                    } else {
                        i13 += zzhi.zze(i15, zze(t2, j3));
                        i3 = 0;
                        z = false;
                        j = 0;
                        break;
                    }
                case 55:
                    if (!zza(t2, i15, i12)) {
                        i3 = 0;
                        z = false;
                        j = 0;
                        break;
                    } else {
                        i13 += zzhi.zzf(i15, zzd(t2, j3));
                        i3 = 0;
                        z = false;
                        j = 0;
                        break;
                    }
                case 56:
                    if (!zza(t2, i15, i12)) {
                        i3 = 0;
                        z = false;
                        j = 0;
                        break;
                    } else {
                        i13 += zzhi.zzg(i15, 0);
                        i3 = 0;
                        z = false;
                        j = 0;
                        break;
                    }
                case 57:
                    if (!zza(t2, i15, i12)) {
                        i3 = 0;
                        z = false;
                        j = 0;
                        break;
                    } else {
                        i13 += zzhi.zzi(i15, 0);
                        i3 = 0;
                        z = false;
                        j = 0;
                        break;
                    }
                case 58:
                    if (!zza(t2, i15, i12)) {
                        i3 = 0;
                        z = false;
                        j = 0;
                        break;
                    } else {
                        i13 += zzhi.zzb(i15, true);
                        i3 = 0;
                        z = false;
                        j = 0;
                        break;
                    }
                case 59:
                    if (!zza(t2, i15, i12)) {
                        i3 = 0;
                        z = false;
                        j = 0;
                        break;
                    } else {
                        Object object2 = unsafe2.getObject(t2, j3);
                        if (!(object2 instanceof zzgt)) {
                            i13 += zzhi.zzb(i15, (String) object2);
                            i3 = 0;
                            z = false;
                            j = 0;
                            break;
                        } else {
                            i13 += zzhi.zzc(i15, (zzgt) object2);
                            i3 = 0;
                            z = false;
                            j = 0;
                            break;
                        }
                    }
                case 60:
                    if (!zza(t2, i15, i12)) {
                        i3 = 0;
                        z = false;
                        j = 0;
                        break;
                    } else {
                        i13 += zzke.zza(i15, unsafe2.getObject(t2, j3), zza(i12));
                        i3 = 0;
                        z = false;
                        j = 0;
                        break;
                    }
                case 61:
                    if (!zza(t2, i15, i12)) {
                        i3 = 0;
                        z = false;
                        j = 0;
                        break;
                    } else {
                        i13 += zzhi.zzc(i15, (zzgt) unsafe2.getObject(t2, j3));
                        i3 = 0;
                        z = false;
                        j = 0;
                        break;
                    }
                case 62:
                    if (!zza(t2, i15, i12)) {
                        i3 = 0;
                        z = false;
                        j = 0;
                        break;
                    } else {
                        i13 += zzhi.zzg(i15, zzd(t2, j3));
                        i3 = 0;
                        z = false;
                        j = 0;
                        break;
                    }
                case 63:
                    if (!zza(t2, i15, i12)) {
                        i3 = 0;
                        z = false;
                        j = 0;
                        break;
                    } else {
                        i13 += zzhi.zzk(i15, zzd(t2, j3));
                        i3 = 0;
                        z = false;
                        j = 0;
                        break;
                    }
                case 64:
                    if (!zza(t2, i15, i12)) {
                        i3 = 0;
                        z = false;
                        j = 0;
                        break;
                    } else {
                        i13 += zzhi.zzj(i15, 0);
                        i3 = 0;
                        z = false;
                        j = 0;
                        break;
                    }
                case 65:
                    if (!zza(t2, i15, i12)) {
                        i3 = 0;
                        z = false;
                        j = 0;
                        break;
                    } else {
                        i13 += zzhi.zzh(i15, 0);
                        i3 = 0;
                        z = false;
                        j = 0;
                        break;
                    }
                case 66:
                    if (!zza(t2, i15, i12)) {
                        i3 = 0;
                        z = false;
                        j = 0;
                        break;
                    } else {
                        i13 += zzhi.zzh(i15, zzd(t2, j3));
                        i3 = 0;
                        z = false;
                        j = 0;
                        break;
                    }
                case 67:
                    if (!zza(t2, i15, i12)) {
                        i3 = 0;
                        z = false;
                        j = 0;
                        break;
                    } else {
                        i13 += zzhi.zzf(i15, zze(t2, j3));
                        i3 = 0;
                        z = false;
                        j = 0;
                        break;
                    }
                case 68:
                    if (!zza(t2, i15, i12)) {
                        i3 = 0;
                        z = false;
                        j = 0;
                        break;
                    } else {
                        i13 += zzhi.zzc(i15, (zzjj) unsafe2.getObject(t2, j3), zza(i12));
                        i3 = 0;
                        z = false;
                        j = 0;
                        break;
                    }
                default:
                    i3 = 0;
                    z = false;
                    j = 0;
                    break;
            }
            i12 += 3;
            i6 = i3;
            boolean z2 = z;
            long j4 = j;
        }
        int i19 = i6;
        int zza4 = i13 + zza(this.zzq, t2);
        if (!this.zzh) {
            return zza4;
        }
        zzhr<?> zza5 = this.zzr.zza((Object) t2);
        for (int i20 = i19; i20 < zza5.zza.zzc(); i20++) {
            Map.Entry<T, Object> zzb4 = zza5.zza.zzb(i20);
            i19 += zzhr.zza((zzht<?>) (zzht) zzb4.getKey(), zzb4.getValue());
        }
        for (Map.Entry next : zza5.zza.zzd()) {
            i19 += zzhr.zza((zzht<?>) (zzht) next.getKey(), next.getValue());
        }
        return zza4 + i19;
    }

    private static <UT, UB> int zza(zzku<UT, UB> zzku, T t) {
        return zzku.zzf(zzku.zzb(t));
    }

    private static List<?> zza(Object obj, long j) {
        return (List) zzla.zzf(obj, j);
    }

    /* JADX WARNING: Removed duplicated region for block: B:10:0x003f  */
    /* JADX WARNING: Removed duplicated region for block: B:163:0x05ad  */
    /* JADX WARNING: Removed duplicated region for block: B:178:0x05f0  */
    /* JADX WARNING: Removed duplicated region for block: B:331:0x0b5e  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void zza(T r14, com.google.android.gms.internal.measurement.zzln r15) throws java.io.IOException {
        /*
            r13 = this;
            int r0 = r15.zza()
            int r1 = com.google.android.gms.internal.measurement.zzib.zzf.zzk
            r2 = 267386880(0xff00000, float:2.3665827E-29)
            r3 = 0
            r4 = 1
            r5 = 0
            r6 = 1048575(0xfffff, float:1.469367E-39)
            if (r0 != r1) goto L_0x05c3
            com.google.android.gms.internal.measurement.zzku<?, ?> r0 = r13.zzq
            zza(r0, r14, (com.google.android.gms.internal.measurement.zzln) r15)
            boolean r0 = r13.zzh
            if (r0 == 0) goto L_0x0036
            com.google.android.gms.internal.measurement.zzhq<?> r0 = r13.zzr
            com.google.android.gms.internal.measurement.zzhr r0 = r0.zza((java.lang.Object) r14)
            com.google.android.gms.internal.measurement.zzkd<T, java.lang.Object> r1 = r0.zza
            boolean r1 = r1.isEmpty()
            if (r1 != 0) goto L_0x0036
            java.util.Iterator r0 = r0.zze()
            java.lang.Object r1 = r0.next()
            java.util.Map$Entry r1 = (java.util.Map.Entry) r1
            goto L_0x0038
        L_0x0036:
            r0 = r3
            r1 = r0
        L_0x0038:
            int[] r7 = r13.zzc
            int r7 = r7.length
            int r7 = r7 + -3
        L_0x003d:
            if (r7 < 0) goto L_0x05ab
            int r8 = r13.zzd((int) r7)
            int[] r9 = r13.zzc
            r9 = r9[r7]
        L_0x0049:
            if (r1 == 0) goto L_0x0067
            com.google.android.gms.internal.measurement.zzhq<?> r10 = r13.zzr
            int r10 = r10.zza((java.util.Map.Entry<?, ?>) r1)
            if (r10 <= r9) goto L_0x0067
            com.google.android.gms.internal.measurement.zzhq<?> r10 = r13.zzr
            r10.zza(r15, r1)
            boolean r1 = r0.hasNext()
            if (r1 == 0) goto L_0x0065
            java.lang.Object r1 = r0.next()
            java.util.Map$Entry r1 = (java.util.Map.Entry) r1
            goto L_0x0049
        L_0x0065:
            r1 = r3
            goto L_0x0049
        L_0x0067:
            r10 = r8 & r2
            int r10 = r10 >>> 20
            switch(r10) {
                case 0: goto L_0x0596;
                case 1: goto L_0x0584;
                case 2: goto L_0x0572;
                case 3: goto L_0x0560;
                case 4: goto L_0x054e;
                case 5: goto L_0x053c;
                case 6: goto L_0x052a;
                case 7: goto L_0x0517;
                case 8: goto L_0x0505;
                case 9: goto L_0x04ef;
                case 10: goto L_0x04db;
                case 11: goto L_0x04c8;
                case 12: goto L_0x04b5;
                case 13: goto L_0x04a2;
                case 14: goto L_0x048f;
                case 15: goto L_0x047c;
                case 16: goto L_0x0469;
                case 17: goto L_0x0453;
                case 18: goto L_0x043f;
                case 19: goto L_0x042b;
                case 20: goto L_0x0417;
                case 21: goto L_0x0403;
                case 22: goto L_0x03ef;
                case 23: goto L_0x03db;
                case 24: goto L_0x03c7;
                case 25: goto L_0x03b3;
                case 26: goto L_0x039f;
                case 27: goto L_0x0387;
                case 28: goto L_0x0373;
                case 29: goto L_0x035f;
                case 30: goto L_0x034b;
                case 31: goto L_0x0337;
                case 32: goto L_0x0323;
                case 33: goto L_0x030f;
                case 34: goto L_0x02fb;
                case 35: goto L_0x02e7;
                case 36: goto L_0x02d3;
                case 37: goto L_0x02bf;
                case 38: goto L_0x02ab;
                case 39: goto L_0x0297;
                case 40: goto L_0x0283;
                case 41: goto L_0x026f;
                case 42: goto L_0x025b;
                case 43: goto L_0x0247;
                case 44: goto L_0x0233;
                case 45: goto L_0x021f;
                case 46: goto L_0x020b;
                case 47: goto L_0x01f7;
                case 48: goto L_0x01e3;
                case 49: goto L_0x01cb;
                case 50: goto L_0x01bf;
                case 51: goto L_0x01ad;
                case 52: goto L_0x019b;
                case 53: goto L_0x0189;
                case 54: goto L_0x0177;
                case 55: goto L_0x0165;
                case 56: goto L_0x0153;
                case 57: goto L_0x0141;
                case 58: goto L_0x012f;
                case 59: goto L_0x011d;
                case 60: goto L_0x0107;
                case 61: goto L_0x00f3;
                case 62: goto L_0x00e1;
                case 63: goto L_0x00cf;
                case 64: goto L_0x00bd;
                case 65: goto L_0x00ab;
                case 66: goto L_0x0099;
                case 67: goto L_0x0087;
                case 68: goto L_0x0071;
                default: goto L_0x006f;
            }
        L_0x006f:
            goto L_0x05a7
        L_0x0071:
            boolean r10 = r13.zza(r14, (int) r9, (int) r7)
            if (r10 == 0) goto L_0x05a7
            r8 = r8 & r6
            long r10 = (long) r8
            java.lang.Object r8 = com.google.android.gms.internal.measurement.zzla.zzf(r14, r10)
            com.google.android.gms.internal.measurement.zzkc r10 = r13.zza((int) r7)
            r15.zzb((int) r9, (java.lang.Object) r8, (com.google.android.gms.internal.measurement.zzkc) r10)
            goto L_0x05a7
        L_0x0087:
            boolean r10 = r13.zza(r14, (int) r9, (int) r7)
            if (r10 == 0) goto L_0x05a7
            r8 = r8 & r6
            long r10 = (long) r8
            long r10 = zze(r14, r10)
            r15.zze((int) r9, (long) r10)
            goto L_0x05a7
        L_0x0099:
            boolean r10 = r13.zza(r14, (int) r9, (int) r7)
            if (r10 == 0) goto L_0x05a7
            r8 = r8 & r6
            long r10 = (long) r8
            int r8 = zzd(r14, r10)
            r15.zzf(r9, r8)
            goto L_0x05a7
        L_0x00ab:
            boolean r10 = r13.zza(r14, (int) r9, (int) r7)
            if (r10 == 0) goto L_0x05a7
            r8 = r8 & r6
            long r10 = (long) r8
            long r10 = zze(r14, r10)
            r15.zzb((int) r9, (long) r10)
            goto L_0x05a7
        L_0x00bd:
            boolean r10 = r13.zza(r14, (int) r9, (int) r7)
            if (r10 == 0) goto L_0x05a7
            r8 = r8 & r6
            long r10 = (long) r8
            int r8 = zzd(r14, r10)
            r15.zza((int) r9, (int) r8)
            goto L_0x05a7
        L_0x00cf:
            boolean r10 = r13.zza(r14, (int) r9, (int) r7)
            if (r10 == 0) goto L_0x05a7
            r8 = r8 & r6
            long r10 = (long) r8
            int r8 = zzd(r14, r10)
            r15.zzb((int) r9, (int) r8)
            goto L_0x05a7
        L_0x00e1:
            boolean r10 = r13.zza(r14, (int) r9, (int) r7)
            if (r10 == 0) goto L_0x05a7
            r8 = r8 & r6
            long r10 = (long) r8
            int r8 = zzd(r14, r10)
            r15.zze((int) r9, (int) r8)
            goto L_0x05a7
        L_0x00f3:
            boolean r10 = r13.zza(r14, (int) r9, (int) r7)
            if (r10 == 0) goto L_0x05a7
            r8 = r8 & r6
            long r10 = (long) r8
            java.lang.Object r8 = com.google.android.gms.internal.measurement.zzla.zzf(r14, r10)
            com.google.android.gms.internal.measurement.zzgt r8 = (com.google.android.gms.internal.measurement.zzgt) r8
            r15.zza((int) r9, (com.google.android.gms.internal.measurement.zzgt) r8)
            goto L_0x05a7
        L_0x0107:
            boolean r10 = r13.zza(r14, (int) r9, (int) r7)
            if (r10 == 0) goto L_0x05a7
            r8 = r8 & r6
            long r10 = (long) r8
            java.lang.Object r8 = com.google.android.gms.internal.measurement.zzla.zzf(r14, r10)
            com.google.android.gms.internal.measurement.zzkc r10 = r13.zza((int) r7)
            r15.zza((int) r9, (java.lang.Object) r8, (com.google.android.gms.internal.measurement.zzkc) r10)
            goto L_0x05a7
        L_0x011d:
            boolean r10 = r13.zza(r14, (int) r9, (int) r7)
            if (r10 == 0) goto L_0x05a7
            r8 = r8 & r6
            long r10 = (long) r8
            java.lang.Object r8 = com.google.android.gms.internal.measurement.zzla.zzf(r14, r10)
            zza((int) r9, (java.lang.Object) r8, (com.google.android.gms.internal.measurement.zzln) r15)
            goto L_0x05a7
        L_0x012f:
            boolean r10 = r13.zza(r14, (int) r9, (int) r7)
            if (r10 == 0) goto L_0x05a7
            r8 = r8 & r6
            long r10 = (long) r8
            boolean r8 = zzf(r14, r10)
            r15.zza((int) r9, (boolean) r8)
            goto L_0x05a7
        L_0x0141:
            boolean r10 = r13.zza(r14, (int) r9, (int) r7)
            if (r10 == 0) goto L_0x05a7
            r8 = r8 & r6
            long r10 = (long) r8
            int r8 = zzd(r14, r10)
            r15.zzd((int) r9, (int) r8)
            goto L_0x05a7
        L_0x0153:
            boolean r10 = r13.zza(r14, (int) r9, (int) r7)
            if (r10 == 0) goto L_0x05a7
            r8 = r8 & r6
            long r10 = (long) r8
            long r10 = zze(r14, r10)
            r15.zzd((int) r9, (long) r10)
            goto L_0x05a7
        L_0x0165:
            boolean r10 = r13.zza(r14, (int) r9, (int) r7)
            if (r10 == 0) goto L_0x05a7
            r8 = r8 & r6
            long r10 = (long) r8
            int r8 = zzd(r14, r10)
            r15.zzc((int) r9, (int) r8)
            goto L_0x05a7
        L_0x0177:
            boolean r10 = r13.zza(r14, (int) r9, (int) r7)
            if (r10 == 0) goto L_0x05a7
            r8 = r8 & r6
            long r10 = (long) r8
            long r10 = zze(r14, r10)
            r15.zzc((int) r9, (long) r10)
            goto L_0x05a7
        L_0x0189:
            boolean r10 = r13.zza(r14, (int) r9, (int) r7)
            if (r10 == 0) goto L_0x05a7
            r8 = r8 & r6
            long r10 = (long) r8
            long r10 = zze(r14, r10)
            r15.zza((int) r9, (long) r10)
            goto L_0x05a7
        L_0x019b:
            boolean r10 = r13.zza(r14, (int) r9, (int) r7)
            if (r10 == 0) goto L_0x05a7
            r8 = r8 & r6
            long r10 = (long) r8
            float r8 = zzc(r14, r10)
            r15.zza((int) r9, (float) r8)
            goto L_0x05a7
        L_0x01ad:
            boolean r10 = r13.zza(r14, (int) r9, (int) r7)
            if (r10 == 0) goto L_0x05a7
            r8 = r8 & r6
            long r10 = (long) r8
            double r10 = zzb(r14, (long) r10)
            r15.zza((int) r9, (double) r10)
            goto L_0x05a7
        L_0x01bf:
            r8 = r8 & r6
            long r10 = (long) r8
            java.lang.Object r8 = com.google.android.gms.internal.measurement.zzla.zzf(r14, r10)
            r13.zza((com.google.android.gms.internal.measurement.zzln) r15, (int) r9, (java.lang.Object) r8, (int) r7)
            goto L_0x05a7
        L_0x01cb:
            int[] r9 = r13.zzc
            r9 = r9[r7]
            r8 = r8 & r6
            long r10 = (long) r8
            java.lang.Object r8 = com.google.android.gms.internal.measurement.zzla.zzf(r14, r10)
            java.util.List r8 = (java.util.List) r8
            com.google.android.gms.internal.measurement.zzkc r10 = r13.zza((int) r7)
            com.google.android.gms.internal.measurement.zzke.zzb((int) r9, (java.util.List<?>) r8, (com.google.android.gms.internal.measurement.zzln) r15, (com.google.android.gms.internal.measurement.zzkc) r10)
            goto L_0x05a7
        L_0x01e3:
            int[] r9 = r13.zzc
            r9 = r9[r7]
            r8 = r8 & r6
            long r10 = (long) r8
            java.lang.Object r8 = com.google.android.gms.internal.measurement.zzla.zzf(r14, r10)
            java.util.List r8 = (java.util.List) r8
            com.google.android.gms.internal.measurement.zzke.zze(r9, r8, r15, r4)
            goto L_0x05a7
        L_0x01f7:
            int[] r9 = r13.zzc
            r9 = r9[r7]
            r8 = r8 & r6
            long r10 = (long) r8
            java.lang.Object r8 = com.google.android.gms.internal.measurement.zzla.zzf(r14, r10)
            java.util.List r8 = (java.util.List) r8
            com.google.android.gms.internal.measurement.zzke.zzj(r9, r8, r15, r4)
            goto L_0x05a7
        L_0x020b:
            int[] r9 = r13.zzc
            r9 = r9[r7]
            r8 = r8 & r6
            long r10 = (long) r8
            java.lang.Object r8 = com.google.android.gms.internal.measurement.zzla.zzf(r14, r10)
            java.util.List r8 = (java.util.List) r8
            com.google.android.gms.internal.measurement.zzke.zzg(r9, r8, r15, r4)
            goto L_0x05a7
        L_0x021f:
            int[] r9 = r13.zzc
            r9 = r9[r7]
            r8 = r8 & r6
            long r10 = (long) r8
            java.lang.Object r8 = com.google.android.gms.internal.measurement.zzla.zzf(r14, r10)
            java.util.List r8 = (java.util.List) r8
            com.google.android.gms.internal.measurement.zzke.zzl(r9, r8, r15, r4)
            goto L_0x05a7
        L_0x0233:
            int[] r9 = r13.zzc
            r9 = r9[r7]
            r8 = r8 & r6
            long r10 = (long) r8
            java.lang.Object r8 = com.google.android.gms.internal.measurement.zzla.zzf(r14, r10)
            java.util.List r8 = (java.util.List) r8
            com.google.android.gms.internal.measurement.zzke.zzm(r9, r8, r15, r4)
            goto L_0x05a7
        L_0x0247:
            int[] r9 = r13.zzc
            r9 = r9[r7]
            r8 = r8 & r6
            long r10 = (long) r8
            java.lang.Object r8 = com.google.android.gms.internal.measurement.zzla.zzf(r14, r10)
            java.util.List r8 = (java.util.List) r8
            com.google.android.gms.internal.measurement.zzke.zzi(r9, r8, r15, r4)
            goto L_0x05a7
        L_0x025b:
            int[] r9 = r13.zzc
            r9 = r9[r7]
            r8 = r8 & r6
            long r10 = (long) r8
            java.lang.Object r8 = com.google.android.gms.internal.measurement.zzla.zzf(r14, r10)
            java.util.List r8 = (java.util.List) r8
            com.google.android.gms.internal.measurement.zzke.zzn(r9, r8, r15, r4)
            goto L_0x05a7
        L_0x026f:
            int[] r9 = r13.zzc
            r9 = r9[r7]
            r8 = r8 & r6
            long r10 = (long) r8
            java.lang.Object r8 = com.google.android.gms.internal.measurement.zzla.zzf(r14, r10)
            java.util.List r8 = (java.util.List) r8
            com.google.android.gms.internal.measurement.zzke.zzk(r9, r8, r15, r4)
            goto L_0x05a7
        L_0x0283:
            int[] r9 = r13.zzc
            r9 = r9[r7]
            r8 = r8 & r6
            long r10 = (long) r8
            java.lang.Object r8 = com.google.android.gms.internal.measurement.zzla.zzf(r14, r10)
            java.util.List r8 = (java.util.List) r8
            com.google.android.gms.internal.measurement.zzke.zzf(r9, r8, r15, r4)
            goto L_0x05a7
        L_0x0297:
            int[] r9 = r13.zzc
            r9 = r9[r7]
            r8 = r8 & r6
            long r10 = (long) r8
            java.lang.Object r8 = com.google.android.gms.internal.measurement.zzla.zzf(r14, r10)
            java.util.List r8 = (java.util.List) r8
            com.google.android.gms.internal.measurement.zzke.zzh(r9, r8, r15, r4)
            goto L_0x05a7
        L_0x02ab:
            int[] r9 = r13.zzc
            r9 = r9[r7]
            r8 = r8 & r6
            long r10 = (long) r8
            java.lang.Object r8 = com.google.android.gms.internal.measurement.zzla.zzf(r14, r10)
            java.util.List r8 = (java.util.List) r8
            com.google.android.gms.internal.measurement.zzke.zzd(r9, r8, r15, r4)
            goto L_0x05a7
        L_0x02bf:
            int[] r9 = r13.zzc
            r9 = r9[r7]
            r8 = r8 & r6
            long r10 = (long) r8
            java.lang.Object r8 = com.google.android.gms.internal.measurement.zzla.zzf(r14, r10)
            java.util.List r8 = (java.util.List) r8
            com.google.android.gms.internal.measurement.zzke.zzc(r9, r8, r15, r4)
            goto L_0x05a7
        L_0x02d3:
            int[] r9 = r13.zzc
            r9 = r9[r7]
            r8 = r8 & r6
            long r10 = (long) r8
            java.lang.Object r8 = com.google.android.gms.internal.measurement.zzla.zzf(r14, r10)
            java.util.List r8 = (java.util.List) r8
            com.google.android.gms.internal.measurement.zzke.zzb((int) r9, (java.util.List<java.lang.Float>) r8, (com.google.android.gms.internal.measurement.zzln) r15, (boolean) r4)
            goto L_0x05a7
        L_0x02e7:
            int[] r9 = r13.zzc
            r9 = r9[r7]
            r8 = r8 & r6
            long r10 = (long) r8
            java.lang.Object r8 = com.google.android.gms.internal.measurement.zzla.zzf(r14, r10)
            java.util.List r8 = (java.util.List) r8
            com.google.android.gms.internal.measurement.zzke.zza((int) r9, (java.util.List<java.lang.Double>) r8, (com.google.android.gms.internal.measurement.zzln) r15, (boolean) r4)
            goto L_0x05a7
        L_0x02fb:
            int[] r9 = r13.zzc
            r9 = r9[r7]
            r8 = r8 & r6
            long r10 = (long) r8
            java.lang.Object r8 = com.google.android.gms.internal.measurement.zzla.zzf(r14, r10)
            java.util.List r8 = (java.util.List) r8
            com.google.android.gms.internal.measurement.zzke.zze(r9, r8, r15, r5)
            goto L_0x05a7
        L_0x030f:
            int[] r9 = r13.zzc
            r9 = r9[r7]
            r8 = r8 & r6
            long r10 = (long) r8
            java.lang.Object r8 = com.google.android.gms.internal.measurement.zzla.zzf(r14, r10)
            java.util.List r8 = (java.util.List) r8
            com.google.android.gms.internal.measurement.zzke.zzj(r9, r8, r15, r5)
            goto L_0x05a7
        L_0x0323:
            int[] r9 = r13.zzc
            r9 = r9[r7]
            r8 = r8 & r6
            long r10 = (long) r8
            java.lang.Object r8 = com.google.android.gms.internal.measurement.zzla.zzf(r14, r10)
            java.util.List r8 = (java.util.List) r8
            com.google.android.gms.internal.measurement.zzke.zzg(r9, r8, r15, r5)
            goto L_0x05a7
        L_0x0337:
            int[] r9 = r13.zzc
            r9 = r9[r7]
            r8 = r8 & r6
            long r10 = (long) r8
            java.lang.Object r8 = com.google.android.gms.internal.measurement.zzla.zzf(r14, r10)
            java.util.List r8 = (java.util.List) r8
            com.google.android.gms.internal.measurement.zzke.zzl(r9, r8, r15, r5)
            goto L_0x05a7
        L_0x034b:
            int[] r9 = r13.zzc
            r9 = r9[r7]
            r8 = r8 & r6
            long r10 = (long) r8
            java.lang.Object r8 = com.google.android.gms.internal.measurement.zzla.zzf(r14, r10)
            java.util.List r8 = (java.util.List) r8
            com.google.android.gms.internal.measurement.zzke.zzm(r9, r8, r15, r5)
            goto L_0x05a7
        L_0x035f:
            int[] r9 = r13.zzc
            r9 = r9[r7]
            r8 = r8 & r6
            long r10 = (long) r8
            java.lang.Object r8 = com.google.android.gms.internal.measurement.zzla.zzf(r14, r10)
            java.util.List r8 = (java.util.List) r8
            com.google.android.gms.internal.measurement.zzke.zzi(r9, r8, r15, r5)
            goto L_0x05a7
        L_0x0373:
            int[] r9 = r13.zzc
            r9 = r9[r7]
            r8 = r8 & r6
            long r10 = (long) r8
            java.lang.Object r8 = com.google.android.gms.internal.measurement.zzla.zzf(r14, r10)
            java.util.List r8 = (java.util.List) r8
            com.google.android.gms.internal.measurement.zzke.zzb((int) r9, (java.util.List<com.google.android.gms.internal.measurement.zzgt>) r8, (com.google.android.gms.internal.measurement.zzln) r15)
            goto L_0x05a7
        L_0x0387:
            int[] r9 = r13.zzc
            r9 = r9[r7]
            r8 = r8 & r6
            long r10 = (long) r8
            java.lang.Object r8 = com.google.android.gms.internal.measurement.zzla.zzf(r14, r10)
            java.util.List r8 = (java.util.List) r8
            com.google.android.gms.internal.measurement.zzkc r10 = r13.zza((int) r7)
            com.google.android.gms.internal.measurement.zzke.zza((int) r9, (java.util.List<?>) r8, (com.google.android.gms.internal.measurement.zzln) r15, (com.google.android.gms.internal.measurement.zzkc) r10)
            goto L_0x05a7
        L_0x039f:
            int[] r9 = r13.zzc
            r9 = r9[r7]
            r8 = r8 & r6
            long r10 = (long) r8
            java.lang.Object r8 = com.google.android.gms.internal.measurement.zzla.zzf(r14, r10)
            java.util.List r8 = (java.util.List) r8
            com.google.android.gms.internal.measurement.zzke.zza((int) r9, (java.util.List<java.lang.String>) r8, (com.google.android.gms.internal.measurement.zzln) r15)
            goto L_0x05a7
        L_0x03b3:
            int[] r9 = r13.zzc
            r9 = r9[r7]
            r8 = r8 & r6
            long r10 = (long) r8
            java.lang.Object r8 = com.google.android.gms.internal.measurement.zzla.zzf(r14, r10)
            java.util.List r8 = (java.util.List) r8
            com.google.android.gms.internal.measurement.zzke.zzn(r9, r8, r15, r5)
            goto L_0x05a7
        L_0x03c7:
            int[] r9 = r13.zzc
            r9 = r9[r7]
            r8 = r8 & r6
            long r10 = (long) r8
            java.lang.Object r8 = com.google.android.gms.internal.measurement.zzla.zzf(r14, r10)
            java.util.List r8 = (java.util.List) r8
            com.google.android.gms.internal.measurement.zzke.zzk(r9, r8, r15, r5)
            goto L_0x05a7
        L_0x03db:
            int[] r9 = r13.zzc
            r9 = r9[r7]
            r8 = r8 & r6
            long r10 = (long) r8
            java.lang.Object r8 = com.google.android.gms.internal.measurement.zzla.zzf(r14, r10)
            java.util.List r8 = (java.util.List) r8
            com.google.android.gms.internal.measurement.zzke.zzf(r9, r8, r15, r5)
            goto L_0x05a7
        L_0x03ef:
            int[] r9 = r13.zzc
            r9 = r9[r7]
            r8 = r8 & r6
            long r10 = (long) r8
            java.lang.Object r8 = com.google.android.gms.internal.measurement.zzla.zzf(r14, r10)
            java.util.List r8 = (java.util.List) r8
            com.google.android.gms.internal.measurement.zzke.zzh(r9, r8, r15, r5)
            goto L_0x05a7
        L_0x0403:
            int[] r9 = r13.zzc
            r9 = r9[r7]
            r8 = r8 & r6
            long r10 = (long) r8
            java.lang.Object r8 = com.google.android.gms.internal.measurement.zzla.zzf(r14, r10)
            java.util.List r8 = (java.util.List) r8
            com.google.android.gms.internal.measurement.zzke.zzd(r9, r8, r15, r5)
            goto L_0x05a7
        L_0x0417:
            int[] r9 = r13.zzc
            r9 = r9[r7]
            r8 = r8 & r6
            long r10 = (long) r8
            java.lang.Object r8 = com.google.android.gms.internal.measurement.zzla.zzf(r14, r10)
            java.util.List r8 = (java.util.List) r8
            com.google.android.gms.internal.measurement.zzke.zzc(r9, r8, r15, r5)
            goto L_0x05a7
        L_0x042b:
            int[] r9 = r13.zzc
            r9 = r9[r7]
            r8 = r8 & r6
            long r10 = (long) r8
            java.lang.Object r8 = com.google.android.gms.internal.measurement.zzla.zzf(r14, r10)
            java.util.List r8 = (java.util.List) r8
            com.google.android.gms.internal.measurement.zzke.zzb((int) r9, (java.util.List<java.lang.Float>) r8, (com.google.android.gms.internal.measurement.zzln) r15, (boolean) r5)
            goto L_0x05a7
        L_0x043f:
            int[] r9 = r13.zzc
            r9 = r9[r7]
            r8 = r8 & r6
            long r10 = (long) r8
            java.lang.Object r8 = com.google.android.gms.internal.measurement.zzla.zzf(r14, r10)
            java.util.List r8 = (java.util.List) r8
            com.google.android.gms.internal.measurement.zzke.zza((int) r9, (java.util.List<java.lang.Double>) r8, (com.google.android.gms.internal.measurement.zzln) r15, (boolean) r5)
            goto L_0x05a7
        L_0x0453:
            boolean r10 = r13.zza(r14, (int) r7)
            if (r10 == 0) goto L_0x05a7
            r8 = r8 & r6
            long r10 = (long) r8
            java.lang.Object r8 = com.google.android.gms.internal.measurement.zzla.zzf(r14, r10)
            com.google.android.gms.internal.measurement.zzkc r10 = r13.zza((int) r7)
            r15.zzb((int) r9, (java.lang.Object) r8, (com.google.android.gms.internal.measurement.zzkc) r10)
            goto L_0x05a7
        L_0x0469:
            boolean r10 = r13.zza(r14, (int) r7)
            if (r10 == 0) goto L_0x05a7
            r8 = r8 & r6
            long r10 = (long) r8
            long r10 = com.google.android.gms.internal.measurement.zzla.zzb(r14, r10)
            r15.zze((int) r9, (long) r10)
            goto L_0x05a7
        L_0x047c:
            boolean r10 = r13.zza(r14, (int) r7)
            if (r10 == 0) goto L_0x05a7
            r8 = r8 & r6
            long r10 = (long) r8
            int r8 = com.google.android.gms.internal.measurement.zzla.zza((java.lang.Object) r14, (long) r10)
            r15.zzf(r9, r8)
            goto L_0x05a7
        L_0x048f:
            boolean r10 = r13.zza(r14, (int) r7)
            if (r10 == 0) goto L_0x05a7
            r8 = r8 & r6
            long r10 = (long) r8
            long r10 = com.google.android.gms.internal.measurement.zzla.zzb(r14, r10)
            r15.zzb((int) r9, (long) r10)
            goto L_0x05a7
        L_0x04a2:
            boolean r10 = r13.zza(r14, (int) r7)
            if (r10 == 0) goto L_0x05a7
            r8 = r8 & r6
            long r10 = (long) r8
            int r8 = com.google.android.gms.internal.measurement.zzla.zza((java.lang.Object) r14, (long) r10)
            r15.zza((int) r9, (int) r8)
            goto L_0x05a7
        L_0x04b5:
            boolean r10 = r13.zza(r14, (int) r7)
            if (r10 == 0) goto L_0x05a7
            r8 = r8 & r6
            long r10 = (long) r8
            int r8 = com.google.android.gms.internal.measurement.zzla.zza((java.lang.Object) r14, (long) r10)
            r15.zzb((int) r9, (int) r8)
            goto L_0x05a7
        L_0x04c8:
            boolean r10 = r13.zza(r14, (int) r7)
            if (r10 == 0) goto L_0x05a7
            r8 = r8 & r6
            long r10 = (long) r8
            int r8 = com.google.android.gms.internal.measurement.zzla.zza((java.lang.Object) r14, (long) r10)
            r15.zze((int) r9, (int) r8)
            goto L_0x05a7
        L_0x04db:
            boolean r10 = r13.zza(r14, (int) r7)
            if (r10 == 0) goto L_0x05a7
            r8 = r8 & r6
            long r10 = (long) r8
            java.lang.Object r8 = com.google.android.gms.internal.measurement.zzla.zzf(r14, r10)
            com.google.android.gms.internal.measurement.zzgt r8 = (com.google.android.gms.internal.measurement.zzgt) r8
            r15.zza((int) r9, (com.google.android.gms.internal.measurement.zzgt) r8)
            goto L_0x05a7
        L_0x04ef:
            boolean r10 = r13.zza(r14, (int) r7)
            if (r10 == 0) goto L_0x05a7
            r8 = r8 & r6
            long r10 = (long) r8
            java.lang.Object r8 = com.google.android.gms.internal.measurement.zzla.zzf(r14, r10)
            com.google.android.gms.internal.measurement.zzkc r10 = r13.zza((int) r7)
            r15.zza((int) r9, (java.lang.Object) r8, (com.google.android.gms.internal.measurement.zzkc) r10)
            goto L_0x05a7
        L_0x0505:
            boolean r10 = r13.zza(r14, (int) r7)
            if (r10 == 0) goto L_0x05a7
            r8 = r8 & r6
            long r10 = (long) r8
            java.lang.Object r8 = com.google.android.gms.internal.measurement.zzla.zzf(r14, r10)
            zza((int) r9, (java.lang.Object) r8, (com.google.android.gms.internal.measurement.zzln) r15)
            goto L_0x05a7
        L_0x0517:
            boolean r10 = r13.zza(r14, (int) r7)
            if (r10 == 0) goto L_0x05a7
            r8 = r8 & r6
            long r10 = (long) r8
            boolean r8 = com.google.android.gms.internal.measurement.zzla.zzc(r14, r10)
            r15.zza((int) r9, (boolean) r8)
            goto L_0x05a7
        L_0x052a:
            boolean r10 = r13.zza(r14, (int) r7)
            if (r10 == 0) goto L_0x05a7
            r8 = r8 & r6
            long r10 = (long) r8
            int r8 = com.google.android.gms.internal.measurement.zzla.zza((java.lang.Object) r14, (long) r10)
            r15.zzd((int) r9, (int) r8)
            goto L_0x05a7
        L_0x053c:
            boolean r10 = r13.zza(r14, (int) r7)
            if (r10 == 0) goto L_0x05a7
            r8 = r8 & r6
            long r10 = (long) r8
            long r10 = com.google.android.gms.internal.measurement.zzla.zzb(r14, r10)
            r15.zzd((int) r9, (long) r10)
            goto L_0x05a7
        L_0x054e:
            boolean r10 = r13.zza(r14, (int) r7)
            if (r10 == 0) goto L_0x05a7
            r8 = r8 & r6
            long r10 = (long) r8
            int r8 = com.google.android.gms.internal.measurement.zzla.zza((java.lang.Object) r14, (long) r10)
            r15.zzc((int) r9, (int) r8)
            goto L_0x05a7
        L_0x0560:
            boolean r10 = r13.zza(r14, (int) r7)
            if (r10 == 0) goto L_0x05a7
            r8 = r8 & r6
            long r10 = (long) r8
            long r10 = com.google.android.gms.internal.measurement.zzla.zzb(r14, r10)
            r15.zzc((int) r9, (long) r10)
            goto L_0x05a7
        L_0x0572:
            boolean r10 = r13.zza(r14, (int) r7)
            if (r10 == 0) goto L_0x05a7
            r8 = r8 & r6
            long r10 = (long) r8
            long r10 = com.google.android.gms.internal.measurement.zzla.zzb(r14, r10)
            r15.zza((int) r9, (long) r10)
            goto L_0x05a7
        L_0x0584:
            boolean r10 = r13.zza(r14, (int) r7)
            if (r10 == 0) goto L_0x05a7
            r8 = r8 & r6
            long r10 = (long) r8
            float r8 = com.google.android.gms.internal.measurement.zzla.zzd(r14, r10)
            r15.zza((int) r9, (float) r8)
            goto L_0x05a7
        L_0x0596:
            boolean r10 = r13.zza(r14, (int) r7)
            if (r10 == 0) goto L_0x05a7
            r8 = r8 & r6
            long r10 = (long) r8
            double r10 = com.google.android.gms.internal.measurement.zzla.zze(r14, r10)
            r15.zza((int) r9, (double) r10)
        L_0x05a7:
            int r7 = r7 + -3
            goto L_0x003d
        L_0x05ab:
            if (r1 == 0) goto L_0x05c2
            com.google.android.gms.internal.measurement.zzhq<?> r14 = r13.zzr
            r14.zza(r15, r1)
            boolean r14 = r0.hasNext()
            if (r14 == 0) goto L_0x05c0
            java.lang.Object r14 = r0.next()
            java.util.Map$Entry r14 = (java.util.Map.Entry) r14
            r1 = r14
            goto L_0x05ab
        L_0x05c0:
            r1 = r3
            goto L_0x05ab
        L_0x05c2:
            return
        L_0x05c3:
            boolean r0 = r13.zzj
            if (r0 == 0) goto L_0x0b78
            boolean r0 = r13.zzh
            if (r0 == 0) goto L_0x05e8
            com.google.android.gms.internal.measurement.zzhq<?> r0 = r13.zzr
            com.google.android.gms.internal.measurement.zzhr r0 = r0.zza((java.lang.Object) r14)
            com.google.android.gms.internal.measurement.zzkd<T, java.lang.Object> r1 = r0.zza
            boolean r1 = r1.isEmpty()
            if (r1 != 0) goto L_0x05e8
            java.util.Iterator r0 = r0.zzd()
            java.lang.Object r1 = r0.next()
            java.util.Map$Entry r1 = (java.util.Map.Entry) r1
            goto L_0x05ea
        L_0x05e8:
            r0 = r3
            r1 = r0
        L_0x05ea:
            int[] r7 = r13.zzc
            int r7 = r7.length
            r8 = r5
        L_0x05ee:
            if (r8 >= r7) goto L_0x0b5c
            int r9 = r13.zzd((int) r8)
            int[] r10 = r13.zzc
            r10 = r10[r8]
        L_0x05fa:
            if (r1 == 0) goto L_0x0618
            com.google.android.gms.internal.measurement.zzhq<?> r11 = r13.zzr
            int r11 = r11.zza((java.util.Map.Entry<?, ?>) r1)
            if (r11 > r10) goto L_0x0618
            com.google.android.gms.internal.measurement.zzhq<?> r11 = r13.zzr
            r11.zza(r15, r1)
            boolean r1 = r0.hasNext()
            if (r1 == 0) goto L_0x0616
            java.lang.Object r1 = r0.next()
            java.util.Map$Entry r1 = (java.util.Map.Entry) r1
            goto L_0x05fa
        L_0x0616:
            r1 = r3
            goto L_0x05fa
        L_0x0618:
            r11 = r9 & r2
            int r11 = r11 >>> 20
            switch(r11) {
                case 0: goto L_0x0b47;
                case 1: goto L_0x0b35;
                case 2: goto L_0x0b23;
                case 3: goto L_0x0b11;
                case 4: goto L_0x0aff;
                case 5: goto L_0x0aed;
                case 6: goto L_0x0adb;
                case 7: goto L_0x0ac8;
                case 8: goto L_0x0ab6;
                case 9: goto L_0x0aa0;
                case 10: goto L_0x0a8c;
                case 11: goto L_0x0a79;
                case 12: goto L_0x0a66;
                case 13: goto L_0x0a53;
                case 14: goto L_0x0a40;
                case 15: goto L_0x0a2d;
                case 16: goto L_0x0a1a;
                case 17: goto L_0x0a04;
                case 18: goto L_0x09f0;
                case 19: goto L_0x09dc;
                case 20: goto L_0x09c8;
                case 21: goto L_0x09b4;
                case 22: goto L_0x09a0;
                case 23: goto L_0x098c;
                case 24: goto L_0x0978;
                case 25: goto L_0x0964;
                case 26: goto L_0x0950;
                case 27: goto L_0x0938;
                case 28: goto L_0x0924;
                case 29: goto L_0x0910;
                case 30: goto L_0x08fc;
                case 31: goto L_0x08e8;
                case 32: goto L_0x08d4;
                case 33: goto L_0x08c0;
                case 34: goto L_0x08ac;
                case 35: goto L_0x0898;
                case 36: goto L_0x0884;
                case 37: goto L_0x0870;
                case 38: goto L_0x085c;
                case 39: goto L_0x0848;
                case 40: goto L_0x0834;
                case 41: goto L_0x0820;
                case 42: goto L_0x080c;
                case 43: goto L_0x07f8;
                case 44: goto L_0x07e4;
                case 45: goto L_0x07d0;
                case 46: goto L_0x07bc;
                case 47: goto L_0x07a8;
                case 48: goto L_0x0794;
                case 49: goto L_0x077c;
                case 50: goto L_0x0770;
                case 51: goto L_0x075e;
                case 52: goto L_0x074c;
                case 53: goto L_0x073a;
                case 54: goto L_0x0728;
                case 55: goto L_0x0716;
                case 56: goto L_0x0704;
                case 57: goto L_0x06f2;
                case 58: goto L_0x06e0;
                case 59: goto L_0x06ce;
                case 60: goto L_0x06b8;
                case 61: goto L_0x06a4;
                case 62: goto L_0x0692;
                case 63: goto L_0x0680;
                case 64: goto L_0x066e;
                case 65: goto L_0x065c;
                case 66: goto L_0x064a;
                case 67: goto L_0x0638;
                case 68: goto L_0x0622;
                default: goto L_0x0620;
            }
        L_0x0620:
            goto L_0x0b58
        L_0x0622:
            boolean r11 = r13.zza(r14, (int) r10, (int) r8)
            if (r11 == 0) goto L_0x0b58
            r9 = r9 & r6
            long r11 = (long) r9
            java.lang.Object r9 = com.google.android.gms.internal.measurement.zzla.zzf(r14, r11)
            com.google.android.gms.internal.measurement.zzkc r11 = r13.zza((int) r8)
            r15.zzb((int) r10, (java.lang.Object) r9, (com.google.android.gms.internal.measurement.zzkc) r11)
            goto L_0x0b58
        L_0x0638:
            boolean r11 = r13.zza(r14, (int) r10, (int) r8)
            if (r11 == 0) goto L_0x0b58
            r9 = r9 & r6
            long r11 = (long) r9
            long r11 = zze(r14, r11)
            r15.zze((int) r10, (long) r11)
            goto L_0x0b58
        L_0x064a:
            boolean r11 = r13.zza(r14, (int) r10, (int) r8)
            if (r11 == 0) goto L_0x0b58
            r9 = r9 & r6
            long r11 = (long) r9
            int r9 = zzd(r14, r11)
            r15.zzf(r10, r9)
            goto L_0x0b58
        L_0x065c:
            boolean r11 = r13.zza(r14, (int) r10, (int) r8)
            if (r11 == 0) goto L_0x0b58
            r9 = r9 & r6
            long r11 = (long) r9
            long r11 = zze(r14, r11)
            r15.zzb((int) r10, (long) r11)
            goto L_0x0b58
        L_0x066e:
            boolean r11 = r13.zza(r14, (int) r10, (int) r8)
            if (r11 == 0) goto L_0x0b58
            r9 = r9 & r6
            long r11 = (long) r9
            int r9 = zzd(r14, r11)
            r15.zza((int) r10, (int) r9)
            goto L_0x0b58
        L_0x0680:
            boolean r11 = r13.zza(r14, (int) r10, (int) r8)
            if (r11 == 0) goto L_0x0b58
            r9 = r9 & r6
            long r11 = (long) r9
            int r9 = zzd(r14, r11)
            r15.zzb((int) r10, (int) r9)
            goto L_0x0b58
        L_0x0692:
            boolean r11 = r13.zza(r14, (int) r10, (int) r8)
            if (r11 == 0) goto L_0x0b58
            r9 = r9 & r6
            long r11 = (long) r9
            int r9 = zzd(r14, r11)
            r15.zze((int) r10, (int) r9)
            goto L_0x0b58
        L_0x06a4:
            boolean r11 = r13.zza(r14, (int) r10, (int) r8)
            if (r11 == 0) goto L_0x0b58
            r9 = r9 & r6
            long r11 = (long) r9
            java.lang.Object r9 = com.google.android.gms.internal.measurement.zzla.zzf(r14, r11)
            com.google.android.gms.internal.measurement.zzgt r9 = (com.google.android.gms.internal.measurement.zzgt) r9
            r15.zza((int) r10, (com.google.android.gms.internal.measurement.zzgt) r9)
            goto L_0x0b58
        L_0x06b8:
            boolean r11 = r13.zza(r14, (int) r10, (int) r8)
            if (r11 == 0) goto L_0x0b58
            r9 = r9 & r6
            long r11 = (long) r9
            java.lang.Object r9 = com.google.android.gms.internal.measurement.zzla.zzf(r14, r11)
            com.google.android.gms.internal.measurement.zzkc r11 = r13.zza((int) r8)
            r15.zza((int) r10, (java.lang.Object) r9, (com.google.android.gms.internal.measurement.zzkc) r11)
            goto L_0x0b58
        L_0x06ce:
            boolean r11 = r13.zza(r14, (int) r10, (int) r8)
            if (r11 == 0) goto L_0x0b58
            r9 = r9 & r6
            long r11 = (long) r9
            java.lang.Object r9 = com.google.android.gms.internal.measurement.zzla.zzf(r14, r11)
            zza((int) r10, (java.lang.Object) r9, (com.google.android.gms.internal.measurement.zzln) r15)
            goto L_0x0b58
        L_0x06e0:
            boolean r11 = r13.zza(r14, (int) r10, (int) r8)
            if (r11 == 0) goto L_0x0b58
            r9 = r9 & r6
            long r11 = (long) r9
            boolean r9 = zzf(r14, r11)
            r15.zza((int) r10, (boolean) r9)
            goto L_0x0b58
        L_0x06f2:
            boolean r11 = r13.zza(r14, (int) r10, (int) r8)
            if (r11 == 0) goto L_0x0b58
            r9 = r9 & r6
            long r11 = (long) r9
            int r9 = zzd(r14, r11)
            r15.zzd((int) r10, (int) r9)
            goto L_0x0b58
        L_0x0704:
            boolean r11 = r13.zza(r14, (int) r10, (int) r8)
            if (r11 == 0) goto L_0x0b58
            r9 = r9 & r6
            long r11 = (long) r9
            long r11 = zze(r14, r11)
            r15.zzd((int) r10, (long) r11)
            goto L_0x0b58
        L_0x0716:
            boolean r11 = r13.zza(r14, (int) r10, (int) r8)
            if (r11 == 0) goto L_0x0b58
            r9 = r9 & r6
            long r11 = (long) r9
            int r9 = zzd(r14, r11)
            r15.zzc((int) r10, (int) r9)
            goto L_0x0b58
        L_0x0728:
            boolean r11 = r13.zza(r14, (int) r10, (int) r8)
            if (r11 == 0) goto L_0x0b58
            r9 = r9 & r6
            long r11 = (long) r9
            long r11 = zze(r14, r11)
            r15.zzc((int) r10, (long) r11)
            goto L_0x0b58
        L_0x073a:
            boolean r11 = r13.zza(r14, (int) r10, (int) r8)
            if (r11 == 0) goto L_0x0b58
            r9 = r9 & r6
            long r11 = (long) r9
            long r11 = zze(r14, r11)
            r15.zza((int) r10, (long) r11)
            goto L_0x0b58
        L_0x074c:
            boolean r11 = r13.zza(r14, (int) r10, (int) r8)
            if (r11 == 0) goto L_0x0b58
            r9 = r9 & r6
            long r11 = (long) r9
            float r9 = zzc(r14, r11)
            r15.zza((int) r10, (float) r9)
            goto L_0x0b58
        L_0x075e:
            boolean r11 = r13.zza(r14, (int) r10, (int) r8)
            if (r11 == 0) goto L_0x0b58
            r9 = r9 & r6
            long r11 = (long) r9
            double r11 = zzb(r14, (long) r11)
            r15.zza((int) r10, (double) r11)
            goto L_0x0b58
        L_0x0770:
            r9 = r9 & r6
            long r11 = (long) r9
            java.lang.Object r9 = com.google.android.gms.internal.measurement.zzla.zzf(r14, r11)
            r13.zza((com.google.android.gms.internal.measurement.zzln) r15, (int) r10, (java.lang.Object) r9, (int) r8)
            goto L_0x0b58
        L_0x077c:
            int[] r10 = r13.zzc
            r10 = r10[r8]
            r9 = r9 & r6
            long r11 = (long) r9
            java.lang.Object r9 = com.google.android.gms.internal.measurement.zzla.zzf(r14, r11)
            java.util.List r9 = (java.util.List) r9
            com.google.android.gms.internal.measurement.zzkc r11 = r13.zza((int) r8)
            com.google.android.gms.internal.measurement.zzke.zzb((int) r10, (java.util.List<?>) r9, (com.google.android.gms.internal.measurement.zzln) r15, (com.google.android.gms.internal.measurement.zzkc) r11)
            goto L_0x0b58
        L_0x0794:
            int[] r10 = r13.zzc
            r10 = r10[r8]
            r9 = r9 & r6
            long r11 = (long) r9
            java.lang.Object r9 = com.google.android.gms.internal.measurement.zzla.zzf(r14, r11)
            java.util.List r9 = (java.util.List) r9
            com.google.android.gms.internal.measurement.zzke.zze(r10, r9, r15, r4)
            goto L_0x0b58
        L_0x07a8:
            int[] r10 = r13.zzc
            r10 = r10[r8]
            r9 = r9 & r6
            long r11 = (long) r9
            java.lang.Object r9 = com.google.android.gms.internal.measurement.zzla.zzf(r14, r11)
            java.util.List r9 = (java.util.List) r9
            com.google.android.gms.internal.measurement.zzke.zzj(r10, r9, r15, r4)
            goto L_0x0b58
        L_0x07bc:
            int[] r10 = r13.zzc
            r10 = r10[r8]
            r9 = r9 & r6
            long r11 = (long) r9
            java.lang.Object r9 = com.google.android.gms.internal.measurement.zzla.zzf(r14, r11)
            java.util.List r9 = (java.util.List) r9
            com.google.android.gms.internal.measurement.zzke.zzg(r10, r9, r15, r4)
            goto L_0x0b58
        L_0x07d0:
            int[] r10 = r13.zzc
            r10 = r10[r8]
            r9 = r9 & r6
            long r11 = (long) r9
            java.lang.Object r9 = com.google.android.gms.internal.measurement.zzla.zzf(r14, r11)
            java.util.List r9 = (java.util.List) r9
            com.google.android.gms.internal.measurement.zzke.zzl(r10, r9, r15, r4)
            goto L_0x0b58
        L_0x07e4:
            int[] r10 = r13.zzc
            r10 = r10[r8]
            r9 = r9 & r6
            long r11 = (long) r9
            java.lang.Object r9 = com.google.android.gms.internal.measurement.zzla.zzf(r14, r11)
            java.util.List r9 = (java.util.List) r9
            com.google.android.gms.internal.measurement.zzke.zzm(r10, r9, r15, r4)
            goto L_0x0b58
        L_0x07f8:
            int[] r10 = r13.zzc
            r10 = r10[r8]
            r9 = r9 & r6
            long r11 = (long) r9
            java.lang.Object r9 = com.google.android.gms.internal.measurement.zzla.zzf(r14, r11)
            java.util.List r9 = (java.util.List) r9
            com.google.android.gms.internal.measurement.zzke.zzi(r10, r9, r15, r4)
            goto L_0x0b58
        L_0x080c:
            int[] r10 = r13.zzc
            r10 = r10[r8]
            r9 = r9 & r6
            long r11 = (long) r9
            java.lang.Object r9 = com.google.android.gms.internal.measurement.zzla.zzf(r14, r11)
            java.util.List r9 = (java.util.List) r9
            com.google.android.gms.internal.measurement.zzke.zzn(r10, r9, r15, r4)
            goto L_0x0b58
        L_0x0820:
            int[] r10 = r13.zzc
            r10 = r10[r8]
            r9 = r9 & r6
            long r11 = (long) r9
            java.lang.Object r9 = com.google.android.gms.internal.measurement.zzla.zzf(r14, r11)
            java.util.List r9 = (java.util.List) r9
            com.google.android.gms.internal.measurement.zzke.zzk(r10, r9, r15, r4)
            goto L_0x0b58
        L_0x0834:
            int[] r10 = r13.zzc
            r10 = r10[r8]
            r9 = r9 & r6
            long r11 = (long) r9
            java.lang.Object r9 = com.google.android.gms.internal.measurement.zzla.zzf(r14, r11)
            java.util.List r9 = (java.util.List) r9
            com.google.android.gms.internal.measurement.zzke.zzf(r10, r9, r15, r4)
            goto L_0x0b58
        L_0x0848:
            int[] r10 = r13.zzc
            r10 = r10[r8]
            r9 = r9 & r6
            long r11 = (long) r9
            java.lang.Object r9 = com.google.android.gms.internal.measurement.zzla.zzf(r14, r11)
            java.util.List r9 = (java.util.List) r9
            com.google.android.gms.internal.measurement.zzke.zzh(r10, r9, r15, r4)
            goto L_0x0b58
        L_0x085c:
            int[] r10 = r13.zzc
            r10 = r10[r8]
            r9 = r9 & r6
            long r11 = (long) r9
            java.lang.Object r9 = com.google.android.gms.internal.measurement.zzla.zzf(r14, r11)
            java.util.List r9 = (java.util.List) r9
            com.google.android.gms.internal.measurement.zzke.zzd(r10, r9, r15, r4)
            goto L_0x0b58
        L_0x0870:
            int[] r10 = r13.zzc
            r10 = r10[r8]
            r9 = r9 & r6
            long r11 = (long) r9
            java.lang.Object r9 = com.google.android.gms.internal.measurement.zzla.zzf(r14, r11)
            java.util.List r9 = (java.util.List) r9
            com.google.android.gms.internal.measurement.zzke.zzc(r10, r9, r15, r4)
            goto L_0x0b58
        L_0x0884:
            int[] r10 = r13.zzc
            r10 = r10[r8]
            r9 = r9 & r6
            long r11 = (long) r9
            java.lang.Object r9 = com.google.android.gms.internal.measurement.zzla.zzf(r14, r11)
            java.util.List r9 = (java.util.List) r9
            com.google.android.gms.internal.measurement.zzke.zzb((int) r10, (java.util.List<java.lang.Float>) r9, (com.google.android.gms.internal.measurement.zzln) r15, (boolean) r4)
            goto L_0x0b58
        L_0x0898:
            int[] r10 = r13.zzc
            r10 = r10[r8]
            r9 = r9 & r6
            long r11 = (long) r9
            java.lang.Object r9 = com.google.android.gms.internal.measurement.zzla.zzf(r14, r11)
            java.util.List r9 = (java.util.List) r9
            com.google.android.gms.internal.measurement.zzke.zza((int) r10, (java.util.List<java.lang.Double>) r9, (com.google.android.gms.internal.measurement.zzln) r15, (boolean) r4)
            goto L_0x0b58
        L_0x08ac:
            int[] r10 = r13.zzc
            r10 = r10[r8]
            r9 = r9 & r6
            long r11 = (long) r9
            java.lang.Object r9 = com.google.android.gms.internal.measurement.zzla.zzf(r14, r11)
            java.util.List r9 = (java.util.List) r9
            com.google.android.gms.internal.measurement.zzke.zze(r10, r9, r15, r5)
            goto L_0x0b58
        L_0x08c0:
            int[] r10 = r13.zzc
            r10 = r10[r8]
            r9 = r9 & r6
            long r11 = (long) r9
            java.lang.Object r9 = com.google.android.gms.internal.measurement.zzla.zzf(r14, r11)
            java.util.List r9 = (java.util.List) r9
            com.google.android.gms.internal.measurement.zzke.zzj(r10, r9, r15, r5)
            goto L_0x0b58
        L_0x08d4:
            int[] r10 = r13.zzc
            r10 = r10[r8]
            r9 = r9 & r6
            long r11 = (long) r9
            java.lang.Object r9 = com.google.android.gms.internal.measurement.zzla.zzf(r14, r11)
            java.util.List r9 = (java.util.List) r9
            com.google.android.gms.internal.measurement.zzke.zzg(r10, r9, r15, r5)
            goto L_0x0b58
        L_0x08e8:
            int[] r10 = r13.zzc
            r10 = r10[r8]
            r9 = r9 & r6
            long r11 = (long) r9
            java.lang.Object r9 = com.google.android.gms.internal.measurement.zzla.zzf(r14, r11)
            java.util.List r9 = (java.util.List) r9
            com.google.android.gms.internal.measurement.zzke.zzl(r10, r9, r15, r5)
            goto L_0x0b58
        L_0x08fc:
            int[] r10 = r13.zzc
            r10 = r10[r8]
            r9 = r9 & r6
            long r11 = (long) r9
            java.lang.Object r9 = com.google.android.gms.internal.measurement.zzla.zzf(r14, r11)
            java.util.List r9 = (java.util.List) r9
            com.google.android.gms.internal.measurement.zzke.zzm(r10, r9, r15, r5)
            goto L_0x0b58
        L_0x0910:
            int[] r10 = r13.zzc
            r10 = r10[r8]
            r9 = r9 & r6
            long r11 = (long) r9
            java.lang.Object r9 = com.google.android.gms.internal.measurement.zzla.zzf(r14, r11)
            java.util.List r9 = (java.util.List) r9
            com.google.android.gms.internal.measurement.zzke.zzi(r10, r9, r15, r5)
            goto L_0x0b58
        L_0x0924:
            int[] r10 = r13.zzc
            r10 = r10[r8]
            r9 = r9 & r6
            long r11 = (long) r9
            java.lang.Object r9 = com.google.android.gms.internal.measurement.zzla.zzf(r14, r11)
            java.util.List r9 = (java.util.List) r9
            com.google.android.gms.internal.measurement.zzke.zzb((int) r10, (java.util.List<com.google.android.gms.internal.measurement.zzgt>) r9, (com.google.android.gms.internal.measurement.zzln) r15)
            goto L_0x0b58
        L_0x0938:
            int[] r10 = r13.zzc
            r10 = r10[r8]
            r9 = r9 & r6
            long r11 = (long) r9
            java.lang.Object r9 = com.google.android.gms.internal.measurement.zzla.zzf(r14, r11)
            java.util.List r9 = (java.util.List) r9
            com.google.android.gms.internal.measurement.zzkc r11 = r13.zza((int) r8)
            com.google.android.gms.internal.measurement.zzke.zza((int) r10, (java.util.List<?>) r9, (com.google.android.gms.internal.measurement.zzln) r15, (com.google.android.gms.internal.measurement.zzkc) r11)
            goto L_0x0b58
        L_0x0950:
            int[] r10 = r13.zzc
            r10 = r10[r8]
            r9 = r9 & r6
            long r11 = (long) r9
            java.lang.Object r9 = com.google.android.gms.internal.measurement.zzla.zzf(r14, r11)
            java.util.List r9 = (java.util.List) r9
            com.google.android.gms.internal.measurement.zzke.zza((int) r10, (java.util.List<java.lang.String>) r9, (com.google.android.gms.internal.measurement.zzln) r15)
            goto L_0x0b58
        L_0x0964:
            int[] r10 = r13.zzc
            r10 = r10[r8]
            r9 = r9 & r6
            long r11 = (long) r9
            java.lang.Object r9 = com.google.android.gms.internal.measurement.zzla.zzf(r14, r11)
            java.util.List r9 = (java.util.List) r9
            com.google.android.gms.internal.measurement.zzke.zzn(r10, r9, r15, r5)
            goto L_0x0b58
        L_0x0978:
            int[] r10 = r13.zzc
            r10 = r10[r8]
            r9 = r9 & r6
            long r11 = (long) r9
            java.lang.Object r9 = com.google.android.gms.internal.measurement.zzla.zzf(r14, r11)
            java.util.List r9 = (java.util.List) r9
            com.google.android.gms.internal.measurement.zzke.zzk(r10, r9, r15, r5)
            goto L_0x0b58
        L_0x098c:
            int[] r10 = r13.zzc
            r10 = r10[r8]
            r9 = r9 & r6
            long r11 = (long) r9
            java.lang.Object r9 = com.google.android.gms.internal.measurement.zzla.zzf(r14, r11)
            java.util.List r9 = (java.util.List) r9
            com.google.android.gms.internal.measurement.zzke.zzf(r10, r9, r15, r5)
            goto L_0x0b58
        L_0x09a0:
            int[] r10 = r13.zzc
            r10 = r10[r8]
            r9 = r9 & r6
            long r11 = (long) r9
            java.lang.Object r9 = com.google.android.gms.internal.measurement.zzla.zzf(r14, r11)
            java.util.List r9 = (java.util.List) r9
            com.google.android.gms.internal.measurement.zzke.zzh(r10, r9, r15, r5)
            goto L_0x0b58
        L_0x09b4:
            int[] r10 = r13.zzc
            r10 = r10[r8]
            r9 = r9 & r6
            long r11 = (long) r9
            java.lang.Object r9 = com.google.android.gms.internal.measurement.zzla.zzf(r14, r11)
            java.util.List r9 = (java.util.List) r9
            com.google.android.gms.internal.measurement.zzke.zzd(r10, r9, r15, r5)
            goto L_0x0b58
        L_0x09c8:
            int[] r10 = r13.zzc
            r10 = r10[r8]
            r9 = r9 & r6
            long r11 = (long) r9
            java.lang.Object r9 = com.google.android.gms.internal.measurement.zzla.zzf(r14, r11)
            java.util.List r9 = (java.util.List) r9
            com.google.android.gms.internal.measurement.zzke.zzc(r10, r9, r15, r5)
            goto L_0x0b58
        L_0x09dc:
            int[] r10 = r13.zzc
            r10 = r10[r8]
            r9 = r9 & r6
            long r11 = (long) r9
            java.lang.Object r9 = com.google.android.gms.internal.measurement.zzla.zzf(r14, r11)
            java.util.List r9 = (java.util.List) r9
            com.google.android.gms.internal.measurement.zzke.zzb((int) r10, (java.util.List<java.lang.Float>) r9, (com.google.android.gms.internal.measurement.zzln) r15, (boolean) r5)
            goto L_0x0b58
        L_0x09f0:
            int[] r10 = r13.zzc
            r10 = r10[r8]
            r9 = r9 & r6
            long r11 = (long) r9
            java.lang.Object r9 = com.google.android.gms.internal.measurement.zzla.zzf(r14, r11)
            java.util.List r9 = (java.util.List) r9
            com.google.android.gms.internal.measurement.zzke.zza((int) r10, (java.util.List<java.lang.Double>) r9, (com.google.android.gms.internal.measurement.zzln) r15, (boolean) r5)
            goto L_0x0b58
        L_0x0a04:
            boolean r11 = r13.zza(r14, (int) r8)
            if (r11 == 0) goto L_0x0b58
            r9 = r9 & r6
            long r11 = (long) r9
            java.lang.Object r9 = com.google.android.gms.internal.measurement.zzla.zzf(r14, r11)
            com.google.android.gms.internal.measurement.zzkc r11 = r13.zza((int) r8)
            r15.zzb((int) r10, (java.lang.Object) r9, (com.google.android.gms.internal.measurement.zzkc) r11)
            goto L_0x0b58
        L_0x0a1a:
            boolean r11 = r13.zza(r14, (int) r8)
            if (r11 == 0) goto L_0x0b58
            r9 = r9 & r6
            long r11 = (long) r9
            long r11 = com.google.android.gms.internal.measurement.zzla.zzb(r14, r11)
            r15.zze((int) r10, (long) r11)
            goto L_0x0b58
        L_0x0a2d:
            boolean r11 = r13.zza(r14, (int) r8)
            if (r11 == 0) goto L_0x0b58
            r9 = r9 & r6
            long r11 = (long) r9
            int r9 = com.google.android.gms.internal.measurement.zzla.zza((java.lang.Object) r14, (long) r11)
            r15.zzf(r10, r9)
            goto L_0x0b58
        L_0x0a40:
            boolean r11 = r13.zza(r14, (int) r8)
            if (r11 == 0) goto L_0x0b58
            r9 = r9 & r6
            long r11 = (long) r9
            long r11 = com.google.android.gms.internal.measurement.zzla.zzb(r14, r11)
            r15.zzb((int) r10, (long) r11)
            goto L_0x0b58
        L_0x0a53:
            boolean r11 = r13.zza(r14, (int) r8)
            if (r11 == 0) goto L_0x0b58
            r9 = r9 & r6
            long r11 = (long) r9
            int r9 = com.google.android.gms.internal.measurement.zzla.zza((java.lang.Object) r14, (long) r11)
            r15.zza((int) r10, (int) r9)
            goto L_0x0b58
        L_0x0a66:
            boolean r11 = r13.zza(r14, (int) r8)
            if (r11 == 0) goto L_0x0b58
            r9 = r9 & r6
            long r11 = (long) r9
            int r9 = com.google.android.gms.internal.measurement.zzla.zza((java.lang.Object) r14, (long) r11)
            r15.zzb((int) r10, (int) r9)
            goto L_0x0b58
        L_0x0a79:
            boolean r11 = r13.zza(r14, (int) r8)
            if (r11 == 0) goto L_0x0b58
            r9 = r9 & r6
            long r11 = (long) r9
            int r9 = com.google.android.gms.internal.measurement.zzla.zza((java.lang.Object) r14, (long) r11)
            r15.zze((int) r10, (int) r9)
            goto L_0x0b58
        L_0x0a8c:
            boolean r11 = r13.zza(r14, (int) r8)
            if (r11 == 0) goto L_0x0b58
            r9 = r9 & r6
            long r11 = (long) r9
            java.lang.Object r9 = com.google.android.gms.internal.measurement.zzla.zzf(r14, r11)
            com.google.android.gms.internal.measurement.zzgt r9 = (com.google.android.gms.internal.measurement.zzgt) r9
            r15.zza((int) r10, (com.google.android.gms.internal.measurement.zzgt) r9)
            goto L_0x0b58
        L_0x0aa0:
            boolean r11 = r13.zza(r14, (int) r8)
            if (r11 == 0) goto L_0x0b58
            r9 = r9 & r6
            long r11 = (long) r9
            java.lang.Object r9 = com.google.android.gms.internal.measurement.zzla.zzf(r14, r11)
            com.google.android.gms.internal.measurement.zzkc r11 = r13.zza((int) r8)
            r15.zza((int) r10, (java.lang.Object) r9, (com.google.android.gms.internal.measurement.zzkc) r11)
            goto L_0x0b58
        L_0x0ab6:
            boolean r11 = r13.zza(r14, (int) r8)
            if (r11 == 0) goto L_0x0b58
            r9 = r9 & r6
            long r11 = (long) r9
            java.lang.Object r9 = com.google.android.gms.internal.measurement.zzla.zzf(r14, r11)
            zza((int) r10, (java.lang.Object) r9, (com.google.android.gms.internal.measurement.zzln) r15)
            goto L_0x0b58
        L_0x0ac8:
            boolean r11 = r13.zza(r14, (int) r8)
            if (r11 == 0) goto L_0x0b58
            r9 = r9 & r6
            long r11 = (long) r9
            boolean r9 = com.google.android.gms.internal.measurement.zzla.zzc(r14, r11)
            r15.zza((int) r10, (boolean) r9)
            goto L_0x0b58
        L_0x0adb:
            boolean r11 = r13.zza(r14, (int) r8)
            if (r11 == 0) goto L_0x0b58
            r9 = r9 & r6
            long r11 = (long) r9
            int r9 = com.google.android.gms.internal.measurement.zzla.zza((java.lang.Object) r14, (long) r11)
            r15.zzd((int) r10, (int) r9)
            goto L_0x0b58
        L_0x0aed:
            boolean r11 = r13.zza(r14, (int) r8)
            if (r11 == 0) goto L_0x0b58
            r9 = r9 & r6
            long r11 = (long) r9
            long r11 = com.google.android.gms.internal.measurement.zzla.zzb(r14, r11)
            r15.zzd((int) r10, (long) r11)
            goto L_0x0b58
        L_0x0aff:
            boolean r11 = r13.zza(r14, (int) r8)
            if (r11 == 0) goto L_0x0b58
            r9 = r9 & r6
            long r11 = (long) r9
            int r9 = com.google.android.gms.internal.measurement.zzla.zza((java.lang.Object) r14, (long) r11)
            r15.zzc((int) r10, (int) r9)
            goto L_0x0b58
        L_0x0b11:
            boolean r11 = r13.zza(r14, (int) r8)
            if (r11 == 0) goto L_0x0b58
            r9 = r9 & r6
            long r11 = (long) r9
            long r11 = com.google.android.gms.internal.measurement.zzla.zzb(r14, r11)
            r15.zzc((int) r10, (long) r11)
            goto L_0x0b58
        L_0x0b23:
            boolean r11 = r13.zza(r14, (int) r8)
            if (r11 == 0) goto L_0x0b58
            r9 = r9 & r6
            long r11 = (long) r9
            long r11 = com.google.android.gms.internal.measurement.zzla.zzb(r14, r11)
            r15.zza((int) r10, (long) r11)
            goto L_0x0b58
        L_0x0b35:
            boolean r11 = r13.zza(r14, (int) r8)
            if (r11 == 0) goto L_0x0b58
            r9 = r9 & r6
            long r11 = (long) r9
            float r9 = com.google.android.gms.internal.measurement.zzla.zzd(r14, r11)
            r15.zza((int) r10, (float) r9)
            goto L_0x0b58
        L_0x0b47:
            boolean r11 = r13.zza(r14, (int) r8)
            if (r11 == 0) goto L_0x0b58
            r9 = r9 & r6
            long r11 = (long) r9
            double r11 = com.google.android.gms.internal.measurement.zzla.zze(r14, r11)
            r15.zza((int) r10, (double) r11)
        L_0x0b58:
            int r8 = r8 + 3
            goto L_0x05ee
        L_0x0b5c:
            if (r1 == 0) goto L_0x0b72
            com.google.android.gms.internal.measurement.zzhq<?> r2 = r13.zzr
            r2.zza(r15, r1)
            boolean r1 = r0.hasNext()
            if (r1 == 0) goto L_0x0b70
            java.lang.Object r1 = r0.next()
            java.util.Map$Entry r1 = (java.util.Map.Entry) r1
            goto L_0x0b5c
        L_0x0b70:
            r1 = r3
            goto L_0x0b5c
        L_0x0b72:
            com.google.android.gms.internal.measurement.zzku<?, ?> r0 = r13.zzq
            zza(r0, r14, (com.google.android.gms.internal.measurement.zzln) r15)
            return
        L_0x0b78:
            r13.zzb(r14, (com.google.android.gms.internal.measurement.zzln) r15)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.measurement.zzjn.zza(java.lang.Object, com.google.android.gms.internal.measurement.zzln):void");
    }

    /* JADX WARNING: Removed duplicated region for block: B:188:0x0559  */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x0035  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private final void zzb(T r18, com.google.android.gms.internal.measurement.zzln r19) throws java.io.IOException {
        /*
            r17 = this;
            r0 = r17
            r1 = r18
            r2 = r19
            boolean r3 = r0.zzh
            if (r3 == 0) goto L_0x0025
            com.google.android.gms.internal.measurement.zzhq<?> r3 = r0.zzr
            com.google.android.gms.internal.measurement.zzhr r3 = r3.zza((java.lang.Object) r1)
            com.google.android.gms.internal.measurement.zzkd<T, java.lang.Object> r5 = r3.zza
            boolean r5 = r5.isEmpty()
            if (r5 != 0) goto L_0x0025
            java.util.Iterator r3 = r3.zzd()
            java.lang.Object r5 = r3.next()
            java.util.Map$Entry r5 = (java.util.Map.Entry) r5
            goto L_0x0027
        L_0x0025:
            r3 = 0
            r5 = 0
        L_0x0027:
            int[] r6 = r0.zzc
            int r6 = r6.length
            sun.misc.Unsafe r7 = zzb
            r10 = 0
            r11 = 1048575(0xfffff, float:1.469367E-39)
            r12 = 0
        L_0x0033:
            if (r10 >= r6) goto L_0x0557
            int r13 = r0.zzd((int) r10)
            int[] r14 = r0.zzc
            r15 = r14[r10]
            r16 = 267386880(0xff00000, float:2.3665827E-29)
            r16 = r13 & r16
            int r4 = r16 >>> 20
            boolean r9 = r0.zzj
            if (r9 != 0) goto L_0x0068
            r9 = 17
            if (r4 > r9) goto L_0x0068
            int r9 = r10 + 2
            r9 = r14[r9]
            r14 = 1048575(0xfffff, float:1.469367E-39)
            r8 = r9 & r14
            if (r8 == r11) goto L_0x0062
            long r11 = (long) r8
            int r12 = r7.getInt(r1, r11)
            r11 = r8
        L_0x0062:
            int r8 = r9 >>> 20
            r9 = 1
            int r8 = r9 << r8
            goto L_0x0069
        L_0x0068:
            r8 = 0
        L_0x0069:
            if (r5 == 0) goto L_0x0087
            com.google.android.gms.internal.measurement.zzhq<?> r9 = r0.zzr
            int r9 = r9.zza((java.util.Map.Entry<?, ?>) r5)
            if (r9 > r15) goto L_0x0087
            com.google.android.gms.internal.measurement.zzhq<?> r9 = r0.zzr
            r9.zza(r2, r5)
            boolean r5 = r3.hasNext()
            if (r5 == 0) goto L_0x0085
            java.lang.Object r5 = r3.next()
            java.util.Map$Entry r5 = (java.util.Map.Entry) r5
            goto L_0x0069
        L_0x0085:
            r5 = 0
            goto L_0x0069
        L_0x0087:
            r9 = 1048575(0xfffff, float:1.469367E-39)
            r13 = r13 & r9
            long r13 = (long) r13
            switch(r4) {
                case 0: goto L_0x0547;
                case 1: goto L_0x053a;
                case 2: goto L_0x052e;
                case 3: goto L_0x0522;
                case 4: goto L_0x0516;
                case 5: goto L_0x050a;
                case 6: goto L_0x04fe;
                case 7: goto L_0x04f1;
                case 8: goto L_0x04e5;
                case 9: goto L_0x04d4;
                case 10: goto L_0x04c5;
                case 11: goto L_0x04b8;
                case 12: goto L_0x04ab;
                case 13: goto L_0x049e;
                case 14: goto L_0x0491;
                case 15: goto L_0x0484;
                case 16: goto L_0x0477;
                case 17: goto L_0x0465;
                case 18: goto L_0x0452;
                case 19: goto L_0x043f;
                case 20: goto L_0x042c;
                case 21: goto L_0x0419;
                case 22: goto L_0x0406;
                case 23: goto L_0x03f3;
                case 24: goto L_0x03e0;
                case 25: goto L_0x03cd;
                case 26: goto L_0x03bb;
                case 27: goto L_0x03a4;
                case 28: goto L_0x0392;
                case 29: goto L_0x037f;
                case 30: goto L_0x036c;
                case 31: goto L_0x0359;
                case 32: goto L_0x0346;
                case 33: goto L_0x0333;
                case 34: goto L_0x0320;
                case 35: goto L_0x030d;
                case 36: goto L_0x02fa;
                case 37: goto L_0x02e7;
                case 38: goto L_0x02d4;
                case 39: goto L_0x02c1;
                case 40: goto L_0x02ae;
                case 41: goto L_0x029b;
                case 42: goto L_0x0288;
                case 43: goto L_0x0275;
                case 44: goto L_0x0262;
                case 45: goto L_0x024f;
                case 46: goto L_0x023c;
                case 47: goto L_0x0229;
                case 48: goto L_0x0216;
                case 49: goto L_0x01ff;
                case 50: goto L_0x01f5;
                case 51: goto L_0x01e2;
                case 52: goto L_0x01cf;
                case 53: goto L_0x01bc;
                case 54: goto L_0x01a9;
                case 55: goto L_0x0196;
                case 56: goto L_0x0183;
                case 57: goto L_0x0170;
                case 58: goto L_0x015d;
                case 59: goto L_0x014a;
                case 60: goto L_0x0133;
                case 61: goto L_0x011e;
                case 62: goto L_0x010b;
                case 63: goto L_0x00f8;
                case 64: goto L_0x00e5;
                case 65: goto L_0x00d2;
                case 66: goto L_0x00bf;
                case 67: goto L_0x00ac;
                case 68: goto L_0x0094;
                default: goto L_0x0091;
            }
        L_0x0091:
            r4 = 0
            goto L_0x0553
        L_0x0094:
            boolean r4 = r0.zza(r1, (int) r15, (int) r10)
            if (r4 == 0) goto L_0x00a9
            java.lang.Object r4 = r7.getObject(r1, r13)
            com.google.android.gms.internal.measurement.zzkc r8 = r0.zza((int) r10)
            r2.zzb((int) r15, (java.lang.Object) r4, (com.google.android.gms.internal.measurement.zzkc) r8)
            r4 = 0
            goto L_0x0553
        L_0x00a9:
            r4 = 0
            goto L_0x0553
        L_0x00ac:
            boolean r4 = r0.zza(r1, (int) r15, (int) r10)
            if (r4 == 0) goto L_0x00bc
            long r13 = zze(r1, r13)
            r2.zze((int) r15, (long) r13)
            r4 = 0
            goto L_0x0553
        L_0x00bc:
            r4 = 0
            goto L_0x0553
        L_0x00bf:
            boolean r4 = r0.zza(r1, (int) r15, (int) r10)
            if (r4 == 0) goto L_0x00cf
            int r4 = zzd(r1, r13)
            r2.zzf(r15, r4)
            r4 = 0
            goto L_0x0553
        L_0x00cf:
            r4 = 0
            goto L_0x0553
        L_0x00d2:
            boolean r4 = r0.zza(r1, (int) r15, (int) r10)
            if (r4 == 0) goto L_0x00e2
            long r13 = zze(r1, r13)
            r2.zzb((int) r15, (long) r13)
            r4 = 0
            goto L_0x0553
        L_0x00e2:
            r4 = 0
            goto L_0x0553
        L_0x00e5:
            boolean r4 = r0.zza(r1, (int) r15, (int) r10)
            if (r4 == 0) goto L_0x00f5
            int r4 = zzd(r1, r13)
            r2.zza((int) r15, (int) r4)
            r4 = 0
            goto L_0x0553
        L_0x00f5:
            r4 = 0
            goto L_0x0553
        L_0x00f8:
            boolean r4 = r0.zza(r1, (int) r15, (int) r10)
            if (r4 == 0) goto L_0x0108
            int r4 = zzd(r1, r13)
            r2.zzb((int) r15, (int) r4)
            r4 = 0
            goto L_0x0553
        L_0x0108:
            r4 = 0
            goto L_0x0553
        L_0x010b:
            boolean r4 = r0.zza(r1, (int) r15, (int) r10)
            if (r4 == 0) goto L_0x011b
            int r4 = zzd(r1, r13)
            r2.zze((int) r15, (int) r4)
            r4 = 0
            goto L_0x0553
        L_0x011b:
            r4 = 0
            goto L_0x0553
        L_0x011e:
            boolean r4 = r0.zza(r1, (int) r15, (int) r10)
            if (r4 == 0) goto L_0x0130
            java.lang.Object r4 = r7.getObject(r1, r13)
            com.google.android.gms.internal.measurement.zzgt r4 = (com.google.android.gms.internal.measurement.zzgt) r4
            r2.zza((int) r15, (com.google.android.gms.internal.measurement.zzgt) r4)
            r4 = 0
            goto L_0x0553
        L_0x0130:
            r4 = 0
            goto L_0x0553
        L_0x0133:
            boolean r4 = r0.zza(r1, (int) r15, (int) r10)
            if (r4 == 0) goto L_0x0147
            java.lang.Object r4 = r7.getObject(r1, r13)
            com.google.android.gms.internal.measurement.zzkc r8 = r0.zza((int) r10)
            r2.zza((int) r15, (java.lang.Object) r4, (com.google.android.gms.internal.measurement.zzkc) r8)
            r4 = 0
            goto L_0x0553
        L_0x0147:
            r4 = 0
            goto L_0x0553
        L_0x014a:
            boolean r4 = r0.zza(r1, (int) r15, (int) r10)
            if (r4 == 0) goto L_0x015a
            java.lang.Object r4 = r7.getObject(r1, r13)
            zza((int) r15, (java.lang.Object) r4, (com.google.android.gms.internal.measurement.zzln) r2)
            r4 = 0
            goto L_0x0553
        L_0x015a:
            r4 = 0
            goto L_0x0553
        L_0x015d:
            boolean r4 = r0.zza(r1, (int) r15, (int) r10)
            if (r4 == 0) goto L_0x016d
            boolean r4 = zzf(r1, r13)
            r2.zza((int) r15, (boolean) r4)
            r4 = 0
            goto L_0x0553
        L_0x016d:
            r4 = 0
            goto L_0x0553
        L_0x0170:
            boolean r4 = r0.zza(r1, (int) r15, (int) r10)
            if (r4 == 0) goto L_0x0180
            int r4 = zzd(r1, r13)
            r2.zzd((int) r15, (int) r4)
            r4 = 0
            goto L_0x0553
        L_0x0180:
            r4 = 0
            goto L_0x0553
        L_0x0183:
            boolean r4 = r0.zza(r1, (int) r15, (int) r10)
            if (r4 == 0) goto L_0x0193
            long r13 = zze(r1, r13)
            r2.zzd((int) r15, (long) r13)
            r4 = 0
            goto L_0x0553
        L_0x0193:
            r4 = 0
            goto L_0x0553
        L_0x0196:
            boolean r4 = r0.zza(r1, (int) r15, (int) r10)
            if (r4 == 0) goto L_0x01a6
            int r4 = zzd(r1, r13)
            r2.zzc((int) r15, (int) r4)
            r4 = 0
            goto L_0x0553
        L_0x01a6:
            r4 = 0
            goto L_0x0553
        L_0x01a9:
            boolean r4 = r0.zza(r1, (int) r15, (int) r10)
            if (r4 == 0) goto L_0x01b9
            long r13 = zze(r1, r13)
            r2.zzc((int) r15, (long) r13)
            r4 = 0
            goto L_0x0553
        L_0x01b9:
            r4 = 0
            goto L_0x0553
        L_0x01bc:
            boolean r4 = r0.zza(r1, (int) r15, (int) r10)
            if (r4 == 0) goto L_0x01cc
            long r13 = zze(r1, r13)
            r2.zza((int) r15, (long) r13)
            r4 = 0
            goto L_0x0553
        L_0x01cc:
            r4 = 0
            goto L_0x0553
        L_0x01cf:
            boolean r4 = r0.zza(r1, (int) r15, (int) r10)
            if (r4 == 0) goto L_0x01df
            float r4 = zzc(r1, r13)
            r2.zza((int) r15, (float) r4)
            r4 = 0
            goto L_0x0553
        L_0x01df:
            r4 = 0
            goto L_0x0553
        L_0x01e2:
            boolean r4 = r0.zza(r1, (int) r15, (int) r10)
            if (r4 == 0) goto L_0x01f2
            double r13 = zzb(r1, (long) r13)
            r2.zza((int) r15, (double) r13)
            r4 = 0
            goto L_0x0553
        L_0x01f2:
            r4 = 0
            goto L_0x0553
        L_0x01f5:
            java.lang.Object r4 = r7.getObject(r1, r13)
            r0.zza((com.google.android.gms.internal.measurement.zzln) r2, (int) r15, (java.lang.Object) r4, (int) r10)
            r4 = 0
            goto L_0x0553
        L_0x01ff:
            int[] r4 = r0.zzc
            r4 = r4[r10]
            java.lang.Object r8 = r7.getObject(r1, r13)
            java.util.List r8 = (java.util.List) r8
            com.google.android.gms.internal.measurement.zzkc r13 = r0.zza((int) r10)
            com.google.android.gms.internal.measurement.zzke.zzb((int) r4, (java.util.List<?>) r8, (com.google.android.gms.internal.measurement.zzln) r2, (com.google.android.gms.internal.measurement.zzkc) r13)
            r4 = 0
            goto L_0x0553
        L_0x0216:
            int[] r4 = r0.zzc
            r4 = r4[r10]
            java.lang.Object r8 = r7.getObject(r1, r13)
            java.util.List r8 = (java.util.List) r8
            r13 = 1
            com.google.android.gms.internal.measurement.zzke.zze(r4, r8, r2, r13)
            r4 = 0
            goto L_0x0553
        L_0x0229:
            int[] r4 = r0.zzc
            r4 = r4[r10]
            java.lang.Object r8 = r7.getObject(r1, r13)
            java.util.List r8 = (java.util.List) r8
            r13 = 1
            com.google.android.gms.internal.measurement.zzke.zzj(r4, r8, r2, r13)
            r4 = 0
            goto L_0x0553
        L_0x023c:
            int[] r4 = r0.zzc
            r4 = r4[r10]
            java.lang.Object r8 = r7.getObject(r1, r13)
            java.util.List r8 = (java.util.List) r8
            r13 = 1
            com.google.android.gms.internal.measurement.zzke.zzg(r4, r8, r2, r13)
            r4 = 0
            goto L_0x0553
        L_0x024f:
            int[] r4 = r0.zzc
            r4 = r4[r10]
            java.lang.Object r8 = r7.getObject(r1, r13)
            java.util.List r8 = (java.util.List) r8
            r13 = 1
            com.google.android.gms.internal.measurement.zzke.zzl(r4, r8, r2, r13)
            r4 = 0
            goto L_0x0553
        L_0x0262:
            int[] r4 = r0.zzc
            r4 = r4[r10]
            java.lang.Object r8 = r7.getObject(r1, r13)
            java.util.List r8 = (java.util.List) r8
            r13 = 1
            com.google.android.gms.internal.measurement.zzke.zzm(r4, r8, r2, r13)
            r4 = 0
            goto L_0x0553
        L_0x0275:
            int[] r4 = r0.zzc
            r4 = r4[r10]
            java.lang.Object r8 = r7.getObject(r1, r13)
            java.util.List r8 = (java.util.List) r8
            r13 = 1
            com.google.android.gms.internal.measurement.zzke.zzi(r4, r8, r2, r13)
            r4 = 0
            goto L_0x0553
        L_0x0288:
            int[] r4 = r0.zzc
            r4 = r4[r10]
            java.lang.Object r8 = r7.getObject(r1, r13)
            java.util.List r8 = (java.util.List) r8
            r13 = 1
            com.google.android.gms.internal.measurement.zzke.zzn(r4, r8, r2, r13)
            r4 = 0
            goto L_0x0553
        L_0x029b:
            int[] r4 = r0.zzc
            r4 = r4[r10]
            java.lang.Object r8 = r7.getObject(r1, r13)
            java.util.List r8 = (java.util.List) r8
            r13 = 1
            com.google.android.gms.internal.measurement.zzke.zzk(r4, r8, r2, r13)
            r4 = 0
            goto L_0x0553
        L_0x02ae:
            int[] r4 = r0.zzc
            r4 = r4[r10]
            java.lang.Object r8 = r7.getObject(r1, r13)
            java.util.List r8 = (java.util.List) r8
            r13 = 1
            com.google.android.gms.internal.measurement.zzke.zzf(r4, r8, r2, r13)
            r4 = 0
            goto L_0x0553
        L_0x02c1:
            int[] r4 = r0.zzc
            r4 = r4[r10]
            java.lang.Object r8 = r7.getObject(r1, r13)
            java.util.List r8 = (java.util.List) r8
            r13 = 1
            com.google.android.gms.internal.measurement.zzke.zzh(r4, r8, r2, r13)
            r4 = 0
            goto L_0x0553
        L_0x02d4:
            int[] r4 = r0.zzc
            r4 = r4[r10]
            java.lang.Object r8 = r7.getObject(r1, r13)
            java.util.List r8 = (java.util.List) r8
            r13 = 1
            com.google.android.gms.internal.measurement.zzke.zzd(r4, r8, r2, r13)
            r4 = 0
            goto L_0x0553
        L_0x02e7:
            int[] r4 = r0.zzc
            r4 = r4[r10]
            java.lang.Object r8 = r7.getObject(r1, r13)
            java.util.List r8 = (java.util.List) r8
            r13 = 1
            com.google.android.gms.internal.measurement.zzke.zzc(r4, r8, r2, r13)
            r4 = 0
            goto L_0x0553
        L_0x02fa:
            int[] r4 = r0.zzc
            r4 = r4[r10]
            java.lang.Object r8 = r7.getObject(r1, r13)
            java.util.List r8 = (java.util.List) r8
            r13 = 1
            com.google.android.gms.internal.measurement.zzke.zzb((int) r4, (java.util.List<java.lang.Float>) r8, (com.google.android.gms.internal.measurement.zzln) r2, (boolean) r13)
            r4 = 0
            goto L_0x0553
        L_0x030d:
            int[] r4 = r0.zzc
            r4 = r4[r10]
            java.lang.Object r8 = r7.getObject(r1, r13)
            java.util.List r8 = (java.util.List) r8
            r13 = 1
            com.google.android.gms.internal.measurement.zzke.zza((int) r4, (java.util.List<java.lang.Double>) r8, (com.google.android.gms.internal.measurement.zzln) r2, (boolean) r13)
            r4 = 0
            goto L_0x0553
        L_0x0320:
            int[] r4 = r0.zzc
            r4 = r4[r10]
            java.lang.Object r8 = r7.getObject(r1, r13)
            java.util.List r8 = (java.util.List) r8
            r13 = 0
            com.google.android.gms.internal.measurement.zzke.zze(r4, r8, r2, r13)
            r4 = 0
            goto L_0x0553
        L_0x0333:
            int[] r4 = r0.zzc
            r4 = r4[r10]
            java.lang.Object r8 = r7.getObject(r1, r13)
            java.util.List r8 = (java.util.List) r8
            r13 = 0
            com.google.android.gms.internal.measurement.zzke.zzj(r4, r8, r2, r13)
            r4 = 0
            goto L_0x0553
        L_0x0346:
            int[] r4 = r0.zzc
            r4 = r4[r10]
            java.lang.Object r8 = r7.getObject(r1, r13)
            java.util.List r8 = (java.util.List) r8
            r13 = 0
            com.google.android.gms.internal.measurement.zzke.zzg(r4, r8, r2, r13)
            r4 = 0
            goto L_0x0553
        L_0x0359:
            int[] r4 = r0.zzc
            r4 = r4[r10]
            java.lang.Object r8 = r7.getObject(r1, r13)
            java.util.List r8 = (java.util.List) r8
            r13 = 0
            com.google.android.gms.internal.measurement.zzke.zzl(r4, r8, r2, r13)
            r4 = 0
            goto L_0x0553
        L_0x036c:
            int[] r4 = r0.zzc
            r4 = r4[r10]
            java.lang.Object r8 = r7.getObject(r1, r13)
            java.util.List r8 = (java.util.List) r8
            r13 = 0
            com.google.android.gms.internal.measurement.zzke.zzm(r4, r8, r2, r13)
            r4 = 0
            goto L_0x0553
        L_0x037f:
            int[] r4 = r0.zzc
            r4 = r4[r10]
            java.lang.Object r8 = r7.getObject(r1, r13)
            java.util.List r8 = (java.util.List) r8
            r13 = 0
            com.google.android.gms.internal.measurement.zzke.zzi(r4, r8, r2, r13)
            r4 = 0
            goto L_0x0553
        L_0x0392:
            int[] r4 = r0.zzc
            r4 = r4[r10]
            java.lang.Object r8 = r7.getObject(r1, r13)
            java.util.List r8 = (java.util.List) r8
            com.google.android.gms.internal.measurement.zzke.zzb((int) r4, (java.util.List<com.google.android.gms.internal.measurement.zzgt>) r8, (com.google.android.gms.internal.measurement.zzln) r2)
            r4 = 0
            goto L_0x0553
        L_0x03a4:
            int[] r4 = r0.zzc
            r4 = r4[r10]
            java.lang.Object r8 = r7.getObject(r1, r13)
            java.util.List r8 = (java.util.List) r8
            com.google.android.gms.internal.measurement.zzkc r13 = r0.zza((int) r10)
            com.google.android.gms.internal.measurement.zzke.zza((int) r4, (java.util.List<?>) r8, (com.google.android.gms.internal.measurement.zzln) r2, (com.google.android.gms.internal.measurement.zzkc) r13)
            r4 = 0
            goto L_0x0553
        L_0x03bb:
            int[] r4 = r0.zzc
            r4 = r4[r10]
            java.lang.Object r8 = r7.getObject(r1, r13)
            java.util.List r8 = (java.util.List) r8
            com.google.android.gms.internal.measurement.zzke.zza((int) r4, (java.util.List<java.lang.String>) r8, (com.google.android.gms.internal.measurement.zzln) r2)
            r4 = 0
            goto L_0x0553
        L_0x03cd:
            int[] r4 = r0.zzc
            r4 = r4[r10]
            java.lang.Object r8 = r7.getObject(r1, r13)
            java.util.List r8 = (java.util.List) r8
            r13 = 0
            com.google.android.gms.internal.measurement.zzke.zzn(r4, r8, r2, r13)
            r4 = 0
            goto L_0x0553
        L_0x03e0:
            int[] r4 = r0.zzc
            r4 = r4[r10]
            java.lang.Object r8 = r7.getObject(r1, r13)
            java.util.List r8 = (java.util.List) r8
            r13 = 0
            com.google.android.gms.internal.measurement.zzke.zzk(r4, r8, r2, r13)
            r4 = 0
            goto L_0x0553
        L_0x03f3:
            int[] r4 = r0.zzc
            r4 = r4[r10]
            java.lang.Object r8 = r7.getObject(r1, r13)
            java.util.List r8 = (java.util.List) r8
            r13 = 0
            com.google.android.gms.internal.measurement.zzke.zzf(r4, r8, r2, r13)
            r4 = 0
            goto L_0x0553
        L_0x0406:
            int[] r4 = r0.zzc
            r4 = r4[r10]
            java.lang.Object r8 = r7.getObject(r1, r13)
            java.util.List r8 = (java.util.List) r8
            r13 = 0
            com.google.android.gms.internal.measurement.zzke.zzh(r4, r8, r2, r13)
            r4 = 0
            goto L_0x0553
        L_0x0419:
            int[] r4 = r0.zzc
            r4 = r4[r10]
            java.lang.Object r8 = r7.getObject(r1, r13)
            java.util.List r8 = (java.util.List) r8
            r13 = 0
            com.google.android.gms.internal.measurement.zzke.zzd(r4, r8, r2, r13)
            r4 = 0
            goto L_0x0553
        L_0x042c:
            int[] r4 = r0.zzc
            r4 = r4[r10]
            java.lang.Object r8 = r7.getObject(r1, r13)
            java.util.List r8 = (java.util.List) r8
            r13 = 0
            com.google.android.gms.internal.measurement.zzke.zzc(r4, r8, r2, r13)
            r4 = 0
            goto L_0x0553
        L_0x043f:
            int[] r4 = r0.zzc
            r4 = r4[r10]
            java.lang.Object r8 = r7.getObject(r1, r13)
            java.util.List r8 = (java.util.List) r8
            r13 = 0
            com.google.android.gms.internal.measurement.zzke.zzb((int) r4, (java.util.List<java.lang.Float>) r8, (com.google.android.gms.internal.measurement.zzln) r2, (boolean) r13)
            r4 = 0
            goto L_0x0553
        L_0x0452:
            int[] r4 = r0.zzc
            r4 = r4[r10]
            java.lang.Object r8 = r7.getObject(r1, r13)
            java.util.List r8 = (java.util.List) r8
            r13 = 0
            com.google.android.gms.internal.measurement.zzke.zza((int) r4, (java.util.List<java.lang.Double>) r8, (com.google.android.gms.internal.measurement.zzln) r2, (boolean) r13)
            r4 = r13
            goto L_0x0553
        L_0x0465:
            r4 = 0
            r8 = r8 & r12
            if (r8 == 0) goto L_0x0553
            java.lang.Object r8 = r7.getObject(r1, r13)
            com.google.android.gms.internal.measurement.zzkc r13 = r0.zza((int) r10)
            r2.zzb((int) r15, (java.lang.Object) r8, (com.google.android.gms.internal.measurement.zzkc) r13)
            goto L_0x0553
        L_0x0477:
            r4 = 0
            r8 = r8 & r12
            if (r8 == 0) goto L_0x0553
            long r13 = r7.getLong(r1, r13)
            r2.zze((int) r15, (long) r13)
            goto L_0x0553
        L_0x0484:
            r4 = 0
            r8 = r8 & r12
            if (r8 == 0) goto L_0x0553
            int r8 = r7.getInt(r1, r13)
            r2.zzf(r15, r8)
            goto L_0x0553
        L_0x0491:
            r4 = 0
            r8 = r8 & r12
            if (r8 == 0) goto L_0x0553
            long r13 = r7.getLong(r1, r13)
            r2.zzb((int) r15, (long) r13)
            goto L_0x0553
        L_0x049e:
            r4 = 0
            r8 = r8 & r12
            if (r8 == 0) goto L_0x0553
            int r8 = r7.getInt(r1, r13)
            r2.zza((int) r15, (int) r8)
            goto L_0x0553
        L_0x04ab:
            r4 = 0
            r8 = r8 & r12
            if (r8 == 0) goto L_0x0553
            int r8 = r7.getInt(r1, r13)
            r2.zzb((int) r15, (int) r8)
            goto L_0x0553
        L_0x04b8:
            r4 = 0
            r8 = r8 & r12
            if (r8 == 0) goto L_0x0553
            int r8 = r7.getInt(r1, r13)
            r2.zze((int) r15, (int) r8)
            goto L_0x0553
        L_0x04c5:
            r4 = 0
            r8 = r8 & r12
            if (r8 == 0) goto L_0x0553
            java.lang.Object r8 = r7.getObject(r1, r13)
            com.google.android.gms.internal.measurement.zzgt r8 = (com.google.android.gms.internal.measurement.zzgt) r8
            r2.zza((int) r15, (com.google.android.gms.internal.measurement.zzgt) r8)
            goto L_0x0553
        L_0x04d4:
            r4 = 0
            r8 = r8 & r12
            if (r8 == 0) goto L_0x0553
            java.lang.Object r8 = r7.getObject(r1, r13)
            com.google.android.gms.internal.measurement.zzkc r13 = r0.zza((int) r10)
            r2.zza((int) r15, (java.lang.Object) r8, (com.google.android.gms.internal.measurement.zzkc) r13)
            goto L_0x0553
        L_0x04e5:
            r4 = 0
            r8 = r8 & r12
            if (r8 == 0) goto L_0x0553
            java.lang.Object r8 = r7.getObject(r1, r13)
            zza((int) r15, (java.lang.Object) r8, (com.google.android.gms.internal.measurement.zzln) r2)
            goto L_0x0553
        L_0x04f1:
            r4 = 0
            r8 = r8 & r12
            if (r8 == 0) goto L_0x0553
            boolean r8 = com.google.android.gms.internal.measurement.zzla.zzc(r1, r13)
            r2.zza((int) r15, (boolean) r8)
            goto L_0x0553
        L_0x04fe:
            r4 = 0
            r8 = r8 & r12
            if (r8 == 0) goto L_0x0553
            int r8 = r7.getInt(r1, r13)
            r2.zzd((int) r15, (int) r8)
            goto L_0x0553
        L_0x050a:
            r4 = 0
            r8 = r8 & r12
            if (r8 == 0) goto L_0x0553
            long r13 = r7.getLong(r1, r13)
            r2.zzd((int) r15, (long) r13)
            goto L_0x0553
        L_0x0516:
            r4 = 0
            r8 = r8 & r12
            if (r8 == 0) goto L_0x0553
            int r8 = r7.getInt(r1, r13)
            r2.zzc((int) r15, (int) r8)
            goto L_0x0553
        L_0x0522:
            r4 = 0
            r8 = r8 & r12
            if (r8 == 0) goto L_0x0553
            long r13 = r7.getLong(r1, r13)
            r2.zzc((int) r15, (long) r13)
            goto L_0x0553
        L_0x052e:
            r4 = 0
            r8 = r8 & r12
            if (r8 == 0) goto L_0x0553
            long r13 = r7.getLong(r1, r13)
            r2.zza((int) r15, (long) r13)
            goto L_0x0553
        L_0x053a:
            r4 = 0
            r8 = r8 & r12
            if (r8 == 0) goto L_0x0553
            float r8 = com.google.android.gms.internal.measurement.zzla.zzd(r1, r13)
            r2.zza((int) r15, (float) r8)
            goto L_0x0553
        L_0x0547:
            r4 = 0
            r8 = r8 & r12
            if (r8 == 0) goto L_0x0553
            double r13 = com.google.android.gms.internal.measurement.zzla.zze(r1, r13)
            r2.zza((int) r15, (double) r13)
        L_0x0553:
            int r10 = r10 + 3
            goto L_0x0033
        L_0x0557:
            if (r5 == 0) goto L_0x056e
            com.google.android.gms.internal.measurement.zzhq<?> r4 = r0.zzr
            r4.zza(r2, r5)
            boolean r4 = r3.hasNext()
            if (r4 == 0) goto L_0x056c
            java.lang.Object r4 = r3.next()
            java.util.Map$Entry r4 = (java.util.Map.Entry) r4
            r5 = r4
            goto L_0x0557
        L_0x056c:
            r5 = 0
            goto L_0x0557
        L_0x056e:
            com.google.android.gms.internal.measurement.zzku<?, ?> r3 = r0.zzq
            zza(r3, r1, (com.google.android.gms.internal.measurement.zzln) r2)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.measurement.zzjn.zzb(java.lang.Object, com.google.android.gms.internal.measurement.zzln):void");
    }

    private final <K, V> void zza(zzln zzln, int i, Object obj, int i2) throws IOException {
        if (obj != null) {
            zzln.zza(i, this.zzs.zzb(zzb(i2)), this.zzs.zzc(obj));
        }
    }

    private static <UT, UB> void zza(zzku<UT, UB> zzku, T t, zzln zzln) throws IOException {
        zzku.zza(zzku.zzb(t), zzln);
    }

    /*  JADX ERROR: StackOverflow in pass: MarkFinallyVisitor
        jadx.core.utils.exceptions.JadxOverflowException: 
        	at jadx.core.utils.ErrorsCounter.addError(ErrorsCounter.java:47)
        	at jadx.core.utils.ErrorsCounter.methodError(ErrorsCounter.java:81)
        */
    public final void zza(T r13, com.google.android.gms.internal.measurement.zzjz r14, com.google.android.gms.internal.measurement.zzho r15) throws java.io.IOException {
        /*
            r12 = this;
            if (r15 == 0) goto L_0x0623
            com.google.android.gms.internal.measurement.zzku<?, ?> r7 = r12.zzq
            com.google.android.gms.internal.measurement.zzhq<?> r8 = r12.zzr
            r9 = 0
            r0 = r9
            r10 = r0
        L_0x000a:
            int r1 = r14.zza()     // Catch:{ all -> 0x060b }
            int r2 = r12.zzg(r1)     // Catch:{ all -> 0x060b }
            if (r2 >= 0) goto L_0x007b
            r2 = 2147483647(0x7fffffff, float:NaN)
            if (r1 != r2) goto L_0x0030
            int r14 = r12.zzm
        L_0x001b:
            int r15 = r12.zzn
            if (r14 >= r15) goto L_0x002a
            int[] r15 = r12.zzl
            r15 = r15[r14]
            java.lang.Object r10 = r12.zza((java.lang.Object) r13, (int) r15, r10, r7)
            int r14 = r14 + 1
            goto L_0x001b
        L_0x002a:
            if (r10 == 0) goto L_0x002f
            r7.zzb((java.lang.Object) r13, r10)
        L_0x002f:
            return
        L_0x0030:
            boolean r2 = r12.zzh     // Catch:{ all -> 0x060b }
            if (r2 != 0) goto L_0x0036
            r2 = r9
            goto L_0x003d
        L_0x0036:
            com.google.android.gms.internal.measurement.zzjj r2 = r12.zzg     // Catch:{ all -> 0x060b }
            java.lang.Object r1 = r8.zza(r15, r2, r1)     // Catch:{ all -> 0x060b }
            r2 = r1
        L_0x003d:
            if (r2 == 0) goto L_0x0055
            if (r0 != 0) goto L_0x0047
            com.google.android.gms.internal.measurement.zzhr r0 = r8.zzb(r13)     // Catch:{ all -> 0x060b }
            r11 = r0
            goto L_0x0048
        L_0x0047:
            r11 = r0
        L_0x0048:
            r0 = r8
            r1 = r14
            r3 = r15
            r4 = r11
            r5 = r10
            r6 = r7
            java.lang.Object r10 = r0.zza(r1, r2, r3, r4, r5, r6)     // Catch:{ all -> 0x060b }
            r0 = r11
            goto L_0x000a
        L_0x0055:
            r7.zza((com.google.android.gms.internal.measurement.zzjz) r14)     // Catch:{ all -> 0x060b }
            if (r10 != 0) goto L_0x005e
            java.lang.Object r10 = r7.zzc(r13)     // Catch:{ all -> 0x060b }
        L_0x005e:
            boolean r1 = r7.zza(r10, (com.google.android.gms.internal.measurement.zzjz) r14)     // Catch:{ all -> 0x060b }
            if (r1 != 0) goto L_0x000a
            int r14 = r12.zzm
        L_0x0066:
            int r15 = r12.zzn
            if (r14 >= r15) goto L_0x0075
            int[] r15 = r12.zzl
            r15 = r15[r14]
            java.lang.Object r10 = r12.zza((java.lang.Object) r13, (int) r15, r10, r7)
            int r14 = r14 + 1
            goto L_0x0066
        L_0x0075:
            if (r10 == 0) goto L_0x007a
            r7.zzb((java.lang.Object) r13, r10)
        L_0x007a:
            return
        L_0x007b:
            int r3 = r12.zzd((int) r2)     // Catch:{ all -> 0x060b }
            r4 = 267386880(0xff00000, float:2.3665827E-29)
            r4 = r4 & r3
            int r4 = r4 >>> 20
            r5 = 1048575(0xfffff, float:1.469367E-39)
            switch(r4) {
                case 0: goto L_0x05b2;
                case 1: goto L_0x05a2;
                case 2: goto L_0x0592;
                case 3: goto L_0x0582;
                case 4: goto L_0x0572;
                case 5: goto L_0x0562;
                case 6: goto L_0x0552;
                case 7: goto L_0x0542;
                case 8: goto L_0x053a;
                case 9: goto L_0x0504;
                case 10: goto L_0x04f4;
                case 11: goto L_0x04e4;
                case 12: goto L_0x04c0;
                case 13: goto L_0x04b0;
                case 14: goto L_0x04a0;
                case 15: goto L_0x0490;
                case 16: goto L_0x0480;
                case 17: goto L_0x044a;
                case 18: goto L_0x043c;
                case 19: goto L_0x042e;
                case 20: goto L_0x0420;
                case 21: goto L_0x0412;
                case 22: goto L_0x0404;
                case 23: goto L_0x03f6;
                case 24: goto L_0x03e8;
                case 25: goto L_0x03da;
                case 26: goto L_0x03b7;
                case 27: goto L_0x03a1;
                case 28: goto L_0x0393;
                case 29: goto L_0x0385;
                case 30: goto L_0x036f;
                case 31: goto L_0x0361;
                case 32: goto L_0x0353;
                case 33: goto L_0x0345;
                case 34: goto L_0x0337;
                case 35: goto L_0x0329;
                case 36: goto L_0x031b;
                case 37: goto L_0x030d;
                case 38: goto L_0x02ff;
                case 39: goto L_0x02f1;
                case 40: goto L_0x02e3;
                case 41: goto L_0x02d5;
                case 42: goto L_0x02c7;
                case 43: goto L_0x02b9;
                case 44: goto L_0x02a3;
                case 45: goto L_0x0295;
                case 46: goto L_0x0287;
                case 47: goto L_0x0279;
                case 48: goto L_0x026b;
                case 49: goto L_0x0256;
                case 50: goto L_0x0212;
                case 51: goto L_0x01ff;
                case 52: goto L_0x01ec;
                case 53: goto L_0x01d9;
                case 54: goto L_0x01c6;
                case 55: goto L_0x01b3;
                case 56: goto L_0x01a0;
                case 57: goto L_0x018d;
                case 58: goto L_0x017a;
                case 59: goto L_0x0172;
                case 60: goto L_0x013c;
                case 61: goto L_0x012d;
                case 62: goto L_0x011a;
                case 63: goto L_0x00f3;
                case 64: goto L_0x00e0;
                case 65: goto L_0x00cd;
                case 66: goto L_0x00ba;
                case 67: goto L_0x00a7;
                case 68: goto L_0x0093;
                default: goto L_0x008b;
            }
        L_0x008b:
            if (r10 != 0) goto L_0x05c2
            java.lang.Object r10 = r7.zza()     // Catch:{ zzim -> 0x05e1 }
            goto L_0x05c2
        L_0x0093:
            r3 = r3 & r5
            long r3 = (long) r3     // Catch:{ zzim -> 0x05e1 }
            com.google.android.gms.internal.measurement.zzkc r5 = r12.zza((int) r2)     // Catch:{ zzim -> 0x05e1 }
            java.lang.Object r5 = r14.zzb(r5, r15)     // Catch:{ zzim -> 0x05e1 }
            com.google.android.gms.internal.measurement.zzla.zza((java.lang.Object) r13, (long) r3, (java.lang.Object) r5)     // Catch:{ zzim -> 0x05e1 }
            r12.zzb(r13, (int) r1, (int) r2)     // Catch:{ zzim -> 0x05e1 }
            goto L_0x000a
        L_0x00a7:
            r3 = r3 & r5
            long r3 = (long) r3     // Catch:{ zzim -> 0x05e1 }
            long r5 = r14.zzt()     // Catch:{ zzim -> 0x05e1 }
            java.lang.Long r5 = java.lang.Long.valueOf(r5)     // Catch:{ zzim -> 0x05e1 }
            com.google.android.gms.internal.measurement.zzla.zza((java.lang.Object) r13, (long) r3, (java.lang.Object) r5)     // Catch:{ zzim -> 0x05e1 }
            r12.zzb(r13, (int) r1, (int) r2)     // Catch:{ zzim -> 0x05e1 }
            goto L_0x000a
        L_0x00ba:
            r3 = r3 & r5
            long r3 = (long) r3     // Catch:{ zzim -> 0x05e1 }
            int r5 = r14.zzs()     // Catch:{ zzim -> 0x05e1 }
            java.lang.Integer r5 = java.lang.Integer.valueOf(r5)     // Catch:{ zzim -> 0x05e1 }
            com.google.android.gms.internal.measurement.zzla.zza((java.lang.Object) r13, (long) r3, (java.lang.Object) r5)     // Catch:{ zzim -> 0x05e1 }
            r12.zzb(r13, (int) r1, (int) r2)     // Catch:{ zzim -> 0x05e1 }
            goto L_0x000a
        L_0x00cd:
            r3 = r3 & r5
            long r3 = (long) r3     // Catch:{ zzim -> 0x05e1 }
            long r5 = r14.zzr()     // Catch:{ zzim -> 0x05e1 }
            java.lang.Long r5 = java.lang.Long.valueOf(r5)     // Catch:{ zzim -> 0x05e1 }
            com.google.android.gms.internal.measurement.zzla.zza((java.lang.Object) r13, (long) r3, (java.lang.Object) r5)     // Catch:{ zzim -> 0x05e1 }
            r12.zzb(r13, (int) r1, (int) r2)     // Catch:{ zzim -> 0x05e1 }
            goto L_0x000a
        L_0x00e0:
            r3 = r3 & r5
            long r3 = (long) r3     // Catch:{ zzim -> 0x05e1 }
            int r5 = r14.zzq()     // Catch:{ zzim -> 0x05e1 }
            java.lang.Integer r5 = java.lang.Integer.valueOf(r5)     // Catch:{ zzim -> 0x05e1 }
            com.google.android.gms.internal.measurement.zzla.zza((java.lang.Object) r13, (long) r3, (java.lang.Object) r5)     // Catch:{ zzim -> 0x05e1 }
            r12.zzb(r13, (int) r1, (int) r2)     // Catch:{ zzim -> 0x05e1 }
            goto L_0x000a
        L_0x00f3:
            int r4 = r14.zzp()     // Catch:{ zzim -> 0x05e1 }
            com.google.android.gms.internal.measurement.zzif r6 = r12.zzc((int) r2)     // Catch:{ zzim -> 0x05e1 }
            if (r6 == 0) goto L_0x010b
            boolean r6 = r6.zza(r4)     // Catch:{ zzim -> 0x05e1 }
            if (r6 == 0) goto L_0x0104
            goto L_0x010b
        L_0x0104:
            java.lang.Object r10 = com.google.android.gms.internal.measurement.zzke.zza((int) r1, (int) r4, r10, r7)     // Catch:{ zzim -> 0x05e1 }
            goto L_0x000a
        L_0x010b:
            r3 = r3 & r5
            long r5 = (long) r3     // Catch:{ zzim -> 0x05e1 }
            java.lang.Integer r3 = java.lang.Integer.valueOf(r4)     // Catch:{ zzim -> 0x05e1 }
            com.google.android.gms.internal.measurement.zzla.zza((java.lang.Object) r13, (long) r5, (java.lang.Object) r3)     // Catch:{ zzim -> 0x05e1 }
            r12.zzb(r13, (int) r1, (int) r2)     // Catch:{ zzim -> 0x05e1 }
            goto L_0x000a
        L_0x011a:
            r3 = r3 & r5
            long r3 = (long) r3     // Catch:{ zzim -> 0x05e1 }
            int r5 = r14.zzo()     // Catch:{ zzim -> 0x05e1 }
            java.lang.Integer r5 = java.lang.Integer.valueOf(r5)     // Catch:{ zzim -> 0x05e1 }
            com.google.android.gms.internal.measurement.zzla.zza((java.lang.Object) r13, (long) r3, (java.lang.Object) r5)     // Catch:{ zzim -> 0x05e1 }
            r12.zzb(r13, (int) r1, (int) r2)     // Catch:{ zzim -> 0x05e1 }
            goto L_0x000a
        L_0x012d:
            r3 = r3 & r5
            long r3 = (long) r3     // Catch:{ zzim -> 0x05e1 }
            com.google.android.gms.internal.measurement.zzgt r5 = r14.zzn()     // Catch:{ zzim -> 0x05e1 }
            com.google.android.gms.internal.measurement.zzla.zza((java.lang.Object) r13, (long) r3, (java.lang.Object) r5)     // Catch:{ zzim -> 0x05e1 }
            r12.zzb(r13, (int) r1, (int) r2)     // Catch:{ zzim -> 0x05e1 }
            goto L_0x000a
        L_0x013c:
            boolean r4 = r12.zza(r13, (int) r1, (int) r2)     // Catch:{ zzim -> 0x05e1 }
            if (r4 == 0) goto L_0x015b
            r3 = r3 & r5
            long r3 = (long) r3     // Catch:{ zzim -> 0x05e1 }
            java.lang.Object r5 = com.google.android.gms.internal.measurement.zzla.zzf(r13, r3)     // Catch:{ zzim -> 0x05e1 }
            com.google.android.gms.internal.measurement.zzkc r6 = r12.zza((int) r2)     // Catch:{ zzim -> 0x05e1 }
            java.lang.Object r6 = r14.zza(r6, r15)     // Catch:{ zzim -> 0x05e1 }
            java.lang.Object r5 = com.google.android.gms.internal.measurement.zzie.zza((java.lang.Object) r5, (java.lang.Object) r6)     // Catch:{ zzim -> 0x05e1 }
            com.google.android.gms.internal.measurement.zzla.zza((java.lang.Object) r13, (long) r3, (java.lang.Object) r5)     // Catch:{ zzim -> 0x05e1 }
            goto L_0x016d
        L_0x015b:
            r3 = r3 & r5
            long r3 = (long) r3     // Catch:{ zzim -> 0x05e1 }
            com.google.android.gms.internal.measurement.zzkc r5 = r12.zza((int) r2)     // Catch:{ zzim -> 0x05e1 }
            java.lang.Object r5 = r14.zza(r5, r15)     // Catch:{ zzim -> 0x05e1 }
            com.google.android.gms.internal.measurement.zzla.zza((java.lang.Object) r13, (long) r3, (java.lang.Object) r5)     // Catch:{ zzim -> 0x05e1 }
            r12.zzb(r13, (int) r2)     // Catch:{ zzim -> 0x05e1 }
        L_0x016d:
            r12.zzb(r13, (int) r1, (int) r2)     // Catch:{ zzim -> 0x05e1 }
            goto L_0x000a
        L_0x0172:
            r12.zza((java.lang.Object) r13, (int) r3, (com.google.android.gms.internal.measurement.zzjz) r14)     // Catch:{ zzim -> 0x05e1 }
            r12.zzb(r13, (int) r1, (int) r2)     // Catch:{ zzim -> 0x05e1 }
            goto L_0x000a
        L_0x017a:
            r3 = r3 & r5
            long r3 = (long) r3     // Catch:{ zzim -> 0x05e1 }
            boolean r5 = r14.zzk()     // Catch:{ zzim -> 0x05e1 }
            java.lang.Boolean r5 = java.lang.Boolean.valueOf(r5)     // Catch:{ zzim -> 0x05e1 }
            com.google.android.gms.internal.measurement.zzla.zza((java.lang.Object) r13, (long) r3, (java.lang.Object) r5)     // Catch:{ zzim -> 0x05e1 }
            r12.zzb(r13, (int) r1, (int) r2)     // Catch:{ zzim -> 0x05e1 }
            goto L_0x000a
        L_0x018d:
            r3 = r3 & r5
            long r3 = (long) r3     // Catch:{ zzim -> 0x05e1 }
            int r5 = r14.zzj()     // Catch:{ zzim -> 0x05e1 }
            java.lang.Integer r5 = java.lang.Integer.valueOf(r5)     // Catch:{ zzim -> 0x05e1 }
            com.google.android.gms.internal.measurement.zzla.zza((java.lang.Object) r13, (long) r3, (java.lang.Object) r5)     // Catch:{ zzim -> 0x05e1 }
            r12.zzb(r13, (int) r1, (int) r2)     // Catch:{ zzim -> 0x05e1 }
            goto L_0x000a
        L_0x01a0:
            r3 = r3 & r5
            long r3 = (long) r3     // Catch:{ zzim -> 0x05e1 }
            long r5 = r14.zzi()     // Catch:{ zzim -> 0x05e1 }
            java.lang.Long r5 = java.lang.Long.valueOf(r5)     // Catch:{ zzim -> 0x05e1 }
            com.google.android.gms.internal.measurement.zzla.zza((java.lang.Object) r13, (long) r3, (java.lang.Object) r5)     // Catch:{ zzim -> 0x05e1 }
            r12.zzb(r13, (int) r1, (int) r2)     // Catch:{ zzim -> 0x05e1 }
            goto L_0x000a
        L_0x01b3:
            r3 = r3 & r5
            long r3 = (long) r3     // Catch:{ zzim -> 0x05e1 }
            int r5 = r14.zzh()     // Catch:{ zzim -> 0x05e1 }
            java.lang.Integer r5 = java.lang.Integer.valueOf(r5)     // Catch:{ zzim -> 0x05e1 }
            com.google.android.gms.internal.measurement.zzla.zza((java.lang.Object) r13, (long) r3, (java.lang.Object) r5)     // Catch:{ zzim -> 0x05e1 }
            r12.zzb(r13, (int) r1, (int) r2)     // Catch:{ zzim -> 0x05e1 }
            goto L_0x000a
        L_0x01c6:
            r3 = r3 & r5
            long r3 = (long) r3     // Catch:{ zzim -> 0x05e1 }
            long r5 = r14.zzf()     // Catch:{ zzim -> 0x05e1 }
            java.lang.Long r5 = java.lang.Long.valueOf(r5)     // Catch:{ zzim -> 0x05e1 }
            com.google.android.gms.internal.measurement.zzla.zza((java.lang.Object) r13, (long) r3, (java.lang.Object) r5)     // Catch:{ zzim -> 0x05e1 }
            r12.zzb(r13, (int) r1, (int) r2)     // Catch:{ zzim -> 0x05e1 }
            goto L_0x000a
        L_0x01d9:
            r3 = r3 & r5
            long r3 = (long) r3     // Catch:{ zzim -> 0x05e1 }
            long r5 = r14.zzg()     // Catch:{ zzim -> 0x05e1 }
            java.lang.Long r5 = java.lang.Long.valueOf(r5)     // Catch:{ zzim -> 0x05e1 }
            com.google.android.gms.internal.measurement.zzla.zza((java.lang.Object) r13, (long) r3, (java.lang.Object) r5)     // Catch:{ zzim -> 0x05e1 }
            r12.zzb(r13, (int) r1, (int) r2)     // Catch:{ zzim -> 0x05e1 }
            goto L_0x000a
        L_0x01ec:
            r3 = r3 & r5
            long r3 = (long) r3     // Catch:{ zzim -> 0x05e1 }
            float r5 = r14.zze()     // Catch:{ zzim -> 0x05e1 }
            java.lang.Float r5 = java.lang.Float.valueOf(r5)     // Catch:{ zzim -> 0x05e1 }
            com.google.android.gms.internal.measurement.zzla.zza((java.lang.Object) r13, (long) r3, (java.lang.Object) r5)     // Catch:{ zzim -> 0x05e1 }
            r12.zzb(r13, (int) r1, (int) r2)     // Catch:{ zzim -> 0x05e1 }
            goto L_0x000a
        L_0x01ff:
            r3 = r3 & r5
            long r3 = (long) r3     // Catch:{ zzim -> 0x05e1 }
            double r5 = r14.zzd()     // Catch:{ zzim -> 0x05e1 }
            java.lang.Double r5 = java.lang.Double.valueOf(r5)     // Catch:{ zzim -> 0x05e1 }
            com.google.android.gms.internal.measurement.zzla.zza((java.lang.Object) r13, (long) r3, (java.lang.Object) r5)     // Catch:{ zzim -> 0x05e1 }
            r12.zzb(r13, (int) r1, (int) r2)     // Catch:{ zzim -> 0x05e1 }
            goto L_0x000a
        L_0x0212:
            java.lang.Object r1 = r12.zzb((int) r2)     // Catch:{ zzim -> 0x05e1 }
            int r2 = r12.zzd((int) r2)     // Catch:{ zzim -> 0x05e1 }
            r2 = r2 & r5
            long r2 = (long) r2     // Catch:{ zzim -> 0x05e1 }
            java.lang.Object r4 = com.google.android.gms.internal.measurement.zzla.zzf(r13, r2)     // Catch:{ zzim -> 0x05e1 }
            if (r4 != 0) goto L_0x022d
            com.google.android.gms.internal.measurement.zzjg r4 = r12.zzs     // Catch:{ zzim -> 0x05e1 }
            java.lang.Object r4 = r4.zzf(r1)     // Catch:{ zzim -> 0x05e1 }
            com.google.android.gms.internal.measurement.zzla.zza((java.lang.Object) r13, (long) r2, (java.lang.Object) r4)     // Catch:{ zzim -> 0x05e1 }
            goto L_0x0245
        L_0x022d:
            com.google.android.gms.internal.measurement.zzjg r5 = r12.zzs     // Catch:{ zzim -> 0x05e1 }
            boolean r5 = r5.zzd(r4)     // Catch:{ zzim -> 0x05e1 }
            if (r5 == 0) goto L_0x0245
            com.google.android.gms.internal.measurement.zzjg r5 = r12.zzs     // Catch:{ zzim -> 0x05e1 }
            java.lang.Object r5 = r5.zzf(r1)     // Catch:{ zzim -> 0x05e1 }
            com.google.android.gms.internal.measurement.zzjg r6 = r12.zzs     // Catch:{ zzim -> 0x05e1 }
            r6.zza(r5, r4)     // Catch:{ zzim -> 0x05e1 }
            com.google.android.gms.internal.measurement.zzla.zza((java.lang.Object) r13, (long) r2, (java.lang.Object) r5)     // Catch:{ zzim -> 0x05e1 }
            r4 = r5
        L_0x0245:
            com.google.android.gms.internal.measurement.zzjg r2 = r12.zzs     // Catch:{ zzim -> 0x05e1 }
            java.util.Map r2 = r2.zza(r4)     // Catch:{ zzim -> 0x05e1 }
            com.google.android.gms.internal.measurement.zzjg r3 = r12.zzs     // Catch:{ zzim -> 0x05e1 }
            com.google.android.gms.internal.measurement.zzje r1 = r3.zzb(r1)     // Catch:{ zzim -> 0x05e1 }
            r14.zza(r2, r1, (com.google.android.gms.internal.measurement.zzho) r15)     // Catch:{ zzim -> 0x05e1 }
            goto L_0x000a
        L_0x0256:
            r1 = r3 & r5
            long r3 = (long) r1     // Catch:{ zzim -> 0x05e1 }
            com.google.android.gms.internal.measurement.zzkc r1 = r12.zza((int) r2)     // Catch:{ zzim -> 0x05e1 }
            com.google.android.gms.internal.measurement.zzit r2 = r12.zzp     // Catch:{ zzim -> 0x05e1 }
            java.util.List r2 = r2.zza(r13, r3)     // Catch:{ zzim -> 0x05e1 }
            r14.zzb(r2, r1, r15)     // Catch:{ zzim -> 0x05e1 }
            goto L_0x000a
        L_0x026b:
            com.google.android.gms.internal.measurement.zzit r1 = r12.zzp     // Catch:{ zzim -> 0x05e1 }
            r2 = r3 & r5
            long r2 = (long) r2     // Catch:{ zzim -> 0x05e1 }
            java.util.List r1 = r1.zza(r13, r2)     // Catch:{ zzim -> 0x05e1 }
            r14.zzq(r1)     // Catch:{ zzim -> 0x05e1 }
            goto L_0x000a
        L_0x0279:
            com.google.android.gms.internal.measurement.zzit r1 = r12.zzp     // Catch:{ zzim -> 0x05e1 }
            r2 = r3 & r5
            long r2 = (long) r2     // Catch:{ zzim -> 0x05e1 }
            java.util.List r1 = r1.zza(r13, r2)     // Catch:{ zzim -> 0x05e1 }
            r14.zzp(r1)     // Catch:{ zzim -> 0x05e1 }
            goto L_0x000a
        L_0x0287:
            com.google.android.gms.internal.measurement.zzit r1 = r12.zzp     // Catch:{ zzim -> 0x05e1 }
            r2 = r3 & r5
            long r2 = (long) r2     // Catch:{ zzim -> 0x05e1 }
            java.util.List r1 = r1.zza(r13, r2)     // Catch:{ zzim -> 0x05e1 }
            r14.zzo(r1)     // Catch:{ zzim -> 0x05e1 }
            goto L_0x000a
        L_0x0295:
            com.google.android.gms.internal.measurement.zzit r1 = r12.zzp     // Catch:{ zzim -> 0x05e1 }
            r2 = r3 & r5
            long r2 = (long) r2     // Catch:{ zzim -> 0x05e1 }
            java.util.List r1 = r1.zza(r13, r2)     // Catch:{ zzim -> 0x05e1 }
            r14.zzn(r1)     // Catch:{ zzim -> 0x05e1 }
            goto L_0x000a
        L_0x02a3:
            com.google.android.gms.internal.measurement.zzit r4 = r12.zzp     // Catch:{ zzim -> 0x05e1 }
            r3 = r3 & r5
            long r5 = (long) r3     // Catch:{ zzim -> 0x05e1 }
            java.util.List r3 = r4.zza(r13, r5)     // Catch:{ zzim -> 0x05e1 }
            r14.zzm(r3)     // Catch:{ zzim -> 0x05e1 }
            com.google.android.gms.internal.measurement.zzif r2 = r12.zzc((int) r2)     // Catch:{ zzim -> 0x05e1 }
            java.lang.Object r10 = com.google.android.gms.internal.measurement.zzke.zza(r1, r3, r2, r10, r7)     // Catch:{ zzim -> 0x05e1 }
            goto L_0x000a
        L_0x02b9:
            com.google.android.gms.internal.measurement.zzit r1 = r12.zzp     // Catch:{ zzim -> 0x05e1 }
            r2 = r3 & r5
            long r2 = (long) r2     // Catch:{ zzim -> 0x05e1 }
            java.util.List r1 = r1.zza(r13, r2)     // Catch:{ zzim -> 0x05e1 }
            r14.zzl(r1)     // Catch:{ zzim -> 0x05e1 }
            goto L_0x000a
        L_0x02c7:
            com.google.android.gms.internal.measurement.zzit r1 = r12.zzp     // Catch:{ zzim -> 0x05e1 }
            r2 = r3 & r5
            long r2 = (long) r2     // Catch:{ zzim -> 0x05e1 }
            java.util.List r1 = r1.zza(r13, r2)     // Catch:{ zzim -> 0x05e1 }
            r14.zzh(r1)     // Catch:{ zzim -> 0x05e1 }
            goto L_0x000a
        L_0x02d5:
            com.google.android.gms.internal.measurement.zzit r1 = r12.zzp     // Catch:{ zzim -> 0x05e1 }
            r2 = r3 & r5
            long r2 = (long) r2     // Catch:{ zzim -> 0x05e1 }
            java.util.List r1 = r1.zza(r13, r2)     // Catch:{ zzim -> 0x05e1 }
            r14.zzg(r1)     // Catch:{ zzim -> 0x05e1 }
            goto L_0x000a
        L_0x02e3:
            com.google.android.gms.internal.measurement.zzit r1 = r12.zzp     // Catch:{ zzim -> 0x05e1 }
            r2 = r3 & r5
            long r2 = (long) r2     // Catch:{ zzim -> 0x05e1 }
            java.util.List r1 = r1.zza(r13, r2)     // Catch:{ zzim -> 0x05e1 }
            r14.zzf(r1)     // Catch:{ zzim -> 0x05e1 }
            goto L_0x000a
        L_0x02f1:
            com.google.android.gms.internal.measurement.zzit r1 = r12.zzp     // Catch:{ zzim -> 0x05e1 }
            r2 = r3 & r5
            long r2 = (long) r2     // Catch:{ zzim -> 0x05e1 }
            java.util.List r1 = r1.zza(r13, r2)     // Catch:{ zzim -> 0x05e1 }
            r14.zze(r1)     // Catch:{ zzim -> 0x05e1 }
            goto L_0x000a
        L_0x02ff:
            com.google.android.gms.internal.measurement.zzit r1 = r12.zzp     // Catch:{ zzim -> 0x05e1 }
            r2 = r3 & r5
            long r2 = (long) r2     // Catch:{ zzim -> 0x05e1 }
            java.util.List r1 = r1.zza(r13, r2)     // Catch:{ zzim -> 0x05e1 }
            r14.zzc(r1)     // Catch:{ zzim -> 0x05e1 }
            goto L_0x000a
        L_0x030d:
            com.google.android.gms.internal.measurement.zzit r1 = r12.zzp     // Catch:{ zzim -> 0x05e1 }
            r2 = r3 & r5
            long r2 = (long) r2     // Catch:{ zzim -> 0x05e1 }
            java.util.List r1 = r1.zza(r13, r2)     // Catch:{ zzim -> 0x05e1 }
            r14.zzd(r1)     // Catch:{ zzim -> 0x05e1 }
            goto L_0x000a
        L_0x031b:
            com.google.android.gms.internal.measurement.zzit r1 = r12.zzp     // Catch:{ zzim -> 0x05e1 }
            r2 = r3 & r5
            long r2 = (long) r2     // Catch:{ zzim -> 0x05e1 }
            java.util.List r1 = r1.zza(r13, r2)     // Catch:{ zzim -> 0x05e1 }
            r14.zzb(r1)     // Catch:{ zzim -> 0x05e1 }
            goto L_0x000a
        L_0x0329:
            com.google.android.gms.internal.measurement.zzit r1 = r12.zzp     // Catch:{ zzim -> 0x05e1 }
            r2 = r3 & r5
            long r2 = (long) r2     // Catch:{ zzim -> 0x05e1 }
            java.util.List r1 = r1.zza(r13, r2)     // Catch:{ zzim -> 0x05e1 }
            r14.zza(r1)     // Catch:{ zzim -> 0x05e1 }
            goto L_0x000a
        L_0x0337:
            com.google.android.gms.internal.measurement.zzit r1 = r12.zzp     // Catch:{ zzim -> 0x05e1 }
            r2 = r3 & r5
            long r2 = (long) r2     // Catch:{ zzim -> 0x05e1 }
            java.util.List r1 = r1.zza(r13, r2)     // Catch:{ zzim -> 0x05e1 }
            r14.zzq(r1)     // Catch:{ zzim -> 0x05e1 }
            goto L_0x000a
        L_0x0345:
            com.google.android.gms.internal.measurement.zzit r1 = r12.zzp     // Catch:{ zzim -> 0x05e1 }
            r2 = r3 & r5
            long r2 = (long) r2     // Catch:{ zzim -> 0x05e1 }
            java.util.List r1 = r1.zza(r13, r2)     // Catch:{ zzim -> 0x05e1 }
            r14.zzp(r1)     // Catch:{ zzim -> 0x05e1 }
            goto L_0x000a
        L_0x0353:
            com.google.android.gms.internal.measurement.zzit r1 = r12.zzp     // Catch:{ zzim -> 0x05e1 }
            r2 = r3 & r5
            long r2 = (long) r2     // Catch:{ zzim -> 0x05e1 }
            java.util.List r1 = r1.zza(r13, r2)     // Catch:{ zzim -> 0x05e1 }
            r14.zzo(r1)     // Catch:{ zzim -> 0x05e1 }
            goto L_0x000a
        L_0x0361:
            com.google.android.gms.internal.measurement.zzit r1 = r12.zzp     // Catch:{ zzim -> 0x05e1 }
            r2 = r3 & r5
            long r2 = (long) r2     // Catch:{ zzim -> 0x05e1 }
            java.util.List r1 = r1.zza(r13, r2)     // Catch:{ zzim -> 0x05e1 }
            r14.zzn(r1)     // Catch:{ zzim -> 0x05e1 }
            goto L_0x000a
        L_0x036f:
            com.google.android.gms.internal.measurement.zzit r4 = r12.zzp     // Catch:{ zzim -> 0x05e1 }
            r3 = r3 & r5
            long r5 = (long) r3     // Catch:{ zzim -> 0x05e1 }
            java.util.List r3 = r4.zza(r13, r5)     // Catch:{ zzim -> 0x05e1 }
            r14.zzm(r3)     // Catch:{ zzim -> 0x05e1 }
            com.google.android.gms.internal.measurement.zzif r2 = r12.zzc((int) r2)     // Catch:{ zzim -> 0x05e1 }
            java.lang.Object r10 = com.google.android.gms.internal.measurement.zzke.zza(r1, r3, r2, r10, r7)     // Catch:{ zzim -> 0x05e1 }
            goto L_0x000a
        L_0x0385:
            com.google.android.gms.internal.measurement.zzit r1 = r12.zzp     // Catch:{ zzim -> 0x05e1 }
            r2 = r3 & r5
            long r2 = (long) r2     // Catch:{ zzim -> 0x05e1 }
            java.util.List r1 = r1.zza(r13, r2)     // Catch:{ zzim -> 0x05e1 }
            r14.zzl(r1)     // Catch:{ zzim -> 0x05e1 }
            goto L_0x000a
        L_0x0393:
            com.google.android.gms.internal.measurement.zzit r1 = r12.zzp     // Catch:{ zzim -> 0x05e1 }
            r2 = r3 & r5
            long r2 = (long) r2     // Catch:{ zzim -> 0x05e1 }
            java.util.List r1 = r1.zza(r13, r2)     // Catch:{ zzim -> 0x05e1 }
            r14.zzk(r1)     // Catch:{ zzim -> 0x05e1 }
            goto L_0x000a
        L_0x03a1:
            com.google.android.gms.internal.measurement.zzkc r1 = r12.zza((int) r2)     // Catch:{ zzim -> 0x05e1 }
            r2 = r3 & r5
            long r2 = (long) r2     // Catch:{ zzim -> 0x05e1 }
            com.google.android.gms.internal.measurement.zzit r4 = r12.zzp     // Catch:{ zzim -> 0x05e1 }
            java.util.List r2 = r4.zza(r13, r2)     // Catch:{ zzim -> 0x05e1 }
            r14.zza(r2, r1, (com.google.android.gms.internal.measurement.zzho) r15)     // Catch:{ zzim -> 0x05e1 }
            goto L_0x000a
        L_0x03b7:
            boolean r1 = zzf(r3)     // Catch:{ zzim -> 0x05e1 }
            if (r1 == 0) goto L_0x03cc
            com.google.android.gms.internal.measurement.zzit r1 = r12.zzp     // Catch:{ zzim -> 0x05e1 }
            r2 = r3 & r5
            long r2 = (long) r2     // Catch:{ zzim -> 0x05e1 }
            java.util.List r1 = r1.zza(r13, r2)     // Catch:{ zzim -> 0x05e1 }
            r14.zzj(r1)     // Catch:{ zzim -> 0x05e1 }
            goto L_0x000a
        L_0x03cc:
            com.google.android.gms.internal.measurement.zzit r1 = r12.zzp     // Catch:{ zzim -> 0x05e1 }
            r2 = r3 & r5
            long r2 = (long) r2     // Catch:{ zzim -> 0x05e1 }
            java.util.List r1 = r1.zza(r13, r2)     // Catch:{ zzim -> 0x05e1 }
            r14.zzi(r1)     // Catch:{ zzim -> 0x05e1 }
            goto L_0x000a
        L_0x03da:
            com.google.android.gms.internal.measurement.zzit r1 = r12.zzp     // Catch:{ zzim -> 0x05e1 }
            r2 = r3 & r5
            long r2 = (long) r2     // Catch:{ zzim -> 0x05e1 }
            java.util.List r1 = r1.zza(r13, r2)     // Catch:{ zzim -> 0x05e1 }
            r14.zzh(r1)     // Catch:{ zzim -> 0x05e1 }
            goto L_0x000a
        L_0x03e8:
            com.google.android.gms.internal.measurement.zzit r1 = r12.zzp     // Catch:{ zzim -> 0x05e1 }
            r2 = r3 & r5
            long r2 = (long) r2     // Catch:{ zzim -> 0x05e1 }
            java.util.List r1 = r1.zza(r13, r2)     // Catch:{ zzim -> 0x05e1 }
            r14.zzg(r1)     // Catch:{ zzim -> 0x05e1 }
            goto L_0x000a
        L_0x03f6:
            com.google.android.gms.internal.measurement.zzit r1 = r12.zzp     // Catch:{ zzim -> 0x05e1 }
            r2 = r3 & r5
            long r2 = (long) r2     // Catch:{ zzim -> 0x05e1 }
            java.util.List r1 = r1.zza(r13, r2)     // Catch:{ zzim -> 0x05e1 }
            r14.zzf(r1)     // Catch:{ zzim -> 0x05e1 }
            goto L_0x000a
        L_0x0404:
            com.google.android.gms.internal.measurement.zzit r1 = r12.zzp     // Catch:{ zzim -> 0x05e1 }
            r2 = r3 & r5
            long r2 = (long) r2     // Catch:{ zzim -> 0x05e1 }
            java.util.List r1 = r1.zza(r13, r2)     // Catch:{ zzim -> 0x05e1 }
            r14.zze(r1)     // Catch:{ zzim -> 0x05e1 }
            goto L_0x000a
        L_0x0412:
            com.google.android.gms.internal.measurement.zzit r1 = r12.zzp     // Catch:{ zzim -> 0x05e1 }
            r2 = r3 & r5
            long r2 = (long) r2     // Catch:{ zzim -> 0x05e1 }
            java.util.List r1 = r1.zza(r13, r2)     // Catch:{ zzim -> 0x05e1 }
            r14.zzc(r1)     // Catch:{ zzim -> 0x05e1 }
            goto L_0x000a
        L_0x0420:
            com.google.android.gms.internal.measurement.zzit r1 = r12.zzp     // Catch:{ zzim -> 0x05e1 }
            r2 = r3 & r5
            long r2 = (long) r2     // Catch:{ zzim -> 0x05e1 }
            java.util.List r1 = r1.zza(r13, r2)     // Catch:{ zzim -> 0x05e1 }
            r14.zzd(r1)     // Catch:{ zzim -> 0x05e1 }
            goto L_0x000a
        L_0x042e:
            com.google.android.gms.internal.measurement.zzit r1 = r12.zzp     // Catch:{ zzim -> 0x05e1 }
            r2 = r3 & r5
            long r2 = (long) r2     // Catch:{ zzim -> 0x05e1 }
            java.util.List r1 = r1.zza(r13, r2)     // Catch:{ zzim -> 0x05e1 }
            r14.zzb(r1)     // Catch:{ zzim -> 0x05e1 }
            goto L_0x000a
        L_0x043c:
            com.google.android.gms.internal.measurement.zzit r1 = r12.zzp     // Catch:{ zzim -> 0x05e1 }
            r2 = r3 & r5
            long r2 = (long) r2     // Catch:{ zzim -> 0x05e1 }
            java.util.List r1 = r1.zza(r13, r2)     // Catch:{ zzim -> 0x05e1 }
            r14.zza(r1)     // Catch:{ zzim -> 0x05e1 }
            goto L_0x000a
        L_0x044a:
            boolean r1 = r12.zza(r13, (int) r2)     // Catch:{ zzim -> 0x05e1 }
            if (r1 == 0) goto L_0x046b
            r1 = r3 & r5
            long r3 = (long) r1     // Catch:{ zzim -> 0x05e1 }
            java.lang.Object r1 = com.google.android.gms.internal.measurement.zzla.zzf(r13, r3)     // Catch:{ zzim -> 0x05e1 }
            com.google.android.gms.internal.measurement.zzkc r2 = r12.zza((int) r2)     // Catch:{ zzim -> 0x05e1 }
            java.lang.Object r2 = r14.zzb(r2, r15)     // Catch:{ zzim -> 0x05e1 }
            java.lang.Object r1 = com.google.android.gms.internal.measurement.zzie.zza((java.lang.Object) r1, (java.lang.Object) r2)     // Catch:{ zzim -> 0x05e1 }
            com.google.android.gms.internal.measurement.zzla.zza((java.lang.Object) r13, (long) r3, (java.lang.Object) r1)     // Catch:{ zzim -> 0x05e1 }
            goto L_0x000a
        L_0x046b:
            r1 = r3 & r5
            long r3 = (long) r1     // Catch:{ zzim -> 0x05e1 }
            com.google.android.gms.internal.measurement.zzkc r1 = r12.zza((int) r2)     // Catch:{ zzim -> 0x05e1 }
            java.lang.Object r1 = r14.zzb(r1, r15)     // Catch:{ zzim -> 0x05e1 }
            com.google.android.gms.internal.measurement.zzla.zza((java.lang.Object) r13, (long) r3, (java.lang.Object) r1)     // Catch:{ zzim -> 0x05e1 }
            r12.zzb(r13, (int) r2)     // Catch:{ zzim -> 0x05e1 }
            goto L_0x000a
        L_0x0480:
            r1 = r3 & r5
            long r3 = (long) r1     // Catch:{ zzim -> 0x05e1 }
            long r5 = r14.zzt()     // Catch:{ zzim -> 0x05e1 }
            com.google.android.gms.internal.measurement.zzla.zza((java.lang.Object) r13, (long) r3, (long) r5)     // Catch:{ zzim -> 0x05e1 }
            r12.zzb(r13, (int) r2)     // Catch:{ zzim -> 0x05e1 }
            goto L_0x000a
        L_0x0490:
            r1 = r3 & r5
            long r3 = (long) r1     // Catch:{ zzim -> 0x05e1 }
            int r1 = r14.zzs()     // Catch:{ zzim -> 0x05e1 }
            com.google.android.gms.internal.measurement.zzla.zza((java.lang.Object) r13, (long) r3, (int) r1)     // Catch:{ zzim -> 0x05e1 }
            r12.zzb(r13, (int) r2)     // Catch:{ zzim -> 0x05e1 }
            goto L_0x000a
        L_0x04a0:
            r1 = r3 & r5
            long r3 = (long) r1     // Catch:{ zzim -> 0x05e1 }
            long r5 = r14.zzr()     // Catch:{ zzim -> 0x05e1 }
            com.google.android.gms.internal.measurement.zzla.zza((java.lang.Object) r13, (long) r3, (long) r5)     // Catch:{ zzim -> 0x05e1 }
            r12.zzb(r13, (int) r2)     // Catch:{ zzim -> 0x05e1 }
            goto L_0x000a
        L_0x04b0:
            r1 = r3 & r5
            long r3 = (long) r1     // Catch:{ zzim -> 0x05e1 }
            int r1 = r14.zzq()     // Catch:{ zzim -> 0x05e1 }
            com.google.android.gms.internal.measurement.zzla.zza((java.lang.Object) r13, (long) r3, (int) r1)     // Catch:{ zzim -> 0x05e1 }
            r12.zzb(r13, (int) r2)     // Catch:{ zzim -> 0x05e1 }
            goto L_0x000a
        L_0x04c0:
            int r4 = r14.zzp()     // Catch:{ zzim -> 0x05e1 }
            com.google.android.gms.internal.measurement.zzif r6 = r12.zzc((int) r2)     // Catch:{ zzim -> 0x05e1 }
            if (r6 == 0) goto L_0x04d8
            boolean r6 = r6.zza(r4)     // Catch:{ zzim -> 0x05e1 }
            if (r6 == 0) goto L_0x04d1
            goto L_0x04d8
        L_0x04d1:
            java.lang.Object r10 = com.google.android.gms.internal.measurement.zzke.zza((int) r1, (int) r4, r10, r7)     // Catch:{ zzim -> 0x05e1 }
            goto L_0x000a
        L_0x04d8:
            r1 = r3 & r5
            long r5 = (long) r1     // Catch:{ zzim -> 0x05e1 }
            com.google.android.gms.internal.measurement.zzla.zza((java.lang.Object) r13, (long) r5, (int) r4)     // Catch:{ zzim -> 0x05e1 }
            r12.zzb(r13, (int) r2)     // Catch:{ zzim -> 0x05e1 }
            goto L_0x000a
        L_0x04e4:
            r1 = r3 & r5
            long r3 = (long) r1     // Catch:{ zzim -> 0x05e1 }
            int r1 = r14.zzo()     // Catch:{ zzim -> 0x05e1 }
            com.google.android.gms.internal.measurement.zzla.zza((java.lang.Object) r13, (long) r3, (int) r1)     // Catch:{ zzim -> 0x05e1 }
            r12.zzb(r13, (int) r2)     // Catch:{ zzim -> 0x05e1 }
            goto L_0x000a
        L_0x04f4:
            r1 = r3 & r5
            long r3 = (long) r1     // Catch:{ zzim -> 0x05e1 }
            com.google.android.gms.internal.measurement.zzgt r1 = r14.zzn()     // Catch:{ zzim -> 0x05e1 }
            com.google.android.gms.internal.measurement.zzla.zza((java.lang.Object) r13, (long) r3, (java.lang.Object) r1)     // Catch:{ zzim -> 0x05e1 }
            r12.zzb(r13, (int) r2)     // Catch:{ zzim -> 0x05e1 }
            goto L_0x000a
        L_0x0504:
            boolean r1 = r12.zza(r13, (int) r2)     // Catch:{ zzim -> 0x05e1 }
            if (r1 == 0) goto L_0x0525
            r1 = r3 & r5
            long r3 = (long) r1     // Catch:{ zzim -> 0x05e1 }
            java.lang.Object r1 = com.google.android.gms.internal.measurement.zzla.zzf(r13, r3)     // Catch:{ zzim -> 0x05e1 }
            com.google.android.gms.internal.measurement.zzkc r2 = r12.zza((int) r2)     // Catch:{ zzim -> 0x05e1 }
            java.lang.Object r2 = r14.zza(r2, r15)     // Catch:{ zzim -> 0x05e1 }
            java.lang.Object r1 = com.google.android.gms.internal.measurement.zzie.zza((java.lang.Object) r1, (java.lang.Object) r2)     // Catch:{ zzim -> 0x05e1 }
            com.google.android.gms.internal.measurement.zzla.zza((java.lang.Object) r13, (long) r3, (java.lang.Object) r1)     // Catch:{ zzim -> 0x05e1 }
            goto L_0x000a
        L_0x0525:
            r1 = r3 & r5
            long r3 = (long) r1     // Catch:{ zzim -> 0x05e1 }
            com.google.android.gms.internal.measurement.zzkc r1 = r12.zza((int) r2)     // Catch:{ zzim -> 0x05e1 }
            java.lang.Object r1 = r14.zza(r1, r15)     // Catch:{ zzim -> 0x05e1 }
            com.google.android.gms.internal.measurement.zzla.zza((java.lang.Object) r13, (long) r3, (java.lang.Object) r1)     // Catch:{ zzim -> 0x05e1 }
            r12.zzb(r13, (int) r2)     // Catch:{ zzim -> 0x05e1 }
            goto L_0x000a
        L_0x053a:
            r12.zza((java.lang.Object) r13, (int) r3, (com.google.android.gms.internal.measurement.zzjz) r14)     // Catch:{ zzim -> 0x05e1 }
            r12.zzb(r13, (int) r2)     // Catch:{ zzim -> 0x05e1 }
            goto L_0x000a
        L_0x0542:
            r1 = r3 & r5
            long r3 = (long) r1     // Catch:{ zzim -> 0x05e1 }
            boolean r1 = r14.zzk()     // Catch:{ zzim -> 0x05e1 }
            com.google.android.gms.internal.measurement.zzla.zza((java.lang.Object) r13, (long) r3, (boolean) r1)     // Catch:{ zzim -> 0x05e1 }
            r12.zzb(r13, (int) r2)     // Catch:{ zzim -> 0x05e1 }
            goto L_0x000a
        L_0x0552:
            r1 = r3 & r5
            long r3 = (long) r1     // Catch:{ zzim -> 0x05e1 }
            int r1 = r14.zzj()     // Catch:{ zzim -> 0x05e1 }
            com.google.android.gms.internal.measurement.zzla.zza((java.lang.Object) r13, (long) r3, (int) r1)     // Catch:{ zzim -> 0x05e1 }
            r12.zzb(r13, (int) r2)     // Catch:{ zzim -> 0x05e1 }
            goto L_0x000a
        L_0x0562:
            r1 = r3 & r5
            long r3 = (long) r1     // Catch:{ zzim -> 0x05e1 }
            long r5 = r14.zzi()     // Catch:{ zzim -> 0x05e1 }
            com.google.android.gms.internal.measurement.zzla.zza((java.lang.Object) r13, (long) r3, (long) r5)     // Catch:{ zzim -> 0x05e1 }
            r12.zzb(r13, (int) r2)     // Catch:{ zzim -> 0x05e1 }
            goto L_0x000a
        L_0x0572:
            r1 = r3 & r5
            long r3 = (long) r1     // Catch:{ zzim -> 0x05e1 }
            int r1 = r14.zzh()     // Catch:{ zzim -> 0x05e1 }
            com.google.android.gms.internal.measurement.zzla.zza((java.lang.Object) r13, (long) r3, (int) r1)     // Catch:{ zzim -> 0x05e1 }
            r12.zzb(r13, (int) r2)     // Catch:{ zzim -> 0x05e1 }
            goto L_0x000a
        L_0x0582:
            r1 = r3 & r5
            long r3 = (long) r1     // Catch:{ zzim -> 0x05e1 }
            long r5 = r14.zzf()     // Catch:{ zzim -> 0x05e1 }
            com.google.android.gms.internal.measurement.zzla.zza((java.lang.Object) r13, (long) r3, (long) r5)     // Catch:{ zzim -> 0x05e1 }
            r12.zzb(r13, (int) r2)     // Catch:{ zzim -> 0x05e1 }
            goto L_0x000a
        L_0x0592:
            r1 = r3 & r5
            long r3 = (long) r1     // Catch:{ zzim -> 0x05e1 }
            long r5 = r14.zzg()     // Catch:{ zzim -> 0x05e1 }
            com.google.android.gms.internal.measurement.zzla.zza((java.lang.Object) r13, (long) r3, (long) r5)     // Catch:{ zzim -> 0x05e1 }
            r12.zzb(r13, (int) r2)     // Catch:{ zzim -> 0x05e1 }
            goto L_0x000a
        L_0x05a2:
            r1 = r3 & r5
            long r3 = (long) r1     // Catch:{ zzim -> 0x05e1 }
            float r1 = r14.zze()     // Catch:{ zzim -> 0x05e1 }
            com.google.android.gms.internal.measurement.zzla.zza((java.lang.Object) r13, (long) r3, (float) r1)     // Catch:{ zzim -> 0x05e1 }
            r12.zzb(r13, (int) r2)     // Catch:{ zzim -> 0x05e1 }
            goto L_0x000a
        L_0x05b2:
            r1 = r3 & r5
            long r3 = (long) r1     // Catch:{ zzim -> 0x05e1 }
            double r5 = r14.zzd()     // Catch:{ zzim -> 0x05e1 }
            com.google.android.gms.internal.measurement.zzla.zza((java.lang.Object) r13, (long) r3, (double) r5)     // Catch:{ zzim -> 0x05e1 }
            r12.zzb(r13, (int) r2)     // Catch:{ zzim -> 0x05e1 }
            goto L_0x000a
        L_0x05c2:
            boolean r1 = r7.zza(r10, (com.google.android.gms.internal.measurement.zzjz) r14)     // Catch:{ zzim -> 0x05e1 }
            if (r1 != 0) goto L_0x05df
            int r14 = r12.zzm
        L_0x05ca:
            int r15 = r12.zzn
            if (r14 >= r15) goto L_0x05d9
            int[] r15 = r12.zzl
            r15 = r15[r14]
            java.lang.Object r10 = r12.zza((java.lang.Object) r13, (int) r15, r10, r7)
            int r14 = r14 + 1
            goto L_0x05ca
        L_0x05d9:
            if (r10 == 0) goto L_0x05de
            r7.zzb((java.lang.Object) r13, r10)
        L_0x05de:
            return
        L_0x05df:
            goto L_0x000a
        L_0x05e1:
            r1 = move-exception
            r7.zza((com.google.android.gms.internal.measurement.zzjz) r14)     // Catch:{ all -> 0x060b }
            if (r10 != 0) goto L_0x05ec
            java.lang.Object r1 = r7.zzc(r13)     // Catch:{ all -> 0x060b }
            r10 = r1
        L_0x05ec:
            boolean r1 = r7.zza(r10, (com.google.android.gms.internal.measurement.zzjz) r14)     // Catch:{ all -> 0x060b }
            if (r1 != 0) goto L_0x0609
            int r14 = r12.zzm
        L_0x05f4:
            int r15 = r12.zzn
            if (r14 >= r15) goto L_0x0603
            int[] r15 = r12.zzl
            r15 = r15[r14]
            java.lang.Object r10 = r12.zza((java.lang.Object) r13, (int) r15, r10, r7)
            int r14 = r14 + 1
            goto L_0x05f4
        L_0x0603:
            if (r10 == 0) goto L_0x0608
            r7.zzb((java.lang.Object) r13, r10)
        L_0x0608:
            return
        L_0x0609:
            goto L_0x000a
        L_0x060b:
            r14 = move-exception
            int r15 = r12.zzm
        L_0x060e:
            int r0 = r12.zzn
            if (r15 >= r0) goto L_0x061d
            int[] r0 = r12.zzl
            r0 = r0[r15]
            java.lang.Object r10 = r12.zza((java.lang.Object) r13, (int) r0, r10, r7)
            int r15 = r15 + 1
            goto L_0x060e
        L_0x061d:
            if (r10 == 0) goto L_0x0622
            r7.zzb((java.lang.Object) r13, r10)
        L_0x0622:
            throw r14
        L_0x0623:
            java.lang.NullPointerException r13 = new java.lang.NullPointerException
            r13.<init>()
            throw r13
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.measurement.zzjn.zza(java.lang.Object, com.google.android.gms.internal.measurement.zzjz, com.google.android.gms.internal.measurement.zzho):void");
    }

    private static zzkt zze(Object obj) {
        zzib zzib = (zzib) obj;
        zzkt zzkt = zzib.zzb;
        if (zzkt != zzkt.zza()) {
            return zzkt;
        }
        zzkt zzb2 = zzkt.zzb();
        zzib.zzb = zzb2;
        return zzb2;
    }

    private static int zza(byte[] bArr, int i, int i2, zzlh zzlh, Class<?> cls, zzgo zzgo) throws IOException {
        switch (zzjq.zza[zzlh.ordinal()]) {
            case 1:
                int zzb2 = zzgp.zzb(bArr, i, zzgo);
                zzgo.zzc = Boolean.valueOf(zzgo.zzb != 0);
                return zzb2;
            case 2:
                return zzgp.zze(bArr, i, zzgo);
            case 3:
                zzgo.zzc = Double.valueOf(zzgp.zzc(bArr, i));
                return i + 8;
            case 4:
            case 5:
                zzgo.zzc = Integer.valueOf(zzgp.zza(bArr, i));
                return i + 4;
            case 6:
            case 7:
                zzgo.zzc = Long.valueOf(zzgp.zzb(bArr, i));
                return i + 8;
            case 8:
                zzgo.zzc = Float.valueOf(zzgp.zzd(bArr, i));
                return i + 4;
            case 9:
            case 10:
            case 11:
                int zza2 = zzgp.zza(bArr, i, zzgo);
                zzgo.zzc = Integer.valueOf(zzgo.zza);
                return zza2;
            case 12:
            case 13:
                int zzb3 = zzgp.zzb(bArr, i, zzgo);
                zzgo.zzc = Long.valueOf(zzgo.zzb);
                return zzb3;
            case 14:
                return zzgp.zza((zzkc) zzjy.zza().zza(cls), bArr, i, i2, zzgo);
            case 15:
                int zza3 = zzgp.zza(bArr, i, zzgo);
                zzgo.zzc = Integer.valueOf(zzhf.zze(zzgo.zza));
                return zza3;
            case 16:
                int zzb4 = zzgp.zzb(bArr, i, zzgo);
                zzgo.zzc = Long.valueOf(zzhf.zza(zzgo.zzb));
                return zzb4;
            case 17:
                return zzgp.zzd(bArr, i, zzgo);
            default:
                throw new RuntimeException("unsupported field type.");
        }
    }

    private final int zza(T t, byte[] bArr, int i, int i2, int i3, int i4, int i5, int i6, long j, int i7, long j2, zzgo zzgo) throws IOException {
        int i8;
        int i9;
        int i10;
        int i11;
        T t2 = t;
        byte[] bArr2 = bArr;
        int i12 = i;
        int i13 = i2;
        int i14 = i3;
        int i15 = i5;
        int i16 = i6;
        long j3 = j2;
        zzgo zzgo2 = zzgo;
        Unsafe unsafe = zzb;
        zzik zzik = (zzik) unsafe.getObject(t2, j3);
        if (!zzik.zza()) {
            int size = zzik.size();
            zzik = zzik.zza(size == 0 ? 10 : size << 1);
            unsafe.putObject(t2, j3, zzik);
        }
        switch (i7) {
            case 18:
            case 35:
                if (i15 == 2) {
                    zzhn zzhn = (zzhn) zzik;
                    int zza2 = zzgp.zza(bArr2, i12, zzgo2);
                    int i17 = zzgo2.zza + zza2;
                    while (zza2 < i17) {
                        zzhn.zza(zzgp.zzc(bArr2, zza2));
                        zza2 += 8;
                    }
                    if (zza2 == i17) {
                        return zza2;
                    }
                    throw zzij.zza();
                } else if (i15 == 1) {
                    zzhn zzhn2 = (zzhn) zzik;
                    zzhn2.zza(zzgp.zzc(bArr, i));
                    int i18 = i12 + 8;
                    while (i18 < i13) {
                        int zza3 = zzgp.zza(bArr2, i18, zzgo2);
                        if (i14 != zzgo2.zza) {
                            return i18;
                        }
                        zzhn2.zza(zzgp.zzc(bArr2, zza3));
                        i18 = zza3 + 8;
                    }
                    return i18;
                }
                break;
            case 19:
            case 36:
                if (i15 == 2) {
                    zzhx zzhx = (zzhx) zzik;
                    int zza4 = zzgp.zza(bArr2, i12, zzgo2);
                    int i19 = zzgo2.zza + zza4;
                    while (zza4 < i19) {
                        zzhx.zza(zzgp.zzd(bArr2, zza4));
                        zza4 += 4;
                    }
                    if (zza4 == i19) {
                        return zza4;
                    }
                    throw zzij.zza();
                } else if (i15 == 5) {
                    zzhx zzhx2 = (zzhx) zzik;
                    zzhx2.zza(zzgp.zzd(bArr, i));
                    int i20 = i12 + 4;
                    while (i20 < i13) {
                        int zza5 = zzgp.zza(bArr2, i20, zzgo2);
                        if (i14 != zzgo2.zza) {
                            return i20;
                        }
                        zzhx2.zza(zzgp.zzd(bArr2, zza5));
                        i20 = zza5 + 4;
                    }
                    return i20;
                }
                break;
            case 20:
            case 21:
            case 37:
            case 38:
                if (i15 == 2) {
                    zzix zzix = (zzix) zzik;
                    int zza6 = zzgp.zza(bArr2, i12, zzgo2);
                    int i21 = zzgo2.zza + zza6;
                    while (zza6 < i21) {
                        zza6 = zzgp.zzb(bArr2, zza6, zzgo2);
                        zzix.zza(zzgo2.zzb);
                    }
                    if (zza6 == i21) {
                        return zza6;
                    }
                    throw zzij.zza();
                } else if (i15 == 0) {
                    zzix zzix2 = (zzix) zzik;
                    int zzb2 = zzgp.zzb(bArr2, i12, zzgo2);
                    zzix2.zza(zzgo2.zzb);
                    while (zzb2 < i13) {
                        int zza7 = zzgp.zza(bArr2, zzb2, zzgo2);
                        if (i14 != zzgo2.zza) {
                            return zzb2;
                        }
                        zzb2 = zzgp.zzb(bArr2, zza7, zzgo2);
                        zzix2.zza(zzgo2.zzb);
                    }
                    return zzb2;
                }
                break;
            case 22:
            case 29:
            case 39:
            case 43:
                if (i15 == 2) {
                    return zzgp.zza(bArr2, i12, (zzik<?>) zzik, zzgo2);
                }
                if (i15 == 0) {
                    return zzgp.zza(i3, bArr, i, i2, (zzik<?>) zzik, zzgo);
                }
                break;
            case 23:
            case 32:
            case 40:
            case 46:
                if (i15 == 2) {
                    zzix zzix3 = (zzix) zzik;
                    int zza8 = zzgp.zza(bArr2, i12, zzgo2);
                    int i22 = zzgo2.zza + zza8;
                    while (zza8 < i22) {
                        zzix3.zza(zzgp.zzb(bArr2, zza8));
                        zza8 += 8;
                    }
                    if (zza8 == i22) {
                        return zza8;
                    }
                    throw zzij.zza();
                } else if (i15 == 1) {
                    zzix zzix4 = (zzix) zzik;
                    zzix4.zza(zzgp.zzb(bArr, i));
                    int i23 = i12 + 8;
                    while (i23 < i13) {
                        int zza9 = zzgp.zza(bArr2, i23, zzgo2);
                        if (i14 != zzgo2.zza) {
                            return i23;
                        }
                        zzix4.zza(zzgp.zzb(bArr2, zza9));
                        i23 = zza9 + 8;
                    }
                    return i23;
                }
                break;
            case 24:
            case 31:
            case 41:
            case 45:
                if (i15 == 2) {
                    zzic zzic = (zzic) zzik;
                    int zza10 = zzgp.zza(bArr2, i12, zzgo2);
                    int i24 = zzgo2.zza + zza10;
                    while (zza10 < i24) {
                        zzic.zzd(zzgp.zza(bArr2, zza10));
                        zza10 += 4;
                    }
                    if (zza10 == i24) {
                        return zza10;
                    }
                    throw zzij.zza();
                } else if (i15 == 5) {
                    zzic zzic2 = (zzic) zzik;
                    zzic2.zzd(zzgp.zza(bArr, i));
                    int i25 = i12 + 4;
                    while (i25 < i13) {
                        int zza11 = zzgp.zza(bArr2, i25, zzgo2);
                        if (i14 != zzgo2.zza) {
                            return i25;
                        }
                        zzic2.zzd(zzgp.zza(bArr2, zza11));
                        i25 = zza11 + 4;
                    }
                    return i25;
                }
                break;
            case 25:
            case 42:
                if (i15 == 2) {
                    zzgr zzgr = (zzgr) zzik;
                    int zza12 = zzgp.zza(bArr2, i12, zzgo2);
                    int i26 = zzgo2.zza + zza12;
                    while (zza12 < i26) {
                        zza12 = zzgp.zzb(bArr2, zza12, zzgo2);
                        zzgr.zza(zzgo2.zzb != 0);
                    }
                    if (zza12 == i26) {
                        return zza12;
                    }
                    throw zzij.zza();
                } else if (i15 == 0) {
                    zzgr zzgr2 = (zzgr) zzik;
                    int zzb3 = zzgp.zzb(bArr2, i12, zzgo2);
                    zzgr2.zza(zzgo2.zzb != 0);
                    while (zzb3 < i13) {
                        int zza13 = zzgp.zza(bArr2, zzb3, zzgo2);
                        if (i14 != zzgo2.zza) {
                            return zzb3;
                        }
                        zzb3 = zzgp.zzb(bArr2, zza13, zzgo2);
                        zzgr2.zza(zzgo2.zzb != 0);
                    }
                    return zzb3;
                }
                break;
            case 26:
                if (i15 == 2) {
                    if ((j & 536870912) == 0) {
                        int zza14 = zzgp.zza(bArr2, i12, zzgo2);
                        int i27 = zzgo2.zza;
                        if (i27 >= 0) {
                            if (i27 == 0) {
                                zzik.add("");
                            } else {
                                zzik.add(new String(bArr2, zza14, i27, zzie.zza));
                                zza14 += i27;
                            }
                            while (i9 < i13) {
                                int zza15 = zzgp.zza(bArr2, i9, zzgo2);
                                if (i14 != zzgo2.zza) {
                                    return i9;
                                }
                                i9 = zzgp.zza(bArr2, zza15, zzgo2);
                                int i28 = zzgo2.zza;
                                if (i28 < 0) {
                                    throw zzij.zzb();
                                } else if (i28 == 0) {
                                    zzik.add("");
                                } else {
                                    zzik.add(new String(bArr2, i9, i28, zzie.zza));
                                    i9 += i28;
                                }
                            }
                            return i9;
                        }
                        throw zzij.zzb();
                    }
                    int zza16 = zzgp.zza(bArr2, i12, zzgo2);
                    int i29 = zzgo2.zza;
                    if (i29 >= 0) {
                        if (i29 == 0) {
                            zzik.add("");
                        } else {
                            int i30 = zza16 + i29;
                            if (zzlc.zza(bArr2, zza16, i30)) {
                                zzik.add(new String(bArr2, zza16, i29, zzie.zza));
                                zza16 = i30;
                            } else {
                                throw zzij.zzh();
                            }
                        }
                        while (i8 < i13) {
                            int zza17 = zzgp.zza(bArr2, i8, zzgo2);
                            if (i14 != zzgo2.zza) {
                                return i8;
                            }
                            i8 = zzgp.zza(bArr2, zza17, zzgo2);
                            int i31 = zzgo2.zza;
                            if (i31 < 0) {
                                throw zzij.zzb();
                            } else if (i31 == 0) {
                                zzik.add("");
                            } else {
                                int i32 = i8 + i31;
                                if (zzlc.zza(bArr2, i8, i32)) {
                                    zzik.add(new String(bArr2, i8, i31, zzie.zza));
                                    i8 = i32;
                                } else {
                                    throw zzij.zzh();
                                }
                            }
                        }
                        return i8;
                    }
                    throw zzij.zzb();
                }
                break;
            case 27:
                if (i15 == 2) {
                    return zzgp.zza(zza(i16), i3, bArr, i, i2, zzik, zzgo);
                }
                break;
            case 28:
                if (i15 == 2) {
                    int zza18 = zzgp.zza(bArr2, i12, zzgo2);
                    int i33 = zzgo2.zza;
                    if (i33 < 0) {
                        throw zzij.zzb();
                    } else if (i33 <= bArr2.length - zza18) {
                        if (i33 == 0) {
                            zzik.add(zzgt.zza);
                        } else {
                            zzik.add(zzgt.zza(bArr2, zza18, i33));
                            zza18 += i33;
                        }
                        while (i10 < i13) {
                            int zza19 = zzgp.zza(bArr2, i10, zzgo2);
                            if (i14 != zzgo2.zza) {
                                return i10;
                            }
                            i10 = zzgp.zza(bArr2, zza19, zzgo2);
                            int i34 = zzgo2.zza;
                            if (i34 < 0) {
                                throw zzij.zzb();
                            } else if (i34 > bArr2.length - i10) {
                                throw zzij.zza();
                            } else if (i34 == 0) {
                                zzik.add(zzgt.zza);
                            } else {
                                zzik.add(zzgt.zza(bArr2, i10, i34));
                                i10 += i34;
                            }
                        }
                        return i10;
                    } else {
                        throw zzij.zza();
                    }
                }
                break;
            case 30:
            case 44:
                if (i15 == 2) {
                    i11 = zzgp.zza(bArr2, i12, (zzik<?>) zzik, zzgo2);
                } else if (i15 == 0) {
                    i11 = zzgp.zza(i3, bArr, i, i2, (zzik<?>) zzik, zzgo);
                }
                zzib zzib = (zzib) t2;
                zzkt zzkt = zzib.zzb;
                if (zzkt == zzkt.zza()) {
                    zzkt = null;
                }
                zzkt zzkt2 = (zzkt) zzke.zza(i4, zzik, zzc(i16), zzkt, this.zzq);
                if (zzkt2 != null) {
                    zzib.zzb = zzkt2;
                }
                return i11;
            case 33:
            case 47:
                if (i15 == 2) {
                    zzic zzic3 = (zzic) zzik;
                    int zza20 = zzgp.zza(bArr2, i12, zzgo2);
                    int i35 = zzgo2.zza + zza20;
                    while (zza20 < i35) {
                        zza20 = zzgp.zza(bArr2, zza20, zzgo2);
                        zzic3.zzd(zzhf.zze(zzgo2.zza));
                    }
                    if (zza20 == i35) {
                        return zza20;
                    }
                    throw zzij.zza();
                } else if (i15 == 0) {
                    zzic zzic4 = (zzic) zzik;
                    int zza21 = zzgp.zza(bArr2, i12, zzgo2);
                    zzic4.zzd(zzhf.zze(zzgo2.zza));
                    while (zza21 < i13) {
                        int zza22 = zzgp.zza(bArr2, zza21, zzgo2);
                        if (i14 != zzgo2.zza) {
                            return zza21;
                        }
                        zza21 = zzgp.zza(bArr2, zza22, zzgo2);
                        zzic4.zzd(zzhf.zze(zzgo2.zza));
                    }
                    return zza21;
                }
                break;
            case 34:
            case 48:
                if (i15 == 2) {
                    zzix zzix5 = (zzix) zzik;
                    int zza23 = zzgp.zza(bArr2, i12, zzgo2);
                    int i36 = zzgo2.zza + zza23;
                    while (zza23 < i36) {
                        zza23 = zzgp.zzb(bArr2, zza23, zzgo2);
                        zzix5.zza(zzhf.zza(zzgo2.zzb));
                    }
                    if (zza23 == i36) {
                        return zza23;
                    }
                    throw zzij.zza();
                } else if (i15 == 0) {
                    zzix zzix6 = (zzix) zzik;
                    int zzb4 = zzgp.zzb(bArr2, i12, zzgo2);
                    zzix6.zza(zzhf.zza(zzgo2.zzb));
                    while (zzb4 < i13) {
                        int zza24 = zzgp.zza(bArr2, zzb4, zzgo2);
                        if (i14 != zzgo2.zza) {
                            return zzb4;
                        }
                        zzb4 = zzgp.zzb(bArr2, zza24, zzgo2);
                        zzix6.zza(zzhf.zza(zzgo2.zzb));
                    }
                    return zzb4;
                }
                break;
            case 49:
                if (i15 == 3) {
                    zzkc zza25 = zza(i16);
                    int i37 = (i14 & -8) | 4;
                    int zza26 = zzgp.zza(zza25, bArr, i, i2, i37, zzgo);
                    zzik.add(zzgo2.zzc);
                    while (zza26 < i13) {
                        int zza27 = zzgp.zza(bArr2, zza26, zzgo2);
                        if (i14 != zzgo2.zza) {
                            return zza26;
                        }
                        zza26 = zzgp.zza(zza25, bArr, zza27, i2, i37, zzgo);
                        zzik.add(zzgo2.zzc);
                    }
                    return zza26;
                }
                break;
        }
        return i12;
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r10v4, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r10v11, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r10v12, resolved type: byte} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private final <K, V> int zza(T r8, byte[] r9, int r10, int r11, int r12, long r13, com.google.android.gms.internal.measurement.zzgo r15) throws java.io.IOException {
        /*
            r7 = this;
            sun.misc.Unsafe r0 = zzb
            java.lang.Object r12 = r7.zzb((int) r12)
            java.lang.Object r1 = r0.getObject(r8, r13)
            com.google.android.gms.internal.measurement.zzjg r2 = r7.zzs
            boolean r2 = r2.zzd(r1)
            if (r2 == 0) goto L_0x0022
            com.google.android.gms.internal.measurement.zzjg r2 = r7.zzs
            java.lang.Object r2 = r2.zzf(r12)
            com.google.android.gms.internal.measurement.zzjg r3 = r7.zzs
            r3.zza(r2, r1)
            r0.putObject(r8, r13, r2)
            r1 = r2
        L_0x0022:
            com.google.android.gms.internal.measurement.zzjg r8 = r7.zzs
            com.google.android.gms.internal.measurement.zzje r8 = r8.zzb(r12)
            com.google.android.gms.internal.measurement.zzjg r12 = r7.zzs
            java.util.Map r12 = r12.zza(r1)
            int r10 = com.google.android.gms.internal.measurement.zzgp.zza(r9, r10, r15)
            int r13 = r15.zza
            if (r13 < 0) goto L_0x009c
            int r14 = r11 - r10
            if (r13 > r14) goto L_0x009c
            int r13 = r13 + r10
            K r14 = r8.zzb
            V r0 = r8.zzd
        L_0x0040:
            if (r10 >= r13) goto L_0x0090
            int r1 = r10 + 1
            byte r10 = r9[r10]
            if (r10 >= 0) goto L_0x0050
            int r1 = com.google.android.gms.internal.measurement.zzgp.zza((int) r10, (byte[]) r9, (int) r1, (com.google.android.gms.internal.measurement.zzgo) r15)
            int r10 = r15.zza
            r2 = r1
            goto L_0x0051
        L_0x0050:
            r2 = r1
        L_0x0051:
            int r1 = r10 >>> 3
            r3 = r10 & 7
            r4 = 1
            if (r1 == r4) goto L_0x0076
            r4 = 2
            if (r1 == r4) goto L_0x005c
            goto L_0x008b
        L_0x005c:
            com.google.android.gms.internal.measurement.zzlh r1 = r8.zzc
            int r1 = r1.zzb()
            if (r3 != r1) goto L_0x008b
            com.google.android.gms.internal.measurement.zzlh r4 = r8.zzc
            V r10 = r8.zzd
            java.lang.Class r5 = r10.getClass()
            r1 = r9
            r3 = r11
            r6 = r15
            int r10 = zza((byte[]) r1, (int) r2, (int) r3, (com.google.android.gms.internal.measurement.zzlh) r4, (java.lang.Class<?>) r5, (com.google.android.gms.internal.measurement.zzgo) r6)
            java.lang.Object r0 = r15.zzc
            goto L_0x0040
        L_0x0076:
            com.google.android.gms.internal.measurement.zzlh r1 = r8.zza
            int r1 = r1.zzb()
            if (r3 != r1) goto L_0x008b
            com.google.android.gms.internal.measurement.zzlh r4 = r8.zza
            r5 = 0
            r1 = r9
            r3 = r11
            r6 = r15
            int r10 = zza((byte[]) r1, (int) r2, (int) r3, (com.google.android.gms.internal.measurement.zzlh) r4, (java.lang.Class<?>) r5, (com.google.android.gms.internal.measurement.zzgo) r6)
            java.lang.Object r14 = r15.zzc
            goto L_0x0040
        L_0x008b:
            int r10 = com.google.android.gms.internal.measurement.zzgp.zza((int) r10, (byte[]) r9, (int) r2, (int) r11, (com.google.android.gms.internal.measurement.zzgo) r15)
            goto L_0x0040
        L_0x0090:
            if (r10 != r13) goto L_0x0097
            r12.put(r14, r0)
            return r13
        L_0x0097:
            com.google.android.gms.internal.measurement.zzij r8 = com.google.android.gms.internal.measurement.zzij.zzg()
            throw r8
        L_0x009c:
            com.google.android.gms.internal.measurement.zzij r8 = com.google.android.gms.internal.measurement.zzij.zza()
            throw r8
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.measurement.zzjn.zza(java.lang.Object, byte[], int, int, int, long, com.google.android.gms.internal.measurement.zzgo):int");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:65:0x01a4, code lost:
        r12.putInt(r1, r13, r8);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:67:?, code lost:
        return r4;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:71:?, code lost:
        return r2;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private final int zza(T r17, byte[] r18, int r19, int r20, int r21, int r22, int r23, int r24, int r25, long r26, int r28, com.google.android.gms.internal.measurement.zzgo r29) throws java.io.IOException {
        /*
            r16 = this;
            r0 = r16
            r1 = r17
            r3 = r18
            r4 = r19
            r2 = r21
            r8 = r22
            r5 = r23
            r9 = r26
            r6 = r28
            r11 = r29
            sun.misc.Unsafe r12 = zzb
            int[] r7 = r0.zzc
            int r13 = r6 + 2
            r7 = r7[r13]
            r13 = 1048575(0xfffff, float:1.469367E-39)
            r7 = r7 & r13
            long r13 = (long) r7
            r7 = 5
            r15 = 2
            switch(r25) {
                case 51: goto L_0x0193;
                case 52: goto L_0x0183;
                case 53: goto L_0x0173;
                case 54: goto L_0x0173;
                case 55: goto L_0x0163;
                case 56: goto L_0x0152;
                case 57: goto L_0x0142;
                case 58: goto L_0x0129;
                case 59: goto L_0x00f5;
                case 60: goto L_0x00c6;
                case 61: goto L_0x00b9;
                case 62: goto L_0x0163;
                case 63: goto L_0x008b;
                case 64: goto L_0x0142;
                case 65: goto L_0x0152;
                case 66: goto L_0x0076;
                case 67: goto L_0x0061;
                case 68: goto L_0x0028;
                default: goto L_0x0026;
            }
        L_0x0026:
            goto L_0x01a8
        L_0x0028:
            r7 = 3
            if (r5 != r7) goto L_0x01a8
            r2 = r2 & -8
            r7 = r2 | 4
            com.google.android.gms.internal.measurement.zzkc r2 = r0.zza((int) r6)
            r3 = r18
            r4 = r19
            r5 = r20
            r6 = r7
            r7 = r29
            int r2 = com.google.android.gms.internal.measurement.zzgp.zza((com.google.android.gms.internal.measurement.zzkc) r2, (byte[]) r3, (int) r4, (int) r5, (int) r6, (com.google.android.gms.internal.measurement.zzgo) r7)
            int r3 = r12.getInt(r1, r13)
            if (r3 != r8) goto L_0x004c
            java.lang.Object r15 = r12.getObject(r1, r9)
            goto L_0x004d
        L_0x004c:
            r15 = 0
        L_0x004d:
            if (r15 != 0) goto L_0x0056
            java.lang.Object r3 = r11.zzc
            r12.putObject(r1, r9, r3)
            goto L_0x01a4
        L_0x0056:
            java.lang.Object r3 = r11.zzc
            java.lang.Object r3 = com.google.android.gms.internal.measurement.zzie.zza((java.lang.Object) r15, (java.lang.Object) r3)
            r12.putObject(r1, r9, r3)
            goto L_0x01a4
        L_0x0061:
            if (r5 != 0) goto L_0x01a8
            int r2 = com.google.android.gms.internal.measurement.zzgp.zzb(r3, r4, r11)
            long r3 = r11.zzb
            long r3 = com.google.android.gms.internal.measurement.zzhf.zza((long) r3)
            java.lang.Long r3 = java.lang.Long.valueOf(r3)
            r12.putObject(r1, r9, r3)
            goto L_0x01a4
        L_0x0076:
            if (r5 != 0) goto L_0x01a8
            int r2 = com.google.android.gms.internal.measurement.zzgp.zza(r3, r4, r11)
            int r3 = r11.zza
            int r3 = com.google.android.gms.internal.measurement.zzhf.zze(r3)
            java.lang.Integer r3 = java.lang.Integer.valueOf(r3)
            r12.putObject(r1, r9, r3)
            goto L_0x01a4
        L_0x008b:
            if (r5 != 0) goto L_0x01a8
            int r3 = com.google.android.gms.internal.measurement.zzgp.zza(r3, r4, r11)
            int r4 = r11.zza
            com.google.android.gms.internal.measurement.zzif r5 = r0.zzc((int) r6)
            if (r5 == 0) goto L_0x00af
            boolean r5 = r5.zza(r4)
            if (r5 == 0) goto L_0x00a0
            goto L_0x00af
        L_0x00a0:
            com.google.android.gms.internal.measurement.zzkt r1 = zze((java.lang.Object) r17)
            long r4 = (long) r4
            java.lang.Long r4 = java.lang.Long.valueOf(r4)
            r1.zza((int) r2, (java.lang.Object) r4)
            r2 = r3
            goto L_0x01a9
        L_0x00af:
            java.lang.Integer r2 = java.lang.Integer.valueOf(r4)
            r12.putObject(r1, r9, r2)
            r2 = r3
            goto L_0x01a4
        L_0x00b9:
            if (r5 != r15) goto L_0x01a8
            int r2 = com.google.android.gms.internal.measurement.zzgp.zze(r3, r4, r11)
            java.lang.Object r3 = r11.zzc
            r12.putObject(r1, r9, r3)
            goto L_0x01a4
        L_0x00c6:
            if (r5 != r15) goto L_0x01a8
            com.google.android.gms.internal.measurement.zzkc r2 = r0.zza((int) r6)
            r5 = r20
            int r2 = com.google.android.gms.internal.measurement.zzgp.zza((com.google.android.gms.internal.measurement.zzkc) r2, (byte[]) r3, (int) r4, (int) r5, (com.google.android.gms.internal.measurement.zzgo) r11)
            int r3 = r12.getInt(r1, r13)
            if (r3 != r8) goto L_0x00de
            java.lang.Object r15 = r12.getObject(r1, r9)
            goto L_0x00df
        L_0x00de:
            r15 = 0
        L_0x00df:
            if (r15 != 0) goto L_0x00e7
            java.lang.Object r3 = r11.zzc
            r12.putObject(r1, r9, r3)
            goto L_0x00f0
        L_0x00e7:
            java.lang.Object r3 = r11.zzc
            java.lang.Object r3 = com.google.android.gms.internal.measurement.zzie.zza((java.lang.Object) r15, (java.lang.Object) r3)
            r12.putObject(r1, r9, r3)
        L_0x00f0:
            r12.putInt(r1, r13, r8)
            goto L_0x01a9
        L_0x00f5:
            if (r5 != r15) goto L_0x01a8
            int r2 = com.google.android.gms.internal.measurement.zzgp.zza(r3, r4, r11)
            int r4 = r11.zza
            if (r4 != 0) goto L_0x0105
            java.lang.String r3 = ""
            r12.putObject(r1, r9, r3)
            goto L_0x0124
        L_0x0105:
            r5 = 536870912(0x20000000, float:1.0842022E-19)
            r5 = r24 & r5
            if (r5 == 0) goto L_0x0119
            int r5 = r2 + r4
            boolean r5 = com.google.android.gms.internal.measurement.zzlc.zza((byte[]) r3, (int) r2, (int) r5)
            if (r5 == 0) goto L_0x0114
            goto L_0x0119
        L_0x0114:
            com.google.android.gms.internal.measurement.zzij r1 = com.google.android.gms.internal.measurement.zzij.zzh()
            throw r1
        L_0x0119:
            java.lang.String r5 = new java.lang.String
            java.nio.charset.Charset r6 = com.google.android.gms.internal.measurement.zzie.zza
            r5.<init>(r3, r2, r4, r6)
            r12.putObject(r1, r9, r5)
            int r2 = r2 + r4
        L_0x0124:
            r12.putInt(r1, r13, r8)
            goto L_0x01a9
        L_0x0129:
            if (r5 != 0) goto L_0x01a8
            int r2 = com.google.android.gms.internal.measurement.zzgp.zzb(r3, r4, r11)
            long r3 = r11.zzb
            r5 = 0
            int r3 = (r3 > r5 ? 1 : (r3 == r5 ? 0 : -1))
            if (r3 == 0) goto L_0x0139
            r15 = 1
            goto L_0x013a
        L_0x0139:
            r15 = 0
        L_0x013a:
            java.lang.Boolean r3 = java.lang.Boolean.valueOf(r15)
            r12.putObject(r1, r9, r3)
            goto L_0x01a4
        L_0x0142:
            if (r5 != r7) goto L_0x01a8
            int r2 = com.google.android.gms.internal.measurement.zzgp.zza(r18, r19)
            java.lang.Integer r2 = java.lang.Integer.valueOf(r2)
            r12.putObject(r1, r9, r2)
            int r2 = r4 + 4
            goto L_0x01a4
        L_0x0152:
            r2 = 1
            if (r5 != r2) goto L_0x01a8
            long r2 = com.google.android.gms.internal.measurement.zzgp.zzb(r18, r19)
            java.lang.Long r2 = java.lang.Long.valueOf(r2)
            r12.putObject(r1, r9, r2)
            int r2 = r4 + 8
            goto L_0x01a4
        L_0x0163:
            if (r5 != 0) goto L_0x01a8
            int r2 = com.google.android.gms.internal.measurement.zzgp.zza(r3, r4, r11)
            int r3 = r11.zza
            java.lang.Integer r3 = java.lang.Integer.valueOf(r3)
            r12.putObject(r1, r9, r3)
            goto L_0x01a4
        L_0x0173:
            if (r5 != 0) goto L_0x01a8
            int r2 = com.google.android.gms.internal.measurement.zzgp.zzb(r3, r4, r11)
            long r3 = r11.zzb
            java.lang.Long r3 = java.lang.Long.valueOf(r3)
            r12.putObject(r1, r9, r3)
            goto L_0x01a4
        L_0x0183:
            if (r5 != r7) goto L_0x01a8
            float r2 = com.google.android.gms.internal.measurement.zzgp.zzd(r18, r19)
            java.lang.Float r2 = java.lang.Float.valueOf(r2)
            r12.putObject(r1, r9, r2)
            int r2 = r4 + 4
            goto L_0x01a4
        L_0x0193:
            r2 = 1
            if (r5 != r2) goto L_0x01a8
            double r2 = com.google.android.gms.internal.measurement.zzgp.zzc(r18, r19)
            java.lang.Double r2 = java.lang.Double.valueOf(r2)
            r12.putObject(r1, r9, r2)
            int r2 = r4 + 8
        L_0x01a4:
            r12.putInt(r1, r13, r8)
            goto L_0x01a9
        L_0x01a8:
            r2 = r4
        L_0x01a9:
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.measurement.zzjn.zza(java.lang.Object, byte[], int, int, int, int, int, int, int, long, int, com.google.android.gms.internal.measurement.zzgo):int");
    }

    private final zzkc zza(int i) {
        int i2 = (i / 3) << 1;
        zzkc zzkc = (zzkc) this.zzd[i2];
        if (zzkc != null) {
            return zzkc;
        }
        zzkc zza2 = zzjy.zza().zza((Class) this.zzd[i2 + 1]);
        this.zzd[i2] = zza2;
        return zza2;
    }

    private final Object zzb(int i) {
        return this.zzd[(i / 3) << 1];
    }

    private final zzif zzc(int i) {
        return (zzif) this.zzd[((i / 3) << 1) + 1];
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r11v0, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r11v1, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v1, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r7v0, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r7v1, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v2, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v4, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r11v2, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r19v0, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r7v3, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r8v1, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v9, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v7, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r11v4, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v18, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v9, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r11v8, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v10, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r19v4, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r8v3, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r19v6, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r19v7, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r5v10, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r7v4, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r8v5, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v12, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r11v11, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r11v12, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v14, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r5v15, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r19v8, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r11v13, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v15, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r19v9, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v26, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r11v14, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v16, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r8v10, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r19v10, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r8v11, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v17, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r11v17, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r8v12, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v18, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r11v19, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r8v13, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v19, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r11v21, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r8v14, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v20, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r11v23, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r8v15, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v21, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r11v25, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r8v16, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v22, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r11v27, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r8v17, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v24, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r11v29, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r8v18, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v25, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r11v31, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r8v19, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v26, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r11v33, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r8v20, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v28, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r11v35, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r8v21, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v29, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r11v37, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v31, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r11v38, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r8v22, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v32, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r11v40, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r5v32, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r8v23, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r8v24, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v33, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r11v42, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r7v15, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r8v25, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r7v16, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v35, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r11v45, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r8v26, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r19v27, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v39, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v40, resolved type: byte} */
    /* access modifiers changed from: package-private */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final int zza(T r28, byte[] r29, int r30, int r31, int r32, com.google.android.gms.internal.measurement.zzgo r33) throws java.io.IOException {
        /*
            r27 = this;
            r15 = r27
            r14 = r28
            r12 = r29
            r13 = r31
            r11 = r32
            r9 = r33
            sun.misc.Unsafe r10 = zzb
            r16 = 0
            r0 = r30
            r2 = r16
            r3 = r2
            r5 = r3
            r1 = -1
            r6 = 1048575(0xfffff, float:1.469367E-39)
        L_0x001e:
            if (r0 >= r13) goto L_0x0586
            int r3 = r0 + 1
            byte r0 = r12[r0]
            if (r0 >= 0) goto L_0x002f
            int r0 = com.google.android.gms.internal.measurement.zzgp.zza((int) r0, (byte[]) r12, (int) r3, (com.google.android.gms.internal.measurement.zzgo) r9)
            int r3 = r9.zza
            r4 = r3
            r3 = r0
            goto L_0x0030
        L_0x002f:
            r4 = r0
        L_0x0030:
            int r0 = r4 >>> 3
            r7 = r4 & 7
            r8 = 3
            if (r0 <= r1) goto L_0x003e
            int r2 = r2 / r8
            int r1 = r15.zza((int) r0, (int) r2)
            r2 = r1
            goto L_0x0043
        L_0x003e:
            int r1 = r15.zzg(r0)
            r2 = r1
        L_0x0043:
            r1 = -1
            if (r2 != r1) goto L_0x0056
            r30 = r0
            r18 = r1
            r2 = r3
            r19 = r4
            r22 = r5
            r26 = r10
            r17 = r16
            goto L_0x04d5
        L_0x0056:
            int[] r1 = r15.zzc
            int r19 = r2 + 1
            r8 = r1[r19]
            r19 = 267386880(0xff00000, float:2.3665827E-29)
            r19 = r8 & r19
            int r11 = r19 >>> 20
            r19 = r4
            r17 = 1048575(0xfffff, float:1.469367E-39)
            r4 = r8 & r17
            long r12 = (long) r4
            r4 = 17
            r20 = r8
            if (r11 > r4) goto L_0x03af
            int r4 = r2 + 2
            r1 = r1[r4]
            int r4 = r1 >>> 20
            r8 = 1
            int r22 = r8 << r4
            r4 = 1048575(0xfffff, float:1.469367E-39)
            r1 = r1 & r4
            if (r1 == r6) goto L_0x0092
            if (r6 == r4) goto L_0x0089
            long r8 = (long) r6
            r10.putInt(r14, r8, r5)
        L_0x0089:
            long r5 = (long) r1
            int r5 = r10.getInt(r14, r5)
            r8 = r1
            r6 = r5
            goto L_0x0094
        L_0x0092:
            r8 = r6
            r6 = r5
        L_0x0094:
            r1 = 5
            switch(r11) {
                case 0: goto L_0x0377;
                case 1: goto L_0x034f;
                case 2: goto L_0x031e;
                case 3: goto L_0x031e;
                case 4: goto L_0x02f6;
                case 5: goto L_0x02c1;
                case 6: goto L_0x0298;
                case 7: goto L_0x0263;
                case 8: goto L_0x022e;
                case 9: goto L_0x01e8;
                case 10: goto L_0x01bf;
                case 11: goto L_0x02f6;
                case 12: goto L_0x0171;
                case 13: goto L_0x0298;
                case 14: goto L_0x02c1;
                case 15: goto L_0x0145;
                case 16: goto L_0x0105;
                case 17: goto L_0x00a8;
                default: goto L_0x0098;
            }
        L_0x0098:
            r12 = r29
            r13 = r33
            r9 = r0
            r11 = r2
            r30 = r8
            r8 = r19
            r18 = -1
            r19 = r4
            goto L_0x03a0
        L_0x00a8:
            r1 = 3
            if (r7 != r1) goto L_0x00f3
            int r1 = r0 << 3
            r5 = r1 | 4
            com.google.android.gms.internal.measurement.zzkc r1 = r15.zza((int) r2)
            r9 = r0
            r0 = r1
            r18 = -1
            r1 = r29
            r11 = r2
            r2 = r3
            r3 = r31
            r7 = r19
            r19 = r4
            r4 = r5
            r5 = r33
            int r0 = com.google.android.gms.internal.measurement.zzgp.zza((com.google.android.gms.internal.measurement.zzkc) r0, (byte[]) r1, (int) r2, (int) r3, (int) r4, (com.google.android.gms.internal.measurement.zzgo) r5)
            r1 = r6 & r22
            if (r1 != 0) goto L_0x00d5
            r4 = r33
            java.lang.Object r1 = r4.zzc
            r10.putObject(r14, r12, r1)
            goto L_0x00e4
        L_0x00d5:
            r4 = r33
            java.lang.Object r1 = r10.getObject(r14, r12)
            java.lang.Object r2 = r4.zzc
            java.lang.Object r1 = com.google.android.gms.internal.measurement.zzie.zza((java.lang.Object) r1, (java.lang.Object) r2)
            r10.putObject(r14, r12, r1)
        L_0x00e4:
            r5 = r6 | r22
            r12 = r29
            r13 = r31
            r3 = r7
            r6 = r8
            r1 = r9
            r2 = r11
            r11 = r32
            r9 = r4
            goto L_0x001e
        L_0x00f3:
            r9 = r0
            r11 = r2
            r7 = r19
            r18 = -1
            r19 = r4
            r4 = r33
            r12 = r29
            r13 = r4
            r30 = r8
            r8 = r7
            goto L_0x03a0
        L_0x0105:
            r9 = r0
            r11 = r2
            r5 = r19
            r18 = -1
            r19 = r4
            r4 = r33
            if (r7 != 0) goto L_0x013d
            r1 = r12
            r12 = r29
            int r7 = com.google.android.gms.internal.measurement.zzgp.zzb(r12, r3, r4)
            r20 = r1
            long r0 = r4.zzb
            long r23 = com.google.android.gms.internal.measurement.zzhf.zza((long) r0)
            r0 = r10
            r2 = r20
            r1 = r28
            r13 = r4
            r30 = r8
            r8 = r5
            r4 = r23
            r0.putLong(r1, r2, r4)
            r5 = r6 | r22
            r6 = r30
            r0 = r7
            r3 = r8
            r1 = r9
            r2 = r11
            r9 = r13
            r13 = r31
            r11 = r32
            goto L_0x001e
        L_0x013d:
            r12 = r29
            r13 = r4
            r30 = r8
            r8 = r5
            goto L_0x03a0
        L_0x0145:
            r9 = r0
            r11 = r2
            r30 = r8
            r8 = r19
            r18 = -1
            r19 = r4
            r4 = r12
            r12 = r29
            r13 = r33
            if (r7 != 0) goto L_0x03a0
            int r0 = com.google.android.gms.internal.measurement.zzgp.zza(r12, r3, r13)
            int r1 = r13.zza
            int r1 = com.google.android.gms.internal.measurement.zzhf.zze(r1)
            r10.putInt(r14, r4, r1)
            r5 = r6 | r22
            r6 = r30
            r3 = r8
            r1 = r9
            r2 = r11
            r9 = r13
            r13 = r31
            r11 = r32
            goto L_0x001e
        L_0x0171:
            r9 = r0
            r11 = r2
            r30 = r8
            r8 = r19
            r18 = -1
            r19 = r4
            r4 = r12
            r12 = r29
            r13 = r33
            if (r7 != 0) goto L_0x03a0
            int r0 = com.google.android.gms.internal.measurement.zzgp.zza(r12, r3, r13)
            int r1 = r13.zza
            com.google.android.gms.internal.measurement.zzif r2 = r15.zzc((int) r11)
            if (r2 == 0) goto L_0x01ae
            boolean r2 = r2.zza(r1)
            if (r2 == 0) goto L_0x0195
            goto L_0x01ae
        L_0x0195:
            com.google.android.gms.internal.measurement.zzkt r2 = zze((java.lang.Object) r28)
            long r3 = (long) r1
            java.lang.Long r1 = java.lang.Long.valueOf(r3)
            r2.zza((int) r8, (java.lang.Object) r1)
            r5 = r6
            r3 = r8
            r1 = r9
            r2 = r11
            r9 = r13
            r6 = r30
            r13 = r31
            r11 = r32
            goto L_0x001e
        L_0x01ae:
            r10.putInt(r14, r4, r1)
            r5 = r6 | r22
            r6 = r30
            r3 = r8
            r1 = r9
            r2 = r11
            r9 = r13
            r13 = r31
            r11 = r32
            goto L_0x001e
        L_0x01bf:
            r9 = r0
            r11 = r2
            r30 = r8
            r8 = r19
            r18 = -1
            r19 = r4
            r4 = r12
            r12 = r29
            r13 = r33
            r0 = 2
            if (r7 != r0) goto L_0x03a0
            int r0 = com.google.android.gms.internal.measurement.zzgp.zze(r12, r3, r13)
            java.lang.Object r1 = r13.zzc
            r10.putObject(r14, r4, r1)
            r5 = r6 | r22
            r6 = r30
            r3 = r8
            r1 = r9
            r2 = r11
            r9 = r13
            r13 = r31
            r11 = r32
            goto L_0x001e
        L_0x01e8:
            r9 = r0
            r11 = r2
            r30 = r8
            r8 = r19
            r18 = -1
            r19 = r4
            r4 = r12
            r12 = r29
            r13 = r33
            r0 = 2
            if (r7 != r0) goto L_0x022a
            com.google.android.gms.internal.measurement.zzkc r0 = r15.zza((int) r11)
            r2 = r31
            int r0 = com.google.android.gms.internal.measurement.zzgp.zza((com.google.android.gms.internal.measurement.zzkc) r0, (byte[]) r12, (int) r3, (int) r2, (com.google.android.gms.internal.measurement.zzgo) r13)
            r1 = r6 & r22
            if (r1 != 0) goto L_0x020f
            java.lang.Object r1 = r13.zzc
            r10.putObject(r14, r4, r1)
            goto L_0x021d
        L_0x020f:
            java.lang.Object r1 = r10.getObject(r14, r4)
            java.lang.Object r3 = r13.zzc
            java.lang.Object r1 = com.google.android.gms.internal.measurement.zzie.zza((java.lang.Object) r1, (java.lang.Object) r3)
            r10.putObject(r14, r4, r1)
        L_0x021d:
            r5 = r6 | r22
            r6 = r30
            r3 = r8
            r1 = r9
            r9 = r13
            r13 = r2
            r2 = r11
            r11 = r32
            goto L_0x001e
        L_0x022a:
            r2 = r31
            goto L_0x03a0
        L_0x022e:
            r9 = r0
            r11 = r2
            r30 = r8
            r8 = r19
            r18 = -1
            r2 = r31
            r19 = r4
            r4 = r12
            r12 = r29
            r13 = r33
            r0 = 2
            if (r7 != r0) goto L_0x03a0
            r0 = 536870912(0x20000000, float:1.0842022E-19)
            r0 = r20 & r0
            if (r0 != 0) goto L_0x024d
            int r0 = com.google.android.gms.internal.measurement.zzgp.zzc(r12, r3, r13)
            goto L_0x0251
        L_0x024d:
            int r0 = com.google.android.gms.internal.measurement.zzgp.zzd(r12, r3, r13)
        L_0x0251:
            java.lang.Object r1 = r13.zzc
            r10.putObject(r14, r4, r1)
            r5 = r6 | r22
            r6 = r30
            r3 = r8
            r1 = r9
            r9 = r13
            r13 = r2
            r2 = r11
            r11 = r32
            goto L_0x001e
        L_0x0263:
            r9 = r0
            r11 = r2
            r30 = r8
            r8 = r19
            r18 = -1
            r2 = r31
            r19 = r4
            r4 = r12
            r12 = r29
            r13 = r33
            if (r7 != 0) goto L_0x03a0
            int r0 = com.google.android.gms.internal.measurement.zzgp.zzb(r12, r3, r13)
            r3 = r0
            long r0 = r13.zzb
            r20 = 0
            int r0 = (r0 > r20 ? 1 : (r0 == r20 ? 0 : -1))
            if (r0 == 0) goto L_0x0285
            r0 = 1
            goto L_0x0287
        L_0x0285:
            r0 = r16
        L_0x0287:
            com.google.android.gms.internal.measurement.zzla.zza((java.lang.Object) r14, (long) r4, (boolean) r0)
            r5 = r6 | r22
            r6 = r30
            r0 = r3
            r3 = r8
            r1 = r9
            r9 = r13
            r13 = r2
            r2 = r11
            r11 = r32
            goto L_0x001e
        L_0x0298:
            r9 = r0
            r11 = r2
            r30 = r8
            r8 = r19
            r18 = -1
            r2 = r31
            r19 = r4
            r4 = r12
            r12 = r29
            r13 = r33
            if (r7 != r1) goto L_0x03a0
            int r0 = com.google.android.gms.internal.measurement.zzgp.zza(r12, r3)
            r10.putInt(r14, r4, r0)
            int r0 = r3 + 4
            r5 = r6 | r22
            r6 = r30
            r3 = r8
            r1 = r9
            r9 = r13
            r13 = r2
            r2 = r11
            r11 = r32
            goto L_0x001e
        L_0x02c1:
            r9 = r0
            r11 = r2
            r30 = r8
            r8 = r19
            r18 = -1
            r2 = r31
            r19 = r4
            r4 = r12
            r12 = r29
            r13 = r33
            r0 = 1
            if (r7 != r0) goto L_0x02f3
            long r20 = com.google.android.gms.internal.measurement.zzgp.zzb(r12, r3)
            r0 = r10
            r1 = r28
            r7 = r3
            r2 = r4
            r4 = r20
            r0.putLong(r1, r2, r4)
            int r0 = r7 + 8
            r5 = r6 | r22
            r6 = r30
            r3 = r8
            r1 = r9
            r2 = r11
            r9 = r13
            r13 = r31
            r11 = r32
            goto L_0x001e
        L_0x02f3:
            r7 = r3
            goto L_0x03a0
        L_0x02f6:
            r9 = r0
            r11 = r2
            r30 = r8
            r8 = r19
            r18 = -1
            r19 = r4
            r4 = r12
            r12 = r29
            r13 = r33
            if (r7 != 0) goto L_0x03a0
            int r0 = com.google.android.gms.internal.measurement.zzgp.zza(r12, r3, r13)
            int r1 = r13.zza
            r10.putInt(r14, r4, r1)
            r5 = r6 | r22
            r6 = r30
            r3 = r8
            r1 = r9
            r2 = r11
            r9 = r13
            r13 = r31
            r11 = r32
            goto L_0x001e
        L_0x031e:
            r9 = r0
            r11 = r2
            r30 = r8
            r8 = r19
            r18 = -1
            r19 = r4
            r4 = r12
            r12 = r29
            r13 = r33
            if (r7 != 0) goto L_0x03a0
            int r7 = com.google.android.gms.internal.measurement.zzgp.zzb(r12, r3, r13)
            long r2 = r13.zzb
            r0 = r10
            r1 = r28
            r20 = r2
            r2 = r4
            r4 = r20
            r0.putLong(r1, r2, r4)
            r5 = r6 | r22
            r6 = r30
            r0 = r7
            r3 = r8
            r1 = r9
            r2 = r11
            r9 = r13
            r13 = r31
            r11 = r32
            goto L_0x001e
        L_0x034f:
            r9 = r0
            r11 = r2
            r30 = r8
            r8 = r19
            r18 = -1
            r19 = r4
            r4 = r12
            r12 = r29
            r13 = r33
            if (r7 != r1) goto L_0x03a0
            float r0 = com.google.android.gms.internal.measurement.zzgp.zzd(r12, r3)
            com.google.android.gms.internal.measurement.zzla.zza((java.lang.Object) r14, (long) r4, (float) r0)
            int r0 = r3 + 4
            r5 = r6 | r22
            r6 = r30
            r3 = r8
            r1 = r9
            r2 = r11
            r9 = r13
            r13 = r31
            r11 = r32
            goto L_0x001e
        L_0x0377:
            r9 = r0
            r11 = r2
            r30 = r8
            r8 = r19
            r18 = -1
            r19 = r4
            r4 = r12
            r12 = r29
            r13 = r33
            r0 = 1
            if (r7 != r0) goto L_0x03a0
            double r0 = com.google.android.gms.internal.measurement.zzgp.zzc(r12, r3)
            com.google.android.gms.internal.measurement.zzla.zza((java.lang.Object) r14, (long) r4, (double) r0)
            int r0 = r3 + 8
            r5 = r6 | r22
            r6 = r30
            r3 = r8
            r1 = r9
            r2 = r11
            r9 = r13
            r13 = r31
            r11 = r32
            goto L_0x001e
        L_0x03a0:
            r2 = r3
            r22 = r6
            r19 = r8
            r26 = r10
            r17 = r11
            r6 = r30
            r30 = r9
            goto L_0x04d5
        L_0x03af:
            r4 = r2
            r1 = r12
            r8 = r19
            r18 = -1
            r19 = 1048575(0xfffff, float:1.469367E-39)
            r12 = r29
            r13 = r9
            r9 = r0
            r0 = 27
            if (r11 != r0) goto L_0x041a
            r0 = 2
            if (r7 != r0) goto L_0x040b
            java.lang.Object r0 = r10.getObject(r14, r1)
            com.google.android.gms.internal.measurement.zzik r0 = (com.google.android.gms.internal.measurement.zzik) r0
            boolean r7 = r0.zza()
            if (r7 != 0) goto L_0x03e4
            int r7 = r0.size()
            if (r7 != 0) goto L_0x03d9
            r7 = 10
            goto L_0x03db
        L_0x03d9:
            int r7 = r7 << 1
        L_0x03db:
            com.google.android.gms.internal.measurement.zzik r0 = r0.zza(r7)
            r10.putObject(r14, r1, r0)
            r7 = r0
            goto L_0x03e5
        L_0x03e4:
            r7 = r0
        L_0x03e5:
            com.google.android.gms.internal.measurement.zzkc r0 = r15.zza((int) r4)
            r1 = r8
            r2 = r29
            r17 = r4
            r4 = r31
            r22 = r5
            r5 = r7
            r23 = r6
            r6 = r33
            int r0 = com.google.android.gms.internal.measurement.zzgp.zza(r0, r1, r2, r3, r4, r5, r6)
            r11 = r32
            r3 = r8
            r1 = r9
            r9 = r13
            r2 = r17
            r5 = r22
            r6 = r23
            r13 = r31
            goto L_0x001e
        L_0x040b:
            r17 = r4
            r22 = r5
            r23 = r6
            r15 = r3
            r19 = r8
            r30 = r9
            r26 = r10
            goto L_0x04b1
        L_0x041a:
            r17 = r4
            r22 = r5
            r23 = r6
            r0 = 49
            if (r11 > r0) goto L_0x046b
            r6 = r20
            long r5 = (long) r6
            r0 = r27
            r24 = r1
            r1 = r28
            r2 = r29
            r4 = r3
            r15 = r4
            r4 = r31
            r20 = r5
            r5 = r8
            r6 = r9
            r19 = r8
            r8 = r17
            r30 = r9
            r26 = r10
            r9 = r20
            r12 = r24
            r14 = r33
            int r0 = r0.zza(r1, (byte[]) r2, (int) r3, (int) r4, (int) r5, (int) r6, (int) r7, (int) r8, (long) r9, (int) r11, (long) r12, (com.google.android.gms.internal.measurement.zzgo) r14)
            if (r0 != r15) goto L_0x0451
            r2 = r0
            r6 = r23
            goto L_0x04d5
        L_0x0451:
            r15 = r27
            r14 = r28
            r12 = r29
            r1 = r30
            r13 = r31
            r11 = r32
            r9 = r33
            r2 = r17
            r3 = r19
            r5 = r22
            r6 = r23
            r10 = r26
            goto L_0x001e
        L_0x046b:
            r24 = r1
            r15 = r3
            r19 = r8
            r30 = r9
            r26 = r10
            r6 = r20
            r0 = 50
            if (r11 != r0) goto L_0x04b5
            r0 = 2
            if (r7 != r0) goto L_0x04b1
            r0 = r27
            r1 = r28
            r2 = r29
            r3 = r15
            r4 = r31
            r5 = r17
            r6 = r24
            r8 = r33
            int r0 = r0.zza(r1, (byte[]) r2, (int) r3, (int) r4, (int) r5, (long) r6, (com.google.android.gms.internal.measurement.zzgo) r8)
            if (r0 != r15) goto L_0x0497
            r2 = r0
            r6 = r23
            goto L_0x04d5
        L_0x0497:
            r15 = r27
            r14 = r28
            r12 = r29
            r1 = r30
            r13 = r31
            r11 = r32
            r9 = r33
            r2 = r17
            r3 = r19
            r5 = r22
            r6 = r23
            r10 = r26
            goto L_0x001e
        L_0x04b1:
            r2 = r15
            r6 = r23
            goto L_0x04d5
        L_0x04b5:
            r0 = r27
            r1 = r28
            r2 = r29
            r3 = r15
            r4 = r31
            r5 = r19
            r8 = r6
            r6 = r30
            r9 = r11
            r10 = r24
            r12 = r17
            r13 = r33
            int r0 = r0.zza(r1, (byte[]) r2, (int) r3, (int) r4, (int) r5, (int) r6, (int) r7, (int) r8, (int) r9, (long) r10, (int) r12, (com.google.android.gms.internal.measurement.zzgo) r13)
            if (r0 != r15) goto L_0x0566
            r2 = r0
            r6 = r23
        L_0x04d5:
            r7 = r32
            r8 = r19
            if (r8 != r7) goto L_0x04e8
            if (r7 != 0) goto L_0x04de
            goto L_0x04e8
        L_0x04de:
            r9 = r27
            r12 = r28
            r0 = r2
            r3 = r8
            r5 = r22
            goto L_0x058f
        L_0x04e8:
            r9 = r27
            boolean r0 = r9.zzh
            if (r0 == 0) goto L_0x053e
            r10 = r33
            com.google.android.gms.internal.measurement.zzho r0 = r10.zzd
            com.google.android.gms.internal.measurement.zzho r1 = com.google.android.gms.internal.measurement.zzho.zza()
            if (r0 == r1) goto L_0x0539
            com.google.android.gms.internal.measurement.zzjj r0 = r9.zzg
            com.google.android.gms.internal.measurement.zzho r1 = r10.zzd
            r11 = r30
            com.google.android.gms.internal.measurement.zzib$zzd r0 = r1.zza(r0, r11)
            if (r0 != 0) goto L_0x0528
            com.google.android.gms.internal.measurement.zzkt r4 = zze((java.lang.Object) r28)
            r0 = r8
            r1 = r29
            r3 = r31
            r5 = r33
            int r0 = com.google.android.gms.internal.measurement.zzgp.zza((int) r0, (byte[]) r1, (int) r2, (int) r3, (com.google.android.gms.internal.measurement.zzkt) r4, (com.google.android.gms.internal.measurement.zzgo) r5)
            r14 = r28
            r12 = r29
            r13 = r31
            r3 = r8
            r15 = r9
            r9 = r10
            r1 = r11
            r2 = r17
            r5 = r22
            r10 = r26
            r11 = r7
            goto L_0x001e
        L_0x0528:
            r12 = r28
            r0 = r12
            com.google.android.gms.internal.measurement.zzib$zzb r0 = (com.google.android.gms.internal.measurement.zzib.zzb) r0
            r0.zza()
            com.google.android.gms.internal.measurement.zzhr<com.google.android.gms.internal.measurement.zzib$zze> r0 = r0.zzc
            java.lang.NoSuchMethodError r0 = new java.lang.NoSuchMethodError
            r0.<init>()
            throw r0
        L_0x0539:
            r12 = r28
            r11 = r30
            goto L_0x0544
        L_0x053e:
            r12 = r28
            r11 = r30
            r10 = r33
        L_0x0544:
            com.google.android.gms.internal.measurement.zzkt r4 = zze((java.lang.Object) r28)
            r0 = r8
            r1 = r29
            r3 = r31
            r5 = r33
            int r0 = com.google.android.gms.internal.measurement.zzgp.zza((int) r0, (byte[]) r1, (int) r2, (int) r3, (com.google.android.gms.internal.measurement.zzkt) r4, (com.google.android.gms.internal.measurement.zzgo) r5)
            r13 = r31
            r3 = r8
            r15 = r9
            r9 = r10
            r1 = r11
            r14 = r12
            r2 = r17
            r5 = r22
            r10 = r26
            r12 = r29
            r11 = r7
            goto L_0x001e
        L_0x0566:
            r9 = r27
            r12 = r28
            r11 = r30
            r7 = r32
            r10 = r33
            r8 = r19
            r13 = r31
            r3 = r8
            r15 = r9
            r9 = r10
            r1 = r11
            r14 = r12
            r2 = r17
            r5 = r22
            r6 = r23
            r10 = r26
            r12 = r29
            r11 = r7
            goto L_0x001e
        L_0x0586:
            r22 = r5
            r23 = r6
            r26 = r10
            r7 = r11
            r12 = r14
            r9 = r15
        L_0x058f:
            r1 = 1048575(0xfffff, float:1.469367E-39)
            if (r6 == r1) goto L_0x059a
            long r1 = (long) r6
            r4 = r26
            r4.putInt(r12, r1, r5)
        L_0x059a:
            r1 = 0
            int r2 = r9.zzm
        L_0x059d:
            int r4 = r9.zzn
            if (r2 >= r4) goto L_0x05b0
            int[] r4 = r9.zzl
            r4 = r4[r2]
            com.google.android.gms.internal.measurement.zzku<?, ?> r5 = r9.zzq
            java.lang.Object r1 = r9.zza((java.lang.Object) r12, (int) r4, r1, r5)
            com.google.android.gms.internal.measurement.zzkt r1 = (com.google.android.gms.internal.measurement.zzkt) r1
            int r2 = r2 + 1
            goto L_0x059d
        L_0x05b0:
            if (r1 == 0) goto L_0x05b7
            com.google.android.gms.internal.measurement.zzku<?, ?> r2 = r9.zzq
            r2.zzb((java.lang.Object) r12, r1)
        L_0x05b7:
            if (r7 != 0) goto L_0x05c3
            r1 = r31
            if (r0 != r1) goto L_0x05be
            goto L_0x05c9
        L_0x05be:
            com.google.android.gms.internal.measurement.zzij r0 = com.google.android.gms.internal.measurement.zzij.zzg()
            throw r0
        L_0x05c3:
            r1 = r31
            if (r0 > r1) goto L_0x05ca
            if (r3 != r7) goto L_0x05ca
        L_0x05c9:
            return r0
        L_0x05ca:
            com.google.android.gms.internal.measurement.zzij r0 = com.google.android.gms.internal.measurement.zzij.zzg()
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.measurement.zzjn.zza(java.lang.Object, byte[], int, int, int, com.google.android.gms.internal.measurement.zzgo):int");
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r17v0, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v7, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v20, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r17v2, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r17v3, resolved type: byte} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void zza(T r30, byte[] r31, int r32, int r33, com.google.android.gms.internal.measurement.zzgo r34) throws java.io.IOException {
        /*
            r29 = this;
            r15 = r29
            r14 = r30
            r12 = r31
            r13 = r33
            r11 = r34
            boolean r0 = r15.zzj
            if (r0 == 0) goto L_0x042b
            sun.misc.Unsafe r9 = zzb
            r10 = -1
            r16 = 0
            r0 = r32
            r1 = r10
            r2 = r16
            r6 = r2
            r7 = 1048575(0xfffff, float:1.469367E-39)
        L_0x0020:
            if (r0 >= r13) goto L_0x040c
            int r3 = r0 + 1
            byte r0 = r12[r0]
            if (r0 >= 0) goto L_0x0032
            int r0 = com.google.android.gms.internal.measurement.zzgp.zza((int) r0, (byte[]) r12, (int) r3, (com.google.android.gms.internal.measurement.zzgo) r11)
            int r3 = r11.zza
            r4 = r0
            r17 = r3
            goto L_0x0035
        L_0x0032:
            r17 = r0
            r4 = r3
        L_0x0035:
            int r5 = r17 >>> 3
            r3 = r17 & 7
            if (r5 <= r1) goto L_0x0043
            int r2 = r2 / 3
            int r0 = r15.zza((int) r5, (int) r2)
            r2 = r0
            goto L_0x0048
        L_0x0043:
            int r0 = r15.zzg(r5)
            r2 = r0
        L_0x0048:
            if (r2 != r10) goto L_0x0056
            r2 = r4
            r19 = r5
            r26 = r9
            r27 = r10
            r20 = r16
            goto L_0x03cf
        L_0x0056:
            int[] r0 = r15.zzc
            int r1 = r2 + 1
            r1 = r0[r1]
            r18 = 267386880(0xff00000, float:2.3665827E-29)
            r18 = r1 & r18
            int r8 = r18 >>> 20
            r18 = 1048575(0xfffff, float:1.469367E-39)
            r10 = r1 & r18
            r18 = r4
            r32 = r5
            long r4 = (long) r10
            r10 = 17
            r20 = r1
            if (r8 > r10) goto L_0x029c
            int r10 = r2 + 2
            r0 = r0[r10]
            int r10 = r0 >>> 20
            r1 = 1
            int r10 = r1 << r10
            r13 = 1048575(0xfffff, float:1.469367E-39)
            r0 = r0 & r13
            if (r0 == r7) goto L_0x009a
            if (r7 == r13) goto L_0x008e
            r19 = r2
            long r1 = (long) r7
            r9.putInt(r14, r1, r6)
            goto L_0x0090
        L_0x008e:
            r19 = r2
        L_0x0090:
            if (r0 == r13) goto L_0x0098
            long r1 = (long) r0
            int r1 = r9.getInt(r14, r1)
            r6 = r1
        L_0x0098:
            r7 = r0
            goto L_0x009c
        L_0x009a:
            r19 = r2
        L_0x009c:
            r0 = 5
            switch(r8) {
                case 0: goto L_0x0276;
                case 1: goto L_0x025a;
                case 2: goto L_0x0235;
                case 3: goto L_0x0235;
                case 4: goto L_0x0219;
                case 5: goto L_0x01f4;
                case 6: goto L_0x01d4;
                case 7: goto L_0x01a6;
                case 8: goto L_0x017a;
                case 9: goto L_0x013c;
                case 10: goto L_0x011c;
                case 11: goto L_0x0219;
                case 12: goto L_0x00fd;
                case 13: goto L_0x01d4;
                case 14: goto L_0x01f4;
                case 15: goto L_0x00da;
                case 16: goto L_0x00aa;
                default: goto L_0x00a0;
            }
        L_0x00a0:
            r8 = r18
            r18 = r13
            r13 = r19
            r19 = r32
            goto L_0x0293
        L_0x00aa:
            if (r3 != 0) goto L_0x00cf
            r8 = r18
            int r8 = com.google.android.gms.internal.measurement.zzgp.zzb(r12, r8, r11)
            long r0 = r11.zzb
            long r17 = com.google.android.gms.internal.measurement.zzhf.zza((long) r0)
            r0 = r9
            r1 = r30
            r13 = r19
            r2 = r4
            r19 = r32
            r4 = r17
            r0.putLong(r1, r2, r4)
            r6 = r6 | r10
            r0 = r8
            r2 = r13
            r1 = r19
            r10 = -1
            r13 = r33
            goto L_0x0020
        L_0x00cf:
            r8 = r18
            r13 = r19
            r19 = r32
            r18 = 1048575(0xfffff, float:1.469367E-39)
            goto L_0x0293
        L_0x00da:
            r8 = r18
            r13 = r19
            r19 = r32
            if (r3 != 0) goto L_0x00f8
            int r0 = com.google.android.gms.internal.measurement.zzgp.zza(r12, r8, r11)
            int r1 = r11.zza
            int r1 = com.google.android.gms.internal.measurement.zzhf.zze(r1)
            r9.putInt(r14, r4, r1)
            r6 = r6 | r10
            r2 = r13
            r1 = r19
            r10 = -1
            r13 = r33
            goto L_0x0020
        L_0x00f8:
            r18 = 1048575(0xfffff, float:1.469367E-39)
            goto L_0x0293
        L_0x00fd:
            r8 = r18
            r13 = r19
            r19 = r32
            if (r3 != 0) goto L_0x0117
            int r0 = com.google.android.gms.internal.measurement.zzgp.zza(r12, r8, r11)
            int r1 = r11.zza
            r9.putInt(r14, r4, r1)
            r6 = r6 | r10
            r2 = r13
            r1 = r19
            r10 = -1
            r13 = r33
            goto L_0x0020
        L_0x0117:
            r18 = 1048575(0xfffff, float:1.469367E-39)
            goto L_0x0293
        L_0x011c:
            r8 = r18
            r13 = r19
            r19 = r32
            r0 = 2
            if (r3 != r0) goto L_0x0137
            int r0 = com.google.android.gms.internal.measurement.zzgp.zze(r12, r8, r11)
            java.lang.Object r1 = r11.zzc
            r9.putObject(r14, r4, r1)
            r6 = r6 | r10
            r2 = r13
            r1 = r19
            r10 = -1
            r13 = r33
            goto L_0x0020
        L_0x0137:
            r18 = 1048575(0xfffff, float:1.469367E-39)
            goto L_0x0293
        L_0x013c:
            r8 = r18
            r13 = r19
            r19 = r32
            r0 = 2
            if (r3 != r0) goto L_0x0173
            com.google.android.gms.internal.measurement.zzkc r0 = r15.zza((int) r13)
            r2 = r33
            r18 = 1048575(0xfffff, float:1.469367E-39)
            int r0 = com.google.android.gms.internal.measurement.zzgp.zza((com.google.android.gms.internal.measurement.zzkc) r0, (byte[]) r12, (int) r8, (int) r2, (com.google.android.gms.internal.measurement.zzgo) r11)
            java.lang.Object r1 = r9.getObject(r14, r4)
            if (r1 != 0) goto L_0x015f
            java.lang.Object r1 = r11.zzc
            r9.putObject(r14, r4, r1)
            goto L_0x0168
        L_0x015f:
            java.lang.Object r3 = r11.zzc
            java.lang.Object r1 = com.google.android.gms.internal.measurement.zzie.zza((java.lang.Object) r1, (java.lang.Object) r3)
            r9.putObject(r14, r4, r1)
        L_0x0168:
            r6 = r6 | r10
            r1 = r19
            r10 = -1
            r28 = r13
            r13 = r2
            r2 = r28
            goto L_0x0020
        L_0x0173:
            r2 = r33
            r18 = 1048575(0xfffff, float:1.469367E-39)
            goto L_0x0293
        L_0x017a:
            r2 = r33
            r8 = r18
            r18 = r13
            r13 = r19
            r19 = r32
            r0 = 2
            if (r3 != r0) goto L_0x0293
            r0 = 536870912(0x20000000, float:1.0842022E-19)
            r0 = r20 & r0
            if (r0 != 0) goto L_0x0192
            int r0 = com.google.android.gms.internal.measurement.zzgp.zzc(r12, r8, r11)
            goto L_0x0196
        L_0x0192:
            int r0 = com.google.android.gms.internal.measurement.zzgp.zzd(r12, r8, r11)
        L_0x0196:
            java.lang.Object r1 = r11.zzc
            r9.putObject(r14, r4, r1)
            r6 = r6 | r10
            r1 = r19
            r10 = -1
            r28 = r13
            r13 = r2
            r2 = r28
            goto L_0x0020
        L_0x01a6:
            r2 = r33
            r8 = r18
            r18 = r13
            r13 = r19
            r19 = r32
            if (r3 != 0) goto L_0x0293
            int r0 = com.google.android.gms.internal.measurement.zzgp.zzb(r12, r8, r11)
            r32 = r0
            long r0 = r11.zzb
            r20 = 0
            int r0 = (r0 > r20 ? 1 : (r0 == r20 ? 0 : -1))
            if (r0 == 0) goto L_0x01c2
            r1 = 1
            goto L_0x01c4
        L_0x01c2:
            r1 = r16
        L_0x01c4:
            com.google.android.gms.internal.measurement.zzla.zza((java.lang.Object) r14, (long) r4, (boolean) r1)
            r6 = r6 | r10
            r0 = r32
            r1 = r19
            r10 = -1
            r28 = r13
            r13 = r2
            r2 = r28
            goto L_0x0020
        L_0x01d4:
            r2 = r33
            r8 = r18
            r18 = r13
            r13 = r19
            r19 = r32
            if (r3 != r0) goto L_0x0293
            int r0 = com.google.android.gms.internal.measurement.zzgp.zza(r12, r8)
            r9.putInt(r14, r4, r0)
            int r0 = r8 + 4
            r6 = r6 | r10
            r1 = r19
            r10 = -1
            r28 = r13
            r13 = r2
            r2 = r28
            goto L_0x0020
        L_0x01f4:
            r2 = r33
            r8 = r18
            r18 = r13
            r13 = r19
            r19 = r32
            r0 = 1
            if (r3 != r0) goto L_0x0293
            long r20 = com.google.android.gms.internal.measurement.zzgp.zzb(r12, r8)
            r0 = r9
            r1 = r30
            r2 = r4
            r4 = r20
            r0.putLong(r1, r2, r4)
            int r0 = r8 + 8
            r6 = r6 | r10
            r2 = r13
            r1 = r19
            r10 = -1
            r13 = r33
            goto L_0x0020
        L_0x0219:
            r8 = r18
            r18 = r13
            r13 = r19
            r19 = r32
            if (r3 != 0) goto L_0x0293
            int r0 = com.google.android.gms.internal.measurement.zzgp.zza(r12, r8, r11)
            int r1 = r11.zza
            r9.putInt(r14, r4, r1)
            r6 = r6 | r10
            r2 = r13
            r1 = r19
            r10 = -1
            r13 = r33
            goto L_0x0020
        L_0x0235:
            r8 = r18
            r18 = r13
            r13 = r19
            r19 = r32
            if (r3 != 0) goto L_0x0293
            int r8 = com.google.android.gms.internal.measurement.zzgp.zzb(r12, r8, r11)
            long r2 = r11.zzb
            r0 = r9
            r1 = r30
            r20 = r2
            r2 = r4
            r4 = r20
            r0.putLong(r1, r2, r4)
            r6 = r6 | r10
            r0 = r8
            r2 = r13
            r1 = r19
            r10 = -1
            r13 = r33
            goto L_0x0020
        L_0x025a:
            r8 = r18
            r18 = r13
            r13 = r19
            r19 = r32
            if (r3 != r0) goto L_0x0293
            float r0 = com.google.android.gms.internal.measurement.zzgp.zzd(r12, r8)
            com.google.android.gms.internal.measurement.zzla.zza((java.lang.Object) r14, (long) r4, (float) r0)
            int r0 = r8 + 4
            r6 = r6 | r10
            r2 = r13
            r1 = r19
            r10 = -1
            r13 = r33
            goto L_0x0020
        L_0x0276:
            r8 = r18
            r18 = r13
            r13 = r19
            r19 = r32
            r0 = 1
            if (r3 != r0) goto L_0x0293
            double r0 = com.google.android.gms.internal.measurement.zzgp.zzc(r12, r8)
            com.google.android.gms.internal.measurement.zzla.zza((java.lang.Object) r14, (long) r4, (double) r0)
            int r0 = r8 + 8
            r6 = r6 | r10
            r2 = r13
            r1 = r19
            r10 = -1
            r13 = r33
            goto L_0x0020
        L_0x0293:
            r2 = r8
            r26 = r9
            r20 = r13
            r27 = -1
            goto L_0x03cf
        L_0x029c:
            r19 = r32
            r13 = r2
            r10 = r18
            r18 = 1048575(0xfffff, float:1.469367E-39)
            r0 = 27
            if (r8 != r0) goto L_0x02f7
            r0 = 2
            if (r3 != r0) goto L_0x02e9
            java.lang.Object r0 = r9.getObject(r14, r4)
            com.google.android.gms.internal.measurement.zzik r0 = (com.google.android.gms.internal.measurement.zzik) r0
            boolean r1 = r0.zza()
            if (r1 != 0) goto L_0x02cc
            int r1 = r0.size()
            if (r1 != 0) goto L_0x02c1
            r1 = 10
            goto L_0x02c3
        L_0x02c1:
            int r1 = r1 << 1
        L_0x02c3:
            com.google.android.gms.internal.measurement.zzik r0 = r0.zza(r1)
            r9.putObject(r14, r4, r0)
            r5 = r0
            goto L_0x02cd
        L_0x02cc:
            r5 = r0
        L_0x02cd:
            com.google.android.gms.internal.measurement.zzkc r0 = r15.zza((int) r13)
            r1 = r17
            r2 = r31
            r3 = r10
            r4 = r33
            r8 = r6
            r6 = r34
            int r0 = com.google.android.gms.internal.measurement.zzgp.zza(r0, r1, r2, r3, r4, r5, r6)
            r6 = r8
            r2 = r13
            r1 = r19
            r10 = -1
            r13 = r33
            goto L_0x0020
        L_0x02e9:
            r8 = r6
            r18 = r7
            r24 = r8
            r26 = r9
            r15 = r10
            r20 = r13
            r27 = -1
            goto L_0x03a7
        L_0x02f7:
            r0 = 49
            if (r8 > r0) goto L_0x0355
            r1 = r20
            long r1 = (long) r1
            r0 = r29
            r20 = r1
            r1 = r30
            r2 = r31
            r32 = r3
            r3 = r10
            r22 = r4
            r4 = r33
            r5 = r17
            r15 = r6
            r6 = r19
            r24 = r15
            r15 = r7
            r7 = r32
            r25 = r8
            r28 = r18
            r18 = r15
            r15 = r28
            r8 = r13
            r26 = r9
            r15 = r10
            r27 = -1
            r9 = r20
            r11 = r25
            r20 = r13
            r12 = r22
            r14 = r34
            int r0 = r0.zza(r1, (byte[]) r2, (int) r3, (int) r4, (int) r5, (int) r6, (int) r7, (int) r8, (long) r9, (int) r11, (long) r12, (com.google.android.gms.internal.measurement.zzgo) r14)
            if (r0 != r15) goto L_0x033d
            r2 = r0
            r7 = r18
            r6 = r24
            goto L_0x03cf
        L_0x033d:
            r15 = r29
            r14 = r30
            r12 = r31
            r13 = r33
            r11 = r34
            r7 = r18
            r1 = r19
            r2 = r20
            r6 = r24
            r9 = r26
            r10 = r27
            goto L_0x0020
        L_0x0355:
            r32 = r3
            r22 = r4
            r24 = r6
            r18 = r7
            r25 = r8
            r26 = r9
            r15 = r10
            r1 = r20
            r27 = -1
            r20 = r13
            r0 = 50
            r9 = r25
            if (r9 != r0) goto L_0x03ad
            r7 = r32
            r0 = 2
            if (r7 != r0) goto L_0x03a7
            r0 = r29
            r1 = r30
            r2 = r31
            r3 = r15
            r4 = r33
            r5 = r20
            r6 = r22
            r8 = r34
            int r0 = r0.zza(r1, (byte[]) r2, (int) r3, (int) r4, (int) r5, (long) r6, (com.google.android.gms.internal.measurement.zzgo) r8)
            if (r0 != r15) goto L_0x038f
            r2 = r0
            r7 = r18
            r6 = r24
            goto L_0x03cf
        L_0x038f:
            r15 = r29
            r14 = r30
            r12 = r31
            r13 = r33
            r11 = r34
            r7 = r18
            r1 = r19
            r2 = r20
            r6 = r24
            r9 = r26
            r10 = r27
            goto L_0x0020
        L_0x03a7:
            r2 = r15
            r7 = r18
            r6 = r24
            goto L_0x03cf
        L_0x03ad:
            r7 = r32
            r0 = r29
            r8 = r1
            r1 = r30
            r2 = r31
            r3 = r15
            r4 = r33
            r5 = r17
            r6 = r19
            r10 = r22
            r12 = r20
            r13 = r34
            int r0 = r0.zza(r1, (byte[]) r2, (int) r3, (int) r4, (int) r5, (int) r6, (int) r7, (int) r8, (int) r9, (long) r10, (int) r12, (com.google.android.gms.internal.measurement.zzgo) r13)
            if (r0 != r15) goto L_0x03f4
            r2 = r0
            r7 = r18
            r6 = r24
        L_0x03cf:
            com.google.android.gms.internal.measurement.zzkt r4 = zze((java.lang.Object) r30)
            r0 = r17
            r1 = r31
            r3 = r33
            r5 = r34
            int r0 = com.google.android.gms.internal.measurement.zzgp.zza((int) r0, (byte[]) r1, (int) r2, (int) r3, (com.google.android.gms.internal.measurement.zzkt) r4, (com.google.android.gms.internal.measurement.zzgo) r5)
            r15 = r29
            r14 = r30
            r12 = r31
            r13 = r33
            r11 = r34
            r1 = r19
            r2 = r20
            r9 = r26
            r10 = r27
            goto L_0x0020
        L_0x03f4:
            r15 = r29
            r14 = r30
            r12 = r31
            r13 = r33
            r11 = r34
            r7 = r18
            r1 = r19
            r2 = r20
            r6 = r24
            r9 = r26
            r10 = r27
            goto L_0x0020
        L_0x040c:
            r24 = r6
            r18 = r7
            r26 = r9
            r1 = 1048575(0xfffff, float:1.469367E-39)
            if (r7 == r1) goto L_0x0421
            long r1 = (long) r7
            r3 = r30
            r6 = r24
            r4 = r26
            r4.putInt(r3, r1, r6)
        L_0x0421:
            r4 = r33
            if (r0 != r4) goto L_0x0426
            return
        L_0x0426:
            com.google.android.gms.internal.measurement.zzij r0 = com.google.android.gms.internal.measurement.zzij.zzg()
            throw r0
        L_0x042b:
            r4 = r13
            r3 = r14
            r5 = 0
            r0 = r29
            r1 = r30
            r2 = r31
            r3 = r32
            r4 = r33
            r6 = r34
            r0.zza(r1, (byte[]) r2, (int) r3, (int) r4, (int) r5, (com.google.android.gms.internal.measurement.zzgo) r6)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.measurement.zzjn.zza(java.lang.Object, byte[], int, int, com.google.android.gms.internal.measurement.zzgo):void");
    }

    public final void zzc(T t) {
        int i;
        int i2 = this.zzm;
        while (true) {
            i = this.zzn;
            if (i2 >= i) {
                break;
            }
            long zzd2 = (long) (zzd(this.zzl[i2]) & 1048575);
            Object zzf2 = zzla.zzf(t, zzd2);
            if (zzf2 != null) {
                zzla.zza((Object) t, zzd2, this.zzs.zze(zzf2));
            }
            i2++;
        }
        int length = this.zzl.length;
        while (i < length) {
            this.zzp.zzb(t, (long) this.zzl[i]);
            i++;
        }
        this.zzq.zzd(t);
        if (this.zzh) {
            this.zzr.zzc(t);
        }
    }

    private final <UT, UB> UB zza(Object obj, int i, UB ub, zzku<UT, UB> zzku) {
        zzif zzc2;
        int i2 = this.zzc[i];
        Object zzf2 = zzla.zzf(obj, (long) (zzd(i) & 1048575));
        if (zzf2 == null || (zzc2 = zzc(i)) == null) {
            return ub;
        }
        return zza(i, i2, this.zzs.zza(zzf2), zzc2, ub, zzku);
    }

    private final <K, V, UT, UB> UB zza(int i, int i2, Map<K, V> map, zzif zzif, UB ub, zzku<UT, UB> zzku) {
        zzje<?, ?> zzb2 = this.zzs.zzb(zzb(i));
        Iterator<Map.Entry<K, V>> it = map.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry next = it.next();
            if (!zzif.zza(((Integer) next.getValue()).intValue())) {
                if (ub == null) {
                    ub = zzku.zza();
                }
                zzhb zzc2 = zzgt.zzc(zzjb.zza(zzb2, next.getKey(), next.getValue()));
                try {
                    zzjb.zza(zzc2.zzb(), zzb2, next.getKey(), next.getValue());
                    zzku.zza(ub, i2, zzc2.zza());
                    it.remove();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return ub;
    }

    public final boolean zzd(T t) {
        int i;
        int i2;
        T t2 = t;
        int i3 = 1048575;
        int i4 = 0;
        int i5 = 0;
        while (true) {
            boolean z = true;
            if (i5 >= this.zzm) {
                return !this.zzh || this.zzr.zza((Object) t2).zzf();
            }
            int i6 = this.zzl[i5];
            int i7 = this.zzc[i6];
            int zzd2 = zzd(i6);
            int i8 = this.zzc[i6 + 2];
            int i9 = i8 & 1048575;
            int i10 = 1 << (i8 >>> 20);
            if (i9 == i3) {
                i2 = i3;
                i = i4;
            } else if (i9 != 1048575) {
                i = zzb.getInt(t2, (long) i9);
                i2 = i9;
            } else {
                i = i4;
                i2 = i9;
            }
            if (((268435456 & zzd2) != 0) && !zza(t, i6, i2, i, i10)) {
                return false;
            }
            int i11 = (267386880 & zzd2) >>> 20;
            if (i11 != 9 && i11 != 17) {
                if (i11 != 27) {
                    if (i11 == 60 || i11 == 68) {
                        if (zza(t2, i7, i6) && !zza((Object) t2, zzd2, zza(i6))) {
                            return false;
                        }
                    } else if (i11 != 49) {
                        if (i11 != 50) {
                            continue;
                        } else {
                            Map<?, ?> zzc2 = this.zzs.zzc(zzla.zzf(t2, (long) (zzd2 & 1048575)));
                            if (!zzc2.isEmpty()) {
                                if (this.zzs.zzb(zzb(i6)).zzc.zza() == zzlo.MESSAGE) {
                                    zzkc<?> zzkc = null;
                                    Iterator<?> it = zzc2.values().iterator();
                                    while (true) {
                                        if (!it.hasNext()) {
                                            break;
                                        }
                                        Object next = it.next();
                                        if (zzkc == null) {
                                            zzkc = zzjy.zza().zza(next.getClass());
                                        }
                                        if (!zzkc.zzd(next)) {
                                            z = false;
                                            break;
                                        }
                                    }
                                }
                            }
                            if (!z) {
                                return false;
                            }
                        }
                    }
                }
                List list = (List) zzla.zzf(t2, (long) (zzd2 & 1048575));
                if (!list.isEmpty()) {
                    zzkc zza2 = zza(i6);
                    int i12 = 0;
                    while (true) {
                        if (i12 >= list.size()) {
                            break;
                        } else if (!zza2.zzd(list.get(i12))) {
                            z = false;
                            break;
                        } else {
                            i12++;
                        }
                    }
                }
                if (!z) {
                    return false;
                }
            } else if (zza(t, i6, i2, i, i10) && !zza((Object) t2, zzd2, zza(i6))) {
                return false;
            }
            i5++;
            i3 = i2;
            i4 = i;
        }
    }

    private static boolean zza(Object obj, int i, zzkc zzkc) {
        return zzkc.zzd(zzla.zzf(obj, (long) (i & 1048575)));
    }

    private static void zza(int i, Object obj, zzln zzln) throws IOException {
        if (obj instanceof String) {
            zzln.zza(i, (String) obj);
        } else {
            zzln.zza(i, (zzgt) obj);
        }
    }

    private final void zza(Object obj, int i, zzjz zzjz) throws IOException {
        if (zzf(i)) {
            zzla.zza(obj, (long) (i & 1048575), (Object) zzjz.zzm());
        } else if (this.zzi) {
            zzla.zza(obj, (long) (i & 1048575), (Object) zzjz.zzl());
        } else {
            zzla.zza(obj, (long) (i & 1048575), (Object) zzjz.zzn());
        }
    }

    private final int zzd(int i) {
        return this.zzc[i + 1];
    }

    private final int zze(int i) {
        return this.zzc[i + 2];
    }

    private static boolean zzf(int i) {
        return (i & 536870912) != 0;
    }

    private static <T> double zzb(T t, long j) {
        return ((Double) zzla.zzf(t, j)).doubleValue();
    }

    private static <T> float zzc(T t, long j) {
        return ((Float) zzla.zzf(t, j)).floatValue();
    }

    private static <T> int zzd(T t, long j) {
        return ((Integer) zzla.zzf(t, j)).intValue();
    }

    private static <T> long zze(T t, long j) {
        return ((Long) zzla.zzf(t, j)).longValue();
    }

    private static <T> boolean zzf(T t, long j) {
        return ((Boolean) zzla.zzf(t, j)).booleanValue();
    }

    private final boolean zzc(T t, T t2, int i) {
        return zza(t, i) == zza(t2, i);
    }

    private final boolean zza(T t, int i, int i2, int i3, int i4) {
        if (i2 == 1048575) {
            return zza(t, i);
        }
        return (i3 & i4) != 0;
    }

    private final boolean zza(T t, int i) {
        int zze2 = zze(i);
        long j = (long) (zze2 & 1048575);
        if (j == 1048575) {
            int zzd2 = zzd(i);
            long j2 = (long) (zzd2 & 1048575);
            switch ((zzd2 & 267386880) >>> 20) {
                case 0:
                    return zzla.zze(t, j2) != 0.0d;
                case 1:
                    return zzla.zzd(t, j2) != 0.0f;
                case 2:
                    return zzla.zzb(t, j2) != 0;
                case 3:
                    return zzla.zzb(t, j2) != 0;
                case 4:
                    return zzla.zza((Object) t, j2) != 0;
                case 5:
                    return zzla.zzb(t, j2) != 0;
                case 6:
                    return zzla.zza((Object) t, j2) != 0;
                case 7:
                    return zzla.zzc(t, j2);
                case 8:
                    Object zzf2 = zzla.zzf(t, j2);
                    if (zzf2 instanceof String) {
                        return !((String) zzf2).isEmpty();
                    }
                    if (zzf2 instanceof zzgt) {
                        return !zzgt.zza.equals(zzf2);
                    }
                    throw new IllegalArgumentException();
                case 9:
                    return zzla.zzf(t, j2) != null;
                case 10:
                    return !zzgt.zza.equals(zzla.zzf(t, j2));
                case 11:
                    return zzla.zza((Object) t, j2) != 0;
                case 12:
                    return zzla.zza((Object) t, j2) != 0;
                case 13:
                    return zzla.zza((Object) t, j2) != 0;
                case 14:
                    return zzla.zzb(t, j2) != 0;
                case 15:
                    return zzla.zza((Object) t, j2) != 0;
                case 16:
                    return zzla.zzb(t, j2) != 0;
                case 17:
                    return zzla.zzf(t, j2) != null;
                default:
                    throw new IllegalArgumentException();
            }
        } else {
            return (zzla.zza((Object) t, j) & (1 << (zze2 >>> 20))) != 0;
        }
    }

    private final void zzb(T t, int i) {
        int zze2 = zze(i);
        long j = (long) (1048575 & zze2);
        if (j != 1048575) {
            zzla.zza((Object) t, j, (1 << (zze2 >>> 20)) | zzla.zza((Object) t, j));
        }
    }

    private final boolean zza(T t, int i, int i2) {
        return zzla.zza((Object) t, (long) (zze(i2) & 1048575)) == i;
    }

    private final void zzb(T t, int i, int i2) {
        zzla.zza((Object) t, (long) (zze(i2) & 1048575), i);
    }

    private final int zzg(int i) {
        if (i < this.zze || i > this.zzf) {
            return -1;
        }
        return zzb(i, 0);
    }

    private final int zza(int i, int i2) {
        if (i < this.zze || i > this.zzf) {
            return -1;
        }
        return zzb(i, i2);
    }

    private final int zzb(int i, int i2) {
        int length = (this.zzc.length / 3) - 1;
        while (i2 <= length) {
            int i3 = (length + i2) >>> 1;
            int i4 = i3 * 3;
            int i5 = this.zzc[i4];
            if (i == i5) {
                return i4;
            }
            if (i < i5) {
                length = i3 - 1;
            } else {
                i2 = i3 + 1;
            }
        }
        return -1;
    }
}
