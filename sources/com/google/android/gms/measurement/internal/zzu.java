package com.google.android.gms.measurement.internal;

import com.google.android.gms.internal.measurement.zzbu;

/* compiled from: com.google.android.gms:play-services-measurement@@17.4.4 */
final class zzu extends zzv {
    private zzbu.zze zzg;
    private final /* synthetic */ zzo zzh;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    zzu(zzo zzo, String str, int i, zzbu.zze zze) {
        super(str, i);
        this.zzh = zzo;
        this.zzg = zze;
    }

    /* access modifiers changed from: package-private */
    public final int zza() {
        return this.zzg.zzb();
    }

    /* access modifiers changed from: package-private */
    public final boolean zzb() {
        return true;
    }

    /* access modifiers changed from: package-private */
    public final boolean zzc() {
        return false;
    }

    /* JADX WARNING: type inference failed for: r4v17, types: [java.lang.Integer] */
    /* access modifiers changed from: package-private */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final boolean zza(java.lang.Long r11, java.lang.Long r12, com.google.android.gms.internal.measurement.zzcc.zzk r13, boolean r14) {
        /*
            r10 = this;
            boolean r0 = com.google.android.gms.internal.measurement.zzna.zzb()
            r1 = 0
            r2 = 1
            if (r0 == 0) goto L_0x001b
            com.google.android.gms.measurement.internal.zzo r0 = r10.zzh
            com.google.android.gms.measurement.internal.zzy r0 = r0.zzt()
            java.lang.String r3 = r10.zza
            com.google.android.gms.measurement.internal.zzeo<java.lang.Boolean> r4 = com.google.android.gms.measurement.internal.zzaq.zzbd
            boolean r0 = r0.zzd(r3, r4)
            if (r0 == 0) goto L_0x001b
            r0 = r2
            goto L_0x001c
        L_0x001b:
            r0 = r1
        L_0x001c:
            com.google.android.gms.internal.measurement.zzbu$zze r3 = r10.zzg
            boolean r3 = r3.zze()
            com.google.android.gms.internal.measurement.zzbu$zze r4 = r10.zzg
            boolean r4 = r4.zzf()
            com.google.android.gms.internal.measurement.zzbu$zze r5 = r10.zzg
            boolean r5 = r5.zzh()
            if (r3 != 0) goto L_0x0037
            if (r4 != 0) goto L_0x0037
            if (r5 == 0) goto L_0x0035
            goto L_0x0037
        L_0x0035:
            r3 = r1
            goto L_0x0038
        L_0x0037:
            r3 = r2
        L_0x0038:
            r4 = 0
            if (r14 == 0) goto L_0x0065
            if (r3 != 0) goto L_0x0065
            com.google.android.gms.measurement.internal.zzo r11 = r10.zzh
            com.google.android.gms.measurement.internal.zzez r11 = r11.zzr()
            com.google.android.gms.measurement.internal.zzfb r11 = r11.zzx()
            int r12 = r10.zzb
            java.lang.Integer r12 = java.lang.Integer.valueOf(r12)
            com.google.android.gms.internal.measurement.zzbu$zze r13 = r10.zzg
            boolean r13 = r13.zza()
            if (r13 == 0) goto L_0x005f
            com.google.android.gms.internal.measurement.zzbu$zze r13 = r10.zzg
            int r13 = r13.zzb()
            java.lang.Integer r4 = java.lang.Integer.valueOf(r13)
        L_0x005f:
            java.lang.String r13 = "Property filter already evaluated true and it is not associated with an enhanced audience. audience ID, filter ID"
            r11.zza(r13, r12, r4)
            return r2
        L_0x0065:
            com.google.android.gms.internal.measurement.zzbu$zze r6 = r10.zzg
            com.google.android.gms.internal.measurement.zzbu$zzc r6 = r6.zzd()
            boolean r7 = r6.zzf()
            boolean r8 = r13.zzf()
            if (r8 == 0) goto L_0x00ad
            boolean r8 = r6.zzc()
            if (r8 != 0) goto L_0x009a
            com.google.android.gms.measurement.internal.zzo r6 = r10.zzh
            com.google.android.gms.measurement.internal.zzez r6 = r6.zzr()
            com.google.android.gms.measurement.internal.zzfb r6 = r6.zzi()
            com.google.android.gms.measurement.internal.zzo r7 = r10.zzh
            com.google.android.gms.measurement.internal.zzex r7 = r7.zzo()
            java.lang.String r8 = r13.zzc()
            java.lang.String r7 = r7.zzc(r8)
            java.lang.String r8 = "No number filter for long property. property"
            r6.zza(r8, r7)
            goto L_0x018f
        L_0x009a:
            long r8 = r13.zzg()
            com.google.android.gms.internal.measurement.zzbu$zzd r4 = r6.zzd()
            java.lang.Boolean r4 = zza((long) r8, (com.google.android.gms.internal.measurement.zzbu.zzd) r4)
            java.lang.Boolean r4 = zza((java.lang.Boolean) r4, (boolean) r7)
            goto L_0x018f
        L_0x00ad:
            boolean r8 = r13.zzh()
            if (r8 == 0) goto L_0x00eb
            boolean r8 = r6.zzc()
            if (r8 != 0) goto L_0x00d8
            com.google.android.gms.measurement.internal.zzo r6 = r10.zzh
            com.google.android.gms.measurement.internal.zzez r6 = r6.zzr()
            com.google.android.gms.measurement.internal.zzfb r6 = r6.zzi()
            com.google.android.gms.measurement.internal.zzo r7 = r10.zzh
            com.google.android.gms.measurement.internal.zzex r7 = r7.zzo()
            java.lang.String r8 = r13.zzc()
            java.lang.String r7 = r7.zzc(r8)
            java.lang.String r8 = "No number filter for double property. property"
            r6.zza(r8, r7)
            goto L_0x018f
        L_0x00d8:
            double r8 = r13.zzi()
            com.google.android.gms.internal.measurement.zzbu$zzd r4 = r6.zzd()
            java.lang.Boolean r4 = zza((double) r8, (com.google.android.gms.internal.measurement.zzbu.zzd) r4)
            java.lang.Boolean r4 = zza((java.lang.Boolean) r4, (boolean) r7)
            goto L_0x018f
        L_0x00eb:
            boolean r8 = r13.zzd()
            if (r8 == 0) goto L_0x0171
            boolean r8 = r6.zza()
            if (r8 != 0) goto L_0x0159
            boolean r8 = r6.zzc()
            if (r8 != 0) goto L_0x011b
            com.google.android.gms.measurement.internal.zzo r6 = r10.zzh
            com.google.android.gms.measurement.internal.zzez r6 = r6.zzr()
            com.google.android.gms.measurement.internal.zzfb r6 = r6.zzi()
            com.google.android.gms.measurement.internal.zzo r7 = r10.zzh
            com.google.android.gms.measurement.internal.zzex r7 = r7.zzo()
            java.lang.String r8 = r13.zzc()
            java.lang.String r7 = r7.zzc(r8)
            java.lang.String r8 = "No string or number filter defined. property"
            r6.zza(r8, r7)
            goto L_0x0158
        L_0x011b:
            java.lang.String r8 = r13.zze()
            boolean r8 = com.google.android.gms.measurement.internal.zzks.zza((java.lang.String) r8)
            if (r8 == 0) goto L_0x0137
            java.lang.String r4 = r13.zze()
            com.google.android.gms.internal.measurement.zzbu$zzd r6 = r6.zzd()
            java.lang.Boolean r4 = zza((java.lang.String) r4, (com.google.android.gms.internal.measurement.zzbu.zzd) r6)
            java.lang.Boolean r4 = zza((java.lang.Boolean) r4, (boolean) r7)
            goto L_0x018f
        L_0x0137:
            com.google.android.gms.measurement.internal.zzo r6 = r10.zzh
            com.google.android.gms.measurement.internal.zzez r6 = r6.zzr()
            com.google.android.gms.measurement.internal.zzfb r6 = r6.zzi()
            com.google.android.gms.measurement.internal.zzo r7 = r10.zzh
            com.google.android.gms.measurement.internal.zzex r7 = r7.zzo()
            java.lang.String r8 = r13.zzc()
            java.lang.String r7 = r7.zzc(r8)
            java.lang.String r8 = r13.zze()
            java.lang.String r9 = "Invalid user property value for Numeric number filter. property, value"
            r6.zza(r9, r7, r8)
        L_0x0158:
            goto L_0x018f
        L_0x0159:
            java.lang.String r4 = r13.zze()
            com.google.android.gms.internal.measurement.zzbu$zzf r6 = r6.zzb()
            com.google.android.gms.measurement.internal.zzo r8 = r10.zzh
            com.google.android.gms.measurement.internal.zzez r8 = r8.zzr()
            java.lang.Boolean r4 = zza((java.lang.String) r4, (com.google.android.gms.internal.measurement.zzbu.zzf) r6, (com.google.android.gms.measurement.internal.zzez) r8)
            java.lang.Boolean r4 = zza((java.lang.Boolean) r4, (boolean) r7)
            goto L_0x018f
        L_0x0171:
            com.google.android.gms.measurement.internal.zzo r6 = r10.zzh
            com.google.android.gms.measurement.internal.zzez r6 = r6.zzr()
            com.google.android.gms.measurement.internal.zzfb r6 = r6.zzi()
            com.google.android.gms.measurement.internal.zzo r7 = r10.zzh
            com.google.android.gms.measurement.internal.zzex r7 = r7.zzo()
            java.lang.String r8 = r13.zzc()
            java.lang.String r7 = r7.zzc(r8)
            java.lang.String r8 = "User property has no value, property"
            r6.zza(r8, r7)
        L_0x018f:
            com.google.android.gms.measurement.internal.zzo r6 = r10.zzh
            com.google.android.gms.measurement.internal.zzez r6 = r6.zzr()
            com.google.android.gms.measurement.internal.zzfb r6 = r6.zzx()
            if (r4 != 0) goto L_0x019f
            java.lang.String r7 = "null"
            goto L_0x01a0
        L_0x019f:
            r7 = r4
        L_0x01a0:
            java.lang.String r8 = "Property filter result"
            r6.zza(r8, r7)
            if (r4 != 0) goto L_0x01a8
            return r1
        L_0x01a8:
            java.lang.Boolean r1 = java.lang.Boolean.valueOf(r2)
            r10.zzc = r1
            if (r5 == 0) goto L_0x01b7
            boolean r1 = r4.booleanValue()
            if (r1 != 0) goto L_0x01b7
            return r2
        L_0x01b7:
            if (r14 == 0) goto L_0x01c1
            com.google.android.gms.internal.measurement.zzbu$zze r14 = r10.zzg
            boolean r14 = r14.zze()
            if (r14 == 0) goto L_0x01c3
        L_0x01c1:
            r10.zzd = r4
        L_0x01c3:
            boolean r14 = r4.booleanValue()
            if (r14 == 0) goto L_0x0209
            if (r3 == 0) goto L_0x0209
            boolean r14 = r13.zza()
            if (r14 == 0) goto L_0x0209
            long r13 = r13.zzb()
            if (r11 == 0) goto L_0x01dc
            long r13 = r11.longValue()
        L_0x01dc:
            if (r0 == 0) goto L_0x01f4
            com.google.android.gms.internal.measurement.zzbu$zze r11 = r10.zzg
            boolean r11 = r11.zze()
            if (r11 == 0) goto L_0x01f4
            com.google.android.gms.internal.measurement.zzbu$zze r11 = r10.zzg
            boolean r11 = r11.zzf()
            if (r11 != 0) goto L_0x01f4
            if (r12 == 0) goto L_0x01f4
            long r13 = r12.longValue()
        L_0x01f4:
            com.google.android.gms.internal.measurement.zzbu$zze r11 = r10.zzg
            boolean r11 = r11.zzf()
            if (r11 == 0) goto L_0x0203
            java.lang.Long r11 = java.lang.Long.valueOf(r13)
            r10.zzf = r11
            goto L_0x0209
        L_0x0203:
            java.lang.Long r11 = java.lang.Long.valueOf(r13)
            r10.zze = r11
        L_0x0209:
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.measurement.internal.zzu.zza(java.lang.Long, java.lang.Long, com.google.android.gms.internal.measurement.zzcc$zzk, boolean):boolean");
    }
}
