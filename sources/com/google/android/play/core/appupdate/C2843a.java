package com.google.android.play.core.appupdate;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import com.google.android.play.core.install.InstallState;
import com.google.android.play.core.internal.C2989aa;
import com.google.android.play.core.listener.C3076b;

/* renamed from: com.google.android.play.core.appupdate.a */
public final class C2843a extends C3076b<InstallState> {
    public C2843a(Context context) {
        super(new C2989aa("AppUpdateListenerRegistry"), new IntentFilter("com.google.android.play.core.install.ACTION_INSTALL_STATUS"), context);
    }

    /* access modifiers changed from: protected */
    /* renamed from: a */
    public final void mo43835a(Context context, Intent intent) {
        Intent intent2 = intent;
        if (context.getPackageName().equals(intent2.getStringExtra("package.name"))) {
            this.f1339a.mo44087a("List of extras in received intent:", new Object[0]);
            for (String str : intent.getExtras().keySet()) {
                this.f1339a.mo44087a("Key: %s; value: %s", str, intent.getExtras().get(str));
            }
            C2989aa aaVar = this.f1339a;
            aaVar.mo44087a("List of extras in received intent needed by fromUpdateIntent:", new Object[0]);
            aaVar.mo44087a("Key: %s; value: %s", "install.status", Integer.valueOf(intent2.getIntExtra("install.status", 0)));
            aaVar.mo44087a("Key: %s; value: %s", "error.code", Integer.valueOf(intent2.getIntExtra("error.code", 0)));
            InstallState a = InstallState.m612a(intent2.getIntExtra("install.status", 0), intent2.getLongExtra("bytes.downloaded", 0), intent2.getLongExtra("total.bytes.to.download", 0), intent2.getIntExtra("error.code", 0), intent2.getStringExtra("package.name"));
            this.f1339a.mo44087a("ListenerRegistryBroadcastReceiver.onReceive: %s", a);
            mo44198a(a);
            return;
        }
        this.f1339a.mo44087a("ListenerRegistryBroadcastReceiver received broadcast for third party app: %s", intent2.getStringExtra("package.name"));
    }
}
