package com.google.android.gms.internal.measurement;

import android.util.Log;

/* compiled from: com.google.android.gms:play-services-measurement-impl@@17.4.4 */
final class zzda extends zzcw<Boolean> {
    zzda(zzdf zzdf, String str, Boolean bool, boolean z) {
        super(zzdf, str, bool, z, (zzdb) null);
    }

    /* access modifiers changed from: package-private */
    public final /* synthetic */ Object zza(Object obj) {
        if (obj instanceof Boolean) {
            return (Boolean) obj;
        }
        if (obj instanceof String) {
            String str = (String) obj;
            if (zzcf.zzb.matcher(str).matches()) {
                return true;
            }
            if (zzcf.zzc.matcher(str).matches()) {
                return false;
            }
        }
        String zzb = super.zzb();
        String valueOf = String.valueOf(obj);
        Log.e("PhenotypeFlag", new StringBuilder(String.valueOf(zzb).length() + 28 + String.valueOf(valueOf).length()).append("Invalid boolean value for ").append(zzb).append(": ").append(valueOf).toString());
        return null;
    }
}
