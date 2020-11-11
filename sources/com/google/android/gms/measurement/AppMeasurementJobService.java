package com.google.android.gms.measurement;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Intent;
import com.google.android.gms.measurement.internal.zzjt;
import com.google.android.gms.measurement.internal.zzjx;

/* compiled from: com.google.android.gms:play-services-measurement@@17.4.4 */
public final class AppMeasurementJobService extends JobService implements zzjx {
    private zzjt<AppMeasurementJobService> zza;

    private final zzjt<AppMeasurementJobService> zza() {
        if (this.zza == null) {
            this.zza = new zzjt<>(this);
        }
        return this.zza;
    }

    public final void onCreate() {
        super.onCreate();
        zza().zza();
    }

    public final void onDestroy() {
        zza().zzb();
        super.onDestroy();
    }

    public final boolean onStartJob(JobParameters jobParameters) {
        return zza().zza(jobParameters);
    }

    public final boolean onStopJob(JobParameters jobParameters) {
        return false;
    }

    public final boolean onUnbind(Intent intent) {
        return zza().zzb(intent);
    }

    public final void onRebind(Intent intent) {
        zza().zzc(intent);
    }

    public final boolean zza(int i) {
        throw new UnsupportedOperationException();
    }

    public final void zza(JobParameters jobParameters, boolean z) {
        jobFinished(jobParameters, false);
    }

    public final void zza(Intent intent) {
    }
}
