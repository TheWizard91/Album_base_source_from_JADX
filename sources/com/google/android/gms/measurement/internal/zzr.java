package com.google.android.gms.measurement.internal;

import com.google.android.gms.internal.measurement.zzbu;

/* compiled from: com.google.android.gms:play-services-measurement@@17.4.4 */
final /* synthetic */ class zzr {
    static final /* synthetic */ int[] zza;
    static final /* synthetic */ int[] zzb;

    static {
        int[] iArr = new int[zzbu.zzd.zzb.values().length];
        zzb = iArr;
        try {
            iArr[zzbu.zzd.zzb.LESS_THAN.ordinal()] = 1;
        } catch (NoSuchFieldError e) {
        }
        try {
            zzb[zzbu.zzd.zzb.GREATER_THAN.ordinal()] = 2;
        } catch (NoSuchFieldError e2) {
        }
        try {
            zzb[zzbu.zzd.zzb.EQUAL.ordinal()] = 3;
        } catch (NoSuchFieldError e3) {
        }
        try {
            zzb[zzbu.zzd.zzb.BETWEEN.ordinal()] = 4;
        } catch (NoSuchFieldError e4) {
        }
        int[] iArr2 = new int[zzbu.zzf.zza.values().length];
        zza = iArr2;
        try {
            iArr2[zzbu.zzf.zza.REGEXP.ordinal()] = 1;
        } catch (NoSuchFieldError e5) {
        }
        try {
            zza[zzbu.zzf.zza.BEGINS_WITH.ordinal()] = 2;
        } catch (NoSuchFieldError e6) {
        }
        try {
            zza[zzbu.zzf.zza.ENDS_WITH.ordinal()] = 3;
        } catch (NoSuchFieldError e7) {
        }
        try {
            zza[zzbu.zzf.zza.PARTIAL.ordinal()] = 4;
        } catch (NoSuchFieldError e8) {
        }
        try {
            zza[zzbu.zzf.zza.EXACT.ordinal()] = 5;
        } catch (NoSuchFieldError e9) {
        }
        try {
            zza[zzbu.zzf.zza.IN_LIST.ordinal()] = 6;
        } catch (NoSuchFieldError e10) {
        }
    }
}
