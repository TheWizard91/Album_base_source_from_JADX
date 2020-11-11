package com.google.android.gms.measurement.internal;

import androidx.exifinterface.media.ExifInterface;

/* compiled from: com.google.android.gms:play-services-measurement-impl@@17.4.4 */
final class zzey implements Runnable {
    private final /* synthetic */ int zza;
    private final /* synthetic */ String zzb;
    private final /* synthetic */ Object zzc;
    private final /* synthetic */ Object zzd;
    private final /* synthetic */ Object zze;
    private final /* synthetic */ zzez zzf;

    zzey(zzez zzez, int i, String str, Object obj, Object obj2, Object obj3) {
        this.zzf = zzez;
        this.zza = i;
        this.zzb = str;
        this.zzc = obj;
        this.zzd = obj2;
        this.zze = obj3;
    }

    public final void run() {
        zzfl zzc2 = this.zzf.zzy.zzc();
        if (zzc2.zzz()) {
            if (this.zzf.zza == 0) {
                if (this.zzf.zzt().zzg()) {
                    zzez zzez = this.zzf;
                    zzez.zzu();
                    char unused = zzez.zza = 'C';
                } else {
                    zzez zzez2 = this.zzf;
                    zzez2.zzu();
                    char unused2 = zzez2.zza = 'c';
                }
            }
            if (this.zzf.zzb < 0) {
                zzez zzez3 = this.zzf;
                long unused3 = zzez3.zzb = zzez3.zzt().zzf();
            }
            char charAt = "01VDIWEA?".charAt(this.zza);
            char zza2 = this.zzf.zza;
            long zzb2 = this.zzf.zzb;
            String zza3 = zzez.zza(true, this.zzb, this.zzc, this.zzd, this.zze);
            String sb = new StringBuilder(String.valueOf(zza3).length() + 24).append(ExifInterface.GPS_MEASUREMENT_2D).append(charAt).append(zza2).append(zzb2).append(":").append(zza3).toString();
            if (sb.length() > 1024) {
                sb = this.zzb.substring(0, 1024);
            }
            zzc2.zzb.zza(sb, 1);
            return;
        }
        this.zzf.zza(6, "Persisted config not initialized. Not logging error/warn");
    }
}
