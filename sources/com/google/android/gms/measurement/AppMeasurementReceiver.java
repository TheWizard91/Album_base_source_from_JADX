package com.google.android.gms.measurement;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import androidx.legacy.content.WakefulBroadcastReceiver;
import com.google.android.gms.measurement.internal.zzfu;
import com.google.android.gms.measurement.internal.zzfv;

/* compiled from: com.google.android.gms:play-services-measurement@@17.4.4 */
public final class AppMeasurementReceiver extends WakefulBroadcastReceiver implements zzfu {
    private zzfv zza;

    public final void onReceive(Context context, Intent intent) {
        if (this.zza == null) {
            this.zza = new zzfv(this);
        }
        this.zza.zza(context, intent);
    }

    public final void doStartService(Context context, Intent intent) {
        startWakefulService(context, intent);
    }

    public final BroadcastReceiver.PendingResult doGoAsync() {
        return goAsync();
    }
}
