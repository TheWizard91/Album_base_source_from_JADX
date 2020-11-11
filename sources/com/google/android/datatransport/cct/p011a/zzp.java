package com.google.android.datatransport.cct.p011a;

import com.google.android.datatransport.cct.p011a.zzg;

/* renamed from: com.google.android.datatransport.cct.a.zzp */
public abstract class zzp {

    /* renamed from: com.google.android.datatransport.cct.a.zzp$zza */
    public static abstract class zza {
        public abstract zza zza(zza zza);

        public abstract zza zza(zzb zzb);

        public abstract zzp zza();
    }

    /* renamed from: com.google.android.datatransport.cct.a.zzp$zzb */
    public enum zzb {
        UNKNOWN(0),
        ANDROID_FIREBASE(23);

        static {
            zzb zzb = new zzb("UNKNOWN", 0, 0);
            UNKNOWN = zzb;
            zzb zzb2 = new zzb("ANDROID_FIREBASE", 1, 23);
            ANDROID_FIREBASE = zzb2;
            zzb[] zzbArr = {zzb, zzb2};
        }

        private zzb(int i) {
        }
    }

    public static zza zza() {
        return new zzg.zza();
    }

    public abstract zza zzb();

    public abstract zzb zzc();
}
