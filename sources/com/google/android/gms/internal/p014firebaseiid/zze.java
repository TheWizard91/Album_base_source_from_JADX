package com.google.android.gms.internal.p014firebaseiid;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

/* renamed from: com.google.android.gms.internal.firebase-iid.zze */
/* compiled from: com.google.firebase:firebase-iid@@20.2.3 */
public class zze extends Handler {
    private static zzg propagator = null;

    public zze() {
    }

    public zze(Looper looper) {
        super(looper);
    }

    public zze(Looper looper, Handler.Callback callback) {
        super(looper, callback);
    }

    public boolean sendMessageAtTime(Message message, long j) {
        prepare(message, j);
        return super.sendMessageAtTime(message, j);
    }

    public final void dispatchMessage(Message message) {
        zzg zzg = propagator;
        if (zzg == null) {
            dispatchMessageTraced(message);
            return;
        }
        Object zza = zzg.zza(this, message);
        try {
            dispatchMessageTraced(message);
            zzg.zza(this, message, zza);
        } catch (Throwable th) {
            zzg.zza(this, message, zza);
            throw th;
        }
    }

    /* access modifiers changed from: protected */
    public void dispatchMessageTraced(Message message) {
        super.dispatchMessage(message);
    }

    private void prepare(Message message, long j) {
        zzg zzg = propagator;
        if (zzg != null) {
            zzg.zza(this, message, j);
        }
    }
}
