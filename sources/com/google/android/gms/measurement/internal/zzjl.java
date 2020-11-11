package com.google.android.gms.measurement.internal;

import android.os.RemoteException;
import android.text.TextUtils;
import java.util.Collections;
import java.util.concurrent.atomic.AtomicReference;

/* compiled from: com.google.android.gms:play-services-measurement-impl@@17.4.4 */
final class zzjl implements Runnable {
    private final /* synthetic */ AtomicReference zza;
    private final /* synthetic */ String zzb;
    private final /* synthetic */ String zzc;
    private final /* synthetic */ String zzd;
    private final /* synthetic */ boolean zze;
    private final /* synthetic */ zzn zzf;
    private final /* synthetic */ zzis zzg;

    zzjl(zzis zzis, AtomicReference atomicReference, String str, String str2, String str3, boolean z, zzn zzn) {
        this.zzg = zzis;
        this.zza = atomicReference;
        this.zzb = str;
        this.zzc = str2;
        this.zzd = str3;
        this.zze = z;
        this.zzf = zzn;
    }

    public final void run() {
        synchronized (this.zza) {
            try {
                zzer zzd2 = this.zzg.zzb;
                if (zzd2 == null) {
                    this.zzg.zzr().zzf().zza("(legacy) Failed to get user properties; not connected to service", zzez.zza(this.zzb), this.zzc, this.zzd);
                    this.zza.set(Collections.emptyList());
                    this.zza.notify();
                    return;
                }
                if (TextUtils.isEmpty(this.zzb)) {
                    this.zza.set(zzd2.zza(this.zzc, this.zzd, this.zze, this.zzf));
                } else {
                    this.zza.set(zzd2.zza(this.zzb, this.zzc, this.zzd, this.zze));
                }
                this.zzg.zzak();
                this.zza.notify();
            } catch (RemoteException e) {
                try {
                    this.zzg.zzr().zzf().zza("(legacy) Failed to get user properties; remote exception", zzez.zza(this.zzb), this.zzc, e);
                    this.zza.set(Collections.emptyList());
                    this.zza.notify();
                } catch (Throwable th) {
                    this.zza.notify();
                    throw th;
                }
            }
        }
    }
}
