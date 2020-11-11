package com.google.android.gms.measurement.internal;

import android.os.RemoteException;

/* compiled from: com.google.android.gms:play-services-measurement-impl@@17.4.4 */
final class zzja implements Runnable {
    private final /* synthetic */ zzik zza;
    private final /* synthetic */ zzis zzb;

    zzja(zzis zzis, zzik zzik) {
        this.zzb = zzis;
        this.zza = zzik;
    }

    public final void run() {
        zzer zzd = this.zzb.zzb;
        if (zzd == null) {
            this.zzb.zzr().zzf().zza("Failed to send current screen to service");
            return;
        }
        try {
            zzik zzik = this.zza;
            if (zzik == null) {
                zzd.zza(0, (String) null, (String) null, this.zzb.zzn().getPackageName());
            } else {
                zzd.zza(zzik.zzc, this.zza.zza, this.zza.zzb, this.zzb.zzn().getPackageName());
            }
            this.zzb.zzak();
        } catch (RemoteException e) {
            this.zzb.zzr().zzf().zza("Failed to send current screen to the service", e);
        }
    }
}
