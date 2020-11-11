package com.google.android.gms.common.api.internal;

import android.os.Looper;
import android.os.Message;
import android.util.Log;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.internal.base.zar;

/* compiled from: com.google.android.gms:play-services-base@@17.1.0 */
final class zacm extends zar {
    private final /* synthetic */ zack zaky;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public zacm(zack zack, Looper looper) {
        super(looper);
        this.zaky = zack;
    }

    public final void handleMessage(Message message) {
        int i = message.what;
        if (i == 0) {
            PendingResult pendingResult = (PendingResult) message.obj;
            synchronized (this.zaky.zadp) {
                if (pendingResult == null) {
                    this.zaky.zaks.zad(new Status(13, "Transform returned null"));
                } else if (pendingResult instanceof zacc) {
                    this.zaky.zaks.zad(((zacc) pendingResult).getStatus());
                } else {
                    this.zaky.zaks.zaa(pendingResult);
                }
            }
        } else if (i != 1) {
            Log.e("TransformedResultImpl", new StringBuilder(70).append("TransformationResultHandler received unknown message type: ").append(message.what).toString());
        } else {
            RuntimeException runtimeException = (RuntimeException) message.obj;
            String valueOf = String.valueOf(runtimeException.getMessage());
            Log.e("TransformedResultImpl", valueOf.length() != 0 ? "Runtime exception on the transformation worker thread: ".concat(valueOf) : new String("Runtime exception on the transformation worker thread: "));
            throw runtimeException;
        }
    }
}
