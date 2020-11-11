package com.google.android.gms.internal.measurement;

import android.database.ContentObserver;
import android.os.Handler;

/* compiled from: com.google.android.gms:play-services-measurement-impl@@17.4.4 */
final class zzct extends ContentObserver {
    zzct(zzcr zzcr, Handler handler) {
        super((Handler) null);
    }

    public final void onChange(boolean z) {
        zzcw.zza();
    }
}
