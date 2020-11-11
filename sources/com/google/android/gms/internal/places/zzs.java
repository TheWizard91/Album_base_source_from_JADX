package com.google.android.gms.internal.places;

import com.google.common.base.Ascii;
import java.io.IOException;
import kotlinx.coroutines.scheduling.WorkQueueKt;

final class zzs {
    static int zzb(byte[] bArr, int i, zzr zzr) {
        int i2 = i + 1;
        byte b = bArr[i];
        if (b < 0) {
            return zzb((int) b, bArr, i2, zzr);
        }
        zzr.zzdz = b;
        return i2;
    }

    static int zzb(int i, byte[] bArr, int i2, zzr zzr) {
        int i3 = i & WorkQueueKt.MASK;
        int i4 = i2 + 1;
        byte b = bArr[i2];
        if (b >= 0) {
            zzr.zzdz = i3 | (b << 7);
            return i4;
        }
        int i5 = i3 | ((b & Byte.MAX_VALUE) << 7);
        int i6 = i4 + 1;
        byte b2 = bArr[i4];
        if (b2 >= 0) {
            zzr.zzdz = i5 | (b2 << Ascii.f1614SO);
            return i6;
        }
        int i7 = i5 | ((b2 & Byte.MAX_VALUE) << Ascii.f1614SO);
        int i8 = i6 + 1;
        byte b3 = bArr[i6];
        if (b3 >= 0) {
            zzr.zzdz = i7 | (b3 << Ascii.NAK);
            return i8;
        }
        int i9 = i7 | ((b3 & Byte.MAX_VALUE) << Ascii.NAK);
        int i10 = i8 + 1;
        byte b4 = bArr[i8];
        if (b4 >= 0) {
            zzr.zzdz = i9 | (b4 << Ascii.f1607FS);
            return i10;
        }
        int i11 = i9 | ((b4 & Byte.MAX_VALUE) << Ascii.f1607FS);
        while (true) {
            int i12 = i10 + 1;
            if (bArr[i10] >= 0) {
                zzr.zzdz = i11;
                return i12;
            }
            i10 = i12;
        }
    }

    static int zzc(byte[] bArr, int i, zzr zzr) {
        int i2 = i + 1;
        long j = (long) bArr[i];
        if (j >= 0) {
            zzr.zzea = j;
            return i2;
        }
        int i3 = i2 + 1;
        byte b = bArr[i2];
        long j2 = (j & 127) | (((long) (b & Byte.MAX_VALUE)) << 7);
        int i4 = 7;
        while (b < 0) {
            int i5 = i3 + 1;
            byte b2 = bArr[i3];
            i4 += 7;
            j2 |= ((long) (b2 & Byte.MAX_VALUE)) << i4;
            int i6 = i5;
            b = b2;
            i3 = i6;
        }
        zzr.zzea = j2;
        return i3;
    }

    static int zzb(byte[] bArr, int i) {
        return ((bArr[i + 3] & 255) << Ascii.CAN) | (bArr[i] & 255) | ((bArr[i + 1] & 255) << 8) | ((bArr[i + 2] & 255) << Ascii.DLE);
    }

    static long zzc(byte[] bArr, int i) {
        return ((((long) bArr[i + 7]) & 255) << 56) | (((long) bArr[i]) & 255) | ((((long) bArr[i + 1]) & 255) << 8) | ((((long) bArr[i + 2]) & 255) << 16) | ((((long) bArr[i + 3]) & 255) << 24) | ((((long) bArr[i + 4]) & 255) << 32) | ((((long) bArr[i + 5]) & 255) << 40) | ((((long) bArr[i + 6]) & 255) << 48);
    }

    static double zzd(byte[] bArr, int i) {
        return Double.longBitsToDouble(zzc(bArr, i));
    }

    static float zze(byte[] bArr, int i) {
        return Float.intBitsToFloat(zzb(bArr, i));
    }

