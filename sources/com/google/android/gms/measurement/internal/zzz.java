package com.google.android.gms.measurement.internal;

import android.os.Parcelable;

/* compiled from: com.google.android.gms:play-services-measurement-impl@@17.4.4 */
public final class zzz implements Parcelable.Creator<zzw> {
    public final /* synthetic */ Object[] newArray(int i) {
        return new zzw[i];
    }

    /* JADX WARNING: type inference failed for: r2v3, types: [android.os.Parcelable] */
    /* JADX WARNING: type inference failed for: r2v4, types: [android.os.Parcelable] */
    /* JADX WARNING: type inference failed for: r2v5, types: [android.os.Parcelable] */
    /* JADX WARNING: type inference failed for: r2v6, types: [android.os.Parcelable] */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final /* synthetic */ java.lang.Object createFromParcel(android.os.Parcel r22) {
        /*
            r21 = this;
            r0 = r22
            int r1 = com.google.android.gms.common.internal.safeparcel.SafeParcelReader.validateObjectHeader(r22)
            r2 = 0
            r4 = 0
            r5 = 0
            r10 = r2
            r15 = r10
            r18 = r15
            r7 = r4
            r8 = r7
            r9 = r8
            r13 = r9
            r14 = r13
            r17 = r14
            r20 = r17
            r12 = r5
        L_0x0022:
            int r2 = r22.dataPosition()
            if (r2 >= r1) goto L_0x008b
            int r2 = com.google.android.gms.common.internal.safeparcel.SafeParcelReader.readHeader(r22)
            int r3 = com.google.android.gms.common.internal.safeparcel.SafeParcelReader.getFieldId(r2)
            switch(r3) {
                case 2: goto L_0x0085;
                case 3: goto L_0x007f;
                case 4: goto L_0x0075;
                case 5: goto L_0x006f;
                case 6: goto L_0x0069;
                case 7: goto L_0x0063;
                case 8: goto L_0x0059;
                case 9: goto L_0x0053;
                case 10: goto L_0x0048;
                case 11: goto L_0x0042;
                case 12: goto L_0x0037;
                default: goto L_0x0033;
            }
        L_0x0033:
            com.google.android.gms.common.internal.safeparcel.SafeParcelReader.skipUnknownField(r0, r2)
            goto L_0x0022
        L_0x0037:
            android.os.Parcelable$Creator<com.google.android.gms.measurement.internal.zzao> r3 = com.google.android.gms.measurement.internal.zzao.CREATOR
            android.os.Parcelable r2 = com.google.android.gms.common.internal.safeparcel.SafeParcelReader.createParcelable(r0, r2, r3)
            r20 = r2
            com.google.android.gms.measurement.internal.zzao r20 = (com.google.android.gms.measurement.internal.zzao) r20
            goto L_0x0022
        L_0x0042:
            long r18 = com.google.android.gms.common.internal.safeparcel.SafeParcelReader.readLong(r0, r2)
            goto L_0x0022
        L_0x0048:
            android.os.Parcelable$Creator<com.google.android.gms.measurement.internal.zzao> r3 = com.google.android.gms.measurement.internal.zzao.CREATOR
            android.os.Parcelable r2 = com.google.android.gms.common.internal.safeparcel.SafeParcelReader.createParcelable(r0, r2, r3)
            r17 = r2
            com.google.android.gms.measurement.internal.zzao r17 = (com.google.android.gms.measurement.internal.zzao) r17
            goto L_0x0022
        L_0x0053:
            long r15 = com.google.android.gms.common.internal.safeparcel.SafeParcelReader.readLong(r0, r2)
            goto L_0x0022
        L_0x0059:
            android.os.Parcelable$Creator<com.google.android.gms.measurement.internal.zzao> r3 = com.google.android.gms.measurement.internal.zzao.CREATOR
            android.os.Parcelable r2 = com.google.android.gms.common.internal.safeparcel.SafeParcelReader.createParcelable(r0, r2, r3)
            r14 = r2
            com.google.android.gms.measurement.internal.zzao r14 = (com.google.android.gms.measurement.internal.zzao) r14
            goto L_0x0022
        L_0x0063:
            java.lang.String r13 = com.google.android.gms.common.internal.safeparcel.SafeParcelReader.createString(r0, r2)
            goto L_0x0022
        L_0x0069:
            boolean r12 = com.google.android.gms.common.internal.safeparcel.SafeParcelReader.readBoolean(r0, r2)
            goto L_0x0022
        L_0x006f:
            long r10 = com.google.android.gms.common.internal.safeparcel.SafeParcelReader.readLong(r0, r2)
            goto L_0x0022
        L_0x0075:
            android.os.Parcelable$Creator<com.google.android.gms.measurement.internal.zzkr> r3 = com.google.android.gms.measurement.internal.zzkr.CREATOR
            android.os.Parcelable r2 = com.google.android.gms.common.internal.safeparcel.SafeParcelReader.createParcelable(r0, r2, r3)
            r9 = r2
            com.google.android.gms.measurement.internal.zzkr r9 = (com.google.android.gms.measurement.internal.zzkr) r9
            goto L_0x0022
        L_0x007f:
            java.lang.String r8 = com.google.android.gms.common.internal.safeparcel.SafeParcelReader.createString(r0, r2)
            goto L_0x0022
        L_0x0085:
            java.lang.String r7 = com.google.android.gms.common.internal.safeparcel.SafeParcelReader.createString(r0, r2)
            goto L_0x0022
        L_0x008b:
            com.google.android.gms.common.internal.safeparcel.SafeParcelReader.ensureAtEnd(r0, r1)
            com.google.android.gms.measurement.internal.zzw r0 = new com.google.android.gms.measurement.internal.zzw
            r6 = r0
            r6.<init>(r7, r8, r9, r10, r12, r13, r14, r15, r17, r18, r20)
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.measurement.internal.zzz.createFromParcel(android.os.Parcel):java.lang.Object");
    }
}
