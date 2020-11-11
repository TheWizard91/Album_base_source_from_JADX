package com.google.android.gms.measurement.internal;

import android.content.ComponentName;
import android.content.Context;

/* compiled from: com.google.android.gms:play-services-measurement-impl@@17.4.4 */
final class zzjr implements Runnable {
    private final /* synthetic */ zzjn zza;

    zzjr(zzjn zzjn) {
        this.zza = zzjn;
    }

    public final void run() {
        zzis zzis = this.zza.zza;
        Context zzn = this.zza.zza.zzn();
        this.zza.zza.zzu();
        zzis.zza(new ComponentName(zzn, "com.google.android.gms.measurement.AppMeasurementService"));
    }
}
