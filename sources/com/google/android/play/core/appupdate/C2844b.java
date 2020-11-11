package com.google.android.play.core.appupdate;

import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;
import com.google.android.play.core.tasks.C3169i;

/* renamed from: com.google.android.play.core.appupdate.b */
final class C2844b extends ResultReceiver {

    /* renamed from: a */
    final /* synthetic */ C3169i f817a;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    C2844b(Handler handler, C3169i iVar) {
        super(handler);
        this.f817a = iVar;
    }

    public final void onReceiveResult(int i, Bundle bundle) {
        C3169i iVar;
        int i2 = 1;
        if (i == 1) {
            iVar = this.f817a;
            i2 = -1;
        } else if (i != 2) {
            iVar = this.f817a;
        } else {
            iVar = this.f817a;
            i2 = 0;
        }
        iVar.mo44330a(Integer.valueOf(i2));
    }
}
