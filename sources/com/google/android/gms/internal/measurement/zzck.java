package com.google.android.gms.internal.measurement;

import android.database.ContentObserver;
import android.os.Handler;

/* compiled from: com.google.android.gms:play-services-measurement-impl@@17.4.4 */
final class zzck extends ContentObserver {
    private final /* synthetic */ zzci zza;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    zzck(zzci zzci, Handler handler) {
        super((Handler) null);
        this.zza = zzci;
    }

    public final void onChange(boolean z) {
        this.zza.zzb();
    }
}
