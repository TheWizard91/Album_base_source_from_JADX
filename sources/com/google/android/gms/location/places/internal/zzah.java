package com.google.android.gms.location.places.internal;

import android.os.Parcelable;

public final class zzah implements Parcelable.Creator<zzai> {
    public final /* synthetic */ Object[] newArray(int i) {
        return new zzai[i];
    }

    /* JADX WARNING: type inference failed for: r1v3, types: [android.os.Parcelable] */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final /* synthetic */ java.lang.Object createFromParcel(android.os.Parcel r11) {
        /*
            r10 = this;
            int r0 = com.google.android.gms.common.internal.safeparcel.SafeParcelReader.validateObjectHeader(r11)
            r1 = 0
            r2 = 0
            r3 = 0
            r5 = r1
            r6 = r5
            r7 = r6
            r8 = r2
            r9 = r3
        L_0x0011:
            int r1 = r11.dataPosition()
            if (r1 >= r0) goto L_0x0054
            int r1 = com.google.android.gms.common.internal.safeparcel.SafeParcelReader.readHeader(r11)
            int r2 = com.google.android.gms.common.internal.safeparcel.SafeParcelReader.getFieldId(r1)
            r3 = 1
            if (r2 == r3) goto L_0x004e
            r3 = 2
            if (r2 == r3) goto L_0x0048
            r3 = 3
            if (r2 == r3) goto L_0x003e
            r3 = 4
            if (r2 == r3) goto L_0x0038
            r3 = 5
            if (r2 == r3) goto L_0x0032
            com.google.android.gms.common.internal.safeparcel.SafeParcelReader.skipUnknownField(r11, r1)
            goto L_0x0011
        L_0x0032:
            int r9 = com.google.android.gms.common.internal.safeparcel.SafeParcelReader.readInt(r11, r1)
            goto L_0x0011
        L_0x0038:
            float r8 = com.google.android.gms.common.internal.safeparcel.SafeParcelReader.readFloat(r11, r1)
            goto L_0x0011
        L_0x003e:
            android.os.Parcelable$Creator r2 = android.net.Uri.CREATOR
            android.os.Parcelable r1 = com.google.android.gms.common.internal.safeparcel.SafeParcelReader.createParcelable(r11, r1, r2)
            r7 = r1
            android.net.Uri r7 = (android.net.Uri) r7
            goto L_0x0011
        L_0x0048:
            java.lang.String r6 = com.google.android.gms.common.internal.safeparcel.SafeParcelReader.createString(r11, r1)
            goto L_0x0011
        L_0x004e:
            java.util.ArrayList r5 = com.google.android.gms.common.internal.safeparcel.SafeParcelReader.createIntegerList(r11, r1)
            goto L_0x0011
        L_0x0054:
            com.google.android.gms.common.internal.safeparcel.SafeParcelReader.ensureAtEnd(r11, r0)
            com.google.android.gms.location.places.internal.zzai r11 = new com.google.android.gms.location.places.internal.zzai
            r4 = r11
            r4.<init>(r5, r6, r7, r8, r9)
            return r11
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.location.places.internal.zzah.createFromParcel(android.os.Parcel):java.lang.Object");
    }
}
