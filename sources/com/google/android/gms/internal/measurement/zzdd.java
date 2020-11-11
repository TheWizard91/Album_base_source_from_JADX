package com.google.android.gms.internal.measurement;

import android.util.Log;

/* compiled from: com.google.android.gms:play-services-measurement-impl@@17.4.4 */
final class zzdd extends zzcw<Double> {
    zzdd(zzdf zzdf, String str, Double d, boolean z) {
        super(zzdf, str, d, z, (zzdb) null);
    }

    /* access modifiers changed from: private */
    /* renamed from: zzb */
    public final Double zza(Object obj) {
        if (obj instanceof Double) {
            return (Double) obj;
        }
        if (obj instanceof Float) {
            return Double.valueOf(((Float) obj).doubleValue());
        }
        if (obj instanceof String) {
            try {
                return Double.valueOf(Double.parseDouble((String) obj));
            } catch (NumberFormatException e) {
            }
        }
        String zzb = super.zzb();
        String valueOf = String.valueOf(obj);
        Log.e("PhenotypeFlag", new StringBuilder(String.valueOf(zzb).length() + 27 + String.valueOf(valueOf).length()).append("Invalid double value for ").append(zzb).append(": ").append(valueOf).toString());
        return null;
    }
}
