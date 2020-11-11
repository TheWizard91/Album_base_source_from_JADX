package com.google.android.datatransport.cct.p011a;

import com.google.android.datatransport.cct.p011a.zzk;
import com.google.firebase.encoders.annotations.Encodable;
import java.util.List;

/* renamed from: com.google.android.datatransport.cct.a.zzr */
public abstract class zzr {

    /* renamed from: com.google.android.datatransport.cct.a.zzr$zza */
    public static abstract class zza {
        public zza zza(int i) {
            return zza(Integer.valueOf(i));
        }

        public abstract zza zza(long j);

        public abstract zza zza(zzp zzp);

        public abstract zza zza(zzu zzu);

        /* access modifiers changed from: package-private */
        public abstract zza zza(Integer num);

        /* access modifiers changed from: package-private */
        public abstract zza zza(String str);

        public abstract zza zza(List<zzq> list);

        public abstract zzr zza();

        public abstract zza zzb(long j);

        public zza zzb(String str) {
            return zza(str);
        }
    }

    public static zza zza() {
        return new zzk.zza();
    }

    public abstract zzp zzb();

    @Encodable.Field(name = "logEvent")
    public abstract List<zzq> zzc();

    public abstract Integer zzd();

    public abstract String zze();

    public abstract zzu zzf();

    public abstract long zzg();

    public abstract long zzh();
}
