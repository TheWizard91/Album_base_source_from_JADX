package com.google.android.gms.internal.measurement;

import java.util.Map;
import kotlin.UShort;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

/* compiled from: com.google.android.gms:play-services-measurement-impl@@17.4.4 */
final class zzfm<K, V> extends zzeu<K, V> {
    static final zzeu<Object, Object> zza = new zzfm((Object) null, new Object[0], 0);
    private final transient Object zzb;
    private final transient Object[] zzc;
    private final transient int zzd;

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v0, resolved type: short[]} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v1, resolved type: byte[]} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v3, resolved type: byte[]} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v4, resolved type: short[]} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v5, resolved type: byte[]} */
    /* JADX WARNING: Code restructure failed: missing block: B:17:0x0058, code lost:
        r2[r6] = (byte) r3;
        r1 = r1 + 1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:31:0x009b, code lost:
        r2[r6] = (short) r3;
        r1 = r1 + 1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:43:0x00d4, code lost:
        r2[r7] = r3;
        r1 = r1 + 1;
     */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static <K, V> com.google.android.gms.internal.measurement.zzfm<K, V> zza(int r10, java.lang.Object[] r11) {
        /*
            if (r10 != 0) goto L_0x0007
            com.google.android.gms.internal.measurement.zzeu<java.lang.Object, java.lang.Object> r10 = zza
            com.google.android.gms.internal.measurement.zzfm r10 = (com.google.android.gms.internal.measurement.zzfm) r10
            return r10
        L_0x0007:
            r0 = 0
            r1 = 0
            r2 = 1
            if (r10 != r2) goto L_0x0019
            r10 = r11[r1]
            r1 = r11[r2]
            com.google.android.gms.internal.measurement.zzec.zza(r10, r1)
            com.google.android.gms.internal.measurement.zzfm r10 = new com.google.android.gms.internal.measurement.zzfm
            r10.<init>(r0, r11, r2)
            return r10
        L_0x0019:
            int r3 = r11.length
            int r3 = r3 >> r2
            com.google.android.gms.internal.measurement.zzdq.zzb((int) r10, (int) r3)
            int r3 = com.google.android.gms.internal.measurement.zzey.zza((int) r10)
            if (r10 != r2) goto L_0x002e
            r1 = r11[r1]
            r2 = r11[r2]
            com.google.android.gms.internal.measurement.zzec.zza(r1, r2)
            goto L_0x00eb
        L_0x002e:
            int r0 = r3 + -1
            r2 = 128(0x80, float:1.794E-43)
            r4 = -1
            if (r3 > r2) goto L_0x0072
            byte[] r2 = new byte[r3]
            java.util.Arrays.fill(r2, r4)
        L_0x003b:
            if (r1 >= r10) goto L_0x006f
            int r3 = r1 * 2
            r4 = r11[r3]
            r5 = r3 ^ 1
            r5 = r11[r5]
            com.google.android.gms.internal.measurement.zzec.zza(r4, r5)
            int r6 = r4.hashCode()
            int r6 = com.google.android.gms.internal.measurement.zzeo.zza((int) r6)
        L_0x0050:
            r6 = r6 & r0
            byte r7 = r2[r6]
            r8 = 255(0xff, float:3.57E-43)
            r7 = r7 & r8
            if (r7 != r8) goto L_0x005f
            byte r3 = (byte) r3
            r2[r6] = r3
            int r1 = r1 + 1
            goto L_0x003b
        L_0x005f:
            r8 = r11[r7]
            boolean r8 = r8.equals(r4)
            if (r8 != 0) goto L_0x006a
            int r6 = r6 + 1
            goto L_0x0050
        L_0x006a:
            java.lang.IllegalArgumentException r10 = zza(r4, r5, r11, r7)
            throw r10
        L_0x006f:
            r0 = r2
            goto L_0x00eb
        L_0x0072:
            r2 = 32768(0x8000, float:4.5918E-41)
            if (r3 > r2) goto L_0x00b4
            short[] r2 = new short[r3]
            java.util.Arrays.fill(r2, r4)
        L_0x007d:
            if (r1 >= r10) goto L_0x00b2
            int r3 = r1 * 2
            r4 = r11[r3]
            r5 = r3 ^ 1
            r5 = r11[r5]
            com.google.android.gms.internal.measurement.zzec.zza(r4, r5)
            int r6 = r4.hashCode()
            int r6 = com.google.android.gms.internal.measurement.zzeo.zza((int) r6)
        L_0x0092:
            r6 = r6 & r0
            short r7 = r2[r6]
            r8 = 65535(0xffff, float:9.1834E-41)
            r7 = r7 & r8
            if (r7 != r8) goto L_0x00a2
            short r3 = (short) r3
            r2[r6] = r3
            int r1 = r1 + 1
            goto L_0x007d
        L_0x00a2:
            r8 = r11[r7]
            boolean r8 = r8.equals(r4)
            if (r8 != 0) goto L_0x00ad
            int r6 = r6 + 1
            goto L_0x0092
        L_0x00ad:
            java.lang.IllegalArgumentException r10 = zza(r4, r5, r11, r7)
            throw r10
        L_0x00b2:
            r0 = r2
            goto L_0x00eb
        L_0x00b4:
            int[] r2 = new int[r3]
            java.util.Arrays.fill(r2, r4)
        L_0x00ba:
            if (r1 >= r10) goto L_0x00ea
            int r3 = r1 * 2
            r5 = r11[r3]
            r6 = r3 ^ 1
            r6 = r11[r6]
            com.google.android.gms.internal.measurement.zzec.zza(r5, r6)
            int r7 = r5.hashCode()
            int r7 = com.google.android.gms.internal.measurement.zzeo.zza((int) r7)
        L_0x00cf:
            r7 = r7 & r0
            r8 = r2[r7]
            if (r8 != r4) goto L_0x00da
            r2[r7] = r3
            int r1 = r1 + 1
            goto L_0x00ba
        L_0x00da:
            r9 = r11[r8]
            boolean r9 = r9.equals(r5)
            if (r9 != 0) goto L_0x00e5
            int r7 = r7 + 1
            goto L_0x00cf
        L_0x00e5:
            java.lang.IllegalArgumentException r10 = zza(r5, r6, r11, r8)
            throw r10
        L_0x00ea:
            r0 = r2
        L_0x00eb:
            com.google.android.gms.internal.measurement.zzfm r1 = new com.google.android.gms.internal.measurement.zzfm
            r1.<init>(r0, r11, r10)
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.measurement.zzfm.zza(int, java.lang.Object[]):com.google.android.gms.internal.measurement.zzfm");
    }

    private static IllegalArgumentException zza(Object obj, Object obj2, Object[] objArr, int i) {
        String valueOf = String.valueOf(obj);
        String valueOf2 = String.valueOf(obj2);
        String valueOf3 = String.valueOf(objArr[i]);
        String valueOf4 = String.valueOf(objArr[i ^ 1]);
        return new IllegalArgumentException(new StringBuilder(String.valueOf(valueOf).length() + 39 + String.valueOf(valueOf2).length() + String.valueOf(valueOf3).length() + String.valueOf(valueOf4).length()).append("Multiple entries with same key: ").append(valueOf).append("=").append(valueOf2).append(" and ").append(valueOf3).append("=").append(valueOf4).toString());
    }

    private zzfm(Object obj, Object[] objArr, int i) {
        this.zzb = obj;
        this.zzc = objArr;
        this.zzd = i;
    }

    public final int size() {
        return this.zzd;
    }

    @NullableDecl
    public final V get(@NullableDecl Object obj) {
        Object obj2 = this.zzb;
        V[] vArr = this.zzc;
        int i = this.zzd;
        if (obj == null) {
            return null;
        }
        if (i == 1) {
            if (vArr[0].equals(obj)) {
                return vArr[1];
            }
            return null;
        } else if (obj2 == null) {
            return null;
        } else {
            if (obj2 instanceof byte[]) {
                byte[] bArr = (byte[]) obj2;
                int length = bArr.length - 1;
                int zza2 = zzeo.zza(obj.hashCode());
                while (true) {
                    int i2 = zza2 & length;
                    byte b = bArr[i2] & 255;
                    if (b == 255) {
                        return null;
                    }
                    if (vArr[b].equals(obj)) {
                        return vArr[b ^ 1];
                    }
                    zza2 = i2 + 1;
                }
            } else if (obj2 instanceof short[]) {
                short[] sArr = (short[]) obj2;
                int length2 = sArr.length - 1;
                int zza3 = zzeo.zza(obj.hashCode());
                while (true) {
                    int i3 = zza3 & length2;
                    short s = sArr[i3] & UShort.MAX_VALUE;
                    if (s == 65535) {
                        return null;
                    }
                    if (vArr[s].equals(obj)) {
                        return vArr[s ^ 1];
                    }
                    zza3 = i3 + 1;
                }
            } else {
                int[] iArr = (int[]) obj2;
                int length3 = iArr.length - 1;
                int zza4 = zzeo.zza(obj.hashCode());
                while (true) {
                    int i4 = zza4 & length3;
                    int i5 = iArr[i4];
                    if (i5 == -1) {
                        return null;
                    }
                    if (vArr[i5].equals(obj)) {
                        return vArr[i5 ^ 1];
                    }
                    zza4 = i4 + 1;
                }
            }
        }
    }

    /* access modifiers changed from: package-private */
    public final zzey<Map.Entry<K, V>> zza() {
        return new zzfp(this, this.zzc, 0, this.zzd);
    }

    /* access modifiers changed from: package-private */
    public final zzey<K> zzb() {
        return new zzfr(this, new zzfq(this.zzc, 0, this.zzd));
    }

    /* access modifiers changed from: package-private */
    public final zzer<V> zzc() {
        return new zzfq(this.zzc, 1, this.zzd);
    }
}
