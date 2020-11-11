package com.google.android.gms.measurement.internal;

import android.content.Context;
import android.content.res.Resources;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.internal.StringResourceValueReader;

/* compiled from: com.google.android.gms:play-services-measurement-base@@17.4.4 */
public final class zzil {
    public static String zza(Context context, String str) {
        try {
            return new StringResourceValueReader(context).getString(str);
        } catch (Resources.NotFoundException e) {
            return null;
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:19:0x0039 A[Catch:{ IOException -> 0x003f, ClassNotFoundException -> 0x003d }] */
    /* JADX WARNING: Removed duplicated region for block: B:25:0x0043 A[Catch:{ IOException -> 0x003f, ClassNotFoundException -> 0x003d }] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.lang.Object zza(java.lang.Object r4) {
        /*
            r0 = 0
            if (r4 != 0) goto L_0x0004
            return r0
        L_0x0004:
            java.io.ByteArrayOutputStream r1 = new java.io.ByteArrayOutputStream     // Catch:{ all -> 0x0034 }
            r1.<init>()     // Catch:{ all -> 0x0034 }
            java.io.ObjectOutputStream r2 = new java.io.ObjectOutputStream     // Catch:{ all -> 0x0034 }
            r2.<init>(r1)     // Catch:{ all -> 0x0034 }
            r2.writeObject(r4)     // Catch:{ all -> 0x0031 }
            r2.flush()     // Catch:{ all -> 0x0031 }
            java.io.ObjectInputStream r4 = new java.io.ObjectInputStream     // Catch:{ all -> 0x0031 }
            java.io.ByteArrayInputStream r3 = new java.io.ByteArrayInputStream     // Catch:{ all -> 0x0031 }
            byte[] r1 = r1.toByteArray()     // Catch:{ all -> 0x0031 }
            r3.<init>(r1)     // Catch:{ all -> 0x0031 }
            r4.<init>(r3)     // Catch:{ all -> 0x0031 }
            java.lang.Object r1 = r4.readObject()     // Catch:{ all -> 0x002f }
            r2.close()     // Catch:{ IOException -> 0x003f, ClassNotFoundException -> 0x003d }
            r4.close()     // Catch:{ IOException -> 0x003f, ClassNotFoundException -> 0x003d }
            return r1
        L_0x002f:
            r1 = move-exception
            goto L_0x0037
        L_0x0031:
            r1 = move-exception
            r4 = r0
            goto L_0x0037
        L_0x0034:
            r1 = move-exception
            r4 = r0
            r2 = r4
        L_0x0037:
            if (r2 == 0) goto L_0x0041
            r2.close()     // Catch:{ IOException -> 0x003f, ClassNotFoundException -> 0x003d }
            goto L_0x0041
        L_0x003d:
            r4 = move-exception
            goto L_0x0047
        L_0x003f:
            r4 = move-exception
            goto L_0x0047
        L_0x0041:
            if (r4 == 0) goto L_0x0046
            r4.close()     // Catch:{ IOException -> 0x003f, ClassNotFoundException -> 0x003d }
        L_0x0046:
            throw r1     // Catch:{ IOException -> 0x003f, ClassNotFoundException -> 0x003d }
        L_0x0047:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.measurement.internal.zzil.zza(java.lang.Object):java.lang.Object");
    }

    public static String zza(String str, String[] strArr, String[] strArr2) {
        boolean z;
        Preconditions.checkNotNull(strArr);
        Preconditions.checkNotNull(strArr2);
        int min = Math.min(strArr.length, strArr2.length);
        for (int i = 0; i < min; i++) {
            String str2 = strArr[i];
            if (str == null && str2 == null) {
                z = true;
            } else if (str == null) {
                z = false;
            } else {
                z = str.equals(str2);
            }
            if (z) {
                return strArr2[i];
            }
        }
        return null;
    }
}
