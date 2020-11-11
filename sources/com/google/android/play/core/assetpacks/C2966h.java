package com.google.android.play.core.assetpacks;

import android.app.PendingIntent;
import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;
import com.google.android.play.core.tasks.C3169i;

/* renamed from: com.google.android.play.core.assetpacks.h */
final class C2966h extends ResultReceiver {

    /* renamed from: a */
    final /* synthetic */ C3169i f1226a;

    /* renamed from: b */
    final /* synthetic */ C2967i f1227b;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    C2966h(C2967i iVar, Handler handler, C3169i iVar2) {
        super(handler);
        this.f1227b = iVar;
        this.f1226a = iVar2;
    }

    public final void onReceiveResult(int i, Bundle bundle) {
        if (i == 1) {
            this.f1226a.mo44330a(-1);
            this.f1227b.f1235h.mo44008a((PendingIntent) null);
        } else if (i != 2) {
            this.f1226a.mo44329a((Exception) new AssetPackException(-100));
        } else {
            this.f1226a.mo44330a(0);
        }
    }
}
