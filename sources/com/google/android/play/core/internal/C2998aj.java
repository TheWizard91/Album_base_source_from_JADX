package com.google.android.play.core.internal;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.IBinder;

/* renamed from: com.google.android.play.core.internal.aj */
final class C2998aj implements ServiceConnection {

    /* renamed from: a */
    final /* synthetic */ C2999ak f1284a;

    /* synthetic */ C2998aj(C2999ak akVar) {
        this.f1284a = akVar;
    }

    public final void onServiceConnected(ComponentName componentName, IBinder iBinder) {
        this.f1284a.f1287c.mo44090c("ServiceConnectionImpl.onServiceConnected(%s)", componentName);
        this.f1284a.m635b((C2990ab) new C2996ah(this, iBinder));
    }

    public final void onServiceDisconnected(ComponentName componentName) {
        this.f1284a.f1287c.mo44090c("ServiceConnectionImpl.onServiceDisconnected(%s)", componentName);
        this.f1284a.m635b((C2990ab) new C2997ai(this));
    }
}
