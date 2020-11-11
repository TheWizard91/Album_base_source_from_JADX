package com.google.android.gms.internal.measurement;

import java.lang.reflect.Type;

/* compiled from: com.google.android.gms:play-services-measurement-base@@17.4.4 */
public enum zzhw {
    DOUBLE(0, zzhy.SCALAR, zzil.DOUBLE),
    FLOAT(1, zzhy.SCALAR, zzil.FLOAT),
    INT64(2, zzhy.SCALAR, zzil.LONG),
    UINT64(3, zzhy.SCALAR, zzil.LONG),
    INT32(4, zzhy.SCALAR, zzil.INT),
    FIXED64(5, zzhy.SCALAR, zzil.LONG),
    FIXED32(6, zzhy.SCALAR, zzil.INT),
    BOOL(7, zzhy.SCALAR, zzil.BOOLEAN),
    STRING(8, zzhy.SCALAR, zzil.STRING),
    MESSAGE(9, zzhy.SCALAR, zzil.MESSAGE),
    BYTES(10, zzhy.SCALAR, zzil.BYTE_STRING),
    UINT32(11, zzhy.SCALAR, zzil.INT),
    ENUM(12, zzhy.SCALAR, zzil.ENUM),
    SFIXED32(13, zzhy.SCALAR, zzil.INT),
    SFIXED64(14, zzhy.SCALAR, zzil.LONG),
    SINT32(15, zzhy.SCALAR, zzil.INT),
    SINT64(16, zzhy.SCALAR, zzil.LONG),
    GROUP(17, zzhy.SCALAR, zzil.MESSAGE),
    DOUBLE_LIST(18, zzhy.VECTOR, zzil.DOUBLE),
    FLOAT_LIST(19, zzhy.VECTOR, zzil.FLOAT),
    INT64_LIST(20, zzhy.VECTOR, zzil.LONG),
    UINT64_LIST(21, zzhy.VECTOR, zzil.LONG),
    INT32_LIST(22, zzhy.VECTOR, zzil.INT),
    FIXED64_LIST(23, zzhy.VECTOR, zzil.LONG),
    FIXED32_LIST(24, zzhy.VECTOR, zzil.INT),
    BOOL_LIST(25, zzhy.VECTOR, zzil.BOOLEAN),
    STRING_LIST(26, zzhy.VECTOR, zzil.STRING),
    MESSAGE_LIST(27, zzhy.VECTOR, zzil.MESSAGE),
    BYTES_LIST(28, zzhy.VECTOR, zzil.BYTE_STRING),
    UINT32_LIST(29, zzhy.VECTOR, zzil.INT),
    ENUM_LIST(30, zzhy.VECTOR, zzil.ENUM),
    SFIXED32_LIST(31, zzhy.VECTOR, zzil.INT),
    SFIXED64_LIST(32, zzhy.VECTOR, zzil.LONG),
    SINT32_LIST(33, zzhy.VECTOR, zzil.INT),
    SINT64_LIST(34, zzhy.VECTOR, zzil.LONG),
    DOUBLE_LIST_PACKED(35, zzhy.PACKED_VECTOR, zzil.DOUBLE),
    FLOAT_LIST_PACKED(36, zzhy.PACKED_VECTOR, zzil.FLOAT),
    INT64_LIST_PACKED(37, zzhy.PACKED_VECTOR, zzil.LONG),
    UINT64_LIST_PACKED(38, zzhy.PACKED_VECTOR, zzil.LONG),
    INT32_LIST_PACKED(39, zzhy.PACKED_VECTOR, zzil.INT),
    FIXED64_LIST_PACKED(40, zzhy.PACKED_VECTOR, zzil.LONG),
    FIXED32_LIST_PACKED(41, zzhy.PACKED_VECTOR, zzil.INT),
    BOOL_LIST_PACKED(42, zzhy.PACKED_VECTOR, zzil.BOOLEAN),
    UINT32_LIST_PACKED(43, zzhy.PACKED_VECTOR, zzil.INT),
    ENUM_LIST_PACKED(44, zzhy.PACKED_VECTOR, zzil.ENUM),
    SFIXED32_LIST_PACKED(45, zzhy.PACKED_VECTOR, zzil.INT),
    SFIXED64_LIST_PACKED(46, zzhy.PACKED_VECTOR, zzil.LONG),
    SINT32_LIST_PACKED(47, zzhy.PACKED_VECTOR, zzil.INT),
    SINT64_LIST_PACKED(48, zzhy.PACKED_VECTOR, zzil.LONG),
    GROUP_LIST(49, zzhy.VECTOR, zzil.MESSAGE),
    MAP(50, zzhy.MAP, zzil.VOID);
    
    private static final zzhw[] zzbe = null;
    private static final Type[] zzbf = null;
    private final zzil zzaz;
    private final int zzba;
    private final zzhy zzbb;
    private final Class<?> zzbc;
    private final boolean zzbd;

    /* JADX WARNING: Code restructure failed: missing block: B:8:0x002e, code lost:
        r5 = com.google.android.gms.internal.measurement.zzhv.zzb[r6.ordinal()];
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private zzhw(int r4, com.google.android.gms.internal.measurement.zzhy r5, com.google.android.gms.internal.measurement.zzil r6) {
        /*
            r1 = this;
            r1.<init>(r2, r3)
            r1.zzba = r4
            r1.zzbb = r5
            r1.zzaz = r6
            int[] r2 = com.google.android.gms.internal.measurement.zzhv.zza
            int r3 = r5.ordinal()
            r2 = r2[r3]
            r3 = 2
            r4 = 1
            if (r2 == r4) goto L_0x0022
            if (r2 == r3) goto L_0x001b
            r2 = 0
            r1.zzbc = r2
            goto L_0x0029
        L_0x001b:
            java.lang.Class r2 = r6.zza()
            r1.zzbc = r2
            goto L_0x0029
        L_0x0022:
            java.lang.Class r2 = r6.zza()
            r1.zzbc = r2
        L_0x0029:
            r2 = 0
            com.google.android.gms.internal.measurement.zzhy r0 = com.google.android.gms.internal.measurement.zzhy.SCALAR
            if (r5 != r0) goto L_0x003e
            int[] r5 = com.google.android.gms.internal.measurement.zzhv.zzb
            int r6 = r6.ordinal()
            r5 = r5[r6]
            if (r5 == r4) goto L_0x003e
            if (r5 == r3) goto L_0x003e
            r3 = 3
            if (r5 == r3) goto L_0x003e
            goto L_0x003f
        L_0x003e:
            r4 = r2
        L_0x003f:
            r1.zzbd = r4
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.measurement.zzhw.<init>(java.lang.String, int, int, com.google.android.gms.internal.measurement.zzhy, com.google.android.gms.internal.measurement.zzil):void");
    }

    public final int zza() {
        return this.zzba;
    }

    static {
        int i;
        zzbf = new Type[0];
        zzhw[] values = values();
        zzbe = new zzhw[values.length];
        for (zzhw zzhw : values) {
            zzbe[zzhw.zzba] = zzhw;
        }
    }
}
