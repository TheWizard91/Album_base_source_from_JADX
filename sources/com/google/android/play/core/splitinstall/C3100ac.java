package com.google.android.play.core.splitinstall;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import com.google.android.play.core.internal.C2989aa;
import com.google.android.play.core.listener.C3076b;

/* renamed from: com.google.android.play.core.splitinstall.ac */
public final class C3100ac extends C3076b<SplitInstallSessionState> {

    /* renamed from: c */
    private static C3100ac f1409c = null;

    /* renamed from: d */
    private final Handler f1410d = new Handler(Looper.getMainLooper());

    /* renamed from: e */
    private final C3127c f1411e;

    public C3100ac(Context context, C3127c cVar) {
        super(new C2989aa("SplitInstallListenerRegistry"), new IntentFilter("com.google.android.play.core.splitinstall.receiver.SplitInstallUpdateIntentService"), context);
        this.f1411e = cVar;
    }

    /* renamed from: a */
    public static synchronized C3100ac m949a(Context context) {
        C3100ac acVar;
        synchronized (C3100ac.class) {
            if (f1409c == null) {
                f1409c = new C3100ac(context, C3157w.f1517a);
            }
            acVar = f1409c;
        }
        return acVar;
    }

    /* access modifiers changed from: protected */
    /* renamed from: a */
    public final void mo43835a(Context context, Intent intent) {
        Bundle bundleExtra = intent.getBundleExtra("session_state");
        if (bundleExtra != null) {
            SplitInstallSessionState a = SplitInstallSessionState.m938a(bundleExtra);
            this.f1339a.mo44087a("ListenerRegistryBroadcastReceiver.onReceive: %s", a);
            C3128d a2 = this.f1411e.mo44292a();
            if (a.status() == 3 && a2 != null) {
                a2.mo44103a(a.mo44264c(), new C3098aa(this, a, intent, context));
            } else {
                mo44198a(a);
            }
        }
    }
}