    static int zzd(byte[] bArr, int i, zzr zzr) throws zzbk {
        int zzb = zzb(bArr, i, zzr);
        int i2 = zzr.zzdz;
        if (i2 < 0) {
            throw zzbk.zzbq();
        } else if (i2 == 0) {
            zzr.zzeb = "";
            return zzb;
        } else {
            zzr.zzeb = new String(bArr, zzb, i2, zzbd.UTF_8);
            return zzb + i2;
        }
    }

    static int zze(byte[] bArr, int i, zzr zzr) throws zzbk {
        int zzb = zzb(bArr, i, zzr);
        int i2 = zzr.zzdz;
        if (i2 < 0) {
            throw zzbk.zzbq();
        } else if (i2 == 0) {
            zzr.zzeb = "";
            return zzb;
        } else {
            zzr.zzeb = zzea.zzh(bArr, zzb, i2);
            return zzb + i2;
        }
    }

    static int zzf(byte[] bArr, int i, zzr zzr) throws zzbk {
        int zzb = zzb(bArr, i, zzr);
        int i2 = zzr.zzdz;
        if (i2 < 0) {
            throw zzbk.zzbq();
        } else if (i2 > bArr.length - zzb) {
            throw zzbk.zzbp();
        } else if (i2 == 0) {
            zzr.zzeb = zzw.zzeg;
            return zzb;
        } else {
            zzr.zzeb = zzw.zzc(bArr, zzb, i2);
            return zzb + i2;
        }
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r8v2, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v0, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v1, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v2, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r8v5, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r8v6, resolved type: byte} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static int zzb(com.google.android.gms.internal.places.zzda r6, byte[] r7, int r8, int r9, com.google.android.gms.internal.places.zzr r10) throws java.io.IOException {
        /*
            int r0 = r8 + 1
            byte r8 = r7[r8]
            if (r8 >= 0) goto L_0x000e
            int r0 = zzb((int) r8, (byte[]) r7, (int) r0, (com.google.android.gms.internal.places.zzr) r10)
            int r8 = r10.zzdz
            r3 = r0
            goto L_0x000f
        L_0x000e:
            r3 = r0
        L_0x000f:
            if (r8 < 0) goto L_0x0027
            int r9 = r9 - r3
            if (r8 > r9) goto L_0x0027
            java.lang.Object r9 = r6.newInstance()
            int r8 = r8 + r3
            r0 = r6
            r1 = r9
            r2 = r7
            r4 = r8
            r5 = r10
            r0.zzb(r1, r2, r3, r4, r5)
            r6.zzd(r9)
            r10.zzeb = r9
            return r8
        L_0x0027:
            com.google.android.gms.internal.places.zzbk r6 = com.google.android.gms.internal.places.zzbk.zzbp()
            throw r6
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.places.zzs.zzb(com.google.android.gms.internal.places.zzda, byte[], int, int, com.google.android.gms.internal.places.zzr):int");
    }

    static int zzb(zzda zzda, byte[] bArr, int i, int i2, int i3, zzr zzr) throws IOException {
        zzco zzco = (zzco) zzda;
        Object newInstance = zzco.newInstance();
        int zzb = zzco.zzb(newInstance, bArr, i, i2, i3, zzr);
        zzco.zzd(newInstance);
        zzr.zzeb = newInstance;
        return zzb;
    }

    static int zzb(int i, byte[] bArr, int i2, int i3, zzbh<?> zzbh, zzr zzr) {
        zzbe zzbe = (zzbe) zzbh;
        int zzb = zzb(bArr, i2, zzr);
        zzbe.zzac(zzr.zzdz);
        while (zzb < i3) {
            int zzb2 = zzb(bArr, zzb, zzr);
            if (i != zzr.zzdz) {
                break;
            }
            zzb = zzb(bArr, zzb2, zzr);
            zzbe.zzac(zzr.zzdz);
        }
        return zzb;
    }

    static int zzb(byte[] bArr, int i, zzbh<?> zzbh, zzr zzr) throws IOException {
        zzbe zzbe = (zzbe) zzbh;
        int zzb = zzb(bArr, i, zzr);
        int i2 = zzr.zzdz + zzb;
        while (zzb < i2) {
            zzb = zzb(bArr, zzb, zzr);
            zzbe.zzac(zzr.zzdz);
        }
        if (zzb == i2) {
            return zzb;
        }
        throw zzbk.zzbp();
    }

    static int zzb(zzda<?> zzda, int i, byte[] bArr, int i2, int i3, zzbh<?> zzbh, zzr zzr) throws IOException {
        int zzb = zzb((zzda) zzda, bArr, i2, i3, zzr);
        zzbh.add(zzr.zzeb);
        while (zzb < i3) {
            int zzb2 = zzb(bArr, zzb, zzr);
            if (i != zzr.zzdz) {
                break;
            }
            zzb = zzb((zzda) zzda, bArr, zzb2, i3, zzr);
            zzbh.add(zzr.zzeb);
        }
        return zzb;
    }

    static int zzb(int i, byte[] bArr, int i2, int i3, zzdr zzdr, zzr zzr) throws zzbk {
        if ((i >>> 3) != 0) {
            int i4 = i & 7;
            if (i4 == 0) {
                int zzc = zzc(bArr, i2, zzr);
                zzdr.zzc(i, Long.valueOf(zzr.zzea));
                return zzc;
            } else if (i4 == 1) {
                zzdr.zzc(i, Long.valueOf(zzc(bArr, i2)));
                return i2 + 8;
            } else if (i4 == 2) {
                int zzb = zzb(bArr, i2, zzr);
                int i5 = zzr.zzdz;
                if (i5 < 0) {
                    throw zzbk.zzbq();
                } else if (i5 <= bArr.length - zzb) {
                    if (i5 == 0) {
                        zzdr.zzc(i, zzw.zzeg);
                    } else {
                        zzdr.zzc(i, zzw.zzc(bArr, zzb, i5));
                    }
                    return zzb + i5;
                } else {
                    throw zzbk.zzbp();
                }
            } else if (i4 == 3) {
                zzdr zzdi = zzdr.zzdi();
                int i6 = (i & -8) | 4;
                int i7 = 0;
                while (true) {
                    if (i2 >= i3) {
                        break;
                    }
                    int zzb2 = zzb(bArr, i2, zzr);
                    int i8 = zzr.zzdz;
                    if (i8 == i6) {
                        i7 = i8;
                        i2 = zzb2;
                        break;
                    }
                    i7 = i8;
                    i2 = zzb(i8, bArr, zzb2, i3, zzdi, zzr);
                }
                if (i2 > i3 || i7 != i6) {
                    throw zzbk.zzbt();
                }
                zzdr.zzc(i, zzdi);
                return i2;
            } else if (i4 == 5) {
                zzdr.zzc(i, Integer.valueOf(zzb(bArr, i2)));
                return i2 + 4;
            } else {
                throw zzbk.zzbr();
            }
        } else {
            throw zzbk.zzbr();
        }
    }

    static int zzb(int i, byte[] bArr, int i2, int i3, zzr zzr) throws zzbk {
        if ((i >>> 3) != 0) {
            int i4 = i & 7;
            if (i4 == 0) {
                return zzc(bArr, i2, zzr);
            }
            if (i4 == 1) {
                return i2 + 8;
            }
            if (i4 == 2) {
                return zzb(bArr, i2, zzr) + zzr.zzdz;
            }
            if (i4 == 3) {
                int i5 = (i & -8) | 4;
                int i6 = 0;
                while (i2 < i3) {
                    i2 = zzb(bArr, i2, zzr);
                    i6 = zzr.zzdz;
                    if (i6 == i5) {
                        break;
                    }
                    i2 = zzb(i6, bArr, i2, i3, zzr);
                }
                if (i2 <= i3 && i6 == i5) {
                    return i2;
                }
                throw zzbk.zzbt();
            } else if (i4 == 5) {
                return i2 + 4;
            } else {
                throw zzbk.zzbr();
            }
        } else {
            throw zzbk.zzbr();
        }
    }
}
